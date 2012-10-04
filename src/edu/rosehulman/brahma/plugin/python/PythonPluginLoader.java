package edu.rosehulman.brahma.plugin.python;

import java.io.File;

import org.python.util.PythonInterpreter;

import edu.rosehulman.brahma.PluginLoader;
import edu.rosehulman.brahma.plugin.Plugin;

public class PythonPluginLoader implements PluginLoader {
	
	private static final String filetype = "pyp";
	
	@Override
	public Plugin loadPlugin(File file) throws Exception {
		PythonPlugin result = null;
		
		try {
			PythonHooks hooks = new PythonHooks();
			PythonInterpreter interpreter = new PythonInterpreter();
			
			interpreter.exec("import __builtin__");
			interpreter.exec("__builtin__.hook = hook");
			interpreter.exec("__builtin__.info = info");
		} catch (Exception e) {
			// TODO
		}
		return result;
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
