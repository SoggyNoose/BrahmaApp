package edu.rosehulman.brahma.plugin.java;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import edu.rosehulman.brahma.PluginLoader;
import edu.rosehulman.brahma.plugin.Plugin;

public class JavaPluginLoader implements PluginLoader {
	
	private static final String filetype = "jar";

	@Override
	public Plugin loadPlugin(File file) {
		return null;
	}

	@Override
	public void unloadPlugin(Plugin plugin) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getFileType() {
		return filetype;
	}

}
