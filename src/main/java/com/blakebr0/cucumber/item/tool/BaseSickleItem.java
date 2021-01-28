package com.blakebr0.cucumber.item.tool;

import com.blakebr0.cucumber.helper.BlockHelper;
import com.blakebr0.cucumber.iface.IEnableable;
import com.blakebr0.cucumber.util.Utils;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

import java.util.HashSet;
import java.util.Iterator;
import java.util.function.Function;

public class BaseSickleItem extends ToolItem {
    private final float attackDamage;
    private final float attackSpeed;
    private final int range;

    public BaseSickleItem(IItemTier tier, float attackDamage, float attackSpeed, int range, Function<Properties, Properties> properties) {
        super(attackDamage, attackSpeed, tier, new HashSet<>(), properties.apply(new Properties()));
        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
        this.range = range;
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (this instanceof IEnableable) {
            IEnableable enableable = (IEnableable) this;
            if (enableable.isEnabled())
                super.fillItemGroup(group, items);
        } else {
            super.fillItemGroup(group, items);
        }
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return isValidMaterial(state) ? this.getTier().getEfficiency() / 2 : super.getDestroySpeed(stack, state);
    }

    @Override
    public boolean canHarvestBlock(ItemStack stack, BlockState state) {
        return isValidMaterial(state);
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, PlayerEntity player) {
        World world = player.getEntityWorld();
        return !this.harvest(stack, world, pos, player);
    }

    public float getAttackDamage() {
        return this.attackDamage + this.getTier().getAttackDamage();
    }

    public float getAttackSpeed() {
        return this.attackSpeed;
    }

    private boolean harvest(ItemStack stack, World world, BlockPos pos, PlayerEntity player) {
        BlockState state = world.getBlockState(pos);
        float hardness = state.getBlockHardness(world, pos);

        if (!this.tryHarvest(world, pos, false, stack, player) || !isValidMaterial(state)) {
            stack.damageItem(1, player, entity -> {
                entity.sendBreakAnimation(player.getActiveHand());
            });

            return false;
        }

        int radius = this.range;
        if (radius > 0) {
            int used = 0;
            Iterator<BlockPos> blocks = BlockPos.getAllInBox(pos.add(-radius, -radius, -radius), pos.add(radius, radius, radius)).iterator();

            while (blocks.hasNext()) {
                BlockPos aoePos = blocks.next();
                if (aoePos != pos) {
                    BlockState aoeState = world.getBlockState(aoePos);
                    if (aoeState.getBlockHardness(world, aoePos) <= hardness + 5.0F) {
                        if (isValidMaterial(aoeState)) {
                            int remaining = stack.getMaxDamage() - stack.getDamage() + 1;
                            if (used < remaining || stack.getMaxDamage() == -1) {
                                if (this.tryHarvest(world, aoePos, true, stack, player)) {
                                    if (aoeState.getBlockHardness(world, aoePos) <= 0.0F) {
                                        if (Utils.randInt(1, 3) == 1) {
                                            used++;
                                        }
                                    } else {
                                        used++;
                                    }
                                }
                            }
                        }
                    } else {
                        return false;
                    }
                }
            }

            if (used > 0 && !player.abilities.isCreativeMode) {
                stack.damageItem(used, player, entity -> {
                    entity.sendBreakAnimation(player.getActiveHand());
                });
            }
        }

        return true;
    }

    private boolean tryHarvest(World world, BlockPos pos, boolean extra, ItemStack stack, PlayerEntity player) {
        BlockState state = world.getBlockState(pos);
        float hardness = state.getBlockHardness(world, pos);
        boolean harvest = !extra || (ForgeHooks.canHarvestBlock(state, player, world, pos) || this.canHarvestBlock(stack, state));

        if (hardness >= 0.0F && harvest)
            return BlockHelper.breakBlocksAOE(stack, world, player, pos, !extra);

        return false;
    }

    private static boolean isValidMaterial(BlockState state) {
        Material material = state.getMaterial();
        return material == Material.LEAVES || material == Material.PLANTS || material == Material.TALL_PLANTS;
    }
}
