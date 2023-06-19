package com.blakebr0.cucumber.item.tool;

import com.blakebr0.cucumber.helper.BlockHelper;
import com.blakebr0.cucumber.lib.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Function;

public class BaseSickleItem extends DiggerItem {
    private final float attackDamage;
    private final float attackSpeed;
    private final int range;

    public BaseSickleItem(Tier tier, int range) {
        this(tier, range, p -> p);
    }

    public BaseSickleItem(Tier tier, int range, Function<Properties, Properties> properties) {
        super(4.0F, -3.0F, tier, ModTags.MINEABLE_WITH_SICKLE, properties.apply(new Properties()));
        this.attackDamage = 4.0F;
        this.attackSpeed = -3.0F;
        this.range = range;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return state.is(ModTags.MINEABLE_WITH_SICKLE) ? this.getTier().getSpeed() / 2 : super.getDestroySpeed(stack, state);
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, Player player) {
        var level = player.level();
        if (level.isClientSide())
            return false;

        this.harvestAOEBlocks(stack, level, pos, player);

        return false;
    }

    public float getAttackDamage() {
        return this.attackDamage + this.getTier().getAttackDamageBonus();
    }

    public float getAttackSpeed() {
        return this.attackSpeed;
    }

    private void harvestAOEBlocks(ItemStack stack, Level level, BlockPos pos, Player player) {
        var state = level.getBlockState(pos);

        if (this.range > 0 && this.isCorrectToolForDrops(stack, state)) {
            var hardness = state.getDestroySpeed(level, pos);

            BlockPos.betweenClosed(pos.offset(-this.range, -this.range, -this.range), pos.offset(this.range, this.range, this.range)).forEach(aoePos -> {
                if (stack.isEmpty())
                    return;

                if (aoePos != pos) {
                    var aoeState = level.getBlockState(aoePos);

                    if (!this.isCorrectToolForDrops(stack, aoeState))
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
    }
}
