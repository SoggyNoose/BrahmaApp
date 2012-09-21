package edu.rosehulman.brahma.events.plugin;

import edu.rosehulman.brahma.plugin.Plugin;

public class PluginDisableEvent extends PluginEvent {

	public PluginDisableEvent(Plugin thePlugin) {
		super(thePlugin);
	}

}
