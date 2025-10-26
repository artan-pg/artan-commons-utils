package ir.artanpg.commons.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenException;

/**
 * Unit tests for the {@link ArrayUtils} class.
 *
 * @author Mohammad Yazdian
 */
@SuppressWarnings({"ConstantValue", "DataFlowIssue"})
class ArrayUtilsTests {

    @Test
    void addBoolean_ShouldThrowIllegalArgumentException_WhenSourceArrayIsNull() {
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
    void addBoolean_ShouldThrowIndexOutOfBoundsException_WhenIndexIsNegative() {
        // Given
        boolean[] inputSource = new boolean[]{true, false};
        boolean inputElement = true;
        int inputIndex = -1;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.add(inputSource, inputElement, inputIndex))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .withMessage("Index -1 out of bounds for length 2");
    }

    @Test
    void addBoolean_ShouldThrowIndexOutOfBoundsException_WhenIndexExceedsArrayLength() {
        // Given
        boolean[] inputSource = new boolean[]{true, false};
        boolean inputElement = true;
        int inputIndex = 3;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.add(inputSource, inputElement, inputIndex))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .withMessage("Index 3 out of bounds for length 2");
    }

    @Test
    void addBoolean_ShouldAddElementAtStart_WhenIndexIsZero() {
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
    void addBoolean_ShouldAddElementAtEnd_WhenIndexEqualsArrayLength() {
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
    void addBoolean_ShouldAddElementInMiddle_WhenIndexIsBetweenZeroAndLength() {
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
    void addBoolean_ShouldAddElementToEmptyArray_WhenArrayIsEmpty() {
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
    void addByte_ShouldThrowIllegalArgumentException_WhenByteSourceArrayIsNull() {
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
    void addByte_ShouldThrowIndexOutOfBoundsException_WhenByteIndexIsNegative() {
        // Given
        byte[] inputSource = new byte[]{-128, -127};
        byte inputElement = -125;
        int inputIndex = -1;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.add(inputSource, inputElement, inputIndex))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .withMessage("Index -1 out of bounds for length 2");
    }

    @Test
    void addByte_ShouldThrowIndexOutOfBoundsException_WhenIndexExceedsArrayLength() {
        // Given
        byte[] inputSource = new byte[]{-128, -127};
        byte inputElement = -125;
        int inputIndex = 3;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.add(inputSource, inputElement, inputIndex))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .withMessage("Index 3 out of bounds for length 2");
    }

    @Test
    void addByte_ShouldAddElementAtStart_WhenIndexIsZero() {
        // Given
        byte[] inputSource = new byte[]{-128, -127};
        byte inputElement = -125;
        int inputIndex = 0;

        // When
        byte[] actual = ArrayUtils.add(inputSource, inputElement, inputIndex);

        // Then
        then(actual)
                .hasSize(3)
                .containsExactly(-125, -128, -127);
    }

    @Test
    void addByte_ShouldAddElementAtEnd_WhenIndexEqualsArrayLength() {
        // Given
        byte[] inputSource = new byte[]{-128, -127};
        byte inputElement = -125;
        int inputIndex = 2;

        // When
        byte[] actual = ArrayUtils.add(inputSource, inputElement, inputIndex);

        // Then
        then(actual)
                .hasSize(3)
                .containsExactly(-128, -127, -125);
    }

    @Test
    void addByte_ShouldAddElementInMiddle_WhenIndexIsBetweenZeroAndLength() {
        // Given
        byte[] inputSource = new byte[]{-128, -127, -126};
        byte inputElement = -125;
        int inputIndex = 1;

        // When
        byte[] actual = ArrayUtils.add(inputSource, inputElement, inputIndex);

        // Then
        then(actual)
                .hasSize(4)
                .containsExactly(-128, -125, -127, -126);
    }

    @Test
    void addByte_ShouldAddElementToEmptyArray_WhenArrayIsEmpty() {
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
    void addChar_ShouldThrowIllegalArgumentException_WhenSourceArrayIsNull() {
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
    void addChar_ShouldThrowIndexOutOfBoundsException_WhenIndexIsNegative() {
        // Given
        char[] inputSource = new char[]{'a', 'b'};
        char inputElement = 'c';
        int inputIndex = -1;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.add(inputSource, inputElement, inputIndex))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .withMessage("Index -1 out of bounds for length 2");
    }

    @Test
    void addChar_ShouldThrowIndexOutOfBoundsException_WhenIndexExceedsArrayLength() {
        // Given
        char[] inputSource = new char[]{'a', 'b'};
        char inputElement = 'c';
        int inputIndex = 3;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.add(inputSource, inputElement, inputIndex))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .withMessage("Index 3 out of bounds for length 2");
    }

    @Test
    void addChar_ShouldAddElementAtStart_WhenIndexIsZero() {
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
    void addChar_ShouldAddElementAtEnd_WhenIndexEqualsArrayLength() {
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
    void addChar_ShouldAddElementInMiddle_WhenIndexIsBetweenZeroAndLength() {
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
    void addChar_ShouldAddElementToEmptyArray_WhenArrayIsEmpty() {
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
    void addShort_ShouldThrowIllegalArgumentException_WhenSourceArrayIsNull() {
        // Given
        short[] inputSource = null;
        short inputElement = 32767;
        int inputIndex = 0;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.add(inputSource, inputElement, inputIndex))
                .isInstanceOf(IllegalArgumentException.class)
                .withMessage("The input array cannot be null");
    }

    @Test
    void addShort_ShouldThrowIndexOutOfBoundsException_WhenIndexIsNegative() {
        // Given
        short[] inputSource = new short[]{32767, 32766};
        short inputElement = 32765;
        int inputIndex = -1;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.add(inputSource, inputElement, inputIndex))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .withMessage("Index -1 out of bounds for length 2");
    }

    @Test
    void addShort_ShouldThrowIndexOutOfBoundsException_WhenIndexExceedsArrayLength() {
        // Given
        short[] inputSource = new short[]{32767, 32766};
        short inputElement = 32765;
        int inputIndex = 3;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.add(inputSource, inputElement, inputIndex))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .withMessage("Index 3 out of bounds for length 2");
    }

    @Test
    void addShort_ShouldAddElementAtStart_WhenIndexIsZero() {
        // Given
        short[] inputSource = new short[]{32767, 32766};
        short inputElement = 32765;
        int inputIndex = 0;

        // When
        short[] actual = ArrayUtils.add(inputSource, inputElement, inputIndex);

        // Then
        then(actual)
                .hasSize(3)
                .containsExactly(32765, 32767, 32766);
    }

    @Test
    void addShort_ShouldAddElementAtEnd_WhenIndexEqualsArrayLength() {
        // Given
        short[] inputSource = new short[]{32767, 32766};
        short inputElement = 32765;
        int inputIndex = 2;

        // When
        short[] actual = ArrayUtils.add(inputSource, inputElement, inputIndex);

        // Then
        then(actual)
                .hasSize(3)
                .containsExactly(32767, 32766, 32765);
    }

    @Test
    void addShort_ShouldAddElementInMiddle_WhenIndexIsBetweenZeroAndLength() {
        // Given
        short[] inputSource = new short[]{32767, 32766, 32765};
        short inputElement = 32764;
        int inputIndex = 1;

        // When
        short[] actual = ArrayUtils.add(inputSource, inputElement, inputIndex);

        // Then
        then(actual)
                .hasSize(4)
                .containsExactly(32767, 32764, 32766, 32765);
    }

    @Test
    void addShort_ShouldAddElementToEmptyArray_WhenArrayIsEmpty() {
        // Given
        short[] inputSource = new short[0];
        short inputElement = 32767;
        int inputIndex = 0;

        // When
        short[] actual = ArrayUtils.add(inputSource, inputElement, inputIndex);

        // Then
        then(actual)
                .hasSize(1)
                .containsExactly(32767);
    }

    @Test
    void addInt_ShouldThrowIllegalArgumentException_WhenSourceArrayIsNull() {
        // Given
        int[] inputSource = null;
        int inputElement = 1;
        int inputIndex = 0;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.add(inputSource, inputElement, inputIndex))
                .isInstanceOf(IllegalArgumentException.class)
                .withMessage("The input array cannot be null");
    }

    @Test
    void addInt_ShouldThrowIndexOutOfBoundsException_WhenIndexIsNegative() {
        // Given
        int[] inputSource = new int[]{1, 2};
        int inputElement = 3;
        int inputIndex = -1;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.add(inputSource, inputElement, inputIndex))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .withMessage("Index -1 out of bounds for length 2");
    }

    @Test
    void addInt_ShouldThrowIndexOutOfBoundsException_WhenIndexExceedsArrayLength() {
        // Given
        int[] inputSource = new int[]{1, 2};
        int inputElement = 3;
        int inputIndex = 3;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.add(inputSource, inputElement, inputIndex))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .withMessage("Index 3 out of bounds for length 2");
    }

    @Test
    void addInt_ShouldAddElementAtStart_WhenIndexIsZero() {
        // Given
        int[] inputSource = new int[]{1, 2};
        int inputElement = 3;
        int inputIndex = 0;

        // When
        int[] actual = ArrayUtils.add(inputSource, inputElement, inputIndex);

        // Then
        then(actual)
                .hasSize(3)
                .containsExactly(3, 1, 2);
    }

    @Test
    void addInt_ShouldAddElementAtEnd_WhenIndexEqualsArrayLength() {
        // Given
        int[] inputSource = new int[]{1, 2};
        int inputElement = 3;
        int inputIndex = 2;

        // When
        int[] actual = ArrayUtils.add(inputSource, inputElement, inputIndex);

        // Then
        then(actual)
                .hasSize(3)
                .containsExactly(1, 2, 3);
    }

    @Test
    void addInt_ShouldAddElementInMiddle_WhenIndexIsBetweenZeroAndLength() {
        // Given
        int[] inputSource = new int[]{1, 2, 3};
        int inputElement = 4;
        int inputIndex = 1;

        // When
        int[] actual = ArrayUtils.add(inputSource, inputElement, inputIndex);

        // Then
        then(actual)
                .hasSize(4)
                .containsExactly(1, 4, 2, 3);
    }

    @Test
    void addInt_ShouldAddElementToEmptyArray_WhenArrayIsEmpty() {
        // Given
        int[] inputSource = new int[0];
        int inputElement = 1;
        int inputIndex = 0;

        // When
        int[] actual = ArrayUtils.add(inputSource, inputElement, inputIndex);

        // Then
        then(actual)
                .hasSize(1)
                .containsExactly(1);
    }

    @Test
    void addLong_ShouldThrowIllegalArgumentException_WhenSourceArrayIsNull() {
        // Given
        long[] inputSource = null;
        long inputElement = 1L;
        int inputIndex = 0;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.add(inputSource, inputElement, inputIndex))
                .isInstanceOf(IllegalArgumentException.class)
                .withMessage("The input array cannot be null");
    }

    @Test
    void addLong_ShouldThrowIndexOutOfBoundsException_WhenIndexIsNegative() {
        // Given
        long[] inputSource = new long[]{1L, 2L};
        long inputElement = 3L;
        int inputIndex = -1;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.add(inputSource, inputElement, inputIndex))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .withMessage("Index -1 out of bounds for length 2");
    }

    @Test
    void addLong_ShouldThrowIndexOutOfBoundsException_WhenIndexExceedsArrayLength() {
        // Given
        long[] inputSource = new long[]{1L, 2L};
        long inputElement = 3L;
        int inputIndex = 3;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.add(inputSource, inputElement, inputIndex))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .withMessage("Index 3 out of bounds for length 2");
    }

    @Test
    void addLong_ShouldAddElementAtStart_WhenIndexIsZero() {
        // Given
        long[] inputSource = new long[]{1L, 2L};
        long inputElement = 3L;
        int inputIndex = 0;

        // When
        long[] actual = ArrayUtils.add(inputSource, inputElement, inputIndex);

        // Then
        then(actual)
                .hasSize(3)
                .containsExactly(3L, 1L, 2L);
    }

    @Test
    void addLong_ShouldAddElementAtEnd_WhenIndexEqualsArrayLength() {
        // Given
        long[] inputSource = new long[]{1L, 2L};
        long inputElement = 3L;
        int inputIndex = 2;

        // When
        long[] actual = ArrayUtils.add(inputSource, inputElement, inputIndex);

        // Then
        then(actual)
                .hasSize(3)
                .containsExactly(1L, 2L, 3L);
    }

    @Test
    void addLong_ShouldAddElementInMiddle_WhenIndexIsBetweenZeroAndLength() {
        // Given
        long[] inputSource = new long[]{1L, 2L, 3L};
        long inputElement = 4L;
        int inputIndex = 1;

        // When
        long[] actual = ArrayUtils.add(inputSource, inputElement, inputIndex);

        // Then
        then(actual)
                .hasSize(4)
                .containsExactly(1L, 4L, 2L, 3L);
    }

    @Test
    void addLong_ShouldAddElementToEmptyArray_WhenArrayIsEmpty() {
        // Given
        long[] inputSource = new long[0];
        long inputElement = 1L;
        int inputIndex = 0;

        // When
        long[] actual = ArrayUtils.add(inputSource, inputElement, inputIndex);

        // Then
        then(actual)
                .hasSize(1)
                .containsExactly(1L);
    }

    @Test
    void addFloat_ShouldThrowIllegalArgumentException_WhenSourceArrayIsNull() {
        // Given
        float[] inputSource = null;
        float inputElement = 1.0F;
        int inputIndex = 0;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.add(inputSource, inputElement, inputIndex))
                .isInstanceOf(IllegalArgumentException.class)
                .withMessage("The input array cannot be null");
    }

    @Test
    void addFloat_ShouldThrowIndexOutOfBoundsException_WhenIndexIsNegative() {
        // Given
        float[] inputSource = new float[]{1.0F, 2.0F};
        float inputElement = 3.0F;
        int inputIndex = -1;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.add(inputSource, inputElement, inputIndex))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .withMessage("Index -1 out of bounds for length 2");
    }

    @Test
    void addFloat_ShouldThrowIndexOutOfBoundsException_WhenIndexExceedsArrayLength() {
        // Given
        float[] inputSource = new float[]{1.0F, 2.0F};
        float inputElement = 3.0F;
        int inputIndex = 3;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.add(inputSource, inputElement, inputIndex))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .withMessage("Index 3 out of bounds for length 2");
    }

    @Test
    void addFloat_ShouldAddElementAtStart_WhenIndexIsZero() {
        // Given
        float[] inputSource = new float[]{1.0F, 2.0F};
        float inputElement = 3.0F;
        int inputIndex = 0;

        // When
        float[] actual = ArrayUtils.add(inputSource, inputElement, inputIndex);

        // Then
        then(actual)
                .hasSize(3)
                .containsExactly(3.0F, 1.0F, 2.0F);
    }

    @Test
    void addFloat_ShouldAddElementAtEnd_WhenIndexEqualsArrayLength() {
        // Given
        float[] inputSource = new float[]{1.0F, 2.0F};
        float inputElement = 3.0F;
        int inputIndex = 2;

        // When
        float[] actual = ArrayUtils.add(inputSource, inputElement, inputIndex);

        // Then
        then(actual)
                .hasSize(3)
                .containsExactly(1.0F, 2.0F, 3.0F);
    }

    @Test
    void addFloat_ShouldAddElementInMiddle_WhenIndexIsBetweenZeroAndLength() {
        // Given
        float[] inputSource = new float[]{1.0F, 2.0F, 3.0F};
        float inputElement = 4.0F;
        int inputIndex = 1;

        // When
        float[] actual = ArrayUtils.add(inputSource, inputElement, inputIndex);

        // Then
        then(actual)
                .hasSize(4)
                .containsExactly(1.0F, 4.0F, 2.0F, 3.0F);
    }

    @Test
    void addFloat_ShouldAddElementToEmptyArray_WhenArrayIsEmpty() {
        // Given
        float[] inputSource = new float[0];
        float inputElement = 1.0F;
        int inputIndex = 0;

        // When
        float[] actual = ArrayUtils.add(inputSource, inputElement, inputIndex);

        // Then
        then(actual)
                .hasSize(1)
                .containsExactly(1.0F);
    }

    @Test
    void addDouble_ShouldThrowIllegalArgumentException_WhenSourceArrayIsNull() {
        // Given
        double[] inputSource = null;
        double inputElement = 1.0;
        int inputIndex = 0;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.add(inputSource, inputElement, inputIndex))
                .isInstanceOf(IllegalArgumentException.class)
                .withMessage("The input array cannot be null");
    }

    @Test
    void addDouble_ShouldThrowIndexOutOfBoundsException_WhenIndexIsNegative() {
        // Given
        double[] inputSource = new double[]{1.0, 2.0};
        double inputElement = 3.0;
        int inputIndex = -1;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.add(inputSource, inputElement, inputIndex))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .withMessage("Index -1 out of bounds for length 2");
    }

    @Test
    void addDouble_ShouldThrowIndexOutOfBoundsException_WhenIndexExceedsArrayLength() {
        // Given
        double[] inputSource = new double[]{1.0, 2.0};
        double inputElement = 3.0;
        int inputIndex = 3;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.add(inputSource, inputElement, inputIndex))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .withMessage("Index 3 out of bounds for length 2");
    }

    @Test
    void addDouble_ShouldAddElementAtStart_WhenIndexIsZero() {
        // Given
        double[] inputSource = new double[]{1.0, 2.0};
        double inputElement = 3.0;
        int inputIndex = 0;

        // When
        double[] actual = ArrayUtils.add(inputSource, inputElement, inputIndex);

        // Then
        then(actual)
                .hasSize(3)
                .containsExactly(3.0, 1.0, 2.0);
    }

    @Test
    void addDouble_ShouldAddElementAtEnd_WhenIndexEqualsArrayLength() {
        // Given
        double[] inputSource = new double[]{1.0, 2.0};
        double inputElement = 3.0;
        int inputIndex = 2;

        // When
        double[] actual = ArrayUtils.add(inputSource, inputElement, inputIndex);

        // Then
        then(actual)
                .hasSize(3)
                .containsExactly(1.0, 2.0, 3.0);
    }

    @Test
    void addDouble_ShouldAddElementInMiddle_WhenIndexIsBetweenZeroAndLength() {
        // Given
        double[] inputSource = new double[]{1.0, 2.0, 3.0};
        double inputElement = 4.0;
        int inputIndex = 1;

        // When
        double[] actual = ArrayUtils.add(inputSource, inputElement, inputIndex);

        // Then
        then(actual)
                .hasSize(4)
                .containsExactly(1.0, 4.0, 2.0, 3.0);
    }

    @Test
    void addDouble_ShouldAddElementToEmptyArray_WhenArrayIsEmpty() {
        // Given
        double[] inputSource = new double[0];
        double inputElement = 1.0;
        int inputIndex = 0;

        // When
        double[] actual = ArrayUtils.add(inputSource, inputElement, inputIndex);

        // Then
        then(actual)
                .hasSize(1)
                .containsExactly(1.0);
    }

    @Test
    void addGeneric_ShouldThrowIllegalArgumentException_WhenSourceArrayIsNull() {
        // Given
        String[] inputSource = null;
        String inputElement = "c";
        int inputIndex = 0;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.add(inputSource, inputElement, inputIndex))
                .isInstanceOf(IllegalArgumentException.class)
                .withMessage("The input array cannot be null");
    }

    @Test
    void addGeneric_ShouldThrowIndexOutOfBoundsException_WhenIndexIsNegative() {
        // Given
        String[] inputSource = new String[]{"a", "b"};
        String inputElement = "c";
        int inputIndex = -1;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.add(inputSource, inputElement, inputIndex))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .withMessage("Index -1 out of bounds for length 2");
    }

    @Test
    void addGeneric_ShouldThrowIndexOutOfBoundsException_WhenIndexExceedsArrayLength() {
        // Given
        String[] inputSource = new String[]{"a", "b"};
        String inputElement = "c";
        int inputIndex = 3;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.add(inputSource, inputElement, inputIndex))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .withMessage("Index 3 out of bounds for length 2");
    }

    @Test
    void addGeneric_ShouldAddElementAtStart_WhenIndexIsZero() {
        // Given
        String[] inputSource = new String[]{"a", "b"};
        String inputElement = "c";
        int inputIndex = 0;

        // When
        String[] actual = ArrayUtils.add(inputSource, inputElement, inputIndex);

        // Then
        then(actual)
                .hasSize(3)
                .containsExactly("c", "a", "b");
    }

    @Test
    void addGeneric_ShouldAddElementAtEnd_WhenIndexEqualsArrayLength() {
        // Given
        String[] inputSource = new String[]{"a", "b"};
        String inputElement = "c";
        int inputIndex = 2;

        // When
        String[] actual = ArrayUtils.add(inputSource, inputElement, inputIndex);

        // Then
        then(actual)
                .hasSize(3)
                .containsExactly("a", "b", "c");
    }

    @Test
    void addGeneric_ShouldAddElementInMiddle_WhenIndexIsBetweenZeroAndLength() {
        // Given
        String[] inputSource = new String[]{"a", "b", "c"};
        String inputElement = "d";
        int inputIndex = 1;

        // When
        String[] actual = ArrayUtils.add(inputSource, inputElement, inputIndex);

        // Then
        then(actual)
                .hasSize(4)
                .containsExactly("a", "d", "b", "c");
    }

    @Test
    void addGeneric_ShouldAddElementToEmptyArray_WhenArrayIsEmpty() {
        // Given
        String[] inputSource = new String[0];
        String inputElement = "a";
        int inputIndex = 0;

        // When
        String[] actual = ArrayUtils.add(inputSource, inputElement, inputIndex);

        // Then
        then(actual)
                .hasSize(1)
                .containsExactly("a");
    }

    @Test
    void addFirstBoolean_ShouldThrowIllegalArgumentException_WhenSourceArrayIsNull() {
        // Given
        boolean[] inputSource = null;
        boolean inputElement = true;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.addFirst(inputSource, inputElement))
                .isInstanceOf(IllegalArgumentException.class)
                .withMessage("The input array cannot be null");
    }

    @Test
    void addFirstBoolean_ShouldAddElementAtStart_WhenArrayIsNotEmpty() {
        // Given
        boolean[] inputSource = new boolean[]{true, false};
        boolean inputElement = false;

        // When
        boolean[] actual = ArrayUtils.addFirst(inputSource, inputElement);

        // Then
        then(actual)
                .hasSize(3)
                .containsExactly(false, true, false);
    }

    @Test
    void addFirstBoolean_ShouldAddElementToEmptyArray_WhenArrayIsEmpty() {
        // Given
        boolean[] inputSource = new boolean[0];
        boolean inputElement = true;

        // When
        boolean[] actual = ArrayUtils.addFirst(inputSource, inputElement);

        // Then
        then(actual)
                .hasSize(1)
                .containsExactly(true);
    }

    @Test
    void addFirstByte_ShouldThrowIllegalArgumentException_WhenSourceArrayIsNull() {
        // Given
        byte[] inputSource = null;
        byte inputElement = 1;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.addFirst(inputSource, inputElement))
                .isInstanceOf(IllegalArgumentException.class)
                .withMessage("The input array cannot be null");
    }

    @Test
    void addFirstByte_ShouldAddElementAtStart_WhenArrayIsNotEmpty() {
        // Given
        byte[] inputSource = new byte[]{-128, -127};
        byte inputElement = -126;

        // When
        byte[] actual = ArrayUtils.addFirst(inputSource, inputElement);

        // Then
        then(actual)
                .hasSize(3)
                .containsExactly(-126, -128, -127);
    }

    @Test
    void addFirstByte_ShouldAddElementToEmptyArray_WhenArrayIsEmpty() {
        // Given
        byte[] inputSource = new byte[0];
        byte inputElement = 1;

        // When
        byte[] actual = ArrayUtils.addFirst(inputSource, inputElement);

        // Then
        then(actual)
                .hasSize(1)
                .containsExactly(1);
    }

    @Test
    void addFirstChar_ShouldThrowIllegalArgumentException_WhenSourceArrayIsNull() {
        // Given
        char[] inputSource = null;
        char inputElement = 'a';

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.addFirst(inputSource, inputElement))
                .isInstanceOf(IllegalArgumentException.class)
                .withMessage("The input array cannot be null");
    }

    @Test
    void addFirstChar_ShouldAddElementAtStart_WhenArrayIsNotEmpty() {
        // Given
        char[] inputSource = new char[]{'a', 'b'};
        char inputElement = 'c';

        // When
        char[] actual = ArrayUtils.addFirst(inputSource, inputElement);

        // Then
        then(actual)
                .hasSize(3)
                .containsExactly('c', 'a', 'b');
    }

    @Test
    void addFirstChar_ShouldAddElementToEmptyArray_WhenArrayIsEmpty() {
        // Given
        char[] inputSource = new char[0];
        char inputElement = 'a';

        // When
        char[] actual = ArrayUtils.addFirst(inputSource, inputElement);

        // Then
        then(actual)
                .hasSize(1)
                .containsExactly('a');
    }

    @Test
    void addFirstShort_ShouldThrowIllegalArgumentException_WhenSourceArrayIsNull() {
        // Given
        short[] inputSource = null;
        short inputElement = 1;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.addFirst(inputSource, inputElement))
                .isInstanceOf(IllegalArgumentException.class)
                .withMessage("The input array cannot be null");
    }

    @Test
    void addFirstShort_ShouldAddElementAtStart_WhenArrayIsNotEmpty() {
        // Given
        short[] inputSource = new short[]{32767, 32766};
        short inputElement = 32765;

        // When
        short[] actual = ArrayUtils.addFirst(inputSource, inputElement);

        // Then
        then(actual)
                .hasSize(3)
                .containsExactly(32765, 32767, 32766);
    }

    @Test
    void addFirstShort_ShouldAddElementToEmptyArray_WhenArrayIsEmpty() {
        // Given
        short[] inputSource = new short[0];
        short inputElement = 1;

        // When
        short[] actual = ArrayUtils.addFirst(inputSource, inputElement);

        // Then
        then(actual)
                .hasSize(1)
                .containsExactly(1);
    }

    @Test
    void addFirstInt_ShouldThrowIllegalArgumentException_WhenSourceArrayIsNull() {
        // Given
        int[] inputSource = null;
        int inputElement = 1;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.addFirst(inputSource, inputElement))
                .isInstanceOf(IllegalArgumentException.class)
                .withMessage("The input array cannot be null");
    }

    @Test
    void addFirstInt_ShouldAddElementAtStart_WhenArrayIsNotEmpty() {
        // Given
        int[] inputSource = new int[]{1, 2};
        int inputElement = 3;

        // When
        int[] actual = ArrayUtils.addFirst(inputSource, inputElement);

        // Then
        then(actual)
                .hasSize(3)
                .containsExactly(3, 1, 2);
    }

    @Test
    void addFirstInt_ShouldAddElementToEmptyArray_WhenArrayIsEmpty() {
        // Given
        int[] inputSource = new int[0];
        int inputElement = 1;

        // When
        int[] actual = ArrayUtils.addFirst(inputSource, inputElement);

        // Then
        then(actual)
                .hasSize(1)
                .containsExactly(1);
    }

    @Test
    void addFirstLong_ShouldThrowIllegalArgumentException_WhenSourceArrayIsNull() {
        // Given
        long[] inputSource = null;
        long inputElement = 1L;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.addFirst(inputSource, inputElement))
                .isInstanceOf(IllegalArgumentException.class)
                .withMessage("The input array cannot be null");
    }

    @Test
    void addFirstLong_ShouldAddElementAtStart_WhenArrayIsNotEmpty() {
        // Given
        long[] inputSource = new long[]{1L, 2L};
        long inputElement = 3L;

        // When
        long[] actual = ArrayUtils.addFirst(inputSource, inputElement);

        // Then
        then(actual)
                .hasSize(3)
                .containsExactly(3L, 1L, 2L);
    }

    @Test
    void addFirstLong_ShouldAddElementToEmptyArray_WhenArrayIsEmpty() {
        // Given
        long[] inputSource = new long[0];
        long inputElement = 1L;

        // When
        long[] actual = ArrayUtils.addFirst(inputSource, inputElement);

        // Then
        then(actual)
                .hasSize(1)
                .containsExactly(1L);
    }

    @Test
    void addFirstFloat_ShouldThrowIllegalArgumentException_WhenSourceArrayIsNull() {
        // Given
        float[] inputSource = null;
        float inputElement = 1.0F;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.addFirst(inputSource, inputElement))
                .isInstanceOf(IllegalArgumentException.class)
                .withMessage("The input array cannot be null");
    }

    @Test
    void addFirstFloat_ShouldAddElementAtStart_WhenArrayIsNotEmpty() {
        // Given
        float[] inputSource = new float[]{1.0F, 2.0F};
        float inputElement = 3.0F;

        // When
        float[] actual = ArrayUtils.addFirst(inputSource, inputElement);

        // Then
        then(actual)
                .hasSize(3)
                .containsExactly(3.0F, 1.0F, 2.0F);
    }

    @Test
    void addFirstFloat_ShouldAddElementToEmptyArray_WhenArrayIsEmpty() {
        // Given
        float[] inputSource = new float[0];
        float inputElement = 1.0F;

        // When
        float[] actual = ArrayUtils.addFirst(inputSource, inputElement);

        // Then
        then(actual)
                .hasSize(1)
                .containsExactly(1.0F);
    }

    @Test
    void addFirstDouble_ShouldThrowIllegalArgumentException_WhenSourceArrayIsNull() {
        // Given
        double[] inputSource = null;
        double inputElement = 1.0;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.addFirst(inputSource, inputElement))
                .isInstanceOf(IllegalArgumentException.class)
                .withMessage("The input array cannot be null");
    }

    @Test
    void addFirstDouble_ShouldAddElementAtStart_WhenArrayIsNotEmpty() {
        // Given
        double[] inputSource = new double[]{1.0, 2.0};
        double inputElement = 3.0;

        // When
        double[] actual = ArrayUtils.addFirst(inputSource, inputElement);

        // Then
        then(actual)
                .hasSize(3)
                .containsExactly(3.0, 1.0, 2.0);
    }

    @Test
    void addFirstDouble_ShouldAddElementToEmptyArray_WhenArrayIsEmpty() {
        // Given
        double[] inputSource = new double[0];
        double inputElement = 1.0;

        // When
        double[] actual = ArrayUtils.addFirst(inputSource, inputElement);

        // Then
        then(actual)
                .hasSize(1)
                .containsExactly(1.0);
    }

    @Test
    void addFirstGeneric_ShouldThrowIllegalArgumentException_WhenSourceArrayIsNull() {
        // Given
        String[] inputSource = null;
        String inputElement = "c";

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.addFirst(inputSource, inputElement))
                .isInstanceOf(IllegalArgumentException.class)
                .withMessage("The input array cannot be null");
    }

    @Test
    void addFirstGeneric_ShouldAddElementAtStart_WhenArrayIsNotEmpty() {
        // Given
        String[] inputSource = new String[]{"a", "b"};
        String inputElement = "c";

        // When
        String[] actual = ArrayUtils.addFirst(inputSource, inputElement);

        // Then
        then(actual)
                .hasSize(3)
                .containsExactly("c", "a", "b");
    }

    @Test
    void addFirstGeneric_ShouldAddElementToEmptyArray_WhenArrayIsEmpty() {
        // Given
        String[] inputSource = new String[0];
        String inputElement = "a";

        // When
        String[] actual = ArrayUtils.addFirst(inputSource, inputElement);

        // Then
        then(actual)
                .hasSize(1)
                .containsExactly("a");
    }

    @Test
    void addLastBoolean_ShouldThrowIllegalArgumentException_WhenSourceArrayIsNull() {
        // Given
        boolean[] inputSource = null;
        boolean inputElement = true;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.addLast(inputSource, inputElement))
                .isInstanceOf(IllegalArgumentException.class)
                .withMessage("The input array cannot be null");
    }

    @Test
    void addLastBoolean_ShouldAddElementAtEnd_WhenArrayIsNotEmpty() {
        // Given
        boolean[] inputSource = new boolean[]{true, false};
        boolean inputElement = true;

        // When
        boolean[] actual = ArrayUtils.addLast(inputSource, inputElement);

        // Then
        then(actual)
                .hasSize(3)
                .containsExactly(true, false, true);
    }

    @Test
    void addLastBoolean_ShouldAddElementToEmptyArray_WhenArrayIsEmpty() {
        // Given
        boolean[] inputSource = new boolean[0];
        boolean inputElement = true;

        // When
        boolean[] actual = ArrayUtils.addLast(inputSource, inputElement);

        // Then
        then(actual)
                .hasSize(1)
                .containsExactly(true);
    }

    @Test
    void addLastByte_ShouldThrowIllegalArgumentException_WhenSourceArrayIsNull() {
        // Given
        byte[] inputSource = null;
        byte inputElement = 1;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.addLast(inputSource, inputElement))
                .isInstanceOf(IllegalArgumentException.class)
                .withMessage("The input array cannot be null");
    }

    @Test
    void addLastByte_ShouldAddElementAtEnd_WhenArrayIsNotEmpty() {
        // Given
        byte[] inputSource = new byte[]{-128, -127};
        byte inputElement = -126;

        // When
        byte[] actual = ArrayUtils.addLast(inputSource, inputElement);

        // Then
        then(actual)
                .hasSize(3)
                .containsExactly(-128, -127, -126);
    }

    @Test
    void addLastByte_ShouldAddElementToEmptyArray_WhenArrayIsEmpty() {
        // Given
        byte[] inputSource = new byte[0];
        byte inputElement = 1;

        // When
        byte[] actual = ArrayUtils.addLast(inputSource, inputElement);

        // Then
        then(actual)
                .hasSize(1)
                .containsExactly(1);
    }

    @Test
    void addLastChar_ShouldThrowIllegalArgumentException_WhenSourceArrayIsNull() {
        // Given
        char[] inputSource = null;
        char inputElement = 'a';

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.addLast(inputSource, inputElement))
                .isInstanceOf(IllegalArgumentException.class)
                .withMessage("The input array cannot be null");
    }

    @Test
    void addLastChar_ShouldAddElementAtEnd_WhenArrayIsNotEmpty() {
        // Given
        char[] inputSource = new char[]{'a', 'b'};
        char inputElement = 'c';

        // When
        char[] actual = ArrayUtils.addLast(inputSource, inputElement);

        // Then
        then(actual)
                .hasSize(3)
                .containsExactly('a', 'b', 'c');
    }

    @Test
    void addLastChar_ShouldAddElementToEmptyArray_WhenArrayIsEmpty() {
        // Given
        char[] inputSource = new char[0];
        char inputElement = 'a';

        // When
        char[] actual = ArrayUtils.addLast(inputSource, inputElement);

        // Then
        then(actual)
                .hasSize(1)
                .containsExactly('a');
    }

    @Test
    void addLastShort_ShouldThrowIllegalArgumentException_WhenSourceArrayIsNull() {
        // Given
        short[] inputSource = null;
        short inputElement = 1;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.addLast(inputSource, inputElement))
                .isInstanceOf(IllegalArgumentException.class)
                .withMessage("The input array cannot be null");
    }

    @Test
    void addLastShort_ShouldAddElementAtEnd_WhenArrayIsNotEmpty() {
        // Given
        short[] inputSource = new short[]{32767, 32766};
        short inputElement = 32765;

        // When
        short[] actual = ArrayUtils.addLast(inputSource, inputElement);

        // Then
        then(actual)
                .hasSize(3)
                .containsExactly(32767, 32766, 32765);
    }

    @Test
    void addLastShort_ShouldAddElementToEmptyArray_WhenArrayIsEmpty() {
        // Given
        short[] inputSource = new short[0];
        short inputElement = 1;

        // When
        short[] actual = ArrayUtils.addLast(inputSource, inputElement);

        // Then
        then(actual)
                .hasSize(1)
                .containsExactly(1);
    }

    @Test
    void addLastInt_ShouldThrowIllegalArgumentException_WhenSourceArrayIsNull() {
        // Given
        int[] inputSource = null;
        int inputElement = 1;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.addLast(inputSource, inputElement))
                .isInstanceOf(IllegalArgumentException.class)
                .withMessage("The input array cannot be null");
    }

    @Test
    void addLastInt_ShouldAddElementAtEnd_WhenArrayIsNotEmpty() {
        // Given
        int[] inputSource = new int[]{1, 2};
        int inputElement = 3;

        // When
        int[] actual = ArrayUtils.addLast(inputSource, inputElement);

        // Then
        then(actual)
                .hasSize(3)
                .containsExactly(1, 2, 3);
    }

    @Test
    void addLastInt_ShouldAddElementToEmptyArray_WhenArrayIsEmpty() {
        // Given
        int[] inputSource = new int[0];
        int inputElement = 1;

        // When
        int[] actual = ArrayUtils.addLast(inputSource, inputElement);

        // Then
        then(actual)
                .hasSize(1)
                .containsExactly(1);
    }

    @Test
    void addLastLong_ShouldThrowIllegalArgumentException_WhenSourceArrayIsNull() {
        // Given
        long[] inputSource = null;
        long inputElement = 1L;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.addLast(inputSource, inputElement))
                .isInstanceOf(IllegalArgumentException.class)
                .withMessage("The input array cannot be null");
    }

    @Test
    void addLastLong_ShouldAddElementAtEnd_WhenArrayIsNotEmpty() {
        // Given
        long[] inputSource = new long[]{1L, 2L};
        long inputElement = 3L;

        // When
        long[] actual = ArrayUtils.addLast(inputSource, inputElement);

        // Then
        then(actual)
                .hasSize(3)
                .containsExactly(1L, 2L, 3L);
    }

    @Test
    void addLastLong_ShouldAddElementToEmptyArray_WhenArrayIsEmpty() {
        // Given
        long[] inputSource = new long[0];
        long inputElement = 1L;

        // When
        long[] actual = ArrayUtils.addLast(inputSource, inputElement);

        // Then
        then(actual)
                .hasSize(1)
                .containsExactly(1L);
    }

    @Test
    void addLastFloat_ShouldThrowIllegalArgumentException_WhenSourceArrayIsNull() {
        // Given
        float[] inputSource = null;
        float inputElement = 1.0F;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.addLast(inputSource, inputElement))
                .isInstanceOf(IllegalArgumentException.class)
                .withMessage("The input array cannot be null");
    }

    @Test
    void addLastFloat_ShouldAddElementAtEnd_WhenArrayIsNotEmpty() {
        // Given
        float[] inputSource = new float[]{1.0F, 2.0F};
        float inputElement = 3.0F;

        // When
        float[] actual = ArrayUtils.addLast(inputSource, inputElement);

        // Then
        then(actual)
                .hasSize(3)
                .containsExactly(1.0F, 2.0F, 3.0F);
    }

    @Test
    void addLastFloat_ShouldAddElementToEmptyArray_WhenArrayIsEmpty() {
        // Given
        float[] inputSource = new float[0];
        float inputElement = 1.0F;

        // When
        float[] actual = ArrayUtils.addLast(inputSource, inputElement);

        // Then
        then(actual)
                .hasSize(1)
                .containsExactly(1.0F);
    }

    @Test
    void addLastDouble_ShouldThrowIllegalArgumentException_WhenSourceArrayIsNull() {
        // Given
        double[] inputSource = null;
        double inputElement = 1.0;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.addLast(inputSource, inputElement))
                .isInstanceOf(IllegalArgumentException.class)
                .withMessage("The input array cannot be null");
    }

    @Test
    void addLastDouble_ShouldAddElementAtEnd_WhenArrayIsNotEmpty() {
        // Given
        double[] inputSource = new double[]{1.0, 2.0};
        double inputElement = 3.0;

        // When
        double[] actual = ArrayUtils.addLast(inputSource, inputElement);

        // Then
        then(actual)
                .hasSize(3)
                .containsExactly(1.0, 2.0, 3.0);
    }

    @Test
    void addLastDouble_ShouldAddElementToEmptyArray_WhenArrayIsEmpty() {
        // Given
        double[] inputSource = new double[0];
        double inputElement = 1.0;

        // When
        double[] actual = ArrayUtils.addLast(inputSource, inputElement);

        // Then
        then(actual)
                .hasSize(1)
                .containsExactly(1.0);
    }

    @Test
    void addLastGeneric_ShouldThrowIllegalArgumentException_WhenSourceArrayIsNull() {
        // Given
        String[] inputSource = null;
        String inputElement = "c";

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.addLast(inputSource, inputElement))
                .isInstanceOf(IllegalArgumentException.class)
                .withMessage("The input array cannot be null");
    }

    @Test
    void addLastGeneric_ShouldAddElementAtEnd_WhenArrayIsNotEmpty() {
        // Given
        String[] inputSource = new String[]{"a", "b"};
        String inputElement = "c";

        // When
        String[] actual = ArrayUtils.addLast(inputSource, inputElement);

        // Then
        then(actual)
                .hasSize(3)
                .containsExactly("a", "b", "c");
    }

    @Test
    void addLastGeneric_ShouldAddElementToEmptyArray_WhenArrayIsEmpty() {
        // Given
        String[] inputSource = new String[0];
        String inputElement = "a";

        // When
        String[] actual = ArrayUtils.addLast(inputSource, inputElement);

        // Then
        then(actual)
                .hasSize(1)
                .containsExactly("a");
    }

    @Test
    void cloneBoolean_ShouldReturnEmptyArray_WhenInputIsNull() {
        // Given
        boolean[] input = null;

        // When
        boolean[] actual = ArrayUtils.clone(input);

        // Then
        then(actual)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void cloneBoolean_ShouldReturnEmptyArray_WhenInputIsEmpty() {
        // Given
        boolean[] input = new boolean[0];

        // When
        boolean[] actual = ArrayUtils.clone(input);

        // Then
        then(actual)
                .isNotSameAs(input)
                .isEmpty();
    }

    @Test
    void cloneBoolean_ShouldReturnCopy_WhenInputIsNotEmpty() {
        // Given
        boolean[] input = new boolean[]{true, false, true};

        // When
        boolean[] actual = ArrayUtils.clone(input);

        // Then
        then(actual)
                .isNotSameAs(input)
                .hasSize(3)
                .containsExactly(true, false, true);
    }

    @Test
    void cloneByte_ShouldReturnEmptyArray_WhenInputIsNull() {
        // Given
        byte[] input = null;

        // When
        byte[] actual = ArrayUtils.clone(input);

        // Then
        then(actual)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void cloneByte_ShouldReturnEmptyArray_WhenInputIsEmpty() {
        // Given
        byte[] input = new byte[0];

        // When
        byte[] actual = ArrayUtils.clone(input);

        // Then
        then(actual)
                .isNotSameAs(input)
                .isEmpty();
    }

    @Test
    void cloneByte_ShouldReturnCopy_WhenInputIsNotEmpty() {
        // Given
        byte[] input = new byte[]{-128, -127, -126};

        // When
        byte[] actual = ArrayUtils.clone(input);

        // Then
        then(actual)
                .isNotSameAs(input)
                .hasSize(3)
                .containsExactly(-128, -127, -126);
    }

    @Test
    void cloneChar_ShouldReturnEmptyArray_WhenInputIsNull() {
        // Given
        char[] input = null;

        // When
        char[] actual = ArrayUtils.clone(input);

        // Then
        then(actual)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void cloneChar_ShouldReturnEmptyArray_WhenInputIsEmpty() {
        // Given
        char[] input = new char[0];

        // When
        char[] actual = ArrayUtils.clone(input);

        // Then
        then(actual)
                .isNotSameAs(input)
                .isEmpty();
    }

    @Test
    void cloneChar_ShouldReturnCopy_WhenInputIsNotEmpty() {
        // Given
        char[] input = new char[]{'a', 'b', 'c'};

        // When
        char[] actual = ArrayUtils.clone(input);

        // Then
        then(actual)
                .isNotSameAs(input)
                .hasSize(3)
                .containsExactly('a', 'b', 'c');
    }

    @Test
    void cloneShort_ShouldReturnEmptyArray_WhenInputIsNull() {
        // Given
        short[] input = null;

        // When
        short[] actual = ArrayUtils.clone(input);

        // Then
        then(actual)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void cloneShort_ShouldReturnEmptyArray_WhenInputIsEmpty() {
        // Given
        short[] input = new short[0];

        // When
        short[] actual = ArrayUtils.clone(input);

        // Then
        then(actual)
                .isNotSameAs(input)
                .isEmpty();
    }

    @Test
    void cloneShort_ShouldReturnCopy_WhenInputIsNotEmpty() {
        // Given
        short[] input = new short[]{32767, 32766, 32765};

        // When
        short[] actual = ArrayUtils.clone(input);

        // Then
        then(actual)
                .isNotSameAs(input)
                .hasSize(3)
                .containsExactly(32767, 32766, 32765);
    }

    @Test
    void cloneInt_ShouldReturnEmptyArray_WhenInputIsNull() {
        // Given
        int[] input = null;

        // When
        int[] actual = ArrayUtils.clone(input);

        // Then
        then(actual)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void cloneInt_ShouldReturnEmptyArray_WhenInputIsEmpty() {
        // Given
        int[] input = new int[0];

        // When
        int[] actual = ArrayUtils.clone(input);

        // Then
        then(actual)
                .isNotSameAs(input)
                .isEmpty();
    }

    @Test
    void cloneInt_ShouldReturnCopy_WhenInputIsNotEmpty() {
        // Given
        int[] input = new int[]{1, 2, 3};

        // When
        int[] actual = ArrayUtils.clone(input);

        // Then
        then(actual)
                .isNotSameAs(input)
                .hasSize(3)
                .containsExactly(1, 2, 3);
    }

    @Test
    void cloneLong_ShouldReturnEmptyArray_WhenInputIsNull() {
        // Given
        long[] input = null;

        // When
        long[] actual = ArrayUtils.clone(input);

        // Then
        then(actual)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void cloneLong_ShouldReturnEmptyArray_WhenInputIsEmpty() {
        // Given
        long[] input = new long[0];

        // When
        long[] actual = ArrayUtils.clone(input);

        // Then
        then(actual)
                .isNotSameAs(input)
                .isEmpty();
    }

    @Test
    void cloneLong_ShouldReturnCopy_WhenInputIsNotEmpty() {
        // Given
        long[] input = new long[]{1L, 2L, 3L};

        // When
        long[] actual = ArrayUtils.clone(input);

        // Then
        then(actual)
                .isNotSameAs(input)
                .hasSize(3)
                .containsExactly(1L, 2L, 3L);
    }

    @Test
    void cloneFloat_ShouldReturnEmptyArray_WhenInputIsNull() {
        // Given
        float[] input = null;

        // When
        float[] actual = ArrayUtils.clone(input);

        // Then
        then(actual)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void cloneFloat_ShouldReturnEmptyArray_WhenInputIsEmpty() {
        // Given
        float[] input = new float[0];

        // When
        float[] actual = ArrayUtils.clone(input);

        // Then
        then(actual)
                .isNotSameAs(input)
                .isEmpty();
    }

    @Test
    void cloneFloat_ShouldReturnCopy_WhenInputIsNotEmpty() {
        // Given
        float[] input = new float[]{1.0F, 2.0F, 3.0F};

        // When
        float[] actual = ArrayUtils.clone(input);

        // Then
        then(actual)
                .isNotSameAs(input)
                .hasSize(3)
                .containsExactly(1.0F, 2.0F, 3.0F);
    }

    @Test
    void cloneDouble_ShouldReturnEmptyArray_WhenInputIsNull() {
        // Given
        double[] input = null;

        // When
        double[] actual = ArrayUtils.clone(input);

        // Then
        then(actual)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void cloneDouble_ShouldReturnEmptyArray_WhenInputIsEmpty() {
        // Given
        double[] input = new double[0];

        // When
        double[] actual = ArrayUtils.clone(input);

        // Then
        then(actual)
                .isNotSameAs(input)
                .isEmpty();
    }

    @Test
    void cloneDouble_ShouldReturnCopy_WhenInputIsNotEmpty() {
        // Given
        double[] input = new double[]{1.0, 2.0, 3.0};

        // When
        double[] actual = ArrayUtils.clone(input);

        // Then
        then(actual)
                .isNotSameAs(input)
                .hasSize(3)
                .containsExactly(1.0, 2.0, 3.0);
    }

    @Test
    void cloneGeneric_ShouldReturnNull_WhenInputIsNull() {
        // Given
        String[] input = null;

        // When
        String[] actual = ArrayUtils.clone(input);

        // Then
        then(actual).isNull();
    }

    @Test
    void cloneGeneric_ShouldReturnEmptyArray_WhenInputIsEmpty() {
        // Given
        String[] input = new String[0];

        // When
        String[] actual = ArrayUtils.clone(input);

        // Then
        then(actual)
                .isNotSameAs(input)
                .isEmpty();
    }

    @Test
    void cloneGeneric_ShouldReturnCopy_WhenInputIsNotEmpty() {
        // Given
        String[] input = new String[]{"a", "b", "c"};

        // When
        String[] actual = ArrayUtils.clone(input);

        // Then
        then(actual)
                .isNotSameAs(input)
                .hasSize(3)
                .containsExactly("a", "b", "c");
    }

    @Test
    void containsBoolean_ShouldReturnFalse_WhenArrayIsNull() {
        // Given
        boolean[] inputSource = null;
        boolean inputElement = true;

        // When
        boolean actual = ArrayUtils.contains(inputSource, inputElement);

        // Then
        then(actual).isFalse();
    }

    @Test
    void containsBoolean_ShouldReturnFalse_WhenArrayIsEmpty() {
        // Given
        boolean[] inputSource = new boolean[]{};
        boolean inputElement = true;

        // When
        boolean actual = ArrayUtils.contains(inputSource, inputElement);

        // Then
        then(actual).isFalse();
    }

    @Test
    void containsBoolean_ShouldReturnFalse_WhenElementIsAbsent() {
        // Given
        boolean[] inputSource = new boolean[]{false, false};
        boolean inputElement = true;

        // When
        boolean actual = ArrayUtils.contains(inputSource, inputElement);

        // Then
        then(actual).isFalse();
    }

    @Test
    void containsBoolean_ShouldReturnTrue_WhenElementIsPresent() {
        // Given
        boolean[] inputSource = new boolean[]{true, false, true};
        boolean inputElement = true;

        // When
        boolean actual = ArrayUtils.contains(inputSource, inputElement);

        // Then
        then(actual).isTrue();
    }

    @Test
    void containsByte_ShouldReturnFalse_WhenArrayIsNull() {
        // Given
        byte[] inputSource = null;
        byte inputElement = 1;

        // When
        boolean actual = ArrayUtils.contains(inputSource, inputElement);

        // Then
        then(actual).isFalse();
    }

    @Test
    void containsByte_ShouldReturnFalse_WhenArrayIsEmpty() {
        // Given
        byte[] inputSource = new byte[]{};
        byte inputElement = 1;

        // When
        boolean actual = ArrayUtils.contains(inputSource, inputElement);

        // Then
        then(actual).isFalse();
    }

    @Test
    void containsByte_ShouldReturnFalse_WhenElementIsAbsent() {
        // Given
        byte[] inputSource = new byte[]{2, 3};
        byte inputElement = 1;

        // When
        boolean actual = ArrayUtils.contains(inputSource, inputElement);

        // Then
        then(actual).isFalse();
    }

    @Test
    void containsByte_ShouldReturnTrue_WhenElementIsPresent() {
        // Given
        byte[] inputSource = new byte[]{1, 2, 1};
        byte inputElement = 1;

        // When
        boolean actual = ArrayUtils.contains(inputSource, inputElement);

        // Then
        then(actual).isTrue();
    }

    @Test
    void containsChar_ShouldReturnFalse_WhenArrayIsNull() {
        // Given
        char[] inputSource = null;
        char inputElement = 'a';

        // When
        boolean actual = ArrayUtils.contains(inputSource, inputElement);

        // Then
        then(actual).isFalse();
    }

    @Test
    void containsChar_ShouldReturnFalse_WhenArrayIsEmpty() {
        // Given
        char[] inputSource = new char[]{};
        char inputElement = 'a';

        // When
        boolean actual = ArrayUtils.contains(inputSource, inputElement);

        // Then
        then(actual).isFalse();
    }

    @Test
    void containsChar_ShouldReturnFalse_WhenElementIsAbsent() {
        // Given
        char[] inputSource = new char[]{'b', 'c'};
        char inputElement = 'a';

        // When
        boolean actual = ArrayUtils.contains(inputSource, inputElement);

        // Then
        then(actual).isFalse();
    }

    @Test
    void containsChar_ShouldReturnTrue_WhenElementIsPresent() {
        // Given
        char[] inputSource = new char[]{'a', 'b', 'a'};
        char inputElement = 'a';

        // When
        boolean actual = ArrayUtils.contains(inputSource, inputElement);

        // Then
        then(actual).isTrue();
    }

    @Test
    void containsShort_ShouldReturnFalse_WhenArrayIsNull() {
        // Given
        short[] inputSource = null;
        short inputElement = 1;

        // When
        boolean actual = ArrayUtils.contains(inputSource, inputElement);

        // Then
        then(actual).isFalse();
    }

    @Test
    void containsShort_ShouldReturnFalse_WhenArrayIsEmpty() {
        // Given
        short[] inputSource = new short[]{};
        short inputElement = 1;

        // When
        boolean actual = ArrayUtils.contains(inputSource, inputElement);

        // Then
        then(actual).isFalse();
    }

    @Test
    void containsShort_ShouldReturnFalse_WhenElementIsAbsent() {
        // Given
        short[] inputSource = new short[]{2, 3};
        short inputElement = 1;

        // When
        boolean actual = ArrayUtils.contains(inputSource, inputElement);

        // Then
        then(actual).isFalse();
    }

    @Test
    void containsShort_ShouldReturnTrue_WhenElementIsPresent() {
        // Given
        short[] inputSource = new short[]{1, 2, 1};
        short inputElement = 1;

        // When
        boolean actual = ArrayUtils.contains(inputSource, inputElement);

        // Then
        then(actual).isTrue();
    }

    @Test
    void containsInt_ShouldReturnFalse_WhenArrayIsNull() {
        // Given
        int[] inputSource = null;
        int inputElement = 1;

        // When
        boolean actual = ArrayUtils.contains(inputSource, inputElement);

        // Then
        then(actual).isFalse();
    }

    @Test
    void containsInt_ShouldReturnFalse_WhenArrayIsEmpty() {
        // Given
        int[] inputSource = new int[]{};
        int inputElement = 1;

        // When
        boolean actual = ArrayUtils.contains(inputSource, inputElement);

        // Then
        then(actual).isFalse();
    }

    @Test
    void containsInt_ShouldReturnFalse_WhenElementIsAbsent() {
        // Given
        int[] inputSource = new int[]{2, 3};
        int inputElement = 1;

        // When
        boolean actual = ArrayUtils.contains(inputSource, inputElement);

        // Then
        then(actual).isFalse();
    }

    @Test
    void containsInt_ShouldReturnTrue_WhenElementIsPresent() {
        // Given
        int[] inputSource = new int[]{1, 2, 1};
        int inputElement = 1;

        // When
        boolean actual = ArrayUtils.contains(inputSource, inputElement);

        // Then
        then(actual).isTrue();
    }

    @Test
    void containsLong_ShouldReturnFalse_WhenArrayIsNull() {
        // Given
        long[] inputSource = null;
        long inputElement = 1L;

        // When
        boolean actual = ArrayUtils.contains(inputSource, inputElement);

        // Then
        then(actual).isFalse();
    }

    @Test
    void containsLong_ShouldReturnFalse_WhenArrayIsEmpty() {
        // Given
        long[] inputSource = new long[]{};
        long inputElement = 1L;

        // When
        boolean actual = ArrayUtils.contains(inputSource, inputElement);

        // Then
        then(actual).isFalse();
    }

    @Test
    void containsLong_ShouldReturnFalse_WhenElementIsAbsent() {
        // Given
        long[] inputSource = new long[]{2L, 3L};
        long inputElement = 1L;

        // When
        boolean actual = ArrayUtils.contains(inputSource, inputElement);

        // Then
        then(actual).isFalse();
    }

    @Test
    void containsLong_ShouldReturnTrue_WhenElementIsPresent() {
        // Given
        long[] inputSource = new long[]{1L, 2L, 1L};
        long inputElement = 1L;

        // When
        boolean actual = ArrayUtils.contains(inputSource, inputElement);

        // Then
        then(actual).isTrue();
    }

    @Test
    void containsFloat_ShouldReturnFalse_WhenArrayIsNull() {
        // Given
        float[] inputSource = null;
        float inputElement = 1.0F;

        // When
        boolean actual = ArrayUtils.contains(inputSource, inputElement);

        // Then
        then(actual).isFalse();
    }

    @Test
    void containsFloat_ShouldReturnFalse_WhenArrayIsEmpty() {
        // Given
        float[] inputSource = new float[]{};
        float inputElement = 1.0F;

        // When
        boolean actual = ArrayUtils.contains(inputSource, inputElement);

        // Then
        then(actual).isFalse();
    }

    @Test
    void containsFloat_ShouldReturnFalse_WhenElementIsAbsent() {
        // Given
        float[] inputSource = new float[]{2.0F, 3.0F};
        float inputElement = 1.0F;

        // When
        boolean actual = ArrayUtils.contains(inputSource, inputElement);

        // Then
        then(actual).isFalse();
    }

    @Test
    void containsFloat_ShouldReturnTrue_WhenElementIsPresent() {
        // Given
        float[] inputSource = new float[]{1.0F, 2.0F, 1.0F};
        float inputElement = 1.0F;

        // When
        boolean actual = ArrayUtils.contains(inputSource, inputElement);

        // Then
        then(actual).isTrue();
    }

    @Test
    void containsDouble_ShouldReturnFalse_WhenArrayIsNull() {
        // Given
        double[] inputSource = null;
        double inputElement = 1.0;

        // When
        boolean actual = ArrayUtils.contains(inputSource, inputElement);

        // Then
        then(actual).isFalse();
    }

    @Test
    void containsDouble_ShouldReturnFalse_WhenArrayIsEmpty() {
        // Given
        double[] inputSource = new double[]{};
        double inputElement = 1.0;

        // When
        boolean actual = ArrayUtils.contains(inputSource, inputElement);

        // Then
        then(actual).isFalse();
    }

    @Test
    void containsDouble_ShouldReturnFalse_WhenElementIsAbsent() {
        // Given
        double[] inputSource = new double[]{2.0, 3.0};
        double inputElement = 1.0;

        // When
        boolean actual = ArrayUtils.contains(inputSource, inputElement);

        // Then
        then(actual).isFalse();
    }

    @Test
    void containsDouble_ShouldReturnTrue_WhenElementIsPresent() {
        // Given
        double[] inputSource = new double[]{1.0, 2.0, 1.0};
        double inputElement = 1.0;

        // When
        boolean actual = ArrayUtils.contains(inputSource, inputElement);

        // Then
        then(actual).isTrue();
    }

    @Test
    void containsGeneric_ShouldReturnFalse_WhenArrayIsNull() {
        // Given
        String[] inputSource = null;
        String inputElement = "a";

        // When
        boolean actual = ArrayUtils.contains(inputSource, inputElement);

        // Then
        then(actual).isFalse();
    }

    @Test
    void containsGeneric_ShouldReturnFalse_WhenArrayIsEmpty() {
        // Given
        String[] inputSource = new String[]{};
        String inputElement = "a";

        // When
        boolean actual = ArrayUtils.contains(inputSource, inputElement);

        // Then
        then(actual).isFalse();
    }

    @Test
    void containsGeneric_ShouldReturnFalse_WhenElementIsAbsent() {
        // Given
        String[] inputSource = new String[]{"b", "c"};
        String inputElement = "a";

        // When
        boolean actual = ArrayUtils.contains(inputSource, inputElement);

        // Then
        then(actual).isFalse();
    }

    @Test
    void containsGeneric_ShouldReturnFalse_WhenElementTypeIsIncompatible() {
        // Given
        String[] inputSource = new String[]{"a", "b"};
        Object inputElement = 1;

        // When
        boolean actual = ArrayUtils.contains(inputSource, inputElement);

        // Then
        then(actual).isFalse();
    }

    @Test
    void containsGeneric_ShouldReturnTrue_WhenElementIsPresent() {
        // Given
        String[] inputSource = new String[]{"a", "b", "a"};
        String inputElement = "a";

        // When
        boolean actual = ArrayUtils.contains(inputSource, inputElement);

        // Then
        then(actual).isTrue();
    }

    @Test
    void containsGeneric_ShouldReturnTrue_WhenNullElementIsPresent() {
        // Given
        String[] inputSource = new String[]{"a", null, "b"};
        String inputElement = null;

        // When
        boolean actual = ArrayUtils.contains(inputSource, inputElement);

        // Then
        then(actual).isTrue();
    }

    @Test
    void getBoolean_ShouldReturnEmptyOptional_WhenArrayIsNull() {
        // Given
        boolean[] inputValues = null;
        int inputIndex = 0;

        // When
        Optional<Boolean> actual = ArrayUtils.get(inputValues, inputIndex);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void getBoolean_ShouldReturnEmptyOptional_WhenArrayIsEmpty() {
        // Given
        boolean[] inputValues = new boolean[0];
        int inputIndex = 0;

        // When
        Optional<Boolean> actual = ArrayUtils.get(inputValues, inputIndex);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void getBoolean_ShouldReturnEmptyOptional_WhenIndexIsNegative() {
        // Given
        boolean[] inputValues = new boolean[]{true, false};
        int inputIndex = -1;

        // When
        Optional<Boolean> actual = ArrayUtils.get(inputValues, inputIndex);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void getBoolean_ShouldReturnEmptyOptional_WhenIndexIsOutOfBounds() {
        // Given
        boolean[] inputValues = new boolean[]{true, false};
        int inputIndex = 2;

        // When
        Optional<Boolean> actual = ArrayUtils.get(inputValues, inputIndex);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void getBoolean_ShouldReturnValue_WhenIndexIsValid() {
        // Given
        boolean[] inputValues = new boolean[]{true, false};
        int inputIndex = 1;

        // When
        Optional<Boolean> actual = ArrayUtils.get(inputValues, inputIndex);

        // Then
        then(actual)
                .isPresent()
                .hasValue(false);
    }

    @Test
    void getByte_ShouldReturnEmptyOptional_WhenArrayIsNull() {
        // Given
        byte[] inputValues = null;
        int inputIndex = 0;

        // When
        Optional<Byte> actual = ArrayUtils.get(inputValues, inputIndex);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void getByte_ShouldReturnEmptyOptional_WhenArrayIsEmpty() {
        // Given
        byte[] inputValues = new byte[0];
        int inputIndex = 0;

        // When
        Optional<Byte> actual = ArrayUtils.get(inputValues, inputIndex);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void getByte_ShouldReturnEmptyOptional_WhenIndexIsNegative() {
        // Given
        byte[] inputValues = new byte[]{-128, -127};
        int inputIndex = -1;

        // When
        Optional<Byte> actual = ArrayUtils.get(inputValues, inputIndex);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void getByte_ShouldReturnEmptyOptional_WhenIndexIsOutOfBounds() {
        // Given
        byte[] inputValues = new byte[]{-128, -127};
        int inputIndex = 2;

        // When
        Optional<Byte> actual = ArrayUtils.get(inputValues, inputIndex);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void getByte_ShouldReturnValue_WhenIndexIsValid() {
        // Given
        byte[] inputValues = new byte[]{-128, -127};
        int inputIndex = 1;

        // When
        Optional<Byte> actual = ArrayUtils.get(inputValues, inputIndex);

        // Then
        then(actual)
                .isPresent()
                .hasValue((byte) -127);
    }

    @Test
    void getChar_ShouldReturnEmptyOptional_WhenArrayIsNull() {
        // Given
        char[] inputValues = null;
        int inputIndex = 0;

        // When
        Optional<Character> actual = ArrayUtils.get(inputValues, inputIndex);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void getChar_ShouldReturnEmptyOptional_WhenArrayIsEmpty() {
        // Given
        char[] inputValues = new char[0];
        int inputIndex = 0;

        // When
        Optional<Character> actual = ArrayUtils.get(inputValues, inputIndex);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void getChar_ShouldReturnEmptyOptional_WhenIndexIsNegative() {
        // Given
        char[] inputValues = new char[]{'a', 'b'};
        int inputIndex = -1;

        // When
        Optional<Character> actual = ArrayUtils.get(inputValues, inputIndex);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void getChar_ShouldReturnEmptyOptional_WhenIndexIsOutOfBounds() {
        // Given
        char[] inputValues = new char[]{'a', 'b'};
        int inputIndex = 2;

        // When
        Optional<Character> actual = ArrayUtils.get(inputValues, inputIndex);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void getChar_ShouldReturnValue_WhenIndexIsValid() {
        // Given
        char[] inputValues = new char[]{'a', 'b'};
        int inputIndex = 1;

        // When
        Optional<Character> actual = ArrayUtils.get(inputValues, inputIndex);

        // Then
        then(actual)
                .isPresent()
                .hasValue('b');
    }

    @Test
    void getShort_ShouldReturnEmptyOptional_WhenArrayIsNull() {
        // Given
        short[] inputValues = null;
        int inputIndex = 0;

        // When
        Optional<Short> actual = ArrayUtils.get(inputValues, inputIndex);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void getShort_ShouldReturnEmptyOptional_WhenArrayIsEmpty() {
        // Given
        short[] inputValues = new short[0];
        int inputIndex = 0;

        // When
        Optional<Short> actual = ArrayUtils.get(inputValues, inputIndex);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void getShort_ShouldReturnEmptyOptional_WhenIndexIsNegative() {
        // Given
        short[] inputValues = new short[]{32767, 32766};
        int inputIndex = -1;

        // When
        Optional<Short> actual = ArrayUtils.get(inputValues, inputIndex);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void getShort_ShouldReturnEmptyOptional_WhenIndexIsOutOfBounds() {
        // Given
        short[] inputValues = new short[]{32767, 32766};
        int inputIndex = 2;

        // When
        Optional<Short> actual = ArrayUtils.get(inputValues, inputIndex);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void getShort_ShouldReturnValue_WhenIndexIsValid() {
        // Given
        short[] inputValues = new short[]{32767, 32766};
        int inputIndex = 1;

        // When
        Optional<Short> actual = ArrayUtils.get(inputValues, inputIndex);

        // Then
        then(actual)
                .isPresent()
                .hasValue((short) 32766);
    }

    @Test
    void getInt_ShouldReturnEmptyOptional_WhenArrayIsNull() {
        // Given
        int[] inputValues = null;
        int inputIndex = 0;

        // When
        Optional<Integer> actual = ArrayUtils.get(inputValues, inputIndex);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void getInt_ShouldReturnEmptyOptional_WhenArrayIsEmpty() {
        // Given
        int[] inputValues = new int[0];
        int inputIndex = 0;

        // When
        Optional<Integer> actual = ArrayUtils.get(inputValues, inputIndex);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void getInt_ShouldReturnEmptyOptional_WhenIndexIsNegative() {
        // Given
        int[] inputValues = new int[]{1, 2};
        int inputIndex = -1;

        // When
        Optional<Integer> actual = ArrayUtils.get(inputValues, inputIndex);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void getInt_ShouldReturnEmptyOptional_WhenIndexIsOutOfBounds() {
        // Given
        int[] inputValues = new int[]{1, 2};
        int inputIndex = 2;

        // When
        Optional<Integer> actual = ArrayUtils.get(inputValues, inputIndex);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void getInt_ShouldReturnValue_WhenIndexIsValid() {
        // Given
        int[] inputValues = new int[]{1, 2};
        int inputIndex = 1;

        // When
        Optional<Integer> actual = ArrayUtils.get(inputValues, inputIndex);

        // Then
        then(actual)
                .isPresent()
                .hasValue(2);
    }

    @Test
    void getLong_ShouldReturnEmptyOptional_WhenArrayIsNull() {
        // Given
        long[] inputValues = null;
        int inputIndex = 0;

        // When
        Optional<Long> actual = ArrayUtils.get(inputValues, inputIndex);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void getLong_ShouldReturnEmptyOptional_WhenArrayIsEmpty() {
        // Given
        long[] inputValues = new long[0];
        int inputIndex = 0;

        // When
        Optional<Long> actual = ArrayUtils.get(inputValues, inputIndex);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void getLong_ShouldReturnEmptyOptional_WhenIndexIsNegative() {
        // Given
        long[] inputValues = new long[]{1L, 2L};
        int inputIndex = -1;

        // When
        Optional<Long> actual = ArrayUtils.get(inputValues, inputIndex);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void getLong_ShouldReturnEmptyOptional_WhenIndexIsOutOfBounds() {
        // Given
        long[] inputValues = new long[]{1L, 2L};
        int inputIndex = 2;

        // When
        Optional<Long> actual = ArrayUtils.get(inputValues, inputIndex);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void getLong_ShouldReturnValue_WhenIndexIsValid() {
        // Given
        long[] inputValues = new long[]{1L, 2L};
        int inputIndex = 1;

        // When
        Optional<Long> actual = ArrayUtils.get(inputValues, inputIndex);

        // Then
        then(actual)
                .isPresent()
                .hasValue(2L);
    }

    @Test
    void getFloat_ShouldReturnEmptyOptional_WhenArrayIsNull() {
        // Given
        float[] inputValues = null;
        int inputIndex = 0;

        // When
        Optional<Float> actual = ArrayUtils.get(inputValues, inputIndex);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void getFloat_ShouldReturnEmptyOptional_WhenArrayIsEmpty() {
        // Given
        float[] inputValues = new float[0];
        int inputIndex = 0;

        // When
        Optional<Float> actual = ArrayUtils.get(inputValues, inputIndex);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void getFloat_ShouldReturnEmptyOptional_WhenIndexIsNegative() {
        // Given
        float[] inputValues = new float[]{1.0F, 2.0F};
        int inputIndex = -1;

        // When
        Optional<Float> actual = ArrayUtils.get(inputValues, inputIndex);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void getFloat_ShouldReturnEmptyOptional_WhenIndexIsOutOfBounds() {
        // Given
        float[] inputValues = new float[]{1.0F, 2.0F};
        int inputIndex = 2;

        // When
        Optional<Float> actual = ArrayUtils.get(inputValues, inputIndex);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void getFloat_ShouldReturnValue_WhenIndexIsValid() {
        // Given
        float[] inputValues = new float[]{1.0F, 2.0F};
        int inputIndex = 1;

        // When
        Optional<Float> actual = ArrayUtils.get(inputValues, inputIndex);

        // Then
        then(actual)
                .isPresent()
                .hasValue(2.0F);
    }

    @Test
    void getDouble_ShouldReturnEmptyOptional_WhenArrayIsNull() {
        // Given
        double[] inputValues = null;
        int inputIndex = 0;

        // When
        Optional<Double> actual = ArrayUtils.get(inputValues, inputIndex);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void getDouble_ShouldReturnEmptyOptional_WhenArrayIsEmpty() {
        // Given
        double[] inputValues = new double[0];
        int inputIndex = 0;

        // When
        Optional<Double> actual = ArrayUtils.get(inputValues, inputIndex);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void getDouble_ShouldReturnEmptyOptional_WhenIndexIsNegative() {
        // Given
        double[] inputValues = new double[]{1.0, 2.0};
        int inputIndex = -1;

        // When
        Optional<Double> actual = ArrayUtils.get(inputValues, inputIndex);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void getDouble_ShouldReturnEmptyOptional_WhenIndexIsOutOfBounds() {
        // Given
        double[] inputValues = new double[]{1.0, 2.0};
        int inputIndex = 2;

        // When
        Optional<Double> actual = ArrayUtils.get(inputValues, inputIndex);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void getDouble_ShouldReturnValue_WhenIndexIsValid() {
        // Given
        double[] inputValues = new double[]{1.0, 2.0};
        int inputIndex = 1;

        // When
        Optional<Double> actual = ArrayUtils.get(inputValues, inputIndex);

        // Then
        then(actual)
                .isPresent()
                .hasValue(2.0);
    }

    @Test
    void getGeneric_ShouldReturnEmptyOptional_WhenArrayIsNull() {
        // Given
        String[] inputValues = null;
        int inputIndex = 0;

        // When
        Optional<String> actual = ArrayUtils.get(inputValues, inputIndex);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void getGeneric_ShouldReturnEmptyOptional_WhenArrayIsEmpty() {
        // Given
        String[] inputValues = new String[0];
        int inputIndex = 0;

        // When
        Optional<String> actual = ArrayUtils.get(inputValues, inputIndex);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void getGeneric_ShouldReturnEmptyOptional_WhenIndexIsNegative() {
        // Given
        String[] inputValues = new String[]{"a", "b"};
        int inputIndex = -1;

        // When
        Optional<String> actual = ArrayUtils.get(inputValues, inputIndex);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void getGeneric_ShouldReturnEmptyOptional_WhenIndexIsOutOfBounds() {
        // Given
        String[] inputValues = new String[]{"a", "b"};
        int inputIndex = 2;

        // When
        Optional<String> actual = ArrayUtils.get(inputValues, inputIndex);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void getGeneric_ShouldReturnValue_WhenIndexIsValid() {
        // Given
        String[] inputValues = new String[]{"a", "b"};
        int inputIndex = 1;

        // When
        Optional<String> actual = ArrayUtils.get(inputValues, inputIndex);

        // Then
        then(actual)
                .isPresent()
                .hasValue("b");
    }

    @Test
    void getComponentType_ShouldReturnNull_WhenArrayIsNull() {
        // Given
        String[] inputArray = null;

        // When
        Class<String> actual = ArrayUtils.getComponentType(inputArray);

        // Then
        then(actual).isNull();
    }

    @Test
    void getComponentType_ShouldReturnStringClass_WhenArrayIsStringArray() {
        // Given
        String[] inputArray = new String[]{"a", "b"};

        // When
        Class<String> actual = ArrayUtils.getComponentType(inputArray);

        // Then
        then(actual)
                .isNotNull()
                .isEqualTo(String.class);
    }

    @Test
    void getComponentType_ShouldReturnIntegerClass_WhenArrayIsIntegerArray() {
        // Given
        Integer[] inputArray = new Integer[]{1, 2, 3};

        // When
        Class<Integer> actual = ArrayUtils.getComponentType(inputArray);

        // Then
        then(actual)
                .isNotNull()
                .isEqualTo(Integer.class);
    }

    @Test
    void getComponentType_ShouldReturnBooleanClass_WhenArrayIsBooleanArray() {
        // Given
        Boolean[] inputArray = new Boolean[]{true, false};

        // When
        Class<Boolean> actual = ArrayUtils.getComponentType(inputArray);

        // Then
        then(actual)
                .isNotNull()
                .isEqualTo(Boolean.class);
    }

    @Test
    void getComponentType_ShouldReturnCharacterClass_WhenArrayIsEmptyCharacterArray() {
        // Given
        Character[] inputArray = new Character[0];

        // When
        Class<Character> actual = ArrayUtils.getComponentType(inputArray);

        // Then
        then(actual)
                .isNotNull()
                .isEqualTo(Character.class);
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
        float[] input = {1.0F, 2.0F};

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

    @Test
    void indexOfBoolean_ShouldReturnMinusOne_WhenArrayIsNull() {
        // Given
        boolean[] inputSource = null;
        boolean inputElement = true;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfBoolean_ShouldReturnMinusOne_WhenArrayIsEmpty() {
        // Given
        boolean[] inputSource = new boolean[]{};
        boolean inputElement = true;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfBoolean_ShouldReturnIndex_WhenElementIsPresent() {
        // Given
        boolean[] inputSource = new boolean[]{true, false, true};
        boolean inputElement = true;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement);

        // Then
        then(actual).isEqualTo(0);
    }

    @Test
    void indexOfBoolean_ShouldReturnMinusOne_WhenElementIsAbsent() {
        // Given
        boolean[] inputSource = new boolean[]{false, false};
        boolean inputElement = true;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfBooleanWithFromIndex_ShouldReturnMinusOne_WhenArrayIsNull() {
        // Given
        boolean[] inputSource = null;
        boolean inputElement = true;
        int inputFromIndex = 0;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfBooleanWithFromIndex_ShouldReturnMinusOne_WhenArrayIsEmpty() {
        // Given
        boolean[] inputSource = new boolean[]{};
        boolean inputElement = true;
        int inputFromIndex = 0;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfBooleanWithFromIndex_ShouldReturnMinusOne_WhenFromIndexIsNegative() {
        // Given
        boolean[] inputSource = new boolean[]{true, false};
        boolean inputElement = true;
        int inputFromIndex = -1;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfBooleanWithFromIndex_ShouldReturnMinusOne_WhenFromIndexExceedsArrayLength() {
        // Given
        boolean[] inputSource = new boolean[]{true, false};
        boolean inputElement = true;
        int inputFromIndex = 3;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfBooleanWithFromIndex_ShouldReturnMinusOne_WhenFromIndexEqualsArrayLength() {
        // Given
        boolean[] inputSource = new boolean[]{true, false};
        boolean inputElement = true;
        int inputFromIndex = 2;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfBooleanWithFromIndex_ShouldReturnIndex_WhenElementIsPresentAfterFromIndex() {
        // Given
        boolean[] inputSource = new boolean[]{false, true, false, true};
        boolean inputElement = true;
        int inputFromIndex = 1;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(1);
    }

    @Test
    void indexOfBooleanWithFromIndex_ShouldReturnMinusOne_WhenElementIsAbsentAfterFromIndex() {
        // Given
        boolean[] inputSource = new boolean[]{true, false, false};
        boolean inputElement = true;
        int inputFromIndex = 1;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfByte_ShouldReturnMinusOne_WhenArrayIsNull() {
        // Given
        byte[] inputSource = null;
        byte inputElement = 1;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfByte_ShouldReturnMinusOne_WhenArrayIsEmpty() {
        // Given
        byte[] inputSource = new byte[]{};
        byte inputElement = 1;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfByte_ShouldReturnIndex_WhenElementIsPresent() {
        // Given
        byte[] inputSource = new byte[]{1, 2, 1};
        byte inputElement = 1;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement);

        // Then
        then(actual).isEqualTo(0);
    }

    @Test
    void indexOfByte_ShouldReturnMinusOne_WhenElementIsAbsent() {
        // Given
        byte[] inputSource = new byte[]{2, 3};
        byte inputElement = 1;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfByteWithFromIndex_ShouldReturnMinusOne_WhenArrayIsNull() {
        // Given
        byte[] inputSource = null;
        byte inputElement = 1;
        int inputFromIndex = 0;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfByteWithFromIndex_ShouldReturnMinusOne_WhenArrayIsEmpty() {
        // Given
        byte[] inputSource = new byte[]{};
        byte inputElement = 1;
        int inputFromIndex = 0;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfByteWithFromIndex_ShouldReturnMinusOne_WhenFromIndexIsNegative() {
        // Given
        byte[] inputSource = new byte[]{1, 2};
        byte inputElement = 1;
        int inputFromIndex = -1;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfByteWithFromIndex_ShouldReturnMinusOne_WhenFromIndexExceedsArrayLength() {
        // Given
        byte[] inputSource = new byte[]{1, 2};
        byte inputElement = 1;
        int inputFromIndex = 3;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfByteWithFromIndex_ShouldReturnMinusOne_WhenFromIndexEqualsArrayLength() {
        // Given
        byte[] inputSource = new byte[]{1, 2};
        byte inputElement = 1;
        int inputFromIndex = 2;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfByteWithFromIndex_ShouldReturnIndex_WhenElementIsPresentAfterFromIndex() {
        // Given
        byte[] inputSource = new byte[]{2, 1, 3, 1};
        byte inputElement = 1;
        int inputFromIndex = 1;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(1);
    }

    @Test
    void indexOfByteWithFromIndex_ShouldReturnMinusOne_WhenElementIsAbsentAfterFromIndex() {
        // Given
        byte[] inputSource = new byte[]{1, 2, 3};
        byte inputElement = 1;
        int inputFromIndex = 1;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfChar_ShouldReturnMinusOne_WhenArrayIsNull() {
        // Given
        char[] inputSource = null;
        char inputElement = 'a';

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfChar_ShouldReturnMinusOne_WhenArrayIsEmpty() {
        // Given
        char[] inputSource = new char[]{};
        char inputElement = 'a';

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfChar_ShouldReturnIndex_WhenElementIsPresent() {
        // Given
        char[] inputSource = new char[]{'a', 'b', 'a'};
        char inputElement = 'a';

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement);

        // Then
        then(actual).isEqualTo(0);
    }

    @Test
    void indexOfChar_ShouldReturnMinusOne_WhenElementIsAbsent() {
        // Given
        char[] inputSource = new char[]{'b', 'c'};
        char inputElement = 'a';

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfCharWithFromIndex_ShouldReturnMinusOne_WhenArrayIsNull() {
        // Given
        char[] inputSource = null;
        char inputElement = 'a';
        int inputFromIndex = 0;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfCharWithFromIndex_ShouldReturnMinusOne_WhenArrayIsEmpty() {
        // Given
        char[] inputSource = new char[]{};
        char inputElement = 'a';
        int inputFromIndex = 0;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfCharWithFromIndex_ShouldReturnMinusOne_WhenFromIndexIsNegative() {
        // Given
        char[] inputSource = new char[]{'a', 'b'};
        char inputElement = 'a';
        int inputFromIndex = -1;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfCharWithFromIndex_ShouldReturnMinusOne_WhenFromIndexExceedsArrayLength() {
        // Given
        char[] inputSource = new char[]{'a', 'b'};
        char inputElement = 'a';
        int inputFromIndex = 3;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfCharWithFromIndex_ShouldReturnMinusOne_WhenFromIndexEqualsArrayLength() {
        // Given
        char[] inputSource = new char[]{'a', 'b'};
        char inputElement = 'a';
        int inputFromIndex = 2;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfCharWithFromIndex_ShouldReturnIndex_WhenElementIsPresentAfterFromIndex() {
        // Given
        char[] inputSource = new char[]{'b', 'a', 'c', 'a'};
        char inputElement = 'a';
        int inputFromIndex = 1;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(1);
    }

    @Test
    void indexOfCharWithFromIndex_ShouldReturnMinusOne_WhenElementIsAbsentAfterFromIndex() {
        // Given
        char[] inputSource = new char[]{'a', 'b', 'c'};
        char inputElement = 'a';
        int inputFromIndex = 1;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfShort_ShouldReturnMinusOne_WhenArrayIsNull() {
        // Given
        short[] inputSource = null;
        short inputElement = 1;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfShort_ShouldReturnMinusOne_WhenArrayIsEmpty() {
        // Given
        short[] inputSource = new short[]{};
        short inputElement = 1;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfShort_ShouldReturnIndex_WhenElementIsPresent() {
        // Given
        short[] inputSource = new short[]{1, 2, 1};
        short inputElement = 1;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement);

        // Then
        then(actual).isEqualTo(0);
    }

    @Test
    void indexOfShort_ShouldReturnMinusOne_WhenElementIsAbsent() {
        // Given
        short[] inputSource = new short[]{2, 3};
        short inputElement = 1;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfShortWithFromIndex_ShouldReturnMinusOne_WhenArrayIsNull() {
        // Given
        short[] inputSource = null;
        short inputElement = 1;
        int inputFromIndex = 0;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfShortWithFromIndex_ShouldReturnMinusOne_WhenArrayIsEmpty() {
        // Given
        short[] inputSource = new short[]{};
        short inputElement = 1;
        int inputFromIndex = 0;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfShortWithFromIndex_ShouldReturnMinusOne_WhenFromIndexIsNegative() {
        // Given
        short[] inputSource = new short[]{1, 2};
        short inputElement = 1;
        int inputFromIndex = -1;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfShortWithFromIndex_ShouldReturnMinusOne_WhenFromIndexExceedsArrayLength() {
        // Given
        short[] inputSource = new short[]{1, 2};
        short inputElement = 1;
        int inputFromIndex = 3;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfShortWithFromIndex_ShouldReturnMinusOne_WhenFromIndexEqualsArrayLength() {
        // Given
        short[] inputSource = new short[]{1, 2};
        short inputElement = 1;
        int inputFromIndex = 2;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfShortWithFromIndex_ShouldReturnIndex_WhenElementIsPresentAfterFromIndex() {
        // Given
        short[] inputSource = new short[]{2, 1, 3, 1};
        short inputElement = 1;
        int inputFromIndex = 1;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(1);
    }

    @Test
    void indexOfShortWithFromIndex_ShouldReturnMinusOne_WhenElementIsAbsentAfterFromIndex() {
        // Given
        short[] inputSource = new short[]{1, 2, 3};
        short inputElement = 1;
        int inputFromIndex = 1;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfInt_ShouldReturnMinusOne_WhenArrayIsNull() {
        // Given
        int[] inputSource = null;
        int inputElement = 1;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfInt_ShouldReturnMinusOne_WhenArrayIsEmpty() {
        // Given
        int[] inputSource = new int[]{};
        int inputElement = 1;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfInt_ShouldReturnIndex_WhenElementIsPresent() {
        // Given
        int[] inputSource = new int[]{1, 2, 1};
        int inputElement = 1;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement);

        // Then
        then(actual).isEqualTo(0);
    }

    @Test
    void indexOfInt_ShouldReturnMinusOne_WhenElementIsAbsent() {
        // Given
        int[] inputSource = new int[]{2, 3};
        int inputElement = 1;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfIntWithFromIndex_ShouldReturnMinusOne_WhenArrayIsNull() {
        // Given
        int[] inputSource = null;
        int inputElement = 1;
        int inputFromIndex = 0;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfIntWithFromIndex_ShouldReturnMinusOne_WhenArrayIsEmpty() {
        // Given
        int[] inputSource = new int[]{};
        int inputElement = 1;
        int inputFromIndex = 0;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfIntWithFromIndex_ShouldReturnMinusOne_WhenFromIndexIsNegative() {
        // Given
        int[] inputSource = new int[]{1, 2};
        int inputElement = 1;
        int inputFromIndex = -1;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfIntWithFromIndex_ShouldReturnMinusOne_WhenFromIndexExceedsArrayLength() {
        // Given
        int[] inputSource = new int[]{1, 2};
        int inputElement = 1;
        int inputFromIndex = 3;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfIntWithFromIndex_ShouldReturnMinusOne_WhenFromIndexEqualsArrayLength() {
        // Given
        int[] inputSource = new int[]{1, 2};
        int inputElement = 1;
        int inputFromIndex = 2;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfIntWithFromIndex_ShouldReturnIndex_WhenElementIsPresentAfterFromIndex() {
        // Given
        int[] inputSource = new int[]{2, 1, 3, 1};
        int inputElement = 1;
        int inputFromIndex = 1;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(1);
    }

    @Test
    void indexOfIntWithFromIndex_ShouldReturnMinusOne_WhenElementIsAbsentAfterFromIndex() {
        // Given
        int[] inputSource = new int[]{1, 2, 3};
        int inputElement = 1;
        int inputFromIndex = 1;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfLong_ShouldReturnMinusOne_WhenArrayIsNull() {
        // Given
        long[] inputSource = null;
        long inputElement = 1L;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfLong_ShouldReturnMinusOne_WhenArrayIsEmpty() {
        // Given
        long[] inputSource = new long[]{};
        long inputElement = 1L;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfLong_ShouldReturnIndex_WhenElementIsPresent() {
        // Given
        long[] inputSource = new long[]{1L, 2L, 1L};
        long inputElement = 1L;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement);

        // Then
        then(actual).isEqualTo(0);
    }

    @Test
    void indexOfLong_ShouldReturnMinusOne_WhenElementIsAbsent() {
        // Given
        long[] inputSource = new long[]{2L, 3L};
        long inputElement = 1L;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfLongWithFromIndex_ShouldReturnMinusOne_WhenArrayIsNull() {
        // Given
        long[] inputSource = null;
        long inputElement = 1L;
        int inputFromIndex = 0;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfLongWithFromIndex_ShouldReturnMinusOne_WhenArrayIsEmpty() {
        // Given
        long[] inputSource = new long[]{};
        long inputElement = 1L;
        int inputFromIndex = 0;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfLongWithFromIndex_ShouldReturnMinusOne_WhenFromIndexIsNegative() {
        // Given
        long[] inputSource = new long[]{1L, 2L};
        long inputElement = 1L;
        int inputFromIndex = -1;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfLongWithFromIndex_ShouldReturnMinusOne_WhenFromIndexExceedsArrayLength() {
        // Given
        long[] inputSource = new long[]{1L, 2L};
        long inputElement = 1L;
        int inputFromIndex = 3;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfLongWithFromIndex_ShouldReturnMinusOne_WhenFromIndexEqualsArrayLength() {
        // Given
        long[] inputSource = new long[]{1L, 2L};
        long inputElement = 1L;
        int inputFromIndex = 2;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfLongWithFromIndex_ShouldReturnIndex_WhenElementIsPresentAfterFromIndex() {
        // Given
        long[] inputSource = new long[]{2L, 1L, 3L, 1L};
        long inputElement = 1L;
        int inputFromIndex = 1;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(1);
    }

    @Test
    void indexOfLongWithFromIndex_ShouldReturnMinusOne_WhenElementIsAbsentAfterFromIndex() {
        // Given
        long[] inputSource = new long[]{1L, 2L, 3L};
        long inputElement = 1L;
        int inputFromIndex = 1;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfFloat_ShouldReturnMinusOne_WhenArrayIsNull() {
        // Given
        float[] inputSource = null;
        float inputElement = 1.0F;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfFloat_ShouldReturnMinusOne_WhenArrayIsEmpty() {
        // Given
        float[] inputSource = new float[]{};
        float inputElement = 1.0F;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfFloat_ShouldReturnIndex_WhenElementIsPresent() {
        // Given
        float[] inputSource = new float[]{1.0F, 2.0F, 1.0F};
        float inputElement = 1.0F;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement);

        // Then
        then(actual).isEqualTo(0);
    }

    @Test
    void indexOfFloat_ShouldReturnMinusOne_WhenElementIsAbsent() {
        // Given
        float[] inputSource = new float[]{2.0F, 3.0F};
        float inputElement = 1.0F;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfFloatWithFromIndex_ShouldReturnMinusOne_WhenArrayIsNull() {
        // Given
        float[] inputSource = null;
        float inputElement = 1.0F;
        int inputFromIndex = 0;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfFloatWithFromIndex_ShouldReturnMinusOne_WhenArrayIsEmpty() {
        // Given
        float[] inputSource = new float[]{};
        float inputElement = 1.0F;
        int inputFromIndex = 0;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfFloatWithFromIndex_ShouldReturnMinusOne_WhenFromIndexIsNegative() {
        // Given
        float[] inputSource = new float[]{1.0F, 2.0F};
        float inputElement = 1.0F;
        int inputFromIndex = -1;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfFloatWithFromIndex_ShouldReturnMinusOne_WhenFromIndexExceedsArrayLength() {
        // Given
        float[] inputSource = new float[]{1.0F, 2.0F};
        float inputElement = 1.0F;
        int inputFromIndex = 3;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfFloatWithFromIndex_ShouldReturnMinusOne_WhenFromIndexEqualsArrayLength() {
        // Given
        float[] inputSource = new float[]{1.0F, 2.0F};
        float inputElement = 1.0F;
        int inputFromIndex = 2;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfFloatWithFromIndex_ShouldReturnIndex_WhenElementIsPresentAfterFromIndex() {
        // Given
        float[] inputSource = new float[]{2.0F, 1.0F, 3.0F, 1.0F};
        float inputElement = 1.0F;
        int inputFromIndex = 1;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(1);
    }

    @Test
    void indexOfFloatWithFromIndex_ShouldReturnMinusOne_WhenElementIsAbsentAfterFromIndex() {
        // Given
        float[] inputSource = new float[]{1.0F, 2.0F, 3.0F};
        float inputElement = 1.0F;
        int inputFromIndex = 1;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfDouble_ShouldReturnMinusOne_WhenArrayIsNull() {
        // Given
        double[] inputSource = null;
        double inputElement = 1.0;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfDouble_ShouldReturnMinusOne_WhenArrayIsEmpty() {
        // Given
        double[] inputSource = new double[]{};
        double inputElement = 1.0;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfDouble_ShouldReturnIndex_WhenElementIsPresent() {
        // Given
        double[] inputSource = new double[]{1.0, 2.0, 1.0};
        double inputElement = 1.0;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement);

        // Then
        then(actual).isEqualTo(0);
    }

    @Test
    void indexOfDouble_ShouldReturnMinusOne_WhenElementIsAbsent() {
        // Given
        double[] inputSource = new double[]{2.0, 3.0};
        double inputElement = 1.0;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfDoubleWithFromIndex_ShouldReturnMinusOne_WhenArrayIsNull() {
        // Given
        double[] inputSource = null;
        double inputElement = 1.0;
        int inputFromIndex = 0;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfDoubleWithFromIndex_ShouldReturnMinusOne_WhenArrayIsEmpty() {
        // Given
        double[] inputSource = new double[]{};
        double inputElement = 1.0;
        int inputFromIndex = 0;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfDoubleWithFromIndex_ShouldReturnMinusOne_WhenFromIndexIsNegative() {
        // Given
        double[] inputSource = new double[]{1.0, 2.0};
        double inputElement = 1.0;
        int inputFromIndex = -1;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfDoubleWithFromIndex_ShouldReturnMinusOne_WhenFromIndexExceedsArrayLength() {
        // Given
        double[] inputSource = new double[]{1.0, 2.0};
        double inputElement = 1.0;
        int inputFromIndex = 3;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfDoubleWithFromIndex_ShouldReturnMinusOne_WhenFromIndexEqualsArrayLength() {
        // Given
        double[] inputSource = new double[]{1.0, 2.0};
        double inputElement = 1.0;
        int inputFromIndex = 2;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfDoubleWithFromIndex_ShouldReturnIndex_WhenElementIsPresentAfterFromIndex() {
        // Given
        double[] inputSource = new double[]{2.0, 1.0, 3.0, 1.0};
        double inputElement = 1.0;
        int inputFromIndex = 1;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(1);
    }

    @Test
    void indexOfDoubleWithFromIndex_ShouldReturnMinusOne_WhenElementIsAbsentAfterFromIndex() {
        // Given
        double[] inputSource = new double[]{1.0, 2.0, 3.0};
        double inputElement = 1.0;
        int inputFromIndex = 1;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfGeneric_ShouldReturnMinusOne_WhenArrayIsNull() {
        // Given
        String[] inputSource = null;
        String inputElement = "a";

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfGeneric_ShouldReturnMinusOne_WhenArrayIsEmpty() {
        // Given
        String[] inputSource = new String[]{};
        String inputElement = "a";

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfGeneric_ShouldReturnIndex_WhenElementIsPresent() {
        // Given
        String[] inputSource = new String[]{"a", "b", "a"};
        String inputElement = "a";

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement);

        // Then
        then(actual).isEqualTo(0);
    }

    @Test
    void indexOfGeneric_ShouldReturnMinusOne_WhenElementIsAbsent() {
        // Given
        String[] inputSource = new String[]{"b", "c"};
        String inputElement = "a";

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfGeneric_ShouldReturnIndex_WhenNullElementIsPresent() {
        // Given
        String[] inputSource = new String[]{"a", null, "b"};
        String inputElement = null;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement);

        // Then
        then(actual).isEqualTo(1);
    }

    @Test
    void indexOfGenericWithFromIndex_ShouldReturnMinusOne_WhenArrayIsNull() {
        // Given
        String[] inputSource = null;
        String inputElement = "a";
        int inputFromIndex = 0;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfGenericWithFromIndex_ShouldReturnMinusOne_WhenArrayIsEmpty() {
        // Given
        String[] inputSource = new String[]{};
        String inputElement = "a";
        int inputFromIndex = 0;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfGenericWithFromIndex_ShouldReturnMinusOne_WhenFromIndexIsNegative() {
        // Given
        String[] inputSource = new String[]{"a", "b"};
        String inputElement = "a";
        int inputFromIndex = -1;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfGenericWithFromIndex_ShouldReturnMinusOne_WhenFromIndexExceedsArrayLength() {
        // Given
        String[] inputSource = new String[]{"a", "b"};
        String inputElement = "a";
        int inputFromIndex = 3;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfGenericWithFromIndex_ShouldReturnMinusOne_WhenFromIndexEqualsArrayLength() {
        // Given
        String[] inputSource = new String[]{"a", "b"};
        String inputElement = "a";
        int inputFromIndex = 2;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfGenericWithFromIndex_ShouldReturnIndex_WhenElementIsPresentAfterFromIndex() {
        // Given
        String[] inputSource = new String[]{"b", "a", "c", "a"};
        String inputElement = "a";
        int inputFromIndex = 1;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(1);
    }

    @Test
    void indexOfGenericWithFromIndex_ShouldReturnMinusOne_WhenElementIsAbsentAfterFromIndex() {
        // Given
        String[] inputSource = new String[]{"a", "b", "c"};
        String inputElement = "a";
        int inputFromIndex = 1;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfGenericWithFromIndex_ShouldReturnIndex_WhenNullElementIsPresentAfterFromIndex() {
        // Given
        String[] inputSource = new String[]{"a", "b", null, "c"};
        String inputElement = null;
        int inputFromIndex = 2;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(2);
    }

    @Test
    void indexOfGenericWithFromIndex_ShouldReturnMinusOne_WhenNullElementNotPresent() {
        // Given
        String[] inputSource = new String[]{"a", "b", "c"};
        String inputElement = null;
        int inputFromIndex = 0;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void indexOfGenericWithFromIndex_ShouldReturnMinusOne_WhenElementTypeIsIncompatible() {
        // Given
        String[] inputSource = new String[]{"a", "b"};
        Object inputElement = 1;
        int inputFromIndex = 0;

        // When
        int actual = ArrayUtils.indexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void lastIndexOfBoolean_ShouldThrowIllegalArgumentException_WhenArrayIsNull() {
        // Given
        boolean[] inputSource = null;
        boolean inputElement = true;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.lastIndexOf(inputSource, inputElement))
                .isInstanceOf(IllegalArgumentException.class)
                .withMessage("The input array cannot be null");
    }

    @Test
    void lastIndexOfBoolean_ShouldReturnMinusOne_WhenArrayIsEmpty() {
        // Given
        boolean[] inputSource = new boolean[]{};
        boolean inputElement = true;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void lastIndexOfBoolean_ShouldReturnIndex_WhenElementIsPresent() {
        // Given
        boolean[] inputSource = new boolean[]{true, false, true};
        boolean inputElement = true;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement);

        // Then
        then(actual).isEqualTo(2);
    }

    @Test
    void lastIndexOfBoolean_ShouldReturnMinusOne_WhenElementIsAbsent() {
        // Given
        boolean[] inputSource = new boolean[]{false, false};
        boolean inputElement = true;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void lastIndexOfBooleanWithFromIndex_ShouldReturnMinusOne_WhenArrayIsNull() {
        // Given
        boolean[] inputSource = null;
        boolean inputElement = true;
        int inputFromIndex = 0;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void lastIndexOfBooleanWithFromIndex_ShouldReturnMinusOne_WhenArrayIsEmpty() {
        // Given
        boolean[] inputSource = new boolean[]{};
        boolean inputElement = true;
        int inputFromIndex = 0;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void lastIndexOfBooleanWithFromIndex_ShouldReturnMinusOne_WhenFromIndexIsNegative() {
        // Given
        boolean[] inputSource = new boolean[]{true, false};
        boolean inputElement = true;
        int inputFromIndex = -1;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void lastIndexOfBooleanWithFromIndex_ShouldReturnMinusOne_WhenFromIndexExceedsArrayLength() {
        // Given
        boolean[] inputSource = new boolean[]{true, false};
        boolean inputElement = true;
        int inputFromIndex = 2;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void lastIndexOfBooleanWithFromIndex_ShouldReturnIndex_WhenElementIsPresentBeforeFromIndex() {
        // Given
        boolean[] inputSource = new boolean[]{true, false, true};
        boolean inputElement = true;
        int inputFromIndex = 2;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(2);
    }

    @Test
    void lastIndexOfBooleanWithFromIndex_ShouldReturnIndex_WhenElementIsPresentAtStart() {
        // Given
        boolean[] inputSource = new boolean[]{true, false, true};
        boolean inputElement = true;
        int inputFromIndex = 1;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(0);
    }

    @Test
    void lastIndexOfBooleanWithFromIndex_ShouldReturnMinusOne_WhenElementIsAbsentBeforeFromIndex() {
        // Given
        boolean[] inputSource = new boolean[]{false, false, true};
        boolean inputElement = true;
        int inputFromIndex = 1;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void lastIndexOfByte_ShouldThrowIllegalArgumentException_WhenArrayIsNull() {
        // Given
        byte[] inputSource = null;
        byte inputElement = 1;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.lastIndexOf(inputSource, inputElement))
                .isInstanceOf(IllegalArgumentException.class)
                .withMessage("The input array cannot be null");
    }

    @Test
    void lastIndexOfByte_ShouldReturnMinusOne_WhenArrayIsEmpty() {
        // Given
        byte[] inputSource = new byte[]{};
        byte inputElement = 1;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void lastIndexOfByte_ShouldReturnIndex_WhenElementIsPresent() {
        // Given
        byte[] inputSource = new byte[]{1, 2, 1};
        byte inputElement = 1;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement);

        // Then
        then(actual).isEqualTo(2);
    }

    @Test
    void lastIndexOfByte_ShouldReturnMinusOne_WhenElementIsAbsent() {
        // Given
        byte[] inputSource = new byte[]{2, 3};
        byte inputElement = 1;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void lastIndexOfByteWithFromIndex_ShouldReturnMinusOne_WhenArrayIsNull() {
        // Given
        byte[] inputSource = null;
        byte inputElement = 1;
        int inputFromIndex = 0;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void lastIndexOfByteWithFromIndex_ShouldReturnMinusOne_WhenArrayIsEmpty() {
        // Given
        byte[] inputSource = new byte[]{};
        byte inputElement = 1;
        int inputFromIndex = 0;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void lastIndexOfByteWithFromIndex_ShouldReturnMinusOne_WhenFromIndexIsNegative() {
        // Given
        byte[] inputSource = new byte[]{1, 2};
        byte inputElement = 1;
        int inputFromIndex = -1;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void lastIndexOfByteWithFromIndex_ShouldReturnMinusOne_WhenFromIndexExceedsArrayLength() {
        // Given
        byte[] inputSource = new byte[]{1, 2};
        byte inputElement = 1;
        int inputFromIndex = 2;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void lastIndexOfByteWithFromIndex_ShouldReturnIndex_WhenElementIsPresentBeforeFromIndex() {
        // Given
        byte[] inputSource = new byte[]{1, 2, 1};
        byte inputElement = 1;
        int inputFromIndex = 2;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(2);
    }

    @Test
    void lastIndexOfByteWithFromIndex_ShouldReturnIndex_WhenElementIsPresentAtStart() {
        // Given
        byte[] inputSource = new byte[]{1, 2, 1};
        byte inputElement = 1;
        int inputFromIndex = 1;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(0);
    }

    @Test
    void lastIndexOfByteWithFromIndex_ShouldReturnMinusOne_WhenElementIsAbsentBeforeFromIndex() {
        // Given
        byte[] inputSource = new byte[]{2, 3, 1};
        byte inputElement = 1;
        int inputFromIndex = 1;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void lastIndexOfChar_ShouldThrowIllegalArgumentException_WhenArrayIsNull() {
        // Given
        char[] inputSource = null;
        char inputElement = 'a';

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.lastIndexOf(inputSource, inputElement))
                .isInstanceOf(IllegalArgumentException.class)
                .withMessage("The input array cannot be null");
    }

    @Test
    void lastIndexOfChar_ShouldReturnMinusOne_WhenArrayIsEmpty() {
        // Given
        char[] inputSource = new char[]{};
        char inputElement = 'a';

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void lastIndexOfChar_ShouldReturnIndex_WhenElementIsPresent() {
        // Given
        char[] inputSource = new char[]{'a', 'b', 'a'};
        char inputElement = 'a';

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement);

        // Then
        then(actual).isEqualTo(2);
    }

    @Test
    void lastIndexOfChar_ShouldReturnMinusOne_WhenElementIsAbsent() {
        // Given
        char[] inputSource = new char[]{'b', 'c'};
        char inputElement = 'a';

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void lastIndexOfCharWithFromIndex_ShouldReturnMinusOne_WhenArrayIsNull() {
        // Given
        char[] inputSource = null;
        char inputElement = 'a';
        int inputFromIndex = 0;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void lastIndexOfCharWithFromIndex_ShouldReturnMinusOne_WhenArrayIsEmpty() {
        // Given
        char[] inputSource = new char[]{};
        char inputElement = 'a';
        int inputFromIndex = 0;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void lastIndexOfCharWithFromIndex_ShouldReturnMinusOne_WhenFromIndexIsNegative() {
        // Given
        char[] inputSource = new char[]{'a', 'b'};
        char inputElement = 'a';
        int inputFromIndex = -1;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void lastIndexOfCharWithFromIndex_ShouldReturnMinusOne_WhenFromIndexExceedsArrayLength() {
        // Given
        char[] inputSource = new char[]{'a', 'b'};
        char inputElement = 'a';
        int inputFromIndex = 2;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void lastIndexOfCharWithFromIndex_ShouldReturnIndex_WhenElementIsPresentBeforeFromIndex() {
        // Given
        char[] inputSource = new char[]{'a', 'b', 'a'};
        char inputElement = 'a';
        int inputFromIndex = 2;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(2);
    }

    @Test
    void lastIndexOfCharWithFromIndex_ShouldReturnIndex_WhenElementIsPresentAtStart() {
        // Given
        char[] inputSource = new char[]{'a', 'b', 'a'};
        char inputElement = 'a';
        int inputFromIndex = 1;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(0);
    }

    @Test
    void lastIndexOfCharWithFromIndex_ShouldReturnMinusOne_WhenElementIsAbsentBeforeFromIndex() {
        // Given
        char[] inputSource = new char[]{'b', 'c', 'a'};
        char inputElement = 'a';
        int inputFromIndex = 1;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void lastIndexOfShort_ShouldThrowIllegalArgumentException_WhenArrayIsNull() {
        // Given
        short[] inputSource = null;
        short inputElement = 1;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.lastIndexOf(inputSource, inputElement))
                .isInstanceOf(IllegalArgumentException.class)
                .withMessage("The input array cannot be null");
    }

    @Test
    void lastIndexOfShort_ShouldReturnMinusOne_WhenArrayIsEmpty() {
        // Given
        short[] inputSource = new short[]{};
        short inputElement = 1;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void lastIndexOfShort_ShouldReturnIndex_WhenElementIsPresent() {
        // Given
        short[] inputSource = new short[]{1, 2, 1};
        short inputElement = 1;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement);

        // Then
        then(actual).isEqualTo(2);
    }

    @Test
    void lastIndexOfShort_ShouldReturnMinusOne_WhenElementIsAbsent() {
        // Given
        short[] inputSource = new short[]{2, 3};
        short inputElement = 1;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void lastIndexOfShortWithFromIndex_ShouldReturnMinusOne_WhenArrayIsNull() {
        // Given
        short[] inputSource = null;
        short inputElement = 1;
        int inputFromIndex = 0;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void lastIndexOfShortWithFromIndex_ShouldReturnMinusOne_WhenArrayIsEmpty() {
        // Given
        short[] inputSource = new short[]{};
        short inputElement = 1;
        int inputFromIndex = 0;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void lastIndexOfShortWithFromIndex_ShouldReturnMinusOne_WhenFromIndexIsNegative() {
        // Given
        short[] inputSource = new short[]{1, 2};
        short inputElement = 1;
        int inputFromIndex = -1;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void lastIndexOfShortWithFromIndex_ShouldReturnMinusOne_WhenFromIndexExceedsArrayLength() {
        // Given
        short[] inputSource = new short[]{1, 2};
        short inputElement = 1;
        int inputFromIndex = 2;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void lastIndexOfShortWithFromIndex_ShouldReturnIndex_WhenElementIsPresentBeforeFromIndex() {
        // Given
        short[] inputSource = new short[]{1, 2, 1};
        short inputElement = 1;
        int inputFromIndex = 2;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(2);
    }

    @Test
    void lastIndexOfShortWithFromIndex_ShouldReturnIndex_WhenElementIsPresentAtStart() {
        // Given
        short[] inputSource = new short[]{1, 2, 1};
        short inputElement = 1;
        int inputFromIndex = 1;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(0);
    }

    @Test
    void lastIndexOfShortWithFromIndex_ShouldReturnMinusOne_WhenElementIsAbsentBeforeFromIndex() {
        // Given
        short[] inputSource = new short[]{2, 3, 1};
        short inputElement = 1;
        int inputFromIndex = 1;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void lastIndexOfInt_ShouldThrowIllegalArgumentException_WhenArrayIsNull() {
        // Given
        int[] inputSource = null;
        int inputElement = 1;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.lastIndexOf(inputSource, inputElement))
                .isInstanceOf(IllegalArgumentException.class)
                .withMessage("The input array cannot be null");
    }

    @Test
    void lastIndexOfInt_ShouldReturnMinusOne_WhenArrayIsEmpty() {
        // Given
        int[] inputSource = new int[]{};
        int inputElement = 1;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void lastIndexOfInt_ShouldReturnIndex_WhenElementIsPresent() {
        // Given
        int[] inputSource = new int[]{1, 2, 1};
        int inputElement = 1;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement);

        // Then
        then(actual).isEqualTo(2);
    }

    @Test
    void lastIndexOfInt_ShouldReturnMinusOne_WhenElementIsAbsent() {
        // Given
        int[] inputSource = new int[]{2, 3};
        int inputElement = 1;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void lastIndexOfIntWithFromIndex_ShouldReturnMinusOne_WhenArrayIsNull() {
        // Given
        int[] inputSource = null;
        int inputElement = 1;
        int inputFromIndex = 0;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void lastIndexOfIntWithFromIndex_ShouldReturnMinusOne_WhenArrayIsEmpty() {
        // Given
        int[] inputSource = new int[]{};
        int inputElement = 1;
        int inputFromIndex = 0;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void lastIndexOfIntWithFromIndex_ShouldReturnMinusOne_WhenFromIndexIsNegative() {
        // Given
        int[] inputSource = new int[]{1, 2};
        int inputElement = 1;
        int inputFromIndex = -1;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void lastIndexOfIntWithFromIndex_ShouldReturnMinusOne_WhenFromIndexExceedsArrayLength() {
        // Given
        int[] inputSource = new int[]{1, 2};
        int inputElement = 1;
        int inputFromIndex = 2;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void lastIndexOfIntWithFromIndex_ShouldReturnIndex_WhenElementIsPresentBeforeFromIndex() {
        // Given
        int[] inputSource = new int[]{1, 2, 1};
        int inputElement = 1;
        int inputFromIndex = 2;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(2);
    }

    @Test
    void lastIndexOfIntWithFromIndex_ShouldReturnIndex_WhenElementIsPresentAtStart() {
        // Given
        int[] inputSource = new int[]{1, 2, 1};
        int inputElement = 1;
        int inputFromIndex = 1;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(0);
    }

    @Test
    void lastIndexOfIntWithFromIndex_ShouldReturnMinusOne_WhenElementIsAbsentBeforeFromIndex() {
        // Given
        int[] inputSource = new int[]{2, 3, 1};
        int inputElement = 1;
        int inputFromIndex = 1;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void lastIndexOfLong_ShouldThrowIllegalArgumentException_WhenArrayIsNull() {
        // Given
        long[] inputSource = null;
        long inputElement = 1L;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.lastIndexOf(inputSource, inputElement))
                .isInstanceOf(IllegalArgumentException.class)
                .withMessage("The input array cannot be null");
    }

    @Test
    void lastIndexOfLong_ShouldReturnMinusOne_WhenArrayIsEmpty() {
        // Given
        long[] inputSource = new long[]{};
        long inputElement = 1L;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void lastIndexOfLong_ShouldReturnIndex_WhenElementIsPresent() {
        // Given
        long[] inputSource = new long[]{1L, 2L, 1L};
        long inputElement = 1L;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement);

        // Then
        then(actual).isEqualTo(2);
    }

    @Test
    void lastIndexOfLong_ShouldReturnMinusOne_WhenElementIsAbsent() {
        // Given
        long[] inputSource = new long[]{2L, 3L};
        long inputElement = 1L;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void lastIndexOfLongWithFromIndex_ShouldReturnMinusOne_WhenArrayIsNull() {
        // Given
        long[] inputSource = null;
        long inputElement = 1L;
        int inputFromIndex = 0;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void lastIndexOfLongWithFromIndex_ShouldReturnMinusOne_WhenArrayIsEmpty() {
        // Given
        long[] inputSource = new long[]{};
        long inputElement = 1L;
        int inputFromIndex = 0;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void lastIndexOfLongWithFromIndex_ShouldReturnMinusOne_WhenFromIndexIsNegative() {
        // Given
        long[] inputSource = new long[]{1L, 2L};
        long inputElement = 1L;
        int inputFromIndex = -1;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void lastIndexOfLongWithFromIndex_ShouldReturnMinusOne_WhenFromIndexExceedsArrayLength() {
        // Given
        long[] inputSource = new long[]{1L, 2L};
        long inputElement = 1L;
        int inputFromIndex = 2;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void lastIndexOfLongWithFromIndex_ShouldReturnIndex_WhenElementIsPresentBeforeFromIndex() {
        // Given
        long[] inputSource = new long[]{1L, 2L, 1L};
        long inputElement = 1L;
        int inputFromIndex = 2;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(2);
    }

    @Test
    void lastIndexOfLongWithFromIndex_ShouldReturnIndex_WhenElementIsPresentAtStart() {
        // Given
        long[] inputSource = new long[]{1L, 2L, 1L};
        long inputElement = 1L;
        int inputFromIndex = 1;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(0);
    }

    @Test
    void lastIndexOfLongWithFromIndex_ShouldReturnMinusOne_WhenElementIsAbsentBeforeFromIndex() {
        // Given
        long[] inputSource = new long[]{2L, 3L, 1L};
        long inputElement = 1L;
        int inputFromIndex = 1;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void lastIndexOfFloat_ShouldThrowIllegalArgumentException_WhenArrayIsNull() {
        // Given
        float[] inputSource = null;
        float inputElement = 1.0F;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.lastIndexOf(inputSource, inputElement))
                .isInstanceOf(IllegalArgumentException.class)
                .withMessage("The input array cannot be null");
    }

    @Test
    void lastIndexOfFloat_ShouldReturnMinusOne_WhenArrayIsEmpty() {
        // Given
        float[] inputSource = new float[]{};
        float inputElement = 1.0F;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void lastIndexOfFloat_ShouldReturnIndex_WhenElementIsPresent() {
        // Given
        float[] inputSource = new float[]{1.0F, 2.0F, 1.0F};
        float inputElement = 1.0F;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement);

        // Then
        then(actual).isEqualTo(2);
    }

    @Test
    void lastIndexOfFloat_ShouldReturnMinusOne_WhenElementIsAbsent() {
        // Given
        float[] inputSource = new float[]{2.0F, 3.0F};
        float inputElement = 1.0F;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void lastIndexOfFloatWithFromIndex_ShouldReturnMinusOne_WhenArrayIsNull() {
        // Given
        float[] inputSource = null;
        float inputElement = 1.0F;
        int inputFromIndex = 0;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void lastIndexOfFloatWithFromIndex_ShouldReturnMinusOne_WhenArrayIsEmpty() {
        // Given
        float[] inputSource = new float[]{};
        float inputElement = 1.0F;
        int inputFromIndex = 0;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void lastIndexOfFloatWithFromIndex_ShouldReturnMinusOne_WhenFromIndexIsNegative() {
        // Given
        float[] inputSource = new float[]{1.0F, 2.0F};
        float inputElement = 1.0F;
        int inputFromIndex = -1;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void lastIndexOfFloatWithFromIndex_ShouldReturnMinusOne_WhenFromIndexExceedsArrayLength() {
        // Given
        float[] inputSource = new float[]{1.0F, 2.0F};
        float inputElement = 1.0F;
        int inputFromIndex = 2;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void lastIndexOfFloatWithFromIndex_ShouldReturnIndex_WhenElementIsPresentBeforeFromIndex() {
        // Given
        float[] inputSource = new float[]{1.0F, 2.0F, 1.0F};
        float inputElement = 1.0F;
        int inputFromIndex = 2;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(2);
    }

    @Test
    void lastIndexOfFloatWithFromIndex_ShouldReturnIndex_WhenElementIsPresentAtStart() {
        // Given
        float[] inputSource = new float[]{1.0F, 2.0F, 1.0F};
        float inputElement = 1.0F;
        int inputFromIndex = 1;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(0);
    }

    @Test
    void lastIndexOfFloatWithFromIndex_ShouldReturnMinusOne_WhenElementIsAbsentBeforeFromIndex() {
        // Given
        float[] inputSource = new float[]{2.0F, 3.0F, 1.0F};
        float inputElement = 1.0F;
        int inputFromIndex = 1;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void lastIndexOfDouble_ShouldThrowIllegalArgumentException_WhenArrayIsNull() {
        // Given
        double[] inputSource = null;
        double inputElement = 1.0;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.lastIndexOf(inputSource, inputElement))
                .isInstanceOf(IllegalArgumentException.class)
                .withMessage("The input array cannot be null");
    }

    @Test
    void lastIndexOfDouble_ShouldReturnMinusOne_WhenArrayIsEmpty() {
        // Given
        double[] inputSource = new double[]{};
        double inputElement = 1.0;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void lastIndexOfDouble_ShouldReturnIndex_WhenElementIsPresent() {
        // Given
        double[] inputSource = new double[]{1.0, 2.0, 1.0};
        double inputElement = 1.0;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement);

        // Then
        then(actual).isEqualTo(2);
    }

    @Test
    void lastIndexOfDouble_ShouldReturnMinusOne_WhenElementIsAbsent() {
        // Given
        double[] inputSource = new double[]{2.0, 3.0};
        double inputElement = 1.0;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void lastIndexOfDoubleWithFromIndex_ShouldReturnMinusOne_WhenArrayIsNull() {
        // Given
        double[] inputSource = null;
        double inputElement = 1.0;
        int inputFromIndex = 0;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void lastIndexOfDoubleWithFromIndex_ShouldReturnMinusOne_WhenArrayIsEmpty() {
        // Given
        double[] inputSource = new double[]{};
        double inputElement = 1.0;
        int inputFromIndex = 0;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void lastIndexOfDoubleWithFromIndex_ShouldReturnMinusOne_WhenFromIndexIsNegative() {
        // Given
        double[] inputSource = new double[]{1.0, 2.0};
        double inputElement = 1.0;
        int inputFromIndex = -1;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void lastIndexOfDoubleWithFromIndex_ShouldReturnMinusOne_WhenFromIndexExceedsArrayLength() {
        // Given
        double[] inputSource = new double[]{1.0, 2.0};
        double inputElement = 1.0;
        int inputFromIndex = 2;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void lastIndexOfDoubleWithFromIndex_ShouldReturnIndex_WhenElementIsPresentBeforeFromIndex() {
        // Given
        double[] inputSource = new double[]{1.0, 2.0, 1.0};
        double inputElement = 1.0;
        int inputFromIndex = 2;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(2);
    }

    @Test
    void lastIndexOfDoubleWithFromIndex_ShouldReturnIndex_WhenElementIsPresentAtStart() {
        // Given
        double[] inputSource = new double[]{1.0, 2.0, 1.0};
        double inputElement = 1.0;
        int inputFromIndex = 1;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(0);
    }

    @Test
    void lastIndexOfDoubleWithFromIndex_ShouldReturnMinusOne_WhenElementIsAbsentBeforeFromIndex() {
        // Given
        double[] inputSource = new double[]{2.0, 3.0, 1.0};
        double inputElement = 1.0;
        int inputFromIndex = 1;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void lastIndexOfGeneric_ShouldThrowIllegalArgumentException_WhenArrayIsNull() {
        // Given
        String[] inputSource = null;
        String inputElement = "a";

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.lastIndexOf(inputSource, inputElement))
                .isInstanceOf(IllegalArgumentException.class)
                .withMessage("The input array cannot be null");
    }

    @Test
    void lastIndexOfGeneric_ShouldReturnMinusOne_WhenArrayIsEmpty() {
        // Given
        String[] inputSource = new String[]{};
        String inputElement = "a";

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void lastIndexOfGeneric_ShouldReturnIndex_WhenElementIsPresent() {
        // Given
        String[] inputSource = new String[]{"a", "b", "a"};
        String inputElement = "a";

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement);

        // Then
        then(actual).isEqualTo(2);
    }

    @Test
    void lastIndexOfGeneric_ShouldReturnMinusOne_WhenElementIsAbsent() {
        // Given
        String[] inputSource = new String[]{"b", "c"};
        String inputElement = "a";

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void lastIndexOfGeneric_ShouldReturnIndex_WhenNullElementIsPresent() {
        // Given
        String[] inputSource = new String[]{"a", null, "b", null};
        String inputElement = null;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement);

        // Then
        then(actual).isEqualTo(3);
    }

    @Test
    void lastIndexOfGenericWithFromIndex_ShouldReturnMinusOne_WhenArrayIsNull() {
        // Given
        String[] inputSource = null;
        String inputElement = "a";
        int inputFromIndex = 0;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void lastIndexOfGenericWithFromIndex_ShouldReturnMinusOne_WhenArrayIsEmpty() {
        // Given
        String[] inputSource = new String[]{};
        String inputElement = "a";
        int inputFromIndex = 0;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void lastIndexOfGenericWithFromIndex_ShouldReturnMinusOne_WhenFromIndexIsNegative() {
        // Given
        String[] inputSource = new String[]{"a", "b"};
        String inputElement = "a";
        int inputFromIndex = -1;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void lastIndexOfGenericWithFromIndex_ShouldReturnMinusOne_WhenFromIndexExceedsArrayLength() {
        // Given
        String[] inputSource = new String[]{"a", "b"};
        String inputElement = "a";
        int inputFromIndex = 2;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void lastIndexOfGenericWithFromIndex_ShouldReturnIndex_WhenElementIsPresentBeforeFromIndex() {
        // Given
        String[] inputSource = new String[]{"a", "b", "a"};
        String inputElement = "a";
        int inputFromIndex = 2;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(2);
    }

    @Test
    void lastIndexOfGenericWithFromIndex_ShouldReturnIndex_WhenElementIsPresentAtStart() {
        // Given
        String[] inputSource = new String[]{"a", "b", "a"};
        String inputElement = "a";
        int inputFromIndex = 1;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(0);
    }

    @Test
    void lastIndexOfGenericWithFromIndex_ShouldReturnMinusOne_WhenElementIsAbsentBeforeFromIndex() {
        // Given
        String[] inputSource = new String[]{"b", "c", "a"};
        String inputElement = "a";
        int inputFromIndex = 1;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void lastIndexOfGenericWithFromIndex_ShouldReturnIndex_WhenNullElementIsPresentBeforeFromIndex() {
        // Given
        String[] inputSource = new String[]{"a", null, "b", null};
        String inputElement = null;
        int inputFromIndex = 3;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(3);
    }

    @Test
    void lastIndexOfGenericWithFromIndex_ShouldReturnIndex_WhenNullElementIsPresentAtStart() {
        // Given
        String[] inputSource = new String[]{null, "a", "b", null};
        String inputElement = null;
        int inputFromIndex = 1;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(0);
    }

    @Test
    void lastIndexOfGenericWithFromIndex_ShouldReturnMinusOne_WhenNullElementNotPresent() {
        // Given
        String[] inputSource = new String[]{"a", "b", "c"};
        String inputElement = null;
        int inputFromIndex = 2;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void lastIndexOfGenericWithFromIndex_ShouldReturnMinusOne_WhenElementTypeIsIncompatible() {
        // Given
        String[] inputSource = new String[]{"a", "b"};
        Object inputElement = 1;
        int inputFromIndex = 1;

        // When
        int actual = ArrayUtils.lastIndexOf(inputSource, inputElement, inputFromIndex);

        // Then
        then(actual).isEqualTo(-1);
    }

    @Test
    void mergeBoolean_ShouldReturnEmptyArray_WhenBothArraysAreNull() {
        // Given
        boolean[] inputArray1 = null;
        boolean[] inputArray2 = null;

        // When
        boolean[] actual = ArrayUtils.merge(inputArray1, inputArray2);

        // Then
        then(actual)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void mergeBoolean_ShouldReturnEmptyArray_WhenBothArraysAreEmpty() {
        // Given
        boolean[] inputArray1 = new boolean[0];
        boolean[] inputArray2 = new boolean[0];

        // When
        boolean[] actual = ArrayUtils.merge(inputArray1, inputArray2);

        // Then
        then(actual)
                .isNotSameAs(inputArray1)
                .isNotSameAs(inputArray2)
                .isEmpty();
    }

    @Test
    void mergeBoolean_ShouldReturnCopyOfArray2_WhenArray1IsNull() {
        // Given
        boolean[] inputArray1 = null;
        boolean[] inputArray2 = new boolean[]{true, false};

        // When
        boolean[] actual = ArrayUtils.merge(inputArray1, inputArray2);

        // Then
        then(actual)
                .isNotSameAs(inputArray2)
                .hasSize(2)
                .containsExactly(true, false);
    }

    @Test
    void mergeBoolean_ShouldReturnCopyOfArray2_WhenArray1IsEmpty() {
        // Given
        boolean[] inputArray1 = new boolean[0];
        boolean[] inputArray2 = new boolean[]{true, false};

        // When
        boolean[] actual = ArrayUtils.merge(inputArray1, inputArray2);

        // Then
        then(actual)
                .isNotSameAs(inputArray2)
                .hasSize(2)
                .containsExactly(true, false);
    }

    @Test
    void mergeBoolean_ShouldReturnCopyOfArray1_WhenArray2IsNull() {
        // Given
        boolean[] inputArray1 = new boolean[]{true, false};
        boolean[] inputArray2 = null;

        // When
        boolean[] actual = ArrayUtils.merge(inputArray1, inputArray2);

        // Then
        then(actual)
                .isNotSameAs(inputArray1)
                .hasSize(2)
                .containsExactly(true, false);
    }

    @Test
    void mergeBoolean_ShouldReturnCopyOfArray1_WhenArray2IsEmpty() {
        // Given
        boolean[] inputArray1 = new boolean[]{true, false};
        boolean[] inputArray2 = new boolean[0];

        // When
        boolean[] actual = ArrayUtils.merge(inputArray1, inputArray2);

        // Then
        then(actual)
                .isNotSameAs(inputArray1)
                .hasSize(2)
                .containsExactly(true, false);
    }

    @Test
    void mergeBoolean_ShouldMergeArrays_WhenBothArraysAreNotEmpty() {
        // Given
        boolean[] inputArray1 = new boolean[]{true, false};
        boolean[] inputArray2 = new boolean[]{false, true};

        // When
        boolean[] actual = ArrayUtils.merge(inputArray1, inputArray2);

        // Then
        then(actual)
                .isNotSameAs(inputArray1)
                .isNotSameAs(inputArray2)
                .hasSize(4)
                .containsExactly(true, false, false, true);
    }

    @Test
    void mergeByte_ShouldReturnEmptyArray_WhenBothArraysAreNull() {
        // Given
        byte[] inputArray1 = null;
        byte[] inputArray2 = null;

        // When
        byte[] actual = ArrayUtils.merge(inputArray1, inputArray2);

        // Then
        then(actual)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void mergeByte_ShouldReturnEmptyArray_WhenBothArraysAreEmpty() {
        // Given
        byte[] inputArray1 = new byte[0];
        byte[] inputArray2 = new byte[0];

        // When
        byte[] actual = ArrayUtils.merge(inputArray1, inputArray2);

        // Then
        then(actual)
                .isNotSameAs(inputArray1)
                .isNotSameAs(inputArray2)
                .isEmpty();
    }

    @Test
    void mergeByte_ShouldReturnCopyOfArray2_WhenArray1IsNull() {
        // Given
        byte[] inputArray1 = null;
        byte[] inputArray2 = new byte[]{-128, -127};

        // When
        byte[] actual = ArrayUtils.merge(inputArray1, inputArray2);

        // Then
        then(actual)
                .isNotSameAs(inputArray2)
                .hasSize(2)
                .containsExactly(-128, -127);
    }

    @Test
    void mergeByte_ShouldReturnCopyOfArray2_WhenArray1IsEmpty() {
        // Given
        byte[] inputArray1 = new byte[0];
        byte[] inputArray2 = new byte[]{-128, -127};

        // When
        byte[] actual = ArrayUtils.merge(inputArray1, inputArray2);

        // Then
        then(actual)
                .isNotSameAs(inputArray2)
                .hasSize(2)
                .containsExactly(-128, -127);
    }

    @Test
    void mergeByte_ShouldReturnCopyOfArray1_WhenArray2IsNull() {
        // Given
        byte[] inputArray1 = new byte[]{-128, -127};
        byte[] inputArray2 = null;

        // When
        byte[] actual = ArrayUtils.merge(inputArray1, inputArray2);

        // Then
        then(actual)
                .isNotSameAs(inputArray1)
                .hasSize(2)
                .containsExactly(-128, -127);
    }

    @Test
    void mergeByte_ShouldReturnCopyOfArray1_WhenArray2IsEmpty() {
        // Given
        byte[] inputArray1 = new byte[]{-128, -127};
        byte[] inputArray2 = new byte[0];

        // When
        byte[] actual = ArrayUtils.merge(inputArray1, inputArray2);

        // Then
        then(actual)
                .isNotSameAs(inputArray1)
                .hasSize(2)
                .containsExactly(-128, -127);
    }

    @Test
    void mergeByte_ShouldMergeArrays_WhenBothArraysAreNotEmpty() {
        // Given
        byte[] inputArray1 = new byte[]{-128, -127};
        byte[] inputArray2 = new byte[]{-126, -125};

        // When
        byte[] actual = ArrayUtils.merge(inputArray1, inputArray2);

        // Then
        then(actual)
                .isNotSameAs(inputArray1)
                .isNotSameAs(inputArray2)
                .hasSize(4)
                .containsExactly(-128, -127, -126, -125);
    }

    @Test
    void mergeChar_ShouldReturnEmptyArray_WhenBothArraysAreNull() {
        // Given
        char[] inputArray1 = null;
        char[] inputArray2 = null;

        // When
        char[] actual = ArrayUtils.merge(inputArray1, inputArray2);

        // Then
        then(actual)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void mergeChar_ShouldReturnEmptyArray_WhenBothArraysAreEmpty() {
        // Given
        char[] inputArray1 = new char[0];
        char[] inputArray2 = new char[0];

        // When
        char[] actual = ArrayUtils.merge(inputArray1, inputArray2);

        // Then
        then(actual)
                .isNotSameAs(inputArray1)
                .isNotSameAs(inputArray2)
                .isEmpty();
    }

    @Test
    void mergeChar_ShouldReturnCopyOfArray2_WhenArray1IsNull() {
        // Given
        char[] inputArray1 = null;
        char[] inputArray2 = new char[]{'a', 'b'};

        // When
        char[] actual = ArrayUtils.merge(inputArray1, inputArray2);

        // Then
        then(actual)
                .isNotSameAs(inputArray2)
                .hasSize(2)
                .containsExactly('a', 'b');
    }

    @Test
    void mergeChar_ShouldReturnCopyOfArray2_WhenArray1IsEmpty() {
        // Given
        char[] inputArray1 = new char[0];
        char[] inputArray2 = new char[]{'a', 'b'};

        // When
        char[] actual = ArrayUtils.merge(inputArray1, inputArray2);

        // Then
        then(actual)
                .isNotSameAs(inputArray2)
                .hasSize(2)
                .containsExactly('a', 'b');
    }

    @Test
    void mergeChar_ShouldReturnCopyOfArray1_WhenArray2IsNull() {
        // Given
        char[] inputArray1 = new char[]{'a', 'b'};
        char[] inputArray2 = null;

        // When
        char[] actual = ArrayUtils.merge(inputArray1, inputArray2);

        // Then
        then(actual)
                .isNotSameAs(inputArray1)
                .hasSize(2)
                .containsExactly('a', 'b');
    }

    @Test
    void mergeChar_ShouldReturnCopyOfArray1_WhenArray2IsEmpty() {
        // Given
        char[] inputArray1 = new char[]{'a', 'b'};
        char[] inputArray2 = new char[0];

        // When
        char[] actual = ArrayUtils.merge(inputArray1, inputArray2);

        // Then
        then(actual)
                .isNotSameAs(inputArray1)
                .hasSize(2)
                .containsExactly('a', 'b');
    }

    @Test
    void mergeChar_ShouldMergeArrays_WhenBothArraysAreNotEmpty() {
        // Given
        char[] inputArray1 = new char[]{'a', 'b'};
        char[] inputArray2 = new char[]{'c', 'd'};

        // When
        char[] actual = ArrayUtils.merge(inputArray1, inputArray2);

        // Then
        then(actual)
                .isNotSameAs(inputArray1)
                .isNotSameAs(inputArray2)
                .hasSize(4)
                .containsExactly('a', 'b', 'c', 'd');
    }

    @Test
    void mergeShort_ShouldReturnEmptyArray_WhenBothArraysAreNull() {
        // Given
        short[] inputArray1 = null;
        short[] inputArray2 = null;

        // When
        short[] actual = ArrayUtils.merge(inputArray1, inputArray2);

        // Then
        then(actual)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void mergeShort_ShouldReturnEmptyArray_WhenBothArraysAreEmpty() {
        // Given
        short[] inputArray1 = new short[0];
        short[] inputArray2 = new short[0];

        // When
        short[] actual = ArrayUtils.merge(inputArray1, inputArray2);

        // Then
        then(actual)
                .isNotSameAs(inputArray1)
                .isNotSameAs(inputArray2)
                .isEmpty();
    }

    @Test
    void mergeShort_ShouldReturnCopyOfArray2_WhenArray1IsNull() {
        // Given
        short[] inputArray1 = null;
        short[] inputArray2 = new short[]{32767, 32766};

        // When
        short[] actual = ArrayUtils.merge(inputArray1, inputArray2);

        // Then
        then(actual)
                .isNotSameAs(inputArray2)
                .hasSize(2)
                .containsExactly(32767, 32766);
    }

    @Test
    void mergeShort_ShouldReturnCopyOfArray2_WhenArray1IsEmpty() {
        // Given
        short[] inputArray1 = new short[0];
        short[] inputArray2 = new short[]{32767, 32766};

        // When
        short[] actual = ArrayUtils.merge(inputArray1, inputArray2);

        // Then
        then(actual)
                .isNotSameAs(inputArray2)
                .hasSize(2)
                .containsExactly(32767, 32766);
    }

    @Test
    void mergeShort_ShouldReturnCopyOfArray1_WhenArray2IsNull() {
        // Given
        short[] inputArray1 = new short[]{32767, 32766};
        short[] inputArray2 = null;

        // When
        short[] actual = ArrayUtils.merge(inputArray1, inputArray2);

        // Then
        then(actual)
                .isNotSameAs(inputArray1)
                .hasSize(2)
                .containsExactly(32767, 32766);
    }

    @Test
    void mergeShort_ShouldReturnCopyOfArray1_WhenArray2IsEmpty() {
        // Given
        short[] inputArray1 = new short[]{32767, 32766};
        short[] inputArray2 = new short[0];

        // When
        short[] actual = ArrayUtils.merge(inputArray1, inputArray2);

        // Then
        then(actual)
                .isNotSameAs(inputArray1)
                .hasSize(2)
                .containsExactly(32767, 32766);
    }

    @Test
    void mergeShort_ShouldMergeArrays_WhenBothArraysAreNotEmpty() {
        // Given
        short[] inputArray1 = new short[]{32767, 32766};
        short[] inputArray2 = new short[]{32765, 32764};

        // When
        short[] actual = ArrayUtils.merge(inputArray1, inputArray2);

        // Then
        then(actual)
                .isNotSameAs(inputArray1)
                .isNotSameAs(inputArray2)
                .hasSize(4)
                .containsExactly(32767, 32766, 32765, 32764);
    }

    @Test
    void mergeInt_ShouldReturnEmptyArray_WhenBothArraysAreNull() {
        // Given
        int[] inputArray1 = null;
        int[] inputArray2 = null;

        // When
        int[] actual = ArrayUtils.merge(inputArray1, inputArray2);

        // Then
        then(actual)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void mergeInt_ShouldReturnEmptyArray_WhenBothArraysAreEmpty() {
        // Given
        int[] inputArray1 = new int[0];
        int[] inputArray2 = new int[0];

        // When
        int[] actual = ArrayUtils.merge(inputArray1, inputArray2);

        // Then
        then(actual)
                .isNotSameAs(inputArray1)
                .isNotSameAs(inputArray2)
                .isEmpty();
    }

    @Test
    void mergeInt_ShouldReturnCopyOfArray2_WhenArray1IsNull() {
        // Given
        int[] inputArray1 = null;
        int[] inputArray2 = new int[]{1, 2};

        // When
        int[] actual = ArrayUtils.merge(inputArray1, inputArray2);

        // Then
        then(actual)
                .isNotSameAs(inputArray2)
                .hasSize(2)
                .containsExactly(1, 2);
    }

    @Test
    void mergeInt_ShouldReturnCopyOfArray2_WhenArray1IsEmpty() {
        // Given
        int[] inputArray1 = new int[0];
        int[] inputArray2 = new int[]{1, 2};

        // When
        int[] actual = ArrayUtils.merge(inputArray1, inputArray2);

        // Then
        then(actual)
                .isNotSameAs(inputArray2)
                .hasSize(2)
                .containsExactly(1, 2);
    }

    @Test
    void mergeInt_ShouldReturnCopyOfArray1_WhenArray2IsNull() {
        // Given
        int[] inputArray1 = new int[]{1, 2};
        int[] inputArray2 = null;

        // When
        int[] actual = ArrayUtils.merge(inputArray1, inputArray2);

        // Then
        then(actual)
                .isNotSameAs(inputArray1)
                .hasSize(2)
                .containsExactly(1, 2);
    }

    @Test
    void mergeInt_ShouldReturnCopyOfArray1_WhenArray2IsEmpty() {
        // Given
        int[] inputArray1 = new int[]{1, 2};
        int[] inputArray2 = new int[0];

        // When
        int[] actual = ArrayUtils.merge(inputArray1, inputArray2);

        // Then
        then(actual)
                .isNotSameAs(inputArray1)
                .hasSize(2)
                .containsExactly(1, 2);
    }

    @Test
    void mergeInt_ShouldMergeArrays_WhenBothArraysAreNotEmpty() {
        // Given
        int[] inputArray1 = new int[]{1, 2};
        int[] inputArray2 = new int[]{3, 4};

        // When
        int[] actual = ArrayUtils.merge(inputArray1, inputArray2);

        // Then
        then(actual)
                .isNotSameAs(inputArray1)
                .isNotSameAs(inputArray2)
                .hasSize(4)
                .containsExactly(1, 2, 3, 4);
    }

    @Test
    void mergeLong_ShouldReturnEmptyArray_WhenBothArraysAreNull() {
        // Given
        long[] inputArray1 = null;
        long[] inputArray2 = null;

        // When
        long[] actual = ArrayUtils.merge(inputArray1, inputArray2);

        // Then
        then(actual)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void mergeLong_ShouldReturnEmptyArray_WhenBothArraysAreEmpty() {
        // Given
        long[] inputArray1 = new long[0];
        long[] inputArray2 = new long[0];

        // When
        long[] actual = ArrayUtils.merge(inputArray1, inputArray2);

        // Then
        then(actual)
                .isNotSameAs(inputArray1)
                .isNotSameAs(inputArray2)
                .isEmpty();
    }

    @Test
    void mergeLong_ShouldReturnCopyOfArray2_WhenArray1IsNull() {
        // Given
        long[] inputArray1 = null;
        long[] inputArray2 = new long[]{1L, 2L};

        // When
        long[] actual = ArrayUtils.merge(inputArray1, inputArray2);

        // Then
        then(actual)
                .isNotSameAs(inputArray2)
                .hasSize(2)
                .containsExactly(1L, 2L);
    }

    @Test
    void mergeLong_ShouldReturnCopyOfArray2_WhenArray1IsEmpty() {
        // Given
        long[] inputArray1 = new long[0];
        long[] inputArray2 = new long[]{1L, 2L};

        // When
        long[] actual = ArrayUtils.merge(inputArray1, inputArray2);

        // Then
        then(actual)
                .isNotSameAs(inputArray2)
                .hasSize(2)
                .containsExactly(1L, 2L);
    }

    @Test
    void mergeLong_ShouldReturnCopyOfArray1_WhenArray2IsNull() {
        // Given
        long[] inputArray1 = new long[]{1L, 2L};
        long[] inputArray2 = null;

        // When
        long[] actual = ArrayUtils.merge(inputArray1, inputArray2);

        // Then
        then(actual)
                .isNotSameAs(inputArray1)
                .hasSize(2)
                .containsExactly(1L, 2L);
    }

    @Test
    void mergeLong_ShouldReturnCopyOfArray1_WhenArray2IsEmpty() {
        // Given
        long[] inputArray1 = new long[]{1L, 2L};
        long[] inputArray2 = new long[0];

        // When
        long[] actual = ArrayUtils.merge(inputArray1, inputArray2);

        // Then
        then(actual)
                .isNotSameAs(inputArray1)
                .hasSize(2)
                .containsExactly(1L, 2L);
    }

    @Test
    void mergeLong_ShouldMergeArrays_WhenBothArraysAreNotEmpty() {
        // Given
        long[] inputArray1 = new long[]{1L, 2L};
        long[] inputArray2 = new long[]{3L, 4L};

        // When
        long[] actual = ArrayUtils.merge(inputArray1, inputArray2);

        // Then
        then(actual)
                .isNotSameAs(inputArray1)
                .isNotSameAs(inputArray2)
                .hasSize(4)
                .containsExactly(1L, 2L, 3L, 4L);
    }

    @Test
    void mergeFloat_ShouldReturnEmptyArray_WhenBothArraysAreNull() {
        // Given
        float[] inputArray1 = null;
        float[] inputArray2 = null;

        // When
        float[] actual = ArrayUtils.merge(inputArray1, inputArray2);

        // Then
        then(actual)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void mergeFloat_ShouldReturnEmptyArray_WhenBothArraysAreEmpty() {
        // Given
        float[] inputArray1 = new float[0];
        float[] inputArray2 = new float[0];

        // When
        float[] actual = ArrayUtils.merge(inputArray1, inputArray2);

        // Then
        then(actual)
                .isNotSameAs(inputArray1)
                .isNotSameAs(inputArray2)
                .isEmpty();
    }

    @Test
    void mergeFloat_ShouldReturnCopyOfArray2_WhenArray1IsNull() {
        // Given
        float[] inputArray1 = null;
        float[] inputArray2 = new float[]{1.0F, 2.0F};

        // When
        float[] actual = ArrayUtils.merge(inputArray1, inputArray2);

        // Then
        then(actual)
                .isNotSameAs(inputArray2)
                .hasSize(2)
                .containsExactly(1.0F, 2.0F);
    }

    @Test
    void mergeFloat_ShouldReturnCopyOfArray2_WhenArray1IsEmpty() {
        // Given
        float[] inputArray1 = new float[0];
        float[] inputArray2 = new float[]{1.0F, 2.0F};

        // When
        float[] actual = ArrayUtils.merge(inputArray1, inputArray2);

        // Then
        then(actual)
                .isNotSameAs(inputArray2)
                .hasSize(2)
                .containsExactly(1.0F, 2.0F);
    }

    @Test
    void mergeFloat_ShouldReturnCopyOfArray1_WhenArray2IsNull() {
        // Given
        float[] inputArray1 = new float[]{1.0F, 2.0F};
        float[] inputArray2 = null;

        // When
        float[] actual = ArrayUtils.merge(inputArray1, inputArray2);

        // Then
        then(actual)
                .isNotSameAs(inputArray1)
                .hasSize(2)
                .containsExactly(1.0F, 2.0F);
    }

    @Test
    void mergeFloat_ShouldReturnCopyOfArray1_WhenArray2IsEmpty() {
        // Given
        float[] inputArray1 = new float[]{1.0F, 2.0F};
        float[] inputArray2 = new float[0];

        // When
        float[] actual = ArrayUtils.merge(inputArray1, inputArray2);

        // Then
        then(actual)
                .isNotSameAs(inputArray1)
                .hasSize(2)
                .containsExactly(1.0F, 2.0F);
    }

    @Test
    void mergeFloat_ShouldMergeArrays_WhenBothArraysAreNotEmpty() {
        // Given
        float[] inputArray1 = new float[]{1.0F, 2.0F};
        float[] inputArray2 = new float[]{3.0F, 4.0F};

        // When
        float[] actual = ArrayUtils.merge(inputArray1, inputArray2);

        // Then
        then(actual)
                .isNotSameAs(inputArray1)
                .isNotSameAs(inputArray2)
                .hasSize(4)
                .containsExactly(1.0F, 2.0F, 3.0F, 4.0F);
    }

    @Test
    void mergeDouble_ShouldReturnEmptyArray_WhenBothArraysAreNull() {
        // Given
        double[] inputArray1 = null;
        double[] inputArray2 = null;

        // When
        double[] actual = ArrayUtils.merge(inputArray1, inputArray2);

        // Then
        then(actual)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void mergeDouble_ShouldReturnEmptyArray_WhenBothArraysAreEmpty() {
        // Given
        double[] inputArray1 = new double[0];
        double[] inputArray2 = new double[0];

        // When
        double[] actual = ArrayUtils.merge(inputArray1, inputArray2);

        // Then
        then(actual)
                .isNotSameAs(inputArray1)
                .isNotSameAs(inputArray2)
                .isEmpty();
    }

    @Test
    void mergeDouble_ShouldReturnCopyOfArray2_WhenArray1IsNull() {
        // Given
        double[] inputArray1 = null;
        double[] inputArray2 = new double[]{1.0, 2.0};

        // When
        double[] actual = ArrayUtils.merge(inputArray1, inputArray2);

        // Then
        then(actual)
                .isNotSameAs(inputArray2)
                .hasSize(2)
                .containsExactly(1.0, 2.0);
    }

    @Test
    void mergeDouble_ShouldReturnCopyOfArray2_WhenArray1IsEmpty() {
        // Given
        double[] inputArray1 = new double[0];
        double[] inputArray2 = new double[]{1.0, 2.0};

        // When
        double[] actual = ArrayUtils.merge(inputArray1, inputArray2);

        // Then
        then(actual)
                .isNotSameAs(inputArray2)
                .hasSize(2)
                .containsExactly(1.0, 2.0);
    }

    @Test
    void mergeDouble_ShouldReturnCopyOfArray1_WhenArray2IsNull() {
        // Given
        double[] inputArray1 = new double[]{1.0, 2.0};
        double[] inputArray2 = null;

        // When
        double[] actual = ArrayUtils.merge(inputArray1, inputArray2);

        // Then
        then(actual)
                .isNotSameAs(inputArray1)
                .hasSize(2)
                .containsExactly(1.0, 2.0);
    }

    @Test
    void mergeDouble_ShouldReturnCopyOfArray1_WhenArray2IsEmpty() {
        // Given
        double[] inputArray1 = new double[]{1.0, 2.0};
        double[] inputArray2 = new double[0];

        // When
        double[] actual = ArrayUtils.merge(inputArray1, inputArray2);

        // Then
        then(actual)
                .isNotSameAs(inputArray1)
                .hasSize(2)
                .containsExactly(1.0, 2.0);
    }

    @Test
    void mergeDouble_ShouldMergeArrays_WhenBothArraysAreNotEmpty() {
        // Given
        double[] inputArray1 = new double[]{1.0, 2.0};
        double[] inputArray2 = new double[]{3.0, 4.0};

        // When
        double[] actual = ArrayUtils.merge(inputArray1, inputArray2);

        // Then
        then(actual)
                .isNotSameAs(inputArray1)
                .isNotSameAs(inputArray2)
                .hasSize(4)
                .containsExactly(1.0, 2.0, 3.0, 4.0);
    }

    @Test
    void mergeGeneric_ShouldReturnNull_WhenBothArraysAreNull() {
        // Given
        String[] inputArray1 = null;
        String[] inputArray2 = null;

        // When
        String[] actual = ArrayUtils.merge(inputArray1, inputArray2);

        // Then
        then(actual).isNull();
    }

    @Test
    void mergeGeneric_ShouldReturnEmptyArray_WhenBothArraysAreEmpty() {
        // Given
        String[] inputArray1 = new String[0];
        String[] inputArray2 = new String[0];

        // When
        String[] actual = ArrayUtils.merge(inputArray1, inputArray2);

        // Then
        then(actual)
                .isNotSameAs(inputArray1)
                .isNotSameAs(inputArray2)
                .isEmpty();
    }

    @Test
    void mergeGeneric_ShouldReturnCopyOfArray2_WhenArray1IsNull() {
        // Given
        String[] inputArray1 = null;
        String[] inputArray2 = new String[]{"a", "b"};

        // When
        String[] actual = ArrayUtils.merge(inputArray1, inputArray2);

        // Then
        then(actual)
                .isNotSameAs(inputArray2)
                .hasSize(2)
                .containsExactly("a", "b");
    }

    @Test
    void mergeGeneric_ShouldReturnCopyOfArray2_WhenArray1IsEmpty() {
        // Given
        String[] inputArray1 = new String[0];
        String[] inputArray2 = new String[]{"a", "b"};

        // When
        String[] actual = ArrayUtils.merge(inputArray1, inputArray2);

        // Then
        then(actual)
                .isNotSameAs(inputArray2)
                .hasSize(2)
                .containsExactly("a", "b");
    }

    @Test
    void mergeGeneric_ShouldReturnCopyOfArray1_WhenArray2IsNull() {
        // Given
        String[] inputArray1 = new String[]{"a", "b"};
        String[] inputArray2 = null;

        // When
        String[] actual = ArrayUtils.merge(inputArray1, inputArray2);

        // Then
        then(actual)
                .isNotSameAs(inputArray1)
                .hasSize(2)
                .containsExactly("a", "b");
    }

    @Test
    void mergeGeneric_ShouldReturnCopyOfArray1_WhenArray2IsEmpty() {
        // Given
        String[] inputArray1 = new String[]{"a", "b"};
        String[] inputArray2 = new String[0];

        // When
        String[] actual = ArrayUtils.merge(inputArray1, inputArray2);

        // Then
        then(actual)
                .isNotSameAs(inputArray1)
                .hasSize(2)
                .containsExactly("a", "b");
    }

    @Test
    void mergeGeneric_ShouldMergeArrays_WhenBothArraysAreNotEmpty() {
        // Given
        String[] inputArray1 = new String[]{"a", "b"};
        String[] inputArray2 = new String[]{"c", "d"};

        // When
        String[] actual = ArrayUtils.merge(inputArray1, inputArray2);

        // Then
        then(actual)
                .isNotSameAs(inputArray1)
                .isNotSameAs(inputArray2)
                .hasSize(4)
                .containsExactly("a", "b", "c", "d");
    }

    @Test
    void removeBoolean_ShouldThrowIllegalArgumentException_WhenArrayIsNull() {
        // Given
        boolean[] inputSource = null;
        int inputIndex = 0;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.remove(inputSource, inputIndex))
                .isInstanceOf(IllegalArgumentException.class)
                .withMessage("The input array cannot be null");
    }

    @Test
    void removeBoolean_ShouldThrowIndexOutOfBoundsException_WhenIndexIsNegative() {
        // Given
        boolean[] inputSource = new boolean[]{true, false};
        int inputIndex = -1;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.remove(inputSource, inputIndex))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .withMessage("Index -1 out of bounds for length 2");
    }

    @Test
    void removeBoolean_ShouldThrowIndexOutOfBoundsException_WhenIndexIsOutOfBounds() {
        // Given
        boolean[] inputSource = new boolean[]{true, false};
        int inputIndex = 2;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.remove(inputSource, inputIndex))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .withMessage("Index 2 out of bounds for length 2");
    }

    @Test
    void removeBoolean_ShouldReturnEmptyArray_WhenRemovingFromSingleElementArray() {
        // Given
        boolean[] inputSource = new boolean[]{true};
        int inputIndex = 0;

        // When
        boolean[] actual = ArrayUtils.remove(inputSource, inputIndex);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .isEmpty();
    }

    @ParameterizedTest(name = "{2}")
    @MethodSource("removeBooleanTestCases")
    void removeBoolean_ShouldRemoveElement_WhenIndexIsProvided(int inputIndex, boolean[] expected, String description) {
        // Given
        boolean[] inputSource = new boolean[]{true, false, true};

        // When
        boolean[] actual = ArrayUtils.remove(inputSource, inputIndex);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .hasSize(2)
                .containsExactly(expected);
    }

    private static Stream<Arguments> removeBooleanTestCases() {
        return Stream.of(
                Arguments.of(0, new boolean[]{false, true}, "removing first element"),
                Arguments.of(2, new boolean[]{true, false}, "removing last element"),
                Arguments.of(1, new boolean[]{true, true}, "removing middle element")
        );
    }

    @Test
    void removeByte_ShouldThrowIllegalArgumentException_WhenArrayIsNull() {
        // Given
        byte[] inputSource = null;
        int inputIndex = 0;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.remove(inputSource, inputIndex))
                .isInstanceOf(IllegalArgumentException.class)
                .withMessage("The input array cannot be null");
    }

    @Test
    void removeByte_ShouldThrowIndexOutOfBoundsException_WhenIndexIsNegative() {
        // Given
        byte[] inputSource = new byte[]{1, 2};
        int inputIndex = -1;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.remove(inputSource, inputIndex))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .withMessage("Index -1 out of bounds for length 2");
    }

    @Test
    void removeByte_ShouldThrowIndexOutOfBoundsException_WhenIndexIsOutOfBounds() {
        // Given
        byte[] inputSource = new byte[]{1, 2};
        int inputIndex = 2;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.remove(inputSource, inputIndex))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .withMessage("Index 2 out of bounds for length 2");
    }

    @Test
    void removeByte_ShouldReturnEmptyArray_WhenRemovingFromSingleElementArray() {
        // Given
        byte[] inputSource = new byte[]{1};
        int inputIndex = 0;

        // When
        byte[] actual = ArrayUtils.remove(inputSource, inputIndex);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .isEmpty();
    }

    @ParameterizedTest(name = "{2}")
    @MethodSource("removeByteTestCases")
    void removeByte_ShouldRemoveElement_WhenIndexIsProvided(int inputIndex, byte[] expected, String description) {
        // Given
        byte[] inputSource = new byte[]{1, 2, 3};

        // When
        byte[] actual = ArrayUtils.remove(inputSource, inputIndex);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .hasSize(2)
                .containsExactly(expected);
    }

    private static Stream<Arguments> removeByteTestCases() {
        return Stream.of(
                Arguments.of(0, new byte[]{2, 3}, "removing first element"),
                Arguments.of(2, new byte[]{1, 2}, "removing last element"),
                Arguments.of(1, new byte[]{1, 3}, "removing middle element")
        );
    }

    @Test
    void removeChar_ShouldThrowIllegalArgumentException_WhenArrayIsNull() {
        // Given
        char[] inputSource = null;
        int inputIndex = 0;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.remove(inputSource, inputIndex))
                .isInstanceOf(IllegalArgumentException.class)
                .withMessage("The input array cannot be null");
    }

    @Test
    void removeChar_ShouldThrowIndexOutOfBoundsException_WhenIndexIsNegative() {
        // Given
        char[] inputSource = new char[]{'a', 'b'};
        int inputIndex = -1;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.remove(inputSource, inputIndex))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .withMessage("Index -1 out of bounds for length 2");
    }

    @Test
    void removeChar_ShouldThrowIndexOutOfBoundsException_WhenIndexIsOutOfBounds() {
        // Given
        char[] inputSource = new char[]{'a', 'b'};
        int inputIndex = 2;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.remove(inputSource, inputIndex))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .withMessage("Index 2 out of bounds for length 2");
    }

    @Test
    void removeChar_ShouldReturnEmptyArray_WhenRemovingFromSingleElementArray() {
        // Given
        char[] inputSource = new char[]{'a'};
        int inputIndex = 0;

        // When
        char[] actual = ArrayUtils.remove(inputSource, inputIndex);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .isEmpty();
    }

    @ParameterizedTest(name = "{2}")
    @MethodSource("removeCharTestCases")
    void removeChar_ShouldRemoveElement_WhenIndexIsProvided(int inputIndex, char[] expected, String description) {
        // Given
        char[] inputSource = new char[]{'a', 'b', 'c'};

        // When
        char[] actual = ArrayUtils.remove(inputSource, inputIndex);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .hasSize(2)
                .containsExactly(expected);
    }

    private static Stream<Arguments> removeCharTestCases() {
        return Stream.of(
                Arguments.of(0, new char[]{'b', 'c'}, "removing first element"),
                Arguments.of(2, new char[]{'a', 'b'}, "removing last element"),
                Arguments.of(1, new char[]{'a', 'c'}, "removing middle element")
        );
    }

    @Test
    void removeShort_ShouldThrowIllegalArgumentException_WhenArrayIsNull() {
        // Given
        short[] inputSource = null;
        int inputIndex = 0;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.remove(inputSource, inputIndex))
                .isInstanceOf(IllegalArgumentException.class)
                .withMessage("The input array cannot be null");
    }

    @Test
    void removeShort_ShouldThrowIndexOutOfBoundsException_WhenIndexIsNegative() {
        // Given
        short[] inputSource = new short[]{1, 2};
        int inputIndex = -1;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.remove(inputSource, inputIndex))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .withMessage("Index -1 out of bounds for length 2");
    }

    @Test
    void removeShort_ShouldThrowIndexOutOfBoundsException_WhenIndexIsOutOfBounds() {
        // Given
        short[] inputSource = new short[]{1, 2};
        int inputIndex = 2;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.remove(inputSource, inputIndex))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .withMessage("Index 2 out of bounds for length 2");
    }

    @Test
    void removeShort_ShouldReturnEmptyArray_WhenRemovingFromSingleElementArray() {
        // Given
        short[] inputSource = new short[]{1};
        int inputIndex = 0;

        // When
        short[] actual = ArrayUtils.remove(inputSource, inputIndex);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .isEmpty();
    }

    @ParameterizedTest(name = "{2}")
    @MethodSource("removeShortTestCases")
    void removeShort_ShouldRemoveElement_WhenIndexIsProvided(int inputIndex, short[] expected, String description) {
        // Given
        short[] inputSource = new short[]{1, 2, 3};

        // When
        short[] actual = ArrayUtils.remove(inputSource, inputIndex);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .hasSize(2)
                .containsExactly(expected);
    }

    private static Stream<Arguments> removeShortTestCases() {
        return Stream.of(
                Arguments.of(0, new short[]{2, 3}, "removing first element"),
                Arguments.of(2, new short[]{1, 2}, "removing last element"),
                Arguments.of(1, new short[]{1, 3}, "removing middle element")
        );
    }

    @Test
    void removeInt_ShouldThrowIllegalArgumentException_WhenArrayIsNull() {
        // Given
        int[] inputSource = null;
        int inputIndex = 0;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.remove(inputSource, inputIndex))
                .isInstanceOf(IllegalArgumentException.class)
                .withMessage("The input array cannot be null");
    }

    @Test
    void removeInt_ShouldThrowIndexOutOfBoundsException_WhenIndexIsNegative() {
        // Given
        int[] inputSource = new int[]{1, 2};
        int inputIndex = -1;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.remove(inputSource, inputIndex))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .withMessage("Index -1 out of bounds for length 2");
    }

    @Test
    void removeInt_ShouldThrowIndexOutOfBoundsException_WhenIndexIsOutOfBounds() {
        // Given
        int[] inputSource = new int[]{1, 2};
        int inputIndex = 2;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.remove(inputSource, inputIndex))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .withMessage("Index 2 out of bounds for length 2");
    }

    @Test
    void removeInt_ShouldReturnEmptyArray_WhenRemovingFromSingleElementArray() {
        // Given
        int[] inputSource = new int[]{1};
        int inputIndex = 0;

        // When
        int[] actual = ArrayUtils.remove(inputSource, inputIndex);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .isEmpty();
    }

    @ParameterizedTest(name = "{2}")
    @MethodSource("removeIntTestCases")
    void removeInt_ShouldRemoveElement_WhenIndexIsProvided(int inputIndex, int[] expected, String description) {
        // Given
        int[] inputSource = new int[]{1, 2, 3};

        // When
        int[] actual = ArrayUtils.remove(inputSource, inputIndex);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .hasSize(2)
                .containsExactly(expected);
    }

    private static Stream<Arguments> removeIntTestCases() {
        return Stream.of(
                Arguments.of(0, new int[]{2, 3}, "removing first element"),
                Arguments.of(2, new int[]{1, 2}, "removing last element"),
                Arguments.of(1, new int[]{1, 3}, "removing middle element")
        );
    }

    @Test
    void removeLong_ShouldThrowIllegalArgumentException_WhenArrayIsNull() {
        // Given
        long[] inputSource = null;
        int inputIndex = 0;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.remove(inputSource, inputIndex))
                .isInstanceOf(IllegalArgumentException.class)
                .withMessage("The input array cannot be null");
    }

    @Test
    void removeLong_ShouldThrowIndexOutOfBoundsException_WhenIndexIsNegative() {
        // Given
        long[] inputSource = new long[]{1L, 2L};
        int inputIndex = -1;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.remove(inputSource, inputIndex))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .withMessage("Index -1 out of bounds for length 2");
    }

    @Test
    void removeLong_ShouldThrowIndexOutOfBoundsException_WhenIndexIsOutOfBounds() {
        // Given
        long[] inputSource = new long[]{1L, 2L};
        int inputIndex = 2;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.remove(inputSource, inputIndex))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .withMessage("Index 2 out of bounds for length 2");
    }

    @Test
    void removeLong_ShouldReturnEmptyArray_WhenRemovingFromSingleElementArray() {
        // Given
        long[] inputSource = new long[]{1L};
        int inputIndex = 0;

        // When
        long[] actual = ArrayUtils.remove(inputSource, inputIndex);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .isEmpty();
    }

    @ParameterizedTest(name = "{2}")
    @MethodSource("removeLongTestCases")
    void removeLong_ShouldRemoveElement_WhenIndexIsProvided(int inputIndex, long[] expected, String description) {
        // Given
        long[] inputSource = new long[]{1L, 2L, 3L};

        // When
        long[] actual = ArrayUtils.remove(inputSource, inputIndex);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .hasSize(2)
                .containsExactly(expected);
    }

    private static Stream<Arguments> removeLongTestCases() {
        return Stream.of(
                Arguments.of(0, new long[]{2L, 3L}, "removing first element"),
                Arguments.of(2, new long[]{1L, 2L}, "removing last element"),
                Arguments.of(1, new long[]{1L, 3L}, "removing middle element")
        );
    }

    @Test
    void removeFloat_ShouldThrowIllegalArgumentException_WhenArrayIsNull() {
        // Given
        float[] inputSource = null;
        int inputIndex = 0;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.remove(inputSource, inputIndex))
                .isInstanceOf(IllegalArgumentException.class)
                .withMessage("The input array cannot be null");
    }

    @Test
    void removeFloat_ShouldThrowIndexOutOfBoundsException_WhenIndexIsNegative() {
        // Given
        float[] inputSource = new float[]{1.0F, 2.0F};
        int inputIndex = -1;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.remove(inputSource, inputIndex))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .withMessage("Index -1 out of bounds for length 2");
    }

    @Test
    void removeFloat_ShouldThrowIndexOutOfBoundsException_WhenIndexIsOutOfBounds() {
        // Given
        float[] inputSource = new float[]{1.0F, 2.0F};
        int inputIndex = 2;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.remove(inputSource, inputIndex))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .withMessage("Index 2 out of bounds for length 2");
    }

    @Test
    void removeFloat_ShouldReturnEmptyArray_WhenRemovingFromSingleElementArray() {
        // Given
        float[] inputSource = new float[]{1.0F};
        int inputIndex = 0;

        // When
        float[] actual = ArrayUtils.remove(inputSource, inputIndex);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .isEmpty();
    }

    @ParameterizedTest(name = "{2}")
    @MethodSource("removeFloatTestCases")
    void removeFloat_ShouldRemoveElement_WhenIndexIsProvided(int inputIndex, float[] expected, String description) {
        // Given
        float[] inputSource = new float[]{1.0F, 2.0F, 3.0F};

        // When
        float[] actual = ArrayUtils.remove(inputSource, inputIndex);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .hasSize(2)
                .containsExactly(expected);
    }

    private static Stream<Arguments> removeFloatTestCases() {
        return Stream.of(
                Arguments.of(0, new float[]{2.0F, 3.0F}, "removing first element"),
                Arguments.of(2, new float[]{1.0F, 2.0F}, "removing last element"),
                Arguments.of(1, new float[]{1.0F, 3.0F}, "removing middle element")
        );
    }

    @Test
    void removeDouble_ShouldThrowIllegalArgumentException_WhenArrayIsNull() {
        // Given
        double[] inputSource = null;
        int inputIndex = 0;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.remove(inputSource, inputIndex))
                .isInstanceOf(IllegalArgumentException.class)
                .withMessage("The input array cannot be null");
    }

    @Test
    void removeDouble_ShouldThrowIndexOutOfBoundsException_WhenIndexIsNegative() {
        // Given
        double[] inputSource = new double[]{1.0, 2.0};
        int inputIndex = -1;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.remove(inputSource, inputIndex))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .withMessage("Index -1 out of bounds for length 2");
    }

    @Test
    void removeDouble_ShouldThrowIndexOutOfBoundsException_WhenIndexIsOutOfBounds() {
        // Given
        double[] inputSource = new double[]{1.0, 2.0};
        int inputIndex = 2;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.remove(inputSource, inputIndex))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .withMessage("Index 2 out of bounds for length 2");
    }

    @Test
    void removeDouble_ShouldReturnEmptyArray_WhenRemovingFromSingleElementArray() {
        // Given
        double[] inputSource = new double[]{1.0};
        int inputIndex = 0;

        // When
        double[] actual = ArrayUtils.remove(inputSource, inputIndex);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .isEmpty();
    }

    @ParameterizedTest(name = "{2}")
    @MethodSource("removeDoubleTestCases")
    void removeDouble_ShouldRemoveElement_WhenIndexIsProvided(int inputIndex, double[] expected, String description) {
        // Given
        double[] inputSource = new double[]{1.0, 2.0, 3.0};

        // When
        double[] actual = ArrayUtils.remove(inputSource, inputIndex);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .hasSize(2)
                .containsExactly(expected);
    }

    private static Stream<Arguments> removeDoubleTestCases() {
        return Stream.of(
                Arguments.of(0, new double[]{2.0, 3.0}, "removing first element"),
                Arguments.of(2, new double[]{1.0, 2.0}, "removing last element"),
                Arguments.of(1, new double[]{1.0, 3.0}, "removing middle element")
        );
    }

    @Test
    void removeGeneric_ShouldThrowIllegalArgumentException_WhenArrayIsNull() {
        // Given
        String[] inputSource = null;
        int inputIndex = 0;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.remove(inputSource, inputIndex))
                .isInstanceOf(IllegalArgumentException.class)
                .withMessage("The input array cannot be null");
    }

    @Test
    void removeGeneric_ShouldThrowIndexOutOfBoundsException_WhenIndexIsNegative() {
        // Given
        String[] inputSource = new String[]{"a", "b"};
        int inputIndex = -1;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.remove(inputSource, inputIndex))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .withMessage("Index -1 out of bounds for length 2");
    }

    @Test
    void removeGeneric_ShouldThrowIndexOutOfBoundsException_WhenIndexIsOutOfBounds() {
        // Given
        String[] inputSource = new String[]{"a", "b"};
        int inputIndex = 2;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.remove(inputSource, inputIndex))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .withMessage("Index 2 out of bounds for length 2");
    }

    @Test
    void removeGeneric_ShouldReturnEmptyArray_WhenRemovingFromSingleElementArray() {
        // Given
        String[] inputSource = new String[]{"a"};
        int inputIndex = 0;

        // When
        String[] actual = ArrayUtils.remove(inputSource, inputIndex);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .isEmpty();
    }

    @ParameterizedTest(name = "{2}")
    @MethodSource("removeGenericTestCases")
    void removeGeneric_ShouldRemoveElement_WhenIndexIsProvided(int inputIndex, String[] expected, String description) {
        // Given
        String[] inputSource = new String[]{"a", "b", "c"};

        // When
        String[] actual = ArrayUtils.remove(inputSource, inputIndex);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .hasSize(2)
                .containsExactly(expected);
    }

    private static Stream<Arguments> removeGenericTestCases() {
        return Stream.of(
                Arguments.of(0, new String[]{"b", "c"}, "removing first element"),
                Arguments.of(2, new String[]{"a", "b"}, "removing last element"),
                Arguments.of(1, new String[]{"a", "c"}, "removing middle element")
        );
    }

    @Test
    void removeFirstBoolean_ShouldThrowIllegalArgumentException_WhenArrayIsNull() {
        // Given
        boolean[] inputSource = null;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.removeFirst(inputSource))
                .isInstanceOf(IllegalArgumentException.class)
                .withMessage("The input array cannot be null");
    }

    @Test
    void removeFirstBoolean_ShouldThrowIndexOutOfBoundsException_WhenArrayIsEmpty() {
        // Given
        boolean[] inputSource = new boolean[]{};

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.removeFirst(inputSource))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .withMessage("Index 0 out of bounds for length 0");
    }

    @Test
    void removeFirstBoolean_ShouldReturnEmptyArray_WhenArrayHasSingleElement() {
        // Given
        boolean[] inputSource = new boolean[]{true};

        // When
        boolean[] actual = ArrayUtils.removeFirst(inputSource);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .isEmpty();
    }

    @Test
    void removeFirstBoolean_ShouldRemoveFirstElement_WhenArrayHasMultipleElements() {
        // Given
        boolean[] inputSource = new boolean[]{true, false, true};

        // When
        boolean[] actual = ArrayUtils.removeFirst(inputSource);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .hasSize(2)
                .containsExactly(false, true);
    }

    @Test
    void removeFirstByte_ShouldThrowIllegalArgumentException_WhenArrayIsNull() {
        // Given
        byte[] inputSource = null;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.removeFirst(inputSource))
                .isInstanceOf(IllegalArgumentException.class)
                .withMessage("The input array cannot be null");
    }

    @Test
    void removeFirstByte_ShouldThrowIndexOutOfBoundsException_WhenArrayIsEmpty() {
        // Given
        byte[] inputSource = new byte[]{};

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.removeFirst(inputSource))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .withMessage("Index 0 out of bounds for length 0");
    }

    @Test
    void removeFirstByte_ShouldReturnEmptyArray_WhenArrayHasSingleElement() {
        // Given
        byte[] inputSource = new byte[]{1};

        // When
        byte[] actual = ArrayUtils.removeFirst(inputSource);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .isEmpty();
    }

    @Test
    void removeFirstByte_ShouldRemoveFirstElement_WhenArrayHasMultipleElements() {
        // Given
        byte[] inputSource = new byte[]{1, 2, 3};

        // When
        byte[] actual = ArrayUtils.removeFirst(inputSource);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .hasSize(2)
                .containsExactly(2, 3);
    }

    @Test
    void removeFirstChar_ShouldThrowIllegalArgumentException_WhenArrayIsNull() {
        // Given
        char[] inputSource = null;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.removeFirst(inputSource))
                .isInstanceOf(IllegalArgumentException.class)
                .withMessage("The input array cannot be null");
    }

    @Test
    void removeFirstChar_ShouldThrowIndexOutOfBoundsException_WhenArrayIsEmpty() {
        // Given
        char[] inputSource = new char[]{};

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.removeFirst(inputSource))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .withMessage("Index 0 out of bounds for length 0");
    }

    @Test
    void removeFirstChar_ShouldReturnEmptyArray_WhenArrayHasSingleElement() {
        // Given
        char[] inputSource = new char[]{'a'};

        // When
        char[] actual = ArrayUtils.removeFirst(inputSource);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .isEmpty();
    }

    @Test
    void removeFirstChar_ShouldRemoveFirstElement_WhenArrayHasMultipleElements() {
        // Given
        char[] inputSource = new char[]{'a', 'b', 'c'};

        // When
        char[] actual = ArrayUtils.removeFirst(inputSource);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .hasSize(2)
                .containsExactly('b', 'c');
    }

    @Test
    void removeFirstShort_ShouldThrowIllegalArgumentException_WhenArrayIsNull() {
        // Given
        short[] inputSource = null;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.removeFirst(inputSource))
                .isInstanceOf(IllegalArgumentException.class)
                .withMessage("The input array cannot be null");
    }

    @Test
    void removeFirstShort_ShouldThrowIndexOutOfBoundsException_WhenArrayIsEmpty() {
        // Given
        short[] inputSource = new short[]{};

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.removeFirst(inputSource))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .withMessage("Index 0 out of bounds for length 0");
    }

    @Test
    void removeFirstShort_ShouldReturnEmptyArray_WhenArrayHasSingleElement() {
        // Given
        short[] inputSource = new short[]{1};

        // When
        short[] actual = ArrayUtils.removeFirst(inputSource);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .isEmpty();
    }

    @Test
    void removeFirstShort_ShouldRemoveFirstElement_WhenArrayHasMultipleElements() {
        // Given
        short[] inputSource = new short[]{1, 2, 3};

        // When
        short[] actual = ArrayUtils.removeFirst(inputSource);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .hasSize(2)
                .containsExactly(2, 3);
    }

    @Test
    void removeFirstInt_ShouldThrowIllegalArgumentException_WhenArrayIsNull() {
        // Given
        int[] inputSource = null;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.removeFirst(inputSource))
                .isInstanceOf(IllegalArgumentException.class)
                .withMessage("The input array cannot be null");
    }

    @Test
    void removeFirstInt_ShouldThrowIndexOutOfBoundsException_WhenArrayIsEmpty() {
        // Given
        int[] inputSource = new int[]{};

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.removeFirst(inputSource))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .withMessage("Index 0 out of bounds for length 0");
    }

    @Test
    void removeFirstInt_ShouldReturnEmptyArray_WhenArrayHasSingleElement() {
        // Given
        int[] inputSource = new int[]{1};

        // When
        int[] actual = ArrayUtils.removeFirst(inputSource);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .isEmpty();
    }

    @Test
    void removeFirstInt_ShouldRemoveFirstElement_WhenArrayHasMultipleElements() {
        // Given
        int[] inputSource = new int[]{1, 2, 3};

        // When
        int[] actual = ArrayUtils.removeFirst(inputSource);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .hasSize(2)
                .containsExactly(2, 3);
    }

    @Test
    void removeFirstLong_ShouldThrowIllegalArgumentException_WhenArrayIsNull() {
        // Given
        long[] inputSource = null;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.removeFirst(inputSource))
                .isInstanceOf(IllegalArgumentException.class)
                .withMessage("The input array cannot be null");
    }

    @Test
    void removeFirstLong_ShouldThrowIndexOutOfBoundsException_WhenArrayIsEmpty() {
        // Given
        long[] inputSource = new long[]{};

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.removeFirst(inputSource))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .withMessage("Index 0 out of bounds for length 0");
    }

    @Test
    void removeFirstLong_ShouldReturnEmptyArray_WhenArrayHasSingleElement() {
        // Given
        long[] inputSource = new long[]{1L};

        // When
        long[] actual = ArrayUtils.removeFirst(inputSource);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .isEmpty();
    }

    @Test
    void removeFirstLong_ShouldRemoveFirstElement_WhenArrayHasMultipleElements() {
        // Given
        long[] inputSource = new long[]{1L, 2L, 3L};

        // When
        long[] actual = ArrayUtils.removeFirst(inputSource);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .hasSize(2)
                .containsExactly(2L, 3L);
    }

    @Test
    void removeFirstFloat_ShouldThrowIllegalArgumentException_WhenArrayIsNull() {
        // Given
        float[] inputSource = null;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.removeFirst(inputSource))
                .isInstanceOf(IllegalArgumentException.class)
                .withMessage("The input array cannot be null");
    }

    @Test
    void removeFirstFloat_ShouldThrowIndexOutOfBoundsException_WhenArrayIsEmpty() {
        // Given
        float[] inputSource = new float[]{};

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.removeFirst(inputSource))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .withMessage("Index 0 out of bounds for length 0");
    }

    @Test
    void removeFirstFloat_ShouldReturnEmptyArray_WhenArrayHasSingleElement() {
        // Given
        float[] inputSource = new float[]{1.0F};

        // When
        float[] actual = ArrayUtils.removeFirst(inputSource);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .isEmpty();
    }

    @Test
    void removeFirstFloat_ShouldRemoveFirstElement_WhenArrayHasMultipleElements() {
        // Given
        float[] inputSource = new float[]{1.0F, 2.0F, 3.0F};

        // When
        float[] actual = ArrayUtils.removeFirst(inputSource);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .hasSize(2)
                .containsExactly(2.0F, 3.0F);
    }

    @Test
    void removeFirstDouble_ShouldThrowIllegalArgumentException_WhenArrayIsNull() {
        // Given
        double[] inputSource = null;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.removeFirst(inputSource))
                .isInstanceOf(IllegalArgumentException.class)
                .withMessage("The input array cannot be null");
    }

    @Test
    void removeFirstDouble_ShouldThrowIndexOutOfBoundsException_WhenArrayIsEmpty() {
        // Given
        double[] inputSource = new double[]{};

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.removeFirst(inputSource))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .withMessage("Index 0 out of bounds for length 0");
    }

    @Test
    void removeFirstDouble_ShouldReturnEmptyArray_WhenArrayHasSingleElement() {
        // Given
        double[] inputSource = new double[]{1.0};

        // When
        double[] actual = ArrayUtils.removeFirst(inputSource);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .isEmpty();
    }

    @Test
    void removeFirstDouble_ShouldRemoveFirstElement_WhenArrayHasMultipleElements() {
        // Given
        double[] inputSource = new double[]{1.0, 2.0, 3.0};

        // When
        double[] actual = ArrayUtils.removeFirst(inputSource);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .hasSize(2)
                .containsExactly(2.0, 3.0);
    }

    @Test
    void removeFirstGeneric_ShouldThrowIllegalArgumentException_WhenArrayIsNull() {
        // Given
        String[] inputSource = null;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.removeFirst(inputSource))
                .isInstanceOf(IllegalArgumentException.class)
                .withMessage("The input array cannot be null");
    }

    @Test
    void removeFirstGeneric_ShouldThrowIndexOutOfBoundsException_WhenArrayIsEmpty() {
        // Given
        String[] inputSource = new String[]{};

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.removeFirst(inputSource))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .withMessage("Index 0 out of bounds for length 0");
    }

    @Test
    void removeFirstGeneric_ShouldReturnEmptyArray_WhenArrayHasSingleElement() {
        // Given
        String[] inputSource = new String[]{"a"};

        // When
        String[] actual = ArrayUtils.removeFirst(inputSource);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .isEmpty();
    }

    @Test
    void removeFirstGeneric_ShouldRemoveFirstElement_WhenArrayHasMultipleElements() {
        // Given
        String[] inputSource = new String[]{"a", "b", "c"};

        // When
        String[] actual = ArrayUtils.removeFirst(inputSource);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .hasSize(2)
                .containsExactly("b", "c");
    }

    @Test
    void removeLastBoolean_ShouldThrowIllegalArgumentException_WhenArrayIsNull() {
        // Given
        boolean[] inputSource = null;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.removeLast(inputSource))
                .isInstanceOf(IllegalArgumentException.class)
                .withMessage("The input array cannot be null");
    }

    @Test
    void removeLastBoolean_ShouldThrowIndexOutOfBoundsException_WhenArrayIsEmpty() {
        // Given
        boolean[] inputSource = new boolean[]{};

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.removeLast(inputSource))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .withMessage("Index -1 out of bounds for length 0");
    }

    @Test
    void removeLastBoolean_ShouldReturnEmptyArray_WhenArrayHasSingleElement() {
        // Given
        boolean[] inputSource = new boolean[]{true};

        // When
        boolean[] actual = ArrayUtils.removeLast(inputSource);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .isEmpty();
    }

    @Test
    void removeLastBoolean_ShouldRemoveLastElement_WhenArrayHasMultipleElements() {
        // Given
        boolean[] inputSource = new boolean[]{true, false, true};

        // When
        boolean[] actual = ArrayUtils.removeLast(inputSource);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .hasSize(2)
                .containsExactly(true, false);
    }

    @Test
    void removeLastByte_ShouldThrowIllegalArgumentException_WhenArrayIsNull() {
        // Given
        byte[] inputSource = null;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.removeLast(inputSource))
                .isInstanceOf(IllegalArgumentException.class)
                .withMessage("The input array cannot be null");
    }

    @Test
    void removeLastByte_ShouldThrowIndexOutOfBoundsException_WhenArrayIsEmpty() {
        // Given
        byte[] inputSource = new byte[]{};

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.removeLast(inputSource))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .withMessage("Index -1 out of bounds for length 0");
    }

    @Test
    void removeLastByte_ShouldReturnEmptyArray_WhenArrayHasSingleElement() {
        // Given
        byte[] inputSource = new byte[]{1};

        // When
        byte[] actual = ArrayUtils.removeLast(inputSource);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .isEmpty();
    }

    @Test
    void removeLastByte_ShouldRemoveLastElement_WhenArrayHasMultipleElements() {
        // Given
        byte[] inputSource = new byte[]{1, 2, 3};

        // When
        byte[] actual = ArrayUtils.removeLast(inputSource);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .hasSize(2)
                .containsExactly(1, 2);
    }

    @Test
    void removeLastChar_ShouldThrowIllegalArgumentException_WhenArrayIsNull() {
        // Given
        char[] inputSource = null;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.removeLast(inputSource))
                .isInstanceOf(IllegalArgumentException.class)
                .withMessage("The input array cannot be null");
    }

    @Test
    void removeLastChar_ShouldThrowIndexOutOfBoundsException_WhenArrayIsEmpty() {
        // Given
        char[] inputSource = new char[]{};

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.removeLast(inputSource))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .withMessage("Index -1 out of bounds for length 0");
    }

    @Test
    void removeLastChar_ShouldReturnEmptyArray_WhenArrayHasSingleElement() {
        // Given
        char[] inputSource = new char[]{'a'};

        // When
        char[] actual = ArrayUtils.removeLast(inputSource);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .isEmpty();
    }

    @Test
    void removeLastChar_ShouldRemoveLastElement_WhenArrayHasMultipleElements() {
        // Given
        char[] inputSource = new char[]{'a', 'b', 'c'};

        // When
        char[] actual = ArrayUtils.removeLast(inputSource);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .hasSize(2)
                .containsExactly('a', 'b');
    }

    @Test
    void removeLastShort_ShouldThrowIllegalArgumentException_WhenArrayIsNull() {
        // Given
        short[] inputSource = null;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.removeLast(inputSource))
                .isInstanceOf(IllegalArgumentException.class)
                .withMessage("The input array cannot be null");
    }

    @Test
    void removeLastShort_ShouldThrowIndexOutOfBoundsException_WhenArrayIsEmpty() {
        // Given
        short[] inputSource = new short[]{};

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.removeLast(inputSource))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .withMessage("Index -1 out of bounds for length 0");
    }

    @Test
    void removeLastShort_ShouldReturnEmptyArray_WhenArrayHasSingleElement() {
        // Given
        short[] inputSource = new short[]{1};

        // When
        short[] actual = ArrayUtils.removeLast(inputSource);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .isEmpty();
    }

    @Test
    void removeLastShort_ShouldRemoveLastElement_WhenArrayHasMultipleElements() {
        // Given
        short[] inputSource = new short[]{1, 2, 3};

        // When
        short[] actual = ArrayUtils.removeLast(inputSource);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .hasSize(2)
                .containsExactly(1, 2);
    }

    @Test
    void removeLastInt_ShouldThrowIllegalArgumentException_WhenArrayIsNull() {
        // Given
        int[] inputSource = null;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.removeLast(inputSource))
                .isInstanceOf(IllegalArgumentException.class)
                .withMessage("The input array cannot be null");
    }

    @Test
    void removeLastInt_ShouldThrowIndexOutOfBoundsException_WhenArrayIsEmpty() {
        // Given
        int[] inputSource = new int[]{};

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.removeLast(inputSource))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .withMessage("Index -1 out of bounds for length 0");
    }

    @Test
    void removeLastInt_ShouldReturnEmptyArray_WhenArrayHasSingleElement() {
        // Given
        int[] inputSource = new int[]{1};

        // When
        int[] actual = ArrayUtils.removeLast(inputSource);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .isEmpty();
    }

    @Test
    void removeLastInt_ShouldRemoveLastElement_WhenArrayHasMultipleElements() {
        // Given
        int[] inputSource = new int[]{1, 2, 3};

        // When
        int[] actual = ArrayUtils.removeLast(inputSource);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .hasSize(2)
                .containsExactly(1, 2);
    }

    @Test
    void removeLastLong_ShouldThrowIllegalArgumentException_WhenArrayIsNull() {
        // Given
        long[] inputSource = null;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.removeLast(inputSource))
                .isInstanceOf(IllegalArgumentException.class)
                .withMessage("The input array cannot be null");
    }

    @Test
    void removeLastLong_ShouldThrowIndexOutOfBoundsException_WhenArrayIsEmpty() {
        // Given
        long[] inputSource = new long[]{};

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.removeLast(inputSource))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .withMessage("Index -1 out of bounds for length 0");
    }

    @Test
    void removeLastLong_ShouldReturnEmptyArray_WhenArrayHasSingleElement() {
        // Given
        long[] inputSource = new long[]{1L};

        // When
        long[] actual = ArrayUtils.removeLast(inputSource);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .isEmpty();
    }

    @Test
    void removeLastLong_ShouldRemoveLastElement_WhenArrayHasMultipleElements() {
        // Given
        long[] inputSource = new long[]{1L, 2L, 3L};

        // When
        long[] actual = ArrayUtils.removeLast(inputSource);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .hasSize(2)
                .containsExactly(1L, 2L);
    }

    @Test
    void removeLastFloat_ShouldThrowIllegalArgumentException_WhenArrayIsNull() {
        // Given
        float[] inputSource = null;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.removeLast(inputSource))
                .isInstanceOf(IllegalArgumentException.class)
                .withMessage("The input array cannot be null");
    }

    @Test
    void removeLastFloat_ShouldThrowIndexOutOfBoundsException_WhenArrayIsEmpty() {
        // Given
        float[] inputSource = new float[]{};

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.removeLast(inputSource))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .withMessage("Index -1 out of bounds for length 0");
    }

    @Test
    void removeLastFloat_ShouldReturnEmptyArray_WhenArrayHasSingleElement() {
        // Given
        float[] inputSource = new float[]{1.0F};

        // When
        float[] actual = ArrayUtils.removeLast(inputSource);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .isEmpty();
    }

    @Test
    void removeLastFloat_ShouldRemoveLastElement_WhenArrayHasMultipleElements() {
        // Given
        float[] inputSource = new float[]{1.0F, 2.0F, 3.0F};

        // When
        float[] actual = ArrayUtils.removeLast(inputSource);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .hasSize(2)
                .containsExactly(1.0F, 2.0F);
    }

    @Test
    void removeLastDouble_ShouldThrowIllegalArgumentException_WhenArrayIsNull() {
        // Given
        double[] inputSource = null;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.removeLast(inputSource))
                .isInstanceOf(IllegalArgumentException.class)
                .withMessage("The input array cannot be null");
    }

    @Test
    void removeLastDouble_ShouldThrowIndexOutOfBoundsException_WhenArrayIsEmpty() {
        // Given
        double[] inputSource = new double[]{};

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.removeLast(inputSource))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .withMessage("Index -1 out of bounds for length 0");
    }

    @Test
    void removeLastDouble_ShouldReturnEmptyArray_WhenArrayHasSingleElement() {
        // Given
        double[] inputSource = new double[]{1.0};

        // When
        double[] actual = ArrayUtils.removeLast(inputSource);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .isEmpty();
    }

    @Test
    void removeLastDouble_ShouldRemoveLastElement_WhenArrayHasMultipleElements() {
        // Given
        double[] inputSource = new double[]{1.0, 2.0, 3.0};

        // When
        double[] actual = ArrayUtils.removeLast(inputSource);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .hasSize(2)
                .containsExactly(1.0, 2.0);
    }

    @Test
    void removeLastGeneric_ShouldThrowIllegalArgumentException_WhenArrayIsNull() {
        // Given
        String[] inputSource = null;

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.removeLast(inputSource))
                .isInstanceOf(IllegalArgumentException.class)
                .withMessage("The input array cannot be null");
    }

    @Test
    void removeLastGeneric_ShouldThrowIndexOutOfBoundsException_WhenArrayIsEmpty() {
        // Given
        String[] inputSource = new String[]{};

        // When & Then
        thenException()
                .isThrownBy(() -> ArrayUtils.removeLast(inputSource))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .withMessage("Index -1 out of bounds for length 0");
    }

    @Test
    void removeLastGeneric_ShouldReturnEmptyArray_WhenArrayHasSingleElement() {
        // Given
        String[] inputSource = new String[]{"a"};

        // When
        String[] actual = ArrayUtils.removeLast(inputSource);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .isEmpty();
    }

    @Test
    void removeLastGeneric_ShouldRemoveLastElement_WhenArrayHasMultipleElements() {
        // Given
        String[] inputSource = new String[]{"a", "b", "c"};

        // When
        String[] actual = ArrayUtils.removeLast(inputSource);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .hasSize(2)
                .containsExactly("a", "b");
    }

    @Test
    void removeElementBoolean_ShouldReturnEmptyArray_WhenArrayIsNull() {
        // Given
        boolean[] inputSource = null;
        boolean inputElement = true;

        // When
        boolean[] actual = ArrayUtils.removeElement(inputSource, inputElement);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void removeElementBoolean_ShouldReturnEmptyArray_WhenArrayIsEmpty() {
        // Given
        boolean[] inputSource = new boolean[0];
        boolean inputElement = true;

        // When
        boolean[] actual = ArrayUtils.removeElement(inputSource, inputElement);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .isEmpty();
    }

    @Test
    void removeElementBoolean_ShouldReturnClone_WhenElementNotFound() {
        // Given
        boolean[] inputSource = new boolean[]{true, true, true};
        boolean inputElement = false;

        // When
        boolean[] actual = ArrayUtils.removeElement(inputSource, inputElement);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .hasSize(3)
                .containsExactly(true, true, true);
    }

    @Test
    void removeElementBoolean_ShouldRemoveFirstOccurrence_WhenElementExists() {
        // Given
        boolean[] inputSource = new boolean[]{true, false, true, false};
        boolean inputElement = false;

        // When
        boolean[] actual = ArrayUtils.removeElement(inputSource, inputElement);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .hasSize(3)
                .containsExactly(true, true, false);
    }

    @Test
    void removeElementByte_ShouldReturnEmptyArray_WhenArrayIsNull() {
        // Given
        byte[] inputSource = null;
        byte inputElement = 1;

        // When
        byte[] actual = ArrayUtils.removeElement(inputSource, inputElement);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void removeElementByte_ShouldReturnEmptyArray_WhenArrayIsEmpty() {
        // Given
        byte[] inputSource = new byte[0];
        byte inputElement = 1;

        // When
        byte[] actual = ArrayUtils.removeElement(inputSource, inputElement);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .isEmpty();
    }

    @Test
    void removeElementByte_ShouldReturnClone_WhenElementNotFound() {
        // Given
        byte[] inputSource = new byte[]{1, 2, 3};
        byte inputElement = 4;

        // When
        byte[] actual = ArrayUtils.removeElement(inputSource, inputElement);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .hasSize(3)
                .containsExactly(1, 2, 3);
    }

    @Test
    void removeElementByte_ShouldRemoveFirstOccurrence_WhenElementExists() {
        // Given
        byte[] inputSource = new byte[]{1, 2, 3, 2};
        byte inputElement = 2;

        // When
        byte[] actual = ArrayUtils.removeElement(inputSource, inputElement);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .hasSize(3)
                .containsExactly(1, 3, 2);
    }

    @Test
    void removeElementChar_ShouldReturnEmptyArray_WhenArrayIsNull() {
        // Given
        char[] inputSource = null;
        char inputElement = 'a';

        // When
        char[] actual = ArrayUtils.removeElement(inputSource, inputElement);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void removeElementChar_ShouldReturnEmptyArray_WhenArrayIsEmpty() {
        // Given
        char[] inputSource = new char[0];
        char inputElement = 'a';

        // When
        char[] actual = ArrayUtils.removeElement(inputSource, inputElement);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .isEmpty();
    }

    @Test
    void removeElementChar_ShouldReturnClone_WhenElementNotFound() {
        // Given
        char[] inputSource = new char[]{'a', 'b', 'c'};
        char inputElement = 'd';

        // When
        char[] actual = ArrayUtils.removeElement(inputSource, inputElement);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .hasSize(3)
                .containsExactly('a', 'b', 'c');
    }

    @Test
    void removeElementChar_ShouldRemoveFirstOccurrence_WhenElementExists() {
        // Given
        char[] inputSource = new char[]{'a', 'b', 'c', 'b'};
        char inputElement = 'b';

        // When
        char[] actual = ArrayUtils.removeElement(inputSource, inputElement);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .hasSize(3)
                .containsExactly('a', 'c', 'b');
    }

    @Test
    void removeElementShort_ShouldReturnEmptyArray_WhenArrayIsNull() {
        // Given
        short[] inputSource = null;
        short inputElement = 1;

        // When
        short[] actual = ArrayUtils.removeElement(inputSource, inputElement);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void removeElementShort_ShouldReturnEmptyArray_WhenArrayIsEmpty() {
        // Given
        short[] inputSource = new short[0];
        short inputElement = 1;

        // When
        short[] actual = ArrayUtils.removeElement(inputSource, inputElement);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .isEmpty();
    }

    @Test
    void removeElementShort_ShouldReturnClone_WhenElementNotFound() {
        // Given
        short[] inputSource = new short[]{1, 2, 3};
        short inputElement = 4;

        // When
        short[] actual = ArrayUtils.removeElement(inputSource, inputElement);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .hasSize(3)
                .containsExactly(1, 2, 3);
    }

    @Test
    void removeElementShort_ShouldRemoveFirstOccurrence_WhenElementExists() {
        // Given
        short[] inputSource = new short[]{1, 2, 3, 2};
        short inputElement = 2;

        // When
        short[] actual = ArrayUtils.removeElement(inputSource, inputElement);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .hasSize(3)
                .containsExactly(1, 3, 2);
    }

    @Test
    void removeElementInt_ShouldReturnEmptyArray_WhenArrayIsNull() {
        // Given
        int[] inputSource = null;
        int inputElement = 1;

        // When
        int[] actual = ArrayUtils.removeElement(inputSource, inputElement);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void removeElementInt_ShouldReturnEmptyArray_WhenArrayIsEmpty() {
        // Given
        int[] inputSource = new int[0];
        int inputElement = 1;

        // When
        int[] actual = ArrayUtils.removeElement(inputSource, inputElement);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .isEmpty();
    }

    @Test
    void removeElementInt_ShouldReturnClone_WhenElementNotFound() {
        // Given
        int[] inputSource = new int[]{1, 2, 3};
        int inputElement = 4;

        // When
        int[] actual = ArrayUtils.removeElement(inputSource, inputElement);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .hasSize(3)
                .containsExactly(1, 2, 3);
    }

    @Test
    void removeElementInt_ShouldRemoveFirstOccurrence_WhenElementExists() {
        // Given
        int[] inputSource = new int[]{1, 2, 3, 2};
        int inputElement = 2;

        // When
        int[] actual = ArrayUtils.removeElement(inputSource, inputElement);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .hasSize(3)
                .containsExactly(1, 3, 2);
    }

    @Test
    void removeElementLong_ShouldReturnEmptyArray_WhenArrayIsNull() {
        // Given
        long[] inputSource = null;
        long inputElement = 1L;

        // When
        long[] actual = ArrayUtils.removeElement(inputSource, inputElement);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void removeElementLong_ShouldReturnEmptyArray_WhenArrayIsEmpty() {
        // Given
        long[] inputSource = new long[0];
        long inputElement = 1L;

        // When
        long[] actual = ArrayUtils.removeElement(inputSource, inputElement);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .isEmpty();
    }

    @Test
    void removeElementLong_ShouldReturnClone_WhenElementNotFound() {
        // Given
        long[] inputSource = new long[]{1L, 2L, 3L};
        long inputElement = 4L;

        // When
        long[] actual = ArrayUtils.removeElement(inputSource, inputElement);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .hasSize(3)
                .containsExactly(1L, 2L, 3L);
    }

    @Test
    void removeElementLong_ShouldRemoveFirstOccurrence_WhenElementExists() {
        // Given
        long[] inputSource = new long[]{1L, 2L, 3L, 2L};
        long inputElement = 2L;

        // When
        long[] actual = ArrayUtils.removeElement(inputSource, inputElement);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .hasSize(3)
                .containsExactly(1L, 3L, 2L);
    }

    @Test
    void removeElementFloat_ShouldReturnEmptyArray_WhenArrayIsNull() {
        // Given
        float[] inputSource = null;
        float inputElement = 1.0F;

        // When
        float[] actual = ArrayUtils.removeElement(inputSource, inputElement);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void removeElementFloat_ShouldReturnEmptyArray_WhenArrayIsEmpty() {
        // Given
        float[] inputSource = new float[0];
        float inputElement = 1.0F;

        // When
        float[] actual = ArrayUtils.removeElement(inputSource, inputElement);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .isEmpty();
    }

    @Test
    void removeElementFloat_ShouldReturnClone_WhenElementNotFound() {
        // Given
        float[] inputSource = new float[]{1.0F, 2.0F, 3.0F};
        float inputElement = 4.0F;

        // When
        float[] actual = ArrayUtils.removeElement(inputSource, inputElement);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .hasSize(3)
                .containsExactly(1.0F, 2.0F, 3.0F);
    }

    @Test
    void removeElementFloat_ShouldRemoveFirstOccurrence_WhenElementExists() {
        // Given
        float[] inputSource = new float[]{1.0F, 2.0F, 3.0F, 2.0F};
        float inputElement = 2.0F;

        // When
        float[] actual = ArrayUtils.removeElement(inputSource, inputElement);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .hasSize(3)
                .containsExactly(1.0F, 3.0F, 2.0F);
    }

    @Test
    void removeElementDouble_ShouldReturnEmptyArray_WhenArrayIsNull() {
        // Given
        double[] inputSource = null;
        double inputElement = 1.0;

        // When
        double[] actual = ArrayUtils.removeElement(inputSource, inputElement);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void removeElementDouble_ShouldReturnEmptyArray_WhenArrayIsEmpty() {
        // Given
        double[] inputSource = new double[0];
        double inputElement = 1.0;

        // When
        double[] actual = ArrayUtils.removeElement(inputSource, inputElement);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .isEmpty();
    }

    @Test
    void removeElementDouble_ShouldReturnClone_WhenElementNotFound() {
        // Given
        double[] inputSource = new double[]{1.0, 2.0, 3.0};
        double inputElement = 4.0;

        // When
        double[] actual = ArrayUtils.removeElement(inputSource, inputElement);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .hasSize(3)
                .containsExactly(1.0, 2.0, 3.0);
    }

    @Test
    void removeElementDouble_ShouldRemoveFirstOccurrence_WhenElementExists() {
        // Given
        double[] inputSource = new double[]{1.0, 2.0, 3.0, 2.0};
        double inputElement = 2.0;

        // When
        double[] actual = ArrayUtils.removeElement(inputSource, inputElement);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .hasSize(3)
                .containsExactly(1.0, 3.0, 2.0);
    }

    @Test
    void removeElementGeneric_ShouldReturnNull_WhenArrayIsNull() {
        // Given
        String[] inputSource = null;
        String inputElement = "a";

        // When
        String[] actual = ArrayUtils.removeElement(inputSource, inputElement);

        // Then
        then(actual).isNull();
    }

    @Test
    void removeElementGeneric_ShouldReturnEmptyArray_WhenArrayIsEmpty() {
        // Given
        String[] inputSource = new String[0];
        String inputElement = "a";

        // When
        String[] actual = ArrayUtils.removeElement(inputSource, inputElement);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .isEmpty();
    }

    @Test
    void removeElementGeneric_ShouldReturnClone_WhenElementNotFound() {
        // Given
        String[] inputSource = new String[]{"a", "b", "c"};
        String inputElement = "d";

        // When
        String[] actual = ArrayUtils.removeElement(inputSource, inputElement);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .hasSize(3)
                .containsExactly("a", "b", "c");
    }

    @Test
    void removeElementGeneric_ShouldRemoveFirstOccurrence_WhenElementExists() {
        // Given
        String[] inputSource = new String[]{"a", "b", "c", "b"};
        String inputElement = "b";

        // When
        String[] actual = ArrayUtils.removeElement(inputSource, inputElement);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .hasSize(3)
                .containsExactly("a", "c", "b");
    }

    @Test
    void removeElementGeneric_ShouldHandleNullElement_WhenElementIsNull() {
        // Given
        String[] inputSource = new String[]{"a", null, "c"};
        String inputElement = null;

        // When
        String[] actual = ArrayUtils.removeElement(inputSource, inputElement);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .hasSize(2)
                .containsExactly("a", "c");
    }

    @Test
    void sortByte_ShouldReturnNull_WhenArrayIsNull() {
        // Given
        byte[] inputSource = null;

        // When
        byte[] actual = ArrayUtils.sort(inputSource);

        // Then
        then(actual).isNull();
    }

    @Test
    void sortByte_ShouldReturnEmptyArray_WhenArrayIsEmpty() {
        // Given
        byte[] inputSource = new byte[0];

        // When
        byte[] actual = ArrayUtils.sort(inputSource);

        // Then
        then(actual)
                .isSameAs(inputSource)
                .isEmpty();
    }

    @Test
    void sortByte_ShouldSortArray_WhenArrayIsUnsorted() {
        // Given
        byte[] inputSource = new byte[]{3, 1, 2};

        // When
        byte[] actual = ArrayUtils.sort(inputSource);

        // Then
        then(actual)
                .isSameAs(inputSource)
                .hasSize(3)
                .containsExactly(1, 2, 3);
    }

    @Test
    void sortByte_ShouldReturnSameArray_WhenArrayIsAlreadySorted() {
        // Given
        byte[] inputSource = new byte[]{1, 2, 3};

        // When
        byte[] actual = ArrayUtils.sort(inputSource);

        // Then
        then(actual)
                .isSameAs(inputSource)
                .hasSize(3)
                .containsExactly(1, 2, 3);
    }

    @Test
    void sortByte_ShouldSortArray_WhenArrayHasDuplicates() {
        // Given
        byte[] inputSource = new byte[]{2, 1, 2, 1};

        // When
        byte[] actual = ArrayUtils.sort(inputSource);

        // Then
        then(actual)
                .isSameAs(inputSource)
                .hasSize(4)
                .containsExactly(1, 1, 2, 2);
    }

    @Test
    void sortChar_ShouldReturnNull_WhenArrayIsNull() {
        // Given
        char[] inputSource = null;

        // When
        char[] actual = ArrayUtils.sort(inputSource);

        // Then
        then(actual).isNull();
    }

    @Test
    void sortChar_ShouldReturnEmptyArray_WhenArrayIsEmpty() {
        // Given
        char[] inputSource = new char[0];

        // When
        char[] actual = ArrayUtils.sort(inputSource);

        // Then
        then(actual)
                .isSameAs(inputSource)
                .isEmpty();
    }

    @Test
    void sortChar_ShouldSortArray_WhenArrayIsUnsorted() {
        // Given
        char[] inputSource = new char[]{'c', 'a', 'b'};

        // When
        char[] actual = ArrayUtils.sort(inputSource);

        // Then
        then(actual)
                .isSameAs(inputSource)
                .hasSize(3)
                .containsExactly('a', 'b', 'c');
    }

    @Test
    void sortChar_ShouldReturnSameArray_WhenArrayIsAlreadySorted() {
        // Given
        char[] inputSource = new char[]{'a', 'b', 'c'};

        // When
        char[] actual = ArrayUtils.sort(inputSource);

        // Then
        then(actual)
                .isSameAs(inputSource)
                .hasSize(3)
                .containsExactly('a', 'b', 'c');
    }

    @Test
    void sortChar_ShouldSortArray_WhenArrayHasDuplicates() {
        // Given
        char[] inputSource = new char[]{'b', 'a', 'b', 'a'};

        // When
        char[] actual = ArrayUtils.sort(inputSource);

        // Then
        then(actual)
                .isSameAs(inputSource)
                .hasSize(4)
                .containsExactly('a', 'a', 'b', 'b');
    }

    @Test
    void sortShort_ShouldReturnNull_WhenArrayIsNull() {
        // Given
        short[] inputSource = null;

        // When
        short[] actual = ArrayUtils.sort(inputSource);

        // Then
        then(actual).isNull();
    }

    @Test
    void sortShort_ShouldReturnEmptyArray_WhenArrayIsEmpty() {
        // Given
        short[] inputSource = new short[0];

        // When
        short[] actual = ArrayUtils.sort(inputSource);

        // Then
        then(actual)
                .isSameAs(inputSource)
                .isEmpty();
    }

    @Test
    void sortShort_ShouldSortArray_WhenArrayIsUnsorted() {
        // Given
        short[] inputSource = new short[]{3, 1, 2};

        // When
        short[] actual = ArrayUtils.sort(inputSource);

        // Then
        then(actual)
                .isSameAs(inputSource)
                .hasSize(3)
                .containsExactly(1, 2, 3);
    }

    @Test
    void sortShort_ShouldReturnSameArray_WhenArrayIsAlreadySorted() {
        // Given
        short[] inputSource = new short[]{1, 2, 3};

        // When
        short[] actual = ArrayUtils.sort(inputSource);

        // Then
        then(actual)
                .isSameAs(inputSource)
                .hasSize(3)
                .containsExactly(1, 2, 3);
    }

    @Test
    void sortShort_ShouldSortArray_WhenArrayHasDuplicates() {
        // Given
        short[] inputSource = new short[]{2, 1, 2, 1};

        // When
        short[] actual = ArrayUtils.sort(inputSource);

        // Then
        then(actual)
                .isSameAs(inputSource)
                .hasSize(4)
                .containsExactly(1, 1, 2, 2);
    }

    @Test
    void sortInt_ShouldReturnNull_WhenArrayIsNull() {
        // Given
        int[] inputSource = null;

        // When
        int[] actual = ArrayUtils.sort(inputSource);

        // Then
        then(actual).isNull();
    }

    @Test
    void sortInt_ShouldReturnEmptyArray_WhenArrayIsEmpty() {
        // Given
        int[] inputSource = new int[0];

        // When
        int[] actual = ArrayUtils.sort(inputSource);

        // Then
        then(actual)
                .isSameAs(inputSource)
                .isEmpty();
    }

    @Test
    void sortInt_ShouldSortArray_WhenArrayIsUnsorted() {
        // Given
        int[] inputSource = new int[]{3, 1, 2};

        // When
        int[] actual = ArrayUtils.sort(inputSource);

        // Then
        then(actual)
                .isSameAs(inputSource)
                .hasSize(3)
                .containsExactly(1, 2, 3);
    }

    @Test
    void sortInt_ShouldReturnSameArray_WhenArrayIsAlreadySorted() {
        // Given
        int[] inputSource = new int[]{1, 2, 3};

        // When
        int[] actual = ArrayUtils.sort(inputSource);

        // Then
        then(actual)
                .isSameAs(inputSource)
                .hasSize(3)
                .containsExactly(1, 2, 3);
    }

    @Test
    void sortInt_ShouldSortArray_WhenArrayHasDuplicates() {
        // Given
        int[] inputSource = new int[]{2, 1, 2, 1};

        // When
        int[] actual = ArrayUtils.sort(inputSource);

        // Then
        then(actual)
                .isSameAs(inputSource)
                .hasSize(4)
                .containsExactly(1, 1, 2, 2);
    }

    @Test
    void sortLong_ShouldReturnNull_WhenArrayIsNull() {
        // Given
        long[] inputSource = null;

        // When
        long[] actual = ArrayUtils.sort(inputSource);

        // Then
        then(actual).isNull();
    }

    @Test
    void sortLong_ShouldReturnEmptyArray_WhenArrayIsEmpty() {
        // Given
        long[] inputSource = new long[0];

        // When
        long[] actual = ArrayUtils.sort(inputSource);

        // Then
        then(actual)
                .isSameAs(inputSource)
                .isEmpty();
    }

    @Test
    void sortLong_ShouldSortArray_WhenArrayIsUnsorted() {
        // Given
        long[] inputSource = new long[]{3L, 1L, 2L};

        // When
        long[] actual = ArrayUtils.sort(inputSource);

        // Then
        then(actual)
                .isSameAs(inputSource)
                .hasSize(3)
                .containsExactly(1L, 2L, 3L);
    }

    @Test
    void sortLong_ShouldReturnSameArray_WhenArrayIsAlreadySorted() {
        // Given
        long[] inputSource = new long[]{1L, 2L, 3L};

        // When
        long[] actual = ArrayUtils.sort(inputSource);

        // Then
        then(actual)
                .isSameAs(inputSource)
                .hasSize(3)
                .containsExactly(1L, 2L, 3L);
    }

    @Test
    void sortLong_ShouldSortArray_WhenArrayHasDuplicates() {
        // Given
        long[] inputSource = new long[]{2L, 1L, 2L, 1L};

        // When
        long[] actual = ArrayUtils.sort(inputSource);

        // Then
        then(actual)
                .isSameAs(inputSource)
                .hasSize(4)
                .containsExactly(1L, 1L, 2L, 2L);
    }

    @Test
    void sortFloat_ShouldReturnNull_WhenArrayIsNull() {
        // Given
        float[] inputSource = null;

        // When
        float[] actual = ArrayUtils.sort(inputSource);

        // Then
        then(actual).isNull();
    }

    @Test
    void sortFloat_ShouldReturnEmptyArray_WhenArrayIsEmpty() {
        // Given
        float[] inputSource = new float[0];

        // When
        float[] actual = ArrayUtils.sort(inputSource);

        // Then
        then(actual)
                .isSameAs(inputSource)
                .isEmpty();
    }

    @Test
    void sortFloat_ShouldSortArray_WhenArrayIsUnsorted() {
        // Given
        float[] inputSource = new float[]{3.0F, 1.0F, 2.0F};

        // When
        float[] actual = ArrayUtils.sort(inputSource);

        // Then
        then(actual)
                .isSameAs(inputSource)
                .hasSize(3)
                .containsExactly(1.0F, 2.0F, 3.0F);
    }

    @Test
    void sortFloat_ShouldReturnSameArray_WhenArrayIsAlreadySorted() {
        // Given
        float[] inputSource = new float[]{1.0F, 2.0F, 3.0F};

        // When
        float[] actual = ArrayUtils.sort(inputSource);

        // Then
        then(actual)
                .isSameAs(inputSource)
                .hasSize(3)
                .containsExactly(1.0F, 2.0F, 3.0F);
    }

    @Test
    void sortFloat_ShouldSortArray_WhenArrayHasDuplicates() {
        // Given
        float[] inputSource = new float[]{2.0F, 1.0F, 2.0F, 1.0F};

        // When
        float[] actual = ArrayUtils.sort(inputSource);

        // Then
        then(actual)
                .isSameAs(inputSource)
                .hasSize(4)
                .containsExactly(1.0F, 1.0F, 2.0F, 2.0F);
    }

    @Test
    void sortDouble_ShouldReturnNull_WhenArrayIsNull() {
        // Given
        double[] inputSource = null;

        // When
        double[] actual = ArrayUtils.sort(inputSource);

        // Then
        then(actual).isNull();
    }

    @Test
    void sortDouble_ShouldReturnEmptyArray_WhenArrayIsEmpty() {
        // Given
        double[] inputSource = new double[0];

        // When
        double[] actual = ArrayUtils.sort(inputSource);

        // Then
        then(actual)
                .isSameAs(inputSource)
                .isEmpty();
    }

    @Test
    void sortDouble_ShouldSortArray_WhenArrayIsUnsorted() {
        // Given
        double[] inputSource = new double[]{3.0, 1.0, 2.0};

        // When
        double[] actual = ArrayUtils.sort(inputSource);

        // Then
        then(actual)
                .isSameAs(inputSource)
                .hasSize(3)
                .containsExactly(1.0, 2.0, 3.0);
    }

    @Test
    void sortDouble_ShouldReturnSameArray_WhenArrayIsAlreadySorted() {
        // Given
        double[] inputSource = new double[]{1.0, 2.0, 3.0};

        // When
        double[] actual = ArrayUtils.sort(inputSource);

        // Then
        then(actual)
                .isSameAs(inputSource)
                .hasSize(3)
                .containsExactly(1.0, 2.0, 3.0);
    }

    @Test
    void sortDouble_ShouldSortArray_WhenArrayHasDuplicates() {
        // Given
        double[] inputSource = new double[]{2.0, 1.0, 2.0, 1.0};

        // When
        double[] actual = ArrayUtils.sort(inputSource);

        // Then
        then(actual)
                .isSameAs(inputSource)
                .hasSize(4)
                .containsExactly(1.0, 1.0, 2.0, 2.0);
    }

    @Test
    void sortGeneric_ShouldReturnNull_WhenArrayIsNull() {
        // Given
        String[] inputSource = null;

        // When
        String[] actual = ArrayUtils.sort(inputSource);

        // Then
        then(actual).isNull();
    }

    @Test
    void sortGeneric_ShouldReturnEmptyArray_WhenArrayIsEmpty() {
        // Given
        String[] inputSource = new String[0];

        // When
        String[] actual = ArrayUtils.sort(inputSource);

        // Then
        then(actual)
                .isSameAs(inputSource)
                .isEmpty();
    }

    @Test
    void sortGeneric_ShouldSortArray_WhenArrayIsUnsorted() {
        // Given
        String[] inputSource = new String[]{"c", "a", "b"};

        // When
        String[] actual = ArrayUtils.sort(inputSource);

        // Then
        then(actual)
                .isSameAs(inputSource)
                .hasSize(3)
                .containsExactly("a", "b", "c");
    }

    @Test
    void sortGeneric_ShouldReturnSameArray_WhenArrayIsAlreadySorted() {
        // Given
        String[] inputSource = new String[]{"a", "b", "c"};

        // When
        String[] actual = ArrayUtils.sort(inputSource);

        // Then
        then(actual)
                .isSameAs(inputSource)
                .hasSize(3)
                .containsExactly("a", "b", "c");
    }

    @Test
    void sortGeneric_ShouldSortArray_WhenArrayHasDuplicates() {
        // Given
        String[] inputSource = new String[]{"b", "a", "b", "a"};

        // When
        String[] actual = ArrayUtils.sort(inputSource);

        // Then
        then(actual)
                .isSameAs(inputSource)
                .hasSize(4)
                .containsExactly("a", "a", "b", "b");
    }

    @Test
    void sortGenericWithComparator_ShouldReturnNull_WhenArrayIsNull() {
        // Given
        String[] inputSource = null;
        Comparator<String> inputComparator = String::compareTo;

        // When
        String[] actual = ArrayUtils.sort(inputSource, inputComparator);

        // Then
        then(actual).isNull();
    }

    @Test
    void sortGenericWithComparator_ShouldReturnEmptyArray_WhenArrayIsEmpty() {
        // Given
        String[] inputSource = new String[0];
        Comparator<String> inputComparator = String::compareTo;

        // When
        String[] actual = ArrayUtils.sort(inputSource, inputComparator);

        // Then
        then(actual)
                .isSameAs(inputSource)
                .isEmpty();
    }

    @Test
    void sortGenericWithComparator_ShouldSortArrayAscending_WhenArrayIsUnsorted() {
        // Given
        String[] inputSource = new String[]{"c", "a", "b"};
        Comparator<String> inputComparator = String::compareTo;

        // When
        String[] actual = ArrayUtils.sort(inputSource, inputComparator);

        // Then
        then(actual)
                .isSameAs(inputSource)
                .hasSize(3)
                .containsExactly("a", "b", "c");
    }

    @Test
    void sortGenericWithComparator_ShouldSortArrayDescending_WhenUsingReverseComparator() {
        // Given
        String[] inputSource = new String[]{"a", "c", "b"};
        Comparator<String> inputComparator = Comparator.reverseOrder();

        // When
        String[] actual = ArrayUtils.sort(inputSource, inputComparator);

        // Then
        then(actual)
                .isSameAs(inputSource)
                .hasSize(3)
                .containsExactly("c", "b", "a");
    }

    @Test
    void sortGenericWithComparator_ShouldReturnSameArray_WhenArrayIsAlreadySorted() {
        // Given
        String[] inputSource = new String[]{"a", "b", "c"};
        Comparator<String> inputComparator = String::compareTo;

        // When
        String[] actual = ArrayUtils.sort(inputSource, inputComparator);

        // Then
        then(actual)
                .isSameAs(inputSource)
                .hasSize(3)
                .containsExactly("a", "b", "c");
    }

    @Test
    void sortGenericWithComparator_ShouldSortArray_WhenArrayHasDuplicates() {
        // Given
        String[] inputSource = new String[]{"b", "a", "b", "a"};
        Comparator<String> inputComparator = String::compareTo;

        // When
        String[] actual = ArrayUtils.sort(inputSource, inputComparator);

        // Then
        then(actual)
                .isSameAs(inputSource)
                .hasSize(4)
                .containsExactly("a", "a", "b", "b");
    }

    @Test
    void subarrayBoolean_ShouldReturnEmptyArray_WhenArrayIsNull() {
        // Given
        boolean[] inputValue = null;
        int inputStart = 1;
        int inputEnd = 3;

        // When
        boolean[] actual = ArrayUtils.subarray(inputValue, inputStart, inputEnd);

        // Then
        then(actual)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void subarrayBoolean_ShouldReturnEmptyArray_WhenArrayIsEmpty() {
        // Given
        boolean[] inputValue = new boolean[0];
        int inputStart = 0;
        int inputEnd = 1;

        // When
        boolean[] actual = ArrayUtils.subarray(inputValue, inputStart, inputEnd);

        // Then
        then(actual)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void subarrayBoolean_ShouldReturnSubarray_WhenStartAndEndAreValid() {
        // Given
        boolean[] inputValue = new boolean[]{true, false, true, false};
        int inputStart = 1;
        int inputEnd = 3;

        // When
        boolean[] actual = ArrayUtils.subarray(inputValue, inputStart, inputEnd);

        // Then
        then(actual)
                .hasSize(2)
                .containsExactly(false, true);
    }

    @Test
    void subarrayBoolean_ShouldReturnEmptyArray_WhenStartIsGreaterThanOrEqualToEnd() {
        // Given
        boolean[] inputValue = new boolean[]{true, false, true};
        int inputStart = 2;
        int inputEnd = 2;

        // When
        boolean[] actual = ArrayUtils.subarray(inputValue, inputStart, inputEnd);

        // Then
        then(actual)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void subarrayBoolean_ShouldReturnSubarray_WhenStartIsNegative() {
        // Given
        boolean[] inputValue = new boolean[]{true, false, true};
        int inputStart = -1;
        int inputEnd = 2;

        // When
        boolean[] actual = ArrayUtils.subarray(inputValue, inputStart, inputEnd);

        // Then
        then(actual)
                .hasSize(2)
                .containsExactly(true, false);
    }

    @Test
    void subarrayBoolean_ShouldReturnSubarray_WhenEndExceedsArrayLength() {
        // Given
        boolean[] inputValue = new boolean[]{true, false, true};
        int inputStart = 1;
        int inputEnd = 5;

        // When
        boolean[] actual = ArrayUtils.subarray(inputValue, inputStart, inputEnd);

        // Then
        then(actual)
                .hasSize(2)
                .containsExactly(false, true);
    }

    @Test
    void subarrayByte_ShouldReturnEmptyArray_WhenArrayIsNull() {
        // Given
        byte[] inputValue = null;
        int inputStart = 1;
        int inputEnd = 3;

        // When
        byte[] actual = ArrayUtils.subarray(inputValue, inputStart, inputEnd);

        // Then
        then(actual)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void subarrayByte_ShouldReturnEmptyArray_WhenArrayIsEmpty() {
        // Given
        byte[] inputValue = new byte[0];
        int inputStart = 0;
        int inputEnd = 1;

        // When
        byte[] actual = ArrayUtils.subarray(inputValue, inputStart, inputEnd);

        // Then
        then(actual)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void subarrayByte_ShouldReturnSubarray_WhenStartAndEndAreValid() {
        // Given
        byte[] inputValue = new byte[]{1, 2, 3, 4};
        int inputStart = 1;
        int inputEnd = 3;

        // When
        byte[] actual = ArrayUtils.subarray(inputValue, inputStart, inputEnd);

        // Then
        then(actual)
                .hasSize(2)
                .containsExactly(2, 3);
    }

    @Test
    void subarrayByte_ShouldReturnEmptyArray_WhenStartIsGreaterThanOrEqualToEnd() {
        // Given
        byte[] inputValue = new byte[]{1, 2, 3};
        int inputStart = 2;
        int inputEnd = 2;

        // When
        byte[] actual = ArrayUtils.subarray(inputValue, inputStart, inputEnd);

        // Then
        then(actual)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void subarrayByte_ShouldReturnSubarray_WhenStartIsNegative() {
        // Given
        byte[] inputValue = new byte[]{1, 2, 3};
        int inputStart = -1;
        int inputEnd = 2;

        // When
        byte[] actual = ArrayUtils.subarray(inputValue, inputStart, inputEnd);

        // Then
        then(actual)
                .hasSize(2)
                .containsExactly(1, 2);
    }

    @Test
    void subarrayByte_ShouldReturnSubarray_WhenEndExceedsArrayLength() {
        // Given
        byte[] inputValue = new byte[]{1, 2, 3};
        int inputStart = 1;
        int inputEnd = 5;

        // When
        byte[] actual = ArrayUtils.subarray(inputValue, inputStart, inputEnd);

        // Then
        then(actual)
                .hasSize(2)
                .containsExactly(2, 3);
    }

    @Test
    void subarrayChar_ShouldReturnEmptyArray_WhenSourceIsNull() {
        // Given
        char[] inputSource = null;
        int inputStart = 1;
        int inputEnd = 3;

        // When
        char[] actual = ArrayUtils.subarray(inputSource, inputStart, inputEnd);

        // Then
        then(actual)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void subarrayChar_ShouldReturnEmptyArray_WhenSourceIsEmpty() {
        // Given
        char[] inputSource = new char[0];
        int inputStart = 0;
        int inputEnd = 1;

        // When
        char[] actual = ArrayUtils.subarray(inputSource, inputStart, inputEnd);

        // Then
        then(actual)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void subarrayChar_ShouldReturnSubarray_WhenStartAndEndAreValid() {
        // Given
        char[] inputSource = new char[]{'a', 'b', 'c', 'd'};
        int inputStart = 1;
        int inputEnd = 3;

        // When
        char[] actual = ArrayUtils.subarray(inputSource, inputStart, inputEnd);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .hasSize(2)
                .containsExactly('b', 'c');
    }

    @Test
    void subarrayChar_ShouldReturnEmptyArray_WhenStartIsGreaterThanOrEqualToEnd() {
        // Given
        char[] inputSource = new char[]{'a', 'b', 'c'};
        int inputStart = 2;
        int inputEnd = 2;

        // When
        char[] actual = ArrayUtils.subarray(inputSource, inputStart, inputEnd);

        // Then
        then(actual)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void subarrayChar_ShouldReturnSubarray_WhenStartIsNegative() {
        // Given
        char[] inputSource = new char[]{'a', 'b', 'c'};
        int inputStart = -1;
        int inputEnd = 2;

        // When
        char[] actual = ArrayUtils.subarray(inputSource, inputStart, inputEnd);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .hasSize(2)
                .containsExactly('a', 'b');
    }

    @Test
    void subarrayChar_ShouldReturnSubarray_WhenEndExceedsArrayLength() {
        // Given
        char[] inputSource = new char[]{'a', 'b', 'c'};
        int inputStart = 1;
        int inputEnd = 5;

        // When
        char[] actual = ArrayUtils.subarray(inputSource, inputStart, inputEnd);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .hasSize(2)
                .containsExactly('b', 'c');
    }

    @Test
    void subarrayChar_ShouldReturnSingleElement_WhenStartAndEndCoverOneElement() {
        // Given
        char[] inputSource = new char[]{'a', 'b', 'c'};
        int inputStart = 1;
        int inputEnd = 2;

        // When
        char[] actual = ArrayUtils.subarray(inputSource, inputStart, inputEnd);

        // Then
        then(actual)
                .isNotSameAs(inputSource)
                .hasSize(1)
                .containsExactly('b');
    }

    @Test
    void subarrayShort_ShouldReturnEmptyArray_WhenArrayIsNull() {
        // Given
        short[] inputValue = null;
        int inputStart = 1;
        int inputEnd = 3;

        // When
        short[] actual = ArrayUtils.subarray(inputValue, inputStart, inputEnd);

        // Then
        then(actual)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void subarrayShort_ShouldReturnEmptyArray_WhenArrayIsEmpty() {
        // Given
        short[] inputValue = new short[0];
        int inputStart = 0;
        int inputEnd = 1;

        // When
        short[] actual = ArrayUtils.subarray(inputValue, inputStart, inputEnd);

        // Then
        then(actual)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void subarrayShort_ShouldReturnSubarray_WhenStartAndEndAreValid() {
        // Given
        short[] inputValue = new short[]{1, 2, 3, 4};
        int inputStart = 1;
        int inputEnd = 3;

        // When
        short[] actual = ArrayUtils.subarray(inputValue, inputStart, inputEnd);

        // Then
        then(actual)
                .hasSize(2)
                .containsExactly(2, 3);
    }

    @Test
    void subarrayShort_ShouldReturnEmptyArray_WhenStartIsGreaterThanOrEqualToEnd() {
        // Given
        short[] inputValue = new short[]{1, 2, 3};
        int inputStart = 2;
        int inputEnd = 2;

        // When
        short[] actual = ArrayUtils.subarray(inputValue, inputStart, inputEnd);

        // Then
        then(actual)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void subarrayShort_ShouldReturnSubarray_WhenStartIsNegative() {
        // Given
        short[] inputValue = new short[]{1, 2, 3};
        int inputStart = -1;
        int inputEnd = 2;

        // When
        short[] actual = ArrayUtils.subarray(inputValue, inputStart, inputEnd);

        // Then
        then(actual)
                .hasSize(2)
                .containsExactly(1, 2);
    }

    @Test
    void subarrayShort_ShouldReturnSubarray_WhenEndExceedsArrayLength() {
        // Given
        short[] inputValue = new short[]{1, 2, 3};
        int inputStart = 1;
        int inputEnd = 5;

        // When
        short[] actual = ArrayUtils.subarray(inputValue, inputStart, inputEnd);

        // Then
        then(actual)
                .hasSize(2)
                .containsExactly(2, 3);
    }

    @Test
    void subarrayInt_ShouldReturnEmptyArray_WhenArrayIsNull() {
        // Given
        int[] inputValue = null;
        int inputStart = 1;
        int inputEnd = 3;

        // When
        int[] actual = ArrayUtils.subarray(inputValue, inputStart, inputEnd);

        // Then
        then(actual)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void subarrayInt_ShouldReturnEmptyArray_WhenArrayIsEmpty() {
        // Given
        int[] inputValue = new int[0];
        int inputStart = 0;
        int inputEnd = 1;

        // When
        int[] actual = ArrayUtils.subarray(inputValue, inputStart, inputEnd);

        // Then
        then(actual)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void subarrayInt_ShouldReturnSubarray_WhenStartAndEndAreValid() {
        // Given
        int[] inputValue = new int[]{1, 2, 3, 4};
        int inputStart = 1;
        int inputEnd = 3;

        // When
        int[] actual = ArrayUtils.subarray(inputValue, inputStart, inputEnd);

        // Then
        then(actual)
                .hasSize(2)
                .containsExactly(2, 3);
    }

    @Test
    void subarrayInt_ShouldReturnEmptyArray_WhenStartIsGreaterThanOrEqualToEnd() {
        // Given
        int[] inputValue = new int[]{1, 2, 3};
        int inputStart = 2;
        int inputEnd = 2;

        // When
        int[] actual = ArrayUtils.subarray(inputValue, inputStart, inputEnd);

        // Then
        then(actual)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void subarrayInt_ShouldReturnSubarray_WhenStartIsNegative() {
        // Given
        int[] inputValue = new int[]{1, 2, 3};
        int inputStart = -1;
        int inputEnd = 2;

        // When
        int[] actual = ArrayUtils.subarray(inputValue, inputStart, inputEnd);

        // Then
        then(actual)
                .hasSize(2)
                .containsExactly(1, 2);
    }

    @Test
    void subarrayInt_ShouldReturnSubarray_WhenEndExceedsArrayLength() {
        // Given
        int[] inputValue = new int[]{1, 2, 3};
        int inputStart = 1;
        int inputEnd = 5;

        // When
        int[] actual = ArrayUtils.subarray(inputValue, inputStart, inputEnd);

        // Then
        then(actual)
                .hasSize(2)
                .containsExactly(2, 3);
    }

    @Test
    void subarrayLong_ShouldReturnEmptyArray_WhenArrayIsNull() {
        // Given
        long[] inputValue = null;
        int inputStart = 1;
        int inputEnd = 3;

        // When
        long[] actual = ArrayUtils.subarray(inputValue, inputStart, inputEnd);

        // Then
        then(actual)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void subarrayLong_ShouldReturnEmptyArray_WhenArrayIsEmpty() {
        // Given
        long[] inputValue = new long[0];
        int inputStart = 0;
        int inputEnd = 1;

        // When
        long[] actual = ArrayUtils.subarray(inputValue, inputStart, inputEnd);

        // Then
        then(actual)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void subarrayLong_ShouldReturnSubarray_WhenStartAndEndAreValid() {
        // Given
        long[] inputValue = new long[]{1L, 2L, 3L, 4L};
        int inputStart = 1;
        int inputEnd = 3;

        // When
        long[] actual = ArrayUtils.subarray(inputValue, inputStart, inputEnd);

        // Then
        then(actual)
                .hasSize(2)
                .containsExactly(2L, 3L);
    }

    @Test
    void subarrayLong_ShouldReturnEmptyArray_WhenStartIsGreaterThanOrEqualToEnd() {
        // Given
        long[] inputValue = new long[]{1L, 2L, 3L};
        int inputStart = 2;
        int inputEnd = 2;

        // When
        long[] actual = ArrayUtils.subarray(inputValue, inputStart, inputEnd);

        // Then
        then(actual)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void subarrayLong_ShouldReturnSubarray_WhenStartIsNegative() {
        // Given
        long[] inputValue = new long[]{1L, 2L, 3L};
        int inputStart = -1;
        int inputEnd = 2;

        // When
        long[] actual = ArrayUtils.subarray(inputValue, inputStart, inputEnd);

        // Then
        then(actual)
                .hasSize(2)
                .containsExactly(1L, 2L);
    }

    @Test
    void subarrayLong_ShouldReturnSubarray_WhenEndExceedsArrayLength() {
        // Given
        long[] inputValue = new long[]{1L, 2L, 3L};
        int inputStart = 1;
        int inputEnd = 5;

        // When
        long[] actual = ArrayUtils.subarray(inputValue, inputStart, inputEnd);

        // Then
        then(actual)
                .hasSize(2)
                .containsExactly(2L, 3L);
    }

    @Test
    void subarrayFloat_ShouldReturnEmptyArray_WhenArrayIsNull() {
        // Given
        float[] inputValue = null;
        int inputStart = 1;
        int inputEnd = 3;

        // When
        float[] actual = ArrayUtils.subarray(inputValue, inputStart, inputEnd);

        // Then
        then(actual)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void subarrayFloat_ShouldReturnEmptyArray_WhenArrayIsEmpty() {
        // Given
        float[] inputValue = new float[0];
        int inputStart = 0;
        int inputEnd = 1;

        // When
        float[] actual = ArrayUtils.subarray(inputValue, inputStart, inputEnd);

        // Then
        then(actual)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void subarrayFloat_ShouldReturnSubarray_WhenStartAndEndAreValid() {
        // Given
        float[] inputValue = new float[]{1.0F, 2.0F, 3.0F, 4.0F};
        int inputStart = 1;
        int inputEnd = 3;

        // When
        float[] actual = ArrayUtils.subarray(inputValue, inputStart, inputEnd);

        // Then
        then(actual)
                .hasSize(2)
                .containsExactly(2.0F, 3.0F);
    }

    @Test
    void subarrayFloat_ShouldReturnEmptyArray_WhenStartIsGreaterThanOrEqualToEnd() {
        // Given
        float[] inputValue = new float[]{1.0F, 2.0F, 3.0F};
        int inputStart = 2;
        int inputEnd = 2;

        // When
        float[] actual = ArrayUtils.subarray(inputValue, inputStart, inputEnd);

        // Then
        then(actual)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void subarrayFloat_ShouldReturnSubarray_WhenStartIsNegative() {
        // Given
        float[] inputValue = new float[]{1.0F, 2.0F, 3.0F};
        int inputStart = -1;
        int inputEnd = 2;

        // When
        float[] actual = ArrayUtils.subarray(inputValue, inputStart, inputEnd);

        // Then
        then(actual)
                .hasSize(2)
                .containsExactly(1.0F, 2.0F);
    }

    @Test
    void subarrayFloat_ShouldReturnSubarray_WhenEndExceedsArrayLength() {
        // Given
        float[] inputValue = new float[]{1.0F, 2.0F, 3.0F};
        int inputStart = 1;
        int inputEnd = 5;

        // When
        float[] actual = ArrayUtils.subarray(inputValue, inputStart, inputEnd);

        // Then
        then(actual)
                .hasSize(2)
                .containsExactly(2.0F, 3.0F);
    }

    @Test
    void subarrayDouble_ShouldReturnEmptyArray_WhenArrayIsNull() {
        // Given
        double[] inputValue = null;
        int inputStart = 1;
        int inputEnd = 3;

        // When
        double[] actual = ArrayUtils.subarray(inputValue, inputStart, inputEnd);

        // Then
        then(actual)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void subarrayDouble_ShouldReturnEmptyArray_WhenArrayIsEmpty() {
        // Given
        double[] inputValue = new double[0];
        int inputStart = 0;
        int inputEnd = 1;

        // When
        double[] actual = ArrayUtils.subarray(inputValue, inputStart, inputEnd);

        // Then
        then(actual)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void subarrayDouble_ShouldReturnSubarray_WhenStartAndEndAreValid() {
        // Given
        double[] inputValue = new double[]{1.0, 2.0, 3.0, 4.0};
        int inputStart = 1;
        int inputEnd = 3;

        // When
        double[] actual = ArrayUtils.subarray(inputValue, inputStart, inputEnd);

        // Then
        then(actual)
                .hasSize(2)
                .containsExactly(2.0, 3.0);
    }

    @Test
    void subarrayDouble_ShouldReturnEmptyArray_WhenStartIsGreaterThanOrEqualToEnd() {
        // Given
        double[] inputValue = new double[]{1.0, 2.0, 3.0};
        int inputStart = 2;
        int inputEnd = 2;

        // When
        double[] actual = ArrayUtils.subarray(inputValue, inputStart, inputEnd);

        // Then
        then(actual)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void subarrayDouble_ShouldReturnSubarray_WhenStartIsNegative() {
        // Given
        double[] inputValue = new double[]{1.0, 2.0, 3.0};
        int inputStart = -1;
        int inputEnd = 2;

        // When
        double[] actual = ArrayUtils.subarray(inputValue, inputStart, inputEnd);

        // Then
        then(actual)
                .hasSize(2)
                .containsExactly(1.0, 2.0);
    }

    @Test
    void subarrayDouble_ShouldReturnSubarray_WhenEndExceedsArrayLength() {
        // Given
        double[] inputValue = new double[]{1.0, 2.0, 3.0};
        int inputStart = 1;
        int inputEnd = 5;

        // When
        double[] actual = ArrayUtils.subarray(inputValue, inputStart, inputEnd);

        // Then
        then(actual)
                .hasSize(2)
                .containsExactly(2.0, 3.0);
    }

    @Test
    void subarrayGeneric_ShouldReturnEmptyArray_WhenArrayIsNull() {
        // Given
        String[] inputValue = null;
        int inputStart = 1;
        int inputEnd = 3;

        // When
        String[] actual = ArrayUtils.subarray(inputValue, inputStart, inputEnd);

        // Then
        then(actual).isNull();
    }

    @Test
    void subarrayGeneric_ShouldReturnEmptyArray_WhenArrayIsEmpty() {
        // Given
        String[] inputValue = new String[0];
        int inputStart = 0;
        int inputEnd = 1;

        // When
        String[] actual = ArrayUtils.subarray(inputValue, inputStart, inputEnd);

        // Then
        then(actual)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void subarrayGeneric_ShouldReturnSubarray_WhenStartAndEndAreValid() {
        // Given
        String[] inputValue = new String[]{"a", "b", "c", "d"};
        int inputStart = 1;
        int inputEnd = 3;

        // When
        String[] actual = ArrayUtils.subarray(inputValue, inputStart, inputEnd);

        // Then
        then(actual)
                .hasSize(2)
                .containsExactly("b", "c");
    }

    @Test
    void subarrayGeneric_ShouldReturnEmptyArray_WhenStartIsGreaterThanOrEqualToEnd() {
        // Given
        String[] inputValue = new String[]{"a", "b", "c"};
        int inputStart = 2;
        int inputEnd = 2;

        // When
        String[] actual = ArrayUtils.subarray(inputValue, inputStart, inputEnd);

        // Then
        then(actual)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void subarrayGeneric_ShouldReturnSubarray_WhenStartIsNegative() {
        // Given
        String[] inputValue = new String[]{"a", "b", "c"};
        int inputStart = -1;
        int inputEnd = 2;

        // When
        String[] actual = ArrayUtils.subarray(inputValue, inputStart, inputEnd);

        // Then
        then(actual)
                .hasSize(2)
                .containsExactly("a", "b");
    }

    @Test
    void subarrayGeneric_ShouldReturnSubarray_WhenEndExceedsArrayLength() {
        // Given
        String[] inputValue = new String[]{"a", "b", "c"};
        int inputStart = 1;
        int inputEnd = 5;

        // When
        String[] actual = ArrayUtils.subarray(inputValue, inputStart, inputEnd);

        // Then
        then(actual)
                .hasSize(2)
                .containsExactly("b", "c");
    }
}
