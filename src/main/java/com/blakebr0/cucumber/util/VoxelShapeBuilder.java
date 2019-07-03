package com.blakebr0.cucumber.util;

import net.minecraft.block.Block;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;

public class VoxelShapeBuilder {
    private VoxelShape leftShape;
    private VoxelShape lastOrShape;

    public static VoxelShapeBuilder fromShapes(VoxelShape... shapes) {
        VoxelShapeBuilder builder = new VoxelShapeBuilder();
        for (VoxelShape shape : shapes) {
            builder.shape(shape);
        }

        return builder;
    }

    public VoxelShapeBuilder shape(VoxelShape shape) {
        if (this.leftShape == null) {
            this.leftShape = shape;
        } else {
            VoxelShape newShape = VoxelShapes.or(this.leftShape, shape);
            if (this.lastOrShape != null) {
                this.lastOrShape = VoxelShapes.or(this.lastOrShape, newShape);
            } else {
                this.lastOrShape = newShape;
            }

            this.leftShape = null;
        }

        return this;
    }

    public VoxelShapeBuilder cuboid(double x1, double y1, double z1, double x2, double y2, double z2) {
        VoxelShape shape = Block.makeCuboidShape(x1, y1, z1, x2, y2, z2);
        return this.shape(shape);
    }

    public VoxelShape build() {
        return this.lastOrShape;
    }
}
