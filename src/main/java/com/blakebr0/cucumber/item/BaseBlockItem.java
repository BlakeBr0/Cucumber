package com.blakebr0.cucumber.item;

import com.blakebr0.cucumber.iface.IEnableable;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.function.Function;

public class BaseBlockItem extends BlockItem implements IEnableable {
    public BaseBlockItem(Block block, Function<Properties, Properties> properties) {
        super(block, properties.apply(new Properties()));
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (this.getBlock() instanceof IEnableable) {
            IEnableable enableable = (IEnableable) this.getBlock();
            if (enableable.isEnabled())
                super.fillItemGroup(group, items);
        } else {
            super.fillItemGroup(group, items);
        }
    }

    @Override
    public boolean isEnabled() {
        if (this.getBlock() instanceof IEnableable)
            return ((IEnableable) this.getBlock()).isEnabled();

        return true;
    }
}
