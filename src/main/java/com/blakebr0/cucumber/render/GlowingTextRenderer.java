package com.blakebr0.cucumber.render;

import com.blakebr0.cucumber.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GlowingTextRenderer {
	public static final ColorInfo ORANGE = new ColorInfo(255, 64, 16, 0, 64, 0);
	public static final ColorInfo EPIC = new ColorInfo(255, 85, 255, -64, 0, 0);
	
	private static int ticks = 0;
	private static Map<ItemStack, ColorInfo> stacks = new HashMap<>();

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onTick(TickEvent.ClientTickEvent event) {
		if (event.side == LogicalSide.CLIENT && event.phase == Phase.START) {
			ticks++;
		}
	}

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public void onTooltipRender(RenderTooltipEvent.PostText event) {
		if (event.getStack().isEmpty()) { return; }
		List<ItemStack> listOfStacks = stacks.keySet().stream().filter(s -> s.isItemEqual(event.getStack())).collect(Collectors.toList());
		if (!listOfStacks.isEmpty()) {
			String s = event.getLines().get(0);
			drawGlowingText(event.getFontRenderer(), event.getStack().getDisplayName().getFormattedText(), event.getX(), event.getY() - 1 + (event.getFontRenderer().FONT_HEIGHT) * (event.getLines().indexOf(s)) - 1 + 2, stacks.get(listOfStacks.get(0)));
		}
	}

	public static void registerStack(ItemStack stack, int r, int g, int b, int rl, int gl, int bl) {
		stacks.put(stack, new ColorInfo(r, g, b, rl, gl, bl));
	}

	public static void registerStack(ItemStack stack, ColorInfo info) {
		stacks.put(stack, info);
	}

	public static int drawGlowingText(FontRenderer font, String s, int x, int y, ColorInfo info) {
		float sine = 0.5F * ((float) Math.sin(Math.toRadians(4.0F * ((float) GlowingTextRenderer.getTicks() + Minecraft.getInstance().getRenderPartialTicks()))) + 1.0F);
		return font.drawStringWithShadow(s, x, y, Utils.intColor(info.r + (int) (info.rl * sine), info.g + (int) (info.gl * sine), info.b + (int) (info.bl * sine)));
	}

	public static int getTicks() {
		return ticks;
	}
	
	public static class ColorInfo {
		
		public int r, g, b;
		public int rl, gl, bl;
		
		public ColorInfo(int r, int g, int b, int rl, int gl, int bl) {
			this.r = r;
			this.g = g;
			this.b = b;
			this.rl = rl;
			this.gl = gl;
			this.bl = bl;
		}
	}
}
