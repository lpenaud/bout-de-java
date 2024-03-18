package com.lpenaud.bouts;

import java.util.function.IntFunction;
import java.util.function.Supplier;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ArrayUtils {

	/**
	 * Empty object array.
	 */
	public static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];

	/**
	 * Empty byte array.
	 */
	public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];

	/**
	 * Copy the source array in the destination one at 0 index If length of the
	 * source array is greeter than the destination one copy from 0 to
	 * destination length otherwise 0 to source length. <br>
	 * Equivalent of:
	 *
	 * <pre>
	 * ArrayUtils.copy(src, dest, 0);
	 * </pre>
	 *
	 * @param src  The source array
	 * @param dest The destination array.
	 * @see ArrayUtils#copy(Object[], Object[], int)
	 */
	public static void copy(final Object[] src, final Object[] dest) {
		ArrayUtils.copy(src, dest, 0);
	}

	/**
	 * Copy the source array in the destination one at 0 index. If length of the
	 * source array is greeter than the destination one copy from 0 to
	 * destination length otherwise 0 to source length.
	 *
	 * @param src    The source array
	 * @param dest   The destination array.
	 * @param srcPos Starting position in the source array.
	 * @see System#arraycopy(Object, int, Object, int, int)
	 */
	public static void copy(final Object[] src, final Object[] dest,
			final int srcPos) {
		if (src.length <= srcPos) {
			return;
		}
		System.arraycopy(src, srcPos, dest, 0,
				src.length > dest.length ? dest.length : src.length);
	}

	/**
	 * Fill the array with the results of the given factory.
	 *
	 * @param <T>     Array type
	 * @param array   Array to fill.
	 * @param factory Object supplier.
	 * @return The same input array.
	 */
	public static <T> T[] fill(final T[] array, final Supplier<T> factory) {
		if (array.length == 0) {
			return ArrayUtils.EMPTY_OBJECT_ARRAY;
		}
		for (int i = 0; i < array.length; i++) {
			array[i] = factory.get();
		}
		return array;
	}

	/**
	 * Fill the array with the results of the given factory.
	 *
	 * @param <T>     Array type
	 * @param array   Array to fill.
	 * @param factory Object supplier.
	 * @return The same input array.
	 */
	public static <T> T[] fill(final T[] array, final IntFunction<T> factory) {
		for (int i = 0; i < array.length; i++) {
			array[i] = factory.apply(i);
		}
		return array;
	}

	/**
     * Concat arrays.
     * If no arrays is given return empty array.
     * Always return new array expect if no arrays is given.
     * @param arrays Arrays to concat.
     * @return New array with all given values.
     */
    public static byte[] cat(final byte[]... arrays) {
        if (arrays.length == 0) {
            return ArrayUtils.EMPTY_BYTE_ARRAY;
        }
        if (arrays.length == 1) {
            return arrays[0].clone();
        }
        var length = 0;
        for (final var array : arrays) {
            length += array.length;
        }
        final var result = new byte[length];
        var destPos = 0;
        for (final var array : arrays) {
            System.arraycopy(array, 0, result, destPos, array.length);
            destPos += array.length;
        }
        return result;
    }

    /**
     * If the given array is null return empty one.
     * @param <T> Array type value.
     * @param array Nullable array.
     * @return Return the given array if it's not empty otherwise an empty one.
     */
    public static <T> T[] nullToEmpty(final T[] array) {
        return array == null ? ArrayUtils.emptyArray() : array;
    }

    /**
     * @param <T> Array type value.
     * @return Empty array with the given type.
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] emptyArray() {
        return (T[]) ArrayUtils.EMPTY_OBJECT_ARRAY;
    }
}
