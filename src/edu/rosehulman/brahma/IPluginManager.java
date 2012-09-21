package edu.rosehulman.brahma;

import edu.rosehulman.brahma.plugin.Plugin;

public interface IPluginManager {
	public void start();
	
	public void registerEvents(Plugin plugin);
}
