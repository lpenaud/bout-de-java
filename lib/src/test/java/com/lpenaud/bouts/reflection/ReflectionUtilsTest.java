package com.lpenaud.bouts.reflection;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ReflectionUtilsTest {

	private final static Class<?>[] TYPES = { John.class, Doe.class,
			Object.class };

	@Getter
	@ToString
	private static class Doe {

		private Object doeValue;

	}

	@Getter
	@ToString(callSuper = true)
	@AllArgsConstructor
	private static class John extends Doe {

		private Object johnValue;
	}

	@Test
	void testGetAllParents() {
		final var actual = ReflectionUtils.getAllParents(John.class)
				.toArray();
		Assertions.assertArrayEquals(TYPES, actual);
	}

	@Test
	void testGetDeclaredFields() {
		final var expected = Doe.class.getDeclaredFields();
		final var actual = ReflectionUtils.getDeclaredFields(Doe.class)
				.toArray();
		Assertions.assertArrayEquals(expected, actual);
	}

	@Test
	void testGetAllDeclaredFields() {
		final Object[] expected = Arrays.stream(TYPES)
				.map(Class::getDeclaredFields)
				.flatMap(Arrays::stream)
				.toArray();
		final var actual = ReflectionUtils.getAllDeclaredFields(John.class)
				.toArray();
		Assertions.assertArrayEquals(expected, actual);
	}

	@Test
	void testPrefixField() {
		final var field = John.class.getDeclaredFields()[0];
		final var expected = "getJohnValue";
		final var actual = ReflectionUtils.prefixField(field, "get");
		Assertions.assertEquals(expected, actual);
	}

}
