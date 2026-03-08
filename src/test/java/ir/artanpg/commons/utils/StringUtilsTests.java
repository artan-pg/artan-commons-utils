package ir.artanpg.commons.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Stream;

import static ir.artanpg.commons.utils.StringUtils.COMMA;
import static ir.artanpg.commons.utils.StringUtils.EMPTY;
import static ir.artanpg.commons.utils.StringUtils.INDEX_NOT_FOUND;
import static ir.artanpg.commons.utils.StringUtils.SEMICOLON;
import static ir.artanpg.commons.utils.StringUtils.SPACE;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

/**
 * Unit tests for the {@link StringUtils} class.
 *
 * @author Mohammad Yazdian
 */
@SuppressWarnings({"ConstantValue", "UnnecessaryLocalVariable"})
class StringUtilsTests {

    @Test
    void abbreviate_ShouldReturnEmpty_WhenInputIsNull() {
        // Given
        String input = null;
        int maxWidth = 5;

        // When
        String actual = StringUtils.abbreviate(input, maxWidth);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void abbreviate_ShouldReturnEmpty_WhenInputIsEmpty() {
        // Given
        String input = EMPTY;
        int maxWidth = 5;

        // When
        String actual = StringUtils.abbreviate(input, maxWidth);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void abbreviate_ShouldReturnEmpty_WhenInputIsBlank() {
        // Given
        String input = "   ";
        int maxWidth = 5;

        // When
        String actual = StringUtils.abbreviate(input, maxWidth);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void abbreviate_ShouldReturnOriginalString_WhenStringLengthIsLessThanOrEqualToMaxWidth() {
        // Given
        String input = "hello";
        int maxWidth = 5;

        // When
        String actual = StringUtils.abbreviate(input, maxWidth);

        // Then
        then(actual).isEqualTo("hello");
    }

    @Test
    void abbreviate_ShouldReturnAbbreviatedString_WhenStringIsLongerThanMaxWidth() {
        // Given
        String input = "hello world";
        int maxWidth = 8;

        // When
        String actual = StringUtils.abbreviate(input, maxWidth);

        // Then
        then(actual).isEqualTo("hello...");
    }

    @Test
    void abbreviate_ShouldThrowIllegalArgumentException_WhenMaxWidthIsLessThanMinimum() {
        // Given
        String input = "hello";
        int maxWidth = 3;

        // When & Then
        thenThrownBy(() -> StringUtils.abbreviate(input, maxWidth))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Minimum abbreviation width is 4");
    }

    @Test
    void abbreviate_ShouldReturnEmpty_WhenInputIsNullAndMarkerProvided() {
        // Given
        String input = null;
        String marker = "..";
        int maxWidth = 5;

        // When
        String actual = StringUtils.abbreviate(input, marker, maxWidth);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void abbreviate_ShouldReturnEmpty_WhenInputIsEmptyAndMarkerProvided() {
        // Given
        String input = EMPTY;
        String marker = "..";
        int maxWidth = 5;

        // When
        String actual = StringUtils.abbreviate(input, marker, maxWidth);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void abbreviate_ShouldReturnEmpty_WhenInputIsBlankAndMarkerProvided() {
        // Given
        String input = "   ";
        String marker = "..";
        int maxWidth = 5;

        // When
        String actual = StringUtils.abbreviate(input, marker, maxWidth);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void abbreviate_ShouldReturnEmpty_WhenMarkerIsNull() {
        // Given
        String input = "hello";
        String marker = null;
        int maxWidth = 5;

        // When
        String actual = StringUtils.abbreviate(input, marker, maxWidth);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void abbreviate_ShouldReturnEmpty_WhenMarkerIsEmpty() {
        // Given
        String input = "hello";
        String marker = EMPTY;
        int maxWidth = 5;

        // When
        String actual = StringUtils.abbreviate(input, marker, maxWidth);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void abbreviate_ShouldReturnEmpty_WhenMarkerIsBlank() {
        // Given
        String input = "hello";
        String marker = "   ";
        int maxWidth = 5;

        // When
        String actual = StringUtils.abbreviate(input, marker, maxWidth);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void abbreviate_ShouldReturnOriginalString_WhenStringLengthIsLessThanOrEqualToMaxWidthAndMarkerProvided() {
        // Given
        String input = "hello";
        String marker = "..";
        int maxWidth = 5;

        // When
        String actual = StringUtils.abbreviate(input, marker, maxWidth);

        // Then
        then(actual).isEqualTo("hello");
    }

    @Test
    void abbreviate_ShouldReturnAbbreviatedString_WhenStringIsLongerThanMaxWidthAndMarkerProvided() {
        // Given
        String input = "hello world";
        String marker = "..";
        int maxWidth = 7;

        // When
        String actual = StringUtils.abbreviate(input, marker, maxWidth);

        // Then
        then(actual).isEqualTo("hello..");
    }

    @Test
    void abbreviate_ShouldThrowIllegalArgumentException_WhenMaxWidthIsLessThanMinimumAndMarkerProvided() {
        // Given
        String input = "hello";
        String marker = "..";
        int maxWidth = 2;

        // When & Then
        thenThrownBy(() -> StringUtils.abbreviate(input, marker, maxWidth))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Minimum abbreviation width is 3");
    }

    @Test
    void capitalize_ShouldReturnOriginal_WhenInputIsNull() {
        // Given
        String input = null;

        // When
        String actual = StringUtils.capitalize(input);

        // Then
        then(actual).isNull();
    }

    @Test
    void capitalize_ShouldReturnOriginal_WhenInputIsEmpty() {
        // Given
        String input = EMPTY;

        // When
        String actual = StringUtils.capitalize(input);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void capitalize_ShouldReturnOriginal_WhenInputIsBlank() {
        // Given
        String input = "   ";

        // When
        String actual = StringUtils.capitalize(input);

        // Then
        then(actual).isEqualTo("   ");
    }

    @Test
    void capitalize_ShouldCapitalizeFirstCharacter_WhenInputStartsWithLowerCase() {
        // Given
        String input = "hello";

        // When
        String actual = StringUtils.capitalize(input);

        // Then
        then(actual).isEqualTo("Hello");
    }

    @Test
    void capitalize_ShouldReturnOriginal_WhenInputAlreadyStartsWithUpperCase() {
        // Given
        String input = "Hello";

        // When
        String actual = StringUtils.capitalize(input);

        // Then
        then(actual).isEqualTo("Hello");
    }

    @Test
    void capitalize_ShouldHandleUnicode_WhenInputHasSpecialCharacters() {
        // Given
        String input = "éclair";

        // When
        String actual = StringUtils.capitalize(input);

        // Then
        then(actual).isEqualTo("Éclair");
    }

    @Test
    void chomp_ShouldReturnOriginal_WhenInputIsNull() {
        // Given
        String input = null;

        // When
        String actual = StringUtils.chomp(input);

        // Then
        then(actual).isNull();
    }

    @Test
    void chomp_ShouldReturnOriginal_WhenInputIsEmpty() {
        // Given
        String input = EMPTY;

        // When
        String actual = StringUtils.chomp(input);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void chomp_ShouldReturnOriginal_WhenInputIsBlank() {
        // Given
        String input = "   ";

        // When
        String actual = StringUtils.chomp(input);

        // Then
        then(actual).isEqualTo("   ");
    }

    @Test
    void chomp_ShouldReturnSameString_WhenNoLineEndingExists() {
        // Given
        String input = "Hello World";

        // When
        String actual = StringUtils.chomp(input);

        // Then
        then(actual).isEqualTo(input);
    }

    @Test
    void chomp_ShouldReturnSameString_WhenEndsWithLetterAfterPossibleLineEnding() {
        // Given
        String input = "Text\rabc";

        // When
        String actual = StringUtils.chomp(input);

        // Then
        then(actual).isEqualTo(input);
    }

    @Test
    void chomp_ShouldRemoveTrailingLf_WhenEndsWithSingleLf() {
        // Given
        String input = "Line one\n";

        // When
        String actual = StringUtils.chomp(input);

        // Then
        then(actual).isEqualTo("Line one");
    }

    @Test
    void chomp_ShouldRemoveTrailingLf_WhenEndsWithLfAndContent() {
        // Given
        String input = "A\nB\nC\n";

        // When
        String actual = StringUtils.chomp(input);

        // Then
        then(actual).isEqualTo("A\nB\nC");
    }

    @Test
    void chomp_ShouldRemoveTrailingCr_WhenEndsWithSingleCr() {
        // Given
        String input = "Line one\r";

        // When
        String actual = StringUtils.chomp(input);

        // Then
        then(actual).isEqualTo("Line one");
    }

    @Test
    void chomp_ShouldRemoveTrailingCrLf_WhenEndsWithCrLf() {
        // Given
        String input = "Hello\r\n";

        // When
        String actual = StringUtils.chomp(input);

        // Then
        then(actual).isEqualTo("Hello");
    }

    @Test
    void chomp_ShouldRemoveOnlyOneCrLf_WhenEndsWithMultipleCrLf() {
        // Given
        String input = "Line1\r\nLine2\r\n";

        // When
        String actual = StringUtils.chomp(input);

        // Then
        then(actual).isEqualTo("Line1\r\nLine2");
    }

    @Test
    void chomp_ShouldReturnEmpty_WhenInputIsSingleLf() {
        // Given
        String input = "\n";

        // When
        String actual = StringUtils.chomp(input);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void chomp_ShouldReturnEmpty_WhenInputIsSingleCr() {
        // Given
        String input = "\r";

        // When
        String actual = StringUtils.chomp(input);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void chomp_ShouldReturnSameChar_WhenSingleCharIsNotLineEnding() {
        // Given
        String input = "A";

        // When
        String actual = StringUtils.chomp(input);

        // Then
        then(actual).isEqualTo("A");
    }

    @Test
    void chomp_ShouldRemoveOnlyLastCrLf_WhenStringEndsWithCrCrLf() {
        // Given
        String input = "test\r\r\n";

        // When
        String actual = StringUtils.chomp(input);

        // Then
        then(actual).isEqualTo("test\r");
    }

    @Test
    void chomp_ShouldRemoveOnlyLastLf_WhenStringEndsWithCrLfLf() {
        // Given
        String input = "data\r\n\n";

        // When
        String actual = StringUtils.chomp(input);

        // Then
        then(actual).isEqualTo("data\r\n");
    }

    @Test
    void chomp_ShouldRemoveOnlyLastCr_WhenStringEndsWithLfCr() {
        // Given
        String input = "end\n\r";

        // When
        String actual = StringUtils.chomp(input);

        // Then
        then(actual).isEqualTo("end\n");
    }

    @Test
    void chomp_ShouldReturnWholeString_WhenEndsWithCrButPreviousIsNotCr() {
        // Given
        String input = "ABC\nDEF\r";

        // When
        String actual = StringUtils.chomp(input);

        // Then
        then(actual).isEqualTo("ABC\nDEF");
    }

    @Test
    void chop_ShouldReturnEmpty_WhenInputIsNull() {
        // Given
        String input = null;

        // When
        String actual = StringUtils.chop(input);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void chop_ShouldReturnEmpty_WhenInputIsEmpty() {
        // Given
        String input = EMPTY;

        // When
        String actual = StringUtils.chop(input);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "\t", "\n", "a"})
    void chop_ShouldReturnEmpty_WhenInputLengthIsLessThan2(String input) {
        // When
        String actual = StringUtils.chop(input);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @ParameterizedTest
    @ValueSource(strings = {"ab", "hello", "test123", "  two spaces  ", "line\nwithout cr"})
    void chop_ShouldRemoveLastCharacter_WhenNoCrLfAtEnd(String input) {
        // When
        String actual = StringUtils.chop(input);

        // Then
        then(actual).isEqualTo(input.substring(0, input.length() - 1));
    }

    @ParameterizedTest
    @ValueSource(strings = {"text\n", "hello\nworld\n", "\n", "A\n", "123\n\n"})
    void chop_ShouldRemoveOnlyLastLf_WhenEndsWithLfButNotPrecededByCr(String input) {
        // When
        String actual = StringUtils.chop(input);

        // Then
        then(actual).isEqualTo(input.substring(0, input.length() - 1));
    }

    @ParameterizedTest
    @ValueSource(strings = {"text\r", "data\r", "\r", "Z\r"})
    void chop_ShouldRemoveOnlyLastCr_WhenEndsWithCr(String input) {
        // When
        String actual = StringUtils.chop(input);

        // Then
        then(actual).isEqualTo(input.substring(0, input.length() - 1));
    }

    @Test
    void chop_ShouldRemoveCrLf_WhenStringIsExactlyCrLf() {
        // Given
        String input = "\r\n";

        // When
        String actual = StringUtils.chop(input);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void chop_ShouldRemoveCrLf_WhenStringEndsWithCrLf() {
        // Given
        String input = "content\n\r\n";

        // When
        String actual = StringUtils.chop(input);

        // Then
        then(actual).isEqualTo("content\n");
    }

    @Test
    void chop_ShouldRemoveOnlyLastChar_WhenEndsWithCrCr() {
        // Given
        String input = "test\r\r";

        // When
        String actual = StringUtils.chop(input);

        // Then
        then(actual).isEqualTo("test\r");
    }

    @Test
    void chop_ShouldRemoveOnlyLastLf_WhenEndsWithMultipleLf() {
        // Given
        String input = "multi\n\n\n";

        // When
        String actual = StringUtils.chop(input);

        // Then
        then(actual).isEqualTo("multi\n\n");
    }

    @Test
    void contains_ShouldReturnFalse_WhenInputIsNull() {
        // Given
        String input = null;
        String search = "Hello";

        // When
        boolean actual = StringUtils.contains(input, search);

        // Then
        then(actual).isFalse();
    }

    @Test
    void contains_ShouldReturnFalse_WhenInputIsEmpty() {
        // Given
        String input = EMPTY;
        String search = "Hello";

        // When
        boolean actual = StringUtils.contains(input, search);

        // Then
        then(actual).isFalse();
    }

    @Test
    void contains_ShouldReturnFalse_WhenSearchIsNull() {
        // Given
        String input = "Hello";
        String search = null;

        // When
        boolean actual = StringUtils.contains(input, search);

        // Then
        then(actual).isFalse();
    }

    @Test
    void contains_ShouldReturnFalse_WhenSearchIsEmpty() {
        // Given
        String input = "Hello";
        String search = EMPTY;

        // When
        boolean actual = StringUtils.contains(input, search);

        // Then
        then(actual).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "  \t  ", "\n", "\r\n"})
    void contains_ShouldReturnFalse_WhenStringHasNoText(String input) {
        // Given
        String search = "Hello";

        // When
        boolean actual = StringUtils.contains(input, search);

        // Then
        then(actual).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "  \t  ", "\n", "\r\n"})
    void contains_ShouldReturnFalse_WhenSearchHasNoLength(String search) {
        // Given
        String input = "Hello";

        // When
        boolean actual = StringUtils.contains(input, search);

        // Then
        then(actual).isFalse();
    }

    @ParameterizedTest
    @CsvSource({
            "Hello World, Hello",
            "Hello World, World",
            "Hello World, lo Wo",
            "banana, ana",
            "aaa, aa",
            "12345, 234",
            "case-sensitive test, case",
            "  spaces around  , around"
    })
    void contains_ShouldReturnTrue_WhenSearchIsFoundInString(String input, String search) {
        // When
        boolean actual = StringUtils.contains(input, search);

        // Then
        then(actual).isTrue();
    }

    @ParameterizedTest
    @CsvSource({
            "Hello World, hello",
            "Hello World, universe",
            "banana, apple",
            "short, longerstring",
            "abc, abcd",
            "xyz, xYz",
    })
    void contains_ShouldReturnFalse_WhenSearchIsNotFound(String input, String search) {
        // When
        boolean actual = StringUtils.contains(input, search);

        // Then
        then(actual).isFalse();
    }

    @Test
    void contains_ShouldReturnTrue_WhenSearchEqualsWholeString() {
        // Given
        String input = "exact";
        String search = "exact";

        // When
        boolean actual = StringUtils.contains(input, search);

        // Then
        then(actual).isTrue();
    }

    @Test
    void contains_ShouldReturnTrue_WhenSearchIsSingleCharacterAndExists() {
        // Given
        String input = "kotlin";
        String search = "t";

        // When
        boolean actual = StringUtils.contains(input, search);

        // Then
        then(actual).isTrue();
    }

    @Test
    void contains_ShouldReturnFalse_WhenSearchIsSingleCharacterAndNotExists() {
        // Given
        String input = "java";
        String search = "z";

        // When
        boolean actual = StringUtils.contains(input, search);

        // Then
        then(actual).isFalse();
    }

    @Test
    void containsWhitespace_ShouldReturnFalse_WhenInputIsNull() {
        // Given
        String input = null;

        // When
        boolean actual = StringUtils.containsWhitespace(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void containsWhitespace_ShouldReturnFalse_WhenInputIsEmpty() {
        // Given
        String input = EMPTY;

        // When
        boolean actual = StringUtils.containsWhitespace(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void containsWhitespace_ShouldReturnTrue_WhenInputIsBlank() {
        // Given
        String input = "   ";

        // When
        boolean actual = StringUtils.containsWhitespace(input);

        // Then
        then(actual).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"hello", "HelloWorld", "12345", "こんにちは", "café", "!@#$%^&*()_+"})
    void containsWhitespace_ShouldReturnFalse_WhenNoWhitespaceExists(String input) {
        // When
        boolean actual = StringUtils.containsWhitespace(input);

        // Then
        then(actual).isFalse();
    }

    @ParameterizedTest
    @CsvSource({
            "'Hello World', true",
            "' leading space', true",
            "'trailing space ', true",
            "'multiple   spaces', true",
            "'tab\tinside', true",
            "'line\nbreak', true",
            "'\r carriage return', true",
            "'\f form feed', true",
            "'\u2003 em space', true",
            "'mixed\t\n content ', true"
    })
    void containsWhitespace_ShouldReturnTrue_WhenAtLeastOneWhitespaceExists(String input, boolean expected) {
        // Given
        boolean actual = StringUtils.containsWhitespace(input);

        // Then
        then(actual).isEqualTo(expected);
    }

    @Test
    void containsWhitespace_ShouldReturnTrue_WhenStringIsSingleTab() {
        // Given
        String input = "\t";

        // When
        boolean actual = StringUtils.containsWhitespace(input);

        // Then
        then(actual).isTrue();
    }

    @Test
    void containsWhitespace_ShouldReturnTrue_WhenStringIsSingleNewline() {
        // Given
        String input = "\n";

        // When
        boolean actual = StringUtils.containsWhitespace(input);

        // Then
        then(actual).isTrue();
    }

    @Test
    void containsWhitespace_ShouldReturnTrue_WhenWhitespaceIsInTheMiddle() {
        // Given
        String input = "hello\tworld";

        // When
        boolean actual = StringUtils.containsWhitespace(input);

        // Then
        then(actual).isTrue();
    }

    @Test
    void countMatches_ShouldReturnZero_WhenInputStringIsNull() {
        // Given
        String input = null;
        String substring = "Hello";

        // When
        int actual = StringUtils.countMatches(input, substring);

        // Then
        then(actual).isZero();
    }

    @Test
    void countMatches_ShouldReturnZero_WhenInputStringIsEmpty() {
        // Given
        String input = EMPTY;
        String substring = "Hello";

        // When
        int actual = StringUtils.countMatches(input, substring);

        // Then
        then(actual).isZero();
    }

    @Test
    void countMatches_ShouldReturnZero_WhenSubStringIsNull() {
        // Given
        String input = "Hello";
        String substring = null;

        // When
        int actual = StringUtils.countMatches(input, substring);

        // Then
        then(actual).isZero();
    }

    @Test
    void countMatches_ShouldReturnZero_WhenSubStringIsEmpty() {
        // Given
        String input = "Hello";
        String substring = EMPTY;

        // When
        int actual = StringUtils.countMatches(input, substring);

        // Then
        then(actual).isZero();
    }

    @Test
    void countMatches_ShouldReturnZero_WhenInputStringIsBlankAndSubStringHasContent() {
        // Given
        String input = SPACE;
        String substring = "Hello";

        // When
        int actual = StringUtils.countMatches(input, substring);

        // Then
        then(actual).isZero();
    }

    @Test
    void countMatches_ShouldReturnZero_WhenSubstringIsBlankAndInputStringHasContent() {
        // Given
        String input = "Hello";
        String substring = SPACE;

        // When
        int actual = StringUtils.countMatches(input, substring);

        // Then
        then(actual).isZero();
    }

    @Test
    void countMatches_ShouldReturnZero_WhenSubstringDoesNotAppear() {
        // Given
        String input = "Hello";
        String substring = "he";

        // When
        int actual = StringUtils.countMatches(input, substring);

        // Then
        then(actual).isZero();
    }

    @Test
    void countMatches_ShouldReturnZero_WhenSubstringIsLongerThanInputString() {
        // Given
        String input = "he";
        String substring = "Hello";

        // When
        int actual = StringUtils.countMatches(input, substring);

        // Then
        then(actual).isZero();
    }

    @Test
    void countMatches_ShouldReturnCount_WhenInputStringAndSubstringAreIdentical() {
        // Given
        String input = "Hello";
        String substring = "Hello";

        // When
        int actual = StringUtils.countMatches(input, substring);

        // Then
        then(actual).isOne();
    }

    @Test
    void countMatches_ShouldReturnCount_WhenSubstringAppearsOnce() {
        // Given
        String input = "banana";
        String substring = "ana";

        // When
        int actual = StringUtils.countMatches(input, substring);

        // Then
        then(actual).isOne();
    }

    @Test
    void countMatches_ShouldReturnCount_WhenSubstringIsSingleChar() {
        // Given
        String input = "Hello";
        String substring = "l";

        // When
        int actual = StringUtils.countMatches(input, substring);

        // Then
        then(actual).isEqualTo(2);
    }

    @Test
    void countMatches_ShouldReturnCount_WhenSubstringIsUnicodeAndAppears() {
        // Given
        String input = "θetaθworld";
        String substring = "θ";

        // When
        int actual = StringUtils.countMatches(input, substring);

        // Then
        then(actual).isEqualTo(2);
    }

    @Test
    void collectionToDelimitedString_ShouldReturnEmpty_WhenCollectionIsNull() {
        // Given
        Collection<String> input = null;

        // When
        String actual = StringUtils.collectionToDelimitedString(input);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void collectionToDelimitedString_ShouldReturnEmpty_WhenCollectionIsEmpty() {
        // Given
        Collection<String> input = Collections.emptyList();

        // When
        String actual = StringUtils.collectionToDelimitedString(input);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void collectionToDelimitedString_ShouldReturnSingleElement_WhenCollectionHasOneItem() {
        // Given
        Collection<String> input = List.of("apple");

        // When
        String actual = StringUtils.collectionToDelimitedString(input);

        // Then
        then(actual).isEqualTo("apple");
    }

    @Test
    void collectionToDelimitedString_ShouldReturnCommaSeparated_WhenCollectionHasMultipleItems() {
        // Given
        Collection<String> input = List.of("a", "b", "c");

        // When
        String actual = StringUtils.collectionToDelimitedString(input);

        // Then
        then(actual).isEqualTo("a,b,c");
    }

    @Test
    void collectionToDelimitedString_ShouldReturnEmpty_WhenCollectionIsNullAndDelimiterProvided() {
        // Given
        Collection<String> input = null;

        // When
        String actual = StringUtils.collectionToDelimitedString(input, SEMICOLON);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void collectionToDelimitedString_ShouldReturnEmpty_WhenCollectionIsEmptyAndDelimiterProvided() {
        // Given
        Collection<String> input = Collections.emptyList();

        // When
        String actual = StringUtils.collectionToDelimitedString(input, SEMICOLON);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void collectionToDelimitedString_ShouldTreatNullPrefixAsEmpty_WhenPrefixIsNull() {
        // Given
        Collection<String> input = List.of("a", "b", "c");
        String delimiter = "|";
        String prefix = null;
        String suffix = "→";

        // When
        String actual = StringUtils.collectionToDelimitedString(input, delimiter, prefix, suffix);

        // Then
        then(actual)
                .isEqualTo("a→|b→|c→")
                .doesNotContain("[")
                .doesNotContain("]");
    }

    @Test
    void collectionToDelimitedString_ShouldTreatNullSuffixAsEmpty_WhenSuffixIsNull() {
        // Given
        Collection<String> input = List.of("x", "y");
        String delimiter = " - ";
        String prefix = "«";
        String suffix = null;

        // When
        String actual = StringUtils.collectionToDelimitedString(input, delimiter, prefix, suffix);

        // Then
        then(actual)
                .isEqualTo("«x - «y")
                .doesNotContain("»")
                .doesNotContain("]");
    }

    @Test
    void collectionToDelimitedString_ShouldUseProvidedDelimiter_WhenCollectionHasMultipleItemsAndDelimiterProvided() {
        // Given
        Collection<String> input = List.of("one", "two", "three");

        // When
        String actual = StringUtils.collectionToDelimitedString(input, SEMICOLON);

        // Then
        then(actual).isEqualTo("one;two;three");
    }

    @Test
    void collectionToDelimitedString_ShouldUseComma_WhenDelimiterIsNullAndDelimiterProvided() {
        // Given
        Collection<String> input = List.of("x", "y");

        // When
        String actual = StringUtils.collectionToDelimitedString(input, null);

        // Then
        then(actual).isEqualTo("x,y");
    }

    @Test
    void collectionToDelimitedString_ShouldHandleNullElements_WhenCollectionContainsNull() {
        // Given
        Collection<String> input = Arrays.asList("start", null, "end");

        // When
        String actual = StringUtils.collectionToDelimitedString(input);

        // Then
        then(actual).isEqualTo("start,null,end");
    }

    @Test
    void collectionToDelimitedString_ShouldHandleLargeCollectionEfficiently() {
        // Given
        Collection<String> input = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            input.add("element" + i);
        }

        // When
        String actual = StringUtils.collectionToDelimitedString(input, ";", "<", ">");

        // Then
        then(actual).startsWith("<element1>");
        then(actual).contains(";")
                .contains("element25")
                .endsWith("<element50>");
        then(actual.split(";")).hasSize(50);
    }

    @Test
    void collectionToDelimitedString_ShouldHandleEmptyStringsCorrectly() {
        // Given
        Collection<String> input = List.of(EMPTY, SPACE, "value");

        // When
        String actual = StringUtils.collectionToDelimitedString(input, "|");

        // Then
        then(actual).isEqualTo("| |value");
    }

    @ParameterizedTest
    @MethodSource("provideCasesForDefaultReturn")
    void defaultIfNotHasLength_ShouldReturnDefaultString_WhenInputHasNoLength(String input,
                                                                              String defaultValue,
                                                                              String expected) {
        // When
        String actual = StringUtils.defaultIfNotHasLength(input, defaultValue);

        // Then
        then(actual).isEqualTo(expected);
    }

    static Stream<Arguments> provideCasesForDefaultReturn() {
        return Stream.of(
                Arguments.of(null, "fallback", "fallback"),
                Arguments.of(EMPTY, "fallback", "fallback"),
                Arguments.of("   ", "fallback", "   "),
                Arguments.of(null, EMPTY, EMPTY),
                Arguments.of(EMPTY, null, null),
                Arguments.of(null, "default", "default")
        );
    }

    @ParameterizedTest
    @MethodSource("provideCasesForOriginalReturn")
    void defaultIfNotHasLength_ShouldReturnOriginalString_WhenInputHasLength(String input, String defaultValue) {
        // When
        String actual = StringUtils.defaultIfNotHasLength(input, defaultValue);

        // Then
        then(actual).isSameAs(input);
        then(actual).isEqualTo(input);
    }

    static Stream<Arguments> provideCasesForOriginalReturn() {
        return Stream.of(
                Arguments.of("hello", "fallback"),
                Arguments.of("a", "default"),
                Arguments.of("   ", "fallback"),
                Arguments.of("123", null),
                Arguments.of("text with space", "ignored"),
                Arguments.of("\tvisible\t", "ignored")
        );
    }

    @Test
    void defaultIfNotHasLength_ShouldReturnNull_WhenInputIsNullAndDefaultIsNull() {
        // Given
        String input = null;
        String defaultValue = null;

        // When
        String actual = StringUtils.defaultIfNotHasLength(input, defaultValue);

        // Then
        then(actual).isNull();
    }

    @Test
    void defaultIfNotHasLength_ShouldReturnEmptyString_WhenInputIsEmptyAndDefaultIsEmpty() {
        // Given
        String input = EMPTY;
        String defaultValue = EMPTY;

        // When
        String actual = StringUtils.defaultIfNotHasLength(input, defaultValue);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void defaultIfNotHasLength_ShouldReturnDefault_WhenInputIsNullAndDefaultIsNonEmpty() {
        // Given
        String input = null;
        String defaultValue = "replacement";

        // When
        String actual = StringUtils.defaultIfNotHasLength(input, defaultValue);

        // Then
        then(actual).isEqualTo("replacement");
    }

    @Test
    void defaultIfNotHasLength_ShouldReturnInput_WhenInputIsNonEmptyAndDefaultIsNull() {
        // Given
        String input = "valuable";
        String defaultValue = null;

        // When
        String actual = StringUtils.defaultIfNotHasLength(input, defaultValue);

        // Then
        then(actual).isEqualTo("valuable");
    }

    @Test
    void defaultIfNotHasLength_ShouldReturnWhitespaceString_WhenInputIsOnlyWhitespace() {
        // Given
        String input = "   \t  ";
        String defaultValue = "fallback";

        // When
        String actual = StringUtils.defaultIfNotHasLength(input, defaultValue);

        // Then
        then(actual).isEqualTo("   \t  ");
        then(actual).isNotEqualTo("fallback");
    }

    @ParameterizedTest(name = "[{index}] input={0} → default={1} → expected={2}")
    @MethodSource("provideCasesWhereDefaultShouldBeReturned")
    void defaultIfNotHasText_ShouldReturnDefaultString_WhenInputHasNoText(String input,
                                                                          String defaultValue,
                                                                          String expected) {
        // When
        String actual = StringUtils.defaultIfNotHasText(input, defaultValue);

        // Then
        then(actual).isEqualTo(expected);
    }

    static Stream<Arguments> provideCasesWhereDefaultShouldBeReturned() {
        return Stream.of(
                Arguments.of(null, "fallback", "fallback"),
                Arguments.of(EMPTY, "fallback", "fallback"),
                Arguments.of("   ", "fallback", "fallback"),
                Arguments.of(" \t\n ", "fallback", "fallback"),
                Arguments.of("  \u2003  ", "fallback", "fallback"), // em space
                Arguments.of(null, EMPTY, EMPTY),
                Arguments.of(EMPTY, null, null),
                Arguments.of("   ", null, null)
        );
    }

    @ParameterizedTest(name = "[{index}] input={0} → default ignored")
    @MethodSource("provideCasesWhereOriginalShouldBeReturned")
    void defaultIfNotHasText_ShouldReturnOriginalString_WhenInputHasText(String input, String defaultValue) {
        // When
        String actual = StringUtils.defaultIfNotHasText(input, defaultValue);

        // Then
        then(actual).isSameAs(input);
        then(actual).isEqualTo(input);
    }

    static Stream<Arguments> provideCasesWhereOriginalShouldBeReturned() {
        return Stream.of(
                Arguments.of("hello", "fallback"),
                Arguments.of(" a ", "fallback"),
                Arguments.of("123", null),
                Arguments.of("word\twith\tspaces", "ignored"),
                Arguments.of("こんにちは", "ignored"),
                Arguments.of("text with trailing space ", "ignored"),
                Arguments.of("leading space text", "ignored")
        );
    }

    @Test
    void defaultIfNotHasText_ShouldReturnNull_WhenBothInputAndDefaultAreNull() {
        // Given
        String input = null;
        String defaultValue = null;

        // When
        String actual = StringUtils.defaultIfNotHasText(input, defaultValue);

        // Then
        then(actual).isNull();
    }

    @Test
    void defaultIfNotHasText_ShouldReturnInput_WhenInputIsOnlyWhitespaceAndDefaultIsNonEmpty() {
        // Given
        String input = " \t\n\u00A0 ";
        String defaultValue = "DEFAULT_VALUE";

        // When
        String actual = StringUtils.defaultIfNotHasText(input, defaultValue);

        // Then
        then(actual).isEqualTo(" \t\n\u00A0 ");
    }

    @Test
    void defaultIfNotHasText_ShouldReturnInput_WhenInputHasVisibleCharacterEvenWithWhitespace() {
        // Given
        String input = " \t visible \n";
        String defaultValue = "fallback";

        // When
        String actual = StringUtils.defaultIfNotHasText(input, defaultValue);

        // Then
        then(actual).isEqualTo(" \t visible \n");
    }

    @Test
    void defaultIfNotHasText_ShouldReturnEmptyString_WhenInputIsEmptyAndDefaultIsEmpty() {
        // Given
        String input = EMPTY;
        String defaultValue = EMPTY;

        // When
        String actual = StringUtils.defaultIfNotHasText(input, defaultValue);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void defaultIfNotHasText_ShouldReturnInput_WhenInputIsSingleNonWhitespaceCharacter() {
        // Given
        String input = ".";
        String defaultValue = "fallback";

        // When
        String actual = StringUtils.defaultIfNotHasText(input, defaultValue);

        // Then
        then(actual).isEqualTo(".");
    }

    @Test
    void deleteAny_ShouldReturnInputUnchanged_WhenStringIsNull() {
        // Given
        String input = null;
        String charsToDelete = "hello";

        // When
        String actual = StringUtils.deleteAny(input, charsToDelete);

        // Then
        then(actual).isEqualTo(input);
    }

    @Test
    void deleteAny_ShouldReturnInputUnchanged_WhenStringIsEmpty() {
        // Given
        String input = EMPTY;
        String charsToDelete = "hello";

        // When
        String actual = StringUtils.deleteAny(input, charsToDelete);

        // Then
        then(actual).isEqualTo(input);
    }

    @Test
    void deleteAny_ShouldReturnInputUnchanged_WhenStringIsBlank() {
        // Given
        String input = "    ";
        String charsToDelete = "hello";

        // When
        String actual = StringUtils.deleteAny(input, charsToDelete);

        // Then
        then(actual).isEqualTo(input);
    }

    @Test
    void deleteAny_ShouldReturnInputUnchanged_WhenCharsToDeleteIsNull() {
        // Given
        String input = "hello world";
        String charsToDelete = null;

        // When
        String actual = StringUtils.deleteAny(input, charsToDelete);

        // Then
        then(actual).isEqualTo(input);
    }

    @Test
    void deleteAny_ShouldReturnInputUnchanged_WhenCharsToDeleteIsEmpty() {
        // Given
        String input = "hello world";
        String charsToDelete = EMPTY;

        // When
        String actual = StringUtils.deleteAny(input, charsToDelete);

        // Then
        then(actual).isEqualTo(input);
    }

    @Test
    void deleteAny_ShouldReturnInputUnchanged_WhenCharsToDeleteIsBlank() {
        // Given
        String input = "hello-world";
        String charsToDelete = "    ";

        // When
        String actual = StringUtils.deleteAny(input, charsToDelete);

        // Then
        then(actual).isEqualTo(input);
    }

    @Test
    void deleteAny_ShouldReturnSameString_WhenNoCharactersMatchToDelete() {
        // Given
        String input = "hello world";
        String charsToDelete = "1234567890";

        // When
        String actual = StringUtils.deleteAny(input, charsToDelete);

        // Then
        then(actual).isEqualTo(input);
        then(actual).isSameAs(input);
    }

    @Test
    void deleteAny_ShouldReturnSameString_WhenCharsToDeleteIsIrrelevant() {
        // Given
        String input = "Java123";
        String charsToDelete = "!@#$%^&*()";

        // When
        String actual = StringUtils.deleteAny(input, charsToDelete);

        // Then
        then(actual).isEqualTo(input);
    }

    @ParameterizedTest(name = "[{index}] {0} → حذف {1} → {2}")
    @MethodSource("provideDeletionCases")
    void deleteAny_ShouldRemoveAllSpecifiedCharacters_WhenTheyExist(String input, String charsToDelete, String expected) {
        // When
        String actual = StringUtils.deleteAny(input, charsToDelete);

        // Then
        then(actual).isEqualTo(expected);
    }

    static Stream<Arguments> provideDeletionCases() {
        return Stream.of(
                Arguments.of("hello world", "aeiou ", "hllwrld"),
                Arguments.of("banana", "an", "b"),
                Arguments.of("12345-67890", "0-9", "12345678"),
                Arguments.of("Java,C#;Python", ",;#", "JavaCPython"),
                Arguments.of("!!!important!!!", "!", "important"),
                Arguments.of("test123test", "123", "testtest"),
                Arguments.of("  spaces  ", SPACE, "spaces"),
                Arguments.of("mixed CASE", "a-z", "mixed CASE"),
                Arguments.of("café", "é", "caf")
        );
    }

    @Test
    void deleteAny_ShouldReturnEmptyString_WhenAllCharactersAreToBeDeleted() {
        // Given
        String input = "aaaaa";
        String charsToDelete = "a";

        // When
        String actual = StringUtils.deleteAny(input, charsToDelete);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void deleteAny_ShouldHandleDuplicateCharsInDeleteList_Correctly() {
        // Given
        String input = "bookkeeper";
        String charsToDelete = "oookkee";

        // When
        String actual = StringUtils.deleteAny(input, charsToDelete);

        // Then
        then(actual).isEqualTo("bpr");
    }

    @Test
    void deleteAny_ShouldPreserveOrder_WhenCharactersAreRemoved() {
        // Given
        String input = "a1b2c3d4e5";
        String charsToDelete = "135";

        // When
        String actual = StringUtils.deleteAny(input, charsToDelete);

        // Then
        then(actual).isEqualTo("ab2cd4e");
    }

    @Test
    void deleteAny_ShouldReturnNewString_WhenSomeCharactersWereDeleted() {
        // Given
        String input = "hello";
        String charsToDelete = "l";

        // When
        String actual = StringUtils.deleteAny(input, charsToDelete);

        // Then
        then(actual).isEqualTo("heo");
        then(actual).isNotSameAs(input);
    }

    @Test
    void deleteWhitespace_ShouldReturnInputUnchanged_WhenStringIsNull() {
        // Given
        String input = null;

        // When
        String actual = StringUtils.deleteWhitespace(input);

        // Then
        then(actual).isEqualTo(input);
    }

    @Test
    void deleteWhitespace_ShouldReturnInputUnchanged_WhenStringIsEmpty() {
        // Given
        String input = EMPTY;

        // When
        String actual = StringUtils.deleteWhitespace(input);

        // Then
        then(actual).isEqualTo(input);
    }

    @Test
    void deleteWhitespace_ShouldReturnEmpty_WhenStringIsBlank() {
        // Given
        String input = "    ";

        // When
        String actual = StringUtils.deleteWhitespace(input);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void deleteWhitespace_ShouldReturnEmpty_WhenInputContainsOnlyWhitespace() {
        // Given
        String input = "\t\n\r\f\u2003";

        // When
        String actual = StringUtils.deleteWhitespace(input);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void deleteWhitespace_ShouldHandleUnicodeWhitespaceCorrectly() {
        // Given
        String input = "Hello\u2002\u2003\u2009\u3000World";

        // When
        String actual = StringUtils.deleteWhitespace(input);

        // Then
        then(actual).isEqualTo("HelloWorld");
    }

    @ParameterizedTest
    @ValueSource(strings = {"hello", "HelloWorld", "12345", "café", "こんにちは", "!@#$%^&*()_+", "a1b2c3"})
    void deleteWhitespace_ShouldReturnSameString_WhenNoWhitespaceExists(String input) {
        // When
        String actual = StringUtils.deleteWhitespace(input);

        // Then
        then(actual).isEqualTo(input);
        then(actual).isSameAs(input);
    }

    @ParameterizedTest(name = "[{index}] \"{0}\" → \"{1}\"")
    @MethodSource("whitespaceRemovalTestCases")
    void deleteWhitespace_ShouldRemoveAllWhitespaceCharacters_WhenTheyExist(String input, String expected) {
        // When
        String actual = StringUtils.deleteWhitespace(input);

        // Then
        then(actual).isEqualTo(expected);
    }

    static Stream<Arguments> whitespaceRemovalTestCases() {
        return Stream.of(
                Arguments.of("hello world", "helloworld"),
                Arguments.of("  leading spaces", "leadingspaces"),
                Arguments.of("trailing spaces  ", "trailingspaces"),
                Arguments.of("multiple   spaces here", "multiplespaceshere"),
                Arguments.of("tab\tand\nnewlines", "tabandnewlines"),
                Arguments.of(" mixed \t content \n with \r various ", "mixedcontentwithvarious"),
                Arguments.of("no whitespace here!", "nowhitespacehere!"),
                Arguments.of("  spaces  around  words  ", "spacesaroundwords"),
                Arguments.of("line1\nline2\r\nline3", "line1line2line3"),
                Arguments.of("\u2003\u00A0\u1680 ideographic spaces", "ideographicspaces")
        );
    }

    @Test
    void difference_ShouldReturnSecondString_WhenFirstIsNull() {
        // Given
        String input1 = null;
        String input2 = "remaining";

        // When
        String actual = StringUtils.difference(input1, input2);

        // Then
        then(actual).isEqualTo(input2);
    }

    @Test
    void difference_ShouldReturnSecondString_WhenFirstIsEmpty() {
        // Given
        String input1 = EMPTY;
        String input2 = "remaining";

        // When
        String actual = StringUtils.difference(input1, input2);

        // Then
        then(actual).isEqualTo(input2);
    }

    @Test
    void difference_ShouldReturnFirstString_WhenSecondIsNull() {
        // Given
        String input1 = "remaining";
        String input2 = null;

        // When
        String actual = StringUtils.difference(input1, input2);

        then(actual).isEqualTo(input1);
    }

    @Test
    void difference_ShouldReturnFirstString_WhenSecondIsEmpty() {
        // Given
        String input1 = "remaining";
        String input2 = EMPTY;

        // When
        String actual = StringUtils.difference(input1, input2);

        then(actual).isEqualTo(input1);
    }

    @Test
    void difference_ShouldReturnFirstString_WhenBothStringsIsNull() {
        // Given
        String input1 = null;
        String input2 = null;

        // When
        String actual = StringUtils.difference(input1, input2);

        then(actual).isNull();
    }

    @Test
    void difference_ShouldReturnFirstString_WhenBothStringsIsEmpty() {
        // Given
        String input1 = EMPTY;
        String input2 = EMPTY;

        // When
        String actual = StringUtils.difference(input1, input2);

        then(actual).isEmpty();
    }

    @Test
    void difference_ShouldReturnEmpty_WhenBothStringsAreIdentical() {
        // Given
        String input1 = "identical text";
        String input2 = "identical text";

        // When
        String actual = StringUtils.difference(input1, input2);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void difference_ShouldReturnWholeSecondString_WhenDifferenceIsAtPositionZero() {
        // Given
        String input1 = "abc";
        String input2 = "xyzabc";

        // When
        String actual = StringUtils.difference(input1, input2);

        // Then
        then(actual).isEqualTo("xyzabc");
    }

    @Test
    void difference_ShouldHandleSingleCharacterDifferenceCorrectly() {
        // Given
        String input1 = "a";
        String input2 = "ab";

        // When
        String actual = StringUtils.difference(input1, input2);

        // Then
        then(actual).isEqualTo("b");
    }

    @Test
    void difference_ShouldReturnEmpty_WhenSecondStringIsPrefixOfFirst() {
        // Given
        String input1 = "longtext";
        String input2 = "long";

        // When
        String actual = StringUtils.difference(input1, input2);

        // Then
        then(actual).isEmpty();
    }

    @ParameterizedTest(name = "[{index}] s1={0}  s2={1}  →  {2}")
    @MethodSource("differenceTestCases")
    void difference_ShouldReturnSuffixOfSecondStringFromDifferencePoint_WhenStringsDiffer(String string1,
                                                                                          String string2,
                                                                                          String expected) {
        // When
        String actual = StringUtils.difference(string1, string2);

        // Then
        then(actual).isEqualTo(expected);
    }

    static Stream<Arguments> differenceTestCases() {
        return Stream.of(
                Arguments.of("abc", "abcdef", "def"),
                Arguments.of("hell", "hello", "o"),
                Arguments.of("same", "same", EMPTY),
                Arguments.of("prefix", "pre", EMPTY),
                Arguments.of("pre", "prefix", "fix"),
                Arguments.of("a", "b", "b"),
                Arguments.of("abcde", "abXde", "Xde"),
                Arguments.of("12345", "123", EMPTY),
                Arguments.of("123", "12345", "45"),
                Arguments.of(null, null, null)
        );
    }

    @Test
    void delimitedListToStringArray_ShouldReturnEmptyArray_WhenStringIsNull() {
        // Given
        String input = null;
        String delimiter = COMMA;

        // When
        String[] actual = StringUtils.delimitedListToStringArray(input, delimiter);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void delimitedListToStringArray_ShouldReturnEmptyArray_WhenStringIsEmpty() {
        // Given
        String input = EMPTY;
        String delimiter = COMMA;

        // When
        String[] actual = StringUtils.delimitedListToStringArray(input, delimiter);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void delimitedListToStringArray_ShouldReturnSingleElementArray_WhenDelimiterIsNull() {
        // Given
        String input = "a,b,c";
        String delimiter = null;

        // When
        String[] actual = StringUtils.delimitedListToStringArray(input, delimiter);

        // Then
        then(actual).containsExactly("a,b,c");
    }

    @Test
    void delimitedListToStringArray_ShouldReturnEmptyArray_WhenDelimiterExists() {
        // Given
        String input = "a,b,c";
        String delimiter = COMMA;

        // When
        String[] actual = StringUtils.delimitedListToStringArray(input, delimiter);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void delimitedListToStringArray_ShouldReturnEmptyArray_WhenDelimiterDoesNotExist() {
        // Given
        String input = "abc";
        String delimiter = COMMA;

        // When
        String[] actual = StringUtils.delimitedListToStringArray(input, delimiter);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void delimitedListToStringArray_ShouldReturnEmptyArray_WhenStringIsNullAndCharsToDeleteProvided() {
        // Given
        String input = null;
        String delimiter = COMMA;
        String charsToDelete = SPACE;

        // When
        String[] actual = StringUtils.delimitedListToStringArray(input, delimiter, charsToDelete);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void delimitedListToStringArray_ShouldReturnSingleElementArray_WhenDelimiterIsNullAndCharsToDeleteProvided() {
        // Given
        String input = " a , b ";
        String delimiter = null;
        String charsToDelete = SPACE;

        // When
        String[] actual = StringUtils.delimitedListToStringArray(input, delimiter, charsToDelete);

        // Then
        then(actual).containsExactly(" a , b ");
    }

    @Test
    void delimitedListToStringArray_ShouldReturnEmptyArray_WhenDelimiterIsEmpty() {
        // Given
        String input = "abc";
        String delimiter = EMPTY;
        String charsToDelete = null;

        // When
        String[] actual = StringUtils.delimitedListToStringArray(input, delimiter, charsToDelete);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void delimitedListToStringArray_ShouldReturnEmptyArray_WhenDelimiterIsEmptyAndCharsToDeleteProvided() {
        // Given
        String input = "a b c";
        String delimiter = EMPTY;
        String charsToDelete = SPACE;

        // When
        String[] actual = StringUtils.delimitedListToStringArray(input, delimiter, charsToDelete);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void delimitedListToStringArray_ShouldReturnEmptyArray_WhenMultipleDelimitersExist() {
        // Given
        String input = "a|b|c";
        String delimiter = "|";
        String charsToDelete = null;

        // When
        String[] actual = StringUtils.delimitedListToStringArray(input, delimiter, charsToDelete);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void delimitedListToStringArray_ShouldReturnEmptyArray_WhenStringEndsWithDelimiter() {
        // Given
        String input = "a,b,";
        String delimiter = COMMA;
        String charsToDelete = null;

        // When
        String[] actual = StringUtils.delimitedListToStringArray(input, delimiter, charsToDelete);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void delimitedListToStringArray_ShouldReturnEmptyArray_WhenStringStartsWithDelimiter() {
        // Given
        String input = ",a,b";
        String delimiter = COMMA;
        String charsToDelete = null;

        // When
        String[] actual = StringUtils.delimitedListToStringArray(input, delimiter, charsToDelete);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void delimitedListToStringArray_ShouldExecuteLastSubstringBranch_WhenStringDoesNotEndWithDelimiter() {
        // Given
        String input = "a,b,c";
        String delimiter = COMMA;
        String charsToDelete = null;

        // When
        String[] actual = StringUtils.delimitedListToStringArray(input, delimiter, charsToDelete);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void delimitedListToStringArray_ShouldNotExecuteLastSubstringBranch_WhenStringIsEmpty() {
        // Given
        String input = EMPTY;
        String delimiter = COMMA;
        String charsToDelete = null;

        // When
        String[] actual = StringUtils.delimitedListToStringArray(input, delimiter, charsToDelete);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void delimitedListToStringArray_ShouldExecuteLastSubstringBranch_WhenCharsToDeleteIsProvided() {
        // Given
        String input = " a , b , c ";
        String delimiter = COMMA;
        String charsToDelete = SPACE;

        // When
        String[] actual = StringUtils.delimitedListToStringArray(input, delimiter, charsToDelete);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void endsWithIgnoreCase_ShouldReturnFalse_WhenStringIsNull() {
        // Given
        String input = null;
        String suffix = "abc";

        // When
        boolean actual = StringUtils.endsWithIgnoreCase(input, suffix);

        // Then
        then(actual).isFalse();
    }

    @Test
    void endsWithIgnoreCase_ShouldReturnFalse_WhenStringIsEmpty() {
        // Given
        String input = EMPTY;
        String suffix = "abc";

        // When
        boolean actual = StringUtils.endsWithIgnoreCase(input, suffix);

        // Then
        then(actual).isFalse();
    }

    @Test
    void endsWithIgnoreCase_ShouldReturnFalse_WhenSuffixIsNull() {
        // Given
        String input = "abc";
        String suffix = null;

        // When
        boolean actual = StringUtils.endsWithIgnoreCase(input, suffix);

        // Then
        then(actual).isFalse();
    }

    @Test
    void endsWithIgnoreCase_ShouldReturnFalse_WhenSuffixIsEmpty() {
        // Given
        String input = "abc";
        String suffix = EMPTY;

        // When
        boolean actual = StringUtils.endsWithIgnoreCase(input, suffix);

        // Then
        then(actual).isFalse();
    }

    @Test
    void endsWithIgnoreCase_ShouldReturnFalse_WhenBothStringAndSuffixAreNull() {
        // Given
        String input = null;
        String suffix = null;

        // When
        boolean actual = StringUtils.endsWithIgnoreCase(input, suffix);

        // Then
        then(actual).isFalse();
    }

    @Test
    void endsWithIgnoreCase_ShouldReturnFalse_WhenBothStringAndSuffixAreEmpty() {
        // Given
        String input = EMPTY;
        String suffix = EMPTY;

        // When
        boolean actual = StringUtils.endsWithIgnoreCase(input, suffix);

        // Then
        then(actual).isFalse();
    }

    @Test
    void endsWithIgnoreCase_ShouldReturnFalse_WhenSuffixLongerThanString() {
        // Given
        String input = "abc";
        String suffix = "abcdef";

        // When
        boolean actual = StringUtils.endsWithIgnoreCase(input, suffix);

        // Then
        then(actual).isFalse();
    }

    @Test
    void endsWithIgnoreCase_ShouldReturnTrue_WhenStringEqualsSuffixIgnoringCase() {
        // Given
        String input = "AbC";
        String suffix = "aBc";

        // When
        boolean actual = StringUtils.endsWithIgnoreCase(input, suffix);

        // Then
        then(actual).isTrue();
    }

    @Test
    void endsWithIgnoreCase_ShouldReturnTrue_WhenStringEndsWithSuffixIgnoringCase() {
        // Given
        String input = "HelloWorld";
        String suffix = "WORLD";

        // When
        boolean actual = StringUtils.endsWithIgnoreCase(input, suffix);

        // Then
        then(actual).isTrue();
    }

    @Test
    void endsWithIgnoreCase_ShouldReturnFalse_WhenStringDoesNotEndWithSuffix() {
        // Given
        String input = "HelloWorld";
        String suffix = "Hello";

        // When
        boolean actual = StringUtils.endsWithIgnoreCase(input, suffix);

        // Then
        then(actual).isFalse();
    }

    @Test
    void endsWithIgnoreCase_ShouldReturnTrue_WhenStringEndsWithSuffixExactCase() {
        // Given
        String input = "HelloWorld";
        String suffix = "World";

        // When
        boolean actual = StringUtils.endsWithIgnoreCase(input, suffix);

        // Then
        then(actual).isTrue();
    }

    @Test
    void firstHasText_ShouldReturnNull_WhenInputIsNull() {
        // Given
        String[] input = null;

        // When
        String actual = StringUtils.firstHasText(input);

        // Then
        then(actual).isNull();
    }

    @Test
    void firstHasText_ShouldReturnNull_WhenInputArrayIsEmpty() {
        // Given
        String[] input = new String[0];

        // When
        String actual = StringUtils.firstHasText(input);

        // Then
        then(actual).isNull();
    }

    @Test
    void firstHasText_ShouldReturnFirstNonEmpty_WhenFirstElementHasText() {
        // Given
        String[] input = {"hello", "world"};

        // When
        String actual = StringUtils.firstHasText(input);

        // Then
        then(actual).isEqualTo("hello");
    }

    @Test
    void firstHasText_ShouldReturnSecondNonEmpty_WhenFirstElementIsEmptyAndSecondHasText() {
        // Given
        String[] input = {EMPTY, "world"};

        // When
        String actual = StringUtils.firstHasText(input);

        // Then
        then(actual).isEqualTo("world");
    }

    @Test
    void firstHasText_ShouldReturnNull_WhenAllElementsAreEmptyOrNull() {
        // Given
        String[] input = {EMPTY, "   ", null};

        // When
        String actual = StringUtils.firstHasText(input);

        // Then
        then(actual).isNull();
    }

    @Test
    void getDigits_ShouldReturnEmpty_WhenInputIsNull() {
        // Given
        String input = null;

        // When
        String actual = StringUtils.getDigits(input);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void getDigits_ShouldReturnEmpty_WhenInputIsEmpty() {
        // Given
        String input = EMPTY;

        // When
        String actual = StringUtils.getDigits(input);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void getDigits_ShouldReturnEmpty_WhenInputIsBlank() {
        // Given
        String input = "   ";

        // When
        String actual = StringUtils.getDigits(input);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void getDigits_ShouldReturnEmpty_WhenInputHasNoDigits() {
        // Given
        String input = "abc";

        // When
        String actual = StringUtils.getDigits(input);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void getDigits_ShouldReturnDigits_WhenInputHasDigits() {
        // Given
        String input = "123";

        // When
        String actual = StringUtils.getDigits(input);

        // Then
        then(actual).isEqualTo("123");
    }

    @Test
    void getDigits_ShouldReturnDigits_WhenInputHasMixedCharacters() {
        // Given
        String input = "a1b2c3";

        // When
        String actual = StringUtils.getDigits(input);

        // Then
        then(actual).isEqualTo("123");
    }

    @Test
    void hasLength_ShouldReturnFalse_WhenInputIsNull() {
        // Given
        String[] input = null;

        // When
        boolean actual = StringUtils.hasLength(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void hasLength_ShouldReturnFalse_WhenInputIsEmpty() {
        // Given
        String[] input = new String[0];

        // When
        boolean actual = StringUtils.hasLength(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void hasLength_ShouldReturnFalse_WhenAllElementsAreNull() {
        // Given
        String[] input = {null, null};

        // When
        boolean actual = StringUtils.hasLength(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void hasLength_ShouldReturnTrue_WhenInputHasNonEmpty() {
        // Given
        String[] input = {"hello"};

        // When
        boolean actual = StringUtils.hasLength(input);

        // Then
        then(actual).isTrue();
    }

    @Test
    void hasLength_ShouldReturnFalse_WhenInputHasEmpty() {
        // Given
        String[] input = {EMPTY};

        // When
        boolean actual = StringUtils.hasLength(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void hasLength_ShouldReturnTrue_WhenFirstIsEmptyAndSecondHasText() {
        // Given
        String[] input = {EMPTY, "hello"};

        // When
        boolean actual = StringUtils.hasLength(input);

        // Then
        then(actual).isTrue();
    }

    @Test
    void hasLength_ShouldReturnTrue_WhenOneElementHaveLength() {
        // Given
        String[] input = {EMPTY, "   ", null};

        // When
        boolean actual = StringUtils.hasLength(input);

        // Then
        then(actual).isTrue();
    }

    @Test
    void hasLength_ShouldReturnTrue_WhenFirstIsNullAndSecondHasText() {
        // Given
        String[] input = {null, "hello"};

        // When
        boolean actual = StringUtils.hasLength(input);

        // Then
        then(actual).isTrue();
    }

    @Test
    void hasLengthAll_ShouldReturnFalse_WhenInputIsNull() {
        // Given
        String[] input = null;

        // When
        boolean actual = StringUtils.hasLengthAll(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void hasLengthAll_ShouldReturnFalse_WhenInputIsEmptyArray() {
        // Given
        String[] input = new String[0];

        // When
        boolean actual = StringUtils.hasLengthAll(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void hasLengthAll_ShouldReturnFalse_WhenAllStringsAreEmpty() {
        // Given
        String[] input = {EMPTY, EMPTY};

        // When
        boolean actual = StringUtils.hasLengthAll(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void hasLengthAll_ShouldReturnFalse_WhenAllStringsAreNull() {
        // Given
        String[] input = {null, null};

        // When
        boolean actual = StringUtils.hasLengthAll(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void hasLengthAll_ShouldReturnTrue_WhenAllStringsHaveLength() {
        // Given
        String[] input = {"a", "b", "   "};

        // When
        boolean actual = StringUtils.hasLengthAll(input);

        // Then
        then(actual).isTrue();
    }

    @Test
    void hasLengthAll_ShouldReturnFalse_WhenFirstStringIsEmpty() {
        // Given
        String[] input = {EMPTY, "b"};

        // When
        boolean actual = StringUtils.hasLengthAll(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void hasLengthAll_ShouldReturnFalse_WhenFirstStringIsNull() {
        // Given
        String[] input = {null, "b"};

        // When
        boolean actual = StringUtils.hasLengthAll(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void hasLengthAll_ShouldReturnFalse_WhenSecondStringIsEmpty() {
        // Given
        String[] input = {"a", EMPTY};

        // When
        boolean actual = StringUtils.hasLengthAll(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void hasLengthAll_ShouldReturnFalse_WhenSecondStringIsNull() {
        // Given
        String[] input = {"a", null};

        // When
        boolean actual = StringUtils.hasLengthAll(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void hasText_ShouldReturnFalse_WhenInputIsNull() {
        // Given
        String[] input = null;

        // When
        boolean actual = StringUtils.hasText(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void hasText_ShouldReturnFalse_WhenInputIsEmptyArray() {
        // Given
        String[] input = new String[0];

        // When
        boolean actual = StringUtils.hasText(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void hasText_ShouldReturnTrue_WhenInputHasNonBlank() {
        // Given
        String[] input = {"hello"};

        // When
        boolean actual = StringUtils.hasText(input);

        // Then
        then(actual).isTrue();
    }

    @Test
    void hasText_ShouldReturnTrue_WhenFirstElementIsEmptyAndSecondHasText() {
        // Given
        String[] input = {EMPTY, "hello"};

        // When
        boolean actual = StringUtils.hasText(input);

        // Then
        then(actual).isTrue();
    }

    @Test
    void hasText_ShouldReturnFalse_WhenAllElementsAreEmpty() {
        // Given
        String[] input = {EMPTY, "   "};

        // When
        boolean actual = StringUtils.hasText(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void hasText_ShouldReturnTrue_WhenFirstElementIsNullAndSecondHasText() {
        // Given
        String[] input = {null, "hello"};

        // When
        boolean actual = StringUtils.hasText(input);

        // Then
        then(actual).isTrue();
    }

    @Test
    void hasText_ShouldReturnFalse_WhenAllElementsAreNull() {
        // Given
        String[] input = {null, null};

        // When
        boolean actual = StringUtils.hasText(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void hasTextAll_ShouldReturnFalse_WhenInputIsNull() {
        // Given
        String[] input = null;

        // When
        boolean actual = StringUtils.hasTextAll(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void hasTextAll_ShouldReturnFalse_WhenInputIsEmptyArray() {
        // Given
        String[] input = new String[0];

        // When
        boolean actual = StringUtils.hasTextAll(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void hasTextAll_ShouldReturnTrue_WhenAllStringsHaveText() {
        // Given
        String[] input = {"hello", "world"};

        // When
        boolean actual = StringUtils.hasTextAll(input);

        // Then
        then(actual).isTrue();
    }

    @Test
    void hasTextAll_ShouldReturnFalse_WhenFirstStringIsEmpty() {
        // Given
        String[] input = {EMPTY, "world"};

        // When
        boolean actual = StringUtils.hasTextAll(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void hasTextAll_ShouldReturnFalse_WhenFirstStringIsNull() {
        // Given
        String[] input = {null, "world"};

        // When
        boolean actual = StringUtils.hasTextAll(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void hasTextAll_ShouldReturnFalse_WhenSecondStringIsEmpty() {
        // Given
        String[] input = {"hello", EMPTY};

        // When
        boolean actual = StringUtils.hasTextAll(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void hasTextAll_ShouldReturnFalse_WhenSecondStringIsNull() {
        // Given
        String[] input = {"hello", null};

        // When
        boolean actual = StringUtils.hasTextAll(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void hasTextAll_ShouldReturnFalse_WhenAllStringsAreEmpty() {
        // Given
        String[] input = {EMPTY, EMPTY};

        // When
        boolean actual = StringUtils.hasTextAll(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void hasTextAll_ShouldReturnFalse_WhenAllStringsAreNull() {
        // Given
        String[] input = {null, null};

        // When
        boolean actual = StringUtils.hasTextAll(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void indexOfAny_ShouldReturnNegativeOne_WhenInputIsNull() {
        // Given
        String input = null;
        String[] search = {"a", "b"};

        // When
        int actual = StringUtils.indexOfAny(input, search);

        // Then
        then(actual).isEqualTo(INDEX_NOT_FOUND);
    }

    @Test
    void indexOfAny_ShouldReturnNegativeOne_WhenInputIsEmpty() {
        // Given
        String input = EMPTY;
        String[] search = {"a", "b"};

        // When
        int actual = StringUtils.indexOfAny(input, search);

        // Then
        then(actual).isEqualTo(INDEX_NOT_FOUND);
    }

    @Test
    void indexOfAny_ShouldReturnNegativeOne_WhenSearchArrayIsNull() {
        // Given
        String input = "hello";
        String[] search = null;

        // When
        int actual = StringUtils.indexOfAny(input, search);

        // Then
        then(actual).isEqualTo(INDEX_NOT_FOUND);
    }

    @Test
    void indexOfAny_ShouldReturnNegativeOne_WhenSearchArrayIsEmpty() {
        // Given
        String input = "hello";
        String[] search = new String[0];

        // When
        int actual = StringUtils.indexOfAny(input, search);

        // Then
        then(actual).isEqualTo(INDEX_NOT_FOUND);
    }

    @Test
    void indexOfAny_ShouldReturnNegativeOne_WhenNoMatchesFound() {
        // Given
        String input = "hello";
        String[] search = {"world", "foo"};

        // When
        int actual = StringUtils.indexOfAny(input, search);

        // Then
        then(actual).isEqualTo(INDEX_NOT_FOUND);
    }

    @Test
    void indexOfAny_ShouldReturnZero_WhenFirstSearchMatchesAtStart() {
        // Given
        String input = "hello";
        String[] search = {"he", "world"};

        // When
        int actual = StringUtils.indexOfAny(input, search);

        // Then
        then(actual).isEqualTo(0);
    }

    @Test
    void indexOfAny_ShouldReturnOne_WhenInputHasDigits() {
        // Given
        String input = "a1b2c3";
        String[] search = {"1", "2", "3"};

        // When
        int actual = StringUtils.indexOfAny(input, search);

        // Then
        then(actual).isEqualTo(1);
    }

    @Test
    void indexOfAny_ShouldReturnOne_WhenMultipleMatches() {
        // Given
        String input = "abcde";
        String[] search = {"bc", "de"};

        // When
        int actual = StringUtils.indexOfAny(input, search);

        // Then
        then(actual).isEqualTo(1);
    }

    @Test
    void indexOfAny_ShouldReturnZero_WhenSearchStringsOverlap() {
        // Given
        String input = "abcde";
        String[] search = {"abc", "bc"};

        // When
        int actual = StringUtils.indexOfAny(input, search);

        // Then
        then(actual).isEqualTo(0);
    }

    @Test
    void lastIndexOfAny_ShouldReturnNegativeOne_WhenInputIsNull() {
        // Given
        String input = null;
        String[] search = {"a", "b"};

        // When
        int actual = StringUtils.lastIndexOfAny(input, search);

        // Then
        then(actual).isEqualTo(INDEX_NOT_FOUND);
    }

    @Test
    void lastIndexOfAny_ShouldReturnNegativeOne_WhenInputIsEmpty() {
        // Given
        String input = EMPTY;
        String[] search = {"a", "b"};

        // When
        int actual = StringUtils.lastIndexOfAny(input, search);

        // Then
        then(actual).isEqualTo(INDEX_NOT_FOUND);
    }

    @Test
    void lastIndexOfAny_ShouldReturnNegativeOne_WhenSearchArrayIsNull() {
        // Given
        String input = "hello";
        String[] search = null;

        // When
        int actual = StringUtils.lastIndexOfAny(input, search);

        // Then
        then(actual).isEqualTo(INDEX_NOT_FOUND);
    }

    @Test
    void lastIndexOfAny_ShouldReturnNegativeOne_WhenSearchArrayIsEmpty() {
        // Given
        String input = "hello";
        String[] search = new String[0];

        // When
        int actual = StringUtils.lastIndexOfAny(input, search);

        // Then
        then(actual).isEqualTo(INDEX_NOT_FOUND);
    }

    @Test
    void lastIndexOfAny_ShouldReturnNegativeOne_WhenNoMatchesFound() {
        // Given
        String input = "hello";
        String[] search = {"world", "foo"};

        // When
        int actual = StringUtils.lastIndexOfAny(input, search);

        // Then
        then(actual).isEqualTo(INDEX_NOT_FOUND);
    }

    @Test
    void lastIndexOfAny_ShouldReturnZero_WhenInputIsSingleCharacter() {
        // Given
        String input = "a";
        String[] search = {"a"};

        // When
        int actual = StringUtils.lastIndexOfAny(input, search);

        // Then
        then(actual).isEqualTo(0);
    }

    @Test
    void lastIndexOfAny_ShouldReturnFour_WhenInputEndsWithSearch() {
        // Given
        String input = "hello";
        String[] search = {"o"};

        // When
        int actual = StringUtils.lastIndexOfAny(input, search);

        // Then
        then(actual).isEqualTo(4);
    }

    @Test
    void lastIndexOfAny_ShouldReturnThree_WhenInputHasMultipleMatches() {
        // Given
        String input = "abab";
        String[] search = {"a", "b"};

        // When
        int actual = StringUtils.lastIndexOfAny(input, search);

        // Then
        then(actual).isEqualTo(3);
    }

    @Test
    void lastIndexOfAny_ShouldReturnOne_WhenSearchStringsOverlap() {
        // Given
        String input = "abcde";
        String[] search = {"abc", "bc"};

        // When
        int actual = StringUtils.lastIndexOfAny(input, search);

        // Then
        then(actual).isEqualTo(1);
    }

    @Test
    void lastIndexOfAny_ShouldReturnFive_WhenInputHasDigits() {
        // Given
        String input = "a1b2c3";
        String[] search = {"1", "2", "3"};

        // When
        int actual = StringUtils.lastIndexOfAny(input, search);

        // Then
        then(actual).isEqualTo(5);
    }

    @Test
    void isAllLowerCase_ShouldReturnFalse_WhenInputIsNull() {
        // Given
        String input = null;

        // When
        boolean actual = StringUtils.isAllLowerCase(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void isAllLowerCase_ShouldReturnFalse_WhenInputIsEmpty() {
        // Given
        String input = EMPTY;

        // When
        boolean actual = StringUtils.isAllLowerCase(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void isAllLowerCase_ShouldReturnFalse_WhenInputIsBlank() {
        // Given
        String input = "   ";

        // When
        boolean actual = StringUtils.isAllLowerCase(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void isAllLowerCase_ShouldReturnTrue_WhenInputIsAllLowerCase() {
        // Given
        String input = "hello";

        // When
        boolean actual = StringUtils.isAllLowerCase(input);

        // Then
        then(actual).isTrue();
    }

    @Test
    void isAllLowerCase_ShouldReturnFalse_WhenInputHasUppercase() {
        // Given
        String input = "Hello";

        // When
        boolean actual = StringUtils.isAllLowerCase(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void isAllLowerCase_ShouldReturnFalse_WhenInputHasMixedCase() {
        // Given
        String input = "HeLLo";

        // When
        boolean actual = StringUtils.isAllLowerCase(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void isAllLowerCase_ShouldReturnFalse_WhenInputHasDigits() {
        // Given
        String input = "a1b";

        // When
        boolean actual = StringUtils.isAllLowerCase(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void isAllLowerCase_ShouldReturnFalse_WhenInputHasSpecialCharacters() {
        // Given
        String input = "a@b";

        // When
        boolean actual = StringUtils.isAllLowerCase(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void isAllUpperCase_ShouldReturnFalse_WhenInputIsNull() {
        // Given
        String input = null;

        // When
        boolean actual = StringUtils.isAllUpperCase(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void isAllUpperCase_ShouldReturnFalse_WhenInputIsEmpty() {
        // Given
        String input = EMPTY;

        // When
        boolean actual = StringUtils.isAllUpperCase(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void isAllUpperCase_ShouldReturnFalse_WhenInputIsBlank() {
        // Given
        String input = "   ";

        // When
        boolean actual = StringUtils.isAllUpperCase(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void isAllUpperCase_ShouldReturnTrue_WhenInputIsAllUpperCase() {
        // Given
        String input = "HELLO";

        // When
        boolean actual = StringUtils.isAllUpperCase(input);

        // Then
        then(actual).isTrue();
    }

    @Test
    void isAllUpperCase_ShouldReturnFalse_WhenInputHasLowercase() {
        // Given
        String input = "Hello";

        // When
        boolean actual = StringUtils.isAllUpperCase(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void isAllUpperCase_ShouldReturnFalse_WhenInputHasMixedCase() {
        // Given
        String input = "HeLLo";

        // When
        boolean actual = StringUtils.isAllUpperCase(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void isAllUpperCase_ShouldReturnFalse_WhenInputHasDigits() {
        // Given
        String input = "A1B";

        // When
        boolean actual = StringUtils.isAllUpperCase(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void isAllUpperCase_ShouldReturnFalse_WhenInputHasSpecialCharacters() {
        // Given
        String input = "A@B";

        // When
        boolean actual = StringUtils.isAllUpperCase(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void isAlpha_ShouldReturnFalse_WhenInputIsNull() {
        // Given
        String input = null;

        // When
        boolean actual = StringUtils.isAlpha(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void isAlpha_ShouldReturnFalse_WhenInputIsEmpty() {
        // Given
        String input = EMPTY;

        // When
        boolean actual = StringUtils.isAlpha(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void isAlpha_ShouldReturnFalse_WhenInputIsBlank() {
        // Given
        String input = "   ";

        // When
        boolean actual = StringUtils.isAlpha(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void isAlpha_ShouldReturnTrue_WhenInputIsAllLettersLowercase() {
        // Given
        String input = "hello";

        // When
        boolean actual = StringUtils.isAlpha(input);

        // Then
        then(actual).isTrue();
    }

    @Test
    void isAlpha_ShouldReturnTrue_WhenInputIsAllLettersUppercase() {
        // Given
        String input = "HELLO";

        // When
        boolean actual = StringUtils.isAlpha(input);

        // Then
        then(actual).isTrue();
    }

    @Test
    void isAlpha_ShouldReturnTrue_WhenInputIsMixedCaseLetters() {
        // Given
        String input = "HeLLo";

        // When
        boolean actual = StringUtils.isAlpha(input);

        // Then
        then(actual).isTrue();
    }

    @Test
    void isAlpha_ShouldReturnFalse_WhenInputHasDigit() {
        // Given
        String input = "a1b";

        // When
        boolean actual = StringUtils.isAlpha(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void isAlpha_ShouldReturnFalse_WhenInputHasSpecialCharacter() {
        // Given
        String input = "a@b";

        // When
        boolean actual = StringUtils.isAlpha(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void isAlpha_ShouldReturnFalse_WhenInputHasSpace() {
        // Given
        String input = "a b";

        // When
        boolean actual = StringUtils.isAlpha(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void isNumeric_ShouldReturnFalse_WhenInputIsNull() {
        // Given
        String input = null;

        // When
        boolean actual = StringUtils.isNumeric(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void isNumeric_ShouldReturnFalse_WhenInputIsEmpty() {
        // Given
        String input = EMPTY;

        // When
        boolean actual = StringUtils.isNumeric(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void isNumeric_ShouldReturnFalse_WhenInputIsBlank() {
        // Given
        String input = "   ";

        // When
        boolean actual = StringUtils.isNumeric(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void isNumeric_ShouldReturnFalse_WhenInputIsPlusSign() {
        // Given
        String input = "+";

        // When
        boolean actual = StringUtils.isNumeric(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void isNumeric_ShouldReturnFalse_WhenInputIsMinusSign() {
        // Given
        String input = "-";

        // When
        boolean actual = StringUtils.isNumeric(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void isNumeric_ShouldReturnFalse_WhenInputHasMultipleSigns() {
        // Given
        String input = "++1";

        // When
        boolean actual = StringUtils.isNumeric(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void isNumeric_ShouldReturnTrue_WhenInputIsPositiveNumber() {
        // Given
        String input = "+123";

        // When
        boolean actual = StringUtils.isNumeric(input);

        // Then
        then(actual).isTrue();
    }

    @Test
    void isNumeric_ShouldReturnTrue_WhenInputIsNegativeNumber() {
        // Given
        String input = "-123";

        // When
        boolean actual = StringUtils.isNumeric(input);

        // Then
        then(actual).isTrue();
    }

    @Test
    void isNumeric_ShouldReturnTrue_WhenInputIsInteger() {
        // Given
        String input = "123";

        // When
        boolean actual = StringUtils.isNumeric(input);

        // Then
        then(actual).isTrue();
    }

    @Test
    void isNumeric_ShouldReturnTrue_WhenInputIsDecimal() {
        // Given
        String input = "1.23";

        // When
        boolean actual = StringUtils.isNumeric(input);

        // Then
        then(actual).isTrue();
    }

    @Test
    void isNumeric_ShouldReturnTrue_WhenInputIsZero() {
        // Given
        String input = "0";

        // When
        boolean actual = StringUtils.isNumeric(input);

        // Then
        then(actual).isTrue();
    }

    @Test
    void isNumeric_ShouldReturnTrue_WhenInputIsZeroDecimal() {
        // Given
        String input = "0.0";

        // When
        boolean actual = StringUtils.isNumeric(input);

        // Then
        then(actual).isTrue();
    }

    @Test
    void isNumeric_ShouldReturnFalse_WhenInputHasDecimalAtEnd() {
        // Given
        String input = "1.";

        // When
        boolean actual = StringUtils.isNumeric(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void isNumeric_ShouldReturnFalse_WhenInputHasDecimalAtStart() {
        // Given
        String input = ".1";

        // When
        boolean actual = StringUtils.isNumeric(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void isNumeric_ShouldReturnFalse_WhenInputHasMultipleDecimals() {
        // Given
        String input = "1.2.3";

        // When
        boolean actual = StringUtils.isNumeric(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void isNumeric_ShouldReturnFalse_WhenInputHasNonDigit() {
        // Given
        String input = "a123";

        // When
        boolean actual = StringUtils.isNumeric(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void isNumeric_ShouldReturnFalse_WhenInputHasNonDigitInDecimal() {
        // Given
        String input = "1.2a";

        // When
        boolean actual = StringUtils.isNumeric(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void isAlphanumeric_ShouldReturnFalse_WhenInputIsNull() {
        // Given
        String input = null;

        // When
        boolean actual = StringUtils.isAlphanumeric(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void isAlphanumeric_ShouldReturnFalse_WhenInputIsEmpty() {
        // Given
        String input = EMPTY;

        // When
        boolean actual = StringUtils.isAlphanumeric(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void isAlphanumeric_ShouldReturnFalse_WhenInputIsBlank() {
        // Given
        String input = "   ";

        // When
        boolean actual = StringUtils.isAlphanumeric(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void isAlphanumeric_ShouldReturnTrue_WhenInputIsAllLetters() {
        // Given
        String input = "hello";

        // When
        boolean actual = StringUtils.isAlphanumeric(input);

        // Then
        then(actual).isTrue();
    }

    @Test
    void isAlphanumeric_ShouldReturnTrue_WhenInputIsAllDigits() {
        // Given
        String input = "123";

        // When
        boolean actual = StringUtils.isAlphanumeric(input);

        // Then
        then(actual).isTrue();
    }

    @Test
    void isAlphanumeric_ShouldReturnTrue_WhenInputHasLettersAndDigits() {
        // Given
        String input = "a1b2";

        // When
        boolean actual = StringUtils.isAlphanumeric(input);

        // Then
        then(actual).isTrue();
    }

    @Test
    void isAlphanumeric_ShouldReturnFalse_WhenInputHasSpace() {
        // Given
        String input = "a b";

        // When
        boolean actual = StringUtils.isAlphanumeric(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void isAlphanumeric_ShouldReturnFalse_WhenInputHasSpecialCharacter() {
        // Given
        String input = "a@b";

        // When
        boolean actual = StringUtils.isAlphanumeric(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void isAlphaSpace_ShouldReturnFalse_WhenInputIsNull() {
        // Given
        String input = null;

        // When
        boolean actual = StringUtils.isAlphaSpace(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void isAlphaSpace_ShouldReturnFalse_WhenInputIsEmpty() {
        // Given
        String input = EMPTY;

        // When
        boolean actual = StringUtils.isAlphaSpace(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void isAlphaSpace_ShouldReturnTrue_WhenInputIsBlank() {
        // Given
        String input = "   ";

        // When
        boolean actual = StringUtils.isAlphaSpace(input);

        // Then
        then(actual).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "hello", "hello world"})
    void isAlphaSpace_ShouldReturnTrue_WhenInputIsAllLettersOrHasLettersAndSpaces(String input) {
        // When
        boolean actual = StringUtils.isAlphaSpace(input);

        // Then
        then(actual).isTrue();
    }

    @Test
    void isAlphaSpace_ShouldReturnFalse_WhenInputIsDigitsOnly() {
        // Given
        String input = "123";

        // When
        boolean actual = StringUtils.isAlphaSpace(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void isAlphaSpace_ShouldReturnFalse_WhenInputIsSpecialCharactersOnly() {
        // Given
        String input = "@#$";

        // When
        boolean actual = StringUtils.isAlphaSpace(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void isAlphaSpace_ShouldReturnFalse_WhenInputHasLettersAndDigits() {
        // Given
        String input = "hello1";

        // When
        boolean actual = StringUtils.isAlphaSpace(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void isAlphaSpace_ShouldReturnFalse_WhenInputHasLettersAndSpecialCharacters() {
        // Given
        String input = "hello@";

        // When
        boolean actual = StringUtils.isAlphaSpace(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void isAlphaSpace_ShouldReturnFalse_WhenInputHasDigitsAndSpace() {
        // Given
        String input = "123 ";

        // When
        boolean actual = StringUtils.isAlphaSpace(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void isAlphaSpace_ShouldReturnFalse_WhenInputHasSpecialCharactersAndSpace() {
        // Given
        String input = "a @";

        // When
        boolean actual = StringUtils.isAlphaSpace(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void isNumericSpace_ShouldReturnFalse_WhenInputIsNull() {
        // Given
        String input = null;

        // When
        boolean actual = StringUtils.isNumericSpace(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void isNumericSpace_ShouldReturnFalse_WhenInputIsEmpty() {
        // Given
        String input = EMPTY;

        // When
        boolean actual = StringUtils.isNumericSpace(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void isNumericSpace_ShouldReturnFalse_WhenInputIsBlank() {
        // Given
        String input = "   ";

        // When
        boolean actual = StringUtils.isNumericSpace(input);

        // Then
        then(actual).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {"123", "1 2 3", " 123", "123 ", " 123 "})
    void isNumericSpace_ShouldReturnTrue_WhenInputIsDigitsOnlyOrHasDigitsAndSpaces(String input) {
        // When
        boolean actual = StringUtils.isNumericSpace(input);

        // Then
        then(actual).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"a123", "123a", "1@2", "123@"})
    void isNumericSpace_ShouldReturnFalse_WhenInputHasLetterOrSpecialCharacter(String input) {
        // When
        boolean actual = StringUtils.isNumericSpace(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void isAlphanumericSpace_ShouldReturnFalse_WhenInputIsNull() {
        // Given
        String input = null;

        // When
        boolean actual = StringUtils.isAlphanumericSpace(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void isAlphanumericSpace_ShouldReturnFalse_WhenInputIsEmpty() {
        // Given
        String input = EMPTY;

        // When
        boolean actual = StringUtils.isAlphanumericSpace(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void isAlphanumericSpace_ShouldReturnTrue_WhenInputIsBlank() {
        // Given
        String input = "   ";

        // When
        boolean actual = StringUtils.isAlphanumericSpace(input);

        // Then
        then(actual).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "hello", "123", "hello123", "hello 123"})
    void isAlphanumericSpace_ShouldReturnTrue_WhenInputHasLettersAndDigitsAndSpaces(String input) {
        // When
        boolean actual = StringUtils.isAlphanumericSpace(input);

        // Then
        then(actual).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"@", "a@", "1@", " @", "a @"})
    void isAlphanumericSpace_ShouldReturnFalse_WhenInputHasLettersOrDigitsAndHasSpecialCharacter(String input) {
        // When
        boolean actual = StringUtils.isAlphanumericSpace(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void isWhitespace_ShouldReturnFalse_WhenInputIsNull() {
        // Given
        String input = null;

        // When
        boolean actual = StringUtils.isWhitespace(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void isWhitespace_ShouldReturnFalse_WhenInputIsEmpty() {
        // Given
        String input = EMPTY;

        // When
        boolean actual = StringUtils.isWhitespace(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void isWhitespace_ShouldReturnTrue_WhenInputIsBlank() {
        // Given
        String input = "   ";

        // When
        boolean actual = StringUtils.isWhitespace(input);

        // Then
        then(actual).isTrue();
    }

    @Test
    void isWhitespace_ShouldReturnFalse_WhenInputHasNonWhitespace() {
        // Given
        String input = "a";

        // When
        boolean actual = StringUtils.isWhitespace(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void isWhitespace_ShouldReturnFalse_WhenInputHasWhitespaceAndNonWhitespace() {
        // Given
        String input = "a ";

        // When
        boolean actual = StringUtils.isWhitespace(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void isWhitespace_ShouldReturnTrue_WhenInputIsTab() {
        // Given
        String input = "\t";

        // When
        boolean actual = StringUtils.isWhitespace(input);

        // Then
        then(actual).isTrue();
    }

    @Test
    void isWhitespace_ShouldReturnTrue_WhenInputIsNewline() {
        // Given
        String input = "\n";

        // When
        boolean actual = StringUtils.isWhitespace(input);

        // Then
        then(actual).isTrue();
    }

    @Test
    void isWhitespace_ShouldReturnTrue_WhenInputHasMultipleWhitespaceCharacters() {
        // Given
        String input = " \t\n";

        // When
        boolean actual = StringUtils.isWhitespace(input);

        // Then
        then(actual).isTrue();
    }

    @Test
    void left_ShouldReturnNull_WhenInputIsNull() {
        // Given
        String input = null;
        int length = 5;

        // When
        String actual = StringUtils.left(input, length);

        // Then
        then(actual).isNull();
    }

    @Test
    void left_ShouldReturnEmpty_WhenInputIsEmpty() {
        // Given
        String input = EMPTY;
        int length = 5;

        // When
        String actual = StringUtils.left(input, length);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void left_ShouldReturnOriginal_WhenLengthIsGreaterThanInputLength() {
        // Given
        String input = "hello";
        int length = 10;

        // When
        String actual = StringUtils.left(input, length);

        // Then
        then(actual).isEqualTo("hello");
    }

    @Test
    void left_ShouldReturnOriginal_WhenLengthEqualsInputLength() {
        // Given
        String input = "hello";
        int length = 5;

        // When
        String actual = StringUtils.left(input, length);

        // Then
        then(actual).isEqualTo("hello");
    }

    @Test
    void left_ShouldReturnEmpty_WhenLengthIsZero() {
        // Given
        String input = "hello";
        int length = 0;

        // When
        String actual = StringUtils.left(input, length);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void left_ShouldReturnSubstring_WhenLengthIsLessThanInputLength() {
        // Given
        String input = "hello";
        int length = 3;

        // When
        String actual = StringUtils.left(input, length);

        // Then
        then(actual).isEqualTo("hel");
    }

    @Test
    void left_ShouldThrowException_WhenLengthIsNegative() {
        // Given
        String input = "test";
        int length = -1;

        // Then
        thenThrownBy(() -> StringUtils.left(input, length))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Length must be positive");
    }

    @Test
    void length_ShouldReturnZero_WhenInputIsNull() {
        // Given
        String input = null;

        // When
        int actual = StringUtils.length(input);

        // Then
        then(actual).isEqualTo(0);
    }

    @Test
    void length_ShouldReturnZero_WhenInputIsEmpty() {
        // Given
        String input = EMPTY;

        // When
        int actual = StringUtils.length(input);

        // Then
        then(actual).isEqualTo(0);
    }

    @Test
    void length_ShouldReturnThree_WhenInputIsBlank() {
        // Given
        String input = "   ";

        // When
        int actual = StringUtils.length(input);

        // Then
        then(actual).isEqualTo(3);
    }

    @Test
    void length_ShouldReturnFive_WhenInputIsHello() {
        // Given
        String input = "hello";

        // When
        int actual = StringUtils.length(input);

        // Then
        then(actual).isEqualTo(5);
    }

    @Test
    void mid_ShouldReturnNull_WhenInputIsNull() {
        // Given
        String input = null;
        int position = 0;
        int length = 5;

        // When
        String actual = StringUtils.mid(input, position, length);

        // Then
        then(actual).isNull();
    }

    @Test
    void mid_ShouldReturnEmpty_WhenInputIsEmpty() {
        // Given
        String input = EMPTY;
        int position = 0;
        int length = 5;

        // When
        String actual = StringUtils.mid(input, position, length);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void mid_ShouldReturnEmpty_WhenPositionExceedsLength() {
        // Given
        String input = "hello";
        int position = 6;
        int length = 5;

        // When
        String actual = StringUtils.mid(input, position, length);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void mid_ShouldReturnSubstring_WhenPositionAndLengthValid() {
        // Given
        String input = "hello";
        int position = 1;
        int length = 3;

        // When
        String actual = StringUtils.mid(input, position, length);

        // Then
        then(actual).isEqualTo("ell");
    }

    @Test
    void mid_ShouldReturnSubstringToTheEnd_WhenLengthExceedsRemaining() {
        // Given
        String input = "hello";
        int position = 2;
        int length = 10;

        // When
        String actual = StringUtils.mid(input, position, length);

        // Then
        then(actual).isEqualTo("llo");
    }

    @Test
    void mid_ShouldReturnEmpty_WhenLengthIsZero() {
        // Given
        String input = "hello";
        int position = 2;
        int length = 0;

        // When
        String actual = StringUtils.mid(input, position, length);
        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void mid_ShouldReturnEmpty_WhenPositionEqualsLength() {
        // Given
        String input = "hello";
        int position = 5;
        int length = 0;

        // When
        String actual = StringUtils.mid(input, position, length);
        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void mid_ShouldReturnFullString_WhenPositionIsZeroAndLengthEqualsLength() {
        // Given
        String input = "hello";
        int position = 0;
        int length = 5;

        // When
        String actual = StringUtils.mid(input, position, length);
        // Then
        then(actual).isEqualTo("hello");
    }

    @Test
    void mid_ShouldThrowException_WhenLengthIsNegative() {
        // Given
        String input = "hello";
        int position = 0;
        int length = -1;

        // Then
        thenThrownBy(() -> StringUtils.mid(input, position, length))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Length must be positive");
    }

    @Test
    void mid_ShouldThrowException_WhenPositionIsNegative() {
        // Given
        String input = "hello";
        int position = -1;
        int length = 5;

        // Then
        thenThrownBy(() -> StringUtils.mid(input, position, length))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Position must be positive");
    }

    @Test
    void normalizeSpace_ShouldReturnNull_WhenInputIsNull() {
        // Given
        String input = null;

        // When
        String actual = StringUtils.normalizeSpace(input);

        // Then
        then(actual).isNull();
    }

    @Test
    void normalizeSpace_ShouldReturnEmpty_WhenInputIsEmpty() {
        // Given
        String input = EMPTY;

        // When
        String actual = StringUtils.normalizeSpace(input);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void normalizeSpace_ShouldReturnTrimmed_WhenInputHasLeadingAndTrailingWhitespace() {
        // Given
        String input = "   hello   ";

        // When
        String actual = StringUtils.normalizeSpace(input);

        // Then
        then(actual).isEqualTo("hello");
    }

    @Test
    void normalizeSpace_ShouldCollapseMultipleSpaces_WhenInputHasMultipleSpaces() {
        // Given
        String input = "a  b   c";

        // When
        String actual = StringUtils.normalizeSpace(input);

        // Then
        then(actual).isEqualTo("a b c");
    }

    @Test
    void normalizeSpace_ShouldReplaceNonBreakingSpace_WhenInputHasNonBreakingSpace() {
        // Given
        String input = "a\u00A0b";

        // When
        String actual = StringUtils.normalizeSpace(input);

        // Then
        then(actual).isEqualTo("a b");
    }

    @Test
    void normalizeSpace_ShouldCollapseMixedWhitespace_WhenInputHasMixedWhitespace() {
        // Given
        String input = "a\t  \n b \r c";

        // When
        String actual = StringUtils.normalizeSpace(input);

        // Then
        then(actual).isEqualTo("a b c");
    }

    @Test
    void normalizeSpace_ShouldReturnEmpty_WhenInputIsAllWhitespace() {
        // Given
        String input = "   \t\n\r";

        // When
        String actual = StringUtils.normalizeSpace(input);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void normalizeSpace_ShouldReturnEmpty_WhenInputIsAllNonBreakingSpaces() {
        // Given
        String input = "\u00A0\u00A0\u00A0";

        // When
        String actual = StringUtils.normalizeSpace(input);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void normalizeSpace_ShouldTrimNonBreakingSpaceAtStart_WhenInputHasNonBreakingSpaceAtStart() {
        // Given
        String input = "\u00A0a";

        // When
        String actual = StringUtils.normalizeSpace(input);

        // Then
        then(actual).isEqualTo("a");
    }

    @Test
    void normalizeSpace_ShouldTrimNonBreakingSpaceAtEnd_WhenInputHasNonBreakingSpaceAtEnd() {
        // Given
        String input = "a\u00A0";

        // When
        String actual = StringUtils.normalizeSpace(input);

        // Then
        then(actual).isEqualTo("a");
    }

    @Test
    void normalizeSpace_ShouldReturnEmpty_WhenInputIsSingleNonBreakingSpace() {
        // Given
        String input = "\u00A0";

        // When
        String actual = StringUtils.normalizeSpace(input);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void overlay_ShouldReturnNull_WhenInputIsNull() {
        // Given
        String input = null;
        String overlay = "test";
        int position = 0;
        int length = 5;

        // When
        String actual = StringUtils.overlay(input, overlay, position, length);

        // Then
        then(actual).isNull();
    }

    @Test
    void overlay_ShouldUseEmptyString_WhenOverlayIsNull() {
        // Given
        String input = "hello";
        String overlay = null;
        int position = 1;
        int length = 2;

        // When
        String actual = StringUtils.overlay(input, overlay, position, length);

        // Then
        then(actual).isEqualTo("hllo");
    }

    @Test
    void overlay_ShouldSetPositionToLength_WhenPositionExceedsInputLength() {
        // Given
        String input = "hello";
        String overlay = "test";
        int position = 6;
        int length = 2;

        // When
        String actual = StringUtils.overlay(input, overlay, position, length);

        // Then
        then(actual).isEqualTo("hetest");
    }

    @Test
    void overlay_ShouldSetLengthToLength_WhenLengthExceedsInputLength() {
        // Given
        String input = "hello";
        String overlay = "test";
        int position = 3;
        int length = 10;

        // When
        String actual = StringUtils.overlay(input, overlay, position, length);

        // Then
        then(actual).isEqualTo("heltest");
    }

    @Test
    void overlay_ShouldSwapPositionAndLength_WhenPositionExceedsLength() {
        // Given
        String input = "hello";
        String overlay = "test";
        int position = 5;
        int length = 3;

        // When
        String actual = StringUtils.overlay(input, overlay, position, length);

        // Then
        then(actual).isEqualTo("heltest");
    }

    @Test
    void overlay_ShouldReplaceMiddle_WhenPositionAndLengthValid() {
        // Given
        String input = "hello";
        String overlay = "test";
        int position = 2;
        int length = 4;

        // When
        String actual = StringUtils.overlay(input, overlay, position, length);

        // Then
        then(actual).isEqualTo("hetesto");
    }

    @Test
    void overlay_ShouldInsertAtPosition_WhenLengthEqualsPosition() {
        // Given
        String input = "hello";
        String overlay = "test";
        int position = 2;
        int length = 2;

        // When
        String actual = StringUtils.overlay(input, overlay, position, length);

        // Then
        then(actual).isEqualTo("hetestllo");
    }

    @Test
    void overlay_ShouldAppendAtEnd_WhenPositionAndLengthAreLength() {
        // Given
        String input = "hello";
        String overlay = "test";
        int position = 5;
        int length = 5;

        // When
        String actual = StringUtils.overlay(input, overlay, position, length);

        // Then
        then(actual).isEqualTo("hellotest");
    }

    @Test
    void overlay_ShouldReplaceStart_WhenPositionIsZero() {
        // Given
        String input = "hello";
        String overlay = "test";
        int position = 0;
        int length = 2;

        // When
        String actual = StringUtils.overlay(input, overlay, position, length);

        // Then
        then(actual).isEqualTo("testllo");
    }

    @Test
    void overlay_ShouldReplaceEnd_WhenLengthIsZero() {
        // Given
        String input = "hello";
        String overlay = "test";
        int position = 3;
        int length = 0;

        // When
        String actual = StringUtils.overlay(input, overlay, position, length);

        // Then
        then(actual).isEqualTo("testlo");
    }

    @Test
    void overlay_ShouldThrowException_WhenLengthIsNegative() {
        // Given
        String input = "hello";
        String overlay = "test";
        int position = 0;
        int length = -1;

        // Then
        thenThrownBy(() -> StringUtils.overlay(input, overlay, position, length))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Length must be positive");
    }

    @Test
    void overlay_ShouldThrowException_WhenPositionIsNegative() {
        // Given
        String input = "hello";
        String overlay = "test";
        int position = -1;
        int length = 5;

        // Then
        thenThrownBy(() -> StringUtils.overlay(input, overlay, position, length))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Position must be positive");
    }

    @Test
    void countOccurrencesOf_ShouldReturnZero_WhenInputIsNull() {
        // Given
        String input = null;
        String subString = "test";

        // When
        int actual = StringUtils.countOccurrencesOf(input, subString);

        // Then
        then(actual).isEqualTo(0);
    }

    @Test
    void countOccurrencesOf_ShouldReturnZero_WhenInputIsEmpty() {
        // Given
        String input = EMPTY;
        String subString = "test";

        // When
        int actual = StringUtils.countOccurrencesOf(input, subString);

        // Then
        then(actual).isEqualTo(0);
    }

    @Test
    void countOccurrencesOf_ShouldReturnZero_WhenSubStringIsNull() {
        // Given
        String input = "test";
        String subString = null;

        // When
        int actual = StringUtils.countOccurrencesOf(input, subString);

        // Then
        then(actual).isEqualTo(0);
    }

    @Test
    void countOccurrencesOf_ShouldReturnZero_WhenSubStringIsEmpty() {
        // Given
        String input = "test";
        String subString = EMPTY;

        // When
        int actual = StringUtils.countOccurrencesOf(input, subString);

        // Then
        then(actual).isEqualTo(0);
    }

    @Test
    void countOccurrencesOf_ShouldReturnZero_WhenSubStringNotFound() {
        // Given
        String input = "hello";
        String subString = "world";

        // When
        int actual = StringUtils.countOccurrencesOf(input, subString);

        // Then
        then(actual).isEqualTo(0);
    }

    @Test
    void countOccurrencesOf_ShouldReturnOne_WhenSubStringFoundOnce() {
        // Given
        String input = "hello";
        String subString = "ll";

        // When
        int actual = StringUtils.countOccurrencesOf(input, subString);

        // Then
        then(actual).isEqualTo(1);
    }

    @Test
    void countOccurrencesOf_ShouldReturnThree_WhenSubStringAppearsThreeTimes() {
        // Given
        String input = "ababab";
        String subString = "ab";

        // When
        int actual = StringUtils.countOccurrencesOf(input, subString);

        // Then
        then(actual).isEqualTo(3);
    }

    @Test
    void countOccurrencesOf_ShouldReturnTwo_WhenSubStringAppearsTwiceWithAdjacentPositions() {
        // Given
        String input = "aaaa";
        String subString = "aa";

        // When
        int actual = StringUtils.countOccurrencesOf(input, subString);

        // Then
        then(actual).isEqualTo(2);
    }

    @Test
    void countOccurrencesOf_ShouldReturnOne_WhenSubStringAtStart() {
        // Given
        String input = "hello";
        String subString = "he";

        // When
        int actual = StringUtils.countOccurrencesOf(input, subString);

        // Then
        then(actual).isEqualTo(1);
    }

    @Test
    void countOccurrencesOf_ShouldReturnOne_WhenSubStringAtEnd() {
        // Given
        String input = "hello";
        String subString = "lo";

        // When
        int actual = StringUtils.countOccurrencesOf(input, subString);

        // Then
        then(actual).isEqualTo(1);
    }

    @Test
    void countOccurrencesOf_ShouldReturnOne_WhenSubStringInMiddle() {
        // Given
        String input = "hello";
        String subString = "el";

        // When
        int actual = StringUtils.countOccurrencesOf(input, subString);

        // Then
        then(actual).isEqualTo(1);
    }

    @Test
    void countOccurrencesOf_ShouldReturnOne_WhenSubStringEqualsInput() {
        // Given
        String input = "hello";
        String subString = "hello";

        // When
        int actual = StringUtils.countOccurrencesOf(input, subString);

        // Then
        then(actual).isEqualTo(1);
    }

    @Test
    void countOccurrencesOf_ShouldReturnZero_WhenSubStringLongerThanInput() {
        // Given
        String input = "hi";
        String subString = "hello";

        // When
        int actual = StringUtils.countOccurrencesOf(input, subString);

        // Then
        then(actual).isEqualTo(0);
    }

    @Test
    void replace_ShouldReturnNull_WhenInputIsNull() {
        // Given
        String input = null;
        String oldPattern = "a";
        String newPattern = "b";

        // When
        String actual = StringUtils.replace(input, oldPattern, newPattern);

        // Then
        then(actual).isNull();
    }

    @Test
    void replace_ShouldReturnEmpty_WhenInputIsEmpty() {
        // Given
        String input = EMPTY;
        String oldPattern = "a";
        String newPattern = "b";

        // When
        String actual = StringUtils.replace(input, oldPattern, newPattern);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void replace_ShouldReturnOriginal_WhenOldPatternIsNull() {
        // Given
        String input = "hello";
        String oldPattern = null;
        String newPattern = "x";

        // When
        String actual = StringUtils.replace(input, oldPattern, newPattern);

        // Then
        then(actual).isEqualTo("hello");
    }

    @Test
    void replace_ShouldReturnOriginal_WhenOldPatternIsEmpty() {
        // Given
        String input = "hello";
        String oldPattern = EMPTY;
        String newPattern = "x";

        // When
        String actual = StringUtils.replace(input, oldPattern, newPattern);

        // Then
        then(actual).isEqualTo("hello");
    }

    @Test
    void replace_ShouldReturnOriginal_WhenNewPatternIsNull() {
        // Given
        String input = "hello";
        String oldPattern = "l";
        String newPattern = null;

        // When
        String actual = StringUtils.replace(input, oldPattern, newPattern);

        // Then
        then(actual).isEqualTo("hello");
    }

    @Test
    void replace_ShouldReturnOriginal_WhenPatternNotFound() {
        // Given
        String input = "hello";
        String oldPattern = "world";
        String newPattern = "x";

        // When
        String actual = StringUtils.replace(input, oldPattern, newPattern);

        // Then
        then(actual).isEqualTo("hello");
    }

    @Test
    void replace_ShouldReplaceOnce_WhenPatternFoundOnce() {
        // Given
        String input = "hello";
        String oldPattern = "ll";
        String newPattern = "xx";

        // When
        String actual = StringUtils.replace(input, oldPattern, newPattern);

        // Then
        then(actual).isEqualTo("hexxo");
    }

    @Test
    void replace_ShouldReplaceMultiple_WhenPatternFoundMultipleTimes() {
        // Given
        String input = "ababab";
        String oldPattern = "ab";
        String newPattern = "xy";

        // When
        String actual = StringUtils.replace(input, oldPattern, newPattern);

        // Then
        then(actual).isEqualTo("xyxyxy");
    }

    @Test
    void replace_ShouldReplaceWithEmpty_WhenNewPatternIsEmpty() {
        // Given
        String input = "hello";
        String oldPattern = "l";
        String newPattern = EMPTY;

        // When
        String actual = StringUtils.replace(input, oldPattern, newPattern);

        // Then
        then(actual).isEqualTo("heo");
    }

    @Test
    void replace_ShouldReplaceAtStart_WhenPatternAtStart() {
        // Given
        String input = "hello";
        String oldPattern = "he";
        String newPattern = "xy";

        // When
        String actual = StringUtils.replace(input, oldPattern, newPattern);

        // Then
        then(actual).isEqualTo("xyllo");
    }

    @Test
    void replace_ShouldReplaceAtEnd_WhenPatternAtEnd() {
        // Given
        String input = "hello";
        String oldPattern = "lo";
        String newPattern = "xy";

        // When
        String actual = StringUtils.replace(input, oldPattern, newPattern);

        // Then
        then(actual).isEqualTo("helxy");
    }

    @Test
    void replace_ShouldReplaceEntireString_WhenOldPatternEqualsInput() {
        // Given
        String input = "hello";
        String oldPattern = "hello";
        String newPattern = "x";

        // When
        String actual = StringUtils.replace(input, oldPattern, newPattern);

        // Then
        then(actual).isEqualTo("x");
    }

    @Test
    void replace_ShouldReplaceWithLongerString_WhenNewPatternLonger() {
        // Given
        String input = "a";
        String oldPattern = "a";
        String newPattern = "abc";

        // When
        String actual = StringUtils.replace(input, oldPattern, newPattern);

        // Then
        then(actual).isEqualTo("abc");
    }

    @Test
    void replace_ShouldReplaceWithSameLength_WhenNewPatternSameLength() {
        // Given
        String input = "abc";
        String oldPattern = "ab";
        String newPattern = "xy";

        // When
        String actual = StringUtils.replace(input, oldPattern, newPattern);

        // Then
        then(actual).isEqualTo("xyc");
    }

    @Test
    void remove_ShouldReturnNull_WhenInputIsNull() {
        // Given
        String input = null;
        char remove = 'a';

        // When
        String actual = StringUtils.remove(input, remove);

        // Then
        then(actual).isNull();
    }

    @Test
    void remove_ShouldReturnEmpty_WhenInputIsEmpty() {
        // Given
        String input = EMPTY;
        char remove = 'a';

        // When
        String actual = StringUtils.remove(input, remove);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void remove_ShouldReturnOriginal_WhenCharacterNotPresent() {
        // Given
        String input = "hello";
        char remove = 'x';

        // When
        String actual = StringUtils.remove(input, remove);

        // Then
        then(actual).isEqualTo("hello");
    }

    @Test
    void remove_ShouldRemoveCharacterAtStart() {
        // Given
        String input = "hello";
        char remove = 'h';

        // When
        String actual = StringUtils.remove(input, remove);

        // Then
        then(actual).isEqualTo("ello");
    }

    @Test
    void remove_ShouldRemoveCharacterAtMiddle() {
        // Given
        String input = "hello";
        char remove = 'e';

        // When
        String actual = StringUtils.remove(input, remove);

        // Then
        then(actual).isEqualTo("hllo");
    }

    @Test
    void remove_ShouldRemoveCharacterAtEnd() {
        // Given
        String input = "hello";
        char remove = 'o';

        // When
        String actual = StringUtils.remove(input, remove);

        // Then
        then(actual).isEqualTo("hell");
    }

    @Test
    void remove_ShouldRemoveAllOccurrences() {
        // Given
        String input = "hello";
        char remove = 'l';

        // When
        String actual = StringUtils.remove(input, remove);

        // Then
        then(actual).isEqualTo("heo");
    }

    @Test
    void remove_ShouldRemoveAllSpaces() {
        // Given
        String input = "a b c";
        char remove = ' ';

        // When
        String actual = StringUtils.remove(input, remove);

        // Then
        then(actual).isEqualTo("abc");
    }

    @Test
    void remove_ShouldRemoveCharacterInMixedCase() {
        // Given
        String input = "Hello";
        char remove = 'l';

        // When
        String actual = StringUtils.remove(input, remove);

        // Then
        then(actual).isEqualTo("Heo");
    }

    @Test
    void remove_ShouldRemoveAllCharacters_WhenInputIsAllRemoveChar() {
        // Given
        String input = "aaaa";
        char remove = 'a';

        // When
        String actual = StringUtils.remove(input, remove);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void removeStart_ShouldReturnNull_WhenInputIsNull() {
        // Given
        String input = null;
        char remove = 'a';

        // When
        String actual = StringUtils.removeStart(input, remove);

        // Then
        then(actual).isNull();
    }

    @Test
    void removeStart_ShouldReturnEmpty_WhenInputIsEmpty() {
        // Given
        String input = EMPTY;
        char remove = 'a';

        // When
        String actual = StringUtils.removeStart(input, remove);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void removeStart_ShouldReturnEmpty_WhenInputIsSingleCharMatching() {
        // Given
        String input = "a";
        char remove = 'a';

        // When
        String actual = StringUtils.removeStart(input, remove);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void removeStart_ShouldReturnSubstring_WhenFirstCharMatches() {
        // Given
        String input = "hello";
        char remove = 'h';

        // When
        String actual = StringUtils.removeStart(input, remove);

        // Then
        then(actual).isEqualTo("ello");
    }

    @Test
    void removeStart_ShouldReturnOriginal_WhenFirstCharDoesNotMatch() {
        // Given
        String input = "hello";
        char remove = 'e';

        // When
        String actual = StringUtils.removeStart(input, remove);

        // Then
        then(actual).isEqualTo("hello");
    }

    @Test
    void removeStart_ShouldRemoveLeadingSpace_WhenInputStartsWithSpace() {
        // Given
        String input = " hello";
        char remove = ' ';

        // When
        String actual = StringUtils.removeStart(input, remove);

        // Then
        then(actual).isEqualTo("hello");
    }

    @Test
    void removeStart_ShouldReturnSubstring_WhenInputHasMultipleLeadingMatchingChars() {
        // Given
        String input = "aaab";
        char remove = 'a';

        // When
        String actual = StringUtils.removeStart(input, remove);

        // Then
        then(actual).isEqualTo("aab");
    }

    @Test
    void removeEnd_ShouldReturnNull_WhenInputIsNull() {
        // Given
        String input = null;
        char remove = 'a';

        // When
        String actual = StringUtils.removeEnd(input, remove);

        // Then
        then(actual).isNull();
    }

    @Test
    void removeEnd_ShouldReturnEmpty_WhenInputIsEmpty() {
        // Given
        String input = EMPTY;
        char remove = 'a';

        // When
        String actual = StringUtils.removeEnd(input, remove);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void removeEnd_ShouldReturnInput_WhenInputIsBlank() {
        // Given
        String input = "   ";
        char remove = ' ';

        // When
        String actual = StringUtils.removeEnd(input, remove);

        // Then
        then(actual).isEqualTo("   ");
    }

    @Test
    void removeEnd_ShouldReturnEmpty_WhenInputIsSingleCharMatching() {
        // Given
        String input = "a";
        char remove = 'a';

        // When
        String actual = StringUtils.removeEnd(input, remove);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void removeEnd_ShouldReturnSubstring_WhenLastCharMatches() {
        // Given
        String input = "hello";
        char remove = 'o';

        // When
        String actual = StringUtils.removeEnd(input, remove);

        // Then
        then(actual).isEqualTo("hell");
    }

    @Test
    void removeEnd_ShouldReturnOriginal_WhenLastCharDoesNotMatch() {
        // Given
        String input = "hello";
        char remove = 'x';

        // When
        String actual = StringUtils.removeEnd(input, remove);

        // Then
        then(actual).isEqualTo("hello");
    }

    @Test
    void removeEnd_ShouldReturnSubstring_WhenInputHasTrailingSpaces() {
        // Given
        String input = "a  ";
        char remove = ' ';

        // When
        String actual = StringUtils.removeEnd(input, remove);

        // Then
        then(actual).isEqualTo("a ");
    }

    @Test
    void removeEnd_ShouldReturnSubstring_WhenInputHasMultipleTrailingMatchingChars() {
        // Given
        String input = "aa";
        char remove = 'a';

        // When
        String actual = StringUtils.removeEnd(input, remove);

        // Then
        then(actual).isEqualTo("a");
    }

    @Test
    void removeEnd_ShouldReturnOriginal_WhenLastCharIsNotRemove() {
        // Given
        String input = "a a";
        char remove = ' ';

        // When
        String actual = StringUtils.removeEnd(input, remove);

        // Then
        then(actual).isEqualTo("a a");
    }

    @Test
    void removeEnd_ShouldReturnEmpty_WhenInputIsSingleSpace() {
        // Given
        String input = SPACE;
        char remove = ' ';

        // When
        String actual = StringUtils.removeEnd(input, remove);

        // Then
        then(actual).isEqualTo(SPACE);
    }

    @Test
    void reverse_ShouldReturnNull_WhenInputIsNull() {
        // Given
        String input = null;

        // When
        String actual = StringUtils.reverse(input);

        // Then
        then(actual).isNull();
    }

    @Test
    void reverse_ShouldReturnEmpty_WhenInputIsEmpty() {
        // Given
        String input = EMPTY;

        // When
        String actual = StringUtils.reverse(input);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void reverse_ShouldReturnWhitespace_WhenInputIsBlank() {
        // Given
        String input = "   ";
        // When
        String actual = StringUtils.reverse(input);
        // Then
        then(actual).isEqualTo("   ");
    }

    @Test
    void reverse_ShouldReturnReversed_WhenInputIsHello() {
        // Given
        String input = "hello";

        // When
        String actual = StringUtils.reverse(input);

        // Then
        then(actual).isEqualTo("olleh");
    }

    @Test
    void reverse_ShouldReturnReversed_WhenInputHasMixedCase() {
        // Given
        String input = "Hello";

        // When
        String actual = StringUtils.reverse(input);

        // Then
        then(actual).isEqualTo("olleH");
    }

    @Test
    void reverse_ShouldReturnReversed_WhenInputHasDigits() {
        // Given
        String input = "123";

        // When
        String actual = StringUtils.reverse(input);

        // Then
        then(actual).isEqualTo("321");
    }

    @Test
    void reverse_ShouldReturnReversed_WhenInputHasSpecialCharacters() {
        // Given
        String input = "a@b";

        // When
        String actual = StringUtils.reverse(input);

        // Then
        then(actual).isEqualTo("b@a");
    }

    @Test
    void reverse_ShouldReturnSame_WhenInputIsSingleCharacter() {
        // Given
        String input = "a";

        // When
        String actual = StringUtils.reverse(input);

        // Then
        then(actual).isEqualTo("a");
    }

    @Test
    void reverse_ShouldReturnReversed_WhenInputHasSpaceInMiddle() {
        // Given
        String input = "a b";

        // When
        String actual = StringUtils.reverse(input);

        // Then
        then(actual).isEqualTo("b a");
    }

    @Test
    void right_ShouldReturnNull_WhenInputIsNull() {
        // Given
        String input = null;
        int length = 5;

        // When
        String actual = StringUtils.right(input, length);

        // Then
        then(actual).isNull();
    }

    @Test
    void right_ShouldReturnEmpty_WhenInputIsEmpty() {
        // Given
        String input = EMPTY;
        int length = 5;

        // When
        String actual = StringUtils.right(input, length);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void right_ShouldReturnOriginal_WhenLengthGreaterThanInputLength() {
        // Given
        String input = "hello";
        int length = 10;

        // When
        String actual = StringUtils.right(input, length);

        // Then
        then(actual).isEqualTo("hello");
    }

    @Test
    void right_ShouldReturnOriginal_WhenLengthEqualsInputLength() {
        // Given
        String input = "hello";
        int length = 5;

        // When
        String actual = StringUtils.right(input, length);

        // Then
        then(actual).isEqualTo("hello");
    }

    @Test
    void right_ShouldReturnEmpty_WhenLengthIsZero() {
        // Given
        String input = "hello";
        int length = 0;

        // When
        String actual = StringUtils.right(input, length);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void right_ShouldReturnSubstring_WhenLengthLessThanInputLength() {
        // Given
        String input = "hello";
        int length = 3;

        // When
        String actual = StringUtils.right(input, length);

        // Then
        then(actual).isEqualTo("llo");
    }

    @Test
    void right_ShouldReturnSubstring_WhenInputHasTrailingWhitespace() {
        // Given
        String input = "hello   ";
        int length = 3;

        // When
        String actual = StringUtils.right(input, length);

        // Then
        then(actual).isEqualTo("   ");
    }

    @Test
    void right_ShouldReturnOriginal_WhenInputIsSingleCharacter() {
        // Given
        String input = "a";
        int length = 1;

        // When
        String actual = StringUtils.right(input, length);

        // Then
        then(actual).isEqualTo("a");
    }

    @Test
    void right_ShouldThrowException_WhenLengthIsNegative() {
        // Given
        String input = "test";
        int length = INDEX_NOT_FOUND;

        // Then
        thenThrownBy(() -> StringUtils.right(input, length))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Length must be positive");
    }

    @Test
    void split_ShouldReturnEmptyArray_WhenInputIsNull() {
        // Given
        String input = null;

        // When
        String[] actual = StringUtils.split(input);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void split_ShouldReturnEmptyArray_WhenInputIsEmpty() {
        // Given
        String input = EMPTY;

        // When
        String[] actual = StringUtils.split(input);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void split_ShouldReturnSingleElement_WhenInputHasNoSeparator() {
        // Given
        String input = "hello";

        // When
        String[] actual = StringUtils.split(input);

        // Then
        then(actual).hasSize(1);
        then(actual[0]).isEqualTo("hello");
    }

    @Test
    void split_ShouldSplitBySpace_WhenSeparatorIsSpace() {
        // Given
        String input = "a b";

        // When
        String[] actual = StringUtils.split(input, SPACE);

        // Then
        then(actual).hasSize(2);
        then(actual[0]).isEqualTo("a");
        then(actual[1]).isEqualTo("b");
    }

    @Test
    void split_ShouldSplitByComma_WhenSeparatorIsComma() {
        // Given
        String input = "a,b";

        // When
        String[] actual = StringUtils.split(input, COMMA);

        // Then
        then(actual).hasSize(2);
        then(actual[0]).isEqualTo("a");
        then(actual[1]).isEqualTo("b");
    }

    @Test
    void split_ShouldIncludeEmptyTokens_WhenConsecutiveSeparators() {
        // Given
        String input = "a,,b";

        // When
        String[] actual = StringUtils.split(input, COMMA);

        // Then
        then(actual).hasSize(2);
        then(actual[0]).isEqualTo("a");
        then(actual[1]).isEqualTo("b");
    }

    @Test
    void split_ShouldHandleMax_WhenMaxIsLessThanTokens() {
        // Given
        String input = "a,b,c";

        // When
        String[] actual = StringUtils.split(input, COMMA, 2);

        // Then
        then(actual).hasSize(2);
        then(actual[0]).isEqualTo("a");
        then(actual[1]).isEqualTo("b,c");
    }

    @Test
    void split_ShouldHandleMax_WhenMaxIsOneAndTokensExceed() {
        // Given
        String input = "a,b";

        // When
        String[] actual = StringUtils.split(input, COMMA, 1);

        // Then
        then(actual).hasSize(1);
        then(actual[0]).isEqualTo("a,b");
    }

    @Test
    void split_ShouldHandleMax_WhenMaxIsThreeAndTokensExceed() {
        // Given
        String input = "a,b,c,d,e";

        // When
        String[] actual = StringUtils.split(input, COMMA, 3);

        // Then
        then(actual).hasSize(3);
        then(actual[0]).isEqualTo("a");
        then(actual[1]).isEqualTo("b");
        then(actual[2]).isEqualTo("c,d,e");
    }

    @Test
    void split_ShouldHandleMax_WhenMaxIsEqualToTokens() {
        // Given
        String input = "a,b,c";

        // When
        String[] actual = StringUtils.split(input, COMMA, 3);

        // Then
        then(actual).hasSize(3);
        then(actual[0]).isEqualTo("a");
        then(actual[1]).isEqualTo("b");
        then(actual[2]).isEqualTo("c");
    }

    @Test
    void split_ShouldHandleMax_WhenMaxIsZero() {
        // Given
        String input = "a,b,c";

        // When
        String[] actual = StringUtils.split(input, COMMA, 0);

        // Then
        then(actual).hasSize(3);
        then(actual[0]).isEqualTo("a");
        then(actual[1]).isEqualTo("b");
        then(actual[2]).isEqualTo("c");
    }

    @Test
    void split_ShouldSplitByWhitespace_WhenSeparatorIsNull() {
        // Given
        String input = "a b";

        // When
        String[] actual = StringUtils.split(input, null);

        // Then
        then(actual).hasSize(2);
        then(actual[0]).isEqualTo("a");
        then(actual[1]).isEqualTo("b");
    }

    @Test
    void split_ShouldReturnOriginal_WhenMaxIsOne() {
        // Given
        String input = "a b c";

        // When
        String[] actual = StringUtils.split(input, SPACE, 1);

        // Then
        then(actual).hasSize(1);
        then(actual[0]).isEqualTo("a b c");
    }

    @Test
    void split_ShouldReturnEmptyArray_WhenInputIsAllSpaces() {
        // Given
        String input = "   ";

        // When
        String[] actual = StringUtils.split(input, SPACE);

        // Then
        then(actual).hasSize(0);
        then(actual).isEmpty();
    }

    @Test
    void startsWithIgnoreCase_ShouldReturnFalse_WhenStringIsNull() {
        // Given
        String string = null;
        String prefix = "hello";

        // When
        boolean actual = StringUtils.startsWithIgnoreCase(string, prefix);

        // Then
        then(actual).isFalse();
    }

    @Test
    void startsWithIgnoreCase_ShouldReturnFalse_WhenPrefixIsNull() {
        // Given
        String string = "hello";
        String prefix = null;

        // When
        boolean actual = StringUtils.startsWithIgnoreCase(string, prefix);

        // Then
        then(actual).isFalse();
    }

    @Test
    void startsWithIgnoreCase_ShouldReturnFalse_WhenStringIsEmpty() {
        // Given
        String string = EMPTY;
        String prefix = "hello";

        // When
        boolean actual = StringUtils.startsWithIgnoreCase(string, prefix);

        // Then
        then(actual).isFalse();
    }

    @Test
    void startsWithIgnoreCase_ShouldReturnFalse_WhenPrefixIsEmpty() {
        // Given
        String string = "hello";
        String prefix = EMPTY;

        // When
        boolean actual = StringUtils.startsWithIgnoreCase(string, prefix);

        // Then
        then(actual).isFalse();
    }

    @Test
    void startsWithIgnoreCase_ShouldReturnFalse_WhenBothAreEmpty() {
        // Given
        String string = EMPTY;
        String prefix = EMPTY;

        // When
        boolean actual = StringUtils.startsWithIgnoreCase(string, prefix);

        // Then
        then(actual).isFalse();
    }

    @Test
    void startsWithIgnoreCase_ShouldReturnTrue_WhenStringEqualsPrefixIgnoreCase() {
        // Given
        String string = "HELLO";
        String prefix = "hello";

        // When
        boolean actual = StringUtils.startsWithIgnoreCase(string, prefix);

        // Then
        then(actual).isTrue();
    }

    @Test
    void startsWithIgnoreCase_ShouldReturnFalse_WhenStringLengthLessThanPrefixLength() {
        // Given
        String string = "a";
        String prefix = "ab";

        // When
        boolean actual = StringUtils.startsWithIgnoreCase(string, prefix);

        // Then
        then(actual).isFalse();
    }

    @Test
    void startsWithIgnoreCase_ShouldReturnFalse_WhenStringDoesNotStartWithPrefix() {
        // Given
        String string = "Hello";
        String prefix = "world";

        // When
        boolean actual = StringUtils.startsWithIgnoreCase(string, prefix);

        // Then
        then(actual).isFalse();
    }

    @Test
    void startsWithIgnoreCase_ShouldReturnFalse_WhenPrefixDoesNotMatchCaseInsensitive() {
        // Given
        String string = "Hello";
        String prefix = "hellx";

        // When
        boolean actual = StringUtils.startsWithIgnoreCase(string, prefix);

        // Then
        then(actual).isFalse();
    }

    @Test
    void startsWithIgnoreCase_ShouldReturnTrue_WhenStringStartsWithPrefixIgnoreCase() {
        // Given
        String string = "Hello";
        String prefix = "hello";

        // When
        boolean actual = StringUtils.startsWithIgnoreCase(string, prefix);

        // Then
        then(actual).isTrue();
    }

    @Test
    void startsWithIgnoreCase_ShouldReturnTrue_WhenStringStartsWithPrefixWithDifferentCase() {
        // Given
        String string = "HELLO";
        String prefix = "hello";

        // When
        boolean actual = StringUtils.startsWithIgnoreCase(string, prefix);

        // Then
        then(actual).isTrue();
    }

    @Test
    void startsWithIgnoreCase_ShouldReturnTrue_WhenStringStartsWithPrefixWithExtraCharacters() {
        // Given
        String string = "HelloWorld";
        String prefix = "Hello";

        // When
        boolean actual = StringUtils.startsWithIgnoreCase(string, prefix);

        // Then
        then(actual).isTrue();
    }

    @Test
    void strip_ShouldReturnNull_WhenInputIsNull() {
        // Given
        String input = null;

        // When
        String actual = StringUtils.strip(input);

        // Then
        then(actual).isNull();
    }

    @Test
    void strip_ShouldReturnEmpty_WhenInputIsEmpty() {
        // Given
        String input = EMPTY;

        // When
        String actual = StringUtils.strip(input);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void strip_ShouldReturnTrimmed_WhenInputHasWhitespace() {
        // Given
        String input = "   hello   ";

        // When
        String actual = StringUtils.strip(input);

        // Then
        then(actual).isEqualTo("hello");
    }

    @Test
    void strip_ShouldReturnTrimmed_WhenInputHasNonBreakingSpace() {
        // Given
        String input = "\u00A0hello\u00A0";

        // When
        String actual = StringUtils.strip(input);

        // Then
        then(actual).isEqualTo("hello");
    }

    @Test
    void strip_ShouldReturnOriginal_WhenInputHasNoWhitespace() {
        // Given
        String input = "hello";

        // When
        String actual = StringUtils.strip(input);

        // Then
        then(actual).isEqualTo("hello");
    }

    @Test
    void stripStart_ShouldReturnNull_WhenInputIsNull() {
        // Given
        String input = null;
        String stripChars = "a";

        // When
        String actual = StringUtils.stripStart(input, stripChars);

        // Then
        then(actual).isNull();
    }

    @Test
    void stripStart_ShouldReturnEmpty_WhenInputIsEmpty() {
        // Given
        String input = EMPTY;
        String stripChars = "a";

        // When
        String actual = StringUtils.stripStart(input, stripChars);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void stripStart_ShouldStripWhitespace_WhenStripCharsIsNull() {
        // Given
        String input = "   hello";

        // When
        String actual = StringUtils.stripStart(input, null);

        // Then
        then(actual).isEqualTo("hello");
    }

    @Test
    void stripStart_ShouldStripNonBreakingSpace_WhenStripCharsIsNull() {
        // Given
        String input = "\u00A0hello";

        // When
        String actual = StringUtils.stripStart(input, null);

        // Then
        then(actual).isEqualTo("hello");
    }

    @Test
    void stripStart_ShouldStripMultipleWhitespace_WhenStripCharsIsNull() {
        // Given
        String input = " \t\nhello";

        // When
        String actual = StringUtils.stripStart(input, null);

        // Then
        then(actual).isEqualTo("hello");
    }

    @Test
    void stripStart_ShouldStripMixedWhitespace_WhenStripCharsIsNull() {
        // Given
        String input = " \t\u00A0hello";

        // When
        String actual = StringUtils.stripStart(input, null);

        // Then
        then(actual).isEqualTo("hello");
    }

    @Test
    void stripStart_ShouldStripCharacters_WhenStripCharsIsProvided() {
        // Given
        String input = "aabc";
        String stripChars = "a";

        // When
        String actual = StringUtils.stripStart(input, stripChars);

        // Then
        then(actual).isEqualTo("bc");
    }

    @Test
    void stripStart_ShouldStripAllMatchingCharacters_WhenInputIsAllMatching() {
        // Given
        String input = "aaa";
        String stripChars = "a";

        // When
        String actual = StringUtils.stripStart(input, stripChars);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void stripStart_ShouldNotStripWhenNoMatch_WhenStripCharsIsProvided() {
        // Given
        String input = "babc";
        String stripChars = "a";

        // When
        String actual = StringUtils.stripStart(input, stripChars);

        // Then
        then(actual).isEqualTo("babc");
    }

    @Test
    void stripStart_ShouldHandleNonBreakingSpaceInStripChars() {
        // Given
        String input = "\u00A0abc";
        String stripChars = "\u00A0";

        // When
        String actual = StringUtils.stripStart(input, stripChars);

        // Then
        then(actual).isEqualTo("abc");
    }

    @Test
    void stripStart_ShouldHandleMultipleCharactersInStripChars() {
        // Given
        String input = "a\u00A0abc";
        String stripChars = "a\u00A0";

        // When
        String actual = StringUtils.stripStart(input, stripChars);

        // Then
        then(actual).isEqualTo("bc");
    }

    @Test
    void stripStart_ShouldHandleEmptyStripChars() {
        // Given
        String input = "   hello";
        String stripChars = EMPTY;

        // When
        String actual = StringUtils.stripStart(input, stripChars);

        // Then
        then(actual).isEqualTo("hello");
    }

    @Test
    void stripStart_ShouldNotStripNonBreakingSpace_WhenNotInStripChars() {
        // Given
        String input = "\u00A0hello";
        String stripChars = "a";

        // When
        String actual = StringUtils.stripStart(input, stripChars);

        // Then
        then(actual).isEqualTo("\u00A0hello");
    }

    @Test
    void stripStart_ShouldStripLeadingSpaceAndNonBreakingSpace() {
        // Given
        String input = " \u00A0hello";

        // When
        String actual = StringUtils.stripStart(input, null);

        // Then
        then(actual).isEqualTo("hello");
    }

    @Test
    void stripStart_ShouldReturnOriginal_WhenNoCharactersToStrip() {
        // Given
        String input = "hello";
        String stripChars = "a";

        // When
        String actual = StringUtils.stripStart(input, stripChars);

        // Then
        then(actual).isEqualTo("hello");
    }

    @Test
    void stripStart_ShouldStripOnlyLeadingCharacters() {
        // Given
        String input = "aabb";
        String stripChars = "a";

        // When
        String actual = StringUtils.stripStart(input, stripChars);

        // Then
        then(actual).isEqualTo("bb");
    }

    @Test
    void stripStart_ShouldReturnEmpty_WhenInputIsNonBreakingSpaceAndStripCharsIsNull() {
        // Given
        String input = "\u00A0";

        // When
        String actual = StringUtils.stripStart(input, null);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void stripStart_ShouldReturnEmpty_WhenInputIsAllNonBreakingSpacesAndStripCharsIsNull() {
        // Given
        String input = "\u00A0\u00A0\u00A0";

        // When
        String actual = StringUtils.stripStart(input, null);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void stripStart_ShouldReturnEmpty_WhenInputStartsWithNonBreakingSpaceAndWhitespaceAndStripCharsIsNull() {
        // Given
        String input = "\u00A0 \t\n";

        // When
        String actual = StringUtils.stripStart(input, null);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void stripStart_ShouldReturnOriginal_WhenInputStartsWithNonBreakingSpaceAndStripCharsContainsIt() {
        // Given
        String input = "\u00A0hello";
        String stripChars = "\u00A0";

        // When
        String actual = StringUtils.stripStart(input, stripChars);

        // Then
        then(actual).isEqualTo("hello");
    }

    @Test
    void stripStart_ShouldReturnOriginal_WhenInputStartsWithNonBreakingSpaceAndStripCharsDoesNotContainIt() {
        // Given
        String input = "\u00A0hello";
        String stripChars = "a";

        // When
        String actual = StringUtils.stripStart(input, stripChars);

        // Then
        then(actual).isEqualTo("\u00A0hello");
    }

    @Test
    void stripStart_ShouldHandleNonBreakingSpaceInStripCharsWithMultipleCharacters() {
        // Given
        String input = "\u00A0a\u00A0b";
        String stripChars = "\u00A0a";

        // When
        String actual = StringUtils.stripStart(input, stripChars);

        // Then
        then(actual).isEqualTo("b");
    }

    @Test
    void stripEnd_ShouldReturnNull_WhenInputIsNull() {
        // Given
        String input = null;
        String stripChars = "a";

        // When
        String actual = StringUtils.stripEnd(input, stripChars);

        // Then
        then(actual).isNull();
    }

    @Test
    void stripEnd_ShouldReturnEmpty_WhenInputIsEmpty() {
        // Given
        String input = EMPTY;
        String stripChars = "a";

        // When
        String actual = StringUtils.stripEnd(input, stripChars);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void stripEnd_ShouldStripWhitespace_WhenStripCharsIsNull() {
        // Given
        String input = "hello   ";

        // When
        String actual = StringUtils.stripEnd(input, null);

        // Then
        then(actual).isEqualTo("hello");
    }

    @Test
    void stripEnd_ShouldStripNonBreakingSpace_WhenStripCharsIsNull() {
        // Given
        String input = "hello\u00A0";

        // When
        String actual = StringUtils.stripEnd(input, null);

        // Then
        then(actual).isEqualTo("hello");
    }

    @Test
    void stripEnd_ShouldStripWhitespaceAndNonBreakingSpace_WhenStripCharsIsNull() {
        // Given
        String input = "a \u00A0";

        // When
        String actual = StringUtils.stripEnd(input, null);

        // Then
        then(actual).isEqualTo("a");
    }

    @Test
    void stripEnd_ShouldReturnOriginal_WhenNoTrailingWhitespace_WhenStripCharsIsNull() {
        // Given
        String input = "hello";

        // When
        String actual = StringUtils.stripEnd(input, null);

        // Then
        then(actual).isEqualTo("hello");
    }

    @Test
    void stripEnd_ShouldNotStripNonBreakingSpaceWhenStripCharsDoesNotContainIt() {
        // Given
        String input = "ab\u00A0";
        String stripChars = "a";

        // When
        String actual = StringUtils.stripEnd(input, stripChars);

        // Then
        then(actual).isEqualTo("ab\u00A0");
    }

    @Test
    void stripEnd_ShouldHandleEmptyStripChars() {
        // Given
        String input = "   ";
        String stripChars = EMPTY;

        // When
        String actual = StringUtils.stripEnd(input, stripChars);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void stripEnd_ShouldReturnOriginal_WhenNoCharactersToStrip() {
        // Given
        String input = "hello";
        String stripChars = "a";

        // When
        String actual = StringUtils.stripEnd(input, stripChars);

        // Then
        then(actual).isEqualTo("hello");
    }

    @Test
    void stripEnd_ShouldHandleNonBreakingSpaceWithMultipleCharacters() {
        // Given
        String input = "a\u00A0b\u00A0";
        String stripChars = "\u00A0";

        // When
        String actual = StringUtils.stripEnd(input, stripChars);

        // Then
        then(actual).isEqualTo("a\u00A0b");
    }

    @Test
    void stripEnd_ShouldStripNonBreakingSpace_WhenStripCharsContainsIt() {
        // Given
        String input = "a\u00A0";
        String stripChars = "\u00A0";

        // When
        String actual = StringUtils.stripEnd(input, stripChars);

        // Then
        then(actual).isEqualTo("a");
    }

    @Test
    void stripEnd_ShouldStripNonBreakingSpace_WhenLengthLessThanCharsSize() {
        String input = "a\u00A0";
        String stripChars = "abcde\u00A0";

        String actual = StringUtils.stripEnd(input, stripChars);

        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void substringMatch_ShouldReturnFalse_WhenStringIsNull() {
        // Given
        String string = null;
        int index = 0;
        String substring = "he";

        // When
        boolean actual = StringUtils.substringMatch(string, index, substring);

        // Then
        then(actual).isFalse();
    }

    @Test
    void substringMatch_ShouldReturnFalse_WhenStringIsEmpty() {
        // Given
        String string = EMPTY;
        int index = 0;
        String substring = "he";

        // When
        boolean actual = StringUtils.substringMatch(string, index, substring);

        // Then
        then(actual).isFalse();
    }

    @Test
    void substringMatch_ShouldReturnFalse_WhenSubstringIsNull() {
        // Given
        String string = "hello";
        int index = 0;
        String substring = null;

        // When
        boolean actual = StringUtils.substringMatch(string, index, substring);

        // Then
        then(actual).isFalse();
    }

    @Test
    void substringMatch_ShouldReturnFalse_WhenSubstringIsEmpty() {
        // Given
        String string = "hello";
        int index = 0;
        String substring = EMPTY;

        // When
        boolean actual = StringUtils.substringMatch(string, index, substring);

        // Then
        then(actual).isFalse();
    }

    @Test
    void substringMatch_ShouldReturnFalse_WhenSubstringExceedsLength() {
        // Given
        String string = "abc";
        int index = 2;
        String substring = "de";

        // When
        boolean actual = StringUtils.substringMatch(string, index, substring);

        // Then
        then(actual).isFalse();
    }

    @Test
    void substringMatch_ShouldReturnTrue_WhenSubstringAtEnd() {
        // Given
        String string = "abc";
        int index = 1;
        String substring = "bc";

        // When
        boolean actual = StringUtils.substringMatch(string, index, substring);

        // Then
        then(actual).isTrue();
    }

    @Test
    void substringMatch_ShouldReturnTrue_WhenSubstringInMiddle() {
        // Given
        String string = "abcde";
        int index = 0;
        String substring = "ab";

        // When
        boolean actual = StringUtils.substringMatch(string, index, substring);

        // Then
        then(actual).isTrue();
    }

    @Test
    void substringMatch_ShouldReturnTrue_WhenSubstringMatchesEntireString() {
        // Given
        String string = "abc";
        int index = 0;
        String substring = "abc";

        // When
        boolean actual = StringUtils.substringMatch(string, index, substring);

        // Then
        then(actual).isTrue();
    }

    @Test
    void substringMatch_ShouldReturnFalse_WhenFirstCharacterMismatch() {
        // Given
        String string = "abc";
        int index = 0;
        String substring = "xbc";

        // When
        boolean actual = StringUtils.substringMatch(string, index, substring);

        // Then
        then(actual).isFalse();
    }

    @Test
    void substringMatch_ShouldReturnFalse_WhenMiddleCharacterMismatch() {
        // Given
        String string = "abc";
        int index = 0;
        String substring = "axc";

        // When
        boolean actual = StringUtils.substringMatch(string, index, substring);

        // Then
        then(actual).isFalse();
    }

    @Test
    void substringMatch_ShouldReturnFalse_WhenLastCharacterMismatch() {
        // Given
        String string = "abc";
        int index = 0;
        String substring = "abx";

        // When
        boolean actual = StringUtils.substringMatch(string, index, substring);

        // Then
        then(actual).isFalse();
    }

    @Test
    void substringMatch_ShouldReturnFalse_WhenIndexIsZeroAndSubstringIsLonger() {
        // Given
        String string = "a";
        int index = 0;
        String substring = "ab";

        // When
        boolean actual = StringUtils.substringMatch(string, index, substring);

        // Then
        then(actual).isFalse();
    }

    @Test
    void substringMatch_ShouldReturnTrue_WhenIndexIsZeroAndSubstringMatches() {
        // Given
        String string = "hello";
        int index = 0;
        String substring = "he";

        // When
        boolean actual = StringUtils.substringMatch(string, index, substring);

        // Then
        then(actual).isTrue();
    }

    @Test
    void substringMatch_ShouldThrowException_WhenIndexIsNegative() {
        // Given
        String string = "hello";
        int index = INDEX_NOT_FOUND;
        String substring = "he";

        // Then
        thenThrownBy(() -> StringUtils.substringMatch(string, index, substring))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Index must be positive");
    }

    @Test
    void substring_ShouldReturnEmpty_WhenInputIsNull() {
        // Given
        String input = null;
        int start = 0;

        // When
        String actual = StringUtils.substring(input, start);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void substring_ShouldReturnEmpty_WhenInputIsEmpty() {
        // Given
        String input = "";
        int start = 0;

        // When
        String actual = StringUtils.substring(input, start);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void substring_ShouldReturnSubstring_WhenStartIsPositiveAndValid() {
        // Given
        String input = "hello";
        int start = 1;

        // When
        String actual = StringUtils.substring(input, start);

        // Then
        then(actual).isEqualTo("ello");
    }

    @Test
    void substring_ShouldReturnSubstring_WhenStartIsNegativeAndValid() {
        // Given
        String input = "hello";
        int start = -1;

        // When
        String actual = StringUtils.substring(input, start);

        // Then
        then(actual).isEqualTo("o");
    }

    @Test
    void substring_ShouldReturnFullString_WhenStartIsNegativeAndAdjustsToZero() {
        // Given
        String input = "hello";
        int start = -5;

        // When
        String actual = StringUtils.substring(input, start);

        // Then
        then(actual).isEqualTo("hello");
    }

    @Test
    void substring_ShouldReturnOriginalInputString_WhenStartIsNegativeAndAdjustsToZero() {
        // Given
        String input = "hello";
        int start = -6;

        // When
        String actual = StringUtils.substring(input, start);

        // Then
        then(actual).isEqualTo("hello");
    }

    @Test
    void substring_ShouldReturnEmpty_WhenStartEqualsLength() {
        // Given
        String input = "hello";
        int start = 5;

        // When
        String actual = StringUtils.substring(input, start);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void substring_ShouldReturnEmpty_WhenStartExceedsLength() {
        // Given
        String input = "hello";
        int start = 6;

        // When
        String actual = StringUtils.substring(input, start);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void substring_ShouldReturnFullString_WhenStartIsZero() {
        // Given
        String input = "hello";
        int start = 0;

        // When
        String actual = StringUtils.substring(input, start);

        // Then
        then(actual).isEqualTo("hello");
    }

    @Test
    void substring_ShouldReturnSubstring_WhenStartIsNegativeAndAdjustsToValid() {
        // Given
        String input = "abcdef";
        int start = -2;

        // When
        String actual = StringUtils.substring(input, start);

        // Then
        then(actual).isEqualTo("ef");
    }

    @Test
    void substringAfter_ShouldReturnEmpty_WhenInputIsNull() {
        // Given
        String input = null;
        String separator = "a";

        // When
        String actual = StringUtils.substringAfter(input, separator);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void substringAfter_ShouldReturnEmpty_WhenInputIsEmpty() {
        // Given
        String input = EMPTY;
        String separator = "a";

        // When
        String actual = StringUtils.substringAfter(input, separator);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void substringAfter_ShouldReturnOriginal_WhenSeparatorIsNull() {
        // Given
        String input = "hello";
        String separator = null;

        // When
        String actual = StringUtils.substringAfter(input, separator);

        // Then
        then(actual).isEqualTo("hello");
    }

    @Test
    void substringAfter_ShouldReturnOriginal_WhenSeparatorIsEmpty() {
        // Given
        String input = "hello";
        String separator = EMPTY;

        // When
        String actual = StringUtils.substringAfter(input, separator);

        // Then
        then(actual).isEqualTo("hello");
    }

    @Test
    void substringAfter_ShouldReturnEmpty_WhenSeparatorNotFound() {
        // Given
        String input = "hello";
        String separator = "world";

        // When
        String actual = StringUtils.substringAfter(input, separator);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void substringAfter_ShouldReturnSubstring_WhenSeparatorAtStart() {
        // Given
        String input = "hello";
        String separator = "he";

        // When
        String actual = StringUtils.substringAfter(input, separator);

        // Then
        then(actual).isEqualTo("llo");
    }

    @Test
    void substringAfter_ShouldReturnSubstring_WhenSeparatorInMiddle() {
        // Given
        String input = "hello";
        String separator = "ll";

        // When
        String actual = StringUtils.substringAfter(input, separator);

        // Then
        then(actual).isEqualTo("o");
    }

    @Test
    void substringAfter_ShouldReturnEmpty_WhenSeparatorAtEnd() {
        // Given
        String input = "hello";
        String separator = "hello";

        // When
        String actual = StringUtils.substringAfter(input, separator);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void substringAfter_ShouldReturnSubstringAfterFirstOccurrence_WhenMultipleOccurrences() {
        // Given
        String input = "abab";
        String separator = "ab";

        // When
        String actual = StringUtils.substringAfter(input, separator);

        // Then
        then(actual).isEqualTo("ab");
    }

    @Test
    void substringAfter_ShouldReturnSubstring_WhenSeparatorIsSingleCharacter() {
        // Given
        String input = "hello";
        String separator = "l";

        // When
        String actual = StringUtils.substringAfter(input, separator);

        // Then
        then(actual).isEqualTo("lo");
    }

    @Test
    void substringAfter_ShouldReturnEmpty_WhenSeparatorLongerThanInput() {
        // Given
        String input = "a";
        String separator = "ab";

        // When
        String actual = StringUtils.substringAfter(input, separator);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void substringAfter_ShouldReturnEmpty_WhenSeparatorIsAtEndWithTrailingSpace() {
        // Given
        String input = "hello ";
        String separator = "hello";

        // When
        String actual = StringUtils.substringAfter(input, separator);

        // Then
        then(actual).isEqualTo(" ");
    }

    @Test
    void substringAfter_ShouldReturnSubstring_WhenSeparatorIsInMiddleWithSpace() {
        // Given
        String input = "hello world";
        String separator = "lo";

        // When
        String actual = StringUtils.substringAfter(input, separator);

        // Then
        then(actual).isEqualTo(" world");
    }

    @Test
    void substringBefore_ShouldReturnEmpty_WhenInputIsNull() {
        // Given
        String input = null;
        String separator = "a";

        // When
        String actual = StringUtils.substringBefore(input, separator);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void substringBefore_ShouldReturnEmpty_WhenInputIsEmpty() {
        // Given
        String input = EMPTY;
        String separator = "a";

        // When
        String actual = StringUtils.substringBefore(input, separator);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void substringBefore_ShouldReturnOriginal_WhenSeparatorIsNull() {
        // Given
        String input = "hello";
        String separator = null;

        // When
        String actual = StringUtils.substringBefore(input, separator);

        // Then
        then(actual).isEqualTo("hello");
    }

    @Test
    void substringBefore_ShouldReturnOriginal_WhenSeparatorIsEmpty() {
        // Given
        String input = "hello";
        String separator = EMPTY;

        // When
        String actual = StringUtils.substringBefore(input, separator);

        // Then
        then(actual).isEqualTo("hello");
    }

    @Test
    void substringBefore_ShouldReturnEmpty_WhenSeparatorNotFound() {
        // Given
        String input = "hello";
        String separator = "world";

        // When
        String actual = StringUtils.substringBefore(input, separator);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void substringBefore_ShouldReturnEmpty_WhenSeparatorAtStart() {
        // Given
        String input = "hello";
        String separator = "he";

        // When
        String actual = StringUtils.substringBefore(input, separator);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void substringBefore_ShouldReturnSubstring_WhenSeparatorInMiddle() {
        // Given
        String input = "hello";
        String separator = "ll";

        // When
        String actual = StringUtils.substringBefore(input, separator);

        // Then
        then(actual).isEqualTo("he");
    }

    @Test
    void substringBefore_ShouldReturnSubstring_WhenSeparatorAtEnd() {
        // Given
        String input = "hello";
        String separator = "hello";

        // When
        String actual = StringUtils.substringBefore(input, separator);

        // Then
        then(actual).isEqualTo("");
    }

    @Test
    void substringBefore_ShouldReturnSubstringBeforeFirstOccurrence_WhenMultipleOccurrences() {
        // Given
        String input = "abab";
        String separator = "ab";

        // When
        String actual = StringUtils.substringBefore(input, separator);

        // Then
        then(actual).isEqualTo("");
    }

    @Test
    void substringBefore_ShouldReturnSubstring_WhenSeparatorIsSingleCharacter() {
        // Given
        String input = "hello";
        String separator = "l";

        // When
        String actual = StringUtils.substringBefore(input, separator);

        // Then
        then(actual).isEqualTo("he");
    }

    @Test
    void substringBefore_ShouldReturnEmpty_WhenSeparatorLongerThanInput() {
        // Given
        String input = "a";
        String separator = "ab";

        // When
        String actual = StringUtils.substringBefore(input, separator);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void substringBefore_ShouldReturnSubstring_WhenSeparatorInMiddleWithSpace() {
        // Given
        String input = "hello world";
        String separator = "world";

        // When
        String actual = StringUtils.substringBefore(input, separator);

        // Then
        then(actual).isEqualTo("hello ");
    }

    @Test
    void substringBefore_ShouldReturnSubstring_WhenSeparatorIsSpace() {
        // Given
        String input = "a b";
        String separator = SPACE;

        // When
        String actual = StringUtils.substringBefore(input, separator);

        // Then
        then(actual).isEqualTo("a");
    }

    @Test
    void substringBefore_ShouldReturnSubstring_WhenSeparatorIsMultipleSpaces() {
        // Given
        String input = "a  b";
        String separator = "  ";

        // When
        String actual = StringUtils.substringBefore(input, separator);

        // Then
        then(actual).isEqualTo("a");
    }

    @Test
    void substringBefore_ShouldReturnEmpty_WhenSeparatorIsAtEndWithTrailingSpace() {
        // Given
        String input = "hello ";
        String separator = "hello";

        // When
        String actual = StringUtils.substringBefore(input, separator);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void substringBetween_ShouldReturnEmpty_WhenInputIsNull() {
        // Given
        String input = null;
        String open = "a";
        String close = "b";

        // When
        String actual = StringUtils.substringBetween(input, open, close);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void substringBetween_ShouldReturnEmpty_WhenInputIsEmpty() {
        // Given
        String input = EMPTY;
        String open = "a";
        String close = "b";

        // When
        String actual = StringUtils.substringBetween(input, open, close);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void substringBetween_ShouldReturnOriginal_WhenOpenIsNull() {
        // Given
        String input = "hello";
        String open = null;
        String close = "l";

        // When
        String actual = StringUtils.substringBetween(input, open, close);

        // Then
        then(actual).isEqualTo("hello");
    }

    @Test
    void substringBetween_ShouldReturnOriginal_WhenCloseIsNull() {
        // Given
        String input = "hello";
        String open = "he";
        String close = null;

        // When
        String actual = StringUtils.substringBetween(input, open, close);

        // Then
        then(actual).isEqualTo("hello");
    }

    @Test
    void substringBetween_ShouldReturnOriginal_WhenOpenIsEmpty() {
        // Given
        String input = "hello";
        String open = EMPTY;
        String close = "l";

        // When
        String actual = StringUtils.substringBetween(input, open, close);

        // Then
        then(actual).isEqualTo("hello");
    }

    @Test
    void substringBetween_ShouldReturnOriginal_WhenCloseIsEmpty() {
        // Given
        String input = "hello";
        String open = "he";
        String close = EMPTY;

        // When
        String actual = StringUtils.substringBetween(input, open, close);

        // Then
        then(actual).isEqualTo("hello");
    }

    @Test
    void substringBetween_ShouldReturnOriginal_WhenOpenAndCloseEmpty() {
        // Given
        String input = "hello";
        String open = EMPTY;
        String close = EMPTY;

        // When
        String actual = StringUtils.substringBetween(input, open, close);

        // Then
        then(actual).isEqualTo("hello");
    }

    @Test
    void substringBetween_ShouldReturnEmpty_WhenOpenNotFound() {
        // Given
        String input = "hello";
        String open = "world";
        String close = "l";

        // When
        String actual = StringUtils.substringBetween(input, open, close);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void substringBetween_ShouldReturnEmpty_WhenCloseNotFound() {
        // Given
        String input = "hello";
        String open = "he";
        String close = "world";

        // When
        String actual = StringUtils.substringBetween(input, open, close);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void substringBetween_ShouldReturnSubstring_WhenOpenAndCloseFound() {
        // Given
        String input = "aXb";
        String open = "a";
        String close = "b";

        // When
        String actual = StringUtils.substringBetween(input, open, close);

        // Then
        then(actual).isEqualTo("X");
    }

    @Test
    void substringBetween_ShouldReturnSubstring_WhenMultipleOccurrences() {
        // Given
        String input = "aXbYa";
        String open = "a";
        String close = "b";

        // When
        String actual = StringUtils.substringBetween(input, open, close);

        // Then
        then(actual).isEqualTo("X");
    }

    @Test
    void substringBetween_ShouldReturnSubstring_WhenOpenAndCloseOverlap() {
        // Given
        String input = "aXaYb";
        String open = "a";
        String close = "b";

        // When
        String actual = StringUtils.substringBetween(input, open, close);

        // Then
        then(actual).isEqualTo("XaY");
    }

    @Test
    void substringBetween_ShouldReturnEmpty_WhenCloseBeforeOpen() {
        // Given
        String input = "bXa";
        String open = "a";
        String close = "b";

        // When
        String actual = StringUtils.substringBetween(input, open, close);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void substringBetween_ShouldReturnSubstring_WhenOpenAndCloseSame() {
        // Given
        String input = "aXa";
        String open = "a";
        String close = "a";

        // When
        String actual = StringUtils.substringBetween(input, open, close);

        // Then
        then(actual).isEqualTo("X");
    }

    @Test
    void substringBetween_ShouldReturnEmpty_WhenOpenAndCloseAdjacent() {
        // Given
        String input = "ab";
        String open = "a";
        String close = "b";

        // When
        String actual = StringUtils.substringBetween(input, open, close);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void substringBetween_ShouldReturnEmpty_WhenOpenAtEnd() {
        // Given
        String input = "hello";
        String open = "hello";
        String close = "world";

        // When
        String actual = StringUtils.substringBetween(input, open, close);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void substringBetween_ShouldReturnSubstring_WhenOpenAndCloseWithSpaces() {
        // Given
        String input = "a X b";
        String open = "a";
        String close = "b";

        // When
        String actual = StringUtils.substringBetween(input, open, close);

        // Then
        then(actual).isEqualTo(" X ");
    }

    @Test
    void substringsBetween_ShouldReturnEmptyArray_WhenInputIsNull() {
        // Given
        String input = null;
        String open = "a";
        String close = "b";

        // When
        String[] actual = StringUtils.substringsBetween(input, open, close);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void substringsBetween_ShouldReturnEmptyArray_WhenInputIsEmpty() {
        // Given
        String input = EMPTY;
        String open = "a";
        String close = "b";

        // When
        String[] actual = StringUtils.substringsBetween(input, open, close);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void substringsBetween_ShouldReturnEmptyArray_WhenOpenIsNull() {
        // Given
        String input = "hello";
        String open = null;
        String close = "l";

        // When
        String[] actual = StringUtils.substringsBetween(input, open, close);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void substringsBetween_ShouldReturnEmptyArray_WhenCloseIsNull() {
        // Given
        String input = "hello";
        String open = "he";
        String close = null;

        // When
        String[] actual = StringUtils.substringsBetween(input, open, close);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void substringsBetween_ShouldReturnEmptyArray_WhenOpenIsEmpty() {
        // Given
        String input = "hello";
        String open = EMPTY;
        String close = "l";

        // When
        String[] actual = StringUtils.substringsBetween(input, open, close);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void substringsBetween_ShouldReturnEmptyArray_WhenCloseIsEmpty() {
        // Given
        String input = "hello";
        String open = "he";
        String close = EMPTY;

        // When
        String[] actual = StringUtils.substringsBetween(input, open, close);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void substringsBetween_ShouldReturnEmptyArray_WhenOpenNotFound() {
        // Given
        String input = "hello";
        String open = "world";
        String close = "l";

        // When
        String[] actual = StringUtils.substringsBetween(input, open, close);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void substringsBetween_ShouldReturnEmptyArray_WhenCloseNotFound() {
        // Given
        String input = "hello";
        String open = "he";
        String close = "world";

        // When
        String[] actual = StringUtils.substringsBetween(input, open, close);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void substringsBetween_ShouldReturnSingleSubstring_WhenSingleOccurrence() {
        // Given
        String input = "aXb";
        String open = "a";
        String close = "b";

        // When
        String[] actual = StringUtils.substringsBetween(input, open, close);

        // Then
        then(actual).hasSize(1).containsExactly("X");
    }

    @Test
    void substringsBetween_ShouldReturnMultipleSubstrings_WhenMultipleNonOverlapping() {
        // Given
        String input = "aXb aYb";
        String open = "a";
        String close = "b";

        // When
        String[] actual = StringUtils.substringsBetween(input, open, close);

        // Then
        then(actual).hasSize(2).containsExactly("X", "Y");
    }

    @Test
    void substringsBetween_ShouldReturnSubstring_WhenOverlapping() {
        // Given
        String input = "aXaYb";
        String open = "a";
        String close = "b";

        // When
        String[] actual = StringUtils.substringsBetween(input, open, close);

        // Then
        then(actual).hasSize(1).containsExactly("XaY");
    }

    @Test
    void substringsBetween_ShouldReturnSubstring_WhenOpenAndCloseSame() {
        // Given
        String input = "aXa";
        String open = "a";
        String close = "a";

        // When
        String[] actual = StringUtils.substringsBetween(input, open, close);

        // Then
        then(actual).hasSize(1).containsExactly("X");
    }

    @Test
    void substringsBetween_ShouldReturnEmptySubstring_WhenOpenAndCloseAdjacent() {
        // Given
        String input = "ab";
        String open = "a";
        String close = "b";

        // When
        String[] actual = StringUtils.substringsBetween(input, open, close);

        // Then
        then(actual).hasSize(1).containsExactly(EMPTY);
    }

    @Test
    void substringsBetween_ShouldReturnEmptySubstring_WhenSubstringIsEmpty() {
        // Given
        String input = "aa";
        String open = "a";
        String close = "a";

        // When
        String[] actual = StringUtils.substringsBetween(input, open, close);

        // Then
        then(actual).hasSize(1).containsExactly(EMPTY);
    }

    @Test
    void substringsBetween_ShouldReturnMultipleSubstrings_WhenConsecutivePairs() {
        // Given
        String input = "aXaYaZa";
        String open = "a";
        String close = "a";

        // When
        String[] actual = StringUtils.substringsBetween(input, open, close);

        // Then
        then(actual).hasSize(2).containsExactly("X", "Z");
    }

    @Test
    void substringsBetween_ShouldReturnEmptyArray_WhenCloseBeforeOpen() {
        // Given
        String input = "bXa";
        String open = "a";
        String close = "b";

        // When
        String[] actual = StringUtils.substringsBetween(input, open, close);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void substringsBetween_ShouldReturnSubstring_WhenSpaceBetween() {
        // Given
        String input = "a X a";
        String open = "a";
        String close = "a";

        // When
        String[] actual = StringUtils.substringsBetween(input, open, close);

        // Then
        then(actual).hasSize(1).containsExactly(" X ");
    }

    @Test
    void substringsBetween_ShouldReturnMultipleSubstrings_WhenMultipleWithSpaces() {
        // Given
        String input = "a X a a Y a";
        String open = "a";
        String close = "a";

        // When
        String[] actual = StringUtils.substringsBetween(input, open, close);

        // Then
        then(actual).hasSize(2).containsExactly(" X ", " Y ");
    }

    @Test
    void trimLeadingCharacter_ShouldReturnNull_WhenInputIsNull() {
        // Given
        String input = null;
        char leading = 'a';

        // When
        String actual = StringUtils.trimLeadingCharacter(input, leading);

        // Then
        then(actual).isNull();
    }

    @Test
    void trimLeadingCharacter_ShouldReturnEmpty_WhenInputIsEmpty() {
        // Given
        String input = EMPTY;
        char leading = 'a';

        // When
        String actual = StringUtils.trimLeadingCharacter(input, leading);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void trimLeadingCharacter_ShouldReturnOriginal_WhenLeadingNotAtStart() {
        // Given
        String input = "hello";
        char leading = 'e';

        // When
        String actual = StringUtils.trimLeadingCharacter(input, leading);

        // Then
        then(actual).isEqualTo("hello");
    }

    @Test
    void trimLeadingCharacter_ShouldTrimLeading_WhenSomeMatch() {
        // Given
        String input = "aaab";
        char leading = 'a';

        // When
        String actual = StringUtils.trimLeadingCharacter(input, leading);

        // Then
        then(actual).isEqualTo("b");
    }

    @Test
    void trimLeadingCharacter_ShouldReturnEmpty_WhenAllCharactersMatch() {
        // Given
        String input = "aaa";
        char leading = 'a';

        // When
        String actual = StringUtils.trimLeadingCharacter(input, leading);

        // Then
        then(actual).isEqualTo("");
    }

    @Test
    void trimLeadingCharacter_ShouldReturnEmpty_WhenInputIsSingleMatching() {
        // Given
        String input = "a";
        char leading = 'a';

        // When
        String actual = StringUtils.trimLeadingCharacter(input, leading);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void trimLeadingCharacter_ShouldReturnOriginal_WhenInputIsSingleNotMatching() {
        // Given
        String input = "a";
        char leading = 'b';

        // When
        String actual = StringUtils.trimLeadingCharacter(input, leading);

        // Then
        then(actual).isEqualTo("a");
    }

    @Test
    void trimLeadingCharacter_ShouldTrimLeadingSpaces() {
        // Given
        String input = "   hello";
        char leading = ' ';

        // When
        String actual = StringUtils.trimLeadingCharacter(input, leading);

        // Then
        then(actual).isEqualTo("hello");
    }

    @Test
    void trimLeadingCharacter_ShouldTrimLeadingNonBreakingSpace() {
        // Given
        String input = "\u00A0\u00A0hello";
        char leading = '\u00A0';

        // When
        String actual = StringUtils.trimLeadingCharacter(input, leading);

        // Then
        then(actual).isEqualTo("hello");
    }

    @Test
    void trimLeadingCharacter_ShouldTrimMultipleLeadingSpaces() {
        // Given
        String input = "  hello";
        char leading = ' ';

        // When
        String actual = StringUtils.trimLeadingCharacter(input, leading);

        // Then
        then(actual).isEqualTo("hello");
    }

    @Test
    void trimLeadingCharacter_ShouldReturnOriginal_WhenLeadingIsSpaceButInputStartsWithNonSpace() {
        // Given
        String input = "hello";
        char leading = ' ';

        // When
        String actual = StringUtils.trimLeadingCharacter(input, leading);

        // Then
        then(actual).isEqualTo("hello");
    }

    @Test
    void trimLeadingCharacter_ShouldTrimLeadingSpaceWithNonSpace() {
        // Given
        String input = " hello";
        char leading = ' ';

        // When
        String actual = StringUtils.trimLeadingCharacter(input, leading);

        // Then
        then(actual).isEqualTo("hello");
    }

    @Test
    void trimLeadingCharacter_ShouldTrimLeadingNonBreakingSpaceWithNonSpace() {
        // Given
        String input = "\u00A0hello";
        char leading = '\u00A0';

        // When
        String actual = StringUtils.trimLeadingCharacter(input, leading);

        // Then
        then(actual).isEqualTo("hello");
    }

    @Test
    void trimTrailingCharacter_ShouldReturnNull_WhenInputIsNull() {
        // Given
        String input = null;
        char trailing = 'a';

        // When
        String actual = StringUtils.trimTrailingCharacter(input, trailing);

        // Then
        then(actual).isNull();
    }

    @Test
    void trimTrailingCharacter_ShouldReturnEmpty_WhenInputIsEmpty() {
        // Given
        String input = EMPTY;
        char trailing = 'a';

        // When
        String actual = StringUtils.trimTrailingCharacter(input, trailing);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void trimTrailingCharacter_ShouldReturnOriginal_WhenTrailingNotAtEnd() {
        // Given
        String input = "hello";
        char trailing = 'e';

        // When
        String actual = StringUtils.trimTrailingCharacter(input, trailing);

        // Then
        then(actual).isEqualTo("hello");
    }

    @Test
    void trimTrailingCharacter_ShouldTrimTrailing_WhenSomeMatch() {
        // Given
        String input = "bbaa";
        char trailing = 'a';

        // When
        String actual = StringUtils.trimTrailingCharacter(input, trailing);

        // Then
        then(actual).isEqualTo("bb");
    }

    @Test
    void trimTrailingCharacter_ShouldReturnEmpty_WhenAllCharactersMatch() {
        // Given
        String input = "aaa";
        char trailing = 'a';

        // When
        String actual = StringUtils.trimTrailingCharacter(input, trailing);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void trimTrailingCharacter_ShouldReturnEmpty_WhenInputIsSingleMatching() {
        // Given
        String input = "a";
        char trailing = 'a';

        // When
        String actual = StringUtils.trimTrailingCharacter(input, trailing);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void trimTrailingCharacter_ShouldReturnOriginal_WhenInputIsSingleNotMatching() {
        // Given
        String input = "a";
        char trailing = 'b';

        // When
        String actual = StringUtils.trimTrailingCharacter(input, trailing);

        // Then
        then(actual).isEqualTo("a");
    }

    @Test
    void trimTrailingCharacter_ShouldTrimTrailingSpaces() {
        // Given
        String input = "hello   ";
        char trailing = ' ';

        // When
        String actual = StringUtils.trimTrailingCharacter(input, trailing);

        // Then
        then(actual).isEqualTo("hello");
    }

    @Test
    void trimTrailingCharacter_ShouldTrimTrailingNonBreakingSpace() {
        // Given
        String input = "hello\u00A0";
        char trailing = '\u00A0';

        // When
        String actual = StringUtils.trimTrailingCharacter(input, trailing);

        // Then
        then(actual).isEqualTo("hello");
    }

    @Test
    void trimTrailingCharacter_ShouldTrimMultipleTrailingSpaces() {
        // Given
        String input = "hello  ";
        char trailing = ' ';

        // When
        String actual = StringUtils.trimTrailingCharacter(input, trailing);

        // Then
        then(actual).isEqualTo("hello");
    }

    @Test
    void trimTrailingCharacter_ShouldReturnOriginal_WhenTrailingIsSpaceButInputEndsWithNonSpace() {
        // Given
        String input = "hello";
        char trailing = ' ';

        // When
        String actual = StringUtils.trimTrailingCharacter(input, trailing);

        // Then
        then(actual).isEqualTo("hello");
    }

    @Test
    void trimTrailingCharacter_ShouldTrimTrailingSpaceWithNonSpace() {
        // Given
        String input = "hello ";
        char trailing = ' ';

        // When
        String actual = StringUtils.trimTrailingCharacter(input, trailing);

        // Then
        then(actual).isEqualTo("hello");
    }

    @Test
    void trimTrailingCharacter_ShouldTrimTrailingNonBreakingSpaceWithNonSpace() {
        // Given
        String input = "hello\u00A0";
        char trailing = '\u00A0';

        // When
        String actual = StringUtils.trimTrailingCharacter(input, trailing);

        // Then
        then(actual).isEqualTo("hello");
    }

    @Test
    void trimTrailingCharacter_ShouldTrimMultipleTrailingNonBreakingSpaces() {
        // Given
        String input = "hello\u00A0\u00A0";
        char trailing = '\u00A0';

        // When
        String actual = StringUtils.trimTrailingCharacter(input, trailing);

        // Then
        then(actual).isEqualTo("hello");
    }

    @Test
    void trimTrailingCharacter_ShouldReturnOriginal_WhenTrailingIsNonBreakingSpaceButInputEndsWithSpace() {
        // Given
        String input = "hello ";
        char trailing = '\u00A0';

        // When
        String actual = StringUtils.trimTrailingCharacter(input, trailing);

        // Then
        then(actual).isEqualTo("hello ");
    }

    @Test
    void trimArrayElements_ShouldReturnNull_WhenInputIsNull() {
        // Given
        String[] input = null;

        // When
        String[] actual = StringUtils.trimArrayElements(input);

        // Then
        then(actual).isNull();
    }

    @Test
    void trimArrayElements_ShouldReturnEmptyArray_WhenInputIsEmpty() {
        // Given
        String[] input = new String[0];

        // When
        String[] actual = StringUtils.trimArrayElements(input);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void trimArrayElements_ShouldTrimNonNullElements_WhenInputHasNulls() {
        // Given
        String[] input = {"  hello  ", null, "world  "};

        // When
        String[] actual = StringUtils.trimArrayElements(input);

        // Then
        then(actual).hasSize(3);
        then(actual[0]).isEqualTo("hello");
        then(actual[1]).isNull();
        then(actual[2]).isEqualTo("world");
    }

    @Test
    void trimArrayElements_ShouldTrimLeadingAndTrailingSpaces_WhenInputHasSpaces() {
        // Given
        String[] input = {"  hello  ", "  world  "};

        // When
        String[] actual = StringUtils.trimArrayElements(input);

        // Then
        then(actual).hasSize(2);
        then(actual[0]).isEqualTo("hello");
        then(actual[1]).isEqualTo("world");
    }

    @Test
    void trimArrayElements_ShouldReturnOriginal_WhenInputHasNoSpaces() {
        // Given
        String[] input = {"hello", "world"};

        // When
        String[] actual = StringUtils.trimArrayElements(input);

        // Then
        then(actual).hasSize(2);
        then(actual[0]).isEqualTo("hello");
        then(actual[1]).isEqualTo("world");
    }

    @Test
    void trimArrayElements_ShouldReturnArrayWithNulls_WhenAllElementsAreNull() {
        // Given
        String[] input = {null, null};

        // When
        String[] actual = StringUtils.trimArrayElements(input);

        // Then
        then(actual).hasSize(2);
        then(actual[0]).isNull();
        then(actual[1]).isNull();
    }

    @Test
    void trimArrayElements_ShouldHandleSingleNullElement() {
        // Given
        String[] input = {null};

        // When
        String[] actual = StringUtils.trimArrayElements(input);

        // Then
        then(actual).hasSize(1);
        then(actual[0]).isNull();
    }

    @Test
    void trimArrayElements_ShouldTrimSingleElementWithSpaces() {
        // Given
        String[] input = {"  hello  "};

        // When
        String[] actual = StringUtils.trimArrayElements(input);

        // Then
        then(actual).hasSize(1);
        then(actual[0]).isEqualTo("hello");
    }

    @Test
    void trimArrayElements_ShouldHandleEmptyStringElement() {
        // Given
        String[] input = {EMPTY, "  ", "  test  "};

        // When
        String[] actual = StringUtils.trimArrayElements(input);

        // Then
        then(actual).hasSize(3);
        then(actual[0]).isEqualTo(EMPTY);
        then(actual[1]).isEqualTo(EMPTY);
        then(actual[2]).isEqualTo("test");
    }

    @Test
    void trimArrayElements_ShouldReturnNewArray_WhenInputIsNonEmpty() {
        // Given
        String[] input = {"  hello  "};

        // When
        String[] actual = StringUtils.trimArrayElements(input);

        // Then
        then(actual).isNotSameAs(input);
        then(actual[0]).isEqualTo("hello");
    }

    @Test
    void toString_ShouldReturnEmptyArray_WhenCollectionIsNull() {
        // Given
        Collection<String> collection = null;

        // When
        String[] actual = StringUtils.toString(collection);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void toString_ShouldReturnEmptyArray_WhenCollectionIsEmpty() {
        // Given
        Collection<String> collection = Collections.emptyList();

        // When
        String[] actual = StringUtils.toString(collection);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void toString_ShouldReturnEmptyArray_WhenCollectionIsNotEmpty() {
        // Given
        Collection<String> collection = Arrays.asList("hello", "world");

        // When
        String[] actual = StringUtils.toString(collection);

        // Then
        then(actual).hasSize(2);
        then(actual[0]).isEqualTo("hello");
        then(actual[1]).isEqualTo("world");
    }

    @Test
    void toString_ShouldReturnEmptyArray_WhenCollectionHasNullElements() {
        // Given
        Collection<String> collection = Arrays.asList(null, "test", null);

        // When
        String[] actual = StringUtils.toString(collection);

        // Then
        then(actual).hasSize(3);
        then(actual[0]).isNull();
        then(actual[1]).isEqualTo("test");
        then(actual[2]).isNull();
    }

    @Test
    void toString_ShouldReturnEmptyArray_WhenCollectionContainsEmptyStrings() {
        // Given
        Collection<String> collection = Arrays.asList(EMPTY, SPACE, EMPTY);

        // When
        String[] actual = StringUtils.toString(collection);

        // Then
        then(actual).hasSize(3);
        then(actual[0]).isEmpty();
        then(actual[1]).isEqualTo(SPACE);
        then(actual[2]).isEmpty();
    }

    @Test
    void toString_ShouldReturnEmptyArray_WhenEnumerationIsNull() {
        // Given
        Enumeration<String> enumeration = null;

        // When
        String[] actual = StringUtils.toString(enumeration);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void toString_ShouldReturnEmptyArray_WhenEnumerationIsEmpty() {
        // Given
        Enumeration<String> enumeration = Collections.emptyEnumeration();

        // When
        String[] actual = StringUtils.toString(enumeration);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void toString_ShouldReturnEmptyArray_WhenEnumerationIsNotEmpty() {
        // Given
        Enumeration<String> enumeration = Collections.enumeration(Arrays.asList("hello", "world"));

        // When
        String[] actual = StringUtils.toString(enumeration);

        // Then
        then(actual).hasSize(2);
        then(actual[0]).isEqualTo("hello");
        then(actual[1]).isEqualTo("world");
    }

    @Test
    void toString_ShouldReturnEmptyArray_WhenEnumerationHasNullElements() {
        // Given
        Enumeration<String> enumeration = Collections.enumeration(Arrays.asList(null, "test", null));

        // When
        String[] actual = StringUtils.toString(enumeration);

        // Then
        then(actual).hasSize(3);
        then(actual[0]).isNull();
        then(actual[1]).isEqualTo("test");
        then(actual[2]).isNull();
    }

    @Test
    void toString_ShouldReturnEmptyArray_WhenEnumerationHasEmptyStrings() {
        // Given
        Enumeration<String> enumeration = Collections.enumeration(Arrays.asList(EMPTY, SPACE, EMPTY));

        // When
        String[] actual = StringUtils.toString(enumeration);

        // Then
        then(actual).hasSize(3);
        then(actual[0]).isEmpty();
        then(actual[1]).isEqualTo(SPACE);
        then(actual[2]).isEmpty();
    }

    @Test
    void uncapitalize_ShouldReturnNull_WhenInputIsNull() {
        // Given
        String input = null;

        // When
        String actual = StringUtils.uncapitalize(input);

        // Then
        then(actual).isNull();
    }

    @Test
    void uncapitalize_ShouldReturnEmpty_WhenInputIsEmpty() {
        // Given
        String input = EMPTY;

        // When
        String actual = StringUtils.uncapitalize(input);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void uncapitalize_ShouldReturnWhitespace_WhenInputIsBlank() {
        // Given
        String input = "   ";

        // When
        String actual = StringUtils.uncapitalize(input);

        // Then
        then(actual).isEqualTo("   ");
    }

    @Test
    void uncapitalize_ShouldReturnLowercase_WhenInputStartsWithUppercase() {
        // Given
        String input = "Hello";

        // When
        String actual = StringUtils.uncapitalize(input);

        // Then
        then(actual).isEqualTo("hello");
    }

    @Test
    void uncapitalize_ShouldReturnOriginal_WhenInputStartsWithLowercase() {
        // Given
        String input = "hello";

        // When
        String actual = StringUtils.uncapitalize(input);

        // Then
        then(actual).isEqualTo("hello");
    }

    @Test
    void uncapitalize_ShouldReturnOriginal_WhenInputStartsWithNonLetter() {
        // Given
        String input = "123Hello";

        // When
        String actual = StringUtils.uncapitalize(input);

        // Then
        then(actual).isEqualTo("123Hello");
    }

    @Test
    void uncapitalize_ShouldReturnLowercaseUnicode_WhenInputStartsWithUppercaseUnicode() {
        // Given
        String input = "Álvaro";

        // When
        String actual = StringUtils.uncapitalize(input);

        // Then
        then(actual).isEqualTo("álvaro");
    }

    @Test
    void uncapitalize_ShouldReturnOriginal_WhenInputStartsWithLowercaseUnicode() {
        // Given
        String input = "álvaro";

        // When
        String actual = StringUtils.uncapitalize(input);

        // Then
        then(actual).isEqualTo("álvaro");
    }

    @Test
    void uncapitalize_ShouldReturnFirstLowercase_WhenInputIsAllUppercase() {
        // Given
        String input = "HELLO";

        // When
        String actual = StringUtils.uncapitalize(input);

        // Then
        then(actual).isEqualTo("hELLO");
    }

    @Test
    void uncapitalize_ShouldReturnOriginal_WhenInputIsSingleLowercase() {
        // Given
        String input = "a";

        // When
        String actual = StringUtils.uncapitalize(input);

        // Then
        then(actual).isEqualTo("a");
    }

    @Test
    void uncapitalize_ShouldReturnLowercase_WhenInputIsSingleUppercase() {
        // Given
        String input = "A";

        // When
        String actual = StringUtils.uncapitalize(input);

        // Then
        then(actual).isEqualTo("a");
    }

    @Test
    void uncapitalize_ShouldReturnOriginal_WhenInputHasNonBreakingSpace() {
        // Given
        String input = "\u00A0Hello";

        // When
        String actual = StringUtils.uncapitalize(input);

        // Then
        then(actual).isEqualTo("\u00A0Hello");
    }

    @Test
    void unwrap_ShouldReturnNull_WhenInputIsNull() {
        // Given
        String input = null;
        char wrapToken = 'a';

        // When
        String actual = StringUtils.unwrap(input, wrapToken);

        // Then
        then(actual).isNull();
    }

    @Test
    void unwrap_ShouldReturnEmpty_WhenInputIsEmpty() {
        // Given
        String input = EMPTY;
        char wrapToken = 'a';

        // When
        String actual = StringUtils.unwrap(input, wrapToken);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void unwrap_ShouldReturnEmpty_WhenInputIsSingleWrapToken() {
        // Given
        String input = "a";
        char wrapToken = 'a';

        // When
        String actual = StringUtils.unwrap(input, wrapToken);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void unwrap_ShouldReturnEmpty_WhenInputIsDoubleWrapToken() {
        // Given
        String input = "aa";
        char wrapToken = 'a';

        // When
        String actual = StringUtils.unwrap(input, wrapToken);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void unwrap_ShouldReturnMiddle_WhenStartAndEndMatch() {
        // Given
        String input = "aXa";
        char wrapToken = 'a';

        // When
        String actual = StringUtils.unwrap(input, wrapToken);

        // Then
        then(actual).isEqualTo("X");
    }

    @Test
    void unwrap_ShouldReturnOriginal_WhenStartMatchesButEndDoesNot() {
        // Given
        String input = "aXb";
        char wrapToken = 'a';

        // When
        String actual = StringUtils.unwrap(input, wrapToken);

        // Then
        then(actual).isEqualTo("aXb");
    }

    @Test
    void unwrap_ShouldReturnOriginal_WhenEndMatchesButStartDoesNot() {
        // Given
        String input = "Xa";
        char wrapToken = 'a';

        // When
        String actual = StringUtils.unwrap(input, wrapToken);

        // Then
        then(actual).isEqualTo("Xa");
    }

    @Test
    void unwrap_ShouldReturnOriginal_WhenNeitherMatches() {
        // Given
        String input = "XaY";
        char wrapToken = 'a';

        // When
        String actual = StringUtils.unwrap(input, wrapToken);

        // Then
        then(actual).isEqualTo("XaY");
    }

    @Test
    void unwrap_ShouldReturnMiddle_WhenWrapTokenIsSpace() {
        // Given
        String input = " a ";
        char wrapToken = ' ';

        // When
        String actual = StringUtils.unwrap(input, wrapToken);

        // Then
        then(actual).isEqualTo("a");
    }

    @Test
    void unwrap_ShouldReturnMiddle_WhenWrapTokenIsNonBreakingSpace() {
        // Given
        String input = "\u00A0a\u00A0";
        char wrapToken = '\u00A0';

        // When
        String actual = StringUtils.unwrap(input, wrapToken);

        // Then
        then(actual).isEqualTo("a");
    }

    @Test
    void unwrap_ShouldReturnOriginal_WhenWrapTokenIsNonBreakingSpaceButNotAtStart() {
        // Given
        String input = "a\u00A0";
        char wrapToken = '\u00A0';

        // When
        String actual = StringUtils.unwrap(input, wrapToken);

        // Then
        then(actual).isEqualTo("a\u00A0");
    }

    @Test
    void unwrap_ShouldReturnOriginal_WhenWrapTokenIsSpaceButNotAtEnd() {
        // Given
        String input = "a ";
        char wrapToken = ' ';

        // When
        String actual = StringUtils.unwrap(input, wrapToken);

        // Then
        then(actual).isEqualTo("a ");
    }

    @Test
    void wrap_ShouldReturnNull_WhenInputIsNull() {
        // Given
        String input = null;
        String wrapWith = "a";

        // When
        String actual = StringUtils.wrap(input, wrapWith);

        // Then
        then(actual).isNull();
    }

    @Test
    void wrap_ShouldReturnEmpty_WhenInputIsEmpty() {
        // Given
        String input = EMPTY;
        String wrapWith = "a";

        // When
        String actual = StringUtils.wrap(input, wrapWith);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void wrap_ShouldReturnWhitespace_WhenInputIsBlank() {
        // Given
        String input = "   ";
        String wrapWith = "a";

        // When
        String actual = StringUtils.wrap(input, wrapWith);

        // Then
        then(actual).isEqualTo("   ");
    }

    @Test
    void wrap_ShouldReturnOriginal_WhenWrapWithIsNull() {
        // Given
        String input = "hello";
        String wrapWith = null;

        // When
        String actual = StringUtils.wrap(input, wrapWith);

        // Then
        then(actual).isEqualTo("hello");
    }

    @Test
    void wrap_ShouldReturnOriginal_WhenWrapWithIsEmpty() {
        // Given
        String input = "hello";
        String wrapWith = EMPTY;

        // When
        String actual = StringUtils.wrap(input, wrapWith);

        // Then
        then(actual).isEqualTo("hello");
    }

    @Test
    void wrap_ShouldWrap_WhenInputIsTextAndWrapWithIsLength() {
        // Given
        String input = "hello";
        String wrapWith = "a";

        // When
        String actual = StringUtils.wrap(input, wrapWith);

        // Then
        then(actual).isEqualTo("ahelloa");
    }

    @Test
    void wrap_ShouldWrapWithWhitespace_WhenWrapWithIsWhitespace() {
        // Given
        String input = "hello";
        String wrapWith = "  ";

        // When
        String actual = StringUtils.wrap(input, wrapWith);

        // Then
        then(actual).isEqualTo("  hello  ");
    }

    @Test
    void wrap_ShouldReturnWhitespace_WhenInputIsBlankAndWrapWithValid() {
        // Given
        String input = "   ";
        String wrapWith = "a";

        // When
        String actual = StringUtils.wrap(input, wrapWith);

        // Then
        then(actual).isEqualTo("   ");
    }

    @Test
    void wrap_ShouldWrap_WhenInputIsSingleCharacter() {
        // Given
        String input = "a";
        String wrapWith = "b";

        // When
        String actual = StringUtils.wrap(input, wrapWith);

        // Then
        then(actual).isEqualTo("bab");
    }

    @Test
    void wrap_ShouldWrap_WhenInputIsAllUppercase() {
        // Given
        String input = "HELLO";
        String wrapWith = "!";

        // When
        String actual = StringUtils.wrap(input, wrapWith);

        // Then
        then(actual).isEqualTo("!HELLO!");
    }

    @Test
    void wrap_ShouldWrap_WhenWrapWithIsNonBreakingSpace() {
        // Given
        String input = "hello";
        String wrapWith = "\u00A0";

        // When
        String actual = StringUtils.wrap(input, wrapWith);

        // Then
        then(actual).isEqualTo("\u00A0hello\u00A0");
    }

    @Test
    void wrap_ShouldReturnOriginal_WhenInputIsBlankAndWrapWithIsEmpty() {
        // Given
        String input = "   ";
        String wrapWith = EMPTY;

        // When
        String actual = StringUtils.wrap(input, wrapWith);

        // Then
        then(actual).isEqualTo("   ");
    }

    @Test
    void wrap_ShouldReturnOriginal_WhenInputIsBlankAndWrapWithIsNull() {
        // Given
        String input = "   ";
        String wrapWith = null;

        // When
        String actual = StringUtils.wrap(input, wrapWith);

        // Then
        then(actual).isEqualTo("   ");
    }

    @Test
    void wrapIfMissing_ShouldReturnNull_WhenInputIsNull() {
        // Given
        String input = null;
        char wrapWith = 'a';

        // When
        String actual = StringUtils.wrapIfMissing(input, wrapWith);

        // Then
        then(actual).isNull();
    }

    @Test
    void wrapIfMissing_ShouldReturnEmpty_WhenInputIsEmpty() {
        // Given
        String input = "";
        char wrapWith = 'a';

        // When
        String actual = StringUtils.wrapIfMissing(input, wrapWith);

        // Then
        then(actual).isEqualTo(EMPTY);
    }

    @Test
    void wrapIfMissing_ShouldReturnOriginal_WhenAlreadyWrappedAtBothEnds() {
        // Given
        String input = "aXa";
        char wrapWith = 'a';

        // When
        String actual = StringUtils.wrapIfMissing(input, wrapWith);

        // Then
        then(actual).isEqualTo("aXa");
    }

    @Test
    void wrapIfMissing_ShouldWrapEnd_WhenWrappedAtStartButNotEnd() {
        // Given
        String input = "aX";
        char wrapWith = 'a';

        // When
        String actual = StringUtils.wrapIfMissing(input, wrapWith);

        // Then
        then(actual).isEqualTo("aXa");
    }

    @Test
    void wrapIfMissing_ShouldWrapStart_WhenWrappedAtEndButNotStart() {
        // Given
        String input = "Xa";
        char wrapWith = 'a';

        // When
        String actual = StringUtils.wrapIfMissing(input, wrapWith);

        // Then
        then(actual).isEqualTo("aXa");
    }

    @Test
    void wrapIfMissing_ShouldWrapBoth_WhenNotWrappedAtAll() {
        // Given
        String input = "X";
        char wrapWith = 'a';

        // When
        String actual = StringUtils.wrapIfMissing(input, wrapWith);

        // Then
        then(actual).isEqualTo("aXa");
    }

    @Test
    void wrapIfMissing_ShouldReturnOriginal_WhenSingleCharacterMatch() {
        // Given
        String input = "a";
        char wrapWith = 'a';

        // When
        String actual = StringUtils.wrapIfMissing(input, wrapWith);

        // Then
        then(actual).isEqualTo("a");
    }

    @Test
    void wrapIfMissing_ShouldWrapBoth_WhenSingleCharacterDoesNotMatch() {
        // Given
        String input = "a";
        char wrapWith = 'b';

        // When
        String actual = StringUtils.wrapIfMissing(input, wrapWith);

        // Then
        then(actual).isEqualTo("bab");
    }

    @Test
    void wrapIfMissing_ShouldWrapEnd_WhenInputHasNonBreakingSpaceAtStart() {
        // Given
        String input = "\u00A0X";
        char wrapWith = '\u00A0';

        // When
        String actual = StringUtils.wrapIfMissing(input, wrapWith);

        // Then
        then(actual).isEqualTo("\u00A0X\u00A0");
    }

    @Test
    void wrapIfMissing_ShouldWrapStart_WhenInputHasNonBreakingSpaceAtEnd() {
        // Given
        String input = "X\u00A0";
        char wrapWith = '\u00A0';

        // When
        String actual = StringUtils.wrapIfMissing(input, wrapWith);

        // Then
        then(actual).isEqualTo("\u00A0X\u00A0");
    }

    @Test
    void wrapIfMissing_ShouldWrapBoth_WhenInputHasNonBreakingSpace() {
        // Given
        String input = "X";
        char wrapWith = '\u00A0';

        // When
        String actual = StringUtils.wrapIfMissing(input, wrapWith);

        // Then
        then(actual).isEqualTo("\u00A0X\u00A0");
    }

    @Test
    void wrapIfMissing_ShouldReturnOriginal_WhenInputIsBlank() {
        // Given
        String input = "   ";
        char wrapWith = ' ';

        // When
        String actual = StringUtils.wrapIfMissing(input, wrapWith);

        // Then
        then(actual).isEqualTo("   ");
    }

    @Test
    void wrapIfMissing_ShouldWrapStart_WhenInputHasSpaceAtEnd() {
        // Given
        String input = "hello ";
        char wrapWith = ' ';

        // When
        String actual = StringUtils.wrapIfMissing(input, wrapWith);

        // Then
        then(actual).isEqualTo(" hello ");
    }

    @Test
    void wrapIfMissing_ShouldWrapEnd_WhenInputHasSpaceAtStart() {
        // Given
        String input = " hello";
        char wrapWith = ' ';

        // When
        String actual = StringUtils.wrapIfMissing(input, wrapWith);

        // Then
        then(actual).isEqualTo(" hello ");
    }
}
