/*
 * Copyright (c) 2026 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ir.artanpg.commons.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.BitSet;
import java.util.Comparator;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

/**
 * Unit tests for {@link ArrayUtils}.
 *
 * @author Mohammad Yazdian
 */
@SuppressWarnings("ALL")
class ArrayUtilsTests {

    @Nested
    class ConstructorTests {

        @Test
        @DisplayName("Should throw UnsupportedOperationException when trying to instantiate")
        void constructor_ShouldThrowUnsupportedOperationException_WhenInstantiated() throws Exception {
            // given
            Constructor<ArrayUtils> constructor = ArrayUtils.class.getDeclaredConstructor();
            constructor.setAccessible(true);

            // when & then
            thenThrownBy(constructor::newInstance)
                    .isInstanceOf(InvocationTargetException.class)
                    .hasCauseInstanceOf(UnsupportedOperationException.class)
                    .hasRootCauseMessage("This class cannot be instantiated");
        }
    }

    @Nested
    class AddBooleanTests {

        @Test
        @DisplayName("Should insert element at valid index")
        void add_ShouldReturnNewArrayWithElementInserted_WhenValidIndex() {
            // given
            boolean[] source = {true, false};

            // when
            boolean[] result = ArrayUtils.add(source, true, 1);

            // then
            then(result).as("New array with element inserted at index 1").containsExactly(true, true, false);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when source is null")
        void add_ShouldThrowIllegalArgumentException_WhenSourceIsNull() {
            // given

            // when & then
            thenThrownBy(() -> ArrayUtils.add(null, true, 0))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The input array cannot be null");
        }

        @Test
        @DisplayName("Should throw IndexOutOfBoundsException when index is negative")
        void add_ShouldThrowIndexOutOfBoundsException_WhenIndexNegative() {
            // given
            boolean[] source = {true};

            // when & then
            thenThrownBy(() -> ArrayUtils.add(source, false, -1))
                    .isInstanceOf(IndexOutOfBoundsException.class)
                    .hasMessage("Index -1 out of bounds for length 1");
        }

        @Test
        @DisplayName("Should throw IndexOutOfBoundsException when index greater than length")
        void add_ShouldThrowIndexOutOfBoundsException_WhenIndexGreaterThanLength() {
            // given
            boolean[] source = {true};

            // when & then
            thenThrownBy(() -> ArrayUtils.add(source, false, 2))
                    .isInstanceOf(IndexOutOfBoundsException.class)
                    .hasMessage("Index 2 out of bounds for length 1");
        }

        @Test
        @DisplayName("Should insert at beginning when index is zero")
        void add_ShouldInsertAtBeginning_WhenIndexIsZero() {
            // given
            boolean[] source = {false};

            // when
            boolean[] result = ArrayUtils.add(source, true, 0);

            // then
            then(result).as("Element inserted at start").containsExactly(true, false);
        }

        @Test
        @DisplayName("Should insert at end when index equals length")
        void add_ShouldInsertAtEnd_WhenIndexEqualsLength() {
            // given
            boolean[] source = {false};

            // when
            boolean[] result = ArrayUtils.add(source, true, 1);

            // then
            then(result).as("Element inserted at end").containsExactly(false, true);
        }

        @Test
        @DisplayName("Should insert into empty array when index is zero")
        void add_ShouldInsertIntoEmptyArray_WhenIndexIsZero() {
            // given
            boolean[] source = {};

            // when
            boolean[] result = ArrayUtils.add(source, true, 0);

            // then
            then(result).as("Element inserted into empty array").containsExactly(true);
        }
    }

    @Nested
    class AddByteTests {

        @Test
        @DisplayName("Should insert element at valid index")
        void add_ShouldReturnNewArrayWithElementInserted_WhenValidIndex() {
            // given
            byte[] source = {1, 2};

            // when
            byte[] result = ArrayUtils.add(source, (byte) 3, 1);

            // then
            then(result).as("New array with element inserted at index 1").containsExactly((byte) 1, (byte) 3, (byte) 2);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when source is null")
        void add_ShouldThrowIllegalArgumentException_WhenSourceIsNull() {
            // given

            // when & then
            thenThrownBy(() -> ArrayUtils.add((byte[]) null, (byte) 1, 0))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The input array cannot be null");
        }

        @Test
        @DisplayName("Should throw IndexOutOfBoundsException when index is negative")
        void add_ShouldThrowIndexOutOfBoundsException_WhenIndexNegative() {
            // given
            byte[] source = {1};

            // when & then
            thenThrownBy(() -> ArrayUtils.add(source, (byte) 2, -1))
                    .isInstanceOf(IndexOutOfBoundsException.class)
                    .hasMessage("Index -1 out of bounds for length 1");
        }

        @Test
        @DisplayName("Should throw IndexOutOfBoundsException when index greater than length")
        void add_ShouldThrowIndexOutOfBoundsException_WhenIndexGreaterThanLength() {
            // given
            byte[] source = {1};

            // when & then
            thenThrownBy(() -> ArrayUtils.add(source, (byte) 2, 2))
                    .isInstanceOf(IndexOutOfBoundsException.class)
                    .hasMessage("Index 2 out of bounds for length 1");
        }
    }

    @Nested
    class AddCharTests {

        @Test
        @DisplayName("Should insert element at valid index")
        void add_ShouldReturnNewArrayWithElementInserted_WhenValidIndex() {
            // given
            char[] source = {'a', 'b'};

            // when
            char[] result = ArrayUtils.add(source, 'c', 1);

            // then
            then(result).as("New array with element inserted at index 1").containsExactly('a', 'c', 'b');
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when source is null")
        void add_ShouldThrowIllegalArgumentException_WhenSourceIsNull() {
            // given

            // when & then
            thenThrownBy(() -> ArrayUtils.add((char[]) null, 'a', 0))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The input array cannot be null");
        }

        @Test
        @DisplayName("Should throw IndexOutOfBoundsException when index is negative")
        void add_ShouldThrowIndexOutOfBoundsException_WhenIndexNegative() {
            // given
            char[] source = {'a'};

            // when & then
            thenThrownBy(() -> ArrayUtils.add(source, 'b', -1))
                    .isInstanceOf(IndexOutOfBoundsException.class)
                    .hasMessage("Index -1 out of bounds for length 1");
        }

        @Test
        @DisplayName("Should throw IndexOutOfBoundsException when index greater than length")
        void add_ShouldThrowIndexOutOfBoundsException_WhenIndexGreaterThanLength() {
            // given
            char[] source = {'a'};

            // when & then
            thenThrownBy(() -> ArrayUtils.add(source, 'b', 2))
                    .isInstanceOf(IndexOutOfBoundsException.class)
                    .hasMessage("Index 2 out of bounds for length 1");
        }
    }

    @Nested
    class AddShortTests {

        @Test
        @DisplayName("Should insert element at valid index")
        void add_ShouldReturnNewArrayWithElementInserted_WhenValidIndex() {
            // given
            short[] source = {1, 2};

            // when
            short[] result = ArrayUtils.add(source, (short) 3, 1);

            // then
            then(result).as("New array with element inserted at index 1").containsExactly((short) 1, (short) 3, (short) 2);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when source is null")
        void add_ShouldThrowIllegalArgumentException_WhenSourceIsNull() {
            // given

            // when & then
            thenThrownBy(() -> ArrayUtils.add((short[]) null, (short) 1, 0))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The input array cannot be null");
        }

        @Test
        @DisplayName("Should throw IndexOutOfBoundsException when index is negative")
        void add_ShouldThrowIndexOutOfBoundsException_WhenIndexNegative() {
            // given
            short[] source = {1};

            // when & then
            thenThrownBy(() -> ArrayUtils.add(source, (short) 2, -1))
                    .isInstanceOf(IndexOutOfBoundsException.class)
                    .hasMessage("Index -1 out of bounds for length 1");
        }

        @Test
        @DisplayName("Should throw IndexOutOfBoundsException when index greater than length")
        void add_ShouldThrowIndexOutOfBoundsException_WhenIndexGreaterThanLength() {
            // given
            short[] source = {1};

            // when & then
            thenThrownBy(() -> ArrayUtils.add(source, (short) 2, 2))
                    .isInstanceOf(IndexOutOfBoundsException.class)
                    .hasMessage("Index 2 out of bounds for length 1");
        }
    }

    @Nested
    class AddIntTests {

        @Test
        @DisplayName("Should insert element at valid index")
        void add_ShouldReturnNewArrayWithElementInserted_WhenValidIndex() {
            // given
            int[] source = {1, 2};

            // when
            int[] result = ArrayUtils.add(source, 3, 1);

            // then
            then(result).as("New array with element inserted at index 1").containsExactly(1, 3, 2);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when source is null")
        void add_ShouldThrowIllegalArgumentException_WhenSourceIsNull() {
            // given

            // when & then
            thenThrownBy(() -> ArrayUtils.add((int[]) null, 1, 0))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The input array cannot be null");
        }

        @Test
        @DisplayName("Should throw IndexOutOfBoundsException when index is negative")
        void add_ShouldThrowIndexOutOfBoundsException_WhenIndexNegative() {
            // given
            int[] source = {1};

            // when & then
            thenThrownBy(() -> ArrayUtils.add(source, 2, -1))
                    .isInstanceOf(IndexOutOfBoundsException.class)
                    .hasMessage("Index -1 out of bounds for length 1");
        }

        @Test
        @DisplayName("Should throw IndexOutOfBoundsException when index greater than length")
        void add_ShouldThrowIndexOutOfBoundsException_WhenIndexGreaterThanLength() {
            // given
            int[] source = {1};

            // when & then
            thenThrownBy(() -> ArrayUtils.add(source, 2, 2))
                    .isInstanceOf(IndexOutOfBoundsException.class)
                    .hasMessage("Index 2 out of bounds for length 1");
        }
    }

    @Nested
    class AddLongTests {

        @Test
        @DisplayName("Should insert element at valid index")
        void add_ShouldReturnNewArrayWithElementInserted_WhenValidIndex() {
            // given
            long[] source = {1L, 2L};

            // when
            long[] result = ArrayUtils.add(source, 3L, 1);

            // then
            then(result).as("New array with element inserted at index 1").containsExactly(1L, 3L, 2L);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when source is null")
        void add_ShouldThrowIllegalArgumentException_WhenSourceIsNull() {
            // given

            // when & then
            thenThrownBy(() -> ArrayUtils.add((long[]) null, 1L, 0))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The input array cannot be null");
        }

        @Test
        @DisplayName("Should throw IndexOutOfBoundsException when index is negative")
        void add_ShouldThrowIndexOutOfBoundsException_WhenIndexNegative() {
            // given
            long[] source = {1L};

            // when & then
            thenThrownBy(() -> ArrayUtils.add(source, 2L, -1))
                    .isInstanceOf(IndexOutOfBoundsException.class)
                    .hasMessage("Index -1 out of bounds for length 1");
        }

        @Test
        @DisplayName("Should throw IndexOutOfBoundsException when index greater than length")
        void add_ShouldThrowIndexOutOfBoundsException_WhenIndexGreaterThanLength() {
            // given
            long[] source = {1L};

            // when & then
            thenThrownBy(() -> ArrayUtils.add(source, 2L, 2))
                    .isInstanceOf(IndexOutOfBoundsException.class)
                    .hasMessage("Index 2 out of bounds for length 1");
        }
    }

    @Nested
    class AddFloatTests {

        @Test
        @DisplayName("Should insert element at valid index")
        void add_ShouldReturnNewArrayWithElementInserted_WhenValidIndex() {
            // given
            float[] source = {1.0f, 2.0f};

            // when
            float[] result = ArrayUtils.add(source, 3.0f, 1);

            // then
            then(result).as("New array with element inserted at index 1").containsExactly(1.0f, 3.0f, 2.0f);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when source is null")
        void add_ShouldThrowIllegalArgumentException_WhenSourceIsNull() {
            // given

            // when & then
            thenThrownBy(() -> ArrayUtils.add((float[]) null, 1.0f, 0))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The input array cannot be null");
        }

        @Test
        @DisplayName("Should throw IndexOutOfBoundsException when index is negative")
        void add_ShouldThrowIndexOutOfBoundsException_WhenIndexNegative() {
            // given
            float[] source = {1.0f};

            // when & then
            thenThrownBy(() -> ArrayUtils.add(source, 2.0f, -1))
                    .isInstanceOf(IndexOutOfBoundsException.class)
                    .hasMessage("Index -1 out of bounds for length 1");
        }

        @Test
        @DisplayName("Should throw IndexOutOfBoundsException when index greater than length")
        void add_ShouldThrowIndexOutOfBoundsException_WhenIndexGreaterThanLength() {
            // given
            float[] source = {1.0f};

            // when & then
            thenThrownBy(() -> ArrayUtils.add(source, 2.0f, 2))
                    .isInstanceOf(IndexOutOfBoundsException.class)
                    .hasMessage("Index 2 out of bounds for length 1");
        }
    }

    @Nested
    class AddDoubleTests {

        @Test
        @DisplayName("Should insert element at valid index")
        void add_ShouldReturnNewArrayWithElementInserted_WhenValidIndex() {
            // given
            double[] source = {1.0, 2.0};

            // when
            double[] result = ArrayUtils.add(source, 3.0, 1);

            // then
            then(result).as("New array with element inserted at index 1").containsExactly(1.0, 3.0, 2.0);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when source is null")
        void add_ShouldThrowIllegalArgumentException_WhenSourceIsNull() {
            // given

            // when & then
            thenThrownBy(() -> ArrayUtils.add(null, 1.0, 0))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The input array cannot be null");
        }

        @Test
        @DisplayName("Should throw IndexOutOfBoundsException when index is negative")
        void add_ShouldThrowIndexOutOfBoundsException_WhenIndexNegative() {
            // given
            double[] source = {1.0};

            // when & then
            thenThrownBy(() -> ArrayUtils.add(source, 2.0, -1))
                    .isInstanceOf(IndexOutOfBoundsException.class)
                    .hasMessage("Index -1 out of bounds for length 1");
        }

        @Test
        @DisplayName("Should throw IndexOutOfBoundsException when index greater than length")
        void add_ShouldThrowIndexOutOfBoundsException_WhenIndexGreaterThanLength() {
            // given
            double[] source = {1.0};

            // when & then
            thenThrownBy(() -> ArrayUtils.add(source, 2.0, 2))
                    .isInstanceOf(IndexOutOfBoundsException.class)
                    .hasMessage("Index 2 out of bounds for length 1");
        }
    }

    @Nested
    class AddObjectTests {

        @Test
        @DisplayName("Should insert element at valid index")
        void add_ShouldReturnNewArrayWithElementInserted_WhenValidIndex() {
            // given
            String[] source = {"a", "b"};

            // when
            String[] result = ArrayUtils.add(source, "c", 1);

            // then
            then(result).as("New array with element inserted at index 1").containsExactly("a", "c", "b");
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when source is null")
        void add_ShouldThrowIllegalArgumentException_WhenSourceIsNull() {
            // given

            // when & then
            thenThrownBy(() -> ArrayUtils.add(null, "a", 0))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The input array cannot be null");
        }

        @Test
        @DisplayName("Should throw IndexOutOfBoundsException when index is negative")
        void add_ShouldThrowIndexOutOfBoundsException_WhenIndexNegative() {
            // given
            String[] source = {"a"};

            // when & then
            thenThrownBy(() -> ArrayUtils.add(source, "b", -1))
                    .isInstanceOf(IndexOutOfBoundsException.class)
                    .hasMessage("Index -1 out of bounds for length 1");
        }

        @Test
        @DisplayName("Should throw IndexOutOfBoundsException when index greater than length")
        void add_ShouldThrowIndexOutOfBoundsException_WhenIndexGreaterThanLength() {
            // given
            String[] source = {"a"};

            // when & then
            thenThrownBy(() -> ArrayUtils.add(source, "b", 2))
                    .isInstanceOf(IndexOutOfBoundsException.class)
                    .hasMessage("Index 2 out of bounds for length 1");
        }
    }

    @Nested
    class AddFirstBooleanTests {

        @Test
        @DisplayName("Should add element at first position")
        void addFirst_ShouldAddElementAtFirstPosition_WhenSourceValid() {
            // given
            boolean[] source = {false};

            // when
            boolean[] result = ArrayUtils.addFirst(source, true);

            // then
            then(result).as("Element added at first position").containsExactly(true, false);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when source is null")
        void addFirst_ShouldThrowIllegalArgumentException_WhenSourceIsNull() {
            // given

            // when & then
            thenThrownBy(() -> ArrayUtils.addFirst(null, true))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The input array cannot be null");
        }
    }

    @Nested
    class AddFirstByteTests {

        @Test
        @DisplayName("Should add element at first position")
        void addFirst_ShouldAddElementAtFirstPosition_WhenSourceValid() {
            // given
            byte[] source = {1};

            // when
            byte[] result = ArrayUtils.addFirst(source, (byte) 2);

            // then
            then(result).as("Element added at first position").containsExactly((byte) 2, (byte) 1);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when source is null")
        void addFirst_ShouldThrowIllegalArgumentException_WhenSourceIsNull() {
            // given

            // when & then
            thenThrownBy(() -> ArrayUtils.addFirst((byte[]) null, (byte) 1))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The input array cannot be null");
        }
    }

    @Nested
    class AddFirstCharTests {

        @Test
        @DisplayName("Should add element at first position")
        void addFirst_ShouldAddElementAtFirstPosition_WhenSourceValid() {
            // given
            char[] source = {'a'};

            // when
            char[] result = ArrayUtils.addFirst(source, 'b');

            // then
            then(result).as("Element added at first position").containsExactly('b', 'a');
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when source is null")
        void addFirst_ShouldThrowIllegalArgumentException_WhenSourceIsNull() {
            // given

            // when & then
            thenThrownBy(() -> ArrayUtils.addFirst((char[]) null, 'a'))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The input array cannot be null");
        }
    }

    @Nested
    class AddFirstShortTests {

        @Test
        @DisplayName("Should add element at first position")
        void addFirst_ShouldAddElementAtFirstPosition_WhenSourceValid() {
            // given
            short[] source = {1};

            // when
            short[] result = ArrayUtils.addFirst(source, (short) 2);

            // then
            then(result).as("Element added at first position").containsExactly((short) 2, (short) 1);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when source is null")
        void addFirst_ShouldThrowIllegalArgumentException_WhenSourceIsNull() {
            // given

            // when & then
            thenThrownBy(() -> ArrayUtils.addFirst((short[]) null, (short) 1))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The input array cannot be null");
        }
    }

    @Nested
    class AddFirstIntTests {

        @Test
        @DisplayName("Should add element at first position")
        void addFirst_ShouldAddElementAtFirstPosition_WhenSourceValid() {
            // given
            int[] source = {1};

            // when
            int[] result = ArrayUtils.addFirst(source, 2);

            // then
            then(result).as("Element added at first position").containsExactly(2, 1);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when source is null")
        void addFirst_ShouldThrowIllegalArgumentException_WhenSourceIsNull() {
            // given

            // when & then
            thenThrownBy(() -> ArrayUtils.addFirst((int[]) null, 1))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The input array cannot be null");
        }
    }

    @Nested
    class AddFirstLongTests {

        @Test
        @DisplayName("Should add element at first position")
        void addFirst_ShouldAddElementAtFirstPosition_WhenSourceValid() {
            // given
            long[] source = {1L};

            // when
            long[] result = ArrayUtils.addFirst(source, 2L);

            // then
            then(result).as("Element added at first position").containsExactly(2L, 1L);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when source is null")
        void addFirst_ShouldThrowIllegalArgumentException_WhenSourceIsNull() {
            // given

            // when & then
            thenThrownBy(() -> ArrayUtils.addFirst((long[]) null, 1L))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The input array cannot be null");
        }
    }

    @Nested
    class AddFirstFloatTests {

        @Test
        @DisplayName("Should add element at first position")
        void addFirst_ShouldAddElementAtFirstPosition_WhenSourceValid() {
            // given
            float[] source = {1.0f};

            // when
            float[] result = ArrayUtils.addFirst(source, 2.0f);

            // then
            then(result).as("Element added at first position").containsExactly(2.0f, 1.0f);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when source is null")
        void addFirst_ShouldThrowIllegalArgumentException_WhenSourceIsNull() {
            // given

            // when & then
            thenThrownBy(() -> ArrayUtils.addFirst((float[]) null, 1.0f))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The input array cannot be null");
        }
    }

    @Nested
    class AddFirstDoubleTests {

        @Test
        @DisplayName("Should add element at first position")
        void addFirst_ShouldAddElementAtFirstPosition_WhenSourceValid() {
            // given
            double[] source = {1.0};

            // when
            double[] result = ArrayUtils.addFirst(source, 2.0);

            // then
            then(result).as("Element added at first position").containsExactly(2.0, 1.0);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when source is null")
        void addFirst_ShouldThrowIllegalArgumentException_WhenSourceIsNull() {
            // given

            // when & then
            thenThrownBy(() -> ArrayUtils.addFirst(null, 1.0))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The input array cannot be null");
        }
    }

    @Nested
    class AddFirstObjectTests {

