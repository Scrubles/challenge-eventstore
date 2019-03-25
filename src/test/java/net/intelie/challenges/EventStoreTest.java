package net.intelie.challenges;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.concurrent.CountDownLatch;

import org.junit.Before;
import org.junit.Test;

public class EventStoreTest {

	public static final String EVENT = "event_";
	private static final String EVENT_1 = EVENT + "1";
	private static final String EVENT_2 = EVENT + "2";
	private EventStore eventStore;
	
	@Before
	public void setup() {
		eventStore = new MapEventStore();
	}
	
    @Test
	public void shouldInsertEvent() {
		Event event = new Event(EVENT_1);
		eventStore.insert(event);
		assertEquals(1, eventStore.count());
	}
	
    @Test
	public void shouldRemoveEventsByType() {
		eventStore.insert(new Event(EVENT_1));
		eventStore.insert(new Event(EVENT_1));
		eventStore.insert(new Event(EVENT_2));
		eventStore.removeAll(EVENT_1);
		assertEquals(1, eventStore.count());
	}

    @Test
	public void shouldIterateQueriedEvents() throws Exception {
		eventStore.insert(new Event(EVENT_1));
		eventStore.insert(new Event(EVENT_1));
		eventStore.insert(new Event(EVENT_2));
		try (EventIterator iterator = eventStore.query(EVENT_1, 0, System.currentTimeMillis()+1)) {
			int i=0;
			while (iterator.moveNext())
				i++;
			assertEquals(2, i);
			assertEquals(3, eventStore.count());
		} 
	}

    @Test
    public void shouldInsertEventsThreadSafely() throws InterruptedException {
    	long totalEvents = 1_000_000;
    	int nThreads = 4;
		CountDownLatch countDownLatch = new CountDownLatch(nThreads);
		for (int i=0; i < nThreads; i++)
			new EventInsertThread(eventStore, countDownLatch, totalEvents, i).start();
		countDownLatch.await();
		assertEquals(totalEvents*nThreads, eventStore.count());
    }

    @Test
    public void shouldInsertWhileRemovingEventsThreadSafely() throws InterruptedException {
    	long totalEvents = 1_000_000;
    	int nThreads = 4;
		CountDownLatch countDownLatch = new CountDownLatch(nThreads);
		for (int i=0; i < nThreads; i++)
			new EventInsertThread(eventStore, countDownLatch, totalEvents, i).start();
		new EventRemoveThread(eventStore, countDownLatch, nThreads).start();
		countDownLatch.await();
		assertNotEquals(totalEvents*nThreads, eventStore.count());
    }
}
