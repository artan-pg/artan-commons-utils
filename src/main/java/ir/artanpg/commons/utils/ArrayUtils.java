package ir.artanpg.commons.utils;

import ir.artanpg.commons.core.tools.jacoco.Generated;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;

/**
 * Provides utility methods for {@link Array} instances.
 *
 * @author Mohammad Yazdian
 */
public abstract class ArrayUtils {

    /**
     * The index value when an element is not found in an array.
     */
    public static final int INDEX_NOT_FOUND = -1;

    /**
     * An empty {@code boolean} array constant.
     */
    public static final boolean[] EMPTY_BOOLEAN_ARRAY = new boolean[0];

    /**
     * An empty {@code byte} array constant.
     */
    public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];

    /**
     * An empty {@code char} array constant.
     */
    public static final char[] EMPTY_CHAR_ARRAY = new char[0];

    /**
     * An empty {@code short} array constant.
     */
    public static final short[] EMPTY_SHORT_ARRAY = new short[0];

    /**
     * An empty {@code int} array constant.
     */
    public static final int[] EMPTY_INT_ARRAY = new int[0];

    /**
     * An empty {@code long} array constant.
     */
    public static final long[] EMPTY_LONG_ARRAY = new long[0];

    /**
     * An empty {@code float} array constant.
     */
    public static final float[] EMPTY_FLOAT_ARRAY = new float[0];

    /**
     * An empty {@code double} array constant.
     */
    public static final double[] EMPTY_DOUBLE_ARRAY = new double[0];

    /**
     * An empty {@code Object} array constant.
     */
    public static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];

    /**
     * An empty {@code String} array constant.
     */
    public static final String[] EMPTY_STRING_ARRAY = new String[0];

    private static final String INPUT_ARRAY_IS_NULL_MESSAGE = "The input array cannot be null";
    private static final String INDEX_ARRAY_IS_INVALID = "Index %d out of bounds for length %d";

    /**
     * Private constructor to prevent instantiation of this utility class.
     *
     * @throws UnsupportedOperationException if an attempt is made to instantiate this class
     */
    @Generated
    private ArrayUtils() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    /**
     * Adds the given {@code boolean} element at the given index in the array.
     *
     * @param source  the array to add the element to
     * @param element the value to add
     * @param index   the position within array to add the new element
     * @return a new array containing all elements from source with the new element inserted
     * @throws IllegalArgumentException  if the source is {@code null}
     * @throws IndexOutOfBoundsException if index is invalid
     */
    public static boolean[] add(boolean[] source, boolean element, int index) {
        if (source == null) {
            throw new IllegalArgumentException(INPUT_ARRAY_IS_NULL_MESSAGE);
        }
        if (index < 0 || index > source.length) {
            throw new IndexOutOfBoundsException(String.format(INDEX_ARRAY_IS_INVALID, index, source.length));
        }

        boolean[] newArray = new boolean[source.length + 1];
        System.arraycopy(source, 0, newArray, 0, index);
        System.arraycopy(source, index, newArray, index + 1, source.length - index);
        newArray[index] = element;

        return newArray;
    }

    /**
     * Adds the given {@code byte} element at the given index in the array.
     *
     * @param source  the array to add the element to
     * @param element the value to add
     * @param index   the position within array to add the new element
     * @return a new array containing all elements from source with the new element inserted
     * @throws IllegalArgumentException  if the source is {@code null}
     * @throws IndexOutOfBoundsException if index is invalid
     */
    public static byte[] add(byte[] source, byte element, int index) {
        if (source == null) throw new IllegalArgumentException(INPUT_ARRAY_IS_NULL_MESSAGE);
        if (index < 0 || index > source.length) {
            throw new IndexOutOfBoundsException(String.format(INDEX_ARRAY_IS_INVALID, index, source.length));
        }

        byte[] newArray = new byte[source.length + 1];
        System.arraycopy(source, 0, newArray, 0, index);
        System.arraycopy(source, index, newArray, index + 1, source.length - index);
        newArray[index] = element;

        return newArray;
    }

    /**
     * Adds the given {@code char} element at the given index in the array.
     *
     * @param source  the array to add the element to
     * @param element the value to add
     * @param index   the position within array to add the new element
     * @return a new array containing all elements from source with the new element inserted
     * @throws IllegalArgumentException  if the source is {@code null}
     * @throws IndexOutOfBoundsException if index is invalid
     */
    public static char[] add(char[] source, char element, int index) {
        if (source == null) throw new IllegalArgumentException(INPUT_ARRAY_IS_NULL_MESSAGE);
        if (index < 0 || index > source.length) {
            throw new IndexOutOfBoundsException(String.format(INDEX_ARRAY_IS_INVALID, index, source.length));
        }

        char[] newArray = new char[source.length + 1];
        System.arraycopy(source, 0, newArray, 0, index);
        System.arraycopy(source, index, newArray, index + 1, source.length - index);
        newArray[index] = element;

        return newArray;
    }

    /**
     * Adds the given {@code short} element at the given index in the array.
     *
     * @param source  the array to add the element to
     * @param element the value to add
     * @param index   the position within array to add the new element
     * @return a new array containing all elements from source with the new element inserted
     * @throws IllegalArgumentException  if the source is {@code null}
     * @throws IndexOutOfBoundsException if index is invalid
     */
    public static short[] add(short[] source, short element, int index) {
        if (source == null) throw new IllegalArgumentException(INPUT_ARRAY_IS_NULL_MESSAGE);
        if (index < 0 || index > source.length) {
            throw new IndexOutOfBoundsException(String.format(INDEX_ARRAY_IS_INVALID, index, source.length));
        }

        short[] newArray = new short[source.length + 1];
        System.arraycopy(source, 0, newArray, 0, index);
        System.arraycopy(source, index, newArray, index + 1, source.length - index);
        newArray[index] = element;

        return newArray;
    }

    /**
     * Adds the given {@code int} element at the given index in the array.
     *
     * @param source  the array to add the element to
     * @param element the value to add
     * @param index   the position within array to add the new element
     * @return a new array containing all elements from source with the new element inserted
     * @throws IllegalArgumentException  if the source is {@code null}
     * @throws IndexOutOfBoundsException if index is invalid
     */
    public static int[] add(int[] source, int element, int index) {
        if (source == null) throw new IllegalArgumentException(INPUT_ARRAY_IS_NULL_MESSAGE);
        if (index < 0 || index > source.length) {
            throw new IndexOutOfBoundsException(String.format(INDEX_ARRAY_IS_INVALID, index, source.length));
        }

        int[] newArray = new int[source.length + 1];
        System.arraycopy(source, 0, newArray, 0, index);
        System.arraycopy(source, index, newArray, index + 1, source.length - index);
        newArray[index] = element;

        return newArray;
    }

    /**
     * Adds the given {@code long} element at the given index in the array.
     *
     * @param source  the array to add the element to
     * @param element the value to add
     * @param index   the position within array to add the new element
     * @return a new array containing all elements from source with the new element inserted
     * @throws IllegalArgumentException  if the source is {@code null}
     * @throws IndexOutOfBoundsException if index is invalid
     */
    public static long[] add(long[] source, long element, int index) {
        if (source == null) throw new IllegalArgumentException(INPUT_ARRAY_IS_NULL_MESSAGE);
        if (index < 0 || index > source.length) {
            throw new IndexOutOfBoundsException(String.format(INDEX_ARRAY_IS_INVALID, index, source.length));
        }

        long[] newArray = new long[source.length + 1];
        System.arraycopy(source, 0, newArray, 0, index);
        System.arraycopy(source, index, newArray, index + 1, source.length - index);
        newArray[index] = element;

        return newArray;
    }

    /**
     * Adds the given {@code float} element at the given index in the array.
     *
     * @param source  the array to add the element to
     * @param element the value to add
     * @param index   the position within array to add the new element
     * @return a new array containing all elements from source with the new element inserted
     * @throws IllegalArgumentException  if the source is {@code null}
     * @throws IndexOutOfBoundsException if index is invalid
     */
    public static float[] add(float[] source, float element, int index) {
        if (source == null) throw new IllegalArgumentException(INPUT_ARRAY_IS_NULL_MESSAGE);
        if (index < 0 || index > source.length) {
            throw new IndexOutOfBoundsException(String.format(INDEX_ARRAY_IS_INVALID, index, source.length));
        }

        float[] newArray = new float[source.length + 1];
        System.arraycopy(source, 0, newArray, 0, index);
        System.arraycopy(source, index, newArray, index + 1, source.length - index);
        newArray[index] = element;

        return newArray;
    }

    /**
     * Adds the given {@code double} element at the given index in the array.
     *
     * @param source  the array to add the element to
     * @param element the value to add
     * @param index   the position within array to add the new element
     * @return a new array containing all elements from source with the new element inserted
     * @throws IllegalArgumentException  if the source is {@code null}
     * @throws IndexOutOfBoundsException if index is invalid
     */
    public static double[] add(double[] source, double element, int index) {
        if (source == null) throw new IllegalArgumentException(INPUT_ARRAY_IS_NULL_MESSAGE);
        if (index < 0 || index > source.length) {
            throw new IndexOutOfBoundsException(String.format(INDEX_ARRAY_IS_INVALID, index, source.length));
        }

        double[] newArray = new double[source.length + 1];
        System.arraycopy(source, 0, newArray, 0, index);
        System.arraycopy(source, index, newArray, index + 1, source.length - index);
        newArray[index] = element;

        return newArray;
    }

    /**
     * Adds the given {@code Object} element at the given index in the array.
     *
     * @param <T>     the type of array elements
     * @param source  the array to add the element to
     * @param element the value to add
     * @param index   the position within array to add the new element
     * @return a new array containing all elements from source with the new element inserted
     * @throws IllegalArgumentException  if the source is {@code null}
     * @throws IndexOutOfBoundsException if index is invalid
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] add(T[] source, T element, int index) {
        if (source == null) throw new IllegalArgumentException(INPUT_ARRAY_IS_NULL_MESSAGE);

        if (index < 0 || index > source.length) {
            throw new IndexOutOfBoundsException(String.format(INDEX_ARRAY_IS_INVALID, index, source.length));
        }

        T[] newArray = (T[]) Array.newInstance(source.getClass().getComponentType(), source.length + 1);
        System.arraycopy(source, 0, newArray, 0, index);
        System.arraycopy(source, index, newArray, index + 1, source.length - index);
        newArray[index] = element;

        return newArray;
    }

    /**
     * Adds the given {@code boolean} element at in the first position in the
     * array.
     *
     * @param source  the array to add the element to
     * @param element the value to add
     * @return a new array containing all elements from source with the new element inserted
     * @throws IllegalArgumentException if the source is {@code null}
     */
    public static boolean[] addFirst(boolean[] source, boolean element) {
        return add(source, element, 0);
    }

    /**
     * Adds the given {@code byte} element at in the first position in the
     * array.
     *
     * @param source  the array to add the element to
     * @param element the value to add
     * @return a new array containing all elements from source with the new element inserted
     * @throws IllegalArgumentException if the source is {@code null}
     */
    public static byte[] addFirst(byte[] source, byte element) {
        return add(source, element, 0);
    }

    /**
     * Adds the given {@code char} element at in the first position in the
     * array.
     *
     * @param source  the array to add the element to
     * @param element the value to add
     * @return a new array containing all elements from source with the new element inserted
     * @throws IllegalArgumentException if the source is {@code null}
     */
    public static char[] addFirst(char[] source, char element) {
        return add(source, element, 0);
    }

    /**
     * Adds the given {@code short} element at in the first position in the
     * array.
     *
     * @param source  the array to add the element to
     * @param element the value to add
     * @return a new array containing all elements from source with the new element inserted
     * @throws IllegalArgumentException if the source is {@code null}
     */
    public static short[] addFirst(short[] source, short element) {
        return add(source, element, 0);
    }

    /**
     * Adds the given {@code int} element at in the first position in the
     * array.
     *
     * @param source  the array to add the element to
     * @param element the value to add
     * @return a new array containing all elements from source with the new element inserted
     * @throws IllegalArgumentException if the source is {@code null}
     */
    public static int[] addFirst(int[] source, int element) {
        return add(source, element, 0);
    }

    /**
     * Adds the given {@code long} element at in the first position in the
     * array.
     *
     * @param source  the array to add the element to
     * @param element the value to add
     * @return a new array containing all elements from source with the new element inserted
     * @throws IllegalArgumentException if the source is {@code null}
     */
    public static long[] addFirst(long[] source, long element) {
        return add(source, element, 0);
    }

    /**
     * Adds the given {@code float} element at in the first position in the
     * array.
     *
     * @param source  the array to add the element to
     * @param element the value to add
     * @return a new array containing all elements from source with the new element inserted
     * @throws IllegalArgumentException if the source is {@code null}
     */
    public static float[] addFirst(float[] source, float element) {
        return add(source, element, 0);
    }

    /**
     * Adds the given {@code double} element at in the first position in the
     * array.
     *
     * @param source  the array to add the element to
     * @param element the value to add
     * @return a new array containing all elements from source with the new element inserted
     * @throws IllegalArgumentException if the source is {@code null}
     */
    public static double[] addFirst(double[] source, double element) {
        return add(source, element, 0);
    }

    /**
     * Adds the given {@code Object} element at in the first position in the
     * array.
     *
     * @param <T>     the type of array elements
     * @param source  the array to add the element to
     * @param element the value to add
     * @return a new array containing all elements from source with the new element inserted
     * @throws IllegalArgumentException if the source is {@code null}
     */
    public static <T> T[] addFirst(T[] source, T element) {
        return add(source, element, 0);
    }

    /**
     * Adds the given {@code boolean} element at in the last position in the
     * array.
     *
     * @param source  the array to add the element to
     * @param element the value to add
     * @return a new array containing all elements from source with the new element inserted
     * @throws IllegalArgumentException if the source is {@code null}
     */
    public static boolean[] addLast(boolean[] source, boolean element) {
        if (source == null) throw new IllegalArgumentException(INPUT_ARRAY_IS_NULL_MESSAGE);
        return add(source, element, source.length);
    }

    /**
     * Adds the given {@code byte} element at in the last position in the
     * array.
     *
     * @param source  the array to add the element to
     * @param element the value to add
     * @return a new array containing all elements from source with the new element inserted
     * @throws IllegalArgumentException if the source is {@code null}
     */
    public static byte[] addLast(byte[] source, byte element) {
        if (source == null) throw new IllegalArgumentException(INPUT_ARRAY_IS_NULL_MESSAGE);
        return add(source, element, source.length);
    }

    /**
     * Adds the given {@code char} element at in the last position in the
     * array.
     *
     * @param source  the array to add the element to
     * @param element the value to add
     * @return a new array containing all elements from source with the new element inserted
     * @throws IllegalArgumentException if the source is {@code null}
     */
    public static char[] addLast(char[] source, char element) {
        if (source == null) throw new IllegalArgumentException(INPUT_ARRAY_IS_NULL_MESSAGE);
        return add(source, element, source.length);
    }

    /**
     * Adds the given {@code short} element at in the last position in the
     * array.
     *
     * @param source  the array to add the element to
     * @param element the value to add
     * @return a new array containing all elements from source with the new element inserted
     * @throws IllegalArgumentException if the source is {@code null}
     */
    public static short[] addLast(short[] source, short element) {
        if (source == null) throw new IllegalArgumentException(INPUT_ARRAY_IS_NULL_MESSAGE);
        return add(source, element, source.length);
    }

    /**
     * Adds the given {@code int} element at in the last position in the
     * array.
     *
     * @param source  the array to add the element to
     * @param element the value to add
     * @return a new array containing all elements from source with the new element inserted
     * @throws IllegalArgumentException if the source is {@code null}
     */
    public static int[] addLast(int[] source, int element) {
        if (source == null) throw new IllegalArgumentException(INPUT_ARRAY_IS_NULL_MESSAGE);
        return add(source, element, source.length);
    }

    /**
     * Adds the given {@code long} element at in the last position in the
     * array.
     *
     * @param source  the array to add the element to
     * @param element the value to add
     * @return a new array containing all elements from source with the new element inserted
     * @throws IllegalArgumentException if the source is {@code null}
     */
    public static long[] addLast(long[] source, long element) {
        if (source == null) throw new IllegalArgumentException(INPUT_ARRAY_IS_NULL_MESSAGE);
        return add(source, element, source.length);
    }

    /**
     * Adds the given {@code float} element at in the last position in the
     * array.
     *
     * @param source  the array to add the element to
     * @param element the value to add
     * @return a new array containing all elements from source with the new element inserted
     * @throws IllegalArgumentException if the source is {@code null}
     */
    public static float[] addLast(float[] source, float element) {
        if (source == null) throw new IllegalArgumentException(INPUT_ARRAY_IS_NULL_MESSAGE);
        return add(source, element, source.length);
    }

    /**
     * Adds the given {@code double} element at in the last position in the
     * array.
     *
     * @param source  the array to add the element to
     * @param element the value to add
     * @return a new array containing all elements from source with the new element inserted
     * @throws IllegalArgumentException if the source is {@code null}
     */
    public static double[] addLast(double[] source, double element) {
        if (source == null) throw new IllegalArgumentException(INPUT_ARRAY_IS_NULL_MESSAGE);
        return add(source, element, source.length);
    }

    /**
     * Adds the given {@code Object} element at in the last position in the
     * array.
     *
     * @param <T>     the type of array elements
     * @param source  the array to add the element to
     * @param element the value to add
     * @return a new array containing all elements from source with the new element inserted
     * @throws IllegalArgumentException if the source is {@code null}
     * @throws ArrayStoreException      if the element type is incompatible with the array component
     */
    public static <T> T[] addLast(T[] source, T element) {
        if (source == null) throw new IllegalArgumentException(INPUT_ARRAY_IS_NULL_MESSAGE);
        return add(source, element, source.length);
    }

    /**
     * Creates and returns a copy of source.
     *
     * @param source the array to clone
     * @return the cloned array or {@code empty} array if the source is {@code null}
     */
    public static boolean[] clone(boolean[] source) {
        return source != null ? Arrays.copyOf(source, source.length) : new boolean[0];
    }

    /**
     * Creates and returns a copy of source.
     *
     * @param source the array to clone
     * @return the cloned array or {@code empty} array if the source is {@code null}
     */
    public static byte[] clone(byte[] source) {
        return source != null ? Arrays.copyOf(source, source.length) : new byte[0];
    }

    /**
     * Creates and returns a copy of source.
     *
     * @param source the array to clone
     * @return the cloned array or {@code empty} array if the source is {@code null}
     */
    public static char[] clone(char[] source) {
        return source != null ? Arrays.copyOf(source, source.length) : new char[0];
    }

    /**
     * Creates and returns a copy of source.
     *
     * @param source the array to clone
     * @return the cloned array or {@code empty} array if the source is {@code null}
     */
    public static short[] clone(short[] source) {
        return source != null ? Arrays.copyOf(source, source.length) : new short[0];
    }

    /**
     * Creates and returns a copy of source.
     *
     * @param source the array to clone
     * @return the cloned array or {@code empty} array if the source is {@code null}
     */
    public static int[] clone(int[] source) {
        return source != null ? Arrays.copyOf(source, source.length) : new int[0];
    }

    /**
     * Creates and returns a copy of source.
     *
     * @param source the array to clone
     * @return the cloned array or {@code empty} array if the source is {@code null}
     */
    public static long[] clone(long[] source) {
        return source != null ? Arrays.copyOf(source, source.length) : new long[0];
    }

    /**
     * Creates and returns a copy of source.
     *
     * @param source the array to clone
     * @return the cloned array or {@code empty} array if the source is {@code null}
     */
    public static float[] clone(float[] source) {
        return source != null ? Arrays.copyOf(source, source.length) : new float[0];
    }

    /**
     * Creates and returns a copy of source.
     *
     * @param source the array to clone
     * @return the cloned array or {@code empty} array if the source is {@code null}
     */
    public static double[] clone(double[] source) {
        return source != null ? Arrays.copyOf(source, source.length) : new double[0];
    }

    /**
     * Creates and returns a copy of source.
     *
     * @param <T>    the type of array elements
     * @param source the array to clone
     * @return a copy of source or {@code null} if the source is {@code null}
     */
    public static <T> T[] clone(T[] source) {
        return source != null ? Arrays.copyOf(source, source.length) : null;
    }

    /**
     * Check whether the given array contains the given element.
     *
     * @param source  the array to check
     * @param element the element to look for
     * @return {@code true}, if found the element, {@code false} otherwise
     */
    public static boolean contains(boolean[] source, boolean element) {
        return indexOf(source, element) != INDEX_NOT_FOUND;
    }

    /**
     * Check whether the given array contains the given element.
     *
     * @param source  the array to check
     * @param element the element to look for
     * @return {@code true}, if found the element, {@code false} otherwise
     */
    public static boolean contains(byte[] source, byte element) {
        return indexOf(source, element) != INDEX_NOT_FOUND;
    }

    /**
     * Check whether the given array contains the given element.
     *
     * @param source  the array to check
     * @param element the element to look for
     * @return {@code true}, if found the element, {@code false} otherwise
     */
    public static boolean contains(char[] source, char element) {
        return indexOf(source, element) != INDEX_NOT_FOUND;
    }

    /**
     * Check whether the given array contains the given element.
     *
     * @param source  the array to check
     * @param element the element to look for
     * @return {@code true}, if found the element, {@code false} otherwise
     */
    public static boolean contains(short[] source, short element) {
        return indexOf(source, element) != INDEX_NOT_FOUND;
    }

    /**
     * Check whether the given array contains the given element.
     *
     * @param source  the array to check
     * @param element the element to look for
     * @return {@code true}, if found the element, {@code false} otherwise
     */
    public static boolean contains(int[] source, int element) {
        return indexOf(source, element) != INDEX_NOT_FOUND;
    }

    /**
     * Check whether the given array contains the given element.
     *
     * @param source  the array to check
     * @param element the element to look for
     * @return {@code true}, if found the element, {@code false} otherwise
     */
    public static boolean contains(long[] source, long element) {
        return indexOf(source, element) != INDEX_NOT_FOUND;
    }

    /**
     * Check whether the given array contains the given element.
     *
     * @param source  the array to check
     * @param element the element to look for
     * @return {@code true}, if found the element, {@code false} otherwise
     */
    public static boolean contains(float[] source, float element) {
        return indexOf(source, element) != INDEX_NOT_FOUND;
    }

    /**
     * Check whether the given array contains the given element.
     *
     * @param source  the array to check
     * @param element the element to look for
     * @return {@code true}, if found the element, {@code false} otherwise
     */
    public static boolean contains(double[] source, double element) {
        return indexOf(source, element) != INDEX_NOT_FOUND;
    }

    /**
     * Check whether the given array contains the given element.
     *
     * @param <T>     the type of array elements
     * @param source  the array to check
     * @param element the element to look for
     * @return {@code true}, if found the element, {@code false} otherwise
     */
    public static <T> boolean contains(T[] source, T element) {
        return indexOf(source, element) != INDEX_NOT_FOUND;
    }

    /**
     * Safely retrieves a {@code boolean} value from an array at the specified
     * index.
     *
     * @param source values the array to get
     * @param index  the index of the element to retrieve
     * @return an {@link Optional} containing the value if the index is valid, an empty {@link Optional} otherwise.
     * @see Optional
     */
    public static Optional<Boolean> get(boolean[] source, int index) {
        return source != null && index >= 0 && source.length > index ? Optional.of(source[index]) : Optional.empty();
    }

    /**
     * Safely retrieves a {@code byte} value from an array at the specified
     * index.
     *
     * @param source values the array to get
     * @param index  the index of the element to retrieve
     * @return an {@link Optional} containing the value if the index is valid, an empty {@link Optional} otherwise.
     * @see Optional
     */
    public static Optional<Byte> get(byte[] source, int index) {
        return source != null && index >= 0 && source.length > index ? Optional.of(source[index]) : Optional.empty();
    }

    /**
     * Safely retrieves a {@code char} value from an array at the specified
     * index.
     *
     * @param source values the array to get
     * @param index  the index of the element to retrieve
     * @return an {@link Optional} containing the value if the index is valid, an empty {@link Optional} otherwise.
     * @see Optional
     */
    public static Optional<Character> get(char[] source, int index) {
        return source != null && index >= 0 && source.length > index ? Optional.of(source[index]) : Optional.empty();
    }

    /**
     * Safely retrieves a {@code short} value from an array at the specified
     * index.
     *
     * @param source values the array to get
     * @param index  the index of the element to retrieve
     * @return an {@link Optional} containing the value if the index is valid, an empty {@link Optional} otherwise.
     * @see Optional
     */
    public static Optional<Short> get(short[] source, int index) {
        return source != null && index >= 0 && source.length > index ? Optional.of(source[index]) : Optional.empty();
    }

    /**
     * Safely retrieves a {@code int} value from an array at the specified
     * index.
     *
     * @param source values the array to get
     * @param index  the index of the element to retrieve
     * @return an {@link Optional} containing the value if the index is valid, an empty {@link Optional} otherwise.
     * @see Optional
     */
    public static Optional<Integer> get(int[] source, int index) {
        return source != null && index >= 0 && source.length > index ? Optional.of(source[index]) : Optional.empty();
    }

    /**
     * Safely retrieves a {@code long} value from an array at the specified
     * index.
     *
     * @param source values the array to get
     * @param index  the index of the element to retrieve
     * @return an {@link Optional} containing the value if the index is valid, an empty {@link Optional} otherwise.
     * @see Optional
     */
    public static Optional<Long> get(long[] source, int index) {
        return source != null && index >= 0 && source.length > index ? Optional.of(source[index]) : Optional.empty();
    }

    /**
     * Safely retrieves a {@code float} value from an array at the specified
     * index.
     *
     * @param source values the array to get
     * @param index  the index of the element to retrieve
     * @return an {@link Optional} containing the value if the index is valid, an empty {@link Optional} otherwise.
     * @see Optional
     */
    public static Optional<Float> get(float[] source, int index) {
        return source != null && index >= 0 && source.length > index ? Optional.of(source[index]) : Optional.empty();
    }

    /**
     * Safely retrieves a {@code double} value from an array at the specified
     * index.
     *
     * @param source values the array to get
     * @param index  the index of the element to retrieve
     * @return an {@link Optional} containing the value if the index is valid, an empty {@link Optional} otherwise.
     * @see Optional
     */
    public static Optional<Double> get(double[] source, int index) {
        return source != null && index >= 0 && source.length > index ? Optional.of(source[index]) : Optional.empty();
    }

    /**
     * Safely retrieves a {@code Object} value from an array at the specified
     * index.
     *
     * @param <T>    the type of array elements
     * @param source values the array to get
     * @param index  the index of the element to retrieve
     * @return an {@link Optional} containing the value if the index is valid, an empty {@link Optional} otherwise.
     * @see Optional
     */
    public static <T> Optional<T> get(T[] source, int index) {
        return source != null && index >= 0 && source.length > index ? Optional.of(source[index]) : Optional.empty();
    }

    /**
     * Returns the component type of the specified array.
     *
     * <p>This method retrieves the {@code Class} object representing the type
     * of elements in the given array.
     *
     * <p>If the input array is {@code null}, this method returns {@code null}.
     *
     * @param <T>    the type of array elements
     * @param source the array whose component type is to be retrieved
     * @return the {@code Class} object representing the component type of the array, or {@code null}
     * if the input array is {@code null}
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> getComponentType(T[] source) {
        return (source == null) ? null : (Class<T>) source.getClass().getComponentType();
    }

    /**
     * Checks if the specified {@code boolean} array is not {@code null} and
     * has a length greater than zero.
     *
     * @param source the boolean array to check
     * @return {@code true}, if the array is not {@code null} and has at least one element, {@code false} otherwise
     */
    public static boolean hasLength(boolean[] source) {
        return source != null && source.length > 0;
    }

    /**
     * Checks if the specified {@code byte} array is not {@code null} and has a
     * length greater than zero.
     *
     * @param source the byte array to check
     * @return {@code true}, if the array is not {@code null} and has at least one element, {@code false} otherwise
     */
    public static boolean hasLength(byte[] source) {
        return source != null && source.length > 0;
    }

    /**
     * Checks if the specified {@code char} array is not {@code null} and has a
     * length greater than zero.
     *
     * @param source the char array to check
     * @return {@code true}, if the array is not {@code null} and has at least one element, {@code false} otherwise
     */
    public static boolean hasLength(char[] source) {
        return source != null && source.length > 0;
    }

    /**
     * Checks if the specified {@code short} array is not {@code null} and has
     * a length greater than zero.
     *
     * @param source the short array to check
     * @return {@code true}, if the array is not {@code null} and has at least one element, {@code false} otherwise
     */
    public static boolean hasLength(short[] source) {
        return source != null && source.length > 0;
    }

    /**
     * Checks if the specified {@code int} array is not {@code null} and has a
     * length greater than zero.
     *
     * @param source the int array to check
     * @return {@code true}, if the array is not {@code null} and has at least one element, {@code false} otherwise
     */
    public static boolean hasLength(int[] source) {
        return source != null && source.length > 0;
    }

    /**
     * Checks if the specified {@code long} array is not {@code null} and has a
     * length greater than zero.
     *
     * @param source the long array to check
     * @return {@code true}, if the array is not {@code null} and has at least one element, {@code false} otherwise
     */
    public static boolean hasLength(long[] source) {
        return source != null && source.length > 0;
    }

    /**
     * Checks if the specified {@code float} array is not {@code null} and has
     * a length greater than zero.
     *
     * @param source the float array to check
     * @return {@code true}, if the array is not {@code null} and has at least one element, {@code false} otherwise
     */
    public static boolean hasLength(float[] source) {
        return source != null && source.length > 0;
    }

    /**
     * Checks if the specified {@code double} array is not {@code null} and has
     * a length greater than zero.
     *
     * @param source the double array to check
     * @return {@code true}, if the array is not {@code null} and has at least one element, {@code false} otherwise
     */
    public static boolean hasLength(double[] source) {
        return source != null && source.length > 0;
    }

    /**
     * Checks if the specified {@code generic} array is not {@code null} and
     * has a length greater than zero.
     *
     * @param <T>    the type of the array elements
     * @param source the array to check
     * @return {@code true}, if the array is not {@code null} and has at least one element, {@code false} otherwise
     */
    public static <T> boolean hasLength(T[] source) {
        return source != null && source.length > 0;
    }

    /**
     * Finds the index of the given value in the array.
     *
     * @param source  the array to search through for the object
     * @param element the value to find
     * @return index of the value found in the array, {@code -1} otherwise
     * @see #indexOf(boolean[], boolean, int)
     */
    public static int indexOf(boolean[] source, boolean element) {
        return indexOf(source, element, 0);
    }

    /**
     * Finds the index of the given value in the array starting at the given
     * index.
     *
     * @param source    the array to search through for the object
     * @param element   the value to find
     * @param fromIndex the index to start searching at
     * @return index of the value found in the array, {@code -1} otherwise
     */
    @SuppressWarnings("DuplicatedCode")
    public static int indexOf(boolean[] source, boolean element, int fromIndex) {
        if (!hasLength(source) || fromIndex < 0 || fromIndex > source.length) return INDEX_NOT_FOUND;

        for (int i = fromIndex; i < source.length; i++) {
            if (source[i] == element) return i;
        }

        return INDEX_NOT_FOUND;
    }

    /**
     * Finds the index of the given value in the array.
     *
     * @param source  the array to search through for the object
     * @param element the value to find
     * @return index of the value found in the array, {@code -1} otherwise
     * @see #indexOf(byte[], byte, int)
     */
    public static int indexOf(byte[] source, byte element) {
        return indexOf(source, element, 0);
    }

    /**
     * Finds the index of the given value in the array starting at the given
     * index.
     *
     * @param source    the array to search through for the object
     * @param element   the value to find
     * @param fromIndex the index to start searching at
     * @return index of the value found in the array, {@code -1} otherwise
     */
    @SuppressWarnings("DuplicatedCode")
    public static int indexOf(byte[] source, byte element, int fromIndex) {
        if (!hasLength(source) || fromIndex < 0 || fromIndex > source.length) return INDEX_NOT_FOUND;

        for (int i = fromIndex; i < source.length; i++) {
            if (source[i] == element) return i;
        }

        return INDEX_NOT_FOUND;
    }

    /**
     * Finds the index of the given value in the array.
     *
     * @param source  the array to search through for the object
     * @param element the value to find
     * @return index of the value found in the array, {@code -1} otherwise
     * @see #indexOf(char[], char, int)
     */
    public static int indexOf(char[] source, char element) {
        return indexOf(source, element, 0);
    }

    /**
     * Finds the index of the given value in the array starting at the given
     * index.
     *
     * @param source    the array to search through for the object
     * @param element   the value to find
     * @param fromIndex the index to start searching at
     * @return index of the value found in the array, {@code -1} otherwise
     */
    @SuppressWarnings("DuplicatedCode")
    public static int indexOf(char[] source, char element, int fromIndex) {
        if (!hasLength(source) || fromIndex < 0 || fromIndex > source.length) return INDEX_NOT_FOUND;

        for (int i = fromIndex; i < source.length; i++) {
            if (source[i] == element) return i;
        }

        return INDEX_NOT_FOUND;
    }

    /**
     * Finds the index of the given value in the array.
     *
     * @param source  the array to search through for the object
     * @param element the value to find
     * @return index of the value found in the array, {@code -1} otherwise
     * @see #indexOf(short[], short, int)
     */
    public static int indexOf(short[] source, short element) {
        return indexOf(source, element, 0);
    }

    /**
     * Finds the index of the given value in the array starting at the given
     * index.
     *
     * @param source    the array to search through for the object
     * @param element   the value to find
     * @param fromIndex the index to start searching at
     * @return index of the value found in the array, {@code -1} otherwise
     */
    @SuppressWarnings("DuplicatedCode")
    public static int indexOf(short[] source, short element, int fromIndex) {
        if (!hasLength(source) || fromIndex < 0 || fromIndex > source.length) return INDEX_NOT_FOUND;

        for (int i = fromIndex; i < source.length; i++) {
            if (source[i] == element) return i;
        }

        return INDEX_NOT_FOUND;
    }

    /**
     * Finds the index of the given value in the array.
     *
     * @param source  the array to search through for the object
     * @param element the value to find
     * @return index of the value found in the array, {@code -1} otherwise
     * @see #indexOf(int[], int, int)
     */
    public static int indexOf(int[] source, int element) {
        return indexOf(source, element, 0);
    }

    /**
     * Finds the index of the given value in the array starting at the given
     * index.
     *
     * @param source    the array to search through for the object
     * @param element   the value to find
     * @param fromIndex the index to start searching at
     * @return index of the value found in the array, {@code -1} otherwise
     */
    @SuppressWarnings("DuplicatedCode")
    public static int indexOf(int[] source, int element, int fromIndex) {
        if (!hasLength(source) || fromIndex < 0 || fromIndex > source.length) return INDEX_NOT_FOUND;

        for (int i = fromIndex; i < source.length; i++) {
            if (source[i] == element) return i;
        }

        return INDEX_NOT_FOUND;
    }

    /**
     * Finds the index of the given value in the array.
     *
     * @param source  the array to search through for the object
     * @param element the value to find
     * @return index of the value found in the array, {@code -1} otherwise
     * @see #indexOf(long[], long, int)
     */
    public static int indexOf(long[] source, long element) {
        return indexOf(source, element, 0);
    }

    /**
     * Finds the index of the given value in the array starting at the given
     * index.
     *
     * @param source    the array to search through for the object
     * @param element   the value to find
     * @param fromIndex the index to start searching at
     * @return index of the value found in the array, {@code -1} otherwise
     */
    @SuppressWarnings("DuplicatedCode")
    public static int indexOf(long[] source, long element, int fromIndex) {
        if (!hasLength(source) || fromIndex < 0 || fromIndex > source.length) return INDEX_NOT_FOUND;

        for (int i = fromIndex; i < source.length; i++) {
            if (source[i] == element) return i;
        }

        return INDEX_NOT_FOUND;
    }

    /**
     * Finds the index of the given value in the array.
     *
     * @param source  the array to search through for the object
     * @param element the value to find
     * @return index of the value found in the array, {@code -1} otherwise
     * @see #indexOf(float[], float, int)
     */
    public static int indexOf(float[] source, float element) {
        return indexOf(source, element, 0);
    }

    /**
     * Finds the index of the given value in the array starting at the given
     * index.
     *
     * @param source    the array to search through for the object
     * @param element   the value to find
     * @param fromIndex the index to start searching at
     * @return index of the value found in the array, {@code -1} otherwise
     */
    @SuppressWarnings("DuplicatedCode")
    public static int indexOf(float[] source, float element, int fromIndex) {
        if (!hasLength(source) || fromIndex < 0 || fromIndex > source.length) return INDEX_NOT_FOUND;

        for (int i = fromIndex; i < source.length; i++) {
            if (source[i] == element) return i;
        }

        return INDEX_NOT_FOUND;
    }

    /**
     * Finds the index of the given value in the array.
     *
     * @param source  the array to search through for the object
     * @param element the value to find
     * @return index of the value found in the array, {@code -1} otherwise
     * @see #indexOf(double[], double, int)
     */
    public static int indexOf(double[] source, double element) {
        return indexOf(source, element, 0);
    }

    /**
     * Finds the index of the given value in the array starting at the given
     * index.
     *
     * @param source    the array to search through for the object
     * @param element   the value to find
     * @param fromIndex the index to start searching at
     * @return index of the value found in the array, {@code -1} otherwise
     */
    @SuppressWarnings("DuplicatedCode")
    public static int indexOf(double[] source, double element, int fromIndex) {
        if (!hasLength(source) || fromIndex < 0 || fromIndex > source.length) return INDEX_NOT_FOUND;

        for (int i = fromIndex; i < source.length; i++) {
            if (source[i] == element) return i;
        }

        return INDEX_NOT_FOUND;
    }

    /**
     * Finds the index of the given value in the array.
     *
     * @param <T>     the type of array elements
     * @param source  the array to search through for the object
     * @param element the value to find
     * @return index of the value found in the array, {@code -1} otherwise
     * @see #indexOf(Object[], Object, int)
     */
    public static <T> int indexOf(T[] source, T element) {
        return indexOf(source, element, 0);
    }

    /**
     * Finds the index of the given value in the array starting at the given
     * index.
     *
     * @param <T>       the type of array elements
     * @param source    the array to search through for the object
     * @param element   the value to find
     * @param fromIndex the index to start searching at
     * @return index of the value found in the array, {@code -1} otherwise
     */
    public static <T> int indexOf(T[] source, T element, int fromIndex) {
        if (!hasLength(source) || fromIndex < 0 || fromIndex > source.length) return INDEX_NOT_FOUND;

        if (element == null) {
            for (int i = fromIndex; i < source.length; i++) {
                if (source[i] == null) {
                    return i;
                }
            }
        } else if (source.getClass().getComponentType().isInstance(element)) {
            for (int i = fromIndex; i < source.length; i++) {
                if (element == source[i]) {
                    return i;
                }
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * Finds the last index of the given value in the array.
     *
     * @param source  the array to search through for the object
     * @param element the value to find
     * @return the last index of the value found in the array, {@code -1} otherwise
     * @see #lastIndexOf(boolean[], boolean, int)
     */
    public static int lastIndexOf(boolean[] source, boolean element) {
        if (source == null) throw new IllegalArgumentException(INPUT_ARRAY_IS_NULL_MESSAGE);
        return lastIndexOf(source, element, source.length - 1);
    }

    /**
     * Finds the last index of the given value in the array starting at the
     * given index.
     *
     * @param source    the array to search through for the object
     * @param element   the value to find
     * @param fromIndex the index to start searching at
     * @return the last index of the value found in the array, {@code -1} otherwise
     */
    @SuppressWarnings("DuplicatedCode")
    public static int lastIndexOf(boolean[] source, boolean element, int fromIndex) {
        if (!hasLength(source) || fromIndex < 0 || fromIndex >= source.length) return INDEX_NOT_FOUND;

        for (int i = fromIndex; i >= 0; i--) {
            if (element == source[i]) return i;
        }

        return INDEX_NOT_FOUND;
    }

    /**
     * Finds the last index of the given value in the array.
     *
     * @param source  the array to search through for the object
     * @param element the value to find
     * @return the last index of the value found in the array, {@code -1} otherwise
     * @see #lastIndexOf(byte[], byte, int)
     */
    public static int lastIndexOf(byte[] source, byte element) {
        if (source == null) throw new IllegalArgumentException(INPUT_ARRAY_IS_NULL_MESSAGE);
        return lastIndexOf(source, element, source.length - 1);
    }

    /**
     * Finds the last index of the given value in the array starting at the
     * given index.
     *
     * @param source    the array to search through for the object
     * @param element   the value to find
     * @param fromIndex the index to start searching at
     * @return the last index of the value found in the array, {@code -1} otherwise
     */
    @SuppressWarnings("DuplicatedCode")
    public static int lastIndexOf(byte[] source, byte element, int fromIndex) {
        if (!hasLength(source) || fromIndex < 0 || fromIndex >= source.length) return INDEX_NOT_FOUND;

        for (int i = fromIndex; i >= 0; i--) {
            if (element == source[i]) return i;
        }

        return INDEX_NOT_FOUND;
    }

    /**
     * Finds the last index of the given value in the array.
     *
     * @param source  the array to search through for the object
     * @param element the value to find
     * @return the last index of the value found in the array, {@code -1} otherwise
     * @see #lastIndexOf(char[], char, int)
     */
    public static int lastIndexOf(char[] source, char element) {
        if (source == null) throw new IllegalArgumentException(INPUT_ARRAY_IS_NULL_MESSAGE);
        return lastIndexOf(source, element, source.length - 1);
    }

    /**
     * Finds the last index of the given value in the array starting at the
     * given index.
     *
     * @param source    the array to search through for the object
     * @param element   the value to find
     * @param fromIndex the index to start searching at
     * @return the last index of the value found in the array, {@code -1} otherwise
     */
    @SuppressWarnings("DuplicatedCode")
    public static int lastIndexOf(char[] source, char element, int fromIndex) {
        if (!hasLength(source) || fromIndex < 0 || fromIndex >= source.length) return INDEX_NOT_FOUND;

        for (int i = fromIndex; i >= 0; i--) {
            if (element == source[i]) return i;
        }

        return INDEX_NOT_FOUND;
    }

    /**
     * Finds the last index of the given value in the array.
     *
     * @param source  the array to search through for the object
     * @param element the value to find
     * @return the last index of the value found in the array, {@code -1} otherwise
     * @see #lastIndexOf(short[], short, int)
     */
    public static int lastIndexOf(short[] source, short element) {
        if (source == null) throw new IllegalArgumentException(INPUT_ARRAY_IS_NULL_MESSAGE);
        return lastIndexOf(source, element, source.length - 1);
    }

    /**
     * Finds the last index of the given value in the array starting at the
     * given index.
     *
     * @param source    the array to search through for the object
     * @param element   the value to find
     * @param fromIndex the index to start searching at
     * @return the last index of the value found in the array, {@code -1} otherwise
     */
    @SuppressWarnings("DuplicatedCode")
    public static int lastIndexOf(short[] source, short element, int fromIndex) {
        if (!hasLength(source) || fromIndex < 0 || fromIndex >= source.length) return INDEX_NOT_FOUND;

        for (int i = fromIndex; i >= 0; i--) {
            if (element == source[i]) return i;
        }

        return INDEX_NOT_FOUND;
    }

    /**
     * Finds the last index of the given value in the array.
     *
     * @param source  the array to search through for the object
     * @param element the value to find
     * @return the last index of the value found in the array, {@code -1} otherwise
     * @see #lastIndexOf(int[], int, int)
     */
    public static int lastIndexOf(int[] source, int element) {
        if (source == null) throw new IllegalArgumentException(INPUT_ARRAY_IS_NULL_MESSAGE);
        return lastIndexOf(source, element, source.length - 1);
    }

    /**
     * Finds the last index of the given value in the array starting at the
     * given index.
     *
     * @param source    the array to search through for the object
     * @param element   the value to find
     * @param fromIndex the index to start searching at
     * @return the last index of the value found in the array, {@code -1} otherwise
     */
    @SuppressWarnings("DuplicatedCode")
    public static int lastIndexOf(int[] source, int element, int fromIndex) {
        if (!hasLength(source) || fromIndex < 0 || fromIndex >= source.length) return INDEX_NOT_FOUND;

        for (int i = fromIndex; i >= 0; i--) {
            if (element == source[i]) return i;
        }

        return INDEX_NOT_FOUND;
    }

    /**
     * Finds the last index of the given value in the array.
     *
     * @param source  the array to search through for the object
     * @param element the value to find
     * @return the last index of the value found in the array, {@code -1} otherwise
     * @see #lastIndexOf(long[], long, int)
     */
    public static int lastIndexOf(long[] source, long element) {
        if (source == null) throw new IllegalArgumentException(INPUT_ARRAY_IS_NULL_MESSAGE);
        return lastIndexOf(source, element, source.length - 1);
    }

    /**
     * Finds the last index of the given value in the array starting at the
     * given index.
     *
     * @param source    the array to search through for the object
     * @param element   the value to find
     * @param fromIndex the index to start searching at
     * @return the last index of the value found in the array, {@code -1} otherwise
     */
    @SuppressWarnings("DuplicatedCode")
    public static int lastIndexOf(long[] source, long element, int fromIndex) {
        if (!hasLength(source) || fromIndex < 0 || fromIndex >= source.length) return INDEX_NOT_FOUND;

        for (int i = fromIndex; i >= 0; i--) {
            if (element == source[i]) return i;
        }

        return INDEX_NOT_FOUND;
    }

    /**
     * Finds the last index of the given value in the array.
     *
     * @param source  the array to search through for the object
     * @param element the value to find
     * @return the last index of the value found in the array, {@code -1} otherwise
     * @see #lastIndexOf(float[], float, int)
     */
    public static int lastIndexOf(float[] source, float element) {
        if (source == null) throw new IllegalArgumentException(INPUT_ARRAY_IS_NULL_MESSAGE);
        return lastIndexOf(source, element, source.length - 1);
    }

    /**
     * Finds the last index of the given value in the array starting at the
     * given index.
     *
     * @param source    the array to search through for the object
     * @param element   the value to find
     * @param fromIndex the index to start searching at
     * @return the last index of the value found in the array, {@code -1} otherwise
     */
    @SuppressWarnings("DuplicatedCode")
    public static int lastIndexOf(float[] source, float element, int fromIndex) {
        if (!hasLength(source) || fromIndex < 0 || fromIndex >= source.length) return INDEX_NOT_FOUND;

        for (int i = fromIndex; i >= 0; i--) {
            if (element == source[i]) return i;
        }

        return INDEX_NOT_FOUND;
    }

    /**
     * Finds the last index of the given value in the array.
     *
     * @param source  the array to search through for the object
     * @param element the value to find
     * @return the last index of the value found in the array, {@code -1} otherwise
     * @see #lastIndexOf(double[], double, int)
     */
    public static int lastIndexOf(double[] source, double element) {
        if (source == null) throw new IllegalArgumentException(INPUT_ARRAY_IS_NULL_MESSAGE);
        return lastIndexOf(source, element, source.length - 1);
    }

    /**
     * Finds the last index of the given value in the array starting at the
     * given index.
     *
     * @param source    the array to search through for the object
     * @param element   the value to find
     * @param fromIndex the index to start searching at
     * @return the last index of the value found in the array, {@code -1} otherwise
     */
    @SuppressWarnings("DuplicatedCode")
    public static int lastIndexOf(double[] source, double element, int fromIndex) {
        if (!hasLength(source) || fromIndex < 0 || fromIndex >= source.length) return INDEX_NOT_FOUND;

        for (int i = fromIndex; i >= 0; i--) {
            if (element == source[i]) return i;
        }

        return INDEX_NOT_FOUND;
    }

    /**
     * Finds the last index of the given value in the array.
     *
     * @param <T>     the type of array elements
     * @param source  the array to search through for the object
     * @param element the value to find
     * @return the last index of the value found in the array, {@code -1} otherwise
     * @see #lastIndexOf(double[], double, int)
     */
    public static <T> int lastIndexOf(T[] source, T element) {
        if (source == null) throw new IllegalArgumentException(INPUT_ARRAY_IS_NULL_MESSAGE);
        return lastIndexOf(source, element, source.length - 1);
    }

    /**
     * Finds the last index of the given value in the array starting at the
     * given index.
     *
     * @param <T>       the type of array elements
     * @param source    the array to search through for the object
     * @param element   the value to find
     * @param fromIndex the index to start searching at
     * @return the last index of the value found in the array, {@code -1} otherwise
     */
    @SuppressWarnings("DuplicatedCode")
    public static <T> int lastIndexOf(T[] source, T element, int fromIndex) {
        if (!hasLength(source) || fromIndex < 0 || fromIndex >= source.length) return INDEX_NOT_FOUND;

        if (element == null) {
            for (int i = fromIndex; i >= 0; i--) {
                if (source[i] == null) return i;
            }
        } else if (source.getClass().getComponentType().isInstance(element)) {
            for (int i = fromIndex; i >= 0; i--) {
                if (element == source[i]) return i;
            }
        }

        return INDEX_NOT_FOUND;
    }

    /**
     * Merges all elements of the given {@code boolean} arrays into a new
     * {@code boolean} array.
     *
     * @param array1 the first array whose elements are added to the new array
     * @param array2 the second array whose elements are added to the new array
     * @return the new merged {@code boolean} array
     * @see #hasLength(boolean[])
     * @see #clone(boolean[])
     */
    public static boolean[] merge(boolean[] array1, boolean[] array2) {
        if (!hasLength(array1)) return clone(array2);
        if (!hasLength(array2)) return clone(array1);

        boolean[] joinedArray = new boolean[array1.length + array2.length];
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);

        return joinedArray;
    }

    /**
     * Merges all elements of the given {@code byte} arrays into a new
     * {@code byte} array.
     *
     * @param array1 the first array whose elements are added to the new array
     * @param array2 the second array whose elements are added to the new array
     * @return the new merged {@code byte} array
     * @see #hasLength(byte[])
     * @see #clone(byte[])
     */
    public static byte[] merge(byte[] array1, byte[] array2) {
        if (!hasLength(array1)) return clone(array2);
        if (!hasLength(array2)) return clone(array1);

        byte[] joinedArray = new byte[array1.length + array2.length];
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);

        return joinedArray;
    }

    /**
     * Merges all elements of the given {@code char} arrays into a new
     * {@code char} array.
     *
     * @param array1 the first array whose elements are added to the new array
     * @param array2 the second array whose elements are added to the new array
     * @return the new merged {@code char} array
     * @see #hasLength(char[])
     * @see #clone(char[])
     */
    public static char[] merge(char[] array1, char[] array2) {
        if (!hasLength(array1)) return clone(array2);
        if (!hasLength(array2)) return clone(array1);

        char[] joinedArray = new char[array1.length + array2.length];
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);

        return joinedArray;
    }

    /**
     * Merges all elements of the given {@code short} arrays into a new
     * {@code short} array.
     *
     * @param array1 the first array whose elements are added to the new array
     * @param array2 the second array whose elements are added to the new array
     * @return the new merged {@code short} array
     * @see #hasLength(short[])
     * @see #clone(short[])
     */
    public static short[] merge(short[] array1, short[] array2) {
        if (!hasLength(array1)) return clone(array2);
        if (!hasLength(array2)) return clone(array1);

        short[] joinedArray = new short[array1.length + array2.length];
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);

        return joinedArray;
    }

    /**
     * Merges all elements of the given {@code int} arrays into a new
     * {@code int} array.
     *
     * @param array1 the first array whose elements are added to the new array
     * @param array2 the second array whose elements are added to the new array
     * @return the new merged {@code int} array
     * @see #hasLength(int[])
     * @see #clone(int[])
     */
    public static int[] merge(int[] array1, int[] array2) {
        if (!hasLength(array1)) return clone(array2);
        if (!hasLength(array2)) return clone(array1);

        int[] joinedArray = new int[array1.length + array2.length];
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);

        return joinedArray;
    }

    /**
     * Merges all elements of the given {@code long} arrays into a new
     * {@code long} array.
     *
     * @param array1 the first array whose elements are added to the new array
     * @param array2 the second array whose elements are added to the new array
     * @return the new merged {@code long} array
     * @see #hasLength(long[])
     * @see #clone(long[])
     */
    public static long[] merge(long[] array1, long[] array2) {
        if (!hasLength(array1)) return clone(array2);
        if (!hasLength(array2)) return clone(array1);

        long[] joinedArray = new long[array1.length + array2.length];
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);

        return joinedArray;
    }

    /**
     * Merges all elements of the given {@code float} arrays into a new
     * {@code float} array.
     *
     * @param array1 the first array whose elements are added to the new array
     * @param array2 the second array whose elements are added to the new array
     * @return the new merged {@code float} array
     * @see #hasLength(float[])
     * @see #clone(float[])
     */
    public static float[] merge(float[] array1, float[] array2) {
        if (!hasLength(array1)) return clone(array2);
        if (!hasLength(array2)) return clone(array1);

        float[] joinedArray = new float[array1.length + array2.length];
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);

        return joinedArray;
    }

    /**
     * Merges all elements of the given {@code double} arrays into a new
     * {@code double} array.
     *
     * @param array1 the first array whose elements are added to the new array
     * @param array2 the second array whose elements are added to the new array
     * @return the new merged {@code double} array
     * @see #hasLength(double[])
     * @see #clone(double[])
     */
    public static double[] merge(double[] array1, double[] array2) {
        if (!hasLength(array1)) return clone(array2);
        if (!hasLength(array2)) return clone(array1);

        double[] joinedArray = new double[array1.length + array2.length];
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);

        return joinedArray;
    }

    /**
     * Merges all elements of the given {@code Object} arrays into a new
     * {@code Object} array.
     *
     * @param <T>    the type of array elements
     * @param array1 the first array whose elements are added to the new array
     * @param array2 the second array whose elements are added to the new array
     * @return the new merged {@code Object} array
     * @see #hasLength(Object[])
     * @see #clone(Object[])
     */
    public static <T> T[] merge(T[] array1, T[] array2) {
        if (!hasLength(array1)) return clone(array2);
        if (!hasLength(array2)) return clone(array1);

        T[] joinedArray = Arrays.copyOf(array1, array1.length + array2.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);

        return joinedArray;
    }

    /**
     * Removes the given {@code boolean} element at the given index in the
     * array.
     *
     * @param source the array to remove the element from
     * @param index  the position of the element to be removed
     * @return new array containing the existing elements except the element at the specified position
     * @throws IllegalArgumentException  if the source is {@code null}
     * @throws IndexOutOfBoundsException if index is invalid
     */
    public static boolean[] remove(boolean[] source, int index) {
        if (source == null) throw new IllegalArgumentException(INPUT_ARRAY_IS_NULL_MESSAGE);

        if (index < 0 || index >= source.length) {
            throw new IndexOutOfBoundsException(String.format(INDEX_ARRAY_IS_INVALID, index, source.length));
        }

        boolean[] newArray = new boolean[source.length - 1];
        System.arraycopy(source, 0, newArray, 0, index);
        System.arraycopy(source, index + 1, newArray, index, source.length - index - 1);
        return newArray;
    }

    /**
     * Removes the given {@code byte} element at the given index in the array.
     *
     * @param source the array to remove the element from
     * @param index  the position of the element to be removed
     * @return new array containing the existing elements except the element at the specified position
     * @throws IllegalArgumentException  if the source is {@code null}
     * @throws IndexOutOfBoundsException if index is invalid
     */
    public static byte[] remove(byte[] source, int index) {
        if (source == null) throw new IllegalArgumentException(INPUT_ARRAY_IS_NULL_MESSAGE);

        if (index < 0 || index >= source.length) {
            throw new IndexOutOfBoundsException(String.format(INDEX_ARRAY_IS_INVALID, index, source.length));
        }

        byte[] newArray = new byte[source.length - 1];
        System.arraycopy(source, 0, newArray, 0, index);
        System.arraycopy(source, index + 1, newArray, index, source.length - index - 1);
        return newArray;
    }

    /**
     * Removes the given {@code char} element at the given index in the array.
     *
     * @param source the array to remove the element from
     * @param index  the position of the element to be removed
     * @return new array containing the existing elements except the element at the specified position
     * @throws IllegalArgumentException  if the source is {@code null}
     * @throws IndexOutOfBoundsException if index is invalid
     */
    public static char[] remove(char[] source, int index) {
        if (source == null) throw new IllegalArgumentException(INPUT_ARRAY_IS_NULL_MESSAGE);

        if (index < 0 || index >= source.length) {
            throw new IndexOutOfBoundsException(String.format(INDEX_ARRAY_IS_INVALID, index, source.length));
        }

        char[] newArray = new char[source.length - 1];
        System.arraycopy(source, 0, newArray, 0, index);
        System.arraycopy(source, index + 1, newArray, index, source.length - index - 1);
        return newArray;
    }

    /**
     * Removes the given {@code short} element at the given index in the array.
     *
     * @param source the array to remove the element from
     * @param index  the position of the element to be removed
     * @return new array containing the existing elements except the element at the specified position
     * @throws IllegalArgumentException  if the source is {@code null}
     * @throws IndexOutOfBoundsException if index is invalid
     */
    public static short[] remove(short[] source, int index) {
        if (source == null) throw new IllegalArgumentException(INPUT_ARRAY_IS_NULL_MESSAGE);

        if (index < 0 || index >= source.length) {
            throw new IndexOutOfBoundsException(String.format(INDEX_ARRAY_IS_INVALID, index, source.length));
        }

        short[] newArray = new short[source.length - 1];
        System.arraycopy(source, 0, newArray, 0, index);
        System.arraycopy(source, index + 1, newArray, index, source.length - index - 1);
        return newArray;
    }

    /**
     * Removes the given {@code int} element at the given index in the array.
     *
     * @param source the array to remove the element from
     * @param index  the position of the element to be removed
     * @return new array containing the existing elements except the element at the specified position
     * @throws IllegalArgumentException  if the source is {@code null}
     * @throws IndexOutOfBoundsException if index is invalid
     */
    public static int[] remove(int[] source, int index) {
        if (source == null) throw new IllegalArgumentException(INPUT_ARRAY_IS_NULL_MESSAGE);

        if (index < 0 || index >= source.length) {
            throw new IndexOutOfBoundsException(String.format(INDEX_ARRAY_IS_INVALID, index, source.length));
        }

        int[] newArray = new int[source.length - 1];
        System.arraycopy(source, 0, newArray, 0, index);
        System.arraycopy(source, index + 1, newArray, index, source.length - index - 1);
        return newArray;
    }

    /**
     * Removes the given {@code long} element at the given index in the array.
     *
     * @param source the array to remove the element from
     * @param index  the position of the element to be removed
     * @return new array containing the existing elements except the element at the specified position
     * @throws IllegalArgumentException  if the source is {@code null}
     * @throws IndexOutOfBoundsException if index is invalid
     */
    public static long[] remove(long[] source, int index) {
        if (source == null) throw new IllegalArgumentException(INPUT_ARRAY_IS_NULL_MESSAGE);

        if (index < 0 || index >= source.length) {
            throw new IndexOutOfBoundsException(String.format(INDEX_ARRAY_IS_INVALID, index, source.length));
        }

        long[] newArray = new long[source.length - 1];
        System.arraycopy(source, 0, newArray, 0, index);
        System.arraycopy(source, index + 1, newArray, index, source.length - index - 1);
        return newArray;
    }

    /**
     * Removes the given {@code float} element at the given index in the array.
     *
     * @param source the array to remove the element from
     * @param index  the position of the element to be removed
     * @return new array containing the existing elements except the element at the specified position
     * @throws IllegalArgumentException  if the source is {@code null}
     * @throws IndexOutOfBoundsException if index is invalid
     */
    public static float[] remove(float[] source, int index) {
        if (source == null) throw new IllegalArgumentException(INPUT_ARRAY_IS_NULL_MESSAGE);

        if (index < 0 || index >= source.length) {
            throw new IndexOutOfBoundsException(String.format(INDEX_ARRAY_IS_INVALID, index, source.length));
        }

        float[] newArray = new float[source.length - 1];
        System.arraycopy(source, 0, newArray, 0, index);
        System.arraycopy(source, index + 1, newArray, index, source.length - index - 1);
        return newArray;
    }

    /**
     * Removes the given {@code double} element at the given index in the
     * array.
     *
     * @param source the array to remove the element from
     * @param index  the position of the element to be removed
     * @return new array containing the existing elements except the element at the specified position
     * @throws IllegalArgumentException  if the source is {@code null}
     * @throws IndexOutOfBoundsException if index is invalid
     */
    public static double[] remove(double[] source, int index) {
        if (source == null) throw new IllegalArgumentException(INPUT_ARRAY_IS_NULL_MESSAGE);

        if (index < 0 || index >= source.length) {
            throw new IndexOutOfBoundsException(String.format(INDEX_ARRAY_IS_INVALID, index, source.length));
        }

        double[] newArray = new double[source.length - 1];
        System.arraycopy(source, 0, newArray, 0, index);
        System.arraycopy(source, index + 1, newArray, index, source.length - index - 1);
        return newArray;
    }

    /**
     * Removes the given {@code Object} element at the given index in the
     * array.
     *
     * @param <T>    the type of array elements
     * @param source the array to remove the element from
     * @param index  the position of the element to be removed
     * @return new array containing the existing elements except the element at the specified position
     * @throws IllegalArgumentException  if the source is {@code null}
     * @throws IndexOutOfBoundsException if index is invalid
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] remove(T[] source, int index) {
        if (source == null) throw new IllegalArgumentException(INPUT_ARRAY_IS_NULL_MESSAGE);

        if (index < 0 || index >= source.length) {
            throw new IndexOutOfBoundsException(String.format(INDEX_ARRAY_IS_INVALID, index, source.length));
        }

        T[] newArray = (T[]) Array.newInstance(source.getClass().getComponentType(), source.length - 1);
        System.arraycopy(source, 0, newArray, 0, index);
        System.arraycopy(source, index + 1, newArray, index, source.length - index - 1);
        return newArray;
    }

    /**
     * Removes the given {@code boolean} element at in the first position in
     * the array.
     *
     * @param source the array to remove the element from
     * @return new array containing the existing elements except the element at the first position
     * @throws IllegalArgumentException if the source is {@code null}
     */
    public static boolean[] removeFirst(boolean[] source) {
        return remove(source, 0);
    }

    /**
     * Removes the given {@code byte} element at in the first position in the
     * array.
     *
     * @param source the array to remove the element from
     * @return new array containing the existing elements except the element at the first position
     * @throws IllegalArgumentException if the source is {@code null}
     */
    public static byte[] removeFirst(byte[] source) {
        return remove(source, 0);
    }

    /**
     * Removes the given {@code char} element at in the first position in the
     * array.
     *
     * @param source the array to remove the element from
     * @return new array containing the existing elements except the element at the first position
     * @throws IllegalArgumentException if the source is {@code null}
     */
    public static char[] removeFirst(char[] source) {
        return remove(source, 0);
    }

    /**
     * Removes the given {@code short} element at in the first position in the
     * array.
     *
     * @param source the array to remove the element from
     * @return new array containing the existing elements except the element at the first position
     * @throws IllegalArgumentException if the source is {@code null}
     */
    public static short[] removeFirst(short[] source) {
        return remove(source, 0);
    }

    /**
     * Removes the given {@code int} element at in the first position in the
     * array.
     *
     * @param source the array to remove the element from
     * @return new array containing the existing elements except the element at the first position
     * @throws IllegalArgumentException if the source is {@code null}
     */
    public static int[] removeFirst(int[] source) {
        return remove(source, 0);
    }

    /**
     * Removes the given {@code long} element at in the first position in the
     * array.
     *
     * @param source the array to remove the element from
     * @return new array containing the existing elements except the element at the first position
     * @throws IllegalArgumentException if the source is {@code null}
     */
    public static long[] removeFirst(long[] source) {
        return remove(source, 0);
    }

    /**
     * Removes the given {@code float} element at in the first position in the
     * array.
     *
     * @param source the array to remove the element from
     * @return new array containing the existing elements except the element at the first position
     * @throws IllegalArgumentException if the source is {@code null}
     */
    public static float[] removeFirst(float[] source) {
        return remove(source, 0);
    }

    /**
     * Removes the given {@code double} element at in the first position in the
     * array.
     *
     * @param source the array to remove the element from
     * @return new array containing the existing elements except the element at the first position
     * @throws IllegalArgumentException if the source is {@code null}
     */
    public static double[] removeFirst(double[] source) {
        return remove(source, 0);
    }

    /**
     * Removes the given {@code Object} element at in the first position in the
     * array.
     *
     * @param <T>    the type of array elements
     * @param source the array to remove the element from
     * @return new array containing the existing elements except the element at the first position
     * @throws IllegalArgumentException if the source is {@code null}
     */
    public static <T> T[] removeFirst(T[] source) {
        return remove(source, 0);
    }

    /**
     * Removes the given {@code boolean} element at in the last position in the
     * array.
     *
     * @param source the array to remove the element from
     * @return new array containing the existing elements except the element at the last position
     * @throws IllegalArgumentException if the source is {@code null}
     */
    public static boolean[] removeLast(boolean[] source) {
        if (source == null) throw new IllegalArgumentException(INPUT_ARRAY_IS_NULL_MESSAGE);
        return remove(source, source.length - 1);
    }

    /**
     * Removes the given {@code byte} element at in the last position in the
     * array.
     *
     * @param source the array to remove the element from
     * @return new array containing the existing elements except the element at the last position
     * @throws IllegalArgumentException if the source is {@code null}
     */
    public static byte[] removeLast(byte[] source) {
        if (source == null) throw new IllegalArgumentException(INPUT_ARRAY_IS_NULL_MESSAGE);
        return remove(source, source.length - 1);
    }

    /**
     * Removes the given {@code char} element at in the last position in the
     * array.
     *
     * @param source the array to remove the element from
     * @return new array containing the existing elements except the element at the last position
     * @throws IllegalArgumentException if the source is {@code null}
     */
    public static char[] removeLast(char[] source) {
        if (source == null) throw new IllegalArgumentException(INPUT_ARRAY_IS_NULL_MESSAGE);
        return remove(source, source.length - 1);
    }

    /**
     * Removes the given {@code short} element at in the last position in the
     * array.
     *
     * @param source the array to remove the element from
     * @return new array containing the existing elements except the element at the last position
     * @throws IllegalArgumentException if the source is {@code null}
     */
    public static short[] removeLast(short[] source) {
        if (source == null) throw new IllegalArgumentException(INPUT_ARRAY_IS_NULL_MESSAGE);
        return remove(source, source.length - 1);
    }

    /**
     * Removes the given {@code int} element at in the last position in the
     * array.
     *
     * @param source the array to remove the element from
     * @return new array containing the existing elements except the element at the last position
     * @throws IllegalArgumentException if the source is {@code null}
     */
    public static int[] removeLast(int[] source) {
        if (source == null) throw new IllegalArgumentException(INPUT_ARRAY_IS_NULL_MESSAGE);
        return remove(source, source.length - 1);
    }

    /**
     * Removes the given {@code long} element at in the last position in the
     * array.
     *
     * @param source the array to remove the element from
     * @return new array containing the existing elements except the element at the last position
     * @throws IllegalArgumentException if the source is {@code null}
     */
    public static long[] removeLast(long[] source) {
        if (source == null) throw new IllegalArgumentException(INPUT_ARRAY_IS_NULL_MESSAGE);
        return remove(source, source.length - 1);
    }

    /**
     * Removes the given {@code float} element at in the last position in the
     * array.
     *
     * @param source the array to remove the element from
     * @return new array containing the existing elements except the element at the last position
     * @throws IllegalArgumentException if the source is {@code null}
     */
    public static float[] removeLast(float[] source) {
        if (source == null) throw new IllegalArgumentException(INPUT_ARRAY_IS_NULL_MESSAGE);
        return remove(source, source.length - 1);
    }

    /**
     * Removes the given {@code double} element at in the last position in the
     * array.
     *
     * @param source the array to remove the element from
     * @return new array containing the existing elements except the element at the last position
     * @throws IllegalArgumentException if the source is {@code null}
     */
    public static double[] removeLast(double[] source) {
        if (source == null) throw new IllegalArgumentException(INPUT_ARRAY_IS_NULL_MESSAGE);
        return remove(source, source.length - 1);
    }

    /**
     * Removes the given {@code Object} element at in the last position in the
     * array.
     *
     * @param <T>    the type of array elements
     * @param source the array to remove the element from
     * @return new array containing the existing elements except the element at the last position
     * @throws IllegalArgumentException if the source is {@code null}
     */
    public static <T> T[] removeLast(T[] source) {
        if (source == null) throw new IllegalArgumentException(INPUT_ARRAY_IS_NULL_MESSAGE);
        return remove(source, source.length - 1);
    }

    /**
     * Removes the first occurrence of the specified element from the specified
     * array. All subsequent elements are shifted to the left. If the array
     * doesn't contain such an element, no elements are removed from the array.
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the first occurrence of the specified element. The
     * component type of the returned array is always the same as that of the
     * input array.
     *
     * @param source  the type of array elements
     * @param element the element to be removed
     * @return A new array containing the existing elements except the first occurrence of the specified element
     */
    public static boolean[] removeElement(boolean[] source, boolean element) {
        final int index = indexOf(source, element);
        return index == INDEX_NOT_FOUND ? clone(source) : remove(source, index);
    }

    /**
     * Removes the first occurrence of the specified element from the specified
     * array. All subsequent elements are shifted to the left. If the array
     * doesn't contain such an element, no elements are removed from the array.
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the first occurrence of the specified element. The
     * component type of the returned array is always the same as that of the
     * input array.
     *
     * @param source  the type of array elements
     * @param element the element to be removed
     * @return A new array containing the existing elements except the first occurrence of the specified element
     */
    public static byte[] removeElement(byte[] source, byte element) {
        final int index = indexOf(source, element);
        return index == INDEX_NOT_FOUND ? clone(source) : remove(source, index);
    }

    /**
     * Removes the first occurrence of the specified element from the specified
     * array. All subsequent elements are shifted to the left. If the array
     * doesn't contain such an element, no elements are removed from the array.
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the first occurrence of the specified element. The
     * component type of the returned array is always the same as that of the
     * input array.
     *
     * @param source  the type of array elements
     * @param element the element to be removed
     * @return A new array containing the existing elements except the first occurrence of the specified element
     */
    public static char[] removeElement(char[] source, char element) {
        final int index = indexOf(source, element);
        return index == INDEX_NOT_FOUND ? clone(source) : remove(source, index);
    }

    /**
     * Removes the first occurrence of the specified element from the specified
     * array. All subsequent elements are shifted to the left. If the array
     * doesn't contain such an element, no elements are removed from the array.
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the first occurrence of the specified element. The
     * component type of the returned array is always the same as that of the
     * input array.
     *
     * @param source  the type of array elements
     * @param element the element to be removed
     * @return A new array containing the existing elements except the first occurrence of the specified element
     */
    public static short[] removeElement(short[] source, short element) {
        final int index = indexOf(source, element);
        return index == INDEX_NOT_FOUND ? clone(source) : remove(source, index);
    }

    /**
     * Removes the first occurrence of the specified element from the specified
     * array. All subsequent elements are shifted to the left. If the array
     * doesn't contain such an element, no elements are removed from the array.
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the first occurrence of the specified element. The
     * component type of the returned array is always the same as that of the
     * input array.
     *
     * @param source  the type of array elements
     * @param element the element to be removed
     * @return A new array containing the existing elements except the first occurrence of the specified element
     */
    public static int[] removeElement(int[] source, int element) {
        final int index = indexOf(source, element);
        return index == INDEX_NOT_FOUND ? clone(source) : remove(source, index);
    }

    /**
     * Removes the first occurrence of the specified element from the specified
     * array. All subsequent elements are shifted to the left. If the array
     * doesn't contain such an element, no elements are removed from the array.
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the first occurrence of the specified element. The
     * component type of the returned array is always the same as that of the
     * input array.
     *
     * @param source  the type of array elements
     * @param element the element to be removed
     * @return A new array containing the existing elements except the first occurrence of the specified element
     */
    public static long[] removeElement(long[] source, long element) {
        final int index = indexOf(source, element);
        return index == INDEX_NOT_FOUND ? clone(source) : remove(source, index);
    }

    /**
     * Removes the first occurrence of the specified element from the specified
     * array. All subsequent elements are shifted to the left. If the array
     * doesn't contain such an element, no elements are removed from the array.
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the first occurrence of the specified element. The
     * component type of the returned array is always the same as that of the
     * input array.
     *
     * @param source  the type of array elements
     * @param element the element to be removed
     * @return A new array containing the existing elements except the first occurrence of the specified element
     */
    public static float[] removeElement(float[] source, float element) {
        final int index = indexOf(source, element);
        return index == INDEX_NOT_FOUND ? clone(source) : remove(source, index);
    }

    /**
     * Removes the first occurrence of the specified element from the specified
     * array. All subsequent elements are shifted to the left. If the array
     * doesn't contain such an element, no elements are removed from the array.
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the first occurrence of the specified element. The
     * component type of the returned array is always the same as that of the
     * input array.
     *
     * @param source  the type of array elements
     * @param element the element to be removed
     * @return A new array containing the existing elements except the first occurrence of the specified element
     */
    public static double[] removeElement(double[] source, double element) {
        final int index = indexOf(source, element);
        return index == INDEX_NOT_FOUND ? clone(source) : remove(source, index);
    }

    /**
     * Removes the first occurrence of the specified element from the specified
     * array. All subsequent elements are shifted to the left. If the array
     * doesn't contain such an element, no elements are removed from the array.
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the first occurrence of the specified element. The
     * component type of the returned array is always the same as that of the
     * input array.
     *
     * @param <T>     the type of array elements
     * @param source  the type of array elements
     * @param element the element to be removed
     * @return A new array containing the existing elements except the first occurrence of the specified element
     */
    public static <T> T[] removeElement(T[] source, T element) {
        final int index = indexOf(source, element);
        return index == INDEX_NOT_FOUND ? clone(source) : remove(source, index);
    }

    /**
     * Sorts the specified {@code byte} array in ascending order.
     *
     * @param source the byte array to be sorted
     * @return the sorted byte array, or {@code null} if the input array is {@code null}
     */
    public static byte[] sort(byte[] source) {
        if (hasLength(source)) Arrays.sort(source);
        return source;
    }

    /**
     * Sorts the specified {@code char} array in ascending order.
     *
     * @param source the character array to be sorted
     * @return the sorted character array, or {@code null} if the input array is {@code null}
     */
    public static char[] sort(char[] source) {
        if (hasLength(source)) Arrays.sort(source);
        return source;
    }

    /**
     * Sorts the specified {@code short} array in ascending order.
     *
     * @param source the short array to be sorted
     * @return the sorted short array, or {@code null} if the input array is {@code null}
     */
    public static short[] sort(short[] source) {
        if (hasLength(source)) Arrays.sort(source);
        return source;
    }

    /**
     * Sorts the specified {@code int} array in ascending order.
     *
     * @param source the int array to be sorted
     * @return the sorted int array, or {@code null} if the input array is {@code null}
     */
    public static int[] sort(int[] source) {
        if (hasLength(source)) Arrays.sort(source);
        return source;
    }

    /**
     * Sorts the specified {@code long} array in ascending order.
     *
     * @param source the long array to be sorted
     * @return the sorted long array, or {@code null} if the input array is {@code null}
     */
    public static long[] sort(long[] source) {
        if (hasLength(source)) Arrays.sort(source);
        return source;
    }

    /**
     * Sorts the specified {@code float} array in ascending order.
     *
     * @param source the float array to be sorted
     * @return the sorted float array, or {@code null} if the input array is {@code null}
     */
    public static float[] sort(float[] source) {
        if (hasLength(source)) Arrays.sort(source);
        return source;
    }

    /**
     * Sorts the specified {@code double} array in ascending order.
     *
     * @param source the double array to be sorted
     * @return the sorted double array, or {@code null} if the input array is {@code null}
     */
    public static double[] sort(double[] source) {
        if (hasLength(source)) Arrays.sort(source);
        return source;
    }

    /**
     * Sorts the specified array of objects in ascending order.
     *
     * @param <T>    the type of array elements
     * @param source the array to be sorted
     * @return the sorted array, or {@code null} if the input array is {@code null}
     */
    public static <T> T[] sort(T[] source) {
        if (hasLength(source)) Arrays.sort(source);
        return source;
    }

    /**
     * Sorts the specified array of objects according to the order induced by
     * the provided comparator.
     *
     * @param <T>        the type of array elements
     * @param source     the array to be sorted, may be {@code null} or empty
     * @param comparator the comparator to determine the order of the array elements
     * @return the sorted array, or {@code null} if the input array is {@code null}
     */
    public static <T> T[] sort(T[] source, Comparator<T> comparator) {
        if (hasLength(source)) Arrays.sort(source, comparator);
        return source;
    }

    /**
     * Produces a new {@code boolean} array containing the elements between the
     * {@code start} and {@code end} indices.
     *
     * <p>{@code Null} or {@code empty} array input produces {@code empty}
     * output.
     *
     * @param source the input boolean array to extract the subarray from
     * @param start  the starting index (inclusive) of the subarray, negative values are treated as 0
     * @param end    the ending index (exclusive) of the subarray, capped at the array length
     * @return a new array containing the elements between the start and end indices.
     * @see Arrays#copyOfRange(boolean[], int, int)
     */
    public static boolean[] subarray(boolean[] source, int start, int end) {
        if (source == null) return new boolean[0];

        start = Math.max(0, start);
        end = Math.min(end, source.length);

        int newSize = end - start;

        return (newSize <= 0) ? new boolean[0] : Arrays.copyOfRange(source, start, end);
    }

    /**
     * Produces a new {@code byte} array containing the elements between the
     * {@code start} and {@code end} indices.
     *
     * <p>{@code Null} or {@code empty} array input produces {@code empty}
     * output.
     *
     * @param source the input byte array to extract the subarray from
     * @param start  the starting index (inclusive) of the subarray, negative values are treated as 0
     * @param end    the ending index (exclusive) of the subarray, capped at the array length
     * @return a new array containing the elements between the start and end indices.
     * @see Arrays#copyOfRange(byte[], int, int)
     */
    public static byte[] subarray(byte[] source, int start, int end) {
        if (source == null) return new byte[0];

        start = Math.max(0, start);
        end = Math.min(end, source.length);

        int newSize = end - start;

        return (newSize <= 0) ? new byte[0] : Arrays.copyOfRange(source, start, end);
    }

    /**
     * Produces a new {@code char} array containing the elements between the
     * {@code start} and {@code end} indices.
     *
     * <p>{@code Null} or {@code empty} array input produces {@code empty}
     * output.
     *
     * @param source the input char array to extract the subarray from
     * @param start  the starting index (inclusive) of the subarray, negative values are treated as 0
     * @param end    the ending index (exclusive) of the subarray, capped at the array length
     * @return a new array containing the elements between the start and end indices.
     * @see Arrays#copyOfRange(char[], int, int)
     */
    public static char[] subarray(char[] source, int start, int end) {
        if (source == null) return new char[0];

        start = Math.max(0, start);
        end = Math.min(end, source.length);

        int newSize = end - start;

        return (newSize <= 0) ? new char[0] : Arrays.copyOfRange(source, start, end);
    }

    /**
     * Produces a new {@code short} array containing the elements between the
     * {@code start} and {@code end} indices.
     *
     * <p>{@code Null} or {@code empty} array input produces {@code empty}
     * output.
     *
     * @param source the input short array to extract the subarray from
     * @param start  the starting index (inclusive) of the subarray, negative values are treated as 0
     * @param end    the ending index (exclusive) of the subarray, capped at the array length
     * @return a new array containing the elements between the start and end indices.
     * @see Arrays#copyOfRange(short[], int, int)
     */
    public static short[] subarray(short[] source, int start, int end) {
        if (source == null) return new short[0];

        start = Math.max(0, start);
        end = Math.min(end, source.length);

        int newSize = end - start;

        return (newSize <= 0) ? new short[0] : Arrays.copyOfRange(source, start, end);
    }

    /**
     * Produces a new {@code int} array containing the elements between the
     * {@code start} and {@code end} indices.
     *
     * <p>{@code Null} or {@code empty} array input produces {@code empty}
     * output.
     *
     * @param source the input int array to extract the subarray from
     * @param start  the starting index (inclusive) of the subarray, negative values are treated as 0
     * @param end    the ending index (exclusive) of the subarray, capped at the array length
     * @return a new array containing the elements between the start and end indices.
     * @see Arrays#copyOfRange(int[], int, int)
     */
    public static int[] subarray(int[] source, int start, int end) {
        if (source == null) return new int[0];

        start = Math.max(0, start);
        end = Math.min(end, source.length);

        int newSize = end - start;

        return (newSize <= 0) ? new int[0] : Arrays.copyOfRange(source, start, end);
    }

    /**
     * Produces a new {@code long} array containing the elements between the
     * {@code start} and {@code end} indices.
     *
     * <p>{@code Null} or {@code empty} array input produces {@code empty}
     * output.
     *
     * @param source the input long array to extract the subarray from
     * @param start  the starting index (inclusive) of the subarray, negative values are treated as 0
     * @param end    the ending index (exclusive) of the subarray, capped at the array length
     * @return a new array containing the elements between the start and end indices.
     * @see Arrays#copyOfRange(long[], int, int)
     */
    public static long[] subarray(long[] source, int start, int end) {
        if (source == null) return new long[0];

        start = Math.max(0, start);
        end = Math.min(end, source.length);

        int newSize = end - start;

        return (newSize <= 0) ? new long[0] : Arrays.copyOfRange(source, start, end);
    }

    /**
     * Produces a new {@code float} array containing the elements between the
     * {@code start} and {@code end} indices.
     *
     * <p>{@code Null} or {@code empty} array input produces {@code empty}
     * output.
     *
     * @param source the input float array to extract the subarray from
     * @param start  the starting index (inclusive) of the subarray, negative values are treated as 0
     * @param end    the ending index (exclusive) of the subarray, capped at the array length
     * @return a new array containing the elements between the start and end indices.
     * @see Arrays#copyOfRange(float[], int, int)
     */
    public static float[] subarray(float[] source, int start, int end) {
        if (source == null) return new float[0];

        start = Math.max(0, start);
        end = Math.min(end, source.length);

        int newSize = end - start;

        return (newSize <= 0) ? new float[0] : Arrays.copyOfRange(source, start, end);
    }

    /**
     * Produces a new {@code double} array containing the elements between the
     * {@code start} and {@code end} indices.
     *
     * <p>{@code Null} or {@code empty} array input produces {@code empty}
     * output.
     *
     * @param source the input double array to extract the subarray from
     * @param start  the starting index (inclusive) of the subarray, negative values are treated as 0
     * @param end    the ending index (exclusive) of the subarray, capped at the array length
     * @return a new array containing the elements between the start and end indices.
     * @see Arrays#copyOfRange(double[], int, int)
     */
    public static double[] subarray(double[] source, int start, int end) {
        if (source == null) return new double[0];

        start = Math.max(0, start);
        end = Math.min(end, source.length);

        int newSize = end - start;

        return (newSize <= 0) ? new double[0] : Arrays.copyOfRange(source, start, end);
    }

    /**
     * Produces a new {@code Object} array containing the elements between the
     * {@code start} and {@code end} indices.
     *
     * <p>{@code Null} array input produces {@code null} output.
     *
     * @param <T>   the type of array elements
     * @param value the input Object array to extract the subarray from
     * @param start the starting index (inclusive) of the subarray, negative values are treated as 0
     * @param end   the ending index (exclusive) of the subarray, capped at the array length
     * @return a new array containing the elements between the start and end indices.
     * @see Arrays#copyOfRange(Object[], int, int)
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] subarray(T[] value, int start, int end) {
        if (value == null) return null;

        start = Math.max(0, start);
        end = Math.min(end, value.length);

        int newSize = end - start;
        Class<T> type = getComponentType(value);

        return (newSize <= 0) ? (T[]) Array.newInstance(type, 0) : Arrays.copyOfRange(value, start, end);
    }
}
