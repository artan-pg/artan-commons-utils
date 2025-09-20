package ir.artanpg.commons.utils.collections;

import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SuppressWarnings("UnnecessaryLocalVariable")
public class ArrayIteratorTest {

    private static final Integer[] TEST_ARRAY = {1, 2, 3, 4, 5};

    @Test
    void constructor_ShouldInitializeWithFullArray_WhenOnlyArrayIsProvided() {
        // Given
        Integer[] array = TEST_ARRAY;

        // When
        Iterator<Integer> iterator = new ArrayIterator<>(array);

        // Then
        assertThat(iterator.hasNext()).isTrue();
        assertThat(iterator.next()).isEqualTo(1);
    }

    @SuppressWarnings("DataFlowIssue")
    @Test
    void constructor_ShouldThrowNullPointerException_WhenArrayIsNull() {
        // Given
        Integer[] array = null;

        // When & Then
        assertThatThrownBy(() -> new ArrayIterator<>(array))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Array cannot be null");
    }

    @Test
    void constructor_ShouldThrowIllegalArgumentException_WhenOffsetIsNegative() {
        // Given
        Integer[] array = TEST_ARRAY;
        int offset = -1;
        int length = 3;

        // When & Then
        assertThatThrownBy(() -> new ArrayIterator<>(array, offset, length))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Offset cannot be negative");
    }

    @Test
    void constructor_ShouldThrowIllegalArgumentException_WhenOffsetExceedsArrayLength() {
        // Given
        Integer[] array = TEST_ARRAY;
        int offset = 6;
        int length = 1;

        // When & Then
        assertThatThrownBy(() -> new ArrayIterator<>(array, offset, length))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Offset cannot exceed array length");
    }

    @Test
    void constructor_ShouldThrowIllegalArgumentException_WhenLengthExceedsRemainingSize() {
        // Given
        Integer[] array = TEST_ARRAY;
        int offset = 3;
        int length = 3;

        // When & Then
        assertThatThrownBy(() -> new ArrayIterator<>(array, offset, length))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Length cannot exceed remaining array size");
    }

    @Test
    void hasNext_ShouldReturnTrue_WhenMoreElementsAreAvailable() {
        // Given
        Iterator<Integer> iterator = new ArrayIterator<>(TEST_ARRAY, 0, 3);

        // When
        boolean hasNext = iterator.hasNext();

        // Then
        assertThat(hasNext).isTrue();
    }

    @Test
    void hasNext_ShouldReturnFalse_WhenNoMoreElementsAreAvailable() {
        // Given
        Iterator<Integer> iterator = new ArrayIterator<>(TEST_ARRAY, 5, 0);

        // When
        boolean hasNext = iterator.hasNext();

        // Then
        assertThat(hasNext).isFalse();
    }

    @Test
    void next_ShouldReturnNextElement_WhenElementsAreAvailable() {
        // Given
        Iterator<Integer> iterator = new ArrayIterator<>(TEST_ARRAY, 1, 2);

        // When
        Integer first = iterator.next();
        Integer second = iterator.next();

        // Then
        assertThat(first).isEqualTo(2);
        assertThat(second).isEqualTo(3);
    }

    @Test
    void next_ShouldThrowNoSuchElementException_WhenNoMoreElementsAreAvailable() {
        // Given
        Iterator<Integer> iterator = new ArrayIterator<>(TEST_ARRAY, 5, 0);

        // When & Then
        assertThatThrownBy(iterator::next)
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("No more elements to iterate");
    }

    @Test
    void next_ShouldIterateCorrectly_WhenUsingPartialArray() {
        // Given
        Iterator<Integer> iterator = new ArrayIterator<>(TEST_ARRAY, 2, 2);

        // When
        Integer first = iterator.next();
        Integer second = iterator.next();
        boolean hasNext = iterator.hasNext();

        // Then
        assertThat(first).isEqualTo(3);
        assertThat(second).isEqualTo(4);
        assertThat(hasNext).isFalse();
    }

    @Test
    void remove_ShouldSetElementToNull_WhenCalledAfterNext() {
        // Given
        Integer[] array = TEST_ARRAY.clone();
        Iterator<Integer> iterator = new ArrayIterator<>(array, 0, 3);

        // When
        iterator.next(); // Move to first element (1)
        iterator.remove();

        // Then
        assertThat(array[0]).isNull();
        assertThat(iterator.hasNext()).isTrue();
        assertThat(iterator.next()).isEqualTo(2);
    }

    @Test
    void remove_ShouldThrowIllegalStateException_WhenCalledBeforeNext() {
        // Given
        Iterator<Integer> iterator = new ArrayIterator<>(TEST_ARRAY, 0, 3);

        // When & Then
        assertThatThrownBy(iterator::remove)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("No element to remove or element already removed");
    }

    @Test
    void remove_ShouldThrowIllegalStateException_WhenCalledTwiceAfterNext() {
        // Given
        Integer[] array = TEST_ARRAY.clone();
        Iterator<Integer> iterator = new ArrayIterator<>(array, 0, 3);

        // When
        iterator.next(); // Move to first element (1)
        iterator.remove();

        // Then
        assertThatThrownBy(iterator::remove)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("No element to remove or element already removed");
    }

    @Test
    void remove_ShouldNotAffectIteration_WhenCalledAfterNext() {
        // Given
        Integer[] array = TEST_ARRAY.clone();
        Iterator<Integer> iterator = new ArrayIterator<>(array, 0, 3);

        // When
        iterator.next(); // Move to 1
        iterator.remove(); // Set array[0] to null
        Integer nextElement = iterator.next();

        // Then
        assertThat(array[0]).isNull();
        assertThat(nextElement).isEqualTo(2);
        assertThat(iterator.hasNext()).isTrue();
    }
}
