package com.blakebr0.cucumber.handler;

import com.blakebr0.cucumber.iface.ICustomBow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.Mth;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public final class BowFOVHandler {
	@SubscribeEvent
	public void onFOVUpdate(FOVUpdateEvent event) {
		Player entity = event.getEntity();
		if (entity == null)
			return;
		
		ItemStack stack = entity.getUseItem();
		if (!stack.isEmpty()) {
			Item item = stack.getItem();
			if (item instanceof ICustomBow bow) {
				float f = Mth.clamp((stack.getUseDuration() - entity.getUseItemRemainingTicks()) * bow.getDrawSpeedMulti(stack) / 20.0F, 0, 1.0F);

				event.setNewfov(event.getNewfov() - (f * f * 0.15F));
			}
		}
	}
}
