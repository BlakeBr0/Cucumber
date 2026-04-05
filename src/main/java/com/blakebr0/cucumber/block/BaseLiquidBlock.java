package com.blakebr0.cucumber.block;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.FlowingFluid;

import java.util.function.Function;
import java.util.function.Supplier;

public class BaseLiquidBlock extends LiquidBlock {
    public BaseLiquidBlock(Identifier id, FlowingFluid fluid, Supplier<Block> block) {
        super(fluid, Properties.ofFullCopy(block.get()).setId(ResourceKey.create(Registries.BLOCK, id)));
    }

    public BaseLiquidBlock(Identifier id, FlowingFluid fluid, Function<Properties, Properties> properties) {
        super(fluid, properties.apply(Properties.of()).setId(ResourceKey.create(Registries.BLOCK, id)));
    }
}
