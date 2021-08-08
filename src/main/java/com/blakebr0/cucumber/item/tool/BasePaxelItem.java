package com.blakebr0.cucumber.item.tool;

import com.blakebr0.cucumber.iface.IEnableable;
import com.blakebr0.cucumber.lib.ModTags;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.ToolType;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class BasePaxelItem extends DiggerItem {
    private static final Set<Material> EFFECTIVE_ON_MATERIALS = Sets.newHashSet(Material.WOOD, Material.NETHER_WOOD, Material.PLANT, Material.REPLACEABLE_PLANT, Material.BAMBOO, Material.VEGETABLE);
    private static final Map<Block, BlockState> PATH_STUFF = Maps.newHashMap(ImmutableMap.of(Blocks.GRASS_BLOCK, Blocks.DIRT_PATH.defaultBlockState()));

    public BasePaxelItem(Tier tier, Function<Properties, Properties> properties) {
        super(4.0F, -3.2F, tier, ModTags.MINEABLE_WITH_PAXEL, properties.apply(new Properties()
                .defaultDurability((int) (tier.getUses() * 1.5))
                .addToolType(ToolType.PICKAXE, tier.getLevel())
                .addToolType(ToolType.SHOVEL, tier.getLevel())
                .addToolType(ToolType.AXE, tier.getLevel())
        ));
    }

    @Override
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
        if (this instanceof IEnableable enableable) {
            if (enableable.isEnabled())
                super.fillItemCategory(group, items);
        } else {
            super.fillItemCategory(group, items);
        }
    }

    @Override
    public boolean isCorrectToolForDrops(BlockState state) {
        int i = this.getTier().getLevel();
        if (state.getHarvestTool() == ToolType.PICKAXE)
            return i >= state.getHarvestLevel();

        Material material = state.getMaterial();
        return material == Material.STONE || material == Material.METAL || material == Material.HEAVY_METAL
                || state.is(Blocks.SNOW) || state.is(Blocks.SNOW_BLOCK);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        Material material = state.getMaterial();
        return material != Material.METAL && material != Material.HEAVY_METAL && material != Material.STONE
                && !EFFECTIVE_ON_MATERIALS.contains(material)
                ? super.getDestroySpeed(stack, state)
                : this.speed;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        var world = context.getLevel();
        var pos = context.getClickedPos();
        var player = context.getPlayer();
        var stack = context.getItemInHand();
        var state = world.getBlockState(pos);
        var modifiedState = state.getToolModifiedState(world, pos, player, stack, ToolType.AXE);

        if (modifiedState != null) {
            world.playSound(player, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);

            if (!world.isClientSide()) {
                world.setBlock(pos, modifiedState, 11);

                if (player != null) {
                    stack.hurtAndBreak(1, player, entity -> {
                        entity.broadcastBreakEvent(context.getHand());
                    });
                }
            }

            return InteractionResult.sidedSuccess(world.isClientSide());
        } else if (context.getClickedFace() != Direction.DOWN && world.getBlockState(pos.above()).isAir()) {
            var pathState = PATH_STUFF.get(state.getBlock());

            if (pathState != null) {
                world.playSound(player, pos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);

                if (!world.isClientSide()) {
                    world.setBlock(pos, pathState, 11);

                    if (player != null) {
                        stack.hurtAndBreak(1, player, entity -> {
                            entity.broadcastBreakEvent(context.getHand());
                        });
                    }
                }

                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }
}
