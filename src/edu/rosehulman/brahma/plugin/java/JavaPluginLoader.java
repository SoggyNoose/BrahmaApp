package edu.rosehulman.brahma.plugin.java;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import edu.rosehulman.brahma.IPluginManager;
import edu.rosehulman.brahma.PluginLoader;
import edu.rosehulman.brahma.plugin.BasePlugin;
import edu.rosehulman.brahma.plugin.Plugin;

public class JavaPluginLoader implements PluginLoader {
	
	private static final String filetype = "jar";
	
	private final IPluginManager pluginManager;
	
	public JavaPluginLoader(IPluginManager pluginManager) {
		this.pluginManager = pluginManager;
	}

	@Override
	public JavaPlugin loadPlugin(File file) throws Exception {
		JarFile jarFile = new JarFile(file);
		
		// Get the manifest file in the jar file
		Manifest mf = jarFile.getManifest();
        Attributes mainAttribs = mf.getMainAttributes();
        
        // Get hold of the Plugin-Class attribute and load the class
        String className = mainAttribs.getValue("Plugin-Class");
        URL[] urls = new URL[1];
        urls[0] = file.toURI().toURL();
        ClassLoader classLoader = new URLClassLoader(urls);
        Class<?> pluginClass = classLoader.loadClass(className);
        
        // Create a new instance of the plugin class and add to the core
        JavaPlugin plugin = (JavaPlugin)pluginClass.newInstance();
        
        Class<? extends BasePlugin> basePluginClass = plugin.getClass();
        Field pluginManagerField;
        try {
        	pluginManagerField = basePluginClass.getSuperclass().getSuperclass().getDeclaredField("pluginManager");
        	pluginManagerField.setAccessible(true);
        	pluginManagerField.set(plugin, pluginManager);
        } catch (Exception e) {
        	// TODO
        }

        // Release the jar resources
        jarFile.close();
        
        return plugin;
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
