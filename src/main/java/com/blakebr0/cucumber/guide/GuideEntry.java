package com.blakebr0.cucumber.guide;

import java.util.ArrayList;

import com.blakebr0.cucumber.guide.components.IEntryComponent;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GuideEntry {
	
	private ArrayList<IEntryComponent> components = new ArrayList<>();

	private int id;
	private String title;
	
	public GuideEntry(int id, String title) {
		this.id = id;
		this.title = title;
	}
	
	@SideOnly(Side.CLIENT)
	public void open(GuiGuide parent) {
		Minecraft.getMinecraft().displayGuiScreen(new GuiEntry(parent, parent.guide.getEntries().get(id)));
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public GuideEntry addComponent(IEntryComponent component) {
		this.components.add(component);
		return this;
	}
	
	public ArrayList<IEntryComponent> getComponents() {
		return this.components;
	}
}
