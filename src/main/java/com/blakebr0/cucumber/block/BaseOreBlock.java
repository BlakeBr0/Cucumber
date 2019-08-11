package com.blakebr0.cucumber.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorldReader;

import java.util.function.Function;

public class BaseOreBlock extends BaseLayeredBlock {
    private final int minExp;
    private final int maxExp;

    public BaseOreBlock(Material material, Function<Properties, Properties> properties, int minExp, int maxExp) {
        this(material, properties, minExp, maxExp, false);
    }

    public BaseOreBlock(Material material, SoundType sound, float hardness, float resistance, int minExp, int maxExp) {
        this(material, sound, hardness, resistance, minExp, maxExp, false);
    }

    public BaseOreBlock(Material material, Function<Properties, Properties> properties, int minExp, int maxExp, boolean translucent) {
        super(material, properties, translucent);
        this.minExp = minExp;
        this.maxExp = maxExp;
    }

    public BaseOreBlock(Material material, SoundType sound, float hardness, float resistance, int minExp, int maxExp, boolean translucent) {
        super(material, sound, hardness, resistance, translucent);
        this.minExp = minExp;
        this.maxExp = maxExp;
    }

    @Override
    public int getExpDrop(BlockState state, IWorldReader world, BlockPos pos, int fortune, int silktouch) {
        return silktouch == 0 ? MathHelper.nextInt(RANDOM, this.minExp, this.maxExp) : 0;
    }
}
