package edu.rosehulman.brahma.plugin;

import edu.rosehulman.brahma.IPluginManager;

public abstract class BasePlugin implements Plugin {
	
	private String name;
	private boolean enabled = false;
	
	private IPluginManager pluginManager;
	
	public BasePlugin(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public boolean isEnabled() {
		return enabled;
	}
	
	@Override
	public void start() {};
	@Override
	public void stop() {};
	
	@Override
	public IPluginManager getPluginManager() {
		return pluginManager;
	}
	
	@Override
	public final void enable() {
		enabled = true;
	}
	
	@Override
	public final void disable() {
		enabled = false;
	}
}
