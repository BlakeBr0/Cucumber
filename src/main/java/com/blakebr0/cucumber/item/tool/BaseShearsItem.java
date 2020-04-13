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
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootParameters;
import net.minecraftforge.common.IShearable;

import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class BaseShearsItem extends ShearsItem {
    public BaseShearsItem(Function<Properties, Properties> properties) {
        super(properties.apply(new Properties()));
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
    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, PlayerEntity player) {
        World world = player.world;
        if (world.isRemote())
            return false;

        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        if (block instanceof IShearable) {
            TileEntity tile = world.getTileEntity(pos);
            LootContext.Builder context = (new LootContext.Builder((ServerWorld) world))
                    .withRandom(world.getRandom())
                    .withParameter(LootParameters.POSITION, pos)
                    .withParameter(LootParameters.TOOL, new ItemStack(Items.SHEARS))
                    .withNullableParameter(LootParameters.THIS_ENTITY, player)
                    .withNullableParameter(LootParameters.BLOCK_ENTITY, tile);
            List<ItemStack> drops = state.getDrops(context);
            Random rand = new Random();

            for (ItemStack drop : drops) {
                float f = 0.7F;
                double d = rand.nextFloat() * f + (1D - f) * 0.5;
                double d1 = rand.nextFloat() * f + (1D - f) * 0.5;
                double d2 = rand.nextFloat() * f + (1D - f) * 0.5;

                ItemEntity item = new ItemEntity(world, pos.getX() + d, pos.getY() + d1, pos.getZ() + d2, drop);
                item.setPickupDelay(10);
                world.addEntity(item);
            }

            stack.damageItem(1, player, entity -> {
                entity.sendBreakAnimation(player.getActiveHand());
            });

            player.addStat(Stats.BLOCK_MINED.get(block), 1);
            world.setBlockState(pos, Blocks.AIR.getDefaultState(), 11);

            return true;
        }

        return false;
    }
}
