package edu.rosehulman.brahma;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.rosehulman.brahma.events.HandlerList;
import edu.rosehulman.brahma.events.Listener;
import edu.rosehulman.brahma.plugin.Plugin;
import edu.rosehulman.brahma.plugin.java.JavaPluginLoader;

public class PluginManager implements IPluginManager {
	
	private final HandlerList handlerList;
	
	private WatchDir watchDir;
	private List<Plugin> plugins = new ArrayList<Plugin>();
	private Map<String, PluginLoader> filetypeAssociation = new HashMap<String, PluginLoader>();
	private Map<String, Plugin> lookupTable = new HashMap<String, Plugin>();
	
	private final Class[] loaders = { JavaPluginLoader.class }; 
	
	public PluginManager() {
		this.handlerList = new HandlerList();
	}

	@Override
	public void start() {
		for (Class<? extends PluginLoader> c : this.loaders) {
			try {
				PluginLoader instance = c.newInstance();
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
	public void registerEvents(Listener listener, Plugin plugin) {
		if (plugin.isEnabled()) {
			this.handlerList.addListener(listener);
		}
	}

	@Override
	public void loadBundle(Path bundlePath) {
		for (String filetype : filetypeAssociation.keySet()) {
			if (bundlePath.getFileName().toString().contains(filetype)) {
				PluginLoader loader = filetypeAssociation.get(filetype);
				loader.loadPlugin(bundlePath.toFile());
			}
		}
	}

	@Override
	public void unloadBundle(Path bundlePath) {
		// TODO Auto-generated method stub
		
	}
}