package com.lpenaud.bouts.reflection.property;

import java.lang.reflect.Method;

import com.lpenaud.bouts.reflection.exception.ReflectionException;

import lombok.AllArgsConstructor;

/**
 * Java property. Can be (im)utable.
 */
@AllArgsConstructor
public abstract class Property {

	/**
	 * Getter method.
	 */
	protected final Method getter;

	/**
	 * Get the property value from the target.
	 *
	 * @param target Target to use.
	 * @return The property value.
	 */
	public Object getObject(final Object target) {
		try {
			return this.getter.invoke(target);
		} catch (final ReflectiveOperationException e) {
			throw new ReflectionException("Cannot get property", e);
		}
	}

	/**
	 * Set the property from the target.
	 *
	 * @param target Target to use.
	 * @param value  New value to set.
	 * @throws UnsupportedOperationException If the property is immutable.
	 */
	public void setObject(final Object target, final Object value) {
		throw new UnsupportedOperationException(
				"Immutable property cannot be set");
	}

}