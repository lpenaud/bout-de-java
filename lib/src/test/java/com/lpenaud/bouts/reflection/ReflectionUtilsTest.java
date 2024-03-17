package com.lpenaud.bouts.reflection;

import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

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

	@Test
	void testGetConstructor() {
		final var doe = new Doe();
		final var expected = new John(doe);
		final var contructor = ReflectionUtils.getConstructor(John.class,
				Object.class);
		final var actual = contructor.newInstance(doe);
		// It's a new instance
		Assertions.assertTrue(expected != actual);
		// With the same attributes
		Assertions.assertTrue(expected.johnValue == actual.johnValue);
	}

}
