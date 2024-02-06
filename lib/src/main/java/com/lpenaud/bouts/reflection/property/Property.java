package com.lpenaud.bouts.reflection.property;

import com.lpenaud.bouts.reflection.exception.ReflectionException;
import java.lang.reflect.Method;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Property {

	private final Method getter;

	private final Method setter;

	public Object getObject(final Object target) {
		try {
			return this.getter.invoke(target);
		} catch (final ReflectiveOperationException e) {
			throw new ReflectionException("Cannot get property", e);
		}
	}

	public void set(final Object target, final Object value) {
		try {
			this.setter.invoke(target, value);
		} catch (final ReflectiveOperationException e) {
			throw new ReflectionException("Cannot set property", e);
		}
	}
}
