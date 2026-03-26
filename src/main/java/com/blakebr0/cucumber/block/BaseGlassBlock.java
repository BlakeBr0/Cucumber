package com.blakebr0.cucumber.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.TransparentBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Function;

public class BaseGlassBlock extends TransparentBlock {
    public BaseGlassBlock(Function<Properties, Properties> properties) {
        super(properties.apply(Properties.of())
                .noOcclusion()
                .isValidSpawn(BaseGlassBlock::never)
                .isRedstoneConductor(BaseGlassBlock::never)
                .isSuffocating(BaseGlassBlock::never)
                .isViewBlocking(BaseGlassBlock::never)
        );
    }

    public BaseGlassBlock(SoundType sound, float hardness, float resistance) {
        this(sound, hardness, resistance, false);
    }

    public BaseGlassBlock(SoundType sound, float hardness, float resistance, boolean tool) {
        var properties = Properties.of()
                .sound(sound)
                .strength(hardness, resistance)
                .noOcclusion()
                .isValidSpawn(BaseGlassBlock::never)
                .isRedstoneConductor(BaseGlassBlock::never)
                .isSuffocating(BaseGlassBlock::never)
                .isViewBlocking(BaseGlassBlock::never);

        if (tool) {
            properties.requiresCorrectToolForDrops();
        }

        super(properties);
    }

    private static boolean never(BlockState state, BlockGetter level, BlockPos pos, EntityType<?> entity) {
        return false;
    }

    private static boolean never(BlockState state, BlockGetter level, BlockPos pos) {
        return false;
    }
}