        @Test
        @DisplayName("Should add element at first position")
        void addFirst_ShouldAddElementAtFirstPosition_WhenSourceValid() {
            // given
            String[] source = {"a"};

            // when
            String[] result = ArrayUtils.addFirst(source, "b");

            // then
            then(result).as("Element added at first position").containsExactly("b", "a");
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when source is null")
        void addFirst_ShouldThrowIllegalArgumentException_WhenSourceIsNull() {
            // given

            // when & then
            thenThrownBy(() -> ArrayUtils.addFirst(null, "a"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The input array cannot be null");
        }
    }

    @Nested
    class AddLastBooleanTests {

        @Test
        @DisplayName("Should add element at last position")
        void addLast_ShouldAddElementAtLastPosition_WhenSourceValid() {
            // given
            boolean[] source = {false};

            // when
            boolean[] result = ArrayUtils.addLast(source, true);

            // then
            then(result).as("Element added at last position").containsExactly(false, true);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when source is null")
        void addLast_ShouldThrowIllegalArgumentException_WhenSourceIsNull() {
            // given

            // when & then
            thenThrownBy(() -> ArrayUtils.addLast(null, true))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The input array cannot be null");
        }
    }

    @Nested
    class AddLastByteTests {

        @Test
        @DisplayName("Should add element at last position")
        void addLast_ShouldAddElementAtLastPosition_WhenSourceValid() {
            // given
            byte[] source = {1};

            // when
            byte[] result = ArrayUtils.addLast(source, (byte) 2);

            // then
            then(result).as("Element added at last position").containsExactly((byte) 1, (byte) 2);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when source is null")
        void addLast_ShouldThrowIllegalArgumentException_WhenSourceIsNull() {
            // given

            // when & then
            thenThrownBy(() -> ArrayUtils.addLast((byte[]) null, (byte) 1))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The input array cannot be null");
        }
    }

    @Nested
    class AddLastCharTests {

        @Test
        @DisplayName("Should add element at last position")
        void addLast_ShouldAddElementAtLastPosition_WhenSourceValid() {
            // given
            char[] source = {'a'};

            // when
            char[] result = ArrayUtils.addLast(source, 'b');

            // then
            then(result).as("Element added at last position").containsExactly('a', 'b');
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when source is null")
        void addLast_ShouldThrowIllegalArgumentException_WhenSourceIsNull() {
            // given

            // when & then
            thenThrownBy(() -> ArrayUtils.addLast((char[]) null, 'a'))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The input array cannot be null");
        }
    }

    @Nested
    class AddLastShortTests {

        @Test
        @DisplayName("Should add element at last position")
        void addLast_ShouldAddElementAtLastPosition_WhenSourceValid() {
            // given
            short[] source = {1};

            // when
            short[] result = ArrayUtils.addLast(source, (short) 2);

            // then
            then(result).as("Element added at last position").containsExactly((short) 1, (short) 2);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when source is null")
        void addLast_ShouldThrowIllegalArgumentException_WhenSourceIsNull() {
            // given

            // when & then
            thenThrownBy(() -> ArrayUtils.addLast((short[]) null, (short) 1))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The input array cannot be null");
        }
    }

    @Nested
    class AddLastIntTests {

        @Test
        @DisplayName("Should add element at last position")
        void addLast_ShouldAddElementAtLastPosition_WhenSourceValid() {
            // given
            int[] source = {1};

            // when
            int[] result = ArrayUtils.addLast(source, 2);

            // then
            then(result).as("Element added at last position").containsExactly(1, 2);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when source is null")
        void addLast_ShouldThrowIllegalArgumentException_WhenSourceIsNull() {
            // given

            // when & then
            thenThrownBy(() -> ArrayUtils.addLast((int[]) null, 1))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The input array cannot be null");
        }
    }

    @Nested
    class AddLastLongTests {

        @Test
        @DisplayName("Should add element at last position")
        void addLast_ShouldAddElementAtLastPosition_WhenSourceValid() {
            // given
            long[] source = {1L};

            // when
            long[] result = ArrayUtils.addLast(source, 2L);

            // then
            then(result).as("Element added at last position").containsExactly(1L, 2L);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when source is null")
        void addLast_ShouldThrowIllegalArgumentException_WhenSourceIsNull() {
            // given

            // when & then
            thenThrownBy(() -> ArrayUtils.addLast((long[]) null, 1L))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The input array cannot be null");
        }
    }

    @Nested
    class AddLastFloatTests {

        @Test
        @DisplayName("Should add element at last position")
        void addLast_ShouldAddElementAtLastPosition_WhenSourceValid() {
            // given
            float[] source = {1.0f};

            // when
            float[] result = ArrayUtils.addLast(source, 2.0f);

            // then
            then(result).as("Element added at last position").containsExactly(1.0f, 2.0f);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when source is null")
        void addLast_ShouldThrowIllegalArgumentException_WhenSourceIsNull() {
            // given

            // when & then
            thenThrownBy(() -> ArrayUtils.addLast((float[]) null, 1.0f))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The input array cannot be null");
        }
    }

    @Nested
    class AddLastDoubleTests {

        @Test
        @DisplayName("Should add element at last position")
        void addLast_ShouldAddElementAtLastPosition_WhenSourceValid() {
            // given
            double[] source = {1.0};

            // when
            double[] result = ArrayUtils.addLast(source, 2.0);

            // then
            then(result).as("Element added at last position").containsExactly(1.0, 2.0);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when source is null")
        void addLast_ShouldThrowIllegalArgumentException_WhenSourceIsNull() {
            // given

            // when & then
            thenThrownBy(() -> ArrayUtils.addLast(null, 1.0))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The input array cannot be null");
        }
    }

    @Nested
    class AddLastObjectTests {

        @Test
        @DisplayName("Should add element at last position")
        void addLast_ShouldAddElementAtLastPosition_WhenSourceValid() {
            // given
            String[] source = {"a"};

            // when
            String[] result = ArrayUtils.addLast(source, "b");

            // then
            then(result).as("Element added at last position").containsExactly("a", "b");
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when source is null")
        void addLast_ShouldThrowIllegalArgumentException_WhenSourceIsNull() {
            // given

            // when & then
            thenThrownBy(() -> ArrayUtils.addLast(null, "a"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The input array cannot be null");
        }
    }

    @Nested
    class CloneBooleanTests {

        @Test
        @DisplayName("Should return copy when source is not null")
        void clone_ShouldReturnCopy_WhenSourceNotNull() {
            // given
            boolean[] source = {true, false};

            // when
            boolean[] result = ArrayUtils.clone(source);

            // then
            then(result).as("Cloned array equals source but is different instance").isEqualTo(source).isNotSameAs(source);
        }

        @Test
        @DisplayName("Should return empty array when source is null")
        void clone_ShouldReturnEmptyArray_WhenSourceIsNull() {
            // given

            // when
            boolean[] result = ArrayUtils.clone((boolean[]) null);

            // then
            then(result).as("Empty array returned for null source").isEmpty();
        }
    }

    @Nested
    class CloneByteTests {

        @Test
        @DisplayName("Should return copy when source is not null")
        void clone_ShouldReturnCopy_WhenSourceNotNull() {
            // given
            byte[] source = {1, 2};

            // when
            byte[] result = ArrayUtils.clone(source);

            // then
            then(result).as("Cloned array equals source but is different instance").isEqualTo(source).isNotSameAs(source);
        }

        @Test
        @DisplayName("Should return empty array when source is null")
        void clone_ShouldReturnEmptyArray_WhenSourceIsNull() {
            // given

            // when
            byte[] result = ArrayUtils.clone((byte[]) null);

            // then
            then(result).as("Empty array returned for null source").isEmpty();
        }
    }

    @Nested
    class CloneCharTests {

        @Test
        @DisplayName("Should return copy when source is not null")
        void clone_ShouldReturnCopy_WhenSourceNotNull() {
            // given
            char[] source = {'a', 'b'};

            // when
            char[] result = ArrayUtils.clone(source);

            // then
            then(result).as("Cloned array equals source but is different instance").isEqualTo(source).isNotSameAs(source);
        }

        @Test
        @DisplayName("Should return empty array when source is null")
        void clone_ShouldReturnEmptyArray_WhenSourceIsNull() {
            // given

            // when
            char[] result = ArrayUtils.clone((char[]) null);

            // then
            then(result).as("Empty array returned for null source").isEmpty();
        }
    }

    @Nested
    class CloneShortTests {

        @Test
        @DisplayName("Should return copy when source is not null")
        void clone_ShouldReturnCopy_WhenSourceNotNull() {
            // given
            short[] source = {1, 2};

            // when
            short[] result = ArrayUtils.clone(source);

            // then
            then(result).as("Cloned array equals source but is different instance").isEqualTo(source).isNotSameAs(source);
        }

        @Test
        @DisplayName("Should return empty array when source is null")
        void clone_ShouldReturnEmptyArray_WhenSourceIsNull() {
            // given

            // when
            short[] result = ArrayUtils.clone((short[]) null);

            // then
            then(result).as("Empty array returned for null source").isEmpty();
        }
    }

    @Nested
    class CloneIntTests {

        @Test
        @DisplayName("Should return copy when source is not null")
        void clone_ShouldReturnCopy_WhenSourceNotNull() {
            // given
            int[] source = {1, 2};

            // when
            int[] result = ArrayUtils.clone(source);

            // then
            then(result).as("Cloned array equals source but is different instance").isEqualTo(source).isNotSameAs(source);
        }

        @Test
        @DisplayName("Should return empty array when source is null")
        void clone_ShouldReturnEmptyArray_WhenSourceIsNull() {
            // given

            // when
            int[] result = ArrayUtils.clone((int[]) null);

            // then
            then(result).as("Empty array returned for null source").isEmpty();
        }
    }

    @Nested
    class CloneLongTests {

        @Test
        @DisplayName("Should return copy when source is not null")
        void clone_ShouldReturnCopy_WhenSourceNotNull() {
            // given
            long[] source = {1L, 2L};

            // when
            long[] result = ArrayUtils.clone(source);

            // then
            then(result).as("Cloned array equals source but is different instance").isEqualTo(source).isNotSameAs(source);
        }

        @Test
        @DisplayName("Should return empty array when source is null")
        void clone_ShouldReturnEmptyArray_WhenSourceIsNull() {
            // given

            // when
            long[] result = ArrayUtils.clone((long[]) null);

            // then
            then(result).as("Empty array returned for null source").isEmpty();
        }
    }

    @Nested
    class CloneFloatTests {

        @Test
        @DisplayName("Should return copy when source is not null")
        void clone_ShouldReturnCopy_WhenSourceNotNull() {
            // given
            float[] source = {1.0f, 2.0f};

            // when
            float[] result = ArrayUtils.clone(source);

            // then
            then(result).as("Cloned array equals source but is different instance").isEqualTo(source).isNotSameAs(source);
        }

        @Test
        @DisplayName("Should return empty array when source is null")
        void clone_ShouldReturnEmptyArray_WhenSourceIsNull() {
            // given

            // when
            float[] result = ArrayUtils.clone((float[]) null);

            // then
            then(result).as("Empty array returned for null source").isEmpty();
        }
    }

    @Nested
    class CloneDoubleTests {

        @Test
        @DisplayName("Should return copy when source is not null")
        void clone_ShouldReturnCopy_WhenSourceNotNull() {
            // given
            double[] source = {1.0, 2.0};

            // when
            double[] result = ArrayUtils.clone(source);

            // then
            then(result).as("Cloned array equals source but is different instance").isEqualTo(source).isNotSameAs(source);
        }

        @Test
        @DisplayName("Should return empty array when source is null")
        void clone_ShouldReturnEmptyArray_WhenSourceIsNull() {
            // given

            // when
            double[] result = ArrayUtils.clone((double[]) null);

            // then
            then(result).as("Empty array returned for null source").isEmpty();
        }
    }

    @Nested
    class CloneObjectTests {

        @Test
        @DisplayName("Should return copy when source is not null")
        void clone_ShouldReturnCopy_WhenSourceNotNull() {
            // given
            String[] source = {"a", "b"};

            // when
            String[] result = ArrayUtils.clone(source);

            // then
            then(result).as("Cloned array equals source but is different instance").isEqualTo(source).isNotSameAs(source);
        }

        @Test
        @DisplayName("Should return null when source is null")
        void clone_ShouldReturnNull_WhenSourceIsNull() {
            // given

            // when
            String[] result = ArrayUtils.clone((String[]) null);

            // then
            then(result).as("Null returned for null source").isNull();
        }
    }

    @Nested
    class ContainsBooleanTests {

        @Test
        @DisplayName("Should return true when element is contained")
        void contains_ShouldReturnTrue_WhenElementContained() {
            // given
            boolean[] source = {true, false};

            // when
            boolean result = ArrayUtils.contains(source, true);

            // then
            then(result).as("Array contains the element").isTrue();
        }

        @Test
        @DisplayName("Should return false when element is not contained")
        void contains_ShouldReturnFalse_WhenElementNotContained() {
            // given
            boolean[] source = {false};

            // when
            boolean result = ArrayUtils.contains(source, true);

            // then
            then(result).as("Array does not contain the element").isFalse();
        }
    }

    @Nested
    class ContainsByteTests {

        @Test
        @DisplayName("Should return true when element is contained")
        void contains_ShouldReturnTrue_WhenElementContained() {
            // given
            byte[] source = {1, 2};

            // when
            boolean result = ArrayUtils.contains(source, (byte) 1);

            // then
            then(result).as("Array contains the element").isTrue();
        }

        @Test
        @DisplayName("Should return false when element is not contained")
        void contains_ShouldReturnFalse_WhenElementNotContained() {
            // given
            byte[] source = {1};

            // when
            boolean result = ArrayUtils.contains(source, (byte) 2);

            // then
            then(result).as("Array does not contain the element").isFalse();
        }
    }

    @Nested
    class ContainsCharTests {

        @Test
        @DisplayName("Should return true when element is contained")
        void contains_ShouldReturnTrue_WhenElementContained() {
            // given
            char[] source = {'a', 'b'};

            // when
            boolean result = ArrayUtils.contains(source, 'a');

            // then
            then(result).as("Array contains the element").isTrue();
        }

        @Test
        @DisplayName("Should return false when element is not contained")
        void contains_ShouldReturnFalse_WhenElementNotContained() {
            // given
            char[] source = {'a'};

            // when
            boolean result = ArrayUtils.contains(source, 'b');

            // then
            then(result).as("Array does not contain the element").isFalse();
        }
    }

    @Nested
    class ContainsShortTests {

        @Test
        @DisplayName("Should return true when element is contained")
        void contains_ShouldReturnTrue_WhenElementContained() {
            // given
            short[] source = {1, 2};

            // when
            boolean result = ArrayUtils.contains(source, (short) 1);

            // then
            then(result).as("Array contains the element").isTrue();
        }

        @Test
        @DisplayName("Should return false when element is not contained")
        void contains_ShouldReturnFalse_WhenElementNotContained() {
            // given
            short[] source = {1};

            // when
            boolean result = ArrayUtils.contains(source, (short) 2);

            // then
            then(result).as("Array does not contain the element").isFalse();
        }
    }

    @Nested
    class ContainsIntTests {

        @Test
        @DisplayName("Should return true when element is contained")
        void contains_ShouldReturnTrue_WhenElementContained() {
            // given
            int[] source = {1, 2};

            // when
            boolean result = ArrayUtils.contains(source, 1);

            // then
            then(result).as("Array contains the element").isTrue();
        }

        @Test
        @DisplayName("Should return false when element is not contained")
        void contains_ShouldReturnFalse_WhenElementNotContained() {
            // given
            int[] source = {1};

            // when
            boolean result = ArrayUtils.contains(source, 2);

            // then
            then(result).as("Array does not contain the element").isFalse();
        }
    }

    @Nested
    class ContainsLongTests {

        @Test
        @DisplayName("Should return true when element is contained")
        void contains_ShouldReturnTrue_WhenElementContained() {
            // given
            long[] source = {1L, 2L};

            // when
            boolean result = ArrayUtils.contains(source, 1L);

            // then
            then(result).as("Array contains the element").isTrue();
        }

        @Test
        @DisplayName("Should return false when element is not contained")
        void contains_ShouldReturnFalse_WhenElementNotContained() {
            // given
            long[] source = {1L};

            // when
            boolean result = ArrayUtils.contains(source, 2L);

            // then
            then(result).as("Array does not contain the element").isFalse();
        }
    }

    @Nested
    class ContainsFloatTests {

        @Test
        @DisplayName("Should return true when element is contained")
        void contains_ShouldReturnTrue_WhenElementContained() {
            // given
            float[] source = {1.0f, 2.0f};

            // when
            boolean result = ArrayUtils.contains(source, 1.0f);

            // then
            then(result).as("Array contains the element").isTrue();
        }

        @Test
        @DisplayName("Should return false when element is not contained")
        void contains_ShouldReturnFalse_WhenElementNotContained() {
            // given
            float[] source = {1.0f};

            // when
            boolean result = ArrayUtils.contains(source, 2.0f);

            // then
            then(result).as("Array does not contain the element").isFalse();
        }
    }

    @Nested
    class ContainsDoubleTests {

        @Test
        @DisplayName("Should return true when element is contained")
        void contains_ShouldReturnTrue_WhenElementContained() {
            // given
            double[] source = {1.0, 2.0};

            // when
            boolean result = ArrayUtils.contains(source, 1.0);

            // then
            then(result).as("Array contains the element").isTrue();
        }

        @Test
        @DisplayName("Should return false when element is not contained")
        void contains_ShouldReturnFalse_WhenElementNotContained() {
            // given
            double[] source = {1.0};

            // when
            boolean result = ArrayUtils.contains(source, 2.0);

            // then
            then(result).as("Array does not contain the element").isFalse();
        }
    }

    @Nested
    class ContainsObjectTests {

        @Test
        @DisplayName("Should return true when element is contained")
        void contains_ShouldReturnTrue_WhenElementContained() {
            // given
            String[] source = {"a", "b"};

            // when
            boolean result = ArrayUtils.contains(source, "a");

            // then
            then(result).as("Array contains the element").isTrue();
        }

        @Test
        @DisplayName("Should return false when element is not contained")
        void contains_ShouldReturnFalse_WhenElementNotContained() {
            // given
            String[] source = {"a"};

            // when
            boolean result = ArrayUtils.contains(source, "b");

            // then
            then(result).as("Array does not contain the element").isFalse();
        }

        @Test
        @DisplayName("Should return true when null element is contained")
        void contains_ShouldReturnTrue_WhenNullElementContained() {
            // given
            String[] source = {null, "a"};

            // when
            boolean result = ArrayUtils.contains(source, null);

            // then
            then(result).as("Array contains null element").isTrue();
        }
    }

    @Nested
    class GetBooleanTests {

        @Test
        @DisplayName("Should return Optional with value when index valid")
        void get_ShouldReturnOptionalWithValue_WhenIndexValid() {
            // given
            boolean[] source = {true, false};

            // when
            Optional<Boolean> result = ArrayUtils.get(source, 0);

            // then
            then(result).as("Optional contains the value at index").contains(true);
        }

        @Test
        @DisplayName("Should return empty Optional when index invalid")
        void get_ShouldReturnEmptyOptional_WhenIndexInvalid() {
            // given
            boolean[] source = {true};

            // when
            Optional<Boolean> result = ArrayUtils.get(source, 1);

            // then
            then(result).as("Empty Optional for invalid index").isEmpty();
        }

        @Test
        @DisplayName("Should return empty Optional when source is null")
        void get_ShouldReturnEmptyOptional_WhenSourceIsNull() {
            // given

            // when
            Optional<Boolean> result = ArrayUtils.get((boolean[]) null, 0);

            // then
            then(result).as("Empty Optional for null source").isEmpty();
        }

        @Test
        @DisplayName("Should return empty Optional when index is negative")
        void get_ShouldReturnEmptyOptional_WhenIndexIsNegative() {
            // given
            boolean[] source = {true};

            // when
            Optional<Boolean> result = ArrayUtils.get(source, -1);

            // then
            then(result).as("Empty Optional for negative index").isEmpty();
        }
    }

    @Nested
    class GetByteTests {

        @Test
        @DisplayName("Should return Optional with value when index valid")
        void get_ShouldReturnOptionalWithValue_WhenIndexValid() {
            // given
            byte[] source = {1, 2};

            // when
            Optional<Byte> result = ArrayUtils.get(source, 0);

            // then
            then(result).as("Optional contains the value at index").contains((byte) 1);
        }

        @Test
        @DisplayName("Should return empty Optional when index invalid")
        void get_ShouldReturnEmptyOptional_WhenIndexInvalid() {
            // given
            byte[] source = {1};

            // when
            Optional<Byte> result = ArrayUtils.get(source, 1);

            // then
            then(result).as("Empty Optional for invalid index").isEmpty();
        }

        @Test
        @DisplayName("Should return empty Optional when source is null")
        void get_ShouldReturnEmptyOptional_WhenSourceIsNull() {
            // given

            // when
            Optional<Byte> result = ArrayUtils.get((byte[]) null, 0);

            // then
            then(result).as("Empty Optional for null source").isEmpty();
        }

        @Test
        @DisplayName("Should return empty Optional when index is negative")
        void get_ShouldReturnEmptyOptional_WhenIndexIsNegative() {
            // given
            byte[] source = {1};

            // when
            Optional<Byte> result = ArrayUtils.get(source, -1);

            // then
            then(result).as("Empty Optional for negative index").isEmpty();
        }
    }

    @Nested
    class GetCharTests {

        @Test
        @DisplayName("Should return Optional with value when index valid")
        void get_ShouldReturnOptionalWithValue_WhenIndexValid() {
            // given
            char[] source = {'a', 'b'};

            // when
            Optional<Character> result = ArrayUtils.get(source, 0);

            // then
            then(result).as("Optional contains the value at index").contains('a');
        }

        @Test
        @DisplayName("Should return empty Optional when index invalid")
        void get_ShouldReturnEmptyOptional_WhenIndexInvalid() {
            // given
            char[] source = {'a'};

            // when
            Optional<Character> result = ArrayUtils.get(source, 1);

            // then
            then(result).as("Empty Optional for invalid index").isEmpty();
        }

        @Test
        @DisplayName("Should return empty Optional when source is null")
        void get_ShouldReturnEmptyOptional_WhenSourceIsNull() {
            // given

            // when
            Optional<Character> result = ArrayUtils.get((char[]) null, 0);

            // then
            then(result).as("Empty Optional for null source").isEmpty();
        }

        @Test
        @DisplayName("Should return empty Optional when index is negative")
        void get_ShouldReturnEmptyOptional_WhenIndexIsNegative() {
            // given
            char[] source = {'a'};

            // when
            Optional<Character> result = ArrayUtils.get(source, -1);

            // then
            then(result).as("Empty Optional for negative index").isEmpty();
        }
    }

    @Nested
    class GetShortTests {

        @Test
        @DisplayName("Should return Optional with value when index valid")
        void get_ShouldReturnOptionalWithValue_WhenIndexValid() {
            // given
            short[] source = {1, 2};

            // when
            Optional<Short> result = ArrayUtils.get(source, 0);

            // then
            then(result).as("Optional contains the value at index").contains((short) 1);
        }

        @Test
        @DisplayName("Should return empty Optional when index invalid")
        void get_ShouldReturnEmptyOptional_WhenIndexInvalid() {
            // given
            short[] source = {1};

            // when
            Optional<Short> result = ArrayUtils.get(source, 1);

            // then
            then(result).as("Empty Optional for invalid index").isEmpty();
        }

        @Test
        @DisplayName("Should return empty Optional when source is null")
        void get_ShouldReturnEmptyOptional_WhenSourceIsNull() {
            // given

            // when
            Optional<Short> result = ArrayUtils.get((short[]) null, 0);

            // then
            then(result).as("Empty Optional for null source").isEmpty();
        }

        @Test
        @DisplayName("Should return empty Optional when index is negative")
        void get_ShouldReturnEmptyOptional_WhenIndexIsNegative() {
            // given
            short[] source = {1};

            // when
            Optional<Short> result = ArrayUtils.get(source, -1);

            // then
            then(result).as("Empty Optional for negative index").isEmpty();
        }
    }

    @Nested
    class GetIntTests {

        @Test
        @DisplayName("Should return Optional with value when index valid")
        void get_ShouldReturnOptionalWithValue_WhenIndexValid() {
            // given
            int[] source = {1, 2};

            // when
            Optional<Integer> result = ArrayUtils.get(source, 0);

            // then
            then(result).as("Optional contains the value at index").contains(1);
        }

        @Test
        @DisplayName("Should return empty Optional when index invalid")
        void get_ShouldReturnEmptyOptional_WhenIndexInvalid() {
            // given
            int[] source = {1};

            // when
            Optional<Integer> result = ArrayUtils.get(source, 1);

            // then
            then(result).as("Empty Optional for invalid index").isEmpty();
        }

        @Test
        @DisplayName("Should return empty Optional when source is null")
        void get_ShouldReturnEmptyOptional_WhenSourceIsNull() {
            // given

            // when
            Optional<Integer> result = ArrayUtils.get((int[]) null, 0);

            // then
            then(result).as("Empty Optional for null source").isEmpty();
        }

        @Test
        @DisplayName("Should return empty Optional when index is negative")
        void get_ShouldReturnEmptyOptional_WhenIndexIsNegative() {
            // given
            int[] source = {1};

            // when
            Optional<Integer> result = ArrayUtils.get(source, -1);

            // then
            then(result).as("Empty Optional for negative index").isEmpty();
        }
    }

    @Nested
    class GetLongTests {

        @Test
        @DisplayName("Should return Optional with value when index valid")
        void get_ShouldReturnOptionalWithValue_WhenIndexValid() {
            // given
            long[] source = {1L, 2L};

            // when
            Optional<Long> result = ArrayUtils.get(source, 0);

            // then
            then(result).as("Optional contains the value at index").contains(1L);
        }

        @Test
        @DisplayName("Should return empty Optional when index invalid")
        void get_ShouldReturnEmptyOptional_WhenIndexInvalid() {
            // given
            long[] source = {1L};

            // when
            Optional<Long> result = ArrayUtils.get(source, 1);

            // then
            then(result).as("Empty Optional for invalid index").isEmpty();
        }

        @Test
        @DisplayName("Should return empty Optional when source is null")
        void get_ShouldReturnEmptyOptional_WhenSourceIsNull() {
            // given

            // when
            Optional<Long> result = ArrayUtils.get((long[]) null, 0);

            // then
            then(result).as("Empty Optional for null source").isEmpty();
        }

        @Test
        @DisplayName("Should return empty Optional when index is negative")
        void get_ShouldReturnEmptyOptional_WhenIndexIsNegative() {
            // given
            long[] source = {1L};

            // when
            Optional<Long> result = ArrayUtils.get(source, -1);

            // then
            then(result).as("Empty Optional for negative index").isEmpty();
        }
    }

    @Nested
    class GetFloatTests {

        @Test
        @DisplayName("Should return Optional with value when index valid")
        void get_ShouldReturnOptionalWithValue_WhenIndexValid() {
            // given
            float[] source = {1.0f, 2.0f};

            // when
            Optional<Float> result = ArrayUtils.get(source, 0);

            // then
            then(result).as("Optional contains the value at index").contains(1.0f);
        }

        @Test
        @DisplayName("Should return empty Optional when index invalid")
        void get_ShouldReturnEmptyOptional_WhenIndexInvalid() {
            // given
            float[] source = {1.0f};

            // when
            Optional<Float> result = ArrayUtils.get(source, 1);

            // then
            then(result).as("Empty Optional for invalid index").isEmpty();
        }

        @Test
        @DisplayName("Should return empty Optional when source is null")
        void get_ShouldReturnEmptyOptional_WhenSourceIsNull() {
            // given

            // when
            Optional<Float> result = ArrayUtils.get((float[]) null, 0);

            // then
            then(result).as("Empty Optional for null source").isEmpty();
        }

        @Test
        @DisplayName("Should return empty Optional when index is negative")
        void get_ShouldReturnEmptyOptional_WhenIndexIsNegative() {
            // given
            float[] source = {1.0f};

            // when
            Optional<Float> result = ArrayUtils.get(source, -1);

            // then
            then(result).as("Empty Optional for negative index").isEmpty();
        }
    }

    @Nested
    class GetDoubleTests {

        @Test
        @DisplayName("Should return Optional with value when index valid")
        void get_ShouldReturnOptionalWithValue_WhenIndexValid() {
            // given
            double[] source = {1.0, 2.0};

            // when
            Optional<Double> result = ArrayUtils.get(source, 0);

            // then
            then(result).as("Optional contains the value at index").contains(1.0);
        }

        @Test
        @DisplayName("Should return empty Optional when index invalid")
        void get_ShouldReturnEmptyOptional_WhenIndexInvalid() {
            // given
            double[] source = {1.0};

            // when
            Optional<Double> result = ArrayUtils.get(source, 1);

            // then
            then(result).as("Empty Optional for invalid index").isEmpty();
        }

        @Test
        @DisplayName("Should return empty Optional when source is null")
        void get_ShouldReturnEmptyOptional_WhenSourceIsNull() {
            // given

            // when
            Optional<Double> result = ArrayUtils.get((double[]) null, 0);

            // then
            then(result).as("Empty Optional for null source").isEmpty();
        }

        @Test
        @DisplayName("Should return empty Optional when index is negative")
        void get_ShouldReturnEmptyOptional_WhenIndexIsNegative() {
            // given
            double[] source = {1.0};

            // when
            Optional<Double> result = ArrayUtils.get(source, -1);

            // then
            then(result).as("Empty Optional for negative index").isEmpty();
        }
    }

    @Nested
    class GetObjectTests {

        @Test
        @DisplayName("Should return Optional with value when index valid")
        void get_ShouldReturnOptionalWithValue_WhenIndexValid() {
            // given
            String[] source = {"a", "b"};

            // when
            Optional<String> result = ArrayUtils.get(source, 0);

            // then
            then(result).as("Optional contains the value at index").contains("a");
        }

        @Test
        @DisplayName("Should return empty Optional when index invalid")
        void get_ShouldReturnEmptyOptional_WhenIndexInvalid() {
            // given
            String[] source = {"a"};

            // when
            Optional<String> result = ArrayUtils.get(source, 1);

            // then
            then(result).as("Empty Optional for invalid index").isEmpty();
        }

        @Test
        @DisplayName("Should return empty Optional when source is null")
        void get_ShouldReturnEmptyOptional_WhenSourceIsNull() {
            // given

            // when
            Optional<String> result = ArrayUtils.get((String[]) null, 0);

            // then
            then(result).as("Empty Optional for null source").isEmpty();
        }

        @Test
        @DisplayName("Should return empty Optional when index is negative")
        void get_ShouldReturnEmptyOptional_WhenIndexIsNegative() {
            // given
            String[] source = {"a"};

            // when
            Optional<String> result = ArrayUtils.get(source, -1);

            // then
            then(result).as("Empty Optional for negative index").isEmpty();
        }
    }

    @Nested
    class GetComponentTypeTests {

        @Test
        @DisplayName("Should return component type when source not null")
        void getComponentType_ShouldReturnComponentType_WhenSourceNotNull() {
            // given
            String[] source = {"a"};

            // when
            Class<String> result = ArrayUtils.getComponentType(source);

            // then
            then(result).as("Component type is String").isEqualTo(String.class);
        }

        @Test
        @DisplayName("Should return null when source is null")
        void getComponentType_ShouldReturnNull_WhenSourceIsNull() {
            // given

            // when
            Class<String> result = ArrayUtils.getComponentType(null);

            // then
            then(result).as("Null for null source").isNull();
        }
    }

    @Nested
    class HasLengthBooleanTests {

        @Test
        @DisplayName("Should return true when array has length")
        void hasLength_ShouldReturnTrue_WhenArrayHasElements() {
            // given
            boolean[] source = {true};

            // when
            boolean result = ArrayUtils.hasLength(source);

            // then
            then(result).as("Array with elements has length").isTrue();
        }

        @Test
        @DisplayName("Should return false when array is empty")
        void hasLength_ShouldReturnFalse_WhenArrayIsEmpty() {
            // given
            boolean[] source = {};

            // when
            boolean result = ArrayUtils.hasLength(source);

            // then
            then(result).as("Empty array has no length").isFalse();
        }

        @Test
        @DisplayName("Should return false when array is null")
        void hasLength_ShouldReturnFalse_WhenArrayIsNull() {
            // given

            // when
            boolean result = ArrayUtils.hasLength((boolean[]) null);

            // then
            then(result).as("Null array has no length").isFalse();
        }
    }

    @Nested
    class HasLengthByteTests {

        @Test
        @DisplayName("Should return true when array has length")
        void hasLength_ShouldReturnTrue_WhenArrayHasElements() {
            // given
            byte[] source = {1};

            // when
            boolean result = ArrayUtils.hasLength(source);

            // then
            then(result).as("Array with elements has length").isTrue();
        }

        @Test
        @DisplayName("Should return false when array is empty")
        void hasLength_ShouldReturnFalse_WhenArrayIsEmpty() {
            // given
            byte[] source = {};

            // when
            boolean result = ArrayUtils.hasLength(source);

            // then
            then(result).as("Empty array has no length").isFalse();
        }

        @Test
        @DisplayName("Should return false when array is null")
        void hasLength_ShouldReturnFalse_WhenArrayIsNull() {
            // given

            // when
            boolean result = ArrayUtils.hasLength((byte[]) null);

            // then
            then(result).as("Null array has no length").isFalse();
        }
    }

    @Nested
    class HasLengthCharTests {

        @Test
        @DisplayName("Should return true when array has length")
        void hasLength_ShouldReturnTrue_WhenArrayHasElements() {
            // given
            char[] source = {'a'};

            // when
            boolean result = ArrayUtils.hasLength(source);

            // then
            then(result).as("Array with elements has length").isTrue();
        }

        @Test
        @DisplayName("Should return false when array is empty")
        void hasLength_ShouldReturnFalse_WhenArrayIsEmpty() {
            // given
            char[] source = {};

            // when
            boolean result = ArrayUtils.hasLength(source);

            // then
            then(result).as("Empty array has no length").isFalse();
        }

        @Test
        @DisplayName("Should return false when array is null")
        void hasLength_ShouldReturnFalse_WhenArrayIsNull() {
            // given

            // when
            boolean result = ArrayUtils.hasLength((char[]) null);

            // then
            then(result).as("Null array has no length").isFalse();
        }
    }

    @Nested
    class HasLengthShortTests {

        @Test
        @DisplayName("Should return true when array has length")
        void hasLength_ShouldReturnTrue_WhenArrayHasElements() {
            // given
            short[] source = {1};

            // when
            boolean result = ArrayUtils.hasLength(source);

            // then
            then(result).as("Array with elements has length").isTrue();
        }

        @Test
        @DisplayName("Should return false when array is empty")
        void hasLength_ShouldReturnFalse_WhenArrayIsEmpty() {
            // given
            short[] source = {};

            // when
            boolean result = ArrayUtils.hasLength(source);

            // then
            then(result).as("Empty array has no length").isFalse();
        }

        @Test
        @DisplayName("Should return false when array is null")
        void hasLength_ShouldReturnFalse_WhenArrayIsNull() {
            // given

            // when
            boolean result = ArrayUtils.hasLength((short[]) null);

            // then
            then(result).as("Null array has no length").isFalse();
        }
    }

    @Nested
    class HasLengthIntTests {

        @Test
        @DisplayName("Should return true when array has length")
        void hasLength_ShouldReturnTrue_WhenArrayHasElements() {
            // given
            int[] source = {1};

            // when
            boolean result = ArrayUtils.hasLength(source);

            // then
            then(result).as("Array with elements has length").isTrue();
        }

        @Test
        @DisplayName("Should return false when array is empty")
        void hasLength_ShouldReturnFalse_WhenArrayIsEmpty() {
            // given
            int[] source = {};

            // when
            boolean result = ArrayUtils.hasLength(source);

            // then
            then(result).as("Empty array has no length").isFalse();
        }

        @Test
        @DisplayName("Should return false when array is null")
        void hasLength_ShouldReturnFalse_WhenArrayIsNull() {
            // given

            // when
            boolean result = ArrayUtils.hasLength((int[]) null);

            // then
            then(result).as("Null array has no length").isFalse();
        }
    }

    @Nested
    class HasLengthLongTests {

        @Test
        @DisplayName("Should return true when array has length")
        void hasLength_ShouldReturnTrue_WhenArrayHasElements() {
            // given
            long[] source = {1L};

            // when
            boolean result = ArrayUtils.hasLength(source);

            // then
            then(result).as("Array with elements has length").isTrue();
        }

        @Test
        @DisplayName("Should return false when array is empty")
        void hasLength_ShouldReturnFalse_WhenArrayIsEmpty() {
            // given
            long[] source = {};

            // when
            boolean result = ArrayUtils.hasLength(source);

            // then
            then(result).as("Empty array has no length").isFalse();
        }

        @Test
        @DisplayName("Should return false when array is null")
        void hasLength_ShouldReturnFalse_WhenArrayIsNull() {
            // given

            // when
            boolean result = ArrayUtils.hasLength((long[]) null);

            // then
            then(result).as("Null array has no length").isFalse();
        }
    }

    @Nested
    class HasLengthFloatTests {

        @Test
        @DisplayName("Should return true when array has length")
        void hasLength_ShouldReturnTrue_WhenArrayHasElements() {
            // given
            float[] source = {1.0f};

            // when
            boolean result = ArrayUtils.hasLength(source);

            // then
            then(result).as("Array with elements has length").isTrue();
        }

        @Test
        @DisplayName("Should return false when array is empty")
        void hasLength_ShouldReturnFalse_WhenArrayIsEmpty() {
            // given
            float[] source = {};

            // when
            boolean result = ArrayUtils.hasLength(source);

            // then
            then(result).as("Empty array has no length").isFalse();
        }

        @Test
        @DisplayName("Should return false when array is null")
        void hasLength_ShouldReturnFalse_WhenArrayIsNull() {
            // given

            // when
            boolean result = ArrayUtils.hasLength((float[]) null);

            // then
            then(result).as("Null array has no length").isFalse();
        }
    }

    @Nested
    class HasLengthDoubleTests {

        @Test
        @DisplayName("Should return true when array has length")
        void hasLength_ShouldReturnTrue_WhenArrayHasElements() {
            // given
            double[] source = {1.0};

            // when
            boolean result = ArrayUtils.hasLength(source);

            // then
            then(result).as("Array with elements has length").isTrue();
        }

        @Test
        @DisplayName("Should return false when array is empty")
        void hasLength_ShouldReturnFalse_WhenArrayIsEmpty() {
            // given
            double[] source = {};

            // when
            boolean result = ArrayUtils.hasLength(source);

            // then
            then(result).as("Empty array has no length").isFalse();
        }

        @Test
        @DisplayName("Should return false when array is null")
        void hasLength_ShouldReturnFalse_WhenArrayIsNull() {
            // given

            // when
            boolean result = ArrayUtils.hasLength((double[]) null);

            // then
            then(result).as("Null array has no length").isFalse();
        }
    }

    @Nested
    class HasLengthObjectTests {

        @Test
        @DisplayName("Should return true when array has length")
        void hasLength_ShouldReturnTrue_WhenArrayHasElements() {
            // given
            String[] source = {"a"};

            // when
            boolean result = ArrayUtils.hasLength(source);

            // then
            then(result).as("Array with elements has length").isTrue();
        }

        @Test
        @DisplayName("Should return false when array is empty")
        void hasLength_ShouldReturnFalse_WhenArrayIsEmpty() {
            // given
            String[] source = {};

            // when
            boolean result = ArrayUtils.hasLength(source);

            // then
            then(result).as("Empty array has no length").isFalse();
        }

        @Test
        @DisplayName("Should return false when array is null")
        void hasLength_ShouldReturnFalse_WhenArrayIsNull() {
            // given

            // when
            boolean result = ArrayUtils.hasLength((String[]) null);

            // then
            then(result).as("Null array has no length").isFalse();
        }
    }

    @Nested
    class IndexOfBooleanTests {

        @Test
        @DisplayName("Should return index when element found")
        void indexOf_ShouldReturnIndex_WhenElementFound() {
            // given
            boolean[] source = {true, false, true};

            // when
            int result = ArrayUtils.indexOf(source, true);

            // then
            then(result).as("First occurrence of true is at index 0").isEqualTo(0);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when not found")
        void indexOf_ShouldReturnIndexNotFound_WhenElementNotFound() {
            // given
            boolean[] source = {false};

            // when
            int result = ArrayUtils.indexOf(source, true);

            // then
            then(result).as("Element not found returns -1").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when fromIndex invalid")
        void indexOf_ShouldReturnIndexNotFound_WhenFromIndexInvalid() {
            // given
            boolean[] source = {true};

            // when
            int result = ArrayUtils.indexOf(source, true, -1);

            // then
            then(result).as("Invalid fromIndex returns -1").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should find from given fromIndex")
        void indexOf_ShouldFindFromFromIndex_WhenFromIndexValid() {
            // given
            boolean[] source = {true, false, true};

            // when
            int result = ArrayUtils.indexOf(source, true, 1);

            // then
            then(result).as("Second occurrence of true is at index 2").isEqualTo(2);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when fromIndex equals length")
        void indexOf_ShouldReturnIndexNotFound_WhenFromIndexEqualsLength() {
            // given
            boolean[] source = {true};

            // when
            int result = ArrayUtils.indexOf(source, true, 1);

            // then
            then(result).as("fromIndex equal to length returns -1").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when source is null")
        void indexOf_ShouldReturnIndexNotFound_WhenSourceIsNullWithFromIndex() {
            // given

            // when
            int result = ArrayUtils.indexOf((boolean[]) null, true, 0);

            // then
            then(result).as("INDEX_NOT_FOUND for null source").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when fromIndex exceeds length")
        void indexOf_ShouldReturnIndexNotFound_WhenFromIndexExceedsLength() {
            // given
            boolean[] source = {true};

            // when
            int result = ArrayUtils.indexOf(source, true, 2);

            // then
            then(result).as("INDEX_NOT_FOUND for fromIndex > length").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }
    }

    @Nested
    class IndexOfByteTests {

        @Test
        @DisplayName("Should return index when element found")
        void indexOf_ShouldReturnIndex_WhenElementFound() {
            // given
            byte[] source = {1, 2, 1};

            // when
            int result = ArrayUtils.indexOf(source, (byte) 1);

            // then
            then(result).as("First occurrence of 1 is at index 0").isEqualTo(0);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when not found")
        void indexOf_ShouldReturnIndexNotFound_WhenElementNotFound() {
            // given
            byte[] source = {2};

            // when
            int result = ArrayUtils.indexOf(source, (byte) 1);

            // then
            then(result).as("Element not found returns -1").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when fromIndex invalid")
        void indexOf_ShouldReturnIndexNotFound_WhenFromIndexInvalid() {
            // given
            byte[] source = {1};

            // when
            int result = ArrayUtils.indexOf(source, (byte) 1, -1);

            // then
            then(result).as("Invalid fromIndex returns -1").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when source is null")
        void indexOf_ShouldReturnIndexNotFound_WhenSourceIsNullWithFromIndex() {
            // given

            // when
            int result = ArrayUtils.indexOf((byte[]) null, (byte) 1, 0);

            // then
            then(result).as("INDEX_NOT_FOUND for null source").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when fromIndex exceeds length")
        void indexOf_ShouldReturnIndexNotFound_WhenFromIndexExceedsLength() {
            // given
            byte[] source = {1};

            // when
            int result = ArrayUtils.indexOf(source, (byte) 1, 2);

            // then
            then(result).as("INDEX_NOT_FOUND for fromIndex > length").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }
    }

    @Nested
    class IndexOfCharTests {

        @Test
        @DisplayName("Should return index when element found")
        void indexOf_ShouldReturnIndex_WhenElementFound() {
            // given
            char[] source = {'a', 'b', 'a'};

            // when
            int result = ArrayUtils.indexOf(source, 'a');

            // then
            then(result).as("First occurrence of a is at index 0").isEqualTo(0);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when not found")
        void indexOf_ShouldReturnIndexNotFound_WhenElementNotFound() {
            // given
            char[] source = {'b'};

            // when
            int result = ArrayUtils.indexOf(source, 'a');

            // then
            then(result).as("Element not found returns -1").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when fromIndex invalid")
        void indexOf_ShouldReturnIndexNotFound_WhenFromIndexInvalid() {
            // given
            char[] source = {'a'};

            // when
            int result = ArrayUtils.indexOf(source, 'a', -1);

            // then
            then(result).as("Invalid fromIndex returns -1").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when source is null")
        void indexOf_ShouldReturnIndexNotFound_WhenSourceIsNullWithFromIndex() {
            // given

            // when
            int result = ArrayUtils.indexOf((char[]) null, 'a', 0);

            // then
            then(result).as("INDEX_NOT_FOUND for null source").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when fromIndex exceeds length")
        void indexOf_ShouldReturnIndexNotFound_WhenFromIndexExceedsLength() {
            // given
            char[] source = {'a'};

            // when
            int result = ArrayUtils.indexOf(source, 'a', 2);

            // then
            then(result).as("INDEX_NOT_FOUND for fromIndex > length").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }
    }

    @Nested
    class IndexOfShortTests {

        @Test
        @DisplayName("Should return index when element found")
        void indexOf_ShouldReturnIndex_WhenElementFound() {
            // given
            short[] source = {1, 2, 1};

            // when
            int result = ArrayUtils.indexOf(source, (short) 1);

            // then
            then(result).as("First occurrence of 1 is at index 0").isEqualTo(0);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when not found")
        void indexOf_ShouldReturnIndexNotFound_WhenElementNotFound() {
            // given
            short[] source = {2};

            // when
            int result = ArrayUtils.indexOf(source, (short) 1);

            // then
            then(result).as("Element not found returns -1").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when fromIndex invalid")
        void indexOf_ShouldReturnIndexNotFound_WhenFromIndexInvalid() {
            // given
            short[] source = {1};

            // when
            int result = ArrayUtils.indexOf(source, (short) 1, -1);

            // then
            then(result).as("Invalid fromIndex returns -1").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when source is null")
        void indexOf_ShouldReturnIndexNotFound_WhenSourceIsNullWithFromIndex() {
            // given

            // when
            int result = ArrayUtils.indexOf((short[]) null, (short) 1, 0);

            // then
            then(result).as("INDEX_NOT_FOUND for null source").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when fromIndex exceeds length")
        void indexOf_ShouldReturnIndexNotFound_WhenFromIndexExceedsLength() {
            // given
            short[] source = {1};

            // when
            int result = ArrayUtils.indexOf(source, (short) 1, 2);

            // then
            then(result).as("INDEX_NOT_FOUND for fromIndex > length").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }
    }

    @Nested
    class IndexOfIntTests {

        @Test
        @DisplayName("Should return index when element found")
        void indexOf_ShouldReturnIndex_WhenElementFound() {
            // given
            int[] source = {1, 2, 1};

            // when
            int result = ArrayUtils.indexOf(source, 1);

            // then
            then(result).as("First occurrence of 1 is at index 0").isEqualTo(0);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when not found")
        void indexOf_ShouldReturnIndexNotFound_WhenElementNotFound() {
            // given
            int[] source = {2};

            // when
            int result = ArrayUtils.indexOf(source, 1);

            // then
            then(result).as("Element not found returns -1").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when fromIndex invalid")
        void indexOf_ShouldReturnIndexNotFound_WhenFromIndexInvalid() {
            // given
            int[] source = {1};

            // when
            int result = ArrayUtils.indexOf(source, 1, -1);

            // then
            then(result).as("Invalid fromIndex returns -1").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when source is null")
        void indexOf_ShouldReturnIndexNotFound_WhenSourceIsNullWithFromIndex() {
            // given

            // when
            int result = ArrayUtils.indexOf((int[]) null, 1, 0);

            // then
            then(result).as("INDEX_NOT_FOUND for null source").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when fromIndex exceeds length")
        void indexOf_ShouldReturnIndexNotFound_WhenFromIndexExceedsLength() {
            // given
            int[] source = {1};

            // when
            int result = ArrayUtils.indexOf(source, 1, 2);

            // then
            then(result).as("INDEX_NOT_FOUND for fromIndex > length").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }
    }

    @Nested
    class IndexOfLongTests {

        @Test
        @DisplayName("Should return index when element found")
        void indexOf_ShouldReturnIndex_WhenElementFound() {
            // given
            long[] source = {1L, 2L, 1L};

            // when
            int result = ArrayUtils.indexOf(source, 1L);

            // then
            then(result).as("First occurrence of 1 is at index 0").isEqualTo(0);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when not found")
        void indexOf_ShouldReturnIndexNotFound_WhenElementNotFound() {
            // given
            long[] source = {2L};

            // when
            int result = ArrayUtils.indexOf(source, 1L);

            // then
            then(result).as("Element not found returns -1").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when fromIndex invalid")
        void indexOf_ShouldReturnIndexNotFound_WhenFromIndexInvalid() {
            // given
            long[] source = {1L};

            // when
            int result = ArrayUtils.indexOf(source, 1L, -1);

            // then
            then(result).as("Invalid fromIndex returns -1").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when source is null")
        void indexOf_ShouldReturnIndexNotFound_WhenSourceIsNullWithFromIndex() {
            // given

            // when
            int result = ArrayUtils.indexOf((long[]) null, 1L, 0);

            // then
            then(result).as("INDEX_NOT_FOUND for null source").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when fromIndex exceeds length")
        void indexOf_ShouldReturnIndexNotFound_WhenFromIndexExceedsLength() {
            // given
            long[] source = {1L};

            // when
            int result = ArrayUtils.indexOf(source, 1L, 2);

            // then
            then(result).as("INDEX_NOT_FOUND for fromIndex > length").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }
    }

    @Nested
    class IndexOfFloatTests {

        @Test
        @DisplayName("Should return index when element found")
        void indexOf_ShouldReturnIndex_WhenElementFound() {
            // given
            float[] source = {1.0f, 2.0f, 1.0f};

            // when
            int result = ArrayUtils.indexOf(source, 1.0f);

            // then
            then(result).as("First occurrence of 1.0f is at index 0").isEqualTo(0);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when not found")
        void indexOf_ShouldReturnIndexNotFound_WhenElementNotFound() {
            // given
            float[] source = {2.0f};

            // when
            int result = ArrayUtils.indexOf(source, 1.0f);

            // then
            then(result).as("Element not found returns -1").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when fromIndex invalid")
        void indexOf_ShouldReturnIndexNotFound_WhenFromIndexInvalid() {
            // given
            float[] source = {1.0f};

            // when
            int result = ArrayUtils.indexOf(source, 1.0f, -1);

            // then
            then(result).as("Invalid fromIndex returns -1").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when source is null")
        void indexOf_ShouldReturnIndexNotFound_WhenSourceIsNullWithFromIndex() {
            // given

            // when
            int result = ArrayUtils.indexOf((float[]) null, 1.0f, 0);

            // then
            then(result).as("INDEX_NOT_FOUND for null source").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when fromIndex exceeds length")
        void indexOf_ShouldReturnIndexNotFound_WhenFromIndexExceedsLength() {
            // given
            float[] source = {1.0f};

            // when
            int result = ArrayUtils.indexOf(source, 1.0f, 2);

            // then
            then(result).as("INDEX_NOT_FOUND for fromIndex > length").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }
    }

    @Nested
    class IndexOfDoubleTests {

        @Test
        @DisplayName("Should return index when element found")
        void indexOf_ShouldReturnIndex_WhenElementFound() {
            // given
            double[] source = {1.0, 2.0, 1.0};

            // when
            int result = ArrayUtils.indexOf(source, 1.0);

            // then
            then(result).as("First occurrence of 1.0 is at index 0").isEqualTo(0);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when not found")
        void indexOf_ShouldReturnIndexNotFound_WhenElementNotFound() {
            // given
            double[] source = {2.0};

            // when
            int result = ArrayUtils.indexOf(source, 1.0);

            // then
            then(result).as("Element not found returns -1").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when fromIndex invalid")
        void indexOf_ShouldReturnIndexNotFound_WhenFromIndexInvalid() {
            // given
            double[] source = {1.0};

            // when
            int result = ArrayUtils.indexOf(source, 1.0, -1);

            // then
            then(result).as("Invalid fromIndex returns -1").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when source is null")
        void indexOf_ShouldReturnIndexNotFound_WhenSourceIsNullWithFromIndex() {
            // given

            // when
            int result = ArrayUtils.indexOf((double[]) null, 1.0, 0);

            // then
            then(result).as("INDEX_NOT_FOUND for null source").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when fromIndex exceeds length")
        void indexOf_ShouldReturnIndexNotFound_WhenFromIndexExceedsLength() {
            // given
            double[] source = {1.0};

            // when
            int result = ArrayUtils.indexOf(source, 1.0, 2);

            // then
            then(result).as("INDEX_NOT_FOUND for fromIndex > length").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }
    }

    @Nested
    class IndexOfObjectTests {

        @Test
        @DisplayName("Should return index when element found")
        void indexOf_ShouldReturnIndex_WhenElementFound() {
            // given
            String[] source = {"a", "b", "a"};

            // when
            int result = ArrayUtils.indexOf(source, "a");

            // then
            then(result).as("First occurrence of a is at index 0").isEqualTo(0);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when not found")
        void indexOf_ShouldReturnIndexNotFound_WhenElementNotFound() {
            // given
            String[] source = {"b"};

            // when
            int result = ArrayUtils.indexOf(source, "a");

            // then
            then(result).as("Element not found returns -1").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when fromIndex invalid")
        void indexOf_ShouldReturnIndexNotFound_WhenFromIndexInvalid() {
            // given
            String[] source = {"a"};

            // when
            int result = ArrayUtils.indexOf(source, "a", -1);

            // then
            then(result).as("Invalid fromIndex returns -1").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should find null element")
        void indexOf_ShouldFindNullElement_WhenNullPresent() {
            // given
            String[] source = {"a", null, "b"};

            // when
            int result = ArrayUtils.indexOf(source, null);

            // then
            then(result).as("Null found at index 1").isEqualTo(1);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when type incompatible")
        void indexOf_ShouldReturnIndexNotFound_WhenTypeIncompatible() {
            // given
            String[] source = {"a"};

            // when
            int result = ArrayUtils.indexOf(source, 1);

            // then
            then(result).as("Incompatible type returns -1").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when source is null")
        void indexOf_ShouldReturnIndexNotFound_WhenSourceIsNull() {
            // given

            // when
            int result = ArrayUtils.indexOf((String[]) null, "a");

            // then
            then(result).as("INDEX_NOT_FOUND for null source").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when fromIndex exceeds length")
        void indexOf_ShouldReturnIndexNotFound_WhenFromIndexExceedsLength() {
            // given
            String[] source = {"a"};

            // when
            int result = ArrayUtils.indexOf(source, "a", 2);

            // then
            then(result).as("INDEX_NOT_FOUND for fromIndex > length").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when null element not found")
        void indexOf_ShouldReturnIndexNotFound_WhenNullElementNotFound() {
            // given
            String[] source = {"a", "b"};

            // when
            int result = ArrayUtils.indexOf(source, null);

            // then
            then(result).as("INDEX_NOT_FOUND for null element not found").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }
    }

    @Nested
    class IndexesOfBooleanTests {

        @Test
        @DisplayName("Should return BitSet with all indices")
        void indexesOf_ShouldReturnBitSetWithAllIndices_WhenMultipleFound() {
            // given
            boolean[] source = {true, false, true};

            // when
            BitSet result = ArrayUtils.indexesOf(source, true);

            // then
            then(result.cardinality()).as("BitSet contains indices 0 and 2").isEqualTo(2);
        }

        @Test
        @DisplayName("Should return empty BitSet when not found")
        void indexesOf_ShouldReturnEmptyBitSet_WhenNotFound() {
            // given
            boolean[] source = {false};

            // when
            BitSet result = ArrayUtils.indexesOf(source, true);

            // then
            then(result.isEmpty()).as("Empty BitSet when not found").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when fromIndex invalid")
        void indexesOf_ShouldReturnEmptyBitSet_WhenFromIndexInvalid() {
            // given
            boolean[] source = {true};

            // when
            BitSet result = ArrayUtils.indexesOf(source, true, -1);

            // then
            then(result.isEmpty()).as("Empty BitSet for invalid fromIndex").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when source is null")
        void indexesOf_ShouldReturnEmptyBitSet_WhenSourceIsNull() {
            // given

            // when
            BitSet result = ArrayUtils.indexesOf((boolean[]) null, true);

            // then
            then(result.isEmpty()).as("Empty BitSet for null source").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when fromIndex exceeds length")
        void indexesOf_ShouldReturnEmptyBitSet_WhenFromIndexExceedsLength() {
            // given
            boolean[] source = {true};

            // when
            BitSet result = ArrayUtils.indexesOf(source, true, 2);

            // then
            then(result.isEmpty()).as("Empty BitSet for fromIndex > length").isTrue();
        }
    }

    @Nested
    class IndexesOfByteTests {

        @Test
        @DisplayName("Should return BitSet with all indices")
        void indexesOf_ShouldReturnBitSetWithAllIndices_WhenMultipleFound() {
            // given
            byte[] source = {1, 2, 1};

            // when
            BitSet result = ArrayUtils.indexesOf(source, (byte) 1);

            // then
            then(result.cardinality()).as("BitSet contains indices 0 and 2").isEqualTo(2);
        }

        @Test
        @DisplayName("Should return empty BitSet when not found")
        void indexesOf_ShouldReturnEmptyBitSet_WhenNotFound() {
            // given
            byte[] source = {2};

            // when
            BitSet result = ArrayUtils.indexesOf(source, (byte) 1);

            // then
            then(result.isEmpty()).as("Empty BitSet when not found").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when source is null")
        void indexesOf_ShouldReturnEmptyBitSet_WhenSourceIsNull() {
            // given

            // when
            BitSet result = ArrayUtils.indexesOf((byte[]) null, (byte) 1);

            // then
            then(result.isEmpty()).as("Empty BitSet for null source").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when fromIndex is negative")
        void indexesOf_ShouldReturnEmptyBitSet_WhenFromIndexIsNegative() {
            // given
            byte[] source = {1};

            // when
            BitSet result = ArrayUtils.indexesOf(source, (byte) 1, -1);

            // then
            then(result.isEmpty()).as("Empty BitSet for fromIndex < 0").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when fromIndex exceeds length")
        void indexesOf_ShouldReturnEmptyBitSet_WhenFromIndexExceedsLength() {
            // given
            byte[] source = {1};

            // when
            BitSet result = ArrayUtils.indexesOf(source, (byte) 1, 2);

            // then
            then(result.isEmpty()).as("Empty BitSet for fromIndex > length").isTrue();
        }
    }

    @Nested
    class IndexesOfCharTests {

        @Test
        @DisplayName("Should return BitSet with all indices")
        void indexesOf_ShouldReturnBitSetWithAllIndices_WhenMultipleFound() {
            // given
            char[] source = {'a', 'b', 'a'};

            // when
            BitSet result = ArrayUtils.indexesOf(source, 'a');

            // then
            then(result.cardinality()).as("BitSet contains indices 0 and 2").isEqualTo(2);
        }

        @Test
        @DisplayName("Should return empty BitSet when not found")
        void indexesOf_ShouldReturnEmptyBitSet_WhenNotFound() {
            // given
            char[] source = {'b'};

            // when
            BitSet result = ArrayUtils.indexesOf(source, 'a');

            // then
            then(result.isEmpty()).as("Empty BitSet when not found").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when source is null")
        void indexesOf_ShouldReturnEmptyBitSet_WhenSourceIsNull() {
            // given

            // when
            BitSet result = ArrayUtils.indexesOf((char[]) null, 'a');

            // then
            then(result.isEmpty()).as("Empty BitSet for null source").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when fromIndex is negative")
        void indexesOf_ShouldReturnEmptyBitSet_WhenFromIndexIsNegative() {
            // given
            char[] source = {'a'};

            // when
            BitSet result = ArrayUtils.indexesOf(source, 'a', -1);

            // then
            then(result.isEmpty()).as("Empty BitSet for fromIndex < 0").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when fromIndex exceeds length")
        void indexesOf_ShouldReturnEmptyBitSet_WhenFromIndexExceedsLength() {
            // given
            char[] source = {'a'};

            // when
            BitSet result = ArrayUtils.indexesOf(source, 'a', 2);

            // then
            then(result.isEmpty()).as("Empty BitSet for fromIndex > length").isTrue();
        }
    }

    @Nested
    class IndexesOfShortTests {

        @Test
        @DisplayName("Should return BitSet with all indices")
        void indexesOf_ShouldReturnBitSetWithAllIndices_WhenMultipleFound() {
            // given
            short[] source = {1, 2, 1};

            // when
            BitSet result = ArrayUtils.indexesOf(source, (short) 1);

            // then
            then(result.cardinality()).as("BitSet contains indices 0 and 2").isEqualTo(2);
        }

        @Test
        @DisplayName("Should return empty BitSet when not found")
        void indexesOf_ShouldReturnEmptyBitSet_WhenNotFound() {
            // given
            short[] source = {2};

            // when
            BitSet result = ArrayUtils.indexesOf(source, (short) 1);

            // then
            then(result.isEmpty()).as("Empty BitSet when not found").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when source is null")
        void indexesOf_ShouldReturnEmptyBitSet_WhenSourceIsNull() {
            // given

            // when
            BitSet result = ArrayUtils.indexesOf((short[]) null, (short) 1);

            // then
            then(result.isEmpty()).as("Empty BitSet for null source").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when fromIndex is negative")
        void indexesOf_ShouldReturnEmptyBitSet_WhenFromIndexIsNegative() {
            // given
            short[] source = {1};

            // when
            BitSet result = ArrayUtils.indexesOf(source, (short) 1, -1);

            // then
            then(result.isEmpty()).as("Empty BitSet for fromIndex < 0").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when fromIndex exceeds length")
        void indexesOf_ShouldReturnEmptyBitSet_WhenFromIndexExceedsLength() {
            // given
            short[] source = {1};

            // when
            BitSet result = ArrayUtils.indexesOf(source, (short) 1, 2);

            // then
            then(result.isEmpty()).as("Empty BitSet for fromIndex > length").isTrue();
        }
    }

    @Nested
    class IndexesOfIntTests {

        @Test
        @DisplayName("Should return BitSet with all indices")
        void indexesOf_ShouldReturnBitSetWithAllIndices_WhenMultipleFound() {
            // given
            int[] source = {1, 2, 1};

            // when
            BitSet result = ArrayUtils.indexesOf(source, 1);

            // then
            then(result.cardinality()).as("BitSet contains indices 0 and 2").isEqualTo(2);
        }

        @Test
        @DisplayName("Should return empty BitSet when not found")
        void indexesOf_ShouldReturnEmptyBitSet_WhenNotFound() {
            // given
            int[] source = {2};

            // when
            BitSet result = ArrayUtils.indexesOf(source, 1);

            // then
            then(result.isEmpty()).as("Empty BitSet when not found").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when source is null")
        void indexesOf_ShouldReturnEmptyBitSet_WhenSourceIsNull() {
            // given

            // when
            BitSet result = ArrayUtils.indexesOf((int[]) null, 1);

            // then
            then(result.isEmpty()).as("Empty BitSet for null source").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when fromIndex is negative")
        void indexesOf_ShouldReturnEmptyBitSet_WhenFromIndexIsNegative() {
            // given
            int[] source = {1};

            // when
            BitSet result = ArrayUtils.indexesOf(source, 1, -1);

            // then
            then(result.isEmpty()).as("Empty BitSet for fromIndex < 0").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when fromIndex exceeds length")
        void indexesOf_ShouldReturnEmptyBitSet_WhenFromIndexExceedsLength() {
            // given
            int[] source = {1};

            // when
            BitSet result = ArrayUtils.indexesOf(source, 1, 2);

            // then
            then(result.isEmpty()).as("Empty BitSet for fromIndex > length").isTrue();
        }
    }

    @Nested
    class IndexesOfLongTests {

        @Test
        @DisplayName("Should return BitSet with all indices")
        void indexesOf_ShouldReturnBitSetWithAllIndices_WhenMultipleFound() {
            // given
            long[] source = {1L, 2L, 1L};

            // when
            BitSet result = ArrayUtils.indexesOf(source, 1L);

            // then
            then(result.cardinality()).as("BitSet contains indices 0 and 2").isEqualTo(2);
        }

        @Test
        @DisplayName("Should return empty BitSet when not found")
        void indexesOf_ShouldReturnEmptyBitSet_WhenNotFound() {
            // given
            long[] source = {2L};

            // when
            BitSet result = ArrayUtils.indexesOf(source, 1L);

            // then
            then(result.isEmpty()).as("Empty BitSet when not found").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when source is null")
        void indexesOf_ShouldReturnEmptyBitSet_WhenSourceIsNull() {
            // given

            // when
            BitSet result = ArrayUtils.indexesOf((long[]) null, 1L);

            // then
            then(result.isEmpty()).as("Empty BitSet for null source").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when fromIndex is negative")
        void indexesOf_ShouldReturnEmptyBitSet_WhenFromIndexIsNegative() {
            // given
            long[] source = {1L};

            // when
            BitSet result = ArrayUtils.indexesOf(source, 1L, -1);

            // then
            then(result.isEmpty()).as("Empty BitSet for fromIndex < 0").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when fromIndex exceeds length")
        void indexesOf_ShouldReturnEmptyBitSet_WhenFromIndexExceedsLength() {
            // given
            long[] source = {1L};

            // when
            BitSet result = ArrayUtils.indexesOf(source, 1L, 2);

            // then
            then(result.isEmpty()).as("Empty BitSet for fromIndex > length").isTrue();
        }
    }

    @Nested
    class IndexesOfFloatTests {

        @Test
        @DisplayName("Should return BitSet with all indices")
        void indexesOf_ShouldReturnBitSetWithAllIndices_WhenMultipleFound() {
            // given
            float[] source = {1.0f, 2.0f, 1.0f};

            // when
            BitSet result = ArrayUtils.indexesOf(source, 1.0f);

            // then
            then(result.cardinality()).as("BitSet contains indices 0 and 2").isEqualTo(2);
        }

        @Test
        @DisplayName("Should return empty BitSet when not found")
        void indexesOf_ShouldReturnEmptyBitSet_WhenNotFound() {
            // given
            float[] source = {2.0f};

            // when
            BitSet result = ArrayUtils.indexesOf(source, 1.0f);

            // then
            then(result.isEmpty()).as("Empty BitSet when not found").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when source is null")
        void indexesOf_ShouldReturnEmptyBitSet_WhenSourceIsNull() {
            // given

            // when
            BitSet result = ArrayUtils.indexesOf((float[]) null, 1.0f);

            // then
            then(result.isEmpty()).as("Empty BitSet for null source").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when fromIndex is negative")
        void indexesOf_ShouldReturnEmptyBitSet_WhenFromIndexIsNegative() {
            // given
            float[] source = {1.0f};

            // when
            BitSet result = ArrayUtils.indexesOf(source, 1.0f, -1);

            // then
            then(result.isEmpty()).as("Empty BitSet for fromIndex < 0").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when fromIndex exceeds length")
        void indexesOf_ShouldReturnEmptyBitSet_WhenFromIndexExceedsLength() {
            // given
            float[] source = {1.0f};

            // when
            BitSet result = ArrayUtils.indexesOf(source, 1.0f, 2);

            // then
            then(result.isEmpty()).as("Empty BitSet for fromIndex > length").isTrue();
        }
    }

    @Nested
    class IndexesOfDoubleTests {

        @Test
        @DisplayName("Should return BitSet with all indices")
        void indexesOf_ShouldReturnBitSetWithAllIndices_WhenMultipleFound() {
            // given
            double[] source = {1.0, 2.0, 1.0};

            // when
            BitSet result = ArrayUtils.indexesOf(source, 1.0);

            // then
            then(result.cardinality()).as("BitSet contains indices 0 and 2").isEqualTo(2);
        }

        @Test
        @DisplayName("Should return empty BitSet when not found")
        void indexesOf_ShouldReturnEmptyBitSet_WhenNotFound() {
            // given
            double[] source = {2.0};

            // when
            BitSet result = ArrayUtils.indexesOf(source, 1.0);

            // then
            then(result.isEmpty()).as("Empty BitSet when not found").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when source is null")
        void indexesOf_ShouldReturnEmptyBitSet_WhenSourceIsNull() {
            // given

            // when
            BitSet result = ArrayUtils.indexesOf((double[]) null, 1.0);

            // then
            then(result.isEmpty()).as("Empty BitSet for null source").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when fromIndex is negative")
        void indexesOf_ShouldReturnEmptyBitSet_WhenFromIndexIsNegative() {
            // given
            double[] source = {1.0};

            // when
            BitSet result = ArrayUtils.indexesOf(source, 1.0, -1);

            // then
            then(result.isEmpty()).as("Empty BitSet for fromIndex < 0").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when fromIndex exceeds length")
        void indexesOf_ShouldReturnEmptyBitSet_WhenFromIndexExceedsLength() {
            // given
            double[] source = {1.0};

            // when
            BitSet result = ArrayUtils.indexesOf(source, 1.0, 2);

            // then
            then(result.isEmpty()).as("Empty BitSet for fromIndex > length").isTrue();
        }
    }

    @Nested
    class IndexesOfObjectTests {

        @Test
        @DisplayName("Should return BitSet with all indices")
        void indexesOf_ShouldReturnBitSetWithAllIndices_WhenMultipleFound() {
            // given
            String[] source = {"a", "b", "a"};

            // when
            BitSet result = ArrayUtils.indexesOf(source, "a");

            // then
            then(result.cardinality()).as("BitSet contains indices 0 and 2").isEqualTo(2);
        }

        @Test
        @DisplayName("Should return empty BitSet when not found")
        void indexesOf_ShouldReturnEmptyBitSet_WhenNotFound() {
            // given
            String[] source = {"b"};

            // when
            BitSet result = ArrayUtils.indexesOf(source, "a");

            // then
            then(result.isEmpty()).as("Empty BitSet when not found").isTrue();
        }

        @Test
        @DisplayName("Should find null elements")
        void indexesOf_ShouldFindNullElements_WhenNullPresent() {
            // given
            String[] source = {null, "a", null};

            // when
            BitSet result = ArrayUtils.indexesOf(source, null);

            // then
            then(result).isEqualTo(BitSet.valueOf(new long[]{0b101L}));
            then(result.cardinality()).as("BitSet contains indices of nulls").isEqualTo(2);
            then(result.get(0)).isTrue();
            then(result.get(2)).isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when source is null")
        void indexesOf_ShouldReturnEmptyBitSet_WhenSourceIsNull() {
            // given

            // when
            BitSet result = ArrayUtils.indexesOf((String[]) null, "a");

            // then
            then(result.isEmpty()).as("Empty BitSet for null source").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when fromIndex is negative")
        void indexesOf_ShouldReturnEmptyBitSet_WhenFromIndexIsNegative() {
            // given
            String[] source = {"a"};

            // when
            BitSet result = ArrayUtils.indexesOf(source, "a", -1);

            // then
            then(result.isEmpty()).as("Empty BitSet for fromIndex < 0").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when fromIndex exceeds length")
        void indexesOf_ShouldReturnEmptyBitSet_WhenFromIndexExceedsLength() {
            // given
            String[] source = {"a"};

            // when
            BitSet result = ArrayUtils.indexesOf(source, "a", 2);

            // then
            then(result.isEmpty()).as("Empty BitSet for fromIndex > length").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when fromIndex equals length")
        void indexesOf_ShouldReturnEmptyBitSet_WhenFromIndexEqualsLength() {
            // given
            String[] source = {"a"};

            // when
            BitSet result = ArrayUtils.indexesOf(source, "a", 1);

            // then
            then(result.isEmpty()).as("Empty BitSet for fromIndex == length").isTrue();
        }
    }

    @Nested
    class LastIndexOfBooleanTests {

        @Test
        @DisplayName("Should return last index when element found")
        void lastIndexOf_ShouldReturnLastIndex_WhenElementFound() {
            // given
            boolean[] source = {true, false, true};

            // when
            int result = ArrayUtils.lastIndexOf(source, true);

            // then
            then(result).as("Last occurrence of true is at index 2").isEqualTo(2);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when not found")
        void lastIndexOf_ShouldReturnIndexNotFound_WhenElementNotFound() {
            // given
            boolean[] source = {false};

            // when
            int result = ArrayUtils.lastIndexOf(source, true);

            // then
            then(result).as("Element not found returns -1").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when fromIndex invalid")
        void lastIndexOf_ShouldReturnIndexNotFound_WhenFromIndexInvalid() {
            // given
            boolean[] source = {true};

            // when
            int result = ArrayUtils.lastIndexOf(source, true, -1);

            // then
            then(result).as("Invalid fromIndex returns -1").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when source is null")
        void lastIndexOf_ShouldReturnIndexNotFound_WhenSourceIsNull() {
            // given

            // when
            int result = ArrayUtils.lastIndexOf((boolean[]) null, (boolean) true);

            // then
            then(result).as("INDEX_NOT_FOUND for null source").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when source is null with fromIndex")
        void lastIndexOf_ShouldReturnIndexNotFound_WhenSourceIsNullWithFromIndex() {
            // given

            // when
            int result = ArrayUtils.lastIndexOf((boolean[]) null, true, 0);

            // then
            then(result).as("INDEX_NOT_FOUND for null source with fromIndex").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when fromIndex equals length")
        void lastIndexOf_ShouldReturnIndexNotFound_WhenFromIndexEqualsLength() {
            // given
            boolean[] source = {true};

            // when
            int result = ArrayUtils.lastIndexOf(source, true, 1);

            // then
            then(result).as("INDEX_NOT_FOUND for fromIndex >= length").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }
    }

    @Nested
    class LastIndexOfByteTests {

        @Test
        @DisplayName("Should return last index when element found")
        void lastIndexOf_ShouldReturnLastIndex_WhenElementFound() {
            // given
            byte[] source = {1, 2, 1};

            // when
            int result = ArrayUtils.lastIndexOf(source, (byte) 1);

            // then
            then(result).as("Last occurrence of 1 is at index 2").isEqualTo(2);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when not found")
        void lastIndexOf_ShouldReturnIndexNotFound_WhenElementNotFound() {
            // given
            byte[] source = {2};

            // when
            int result = ArrayUtils.lastIndexOf(source, (byte) 1);

            // then
            then(result).as("Element not found returns -1").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when source is null")
        void lastIndexOf_ShouldReturnIndexNotFound_WhenSourceIsNull() {
            // given

            // when
            int result = ArrayUtils.lastIndexOf((byte[]) null, (byte) 1);

            // then
            then(result).as("INDEX_NOT_FOUND for null source").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when fromIndex is negative")
        void lastIndexOf_ShouldReturnIndexNotFound_WhenFromIndexIsNegative() {
            // given
            byte[] source = {1};

            // when
            int result = ArrayUtils.lastIndexOf(source, (byte) 1, -1);

            // then
            then(result).as("INDEX_NOT_FOUND for fromIndex < 0").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when fromIndex equals length")
        void lastIndexOf_ShouldReturnIndexNotFound_WhenFromIndexEqualsLength() {
            // given
            byte[] source = {1};

            // when
            int result = ArrayUtils.lastIndexOf(source, (byte) 1, 1);

            // then
            then(result).as("INDEX_NOT_FOUND for fromIndex >= length").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when source is null with fromIndex")
        void lastIndexOf_ShouldReturnIndexNotFound_WhenSourceIsNullWithFromIndex() {
            // given

            // when
            int result = ArrayUtils.lastIndexOf((byte[]) null, (byte) 1, 0);

            // then
            then(result).as("INDEX_NOT_FOUND for null source with fromIndex").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }
    }

    @Nested
    class LastIndexOfCharTests {

        @Test
        @DisplayName("Should return last index when element found")
        void lastIndexOf_ShouldReturnLastIndex_WhenElementFound() {
            // given
            char[] source = {'a', 'b', 'a'};

            // when
            int result = ArrayUtils.lastIndexOf(source, 'a');

            // then
            then(result).as("Last occurrence of a is at index 2").isEqualTo(2);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when not found")
        void lastIndexOf_ShouldReturnIndexNotFound_WhenElementNotFound() {
            // given
            char[] source = {'b'};

            // when
            int result = ArrayUtils.lastIndexOf(source, 'a');

            // then
            then(result).as("Element not found returns -1").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when source is null")
        void lastIndexOf_ShouldReturnIndexNotFound_WhenSourceIsNull() {
            // given

            // when
            int result = ArrayUtils.lastIndexOf((char[]) null, (char) 'a');

            // then
            then(result).as("INDEX_NOT_FOUND for null source").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when fromIndex is negative")
        void lastIndexOf_ShouldReturnIndexNotFound_WhenFromIndexIsNegative() {
            // given
            char[] source = {'a'};

            // when
            int result = ArrayUtils.lastIndexOf(source, (char) 'a', -1);

            // then
            then(result).as("INDEX_NOT_FOUND for fromIndex < 0").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when fromIndex equals length")
        void lastIndexOf_ShouldReturnIndexNotFound_WhenFromIndexEqualsLength() {
            // given
            char[] source = {'a'};

            // when
            int result = ArrayUtils.lastIndexOf(source, (char) 'a', 1);

            // then
            then(result).as("INDEX_NOT_FOUND for fromIndex >= length").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when source is null with fromIndex")
        void lastIndexOf_ShouldReturnIndexNotFound_WhenSourceIsNullWithFromIndex() {
            // given

            // when
            int result = ArrayUtils.lastIndexOf((char[]) null, 'a', 0);

            // then
            then(result).as("INDEX_NOT_FOUND for null source with fromIndex").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }
    }

    @Nested
    class LastIndexOfShortTests {

        @Test
        @DisplayName("Should return last index when element found")
        void lastIndexOf_ShouldReturnLastIndex_WhenElementFound() {
            // given
            short[] source = {1, 2, 1};

            // when
            int result = ArrayUtils.lastIndexOf(source, (short) 1);

            // then
            then(result).as("Last occurrence of 1 is at index 2").isEqualTo(2);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when not found")
        void lastIndexOf_ShouldReturnIndexNotFound_WhenElementNotFound() {
            // given
            short[] source = {2};

            // when
            int result = ArrayUtils.lastIndexOf(source, (short) 1);

            // then
            then(result).as("Element not found returns -1").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when source is null")
        void lastIndexOf_ShouldReturnIndexNotFound_WhenSourceIsNull() {
            // given

            // when
            int result = ArrayUtils.lastIndexOf((short[]) null, (short) 1);

            // then
            then(result).as("INDEX_NOT_FOUND for null source").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when fromIndex is negative")
        void lastIndexOf_ShouldReturnIndexNotFound_WhenFromIndexIsNegative() {
            // given
            short[] source = {1};

            // when
            int result = ArrayUtils.lastIndexOf(source, (short) 1, -1);

            // then
            then(result).as("INDEX_NOT_FOUND for fromIndex < 0").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when fromIndex equals length")
        void lastIndexOf_ShouldReturnIndexNotFound_WhenFromIndexEqualsLength() {
            // given
            short[] source = {1};

            // when
            int result = ArrayUtils.lastIndexOf(source, (short) 1, 1);

            // then
            then(result).as("INDEX_NOT_FOUND for fromIndex >= length").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when source is null with fromIndex")
        void lastIndexOf_ShouldReturnIndexNotFound_WhenSourceIsNullWithFromIndex() {
            // given

            // when
            int result = ArrayUtils.lastIndexOf((short[]) null, (short) 1, 0);

            // then
            then(result).as("INDEX_NOT_FOUND for null source with fromIndex").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }
    }

    @Nested
    class LastIndexOfIntTests {

        @Test
        @DisplayName("Should return last index when element found")
        void lastIndexOf_ShouldReturnLastIndex_WhenElementFound() {
            // given
            int[] source = {1, 2, 1};

            // when
            int result = ArrayUtils.lastIndexOf(source, 1);

            // then
            then(result).as("Last occurrence of 1 is at index 2").isEqualTo(2);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when not found")
        void lastIndexOf_ShouldReturnIndexNotFound_WhenElementNotFound() {
            // given
            int[] source = {2};

            // when
            int result = ArrayUtils.lastIndexOf(source, 1);

            // then
            then(result).as("Element not found returns -1").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when source is null")
        void lastIndexOf_ShouldReturnIndexNotFound_WhenSourceIsNull() {
            // given

            // when
            int result = ArrayUtils.lastIndexOf((int[]) null, (int) 1);

            // then
            then(result).as("INDEX_NOT_FOUND for null source").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when fromIndex is negative")
        void lastIndexOf_ShouldReturnIndexNotFound_WhenFromIndexIsNegative() {
            // given
            int[] source = {1};

            // when
            int result = ArrayUtils.lastIndexOf(source, (int) 1, -1);

            // then
            then(result).as("INDEX_NOT_FOUND for fromIndex < 0").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when fromIndex equals length")
        void lastIndexOf_ShouldReturnIndexNotFound_WhenFromIndexEqualsLength() {
            // given
            int[] source = {1};

            // when
            int result = ArrayUtils.lastIndexOf(source, (int) 1, 1);

            // then
            then(result).as("INDEX_NOT_FOUND for fromIndex >= length").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when source is null with fromIndex")
        void lastIndexOf_ShouldReturnIndexNotFound_WhenSourceIsNullWithFromIndex() {
            // given

            // when
            int result = ArrayUtils.lastIndexOf((int[]) null, 1, 0);

            // then
            then(result).as("INDEX_NOT_FOUND for null source with fromIndex").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }
    }

    @Nested
    class LastIndexOfLongTests {

        @Test
        @DisplayName("Should return last index when element found")
        void lastIndexOf_ShouldReturnLastIndex_WhenElementFound() {
            // given
            long[] source = {1L, 2L, 1L};

            // when
            int result = ArrayUtils.lastIndexOf(source, 1L);

            // then
            then(result).as("Last occurrence of 1 is at index 2").isEqualTo(2);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when not found")
        void lastIndexOf_ShouldReturnIndexNotFound_WhenElementNotFound() {
            // given
            long[] source = {2L};

            // when
            int result = ArrayUtils.lastIndexOf(source, 1L);

            // then
            then(result).as("Element not found returns -1").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when source is null")
        void lastIndexOf_ShouldReturnIndexNotFound_WhenSourceIsNull() {
            // given

            // when
            int result = ArrayUtils.lastIndexOf((long[]) null, (long) 1L);

            // then
            then(result).as("INDEX_NOT_FOUND for null source").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when fromIndex is negative")
        void lastIndexOf_ShouldReturnIndexNotFound_WhenFromIndexIsNegative() {
            // given
            long[] source = {1L};

            // when
            int result = ArrayUtils.lastIndexOf(source, (long) 1L, -1);

            // then
            then(result).as("INDEX_NOT_FOUND for fromIndex < 0").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when fromIndex equals length")
        void lastIndexOf_ShouldReturnIndexNotFound_WhenFromIndexEqualsLength() {
            // given
            long[] source = {1L};

            // when
            int result = ArrayUtils.lastIndexOf(source, (long) 1L, 1);

            // then
            then(result).as("INDEX_NOT_FOUND for fromIndex >= length").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when source is null with fromIndex")
        void lastIndexOf_ShouldReturnIndexNotFound_WhenSourceIsNullWithFromIndex() {
            // given

            // when
            int result = ArrayUtils.lastIndexOf((long[]) null, 1L, 0);

            // then
            then(result).as("INDEX_NOT_FOUND for null source with fromIndex").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }
    }

    @Nested
    class LastIndexOfFloatTests {

        @Test
        @DisplayName("Should return last index when element found")
        void lastIndexOf_ShouldReturnLastIndex_WhenElementFound() {
            // given
            float[] source = {1.0f, 2.0f, 1.0f};

            // when
            int result = ArrayUtils.lastIndexOf(source, 1.0f);

            // then
            then(result).as("Last occurrence of 1.0f is at index 2").isEqualTo(2);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when not found")
        void lastIndexOf_ShouldReturnIndexNotFound_WhenElementNotFound() {
            // given
            float[] source = {2.0f};

            // when
            int result = ArrayUtils.lastIndexOf(source, 1.0f);

            // then
            then(result).as("Element not found returns -1").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when source is null")
        void lastIndexOf_ShouldReturnIndexNotFound_WhenSourceIsNull() {
            // given

            // when
            int result = ArrayUtils.lastIndexOf((float[]) null, (float) 1.0f);

            // then
            then(result).as("INDEX_NOT_FOUND for null source").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when fromIndex is negative")
        void lastIndexOf_ShouldReturnIndexNotFound_WhenFromIndexIsNegative() {
            // given
            float[] source = {1.0f};

            // when
            int result = ArrayUtils.lastIndexOf(source, (float) 1.0f, -1);

            // then
            then(result).as("INDEX_NOT_FOUND for fromIndex < 0").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when fromIndex equals length")
        void lastIndexOf_ShouldReturnIndexNotFound_WhenFromIndexEqualsLength() {
            // given
            float[] source = {1.0f};

            // when
            int result = ArrayUtils.lastIndexOf(source, (float) 1.0f, 1);

            // then
            then(result).as("INDEX_NOT_FOUND for fromIndex >= length").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when source is null with fromIndex")
        void lastIndexOf_ShouldReturnIndexNotFound_WhenSourceIsNullWithFromIndex() {
            // given

            // when
            int result = ArrayUtils.lastIndexOf((float[]) null, 1.0f, 0);

            // then
            then(result).as("INDEX_NOT_FOUND for null source with fromIndex").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }
    }

    @Nested
    class LastIndexOfDoubleTests {

        @Test
        @DisplayName("Should return last index when element found")
        void lastIndexOf_ShouldReturnLastIndex_WhenElementFound() {
            // given
            double[] source = {1.0, 2.0, 1.0};

            // when
            int result = ArrayUtils.lastIndexOf(source, 1.0);

            // then
            then(result).as("Last occurrence of 1.0 is at index 2").isEqualTo(2);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when not found")
        void lastIndexOf_ShouldReturnIndexNotFound_WhenElementNotFound() {
            // given
            double[] source = {2.0};

            // when
            int result = ArrayUtils.lastIndexOf(source, 1.0);

            // then
            then(result).as("Element not found returns -1").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when source is null")
        void lastIndexOf_ShouldReturnIndexNotFound_WhenSourceIsNull() {
            // given

            // when
            int result = ArrayUtils.lastIndexOf((double[]) null, (double) 1.0);

            // then
            then(result).as("INDEX_NOT_FOUND for null source").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when fromIndex is negative")
        void lastIndexOf_ShouldReturnIndexNotFound_WhenFromIndexIsNegative() {
            // given
            double[] source = {1.0};

            // when
            int result = ArrayUtils.lastIndexOf(source, (double) 1.0, -1);

            // then
            then(result).as("INDEX_NOT_FOUND for fromIndex < 0").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when fromIndex equals length")
        void lastIndexOf_ShouldReturnIndexNotFound_WhenFromIndexEqualsLength() {
            // given
            double[] source = {1.0};

            // when
            int result = ArrayUtils.lastIndexOf(source, (double) 1.0, 1);

            // then
            then(result).as("INDEX_NOT_FOUND for fromIndex >= length").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when source is null with fromIndex")
        void lastIndexOf_ShouldReturnIndexNotFound_WhenSourceIsNullWithFromIndex() {
            // given

            // when
            int result = ArrayUtils.lastIndexOf((double[]) null, 1.0, 0);

            // then
            then(result).as("INDEX_NOT_FOUND for null source with fromIndex").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }
    }

    @Nested
    class LastIndexOfObjectTests {

        @Test
        @DisplayName("Should return last index when element found")
        void lastIndexOf_ShouldReturnLastIndex_WhenElementFound() {
            // given
            String[] source = {"a", "b", "a"};

            // when
            int result = ArrayUtils.lastIndexOf(source, "a");

            // then
            then(result).as("Last occurrence of a is at index 2").isEqualTo(2);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when not found")
        void lastIndexOf_ShouldReturnIndexNotFound_WhenElementNotFound() {
            // given
            String[] source = {"b"};

            // when
            int result = ArrayUtils.lastIndexOf(source, "a");

            // then
            then(result).as("Element not found returns -1").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should find last null element")
        void lastIndexOf_ShouldFindLastNull_WhenNullPresent() {
            // given
            String[] source = {null, "a", null};

            // when
            int result = ArrayUtils.lastIndexOf(source, null);

            // then
            then(result).as("Last null at index 2").isEqualTo(2);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when source is null")
        void lastIndexOf_ShouldReturnIndexNotFound_WhenSourceIsNull() {
            // given

            // when
            int result = ArrayUtils.lastIndexOf((String[]) null, "a");

            // then
            then(result).as("INDEX_NOT_FOUND for null source").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when fromIndex is negative")
        void lastIndexOf_ShouldReturnIndexNotFound_WhenFromIndexIsNegative() {
            // given
            String[] source = {"a"};

            // when
            int result = ArrayUtils.lastIndexOf(source, "a", -1);

            // then
            then(result).as("INDEX_NOT_FOUND for fromIndex < 0").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when fromIndex equals length")
        void lastIndexOf_ShouldReturnIndexNotFound_WhenFromIndexEqualsLength() {
            // given
            String[] source = {"a"};

            // when
            int result = ArrayUtils.lastIndexOf(source, "a", 1);

            // then
            then(result).as("INDEX_NOT_FOUND for fromIndex >= length").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when null element not found")
        void lastIndexOf_ShouldReturnIndexNotFound_WhenNullElementNotFound() {
            // given
            String[] source = {"a", "b"};

            // when
            int result = ArrayUtils.lastIndexOf(source, null);

            // then
            then(result).as("INDEX_NOT_FOUND for null element not found").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when type incompatible")
        void lastIndexOf_ShouldReturnIndexNotFound_WhenTypeIncompatible() {
            // given
            String[] source = {"a"};

            // when
            int result = ArrayUtils.lastIndexOf((Object[]) source, (Object) 1);

            // then
            then(result).as("INDEX_NOT_FOUND for incompatible type").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Should return INDEX_NOT_FOUND when source is null with fromIndex")
        void lastIndexOf_ShouldReturnIndexNotFound_WhenSourceIsNullWithFromIndex() {
            // given

            // when
            int result = ArrayUtils.lastIndexOf((String[]) null, "a", 0);

            // then
            then(result).as("INDEX_NOT_FOUND for null source with fromIndex").isEqualTo(ArrayUtils.INDEX_NOT_FOUND);
        }
    }

    @Nested
    class LastIndexesOfBooleanTests {

        @Test
        @DisplayName("Should return BitSet with last indices")
        void lastIndexesOf_ShouldReturnBitSetWithIndices_WhenFound() {
            // given
            boolean[] source = {true, false, true};

            // when
            BitSet result = ArrayUtils.lastIndexesOf(source, true);

            // then
            then(result.cardinality()).as("BitSet with last indices").isEqualTo(2);
        }

        @Test
        @DisplayName("Should return empty BitSet when not found")
        void lastIndexesOf_ShouldReturnEmptyBitSet_WhenNotFound() {
            // given
            boolean[] source = {false};

            // when
            BitSet result = ArrayUtils.lastIndexesOf(source, true);

            // then
            then(result.isEmpty()).as("Empty BitSet when not found").isTrue();
        }

        @Test
        @DisplayName("Should find element at index zero when fromIndex is zero")
        void lastIndexesOf_ShouldFindElementAtIndexZero_WhenFromIndexIsZero() {
            // given
            boolean[] source = {true, true};

            // when
            BitSet result = ArrayUtils.lastIndexesOf(source, true, 0);

            // then
            then(result.cardinality()).as("Exactly one index found when scanning from zero").isEqualTo(1);
            then(result.get(0)).as("Index zero found").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when fromIndex is negative")
        void lastIndexesOf_ShouldReturnEmptyBitSet_WhenFromIndexIsNegative() {
            // given
            boolean[] source = {true, true};

            // when
            BitSet result = ArrayUtils.lastIndexesOf(source, true, -1);

            // then
            then(result.isEmpty()).as("Empty BitSet for negative fromIndex").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when source is null")
        void lastIndexesOf_ShouldReturnEmptyBitSet_WhenSourceIsNull() {
            // given

            // when
            BitSet result = ArrayUtils.lastIndexesOf((boolean[]) null, true);

            // then
            then(result.isEmpty()).as("Empty BitSet for null source").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when source is null")
        void lastIndexesOf_ShouldReturnEmptyBitSet_WhenSourceIsNullWithFromIndex() {
            // given

            // when
            BitSet result = ArrayUtils.lastIndexesOf((boolean[]) null, true, 0);

            // then
            then(result.isEmpty()).as("Empty BitSet for null source").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when fromIndex exceeds length")
        void lastIndexesOf_ShouldReturnEmptyBitSet_WhenFromIndexExceedsLength() {
            // given
            boolean[] source = {true, true};

            // when
            BitSet result = ArrayUtils.lastIndexesOf(source, true, 3);

            // then
            then(result.isEmpty()).as("Empty BitSet for fromIndex > length").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when element not found in valid range")
        void lastIndexesOf_ShouldReturnEmptyBitSet_WhenElementNotFoundInRange() {
            // given
            boolean[] source = {false, false, false};

            // when
            BitSet result = ArrayUtils.lastIndexesOf(source, true, 2);

            // then
            then(result.isEmpty()).as("Empty BitSet when not found in range").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when fromIndex equals length")
        void indexesOf_ShouldReturnEmptyBitSet_WhenFromIndexEqualsLength() {
            // given
            boolean[] source = {true};

            // when
            BitSet result = ArrayUtils.indexesOf(source, true, 1);

            // then
            then(result.isEmpty()).as("Empty BitSet for fromIndex == length").isTrue();
        }
    }

    @Nested
    class LastIndexesOfByteTests {

        @Test
        @DisplayName("Should return BitSet with last indices")
        void lastIndexesOf_ShouldReturnBitSetWithIndices_WhenFound() {
            // given
            byte[] source = {1, 2, 1};

            // when
            BitSet result = ArrayUtils.lastIndexesOf(source, (byte) 1);

            // then
            then(result.cardinality()).as("BitSet with last indices").isEqualTo(2);
        }

        @Test
        @DisplayName("Should return empty BitSet when not found")
        void lastIndexesOf_ShouldReturnEmptyBitSet_WhenNotFound() {
            // given
            byte[] source = {2};

            // when
            BitSet result = ArrayUtils.lastIndexesOf(source, (byte) 1);

            // then
            then(result.isEmpty()).as("Empty BitSet when not found").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when source is null")
        void lastIndexesOf_ShouldReturnEmptyBitSet_WhenSourceIsNull() {
            // given

            // when
            BitSet result = ArrayUtils.lastIndexesOf((byte[]) null, (byte) 1);

            // then
            then(result.isEmpty()).as("Empty BitSet for null source").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when source is null")
        void lastIndexesOf_ShouldReturnEmptyBitSet_WhenSourceIsNullWithFromIndex() {
            // given

            // when
            BitSet result = ArrayUtils.lastIndexesOf((byte[]) null, (byte) 1, 0);

            // then
            then(result.isEmpty()).as("Empty BitSet for null source").isTrue();
        }

        @Test
        @DisplayName("Should find element at index zero when fromIndex is zero")
        void lastIndexesOf_ShouldFindElementAtIndexZero_WhenFromIndexIsZero() {
            // given
            byte[] source = {1, 2, 1};

            // when
            BitSet result = ArrayUtils.lastIndexesOf(source, (byte) 1, 0);

            // then
            then(result.cardinality()).as("Exactly one index found when scanning from zero").isEqualTo(1);
            then(result.get(0)).as("Index zero found").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when fromIndex is negative")
        void lastIndexesOf_ShouldReturnEmptyBitSet_WhenFromIndexIsNegative() {
            // given
            byte[] source = {1, 2, 1};

            // when
            BitSet result = ArrayUtils.lastIndexesOf(source, (byte) 1, -1);

            // then
            then(result.isEmpty()).as("Empty BitSet for negative fromIndex").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when fromIndex exceeds length")
        void lastIndexesOf_ShouldReturnEmptyBitSet_WhenFromIndexExceedsLength() {
            // given
            byte[] source = {1, 2, 1};

            // when
            BitSet result = ArrayUtils.lastIndexesOf(source, (byte) 1, 4);

            // then
            then(result.isEmpty()).as("Empty BitSet for fromIndex > length").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when element not found in valid range")
        void lastIndexesOf_ShouldReturnEmptyBitSet_WhenElementNotFoundInRange() {
            // given
            byte[] source = {2, 2, 2};

            // when
            BitSet result = ArrayUtils.lastIndexesOf(source, (byte) 1, 2);

            // then
            then(result.isEmpty()).as("Empty BitSet when not found in range").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when fromIndex equals length")
        void indexesOf_ShouldReturnEmptyBitSet_WhenFromIndexEqualsLength() {
            // given
            byte[] source = {1};

            // when
            BitSet result = ArrayUtils.indexesOf(source, (byte) 1, 1);

            // then
            then(result.isEmpty()).as("Empty BitSet for fromIndex == length").isTrue();
        }
    }

    @Nested
    class LastIndexesOfCharTests {

        @Test
        @DisplayName("Should return BitSet with last indices")
        void lastIndexesOf_ShouldReturnBitSetWithIndices_WhenFound() {
            // given
            char[] source = {'a', 'b', 'a'};

            // when
            BitSet result = ArrayUtils.lastIndexesOf(source, 'a');

            // then
            then(result.cardinality()).as("BitSet with last indices").isEqualTo(2);
        }

        @Test
        @DisplayName("Should return empty BitSet when not found")
        void lastIndexesOf_ShouldReturnEmptyBitSet_WhenNotFound() {
            // given
            char[] source = {'b'};

            // when
            BitSet result = ArrayUtils.lastIndexesOf(source, 'a');

            // then
            then(result.isEmpty()).as("Empty BitSet when not found").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when source is null")
        void lastIndexesOf_ShouldReturnEmptyBitSet_WhenSourceIsNull() {
            // given

            // when
            BitSet result = ArrayUtils.lastIndexesOf((char[]) null, 'a');

            // then
            then(result.isEmpty()).as("Empty BitSet for null source").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when source is null")
        void lastIndexesOf_ShouldReturnEmptyBitSet_WhenSourceIsNullWithFromIndex() {
            // given

            // when
            BitSet result = ArrayUtils.lastIndexesOf((char[]) null, (char) 'a', 0);

            // then
            then(result.isEmpty()).as("Empty BitSet for null source").isTrue();
        }

        @Test
        @DisplayName("Should find element at index zero when fromIndex is zero")
        void lastIndexesOf_ShouldFindElementAtIndexZero_WhenFromIndexIsZero() {
            // given
            char[] source = {'a', 'b', 'a'};

            // when
            BitSet result = ArrayUtils.lastIndexesOf(source, (char) 'a', 0);

            // then
            then(result.cardinality()).as("Exactly one index found when scanning from zero").isEqualTo(1);
            then(result.get(0)).as("Index zero found").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when fromIndex is negative")
        void lastIndexesOf_ShouldReturnEmptyBitSet_WhenFromIndexIsNegative() {
            // given
            char[] source = {'a', 'b', 'a'};

            // when
            BitSet result = ArrayUtils.lastIndexesOf(source, (char) 'a', -1);

            // then
            then(result.isEmpty()).as("Empty BitSet for negative fromIndex").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when fromIndex exceeds length")
        void lastIndexesOf_ShouldReturnEmptyBitSet_WhenFromIndexExceedsLength() {
            // given
            char[] source = {'a', 'b', 'a'};

            // when
            BitSet result = ArrayUtils.lastIndexesOf(source, (char) 'a', 4);

            // then
            then(result.isEmpty()).as("Empty BitSet for fromIndex > length").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when element not found in valid range")
        void lastIndexesOf_ShouldReturnEmptyBitSet_WhenElementNotFoundInRange() {
            // given
            char[] source = {'b', 'b', 'b'};

            // when
            BitSet result = ArrayUtils.lastIndexesOf(source, 'a', 2);

            // then
            then(result.isEmpty()).as("Empty BitSet when not found in range").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when fromIndex equals length")
        void indexesOf_ShouldReturnEmptyBitSet_WhenFromIndexEqualsLength() {
            // given
            char[] source = {'a'};

            // when
            BitSet result = ArrayUtils.indexesOf(source, 'a', 1);

            // then
            then(result.isEmpty()).as("Empty BitSet for fromIndex == length").isTrue();
        }
    }

    @Nested
    class LastIndexesOfShortTests {

        @Test
        @DisplayName("Should return BitSet with last indices")
        void lastIndexesOf_ShouldReturnBitSetWithIndices_WhenFound() {
            // given
            short[] source = {1, 2, 1};

            // when
            BitSet result = ArrayUtils.lastIndexesOf(source, (short) 1);

            // then
            then(result.cardinality()).as("BitSet with last indices").isEqualTo(2);
        }

        @Test
        @DisplayName("Should return empty BitSet when not found")
        void lastIndexesOf_ShouldReturnEmptyBitSet_WhenNotFound() {
            // given
            short[] source = {2};

            // when
            BitSet result = ArrayUtils.lastIndexesOf(source, (short) 1);

            // then
            then(result.isEmpty()).as("Empty BitSet when not found").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when source is null")
        void lastIndexesOf_ShouldReturnEmptyBitSet_WhenSourceIsNull() {
            // given

            // when
            BitSet result = ArrayUtils.lastIndexesOf((short[]) null, (short) 1);

            // then
            then(result.isEmpty()).as("Empty BitSet for null source").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when source is null")
        void lastIndexesOf_ShouldReturnEmptyBitSet_WhenSourceIsNullWithFromIndex() {
            // given

            // when
            BitSet result = ArrayUtils.lastIndexesOf((short[]) null, (short) 1, 0);

            // then
            then(result.isEmpty()).as("Empty BitSet for null source").isTrue();
        }

        @Test
        @DisplayName("Should find element at index zero when fromIndex is zero")
        void lastIndexesOf_ShouldFindElementAtIndexZero_WhenFromIndexIsZero() {
            // given
            short[] source = {1, 2, 1};

            // when
            BitSet result = ArrayUtils.lastIndexesOf(source, (short) 1, 0);

            // then
            then(result.cardinality()).as("Exactly one index found when scanning from zero").isEqualTo(1);
            then(result.get(0)).as("Index zero found").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when fromIndex is negative")
        void lastIndexesOf_ShouldReturnEmptyBitSet_WhenFromIndexIsNegative() {
            // given
            short[] source = {1, 2, 1};

            // when
            BitSet result = ArrayUtils.lastIndexesOf(source, (short) 1, -1);

            // then
            then(result.isEmpty()).as("Empty BitSet for negative fromIndex").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when fromIndex exceeds length")
        void lastIndexesOf_ShouldReturnEmptyBitSet_WhenFromIndexExceedsLength() {
            // given
            short[] source = {1, 2, 1};

            // when
            BitSet result = ArrayUtils.lastIndexesOf(source, (short) 1, 4);

            // then
            then(result.isEmpty()).as("Empty BitSet for fromIndex > length").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when element not found in valid range")
        void lastIndexesOf_ShouldReturnEmptyBitSet_WhenElementNotFoundInRange() {
            // given
            short[] source = {2, 2, 2};

            // when
            BitSet result = ArrayUtils.lastIndexesOf(source, (short) 1, 2);

            // then
            then(result.isEmpty()).as("Empty BitSet when not found in range").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when fromIndex equals length")
        void indexesOf_ShouldReturnEmptyBitSet_WhenFromIndexEqualsLength() {
            // given
            short[] source = {1};

            // when
            BitSet result = ArrayUtils.indexesOf(source, (short) 1, 1);

            // then
            then(result.isEmpty()).as("Empty BitSet for fromIndex == length").isTrue();
        }
    }

    @Nested
    class LastIndexesOfIntTests {

        @Test
        @DisplayName("Should return BitSet with last indices")
        void lastIndexesOf_ShouldReturnBitSetWithIndices_WhenFound() {
            // given
            int[] source = {1, 2, 1};

            // when
            BitSet result = ArrayUtils.lastIndexesOf(source, 1);

            // then
            then(result.cardinality()).as("BitSet with last indices").isEqualTo(2);
        }

        @Test
        @DisplayName("Should return empty BitSet when not found")
        void lastIndexesOf_ShouldReturnEmptyBitSet_WhenNotFound() {
            // given
            int[] source = {2};

            // when
            BitSet result = ArrayUtils.lastIndexesOf(source, 1);

            // then
            then(result.isEmpty()).as("Empty BitSet when not found").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when source is null")
        void lastIndexesOf_ShouldReturnEmptyBitSet_WhenSourceIsNull() {
            // given

            // when
            BitSet result = ArrayUtils.lastIndexesOf((int[]) null, 1);

            // then
            then(result.isEmpty()).as("Empty BitSet for null source").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when source is null")
        void lastIndexesOf_ShouldReturnEmptyBitSet_WhenSourceIsNullWithFromIndex() {
            // given

            // when
            BitSet result = ArrayUtils.lastIndexesOf((int[]) null, (int) 1, 0);

            // then
            then(result.isEmpty()).as("Empty BitSet for null source").isTrue();
        }

        @Test
        @DisplayName("Should find element at index zero when fromIndex is zero")
        void lastIndexesOf_ShouldFindElementAtIndexZero_WhenFromIndexIsZero() {
            // given
            int[] source = {1, 2, 1};

            // when
            BitSet result = ArrayUtils.lastIndexesOf(source, (int) 1, 0);

            // then
            then(result.cardinality()).as("Exactly one index found when scanning from zero").isEqualTo(1);
            then(result.get(0)).as("Index zero found").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when fromIndex is negative")
        void lastIndexesOf_ShouldReturnEmptyBitSet_WhenFromIndexIsNegative() {
            // given
            int[] source = {1, 2, 1};

            // when
            BitSet result = ArrayUtils.lastIndexesOf(source, (int) 1, -1);

            // then
            then(result.isEmpty()).as("Empty BitSet for negative fromIndex").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when fromIndex exceeds length")
        void lastIndexesOf_ShouldReturnEmptyBitSet_WhenFromIndexExceedsLength() {
            // given
            int[] source = {1, 2, 1};

            // when
            BitSet result = ArrayUtils.lastIndexesOf(source, (int) 1, 4);

            // then
            then(result.isEmpty()).as("Empty BitSet for fromIndex > length").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when element not found in valid range")
        void lastIndexesOf_ShouldReturnEmptyBitSet_WhenElementNotFoundInRange() {
            // given
            int[] source = {2, 2, 2};

            // when
            BitSet result = ArrayUtils.lastIndexesOf(source, 1, 2);

            // then
            then(result.isEmpty()).as("Empty BitSet when not found in range").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when fromIndex equals length")
        void indexesOf_ShouldReturnEmptyBitSet_WhenFromIndexEqualsLength() {
            // given
            int[] source = {1};

            // when
            BitSet result = ArrayUtils.indexesOf(source, 1, 1);

            // then
            then(result.isEmpty()).as("Empty BitSet for fromIndex == length").isTrue();
        }
    }

    @Nested
    class LastIndexesOfLongTests {

        @Test
        @DisplayName("Should return BitSet with last indices")
        void lastIndexesOf_ShouldReturnBitSetWithIndices_WhenFound() {
            // given
            long[] source = {1L, 2L, 1L};

            // when
            BitSet result = ArrayUtils.lastIndexesOf(source, 1L);

            // then
            then(result.cardinality()).as("BitSet with last indices").isEqualTo(2);
        }

        @Test
        @DisplayName("Should return empty BitSet when not found")
        void lastIndexesOf_ShouldReturnEmptyBitSet_WhenNotFound() {
            // given
            long[] source = {2L};

            // when
            BitSet result = ArrayUtils.lastIndexesOf(source, 1L);

            // then
            then(result.isEmpty()).as("Empty BitSet when not found").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when source is null")
        void lastIndexesOf_ShouldReturnEmptyBitSet_WhenSourceIsNull() {
            // given

            // when
            BitSet result = ArrayUtils.lastIndexesOf((long[]) null, 1L);

            // then
            then(result.isEmpty()).as("Empty BitSet for null source").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when source is null")
        void lastIndexesOf_ShouldReturnEmptyBitSet_WhenSourceIsNullWithFromIndex() {
            // given

            // when
            BitSet result = ArrayUtils.lastIndexesOf((long[]) null, (long) 1L, 0);

            // then
            then(result.isEmpty()).as("Empty BitSet for null source").isTrue();
        }

        @Test
        @DisplayName("Should find element at index zero when fromIndex is zero")
        void lastIndexesOf_ShouldFindElementAtIndexZero_WhenFromIndexIsZero() {
            // given
            long[] source = {1L, 2L, 1L};

            // when
            BitSet result = ArrayUtils.lastIndexesOf(source, (long) 1L, 0);

            // then
            then(result.cardinality()).as("Exactly one index found when scanning from zero").isEqualTo(1);
            then(result.get(0)).as("Index zero found").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when fromIndex is negative")
        void lastIndexesOf_ShouldReturnEmptyBitSet_WhenFromIndexIsNegative() {
            // given
            long[] source = {1L, 2L, 1L};

            // when
            BitSet result = ArrayUtils.lastIndexesOf(source, (long) 1L, -1);

            // then
            then(result.isEmpty()).as("Empty BitSet for negative fromIndex").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when fromIndex exceeds length")
        void lastIndexesOf_ShouldReturnEmptyBitSet_WhenFromIndexExceedsLength() {
            // given
            long[] source = {1L, 2L, 1L};

            // when
            BitSet result = ArrayUtils.lastIndexesOf(source, (long) 1L, 4);

            // then
            then(result.isEmpty()).as("Empty BitSet for fromIndex > length").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when element not found in valid range")
        void lastIndexesOf_ShouldReturnEmptyBitSet_WhenElementNotFoundInRange() {
            // given
            long[] source = {2L, 2L, 2L};

            // when
            BitSet result = ArrayUtils.lastIndexesOf(source, 1L, 2);

            // then
            then(result.isEmpty()).as("Empty BitSet when not found in range").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when fromIndex equals length")
        void indexesOf_ShouldReturnEmptyBitSet_WhenFromIndexEqualsLength() {
            // given
            long[] source = {1L};

            // when
            BitSet result = ArrayUtils.indexesOf(source, 1L, 1);

            // then
            then(result.isEmpty()).as("Empty BitSet for fromIndex == length").isTrue();
        }
    }

    @Nested
    class LastIndexesOfFloatTests {

        @Test
        @DisplayName("Should return BitSet with last indices")
        void lastIndexesOf_ShouldReturnBitSetWithIndices_WhenFound() {
            // given
            float[] source = {1.0f, 2.0f, 1.0f};

            // when
            BitSet result = ArrayUtils.lastIndexesOf(source, 1.0f);

            // then
            then(result.cardinality()).as("BitSet with last indices").isEqualTo(2);
        }

        @Test
        @DisplayName("Should return empty BitSet when not found")
        void lastIndexesOf_ShouldReturnEmptyBitSet_WhenNotFound() {
            // given
            float[] source = {2.0f};

            // when
            BitSet result = ArrayUtils.lastIndexesOf(source, 1.0f);

            // then
            then(result.isEmpty()).as("Empty BitSet when not found").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when source is null")
        void lastIndexesOf_ShouldReturnEmptyBitSet_WhenSourceIsNull() {
            // given

            // when
            BitSet result = ArrayUtils.lastIndexesOf((float[]) null, 1.0f);

            // then
            then(result.isEmpty()).as("Empty BitSet for null source").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when source is null")
        void lastIndexesOf_ShouldReturnEmptyBitSet_WhenSourceIsNullWithFromIndex() {
            // given

            // when
            BitSet result = ArrayUtils.lastIndexesOf((float[]) null, (float) 1.0f, 0);

            // then
            then(result.isEmpty()).as("Empty BitSet for null source").isTrue();
        }

        @Test
        @DisplayName("Should find element at index zero when fromIndex is zero")
        void lastIndexesOf_ShouldFindElementAtIndexZero_WhenFromIndexIsZero() {
            // given
            float[] source = {1.0f, 2.0f, 1.0f};

            // when
            BitSet result = ArrayUtils.lastIndexesOf(source, (float) 1.0f, 0);

            // then
            then(result.cardinality()).as("Exactly one index found when scanning from zero").isEqualTo(1);
            then(result.get(0)).as("Index zero found").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when fromIndex is negative")
        void lastIndexesOf_ShouldReturnEmptyBitSet_WhenFromIndexIsNegative() {
            // given
            float[] source = {1.0f, 2.0f, 1.0f};

            // when
            BitSet result = ArrayUtils.lastIndexesOf(source, (float) 1.0f, -1);

            // then
            then(result.isEmpty()).as("Empty BitSet for negative fromIndex").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when fromIndex exceeds length")
        void lastIndexesOf_ShouldReturnEmptyBitSet_WhenFromIndexExceedsLength() {
            // given
            float[] source = {1.0f, 2.0f, 1.0f};

            // when
            BitSet result = ArrayUtils.lastIndexesOf(source, (float) 1.0f, 4);

            // then
            then(result.isEmpty()).as("Empty BitSet for fromIndex > length").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when element not found in valid range")
        void lastIndexesOf_ShouldReturnEmptyBitSet_WhenElementNotFoundInRange() {
            // given
            float[] source = {2.0f, 2.0f, 2.0f};

            // when
            BitSet result = ArrayUtils.lastIndexesOf(source, 1.0f, 2);

            // then
            then(result.isEmpty()).as("Empty BitSet when not found in range").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when fromIndex equals length")
        void indexesOf_ShouldReturnEmptyBitSet_WhenFromIndexEqualsLength() {
            // given
            float[] source = {1.0f};

            // when
            BitSet result = ArrayUtils.indexesOf(source, 1.0f, 1);

            // then
            then(result.isEmpty()).as("Empty BitSet for fromIndex == length").isTrue();
        }
    }

    @Nested
    class LastIndexesOfDoubleTests {

        @Test
        @DisplayName("Should return BitSet with last indices")
        void lastIndexesOf_ShouldReturnBitSetWithIndices_WhenFound() {
            // given
            double[] source = {1.0, 2.0, 1.0};

            // when
            BitSet result = ArrayUtils.lastIndexesOf(source, 1.0);

            // then
            then(result.cardinality()).as("BitSet with last indices").isEqualTo(2);
        }

        @Test
        @DisplayName("Should return empty BitSet when not found")
        void lastIndexesOf_ShouldReturnEmptyBitSet_WhenNotFound() {
            // given
            double[] source = {2.0};

            // when
            BitSet result = ArrayUtils.lastIndexesOf(source, 1.0);

            // then
            then(result.isEmpty()).as("Empty BitSet when not found").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when source is null")
        void lastIndexesOf_ShouldReturnEmptyBitSet_WhenSourceIsNull() {
            // given

            // when
            BitSet result = ArrayUtils.lastIndexesOf((double[]) null, 1.0);

            // then
            then(result.isEmpty()).as("Empty BitSet for null source").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when source is null")
        void lastIndexesOf_ShouldReturnEmptyBitSet_WhenSourceIsNullWithFromIndex() {
            // given

            // when
            BitSet result = ArrayUtils.lastIndexesOf((double[]) null, (double) 1, 0);

            // then
            then(result.isEmpty()).as("Empty BitSet for null source").isTrue();
        }

        @Test
        @DisplayName("Should find element at index zero when fromIndex is zero")
        void lastIndexesOf_ShouldFindElementAtIndexZero_WhenFromIndexIsZero() {
            // given
            double[] source = {1.0, 2.0, 1.0};

            // when
            BitSet result = ArrayUtils.lastIndexesOf(source, (double) 1.0, 0);

            // then
            then(result.cardinality()).as("Exactly one index found when scanning from zero").isEqualTo(1);
            then(result.get(0)).as("Index zero found").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when fromIndex is negative")
        void lastIndexesOf_ShouldReturnEmptyBitSet_WhenFromIndexIsNegative() {
            // given
            double[] source = {1.0, 2.0, 1.0};

            // when
            BitSet result = ArrayUtils.lastIndexesOf(source, (double) 1.0, -1);

            // then
            then(result.isEmpty()).as("Empty BitSet for negative fromIndex").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when fromIndex exceeds length")
        void lastIndexesOf_ShouldReturnEmptyBitSet_WhenFromIndexExceedsLength() {
            // given
            double[] source = {1.0, 2.0, 1.0};

            // when
            BitSet result = ArrayUtils.lastIndexesOf(source, (double) 1.0, 4);

            // then
            then(result.isEmpty()).as("Empty BitSet for fromIndex > length").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when element not found in valid range")
        void lastIndexesOf_ShouldReturnEmptyBitSet_WhenElementNotFoundInRange() {
            // given
            double[] source = {2.0, 2.0, 2.0};

            // when
            BitSet result = ArrayUtils.lastIndexesOf(source, 1.0, 2);

            // then
            then(result.isEmpty()).as("Empty BitSet when not found in range").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when fromIndex equals length")
        void indexesOf_ShouldReturnEmptyBitSet_WhenFromIndexEqualsLength() {
            // given
            double[] source = {1.0};

            // when
            BitSet result = ArrayUtils.indexesOf(source, 1.0, 1);

            // then
            then(result.isEmpty()).as("Empty BitSet for fromIndex == length").isTrue();
        }
    }

    @Nested
    class LastIndexesOfObjectTests {

        @Test
        @DisplayName("Should return BitSet with last indices")
        void lastIndexesOf_ShouldReturnBitSetWithIndices_WhenFound() {
            // given
            String[] source = {"a", "b", "a"};

            // when
            BitSet result = ArrayUtils.lastIndexesOf(source, "a");

            // then
            then(result.cardinality()).as("BitSet with last indices").isEqualTo(2);
        }

        @Test
        @DisplayName("Should return empty BitSet when not found")
        void lastIndexesOf_ShouldReturnEmptyBitSet_WhenNotFound() {
            // given
            String[] source = {"b"};

            // when
            BitSet result = ArrayUtils.lastIndexesOf(source, "a");

            // then
            then(result.isEmpty()).as("Empty BitSet when not found").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when source is null")
        void lastIndexesOf_ShouldReturnEmptyBitSet_WhenSourceIsNull() {
            // given

            // when
            BitSet result = ArrayUtils.lastIndexesOf((String[]) null, "a");

            // then
            then(result.isEmpty()).as("Empty BitSet for null source").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when source is null")
        void lastIndexesOf_ShouldReturnEmptyBitSet_WhenSourceIsNullWithFromIndex() {
            // given

            // when
            BitSet result = ArrayUtils.lastIndexesOf((String[]) null, (String) "a", 0);

            // then
            then(result.isEmpty()).as("Empty BitSet for null source").isTrue();
        }

        @Test
        @DisplayName("Should find element at index zero when fromIndex is zero")
        void lastIndexesOf_ShouldFindElementAtIndexZero_WhenFromIndexIsZero() {
            // given
            String[] source = {"a", "b", "a"};

            // when
            BitSet result = ArrayUtils.lastIndexesOf(source, "a", 0);

            // then
            then(result.cardinality()).as("Exactly one index found when scanning from zero").isEqualTo(1);
            then(result.get(0)).as("Index zero found").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when fromIndex is negative")
        void lastIndexesOf_ShouldReturnEmptyBitSet_WhenFromIndexIsNegative() {
            // given
            String[] source = {"a", "b", "a"};

            // when
            BitSet result = ArrayUtils.lastIndexesOf(source, "a", -1);

            // then
            then(result.isEmpty()).as("Empty BitSet for negative fromIndex").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when fromIndex exceeds length")
        void lastIndexesOf_ShouldReturnEmptyBitSet_WhenFromIndexExceedsLength() {
            // given
            String[] source = {"a", "b", "a"};

            // when
            BitSet result = ArrayUtils.lastIndexesOf(source, "a", 4);

            // then
            then(result.isEmpty()).as("Empty BitSet for fromIndex > length").isTrue();
        }

        @Test
        @DisplayName("Should return empty BitSet when element not found in valid range")
        void lastIndexesOf_ShouldReturnEmptyBitSet_WhenElementNotFoundInRange() {
            // given
            String[] source = {"b", "b", "b"};

            // when
            BitSet result = ArrayUtils.lastIndexesOf(source, "a", 2);

            // then
            then(result.isEmpty()).as("Empty BitSet when not found in range").isTrue();
        }
    }

    @Nested
    class MergeBooleanTests {

        @Test
        @DisplayName("Should merge two non-empty arrays")
        void merge_ShouldMergeArrays_WhenBothHaveLength() {
            // given
            boolean[] array1 = {true};
            boolean[] array2 = {false};

            // when
            boolean[] result = ArrayUtils.merge(array1, array2);

            // then
            then(result).as("Merged array").containsExactly(true, false);
        }

        @Test
        @DisplayName("Should return clone of second when first has no length")
        void merge_ShouldReturnCloneOfSecond_WhenFirstHasNoLength() {
            // given
            boolean[] array1 = {};
            boolean[] array2 = {false};

            // when
            boolean[] result = ArrayUtils.merge(array1, array2);

            // then
            then(result).as("Clone of second array").isEqualTo(array2);
        }

        @Test
        @DisplayName("Should return clone of first when second has no length")
        void merge_ShouldReturnCloneOfFirst_WhenSecondHasNoLength() {
            // given
            boolean[] array1 = {true};
            boolean[] array2 = {};

            // when
            boolean[] result = ArrayUtils.merge(array1, array2);

            // then
            then(result).as("Clone of first array").isEqualTo(array1);
        }

        @Test
        @DisplayName("Should return empty when both arrays are null")
        void merge_ShouldReturnEmpty_WhenBothAreNull() {
            // given

            // when
            boolean[] result = ArrayUtils.merge(null, (boolean[]) null);

            // then
            then(result).as("Empty when both null").isEmpty();
        }
    }

    @Nested
    class MergeByteTests {

        @Test
        @DisplayName("Should merge two non-empty arrays")
        void merge_ShouldMergeArrays_WhenBothHaveLength() {
            // given
            byte[] array1 = {1};
            byte[] array2 = {2};

            // when
            byte[] result = ArrayUtils.merge(array1, array2);

            // then
            then(result).as("Merged array").containsExactly((byte) 1, (byte) 2);
        }

        @Test
        @DisplayName("Should return clone of second when first has no length")
        void merge_ShouldReturnCloneOfSecond_WhenFirstHasNoLength() {
            // given
            byte[] array1 = {};
            byte[] array2 = {2};

            // when
            byte[] result = ArrayUtils.merge(array1, array2);

            // then
            then(result).as("Clone of second array").isEqualTo(array2);
        }

        @Test
        @DisplayName("Should return clone of first when second has no length")
        void merge_ShouldReturnCloneOfFirst_WhenSecondHasNoLength() {
            // given
            byte[] array1 = {1};
            byte[] array2 = {};

            // when
            byte[] result = ArrayUtils.merge(array1, array2);

            // then
            then(result).as("Clone of first array").isEqualTo(array1);
        }
    }

    @Nested
    class MergeCharTests {

        @Test
        @DisplayName("Should merge two non-empty arrays")
        void merge_ShouldMergeArrays_WhenBothHaveLength() {
            // given
            char[] array1 = {'a'};
            char[] array2 = {'b'};

            // when
            char[] result = ArrayUtils.merge(array1, array2);

            // then
            then(result).as("Merged array").containsExactly('a', 'b');
        }

        @Test
        @DisplayName("Should return clone of second when first has no length")
        void merge_ShouldReturnCloneOfSecond_WhenFirstHasNoLength() {
            // given
            char[] array1 = {};
            char[] array2 = {'b'};

            // when
            char[] result = ArrayUtils.merge(array1, array2);

            // then
            then(result).as("Clone of second array").isEqualTo(array2);
        }

        @Test
        @DisplayName("Should return clone of first when second has no length")
        void merge_ShouldReturnCloneOfFirst_WhenSecondHasNoLength() {
            // given
            char[] array1 = {'a'};
            char[] array2 = {};

            // when
            char[] result = ArrayUtils.merge(array1, array2);

            // then
            then(result).as("Clone of first array").isEqualTo(array1);
        }
    }

    @Nested
    class MergeShortTests {

        @Test
        @DisplayName("Should merge two non-empty arrays")
        void merge_ShouldMergeArrays_WhenBothHaveLength() {
            // given
            short[] array1 = {1};
            short[] array2 = {2};

            // when
            short[] result = ArrayUtils.merge(array1, array2);

            // then
            then(result).as("Merged array").containsExactly((short) 1, (short) 2);
        }

        @Test
        @DisplayName("Should return clone of second when first has no length")
        void merge_ShouldReturnCloneOfSecond_WhenFirstHasNoLength() {
            // given
            short[] array1 = {};
            short[] array2 = {2};

            // when
            short[] result = ArrayUtils.merge(array1, array2);

            // then
            then(result).as("Clone of second array").isEqualTo(array2);
        }

        @Test
        @DisplayName("Should return clone of first when second has no length")
        void merge_ShouldReturnCloneOfFirst_WhenSecondHasNoLength() {
            // given
            short[] array1 = {1};
            short[] array2 = {};

            // when
            short[] result = ArrayUtils.merge(array1, array2);

            // then
            then(result).as("Clone of first array").isEqualTo(array1);
        }
    }

    @Nested
    class MergeIntTests {

        @Test
        @DisplayName("Should merge two non-empty arrays")
        void merge_ShouldMergeArrays_WhenBothHaveLength() {
            // given
            int[] array1 = {1};
            int[] array2 = {2};

            // when
            int[] result = ArrayUtils.merge(array1, array2);

            // then
            then(result).as("Merged array").containsExactly(1, 2);
        }

        @Test
        @DisplayName("Should return clone of second when first has no length")
        void merge_ShouldReturnCloneOfSecond_WhenFirstHasNoLength() {
            // given
            int[] array1 = {};
            int[] array2 = {2};

            // when
            int[] result = ArrayUtils.merge(array1, array2);

            // then
            then(result).as("Clone of second array").isEqualTo(array2);
        }

        @Test
        @DisplayName("Should return clone of first when second has no length")
        void merge_ShouldReturnCloneOfFirst_WhenSecondHasNoLength() {
            // given
            int[] array1 = {1};
            int[] array2 = {};

            // when
            int[] result = ArrayUtils.merge(array1, array2);

            // then
            then(result).as("Clone of first array").isEqualTo(array1);
        }
    }

    @Nested
    class MergeLongTests {

        @Test
        @DisplayName("Should merge two non-empty arrays")
        void merge_ShouldMergeArrays_WhenBothHaveLength() {
            // given
            long[] array1 = {1L};
            long[] array2 = {2L};

            // when
            long[] result = ArrayUtils.merge(array1, array2);

            // then
            then(result).as("Merged array").containsExactly(1L, 2L);
        }

        @Test
        @DisplayName("Should return clone of second when first has no length")
        void merge_ShouldReturnCloneOfSecond_WhenFirstHasNoLength() {
            // given
            long[] array1 = {};
            long[] array2 = {2L};

            // when
            long[] result = ArrayUtils.merge(array1, array2);

            // then
            then(result).as("Clone of second array").isEqualTo(array2);
        }

        @Test
        @DisplayName("Should return clone of first when second has no length")
        void merge_ShouldReturnCloneOfFirst_WhenSecondHasNoLength() {
            // given
            long[] array1 = {1L};
            long[] array2 = {};

            // when
            long[] result = ArrayUtils.merge(array1, array2);

            // then
            then(result).as("Clone of first array").isEqualTo(array1);
        }
    }

    @Nested
    class MergeFloatTests {

        @Test
        @DisplayName("Should merge two non-empty arrays")
        void merge_ShouldMergeArrays_WhenBothHaveLength() {
            // given
            float[] array1 = {1.0f};
            float[] array2 = {2.0f};

            // when
            float[] result = ArrayUtils.merge(array1, array2);

            // then
            then(result).as("Merged array").containsExactly(1.0f, 2.0f);
        }

        @Test
        @DisplayName("Should return clone of second when first has no length")
        void merge_ShouldReturnCloneOfSecond_WhenFirstHasNoLength() {
            // given
            float[] array1 = {};
            float[] array2 = {2.0f};

            // when
            float[] result = ArrayUtils.merge(array1, array2);

            // then
            then(result).as("Clone of second array").isEqualTo(array2);
        }

        @Test
        @DisplayName("Should return clone of first when second has no length")
        void merge_ShouldReturnCloneOfFirst_WhenSecondHasNoLength() {
            // given
            float[] array1 = {1.0f};
            float[] array2 = {};

            // when
            float[] result = ArrayUtils.merge(array1, array2);

            // then
            then(result).as("Clone of first array").isEqualTo(array1);
        }
    }

    @Nested
    class MergeDoubleTests {

        @Test
        @DisplayName("Should merge two non-empty arrays")
        void merge_ShouldMergeArrays_WhenBothHaveLength() {
            // given
            double[] array1 = {1.0};
            double[] array2 = {2.0};

            // when
            double[] result = ArrayUtils.merge(array1, array2);

            // then
            then(result).as("Merged array").containsExactly(1.0, 2.0);
        }

        @Test
        @DisplayName("Should return clone of second when first has no length")
        void merge_ShouldReturnCloneOfSecond_WhenFirstHasNoLength() {
            // given
            double[] array1 = {};
            double[] array2 = {2.0};

            // when
            double[] result = ArrayUtils.merge(array1, array2);

            // then
            then(result).as("Clone of second array").isEqualTo(array2);
        }

        @Test
        @DisplayName("Should return clone of first when second has no length")
        void merge_ShouldReturnCloneOfFirst_WhenSecondHasNoLength() {
            // given
            double[] array1 = {1.0};
            double[] array2 = {};

            // when
            double[] result = ArrayUtils.merge(array1, array2);

            // then
            then(result).as("Clone of first array").isEqualTo(array1);
        }
    }

    @Nested
    class MergeObjectTests {

        @Test
        @DisplayName("Should merge two non-empty arrays")
        void merge_ShouldMergeArrays_WhenBothHaveLength() {
            // given
            String[] array1 = {"a"};
            String[] array2 = {"b"};

            // when
            String[] result = ArrayUtils.merge(array1, array2);

            // then
            then(result).as("Merged array").containsExactly("a", "b");
        }

        @Test
        @DisplayName("Should return clone of second when first has no length")
        void merge_ShouldReturnCloneOfSecond_WhenFirstHasNoLength() {
            // given
            String[] array1 = {};
            String[] array2 = {"b"};

            // when
            String[] result = ArrayUtils.merge(array1, array2);

            // then
            then(result).as("Clone of second array").isEqualTo(array2);
        }

        @Test
        @DisplayName("Should return clone of first when second has no length")
        void merge_ShouldReturnCloneOfFirst_WhenSecondHasNoLength() {
            // given
            String[] array1 = {"a"};
            String[] array2 = {};

            // when
            String[] result = ArrayUtils.merge(array1, array2);

            // then
            then(result).as("Clone of first array").isEqualTo(array1);
        }
    }

    @Nested
    class RemoveBooleanTests {

        @Test
        @DisplayName("Should remove element at valid index")
        void remove_ShouldReturnNewArrayWithoutElement_WhenValidIndex() {
            // given
            boolean[] source = {true, false, true};

            // when
            boolean[] result = ArrayUtils.remove(source, 1);

            // then
            then(result).as("Array without element at index 1").containsExactly(true, true);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when source is null")
        void remove_ShouldThrowIllegalArgumentException_WhenSourceIsNull() {
            // given

            // when & then
            thenThrownBy(() -> ArrayUtils.remove((boolean[]) null, 0))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The input array cannot be null");
        }

        @Test
        @DisplayName("Should throw IndexOutOfBoundsException when index is negative")
        void remove_ShouldThrowIndexOutOfBoundsException_WhenIndexNegative() {
            // given
            boolean[] source = {true};

            // when & then
            thenThrownBy(() -> ArrayUtils.remove(source, -1))
                    .isInstanceOf(IndexOutOfBoundsException.class)
                    .hasMessage("Index -1 out of bounds for length 1");
        }

        @Test
        @DisplayName("Should throw IndexOutOfBoundsException when index greater or equal length")
        void remove_ShouldThrowIndexOutOfBoundsException_WhenIndexGreaterOrEqualLength() {
            // given
            boolean[] source = {true};

            // when & then
            thenThrownBy(() -> ArrayUtils.remove(source, 1))
                    .isInstanceOf(IndexOutOfBoundsException.class)
                    .hasMessage("Index 1 out of bounds for length 1");
        }

        @Test
        @DisplayName("Should throw IndexOutOfBoundsException when source is empty")
        void remove_ShouldThrowIndexOutOfBoundsException_WhenSourceIsEmpty() {
            // given
            boolean[] source = {};

            // when & then
            thenThrownBy(() -> ArrayUtils.remove(source, 0))
                    .isInstanceOf(IndexOutOfBoundsException.class)
                    .hasMessage("Index 0 out of bounds for length 0");
        }
    }

    @Nested
    class RemoveByteTests {

        @Test
        @DisplayName("Should remove element at valid index")
        void remove_ShouldReturnNewArrayWithoutElement_WhenValidIndex() {
            // given
            byte[] source = {1, 2, 3};

            // when
            byte[] result = ArrayUtils.remove(source, 1);

            // then
            then(result).as("Array without element at index 1").containsExactly((byte) 1, (byte) 3);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when source is null")
        void remove_ShouldThrowIllegalArgumentException_WhenSourceIsNull() {
            // given

            // when & then
            thenThrownBy(() -> ArrayUtils.remove((byte[]) null, 0))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The input array cannot be null");
        }

        @Test
        @DisplayName("Should throw IndexOutOfBoundsException when index is negative")
        void remove_ShouldThrowIndexOutOfBoundsException_WhenIndexNegative() {
            // given
            byte[] source = {1};

            // when & then
            thenThrownBy(() -> ArrayUtils.remove(source, -1))
                    .isInstanceOf(IndexOutOfBoundsException.class)
                    .hasMessage("Index -1 out of bounds for length 1");
        }

        @Test
        @DisplayName("Should throw IndexOutOfBoundsException when index greater or equal length")
        void remove_ShouldThrowIndexOutOfBoundsException_WhenIndexGreaterOrEqualLength() {
            // given
            byte[] source = {1};

            // when & then
            thenThrownBy(() -> ArrayUtils.remove(source, 1))
                    .isInstanceOf(IndexOutOfBoundsException.class)
                    .hasMessage("Index 1 out of bounds for length 1");
        }
    }

    @Nested
    class RemoveCharTests {

        @Test
        @DisplayName("Should remove element at valid index")
        void remove_ShouldReturnNewArrayWithoutElement_WhenValidIndex() {
            // given
            char[] source = {'a', 'b', 'c'};

            // when
            char[] result = ArrayUtils.remove(source, 1);

            // then
            then(result).as("Array without element at index 1").containsExactly('a', 'c');
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when source is null")
        void remove_ShouldThrowIllegalArgumentException_WhenSourceIsNull() {
            // given

            // when & then
            thenThrownBy(() -> ArrayUtils.remove((char[]) null, 0))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The input array cannot be null");
        }

        @Test
        @DisplayName("Should throw IndexOutOfBoundsException when index is negative")
        void remove_ShouldThrowIndexOutOfBoundsException_WhenIndexNegative() {
            // given
            char[] source = {'a'};

            // when & then
            thenThrownBy(() -> ArrayUtils.remove(source, -1))
                    .isInstanceOf(IndexOutOfBoundsException.class)
                    .hasMessage("Index -1 out of bounds for length 1");
        }

        @Test
        @DisplayName("Should throw IndexOutOfBoundsException when index greater or equal length")
        void remove_ShouldThrowIndexOutOfBoundsException_WhenIndexGreaterOrEqualLength() {
            // given
            char[] source = {'a'};

            // when & then
            thenThrownBy(() -> ArrayUtils.remove(source, 1))
                    .isInstanceOf(IndexOutOfBoundsException.class)
                    .hasMessage("Index 1 out of bounds for length 1");
        }
    }

    @Nested
    class RemoveShortTests {

        @Test
        @DisplayName("Should remove element at valid index")
        void remove_ShouldReturnNewArrayWithoutElement_WhenValidIndex() {
            // given
            short[] source = {1, 2, 3};

            // when
            short[] result = ArrayUtils.remove(source, 1);

            // then
            then(result).as("Array without element at index 1").containsExactly((short) 1, (short) 3);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when source is null")
        void remove_ShouldThrowIllegalArgumentException_WhenSourceIsNull() {
            // given

            // when & then
            thenThrownBy(() -> ArrayUtils.remove((short[]) null, 0))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The input array cannot be null");
        }

        @Test
        @DisplayName("Should throw IndexOutOfBoundsException when index is negative")
        void remove_ShouldThrowIndexOutOfBoundsException_WhenIndexNegative() {
            // given
            short[] source = {1};

            // when & then
            thenThrownBy(() -> ArrayUtils.remove(source, -1))
                    .isInstanceOf(IndexOutOfBoundsException.class)
                    .hasMessage("Index -1 out of bounds for length 1");
        }

        @Test
        @DisplayName("Should throw IndexOutOfBoundsException when index greater or equal length")
        void remove_ShouldThrowIndexOutOfBoundsException_WhenIndexGreaterOrEqualLength() {
            // given
            short[] source = {1};

            // when & then
            thenThrownBy(() -> ArrayUtils.remove(source, 1))
                    .isInstanceOf(IndexOutOfBoundsException.class)
                    .hasMessage("Index 1 out of bounds for length 1");
        }
    }

    @Nested
    class RemoveIntTests {

        @Test
        @DisplayName("Should remove element at valid index")
        void remove_ShouldReturnNewArrayWithoutElement_WhenValidIndex() {
            // given
            int[] source = {1, 2, 3};

            // when
            int[] result = ArrayUtils.remove(source, 1);

            // then
            then(result).as("Array without element at index 1").containsExactly(1, 3);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when source is null")
        void remove_ShouldThrowIllegalArgumentException_WhenSourceIsNull() {
            // given

            // when & then
            thenThrownBy(() -> ArrayUtils.remove((int[]) null, 0))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The input array cannot be null");
        }

        @Test
        @DisplayName("Should throw IndexOutOfBoundsException when index is negative")
        void remove_ShouldThrowIndexOutOfBoundsException_WhenIndexNegative() {
            // given
            int[] source = {1};

            // when & then
            thenThrownBy(() -> ArrayUtils.remove(source, -1))
                    .isInstanceOf(IndexOutOfBoundsException.class)
                    .hasMessage("Index -1 out of bounds for length 1");
        }

        @Test
        @DisplayName("Should throw IndexOutOfBoundsException when index greater or equal length")
        void remove_ShouldThrowIndexOutOfBoundsException_WhenIndexGreaterOrEqualLength() {
            // given
            int[] source = {1};

            // when & then
            thenThrownBy(() -> ArrayUtils.remove(source, 1))
                    .isInstanceOf(IndexOutOfBoundsException.class)
                    .hasMessage("Index 1 out of bounds for length 1");
        }
    }

    @Nested
    class RemoveLongTests {

        @Test
        @DisplayName("Should remove element at valid index")
        void remove_ShouldReturnNewArrayWithoutElement_WhenValidIndex() {
            // given
            long[] source = {1L, 2L, 3L};

            // when
            long[] result = ArrayUtils.remove(source, 1);

            // then
            then(result).as("Array without element at index 1").containsExactly(1L, 3L);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when source is null")
        void remove_ShouldThrowIllegalArgumentException_WhenSourceIsNull() {
            // given

            // when & then
            thenThrownBy(() -> ArrayUtils.remove((long[]) null, 0))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The input array cannot be null");
        }

        @Test
        @DisplayName("Should throw IndexOutOfBoundsException when index is negative")
        void remove_ShouldThrowIndexOutOfBoundsException_WhenIndexNegative() {
            // given
            long[] source = {1L};

            // when & then
            thenThrownBy(() -> ArrayUtils.remove(source, -1))
                    .isInstanceOf(IndexOutOfBoundsException.class)
                    .hasMessage("Index -1 out of bounds for length 1");
        }

        @Test
        @DisplayName("Should throw IndexOutOfBoundsException when index greater or equal length")
        void remove_ShouldThrowIndexOutOfBoundsException_WhenIndexGreaterOrEqualLength() {
            // given
            long[] source = {1L};

            // when & then
            thenThrownBy(() -> ArrayUtils.remove(source, 1))
                    .isInstanceOf(IndexOutOfBoundsException.class)
                    .hasMessage("Index 1 out of bounds for length 1");
        }
    }

    @Nested
    class RemoveFloatTests {

        @Test
        @DisplayName("Should remove element at valid index")
        void remove_ShouldReturnNewArrayWithoutElement_WhenValidIndex() {
            // given
            float[] source = {1.0f, 2.0f, 3.0f};

            // when
            float[] result = ArrayUtils.remove(source, 1);

            // then
            then(result).as("Array without element at index 1").containsExactly(1.0f, 3.0f);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when source is null")
        void remove_ShouldThrowIllegalArgumentException_WhenSourceIsNull() {
            // given

            // when & then
            thenThrownBy(() -> ArrayUtils.remove((float[]) null, 0))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The input array cannot be null");
        }

        @Test
        @DisplayName("Should throw IndexOutOfBoundsException when index is negative")
        void remove_ShouldThrowIndexOutOfBoundsException_WhenIndexNegative() {
            // given
            float[] source = {1.0f};

            // when & then
            thenThrownBy(() -> ArrayUtils.remove(source, -1))
                    .isInstanceOf(IndexOutOfBoundsException.class)
                    .hasMessage("Index -1 out of bounds for length 1");
        }

        @Test
        @DisplayName("Should throw IndexOutOfBoundsException when index greater or equal length")
        void remove_ShouldThrowIndexOutOfBoundsException_WhenIndexGreaterOrEqualLength() {
            // given
            float[] source = {1.0f};

            // when & then
            thenThrownBy(() -> ArrayUtils.remove(source, 1))
                    .isInstanceOf(IndexOutOfBoundsException.class)
                    .hasMessage("Index 1 out of bounds for length 1");
        }
    }

    @Nested
    class RemoveDoubleTests {

        @Test
        @DisplayName("Should remove element at valid index")
        void remove_ShouldReturnNewArrayWithoutElement_WhenValidIndex() {
            // given
            double[] source = {1.0, 2.0, 3.0};

            // when
            double[] result = ArrayUtils.remove(source, 1);

            // then
            then(result).as("Array without element at index 1").containsExactly(1.0, 3.0);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when source is null")
        void remove_ShouldThrowIllegalArgumentException_WhenSourceIsNull() {
            // given

            // when & then
            thenThrownBy(() -> ArrayUtils.remove((double[]) null, 0))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The input array cannot be null");
        }

        @Test
        @DisplayName("Should throw IndexOutOfBoundsException when index is negative")
        void remove_ShouldThrowIndexOutOfBoundsException_WhenIndexNegative() {
            // given
            double[] source = {1.0};

            // when & then
            thenThrownBy(() -> ArrayUtils.remove(source, -1))
                    .isInstanceOf(IndexOutOfBoundsException.class)
                    .hasMessage("Index -1 out of bounds for length 1");
        }

        @Test
        @DisplayName("Should throw IndexOutOfBoundsException when index greater or equal length")
        void remove_ShouldThrowIndexOutOfBoundsException_WhenIndexGreaterOrEqualLength() {
            // given
            double[] source = {1.0};

            // when & then
            thenThrownBy(() -> ArrayUtils.remove(source, 1))
                    .isInstanceOf(IndexOutOfBoundsException.class)
                    .hasMessage("Index 1 out of bounds for length 1");
        }
    }

    @Nested
    class RemoveObjectTests {

        @Test
        @DisplayName("Should remove element at valid index")
        void remove_ShouldReturnNewArrayWithoutElement_WhenValidIndex() {
            // given
            String[] source = {"a", "b", "c"};

            // when
            String[] result = ArrayUtils.remove(source, 1);

            // then
            then(result).as("Array without element at index 1").containsExactly("a", "c");
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when source is null")
        void remove_ShouldThrowIllegalArgumentException_WhenSourceIsNull() {
            // given

            // when & then
            thenThrownBy(() -> ArrayUtils.remove((Object[]) null, 0))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The input array cannot be null");
        }

        @Test
        @DisplayName("Should throw IndexOutOfBoundsException when index is negative")
        void remove_ShouldThrowIndexOutOfBoundsException_WhenIndexNegative() {
            // given
            String[] source = {"a"};

            // when & then
            thenThrownBy(() -> ArrayUtils.remove(source, -1))
                    .isInstanceOf(IndexOutOfBoundsException.class)
                    .hasMessage("Index -1 out of bounds for length 1");
        }

        @Test
        @DisplayName("Should throw IndexOutOfBoundsException when index greater or equal length")
        void remove_ShouldThrowIndexOutOfBoundsException_WhenIndexGreaterOrEqualLength() {
            // given
            String[] source = {"a"};

            // when & then
            thenThrownBy(() -> ArrayUtils.remove(source, 1))
                    .isInstanceOf(IndexOutOfBoundsException.class)
                    .hasMessage("Index 1 out of bounds for length 1");
        }
    }

    @Nested
    class RemoveFirstBooleanTests {

        @Test
        @DisplayName("Should remove first element")
        void removeFirst_ShouldRemoveFirstElement_WhenSourceValid() {
            // given
            boolean[] source = {true, false};

            // when
            boolean[] result = ArrayUtils.removeFirst(source);

            // then
            then(result).as("Array without first element").containsExactly(false);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when source is null")
        void removeFirst_ShouldThrowIllegalArgumentException_WhenSourceIsNull() {
            // given

            // when & then
            thenThrownBy(() -> ArrayUtils.removeFirst((boolean[]) null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The input array cannot be null");
        }
    }

    @Nested
    class RemoveFirstByteTests {

        @Test
        @DisplayName("Should remove first element")
        void removeFirst_ShouldRemoveFirstElement_WhenSourceValid() {
            // given
            byte[] source = {1, 2};

            // when
            byte[] result = ArrayUtils.removeFirst(source);

            // then
            then(result).as("Array without first element").containsExactly((byte) 2);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when source is null")
        void removeFirst_ShouldThrowIllegalArgumentException_WhenSourceIsNull() {
            // given

            // when & then
            thenThrownBy(() -> ArrayUtils.removeFirst((byte[]) null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The input array cannot be null");
        }
    }

    @Nested
    class RemoveFirstCharTests {

        @Test
        @DisplayName("Should remove first element")
        void removeFirst_ShouldRemoveFirstElement_WhenSourceValid() {
            // given
            char[] source = {'a', 'b'};

            // when
            char[] result = ArrayUtils.removeFirst(source);

            // then
            then(result).as("Array without first element").containsExactly('b');
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when source is null")
        void removeFirst_ShouldThrowIllegalArgumentException_WhenSourceIsNull() {
            // given

            // when & then
            thenThrownBy(() -> ArrayUtils.removeFirst((char[]) null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The input array cannot be null");
        }
    }

    @Nested
    class RemoveFirstShortTests {

        @Test
        @DisplayName("Should remove first element")
        void removeFirst_ShouldRemoveFirstElement_WhenSourceValid() {
            // given
            short[] source = {1, 2};

            // when
            short[] result = ArrayUtils.removeFirst(source);

            // then
            then(result).as("Array without first element").containsExactly((short) 2);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when source is null")
        void removeFirst_ShouldThrowIllegalArgumentException_WhenSourceIsNull() {
            // given

            // when & then
            thenThrownBy(() -> ArrayUtils.removeFirst((short[]) null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The input array cannot be null");
        }
    }

    @Nested
    class RemoveFirstIntTests {

        @Test
        @DisplayName("Should remove first element")
        void removeFirst_ShouldRemoveFirstElement_WhenSourceValid() {
            // given
            int[] source = {1, 2};

            // when
            int[] result = ArrayUtils.removeFirst(source);

            // then
            then(result).as("Array without first element").containsExactly(2);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when source is null")
        void removeFirst_ShouldThrowIllegalArgumentException_WhenSourceIsNull() {
            // given

            // when & then
            thenThrownBy(() -> ArrayUtils.removeFirst((int[]) null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The input array cannot be null");
        }
    }

    @Nested
    class RemoveFirstLongTests {

        @Test
        @DisplayName("Should remove first element")
        void removeFirst_ShouldRemoveFirstElement_WhenSourceValid() {
            // given
            long[] source = {1L, 2L};

            // when
            long[] result = ArrayUtils.removeFirst(source);

            // then
            then(result).as("Array without first element").containsExactly(2L);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when source is null")
        void removeFirst_ShouldThrowIllegalArgumentException_WhenSourceIsNull() {
            // given

            // when & then
            thenThrownBy(() -> ArrayUtils.removeFirst((long[]) null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The input array cannot be null");
        }
    }

    @Nested
    class RemoveFirstFloatTests {

        @Test
        @DisplayName("Should remove first element")
        void removeFirst_ShouldRemoveFirstElement_WhenSourceValid() {
            // given
            float[] source = {1.0f, 2.0f};

            // when
            float[] result = ArrayUtils.removeFirst(source);

            // then
            then(result).as("Array without first element").containsExactly(2.0f);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when source is null")
        void removeFirst_ShouldThrowIllegalArgumentException_WhenSourceIsNull() {
            // given

            // when & then
            thenThrownBy(() -> ArrayUtils.removeFirst((float[]) null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The input array cannot be null");
        }
    }

    @Nested
    class RemoveFirstDoubleTests {

        @Test
        @DisplayName("Should remove first element")
        void removeFirst_ShouldRemoveFirstElement_WhenSourceValid() {
            // given
            double[] source = {1.0, 2.0};

            // when
            double[] result = ArrayUtils.removeFirst(source);

            // then
            then(result).as("Array without first element").containsExactly(2.0);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when source is null")
        void removeFirst_ShouldThrowIllegalArgumentException_WhenSourceIsNull() {
            // given

            // when & then
            thenThrownBy(() -> ArrayUtils.removeFirst((double[]) null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The input array cannot be null");
        }
    }

    @Nested
    class RemoveFirstObjectTests {

        @Test
        @DisplayName("Should remove first element")
        void removeFirst_ShouldRemoveFirstElement_WhenSourceValid() {
            // given
            String[] source = {"a", "b"};

            // when
            String[] result = ArrayUtils.removeFirst(source);

            // then
            then(result).as("Array without first element").containsExactly("b");
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when source is null")
        void removeFirst_ShouldThrowIllegalArgumentException_WhenSourceIsNull() {
            // given

            // when & then
            thenThrownBy(() -> ArrayUtils.removeFirst((Object[]) null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The input array cannot be null");
        }
    }

    @Nested
    class RemoveLastBooleanTests {

        @Test
        @DisplayName("Should remove last element")
        void removeLast_ShouldRemoveLastElement_WhenSourceValid() {
            // given
            boolean[] source = {true, false};

            // when
            boolean[] result = ArrayUtils.removeLast(source);

            // then
            then(result).as("Array without last element").containsExactly(true);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when source is null")
        void removeLast_ShouldThrowIllegalArgumentException_WhenSourceIsNull() {
            // given

            // when & then
            thenThrownBy(() -> ArrayUtils.removeLast((boolean[]) null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The input array cannot be null");
        }
    }

    @Nested
    class RemoveLastByteTests {

        @Test
        @DisplayName("Should remove last element")
        void removeLast_ShouldRemoveLastElement_WhenSourceValid() {
            // given
            byte[] source = {1, 2};

            // when
            byte[] result = ArrayUtils.removeLast(source);

            // then
            then(result).as("Array without last element").containsExactly((byte) 1);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when source is null")
        void removeLast_ShouldThrowIllegalArgumentException_WhenSourceIsNull() {
            // given

            // when & then
            thenThrownBy(() -> ArrayUtils.removeLast((byte[]) null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The input array cannot be null");
        }
    }

    @Nested
    class RemoveLastCharTests {

        @Test
        @DisplayName("Should remove last element")
        void removeLast_ShouldRemoveLastElement_WhenSourceValid() {
            // given
            char[] source = {'a', 'b'};

            // when
            char[] result = ArrayUtils.removeLast(source);

            // then
            then(result).as("Array without last element").containsExactly('a');
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when source is null")
        void removeLast_ShouldThrowIllegalArgumentException_WhenSourceIsNull() {
            // given

            // when & then
            thenThrownBy(() -> ArrayUtils.removeLast((char[]) null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The input array cannot be null");
        }
    }

    @Nested
    class RemoveLastShortTests {

        @Test
        @DisplayName("Should remove last element")
        void removeLast_ShouldRemoveLastElement_WhenSourceValid() {
            // given
            short[] source = {1, 2};

            // when
            short[] result = ArrayUtils.removeLast(source);

            // then
            then(result).as("Array without last element").containsExactly((short) 1);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when source is null")
        void removeLast_ShouldThrowIllegalArgumentException_WhenSourceIsNull() {
            // given

            // when & then
            thenThrownBy(() -> ArrayUtils.removeLast((short[]) null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The input array cannot be null");
        }
    }

    @Nested
    class RemoveLastIntTests {

        @Test
        @DisplayName("Should remove last element")
        void removeLast_ShouldRemoveLastElement_WhenSourceValid() {
            // given
            int[] source = {1, 2};

            // when
            int[] result = ArrayUtils.removeLast(source);

            // then
            then(result).as("Array without last element").containsExactly(1);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when source is null")
        void removeLast_ShouldThrowIllegalArgumentException_WhenSourceIsNull() {
            // given

            // when & then
            thenThrownBy(() -> ArrayUtils.removeLast((int[]) null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The input array cannot be null");
        }
    }

    @Nested
    class RemoveLastLongTests {

        @Test
        @DisplayName("Should remove last element")
        void removeLast_ShouldRemoveLastElement_WhenSourceValid() {
            // given
            long[] source = {1L, 2L};

            // when
            long[] result = ArrayUtils.removeLast(source);

            // then
            then(result).as("Array without last element").containsExactly(1L);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when source is null")
        void removeLast_ShouldThrowIllegalArgumentException_WhenSourceIsNull() {
            // given

            // when & then
            thenThrownBy(() -> ArrayUtils.removeLast((long[]) null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The input array cannot be null");
        }
    }

    @Nested
    class RemoveLastFloatTests {

        @Test
        @DisplayName("Should remove last element")
        void removeLast_ShouldRemoveLastElement_WhenSourceValid() {
            // given
            float[] source = {1.0f, 2.0f};

            // when
            float[] result = ArrayUtils.removeLast(source);

            // then
            then(result).as("Array without last element").containsExactly(1.0f);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when source is null")
        void removeLast_ShouldThrowIllegalArgumentException_WhenSourceIsNull() {
            // given

            // when & then
            thenThrownBy(() -> ArrayUtils.removeLast((float[]) null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The input array cannot be null");
        }
    }

    @Nested
    class RemoveLastDoubleTests {

        @Test
        @DisplayName("Should remove last element")
        void removeLast_ShouldRemoveLastElement_WhenSourceValid() {
            // given
            double[] source = {1.0, 2.0};

            // when
            double[] result = ArrayUtils.removeLast(source);

            // then
            then(result).as("Array without last element").containsExactly(1.0);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when source is null")
        void removeLast_ShouldThrowIllegalArgumentException_WhenSourceIsNull() {
            // given

            // when & then
            thenThrownBy(() -> ArrayUtils.removeLast((double[]) null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The input array cannot be null");
        }
    }

    @Nested
    class RemoveLastObjectTests {

        @Test
        @DisplayName("Should remove last element")
        void removeLast_ShouldRemoveLastElement_WhenSourceValid() {
            // given
            String[] source = {"a", "b"};

            // when
            String[] result = ArrayUtils.removeLast(source);

            // then
            then(result).as("Array without last element").containsExactly("a");
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when source is null")
        void removeLast_ShouldThrowIllegalArgumentException_WhenSourceIsNull() {
            // given

            // when & then
            thenThrownBy(() -> ArrayUtils.removeLast((Object[]) null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The input array cannot be null");
        }
    }

    @Nested
    class RemoveElementBooleanTests {

        @Test
        @DisplayName("Should remove first occurrence of element")
        void removeElement_ShouldRemoveFirstOccurrence_WhenElementFound() {
            // given
            boolean[] source = {true, false, true};

            // when
            boolean[] result = ArrayUtils.removeElement(source, true);

            // then
            then(result).as("Array without first true").containsExactly(false, true);
        }

        @Test
        @DisplayName("Should return clone when element not found")
        void removeElement_ShouldReturnClone_WhenElementNotFound() {
            // given
            boolean[] source = {false};

            // when
            boolean[] result = ArrayUtils.removeElement(source, true);

            // then
            then(result).as("Clone when not found").isEqualTo(source);
        }
    }

    @Nested
    class RemoveElementByteTests {

        @Test
        @DisplayName("Should remove first occurrence of element")
        void removeElement_ShouldRemoveFirstOccurrence_WhenElementFound() {
            // given
            byte[] source = {1, 2, 1};

            // when
            byte[] result = ArrayUtils.removeElement(source, (byte) 1);

            // then
            then(result).as("Array without first 1").containsExactly((byte) 2, (byte) 1);
        }

        @Test
        @DisplayName("Should return clone when element not found")
        void removeElement_ShouldReturnClone_WhenElementNotFound() {
            // given
            byte[] source = {2};

            // when
            byte[] result = ArrayUtils.removeElement(source, (byte) 1);

            // then
            then(result).as("Clone when not found").isEqualTo(source);
        }
    }

    @Nested
    class RemoveElementCharTests {

        @Test
        @DisplayName("Should remove first occurrence of element")
        void removeElement_ShouldRemoveFirstOccurrence_WhenElementFound() {
            // given
            char[] source = {'a', 'b', 'a'};

            // when
            char[] result = ArrayUtils.removeElement(source, 'a');

            // then
            then(result).as("Array without first a").containsExactly('b', 'a');
        }

        @Test
        @DisplayName("Should return clone when element not found")
        void removeElement_ShouldReturnClone_WhenElementNotFound() {
            // given
            char[] source = {'b'};

            // when
            char[] result = ArrayUtils.removeElement(source, 'a');

            // then
            then(result).as("Clone when not found").isEqualTo(source);
        }
    }

    @Nested
    class RemoveElementShortTests {

        @Test
        @DisplayName("Should remove first occurrence of element")
        void removeElement_ShouldRemoveFirstOccurrence_WhenElementFound() {
            // given
            short[] source = {1, 2, 1};

            // when
            short[] result = ArrayUtils.removeElement(source, (short) 1);

            // then
            then(result).as("Array without first 1").containsExactly((short) 2, (short) 1);
        }

        @Test
        @DisplayName("Should return clone when element not found")
        void removeElement_ShouldReturnClone_WhenElementNotFound() {
            // given
            short[] source = {2};

            // when
            short[] result = ArrayUtils.removeElement(source, (short) 1);

            // then
            then(result).as("Clone when not found").isEqualTo(source);
        }
    }

    @Nested
    class RemoveElementIntTests {

        @Test
        @DisplayName("Should remove first occurrence of element")
        void removeElement_ShouldRemoveFirstOccurrence_WhenElementFound() {
            // given
            int[] source = {1, 2, 1};

            // when
            int[] result = ArrayUtils.removeElement(source, 1);

            // then
            then(result).as("Array without first 1").containsExactly(2, 1);
        }

        @Test
        @DisplayName("Should return clone when element not found")
        void removeElement_ShouldReturnClone_WhenElementNotFound() {
            // given
            int[] source = {2};

            // when
            int[] result = ArrayUtils.removeElement(source, 1);

            // then
            then(result).as("Clone when not found").isEqualTo(source);
        }
    }

    @Nested
    class RemoveElementLongTests {

        @Test
        @DisplayName("Should remove first occurrence of element")
        void removeElement_ShouldRemoveFirstOccurrence_WhenElementFound() {
            // given
            long[] source = {1L, 2L, 1L};

            // when
            long[] result = ArrayUtils.removeElement(source, 1L);

            // then
            then(result).as("Array without first 1").containsExactly(2L, 1L);
        }

        @Test
        @DisplayName("Should return clone when element not found")
        void removeElement_ShouldReturnClone_WhenElementNotFound() {
            // given
            long[] source = {2L};

            // when
            long[] result = ArrayUtils.removeElement(source, 1L);

            // then
            then(result).as("Clone when not found").isEqualTo(source);
        }
    }

    @Nested
    class RemoveElementFloatTests {

        @Test
        @DisplayName("Should remove first occurrence of element")
        void removeElement_ShouldRemoveFirstOccurrence_WhenElementFound() {
            // given
            float[] source = {1.0f, 2.0f, 1.0f};

            // when
            float[] result = ArrayUtils.removeElement(source, 1.0f);

            // then
            then(result).as("Array without first 1.0f").containsExactly(2.0f, 1.0f);
        }

        @Test
        @DisplayName("Should return clone when element not found")
        void removeElement_ShouldReturnClone_WhenElementNotFound() {
            // given
            float[] source = {2.0f};

            // when
            float[] result = ArrayUtils.removeElement(source, 1.0f);

            // then
            then(result).as("Clone when not found").isEqualTo(source);
        }
    }

    @Nested
    class RemoveElementDoubleTests {

        @Test
        @DisplayName("Should remove first occurrence of element")
        void removeElement_ShouldRemoveFirstOccurrence_WhenElementFound() {
            // given
            double[] source = {1.0, 2.0, 1.0};

            // when
            double[] result = ArrayUtils.removeElement(source, 1.0);

            // then
            then(result).as("Array without first 1.0").containsExactly(2.0, 1.0);
        }

        @Test
        @DisplayName("Should return clone when element not found")
        void removeElement_ShouldReturnClone_WhenElementNotFound() {
            // given
            double[] source = {2.0};

            // when
            double[] result = ArrayUtils.removeElement(source, 1.0);

            // then
            then(result).as("Clone when not found").isEqualTo(source);
        }
    }

    @Nested
    class RemoveElementObjectTests {

        @Test
        @DisplayName("Should remove first occurrence of element")
        void removeElement_ShouldRemoveFirstOccurrence_WhenElementFound() {
            // given
            String[] source = {"a", "b", "a"};

            // when
            String[] result = ArrayUtils.removeElement(source, "a");

            // then
            then(result).as("Array without first a").containsExactly("b", "a");
        }

        @Test
        @DisplayName("Should return clone when element not found")
        void removeElement_ShouldReturnClone_WhenElementNotFound() {
            // given
            String[] source = {"b"};

            // when
            String[] result = ArrayUtils.removeElement(source, "a");

            // then
            then(result).as("Clone when not found").isEqualTo(source);
        }
    }

    @Nested
    class SortByteTests {

        @Test
        @DisplayName("Should sort array in ascending order")
        void sort_ShouldSortArray_WhenHasLength() {
            // given
            byte[] source = {3, 1, 2};

            // when
            byte[] result = ArrayUtils.sort(source);

            // then
            then(result).as("Sorted array").containsExactly((byte) 1, (byte) 2, (byte) 3);
        }

        @Test
        @DisplayName("Should return null when source is null")
        void sort_ShouldReturnNull_WhenSourceIsNull() {
            // given

            // when
            byte[] result = ArrayUtils.sort((byte[]) null);

            // then
            then(result).as("Null for null source").isNull();
        }

        @Test
        @DisplayName("Should return same empty array when source is empty")
        void sort_ShouldReturnEmpty_WhenSourceIsEmpty() {
            // given
            byte[] source = {};

            // when
            byte[] result = ArrayUtils.sort(source);

            // then
            then(result).as("Empty remains empty").isEmpty();
        }
    }

    @Nested
    class SortCharTests {

        @Test
        @DisplayName("Should sort array in ascending order")
        void sort_ShouldSortArray_WhenHasLength() {
            // given
            char[] source = {'c', 'a', 'b'};

            // when
            char[] result = ArrayUtils.sort(source);

            // then
            then(result).as("Sorted array").containsExactly('a', 'b', 'c');
        }

        @Test
        @DisplayName("Should return null when source is null")
        void sort_ShouldReturnNull_WhenSourceIsNull() {
            // given

            // when
            char[] result = ArrayUtils.sort((char[]) null);

            // then
            then(result).as("Null for null source").isNull();
        }
    }

    @Nested
    class SortShortTests {

        @Test
        @DisplayName("Should sort array in ascending order")
        void sort_ShouldSortArray_WhenHasLength() {
            // given
            short[] source = {3, 1, 2};

            // when
            short[] result = ArrayUtils.sort(source);

            // then
            then(result).as("Sorted array").containsExactly((short) 1, (short) 2, (short) 3);
        }

        @Test
        @DisplayName("Should return null when source is null")
        void sort_ShouldReturnNull_WhenSourceIsNull() {
            // given

            // when
            short[] result = ArrayUtils.sort((short[]) null);

            // then
            then(result).as("Null for null source").isNull();
        }
    }

    @Nested
    class SortIntTests {

        @Test
        @DisplayName("Should sort array in ascending order")
        void sort_ShouldSortArray_WhenHasLength() {
            // given
            int[] source = {3, 1, 2};

            // when
            int[] result = ArrayUtils.sort(source);

            // then
            then(result).as("Sorted array").containsExactly(1, 2, 3);
        }

        @Test
        @DisplayName("Should return null when source is null")
        void sort_ShouldReturnNull_WhenSourceIsNull() {
            // given

            // when
            int[] result = ArrayUtils.sort((int[]) null);

            // then
            then(result).as("Null for null source").isNull();
        }
    }

    @Nested
    class SortLongTests {

        @Test
        @DisplayName("Should sort array in ascending order")
        void sort_ShouldSortArray_WhenHasLength() {
            // given
            long[] source = {3L, 1L, 2L};

            // when
            long[] result = ArrayUtils.sort(source);

            // then
            then(result).as("Sorted array").containsExactly(1L, 2L, 3L);
        }

        @Test
        @DisplayName("Should return null when source is null")
        void sort_ShouldReturnNull_WhenSourceIsNull() {
            // given

            // when
            long[] result = ArrayUtils.sort((long[]) null);

            // then
            then(result).as("Null for null source").isNull();
        }
    }

    @Nested
    class SortFloatTests {

        @Test
        @DisplayName("Should sort array in ascending order")
        void sort_ShouldSortArray_WhenHasLength() {
            // given
            float[] source = {3.0f, 1.0f, 2.0f};

            // when
            float[] result = ArrayUtils.sort(source);

            // then
            then(result).as("Sorted array").containsExactly(1.0f, 2.0f, 3.0f);
        }

        @Test
        @DisplayName("Should return null when source is null")
        void sort_ShouldReturnNull_WhenSourceIsNull() {
            // given

            // when
            float[] result = ArrayUtils.sort((float[]) null);

            // then
            then(result).as("Null for null source").isNull();
        }
    }

    @Nested
    class SortDoubleTests {

        @Test
        @DisplayName("Should sort array in ascending order")
        void sort_ShouldSortArray_WhenHasLength() {
            // given
            double[] source = {3.0, 1.0, 2.0};

            // when
            double[] result = ArrayUtils.sort(source);

            // then
            then(result).as("Sorted array").containsExactly(1.0, 2.0, 3.0);
        }

        @Test
        @DisplayName("Should return null when source is null")
        void sort_ShouldReturnNull_WhenSourceIsNull() {
            // given

            // when
            double[] result = ArrayUtils.sort((double[]) null);

            // then
            then(result).as("Null for null source").isNull();
        }
    }

    @Nested
    class SortObjectTests {

        @Test
        @DisplayName("Should sort array in ascending order")
        void sort_ShouldSortArray_WhenHasLength() {
            // given
            String[] source = {"c", "a", "b"};

            // when
            String[] result = ArrayUtils.sort(source);

            // then
            then(result).as("Sorted array").containsExactly("a", "b", "c");
        }

        @Test
        @DisplayName("Should return null when source is null")
        void sort_ShouldReturnNull_WhenSourceIsNull() {
            // given

            // when
            String[] result = ArrayUtils.sort((String[]) null);

            // then
            then(result).as("Null for null source").isNull();
        }
    }

    @Nested
    class SortWithComparatorTests {

        @Test
        @DisplayName("Should sort array with comparator")
        void sort_ShouldSortArrayWithComparator_WhenHasLength() {
            // given
            String[] source = {"c", "a", "b"};
            Comparator<String> comparator = String::compareTo;

            // when
            String[] result = ArrayUtils.sort(source, comparator);

            // then
            then(result).as("Sorted with comparator").containsExactly("a", "b", "c");
        }

        @Test
        @DisplayName("Should return null when source is null")
        void sort_ShouldReturnNull_WhenSourceIsNull() {
            // given
            Comparator<String> comparator = String::compareTo;

            // when
            String[] result = ArrayUtils.sort(null, comparator);

            // then
            then(result).as("Null for null source").isNull();
        }
    }

    @Nested
    class SubarrayBooleanTests {

        @Test
        @DisplayName("Should return subarray for valid range")
        void subarray_ShouldReturnSubarray_WhenValidRange() {
            // given
            boolean[] source = {true, false, true, false};

            // when
            boolean[] result = ArrayUtils.subarray(source, 1, 3);

            // then
            then(result).as("Subarray from 1 to 3").containsExactly(false, true);
        }

        @Test
        @DisplayName("Should return empty when source is null")
        void subarray_ShouldReturnEmpty_WhenSourceIsNull() {
            // given

            // when
            boolean[] result = ArrayUtils.subarray((boolean[]) null, 0, 1);

            // then
            then(result).as("Null source returns empty").isEmpty();
        }

        @Test
        @DisplayName("Should handle negative start as zero")
        void subarray_ShouldTreatNegativeStartAsZero_WhenStartNegative() {
            // given
            boolean[] source = {true, false};

            // when
            boolean[] result = ArrayUtils.subarray(source, -5, 1);

            // then
            then(result).as("Negative start treated as 0").containsExactly(true);
        }

        @Test
        @DisplayName("Should return empty when start greater than end")
        void subarray_ShouldReturnEmpty_WhenStartGreaterThanEnd() {
            // given
            boolean[] source = {true, false};

            // when
            boolean[] result = ArrayUtils.subarray(source, 2, 1);

            // then
            then(result).as("Empty when start > end").isEmpty();
        }
    }

    @Nested
    class SubarrayByteTests {

        @Test
        @DisplayName("Should return subarray for valid range")
        void subarray_ShouldReturnSubarray_WhenValidRange() {
            // given
            byte[] source = {1, 2, 3, 4};

            // when
            byte[] result = ArrayUtils.subarray(source, 1, 3);

            // then
            then(result).as("Subarray from 1 to 3").containsExactly((byte) 2, (byte) 3);
        }

        @Test
        @DisplayName("Should return empty when source is null")
        void subarray_ShouldReturnEmpty_WhenSourceIsNull() {
            // given

            // when
            byte[] result = ArrayUtils.subarray((byte[]) null, 0, 1);

            // then
            then(result).as("Null source returns empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty array when newSize <= 0")
        void subarray_ShouldReturnEmptyArray_WhenNewSizeLessOrEqualZero() {
            // given
            byte[] source = {1, 2};

            // when
            byte[] result = ArrayUtils.subarray(source, 2, 1);

            // then
            then(result).as("Empty array when range invalid").isEmpty();
        }
    }

    @Nested
    class SubarrayCharTests {

        @Test
        @DisplayName("Should return subarray for valid range")
        void subarray_ShouldReturnSubarray_WhenValidRange() {
            // given
            char[] source = {'a', 'b', 'c', 'd'};

            // when
            char[] result = ArrayUtils.subarray(source, 1, 3);

            // then
            then(result).as("Subarray from 1 to 3").containsExactly('b', 'c');
        }

        @Test
        @DisplayName("Should return empty when source is null")
        void subarray_ShouldReturnEmpty_WhenSourceIsNull() {
            // given

            // when
            char[] result = ArrayUtils.subarray((char[]) null, 0, 1);

            // then
            then(result).as("Null source returns empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty array when newSize <= 0")
        void subarray_ShouldReturnEmptyArray_WhenNewSizeLessOrEqualZero() {
            // given
            char[] source = {'a', 'b'};

            // when
            char[] result = ArrayUtils.subarray(source, 2, 1);

            // then
            then(result).as("Empty array when range invalid").isEmpty();
        }
    }

    @Nested
    class SubarrayShortTests {

        @Test
        @DisplayName("Should return subarray for valid range")
        void subarray_ShouldReturnSubarray_WhenValidRange() {
            // given
            short[] source = {1, 2, 3, 4};

            // when
            short[] result = ArrayUtils.subarray(source, 1, 3);

            // then
            then(result).as("Subarray from 1 to 3").containsExactly((short) 2, (short) 3);
        }

        @Test
        @DisplayName("Should return empty when source is null")
        void subarray_ShouldReturnEmpty_WhenSourceIsNull() {
            // given

            // when
            short[] result = ArrayUtils.subarray((short[]) null, 0, 1);

            // then
            then(result).as("Null source returns empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty array when newSize <= 0")
        void subarray_ShouldReturnEmptyArray_WhenNewSizeLessOrEqualZero() {
            // given
            short[] source = {1, 2};

            // when
            short[] result = ArrayUtils.subarray(source, 2, 1);

            // then
            then(result).as("Empty array when range invalid").isEmpty();
        }
    }

    @Nested
    class SubarrayIntTests {

        @Test
        @DisplayName("Should return subarray for valid range")
        void subarray_ShouldReturnSubarray_WhenValidRange() {
            // given
            int[] source = {1, 2, 3, 4};

            // when
            int[] result = ArrayUtils.subarray(source, 1, 3);

            // then
            then(result).as("Subarray from 1 to 3").containsExactly(2, 3);
        }

        @Test
        @DisplayName("Should return empty when source is null")
        void subarray_ShouldReturnEmpty_WhenSourceIsNull() {
            // given

            // when
            int[] result = ArrayUtils.subarray((int[]) null, 0, 1);

            // then
            then(result).as("Null source returns empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty array when newSize <= 0")
        void subarray_ShouldReturnEmptyArray_WhenNewSizeLessOrEqualZero() {
            // given
            int[] source = {1, 2};

            // when
            int[] result = ArrayUtils.subarray(source, 2, 1);

            // then
            then(result).as("Empty array when range invalid").isEmpty();
        }
    }

    @Nested
    class SubarrayLongTests {

        @Test
        @DisplayName("Should return subarray for valid range")
        void subarray_ShouldReturnSubarray_WhenValidRange() {
            // given
            long[] source = {1L, 2L, 3L, 4L};

            // when
            long[] result = ArrayUtils.subarray(source, 1, 3);

            // then
            then(result).as("Subarray from 1 to 3").containsExactly(2L, 3L);
        }

        @Test
        @DisplayName("Should return empty when source is null")
        void subarray_ShouldReturnEmpty_WhenSourceIsNull() {
            // given

            // when
            long[] result = ArrayUtils.subarray((long[]) null, 0, 1);

            // then
            then(result).as("Null source returns empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty array when newSize <= 0")
        void subarray_ShouldReturnEmptyArray_WhenNewSizeLessOrEqualZero() {
            // given
            long[] source = {1L, 2L};

            // when
            long[] result = ArrayUtils.subarray(source, 2, 1);

            // then
            then(result).as("Empty array when range invalid").isEmpty();
        }
    }

    @Nested
    class SubarrayFloatTests {

        @Test
        @DisplayName("Should return subarray for valid range")
        void subarray_ShouldReturnSubarray_WhenValidRange() {
            // given
            float[] source = {1.0f, 2.0f, 3.0f, 4.0f};

            // when
            float[] result = ArrayUtils.subarray(source, 1, 3);

            // then
            then(result).as("Subarray from 1 to 3").containsExactly(2.0f, 3.0f);
        }

        @Test
        @DisplayName("Should return empty when source is null")
        void subarray_ShouldReturnEmpty_WhenSourceIsNull() {
            // given

            // when
            float[] result = ArrayUtils.subarray((float[]) null, 0, 1);

            // then
            then(result).as("Null source returns empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty array when newSize <= 0")
        void subarray_ShouldReturnEmptyArray_WhenNewSizeLessOrEqualZero() {
            // given
            float[] source = {1.0f, 2.0f};

            // when
            float[] result = ArrayUtils.subarray(source, 2, 1);

            // then
            then(result).as("Empty array when range invalid").isEmpty();
        }
    }

    @Nested
    class SubarrayDoubleTests {

        @Test
        @DisplayName("Should return subarray for valid range")
        void subarray_ShouldReturnSubarray_WhenValidRange() {
            // given
            double[] source = {1.0, 2.0, 3.0, 4.0};

            // when
            double[] result = ArrayUtils.subarray(source, 1, 3);

            // then
            then(result).as("Subarray from 1 to 3").containsExactly(2.0, 3.0);
        }

        @Test
        @DisplayName("Should return empty when source is null")
        void subarray_ShouldReturnEmpty_WhenSourceIsNull() {
            // given

            // when
            double[] result = ArrayUtils.subarray((double[]) null, 0, 1);

            // then
            then(result).as("Null source returns empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty array when newSize <= 0")
        void subarray_ShouldReturnEmptyArray_WhenNewSizeLessOrEqualZero() {
            // given
            double[] source = {1.0, 2.0};

            // when
            double[] result = ArrayUtils.subarray(source, 2, 1);

            // then
            then(result).as("Empty array when range invalid").isEmpty();
        }
    }

    @Nested
    class SubarrayObjectTests {

        @Test
        @DisplayName("Should return subarray for valid range")
        void subarray_ShouldReturnSubarray_WhenValidRange() {
            // given
            String[] source = {"a", "b", "c", "d"};

            // when
            String[] result = ArrayUtils.subarray(source, 1, 3);

            // then
            then(result).as("Subarray from 1 to 3").containsExactly("b", "c");
        }

        @Test
        @DisplayName("Should return null when source is null")
        void subarray_ShouldReturnNull_WhenSourceIsNull() {
            // given

            // when
            String[] result = ArrayUtils.subarray((String[]) null, 0, 1);

            // then
            then(result).as("Null for null source").isNull();
        }

        @Test
        @DisplayName("Should return empty array when newSize <= 0")
        void subarray_ShouldReturnEmptyArray_WhenNewSizeLessOrEqualZero() {
            // given
            String[] source = {"a", "b"};

            // when
            String[] result = ArrayUtils.subarray(source, 2, 1);

            // then
            then(result).as("Empty array when range invalid").isEmpty();
        }

        @Test
        @DisplayName("Should cap end at array length")
        void subarray_ShouldCapEndAtLength_WhenEndExceedsLength() {
            // given
            String[] source = {"a", "b"};

            // when
            String[] result = ArrayUtils.subarray(source, 0, 10);

            // then
            then(result).as("End capped at length").containsExactly("a", "b");
        }
    }

    // -------------------------------------------------------------------------
    // Helpers and test doubles
    // -------------------------------------------------------------------------

    static Comparator<String> createStringComparator() {
        return (_s1, _s2) -> 0;
    }
}
