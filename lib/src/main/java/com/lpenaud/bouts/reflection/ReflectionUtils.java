package com.lpenaud.bouts.reflection;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Stream;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReflectionUtils {

	/**
	 * Get all parent from the given class.
	 *
	 * @param type Class to iterate.
	 * @return Stream with all parent {@linkplain Object} included.
	 */
	public static Stream<Class<?>> getAllParents(final Class<?> type) {
		return Stream.iterate(type, Objects::nonNull, Class::getSuperclass);
	}

	/**
	 * Get declared fields from a type.
	 *
	 * @param type Type to explore.
	 * @return Stream with the declared field.
	 */
	public static Stream<Field> getDeclaredFields(final Class<?> type) {
		return Arrays.stream(type.getDeclaredFields());
	}

	/**
	 * Get all declared field recursively with all parents.
	 *
	 * @param type Type to explore.
	 * @return Stream with all fields.
	 */
	public static Stream<Field> getAllDeclaredFields(final Class<?> type) {
		return ReflectionUtils.getAllParents(type)
				.flatMap(ReflectionUtils::getDeclaredFields);
	}

	/**
	 * Add a prefix to the field name. Useful to computer getter and setter
	 * method name.
	 *
	 * @param field  Field to use.
	 * @param prefix Prefix to add.
	 * @return Field name with the given prefix.
	 */
	public static String prefixField(final Field field, final String prefix) {
		return prefix == null || prefix.isEmpty() ? field.getName()
				: new StringBuilder(field.getName().length() + prefix.length())
						.append(prefix)
						.append(field.getName().substring(0, 1).toUpperCase())
						.append(field.getName().substring(1))
						.toString();
	}

	/**
	 * Find an contructor of the given type with given types arguments.
	 * 
	 * @param <T>  Type of the result object.
	 * @param type Type of the result object.
	 * @param args Arguments types.
	 * @return Contructor of T object which take the given arguments.
	 * @throws NoSuchElementException If no contructor is found.
	 */
	public static <T> ConstructorWrapper<T> getConstructor(final Class<T> type,
			final Class<?>... args) {
		try {
			return new ConstructorWrapper<>(type.getConstructor(args));
		} catch (final NoSuchMethodException e) {
			throw new NoSuchElementException("Cannot find contructor", e);
		}
	}
}
