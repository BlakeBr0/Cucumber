package com.blakebr0.cucumber.render;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.blakebr0.cucumber.util.RenderUtils;

import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GlowingTextRenderer {
	
	public static final ColorInfo ORANGE = ColorInfo.create(255, 64, 16, 0, 64, 0);
	public static final ColorInfo EPIC = ColorInfo.create(255, 85, 255, -64, 0, 0);
	
	private static int ticks = 0;
	private static Map<ItemStack, ColorInfo> stacks = new HashMap<>();

	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onTick(TickEvent.ClientTickEvent event) {
		if (event.side == Side.CLIENT && event.phase == Phase.START) {
			ticks++;
		}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onTooltipRender(RenderTooltipEvent.PostText event) {
		if (event.getStack().isEmpty()) { return; }
		List<ItemStack> listOfStacks = stacks.keySet().stream().filter(s -> s.isItemEqual(event.getStack())).collect(Collectors.toList());
		if (!listOfStacks.isEmpty()) {
			String s = event.getLines().get(0);
			RenderUtils.drawGlowingText(event.getFontRenderer(), event.getStack().getDisplayName(), event.getX(), event.getY() - 1 + (event.getFontRenderer().FONT_HEIGHT) * (event.getLines().indexOf(s)) - 1 + 2, stacks.get(listOfStacks.get(0)));
		}
	}
	
	public static void addStack(ItemStack stack, int r, int g, int b, int rl, int gl, int bl) {
		stacks.put(stack, ColorInfo.create(r, g, b, rl, gl, bl));
	}
	
	public static void addStack(ItemStack stack, ColorInfo info) {
		stacks.put(stack, info);
	}
	
	public static int getTicks() {
		return ticks;
	}
	
	public static class ColorInfo {
		
		public int r, g, b;
		public int rl, gl, bl;
		
		private ColorInfo(int r, int g, int b, int rl, int gl, int bl) {
			this.r = r;
			this.g = g;
			this.b = b;
			this.rl = rl;
			this.gl = gl;
			this.bl = bl;
		}
		
		public static ColorInfo create(int r, int g, int b, int rl, int gl, int bl) {
			return new ColorInfo(r, g, b, rl, gl, bl);
		}
	}
}
