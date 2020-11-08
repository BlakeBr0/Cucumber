package com.blakebr0.cucumber.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorldReader;
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
        super(material, properties.compose(p -> p.harvestLevel(harvestLevel).harvestTool(ToolType.PICKAXE).setRequiresTool()));
        this.minExp = minExp;
        this.maxExp = maxExp;
    }

    public BaseOreBlock(Material material, SoundType sound, float hardness, float resistance, int harvestLevel, int minExp, int maxExp) {
        this(material, p -> p.sound(sound).hardnessAndResistance(hardness, resistance), harvestLevel, minExp, maxExp);
    }

    @Override
    public int getExpDrop(BlockState state, IWorldReader world, BlockPos pos, int fortune, int silktouch) {
        return silktouch == 0 ? MathHelper.nextInt(RANDOM, this.minExp, this.maxExp) : 0;
    }
}
