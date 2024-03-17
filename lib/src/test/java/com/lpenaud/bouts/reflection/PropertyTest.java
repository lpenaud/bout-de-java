package com.lpenaud.bouts.reflection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.lpenaud.bouts.reflection.property.Property;
import com.lpenaud.bouts.reflection.property.PropertyFactory;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

class PropertyTest {

	@Getter
	@Setter
	@EqualsAndHashCode
	@AllArgsConstructor
	@NoArgsConstructor
	private static class Pojo {

		private int intValue;

		private Object objectValue;
	}

	private record PojoRecord(long longValue, Object objectValue) {

	}

	@Getter
	@Builder
	@AllArgsConstructor(access = AccessLevel.PRIVATE)
	@EqualsAndHashCode
	private static class PojoBuilder {

		private final short shortValue;

		private final Object objectValue;
	}

	@Test
	void testPojo() {
		final var expected = new Pojo(123, "test");
		final var actual = new Pojo();
		final var properties = new PropertyFactory<>(Pojo.class)
				.properties()
				.iterator();
		Assertions.assertNotEquals(expected, actual);
		Assertions.assertTrue(properties.hasNext());
		this.setProperty(properties.next(), actual, expected.getIntValue());
		Assertions.assertTrue(properties.hasNext());
		this.setProperty(properties.next(), actual, expected.getObjectValue());
		Assertions.assertFalse(properties.hasNext());
		Assertions.assertEquals(expected, actual);
	}

	@Test
	void testPojoRecord() {
		final var expected = new PojoRecord(123, "test");
		final var properties = new PropertyFactory<>(PojoRecord.class)
				.getterPrefix("")
				.setterPrefix("")
				.properties()
				.iterator();
		Assertions.assertTrue(properties.hasNext());
		Assertions.assertEquals(properties.next().getObject(expected),
				expected.longValue());
		Assertions.assertTrue(properties.hasNext());
		Assertions.assertEquals(properties.next().getObject(expected),
				expected.objectValue());
		Assertions.assertFalse(properties.hasNext());
	}

	@Test
	void testPojoBuilder() {
		Assertions.fail("Not implemented");
	}

	void setProperty(final Property property, final Object target,
			final Object value) {
		property.setObject(target, value);
		Assertions.assertEquals(value, property.getObject(target));
	}

}
