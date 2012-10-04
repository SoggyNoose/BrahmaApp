package edu.rosehulman.brahma.plugin.python;

import java.io.File;
import java.io.InputStream;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.zip.ZipFile;

import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

import edu.rosehulman.brahma.IPluginManager;
import edu.rosehulman.brahma.PluginLoader;
import edu.rosehulman.brahma.plugin.Plugin;

public class PythonPluginLoader implements PluginLoader {
	
	private static final String filetype = "zip";
	
	private final IPluginManager pluginManager;
	
	public PythonPluginLoader(IPluginManager pluginManager) {
		this.pluginManager = pluginManager;
	}
	
	@Override
	public Plugin loadPlugin(File file) throws Exception {        
		PythonPlugin result = null;
		Manifest mf;
		
		ZipFile zf = new ZipFile(file);
		try {
			InputStream in = zf.getInputStream(zf.getEntry("MANIFEST.MF"));
			mf = new Manifest(in);
			
			Attributes mainAttribs = mf.getMainAttributes();
			
			String pluginName = mainAttribs.getValue("Name");
			String mainClass = mainAttribs.getValue("Plugin-Class");

			PythonHooks hooks = new PythonHooks();
			PythonInterpreter interpreter = new PythonInterpreter();
			
			PyObject pyClass = interpreter.get("plugin.py");
			result = (PythonPlugin)pyClass.__call__().__tojava__(PythonPlugin.class);
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
