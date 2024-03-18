package com.lpenaud.bouts;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ArrayUtilsTest {

	private static final String[] ARRAY = { "abc", "bcd" };

	@Test
	void testCopy() {
		final var actual = new String[ARRAY.length];
		ArrayUtils.copy(ARRAY, actual);
		Assertions.assertArrayEquals(ARRAY, actual);
	}

	@Test
	void testCopySrcPosGt() {
		final Object[] expected = { null, null };
		final var actual = new String[ARRAY.length];
		ArrayUtils.copy(ARRAY, actual, 2);
		Assertions.assertArrayEquals(expected, actual);
	}

	@Test
	void testCopyDestLtSrc() {
		final Object[] expected = { ARRAY[0] };
		final var actual = new String[expected.length];
		ArrayUtils.copy(ARRAY, actual);
		Assertions.assertArrayEquals(expected, actual);

		expected[0] = ARRAY[1];
		ArrayUtils.copy(ARRAY, actual, 1);
		Assertions.assertArrayEquals(expected, actual);
	}

	@Test
	void testFillObject() {
		final var singleton = new Object();
		final Object[] expected = { singleton, singleton, singleton };
		final var actual = new Object[expected.length];
		Assertions.assertEquals(actual,
				ArrayUtils.fill(actual, () -> singleton));
		Assertions.assertArrayEquals(expected, actual);
	}

	@Test
	void testFillInt() {
		final Object[] expected = { 0, 1, 4 };
		final var actual = new Object[expected.length];
		Assertions.assertEquals(actual, ArrayUtils.fill(actual, i -> i * i));
		Assertions.assertArrayEquals(expected, actual);
	}

	@Test
    void testCopyObjectArray() {
        final var source = new Object[] { 1, 2, 3 };
        final var actual = new Object[1];
        // Ingore silencieusement un index trop grand
        ArrayUtils.copy(source, actual, source.length);
        Assertions.assertArrayEquals(new Object[1], actual);
        // Prend la taille la plus petite pour la copie
        ArrayUtils.copy(source, actual, 0);
        Assertions.assertArrayEquals(new Object[] { 1 }, actual);
    }

    @Test
    void testCopyByteArray() {
        final var source = new byte[] { 1, 2, 3 };
        var expected = new byte[1];
        var actual = new byte[1];
        // Ingore silencieusement un index trop grand
        ArrayUtils.copy(source, actual, source.length);
        Assertions.assertArrayEquals(expected, actual);
        // Prend la taille la plus petite pour la copie
        expected = new byte[] { 1 };
        ArrayUtils.copy(source, actual, 0);
        Assertions.assertArrayEquals(expected, actual);
        actual = new byte[source.length + 1];
        expected = new byte[] { 1, 2, 3, 0 };
        ArrayUtils.copy(source, actual);
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    void testCatByteArray() {
        var expected = new byte[0];
        var actual = ArrayUtils.cat();
        // Si aucun tableau est donnée alors retourne un tableau vide.
        Assertions.assertArrayEquals(expected, actual);
        expected = new byte[] { 1 };
        actual = ArrayUtils.cat(expected);
        // Devrait être une instance.
        Assertions.assertNotSame(expected, actual);
        // Devrait avoir les même valeurs.
        Assertions.assertArrayEquals(expected, actual);
        expected = new byte[] { 1, 2, 3 };
        actual = ArrayUtils.cat(new byte[0], new byte[] { 1, 2 }, new byte[] { 3 });
        // Devrait copier tout les tableaux.
        Assertions.assertArrayEquals(expected, actual);
    }
}
