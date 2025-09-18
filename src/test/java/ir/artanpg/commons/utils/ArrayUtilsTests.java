package ir.artanpg.commons.utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenException;

/**
 * Unit tests for the {@link ArrayUtilsTests} class.
 *
 * @author Mohammad Yazdian
 */
@SuppressWarnings({"ConstantValue", "DataFlowIssue"})
class ArrayUtilsTests {

    @Test
    void add_Boolean_ShouldThrowIllegalArgumentException_WhenSourceArrayIsNull() {
        // Given
        boolean[] inputSource = null;
        boolean inputElement = true;
        int inputIndex = 0;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.add(inputSource, inputElement, inputIndex))
                .isInstanceOf(IllegalArgumentException.class)
                .withMessage("The input array cannot be null");
    }

    @Test
    void add_Boolean_ShouldThrowArrayIndexOutOfBoundsException_WhenIndexIsNegative() {
        // Given
        boolean[] inputSource = new boolean[]{true, false};
        boolean inputElement = true;
        int inputIndex = -1;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.add(inputSource, inputElement, inputIndex))
                .isInstanceOf(ArrayIndexOutOfBoundsException.class)
                .withMessage("last destination index -1 out of bounds for boolean[2]");
    }

    @Test
    void add_Boolean_ShouldThrowArrayIndexOutOfBoundsException_WhenIndexExceedsArrayLength() {
        // Given
        boolean[] inputSource = new boolean[]{true, false};
        boolean inputElement = true;
        int inputIndex = 3;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.add(inputSource, inputElement, inputIndex))
                .isInstanceOf(ArrayIndexOutOfBoundsException.class)
                .withMessage("last destination index 3 out of bounds for boolean[2]");
    }

    @Test
    void add_Boolean_ShouldAddElementAtStart_WhenIndexIsZero() {
        // Given
        boolean[] inputSource = new boolean[]{true, false};
        boolean inputElement = false;
        int inputIndex = 0;

        // When
        boolean[] actual = ArrayUtils.add(inputSource, inputElement, inputIndex);

        // Then
        then(actual)
                .hasSize(3)
                .containsExactly(false, true, false);
    }

    @Test
    void add_Boolean_ShouldAddElementAtEnd_WhenIndexEqualsArrayLength() {
        // Given
        boolean[] inputSource = new boolean[]{true, false};
        boolean inputElement = true;
        int inputIndex = 2;

        // When
        boolean[] actual = ArrayUtils.add(inputSource, inputElement, inputIndex);

        // Then
        then(actual)
                .hasSize(3)
                .containsExactly(true, false, true);
    }

    @Test
    void add_Boolean_ShouldAddElementInMiddle_WhenIndexIsBetweenZeroAndLength() {
        // Given
        boolean[] inputSource = new boolean[]{true, false, true};
        boolean inputElement = false;
        int inputIndex = 1;

        // When
        boolean[] actual = ArrayUtils.add(inputSource, inputElement, inputIndex);

        // Then
        then(actual)
                .hasSize(4)
                .containsExactly(true, false, false, true);
    }

    @Test
    void add_Boolean_ShouldAddElementToEmptyArray_WhenArrayIsEmpty() {
        // Given
        boolean[] inputSource = new boolean[0];
        boolean inputElement = true;
        int inputIndex = 0;

        // When
        boolean[] actual = ArrayUtils.add(inputSource, inputElement, inputIndex);

        // Then
        then(actual)
                .hasSize(1)
                .containsExactly(true);
    }

    @Test
    void add_Byte_ShouldThrowIllegalArgumentException_WhenByteSourceArrayIsNull() {
        // Given
        byte[] inputSource = null;
        byte inputElement = -127;
        int inputIndex = 0;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.add(inputSource, inputElement, inputIndex))
                .isInstanceOf(IllegalArgumentException.class)
                .withMessage("The input array cannot be null");
    }

    @Test
    void add_Byte_ShouldThrowArrayIndexOutOfBoundsException_WhenByteIndexIsNegative() {
        // Given
        byte[] inputSource = new byte[]{-127, -126};
        byte inputElement = -125;
        int inputIndex = -1;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.add(inputSource, inputElement, inputIndex))
                .isInstanceOf(ArrayIndexOutOfBoundsException.class)
                .withMessage("last destination index -1 out of bounds for byte[2]");
    }

    @Test
    void add_Byte_ShouldThrowArrayIndexOutOfBoundsException_WhenIndexExceedsArrayLength() {
        // Given
        byte[] inputSource = new byte[]{-127, -126};
        byte inputElement = -125;
        int inputIndex = 3;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.add(inputSource, inputElement, inputIndex))
                .isInstanceOf(ArrayIndexOutOfBoundsException.class)
                .withMessage("last destination index 3 out of bounds for byte[2]");
    }

    @Test
    void add_Byte_ShouldAddElementAtStart_WhenIndexIsZero() {
        // Given
        byte[] inputSource = new byte[]{-127, -126};
        byte inputElement = -125;
        int inputIndex = 0;

        // When
        byte[] actual = ArrayUtils.add(inputSource, inputElement, inputIndex);

        // Then
        then(actual)
                .hasSize(3)
                .containsExactly(-125, -127, -126);
    }

    @Test
    void add_Byte_ShouldAddElementAtEnd_WhenIndexEqualsArrayLength() {
        // Given
        byte[] inputSource = new byte[]{-127, -126};
        byte inputElement = -125;
        int inputIndex = 2;

        // When
        byte[] actual = ArrayUtils.add(inputSource, inputElement, inputIndex);

        // Then
        then(actual)
                .hasSize(3)
                .containsExactly(-127, -126, -125);
    }

    @Test
    void add_Byte_ShouldAddElementInMiddle_WhenIndexIsBetweenZeroAndLength() {
        // Given
        byte[] inputSource = new byte[]{-127, -126, -125};
        byte inputElement = -124;
        int inputIndex = 1;

        // When
        byte[] actual = ArrayUtils.add(inputSource, inputElement, inputIndex);

        // Then
        then(actual)
                .hasSize(4)
                .containsExactly(-127, -124, -126, -125);
    }

    @Test
    void add_Byte_ShouldAddElementToEmptyArray_WhenArrayIsEmpty() {
        // Given
        byte[] inputSource = new byte[0];
        byte inputElement = -127;
        int inputIndex = 0;

        // When
        byte[] actual = ArrayUtils.add(inputSource, inputElement, inputIndex);

        // Then
        then(actual)
                .hasSize(1)
                .containsExactly(-127);
    }

    @Test
    void add_Character_ShouldThrowIllegalArgumentException_WhenSourceArrayIsNull() {
        // Given
        char[] inputSource = null;
        char inputElement = 'a';
        int inputIndex = 0;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.add(inputSource, inputElement, inputIndex))
                .isInstanceOf(IllegalArgumentException.class)
                .withMessage("The input array cannot be null");
    }

    @Test
    void add_Character_ShouldThrowArrayIndexOutOfBoundsException_WhenIndexIsNegative() {
        // Given
        char[] inputSource = new char[]{'a', 'b'};
        char inputElement = 'c';
        int inputIndex = -1;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.add(inputSource, inputElement, inputIndex))
                .isInstanceOf(ArrayIndexOutOfBoundsException.class)
                .withMessage("last destination index -1 out of bounds for char[2]");
    }

    @Test
    void add_Character_ShouldThrowArrayIndexOutOfBoundsException_WhenIndexExceedsArrayLength() {
        // Given
        char[] inputSource = new char[]{'a', 'b'};
        char inputElement = 'c';
        int inputIndex = 3;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.add(inputSource, inputElement, inputIndex))
                .isInstanceOf(ArrayIndexOutOfBoundsException.class)
                .withMessage("last destination index 3 out of bounds for char[2]");
    }

    @Test
    void add_Character_ShouldAddElementAtStart_WhenIndexIsZero() {
        // Given
        char[] inputSource = new char[]{'a', 'b'};
        char inputElement = 'c';
        int inputIndex = 0;

        // When
        char[] actual = ArrayUtils.add(inputSource, inputElement, inputIndex);

        // Then
        then(actual)
                .hasSize(3)
                .containsExactly('c', 'a', 'b');
    }

    @Test
    void add_Character_ShouldAddElementAtEnd_WhenIndexEqualsArrayLength() {
        // Given
        char[] inputSource = new char[]{'a', 'b'};
        char inputElement = 'c';
        int inputIndex = 2;

        // When
        char[] actual = ArrayUtils.add(inputSource, inputElement, inputIndex);

        // Then
        then(actual)
                .hasSize(3)
                .containsExactly('a', 'b', 'c');
    }

    @Test
    void add_Character_ShouldAddElementInMiddle_WhenIndexIsBetweenZeroAndLength() {
        // Given
        char[] inputSource = new char[]{'a', 'b', 'c'};
        char inputElement = 'd';
        int inputIndex = 1;

        // When
        char[] actual = ArrayUtils.add(inputSource, inputElement, inputIndex);

        // Then
        then(actual)
                .hasSize(4)
                .containsExactly('a', 'd', 'b', 'c');
    }

    @Test
    void add_Character_ShouldAddElementToEmptyArray_WhenArrayIsEmpty() {
        // Given
        char[] inputSource = new char[0];
        char inputElement = 'a';
        int inputIndex = 0;

        // When
        char[] actual = ArrayUtils.add(inputSource, inputElement, inputIndex);

        // Then
        then(actual)
                .hasSize(1)
                .containsExactly('a');
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
