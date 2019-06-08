package com.blakebr0.cucumber.util;

import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;

public class VoxelShapeBuilder {
    private VoxelShape leftShape;
    private VoxelShape lastOrShape;

    public VoxelShapeBuilder fromShapes(VoxelShape... shapes) {
        for (VoxelShape shape : shapes) {
            this.shape(shape);
        }

        return this;
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

    public VoxelShape build() {
        return this.lastOrShape;
    }
}
