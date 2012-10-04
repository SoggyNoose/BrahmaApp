package edu.rosehulman.brahma.plugin;

import javax.swing.JPanel;

import edu.rosehulman.brahma.IPluginManager;

public interface Plugin {

	public boolean isEnabled();
	public String getName();
	
	public void layout(JPanel panel);
	public void start();
	public void stop();
	
	public IPluginManager getPluginManager();
	
	public void enable();
	public void disable();
}
