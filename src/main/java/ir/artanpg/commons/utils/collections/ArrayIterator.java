package ir.artanpg.commons.utils.collections;

import ir.artanpg.commons.utils.ArrayUtils;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A generic iterator implementation for iterating over a portion of an array.
 *
 * <p>This class allows iteration over a specified range of elements in an
 * array, starting from an offset and covering a given length.
 *
 * <p>It implements the {@link Iterator} interface and provides methods to
 * check for remaining elements and retrieve the next element.
 *
 * @param <T> the type of elements in the array
 * @author Mohammad Yazdian
 */
public class ArrayIterator<T> implements Iterator<T> {

    private final T[] array;
    private final int maxIndex;
    private int index;

    private int lastReturnedIndex = -1; // Tracks the last index returned by next()

    /**
     * Constructs an iterator over the entire array.
     *
     * @param values the array to iterate over
     * @throws NullPointerException if the array is {@code null}
     */
    public ArrayIterator(T[] values) {
        this(values, 0, values.length);
    }

    /**
     * Constructs an iterator over a portion of the array, starting from the
     * specified index and covering the specified length.
     *
     * @param values the array to iterate over
     * @param index  the starting index of the iteration
     * @param length the number of elements to iterate over
     * @throws IllegalArgumentException if index is negative, index exceeds array length,
     *                                  or length exceeds the remaining array size from index
     * @throws NullPointerException     if the array is {@code null}
     */
    public ArrayIterator(T[] values, int index, int length) {
        if (values == null) throw new NullPointerException("values cannot be null");
        if (index < 0) {
            throw new IllegalArgumentException("Index cannot be negative");
        } else if (index > values.length) {
            throw new IllegalArgumentException("Index cannot exceed array length");
        } else if (length > values.length - index) {
            throw new IllegalArgumentException("Length cannot exceed remaining array size");
        } else {
            this.array = values;
            this.index = index;
            this.maxIndex = length + index;
        }
    }

    /**
     * Checks if there are more elements to iterate over.
     *
     * @return {@code true}, if there are more elements, {@code false} otherwise
     */
    @Override
    public boolean hasNext() {
        return this.index < this.maxIndex;
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the array
     * @throws NoSuchElementException if there are no more elements to iterate over
     */
    @Override
    public T next() {
        if (this.index >= this.maxIndex) {
            throw new NoSuchElementException("No more elements to iterate");
        } else {
            lastReturnedIndex = index;
            return this.array[this.index++];
        }
    }

    /**
     * Removes the last element returned by {@link #next()} by setting it to
     * null in the array.
     *
     * <p>This method can only be called once per call to {@link #next()}.
     *
     * @throws IllegalStateException if {@link #next()} has not been called yet or
     *                               if the element has already been removed
     */
    @Override
    public void remove() {
        if (lastReturnedIndex == -1 || lastReturnedIndex >= index)
            throw new IllegalStateException("No element to remove or element already removed");

        array[lastReturnedIndex] = null;
//        ArrayUtils.remove(array[lastReturnedIndex]);
        lastReturnedIndex = -1; // Prevent multiple removals
    }
}
