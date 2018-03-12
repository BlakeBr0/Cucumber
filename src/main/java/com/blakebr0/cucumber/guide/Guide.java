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
	
	private String modname;
	private String name;
	private String author;
	private String registryName;
	private int color;
	private CreativeTabs tab;
	
	private Guide(String modname, String name, String author, String registryName, int color, CreativeTabs tab) {
		this.modname = modname;
		this.name = name;
		this.author = author;
		this.registryName = registryName;
		this.color = color;
		this.tab = tab;
	}
	
	public static Guide create(String modid, String name, String author, String registryName, int color, CreativeTabs tab) {
		Guide guide = new Guide(modid, name, author, registryName, color, tab);
		GuideRegistry.register(guide);
		return guide;
	}
	
	// TODO remove
	public void register() {
		Cucumber.REGISTRY.register(new ItemGuide(registryName, tab, this), registryName);
	}
	
	public ItemStack makeGuideBookStack(ItemGuide guide) {
		ItemStack stack = new ItemStack(guide);
		NBTTagCompound tag = new NBTTagCompound();
		
		tag.setString("Name", this.getName());
		tag.setString("ModName", this.getModName());
		tag.setString("Author", this.getAuthor());
		
		stack.setTagCompound(tag);	
	
		return stack;
	}
	
	public String getModName() {
		return this.modname;
	}
	
	public String getName() {
		return Utils.localize(this.name);
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
		GuideEntry entry = new GuideEntry(this.entries.size(), Utils.localize(loc));
		this.entries.add(entry);
		return entry;
	}
	
	public GuideEntry getEntryById(int id) {
		return this.entries.get(id);
	}
	
	public ArrayList<GuideEntry> getEntries() {
		return this.entries;
	}
	
	public boolean hasEntries() {
		return !this.entries.isEmpty();
	}
}
