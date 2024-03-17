package com.lpenaud.bouts.reflection;

import java.lang.reflect.Constructor;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

/**
 * Contructor wrapper to wrap exception into runtime ones.
 *
 * @param <T> Target type.
 */
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ConstructorWrapper<T> {

	private final Constructor<T> constructor;

	/**
	 * Create a new instance of the object.
	 *
	 * @param initargs Arguments to pass to the constructor.
	 * @return A new instance.
	 * @throws IllegalArgumentException If there are any problem.
	 * @see Constructor#newInstance(Object...)
	 */
	public T newInstance(final Object... initargs) {
		try {
			return this.constructor.newInstance(initargs);
		} catch (final ReflectiveOperationException e) {
			throw new IllegalArgumentException("Cannot create a new instance",
					e);
		}
	}
}
