package com.lpenaud.bouts.reflection.property;

import java.lang.reflect.Method;

import com.lpenaud.bouts.reflection.exception.ReflectionException;

public class MutableProperty extends Property {

	/**
	 * Setter method.
	 */
	private final Method setter;

	MutableProperty(final Method getter, final Method setter) {
		super(getter);
		this.setter = setter;
	}


	@Override
	public void setObject(final Object target, final Object value) {
		try {
			this.setter.invoke(target, value);
		} catch (final ReflectiveOperationException e) {
			throw new ReflectionException("Cannot set property", e);
		}
	}
}
