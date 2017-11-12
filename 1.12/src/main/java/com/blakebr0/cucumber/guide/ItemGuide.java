package com.blakebr0.cucumber.guide;

import java.util.List;

import com.blakebr0.cucumber.item.ItemBase;
import com.blakebr0.cucumber.lib.Colors;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class ItemGuide extends ItemBase {

	private Guide guide;
	
	public ItemGuide(String name, CreativeTabs tab, Guide guide) {
		super("guide." + name);
		this.setCreativeTab(tab);
		this.guide = guide;
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (this.isInCreativeTab(tab)) {
			ItemStack stack = new ItemStack(this, 1, 0);
			stack.setTagCompound(new NBTTagCompound());
			stack.getTagCompound().setString("Name", guide.getName());
			stack.getTagCompound().setString("ModId", guide.getModId());
			items.add(stack);
		}
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		if (player != null && world.isRemote) {
			Minecraft.getMinecraft().displayGuiScreen(new GuiGuide(player.getHeldItem(hand), guide));
		}
		return super.onItemRightClick(world, player, hand);
	}
	
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
		if (stack.hasTagCompound()) {
			if (stack.getTagCompound().hasKey("ModId")) {
				tooltip.add(Colors.ITALICS + "Added by: " + stack.getTagCompound().getString("ModId"));
			}
		}
	}
}
