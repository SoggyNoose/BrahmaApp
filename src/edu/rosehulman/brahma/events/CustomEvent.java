package edu.rosehulman.brahma.events;

import java.util.HashMap;
import java.util.Map;

public class CustomEvent implements Event {

	private final String id;
	private Map<String, Object> infoBundle = new HashMap<String, Object>();
	
	public CustomEvent(String id) {
		this.id = id;
	}
	
	public String getId() {
		return this.id;
	}
	
	public void addInfo(String key, Object data) {
		infoBundle.put(key, data);
	}
	
	public Object getInfo(String key) {
		return infoBundle.get(key);
	}
}
