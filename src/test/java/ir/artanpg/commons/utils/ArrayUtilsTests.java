package ir.artanpg.commons.utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

/**
 * Unit tests for the {@link ArrayUtilsTests} class.
 *
 * @author Mohammad Yazdian
 */
@SuppressWarnings("ConstantValue")
class ArrayUtilsTests {

    @Test
    void ensureCapacity_ShouldReturnOriginalArray_WhenArrayIsNull() {
        // Given
        String[] inputArray = null;
        int inputMaxIndex = 5;

        // When
        String[] actual = ArrayUtils.ensureCapacity(inputArray, inputMaxIndex);

        // Then
        then(actual).isNull();
    }

    @Test
    void ensureCapacity_ShouldReturnOriginalArray_WhenArrayIsEmpty() {
        // Given
        String[] inputArray = new String[0];
        int inputMaxIndex = 5;

        // When
        String[] actual = ArrayUtils.ensureCapacity(inputArray, inputMaxIndex);

        // Then
        then(actual)
                .isSameAs(inputArray)
                .isEmpty();
    }

    @Test
    void ensureCapacity_ShouldThrowNegativeArraySizeException_WhenMaxIndexIsNegative() {
        // Given
        String[] inputArray = new String[]{"a", "b"};
        int inputMaxIndex = -1;

        // When & Then
        thenThrownBy(() -> ArrayUtils.ensureCapacity(inputArray, inputMaxIndex))
                .isInstanceOf(NegativeArraySizeException.class)
                .hasMessage("maxIndex cannot be negative");
    }

    @Test
    void ensureCapacity_ShouldReturnOriginalArray_WhenMaxIndexIsLessThanArrayLength() {
        // Given
        String[] inputArray = new String[]{"a", "b", "c"};
        int inputMaxIndex = 2;

        // When
        String[] actual = ArrayUtils.ensureCapacity(inputArray, inputMaxIndex);

        // Then
        then(actual)
                .isSameAs(inputArray)
                .hasSize(3)
                .containsExactly("a", "b", "c");
    }

    @Test
    void ensureCapacity_ShouldReturnNewArrayWithIncreasedCapacity_WhenMaxIndexIsGreaterThanOrEqualToArrayLength() {
        // Given
        String[] inputArray = new String[]{"a", "b", "c"};
        int inputMaxIndex = 5; // Requires length >= 6, calculateNewLength will return 6 (2^3)

        // When
        String[] actual = ArrayUtils.ensureCapacity(inputArray, inputMaxIndex);

        // Then
        then(actual)
                .isNotSameAs(inputArray)
                .hasSize(6) // Since calculateNewLength doubles length (3 -> 6)
                .startsWith("a", "b", "c");
    }

    @Test
    void ensureCapacity_ShouldReturnNewArrayWithCopiedElements_WhenArrayHasOneElement() {
        // Given
        String[] inputArray = new String[]{"a"};
        int inputMaxIndex = 3; // Requires length >= 4, calculateNewLength will return 4 (2^2)

        // When
        String[] actual = ArrayUtils.ensureCapacity(inputArray, inputMaxIndex);

        // Then
        then(actual)
                .isNotSameAs(inputArray)
                .hasSize(4) // Since calculateNewLength doubles length (1 -> 2 -> 4)
                .startsWith("a");
    }

    @Test
    void ensureCapacity_ShouldReturnNewArrayWithExactCapacity_WhenMaxIndexRequiresMinimalIncrease() {
        // Given
        String[] inputArray = new String[]{"a", "b"};
        int inputMaxIndex = 3; // Requires length >= 4, calculateNewLength will return 4 (2^2)

        // When
        String[] actual = ArrayUtils.ensureCapacity(inputArray, inputMaxIndex);

        // Then
        then(actual)
                .isNotSameAs(inputArray)
                .hasSize(4) // Since calculateNewLength doubles length (2 -> 4)
                .startsWith("a", "b");
    }

