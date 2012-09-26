package edu.rosehulman.brahma;

import java.nio.file.Path;

import edu.rosehulman.brahma.events.Listener;
import edu.rosehulman.brahma.plugin.Plugin;

public interface IPluginManager {
	public void start();
	
	public void registerEvents(Listener listener, Plugin plugin);
	public void loadBundle(Path bundlePath);
	public void unloadBundle(Path bundlePath);
}
