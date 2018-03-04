package com.blakebr0.cucumber.registry;

import net.minecraftforge.oredict.OreDictionary;

public class Ore {

	private int meta;
	private String name;
	
	private Ore(int meta, String name) {
		this.meta = meta;
		this.name = name;
	}

	public static Ore of(String name) {
		return of(OreDictionary.WILDCARD_VALUE, name);
	}

	public static Ore of(int meta, String name) {
		return new Ore(meta, name);
	}

	public static Ore[] listOf(int[] meta, String name) {
		Ore[] ores = new Ore[meta.length];
		for (int i = 0; i < ores.length; i++) {
			ores[i] = of(meta[i], name);
		}
		return ores;
	}
	
	public static Ore[] listOf(int meta, String[] name) {
		Ore[] ores = new Ore[name.length];
		for (int i = 0; i < ores.length; i++) {
			ores[i] = of(meta, name[i]);
		}
		return ores;
	}
	
	public int getMeta() {
		return this.meta;
	}
	
	public String getName() {
		return this.name;
	}
}
