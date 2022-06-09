package com.blakebr0.cucumber.handler;

import com.blakebr0.cucumber.iface.ICustomBow;
import net.minecraft.util.Mth;
import net.minecraftforge.client.event.FOVModifierEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public final class BowFOVHandler {
	@SubscribeEvent
	public void onFOVUpdate(FOVModifierEvent event) {
		var entity = event.getPlayer();
		var stack = entity.getUseItem();

		if (!stack.isEmpty()) {
			var item = stack.getItem();
			if (item instanceof ICustomBow bow && bow.hasFOVChange()) {
				float f = Mth.clamp((stack.getUseDuration() - entity.getUseItemRemainingTicks()) * bow.getDrawSpeedMulti(stack) / 20.0F, 0, 1.0F);

				event.setNewFov(event.getNewFov() - (f * f * 0.15F));
			}
		}
	}
}
