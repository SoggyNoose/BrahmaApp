package edu.rosehulman.brahma.plugin;

public abstract class BasePlugin implements Plugin {
	
	private String name;
	private boolean enabled = false;
	
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
	public void start() {
		enabled = true;
	}
	
	@Override
	public void stop() {
		enabled = false;
	}
}
