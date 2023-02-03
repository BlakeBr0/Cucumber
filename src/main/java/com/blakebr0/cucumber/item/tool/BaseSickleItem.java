package com.blakebr0.cucumber.item.tool;

import com.blakebr0.cucumber.helper.BlockHelper;
import com.blakebr0.cucumber.iface.IEnableable;
import com.blakebr0.cucumber.lib.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

import java.util.function.Function;

public class BaseSickleItem extends DiggerItem {
    private final float attackDamage;
    private final float attackSpeed;
    private final int range;

    public BaseSickleItem(Tier tier, float attackDamage, float attackSpeed, int range, Function<Properties, Properties> properties) {
        super(attackDamage, attackSpeed, tier, ModTags.MINEABLE_WITH_SICKLE, properties.apply(new Properties()));
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
        return this.harvest(stack, player.level, pos, player);
    }

    public float getAttackDamage() {
        return this.attackDamage + this.getTier().getAttackDamageBonus();
    }

    public float getAttackSpeed() {
        return this.attackSpeed;
    }

    private boolean harvest(ItemStack stack, Level level, BlockPos pos, Player player) {
        if (level.isClientSide())
            return true;

        var state = level.getBlockState(pos);
        var hardness = state.getDestroySpeed(level, pos);

        BlockHelper.harvestBlock(stack, level, (ServerPlayer) player, pos);

        if (this.range > 0 && isValidMaterial(state)) {
            BlockPos.betweenClosed(pos.offset(-this.range, -this.range, -this.range), pos.offset(this.range, this.range, this.range)).forEach(aoePos -> {
                if (stack.isEmpty())
                    return;

                if (aoePos != pos) {
                    var aoeState = level.getBlockState(aoePos);

                    if (!isValidMaterial(aoeState))
                        return;

                    var aoeHardness = aoeState.getDestroySpeed(level, aoePos);

                    if (aoeHardness > hardness + 5.0F)
                        return;

                    var harvested = BlockHelper.harvestAOEBlock(stack, level, (ServerPlayer) player, aoePos.immutable());

                    if (harvested && !player.getAbilities().instabuild && aoeHardness <= 0.0F && Math.random() < 0.33) {
                        stack.hurtAndBreak(1, player, entity -> {
                            entity.broadcastBreakEvent(player.getUsedItemHand());
                        });
                    }
                }
            });
        }

        return true;
    }

    private static boolean isValidMaterial(BlockState state) {
        var material = state.getMaterial();
        return state.is(ModTags.MINEABLE_WITH_SICKLE) || material == Material.LEAVES || material == Material.PLANT || material == Material.REPLACEABLE_PLANT;
    }
}
