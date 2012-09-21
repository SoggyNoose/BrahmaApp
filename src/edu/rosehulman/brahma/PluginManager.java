package edu.rosehulman.brahma;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import edu.rosehulman.brahma.events.Event;
import edu.rosehulman.brahma.events.EventHandler;
import edu.rosehulman.brahma.events.HandlerList;
import edu.rosehulman.brahma.events.Listener;
import edu.rosehulman.brahma.plugin.Plugin;

public class PluginManager implements IPluginManager {
	
	private final HandlerList handlerList;
	
	private WatchDir watchDir;
	private List<Plugin> plugins;
	
	public PluginManager() {
		this.handlerList = new HandlerList();
	}

	@Override
	public void start() {
		
	}
	
	@Override
	public void registerEvents(Listener listener, Plugin plugin) {
		if (plugin.isEnabled()) {
			this.handlerList.addListener(listener);
		}
	}

	@Override
	public void loadPlugin(Plugin plugin) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enablePlugin(Plugin plugin) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disablePlugin(Plugin plugin) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unloadPlugin(Plugin plugin) {
		// TODO Auto-generated method stub
		
	}
}