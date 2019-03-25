package net.intelie.challenges;

import java.util.Iterator;
import java.util.Map;

public class MapEventIterator implements EventIterator {
	
	private Map<String, Event> events;
	private Iterator<Event> iterator;
	private Event current;
	
	public MapEventIterator(Map<String, Event> events, Iterator<Event> iterator) {
		this.events = events;
		this.iterator = iterator;
	}
	
	@Override
	public void close() throws Exception {
		iterator = null;
	}
	
	@Override
	public void remove() {
		if (current != null) {
			events.remove(current.id());
			current = null;
		}
	}
	
	@Override
	public boolean moveNext() {
		current = iterator.hasNext() ? iterator.next() : null;
		return current != null;
	}
	
	@Override
	public Event current() {
		return current;
	}
}
