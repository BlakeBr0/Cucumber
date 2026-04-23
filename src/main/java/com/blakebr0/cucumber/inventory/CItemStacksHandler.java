package com.blakebr0.cucumber.inventory;

import com.blakebr0.cucumber.crafting.ShapelessCraftingInput;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class CItemStacksHandler extends ItemStacksResourceHandler {
    // copy of ItemStack#CODEC that removes the stupid int range limit on count
    private static final Codec<ItemStack> ITEM_STACK_CODEC = RecordCodecBuilder.create(builder ->
            builder.group(
                    Item.CODEC_WITH_BOUND_COMPONENTS.fieldOf("id").forGetter(ItemStack::typeHolder),
                    Codec.INT.optionalFieldOf("count", 1).forGetter(ItemStack::getCount),
                    DataComponentPatch.CODEC.optionalFieldOf("components", DataComponentPatch.EMPTY).forGetter(ItemStack::getComponentsPatch)
            ).apply(builder, ItemStack::new)
    );
    private static final Codec<ItemStack> OPTIONAL_ITEM_STACK_CODEC = ExtraCodecs.optionalEmptyMap(ITEM_STACK_CODEC)
            .xmap(itemStack -> itemStack.orElse(ItemStack.EMPTY), itemStack -> itemStack.isEmpty() ? Optional.empty() : Optional.of(itemStack));

    private final OnContentsChangedFunction onContentsChanged;
    private final Map<Integer, Integer> slotSizeMap;
    private final Codec<NonNullList<ItemStack>> codec;
    private CanInsertFunction canInsert = null;
    private CanExtractFunction canExtract = null;
    private int maxStackSize = 64;
    private int[] outputSlots = null;

    protected CItemStacksHandler(int size, OnContentsChangedFunction onContentsChanged) {
        super(size);
        this.onContentsChanged = onContentsChanged;
        this.slotSizeMap = new HashMap<>();
        this.codec = OPTIONAL_ITEM_STACK_CODEC.listOf().xmap(this::mutableCopyOf, Function.identity());
    }

    @Override
    public int insert(int index, ItemResource resource, int amount, TransactionContext transaction) {
        return this.insert(index, resource, amount, transaction, false);
    }

    public int insert(int index, ItemResource resource, int amount, TransactionContext transaction, boolean bypass) {
        if (!bypass && this.outputSlots != null && ArrayUtils.contains(this.outputSlots, index))
            return 0;

        return super.insert(index, resource, amount, transaction);
    }

    @Override
    public int extract(int index, ItemResource resource, int amount, TransactionContext transaction) {
        return this.extract(index, resource, amount, transaction, false);
    }

    public int extract(int index, ItemResource resource, int amount, TransactionContext transaction, boolean bypass) {
        if (!bypass) {
            if (this.canExtract != null && !this.canExtract.apply(index))
                return 0;

            if (this.outputSlots != null && !ArrayUtils.contains(this.outputSlots, index))
                return 0;
        }

        return super.extract(index, resource,  amount, transaction);
    }

    @Override
    public boolean isValid(int index, ItemResource resource) {
        return this.canInsert == null || this.canInsert.apply(index, resource);
    }

    @Override
    protected void onContentsChanged(int slot, ItemStack oldStack) {
        if (this.onContentsChanged != null) {
            this.onContentsChanged.apply(slot, oldStack);
        }
    }

    @Override
    public void serialize(ValueOutput output) {
        output.store(VALUE_IO_KEY, this.codec, stacks);
    }

    @Override
    public void deserialize(ValueInput input) {
        input.read(VALUE_IO_KEY, this.codec).ifPresent(this::setStacks);
    }

    public NonNullList<ItemStack> getStacks() {
        return this.stacks;
    }

    public int getSlotLimit(int slot) {
        return this.slotSizeMap.containsKey(slot) ? this.slotSizeMap.get(slot) : this.maxStackSize;
    }

    public int getStackLimit(int index, ItemStack stack) {
        return Math.min(this.getSlotLimit(index), stack.getMaxStackSize());
    }

    public int[] getOutputSlots() {
        return this.outputSlots;
    }

    public void setDefaultSlotLimit(int size) {
        this.maxStackSize = size;
    }

    /**
     * Adds a new slot limit for the specified slot
     * <br>
     * If the size is above 64, it must be a multiple of 64
     */
    public void addSlotLimit(int slot, int size) {
        if (size > 64 && size % 64 != 0) {
            throw new IllegalArgumentException("Slot limits above 64 must be a multiple of 64");
        }

        this.slotSizeMap.put(slot, size);
    }

    public void setCanInsert(CanInsertFunction canInsert) {
        this.canInsert = canInsert;
    }

    public void setCanExtract(CanExtractFunction canExtract) {
        this.canExtract = canExtract;
    }

    public void setOutputSlots(int... slots) {
        this.outputSlots = slots;
    }

    public RecipeInventory toRecipeInventory() {
        return toRecipeInventory(0, this.stacks.size());
    }

    /**
     * Creates a RecipeInventory for use in recipe-matching with only a specific range of slots
     * <br>
     * This implementation will NOT copy the items in the inventory, so don't modify them
     *
     * @param start the first index of the recipe inputs in the inventory
     * @param size  the number of recipe input slots
     * @return a new {@link RecipeInventory}
     */
    public RecipeInventory toRecipeInventory(int start, int size) {
        return new RecipeInventory(this, start, size);
    }

    /**
     * Creates a {@link CraftingInput} of this inventory
     *
     * @param width  the width of the input
     * @param height the height of the input
     * @return the new {@link CraftingInput}
     */
    public CraftingInput toCraftingInput(int width, int height) {
        return CraftingInput.of(width, height, this.stacks);
    }

    /**
     * Creates a {@link CraftingInput} of this inventory
     *
     * @return the new {@link CraftingInput}
     */
    public CraftingInput toShapelessCraftingInput() {
        return new ShapelessCraftingInput(this.stacks);
    }

    /**
     * Creates a {@link CraftingInput} using a subset of this inventory
     *
     * @param width      the width of the input
     * @param height     the height of the input
     * @param startIndex the start index of this subset
     * @param endIndex   the end index of this subset
     * @return the new {@link CraftingInput}
     */
    public CraftingInput toCraftingInput(int width, int height, int startIndex, int endIndex) {
        return CraftingInput.of(width, height, this.stacks.subList(startIndex, endIndex));
    }

    /**
     * Creates a {@link CraftingInput} using a subset of this inventory
     *
     * @param startIndex the start index of this subset
     * @param endIndex   the end index of this subset
     * @return the new {@link CraftingInput}
     */
    public CraftingInput toShapelessCraftingInput(int startIndex, int endIndex) {
        return new ShapelessCraftingInput(this.stacks.subList(startIndex, endIndex));
    }

    // coped from StacksResourceHandler#mutableCopyOf
    private NonNullList<ItemStack> mutableCopyOf(Collection<ItemStack> list) {
        return NonNullList.of(emptyStack, list.toArray(ItemStack[]::new));
    }

    public static CItemStacksHandler create(int size) {
        return create(size, builder -> {});
    }

    public static CItemStacksHandler create(int size, Consumer<CItemStacksHandler> builder) {
        return create(size, null, builder);
    }

    public static CItemStacksHandler create(int size, OnContentsChangedFunction onContentsChanged, Consumer<CItemStacksHandler> builder) {
        var handler = new CItemStacksHandler(size, onContentsChanged);
        builder.accept(handler);
        return handler;
    }
}
