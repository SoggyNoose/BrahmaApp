package edu.rosehulman.brahma;

import edu.rosehulman.brahma.events.Listener;
import edu.rosehulman.brahma.plugin.Plugin;

public interface IPluginManager {
	public void start();
	
	public void registerEvents(Listener listener, Plugin plugin);
	public void loadPlugin(Plugin plugin);
	public void enablePlugin(Plugin plugin);
	public void disablePlugin(Plugin plugin);
	public void unloadPlugin(Plugin plugin);
}
