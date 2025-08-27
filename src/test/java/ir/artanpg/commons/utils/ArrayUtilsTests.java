package ir.artanpg.commons.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.BDDAssertions.then;

/**
 * Unit tests for the {@link ArrayUtilsTests} class.
 *
 * @author Mohammad Yazdian
 */
class ArrayUtilsTests {

    @ParameterizedTest
    @NullAndEmptySource
    void hasLength_ShouldReturnFalse_WhenBooleanArrayIsNullOrEmpty(boolean[] input) {
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

    @ParameterizedTest
    @NullAndEmptySource
    void hasLength_ShouldReturnFalse_WhenByteArrayIsNullOrEmpty(byte[] input) {
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

    @ParameterizedTest
    @NullAndEmptySource
    void hasLength_ShouldReturnFalse_WhenCharArrayIsNullOrEmpty(char[] input) {
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

    @ParameterizedTest
    @NullAndEmptySource
    void hasLength_ShouldReturnFalse_WhenShortArrayIsNullOrEmpty(short[] input) {
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

    @ParameterizedTest
    @NullAndEmptySource
    void hasLength_ShouldReturnFalse_WhenIntArrayIsNullOrEmpty(int[] input) {
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

    @ParameterizedTest
    @NullAndEmptySource
    void hasLength_ShouldReturnFalse_WhenLongArrayIsNullOrEmpty(long[] input) {
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

    @ParameterizedTest
    @NullAndEmptySource
    void hasLength_ShouldReturnFalse_WhenFloatArrayIsNullOrEmpty(float[] input) {
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

    @ParameterizedTest
    @NullAndEmptySource
    void hasLength_ShouldReturnFalse_WhenDoubleArrayIsNullOrEmpty(double[] input) {
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

    @ParameterizedTest
    @NullAndEmptySource
    void hasLength_ShouldReturnFalse_WhenStringArrayIsNullOrEmpty(String[] input) {
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

    @ParameterizedTest
    @NullAndEmptySource
    void hasLength_ShouldReturnFalse_WhenGenericArrayIsNullOrEmpty(Integer[] input) {
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
