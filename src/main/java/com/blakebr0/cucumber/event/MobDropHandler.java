package com.blakebr0.cucumber.event;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MobDropHandler {
	
	private static Set<IDrop> drops = new HashSet<>();
	
	@SubscribeEvent
	public void onMobDrops(LivingDropsEvent event) {
		for (IDrop drop : drops) {
			drop.handle(event);
		}
	}
	
	public static void register(IDrop drop) {
		drops.add(drop);
	}
	
	public interface IDrop {
		void handle(LivingDropsEvent event);
	}
}
