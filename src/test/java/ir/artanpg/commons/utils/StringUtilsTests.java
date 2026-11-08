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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

/**
 * Unit tests for {@link StringUtils}.
 *
 * @author Mohammad Yazdian
 */
class StringUtilsTests {

    @Nested
    class ConstructorTests {

        @Test
        @DisplayName("Throws UnsupportedOperationException when reflectively instantiated")
        void constructor_ShouldThrowUnsupportedOperationException_WhenReflectivelyInstantiated() {
            // given
            Constructor<StringUtils> ctor;
            try {
                ctor = StringUtils.class.getDeclaredConstructor();
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
            ctor.setAccessible(true);

            // when & then
            thenThrownBy(ctor::newInstance)
                    .as("Private constructor must reject instantiation")
                    .hasRootCauseInstanceOf(UnsupportedOperationException.class)
                    .hasRootCauseMessage("This class cannot be instantiated");
        }
    }

    @Nested
    class AbbreviateMaxWidth {

        @Test
        @DisplayName("Abbreviates a long string with default ellipsis when it exceeds max width")
        void abbreviate_ShouldAbbreviateWithEllipsis_WhenStringExceedsMaxWidth() {
            // given
            String input = "Hello World";

            // when
            String result = StringUtils.abbreviate(input, 7);

            // then
            then(result).as("Expected abbreviated string 'Hell...'").isEqualTo("Hell...");
        }

        @Test
        @DisplayName("Returns the original string when its length fits within max width")
        void abbreviate_ShouldReturnOriginal_WhenStringFitsWithinMaxWidth() {
            // given
            String input = "Hello";

            // when
            String result = StringUtils.abbreviate(input, 10);

            // then
            then(result).as("String shorter than max width should be unchanged").isEqualTo("Hello");
        }

        @ParameterizedTest
        @ValueSource(strings = {"", "   ", "\t\n"})
        @DisplayName("Returns empty when input string is empty or blank")
        void abbreviate_ShouldReturnEmpty_WhenStringIsEmptyOrBlank(String input) {
            // given
            // (input provided by @ValueSource)

            // when
            String result = StringUtils.abbreviate(input, 10);

            // then
            then(result).as("Input '%s' should yield empty string", input).isEqualTo(StringUtils.EMPTY);
        }

        @Test
        @DisplayName("Returns empty when input string is null")
        void abbreviate_ShouldReturnEmpty_WhenStringIsNull() {
            // given
            String input = null;

            // when
            String result = StringUtils.abbreviate(input, 10);

            // then
            then(result).as("Null input should yield empty string").isEqualTo(StringUtils.EMPTY);
        }

        @Test
        @DisplayName("Throws IllegalArgumentException when max width is smaller than ellipsis minimum")
        void abbreviate_ShouldThrowIllegalArgumentException_WhenMaxWidthIsTooSmall() {
            // given
            String input = "Hello World";

            // when & then
            thenThrownBy(() -> StringUtils.abbreviate(input, 3))
                    .as("Width 3 cannot fit ellipsis of length 3 plus a char")
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Minimum abbreviation width is 4");
        }
    }

    @Nested
    class AbbreviateMarker {

        @Test
        @DisplayName("Abbreviates using a custom marker when string exceeds max width")
        void abbreviate_ShouldAbbreviateWithMarker_WhenStringExceedsMaxWidth() {
            // given
            String input = "Hello World";

            // when
            String result = StringUtils.abbreviate(input, "~", 7);

            // then
            then(result).as("Expected 'Hello ~'").isEqualTo("Hello ~");
        }

        @Test
        @DisplayName("Returns empty when input string is null")
        void abbreviate_ShouldReturnEmpty_WhenStringIsNull() {
            // given
            String input = null;

            // when
            String result = StringUtils.abbreviate(input, "...", 10);

            // then
            then(result).as("Null input should yield empty string").isEqualTo(StringUtils.EMPTY);
        }

        @Test
        @DisplayName("Returns empty when marker is null")
        void abbreviate_ShouldReturnEmpty_WhenMarkerIsNull() {
            // given
            String input = "Hello World";

            // when
            String result = StringUtils.abbreviate(input, null, 10);

            // then
            then(result).as("Null marker should yield empty string").isEqualTo(StringUtils.EMPTY);
        }

        @Test
        @DisplayName("Returns empty when marker is empty")
        void abbreviate_ShouldReturnEmpty_WhenMarkerIsEmpty() {
            // given
            String input = "Hello World";

            // when
            String result = StringUtils.abbreviate(input, "", 10);

            // then
            then(result).as("Empty marker should yield empty string").isEqualTo(StringUtils.EMPTY);
        }

        @Test
        @DisplayName("Returns empty when marker is blank")
        void abbreviate_ShouldReturnEmpty_WhenMarkerIsBlank() {
            // given
            String input = "Hello World";

            // when
            String result = StringUtils.abbreviate(input, "   ", 10);

            // then
            then(result).as("Blank marker should yield empty string").isEqualTo(StringUtils.EMPTY);
        }

        @Test
        @DisplayName("Throws IllegalArgumentException when max width is too small for the marker")
        void abbreviate_ShouldThrowIllegalArgumentException_WhenMaxWidthIsTooSmall() {
            // given
            String input = "Hello World";

            // when & then
            thenThrownBy(() -> StringUtils.abbreviate(input, "~", 1))
                    .as("Width 1 cannot fit marker of length 1 plus a char")
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Minimum abbreviation width is 2");
        }
    }

    @Nested
    class AppendIfMissing {

        @Test
        @DisplayName("Appends suffix when missing")
        void appendIfMissing_ShouldAppend_WhenSuffixMissing() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.appendIfMissing(input, ".txt");

            // then
            then(result).as("Appended").isEqualTo("abc.txt");
        }

        @Test
        @DisplayName("Returns original when suffix present")
        void appendIfMissing_ShouldReturnOriginal_WhenSuffixPresent() {
            // given
            String input = "abc.txt";

            // when
            String result = StringUtils.appendIfMissing(input, ".txt");

            // then
            then(result).as("Unchanged").isEqualTo("abc.txt");
        }

        @Test
        @DisplayName("Returns original when input is null")
        void appendIfMissing_ShouldReturnNull_WhenInputIsNull() {
            // given
            String input = null;

            // when
            String result = StringUtils.appendIfMissing(input, ".txt");

            // then
            then(result).as("Null stays null").isNull();
        }

        @Test
        @DisplayName("Returns original when input is empty")
        void appendIfMissing_ShouldReturnEmpty_WhenInputIsEmpty() {
            // given
            String input = "";

            // when
            String result = StringUtils.appendIfMissing(input, ".txt");

            // then
            then(result).as("Empty").isEmpty();
        }

        @Test
        @DisplayName("Returns original when suffix is null")
        void appendIfMissing_ShouldReturnOriginal_WhenSuffixIsNull() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.appendIfMissing(input, null);

            // then
            then(result).as("Unchanged").isEqualTo("abc");
        }

        @Test
        @DisplayName("Returns original when suffix is empty")
        void appendIfMissing_ShouldReturnOriginal_WhenSuffixIsEmpty() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.appendIfMissing(input, "");

