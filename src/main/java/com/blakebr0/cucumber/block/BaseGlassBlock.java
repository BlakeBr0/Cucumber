package com.blakebr0.cucumber.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.GlassBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

import java.util.function.Function;

public class BaseGlassBlock extends GlassBlock {
    public BaseGlassBlock(Material material, Function<Properties, Properties> properties) {
        super(properties.apply(Properties.of(material))
                .noOcclusion()
                .isValidSpawn(BaseGlassBlock::never)
                .isRedstoneConductor(BaseGlassBlock::never)
                .isSuffocating(BaseGlassBlock::never)
                .isViewBlocking(BaseGlassBlock::never)
        );
    }

    public BaseGlassBlock(Material material, SoundType sound, float hardness, float resistance) {
        super(Properties.of(material)
                .sound(sound)
                .strength(hardness, resistance)
                .noOcclusion()
                .isValidSpawn(BaseGlassBlock::never)
                .isRedstoneConductor(BaseGlassBlock::never)
                .isSuffocating(BaseGlassBlock::never)
                .isViewBlocking(BaseGlassBlock::never)
        );
    }

    public BaseGlassBlock(Material material, SoundType sound, float hardness, float resistance, boolean tool) {
        super(Properties.of(material)
                .sound(sound)
                .strength(hardness, resistance)
                .requiresCorrectToolForDrops()
                .noOcclusion()
                .isValidSpawn(BaseGlassBlock::never)
                .isRedstoneConductor(BaseGlassBlock::never)
                .isSuffocating(BaseGlassBlock::never)
                .isViewBlocking(BaseGlassBlock::never)
        );
    }

    private static boolean never(BlockState state, BlockGetter level, BlockPos pos, EntityType<?> entity) {
        return false;
    }

    private static boolean never(BlockState state, BlockGetter world, BlockPos pos) {
        return false;
    }
}
