package com.blakebr0.cucumber.guide;

import java.util.ArrayList;

import com.blakebr0.cucumber.Cucumber;
import com.blakebr0.cucumber.registry.GuideRegistry;
import com.blakebr0.cucumber.util.Utils;

import net.minecraft.creativetab.CreativeTabs;

public class Guide {

	private ArrayList<GuideEntry> entries = new ArrayList<>();
	
	private String modid;
	private String name;
	private String registryName;
	private int color;
	private CreativeTabs tab;
	
	private Guide(String modid, String name, String registryName, int color, CreativeTabs tab) {
		this.modid = modid;
		this.name = name;
		this.registryName = registryName;
		this.color = color;
		this.tab = tab;
	}
	
	public static Guide create(String modid, String name, String registryName, int color, CreativeTabs tab) {
		return new Guide(modid, name, registryName, color, tab);
	}
	
	public void register() {
		GuideRegistry.register(this);
		Cucumber.REGISTRY.register(new ItemGuide(registryName, tab, this), registryName);
	}
	
	public String getModId() {
		return this.modid;
	}
	
	public String getName() {
		return Utils.localize(name);
	}
	
	public Guide addEntry(GuideEntry entry) {
		this.entries.add(entry);
		return this;
	}
	
	public ArrayList<GuideEntry> getEntries() {
		return this.entries;
	}
}
