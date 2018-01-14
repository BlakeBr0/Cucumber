package com.blakebr0.cucumber.iface;

public interface IColoredItem {
	
	int color();
	default int index() {
		return 0;
	}
}
