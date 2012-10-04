package edu.rosehulman.brahma.plugin.python;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import java.util.zip.ZipFile;

import org.python.core.PyClass;
import org.python.core.PyMethod;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

import edu.rosehulman.brahma.IPluginManager;
import edu.rosehulman.brahma.PluginLoader;
import edu.rosehulman.brahma.plugin.BasePlugin;
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
			
			interpreter.exec("import __builtin__");
			
			InputStream mainFile = zf.getInputStream(zf.getEntry("plugin.py"));
			interpreter.execfile(mainFile);
			
			PyObject pyClass = interpreter.get(pluginName);
			
			try {
				hooks.onEnable = (PyMethod)pyClass.__getattr__("onEnable");
			} catch(Exception e) {
				
			}
			try {
				hooks.onDisable = (PyMethod)pyClass.__getattr__("onDisable");
			} catch(Exception e) {
				
			}
			try {
				hooks.onLoad = (PyMethod)pyClass.__getattr__("onLoad");
			} catch(Exception e) {
				
			}
			try {
				hooks.onUnload = (PyMethod)pyClass.__getattr__("onUnload");
			} catch(Exception e) {
				
			}
			
			PyObject pythonInstance = pyClass.__call__();
			result = (PythonPlugin)pythonInstance.__tojava__(PythonPlugin.class);
			
			result.setHooks(hooks, pythonInstance);
			
			Class<? extends BasePlugin> basePluginClass = result.getClass();
	        Field pluginManagerField;
	        try {
	        	pluginManagerField = basePluginClass.getSuperclass().getSuperclass().getDeclaredField("pluginManager");
	        	pluginManagerField.setAccessible(true);
	        	pluginManagerField.set(result, pluginManager);
	        } catch (Exception e) {
	        	// TODO
	        }
		} catch (Exception e) {
			// TODO
			e.printStackTrace();
		}
		zf.close();
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
