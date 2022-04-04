package com.blakebr0.cucumber.lib;

import com.blakebr0.cucumber.util.Tooltip;
import net.minecraft.ChatFormatting;

public final class Tooltips {
	public static final Tooltip USES_LEFT = new Tooltip("tooltip.cucumber.uses_left");
	public static final Tooltip ONE_USE_LEFT = new Tooltip("tooltip.cucumber.one_use_left");
	public static final Tooltip UNLIMITED_USES = new Tooltip("tooltip.cucumber.unlimited_uses");
	public static final Tooltip HOLD_SHIFT_FOR_INFO = new Tooltip("tooltip.cucumber.hold_shift_for_info");
	public static final Tooltip BLOCK_TAGS = new Tooltip("tooltip.cucumber.block_tags", ChatFormatting.DARK_GRAY);
	public static final Tooltip ITEM_TAGS = new Tooltip("tooltip.cucumber.item_tags", ChatFormatting.DARK_GRAY);
	public static final Tooltip HOLD_CTRL_FOR_TAGS = new Tooltip("tooltip.cucumber.hold_ctrl_for_tags", ChatFormatting.DARK_GRAY);
	public static final Tooltip HOLD_ALT_FOR_NBT = new Tooltip("tooltip.cucumber.hold_alt_for_nbt", ChatFormatting.DARK_GRAY);
	public static final Tooltip FAILED_TO_LOAD_NBT = new Tooltip("tooltip.cucumber.failed_to_load_nbt", ChatFormatting.DARK_GRAY);
	public static final Tooltip NOT_YET_IMPLEMENTED = new Tooltip("tooltip.cucumber.not_yet_implemented");
}
