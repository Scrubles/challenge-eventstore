package net.intelie.challenges;

import java.util.concurrent.CountDownLatch;

/*
 * Created to test insertion with multiple threads
 */
public class EventInsertThread extends Thread {

	private EventStore eventStore;
	private CountDownLatch countDownLatch;
	private long totalEvents;
	private int threadIndex;
	
	public EventInsertThread(EventStore eventStore, CountDownLatch countDownLatch, long totalEvents, int threadIndex) {
		this.eventStore = eventStore;
		this.countDownLatch = countDownLatch;
		this.totalEvents = totalEvents;
		this.threadIndex = threadIndex;
	}

	@Override
	public void run() {
		for (int i=0; i < totalEvents; i++)
			eventStore.insert(new Event(EventStoreTest.EVENT + threadIndex));
		countDownLatch.countDown();
	}
}
