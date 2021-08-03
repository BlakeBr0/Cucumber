package com.blakebr0.cucumber.block;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.ToolType;

import java.util.function.Function;

public class BaseOreBlock extends BaseBlock {
    private final int minExp;
    private final int maxExp;

    @Deprecated // TODO: 1.17: remove
    public BaseOreBlock(Material material, Function<Properties, Properties> properties, int minExp, int maxExp) {
        this(material, properties, 0, minExp, maxExp);
    }

    @Deprecated // TODO: 1.17: remove
    public BaseOreBlock(Material material, SoundType sound, float hardness, float resistance, int minExp, int maxExp) {
        this(material, sound, hardness, resistance, 0, minExp, maxExp);
    }

    public BaseOreBlock(Material material, Function<Properties, Properties> properties, int harvestLevel, int minExp, int maxExp) {
        super(material, properties.compose(p -> p.harvestLevel(harvestLevel).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()));
        this.minExp = minExp;
        this.maxExp = maxExp;
    }

    public BaseOreBlock(Material material, SoundType sound, float hardness, float resistance, int harvestLevel, int minExp, int maxExp) {
        this(material, p -> p.sound(sound).strength(hardness, resistance), harvestLevel, minExp, maxExp);
    }

    @Override
    public int getExpDrop(BlockState state, LevelReader world, BlockPos pos, int fortune, int silktouch) {
        return silktouch == 0 ? Mth.nextInt(RANDOM, this.minExp, this.maxExp) : 0;
    }
}
