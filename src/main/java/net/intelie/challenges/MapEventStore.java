package net.intelie.challenges;

import java.util.Collections;
import java.util.Iterator;
import java.util.concurrent.ConcurrentSkipListMap;

public class MapEventStore implements EventStore {
	
	/*
	 * ConcurrentSkipListMap was chosen for being thread-safe.
	 * There was another option, ConcurrentHashMap, but it doesn't guarantee the runtime of its operations.
	 * Wherein ConcurrentSkipListMap guarantees O(log(n)) performance for most of its operations.
	 */
	private ConcurrentSkipListMap<String, Event> events = new ConcurrentSkipListMap<>();

	@Override
	public void insert(Event event) {
		events.putIfAbsent(event.id(), event);
	}

	@Override
	public void removeAll(String type) {
		if (type == null) // ConcurrentSkipListMap doesn't allow insertion of null key, so no need to try to remove
			return;
		events.values()
			.stream()
			.filter(e -> type.equals(e.type()))
			.forEach(e -> events.remove(e.id()));
	}

	@Override
	public EventIterator query(String type, long startTime, long endTime) {
		if (type == null || endTime < startTime)
			return new MapEventIterator(events, Collections.emptyIterator());
		
		Iterator<Event> iterator = events.values()
			.stream()
			.filter(e -> type.equals(e.type()) && startTime <= e.timestamp() && endTime > e.timestamp())
			.iterator();
		return new MapEventIterator(events, iterator);
	}
	
	@Override
	public int count() {
		return events.size();
	}
}
