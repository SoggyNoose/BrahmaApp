package edu.rosehulman.brahma.events.plugin;

import edu.rosehulman.brahma.plugin.Plugin;

public class PluginLoadEvent extends PluginEvent {

	public PluginLoadEvent(Plugin thePlugin) {
		super(thePlugin);
	}

}
