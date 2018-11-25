package com.blakebr0.cucumber.guide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.blakebr0.cucumber.guide.pages.IEntryPage;
import com.blakebr0.cucumber.guide.pages.PageText;
import com.blakebr0.cucumber.util.Utils;
import com.google.common.collect.Maps;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GuideEntry {
	
	private ArrayList<IEntryPage> pages = new ArrayList<>();
	private HashMap<String, String> replacements = new HashMap<>();

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
		return Utils.localize("guide.entry." + this.title);
	}
	
	public GuideEntry addPage(IEntryPage page) {
		this.pages.add(page);
		return this;
	}
	
	public GuideEntry addPageText() {
		this.pages.add(new PageText("guide.entry." + this.title + ".page." + (this.getPageCount() + 1), this.replacements));
		return this;
	}
	
	public GuideEntry addTextReplacement(String key, String value) {
		this.replacements.put(key, value);
		return this;
	}
		
	public ArrayList<IEntryPage> getPages() {
		return this.pages;
	}
	
	public IEntryPage getPage(int page) {
		return this.pages.get(page);
	}

	public int getPageCount() {
		return this.pages.size();
	}
	
	public boolean hasPages() {
		return !this.pages.isEmpty();
	}
	
	public HashMap<String, String> getTextReplacements() {
		return this.replacements;
	}
	
	public GuideEntry setIconStack(ItemStack stack) {
		this.icon = stack;
		return this;
	}
	
	public ItemStack getIconStack() {
		return this.icon;
	}
}
