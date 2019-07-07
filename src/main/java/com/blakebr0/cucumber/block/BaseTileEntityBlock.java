package com.blakebr0.cucumber.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import java.util.function.Function;

public class BaseTileEntityBlock extends BaseBlock implements ITileEntityProvider {
    public BaseTileEntityBlock(Material material, Function<Properties, Properties> properties) {
        super(material, properties);
    }

    public BaseTileEntityBlock(Material material, SoundType sound, float hardness, float resistance) {
        super(material, sound, hardness, resistance);
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return null;
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader world) {
        return this.createTileEntity(null, world);
    }
}
