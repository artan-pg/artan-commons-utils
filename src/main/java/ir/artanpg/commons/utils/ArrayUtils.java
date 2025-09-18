package ir.artanpg.commons.utils;

import ir.artanpg.commons.core.tools.jacoco.Generated;

import java.lang.reflect.Array;
import java.time.temporal.Temporal;
import java.util.Arrays;

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
     * An empty {@code Temporal} array constant.
     */
    public static final Temporal[] EMPTY_TEMPORAL_ARRAY = {};

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
     * <p>Examples:
     * <pre>
     * 	ArrayUtils.add(new boolean[]{}, true, 0);      = [true]
     * 	ArrayUtils.add(new boolean[]{false}, true, 1); = [false, true]
     * 	ArrayUtils.add(null, false, 0);                = IllegalArgumentException
     * 	ArrayUtils.add(new boolean[]{}, true, 1);      = ArrayIndexOutOfBoundsException
     * </pre>
     *
     * @param source  the array to add the element to
     * @param element the value to add
     * @param index   the position within array to add the new element
     * @return a new array containing all elements from source with the new element inserted
     * @throws IllegalArgumentException       if the source is {@code null}
     * @throws ArrayIndexOutOfBoundsException if index is invalid
     */
    public static boolean[] add(boolean[] source, boolean element, int index) {
        if (source == null) throw new IllegalArgumentException("The input array cannot be null");
        if (index < 0 || index > source.length) {
            throw new ArrayIndexOutOfBoundsException(
                    String.format(
                            "last destination index %d out of bounds for %s[%d]",
                            index, source.getClass().getComponentType(), source.length));
        }

        boolean[] newArray = Arrays.copyOf(source, source.length + 1);
        System.arraycopy(source, index, newArray, index + 1, source.length - index);
        newArray[index] = element;

        return newArray;
    }


    /**
     * Adds the given {@code byte} element at the given index in the array.
     *
     * <p>Examples:
     * <pre>
     * 	ArrayUtils.add(new byte[]{}, -127, 0);     = [-127]
     * 	ArrayUtils.add(new byte[]{-127}, -126, 1); = [-127, -126]
     * 	ArrayUtils.add(null, false, 0);            = IllegalArgumentException
     * 	ArrayUtils.add(new byte[]{}, -127, 1);     = ArrayIndexOutOfBoundsException
     * </pre>
     *
     * @param source  the array to add the element to
     * @param element the value to add
     * @param index   the position within array to add the new element
     * @return a new array containing all elements from source with the new element inserted
     * @throws IllegalArgumentException       if the source is {@code null}
     * @throws ArrayIndexOutOfBoundsException if index is invalid
     */
    public static byte[] add(byte[] source, byte element, int index) {
        if (source == null) throw new IllegalArgumentException("The input array cannot be null");
        if (index < 0 || index > source.length) {
            throw new ArrayIndexOutOfBoundsException(
                    String.format(
                            "last destination index %d out of bounds for %s[%d]",
                            index, source.getClass().getComponentType(), source.length));
        }

        byte[] newArray = Arrays.copyOf(source, source.length + 1);
        System.arraycopy(source, index, newArray, index + 1, source.length - index);
        newArray[index] = element;

        return newArray;
    }

    /**
     * Adds the given {@code char} element at the given index in the array.
     *
     * <p>Examples:
     * <pre>
     * 	ArrayUtils.add(new char[]{}, 'a', 0);    = ['a']
     * 	ArrayUtils.add(new char[]{'a'}, 'b', 1); = ['a', 'b]
     * 	ArrayUtils.add(null, 'a', 0);            = IllegalArgumentException
     * 	ArrayUtils.add(new char[]{}, 'a', 1);    = ArrayIndexOutOfBoundsException
     * </pre>
     *
     * @param source  the array to add the element to
     * @param element the value to add
     * @param index   the position within array to add the new element
     * @return a new array containing all elements from source with the new element inserted
     * @throws IllegalArgumentException       if the source is {@code null}
     * @throws ArrayIndexOutOfBoundsException if index is invalid
     */
    public static char[] add(char[] source, char element, int index) {
        if (source == null) throw new IllegalArgumentException("The input array cannot be null");
        if (index < 0 || index > source.length) {
            throw new ArrayIndexOutOfBoundsException(
                    String.format(
                            "last destination index %d out of bounds for %s[%d]",
                            index, source.getClass().getComponentType(), source.length));
        }

        char[] newArray = Arrays.copyOf(source, source.length + 1);
        System.arraycopy(source, index, newArray, index + 1, source.length - index);
        newArray[index] = element;

        return newArray;
    }


    /**
     * Checks if the specified {@code boolean} array is not {@code null} and
     * has a length greater than zero.
     *
     * <p>Examples:
     * <pre>
     * 	ArrayUtils.hasLength(null);                = false
     * 	ArrayUtils.hasLength(new boolean[]{});     = false
     * 	ArrayUtils.hasLength(new boolean[]{true}); = true
     * </pre>
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
     * <p>Examples:
     * <pre>
     * 	ArrayUtils.hasLength(null);             = false
     * 	ArrayUtils.hasLength(new byte[]{});     = false
     * 	ArrayUtils.hasLength(new byte[]{-127}); = true
     * </pre>
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
     * <p>Examples:
     * <pre>
     * 	ArrayUtils.hasLength(null);            = false
     * 	ArrayUtils.hasLength(new char[]{});    = false
     * 	ArrayUtils.hasLength(new char[]{'a'}); = true
     * </pre>
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
     * <p>Examples:
     * <pre>
     * 	ArrayUtils.hasLength(null);               = false
     * 	ArrayUtils.hasLength(new short[]{});      = false
     * 	ArrayUtils.hasLength(new short[]{32767}); = true
     * </pre>
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
     * <p>Examples:
     * <pre>
     * 	ArrayUtils.hasLength(null);             = false
     * 	ArrayUtils.hasLength(new int[]{});      = false
     * 	ArrayUtils.hasLength(new int[]{32767}); = true
     * </pre>
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
     * <p>Examples:
     * <pre>
     * 	ArrayUtils.hasLength(null);               = false
     * 	ArrayUtils.hasLength(new long[]{});       = false
     * 	ArrayUtils.hasLength(new long[]{32767L}); = true
     * </pre>
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
     * <p>Examples:
     * <pre>
     * 	ArrayUtils.hasLength(null);              = false
     * 	ArrayUtils.hasLength(new float[]{});     = false
     * 	ArrayUtils.hasLength(new float[]{1.0F}); = true
     * </pre>
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
     * <p>Examples:
     * <pre>
     * 	ArrayUtils.hasLength(null);              = false
     * 	ArrayUtils.hasLength(new double[]{});    = false
     * 	ArrayUtils.hasLength(new double[]{1.0}); = true
     * </pre>
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
     * <p>Examples:
     * <pre>
     * 	ArrayUtils.hasLength(null);                       = false
     * 	ArrayUtils.hasLength(new Object[]{});             = false
     * 	ArrayUtils.hasLength(new Object[]{new Object()}); = true
     * </pre>
     *
     * @param <T>    the type of the array elements
     * @param source the array to check
     * @return {@code true}, if the array is not {@code null} and has at least one element, {@code false} otherwise
     */
    public static <T> boolean hasLength(T[] source) {
        return source != null && source.length > 0;
    }
}
