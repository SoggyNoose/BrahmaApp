package edu.rosehulman.brahma.events;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.rosehulman.brahma.EventExecutor;

public class HandlerList {
	
	private Map<Class<? extends Event>, List<EventExecutor>> listeners;
	
	public HandlerList() {
		listeners = new HashMap<Class<? extends Event>, List<EventExecutor>>();
	}
	
	public void addListener(final Listener listener) {
		Class<? extends Listener> listenClass = listener.getClass();
		
		// Go through all the methods in the class to determine which 
		// have the EventHandler annotation
		Method[] methods = listenClass.getDeclaredMethods();
		for (final Method method : methods) {
			EventHandler annotation = method.getAnnotation(EventHandler.class);
			if (annotation != null) {
				Class<?>[] parameters = method.getParameterTypes();
				if (parameters.length != 1 || !Event.class.isAssignableFrom(parameters[0])) {
					continue;
				}
				
				Class<? extends Event> event = (Class<? extends Event>) parameters[0];
				List<EventExecutor> executors = listeners.get(event);
				
				EventExecutor executor = new EventExecutor() {
					public void execute(Event event) {
						try {
							method.invoke(listener, event);
						} catch (Exception ex) {
							// TODO do something here
						}
					}
				};
				
				if (executors != null) {
					executors.add(executor);
				} else {
					List<EventExecutor> list = new ArrayList<EventExecutor>();
					list.add(executor);
					listeners.put(event, list);
				}
			}
		}
	}
	
	public void callEvent(Event event) {
		List<EventExecutor> handlers = this.listeners.get(event.getClass());
		
		if (handlers == null) {
			return;
		}
		
		for (EventExecutor handler : handlers) {
			handler.execute(event);
		}
	}
}
