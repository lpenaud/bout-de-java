package com.lpenaud.bouts;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class IntIterator implements Iterator<Integer> {

	/**
	 * Default incremental step
	 */
	private static final int DEFAULT_STEP = 1;

	/**
	 * Current value
	 */
	private int i;

	/**
	 * Max value
	 */
	private final int max;

	/**
	 * Step value
	 */
	private final int step;

	/**
	 * @param start
	 *            Start value
	 * @param max
	 *            Max value
	 * @param step
	 *            Incremental step
	 */
	public IntIterator(final int start, final int max, final int step) {
		this.i = start;
		this.max = max;
		this.step = step;
	}

	/**
	 * Create a new instance with the incremental step by 1.
	 *
	 * @param start
	 *            Start value
	 * @param max
	 *            Max value
	 * @see IntIterator#IntIterator(int, int, int)
	 */
	public IntIterator(final int start, final int max) {
		this(start, max, DEFAULT_STEP);
	}

	/**
	 * Create a new instance with the incremental step by 1 up to
	 * {@linkplain Integer#MAX_VALUE}.
	 *
	 * @param start
	 *            Start value
	 * @see IntIterator#IntIterator(int, int, int)
	 */
	public IntIterator(final int start) {
		this(start, Integer.MAX_VALUE, DEFAULT_STEP);
	}

	/**
	 * Create a new instance start with 0 to {@linkplain Integer#MAX_VALUE} by 1.
	 *
	 * @param start
	 *            Start value
	 * @see IntIterator#IntIterator(int, int, int)
	 */
	public IntIterator() {
		this(0, Integer.MAX_VALUE, DEFAULT_STEP);
	}

	@Override
	public boolean hasNext() {
		return this.i < this.max;
	}

	@Override
	public Integer next() {
		final int current = this.i;
		if (!this.hasNext()) {
			throw new NoSuchElementException();
		}
		this.i += this.step;
		return current;
	}
}
