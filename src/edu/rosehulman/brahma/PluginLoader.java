package edu.rosehulman.brahma;

import java.io.File;

import edu.rosehulman.brahma.plugin.Plugin;

public interface PluginLoader {
	public Plugin loadPlugin(File file);
	public void unloadPlugin(Plugin plugin);
	public String getFileType();
}
