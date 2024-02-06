package com.lpenaud.bouts.reflection.exception;

public class ReflectionException extends RuntimeException {

	private static final long serialVersionUID = 8846374325216444450L;

	public ReflectionException(final String message) {
		super(message);
	}

	public ReflectionException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public ReflectionException(final String message, final Throwable cause,
			final boolean enableSuppression, final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
