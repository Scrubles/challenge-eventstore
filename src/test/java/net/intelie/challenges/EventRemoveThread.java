package net.intelie.challenges;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/*
 * Created to test removal while inserting
 */
public class EventRemoveThread extends Thread {

	private EventStore eventStore;
	private CountDownLatch countDownLatch;
	private int nThreads;
	
	public EventRemoveThread(EventStore eventStore, CountDownLatch countDownLatch, int nThreads) {
		this.eventStore = eventStore;
		this.countDownLatch = countDownLatch;
		this.nThreads = nThreads;
	}

	@Override
	public void run() {
		while (countDownLatch.getCount() > 0)
			eventStore.removeAll(EventStoreTest.EVENT + new Random().nextInt(nThreads));
	}
}
