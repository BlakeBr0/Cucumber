package com.blakebr0.cucumber.item.tool;

import com.blakebr0.cucumber.helper.BlockHelper;
import com.blakebr0.cucumber.iface.IEnableable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.tags.Tag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.ForgeHooks;

import java.util.Set;
import java.util.function.Function;

public class BaseSickleItem extends DiggerItem {
    private final float attackDamage;
    private final float attackSpeed;
    private final int range;

    public BaseSickleItem(Tier tier, float attackDamage, float attackSpeed, int range, Function<Properties, Properties> properties) {
        super(attackDamage, attackSpeed, tier, Tag.fromSet(Set.of()), properties.apply(new Properties()));
        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
        this.range = range;
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
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return isValidMaterial(state) ? this.getTier().getSpeed() / 2 : super.getDestroySpeed(stack, state);
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        return isValidMaterial(state);
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, Player player) {
        return !this.harvest(stack, player.level, pos, player);
    }

    public float getAttackDamage() {
        return this.attackDamage + this.getTier().getAttackDamageBonus();
    }

    public float getAttackSpeed() {
        return this.attackSpeed;
    }

    private boolean harvest(ItemStack stack, Level world, BlockPos pos, Player player) {
        var state = world.getBlockState(pos);
        var hardness = state.getDestroySpeed(world, pos);

        if (!this.tryHarvest(world, pos, false, stack, player) || !isValidMaterial(state))
            return false;

        if (this.range > 0) {
            BlockPos.betweenClosed(pos.offset(-this.range, -this.range, -this.range), pos.offset(this.range, this.range, this.range)).forEach(aoePos -> {
                if (aoePos != pos) {
                    var aoeState = world.getBlockState(aoePos);
                    var aoeHardness = aoeState.getDestroySpeed(world, aoePos);

                    if (aoeHardness <= hardness + 5.0F && isValidMaterial(aoeState)) {
                        if (this.tryHarvest(world, aoePos, true, stack, player)) {
                            if (aoeHardness <= 0.0F && Math.random() < 0.33) {
                                if (!player.getAbilities().instabuild) {
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
        var state = world.getBlockState(pos);
        var hardness = state.getDestroySpeed(world, pos);
        var harvest = !extra || (ForgeHooks.isCorrectToolForDrops(state, player) || this.isCorrectToolForDrops(stack, state));

        if (hardness >= 0.0F && harvest)
            return BlockHelper.breakBlocksAOE(stack, world, player, pos, !extra);

        return false;
    }

    private static boolean isValidMaterial(BlockState state) {
        var material = state.getMaterial();
        return material == Material.LEAVES || material == Material.PLANT || material == Material.REPLACEABLE_PLANT;
    }
}
