package com.lpenaud.bouts.async;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;
import java.util.function.Supplier;
import java.util.logging.Level;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.java.Log;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Log
public class AsyncHelpers<T> {

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	@Setter
	@Accessors(fluent = true)
	public static class Builder<T> {

		private String prefix = "async-helpers-";

		private long start = 0l;

		private Supplier<Deque<RunnableFuture<T>>> listFactory = ArrayDeque::new;

		public AsyncHelpers<T> build() {
			return new AsyncHelpers<>(this.threadBuilder(), this.listFactory.get());
		}

		private Thread.Builder threadBuilder() {
			return Thread.ofVirtual()
					.name(this.prefix, this.start);
		}
	}

	private final Thread.Builder threadBuilder;

	private final Deque<RunnableFuture<T>> tasks;

	public static <T> AsyncHelpers.Builder<T> builder() {
		return new AsyncHelpers.Builder<>();
	}

	public AsyncHelpers<T> addCallable(final Callable<T> callable) {
		return this.addTask(new FutureTask<>(callable));
	}

	public AsyncHelpers<T> addTask(final RunnableFuture<T> task) {
		this.tasks.add(task);
		this.threadBuilder.start(task);
		return this;
	}

	public List<T> get() throws ExecutionException {
		final List<T> results = new ArrayList<>(this.tasks.size());
		RunnableFuture<T> task;
		try {
			while ((task = this.tasks.poll()) != null) {
				results.add(task.get());
			}
		} catch (final InterruptedException e) {
			log.log(Level.SEVERE, "Threads are interrupted, why?", e);
			this.tasks.forEach(t -> t.cancel(true));
			Thread.currentThread()
					.interrupt();
		}
		return results;
	}
}
