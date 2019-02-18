package com.blakebr0.cucumber.event;

import net.minecraftforge.event.entity.living.LivingDropsEvent;

import java.util.HashSet;
import java.util.Set;
// TODO: Implement mob drop handler
public class MobDropHandler {
	
	private static Set<IDrop> drops = new HashSet<>();
	
	//@SubscribeEvent
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
