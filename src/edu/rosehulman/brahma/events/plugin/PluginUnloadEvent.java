package edu.rosehulman.brahma.events.plugin;

import edu.rosehulman.brahma.plugin.Plugin;

public class PluginUnloadEvent extends PluginEvent {

	public PluginUnloadEvent(Plugin thePlugin) {
		super(thePlugin);
	}

}
