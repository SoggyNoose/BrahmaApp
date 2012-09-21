package edu.rosehulman.brahma.events.plugin;

import edu.rosehulman.brahma.events.Event;
import edu.rosehulman.brahma.plugin.Plugin;

public abstract class PluginEvent implements Event {
	protected Plugin plugin;
	
	public PluginEvent(Plugin thePlugin) {
		this.plugin = thePlugin;
	}
	
	public final Plugin getPlugin() {
		return this.plugin;
	}
}
