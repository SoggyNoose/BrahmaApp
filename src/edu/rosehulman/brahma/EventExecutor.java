package edu.rosehulman.brahma;

import edu.rosehulman.brahma.events.Event;
import edu.rosehulman.brahma.events.Listener;

public interface EventExecutor {
	public void execute(Event event);
}
