package com.lpenaud.bouts.async;

import java.util.concurrent.ExecutionException;
import java.util.stream.LongStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AsyncHelpersTest {

	@Test
	void testGet() throws ExecutionException {
		final var start = 0l;
		final var max = 2l;
		final var prefix = "async-helpers-test-";
		final var expected = LongStream.iterate(start, l -> l < max, l -> l + 1)
				.mapToObj(i -> prefix + i)
				.toList();
		final var asyncHelpers = AsyncHelpers.<String>builder()
				.prefix(prefix)
				.start(start)
				.build();
		expected.forEach(
				i -> asyncHelpers.addCallable(() -> Thread.currentThread()
						.getName()));
		final var results = asyncHelpers.get();
		for (int i = 0; i < results.size(); i++) {
			Assertions.assertEquals(expected.get(i), results.get(i));
		}
	}
}
