package com.blakebr0.cucumber.util;

import net.minecraft.core.BlockPos;

import java.util.ArrayList;
import java.util.List;

public record MultiblockPositions(List<BlockPos> positions) {
    public List<BlockPos> get(BlockPos pos) {
        return this.positions.stream().map(pos::offset).toList();
    }

    public static class Builder {
        private final List<BlockPos> positions = new ArrayList<>();

        public Builder pos(int x, int y, int z) {
            this.positions.add(new BlockPos(x, y, z));
            return this;
        }

        public MultiblockPositions build() {
            return new MultiblockPositions(this.positions);
        }
    }
}
