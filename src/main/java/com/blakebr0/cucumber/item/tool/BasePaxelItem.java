package com.blakebr0.cucumber.item.tool;

import com.blakebr0.cucumber.lib.ModTags;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

import java.util.Optional;
import java.util.function.Function;

public class BasePaxelItem extends DiggerItem {
    public BasePaxelItem(Tier tier, Function<Properties, Properties> properties) {
        super(4.0F, -3.2F, tier, ModTags.MINEABLE_WITH_PAXEL, properties.apply(new Properties()
                .defaultDurability((int) (tier.getUses() * 1.5))
        ));
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ToolAction action) {
        return ToolActions.DEFAULT_AXE_ACTIONS.contains(action)
                || ToolActions.DEFAULT_PICKAXE_ACTIONS.contains(action)
                || ToolActions.DEFAULT_SHOVEL_ACTIONS.contains(action);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        // attempt to handle axe useOn functionality
        var result = tryAxeUseOn(context);

        // axe functionality did not apply
        if (result == InteractionResult.PASS) {
            // attempt to handle shovel useOn functionality
            result = tryShovelUseOn(context);
        }

        return result;
    }

    private static InteractionResult tryAxeUseOn(UseOnContext context) {
        var level = context.getLevel();
        var pos = context.getClickedPos();
        var player = context.getPlayer();
        var stack = context.getItemInHand();
        var hand = context.getHand();
        var state = level.getBlockState(pos);

        var axeStripped = Optional.ofNullable(state.getToolModifiedState(context, ToolActions.AXE_STRIP, false));
        var axeScraped = Optional.ofNullable(state.getToolModifiedState(context, ToolActions.AXE_SCRAPE, false));
        var axeWaxedOff = Optional.ofNullable(state.getToolModifiedState(context, ToolActions.AXE_WAX_OFF, false));

        Optional<BlockState> modifiedState = Optional.empty();

        if (axeStripped.isPresent()) {
            level.playSound(player, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
            modifiedState = axeStripped;
        } else if (axeScraped.isPresent()) {
            level.playSound(player, pos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.levelEvent(player, 3005, pos, 0);
            modifiedState = axeScraped;
        } else if (axeWaxedOff.isPresent()) {
            level.playSound(player, pos, SoundEvents.AXE_WAX_OFF, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.levelEvent(player, 3004, pos, 0);
            modifiedState = axeWaxedOff;
        }

        if (modifiedState.isPresent()) {
            if (player instanceof ServerPlayer serverPlayer) {
                CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, pos, stack);
            }

            level.setBlock(pos, modifiedState.get(), 11);

            if (player != null) {
                stack.hurtAndBreak(1, player, (entity) -> {
                    entity.broadcastBreakEvent(hand);
                });
            }

            return InteractionResult.sidedSuccess(level.isClientSide());
        }

        return InteractionResult.PASS;
    }

    private static InteractionResult tryShovelUseOn(UseOnContext context) {
        var level = context.getLevel();
        var pos = context.getClickedPos();
        var player = context.getPlayer();
        var stack = context.getItemInHand();
        var hand = context.getHand();
        var state = level.getBlockState(pos);

        var modifiedState = state.getToolModifiedState(context, ToolActions.SHOVEL_FLATTEN, false);
        BlockState newState = null;

        if (modifiedState != null && level.isEmptyBlock(pos.above())) {
            level.playSound(player, pos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);
            newState = modifiedState;
        } else if (state.getBlock() instanceof CampfireBlock && state.getValue(CampfireBlock.LIT)) {
            if (!level.isClientSide()) {
                level.levelEvent(null, 1009, pos, 0);
            }

            CampfireBlock.dowse(player, level, pos, state);

            newState = state.setValue(CampfireBlock.LIT, Boolean.FALSE);
        }

        if (newState != null) {
            if (!level.isClientSide()) {
                level.setBlock(pos, newState, 11);

                if (player != null) {
                    stack.hurtAndBreak(1, player, (entity) -> {
                        entity.broadcastBreakEvent(hand);
                    });
                }
            }

            return InteractionResult.sidedSuccess(level.isClientSide());
        }

        return InteractionResult.PASS;
    }
}
