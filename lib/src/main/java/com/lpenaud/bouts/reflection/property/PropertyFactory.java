package com.lpenaud.bouts.reflection.property;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.stream.Stream;

import com.lpenaud.bouts.reflection.ReflectionUtils;

import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.java.Log;

@Setter
@Accessors(fluent = true)
@Log
public class PropertyFactory<T> {

	private final Class<T> type;

	private String getterPrefix;

	private String setterPrefix;

	public PropertyFactory(final Class<T> type) {
		this.type = type;
		this.getterPrefix = "get";
		this.setterPrefix = "set";
	}

	public Stream<Property> properties() {
		return ReflectionUtils.getDeclaredFields(this.type)
				.map(this::newProperty);
	}

	public Property newProperty(final Field field) {
		return this.optionalSetter(field)
				.map(s -> this.newMutableProperty(field, s))
				.orElseGet(() -> this.newImmutableProperty(field));
	}

	public Property newMutableProperty(final Field field) {
		return this.newMutableProperty(field, this.getSetter(field));
	}

	public Property newImmutableProperty(final Field field) {
		return new ImmutableProperty(this.getter(field));
	}

	private Property newMutableProperty(final Field field,
			final Method setter) {
		return new MutableProperty(this.getter(field), setter);
	}

	private Method getter(final Field field) {
		try {
			final var getter = this.type.getMethod(
					ReflectionUtils.prefixField(field, this.getterPrefix));
			getter.setAccessible(true);
			return getter;
		} catch (final NoSuchMethodException e) {
			final var message = String.format(
					"Cannot find the getter method %s.%s",
					this.type.getName(),
					ReflectionUtils.prefixField(field, this.getterPrefix));
			throw new NoSuchElementException(message, e);
		}
	}

	private Method getSetter(final Field field) {
		try {
			final var setter = this.type.getMethod(
					ReflectionUtils.prefixField(field, this.setterPrefix),
					field.getType());
			setter.setAccessible(true);
			return setter;
		} catch (final NoSuchMethodException e) {
			final var message = String.format(
					"Cannot find the setter method %s.%s",
					this.type.getSimpleName(),
					ReflectionUtils.prefixField(field, this.setterPrefix));
			throw new NoSuchElementException(message, e);
		}
	}

	private Optional<Method> optionalSetter(final Field field) {
		try {
			return Optional.of(this.getSetter(field));
		} catch (final NoSuchElementException e) {
			final var logRecord = new LogRecord(Level.WARNING, e.getMessage());
			logRecord.setThrown(e);
			log.log(logRecord);
			return Optional.empty();
		}
	}

}
