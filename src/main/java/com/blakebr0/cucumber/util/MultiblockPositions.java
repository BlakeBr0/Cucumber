package com.blakebr0.cucumber.util;

import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MultiblockPositions {
    private final List<BlockPos> positions;

    public MultiblockPositions(List<BlockPos> positions) {
        this.positions = positions;
    }

    public List<BlockPos> get(BlockPos pos) {
        return this.positions.stream().map(pos::add).collect(Collectors.toList());
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
