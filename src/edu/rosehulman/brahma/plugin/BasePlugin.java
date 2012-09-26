package edu.rosehulman.brahma.plugin;

public abstract class BasePlugin implements Plugin {
	
	private boolean enabled;
	
	@Override
	public boolean isEnabled() {
		return enabled;
	}

}