    @Test
    void hasLength_ShouldReturnFalse_WhenBooleanArrayIsNull() {
        // Given
        boolean[] input = null;

        // When
        boolean actual = ArrayUtils.hasLength(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void hasLength_ShouldReturnFalse_WhenBooleanArrayIsEmpty() {
        // Given
        boolean[] input = new boolean[0];

        // When
        boolean actual = ArrayUtils.hasLength(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void hasLength_ShouldReturnTrue_WhenBooleanArrayIsNonEmpty() {
        // Given
        boolean[] input = {true, false};

        // When
        boolean actual = ArrayUtils.hasLength(input);

        // Then
        then(actual).isTrue();
    }

    @Test
    void hasLength_ShouldReturnFalse_WhenByteArrayIsNull() {
        // Given
        byte[] input = null;

        // When
        boolean actual = ArrayUtils.hasLength(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void hasLength_ShouldReturnFalse_WhenByteArrayIsEmpty() {
        // Given
        byte[] input = new byte[0];

        // When
        boolean actual = ArrayUtils.hasLength(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void hasLength_ShouldReturnTrue_WhenByteArrayIsNonEmpty() {
        // Given
        byte[] input = {1, 2};

        // When
        boolean actual = ArrayUtils.hasLength(input);

        // Then
        then(actual).isTrue();
    }

    @Test
    void hasLength_ShouldReturnFalse_WhenCharArrayIsNull() {
        // Given
        char[] input = null;

        // When
        boolean actual = ArrayUtils.hasLength(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void hasLength_ShouldReturnFalse_WhenCharArrayIsEmpty() {
        // Given
        char[] input = new char[0];

        // When
        boolean actual = ArrayUtils.hasLength(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void hasLength_ShouldReturnTrue_WhenCharArrayIsNonEmpty() {
        // Given
        char[] input = {'a', 'b'};

        // When
        boolean actual = ArrayUtils.hasLength(input);

        // Then
        then(actual).isTrue();
    }

    @Test
    void hasLength_ShouldReturnFalse_WhenShortArrayIsNull() {
        // Given
        short[] input = null;

        // When
        boolean actual = ArrayUtils.hasLength(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void hasLength_ShouldReturnFalse_WhenShortArrayIsEmpty() {
        // Given
        short[] input = new short[0];

        // When
        boolean actual = ArrayUtils.hasLength(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void hasLength_ShouldReturnTrue_WhenShortArrayIsNonEmpty() {
        // Given
        short[] input = {1, 2};

        // When
        boolean actual = ArrayUtils.hasLength(input);

        // Then
        then(actual).isTrue();
    }

    @Test
    void hasLength_ShouldReturnFalse_WhenIntArrayIsNull() {
        // Given
        int[] input = null;

        // When
        boolean actual = ArrayUtils.hasLength(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void hasLength_ShouldReturnFalse_WhenIntArrayIsEmpty() {
        // Given
        int[] input = new int[0];

        // When
        boolean actual = ArrayUtils.hasLength(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void hasLength_ShouldReturnTrue_WhenIntArrayIsNonEmpty() {
        // Given
        int[] input = {1, 2, 3};

        // When
        boolean actual = ArrayUtils.hasLength(input);

        // Then
        then(actual).isTrue();
    }

    @Test
    void hasLength_ShouldReturnFalse_WhenLongArrayIsNull() {
        // Given
        long[] input = null;

        // When
        boolean actual = ArrayUtils.hasLength(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void hasLength_ShouldReturnFalse_WhenLongArrayIsEmpty() {
        // Given
        long[] input = new long[0];

        // When
        boolean actual = ArrayUtils.hasLength(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void hasLength_ShouldReturnTrue_WhenLongArrayIsNonEmpty() {
        // Given
        long[] input = {1L, 2L};

        // When
        boolean actual = ArrayUtils.hasLength(input);

        // Then
        then(actual).isTrue();
    }

    @Test
    void hasLength_ShouldReturnFalse_WhenFloatArrayIsNull() {
        // Given
        float[] input = null;

        // When
        boolean actual = ArrayUtils.hasLength(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void hasLength_ShouldReturnFalse_WhenFloatArrayIsEmpty() {
        // Given
        float[] input = new float[0];

        // When
        boolean actual = ArrayUtils.hasLength(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void hasLength_ShouldReturnTrue_WhenFloatArrayIsNonEmpty() {
        // Given
        float[] input = {1.0f, 2.0f};

        // When
        boolean actual = ArrayUtils.hasLength(input);

        // Then
        then(actual).isTrue();
    }

    @Test
    void hasLength_ShouldReturnFalse_WhenDoubleArrayIsNull() {
        // Given
        double[] input = null;

        // When
        boolean actual = ArrayUtils.hasLength(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void hasLength_ShouldReturnFalse_WhenDoubleArrayIsEmpty() {
        // Given
        double[] input = new double[0];

        // When
        boolean actual = ArrayUtils.hasLength(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void hasLength_ShouldReturnTrue_WhenDoubleArrayIsNonEmpty() {
        // Given
        double[] input = {1.0, 2.0};

        // When
        boolean actual = ArrayUtils.hasLength(input);

        // Then
        then(actual).isTrue();
    }

    @Test
    void hasLength_ShouldReturnFalse_WhenStringArrayIsNull() {
        // Given
        String[] input = null;

        // When
        boolean actual = ArrayUtils.hasLength(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void hasLength_ShouldReturnFalse_WhenStringArrayIsEmpty() {
        // Given
        String[] input = new String[0];

        // When
        boolean actual = ArrayUtils.hasLength(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void hasLength_ShouldReturnTrue_WhenStringArrayIsNonEmpty() {
        // Given
        String[] input = {"a", "b"};

        // When
        boolean actual = ArrayUtils.hasLength(input);

        // Then
        then(actual).isTrue();
    }

    @Test
    void hasLength_ShouldReturnFalse_WhenGenericArrayIsNull() {
        // Given
        Integer[] input = null;

        // When
        boolean actual = ArrayUtils.hasLength(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void hasLength_ShouldReturnFalse_WhenGenericArrayIsEmpty() {
        // Given
        Integer[] input = new Integer[0];

        // When
        boolean actual = ArrayUtils.hasLength(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void hasLength_ShouldReturnTrue_WhenGenericArrayIsNonEmpty() {
        // Given
        Integer[] input = {1, 2, 3};

        // When
        boolean actual = ArrayUtils.hasLength(input);

        // Then
        then(actual).isTrue();
    }
}
