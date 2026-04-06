package com.blakebr0.cucumber.item;

import com.blakebr0.cucumber.iface.IHoverTextProvider;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.block.Block;

import java.util.function.Consumer;
import java.util.function.Function;

public class BaseBlockItem extends BlockItem {
    public BaseBlockItem(Identifier id, Block block) {
        super(block, new Properties().setId(ResourceKey.create(Registries.ITEM, id)).useBlockDescriptionPrefix());
    }

    public BaseBlockItem(Identifier id, Block block, Function<Properties, Properties> properties) {
        super(block, properties.apply(new Properties().setId(ResourceKey.create(Registries.ITEM, id)).useBlockDescriptionPrefix()));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag flag) {
        var block = this.getBlock();
        if (block instanceof IHoverTextProvider provider) {
            provider.appendHoverText(stack, context, display, builder, flag);
        }
    }
}
