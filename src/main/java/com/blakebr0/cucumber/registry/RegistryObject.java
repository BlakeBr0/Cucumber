package com.blakebr0.cucumber.registry;

import net.minecraft.util.IStringSerializable;

public class RegistryObject<T> implements IStringSerializable {

	private final T object;
	private final String name;
	private final Ore[] oreNames;

	public RegistryObject(T object, String name) {
		this.object = object;
		this.name = name;
		this.oreNames = null;
	}
	
	public RegistryObject(T object, String name, Ore[] oreNames) {
		this.object = object;
		this.name = name;
		this.oreNames = oreNames;
	}

	public T get() {
		return this.object;
	}

	@Override
	public String getName() {
		return this.name;
	}
	
	public Ore[] getOreNames() {
		return this.oreNames;
	}
}