package com.lpenaud.bouts.function;

/**
 * Represents a predicate (boolean-valued function) with an object and a {@code int} as arguments.
 * @param <T> The type of the input to the predicate
 */
@FunctionalInterface
public interface ArrayPredicate<T> {

    /**
     * Evaluates this predicate on the given array value with its index.
     * @param value Value to test.
     * @param index Index of the value.
     * @param array The current array to be tested.
     * @return {@code true} if the input arguments matches the predicate,
     * otherwise {@code false}
     */
    boolean test(T value, int index, T[] array);
}
