package com.blakebr0.cucumber.item.tool;

import com.blakebr0.cucumber.iface.IEnableable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShearsItem;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IForgeShearable;

import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class BaseShearsItem extends ShearsItem {
    public BaseShearsItem(Function<Properties, Properties> properties) {
        super(properties.apply(new Properties()));
    }

    @Override
    public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {
        if (this instanceof IEnableable) {
            IEnableable enableable = (IEnableable) this;
            if (enableable.isEnabled())
                super.fillItemCategory(group, items);
        } else {
            super.fillItemCategory(group, items);
        }
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, PlayerEntity player) {
        World world = player.level;
        if (world.isClientSide())
            return false;

        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        if (block instanceof IForgeShearable) {
            TileEntity tile = world.getBlockEntity(pos);
            LootContext.Builder context = (new LootContext.Builder((ServerWorld) world))
                    .withRandom(world.getRandom())
                    .withParameter(LootParameters.ORIGIN, new Vector3d(pos.getX(), pos.getY(), pos.getZ()))
                    .withParameter(LootParameters.TOOL, new ItemStack(Items.SHEARS))
                    .withOptionalParameter(LootParameters.THIS_ENTITY, player)
                    .withOptionalParameter(LootParameters.BLOCK_ENTITY, tile);
            List<ItemStack> drops = state.getDrops(context);
            Random rand = new Random();

            for (ItemStack drop : drops) {
                float f = 0.7F;
                double d = rand.nextFloat() * f + (1D - f) * 0.5;
                double d1 = rand.nextFloat() * f + (1D - f) * 0.5;
                double d2 = rand.nextFloat() * f + (1D - f) * 0.5;

                ItemEntity item = new ItemEntity(world, pos.getX() + d, pos.getY() + d1, pos.getZ() + d2, drop);
                item.setPickUpDelay(10);

                world.addFreshEntity(item);
            }

            stack.hurtAndBreak(1, player, entity -> {
                entity.broadcastBreakEvent(player.getUsedItemHand());
            });

            player.awardStat(Stats.BLOCK_MINED.get(block), 1);

            world.setBlock(pos, Blocks.AIR.defaultBlockState(), 11);

            return true;
        }

        return false;
    }
}
