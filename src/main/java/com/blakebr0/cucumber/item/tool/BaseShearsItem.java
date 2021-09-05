package com.blakebr0.cucumber.item.tool;

import com.blakebr0.cucumber.iface.IEnableable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.IForgeShearable;

import java.util.Random;
import java.util.function.Function;

public class BaseShearsItem extends ShearsItem {
    public BaseShearsItem(Function<Properties, Properties> properties) {
        super(properties.apply(new Properties()));
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
    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, Player player) {
        var level = player.level;
        if (level.isClientSide())
            return false;

        var state = level.getBlockState(pos);
        var block = state.getBlock();

        if (block instanceof IForgeShearable) {
            var tile = level.getBlockEntity(pos);
            var context = (new LootContext.Builder((ServerLevel) level))
                    .withRandom(level.getRandom())
                    .withParameter(LootContextParams.ORIGIN, new Vec3(pos.getX(), pos.getY(), pos.getZ()))
                    .withParameter(LootContextParams.TOOL, new ItemStack(Items.SHEARS))
                    .withOptionalParameter(LootContextParams.THIS_ENTITY, player)
                    .withOptionalParameter(LootContextParams.BLOCK_ENTITY, tile);
            var drops = state.getDrops(context);
            var rand = new Random();

            for (var drop : drops) {
                float f = 0.7F;
                double d = rand.nextFloat() * f + (1D - f) * 0.5;
                double d1 = rand.nextFloat() * f + (1D - f) * 0.5;
                double d2 = rand.nextFloat() * f + (1D - f) * 0.5;

                var item = new ItemEntity(level, pos.getX() + d, pos.getY() + d1, pos.getZ() + d2, drop);

                item.setPickUpDelay(10);

                level.addFreshEntity(item);
            }

            stack.hurtAndBreak(1, player, entity -> {
                entity.broadcastBreakEvent(player.getUsedItemHand());
            });

            player.awardStat(Stats.BLOCK_MINED.get(block), 1);

            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 11);

            return true;
        }

        return false;
    }
}
