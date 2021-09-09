package com.blakebr0.cucumber.item.tool;

import com.blakebr0.cucumber.iface.IEnableable;
import com.blakebr0.cucumber.lib.ModTags;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
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
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

import java.util.Map;
import java.util.function.Function;

public class BasePaxelItem extends DiggerItem {
    private static final Map<Block, BlockState> PATH_STUFF = Maps.newHashMap(ImmutableMap.of(Blocks.GRASS_BLOCK, Blocks.DIRT_PATH.defaultBlockState()));

    public BasePaxelItem(Tier tier, Function<Properties, Properties> properties) {
        super(4.0F, -3.2F, tier, ModTags.MINEABLE_WITH_PAXEL, properties.apply(new Properties()
                .defaultDurability((int) (tier.getUses() * 1.5))
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
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        return isValidMaterial(state);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return isValidMaterial(state) ? this.speed : super.getDestroySpeed(stack, state);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ToolAction action) {
        return ToolActions.DEFAULT_AXE_ACTIONS.contains(action)
                || ToolActions.DEFAULT_PICKAXE_ACTIONS.contains(action)
                || ToolActions.DEFAULT_SHOVEL_ACTIONS.contains(action);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        var level = context.getLevel();
        var pos = context.getClickedPos();
        var player = context.getPlayer();
        var stack = context.getItemInHand();
        var state = level.getBlockState(pos);
        var modifiedState = state.getToolModifiedState(level, pos, player, stack, ToolActions.AXE_STRIP);

        if (modifiedState != null) {
            level.playSound(player, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);

            if (!level.isClientSide()) {
                level.setBlock(pos, modifiedState, 11);

                if (player != null) {
                    stack.hurtAndBreak(1, player, entity -> {
                        entity.broadcastBreakEvent(context.getHand());
                    });
                }
            }

            return InteractionResult.sidedSuccess(level.isClientSide());
        } else if (context.getClickedFace() != Direction.DOWN && level.getBlockState(pos.above()).isAir()) {
            var pathState = PATH_STUFF.get(state.getBlock());

            if (pathState != null) {
                level.playSound(player, pos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);

                if (!level.isClientSide()) {
                    level.setBlock(pos, pathState, 11);

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

    private static boolean isValidMaterial(BlockState state) {
        var material = state.getMaterial();
        return material == Material.STONE || material == Material.METAL || material == Material.HEAVY_METAL
                || material == Material.WOOD || material == Material.NETHER_WOOD || material == Material.PLANT
                || material == Material.REPLACEABLE_PLANT || material == Material.BAMBOO
                || material == Material.VEGETABLE || state.is(Blocks.SNOW) || state.is(Blocks.SNOW_BLOCK);
    }
}
