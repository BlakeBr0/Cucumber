package com.blakebr0.cucumber.guide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.blakebr0.cucumber.guide.components.IEntryComponent;
import com.google.common.collect.Maps;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GuideEntry {
	
	private ArrayList<IEntryComponent> components = new ArrayList<>();

	private int id;
	private String title;
	private ItemStack icon = ItemStack.EMPTY;
	
	public GuideEntry(int id, String title) {
		this.id = id;
		this.title = title;
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
	
	public GuideEntry setIconStack(ItemStack stack) {
		this.icon = stack;
		return this;
	}
	
	public ItemStack getIconStack() {
		return this.icon;
	}
}
