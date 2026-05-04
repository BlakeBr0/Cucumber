package com.blakebr0.cucumber.tileentity;

import com.blakebr0.cucumber.inventory.CItemStacksHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.Clearable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public abstract class BaseInventoryTileEntity extends BaseTileEntity implements Clearable {
    public BaseInventoryTileEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public abstract CItemStacksHandler getInventory();

    @Override
    public void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        this.getInventory().deserialize(input.childOrEmpty("inventory"));
    }

    @Override
    public void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.putChild("inventory", this.getInventory());
    }

    @Override
    public void preRemoveSideEffects(BlockPos pos, BlockState state) {
        if (this.level != null) {
            Containers.dropContents(this.level, pos, this.getInventory().getStacks());
        }
    }

    public boolean isUsableByPlayer(Player player) {
        var pos = this.getBlockPos();
        return player.distanceToSqr(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) <= 64;
    }

    public RecipeManager getRecipeManager() {
        if (this.level == null || this.level.isClientSide())
            return null;

        return ((ServerLevel) this.level).recipeAccess();
    }

    @Override
    public void clearContent() {
        this.getInventory().getStacks().clear();
    }
}
