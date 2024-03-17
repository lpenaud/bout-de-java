package com.lpenaud.bouts.reflection.property;

import java.lang.reflect.Method;

public class ImmutableProperty extends Property {

	public ImmutableProperty(final Method getter) {
		super(getter);
	}
}
