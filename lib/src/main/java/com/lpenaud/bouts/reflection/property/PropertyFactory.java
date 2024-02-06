package com.lpenaud.bouts.reflection.property;

import com.lpenaud.bouts.reflection.ReflectionUtils;
import com.lpenaud.bouts.reflection.exception.ReflectionException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.stream.Stream;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(fluent = true)
public class PropertyFactory {

	private String getterPrefix = "get";

	private String setterPrefix = "set";

	private boolean accessible = true;

	public Stream<Property> properties(final Class<?> type) {
		return ReflectionUtils.getDeclaredFields(type)
				.map(f -> this.newProperty(type, f));
	}

	public Property newProperty(final Class<?> type, final Field field) {
		return Property.builder()
				.getter(this.getter(type, field))
				// TODO: Make optional
				.setter(this.setter(type, field))
				.build();
	}

	private Method getter(final Class<?> type, final Field field) {
		try {
			final var getter = type.getMethod(
					ReflectionUtils.prefixField(field, this.getterPrefix));
			getter.setAccessible(this.accessible);
			return getter;
		} catch (final NoSuchMethodException e) {
			final var message = String.format(
					"Cannot find the getter method %s.%s",
					type.getName(),
					ReflectionUtils.prefixField(field, this.getterPrefix));
			throw new ReflectionException(message, e);
		}
	}

	private Method setter(final Class<?> type, final Field field) {
		try {
			final var setter = type.getMethod(
					ReflectionUtils.prefixField(field, this.setterPrefix),
					field.getType());
			setter.setAccessible(this.accessible);
			return setter;
		} catch (final NoSuchMethodException e) {
			final var message = String.format(
					"Cannot find the setter method %s.%s",
					type.getName(),
					ReflectionUtils.prefixField(field, this.setterPrefix));
			throw new ReflectionException(message, e);
		}
	}

}
