package com.lpenaud.bouts;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ArrayUtilsTest {

	private static final String[] ARRAY = {"abc", "bcd"};

	@Test
	void testCopy() {
		final var actual = new String[ARRAY.length];
		ArrayUtils.copy(ARRAY, actual);
		Assertions.assertArrayEquals(ARRAY, actual);
	}

	@Test
	void testCopySrcPosGt() {
		final Object[] expected = {null, null};
		final var actual = new String[ARRAY.length];
		ArrayUtils.copy(ARRAY, actual, 2);
		Assertions.assertArrayEquals(expected, actual);
	}

	@Test
	void testCopyDestLtSrc() {
		final Object[] expected = {ARRAY[0]};
		final var actual = new String[expected.length];
		ArrayUtils.copy(ARRAY, actual);
		Assertions.assertArrayEquals(expected, actual);

		expected[0] = ARRAY[1];
		ArrayUtils.copy(ARRAY, actual, 1);
		Assertions.assertArrayEquals(expected, actual);
	}

	@Test
	void testFillObject() {
		final var singleton = new Object();
		final Object[] expected = {singleton, singleton, singleton};
		final var actual = new Object[expected.length];
		Assertions.assertEquals(actual, ArrayUtils.fill(actual, () -> singleton));
		Assertions.assertArrayEquals(expected, actual);
	}

	@Test
	void testFillInt() {
		final Object[] expected = {0, 1, 4};
		final var actual = new Object[expected.length];
		Assertions.assertEquals(actual, ArrayUtils.fill(actual, i -> i * i));
		Assertions.assertArrayEquals(expected, actual);
	}
}
