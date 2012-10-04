package edu.rosehulman.brahma.plugin.python;

import org.python.core.PyObject;

import edu.rosehulman.brahma.plugin.BasePlugin;

public abstract class PythonPlugin extends BasePlugin {
	
	private PyObject instance;
	private PythonHooks hooks;

	public PythonPlugin(String name) {
		super(name);
	}
	
	public void setHooks(PythonHooks hooks, PyObject instance) {
		this.hooks = hooks;
		this.instance = instance;
	}
	
	@Override
	public void start() {
		if (this.hooks.onEnable != null) {
			this.hooks.onEnable.__call__(instance);
		}
	}
	
	@Override
	public void stop() {
		if (this.hooks.onDisable != null) {
			this.hooks.onDisable.__call__(instance);
		}
	}
	
	

}
