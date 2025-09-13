package ir.artanpg.commons.utils;

import java.lang.reflect.Array;

/**
 * Provides utility methods for {@link Array} instances.
 *
 * @author Mohammad Yazdian
 */
public abstract class ArrayUtils {

    /**
     * An empty {@code boolean} array constant.
     */
    public static final boolean[] EMPTY_BOOLEAN_ARRAY = {};

    /**
     * An empty {@code byte} array constant.
     */
    public static final byte[] EMPTY_BYTE_ARRAY = {};

    /**
     * An empty {@code short} array constant.
     */
    public static final short[] EMPTY_SHORT_ARRAY = {};

    /**
     * An empty {@code int} array constant.
     */
    public static final int[] EMPTY_INT_ARRAY = {};

    /**
     * An empty {@code long} array constant.
     */
    public static final long[] EMPTY_LONG_ARRAY = {};

    /**
     * An empty {@code float} array constant.
     */
    public static final float[] EMPTY_FLOAT_ARRAY = {};

    /**
     * An empty {@code double} array constant.
     */
    public static final double[] EMPTY_DOUBLE_ARRAY = {};

    /**
     * An empty {@code String} array constant.
     */
    public static final String[] EMPTY_STRING_ARRAY = {};

    /**
     * Private constructor to prevent instantiation of this utility class.
     *
     * @throws UnsupportedOperationException if an attempt is made to instantiate this class
     */
    private ArrayUtils() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    /**
     * Ensures that the given array has enough capacity to accommodate the
     * specified maximum index.
     *
     * <p>If the array's length is sufficient, it is returned as-is, Otherwise, a
     * new array with a larger capacity is created, and the contents of the
     * original array are copied to it.
     *
     * <p>Examples:
     * <pre>
     *  String[] array = {"a", "b", "c"};
     *  String[] result = ArrayUtils.ensureCapacity(array, 4); = ["a", "b", "c", null, null, null, ...]
     * </pre>
     *
     * @param <T>      the type of elements in the array
     * @param array    the input array to check and possibly resize
     * @param maxIndex the maximum index that the array needs to accommodate
     * @return the original array if its length is sufficient, or a new array with increased capacity containing all
     * elements from the original array
     * @throws NegativeArraySizeException if maxIndex is negative
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] ensureCapacity(T[] array, int maxIndex) {
        if (!hasLength(array)) return array;
        if (maxIndex < 0) throw new NegativeArraySizeException("maxIndex cannot be negative");

        if (maxIndex < array.length) {
            return array;
        } else {
            int newLength = calculateNewLength(array.length, maxIndex);
            T[] result = (T[]) (Array.newInstance(array.getClass().getComponentType(), newLength));
            System.arraycopy(array, 0, result, 0, array.length);
            return result;
        }
    }

    private static int calculateNewLength(int currentLength, int maxIndex) {
        while (currentLength < maxIndex + 1) {
            currentLength *= 2;
        }

        return currentLength;
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
     * Checks if the specified {@code String} array is not {@code null} and has
     * a length greater than zero.
     *
     * @param source the String array to check
     * @return {@code true}, if the array is not {@code null} and has at least one element, {@code false} otherwise
     */
    public static boolean hasLength(String[] source) {
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
}
