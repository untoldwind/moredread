package net.untoldwind.moredread.ui.canvas;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;

import net.untoldwind.moredread.ui.MoreDreadUI;

public class TaskQueue {
	private final ConcurrentLinkedQueue<Callable<?>> queue = new ConcurrentLinkedQueue<Callable<?>>();

	public void enqueue(final Callable<?> callable) {
		queue.add(callable);
	}

	public void executeAll() {
		Callable<?> task;

		while ((task = queue.poll()) != null) {
			try {
				task.call();
			} catch (final Exception e) {
				MoreDreadUI.getDefault().log(e);
			}
		}
	}
}
