package com.blakebr0.cucumber.guide;

import com.blakebr0.cucumber.iface.IModelHelper;
import com.blakebr0.cucumber.item.ItemBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class ItemGuide extends ItemBase implements IModelHelper {

	private Guide guide;
	private String author;
	
	public ItemGuide(String name, ItemGroup group, Guide guide) {
		super("guide", new Properties().maxStackSize(1).group(group));
		this.guide = guide;
		this.author = guide.getAuthor();
		
		Guide.addGuideItem(this);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		if (player != null && world.isRemote) {
			openGuide(player.getHeldItem(hand));
		}

		return super.onItemRightClick(world, player, hand);
	}
	
	@Override // TODO: Tooltip fixerino
	public void addInformation(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag advanced) {
//		tooltip.add(Utils.localize("tooltip.cu.author", this.author));
	}
	
	@Override // TODO: Figure out how to register models
	public void initModels() {
//		ModelLoader.setCustomModelResourceLocation(this, 0, ResourceHelper.getModelResource(Cucumber.MOD_ID, "guide", "inventory"));
	}
	
	public Guide getGuide() {
		return this.guide;
	}

	@OnlyIn(Dist.CLIENT)
	private void openGuide(ItemStack book) {
		Minecraft.getInstance().displayGuiScreen(new GuiGuide(book, this.guide));
	}
}
