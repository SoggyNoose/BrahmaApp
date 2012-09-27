package edu.rosehulman.brahma.plugin;

import javax.swing.JPanel;

public interface Plugin {

	public boolean isEnabled();
	public String getName();
	
	public void layout(JPanel panel);
	public void start();
	public void stop();
}
