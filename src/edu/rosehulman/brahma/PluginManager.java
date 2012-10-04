package edu.rosehulman.brahma;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.rosehulman.brahma.events.Event;
import edu.rosehulman.brahma.events.HandlerList;
import edu.rosehulman.brahma.events.Listener;
import edu.rosehulman.brahma.events.plugin.PluginDisableEvent;
import edu.rosehulman.brahma.events.plugin.PluginEnableEvent;
import edu.rosehulman.brahma.events.plugin.PluginLoadEvent;
import edu.rosehulman.brahma.plugin.Plugin;
import edu.rosehulman.brahma.plugin.java.JavaPluginLoader;

public class PluginManager implements IPluginManager {
	
	private final HandlerList handlerList;
	
	private WatchDir watchDir;
	private List<Plugin> plugins = new ArrayList<Plugin>();
	private Map<String, PluginLoader> filetypeAssociation = new HashMap<String, PluginLoader>();
	private Map<Path, Plugin> lookupTable = new HashMap<Path, Plugin>();
	protected Map<String, Plugin> nameToPlugin = new HashMap<String, Plugin>();
	
	private final Class[] loaders = { JavaPluginLoader.class }; 
	
	protected PluginManager() throws IOException {
		this.handlerList = new HandlerList();
		watchDir = new WatchDir(this, FileSystems.getDefault().getPath("plugins"), false);
	}

	@Override
	public void start() {
		for (Class<? extends PluginLoader> c : this.loaders) {
			try {
				PluginLoader instance = c.getDeclaredConstructor(IPluginManager.class).newInstance(this);
				filetypeAssociation.put(instance.getFileType(), instance);
			} catch (Exception e) {
				// TODO 
			}
		}
		
		// First load existing plugins if any
		try {
			Path pluginDir = FileSystems.getDefault().getPath("plugins");
			File pluginFolder = pluginDir.toFile();
			File[] files = pluginFolder.listFiles();
			if(files != null) {
				for(File f : files) {
					this.loadBundle(f.toPath());
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		this.start();
		
		watchDir.processEvents();
	}
	
	@Override
	public void registerEvents(Listener listener, Plugin plugin) {
		if (plugin.isEnabled()) {
			this.registerEvents(listener);
		}
	}

	@Override
	public void registerEvents(Listener listener) {
		this.handlerList.addListener(listener);
	}
	
	@Override
	public void loadBundle(Path bundlePath) {
		Plugin plugin;
		
		for (String filetype : filetypeAssociation.keySet()) {
			if (bundlePath.getFileName().toString().contains(filetype)) {
				PluginLoader loader = filetypeAssociation.get(filetype);
				try {
					plugin = loader.loadPlugin(bundlePath.toFile());
					
					PluginLoadEvent event = new PluginLoadEvent(plugin);
					this.handlerList.callEvent(event);
					
					plugins.add(plugin);
					lookupTable.put(bundlePath, plugin);
					nameToPlugin.put(plugin.getName(), plugin);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void unloadBundle(Path bundlePath) {
		Plugin plugin = lookupTable.remove(bundlePath);
		plugins.remove(plugin);
		nameToPlugin.remove(plugin.getName());
	}

	@Override
	public Map<String, Plugin> getPluginMap() {
		return this.nameToPlugin;
	}

	@Override
	public void enablePlugin(Plugin plugin) {
		if (!plugin.isEnabled()) {
			plugin.start();
			plugin.enable();
			
			PluginEnableEvent event = new PluginEnableEvent(plugin);
			handlerList.callEvent(event);
		}
	}

	@Override
	public void disablePlugin(Plugin plugin) {
		if (plugin.isEnabled()) {
			plugin.stop();
			plugin.disable();
			
			PluginDisableEvent event = new PluginDisableEvent(plugin);
			handlerList.callEvent(event);
		}
	}

	@Override
	public void callEvent(Event event) {
		this.handlerList.callEvent(event);
	}
}