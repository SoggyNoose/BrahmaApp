package edu.rosehulman.brahma.events.plugin;

import edu.rosehulman.brahma.plugin.Plugin;

public class PluginEnableEvent extends PluginEvent {

	public PluginEnableEvent(Plugin thePlugin) {
		super(thePlugin);
	}

}
