package com.blakebr0.cucumber.item.tool;

import com.blakebr0.cucumber.helper.BlockHelper;
import com.blakebr0.cucumber.iface.IEnableable;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.core.NonNullList;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeHooks;

import java.util.HashSet;
import java.util.function.Function;

import net.minecraft.world.item.Item.Properties;

public class BaseSickleItem extends DiggerItem {
    private final float attackDamage;
    private final float attackSpeed;
    private final int range;

    public BaseSickleItem(Tier tier, float attackDamage, float attackSpeed, int range, Function<Properties, Properties> properties) {
        super(attackDamage, attackSpeed, tier, new HashSet<>(), properties.apply(new Properties()));
        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
        this.range = range;
    }

    @Override
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
        if (this instanceof IEnableable) {
            IEnableable enableable = (IEnableable) this;
            if (enableable.isEnabled())
                super.fillItemCategory(group, items);
        } else {
            super.fillItemCategory(group, items);
        }
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return isValidMaterial(state) ? this.getTier().getSpeed() / 2 : super.getDestroySpeed(stack, state);
    }

    @Override
    public boolean canHarvestBlock(ItemStack stack, BlockState state) {
        return isValidMaterial(state);
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, Player player) {
        Level world = player.level;
        return !this.harvest(stack, world, pos, player);
    }

    public float getAttackDamage() {
        return this.attackDamage + this.getTier().getAttackDamageBonus();
    }

    public float getAttackSpeed() {
        return this.attackSpeed;
    }

    private boolean harvest(ItemStack stack, Level world, BlockPos pos, Player player) {
        BlockState state = world.getBlockState(pos);
        float hardness = state.getDestroySpeed(world, pos);

        if (!this.tryHarvest(world, pos, false, stack, player) || !isValidMaterial(state))
            return false;

        int radius = this.range;
        if (radius > 0) {
            BlockPos.betweenClosed(pos.offset(-radius, -radius, -radius), pos.offset(radius, radius, radius)).forEach(aoePos -> {
                if (aoePos != pos) {
                    BlockState aoeState = world.getBlockState(aoePos);
                    float aoeHardness = aoeState.getDestroySpeed(world, aoePos);
                    if (aoeHardness <= hardness + 5.0F && isValidMaterial(aoeState)) {
                        if (this.tryHarvest(world, aoePos, true, stack, player)) {
                            if (aoeHardness <= 0.0F && Math.random() < 0.33) {
                                if (!player.abilities.instabuild) {
                                    stack.hurtAndBreak(1, player, entity -> {
                                        entity.broadcastBreakEvent(player.getUsedItemHand());
                                    });
                                }
                            }
                        }
                    }
                }
            });
        }

        return true;
    }

    private boolean tryHarvest(Level world, BlockPos pos, boolean extra, ItemStack stack, Player player) {
        BlockState state = world.getBlockState(pos);
        float hardness = state.getDestroySpeed(world, pos);
        boolean harvest = !extra || (ForgeHooks.canHarvestBlock(state, player, world, pos) || this.canHarvestBlock(stack, state));

        if (hardness >= 0.0F && harvest)
            return BlockHelper.breakBlocksAOE(stack, world, player, pos, !extra);

        return false;
    }

    private static boolean isValidMaterial(BlockState state) {
        Material material = state.getMaterial();
        return material == Material.LEAVES || material == Material.PLANT || material == Material.REPLACEABLE_PLANT;
    }
}
