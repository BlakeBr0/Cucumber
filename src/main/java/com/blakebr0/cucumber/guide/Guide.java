package com.blakebr0.cucumber.guide;

import java.util.ArrayList;

import com.blakebr0.cucumber.Cucumber;
import com.blakebr0.cucumber.registry.GuideRegistry;
import com.blakebr0.cucumber.util.Utils;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class Guide {

	private static final ArrayList<ItemGuide> ALL_GUIDES = new ArrayList<>();
	private ArrayList<GuideEntry> entries = new ArrayList<>();
	
	private String name;
	private String author;
	private int color;
	
	private Guide(String name, String author, int color) {
		this.name = Utils.localize(name);
		this.author = author;
		this.color = color;
	}
	
	public static Guide create(String name, String author, int color) {
		Guide guide = new Guide(name, author, color);
		GuideRegistry.register(guide);
		return guide;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getAuthor() {
		return this.author;
	}
	
	public int getColor() {
		return this.color;
	}
	
	public static void addGuideItem(ItemGuide guide) {
		ALL_GUIDES.add(guide);
	}
	
	public static ArrayList<ItemGuide> getGuideItems() {
		return ALL_GUIDES;
	}
	
	public static boolean hasGuideItems() {
		return !ALL_GUIDES.isEmpty();
	}
	
	public GuideEntry addEntry(String loc) {
		GuideEntry entry = new GuideEntry(this.getEntryCount(), Utils.localize(loc));
		this.entries.add(entry);
		return entry;
	}
	
	public GuideEntry getEntryById(int id) {
		return this.entries.get(id);
	}
	
	public ArrayList<GuideEntry> getEntries() {
		return this.entries;
	}
	
	public int getEntryCount() {
		return this.entries.size();
	}
	
	public boolean hasEntries() {
		return !this.entries.isEmpty();
	}
}