            // then
            then(result).as("Unchanged").isEqualTo("abc");
        }
    }

    @Nested
    class AppendIfMissingIgnoreCase {

        @Test
        @DisplayName("Appends suffix when missing ignoring case")
        void appendIfMissingIgnoreCase_ShouldAppend_WhenSuffixMissing() {
            // given
            String input = "file";

            // when
            String result = StringUtils.appendIfMissingIgnoreCase(input, ".TXT");

            // then
            then(result).as("Appended").isEqualTo("file.TXT");
        }

        @Test
        @DisplayName("Returns original when suffix present ignoring case")
        void appendIfMissingIgnoreCase_ShouldReturnOriginal_WhenSuffixPresentIgnoringCase() {
            // given
            String input = "file.txt";

            // when
            String result = StringUtils.appendIfMissingIgnoreCase(input, ".TXT");

            // then
            then(result).as("Unchanged").isEqualTo("file.txt");
        }

        @Test
        @DisplayName("Returns original when input is null")
        void appendIfMissingIgnoreCase_ShouldReturnNull_WhenInputIsNull() {
            // given
            String input = null;

            // when
            String result = StringUtils.appendIfMissingIgnoreCase(input, ".txt");

            // then
            then(result).as("Null stays null").isNull();
        }

        @Test
        @DisplayName("Returns original when input is empty")
        void appendIfMissingIgnoreCase_ShouldReturnEmpty_WhenInputIsEmpty() {
            // given
            String input = "";

            // when
            String result = StringUtils.appendIfMissingIgnoreCase(input, ".txt");

            // then
            then(result).as("Empty").isEmpty();
        }

        @Test
        @DisplayName("Returns original when suffix is null")
        void appendIfMissingIgnoreCase_ShouldReturnOriginal_WhenSuffixIsNull() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.appendIfMissingIgnoreCase(input, null);

            // then
            then(result).as("Unchanged").isEqualTo("abc");
        }

        @Test
        @DisplayName("Returns original when suffix is empty")
        void appendIfMissingIgnoreCase_ShouldReturnOriginal_WhenSuffixIsEmpty() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.appendIfMissingIgnoreCase(input, "");

            // then
            then(result).as("Unchanged").isEqualTo("abc");
        }
    }

    @Nested
    class Capitalize {

        @Test
        @DisplayName("Capitalizes the first character of a lowercase string")
        void capitalize_ShouldCapitalizeFirstCharacter_WhenInputStartsLowercase() {
            // given
            String input = "hello";

            // when
            String result = StringUtils.capitalize(input);

            // then
            then(result).as("First character should be title-cased").isEqualTo("Hello");
        }

        @Test
        @DisplayName("Returns the input unchanged when first character is already uppercase")
        void capitalize_ShouldReturnOriginal_WhenAlreadyCapitalized() {
            // given
            String input = "Hello";

            // when
            String result = StringUtils.capitalize(input);

            // then
            then(result).as("Same string should be returned").isEqualTo("Hello");
        }

        @Test
        @DisplayName("Returns null for null input")
        void capitalize_ShouldReturnNull_WhenInputIsNull() {
            // given
            String input = null;

            // when
            String result = StringUtils.capitalize(input);

            // then
            then(result).as("Null input should yield null").isNull();
        }

        @Test
        @DisplayName("Returns empty string for empty input")
        void capitalize_ShouldReturnEmpty_WhenInputIsEmpty() {
            // given
            String input = "";

            // when
            String result = StringUtils.capitalize(input);

            // then
            then(result).as("Empty input should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Returns blank input unchanged")
        void capitalize_ShouldReturnBlank_WhenInputIsBlank() {
            // given
            String input = "   ";

            // when
            String result = StringUtils.capitalize(input);

            // then
            then(result).as("Blank input should be returned unchanged").isEqualTo("   ");
        }
    }

    @Nested
    class CapitalizeFully {

        @Test
        @DisplayName("Capitalizes all whitespace-separated words")
        void capitalizeFully_ShouldCapitalizeWords_WhenInputHasWords() {
            // given
            String input = "hello WORLD";

            // when
            String result = StringUtils.capitalizeFully(input);

            // then
            then(result).as("Each word capitalized").isEqualTo("Hello World");
        }

        @Test
        @DisplayName("Returns null for null input")
        void capitalizeFully_ShouldReturnNull_WhenInputIsNull() {
            // given
            String input = null;

            // when
            String result = StringUtils.capitalizeFully(input);

            // then
            then(result).as("Null stays null").isNull();
        }

        @Test
        @DisplayName("Returns empty for empty input")
        void capitalizeFully_ShouldReturnEmpty_WhenInputIsEmpty() {
            // given
            String input = "";

            // when
            String result = StringUtils.capitalizeFully(input);

            // then
            then(result).as("Empty").isEmpty();
        }

        @Test
        @DisplayName("Returns blank input unchanged")
        void capitalizeFully_ShouldReturnBlank_WhenInputIsBlank() {
            // given
            String input = "   ";

            // when
            String result = StringUtils.capitalizeFully(input);

            // then
            then(result).as("Blank unchanged").isEqualTo("   ");
        }
    }

    @Nested
    class CenterDefault {

        @Test
        @DisplayName("Centers the string with spaces")
        void center_ShouldCenterWithSpaces_WhenSizeLarger() {
            // given
            String input = "ab";

            // when
            String result = StringUtils.center(input, 6);

            // then
            then(result).as("Centered with spaces").isEqualTo("  ab  ");
        }

        @Test
        @DisplayName("Returns original when size is smaller")
        void center_ShouldReturnOriginal_WhenSizeIsSmaller() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.center(input, 2);

            // then
            then(result).as("No centering").isEqualTo("abc");
        }

        @Test
        @DisplayName("Returns null for null input")
        void center_ShouldReturnNull_WhenInputIsNull() {
            // given
            String input = null;

            // when
            String result = StringUtils.center(input, 4);

            // then
            then(result).as("Null stays null").isNull();
        }

        @Test
        @DisplayName("Returns original when size is zero")
        void center_ShouldReturnOriginal_WhenSizeIsZero() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.center(input, 0);

            // then
            then(result).as("No centering").isEqualTo("abc");
        }
    }

    @Nested
    class CenterChar {

        @Test
        @DisplayName("Centers the string with the given char")
        void center_ShouldCenterWithChar_WhenSizeLarger() {
            // given
            String input = "ab";

            // when
            String result = StringUtils.center(input, 6, 'x');

            // then
            then(result).as("Centered with x").isEqualTo("xxabxx");
        }

        @Test
        @DisplayName("Returns original when size is smaller or equal")
        void center_ShouldReturnOriginal_WhenSizeSmallerOrEqual() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.center(input, 3, 'x');

            // then
            then(result).as("No centering").isEqualTo("abc");
        }

        @Test
        @DisplayName("Returns null for null input")
        void center_ShouldReturnNull_WhenInputIsNull() {
            // given
            String input = null;

            // when
            String result = StringUtils.center(input, 4, 'x');

            // then
            then(result).as("Null stays null").isNull();
        }
    }

    @Nested
    class Chomp {

        @Test
        @DisplayName("Removes trailing LF when followed by CR")
        void chomp_ShouldRemoveLf_WhenStringEndsWithLf() {
            // given
            String input = "abc\n";

            // when
            String result = StringUtils.chomp(input);

            // then
            then(result).as("Trailing LF removed").isEqualTo("abc");
        }

        @Test
        @DisplayName("Removes trailing CR")
        void chomp_ShouldRemoveCr_WhenStringEndsWithCr() {
            // given
            String input = "abc\r";

            // when
            String result = StringUtils.chomp(input);

            // then
            then(result).as("Trailing CR should be removed").isEqualTo("abc");
        }

        @Test
        @DisplayName("Removes trailing CRLF pair")
        void chomp_ShouldRemoveCrLf_WhenStringEndsWithCrLf() {
            // given
            String input = "abc\r\n";

            // when
            String result = StringUtils.chomp(input);

            // then
            then(result).as("Trailing CRLF should be removed").isEqualTo("abc");
        }

        @Test
        @DisplayName("Returns input unchanged when no trailing newline")
        void chomp_ShouldReturnOriginal_WhenNoTrailingNewline() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.chomp(input);

            // then
            then(result).as("No newline to remove").isEqualTo("abc");
        }

        @ParameterizedTest
        @ValueSource(strings = {"\n", "\r"})
        @DisplayName("Returns empty for a single LF or CR char")
        void chomp_ShouldReturnEmpty_WhenSingleNewlineChar(String input) {
            // given
            // (input provided by @ValueSource)

            // when
            String result = StringUtils.chomp(input);

            // then
            then(result).as("Single newline '%s' becomes empty", input).isEmpty();
        }

        @Test
        @DisplayName("Returns a single non-newline char unchanged")
        void chomp_ShouldReturnOriginal_WhenSingleNonNewlineChar() {
            // given
            String input = "a";

            // when
            String result = StringUtils.chomp(input);

            // then
            then(result).as("Single non-newline char stays").isEqualTo("a");
        }

        @ParameterizedTest
        @ValueSource(strings = {"", "   ", "\t"})
        @DisplayName("Returns blank or empty input unchanged because hasLength rejects it")
        void chomp_ShouldReturnOriginal_WhenInputIsBlankOrEmpty(String input) {
            // given
            // (input provided by @ValueSource)

            // when
            String result = StringUtils.chomp(input);

            // then
            then(result).as("Input '%s' returned unchanged", input).isEqualTo(input);
        }

        @Test
        @DisplayName("Returns null for null input")
        void chomp_ShouldReturnNull_WhenInputIsNull() {
            // given
            String input = null;

            // when
            String result = StringUtils.chomp(input);

            // then
            then(result).as("Null stays null").isNull();
        }
    }

    @Nested
    class Chop {

        @Test
        @DisplayName("Removes the last char from a string")
        void chop_ShouldRemoveLastChar_WhenStringHasMultipleChars() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.chop(input);

            // then
            then(result).as("Last char removed").isEqualTo("ab");
        }

        @Test
        @DisplayName("Removes trailing CRLF pair")
        void chop_ShouldRemoveCrLfPair_WhenStringEndsWithCrLf() {
            // given
            String input = "abc\r\n";

            // when
            String result = StringUtils.chop(input);

            // then
            then(result).as("CRLF removed").isEqualTo("abc");
        }

        @ParameterizedTest
        @ValueSource(strings = {"a", "", "   ", "\t"})
        @DisplayName("Returns empty when input has no text or is a single char")
        void chop_ShouldReturnEmpty_WhenStringIsSingleCharOrBlank(String input) {
            // given
            // (input provided by @ValueSource)

            // when
            String result = StringUtils.chop(input);

            // then
            then(result).as("Input '%s' should chop to empty", input).isEmpty();
        }

        @Test
        @DisplayName("Returns empty for null input")
        void chop_ShouldReturnEmpty_WhenInputIsNull() {
            // given
            String input = null;

            // when
            String result = StringUtils.chop(input);

            // then
            then(result).as("Null input yields empty").isEmpty();
        }

        @Test
        @DisplayName("Removes only LF when string ends with LF without preceding CR")
        void chop_ShouldRemoveOnlyLf_WhenStringEndsWithLfWithoutCr() {
            // given
            String input = "abc\n";

            // when
            String result = StringUtils.chop(input);

            // then
            then(result).as("Only LF removed").isEqualTo("abc");
        }
    }

    @Nested
    class CollectionToDelimitedStringDefault {

        @Test
        @DisplayName("Joins collection elements with comma by default")
        void collectionToDelimitedString_ShouldJoinWithComma_WhenCollectionGiven() {
            // given
            List<String> items = Arrays.asList("a", "b", "c");

            // when
            String result = StringUtils.collectionToDelimitedString(items);

            // then
            then(result).as("Default separator is comma").isEqualTo("a,b,c");
        }

        @Test
        @DisplayName("Returns empty for null collection")
        void collectionToDelimitedString_ShouldReturnEmpty_WhenCollectionIsNull() {
            // given
            List<String> items = null;

            // when
            String result = StringUtils.collectionToDelimitedString(items);

            // then
            then(result).as("Null collection yields empty").isEmpty();
        }

        @Test
        @DisplayName("Returns empty for empty collection")
        void collectionToDelimitedString_ShouldReturnEmpty_WhenCollectionIsEmpty() {
            // given
            List<String> items = Collections.emptyList();

            // when
            String result = StringUtils.collectionToDelimitedString(items);

            // then
            then(result).as("Empty collection yields empty").isEmpty();
        }
    }

    @Nested
    class CollectionToDelimitedStringDelimiter {

        @Test
        @DisplayName("Joins collection elements with a custom delimiter")
        void collectionToDelimitedString_ShouldJoinWithDelimiter_WhenDelimiterGiven() {
            // given
            List<String> items = Arrays.asList("a", "b", "c");

            // when
            String result = StringUtils.collectionToDelimitedString(items, "-");

            // then
            then(result).as("Custom delimiter used").isEqualTo("a-b-c");
        }

        @Test
        @DisplayName("Returns empty for null collection")
        void collectionToDelimitedString_ShouldReturnEmpty_WhenCollectionIsNull() {
            // given
            List<String> items = null;

            // when
            String result = StringUtils.collectionToDelimitedString(items, "-");

            // then
            then(result).as("Null collection yields empty").isEmpty();
        }
    }

    @Nested
    class CollectionToDelimitedStringFull {

        @Test
        @DisplayName("Joins with prefix, suffix and delimiter")
        void collectionToDelimitedString_ShouldWrapAndJoin_WhenAllArgsGiven() {
            // given
            List<String> items = Arrays.asList("a", "b");

            // when
            String result = StringUtils.collectionToDelimitedString(items, "-", "[", "]");

            // then
            then(result).as("Wrapped items joined").isEqualTo("[a]-[b]");
        }

        @Test
        @DisplayName("Falls back to comma when delimiter is null")
        void collectionToDelimitedString_ShouldUseComma_WhenDelimiterIsNull() {
            // given
            List<String> items = Arrays.asList("a", "b");

            // when
            String result = StringUtils.collectionToDelimitedString(items, null, null, null);

            // then
            then(result).as("Null delimiter defaults to comma and no prefix/suffix").isEqualTo("a,b");
        }

        @Test
        @DisplayName("Returns empty for null collection")
        void collectionToDelimitedString_ShouldReturnEmpty_WhenCollectionIsNull() {
            // given
            List<String> items = null;

            // when
            String result = StringUtils.collectionToDelimitedString(items, ",", "", "");

            // then
            then(result).as("Null collection yields empty").isEmpty();
        }
    }

    @Nested
    class CommonPrefix {

        @Test
        @DisplayName("Returns the longest common prefix")
        void commonPrefix_ShouldReturnCommon_WhenPrefixExists() {
            // given
            String a = "abcdef";
            String b = "abcxyz";

            // when
            String result = StringUtils.commonPrefix(a, b);

            // then
            then(result).as("'abc'").isEqualTo("abc");
        }

        @ParameterizedTest
        @CsvSource(value = {"abc, xyz", "hello, world", "ab, cd"})
        @DisplayName("Returns empty when no common prefix")
        void commonPrefix_ShouldReturnEmpty_WhenNoCommon(String a, String b) {
            // given
            // (a and b provided by @CsvSource)

            // when
            String result = StringUtils.commonPrefix(a, b);

            // then
            then(result).as("No common prefix between '%s' and '%s'", a, b).isEmpty();
        }

        @Test
        @DisplayName("Returns partial prefix when match breaks mid-loop")
        void commonPrefix_ShouldReturnPartialPrefix_WhenMatchBreaksMidLoop() {
            // given
            String a = "abcX";
            String b = "abcY";

            // when
            String result = StringUtils.commonPrefix(a, b);

            // then
            then(result).as("Only 'abc' matches at the start").isEqualTo("abc");
        }

        @ParameterizedTest
        @CsvSource(value = {
                "'', abc",
                "abc, ''",
                "null, abc",
                "abc, null"
        }, nullValues = "null")
        @DisplayName("Returns empty when either input is null or empty")
        void commonPrefix_ShouldReturnEmpty_WhenAnyIsNullOrEmpty(String a, String b) {
            // given
            // (a and b provided by @CsvSource; null values allowed)

            // when
            String result = StringUtils.commonPrefix(a, b);

            // then
            then(result).as("Null or empty input yields empty").isEmpty();
        }

        @Test
        @DisplayName("Returns single-char prefix when only first char matches")
        void commonPrefix_ShouldReturnSingleChar_WhenMinLengthIsOne() {
            // given
            String a = "a";
            String b = "ab";

            // when
            String result = StringUtils.commonPrefix(a, b);

            // then
            then(result).as("Only 'a' matches").isEqualTo("a");
        }
    }

    @Nested
    class CommonSuffix {

        @Test
        @DisplayName("Returns the longest common suffix")
        void commonSuffix_ShouldReturnCommon_WhenSuffixExists() {
            // given
            String a = "abcdef";
            String b = "xyzdef";

            // when
            String result = StringUtils.commonSuffix(a, b);

            // then
            then(result).as("'def'").isEqualTo("def");
        }

        @ParameterizedTest
        @CsvSource(value = {"abc, xyz", "hello, world", "ab, cd"})
        @DisplayName("Returns empty when no common suffix")
        void commonSuffix_ShouldReturnEmpty_WhenNoCommon(String a, String b) {
            // given
            // (a and b provided by @CsvSource)

            // when
            String result = StringUtils.commonSuffix(a, b);

            // then
            then(result).as("No common suffix between '%s' and '%s'", a, b).isEmpty();
        }

        @Test
        @DisplayName("Returns partial suffix when match breaks mid-loop")
        void commonSuffix_ShouldReturnPartialSuffix_WhenMatchBreaksMidLoop() {
            // given
            String a = "abcdef";
            String b = "XYZf";

            // when
            String result = StringUtils.commonSuffix(a, b);

            // then
            then(result).as("Only 'f' matches at the end").isEqualTo("f");
        }

        @ParameterizedTest
        @CsvSource(value = {
                "'', abc",
                "abc, ''",
                "null, abc",
                "abc, null"
        }, nullValues = "null")
        @DisplayName("Returns empty when either input is null or empty")
        void commonSuffix_ShouldReturnEmpty_WhenAnyIsNullOrEmpty(String a, String b) {
            // given
            // (a and b provided by @CsvSource; null values allowed)

            // when
            String result = StringUtils.commonSuffix(a, b);

            // then
            then(result).as("Null or empty input yields empty").isEmpty();
        }

        @Test
        @DisplayName("Returns single-char suffix when only last char matches")
        void commonSuffix_ShouldReturnSingleChar_WhenMinLengthIsOne() {
            // given
            String a = "a";
            String b = "ba";

            // when
            String result = StringUtils.commonSuffix(a, b);

            // then
            then(result).as("Only 'a' matches at the end").isEqualTo("a");
        }
    }

    @Nested
    class Contains {

        @Test
        @DisplayName("Returns true when the string contains the search substring")
        void contains_ShouldReturnTrue_WhenSearchIsContained() {
            // given
            String string = "abcdef";

            // when
            boolean result = StringUtils.contains(string, "cd");

            // then
            then(result).as("'cd' is inside 'abcdef'").isTrue();
        }

        @Test
        @DisplayName("Returns false when the string does not contain the search substring")
        void contains_ShouldReturnFalse_WhenSearchIsNotContained() {
            // given
            String string = "abcdef";

            // when
            boolean result = StringUtils.contains(string, "xy");

            // then
            then(result).as("'xy' is not inside 'abcdef'").isFalse();
        }

        @Test
        @DisplayName("Returns false for null input string")
        void contains_ShouldReturnFalse_WhenStringIsNull() {
            // given
            String string = null;

            // when
            boolean result = StringUtils.contains(string, "a");

            // then
            then(result).as("Null input string is false").isFalse();
        }

        @Test
        @DisplayName("Returns false for empty input string")
        void contains_ShouldReturnFalse_WhenStringIsEmpty() {
            // given
            String string = "";

            // when
            boolean result = StringUtils.contains(string, "a");

            // then
            then(result).as("Empty input string is false").isFalse();
        }

        @Test
        @DisplayName("Returns false for blank input string")
        void contains_ShouldReturnFalse_WhenStringIsBlank() {
            // given
            String string = "   ";

            // when
            boolean result = StringUtils.contains(string, "a");

            // then
            then(result).as("Blank input string is false").isFalse();
        }

        @Test
        @DisplayName("Returns false for null search")
        void contains_ShouldReturnFalse_WhenSearchIsNull() {
            // given
            String string = "abc";

            // when
            boolean result = StringUtils.contains(string, null);

            // then
            then(result).as("Null search is false").isFalse();
        }

        @Test
        @DisplayName("Returns false for empty search")
        void contains_ShouldReturnFalse_WhenSearchIsEmpty() {
            // given
            String string = "abc";

            // when
            boolean result = StringUtils.contains(string, "");

            // then
            then(result).as("Empty search is false").isFalse();
        }
    }

    @Nested
    class ContainsAny {

        @Test
        @DisplayName("Returns true when the string contains one of the search tokens")
        void containsAny_ShouldReturnTrue_WhenAnySearchMatches() {
            // given
            String string = "hello world";

            // when
            boolean result = StringUtils.containsAny(string, "xyz", "wor");

            // then
            then(result).as("'wor' is contained").isTrue();
        }

        @Test
        @DisplayName("Returns false when no search token is contained")
        void containsAny_ShouldReturnFalse_WhenNoSearchMatches() {
            // given
            String string = "hello";

            // when
            boolean result = StringUtils.containsAny(string, "xyz", "abc");

            // then
            then(result).as("No token present").isFalse();
        }

        @ParameterizedTest
        @ValueSource(strings = {"", "   ", "\t\n"})
        @DisplayName("Returns false when input string is empty or blank")
        void containsAny_ShouldReturnFalse_WhenStringIsEmptyOrBlank(String string) {
            // given
            // (input provided by @ValueSource)

            // when
            boolean result = StringUtils.containsAny(string, "a");

            // then
            then(result).as("Empty/blank input '%s' is false", string).isFalse();
        }

        @Test
        @DisplayName("Returns false for null input string")
        void containsAny_ShouldReturnFalse_WhenStringIsNull() {
            // given
            String string = null;

            // when
            boolean result = StringUtils.containsAny(string, "a");

            // then
            then(result).as("Null input string is false").isFalse();
        }

        @Test
        @DisplayName("Returns false when search array is null")
        void containsAny_ShouldReturnFalse_WhenSearchIsNull() {
            // given
            String string = "abc";

            // when
            boolean result = StringUtils.containsAny(string, (String[]) null);

            // then
            then(result).as("Null search array is false").isFalse();
        }

        @Test
        @DisplayName("Returns false when search array is empty")
        void containsAny_ShouldReturnFalse_WhenSearchIsEmpty() {
            // given
            String string = "abc";
            String[] search = new String[0];

            // when
            boolean result = StringUtils.containsAny(string, search);

            // then
            then(result).as("Empty search array is false").isFalse();
        }

        @Test
        @DisplayName("Returns false when all search tokens are blank")
        void containsAny_ShouldReturnFalse_WhenAllSearchTokensBlank() {
            // given
            String string = "abc";

            // when
            boolean result = StringUtils.containsAny(string, "", "   ");

            // then
            then(result).as("Blank tokens are ignored").isFalse();
        }

        @Test
        @DisplayName("Detects longest pattern via failure chain walk in intermediate node")
        void containsAny_ShouldDetectLongestPattern_WhenFailureChainWalksThroughIntermediateNode() {
            // given
            String input = "abc";

            // when
            boolean result = StringUtils.containsAny(input, "ab", "abc", "b");

            // then
            then(result).as("'abc' matched despite intermediate failure walk").isTrue();
        }

        @Test
        @DisplayName("Detects overlapping patterns with suffix relationship")
        void containsAny_ShouldDetectOverlappingPatterns_WhenPatternIsSuffixOfAnother() {
            // given
            String input = "she";

            // when
            boolean result = StringUtils.containsAny(input, "he", "she");

            // then
            then(result).as("'she' matched and suffix 'he' propagated as end-of-word").isTrue();
        }

        @Test
        @DisplayName("Detects single pattern through failure link to root")
        void containsAny_ShouldDetectSinglePattern_WhenFailureLinksPointToRoot() {
            // given
            String input = "xyzabc";

            // when
            boolean result = StringUtils.containsAny(input, "abc");

            // then
            then(result).as("'abc' matched via direct failure-to-root").isTrue();
        }

        @Test
        @DisplayName("Walks failure chain when search encounters mismatch after partial match")
        void containsAny_ShouldWalkFailureChain_WhenSearchMismatchesMidPattern() {
            // given
            String input = "abd";

            // when
            boolean result = StringUtils.containsAny(input, "abc");

            // then
            then(result).as("Mismatch mid-pattern triggers failure walk then returns false").isFalse();
        }
    }

    @Nested
    class ContainsIgnoreCase {

        @Test
        @DisplayName("Returns true when search matches ignoring case")
        void containsIgnoreCase_ShouldReturnTrue_WhenMatchIgnoringCase() {
            // given
            String input = "HelloWorld";

            // when
            boolean result = StringUtils.containsIgnoreCase(input, "WORLD");

            // then
            then(result).as("Contains ignoring case").isTrue();
        }

        @Test
        @DisplayName("Returns false when no match")
        void containsIgnoreCase_ShouldReturnFalse_WhenNoMatch() {
            // given
            String input = "HelloWorld";

            // when
            boolean result = StringUtils.containsIgnoreCase(input, "xyz");

            // then
            then(result).as("No match").isFalse();
        }

        @Test
        @DisplayName("Returns false for null input")
        void containsIgnoreCase_ShouldReturnFalse_WhenInputIsNull() {
            // given
            String input = null;

            // when
            boolean result = StringUtils.containsIgnoreCase(input, "a");

            // then
            then(result).as("Null input").isFalse();
        }

        @Test
        @DisplayName("Returns false for null search")
        void containsIgnoreCase_ShouldReturnFalse_WhenSearchIsNull() {
            // given
            String input = "abc";

            // when
            boolean result = StringUtils.containsIgnoreCase(input, null);

            // then
            then(result).as("Null search").isFalse();
        }

        @Test
        @DisplayName("Returns false for empty search")
        void containsIgnoreCase_ShouldReturnFalse_WhenSearchIsEmpty() {
            // given
            String input = "abc";

            // when
            boolean result = StringUtils.containsIgnoreCase(input, "");

            // then
            then(result).as("Empty search").isFalse();
        }
    }

    @Nested
    class ContainsWhitespace {

        @Test
        @DisplayName("Returns true when the string contains a whitespace char")
        void containsWhitespace_ShouldReturnTrue_WhenWhitespacePresent() {
            // given
            String input = "ab c";

            // when
            boolean result = StringUtils.containsWhitespace(input);

            // then
            then(result).as("Space is present").isTrue();
        }

        @ParameterizedTest
        @ValueSource(strings = {"abc", "", "123", "!@#"})
        @DisplayName("Returns false when the string contains no whitespace")
        void containsWhitespace_ShouldReturnFalse_WhenNoWhitespace(String input) {
            // given
            // (input provided by @ValueSource)

            // when
            boolean result = StringUtils.containsWhitespace(input);

            // then
            then(result).as("Input '%s' has no whitespace", input).isFalse();
        }

        @Test
        @DisplayName("Returns false for null input")
        void containsWhitespace_ShouldReturnFalse_WhenInputIsNull() {
            // given
            String input = null;

            // when
            boolean result = StringUtils.containsWhitespace(input);

            // then
            then(result).as("Null input is false").isFalse();
        }
    }

    @Nested
    class CountMatches {

        @Test
        @DisplayName("Counts non-overlapping occurrences of a substring")
        void countMatches_ShouldCountOccurrences_WhenSubstringAppears() {
            // given
            String string = "aaaa";

            // when
            int result = StringUtils.countMatches(string, "aa");

            // then
            then(result).as("Non-overlapping matches of 'aa' in 'aaaa'").isEqualTo(2);
        }

        @Test
        @DisplayName("Returns zero when substring does not appear")
        void countMatches_ShouldReturnZero_WhenSubstringAbsent() {
            // given
            String string = "abc";

            // when
            int result = StringUtils.countMatches(string, "z");

            // then
            then(result).as("No matches").isZero();
        }

        @Test
        @DisplayName("Returns zero for null input string")
        void countMatches_ShouldReturnZero_WhenStringIsNull() {
            // given
            String string = null;

            // when
            int result = StringUtils.countMatches(string, "a");

            // then
            then(result).as("Null string").isZero();
        }

        @Test
        @DisplayName("Returns zero for empty input string")
        void countMatches_ShouldReturnZero_WhenStringIsEmpty() {
            // given
            String string = "";

            // when
            int result = StringUtils.countMatches(string, "a");

            // then
            then(result).as("Empty string").isZero();
        }

        @Test
        @DisplayName("Returns zero for null substring")
        void countMatches_ShouldReturnZero_WhenSubstringIsNull() {
            // given
            String string = "abc";

            // when
            int result = StringUtils.countMatches(string, null);

            // then
            then(result).as("Null substring").isZero();
        }

        @Test
        @DisplayName("Returns zero for empty substring")
        void countMatches_ShouldReturnZero_WhenSubstringIsEmpty() {
            // given
            String string = "abc";

            // when
            int result = StringUtils.countMatches(string, "");

            // then
            then(result).as("Empty substring").isZero();
        }
    }

    @Nested
    class CountOccurrencesOf {

        @Test
        @DisplayName("Counts non-overlapping matches")
        void countOccurrencesOf_ShouldCountNonOverlapping_WhenSubstringRepeats() {
            // given
            String input = "aaaa";

            // when
            int result = StringUtils.countOccurrencesOf(input, "aa");

            // then
            then(result).as("Two non-overlapping matches").isEqualTo(2);
        }

        @Test
        @DisplayName("Returns zero when substring absent")
        void countOccurrencesOf_ShouldReturnZero_WhenSubstringAbsent() {
            // given
            String input = "abc";

            // when
            int result = StringUtils.countOccurrencesOf(input, "z");

            // then
            then(result).as("No matches").isZero();
        }

        @Test
        @DisplayName("Returns zero for null input")
        void countOccurrencesOf_ShouldReturnZero_WhenInputIsNull() {
            // given
            String input = null;

            // when
            int result = StringUtils.countOccurrencesOf(input, "a");

            // then
            then(result).as("Null input").isZero();
        }

        @Test
        @DisplayName("Returns zero for empty input")
        void countOccurrencesOf_ShouldReturnZero_WhenInputIsEmpty() {
            // given
            String input = "";

            // when
            int result = StringUtils.countOccurrencesOf(input, "a");

            // then
            then(result).as("Empty input").isZero();
        }

        @Test
        @DisplayName("Returns zero for null substring")
        void countOccurrencesOf_ShouldReturnZero_WhenSubstringIsNull() {
            // given
            String input = "abc";

            // when
            int result = StringUtils.countOccurrencesOf(input, null);

            // then
            then(result).as("Null substring").isZero();
        }

        @Test
        @DisplayName("Returns zero for empty substring")
        void countOccurrencesOf_ShouldReturnZero_WhenSubstringIsEmpty() {
            // given
            String input = "abc";

            // when
            int result = StringUtils.countOccurrencesOf(input, "");

            // then
            then(result).as("Empty substring").isZero();
        }
    }

    @Nested
    class DefaultIfNotHasLength {

        @Test
        @DisplayName("Returns default when string is null")
        void defaultIfNotHasLength_ShouldReturnDefault_WhenStringIsNull() {
            // given
            String input = null;

            // when
            String result = StringUtils.defaultIfNotHasLength(input, "def");

            // then
            then(result).as("Default used").isEqualTo("def");
        }

        @Test
        @DisplayName("Returns default when string is empty")
        void defaultIfNotHasLength_ShouldReturnDefault_WhenStringIsEmpty() {
            // given
            String input = "";

            // when
            String result = StringUtils.defaultIfNotHasLength(input, "def");

            // then
            then(result).as("Default used").isEqualTo("def");
        }

        @Test
        @DisplayName("Returns input when string is non-empty")
        void defaultIfNotHasLength_ShouldReturnInput_WhenStringHasLength() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.defaultIfNotHasLength(input, "def");

            // then
            then(result).as("Original returned").isEqualTo("abc");
        }
    }

    @Nested
    class DefaultIfNotHasText {

        @Test
        @DisplayName("Returns default when string is null")
        void defaultIfNotHasText_ShouldReturnDefault_WhenStringIsNull() {
            // given
            String input = null;

            // when
            String result = StringUtils.defaultIfNotHasText(input, "def");

            // then
            then(result).as("Default used").isEqualTo("def");
        }

        @Test
        @DisplayName("Returns default when string is empty")
        void defaultIfNotHasText_ShouldReturnDefault_WhenStringIsEmpty() {
            // given
            String input = "";

            // when
            String result = StringUtils.defaultIfNotHasText(input, "def");

            // then
            then(result).as("Default used").isEqualTo("def");
        }

        @Test
        @DisplayName("Returns default when string is blank")
        void defaultIfNotHasText_ShouldReturnDefault_WhenStringIsBlank() {
            // given
            String input = "   ";

            // when
            String result = StringUtils.defaultIfNotHasText(input, "def");

            // then
            then(result).as("Default used for blank").isEqualTo("def");
        }

        @Test
        @DisplayName("Returns input when string has text")
        void defaultIfNotHasText_ShouldReturnInput_WhenStringHasText() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.defaultIfNotHasText(input, "def");

            // then
            then(result).as("Original returned").isEqualTo("abc");
        }
    }

    @Nested
    class DefaultStringSingleArg {

        @Test
        @DisplayName("Returns input when not null")
        void defaultString_ShouldReturnInput_WhenNotNull() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.defaultString(input);

            // then
            then(result).as("Original returned").isEqualTo("abc");
        }

        @Test
        @DisplayName("Returns empty when input is null")
        void defaultString_ShouldReturnEmpty_WhenNull() {
            // given
            String input = null;

            // when
            String result = StringUtils.defaultString(input);

            // then
            then(result).as("Empty returned").isEmpty();
        }
    }

    @Nested
    class DefaultStringTwoArgs {

        @Test
        @DisplayName("Returns input when not null")
        void defaultString_ShouldReturnInput_WhenNotNull() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.defaultString(input, "def");

            // then
            then(result).as("Original returned").isEqualTo("abc");
        }

        @Test
        @DisplayName("Returns default when input is null")
        void defaultString_ShouldReturnDefault_WhenNull() {
            // given
            String input = null;

            // when
            String result = StringUtils.defaultString(input, "def");

            // then
            then(result).as("Default returned").isEqualTo("def");
        }
    }

    @Nested
    class DeleteAny {

        @Test
        @DisplayName("Removes all given characters from the input")
        void deleteAny_ShouldRemoveChars_WhenCharsPresent() {
            // given
            String input = "hello world";

            // when
            String result = StringUtils.deleteAny(input, "lo");

            // then
            then(result).as("l and o removed").isEqualTo("he wrd");
        }

        @ParameterizedTest
        @ValueSource(strings = {"abc", "hello", "a-b-c"})
        @DisplayName("Returns original instance when no chars are removed")
        void deleteAny_ShouldReturnSameInstance_WhenNoCharsRemoved(String input) {
            // given
            // (input provided by @ValueSource)

            // when
            String result = StringUtils.deleteAny(input, "z");

            // then
            then(result).as("Same instance returned for '%s'", input).isSameAs(input);
        }

        @Test
        @DisplayName("Returns null for null input")
        void deleteAny_ShouldReturnNull_WhenInputIsNull() {
            // given
            String input = null;

            // when
            String result = StringUtils.deleteAny(input, "a");

            // then
            then(result).as("Null stays null").isNull();
        }

        @Test
        @DisplayName("Returns input unchanged when charsToDelete is null")
        void deleteAny_ShouldReturnInput_WhenCharsToDeleteIsNull() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.deleteAny(input, null);

            // then
            then(result).as("Null charsToDelete returns input").isSameAs(input);
        }

        @Test
        @DisplayName("Returns input unchanged when charsToDelete is empty")
        void deleteAny_ShouldReturnInput_WhenCharsToDeleteIsEmpty() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.deleteAny(input, "");

            // then
            then(result).as("Empty charsToDelete returns input").isSameAs(input);
        }
    }

    @Nested
    class DeleteWhitespace {

        @Test
        @DisplayName("Removes all whitespace chars")
        void deleteWhitespace_ShouldRemoveWhitespace_WhenWhitespacePresent() {
            // given
            String input = " a b\tc ";

            // when
            String result = StringUtils.deleteWhitespace(input);

            // then
            then(result).as("All whitespace removed").isEqualTo("abc");
        }

        @Test
        @DisplayName("Returns same instance when no whitespace")
        void deleteWhitespace_ShouldReturnSameInstance_WhenNoWhitespace() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.deleteWhitespace(input);

            // then
            then(result).as("Same instance returned").isSameAs(input);
        }

        @Test
        @DisplayName("Returns empty when input is all whitespace")
        void deleteWhitespace_ShouldReturnEmpty_WhenAllWhitespace() {
            // given
            String input = "   ";

            // when
            String result = StringUtils.deleteWhitespace(input);

            // then
            then(result).as("All whitespace becomes empty").isEmpty();
        }

        @Test
        @DisplayName("Returns null for null input")
        void deleteWhitespace_ShouldReturnNull_WhenInputIsNull() {
            // given
            String input = null;

            // when
            String result = StringUtils.deleteWhitespace(input);

            // then
            then(result).as("Null stays null").isNull();
        }

        @Test
        @DisplayName("Returns empty for empty input")
        void deleteWhitespace_ShouldReturnEmpty_WhenInputIsEmpty() {
            // given
            String input = "";

            // when
            String result = StringUtils.deleteWhitespace(input);

            // then
            then(result).as("Empty stays empty").isEmpty();
        }
    }

    @Nested
    class DelimitedListToStringArray2 {

        @Test
        @DisplayName("Splits string on delimiter")
        void delimitedListToStringArray_ShouldSplitOnDelimiter_WhenDelimiterPresent() {
            // given
            String input = "a,b,c";

            // when
            String[] result = StringUtils.delimitedListToStringArray(input, ",");

            // then
            then(result).as("Three tokens").containsExactly("a", "b", "c");
        }

        @Test
        @DisplayName("Returns empty array for null input")
        void delimitedListToStringArray_ShouldReturnEmpty_WhenInputIsNull() {
            // given
            String input = null;

            // when
            String[] result = StringUtils.delimitedListToStringArray(input, ",");

            // then
            then(result).as("Empty array").isEmpty();
        }

        @Test
        @DisplayName("Returns single-element array when delimiter is not present in input")
        void delimitedListToStringArray_ShouldReturnWholeStringAsSingleElement_WhenDelimiterNotPresent() {
            // given
            String input = "abc";

            // when
            String[] result = StringUtils.delimitedListToStringArray(input, ",", null);

            // then
            then(result).as("No delimiter present, whole string as one token").containsExactly("abc");
        }

        @Test
        @DisplayName("Returns empty array when delimiter and input are both empty")
        void delimitedListToStringArray_ShouldReturnEmpty_WhenDelimiterAndInputAreEmpty() {
            // given
            String input = "";

            // when
            String[] result = StringUtils.delimitedListToStringArray(input, "", null);

            // then
            then(result).as("No chars to split").isEmpty();
        }

        @Test
        @DisplayName("Splits into chars and deletes given chars, leaving empty tokens for removed chars")
        void delimitedListToStringArray_ShouldSplitAndDelete_WhenDelimiterEmptyAndCharsToDeleteGiven() {
            // given
            String input = "a-b";

            // when
            String[] result = StringUtils.delimitedListToStringArray(input, "", "-");

            // then
            then(result)
                    .as("Dash becomes empty token in split output")
                    .containsExactly("a", "", "b");
        }
    }

    @Nested
    class DelimitedListToStringArray3 {

        @Test
        @DisplayName("Splits with chars-to-delete applied to each token")
        void delimitedListToStringArray_ShouldDeleteCharsInTokens_WhenCharsToDeleteGiven() {
            // given
            String input = "a-b,c-d";

            // when
            String[] result = StringUtils.delimitedListToStringArray(input, ",", "-");

            // then
            then(result).as("Dashes removed from tokens").containsExactly("ab", "cd");
        }

        @Test
        @DisplayName("Returns single-element array when delimiter is null")
        void delimitedListToStringArray_ShouldReturnWhole_WhenDelimiterIsNull() {
            // given
            String input = "abc";

            // when
            String[] result = StringUtils.delimitedListToStringArray(input, null, null);

            // then
            then(result).as("Whole string as single element").containsExactly("abc");
        }

        @Test
        @DisplayName("Splits into individual chars when delimiter is empty")
        void delimitedListToStringArray_ShouldSplitChars_WhenDelimiterEmpty() {
            // given
            String input = "abc";

            // when
            String[] result = StringUtils.delimitedListToStringArray(input, "", null);

            // then
            then(result).as("Split into chars").containsExactly("a", "b", "c");
        }

        @Test
        @DisplayName("Returns empty array for empty input")
        void delimitedListToStringArray_ShouldReturnEmpty_WhenInputIsEmpty() {
            // given
            String input = "";

            // when
            String[] result = StringUtils.delimitedListToStringArray(input, ",", null);

            // then
            then(result).as("Empty array").isEmpty();
        }

        @Test
        @DisplayName("Returns empty array for null input")
        void delimitedListToStringArray_ShouldReturnEmpty_WhenInputIsNull() {
            // given
            String input = null;

            // when
            String[] result = StringUtils.delimitedListToStringArray(input, ",", null);

            // then
            then(result).as("Empty array").isEmpty();
        }
    }

    @Nested
    class Difference {

        @Test
        @DisplayName("Returns the tail of the second string where it differs")
        void difference_ShouldReturnTailDifference_WhenSecondIsLongerAndDiffers() {
            // given
            String a = "abc";
            String b = "abXYZ";

            // when
            String result = StringUtils.difference(a, b);

            // then
            then(result).as("Tail after common prefix").isEqualTo("XYZ");
        }

        @Test
        @DisplayName("Returns empty when second string is a strict prefix of the first")
        void difference_ShouldReturnEmpty_WhenSecondIsPrefixOfFirst() {
            // given
            String a = "abcdef";
            String b = "abc";

            // when
            String result = StringUtils.difference(a, b);

            // then
            then(result).as("Second ends exactly at the difference point").isEmpty();
        }

        @Test
        @DisplayName("Returns empty when first string is a prefix of the second")
        void difference_ShouldReturnEmpty_WhenFirstIsPrefixOfSecond() {
            // given
            String a = "ab";
            String b = "abcdef";

            // when
            String result = StringUtils.difference(a, b);

            // then
            then(result).as("Difference point is at end of first").isEqualTo("cdef");
        }

        @Test
        @DisplayName("Returns empty when strings are equal")
        void difference_ShouldReturnEmpty_WhenStringsEqual() {
            // given
            String a = "abc";

            // when
            String result = StringUtils.difference(a, "abc");

            // then
            then(result).as("Equal strings produce empty").isEmpty();
        }

        @Test
        @DisplayName("Returns second string when first is null")
        void difference_ShouldReturnSecond_WhenFirstIsNull() {
            // given
            String a = null;

            // when
            String result = StringUtils.difference(a, "xyz");

            // then
            then(result).as("Null first yields second").isEqualTo("xyz");
        }

        @Test
        @DisplayName("Returns first string when second is null")
        void difference_ShouldReturnFirst_WhenSecondIsNull() {
            // given
            String b = null;

            // when
            String result = StringUtils.difference("abc", b);

            // then
            then(result).as("Null second yields first").isEqualTo("abc");
        }

        @Test
        @DisplayName("Returns second string when first is empty")
        void difference_ShouldReturnSecond_WhenFirstIsEmpty() {
            // given
            String a = "";

            // when
            String result = StringUtils.difference(a, "xyz");

            // then
            then(result).as("Empty first yields second").isEqualTo("xyz");
        }

        @Test
        @DisplayName("Returns first string when second is empty")
        void difference_ShouldReturnFirst_WhenSecondIsEmpty() {
            // given
            String b = "";

            // when
            String result = StringUtils.difference("abc", b);

            // then
            then(result).as("Empty second yields first").isEqualTo("abc");
        }
    }

    @Nested
    class EndsWithAny {

        @Test
        @DisplayName("Returns true when any suffix matches")
        void endsWithAny_ShouldReturnTrue_WhenAnyMatches() {
            // given
            String input = "hello";

            // when
            boolean result = StringUtils.endsWithAny(input, "xyz", "llo");

            // then
            then(result).as("Matches 'llo'").isTrue();
        }

        @Test
        @DisplayName("Returns false when none matches")
        void endsWithAny_ShouldReturnFalse_WhenNoneMatches() {
            // given
            String input = "hello";

            // when
            boolean result = StringUtils.endsWithAny(input, "xyz", "abc");

            // then
            then(result).as("No match").isFalse();
        }

        @Test
        @DisplayName("Skips null and empty suffixes and matches the next valid one")
        void endsWithAny_ShouldSkipNullOrEmptySuffix_WhenPresent() {
            // given
            String input = "hello";

            // when
            boolean result = StringUtils.endsWithAny(input, null, "", "llo");

            // then
            then(result).as("Null and empty suffixes skipped, 'llo' matches").isTrue();
        }

        @Test
        @DisplayName("Returns false for empty suffixes array")
        void endsWithAny_ShouldReturnFalse_WhenSuffixesEmpty() {
            // given
            String input = "hello";
            String[] suffixes = new String[0];

            // when
            boolean result = StringUtils.endsWithAny(input, suffixes);

            // then
            then(result).as("Empty suffixes array yields false").isFalse();
        }

        @ParameterizedTest
        @ValueSource(strings = {"", "   "})
        @DisplayName("Returns false for empty or blank input string")
        void endsWithAny_ShouldReturnFalse_WhenInputIsEmptyOrBlank(String input) {
            // given
            // (input provided by @ValueSource)

            // when
            boolean result = StringUtils.endsWithAny(input, "a");

            // then
            then(result).as("Input '%s' does not end with 'a'", input).isFalse();
        }

        @Test
        @DisplayName("Returns false for null input")
        void endsWithAny_ShouldReturnFalse_WhenInputIsNull() {
            // given
            String input = null;

            // when
            boolean result = StringUtils.endsWithAny(input, "a");

            // then
            then(result).as("Null input").isFalse();
        }

        @Test
        @DisplayName("Returns false for null suffixes")
        void endsWithAny_ShouldReturnFalse_WhenSuffixesNull() {
            // given
            String input = "abc";

            // when
            boolean result = StringUtils.endsWithAny(input, (String[]) null);

            // then
            then(result).as("Null suffixes").isFalse();
        }
    }

    @Nested
    class EndsWithIgnoreCase {

        @Test
        @DisplayName("Returns true when suffix matches ignoring case")
        void endsWithIgnoreCase_ShouldReturnTrue_WhenSuffixMatchesIgnoringCase() {
            // given
            String input = "HelloWorld";

            // when
            boolean result = StringUtils.endsWithIgnoreCase(input, "world");

            // then
            then(result).as("Matches ignoring case").isTrue();
        }

        @Test
        @DisplayName("Returns false when suffix mismatches")
        void endsWithIgnoreCase_ShouldReturnFalse_WhenSuffixMismatches() {
            // given
            String input = "HelloWorld";

            // when
            boolean result = StringUtils.endsWithIgnoreCase(input, "earth");

            // then
            then(result).as("Mismatched suffix").isFalse();
        }

        @Test
        @DisplayName("Returns false when suffix longer than string")
        void endsWithIgnoreCase_ShouldReturnFalse_WhenSuffixLongerThanString() {
            // given
            String input = "ab";

            // when
            boolean result = StringUtils.endsWithIgnoreCase(input, "abcdef");

            // then
            then(result).as("Suffix too long").isFalse();
        }

        @Test
        @DisplayName("Returns false for null input string")
        void endsWithIgnoreCase_ShouldReturnFalse_WhenStringIsNull() {
            // given
            String input = null;

            // when
            boolean result = StringUtils.endsWithIgnoreCase(input, "a");

            // then
            then(result).as("Null input string").isFalse();
        }

        @Test
        @DisplayName("Returns false for empty input string")
        void endsWithIgnoreCase_ShouldReturnFalse_WhenStringIsEmpty() {
            // given
            String input = "";

            // when
            boolean result = StringUtils.endsWithIgnoreCase(input, "a");

            // then
            then(result).as("Empty input string").isFalse();
        }

        @Test
        @DisplayName("Returns false for blank input string")
        void endsWithIgnoreCase_ShouldReturnFalse_WhenStringIsBlank() {
            // given
            String input = "   ";

            // when
            boolean result = StringUtils.endsWithIgnoreCase(input, "a");

            // then
            then(result).as("Blank input string").isFalse();
        }

        @Test
        @DisplayName("Returns false for null suffix")
        void endsWithIgnoreCase_ShouldReturnFalse_WhenSuffixIsNull() {
            // given
            String input = "abc";

            // when
            boolean result = StringUtils.endsWithIgnoreCase(input, null);

            // then
            then(result).as("Null suffix").isFalse();
        }

        @Test
        @DisplayName("Returns false for empty suffix")
        void endsWithIgnoreCase_ShouldReturnFalse_WhenSuffixIsEmpty() {
            // given
            String input = "abc";

            // when
            boolean result = StringUtils.endsWithIgnoreCase(input, "");

            // then
            then(result).as("Empty suffix").isFalse();
        }
    }

    @Nested
    class EqualsIgnoreCaseMethod {

        @Test
        @DisplayName("Returns true for equal strings ignoring case")
        void equalsIgnoreCase_ShouldReturnTrue_WhenEqualIgnoringCase() {
            // given
            String a = "Hello";
            String b = "hello";

            // when
            boolean result = StringUtils.equalsIgnoreCase(a, b);

            // then
            then(result).as("Equal ignoring case").isTrue();
        }

        @Test
        @DisplayName("Returns true when both are null")
        void equalsIgnoreCase_ShouldReturnTrue_WhenBothNull() {
            // given
            String a = null;
            String b = null;

            // when
            boolean result = StringUtils.equalsIgnoreCase(a, b);

            // then
            then(result).as("Both null").isTrue();
        }

        @ParameterizedTest
        @CsvSource(value = {
                "null, abc",
                "abc, null",
                "abc, xyz"
        }, nullValues = "null")
        @DisplayName("Returns false when one is null or strings differ")
        void equalsIgnoreCase_ShouldReturnFalse_WhenOneIsNullOrDifferent(String a, String b) {
            // given
            // (a, b provided by @CsvSource; null values allowed)

            // when
            boolean result = StringUtils.equalsIgnoreCase(a, b);

            // then
            then(result).as("Inputs '%s' and '%s' are not equal ignoring case", a, b).isFalse();
        }
    }

    @Nested
    class FirstHasText {

        @Test
        @DisplayName("Returns first non-blank string")
        void firstHasText_ShouldReturnFirstNonBlank_WhenPresent() {
            // given
            String[] values = {null, "", "  ", "hello", "world"};

            // when
            String result = StringUtils.firstHasText(values);

            // then
            then(result).as("First non-blank value").isEqualTo("hello");
        }

        @Test
        @DisplayName("Returns null when all are blank")
        void firstHasText_ShouldReturnNull_WhenAllBlank() {
            // given
            String[] values = {null, "", "  "};

            // when
            String result = StringUtils.firstHasText(values);

            // then
            then(result).as("No non-blank value").isNull();
        }

        @Test
        @DisplayName("Returns null for null array")
        void firstHasText_ShouldReturnNull_WhenArrayIsNull() {
            // given
            String[] values = null;

            // when
            String result = StringUtils.firstHasText(values);

            // then
            then(result).as("Null array").isNull();
        }

        @Test
        @DisplayName("Returns null for empty array")
        void firstHasText_ShouldReturnNull_WhenArrayIsEmpty() {
            // given
            String[] values = new String[0];

            // when
            String result = StringUtils.firstHasText(values);

            // then
            then(result).as("Empty array").isNull();
        }
    }

    @Nested
    class GetDigits {

        @Test
        @DisplayName("Extracts only digit chars from input")
        void getDigits_ShouldReturnDigits_WhenDigitsPresent() {
            // given
            String input = "a1b2c3";

            // when
            String result = StringUtils.getDigits(input);

            // then
            then(result).as("Only digits").isEqualTo("123");
        }

        @ParameterizedTest
        @ValueSource(strings = {"abc", "", "   ", "!@#"})
        @DisplayName("Returns empty when input has no digits")
        void getDigits_ShouldReturnEmpty_WhenNoDigits(String input) {
            // given
            // (input provided by @ValueSource)

            // when
            String result = StringUtils.getDigits(input);

            // then
            then(result).as("Input '%s' has no digits", input).isEmpty();
        }

        @Test
        @DisplayName("Returns empty for null input")
        void getDigits_ShouldReturnEmpty_WhenInputIsNull() {
            // given
            String input = null;

            // when
            String result = StringUtils.getDigits(input);

            // then
            then(result).as("Null input").isEmpty();
        }
    }

    @Nested
    class HasLengthSingle {

        @Test
        @DisplayName("Returns true for a non-empty string")
        void hasLength_ShouldReturnTrue_WhenStringHasLength() {
            // given
            String input = "a";

            // when
            boolean result = StringUtils.hasLength(input);

            // then
            then(result).as("Non-empty has length").isTrue();
        }

        @Test
        @DisplayName("Returns true for a whitespace string")
        void hasLength_ShouldReturnTrue_WhenStringIsWhitespace() {
            // given
            String input = "   ";

            // when
            boolean result = StringUtils.hasLength(input);

            // then
            then(result).as("Whitespace has length").isTrue();
        }

        @Test
        @DisplayName("Returns false for null")
        void hasLength_ShouldReturnFalse_WhenStringIsNull() {
            // given
            String input = null;

            // when
            boolean result = StringUtils.hasLength(input);

            // then
            then(result).as("Null has no length").isFalse();
        }

        @Test
        @DisplayName("Returns false for empty")
        void hasLength_ShouldReturnFalse_WhenStringIsEmpty() {
            // given
            String input = "";

            // when
            boolean result = StringUtils.hasLength(input);

            // then
            then(result).as("Empty has no length").isFalse();
        }
    }

    @Nested
    class HasLengthVarargs {

        @Test
        @DisplayName("Returns true when at least one string has length")
        void hasLength_ShouldReturnTrue_WhenAnyHasLength() {
            // given
            String a = null;
            String b = "";
            String c = "x";

            // when
            boolean result = StringUtils.hasLength(a, b, c);

            // then
            then(result).as("At least one non-empty").isTrue();
        }

        @Test
        @DisplayName("Returns false when none has length")
        void hasLength_ShouldReturnFalse_WhenNoneHasLength() {
            // given
            String a = null;
            String b = "";

            // when
            boolean result = StringUtils.hasLength(a, b);

            // then
            then(result).as("All empty/null").isFalse();
        }

        @Test
        @DisplayName("Returns false for null array")
        void hasLength_ShouldReturnFalse_WhenArrayIsNull() {
            // given
            String[] values = null;

            // when
            boolean result = StringUtils.hasLength(values);

            // then
            then(result).as("Null array").isFalse();
        }

        @Test
        @DisplayName("Returns false for empty array")
        void hasLength_ShouldReturnFalse_WhenArrayIsEmpty() {
            // given
            String[] values = new String[0];

            // when
            boolean result = StringUtils.hasLength(values);

            // then
            then(result).as("Empty array").isFalse();
        }
    }

    @Nested
    class HasLengthAll {

        @Test
        @DisplayName("Returns true when all strings have length")
        void hasLengthAll_ShouldReturnTrue_WhenAllHaveLength() {
            // given
            String[] values = {"a", "b"};

            // when
            boolean result = StringUtils.hasLengthAll(values);

            // then
            then(result).as("All non-empty").isTrue();
        }

        @Test
        @DisplayName("Returns false when any string is empty")
        void hasLengthAll_ShouldReturnFalse_WhenAnyIsEmpty() {
            // given
            String[] values = {"a", ""};

            // when
            boolean result = StringUtils.hasLengthAll(values);

            // then
            then(result).as("Empty element").isFalse();
        }

        @Test
        @DisplayName("Returns false when any string is null")
        void hasLengthAll_ShouldReturnFalse_WhenAnyIsNull() {
            // given
            String[] values = {"a", null};

            // when
            boolean result = StringUtils.hasLengthAll(values);

            // then
            then(result).as("Null element").isFalse();
        }

        @Test
        @DisplayName("Returns false for null array")
        void hasLengthAll_ShouldReturnFalse_WhenArrayIsNull() {
            // given
            String[] values = null;

            // when
            boolean result = StringUtils.hasLengthAll(values);

            // then
            then(result).as("Null array").isFalse();
        }

        @Test
        @DisplayName("Returns false for empty array")
        void hasLengthAll_ShouldReturnFalse_WhenArrayIsEmpty() {
            // given
            String[] values = new String[0];

            // when
            boolean result = StringUtils.hasLengthAll(values);

            // then
            then(result).as("Empty array").isFalse();
        }
    }

    @Nested
    class HasTextSingle {

        @Test
        @DisplayName("Returns true for a non-blank string")
        void hasText_ShouldReturnTrue_WhenStringHasText() {
            // given
            String input = "abc";

            // when
            boolean result = StringUtils.hasText(input);

            // then
            then(result).as("Non-blank").isTrue();
        }

        @ParameterizedTest
        @ValueSource(strings = {"   ", "", "\t\n"})
        @DisplayName("Returns false for blank or empty string")
        void hasText_ShouldReturnFalse_WhenStringIsBlankOrEmpty(String input) {
            // given
            // (input provided by @ValueSource)

            // when
            boolean result = StringUtils.hasText(input);

            // then
            then(result).as("Input '%s' has no text", input).isFalse();
        }

        @Test
        @DisplayName("Returns false for null")
        void hasText_ShouldReturnFalse_WhenStringIsNull() {
            // given
            String input = null;

            // when
            boolean result = StringUtils.hasText(input);

            // then
            then(result).as("Null").isFalse();
        }
    }

    @Nested
    class HasTextVarargs {

        @Test
        @DisplayName("Returns true when at least one string has text")
        void hasText_ShouldReturnTrue_WhenAnyHasText() {
            // given
            String a = null;
            String b = "  ";
            String c = "x";

            // when
            boolean result = StringUtils.hasText(a, b, c);

            // then
            then(result).as("At least one with text").isTrue();
        }

        @Test
        @DisplayName("Returns false when none has text")
        void hasText_ShouldReturnFalse_WhenNoneHasText() {
            // given
            String a = null;
            String b = "";
            String c = "  ";

            // when
            boolean result = StringUtils.hasText(a, b, c);

            // then
            then(result).as("All blank").isFalse();
        }

        @Test
        @DisplayName("Returns false for null array")
        void hasText_ShouldReturnFalse_WhenArrayIsNull() {
            // given
            String[] values = null;

            // when
            boolean result = StringUtils.hasText(values);

            // then
            then(result).as("Null array").isFalse();
        }

        @Test
        @DisplayName("Returns false for empty array")
        void hasText_ShouldReturnFalse_WhenArrayIsEmpty() {
            // given
            String[] values = new String[0];

            // when
            boolean result = StringUtils.hasText(values);

            // then
            then(result).as("Empty array").isFalse();
        }
    }

    @Nested
    class HasTextAll {

        @Test
        @DisplayName("Returns true when all strings have text")
        void hasTextAll_ShouldReturnTrue_WhenAllHaveText() {
            // given
            String[] values = {"a", "b"};

            // when
            boolean result = StringUtils.hasTextAll(values);

            // then
            then(result).as("All non-blank").isTrue();
        }

        @Test
        @DisplayName("Returns false when any string is blank")
        void hasTextAll_ShouldReturnFalse_WhenAnyIsBlank() {
            // given
            String[] values = {"a", "  "};

            // when
            boolean result = StringUtils.hasTextAll(values);

            // then
            then(result).as("Blank element").isFalse();
        }

        @Test
        @DisplayName("Returns false for null array")
        void hasTextAll_ShouldReturnFalse_WhenArrayIsNull() {
            // given
            String[] values = null;

            // when
            boolean result = StringUtils.hasTextAll(values);

            // then
            then(result).as("Null array").isFalse();
        }

        @Test
        @DisplayName("Returns false for empty array")
        void hasTextAll_ShouldReturnFalse_WhenArrayIsEmpty() {
            // given
            String[] values = new String[0];

            // when
            boolean result = StringUtils.hasTextAll(values);

            // then
            then(result).as("Empty array").isFalse();
        }
    }

    @Nested
    class IndexOfAny {

        @Test
        @DisplayName("Returns the smallest matching index")
        void indexOfAny_ShouldReturnSmallestIndex_WhenAnySearchPresent() {
            // given
            String input = "hello world";

            // when
            int result = StringUtils.indexOfAny(input, new String[]{"world", "hello"});

            // then
            then(result).as("'hello' occurs first").isZero();
        }

        @Test
        @DisplayName("Returns -1 when no search present")
        void indexOfAny_ShouldReturnMinusOne_WhenNoSearchPresent() {
            // given
            String input = "hello";

            // when
            int result = StringUtils.indexOfAny(input, new String[]{"xyz", "abc"});

            // then
            then(result).as("Not found").isEqualTo(StringUtils.INDEX_NOT_FOUND);
        }

        @ParameterizedTest
        @ValueSource(strings = {"", "   ", "\t\n"})
        @DisplayName("Returns -1 for empty or blank input")
        void indexOfAny_ShouldReturnMinusOne_WhenInputIsEmptyOrBlank(String input) {
            // given
            // (input provided by @ValueSource)

            // when
            int result = StringUtils.indexOfAny(input, new String[]{"a"});

            // then
            then(result).as("Input '%s' has no text", input).isEqualTo(StringUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Returns -1 for null input")
        void indexOfAny_ShouldReturnMinusOne_WhenInputIsNull() {
            // given
            String input = null;

            // when
            int result = StringUtils.indexOfAny(input, new String[]{"a"});

            // then
            then(result).as("Null input").isEqualTo(StringUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Returns -1 for null search array")
        void indexOfAny_ShouldReturnMinusOne_WhenSearchIsNull() {
            // given
            String input = "abc";

            // when
            int result = StringUtils.indexOfAny(input, null);

            // then
            then(result).as("Null search array").isEqualTo(StringUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Returns -1 for empty search array")
        void indexOfAny_ShouldReturnMinusOne_WhenSearchIsEmpty() {
            // given
            String input = "abc";

            // when
            int result = StringUtils.indexOfAny(input, new String[0]);

            // then
            then(result).as("Empty search array").isEqualTo(StringUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Returns smallest index when later search token is larger")
        void indexOfAny_ShouldKeepSmallestIndex_WhenLaterTokenIsLarger() {
            // given
            String input = "hello world";

            // when
            int result = StringUtils.indexOfAny(input, new String[]{"hello", "world"});

            // then
            then(result).as("Smaller index from first token retained").isZero();
        }
    }

    @Nested
    class IndexOfIgnoreCase1 {

        @Test
        @DisplayName("Returns index of first match ignoring case")
        void indexOfIgnoreCase_ShouldReturnIndex_WhenMatchExists() {
            // given
            String input = "HelloWorld";

            // when
            int result = StringUtils.indexOfIgnoreCase(input, "WORLD");

            // then
            then(result).as("Index of 'WORLD'").isEqualTo(5);
        }

        @Test
        @DisplayName("Returns -1 when no match")
        void indexOfIgnoreCase_ShouldReturnMinusOne_WhenNoMatch() {
            // given
            String input = "Hello";

            // when
            int result = StringUtils.indexOfIgnoreCase(input, "xyz");

            // then
            then(result).as("Not found").isEqualTo(StringUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Returns -1 for null input")
        void indexOfIgnoreCase_ShouldReturnMinusOne_WhenInputIsNull() {
            // given
            String input = null;

            // when
            int result = StringUtils.indexOfIgnoreCase(input, "a");

            // then
            then(result).as("Null input").isEqualTo(StringUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Returns -1 for null search")
        void indexOfIgnoreCase_ShouldReturnMinusOne_WhenSearchIsNull() {
            // given
            String input = "abc";

            // when
            int result = StringUtils.indexOfIgnoreCase(input, null);

            // then
            then(result).as("Null search").isEqualTo(StringUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Returns -1 for empty search")
        void indexOfIgnoreCase_ShouldReturnMinusOne_WhenSearchEmpty() {
            // given
            String input = "abc";

            // when
            int result = StringUtils.indexOfIgnoreCase(input, "", 1);

            // then
            then(result).as("Empty search is rejected by hasLength guard").isEqualTo(StringUtils.INDEX_NOT_FOUND);
        }
    }

    @Nested
    class IndexOfIgnoreCase2 {

        @Test
        @DisplayName("Returns index from start position ignoring case")
        void indexOfIgnoreCase_ShouldReturnIndexFromStart_WhenMatchAfterStart() {
            // given
            String input = "abcABC";

            // when
            int result = StringUtils.indexOfIgnoreCase(input, "abc", 1);

            // then
            then(result).as("Found at index 3").isEqualTo(3);
        }

        @Test
        @DisplayName("Clamps negative start position to zero")
        void indexOfIgnoreCase_ShouldClampStart_WhenStartNegative() {
            // given
            String input = "abc";

            // when
            int result = StringUtils.indexOfIgnoreCase(input, "a", -5);

            // then
            then(result).as("Found at 0").isZero();
        }

        @Test
        @DisplayName("Returns -1 when start beyond limit")
        void indexOfIgnoreCase_ShouldReturnMinusOne_WhenStartBeyondLimit() {
            // given
            String input = "abc";

            // when
            int result = StringUtils.indexOfIgnoreCase(input, "a", 10);

            // then
            then(result).as("Out of range").isEqualTo(StringUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Returns -1 when search is not present")
        void indexOfIgnoreCase_ShouldReturnMinusOne_WhenSearchNotFound() {
            // given
            String input = "abcdef";

            // when
            int result = StringUtils.indexOfIgnoreCase(input, "xyz", 0);

            // then
            then(result).as("No match anywhere").isEqualTo(StringUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Returns -1 for empty input string")
        void indexOfIgnoreCase_ShouldReturnMinusOne_WhenInputIsEmpty() {
            // given
            String input = "";

            // when
            int result = StringUtils.indexOfIgnoreCase(input, "a", 0);

            // then
            then(result).as("Empty input").isEqualTo(StringUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Returns -1 for null input string")
        void indexOfIgnoreCase_ShouldReturnMinusOne_WhenInputIsNull() {
            // given
            String input = null;

            // when
            int result = StringUtils.indexOfIgnoreCase(input, "a", 0);

            // then
            then(result).as("Null input").isEqualTo(StringUtils.INDEX_NOT_FOUND);
        }
    }

    @Nested
    class IsAllLowerCase {

        @Test
        @DisplayName("Returns true for all lowercase string")
        void isAllLowerCase_ShouldReturnTrue_WhenAllLowercase() {
            // given
            String input = "abc";

            // when
            boolean result = StringUtils.isAllLowerCase(input);

            // then
            then(result).as("All lowercase").isTrue();
        }

        @ParameterizedTest
        @ValueSource(strings = {"aBc", "", "   ", "aB"})
        @DisplayName("Returns false for non-all-lowercase input")
        void isAllLowerCase_ShouldReturnFalse_WhenContainsUppercaseOrNoText(String input) {
            // given
            // (input provided by @ValueSource)

            // when
            boolean result = StringUtils.isAllLowerCase(input);

            // then
            then(result).as("Input '%s' is not all lowercase", input).isFalse();
        }

        @Test
        @DisplayName("Returns false for null input")
        void isAllLowerCase_ShouldReturnFalse_WhenInputIsNull() {
            // given
            String input = null;

            // when
            boolean result = StringUtils.isAllLowerCase(input);

            // then
            then(result).as("Null input").isFalse();
        }
    }

    @Nested
    class IsAllUpperCase {

        @Test
        @DisplayName("Returns true for all uppercase string")
        void isAllUpperCase_ShouldReturnTrue_WhenAllUppercase() {
            // given
            String input = "ABC";

            // when
            boolean result = StringUtils.isAllUpperCase(input);

            // then
            then(result).as("All uppercase").isTrue();
        }

        @ParameterizedTest
        @ValueSource(strings = {"AbC", "", "   ", "aB"})
        @DisplayName("Returns false for non-all-uppercase input")
        void isAllUpperCase_ShouldReturnFalse_WhenContainsLowercaseOrNoText(String input) {
            // given
            // (input provided by @ValueSource)

            // when
            boolean result = StringUtils.isAllUpperCase(input);

            // then
            then(result).as("Input '%s' is not all uppercase", input).isFalse();
        }

        @Test
        @DisplayName("Returns false for null input")
        void isAllUpperCase_ShouldReturnFalse_WhenInputIsNull() {
            // given
            String input = null;

            // when
            boolean result = StringUtils.isAllUpperCase(input);

            // then
            then(result).as("Null input").isFalse();
        }
    }

    @Nested
    class IsAlpha {

        @Test
        @DisplayName("Returns true for all letters")
        void isAlpha_ShouldReturnTrue_WhenAllLetters() {
            // given
            String input = "abcXYZ";

            // when
            boolean result = StringUtils.isAlpha(input);

            // then
            then(result).as("All letters").isTrue();
        }

        @ParameterizedTest
        @ValueSource(strings = {"ab1", "", "   ", "a b"})
        @DisplayName("Returns false for non-alpha input")
        void isAlpha_ShouldReturnFalse_WhenContainsDigitOrNoText(String input) {
            // given
            // (input provided by @ValueSource)

            // when
            boolean result = StringUtils.isAlpha(input);

            // then
            then(result).as("Input '%s' is not alpha", input).isFalse();
        }

        @Test
        @DisplayName("Returns false for null input")
        void isAlpha_ShouldReturnFalse_WhenInputIsNull() {
            // given
            String input = null;

            // when
            boolean result = StringUtils.isAlpha(input);

            // then
            then(result).as("Null input").isFalse();
        }
    }

    @Nested
    class IsAlphanumeric {

        @Test
        @DisplayName("Returns true for letters and digits")
        void isAlphanumeric_ShouldReturnTrue_WhenLettersAndDigits() {
            // given
            String input = "ab12";

            // when
            boolean result = StringUtils.isAlphanumeric(input);

            // then
            then(result).as("Letters and digits").isTrue();
        }

        @ParameterizedTest
        @ValueSource(strings = {"ab 12", "ab!12", "a-b"})
        @DisplayName("Returns false for non-alphanumeric input")
        void isAlphanumeric_ShouldReturnFalse_WhenContainsSpaceOrSpecialChar(String input) {
            // given
            // (input provided by @ValueSource)

            // when
            boolean result = StringUtils.isAlphanumeric(input);

            // then
            then(result).as("Input '%s' is not alphanumeric", input).isFalse();
        }

        @Test
        @DisplayName("Returns false for null input")
        void isAlphanumeric_ShouldReturnFalse_WhenInputIsNull() {
            // given
            String input = null;

            // when
            boolean result = StringUtils.isAlphanumeric(input);

            // then
            then(result).as("Null input").isFalse();
        }

        @Test
        @DisplayName("Returns false for empty input")
        void isAlphanumeric_ShouldReturnFalse_WhenInputIsEmpty() {
            // given
            String input = "";

            // when
            boolean result = StringUtils.isAlphanumeric(input);

            // then
            then(result).as("Empty input").isFalse();
        }
    }

    @Nested
    class IsAlphanumericSpace {

        @Test
        @DisplayName("Returns true for letters, digits and space")
        void isAlphanumericSpace_ShouldReturnTrue_WhenLettersDigitsSpace() {
            // given
            String input = "ab 12";

            // when
            boolean result = StringUtils.isAlphanumericSpace(input);

            // then
            then(result).as("Allowed chars only").isTrue();
        }

        @Test
        @DisplayName("Returns true for spaces-only string")
        void isAlphanumericSpace_ShouldReturnTrue_WhenOnlySpaces() {
            // given
            String input = "   ";

            // when
            boolean result = StringUtils.isAlphanumericSpace(input);

            // then
            then(result).as("Only spaces").isTrue();
        }

        @ParameterizedTest
        @ValueSource(strings = {"ab!12", "a-b", "ab.c"})
        @DisplayName("Returns false when contains punctuation")
        void isAlphanumericSpace_ShouldReturnFalse_WhenContainsPunctuation(String input) {
            // given
            // (input provided by @ValueSource)

            // when
            boolean result = StringUtils.isAlphanumericSpace(input);

            // then
            then(result).as("Punctuation not allowed in '%s'", input).isFalse();
        }

        @Test
        @DisplayName("Returns false for null input")
        void isAlphanumericSpace_ShouldReturnFalse_WhenInputIsNull() {
            // given
            String input = null;

            // when
            boolean result = StringUtils.isAlphanumericSpace(input);

            // then
            then(result).as("Null input").isFalse();
        }

        @Test
        @DisplayName("Returns false for empty input")
        void isAlphanumericSpace_ShouldReturnFalse_WhenInputIsEmpty() {
            // given
            String input = "";

            // when
            boolean result = StringUtils.isAlphanumericSpace(input);

            // then
            then(result).as("Empty input").isFalse();
        }
    }

    @Nested
    class IsAlphaSpace {

        @Test
        @DisplayName("Returns true for letters and spaces")
        void isAlphaSpace_ShouldReturnTrue_WhenLettersAndSpaces() {
            // given
            String input = "ab cd";

            // when
            boolean result = StringUtils.isAlphaSpace(input);

            // then
            then(result).as("Letters and spaces").isTrue();
        }

        @Test
        @DisplayName("Returns true for spaces-only string")
        void isAlphaSpace_ShouldReturnTrue_WhenOnlySpaces() {
            // given
            String input = "   ";

            // when
            boolean result = StringUtils.isAlphaSpace(input);

            // then
            then(result).as("Only spaces").isTrue();
        }

        @ParameterizedTest
        @ValueSource(strings = {"ab 1", "a1", "12 34"})
        @DisplayName("Returns false when contains digit")
        void isAlphaSpace_ShouldReturnFalse_WhenContainsDigit(String input) {
            // given
            // (input provided by @ValueSource)

            // when
            boolean result = StringUtils.isAlphaSpace(input);

            // then
            then(result).as("Digit not allowed in '%s'", input).isFalse();
        }

        @Test
        @DisplayName("Returns false for null input")
        void isAlphaSpace_ShouldReturnFalse_WhenInputIsNull() {
            // given
            String input = null;

            // when
            boolean result = StringUtils.isAlphaSpace(input);

            // then
            then(result).as("Null input").isFalse();
        }

        @Test
        @DisplayName("Returns false for empty input")
        void isAlphaSpace_ShouldReturnFalse_WhenInputIsEmpty() {
            // given
            String input = "";

            // when
            boolean result = StringUtils.isAlphaSpace(input);

            // then
            then(result).as("Empty input").isFalse();
        }
    }

    @Nested
    class IsAnyBlank {

        @Test
        @DisplayName("Returns true when any string is blank")
        void isAnyBlank_ShouldReturnTrue_WhenAnyBlank() {
            // given
            String a = "abc";
            String b = "   ";

            // when
            boolean result = StringUtils.isAnyBlank(a, b);

            // then
            then(result).as("Contains blank").isTrue();
        }

        @Test
        @DisplayName("Returns false when none is blank")
        void isAnyBlank_ShouldReturnFalse_WhenNoneBlank() {
            // given
            String a = "abc";
            String b = "xyz";

            // when
            boolean result = StringUtils.isAnyBlank(a, b);

            // then
            then(result).as("All have text").isFalse();
        }

        @Test
        @DisplayName("Returns false for null array")
        void isAnyBlank_ShouldReturnFalse_WhenArrayIsNull() {
            // given
            String[] values = null;

            // when
            boolean result = StringUtils.isAnyBlank(values);

            // then
            then(result).as("Null array").isFalse();
        }

        @Test
        @DisplayName("Returns false for empty array")
        void isAnyBlank_ShouldReturnFalse_WhenArrayIsEmpty() {
            // given
            String[] values = new String[0];

            // when
            boolean result = StringUtils.isAnyBlank(values);

            // then
            then(result).as("Empty array").isFalse();
        }
    }

    @Nested
    class IsAnyEmpty {

        @Test
        @DisplayName("Returns true when any string is empty")
        void isAnyEmpty_ShouldReturnTrue_WhenAnyEmpty() {
            // given
            String a = "abc";
            String b = "";

            // when
            boolean result = StringUtils.isAnyEmpty(a, b);

            // then
            then(result).as("Contains empty").isTrue();
        }

        @Test
        @DisplayName("Returns true when any string is null")
        void isAnyEmpty_ShouldReturnTrue_WhenAnyNull() {
            // given
            String a = "abc";
            String b = null;

            // when
            boolean result = StringUtils.isAnyEmpty(a, b);

            // then
            then(result).as("Contains null").isTrue();
        }

        @Test
        @DisplayName("Returns false when none is empty")
        void isAnyEmpty_ShouldReturnFalse_WhenNoneEmpty() {
            // given
            String a = "abc";
            String b = "xyz";

            // when
            boolean result = StringUtils.isAnyEmpty(a, b);

            // then
            then(result).as("All non-empty").isFalse();
        }

        @Test
        @DisplayName("Returns false for null array")
        void isAnyEmpty_ShouldReturnFalse_WhenArrayIsNull() {
            // given
            String[] values = null;

            // when
            boolean result = StringUtils.isAnyEmpty(values);

            // then
            then(result).as("Null array").isFalse();
        }

        @Test
        @DisplayName("Returns false for empty array")
        void isAnyEmpty_ShouldReturnFalse_WhenArrayIsEmpty() {
            // given
            String[] values = new String[0];

            // when
            boolean result = StringUtils.isAnyEmpty(values);

            // then
            then(result).as("Empty array").isFalse();
        }
    }

    @Nested
    class IsAsciiPrintable {

        @Test
        @DisplayName("Returns true for all printable ASCII")
        void isAsciiPrintable_ShouldReturnTrue_WhenAllPrintable() {
            // given
            String input = "abc123 !";

            // when
            boolean result = StringUtils.isAsciiPrintable(input);

            // then
            then(result).as("All printable").isTrue();
        }

        @Test
        @DisplayName("Returns true for empty string")
        void isAsciiPrintable_ShouldReturnTrue_WhenInputIsEmpty() {
            // given
            String input = "";

            // when
            boolean result = StringUtils.isAsciiPrintable(input);

            // then
            then(result).as("Empty is printable").isTrue();
        }

        @ParameterizedTest
        @ValueSource(strings = {"ab\tcd", "a\u007Fb"})
        @DisplayName("Returns false for non-printable input")
        void isAsciiPrintable_ShouldReturnFalse_WhenNonPrintable(String input) {
            // given
            // (input provided by @ValueSource)

            // when
            boolean result = StringUtils.isAsciiPrintable(input);

            // then
            then(result).as("Input '%s' is not printable ASCII", input).isFalse();
        }

        @Test
        @DisplayName("Returns false for null input")
        void isAsciiPrintable_ShouldReturnFalse_WhenInputIsNull() {
            // given
            String input = null;

            // when
            boolean result = StringUtils.isAsciiPrintable(input);

            // then
            then(result).as("Null input").isFalse();
        }

        @Test
        @DisplayName("Returns false when string contains char outside printable ASCII range")
        void isAsciiPrintable_ShouldReturnFalse_WhenCharAbovePrintableRange() {
            // given
            String input = "a\u007Fb";

            // when
            boolean result = StringUtils.isAsciiPrintable(input);

            // then
            then(result).as("DEL char is not printable").isFalse();
        }
    }

    @Nested
    class IsMixedCase {

        @Test
        @DisplayName("Returns true for mixed-case string")
        void isMixedCase_ShouldReturnTrue_WhenMixedCase() {
            // given
            String input = "aB";

            // when
            boolean result = StringUtils.isMixedCase(input);

            // then
            then(result).as("Mixed case").isTrue();
        }

        @ParameterizedTest
        @ValueSource(strings = {"abc", "ABC", "123", "a b"})
        @DisplayName("Returns false for non-mixed-case input")
        void isMixedCase_ShouldReturnFalse_WhenNotMixedCase(String input) {
            // given
            // (input provided by @ValueSource)

            // when
            boolean result = StringUtils.isMixedCase(input);

            // then
            then(result).as("Input '%s' is not mixed case", input).isFalse();
        }

        @Test
        @DisplayName("Returns false for null input")
        void isMixedCase_ShouldReturnFalse_WhenInputIsNull() {
            // given
            String input = null;

            // when
            boolean result = StringUtils.isMixedCase(input);

            // then
            then(result).as("Null input").isFalse();
        }

        @Test
        @DisplayName("Returns false for empty input")
        void isMixedCase_ShouldReturnFalse_WhenInputIsEmpty() {
            // given
            String input = "";

            // when
            boolean result = StringUtils.isMixedCase(input);

            // then
            then(result).as("Empty input").isFalse();
        }
    }

    @Nested
    class IsNoneBlank {

        @Test
        @DisplayName("Returns true when none is blank")
        void isNoneBlank_ShouldReturnTrue_WhenNoneBlank() {
            // given
            String a = "abc";
            String b = "xyz";

            // when
            boolean result = StringUtils.isNoneBlank(a, b);

            // then
            then(result).as("None blank").isTrue();
        }

        @Test
        @DisplayName("Returns false when any is blank")
        void isNoneBlank_ShouldReturnFalse_WhenAnyBlank() {
            // given
            String a = "abc";
            String b = "  ";

            // when
            boolean result = StringUtils.isNoneBlank(a, b);

            // then
            then(result).as("Contains blank").isFalse();
        }
    }

    @Nested
    class IsNoneEmpty {

        @Test
        @DisplayName("Returns true when none is empty")
        void isNoneEmpty_ShouldReturnTrue_WhenNoneEmpty() {
            // given
            String a = "abc";
            String b = "xyz";

            // when
            boolean result = StringUtils.isNoneEmpty(a, b);

            // then
            then(result).as("None empty").isTrue();
        }

        @Test
        @DisplayName("Returns false when any is empty")
        void isNoneEmpty_ShouldReturnFalse_WhenAnyEmpty() {
            // given
            String a = "abc";
            String b = "";

            // when
            boolean result = StringUtils.isNoneEmpty(a, b);

            // then
            then(result).as("Contains empty").isFalse();
        }
    }

    @Nested
    class IsNumeric {

        @ParameterizedTest
        @ValueSource(strings = {"123", "-1.5", "+1", "7"})
        @DisplayName("Returns true for valid numeric strings")
        void isNumeric_ShouldReturnTrue_WhenValidNumericString(String input) {
            // given
            // (input provided by @ValueSource)

            // when
            boolean result = StringUtils.isNumeric(input);

            // then
            then(result).as("Valid numeric string '%s'", input).isTrue();
        }

        @ParameterizedTest
        @ValueSource(strings = {"1.", "1.2.3", "-", "abc", "", "   ", "a1", ".5", ".", "+."})
        @DisplayName("Returns false for invalid numeric strings")
        void isNumeric_ShouldReturnFalse_WhenInvalidNumericString(String input) {
            // given

            // when
            boolean result = StringUtils.isNumeric(input);

            // then
            then(result).as("Invalid numeric string '%s'", input).isFalse();
        }

        @Test
        @DisplayName("Returns false for null input")
        void isNumeric_ShouldReturnFalse_WhenInputIsNull() {
            // given
            String input = null;

            // when
            boolean result = StringUtils.isNumeric(input);

            // then
            then(result).as("Null input").isFalse();
        }
    }

    @Nested
    class IsNumericSpace {

        @Test
        @DisplayName("Returns true for digits and spaces")
        void isNumericSpace_ShouldReturnTrue_WhenDigitsAndSpaces() {
            // given
            String input = "12 34";

            // when
            boolean result = StringUtils.isNumericSpace(input);

            // then
            then(result).as("Digits and spaces").isTrue();
        }

        @ParameterizedTest
        @ValueSource(strings = {"1.5", "", "   ", "abc"})
        @DisplayName("Returns false for invalid numeric-space input")
        void isNumericSpace_ShouldReturnFalse_WhenInvalidInput(String input) {
            // given
            // (input provided by @ValueSource)

            // when
            boolean result = StringUtils.isNumericSpace(input);

            // then
            then(result).as("Invalid numeric-space input '%s'", input).isFalse();
        }

        @Test
        @DisplayName("Returns false for null input")
        void isNumericSpace_ShouldReturnFalse_WhenInputIsNull() {
            // given
            String input = null;

            // when
            boolean result = StringUtils.isNumericSpace(input);

            // then
            then(result).as("Null input").isFalse();
        }
    }

    @Nested
    class IsWhitespace {

        @Test
        @DisplayName("Returns true for whitespace-only string")
        void isWhitespace_ShouldReturnTrue_WhenOnlyWhitespace() {
            // given
            String input = "  \t";

            // when
            boolean result = StringUtils.isWhitespace(input);

            // then
            then(result).as("Only whitespace").isTrue();
        }

        @ParameterizedTest
        @ValueSource(strings = {" a ", "", "abc"})
        @DisplayName("Returns false for non-whitespace-only input")
        void isWhitespace_ShouldReturnFalse_WhenNonWhitespacePresent(String input) {
            // given
            // (input provided by @ValueSource)

            // when
            boolean result = StringUtils.isWhitespace(input);

            // then
            then(result).as("Input '%s' is not whitespace-only", input).isFalse();
        }

        @Test
        @DisplayName("Returns false for null input")
        void isWhitespace_ShouldReturnFalse_WhenInputIsNull() {
            // given
            String input = null;

            // when
            boolean result = StringUtils.isWhitespace(input);

            // then
            then(result).as("Null input").isFalse();
        }
    }

    @Nested
    class Join {

        @Test
        @DisplayName("Joins array elements with separator")
        void join_ShouldJoinWithSeparator_WhenArrayHasElements() {
            // given
            String[] input = {"a", "b", "c"};

            // when
            String result = StringUtils.join(input, "-");

            // then
            then(result).as("Joined").isEqualTo("a-b-c");
        }

        @Test
        @DisplayName("Renders null elements as literal 'null'")
        void join_ShouldRenderNullAsLiteral_WhenNullElementPresent() {
            // given
            String[] input = {"a", null};

            // when
            String result = StringUtils.join(input, ",");

            // then
            then(result).as("Null rendered").isEqualTo("a,null");
        }

        @Test
        @DisplayName("Uses empty separator when separator is null")
        void join_ShouldUseEmptySeparator_WhenSeparatorIsNull() {
            // given
            String[] input = {"a", "b"};

            // when
            String result = StringUtils.join(input, null);

            // then
            then(result).as("Concatenated").isEqualTo("ab");
        }

        @Test
        @DisplayName("Returns empty for null array")
        void join_ShouldReturnEmpty_WhenArrayIsNull() {
            // given
            String[] input = null;

            // when
            String result = StringUtils.join(input, ",");

            // then
            then(result).as("Empty").isEmpty();
        }

        @Test
        @DisplayName("Returns empty for empty array")
        void join_ShouldReturnEmpty_WhenArrayIsEmpty() {
            // given
            String[] input = new String[0];

            // when
            String result = StringUtils.join(input, ",");

            // then
            then(result).as("Empty").isEmpty();
        }
    }

    @Nested
    class LastIndexOfAny {

        @Test
        @DisplayName("Returns the largest matching index")
        void lastIndexOfAny_ShouldReturnLargestIndex_WhenAnySearchPresent() {
            // given
            String input = "abab";

            // when
            int result = StringUtils.lastIndexOfAny(input, new String[]{"ab"});

            // then
            then(result).as("Last 'ab' at index 2").isEqualTo(2);
        }

        @Test
        @DisplayName("Returns -1 when no search present")
        void lastIndexOfAny_ShouldReturnMinusOne_WhenNoSearchPresent() {
            // given
            String input = "abc";

            // when
            int result = StringUtils.lastIndexOfAny(input, new String[]{"x"});

            // then
            then(result).as("Not found").isEqualTo(StringUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Returns -1 for null input")
        void lastIndexOfAny_ShouldReturnMinusOne_WhenInputIsNull() {
            // given
            String input = null;

            // when
            int result = StringUtils.lastIndexOfAny(input, new String[]{"a"});

            // then
            then(result).as("Null input").isEqualTo(StringUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Returns -1 for empty input")
        void lastIndexOfAny_ShouldReturnMinusOne_WhenInputIsEmpty() {
            // given
            String input = "";

            // when
            int result = StringUtils.lastIndexOfAny(input, new String[]{"a"});

            // then
            then(result).as("Empty input").isEqualTo(StringUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Returns -1 for null search array")
        void lastIndexOfAny_ShouldReturnMinusOne_WhenSearchIsNull() {
            // given
            String input = "abc";

            // when
            int result = StringUtils.lastIndexOfAny(input, null);

            // then
            then(result).as("Null search").isEqualTo(StringUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Returns -1 for empty search array")
        void lastIndexOfAny_ShouldReturnMinusOne_WhenSearchIsEmpty() {
            // given
            String input = "abc";

            // when
            int result = StringUtils.lastIndexOfAny(input, new String[0]);

            // then
            then(result).as("Empty search").isEqualTo(StringUtils.INDEX_NOT_FOUND);
        }
    }

    @Nested
    class LastIndexOfIgnoreCase1 {

        @Test
        @DisplayName("Returns last index ignoring case")
        void lastIndexOfIgnoreCase_ShouldReturnLastIndex_WhenMatchExists() {
            // given
            String input = "abcABC";

            // when
            int result = StringUtils.lastIndexOfIgnoreCase(input, "abc");

            // then
            then(result).as("Last match at index 3").isEqualTo(3);
        }

        @Test
        @DisplayName("Returns -1 when no match")
        void lastIndexOfIgnoreCase_ShouldReturnMinusOne_WhenNoMatch() {
            // given
            String input = "abc";

            // when
            int result = StringUtils.lastIndexOfIgnoreCase(input, "x");

            // then
            then(result).as("Not found").isEqualTo(StringUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Returns -1 for null input")
        void lastIndexOfIgnoreCase_ShouldReturnMinusOne_WhenInputIsNull() {
            // given
            String input = null;

            // when
            int result = StringUtils.lastIndexOfIgnoreCase(input, "a");

            // then
            then(result).as("Null input").isEqualTo(StringUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Returns -1 for null search")
        void lastIndexOfIgnoreCase_ShouldReturnMinusOne_WhenSearchIsNull() {
            // given
            String input = "abc";

            // when
            int result = StringUtils.lastIndexOfIgnoreCase(input, null);

            // then
            then(result).as("Null search").isEqualTo(StringUtils.INDEX_NOT_FOUND);
        }
    }

    @Nested
    class LastIndexOfIgnoreCase2 {

        @Test
        @DisplayName("Returns last index before the start position")
        void lastIndexOfIgnoreCase_ShouldReturnIndex_WhenSearchBeforeStart() {
            // given
            String input = "abcabc";

            // when
            int result = StringUtils.lastIndexOfIgnoreCase(input, "abc", 4);

            // then
            then(result).as("Last match at 3").isEqualTo(3);
        }

        @Test
        @DisplayName("Returns index when startPos is within bounds and match exists")
        void lastIndexOfIgnoreCase_ShouldReturnIndex_WhenStartPosWithinBounds() {
            // given
            String input = "abcabc";

            // when
            int result = StringUtils.lastIndexOfIgnoreCase(input, "abc", 2);

            // then
            then(result).as("Match at index 0").isZero();
        }

        @Test
        @DisplayName("Clamps start position when too large")
        void lastIndexOfIgnoreCase_ShouldClampStart_WhenStartTooLarge() {
            // given
            String input = "abc";

            // when
            int result = StringUtils.lastIndexOfIgnoreCase(input, "abc", 100);

            // then
            then(result).as("Clamped to 0").isZero();
        }

        @Test
        @DisplayName("Returns -1 when start is negative")
        void lastIndexOfIgnoreCase_ShouldReturnMinusOne_WhenStartNegative() {
            // given
            String input = "abc";

            // when
            int result = StringUtils.lastIndexOfIgnoreCase(input, "abc", -1);

            // then
            then(result).as("Negative start").isEqualTo(StringUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Returns -1 when search is not present")
        void lastIndexOfIgnoreCase_ShouldReturnMinusOne_WhenSearchNotFound() {
            // given
            String input = "abcdef";

            // when
            int result = StringUtils.lastIndexOfIgnoreCase(input, "xyz", 5);

            // then
            then(result).as("No match anywhere").isEqualTo(StringUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Returns -1 for null input")
        void lastIndexOfIgnoreCase_ShouldReturnMinusOne_WhenInputIsNull() {
            // given
            String input = null;

            // when
            int result = StringUtils.lastIndexOfIgnoreCase(input, "a", 0);

            // then
            then(result).as("Null input").isEqualTo(StringUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Returns -1 for null search")
        void lastIndexOfIgnoreCase_ShouldReturnMinusOne_WhenSearchIsNull() {
            // given
            String input = "abc";

            // when
            int result = StringUtils.lastIndexOfIgnoreCase(input, null, 0);

            // then
            then(result).as("Null search").isEqualTo(StringUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Returns -1 for empty search")
        void lastIndexOfIgnoreCase_ShouldReturnMinusOne_WhenSearchIsEmpty() {
            // given
            String input = "abc";

            // when
            int result = StringUtils.lastIndexOfIgnoreCase(input, "", 0);

            // then
            then(result).as("Empty search").isEqualTo(StringUtils.INDEX_NOT_FOUND);
        }

        @Test
        @DisplayName("Returns -1 when input empty")
        void lastIndexOfIgnoreCase_ShouldReturnMinusOne_WhenInputEmpty() {
            // given
            String input = "";

            // when
            int result = StringUtils.lastIndexOfIgnoreCase(input, "a", 0);

            // then
            then(result).as("Empty input").isEqualTo(StringUtils.INDEX_NOT_FOUND);
        }
    }

    @Nested
    class Left {

        @Test
        @DisplayName("Returns the leftmost substring of the given length")
        void left_ShouldReturnLeftmostChars_WhenLengthLessThanString() {
            // given
            String input = "abcdef";

            // when
            String result = StringUtils.left(input, 3);

            // then
            then(result).as("First three chars").isEqualTo("abc");
        }

        @Test
        @DisplayName("Returns the whole string when length exceeds string size")
        void left_ShouldReturnWhole_WhenLengthExceedsStringSize() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.left(input, 10);

            // then
            then(result).as("Whole string").isEqualTo("abc");
        }

        @Test
        @DisplayName("Returns null for null input")
        void left_ShouldReturnNull_WhenInputIsNull() {
            // given
            String input = null;

            // when
            String result = StringUtils.left(input, 3);

            // then
            then(result).as("Null stays null").isNull();
        }

        @Test
        @DisplayName("Returns empty for empty input")
        void left_ShouldReturnEmpty_WhenInputIsEmpty() {
            // given
            String input = "";

            // when
            String result = StringUtils.left(input, 3);

            // then
            then(result).as("Empty stays empty").isEmpty();
        }

        @Test
        @DisplayName("Throws IllegalArgumentException when length is negative")
        void left_ShouldThrowIllegalArgumentException_WhenLengthIsNegative() {
            // given
            String input = "abc";

            // when & then
            thenThrownBy(() -> StringUtils.left(input, -1))
                    .as("Negative length not allowed")
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Length must be positive");
        }
    }

    @Nested
    class LeftPadDefault {

        @Test
        @DisplayName("Pads with spaces to the given size")
        void leftPad_ShouldPadWithSpaces_WhenSizeLarger() {
            // given
            String input = "ab";

            // when
            String result = StringUtils.leftPad(input, 4);

            // then
            then(result).as("Padded with spaces").isEqualTo("  ab");
        }

        @Test
        @DisplayName("Returns original when size is smaller")
        void leftPad_ShouldReturnOriginal_WhenSizeIsSmaller() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.leftPad(input, 2);

            // then
            then(result).as("No padding").isEqualTo("abc");
        }

        @Test
        @DisplayName("Pads null as empty string")
        void leftPad_ShouldPadNull_WhenInputIsNull() {
            // given
            String input = null;

            // when
            String result = StringUtils.leftPad(input, 3);

            // then
            then(result).as("Spaces only").isEqualTo("   ");
        }
    }

    @Nested
    class LeftPadChar {

        @Test
        @DisplayName("Pads with the given char to the given size")
        void leftPad_ShouldPadWithChar_WhenSizeLarger() {
            // given
            String input = "ab";

            // when
            String result = StringUtils.leftPad(input, 4, 'x');

            // then
            then(result).as("Padded with x").isEqualTo("xxab");
        }

        @Test
        @DisplayName("Returns original when size is smaller or equal")
        void leftPad_ShouldReturnOriginal_WhenSizeSmallerOrEqual() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.leftPad(input, 3, 'x');

            // then
            then(result).as("No padding").isEqualTo("abc");
        }

        @Test
        @DisplayName("Pads null as empty string")
        void leftPad_ShouldPadNull_WhenInputIsNull() {
            // given
            String input = null;

            // when
            String result = StringUtils.leftPad(input, 3, 'x');

            // then
            then(result).as("xxx").isEqualTo("xxx");
        }

        @Test
        @DisplayName("Throws IllegalArgumentException when size is negative")
        void leftPad_ShouldThrowIllegalArgumentException_WhenSizeIsNegative() {
            // given
            String input = "abc";

            // when & then
            thenThrownBy(() -> StringUtils.leftPad(input, -1, 'x'))
                    .as("Negative size not allowed")
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Size must be non-negative");
        }
    }

    @Nested
    class Length {

        @Test
        @DisplayName("Returns the length of a non-null string")
        void length_ShouldReturnLength_WhenStringNotNull() {
            // given
            String input = "abc";

            // when
            int result = StringUtils.length(input);

            // then
            then(result).as("Length of 'abc'").isEqualTo(3);
        }

        @Test
        @DisplayName("Returns zero for null input")
        void length_ShouldReturnZero_WhenInputIsNull() {
            // given
            String input = null;

            // when
            int result = StringUtils.length(input);

            // then
            then(result).as("Null has length 0").isZero();
        }
    }

    @Nested
    class LevenshteinDistance {

        @ParameterizedTest
        @CsvSource(value = {
                "abc, abc, 0",
                "kitten, sitting, 3",
                "'', abc, 3",
                "abc, '', 3"
        })
        @DisplayName("Returns the edit distance between two strings")
        void levenshteinDistance_ShouldReturnEdits_WhenStringsDiffer(String a, String b, int expected) {
            // given
            // (a, b, expected provided by @CsvSource)

            // when
            int result = StringUtils.levenshteinDistance(a, b);

            // then
            then(result).as("Distance between '%s' and '%s'", a, b).isEqualTo(expected);
        }

        @SuppressWarnings("DataFlowIssue")
        @Test
        @DisplayName("Throws IllegalArgumentException when first is null")
        void levenshteinDistance_ShouldThrowIllegalArgumentException_WhenFirstIsNull() {
            // given
            String a = null;
            String b = "abc";

            // when & then
            thenThrownBy(() -> StringUtils.levenshteinDistance(a, b))
                    .as("Null first string not allowed")
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Strings must not be null");
        }

        @SuppressWarnings("DataFlowIssue")
        @Test
        @DisplayName("Throws IllegalArgumentException when second is null")
        void levenshteinDistance_ShouldThrowIllegalArgumentException_WhenSecondIsNull() {
            // given
            String a = "abc";
            String b = null;

            // when & then
            thenThrownBy(() -> StringUtils.levenshteinDistance(a, b))
                    .as("Null second string not allowed")
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Strings must not be null");
        }
    }

    @Nested
    class LowerCase {

        @Test
        @DisplayName("Returns lowercase string")
        void lowerCase_ShouldReturnLowercase_WhenInputNotNull() {
            // given
            String input = "ABC";

            // when
            String result = StringUtils.lowerCase(input);

            // then
            then(result).as("Lowercase").isEqualTo("abc");
        }

        @Test
        @DisplayName("Returns null for null input")
        void lowerCase_ShouldReturnNull_WhenInputIsNull() {
            // given
            String input = null;

            // when
            String result = StringUtils.lowerCase(input);

            // then
            then(result).as("Null stays null").isNull();
        }
    }

    @Nested
    class Mid {

        @Test
        @DisplayName("Returns substring of the requested length from the position")
        void mid_ShouldReturnSubstring_WhenWithinBounds() {
            // given
            String input = "abcdef";

            // when
            String result = StringUtils.mid(input, 1, 3);

            // then
            then(result).as("'bcd'").isEqualTo("bcd");
        }

        @Test
        @DisplayName("Returns suffix when length exceeds remaining chars")
        void mid_ShouldReturnSuffix_WhenLengthExceedsRemaining() {
            // given
            String input = "abcdef";

            // when
            String result = StringUtils.mid(input, 4, 10);

            // then
            then(result).as("Only 'ef' remains").isEqualTo("ef");
        }

        @Test
        @DisplayName("Returns empty when position beyond string length")
        void mid_ShouldReturnEmpty_WhenPositionBeyondLength() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.mid(input, 10, 2);

            // then
            then(result).as("Position out of range").isEmpty();
        }

        @Test
        @DisplayName("Returns null for null input")
        void mid_ShouldReturnNull_WhenInputIsNull() {
            // given
            String input = null;

            // when
            String result = StringUtils.mid(input, 0, 1);

            // then
            then(result).as("Null stays null").isNull();
        }

        @Test
        @DisplayName("Returns empty for empty input")
        void mid_ShouldReturnEmpty_WhenInputIsEmpty() {
            // given
            String input = "";

            // when
            String result = StringUtils.mid(input, 0, 1);

            // then
            then(result).as("Empty stays empty").isEmpty();
        }

        @Test
        @DisplayName("Throws IllegalArgumentException when length is negative")
        void mid_ShouldThrowIllegalArgumentException_WhenLengthIsNegative() {
            // given
            String input = "abc";

            // when & then
            thenThrownBy(() -> StringUtils.mid(input, 0, -1))
                    .as("Negative length not allowed")
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Length must be positive");
        }

        @Test
        @DisplayName("Throws IllegalArgumentException when position is negative")
        void mid_ShouldThrowIllegalArgumentException_WhenPositionIsNegative() {
            // given
            String input = "abc";

            // when & then
            thenThrownBy(() -> StringUtils.mid(input, -1, 1))
                    .as("Negative position not allowed")
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Position must be positive");
        }
    }

    @Nested
    class NormalizeSpace {

        @Test
        @DisplayName("Collapses multiple spaces into one and trims")
        void normalizeSpace_ShouldCollapseAndTrim_WhenWhitespacePresent() {
            // given
            String input = "  a   b\tc  ";

            // when
            String result = StringUtils.normalizeSpace(input);

            // then
            then(result).as("Single spaces, trimmed").isEqualTo("a b c");
        }

        @Test
        @DisplayName("Converts non-breaking space to a normal space")
        void normalizeSpace_ShouldTreatNbsp_WhenNbspPresent() {
            // given
            String input = "a\u00A0b";

            // when
            String result = StringUtils.normalizeSpace(input);

            // then
            then(result).as("NBSP handled").isEqualTo("a b");
        }

        @Test
        @DisplayName("Returns empty for whitespace-only input")
        void normalizeSpace_ShouldReturnEmpty_WhenOnlyWhitespace() {
            // given
            String input = "   ";

            // when
            String result = StringUtils.normalizeSpace(input);

            // then
            then(result).as("Only whitespace").isEmpty();
        }

        @Test
        @DisplayName("Returns null for null input")
        void normalizeSpace_ShouldReturnNull_WhenInputIsNull() {
            // given
            String input = null;

            // when
            String result = StringUtils.normalizeSpace(input);

            // then
            then(result).as("Null stays null").isNull();
        }

        @Test
        @DisplayName("Returns empty for empty input")
        void normalizeSpace_ShouldReturnEmpty_WhenInputIsEmpty() {
            // given
            String input = "";

            // when
            String result = StringUtils.normalizeSpace(input);

            // then
            then(result).as("Empty stays empty").isEmpty();
        }
    }

    @Nested
    class Overlay {

        @Test
        @DisplayName("Replaces a segment with the overlay")
        void overlay_ShouldReplaceSegment_WhenValidIndices() {
            // given
            String input = "abcdef";

            // when
            String result = StringUtils.overlay(input, "XY", 1, 3);

            // then
            then(result).as("'a' + 'XY' + 'def'").isEqualTo("aXYdef");
        }

        @Test
        @DisplayName("Treats null overlay as empty")
        void overlay_ShouldTreatNullOverlayAsEmpty_WhenOverlayIsNull() {
            // given
            String input = "abcdef";

            // when
            String result = StringUtils.overlay(input, null, 1, 3);

            // then
            then(result).as("Segment removed").isEqualTo("adef");
        }

        @Test
        @DisplayName("Swaps position and length when position greater")
        void overlay_ShouldSwap_WhenPositionGreaterThanLength() {
            // given
            String input = "abcdef";

            // when
            String result = StringUtils.overlay(input, "X", 3, 1);

            // then
            then(result).as("Swapped indices").isEqualTo("aXdef");
        }

        @Test
        @DisplayName("Clamps position and length to string length")
        void overlay_ShouldClamp_WhenIndicesExceedLength() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.overlay(input, "X", 100, 100);

            // then
            then(result).as("Appended at end").isEqualTo("abcX");
        }

        @Test
        @DisplayName("Returns null for null input")
        void overlay_ShouldReturnNull_WhenInputIsNull() {
            // given
            String input = null;

            // when
            String result = StringUtils.overlay(input, "X", 0, 1);

            // then
            then(result).as("Null stays null").isNull();
        }

        @Test
        @DisplayName("Throws IllegalArgumentException when length is negative")
        void overlay_ShouldThrowIllegalArgumentException_WhenLengthIsNegative() {
            // given
            String input = "abc";

            // when & then
            thenThrownBy(() -> StringUtils.overlay(input, "X", 0, -1))
                    .as("Negative length not allowed")
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Length must be positive");
        }

        @Test
        @DisplayName("Throws IllegalArgumentException when position is negative")
        void overlay_ShouldThrowIllegalArgumentException_WhenPositionIsNegative() {
            // given
            String input = "abc";

            // when & then
            thenThrownBy(() -> StringUtils.overlay(input, "X", -1, 1))
                    .as("Negative position not allowed")
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Position must be positive");
        }
    }

    @Nested
    class PrependIfMissing {

        @Test
        @DisplayName("Prepends prefix when missing")
        void prependIfMissing_ShouldPrepend_WhenPrefixMissing() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.prependIfMissing(input, "pre-");

            // then
            then(result).as("Prepended").isEqualTo("pre-abc");
        }

        @Test
        @DisplayName("Returns original when prefix present")
        void prependIfMissing_ShouldReturnOriginal_WhenPrefixPresent() {
            // given
            String input = "pre-abc";

            // when
            String result = StringUtils.prependIfMissing(input, "pre-");

            // then
            then(result).as("Unchanged").isEqualTo("pre-abc");
        }

        @Test
        @DisplayName("Returns original when input is null")
        void prependIfMissing_ShouldReturnNull_WhenInputIsNull() {
            // given
            String input = null;

            // when
            String result = StringUtils.prependIfMissing(input, "pre-");

            // then
            then(result).as("Null stays null").isNull();
        }

        @Test
        @DisplayName("Returns original when input is empty")
        void prependIfMissing_ShouldReturnEmpty_WhenInputIsEmpty() {
            // given
            String input = "";

            // when
            String result = StringUtils.prependIfMissing(input, "pre-");

            // then
            then(result).as("Empty").isEmpty();
        }

        @Test
        @DisplayName("Returns original when prefix is null")
        void prependIfMissing_ShouldReturnOriginal_WhenPrefixIsNull() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.prependIfMissing(input, null);

            // then
            then(result).as("Unchanged").isEqualTo("abc");
        }

        @Test
        @DisplayName("Returns original when prefix is empty")
        void prependIfMissing_ShouldReturnOriginal_WhenPrefixIsEmpty() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.prependIfMissing(input, "");

            // then
            then(result).as("Unchanged").isEqualTo("abc");
        }
    }

    @Nested
    class PrependIfMissingIgnoreCase {

        @Test
        @DisplayName("Prepends prefix when missing ignoring case")
        void prependIfMissingIgnoreCase_ShouldPrepend_WhenPrefixMissing() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.prependIfMissingIgnoreCase(input, "PRE-");

            // then
            then(result).as("Prepended").isEqualTo("PRE-abc");
        }

        @Test
        @DisplayName("Returns original when prefix present ignoring case")
        void prependIfMissingIgnoreCase_ShouldReturnOriginal_WhenPrefixPresentIgnoringCase() {
            // given
            String input = "pre-abc";

            // when
            String result = StringUtils.prependIfMissingIgnoreCase(input, "PRE-");

            // then
            then(result).as("Unchanged").isEqualTo("pre-abc");
        }

        @Test
        @DisplayName("Returns original when input is null")
        void prependIfMissingIgnoreCase_ShouldReturnNull_WhenInputIsNull() {
            // given
            String input = null;

            // when
            String result = StringUtils.prependIfMissingIgnoreCase(input, "pre-");

            // then
            then(result).as("Null stays null").isNull();
        }

        @Test
        @DisplayName("Returns original when input is empty")
        void prependIfMissingIgnoreCase_ShouldReturnEmpty_WhenInputIsEmpty() {
            // given
            String input = "";

            // when
            String result = StringUtils.prependIfMissingIgnoreCase(input, "pre-");

            // then
            then(result).as("Empty").isEmpty();
        }

        @Test
        @DisplayName("Returns original when prefix is null")
        void prependIfMissingIgnoreCase_ShouldReturnOriginal_WhenPrefixIsNull() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.prependIfMissingIgnoreCase(input, null);

            // then
            then(result).as("Unchanged").isEqualTo("abc");
        }

        @Test
        @DisplayName("Returns original when prefix is empty")
        void prependIfMissingIgnoreCase_ShouldReturnOriginal_WhenPrefixIsEmpty() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.prependIfMissingIgnoreCase(input, "");

            // then
            then(result).as("Unchanged").isEqualTo("abc");
        }
    }

    @Nested
    class RemoveChar {

        @Test
        @DisplayName("Removes all occurrences of a char")
        void remove_ShouldRemoveAll_WhenCharPresent() {
            // given
            String input = "a-b-c";

            // when
            String result = StringUtils.remove(input, '-');

            // then
            then(result).as("Dashes removed").isEqualTo("abc");
        }

        @Test
        @DisplayName("Returns original when char absent")
        void remove_ShouldReturnOriginal_WhenCharAbsent() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.remove(input, 'z');

            // then
            then(result).as("No change").isEqualTo("abc");
        }

        @Test
        @DisplayName("Returns null for null input")
        void remove_ShouldReturnNull_WhenInputIsNull() {
            // given
            String input = null;

            // when
            String result = StringUtils.remove(input, 'a');

            // then
            then(result).as("Null stays null").isNull();
        }

        @Test
        @DisplayName("Returns empty for empty input")
        void remove_ShouldReturnEmpty_WhenInputIsEmpty() {
            // given
            String input = "";

            // when
            String result = StringUtils.remove(input, 'a');

            // then
            then(result).as("Empty stays empty").isEmpty();
        }
    }

    @Nested
    class RemoveEndChar {

        @Test
        @DisplayName("Removes the char if it is the last char")
        void removeEnd_ShouldRemoveTrailingChar_WhenLastCharMatches() {
            // given
            String input = "abc-";

            // when
            String result = StringUtils.removeEnd(input, '-');

            // then
            then(result).as("Trailing dash removed").isEqualTo("abc");
        }

        @Test
        @DisplayName("Returns original when last char differs")
        void removeEnd_ShouldReturnOriginal_WhenLastCharDiffers() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.removeEnd(input, '-');

            // then
            then(result).as("No change").isEqualTo("abc");
        }

        @Test
        @DisplayName("Returns null for null input")
        void removeEnd_ShouldReturnNull_WhenInputIsNull() {
            // given
            String input = null;

            // when
            String result = StringUtils.removeEnd(input, 'a');

            // then
            then(result).as("Null stays null").isNull();
        }

        @Test
        @DisplayName("Returns empty for empty input")
        void removeEnd_ShouldReturnEmpty_WhenInputIsEmpty() {
            // given
            String input = "";

            // when
            String result = StringUtils.removeEnd(input, 'a');

            // then
            then(result).as("Empty stays empty").isEmpty();
        }

        @Test
        @DisplayName("Returns blank input unchanged")
        void removeEnd_ShouldReturnBlank_WhenInputIsBlank() {
            // given
            String input = "   ";

            // when
            String result = StringUtils.removeEnd(input, 'a');

            // then
            then(result).as("Blank returned unchanged").isEqualTo("   ");
        }
    }

    @Nested
    class RemoveEndString {

        @Test
        @DisplayName("Removes the trailing substring when present")
        void removeEnd_ShouldRemoveTrailing_WhenSuffixPresent() {
            // given
            String input = "helloWorld";

            // when
            String result = StringUtils.removeEnd(input, "World");

            // then
            then(result).as("Suffix removed").isEqualTo("hello");
        }

        @Test
        @DisplayName("Returns original when suffix absent")
        void removeEnd_ShouldReturnOriginal_WhenSuffixAbsent() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.removeEnd(input, "xyz");

            // then
            then(result).as("Unchanged").isEqualTo("abc");
        }

        @Test
        @DisplayName("Returns original when input is null")
        void removeEnd_ShouldReturnNull_WhenInputIsNull() {
            // given
            String input = null;

            // when
            String result = StringUtils.removeEnd(input, "x");

            // then
            then(result).as("Null stays null").isNull();
        }

        @Test
        @DisplayName("Returns original when input is empty")
        void removeEnd_ShouldReturnEmpty_WhenInputIsEmpty() {
            // given
            String input = "";

            // when
            String result = StringUtils.removeEnd(input, "x");

            // then
            then(result).as("Empty").isEmpty();
        }

        @Test
        @DisplayName("Returns original when remove is null")
        void removeEnd_ShouldReturnOriginal_WhenRemoveIsNull() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.removeEnd(input, null);

            // then
            then(result).as("Unchanged").isEqualTo("abc");
        }
    }

    @Nested
    class RemoveStartChar {

        @Test
        @DisplayName("Removes the char if it is the first char")
        void removeStart_ShouldRemoveLeadingChar_WhenFirstCharMatches() {
            // given
            String input = "-abc";

            // when
            String result = StringUtils.removeStart(input, '-');

            // then
            then(result).as("Leading dash removed").isEqualTo("abc");
        }

        @Test
        @DisplayName("Returns original when first char differs")
        void removeStart_ShouldReturnOriginal_WhenFirstCharDiffers() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.removeStart(input, '-');

            // then
            then(result).as("No change").isEqualTo("abc");
        }

        @Test
        @DisplayName("Returns null for null input")
        void removeStart_ShouldReturnNull_WhenInputIsNull() {
            // given
            String input = null;

            // when
            String result = StringUtils.removeStart(input, 'a');

            // then
            then(result).as("Null stays null").isNull();
        }

        @Test
        @DisplayName("Returns empty for empty input")
        void removeStart_ShouldReturnEmpty_WhenInputIsEmpty() {
            // given
            String input = "";

            // when
            String result = StringUtils.removeStart(input, 'a');

            // then
            then(result).as("Empty stays empty").isEmpty();
        }
    }

    @Nested
    class RemoveStartString {

        @Test
        @DisplayName("Removes the leading substring when present")
        void removeStart_ShouldRemoveLeading_WhenPrefixPresent() {
            // given
            String input = "helloWorld";

            // when
            String result = StringUtils.removeStart(input, "hello");

            // then
            then(result).as("Prefix removed").isEqualTo("World");
        }

        @Test
        @DisplayName("Returns original when prefix absent")
        void removeStart_ShouldReturnOriginal_WhenPrefixAbsent() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.removeStart(input, "xyz");

            // then
            then(result).as("Unchanged").isEqualTo("abc");
        }

        @Test
        @DisplayName("Returns original when input is null")
        void removeStart_ShouldReturnNull_WhenInputIsNull() {
            // given
            String input = null;

            // when
            String result = StringUtils.removeStart(input, "x");

            // then
            then(result).as("Null stays null").isNull();
        }

        @Test
        @DisplayName("Returns original when input is empty")
        void removeStart_ShouldReturnEmpty_WhenInputIsEmpty() {
            // given
            String input = "";

            // when
            String result = StringUtils.removeStart(input, "x");

            // then
            then(result).as("Empty").isEmpty();
        }

        @Test
        @DisplayName("Returns original when remove is null")
        void removeStart_ShouldReturnOriginal_WhenRemoveIsNull() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.removeStart(input, null);

            // then
            then(result).as("Unchanged").isEqualTo("abc");
        }
    }

    @Nested
    class Repeat {

        @Test
        @DisplayName("Repeats the string the given number of times")
        void repeat_ShouldRepeat_WhenCountPositive() {
            // given
            String input = "ab";

            // when
            String result = StringUtils.repeat(input, 3);

            // then
            then(result).as("Repeated").isEqualTo("ababab");
        }

        @ParameterizedTest
        @ValueSource(strings = {"ab", "", "abc"})
        @DisplayName("Returns empty when count is zero")
        void repeat_ShouldReturnEmpty_WhenCountIsZero(String input) {
            // given
            // (input provided by @ValueSource)

            // when
            String result = StringUtils.repeat(input, 0);

            // then
            then(result).as("Input '%s' with count 0 yields empty", input).isEmpty();
        }

        @Test
        @DisplayName("Returns empty when input is null")
        void repeat_ShouldReturnEmpty_WhenInputIsNull() {
            // given
            String input = null;

            // when
            String result = StringUtils.repeat(input, 3);

            // then
            then(result).as("Empty").isEmpty();
        }
    }

    @Nested
    class Replace {

        @Test
        @DisplayName("Replaces all occurrences of old pattern")
        void replace_ShouldReplaceAll_WhenPatternPresent() {
            // given
            String input = "a-b-c";

            // when
            String result = StringUtils.replace(input, "-", "_");

            // then
            then(result).as("All dashes replaced").isEqualTo("a_b_c");
        }

        @Test
        @DisplayName("Replaces when new pattern is longer than old pattern")
        void replace_ShouldReplace_WhenNewPatternIsLongerThanOld() {
            // given
            String input = "a-b-c";

            // when
            String result = StringUtils.replace(input, "-", "::");

            // then
            then(result).as("Dash replaced by double colon").isEqualTo("a::b::c");
        }

        @Test
        @DisplayName("Returns original when pattern not found")
        void replace_ShouldReturnOriginal_WhenPatternAbsent() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.replace(input, "x", "y");

            // then
            then(result).as("No pattern found").isSameAs(input);
        }

        @Test
        @DisplayName("Returns original when input is null")
        void replace_ShouldReturnNull_WhenInputIsNull() {
            // given
            String input = null;

            // when
            String result = StringUtils.replace(input, "a", "b");

            // then
            then(result).as("Null stays null").isNull();
        }

        @Test
        @DisplayName("Returns original when input is empty")
        void replace_ShouldReturnEmpty_WhenInputIsEmpty() {
            // given
            String input = "";

            // when
            String result = StringUtils.replace(input, "a", "b");

            // then
            then(result).as("Empty stays empty").isEmpty();
        }

        @Test
        @DisplayName("Returns original when input is blank")
        void replace_ShouldReturnBlank_WhenInputIsBlank() {
            // given
            String input = "   ";

            // when
            String result = StringUtils.replace(input, "a", "b");

            // then
            then(result).as("Blank returned unchanged").isEqualTo("   ");
        }

        @Test
        @DisplayName("Returns original when old pattern is null")
        void replace_ShouldReturnOriginal_WhenOldPatternIsNull() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.replace(input, null, "x");

            // then
            then(result).as("Null pattern").isSameAs(input);
        }

        @Test
        @DisplayName("Returns original when old pattern is empty")
        void replace_ShouldReturnOriginal_WhenOldPatternIsEmpty() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.replace(input, "", "x");

            // then
            then(result).as("Empty pattern").isSameAs(input);
        }

        @Test
        @DisplayName("Returns original when new pattern is null")
        void replace_ShouldReturnOriginal_WhenNewPatternIsNull() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.replace(input, "a", null);

            // then
            then(result).as("Null replacement").isSameAs(input);
        }
    }

    @Nested
    class ReplaceMax {

        @Test
        @DisplayName("Replaces up to max occurrences")
        void replace_ShouldReplaceUpToMax_WhenMaxPositive() {
            // given
            String input = "a-a-a-a";

            // when
            String result = StringUtils.replace(input, "-", "_", 2);

            // then
            then(result).as("Two replacements").isEqualTo("a_a_a-a");
        }

        @Test
        @DisplayName("Replaces all occurrences when max is negative")
        void replace_ShouldReplaceAll_WhenMaxNegative() {
            // given
            String input = "a-a-a";

            // when
            String result = StringUtils.replace(input, "-", "_", -1);

            // then
            then(result).as("All replaced").isEqualTo("a_a_a");
        }

        @Test
        @DisplayName("Returns original when max is zero")
        void replace_ShouldReturnOriginal_WhenMaxIsZero() {
            // given
            String input = "a-a";

            // when
            String result = StringUtils.replace(input, "-", "_", 0);

            // then
            then(result).as("Unchanged").isEqualTo("a-a");
        }

        @Test
        @DisplayName("Returns original when input is null")
        void replace_ShouldReturnNull_WhenInputIsNull() {
            // given
            String input = null;

            // when
            String result = StringUtils.replace(input, "-", "_", 1);

            // then
            then(result).as("Null stays null").isNull();
        }

        @Test
        @DisplayName("Returns original when input is empty")
        void replace_ShouldReturnEmpty_WhenInputIsEmpty() {
            // given
            String input = "";

            // when
            String result = StringUtils.replace(input, "-", "_", 1);

            // then
            then(result).as("Empty").isEmpty();
        }

        @Test
        @DisplayName("Returns original when input is blank")
        void replace_ShouldReturnBlank_WhenInputIsBlank() {
            // given
            String input = "   ";

            // when
            String result = StringUtils.replace(input, "-", "_", 1);

            // then
            then(result).as("Blank unchanged").isEqualTo("   ");
        }

        @Test
        @DisplayName("Returns original when search string is null")
        void replace_ShouldReturnOriginal_WhenSearchIsNull() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.replace(input, null, "_", 1);

            // then
            then(result).as("Unchanged").isEqualTo("abc");
        }

        @Test
        @DisplayName("Returns original when replacement is null")
        void replace_ShouldReturnOriginal_WhenReplacementIsNull() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.replace(input, "a", null, 1);

            // then
            then(result).as("Unchanged").isEqualTo("abc");
        }
    }

    @Nested
    class ReplaceIgnoreCaseMethod {

        @Test
        @DisplayName("Replaces all occurrences ignoring case")
        void replaceIgnoreCase_ShouldReplaceAll_WhenMatchesPresent() {
            // given
            String input = "aXaY";

            // when
            String result = StringUtils.replaceIgnoreCase(input, "a", "-");

            // then
            then(result).as("All replaced").isEqualTo("-X-Y");
        }

        @Test
        @DisplayName("Returns original when no match")
        void replaceIgnoreCase_ShouldReturnOriginal_WhenNoMatch() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.replaceIgnoreCase(input, "z", "-");

            // then
            then(result).as("Unchanged").isEqualTo("abc");
        }

        @Test
        @DisplayName("Returns original when input is null")
        void replaceIgnoreCase_ShouldReturnNull_WhenInputIsNull() {
            // given
            String input = null;

            // when
            String result = StringUtils.replaceIgnoreCase(input, "a", "-");

            // then
            then(result).as("Null stays null").isNull();
        }

        @Test
        @DisplayName("Returns original when input is empty")
        void replaceIgnoreCase_ShouldReturnEmpty_WhenInputIsEmpty() {
            // given
            String input = "";

            // when
            String result = StringUtils.replaceIgnoreCase(input, "a", "-");

            // then
            then(result).as("Empty").isEmpty();
        }

        @Test
        @DisplayName("Returns original when search is null")
        void replaceIgnoreCase_ShouldReturnOriginal_WhenSearchIsNull() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.replaceIgnoreCase(input, null, "-");

            // then
            then(result).as("Unchanged").isEqualTo("abc");
        }

        @Test
        @DisplayName("Returns original when replacement is null")
        void replaceIgnoreCase_ShouldReturnOriginal_WhenReplacementIsNull() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.replaceIgnoreCase(input, "a", null);

            // then
            then(result).as("Unchanged").isEqualTo("abc");
        }
    }

    @Nested
    class ReplaceOnce {

        @Test
        @DisplayName("Replaces only the first occurrence")
        void replaceOnce_ShouldReplaceFirstOnly_WhenPatternPresent() {
            // given
            String input = "a-a-a";

            // when
            String result = StringUtils.replaceOnce(input, "-", "_");

            // then
            then(result).as("Only first replaced").isEqualTo("a_a-a");
        }

        @Test
        @DisplayName("Returns original when pattern absent")
        void replaceOnce_ShouldReturnOriginal_WhenPatternAbsent() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.replaceOnce(input, "x", "y");

            // then
            then(result).as("Unchanged").isEqualTo("abc");
        }
    }

    @Nested
    class Reverse {

        @Test
        @DisplayName("Reverses a non-empty string")
        void reverse_ShouldReverse_WhenStringHasText() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.reverse(input);

            // then
            then(result).as("Reversed").isEqualTo("cba");
        }

        @Test
        @DisplayName("Returns null for null input")
        void reverse_ShouldReturnNull_WhenInputIsNull() {
            // given
            String input = null;

            // when
            String result = StringUtils.reverse(input);

            // then
            then(result).as("Null stays null").isNull();
        }

        @Test
        @DisplayName("Returns empty for empty input")
        void reverse_ShouldReturnEmpty_WhenInputIsEmpty() {
            // given
            String input = "";

            // when
            String result = StringUtils.reverse(input);

            // then
            then(result).as("Empty stays empty").isEmpty();
        }

        @Test
        @DisplayName("Returns blank input unchanged")
        void reverse_ShouldReturnBlank_WhenInputIsBlank() {
            // given
            String input = "   ";

            // when
            String result = StringUtils.reverse(input);

            // then
            then(result).as("Blank returned unchanged").isEqualTo("   ");
        }
    }

    @Nested
    class Right {

        @Test
        @DisplayName("Returns the rightmost substring of the given length")
        void right_ShouldReturnRightmostChars_WhenLengthLessThanString() {
            // given
            String input = "abcdef";

            // when
            String result = StringUtils.right(input, 3);

            // then
            then(result).as("Last three chars").isEqualTo("def");
        }

        @Test
        @DisplayName("Returns whole string when length exceeds size")
        void right_ShouldReturnWhole_WhenLengthExceedsStringSize() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.right(input, 10);

            // then
            then(result).as("Whole string").isEqualTo("abc");
        }

        @Test
        @DisplayName("Returns null for null input")
        void right_ShouldReturnNull_WhenInputIsNull() {
            // given
            String input = null;

            // when
            String result = StringUtils.right(input, 3);

            // then
            then(result).as("Null stays null").isNull();
        }

        @Test
        @DisplayName("Returns empty for empty input")
        void right_ShouldReturnEmpty_WhenInputIsEmpty() {
            // given
            String input = "";

            // when
            String result = StringUtils.right(input, 3);

            // then
            then(result).as("Empty stays empty").isEmpty();
        }

        @Test
        @DisplayName("Throws IllegalArgumentException when length is negative")
        void right_ShouldThrowIllegalArgumentException_WhenLengthIsNegative() {
            // given
            String input = "abc";

            // when & then
            thenThrownBy(() -> StringUtils.right(input, -1))
                    .as("Negative length not allowed")
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Length must be positive");
        }
    }

    @Nested
    class RightPadDefault {

        @Test
        @DisplayName("Pads with spaces to the given size")
        void rightPad_ShouldPadWithSpaces_WhenSizeLarger() {
            // given
            String input = "ab";

            // when
            String result = StringUtils.rightPad(input, 4);

            // then
            then(result).as("Padded with spaces").isEqualTo("ab  ");
        }

        @Test
        @DisplayName("Returns original when size is smaller")
        void rightPad_ShouldReturnOriginal_WhenSizeIsSmaller() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.rightPad(input, 2);

            // then
            then(result).as("No padding").isEqualTo("abc");
        }

        @Test
        @DisplayName("Pads null as empty string")
        void rightPad_ShouldPadNull_WhenInputIsNull() {
            // given
            String input = null;

            // when
            String result = StringUtils.rightPad(input, 3);

            // then
            then(result).as("Spaces only").isEqualTo("   ");
        }
    }

    @Nested
    class RightPadChar {

        @Test
        @DisplayName("Pads with the given char to the given size")
        void rightPad_ShouldPadWithChar_WhenSizeLarger() {
            // given
            String input = "ab";

            // when
            String result = StringUtils.rightPad(input, 4, 'x');

            // then
            then(result).as("Padded with x").isEqualTo("abxx");
        }

        @Test
        @DisplayName("Returns original when size is smaller or equal")
        void rightPad_ShouldReturnOriginal_WhenSizeSmallerOrEqual() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.rightPad(input, 3, 'x');

            // then
            then(result).as("No padding").isEqualTo("abc");
        }

        @Test
        @DisplayName("Pads null as empty string")
        void rightPad_ShouldPadNull_WhenInputIsNull() {
            // given
            String input = null;

            // when
            String result = StringUtils.rightPad(input, 3, 'x');

            // then
            then(result).as("xxx").isEqualTo("xxx");
        }

        @Test
        @DisplayName("Throws IllegalArgumentException when size is negative")
        void rightPad_ShouldThrowIllegalArgumentException_WhenSizeIsNegative() {
            // given
            String input = "abc";

            // when & then
            thenThrownBy(() -> StringUtils.rightPad(input, -1, 'x'))
                    .as("Negative size not allowed")
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Size must be non-negative");
        }
    }

    @Nested
    class Rotate {

        @Test
        @DisplayName("Rotates right for positive shift")
        void rotate_ShouldRotateRight_WhenShiftPositive() {
            // given
            String input = "abcd";

            // when
            String result = StringUtils.rotate(input, 1);

            // then
            then(result).as("Right rotate by 1").isEqualTo("dabc");
        }

        @Test
        @DisplayName("Rotates left for negative shift")
        void rotate_ShouldRotateLeft_WhenShiftNegative() {
            // given
            String input = "abcd";

            // when
            String result = StringUtils.rotate(input, -1);

            // then
            then(result).as("Left rotate by 1").isEqualTo("bcda");
        }

        @Test
        @DisplayName("Returns original when shift is a full multiple")
        void rotate_ShouldReturnOriginal_WhenShiftMultipleOfLength() {
            // given
            String input = "abcd";

            // when
            String result = StringUtils.rotate(input, 4);

            // then
            then(result).as("Full rotation").isEqualTo("abcd");
        }

        @Test
        @DisplayName("Returns original when shift is zero")
        void rotate_ShouldReturnOriginal_WhenShiftIsZero() {
            // given
            String input = "abcd";

            // when
            String result = StringUtils.rotate(input, 0);

            // then
            then(result).as("No rotation").isEqualTo("abcd");
        }

        @Test
        @DisplayName("Returns null for null input")
        void rotate_ShouldReturnNull_WhenInputIsNull() {
            // given
            String input = null;

            // when
            String result = StringUtils.rotate(input, 1);

            // then
            then(result).as("Null stays null").isNull();
        }

        @Test
        @DisplayName("Returns empty for empty input")
        void rotate_ShouldReturnEmpty_WhenInputIsEmpty() {
            // given
            String input = "";

            // when
            String result = StringUtils.rotate(input, 1);

            // then
            then(result).as("Empty").isEmpty();
        }
    }

    @Nested
    class SplitDefault {

        @Test
        @DisplayName("Splits on spaces by default")
        void split_ShouldSplitOnSpace_WhenDefaultCalled() {
            // given
            String input = "a b c";

            // when
            String[] result = StringUtils.split(input);

            // then
            then(result).as("Three tokens").containsExactly("a", "b", "c");
        }

        @ParameterizedTest
        @ValueSource(strings = {"", "   ", "\t\n"})
        @DisplayName("Returns empty array for empty or blank input")
        void split_ShouldReturnEmpty_WhenInputIsEmptyOrBlank(String input) {
            // given
            // (input provided by @ValueSource)

            // when
            String[] result = StringUtils.split(input);

            // then
            then(result).as("Input '%s' has no tokens", input).isEmpty();
        }

        @Test
        @DisplayName("Returns empty array for null input")
        void split_ShouldReturnEmpty_WhenInputIsNull() {
            // given
            String input = null;

            // when
            String[] result = StringUtils.split(input);

            // then
            then(result).as("Null input").isEmpty();
        }

        @Test
        @DisplayName("Splits all tokens when max exceeds token count")
        void split_ShouldSplitAll_WhenMaxExceedsTokenCount() {
            // given
            String input = "a,b";

            // when
            String[] result = StringUtils.split(input, ",", 5);

            // then
            then(result).as("Max larger than token count").containsExactly("a", "b");
        }

        @Test
        @DisplayName("Returns empty array when input has only separators")
        void split_ShouldReturnEmpty_WhenInputHasOnlySeparators() {
            // given
            String input = ",";

            // when
            String[] result = StringUtils.split(input, ",", 0);

            // then
            then(result).as("No tokens in separator-only input").isEmpty();
        }

        @Test
        @DisplayName("Returns empty array for empty input")
        void split_ShouldReturnEmpty_WhenInputIsEmpty() {
            // given
            String input = "";

            // when
            String[] result = StringUtils.split(input, ",", 2);

            // then
            then(result).as("Empty input").isEmpty();
        }

        @Test
        @DisplayName("Returns empty array for blank input")
        void split_ShouldReturnEmpty_WhenInputIsBlank() {
            // given
            String input = "   ";

            // when
            String[] result = StringUtils.split(input, ",", 2);

            // then
            then(result).as("Blank input").isEmpty();
        }
    }

    @Nested
    class SplitSeparator {

        @Test
        @DisplayName("Splits on given separator")
        void split_ShouldSplitOnSeparator_WhenSeparatorProvided() {
            // given
            String input = "a,b,c";

            // when
            String[] result = StringUtils.split(input, ",");

            // then
            then(result).as("Three tokens").containsExactly("a", "b", "c");
        }

        @Test
        @DisplayName("Returns empty array for blank input")
        void split_ShouldReturnEmpty_WhenInputIsBlank() {
            // given
            String input = "   ";

            // when
            String[] result = StringUtils.split(input, ",");

            // then
            then(result).as("Empty array").isEmpty();
        }
    }

    @Nested
    class SplitMax {

        @Test
        @DisplayName("Limits the number of tokens when max is positive")
        void split_ShouldLimitTokens_WhenMaxIsPositive() {
            // given
            String input = "a,b,c,d";

            // when
            String[] result = StringUtils.split(input, ",", 2);

            // then
            then(result).as("Two tokens with remainder").containsExactly("a", "b,c,d");
        }

        @Test
        @DisplayName("Splits all tokens when max is zero")
        void split_ShouldSplitAll_WhenMaxIsZero() {
            // given
            String input = "a,b,c";

            // when
            String[] result = StringUtils.split(input, ",", 0);

            // then
            then(result).as("All tokens").containsExactly("a", "b", "c");
        }

        @Test
        @DisplayName("Uses whitespace as default when separator is null")
        void split_ShouldUseWhitespace_WhenSeparatorIsNull() {
            // given
            String input = "a b c";

            // when
            String[] result = StringUtils.split(input, null, 0);

            // then
            then(result).as("Whitespace tokens").containsExactly("a", "b", "c");
        }

        @Test
        @DisplayName("Returns empty array for null input")
        void split_ShouldReturnEmpty_WhenInputIsNull() {
            // given
            String input = null;

            // when
            String[] result = StringUtils.split(input, ",", 2);

            // then
            then(result).as("Empty array").isEmpty();
        }
    }

    @Nested
    class SplitByCharacterType {

        @Test
        @DisplayName("Splits by character type groups")
        void splitByCharacterType_ShouldSplitByType_WhenMixedTypes() {
            // given
            String input = "ab12cd";

            // when
            String[] result = StringUtils.splitByCharacterType(input);

            // then
            then(result).as("Groups by type").containsExactly("ab", "12", "cd");
        }

        @Test
        @DisplayName("Returns empty array for null input")
        void splitByCharacterType_ShouldReturnEmpty_WhenInputIsNull() {
            // given
            String input = null;

            // when
            String[] result = StringUtils.splitByCharacterType(input);

            // then
            then(result).as("Empty").isEmpty();
        }

        @Test
        @DisplayName("Returns empty array for empty input")
        void splitByCharacterType_ShouldReturnEmpty_WhenInputIsEmpty() {
            // given
            String input = "";

            // when
            String[] result = StringUtils.splitByCharacterType(input);

            // then
            then(result).as("Empty").isEmpty();
        }
    }

    @Nested
    class StartsWithAny {

        @Test
        @DisplayName("Returns true when any prefix matches")
        void startsWithAny_ShouldReturnTrue_WhenAnyMatches() {
            // given
            String input = "hello";

            // when
            boolean result = StringUtils.startsWithAny(input, "xyz", "hel");

            // then
            then(result).as("Matches 'hel'").isTrue();
        }

        @Test
        @DisplayName("Returns false when none matches")
        void startsWithAny_ShouldReturnFalse_WhenNoneMatches() {
            // given
            String input = "hello";

            // when
            boolean result = StringUtils.startsWithAny(input, "xyz", "abc");

            // then
            then(result).as("No match").isFalse();
        }

        @Test
        @DisplayName("Skips null and empty prefixes and matches the next valid one")
        void startsWithAny_ShouldSkipNullOrEmptyPrefix_WhenPresent() {
            // given
            String input = "hello";

            // when
            boolean result = StringUtils.startsWithAny(input, null, "", "hel");

            // then
            then(result).as("Null and empty prefixes skipped, 'hel' matches").isTrue();
        }

        @Test
        @DisplayName("Returns false for empty prefixes array")
        void startsWithAny_ShouldReturnFalse_WhenPrefixesEmpty() {
            // given
            String input = "hello";
            String[] prefixes = new String[0];

            // when
            boolean result = StringUtils.startsWithAny(input, prefixes);

            // then
            then(result).as("Empty prefixes array yields false").isFalse();
        }

        @ParameterizedTest
        @ValueSource(strings = {"", "   "})
        @DisplayName("Returns false for empty or blank input string")
        void startsWithAny_ShouldReturnFalse_WhenInputIsEmptyOrBlank(String input) {
            // given
            // (input provided by @ValueSource)

            // when
            boolean result = StringUtils.startsWithAny(input, "a");

            // then
            then(result).as("Input '%s' does not start with 'a'", input).isFalse();
        }

        @Test
        @DisplayName("Returns false for null input")
        void startsWithAny_ShouldReturnFalse_WhenInputIsNull() {
            // given
            String input = null;

            // when
            boolean result = StringUtils.startsWithAny(input, "a");

            // then
            then(result).as("Null input").isFalse();
        }

        @Test
        @DisplayName("Returns false for null prefixes")
        void startsWithAny_ShouldReturnFalse_WhenPrefixesNull() {
            // given
            String input = "abc";

            // when
            boolean result = StringUtils.startsWithAny(input, (String[]) null);

            // then
            then(result).as("Null prefixes").isFalse();
        }
    }

    @Nested
    class StartsWithIgnoreCase {

        @Test
        @DisplayName("Returns true when prefix matches ignoring case")
        void startsWithIgnoreCase_ShouldReturnTrue_WhenPrefixMatchesIgnoringCase() {
            // given
            String input = "HelloWorld";

            // when
            boolean result = StringUtils.startsWithIgnoreCase(input, "hello");

            // then
            then(result).as("Matches ignoring case").isTrue();
        }

        @Test
        @DisplayName("Returns false when prefix mismatches")
        void startsWithIgnoreCase_ShouldReturnFalse_WhenPrefixMismatches() {
            // given
            String input = "HelloWorld";

            // when
            boolean result = StringUtils.startsWithIgnoreCase(input, "world");

            // then
            then(result).as("Mismatch").isFalse();
        }

        @Test
        @DisplayName("Returns false when prefix longer than string")
        void startsWithIgnoreCase_ShouldReturnFalse_WhenPrefixLongerThanString() {
            // given
            String input = "ab";

            // when
            boolean result = StringUtils.startsWithIgnoreCase(input, "abcdef");

            // then
            then(result).as("Prefix too long").isFalse();
        }

        @Test
        @DisplayName("Returns false for null input")
        void startsWithIgnoreCase_ShouldReturnFalse_WhenStringIsNull() {
            // given
            String input = null;

            // when
            boolean result = StringUtils.startsWithIgnoreCase(input, "a");

            // then
            then(result).as("Null input").isFalse();
        }

        @Test
        @DisplayName("Returns false for empty input")
        void startsWithIgnoreCase_ShouldReturnFalse_WhenStringIsEmpty() {
            // given
            String input = "";

            // when
            boolean result = StringUtils.startsWithIgnoreCase(input, "a");

            // then
            then(result).as("Empty input").isFalse();
        }

        @Test
        @DisplayName("Returns false for null prefix")
        void startsWithIgnoreCase_ShouldReturnFalse_WhenPrefixIsNull() {
            // given
            String input = "abc";

            // when
            boolean result = StringUtils.startsWithIgnoreCase(input, null);

            // then
            then(result).as("Null prefix").isFalse();
        }

        @Test
        @DisplayName("Returns false for empty prefix")
        void startsWithIgnoreCase_ShouldReturnFalse_WhenPrefixIsEmpty() {
            // given
            String input = "abc";

            // when
            boolean result = StringUtils.startsWithIgnoreCase(input, "");

            // then
            then(result).as("Empty prefix").isFalse();
        }
    }

    @Nested
    class StripSingle {

        @Test
        @DisplayName("Trims whitespace from both ends")
        void strip_ShouldTrimBothEnds_WhenWhitespacePresent() {
            // given
            String input = "  abc  ";

            // when
            String result = StringUtils.strip(input);

            // then
            then(result).as("Trimmed").isEqualTo("abc");
        }

        @Test
        @DisplayName("Returns null for null input")
        void strip_ShouldReturnNull_WhenInputIsNull() {
            // given
            String input = null;

            // when
            String result = StringUtils.strip(input);

            // then
            then(result).as("Null stays null").isNull();
        }

        @Test
        @DisplayName("Returns empty for empty input")
        void strip_ShouldReturnEmpty_WhenInputIsEmpty() {
            // given
            String input = "";

            // when
            String result = StringUtils.strip(input);

            // then
            then(result).as("Empty stays empty").isEmpty();
        }
    }

    @Nested
    class StripWithChars {

        @Test
        @DisplayName("Strips specified chars from both ends")
        void strip_ShouldStripGivenChars_WhenCharsProvided() {
            // given
            String input = "xxabcxx";

            // when
            String result = StringUtils.strip(input, "x");

            // then
            then(result).as("x stripped").isEqualTo("abc");
        }

        @Test
        @DisplayName("Strips whitespace when stripChars is null")
        void strip_ShouldStripWhitespace_WhenStripCharsIsNull() {
            // given
            String input = "  abc  ";

            // when
            String result = StringUtils.strip(input, null);

            // then
            then(result).as("Trimmed").isEqualTo("abc");
        }

        @Test
        @DisplayName("Returns null for null input")
        void strip_ShouldReturnNull_WhenInputIsNull() {
            // given
            String input = null;

            // when
            String result = StringUtils.strip(input, "x");

            // then
            then(result).as("Null stays null").isNull();
        }

        @Test
        @DisplayName("Returns empty for empty input")
        void strip_ShouldReturnEmpty_WhenInputIsEmpty() {
            // given
            String input = "";

            // when
            String result = StringUtils.strip(input, "x");

            // then
            then(result).as("Empty stays empty").isEmpty();
        }
    }

    @Nested
    class StripAccents {

        @Test
        @DisplayName("Removes accents from a string")
        void stripAccents_ShouldRemoveAccents_WhenAccentsPresent() {
            // given
            String input = "àéî";

            // when
            String result = StringUtils.stripAccents(input);

            // then
            then(result).as("Accents removed").isEqualTo("aei");
        }

        @Test
        @DisplayName("Returns null for null input")
        void stripAccents_ShouldReturnNull_WhenInputIsNull() {
            // given
            String input = null;

            // when
            String result = StringUtils.stripAccents(input);

            // then
            then(result).as("Null stays null").isNull();
        }
    }

    @Nested
    class StripEnd {

        @Test
        @DisplayName("Strips whitespace from end when stripChars null")
        void stripEnd_ShouldStripWhitespace_WhenStripCharsNull() {
            // given
            String input = "abc  ";

            // when
            String result = StringUtils.stripEnd(input, null);

            // then
            then(result).as("Trailing whitespace removed").isEqualTo("abc");
        }

        @Test
        @DisplayName("Strips NBSP from end when stripChars null")
        void stripEnd_ShouldStripNbsp_WhenStripCharsNull() {
            // given
            String input = "abc\u00A0";

            // when
            String result = StringUtils.stripEnd(input, null);

            // then
            then(result).as("NBSP removed").isEqualTo("abc");
        }

        @Test
        @DisplayName("Strips given chars from end")
        void stripEnd_ShouldStripGivenChars_WhenStripCharsProvided() {
            // given
            String input = "abcxx";

            // when
            String result = StringUtils.stripEnd(input, "x");

            // then
            then(result).as("Trailing x's removed").isEqualTo("abc");
        }

        @Test
        @DisplayName("Returns input unchanged when last char is not whitespace or NBSP")
        void stripEnd_ShouldReturnOriginal_WhenLastCharNotWhitespace() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.stripEnd(input, null);

            // then
            then(result).as("No trailing whitespace to strip").isEqualTo("abc");
        }

        @Test
        @DisplayName("Returns input unchanged when last char is not in stripChars")
        void stripEnd_ShouldReturnOriginal_WhenLastCharNotInStripChars() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.stripEnd(input, "x");

            // then
            then(result).as("No trailing char from stripChars to strip").isEqualTo("abc");
        }

        @Test
        @DisplayName("Returns empty when all chars are stripped by whitespace mode")
        void stripEnd_ShouldReturnEmpty_WhenAllCharsAreWhitespace() {
            // given
            String input = "   ";

            // when
            String result = StringUtils.stripEnd(input, null);

            // then
            then(result).as("Entire string stripped").isEmpty();
        }

        @Test
        @DisplayName("Returns empty when all chars are stripped by stripChars")
        void stripEnd_ShouldReturnEmpty_WhenAllCharsMatchStripChars() {
            // given
            String input = "xxx";

            // when
            String result = StringUtils.stripEnd(input, "x");

            // then
            then(result).as("Entire string stripped").isEmpty();
        }

        @Test
        @DisplayName("Strips whitespace when stripChars is empty")
        void stripEnd_ShouldStripWhitespace_WhenStripCharsIsEmpty() {
            // given
            String input = "abc  ";

            // when
            String result = StringUtils.stripEnd(input, "");

            // then
            then(result).as("Empty stripChars falls back to whitespace mode").isEqualTo("abc");
        }

        @Test
        @DisplayName("Returns null for null input")
        void stripEnd_ShouldReturnNull_WhenInputIsNull() {
            // given
            String input = null;

            // when
            String result = StringUtils.stripEnd(input, "x");

            // then
            then(result).as("Null stays null").isNull();
        }

        @Test
        @DisplayName("Returns empty for empty input")
        void stripEnd_ShouldReturnEmpty_WhenInputIsEmpty() {
            // given
            String input = "";

            // when
            String result = StringUtils.stripEnd(input, "x");

            // then
            then(result).as("Empty stays empty").isEmpty();
        }
    }

    @Nested
    class StripStart {

        @Test
        @DisplayName("Strips whitespace from start when stripChars null")
        void stripStart_ShouldStripWhitespace_WhenStripCharsNull() {
            // given
            String input = "  abc";

            // when
            String result = StringUtils.stripStart(input, null);

            // then
            then(result).as("Leading whitespace removed").isEqualTo("abc");
        }

        @Test
        @DisplayName("Strips NBSP from start when stripChars null")
        void stripStart_ShouldStripNbsp_WhenStripCharsNull() {
            // given
            String input = "\u00A0abc";

            // when
            String result = StringUtils.stripStart(input, null);

            // then
            then(result).as("NBSP removed").isEqualTo("abc");
        }

        @Test
        @DisplayName("Strips given chars from start")
        void stripStart_ShouldStripGivenChars_WhenStripCharsProvided() {
            // given
            String input = "xxabc";

            // when
            String result = StringUtils.stripStart(input, "x");

            // then
            then(result).as("Leading x's removed").isEqualTo("abc");
        }

        @Test
        @DisplayName("Returns null for null input")
        void stripStart_ShouldReturnNull_WhenInputIsNull() {
            // given
            String input = null;

            // when
            String result = StringUtils.stripStart(input, "x");

            // then
            then(result).as("Null stays null").isNull();
        }

        @Test
        @DisplayName("Returns empty for empty input")
        void stripStart_ShouldReturnEmpty_WhenInputIsEmpty() {
            // given
            String input = "";

            // when
            String result = StringUtils.stripStart(input, "x");

            // then
            then(result).as("Empty stays empty").isEmpty();
        }

        @Test
        @DisplayName("Returns input unchanged when first char is not whitespace or NBSP")
        void stripStart_ShouldReturnOriginal_WhenFirstCharNotWhitespace() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.stripStart(input, null);

            // then
            then(result).as("No leading whitespace to strip").isEqualTo("abc");
        }

        @Test
        @DisplayName("Returns input unchanged when first char is not in stripChars")
        void stripStart_ShouldReturnOriginal_WhenFirstCharNotInStripChars() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.stripStart(input, "x");

            // then
            then(result).as("No leading char from stripChars to strip").isEqualTo("abc");
        }

        @Test
        @DisplayName("Returns empty when all chars are stripped by whitespace mode")
        void stripStart_ShouldReturnEmpty_WhenAllCharsAreWhitespace() {
            // given
            String input = "   ";

            // when
            String result = StringUtils.stripStart(input, null);

            // then
            then(result).as("Entire string stripped").isEmpty();
        }

        @Test
        @DisplayName("Returns empty when all chars are stripped by stripChars")
        void stripStart_ShouldReturnEmpty_WhenAllCharsMatchStripChars() {
            // given
            String input = "xxx";

            // when
            String result = StringUtils.stripStart(input, "x");

            // then
            then(result).as("Entire string stripped").isEmpty();
        }

        @Test
        @DisplayName("Strips whitespace when stripChars is empty")
        void stripStart_ShouldStripWhitespace_WhenStripCharsIsEmpty() {
            // given
            String input = "  abc";

            // when
            String result = StringUtils.stripStart(input, "");

            // then
            then(result).as("Empty stripChars falls back to whitespace mode").isEqualTo("abc");
        }
    }

    @Nested
    class SubstringFrom {

        @Test
        @DisplayName("Returns substring from given start index")
        void substring_ShouldReturnFromIndex_WhenStartPositive() {
            // given
            String input = "abcdef";

            // when
            String result = StringUtils.substring(input, 3);

            // then
            then(result).as("From index 3").isEqualTo("def");
        }

        @Test
        @DisplayName("Treats negative start relative to end")
        void substring_ShouldTreatNegativeStart_WhenStartIsNegative() {
            // given
            String input = "abcdef";

            // when
            String result = StringUtils.substring(input, -2);

            // then
            then(result).as("Last two chars").isEqualTo("ef");
        }

        @Test
        @DisplayName("Clamps negative start to zero")
        void substring_ShouldClampToZero_WhenStartStillNegative() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.substring(input, -10);

            // then
            then(result).as("Whole string").isEqualTo("abc");
        }

        @ParameterizedTest
        @ValueSource(strings = {"abc", "", "   ", "abcdef"})
        @DisplayName("Returns empty for start beyond string length")
        void substring_ShouldReturnEmpty_WhenStartBeyondLength(String input) {
            // given
            // (input provided by @ValueSource)

            // when
            String result = StringUtils.substring(input, 10);

            // then
            then(result).as("Start 10 is beyond '%s'", input).isEmpty();
        }

        @Test
        @DisplayName("Returns empty for null input")
        void substring_ShouldReturnEmpty_WhenInputIsNull() {
            // given
            String input = null;

            // when
            String result = StringUtils.substring(input, 0);

            // then
            then(result).as("Null input").isEmpty();
        }
    }

    @Nested
    class SubstringAfter {

        @Test
        @DisplayName("Returns substring after first separator")
        void substringAfter_ShouldReturnAfterSeparator_WhenSeparatorPresent() {
            // given
            String input = "key=value";

            // when
            String result = StringUtils.substringAfter(input, "=");

            // then
            then(result).as("After '='").isEqualTo("value");
        }

        @Test
        @DisplayName("Returns original when separator empty")
        void substringAfter_ShouldReturnOriginal_WhenSeparatorEmpty() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.substringAfter(input, "");

            // then
            then(result).as("No separator").isEqualTo("abc");
        }

        @ParameterizedTest
        @ValueSource(strings = {"abc", "", "   ", "hello world"})
        @DisplayName("Returns empty when separator is absent")
        void substringAfter_ShouldReturnEmpty_WhenSeparatorAbsent(String input) {
            // given
            // (input provided by @ValueSource)

            // when
            String result = StringUtils.substringAfter(input, "=");

            // then
            then(result).as("'=' is absent from '%s'", input).isEmpty();
        }

        @Test
        @DisplayName("Returns empty for null input")
        void substringAfter_ShouldReturnEmpty_WhenInputIsNull() {
            // given
            String input = null;

            // when
            String result = StringUtils.substringAfter(input, "=");

            // then
            then(result).as("Null input").isEmpty();
        }
    }

    @Nested
    class SubstringAfterLast {

        @Test
        @DisplayName("Returns substring after last separator")
        void substringAfterLast_ShouldReturnAfterLast_WhenSeparatorPresent() {
            // given
            String input = "a.b.c";

            // when
            String result = StringUtils.substringAfterLast(input, ".");

            // then
            then(result).as("'c'").isEqualTo("c");
        }

        @Test
        @DisplayName("Returns original when separator empty")
        void substringAfterLast_ShouldReturnOriginal_WhenSeparatorEmpty() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.substringAfterLast(input, "");

            // then
            then(result).as("Whole").isEqualTo("abc");
        }

        @ParameterizedTest
        @ValueSource(strings = {"abc.", "abc", "abcd"})
        @DisplayName("Returns empty when separator is at end or absent")
        void substringAfterLast_ShouldReturnEmpty_WhenSeparatorAtEndOrAbsent(String input) {
            // given
            // (input provided by @ValueSource)

            // when
            String result = StringUtils.substringAfterLast(input, ".");

            // then
            then(result).as("No tail after '.' in '%s'", input).isEmpty();
        }

        @Test
        @DisplayName("Returns empty for null input")
        void substringAfterLast_ShouldReturnEmpty_WhenInputIsNull() {
            // given
            String input = null;

            // when
            String result = StringUtils.substringAfterLast(input, ".");

            // then
            then(result).as("Empty").isEmpty();
        }
    }

    @Nested
    class SubstringBefore {

        @Test
        @DisplayName("Returns substring before first separator")
        void substringBefore_ShouldReturnBeforeSeparator_WhenSeparatorPresent() {
            // given
            String input = "key=value";

            // when
            String result = StringUtils.substringBefore(input, "=");

            // then
            then(result).as("Before '='").isEqualTo("key");
        }

        @Test
        @DisplayName("Returns original when separator empty")
        void substringBefore_ShouldReturnOriginal_WhenSeparatorEmpty() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.substringBefore(input, "");

            // then
            then(result).as("No separator").isEqualTo("abc");
        }

        @ParameterizedTest
        @ValueSource(strings = {"abc", "", "   ", "hello world"})
        @DisplayName("Returns empty when separator is absent")
        void substringBefore_ShouldReturnEmpty_WhenSeparatorAbsent(String input) {
            // given
            // (input provided by @ValueSource)

            // when
            String result = StringUtils.substringBefore(input, "=");

            // then
            then(result).as("'=' is absent from '%s'", input).isEmpty();
        }

        @Test
        @DisplayName("Returns empty for null input")
        void substringBefore_ShouldReturnEmpty_WhenInputIsNull() {
            // given
            String input = null;

            // when
            String result = StringUtils.substringBefore(input, "=");

            // then
            then(result).as("Empty").isEmpty();
        }
    }

    @Nested
    class SubstringBeforeLast {

        @Test
        @DisplayName("Returns substring before last separator")
        void substringBeforeLast_ShouldReturnBeforeLast_WhenSeparatorPresent() {
            // given
            String input = "a.b.c";

            // when
            String result = StringUtils.substringBeforeLast(input, ".");

            // then
            then(result).as("'a.b'").isEqualTo("a.b");
        }

        @Test
        @DisplayName("Returns original when separator absent")
        void substringBeforeLast_ShouldReturnOriginal_WhenSeparatorAbsent() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.substringBeforeLast(input, ".");

            // then
            then(result).as("Whole").isEqualTo("abc");
        }

        @Test
        @DisplayName("Returns original when input is null")
        void substringBeforeLast_ShouldReturnNull_WhenInputIsNull() {
            // given
            String input = null;

            // when
            String result = StringUtils.substringBeforeLast(input, ".");

            // then
            then(result).as("Null stays null").isNull();
        }

        @Test
        @DisplayName("Returns empty for empty input")
        void substringBeforeLast_ShouldReturnEmpty_WhenInputIsEmpty() {
            // given
            String input = "";

            // when
            String result = StringUtils.substringBeforeLast(input, ".");

            // then
            then(result).as("Empty").isEmpty();
        }

        @Test
        @DisplayName("Returns original when separator empty")
        void substringBeforeLast_ShouldReturnOriginal_WhenSeparatorEmpty() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.substringBeforeLast(input, "");

            // then
            then(result).as("Whole").isEqualTo("abc");
        }
    }

    @Nested
    class SubstringBetween {

        @Test
        @DisplayName("Returns substring between open and close tags")
        void substringBetween_ShouldReturnBetweenTags_WhenTagsPresent() {
            // given
            String input = "<tag>value</tag>";

            // when
            String result = StringUtils.substringBetween(input, "<tag>", "</tag>");

            // then
            then(result).as("Content between tags").isEqualTo("value");
        }

        @Test
        @DisplayName("Returns empty when close tag absent")
        void substringBetween_ShouldReturnEmpty_WhenCloseAbsent() {
            // given
            String input = "<tag>value";

            // when
            String result = StringUtils.substringBetween(input, "<tag>", "</tag>");

            // then
            then(result).as("No close tag").isEmpty();
        }

        @Test
        @DisplayName("Returns empty when open tag absent")
        void substringBetween_ShouldReturnEmpty_WhenOpenAbsent() {
            // given
            String input = "value</tag>";

            // when
            String result = StringUtils.substringBetween(input, "<tag>", "</tag>");

            // then
            then(result).as("No open tag").isEmpty();
        }

        @Test
        @DisplayName("Returns original when open is empty")
        void substringBetween_ShouldReturnOriginal_WhenOpenEmpty() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.substringBetween(input, "", "</tag>");

            // then
            then(result).as("No open").isEqualTo("abc");
        }

        @Test
        @DisplayName("Returns original when close is empty")
        void substringBetween_ShouldReturnOriginal_WhenCloseEmpty() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.substringBetween(input, "<tag>", "");

            // then
            then(result).as("No close").isEqualTo("abc");
        }

        @Test
        @DisplayName("Returns empty for null input")
        void substringBetween_ShouldReturnEmpty_WhenInputIsNull() {
            // given
            String input = null;

            // when
            String result = StringUtils.substringBetween(input, "<", ">");

            // then
            then(result).as("Empty").isEmpty();
        }

        @Test
        @DisplayName("Returns empty for empty input")
        void substringBetween_ShouldReturnEmpty_WhenInputIsEmpty() {
            // given
            String input = "";

            // when
            String result = StringUtils.substringBetween(input, "<", ">");

            // then
            then(result).as("Empty").isEmpty();
        }

        @Test
        @DisplayName("Returns empty for blank input")
        void substringBetween_ShouldReturnEmpty_WhenInputIsBlank() {
            // given
            String input = "   ";

            // when
            String result = StringUtils.substringBetween(input, "<", ">");

            // then
            then(result).as("Empty").isEmpty();
        }
    }

    @Nested
    class SubstringMatch {

        @Test
        @DisplayName("Returns true when substring matches at index")
        void substringMatch_ShouldReturnTrue_WhenSubstringMatches() {
            // given
            String input = "abcdef";

            // when
            boolean result = StringUtils.substringMatch(input, 2, "cde");

            // then
            then(result).as("Matches at index 2").isTrue();
        }

        @Test
        @DisplayName("Returns false when substring mismatches")
        void substringMatch_ShouldReturnFalse_WhenSubstringMismatches() {
            // given
            String input = "abcdef";

            // when
            boolean result = StringUtils.substringMatch(input, 2, "xyz");

            // then
            then(result).as("Mismatch").isFalse();
        }

        @Test
        @DisplayName("Returns false when substring exceeds string length")
        void substringMatch_ShouldReturnFalse_WhenSubstringExceedsLength() {
            // given
            String input = "abc";

            // when
            boolean result = StringUtils.substringMatch(input, 1, "bcdef");

            // then
            then(result).as("Too long").isFalse();
        }

        @Test
        @DisplayName("Returns false for null input")
        void substringMatch_ShouldReturnFalse_WhenInputIsNull() {
            // given
            String input = null;

            // when
            boolean result = StringUtils.substringMatch(input, 0, "a");

            // then
            then(result).as("Null input").isFalse();
        }

        @Test
        @DisplayName("Returns false for blank input")
        void substringMatch_ShouldReturnFalse_WhenInputIsBlank() {
            // given
            String input = "   ";

            // when
            boolean result = StringUtils.substringMatch(input, 0, "a");

            // then
            then(result).as("Blank input").isFalse();
        }

        @Test
        @DisplayName("Returns false for empty substring")
        void substringMatch_ShouldReturnFalse_WhenSubstringIsEmpty() {
            // given
            String input = "abc";

            // when
            boolean result = StringUtils.substringMatch(input, 0, "");

            // then
            then(result).as("Empty substring").isFalse();
        }

        @Test
        @DisplayName("Throws IllegalArgumentException when index is negative")
        void substringMatch_ShouldThrowIllegalArgumentException_WhenIndexIsNegative() {
            // given
            String input = "abc";

            // when & then
            thenThrownBy(() -> StringUtils.substringMatch(input, -1, "a"))
                    .as("Negative index not allowed")
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Index must be positive");
        }
    }

    @Nested
    class SubstringsBetween {

        @Test
        @DisplayName("Returns all substrings between matching tags")
        void substringsBetween_ShouldReturnAll_WhenTagsPresent() {
            // given
            String input = "<a>x</a><a>y</a>";

            // when
            String[] result = StringUtils.substringsBetween(input, "<a>", "</a>");

            // then
            then(result).as("Two substrings").containsExactly("x", "y");
        }

        @Test
        @DisplayName("Returns empty array when no matches")
        void substringsBetween_ShouldReturnEmpty_WhenNoMatches() {
            // given
            String input = "abc";

            // when
            String[] result = StringUtils.substringsBetween(input, "<a>", "</a>");

            // then
            then(result).as("No matches").isEmpty();
        }

        @Test
        @DisplayName("Returns empty array when open tag missing")
        void substringsBetween_ShouldReturnEmpty_WhenOpenMissing() {
            // given
            String input = "x</a>";

            // when
            String[] result = StringUtils.substringsBetween(input, "<a>", "</a>");

            // then
            then(result).as("No open tag").isEmpty();
        }

        @Test
        @DisplayName("Returns empty array for null input")
        void substringsBetween_ShouldReturnEmpty_WhenInputIsNull() {
            // given
            String input = null;

            // when
            String[] result = StringUtils.substringsBetween(input, "<", ">");

            // then
            then(result).as("Null input").isEmpty();
        }

        @Test
        @DisplayName("Returns empty array for empty input")
        void substringsBetween_ShouldReturnEmpty_WhenInputIsEmpty() {
            // given
            String input = "";

            // when
            String[] result = StringUtils.substringsBetween(input, "<", ">");

            // then
            then(result).as("Empty input").isEmpty();
        }

        @Test
        @DisplayName("Returns empty array for blank input")
        void substringsBetween_ShouldReturnEmpty_WhenInputIsBlank() {
            // given
            String input = "   ";

            // when
            String[] result = StringUtils.substringsBetween(input, "<", ">");

            // then
            then(result).as("Blank input").isEmpty();
        }

        @Test
        @DisplayName("Returns empty array when open is empty")
        void substringsBetween_ShouldReturnEmpty_WhenOpenEmpty() {
            // given
            String input = "abc";

            // when
            String[] result = StringUtils.substringsBetween(input, "", ">");

            // then
            then(result).as("Empty open").isEmpty();
        }

        @Test
        @DisplayName("Returns empty array when open tag present but close tag missing")
        void substringsBetween_ShouldReturnEmpty_WhenCloseMissing() {
            // given
            String input = "<a>value";

            // when
            String[] result = StringUtils.substringsBetween(input, "<a>", "</a>");

            // then
            then(result).as("Open tag found, close missing").isEmpty();
        }

        @Test
        @DisplayName("Returns empty array when open is null")
        void substringsBetween_ShouldReturnEmpty_WhenOpenIsNull() {
            // given
            String input = "abc";

            // when
            String[] result = StringUtils.substringsBetween(input, null, "</a>");

            // then
            then(result).as("Null open tag").isEmpty();
        }

        @Test
        @DisplayName("Returns empty array when close is null")
        void substringsBetween_ShouldReturnEmpty_WhenCloseIsNull() {
            // given
            String input = "abc";

            // when
            String[] result = StringUtils.substringsBetween(input, "<a>", null);

            // then
            then(result).as("Null close tag").isEmpty();
        }
    }

    @Nested
    class SwapCase {

        @Test
        @DisplayName("Swaps upper to lower and lower to title/upper case")
        void swapCase_ShouldSwap_WhenMixedCase() {
            // given
            String input = "Hello World";

            // when
            String result = StringUtils.swapCase(input);

            // then
            then(result).as("Swapped").isEqualTo("hELLO wORLD");
        }

        @Test
        @DisplayName("Converts uppercase to lowercase across the whole string")
        void swapCase_ShouldConvertAllUpperToLower_WhenInputIsAllUpper() {
            // given
            String input = "ABC";

            // when
            String result = StringUtils.swapCase(input);

            // then
            then(result).as("All upper becomes all lower").isEqualTo("abc");
        }

        @Test
        @DisplayName("Title-cases first char and uppercases the rest when input is all lower")
        void swapCase_ShouldTitleFirstAndUpperRest_WhenInputIsAllLower() {
            // given
            String input = "abc def";

            // when
            String result = StringUtils.swapCase(input);

            // then
            then(result)
                    .as("Every lowercase letter becomes uppercase (title == upper for ASCII)")
                    .isEqualTo("ABC DEF");
        }

        @Test
        @DisplayName("Swaps title-case input to lowercase")
        void swapCase_ShouldLowerTitleCase_WhenInputHasTitleCase() {
            // given
            String input = "ǅabc";

            // when
            String result = StringUtils.swapCase(input);

            // then
            then(result).as("Title-case char becomes lower-case").isEqualTo("ǆABC");
        }

        @Test
        @DisplayName("Preserves non-letter chars and resets whitespace flag")
        void swapCase_ShouldPreserveNonLetterAndResetWhitespace_WhenPunctuationPresent() {
            // given
            String input = "a1 b";

            // when
            String result = StringUtils.swapCase(input);

            // then
            then(result).as("Digits kept, whitespace resets capitalization").isEqualTo("A1 B");
        }

        @Test
        @DisplayName("Returns null for null input")
        void swapCase_ShouldReturnNull_WhenInputIsNull() {
            // given
            String input = null;

            // when
            String result = StringUtils.swapCase(input);

            // then
            then(result).as("Null stays null").isNull();
        }

        @Test
        @DisplayName("Returns empty for empty input")
        void swapCase_ShouldReturnEmpty_WhenInputIsEmpty() {
            // given
            String input = "";

            // when
            String result = StringUtils.swapCase(input);

            // then
            then(result).as("Empty").isEmpty();
        }

        @Test
        @DisplayName("Returns blank input unchanged")
        void swapCase_ShouldReturnBlank_WhenInputIsBlank() {
            // given
            String input = "   ";

            // when
            String result = StringUtils.swapCase(input);

            // then
            then(result).as("Blank unchanged").isEqualTo("   ");
        }
    }

    @Nested
    class ToCamelCase {

        @Test
        @DisplayName("Converts snake_case to camelCase")
        void toCamelCase_ShouldConvert_WhenSnakeCase() {
            // given
            String input = "hello_world";

            // when
            String result = StringUtils.toCamelCase(input);

            // then
            then(result).as("Camel case").isEqualTo("helloWorld");
        }

        @Test
        @DisplayName("Returns null for null input")
        void toCamelCase_ShouldReturnNull_WhenInputIsNull() {
            // given
            String input = null;

            // when
            String result = StringUtils.toCamelCase(input);

            // then
            then(result).as("Null stays null").isNull();
        }

        @Test
        @DisplayName("Returns empty for empty input")
        void toCamelCase_ShouldReturnEmpty_WhenInputIsEmpty() {
            // given
            String input = "";

            // when
            String result = StringUtils.toCamelCase(input);

            // then
            then(result).as("Empty").isEmpty();
        }

        @Test
        @DisplayName("Returns blank input unchanged")
        void toCamelCase_ShouldReturnBlank_WhenInputIsBlank() {
            // given
            String input = "   ";

            // when
            String result = StringUtils.toCamelCase(input);

            // then
            then(result).as("Blank unchanged").isEqualTo("   ");
        }
    }

    @Nested
    class ToSnakeCase {

        @Test
        @DisplayName("Converts camelCase to snake_case")
        void toSnakeCase_ShouldConvert_WhenCamelCase() {
            // given
            String input = "helloWorld";

            // when
            String result = StringUtils.toSnakeCase(input);

            // then
            then(result).as("Snake case").isEqualTo("hello_world");
        }

        @Test
        @DisplayName("Converts PascalCase to snake_case")
        void toSnakeCase_ShouldConvert_WhenPascalCase() {
            // given
            String input = "HelloWorld";

            // when
            String result = StringUtils.toSnakeCase(input);

            // then
            then(result).as("Snake case").isEqualTo("hello_world");
        }

        @Test
        @DisplayName("Returns null for null input")
        void toSnakeCase_ShouldReturnNull_WhenInputIsNull() {
            // given
            String input = null;

            // when
            String result = StringUtils.toSnakeCase(input);

            // then
            then(result).as("Null stays null").isNull();
        }

        @Test
        @DisplayName("Returns empty for empty input")
        void toSnakeCase_ShouldReturnEmpty_WhenInputIsEmpty() {
            // given
            String input = "";

            // when
            String result = StringUtils.toSnakeCase(input);

            // then
            then(result).as("Empty").isEmpty();
        }

        @Test
        @DisplayName("Returns blank input unchanged")
        void toSnakeCase_ShouldReturnBlank_WhenInputIsBlank() {
            // given
            String input = "   ";

            // when
            String result = StringUtils.toSnakeCase(input);

            // then
            then(result).as("Blank unchanged").isEqualTo("   ");
        }
    }

    @Nested
    class ToStringCollection {

        @Test
        @DisplayName("Converts a collection to an array")
        void toString_ShouldConvertCollection_WhenElementsPresent() {
            // given
            List<String> input = Arrays.asList("a", "b");

            // when
            String[] result = StringUtils.toString(input);

            // then
            then(result).as("Same elements").containsExactly("a", "b");
        }

        @Test
        @DisplayName("Returns empty array for null collection")
        void toString_ShouldReturnEmpty_WhenCollectionIsNull() {
            // given
            List<String> input = null;

            // when
            String[] result = StringUtils.toString(input);

            // then
            then(result).as("Empty").isEmpty();
        }

        @Test
        @DisplayName("Returns empty array for empty collection")
        void toString_ShouldReturnEmpty_WhenCollectionIsEmpty() {
            // given
            List<String> input = Collections.emptyList();

            // when
            String[] result = StringUtils.toString(input);

            // then
            then(result).as("Empty").isEmpty();
        }
    }

    @Nested
    class ToStringEnumeration {

        @Test
        @DisplayName("Converts an enumeration to an array")
        void toString_ShouldConvertEnumeration_WhenElementsPresent() {
            // given
            Enumeration<String> input = Collections.enumeration(Arrays.asList("a", "b"));

            // when
            String[] result = StringUtils.toString(input);

            // then
            then(result).as("Same elements").containsExactly("a", "b");
        }

        @Test
        @DisplayName("Returns empty array for null enumeration")
        void toString_ShouldReturnEmpty_WhenEnumerationIsNull() {
            // given
            Enumeration<String> input = null;

            // when
            String[] result = StringUtils.toString(input);

            // then
            then(result).as("Empty").isEmpty();
        }

        @Test
        @DisplayName("Returns empty array for empty enumeration")
        void toString_ShouldReturnEmpty_WhenEnumerationIsEmpty() {
            // given
            Enumeration<String> input = new Vector<String>().elements();

            // when
            String[] result = StringUtils.toString(input);

            // then
            then(result).as("Empty").isEmpty();
        }
    }

    @Nested
    class TrimArrayElements {

        @Test
        @DisplayName("Trims each non-null element")
        void trimArrayElements_ShouldTrimEach_WhenArrayHasElements() {
            // given
            String[] input = {"  a ", null, " b  "};

            // when
            String[] result = StringUtils.trimArrayElements(input);

            // then
            then(result).as("Trimmed with null preserved").containsExactly("a", null, "b");
        }

        @Test
        @DisplayName("Returns null for null input")
        void trimArrayElements_ShouldReturnNull_WhenArrayIsNull() {
            // given
            String[] input = null;

            // when
            String[] result = StringUtils.trimArrayElements(input);

            // then
            then(result).as("Null stays null").isNull();
        }

        @Test
        @DisplayName("Returns empty for empty input")
        void trimArrayElements_ShouldReturnEmpty_WhenArrayIsEmpty() {
            // given
            String[] input = new String[0];

            // when
            String[] result = StringUtils.trimArrayElements(input);

            // then
            then(result).as("Empty stays empty").isEmpty();
        }
    }

    @Nested
    class TrimLeadingCharacter {

        @Test
        @DisplayName("Removes leading occurrences of a char")
        void trimLeadingCharacter_ShouldRemoveLeading_WhenCharMatches() {
            // given
            String input = "xxabc";

            // when
            String result = StringUtils.trimLeadingCharacter(input, 'x');

            // then
            then(result).as("Leading x removed").isEqualTo("abc");
        }

        @Test
        @DisplayName("Returns original when first char differs")
        void trimLeadingCharacter_ShouldReturnOriginal_WhenFirstCharDiffers() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.trimLeadingCharacter(input, 'x');

            // then
            then(result).as("No change").isEqualTo("abc");
        }

        @Test
        @DisplayName("Returns null for null input")
        void trimLeadingCharacter_ShouldReturnNull_WhenInputIsNull() {
            // given
            String input = null;

            // when
            String result = StringUtils.trimLeadingCharacter(input, 'x');

            // then
            then(result).as("Null stays null").isNull();
        }

        @Test
        @DisplayName("Returns empty for empty input")
        void trimLeadingCharacter_ShouldReturnEmpty_WhenInputIsEmpty() {
            // given
            String input = "";

            // when
            String result = StringUtils.trimLeadingCharacter(input, 'x');

            // then
            then(result).as("Empty stays empty").isEmpty();
        }

        @Test
        @DisplayName("Returns empty when all chars match leading char")
        void trimLeadingCharacter_ShouldReturnEmpty_WhenAllCharsMatch() {
            // given
            String input = "xxx";

            // when
            String result = StringUtils.trimLeadingCharacter(input, 'x');

            // then
            then(result).as("Entire string trimmed").isEmpty();
        }

        @Test
        @DisplayName("Returns blank input unchanged when it does not start with leading char")
        void trimLeadingCharacter_ShouldReturnBlank_WhenLeadingCharAbsent() {
            // given
            String input = "   ";

            // when
            String result = StringUtils.trimLeadingCharacter(input, 'x');

            // then
            then(result).as("Blank input has no leading 'x'").isEqualTo("   ");
        }
    }

    @Nested
    class TrimTrailingCharacter {

        @Test
        @DisplayName("Removes trailing occurrences of a char")
        void trimTrailingCharacter_ShouldRemoveTrailing_WhenCharMatches() {
            // given
            String input = "abcxx";

            // when
            String result = StringUtils.trimTrailingCharacter(input, 'x');

            // then
            then(result).as("Trailing x removed").isEqualTo("abc");
        }

        @Test
        @DisplayName("Returns original when last char differs")
        void trimTrailingCharacter_ShouldReturnOriginal_WhenLastCharDiffers() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.trimTrailingCharacter(input, 'x');

            // then
            then(result).as("No change").isEqualTo("abc");
        }

        @Test
        @DisplayName("Returns null for null input")
        void trimTrailingCharacter_ShouldReturnNull_WhenInputIsNull() {
            // given
            String input = null;

            // when
            String result = StringUtils.trimTrailingCharacter(input, 'x');

            // then
            then(result).as("Null stays null").isNull();
        }

        @Test
        @DisplayName("Returns empty for empty input")
        void trimTrailingCharacter_ShouldReturnEmpty_WhenInputIsEmpty() {
            // given
            String input = "";

            // when
            String result = StringUtils.trimTrailingCharacter(input, 'x');

            // then
            then(result).as("Empty stays empty").isEmpty();
        }

        @Test
        @DisplayName("Returns empty when all chars match trailing char")
        void trimTrailingCharacter_ShouldReturnEmpty_WhenAllCharsMatch() {
            // given
            String input = "xxx";

            // when
            String result = StringUtils.trimTrailingCharacter(input, 'x');

            // then
            then(result).as("Entire string trimmed").isEmpty();
        }

        @Test
        @DisplayName("Returns blank input unchanged when it does not end with trailing char")
        void trimTrailingCharacter_ShouldReturnBlank_WhenTrailingCharAbsent() {
            // given
            String input = "   ";

            // when
            String result = StringUtils.trimTrailingCharacter(input, 'x');

            // then
            then(result).as("Blank input has no trailing 'x'").isEqualTo("   ");
        }
    }

    @Nested
    class Truncate {

        @Test
        @DisplayName("Truncates to max width when longer")
        void truncate_ShouldTruncate_WhenLongerThanMaxWidth() {
            // given
            String input = "abcdef";

            // when
            String result = StringUtils.truncate(input, 3);

            // then
            then(result).as("Truncated").isEqualTo("abc");
        }

        @Test
        @DisplayName("Returns original when within max width")
        void truncate_ShouldReturnOriginal_WhenWithinMaxWidth() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.truncate(input, 5);

            // then
            then(result).as("Unchanged").isEqualTo("abc");
        }

        @Test
        @DisplayName("Returns original when maxWidth is negative")
        void truncate_ShouldReturnOriginal_WhenMaxWidthNegative() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.truncate(input, -1);

            // then
            then(result).as("Unchanged").isEqualTo("abc");
        }

        @Test
        @DisplayName("Returns original when input is null")
        void truncate_ShouldReturnNull_WhenInputIsNull() {
            // given
            String input = null;

            // when
            String result = StringUtils.truncate(input, 3);

            // then
            then(result).as("Null stays null").isNull();
        }

        @Test
        @DisplayName("Returns empty when input is empty")
        void truncate_ShouldReturnEmpty_WhenInputIsEmpty() {
            // given
            String input = "";

            // when
            String result = StringUtils.truncate(input, 3);

            // then
            then(result).as("Empty").isEmpty();
        }
    }

    @Nested
    class Uncapitalize {

        @Test
        @DisplayName("Uncapitalizes first character of uppercase input")
        void uncapitalize_ShouldLowerFirstChar_WhenFirstIsUppercase() {
            // given
            String input = "Hello";

            // when
            String result = StringUtils.uncapitalize(input);

            // then
            then(result).as("First lowered").isEqualTo("hello");
        }

        @Test
        @DisplayName("Returns same string when first char already lowercase")
        void uncapitalize_ShouldReturnOriginal_WhenAlreadyLowercase() {
            // given
            String input = "hello";

            // when
            String result = StringUtils.uncapitalize(input);

            // then
            then(result).as("Unchanged").isEqualTo("hello");
        }

        @Test
        @DisplayName("Returns null for null input")
        void uncapitalize_ShouldReturnNull_WhenInputIsNull() {
            // given
            String input = null;

            // when
            String result = StringUtils.uncapitalize(input);

            // then
            then(result).as("Null stays null").isNull();
        }

        @Test
        @DisplayName("Returns empty for empty input")
        void uncapitalize_ShouldReturnEmpty_WhenInputIsEmpty() {
            // given
            String input = "";

            // when
            String result = StringUtils.uncapitalize(input);

            // then
            then(result).as("Empty").isEmpty();
        }

        @Test
        @DisplayName("Returns blank input unchanged")
        void uncapitalize_ShouldReturnBlank_WhenInputIsBlank() {
            // given
            String input = "   ";

            // when
            String result = StringUtils.uncapitalize(input);

            // then
            then(result).as("Blank unchanged").isEqualTo("   ");
        }
    }

    @Nested
    class Unwrap {

        @Test
        @DisplayName("Unwraps matching wrap chars from both ends")
        void unwrap_ShouldRemoveWrapChars_WhenBothEndsMatch() {
            // given
            String input = "\"abc\"";

            // when
            String result = StringUtils.unwrap(input, '"');

            // then
            then(result).as("Quotes removed").isEqualTo("abc");
        }

        @Test
        @DisplayName("Returns original when only last char matches wrapToken")
        void unwrap_ShouldReturnOriginal_WhenEndsDiffer() {
            // given
            String input = "\"abc";

            // when
            String result = StringUtils.unwrap(input, '"');

            // then
            then(result).as("Last char is not the wrap token").isEqualTo("\"abc");
        }

        @Test
        @DisplayName("Returns original when first char does not match wrapToken")
        void unwrap_ShouldReturnOriginal_WhenFirstCharDoesNotMatch() {
            // given
            String input = "abc\"";

            // when
            String result = StringUtils.unwrap(input, '"');

            // then
            then(result).as("First char is not the wrap token").isEqualTo("abc\"");
        }

        @Test
        @DisplayName("Returns null for null input")
        void unwrap_ShouldReturnNull_WhenInputIsNull() {
            // given
            String input = null;

            // when
            String result = StringUtils.unwrap(input, '"');

            // then
            then(result).as("Null stays null").isNull();
        }

        @Test
        @DisplayName("Returns empty for empty input")
        void unwrap_ShouldReturnEmpty_WhenInputIsEmpty() {
            // given
            String input = "";

            // when
            String result = StringUtils.unwrap(input, '"');

            // then
            then(result).as("Empty").isEmpty();
        }

        @Test
        @DisplayName("Returns blank input unchanged")
        void unwrap_ShouldReturnBlank_WhenInputIsBlank() {
            // given
            String input = "   ";

            // when
            String result = StringUtils.unwrap(input, '"');

            // then
            then(result).as("Blank unchanged").isEqualTo("   ");
        }

        @Test
        @DisplayName("Returns single-char string unchanged when length is less than 2")
        void unwrap_ShouldReturnOriginal_WhenSingleCharAndNotWrapToken() {
            // given
            String input = "a";

            // when
            String result = StringUtils.unwrap(input, '"');

            // then
            then(result).as("Single char not the wrap token").isEqualTo("a");
        }

        @Test
        @DisplayName("Returns single-char wrap token unchanged without exception")
        void unwrap_ShouldReturnOriginal_WhenSingleCharIsWrapToken() {
            // given
            String input = "\"";

            // when
            String result = StringUtils.unwrap(input, '"');

            // then
            then(result).as("Single wrap char returned unchanged").isEqualTo("\"");
        }
    }

    @Nested
    class UpperCase {

        @Test
        @DisplayName("Returns uppercase string")
        void upperCase_ShouldReturnUppercase_WhenInputNotNull() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.upperCase(input);

            // then
            then(result).as("Uppercase").isEqualTo("ABC");
        }

        @Test
        @DisplayName("Returns null for null input")
        void upperCase_ShouldReturnNull_WhenInputIsNull() {
            // given
            String input = null;

            // when
            String result = StringUtils.upperCase(input);

            // then
            then(result).as("Null stays null").isNull();
        }
    }

    @Nested
    class Wrap {

        @Test
        @DisplayName("Wraps string with wrapWith on both ends")
        void wrap_ShouldWrapWithOnBothEnds_WhenValidInputs() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.wrap(input, "\"");

            // then
            then(result).as("Wrapped").isEqualTo("\"abc\"");
        }

        @Test
        @DisplayName("Returns original when input is null")
        void wrap_ShouldReturnNull_WhenInputIsNull() {
            // given
            String input = null;

            // when
            String result = StringUtils.wrap(input, "\"");

            // then
            then(result).as("Null stays null").isNull();
        }

        @Test
        @DisplayName("Returns original when input is empty")
        void wrap_ShouldReturnEmpty_WhenInputIsEmpty() {
            // given
            String input = "";

            // when
            String result = StringUtils.wrap(input, "\"");

            // then
            then(result).as("Empty").isEmpty();
        }

        @Test
        @DisplayName("Returns original when input is blank")
        void wrap_ShouldReturnBlank_WhenInputIsBlank() {
            // given
            String input = "   ";

            // when
            String result = StringUtils.wrap(input, "\"");

            // then
            then(result).as("Blank unchanged").isEqualTo("   ");
        }

        @Test
        @DisplayName("Returns original when wrapWith is null")
        void wrap_ShouldReturnOriginal_WhenWrapWithIsNull() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.wrap(input, null);

            // then
            then(result).as("No wrap").isEqualTo("abc");
        }

        @Test
        @DisplayName("Returns original when wrapWith is empty")
        void wrap_ShouldReturnOriginal_WhenWrapWithIsEmpty() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.wrap(input, "");

            // then
            then(result).as("No wrap").isEqualTo("abc");
        }
    }

    @Nested
    class WrapIfMissing {

        @Test
        @DisplayName("Wraps when both ends are missing the char")
        void wrapIfMissing_ShouldWrapBothEnds_WhenBothMissing() {
            // given
            String input = "abc";

            // when
            String result = StringUtils.wrapIfMissing(input, '"');

            // then
            then(result).as("Both ends wrapped").isEqualTo("\"abc\"");
        }

        @Test
        @DisplayName("Wraps only start when only start is missing")
        void wrapIfMissing_ShouldWrapStartOnly_WhenStartMissing() {
            // given
            String input = "abc\"";

            // when
            String result = StringUtils.wrapIfMissing(input, '"');

            // then
            then(result).as("Start wrapped").isEqualTo("\"abc\"");
        }

        @Test
        @DisplayName("Wraps only end when only end is missing")
        void wrapIfMissing_ShouldWrapEndOnly_WhenEndMissing() {
            // given
            String input = "\"abc";

            // when
            String result = StringUtils.wrapIfMissing(input, '"');

            // then
            then(result).as("End wrapped").isEqualTo("\"abc\"");
        }

        @Test
        @DisplayName("Returns original when already wrapped")
        void wrapIfMissing_ShouldReturnOriginal_WhenAlreadyWrapped() {
            // given
            String input = "\"abc\"";

            // when
            String result = StringUtils.wrapIfMissing(input, '"');

            // then
            then(result).as("Already wrapped").isSameAs(input);
        }

        @Test
        @DisplayName("Returns null for null input")
        void wrapIfMissing_ShouldReturnNull_WhenInputIsNull() {
            // given
            String input = null;

            // when
            String result = StringUtils.wrapIfMissing(input, '"');

            // then
            then(result).as("Null stays null").isNull();
        }

        @Test
        @DisplayName("Returns empty for empty input")
        void wrapIfMissing_ShouldReturnEmpty_WhenInputIsEmpty() {
            // given
            String input = "";

            // when
            String result = StringUtils.wrapIfMissing(input, '"');

            // then
            then(result).as("Empty").isEmpty();
        }

        @Test
        @DisplayName("Returns blank input unchanged")
        void wrapIfMissing_ShouldReturnBlank_WhenInputIsBlank() {
            // given
            String input = "   ";

            // when
            String result = StringUtils.wrapIfMissing(input, '"');

            // then
            then(result).as("Blank unchanged").isEqualTo("   ");
        }
    }

    // -------------------------------------------------------------------------
    // Helpers and test doubles
    // -------------------------------------------------------------------------
}
