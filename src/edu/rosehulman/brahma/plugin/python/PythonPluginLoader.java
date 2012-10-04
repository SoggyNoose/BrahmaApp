package edu.rosehulman.brahma.plugin.python;

import java.io.File;

import edu.rosehulman.brahma.PluginLoader;
import edu.rosehulman.brahma.plugin.Plugin;

public class PythonPluginLoader implements PluginLoader {
	
	private static final String filetype = "pyp";
	
	@Override
	public Plugin loadPlugin(File file) throws Exception {
		// TODO Auto-generated method stub
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
