package edu.rosehulman.brahma;

import java.nio.file.Path;
import java.util.Map;

import edu.rosehulman.brahma.events.Event;
import edu.rosehulman.brahma.events.Listener;
import edu.rosehulman.brahma.plugin.Plugin;

public interface IPluginManager extends Runnable {
	public void start();
	
	public void registerEvents(Listener listener, Plugin plugin);
	public void loadBundle(Path bundlePath);
	public void unloadBundle(Path bundlePath);
	public void enablePlugin(Plugin plugin);
	public void disablePlugin(Plugin plugin);
	public void registerEvents(Listener listener);
	public void callEvent(Event event);
	
	public Map<String, Plugin> getPluginMap();
}
