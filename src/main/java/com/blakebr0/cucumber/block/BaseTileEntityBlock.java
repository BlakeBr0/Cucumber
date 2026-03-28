package com.blakebr0.cucumber.block;

import net.minecraft.resources.Identifier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Function;

public abstract class BaseTileEntityBlock extends BaseBlock implements EntityBlock {
    public BaseTileEntityBlock(Identifier id, Function<Properties, Properties> properties) {
        super(id, properties);
    }

    public BaseTileEntityBlock(Identifier id, SoundType sound, float hardness, float resistance) {
        super(id, sound, hardness, resistance);
    }

    public BaseTileEntityBlock(Identifier id, SoundType sound, float hardness, float resistance, boolean tool) {
        super(id, sound, hardness, resistance, tool);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide()
                ? this.getClientTicker(level, state, type)
                : this.getServerTicker(level, state, type);
    }

    protected <T extends BlockEntity> BlockEntityTicker<T> getClientTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return null;
    }

    protected <T extends BlockEntity> BlockEntityTicker<T> getServerTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return null;
    }

    @SuppressWarnings("unchecked")
    protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTicker(BlockEntityType<A> typeA, BlockEntityType<E> typeB, BlockEntityTicker<? super E> ticker) {
        return typeA == typeB ? (BlockEntityTicker<A>) ticker : null;
    }
}
