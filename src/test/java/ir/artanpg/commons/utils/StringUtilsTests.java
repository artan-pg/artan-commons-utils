package ir.artanpg.commons.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import static ir.artanpg.commons.utils.StringUtils.INDEX_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the {@link StringUtils} class.
 *
 * @author Mohammad Yazdian
 */
class StringUtilsTests {

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" "})
    void abbreviate_ShouldReturnEmptyString_WhenInputStringIsNullOrEmpty(String input) {
        // Given
        String abbrevMarker = "...";
        int maxWidth = 4;

        // When
        String actual = StringUtils.abbreviate(input, abbrevMarker, maxWidth);

        // Then
        then(actual).isEmpty();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" "})
    void abbreviate_ShouldReturnEmptyString_WhenAbbrevMarkerIsNullOrEmpty(String abbrevMarker) {
        // Given
        String input = "Hello";
        int maxWidth = 4;

        // When
        String actual = StringUtils.abbreviate(input, abbrevMarker, maxWidth);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void abbreviate_ShouldReturnOriginalString_WhenStringLengthIsLessThanMaxWidth() {
        // Given
        String input = "Hello";
        String abbrevMarker = "...";
        int maxWidth = 10;

        // When
        String actual = StringUtils.abbreviate(input, abbrevMarker, maxWidth);

        // Then
        then(actual).isEqualTo(input);
    }

    @Test
    void abbreviate_ShouldReturnOriginalString_WhenStringLengthEqualsMaxWidth() {
        // Given
        String input = "Hello";
        String abbrevMarker = "...";
        int maxWidth = 5;

        // When
        String actual = StringUtils.abbreviate(input, abbrevMarker, maxWidth);

        // Then
        then(actual).isEqualTo(input);
    }

    @Test
    void abbreviate_ShouldReturnAbbreviatedString_WhenStringLengthExceedsMaxWidth() {
        // Given
        String input = "Hello World";
        String abbrevMarker = "...";
        int maxWidth = 8;

        // When
        String actual = StringUtils.abbreviate(input, abbrevMarker, maxWidth);

        // Then
        then(actual).isEqualTo("Hello...");
    }

    @Test
    void abbreviate_ShouldThrowIllegalArgumentException_WhenMaxWidthIsLessThanMinAbbrevWidth() {
        // Given
        String input = "Hello";
        String abbrevMarker = "...";
        int maxWidth = 3;

        // Then
        assertThatThrownBy(() -> StringUtils.abbreviate(input, abbrevMarker, maxWidth))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Minimum abbreviation width is 4");
    }

    @Test
    void abbreviate_ShouldThrowIllegalArgumentException_WhenMaxWidthIsZero() {
        // Given
        String input = "Hello";
        String abbrevMarker = "...";
        int maxWidth = 0;

        // Then
        assertThatThrownBy(() -> StringUtils.abbreviate(input, abbrevMarker, maxWidth))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Minimum abbreviation width is 4");
    }

    @Test
    void abbreviate_ShouldThrowIllegalArgumentException_WhenMaxWidthIsNegative() {
        // Given
        String input = "Hello";
        String abbrevMarker = "...";
        int maxWidth = -1;

        // Then
        assertThatThrownBy(() -> StringUtils.abbreviate(input, abbrevMarker, maxWidth))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Minimum abbreviation width is 4");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" "})
    void capitalize_ShouldReturnOriginalString_WhenInputStringIsNullOrEmptyOrBlank(String input) {
        // When
        String actual = StringUtils.capitalize(input);

        // Then
        then(actual).isEqualTo(input);
    }

    @ParameterizedTest
    @ValueSource(strings = {"hello", "hELLO"})
    void capitalize_ShouldCapitalizeFirstCharacter_WhenInputStringStartsWithLowercase(String input) {
        // When
        String actual = StringUtils.capitalize(input);

        // Then
        then(actual).isIn("Hello", "HELLO");
    }

    @ParameterizedTest
    @ValueSource(strings = {"Hello", "HELLO"})
    void capitalize_ShouldReturnOriginalString_WhenInputStringStartsWithUppercase(String input) {
        // When
        String actual = StringUtils.capitalize(input);

        // Then
        then(actual).isEqualTo(input);
    }

    @Test
    void capitalize_ShouldReturnOriginalString_WhenInputStringStartsWithNonLetter() {
        // Given
        String input = "123test";

        // When
        String actual = StringUtils.capitalize(input);

        // Then
        then(actual).isEqualTo(input);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" "})
    void chomp_ShouldReturnOriginalString_WhenInputStringIsNullOrEmptyOrBlank(String input) {
        // When
        String actual = StringUtils.chomp(input);

        // Then
        then(actual).isEqualTo(input);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Hello\n", "Hello\r", "Hello\r\n"})
    void chomp_ShouldRemoveNewLineFeed_WhenStringEndsWithNewLineFeed(String input) {
        // When
        String actual = StringUtils.chomp(input);

        // Then
        then(actual).isEqualTo("Hello");
    }

    @ParameterizedTest
    @ValueSource(strings = {"Hello", "a"})
    void chomp_ShouldReturnOriginalString_WhenStringDoesNotEndWithLineFeedOrCarriageReturn(String input) {
        // When
        String actual = StringUtils.chomp(input);

        // Then
        then(actual).isIn(input);
    }

    @ParameterizedTest
    @ValueSource(strings = {"\n", "\r", "\r\n"})
    void chomp_ShouldReturnEmptyString_WhenInputStringIsSingleNewLineFeed(String input) {
        // When
        String actual = StringUtils.chomp(input);

        // Then
        then(actual).isEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"Hello\nWorld\n", "Hello\rWorld\r", "Hello\r\nWorld\r\n"})
    void chomp_ShouldRemoveOnlyLastNewline_WhenStringContainsMultipleNewlines(String input) {
        // When
        String actual = StringUtils.chomp(input);

        // Then
        then(actual).isIn("Hello\nWorld", "Hello\rWorld", "Hello\r\nWorld");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" "})
    void chop_ShouldReturnOriginalString_WhenInputStringIsNullOrEmptyOrBlank(String input) {
        // When
        String actual = StringUtils.chop(input);

        // Then
        then(actual).isEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "A", "\n", "\r", "\r\n"})
    void chop_ShouldReturnEmptyString_WhenInputStringIsSingleCharacter(String input) {
        // When
        String actual = StringUtils.chop(input);

        // Then
        then(actual).isEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"Hello ", "Hello!", "Hello\r\n"})
    void chop_ShouldRemoveLastCharacter_WhenStringDoesNotEndWithLineFeed(String input) {
        // When
        String actual = StringUtils.chop(input);

        // Then
        then(actual).isEqualTo("Hello");
    }

    @ParameterizedTest
    @ValueSource(strings = {"Hello\n", "Hello\r"})
    void chop_ShouldRemoveLastTwoCharacters_WhenStringEndsWithLineFeed(String input) {
        // When
        String actual = StringUtils.chop(input);

        // Then
        then(actual).isEqualTo("Hell");
    }

    @ParameterizedTest
    @ValueSource(strings = {"Hello\nWorld\n", "Hello\rWorld\r"})
    void chop_ShouldRemoveLastTwoCharacters_WhenStringHasMultipleLineFeeds(String input) {
        // When
        String actual = StringUtils.chop(input);

        // Then
        then(actual).isIn("Hello\nWorl", "Hello\rWorl");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" "})
    void contains_ShouldReturnFalse_WhenInputStringIsNullOrEmptyOrBlank(String input) {
        // Given
        String search = "*";

        // When
        boolean actual = StringUtils.contains(input, search);

        // Then
        then(actual).isFalse();
    }

    @ParameterizedTest
    @NullAndEmptySource
    void contains_ShouldReturnFalse_WhenSearchStringIsNullOrEmpty(String search) {
        // Given
        String input = "Hello World";

        // When
        boolean actual = StringUtils.contains(input, search);

        // Then
        then(actual).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {"Hello", "World", "Hello World", "H", "W", " "})
    void contains_ShouldReturnTrue_WhenStringContainsSearchString(String search) {
        // Given
        String input = "Hello World";

        // When
        boolean actual = StringUtils.contains(input, search);

        // Then
        then(actual).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"hello", "world", "hello world", "h", "w"})
    void contains_ShouldReturnFalse_WhenStringDoesNotContainSearchString(String search) {
        // Given
        String input = "Hello World";

        // When
        boolean actual = StringUtils.contains(input, search);

        // Then
        then(actual).isFalse();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" "})
    void containsAny_ShouldReturnFalse_WhenInputStringIsNullOrEmptyOrBlank(String input) {
        // Given
        String[] search = {"Hello", "World"};

        // When
        boolean actual = StringUtils.containsAny(input, search);

        // Then
        then(actual).isFalse();
    }

    @ParameterizedTest
    @NullAndEmptySource
    void containsAny_ShouldReturnFalse_WhenSearchStringIsNullOrEmpty(String search) {
        // Given
        String input = "Hello World";

        // When
        boolean actual = StringUtils.containsAny(input, search);

        // Then
        then(actual).isFalse();
    }

    @Test
    void containsAny_ShouldReturnFalse_WhenSearchStringIsNullOrEmptyElements() {
        // Given
        String input = "Hello World";
        String[] search = {null, null, "", ""};

        // When
        boolean actual = StringUtils.containsAny(input, search);

        // Then
        then(actual).isFalse();
    }

    @Test
    void containsAny_ShouldReturnTrue_WhenStringContainsAnyCharFromSearchString() {
        // Given
        String input = "Hello World";
        String[] search = {"Hello", "", null};

        // When
        boolean actual = StringUtils.containsAny(input, search);

        // Then
        then(actual).isTrue();
    }

    @Test
    void containsAny_ShouldReturnFalse_WhenStringDoesNotContainAnyCharFromSearchString() {
        // Given
        String input = "Hello World";
        String[] search = {"hello", "", null};

        // When
        boolean actual = StringUtils.containsAny(input, search);

        // Then
        then(actual).isFalse();
    }

    @Test
    void containsAny_ShouldReturnTrue_WhenStringContainsUnicodeCharFromSearchString() {
        // Given
        String input = "Hello θ World";
        String[] search = {"θ", "hello"};

        // When
        boolean actual = StringUtils.containsAny(input, search);

        // Then
        then(actual).isTrue();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"HelloWorld"})
    void containsWhitespace_ShouldReturnFalse_WhenInputStringIsNullOrEmptyOrHasNoWhitespace(String input) {
        // When
        boolean actual = StringUtils.containsWhitespace(input);

        // Then
        then(actual).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "Hello World", "Hello\tWorld", "Hello\nWorld", "Hello\u2002World", "Hello\t\nWorld"})
    void containsWhitespace_ShouldReturnTrue_WhenStringContainsSpace(String input) {
        // When
        boolean actual = StringUtils.containsWhitespace(input);

        // Then
        then(actual).isTrue();
    }

    @ParameterizedTest
    @NullAndEmptySource
    void countMatches_ShouldReturnZero_WhenInputStringIsNullOrEmpty(String input) {
        // Given
        String substring = "Hello";

        // When
        int actual = StringUtils.countMatches(input, substring);

        // Then
        then(actual).isZero();
    }

    @ParameterizedTest
    @NullAndEmptySource
    void countMatches_ShouldReturnZero_WhenSubStringIsNullOrEmpty(String substring) {
        // Given
        String input = "Hello";

        // When
        int actual = StringUtils.countMatches(input, substring);

        // Then
        then(actual).isZero();
    }

    @Test
    void countMatches_ShouldReturnZero_WhenInputStringIsBlankAndSubStringHasContent() {
        // Given
        String input = " ";
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
        String substring = " ";

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
    void collectionToDelimitedString_ShouldReturnEmptyString_WhenCollectionIsNull() {
        // When
        String actual = StringUtils.collectionToDelimitedString(null, ",", "[", "]");

        // Then
        then(actual).isEmpty();
    }

    @Test
    void collectionToDelimitedString_ShouldReturnEmptyString_WhenCollectionIsEmpty() {
        // Given
        List<String> emptyList = Collections.emptyList();

        // When
        String actual = StringUtils.collectionToDelimitedString(emptyList, ",", "[", "]");

        // Then
        then(actual).isEmpty();
    }

    @Test
    void collectionToDelimitedString_ShouldReturnDelimitedString_WhenSingleElement() {
        // Given
        List<String> list = List.of("Java");

        // When
        String actual = StringUtils.collectionToDelimitedString(list, ",", "[", "]");

        // Then
        then(actual).isEqualTo("[Java]");
    }

    @Test
    void collectionToDelimitedString_ShouldReturnDelimitedString_WhenMultipleElements() {
        // Given
        List<String> list = List.of("Java", "JavaScript", "C#");

        // When
        String actual = StringUtils.collectionToDelimitedString(list, ";", "[", "]");

        // Then
        then(actual).isEqualTo("[Java];[JavaScript];[C#]");
    }

    @Test
    void collectionToDelimitedString_ShouldHandleNullElement_WhenCollectionContainsNull() {
        // Given
        List<String> list = Arrays.asList("Java", null, "C#");

        // When
        String actual = StringUtils.collectionToDelimitedString(list, ",", "(", ")");

        // Then
        then(actual).isEqualTo("(Java),(null),(C#)");
    }

    @ParameterizedTest
    @NullAndEmptySource
    void defaultIfNotHasLength_ShouldReturnDefaultString_WhenInputStringIsNullOrEmpty(String input) {
        assertEquals("default", StringUtils.defaultIfNotHasLength(input, "default"));
    }

    @Test
    void defaultIfNotHasLength_ShouldReturnOriginalString_WhenInputStringHasLength() {
        assertEquals(" ", StringUtils.defaultIfNotHasLength(" ", "default"));
        assertEquals("Hello", StringUtils.defaultIfNotHasLength("Hello", "default"));
    }

    @Test
    void defaultIfNotHasLength_ShouldReturnNull_WhenInputStringIsNullAndDefaultStringIsNull() {
        assertNull(StringUtils.defaultIfNotHasLength(null, null));
    }

    @Test
    void defaultIfNotHasLength_ShouldReturnEmptyString_WhenInputStringIsNullAndDefaultStringIsEmpty() {
        assertEquals("", StringUtils.defaultIfNotHasLength(null, ""));
    }

    @Test
    void defaultIfNotHasLength_ShouldReturnOriginalString_WhenInputStringIsUnicodeWhitespace() {
        assertEquals("\u2002", StringUtils.defaultIfNotHasLength("\u2002", "default"));
    }

    @Test
    void defaultIfNotHasLength_ShouldReturnOriginalString_WhenInputStringHasUnicodeText() {
        assertEquals("θeta", StringUtils.defaultIfNotHasLength("θeta", "default"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void defaultIfNotHasText_ShouldReturnDefaultString_WhenInputStringIsNullOrEmpty(String input) {
        assertEquals("default", StringUtils.defaultIfNotHasText(input, "default"));
    }

    @Test
    void defaultIfNotHasText_ShouldReturnDefaultString_WhenInputStringIsBlank() {
        assertEquals("default", StringUtils.defaultIfNotHasText(" ", "default"));
    }

    @Test
    void defaultIfNotHasText_ShouldReturnOriginalString_WhenInputStringHasText() {
        assertEquals("Hello", StringUtils.defaultIfNotHasText("Hello", "default"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void defaultIfNotHasText_ShouldReturnNull_WhenInputStringIsNullOrEmptyAndDefaultStringIsNull(String input) {
        assertNull(StringUtils.defaultIfNotHasText(input, null));
    }

    @Test
    void defaultIfNotHasText_ShouldReturnNull_WhenInputStringIsBlankAndDefaultStringIsNull() {
        assertNull(StringUtils.defaultIfNotHasText(" ", null));
    }

    @Test
    void defaultIfNotHasText_ShouldReturnDefaultString_WhenInputStringIsUnicodeWhitespace() {
        assertEquals("default", StringUtils.defaultIfNotHasText("\u2002", "default"));
    }

    @Test
    void defaultIfNotHasText_ShouldReturnOriginalString_WhenInputStringHasUnicodeText() {
        assertEquals("θeta", StringUtils.defaultIfNotHasText("θeta", "default"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void deleteWhitespace_ShouldReturnNull_WhenInputStringIsNullOrEmpty(String input) {
        assertEquals(input, StringUtils.deleteWhitespace(input));
    }

    @Test
    void deleteWhitespace_ShouldReturnEmptyString_WhenInputStringIsBlank() {
        assertEquals("", StringUtils.deleteWhitespace(" "));
    }

    @Test
    void deleteWhitespace_ShouldReturnEmptyString_WhenInputStringContainsOnlyNewlines() {
        assertEquals("", StringUtils.deleteWhitespace("\n\r\n"));
    }

    @Test
    void deleteWhitespace_ShouldReturnOriginalString_WhenInputStringHasNoWhitespace() {
        assertEquals("Hello", StringUtils.deleteWhitespace("Hello"));
    }

    @Test
    void deleteWhitespace_ShouldRemoveAllWhitespace_WhenInputStringContainsWhitespace() {
        assertEquals("Hello", StringUtils.deleteWhitespace(" H e l l o "));
    }

    @Test
    void deleteWhitespace_ShouldRemoveMixedWhitespace_WhenInputStringContainsTabsAndSpaces() {
        assertEquals("Hello", StringUtils.deleteWhitespace("H\t e\nl l\to"));
    }

    @Test
    void deleteWhitespace_ShouldHandleUnicodeWhitespace_WhenInputStringContainsUnicodeWhitespace() {
        assertEquals("Hello", StringUtils.deleteWhitespace("H\u2000e\u2003l l o"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void difference_ShouldReturnEmptyString_WhenBothStringsAreNullOrEmpty(String input) {
        assertEquals(input, StringUtils.difference(input, input));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void difference_ShouldReturnString2_WhenString1IsNullOrEmpty(String input) {
        assertEquals("Hello", StringUtils.difference(input, "Hello"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void difference_ShouldReturnString1_WhenString2IsNullOrEmpty(String input) {
        assertEquals("Hello", StringUtils.difference("Hello", input));
    }

    @Test
    void difference_ShouldReturnEmptyString_WhenStringsAreIdentical() {
        assertEquals("", StringUtils.difference("Hello", "Hello"));
    }

    @Test
    void difference_ShouldReturnSubstringFromDifference_WhenStringsDiffer() {
        assertEquals("World", StringUtils.difference("Hello", "HelloWorld"));
    }

    @Test
    void difference_ShouldReturnString2_WhenStringsDifferFromStart() {
        assertEquals("World", StringUtils.difference("Hello", "World"));
    }

    @Test
    void difference_ShouldHandleUnicodeCharacters_WhenStringsDifferWithUnicode() {
        assertEquals("ẞHello", StringUtils.difference("ßest", "ẞHello"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void endsWithIgnoreCase_ShouldReturnFalse_WhenInputStringIsNullOrEmpty(String input) {
        assertFalse(StringUtils.endsWithIgnoreCase(input, "pre"));
    }

    @Test
    void endsWithIgnoreCase_ShouldReturnFalse_WhenInputStringIsBlank() {
        assertFalse(StringUtils.endsWithIgnoreCase(" ", "pre"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void endsWithIgnoreCase_ShouldReturnFalse_WhenSuffixIsNullOeEmpty(String suffix) {
        assertFalse(StringUtils.endsWithIgnoreCase("Hello", suffix));
    }

    @Test
    void endsWithIgnoreCase_ShouldReturnFalse_WhenSuffixIsBlank() {
        assertFalse(StringUtils.endsWithIgnoreCase("Hello", " "));
    }

    @Test
    void endsWithIgnoreCase_ShouldReturnTrue_WhenSuffixMatchesCaseSensitively() {
        assertTrue(StringUtils.endsWithIgnoreCase("Hello suffix", "suffix"));
    }

    @Test
    void endsWithIgnoreCase_ShouldReturnTrue_WhenSuffixMatchesCaseInsensitively() {
        assertTrue(StringUtils.endsWithIgnoreCase("Hello SUFFIX", "suffix"));
    }

    @Test
    void endsWithIgnoreCase_ShouldReturnFalse_WhenSuffixDoesNotMatch() {
        assertFalse(StringUtils.endsWithIgnoreCase("Hello", "suffix"));
    }

    @Test
    void endsWithIgnoreCase_ShouldReturnFalse_WhenStringShorterThanSuffix() {
        assertFalse(StringUtils.endsWithIgnoreCase("pre", "suffix"));
    }

    @Test
    void endsWithIgnoreCase_ShouldReturnTrue_WhenSuffixIsUnicodeAndMatches() {
        assertTrue(StringUtils.endsWithIgnoreCase("Hello ẞüñ", "ẞüñ"));
    }

    @Test
    void endsWithIgnoreCase_ShouldReturnFalse_WhenUnicodeSuffixDoesNotMatch() {
        assertFalse(StringUtils.endsWithIgnoreCase("üñ Hello", "ẞüñ"));
    }

    @Test
    void endsWithIgnoreCase_ShouldReturnTrue_WhenSingleCharSuffixMatchesCaseInsensitively() {
        assertTrue(StringUtils.endsWithIgnoreCase("Apple", "e"));
    }

    @Test
    void endsWithIgnoreCase_ShouldReturnTrue_WhenSuffixIsEntireString() {
        assertTrue(StringUtils.endsWithIgnoreCase("suffix", "suffix"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void firstHasText_ShouldReturnNull_WhenInputArrayIsNull(String input) {
        assertNull(StringUtils.firstHasText(input));
    }

    @Test
    void firstHasText_ShouldReturnNull_WhenAllStringsAreNull() {
        assertNull(StringUtils.firstHasText(null, null, null));
    }

    @Test
    void firstHasText_ShouldReturnNull_WhenAllStringsAreEmpty() {
        assertNull(StringUtils.firstHasText("", "", ""));
    }

    @Test
    void firstHasText_ShouldReturnNull_WhenAllStringsAreNullOrBlank() {
        assertNull(StringUtils.firstHasText("   ", " ", "\t", null));
    }

    @Test
    void firstHasText_ShouldReturnFirstNonBlankString_WhenArrayContainsValidString() {
        assertEquals("Hello", StringUtils.firstHasText(null, "", "   ", "Hello", "World"));
    }

    @Test
    void firstHasText_ShouldReturnFirstNonBlankString_WhenArrayStartsWithValidString() {
        assertEquals("Hello", StringUtils.firstHasText("Hello", "world", ""));
    }

    @Test
    void firstHasText_ShouldReturnSingleValidString_WhenArrayHasOneNonBlankString() {
        assertEquals("Hello", StringUtils.firstHasText("Hello"));
    }

    @Test
    void firstHasText_ShouldReturnUnicodeString_WhenFirstNonBlankStringIsUnicode() {
        assertEquals("ẞHello", StringUtils.firstHasText("   ", null, "ẞHello", "World"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void getDigits_ShouldReturnEmptyString_WhenInputStringIsNullOrEmpty(String input) {
        assertEquals("", StringUtils.getDigits(input));
    }

    @Test
    void getDigits_ShouldReturnEmptyString_WhenInputStringIsBlank() {
        assertEquals("", StringUtils.getDigits(" "));
    }

    @Test
    void getDigits_ShouldReturnEmptyString_WhenInputStringHasNoDigits() {
        assertEquals("", StringUtils.getDigits("Hello"));
    }

    @Test
    void getDigits_ShouldReturnDigitsOnly_WhenInputStringContainsDigitsAndLetters() {
        assertEquals("123", StringUtils.getDigits("H1e2l3lo"));
    }

    @Test
    void getDigits_ShouldReturnDigitsOnly_WhenInputStringContainsOnlyDigits() {
        assertEquals("123", StringUtils.getDigits("123"));
        assertEquals("123", StringUtils.getDigits(" 1 2 3 "));
    }

    @SuppressWarnings("UnnecessaryUnicodeEscape")
    @Test
    void getDigits_ShouldReturnUnicodeDigitOnly_WhenInputStringContainsDigitUnicode() {
        assertEquals("\u0967\u0968\u0969", StringUtils.getDigits("\u0967\u0968\u0969"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void hasLength_ShouldReturnFalse_WhenInputStringIsNullOrEmpty(String input) {
        assertFalse(StringUtils.hasLength(input));
    }

    @Test
    void hasLength_ShouldReturnTrue_WhenInputStringIsBlank() {
        assertTrue(StringUtils.hasLength(" "));
    }

    @Test
    void hasLength_ShouldReturnTrue_WhenInputStringHasNonWhitespaceCharacters() {
        assertTrue(StringUtils.hasLength("Hello"));
    }

    @Test
    void hasLength_ShouldReturnTrue_WhenInputStringIsSingleCharacter() {
        assertTrue(StringUtils.hasLength("a"));
    }

    @Test
    void hasLength_ShouldReturnTrue_WhenInputStringContainsWhitespaceAndText() {
        assertTrue(StringUtils.hasLength(" Hello "));
    }

    @Test
    void hasLength_ShouldReturnTrue_WhenInputStringIsUnicode() {
        assertTrue(StringUtils.hasLength("ẞHello"));
    }

    @Test
    void hasLength_ShouldReturnFalse_WhenAllStringsAreNull() {
        assertFalse(StringUtils.hasLength(null, null, null));
    }

    @Test
    void hasLength_ShouldReturnFalse_WhenAllStringsAreEmpty() {
        assertFalse(StringUtils.hasLength("", "", ""));
    }

    @Test
    void hasLength_ShouldReturnTrue_WhenAtLeastOneStringIsBlank() {
        assertTrue(StringUtils.hasLength(" ", "", null));
    }

    @Test
    void hasLength_ShouldReturnTrue_WhenAtLeastOneStringHasNonWhitespaceCharacters() {
        assertTrue(StringUtils.hasLength(null, "", "Hello"));
    }

    @Test
    void hasLength_ShouldReturnTrue_WhenStringIsUnicode() {
        assertTrue(StringUtils.hasLength(null, "ẞHello", ""));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void hasLengthAll_ShouldReturnFalse_WhenInputArrayIsNullOrEmpty(String... input) {
        assertFalse(StringUtils.hasLengthAll(input));
    }

    @Test
    void hasLengthAll_ShouldReturnFalse_WhenAnyStringIsNullOrEmpty() {
        assertFalse(StringUtils.hasLengthAll("Hello", null, "World"));
        assertFalse(StringUtils.hasLengthAll("Hello", "", "World"));
    }

    @Test
    void hasLengthAll_ShouldReturnTrue_WhenAllStringsAreBlank() {
        assertTrue(StringUtils.hasLengthAll("   ", " ", "\t"));
    }

    @Test
    void hasLengthAll_ShouldReturnTrue_WhenAllStringsHaveNonWhitespaceCharacters() {
        assertTrue(StringUtils.hasLengthAll("Hello", "World"));
    }

    @Test
    void hasLengthAll_ShouldReturnTrue_WhenAllStringsAreUnicode() {
        assertTrue(StringUtils.hasLengthAll("ẞHello", "üser"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void hasText_ShouldReturnFalse_WhenInputStringIsNullOrEmpty(String input) {
        assertFalse(StringUtils.hasText(input));
    }

    @Test
    void hasText_ShouldReturnFalse_WhenInputStringIsBlank() {
        assertFalse(StringUtils.hasText(" "));
    }

    @Test
    void hasText_ShouldReturnTrue_WhenInputStringHasNonWhitespaceCharacters() {
        assertTrue(StringUtils.hasText("Hello"));
    }

    @Test
    void hasText_ShouldReturnTrue_WhenInputStringContainsWhitespaceAndText() {
        assertTrue(StringUtils.hasText(" Hello "));
    }

    @Test
    void hasText_ShouldReturnTrue_WhenInputStringIsUnicode() {
        assertTrue(StringUtils.hasText("ẞHello"));
    }

    @Test
    void hasText_ShouldReturnFalse_WhenAllStringsAreNull() {
        assertFalse(StringUtils.hasText(null, null, null));
    }

    @Test
    void hasText_ShouldReturnFalse_WhenAllStringsAreEmpty() {
        assertFalse(StringUtils.hasText("", "", ""));
    }

    @Test
    void hasText_ShouldReturnFalse_WhenAllStringsAreBlank() {
        assertFalse(StringUtils.hasText("   ", " ", "\t"));
    }

    @Test
    void hasText_ShouldReturnTrue_WhenAtLeastOneStringHasNonWhitespaceCharacters() {
        assertTrue(StringUtils.hasText(null, "", "Hello"));
    }

    @Test
    void hasText_ShouldReturnTrue_WhenStringIsUnicode() {
        assertTrue(StringUtils.hasText(null, "ẞHello", ""));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void hasTextAll_ShouldReturnFalse_WhenInputArrayIsNullOrEmpty(String... input) {
        assertFalse(StringUtils.hasTextAll(input));
    }

    @Test
    void hasTextAll_ShouldReturnFalse_WhenAnyStringIsNullOrEmpty() {
        assertFalse(StringUtils.hasTextAll("Hello", null, "World"));
        assertFalse(StringUtils.hasTextAll("Hello", "", "World"));
    }

    @Test
    void hasTextAll_ShouldReturnFalse_WhenAnyStringIsBlank() {
        assertFalse(StringUtils.hasTextAll("Hello", " ", "World"));
    }

    @Test
    void hasTextAll_ShouldReturnTrue_WhenAllStringsHaveNonWhitespaceCharacters() {
        assertTrue(StringUtils.hasTextAll("Hello", "World"));
    }

    @Test
    void hasTextAll_ShouldReturnTrue_WhenAllStringsAreUnicode() {
        assertTrue(StringUtils.hasTextAll("ẞHello", "üser"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" "})
    void indexOfAny_ShouldReturnIndexNotFound_WhenInputStringIsNullOrEmptyOrBlank(String input) {
        // Given
        String[] search = {"lo", "or", "ld"};

        // When
        int actual = StringUtils.indexOfAny(input, search);

        // Then
        then(actual).isEqualTo(INDEX_NOT_FOUND);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void indexOfAny_ShouldReturnIndexNotFound_WhenSearchArrayIsNullOrEmpty(String... search) {
        // Given
        String input = "Hello World";

        // When
        int actual = StringUtils.indexOfAny(input, search);

        // Then
        then(actual).isEqualTo(INDEX_NOT_FOUND);
    }

    @Test
    void indexOfAny_ShouldReturnFirstOccurrenceIndex_WhenMultipleMatchesExist() {
        // Given
        String string = "Hello World";
        String[] search = {"lo", "or", "ld"};

        // When
        int actual = StringUtils.indexOfAny(string, search);

        // Then
        then(actual).isEqualTo(3);
    }

    @Test
    void indexOfAny_ShouldReturnIndexNotFound_WhenNoMatchExists() {
        // Given
        String string = "Hello World";
        String[] search = {"xyz", "abc"};

        // When
        int actual = StringUtils.indexOfAny(string, search);

        // Then
        then(actual).isEqualTo(INDEX_NOT_FOUND);
    }

    @Test
    void indexOfAny_ShouldReturnCorrectIndex_WhenSingleMatchExists() {
        // Given
        String string = "abc123";
        String[] search = {"123"};

        // When
        int actual = StringUtils.indexOfAny(string, search);

        // Then
        then(actual).isEqualTo(3);
    }

    @Test
    void indexOfAny_ShouldReturnEarliestIndex_WhenMultipleStringsMatch() {
        // Given
        String string = "abc123def";
        String[] search = {"bc", "123", "def"};

        // When
        int actual = StringUtils.indexOfAny(string, search);

        // Then
        then(actual).isEqualTo(1);
    }

    @Test
    void indexOfAny_ShouldReturnIndexNotFound_WhenInputStringIsCaseSensitive() {
        // Given
        String string = "Hello World";
        String[] search = {"hello", "WORLD"};

        // When
        int actual = StringUtils.indexOfAny(string, search);

        // Then
        then(actual).isEqualTo(INDEX_NOT_FOUND); // Case-sensitive, no match
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" "})
    void lastIndexOfAny_ShouldReturnIndexNotFound_WhenStringIsNullOrEmptyOrBlank(String input) {
        // Given
        String[] search = {"lo", "or", "ld"};

        // When
        int actual = StringUtils.lastIndexOfAny(input, search);

        // Then
        then(actual).isEqualTo(INDEX_NOT_FOUND);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void lastIndexOfAny_ShouldReturnIndexNotFound_WhenSearchArrayIsNullOrEmpty(String... search) {
        // Given
        String string = "Hello World";

        // When
        int actual = StringUtils.lastIndexOfAny(string, search);

        // Then
        then(actual).isEqualTo(INDEX_NOT_FOUND);
    }

    @Test
    void lastIndexOfAny_ShouldReturnLastIndex_WhenSingleSearchStringIsFound() {
        // Given
        String input = "Hello World";
        String[] search = {"World"};

        // When
        int actual = StringUtils.lastIndexOfAny(input, search);

        // Then
        then(actual).isEqualTo(6);
    }

    @Test
    void lastIndexOfAny_ShouldReturnLastIndex_WhenMultipleSearchStringsAreFound() {
        // Given
        String input = "hello world, hello universe";
        String[] search = {"world", "universe"};

        // When
        int actual = StringUtils.lastIndexOfAny(input, search);

        // Then
        then(actual).isEqualTo(19);
    }

    @Test
    void lastIndexOfAny_ShouldReturnIndexNotFound_WhenNoSearchStringIsFound() {
        // Given
        String input = "hello world";
        String[] search = {"java", "python"};

        // When
        int actual = StringUtils.lastIndexOfAny(input, search);

        // Then
        then(actual).isEqualTo(INDEX_NOT_FOUND);
    }

    @Test
    void lastIndexOfAny_ShouldReturnLastIndex_WhenSearchStringAppearsMultipleTimes() {
        // Given
        String input = "hello hello world";
        String[] search = {"hello"};

        // When
        int actual = StringUtils.lastIndexOfAny(input, search);

        // Then
        then(actual).isEqualTo(6);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void isAllLowerCase_ShouldReturnFalse_WhenInputStringIsNullOrEmpty(String input) {
        assertFalse(StringUtils.isAllLowerCase(input));
    }

    @Test
    void isAllLowerCase_ShouldReturnFalse_WhenInputStringIsBlank() {
        assertFalse(StringUtils.isAllLowerCase(" "));
    }

    @Test
    void isAllLowerCase_ShouldReturnFalse_WhenInputStringContainsUpperCaseLetters() {
        assertFalse(StringUtils.isAllLowerCase("WeLlo"));
    }

    @Test
    void isAllLowerCase_ShouldReturnFalse_WhenInputStringContainsDigitsAndSpecialCharacters() {
        assertFalse(StringUtils.isAllLowerCase("hell0"));
    }

    @Test
    void isAllLowerCase_ShouldReturnFalse_WhenInputStringContainsSpecialCharacters() {
        assertFalse(StringUtils.isAllLowerCase("hel@lo"));
    }

    @Test
    void isAllLowerCase_ShouldReturnFalse_WhenInputStringContainsUnicodeUpperCaseLetters() {
        assertFalse(StringUtils.isAllLowerCase("ẞüñ"));
    }

    @Test
    void isAllLowerCase_ShouldReturnTrue_WhenInputStringContainsOnlyLowerCaseLetters() {
        assertTrue(StringUtils.isAllLowerCase("hello"));
    }

    @Test
    void isAllLowerCase_ShouldReturnTrue_WhenInputStringIsSingleLowerCaseLetter() {
        assertTrue(StringUtils.isAllLowerCase("a"));
    }

    @Test
    void isAllLowerCase_ShouldReturnTrue_WhenInputStringIsUnicodeLowerCaseLetters() {
        assertTrue(StringUtils.isAllLowerCase("üñ"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void isAllUpperCase_ShouldReturnFalse_WhenInputStringIsNullOrEmpty(String input) {
        assertFalse(StringUtils.isAllUpperCase(input));
    }

    @Test
    void isAllUpperCase_ShouldReturnFalse_WhenInputStringIsBlank() {
        assertFalse(StringUtils.isAllUpperCase(" "));
    }

    @Test
    void isAllUpperCase_ShouldReturnFalse_WhenInputStringContainsLowerCaseLetters() {
        assertFalse(StringUtils.isAllUpperCase("HElLO"));
    }

    @Test
    void isAllUpperCase_ShouldReturnFalse_WhenInputStringContainsDigits() {
        assertFalse(StringUtils.isAllUpperCase("HELL0"));
    }

    @Test
    void isAllUpperCase_ShouldReturnFalse_WhenInputStringContainsSpecialCharacters() {
        assertFalse(StringUtils.isAllUpperCase("HEL@LO"));
    }

    @Test
    void isAllUpperCase_ShouldReturnFalse_WhenInputStringContainsUnicodeLowerCaseLetters() {
        assertFalse(StringUtils.isAllUpperCase("ẞüÑ"));
    }

    @Test
    void isAllUpperCase_ShouldReturnTrue_WhenInputStringContainsOnlyUpperCaseLetters() {
        assertTrue(StringUtils.isAllUpperCase("HELLO"));
    }

    @Test
    void isAllUpperCase_ShouldReturnTrue_WhenInputStringIsSingleUpperCaseLetter() {
        assertTrue(StringUtils.isAllUpperCase("A"));
    }

    @Test
    void isAllUpperCase_ShouldReturnTrue_WhenInputStringIsUnicodeUpperCaseLetters() {
        assertTrue(StringUtils.isAllUpperCase("ẞÜÑ"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void isAlpha_ShouldReturnFalse_WhenInputStringIsNullOrEmpty(String input) {
        assertFalse(StringUtils.isAlpha(input));
    }

    @Test
    void isAlpha_ShouldReturnFalse_WhenInputStringIsBlank() {
        assertFalse(StringUtils.isAlpha(" "));
    }

    @Test
    void isAlpha_ShouldReturnFalse_WhenInputStringContainsDigits() {
        assertFalse(StringUtils.isAlpha("Hell0"));
    }

    @Test
    void isAlpha_ShouldReturnFalse_WhenInputStringContainsWhitespace() {
        assertFalse(StringUtils.isAlpha("Hel lo"));
    }

    @Test
    void isAlpha_ShouldReturnFalse_WhenInputStringContainsSpecialCharacters() {
        assertFalse(StringUtils.isAlpha("Hel@lo"));
    }

    @Test
    void isAlpha_ShouldReturnFalse_WhenInputStringContainsUnicodeNonLetters() {
        assertFalse(StringUtils.isAlpha("ẞüñ1"));
    }

    @Test
    void isAlpha_ShouldReturnTrue_WhenInputStringContainsOnlyLetters() {
        assertTrue(StringUtils.isAlpha("Hello"));
    }

    @Test
    void isAlpha_ShouldReturnTrue_WhenInputStringIsSingleLetter() {
        assertTrue(StringUtils.isAlpha("a"));
    }

    @Test
    void isAlpha_ShouldReturnTrue_WhenInputStringIsUnicodeLetters() {
        assertTrue(StringUtils.isAlpha("ẞüñ"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void isAlphanumeric_ShouldReturnFalse_WhenInputStringIsNullOrEmpty(String input) {
        assertFalse(StringUtils.isAlphanumeric(input));
    }

    @Test
    void isAlphanumeric_ShouldReturnFalse_WhenInputStringIsBlank() {
        assertFalse(StringUtils.isAlphanumeric(" "));
    }

    @Test
    void isAlphanumeric_ShouldReturnFalse_WhenInputStringContainsWhitespace() {
        assertFalse(StringUtils.isAlphanumeric("Hel lo"));
    }

    @Test
    void isAlphanumeric_ShouldReturnFalse_WhenInputStringContainsSpecialCharacters() {
        assertFalse(StringUtils.isAlphanumeric("Hel@lo"));
    }

    @Test
    void isAlphanumeric_ShouldReturnFalse_WhenInputStringContainsUnicodeNonAlphanumeric() {
        assertFalse(StringUtils.isAlphanumeric("ẞüñ@"));
    }

    @Test
    void isAlphanumeric_ShouldReturnTrue_WhenInputStringContainsOnlyLetters() {
        assertTrue(StringUtils.isAlphanumeric("Hello"));
    }

    @Test
    void isAlphanumeric_ShouldReturnTrue_WhenInputStringContainsOnlyDigits() {
        assertTrue(StringUtils.isAlphanumeric("123"));
    }

    @Test
    void isAlphanumeric_ShouldReturnTrue_WhenInputStringContainsLettersAndDigits() {
        assertTrue(StringUtils.isAlphanumeric("Hello123"));
    }

    @Test
    void isAlphanumeric_ShouldReturnTrue_WhenInputStringIsSingleAlphanumericCharacter() {
        assertTrue(StringUtils.isAlphanumeric("a"));
    }

    @Test
    void isAlphanumeric_ShouldReturnTrue_WhenInputStringIsUnicodeLettersAndDigits() {
        assertTrue(StringUtils.isAlphanumeric("ẞüñ123"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void isAlphanumericSpace_ShouldReturnFalse_WhenInputStringIsNullOrEmpty(String input) {
        assertFalse(StringUtils.isAlphanumericSpace(input));
    }

    @Test
    void isAlphanumericSpace_ShouldReturnFalse_WhenInputStringContainsSpecialCharacters() {
        assertFalse(StringUtils.isAlphanumericSpace("hel@lo"));
    }

    @Test
    void isAlphanumericSpace_ShouldReturnFalse_WhenInputStringContainsUnicodeNonAlphanumeric() {
        assertFalse(StringUtils.isAlphanumericSpace("ẞüñ@"));
    }

    @Test
    void isAlphanumericSpace_ShouldReturnTrue_WhenInputStringIsBlank() {
        assertTrue(StringUtils.isAlphanumericSpace(" "));
    }

    @Test
    void isAlphanumericSpace_ShouldReturnTrue_WhenInputStringContainsOnlyLetters() {
        assertTrue(StringUtils.isAlphanumericSpace("Hello"));
    }

    @Test
    void isAlphanumericSpace_ShouldReturnTrue_WhenInputStringContainsOnlyDigits() {
        assertTrue(StringUtils.isAlphanumericSpace("123"));
    }

    @Test
    void isAlphanumericSpace_ShouldReturnTrue_WhenInputStringContainsLettersDigitsAndSpaces() {
        assertTrue(StringUtils.isAlphanumericSpace("Hello 123"));
    }

    @Test
    void isAlphanumericSpace_ShouldReturnTrue_WhenInputStringIsSingleLetterOrDigit() {
        assertTrue(StringUtils.isAlphanumericSpace("a"));
    }

    @Test
    void isAlphanumericSpace_ShouldReturnTrue_WhenInputStringIsUnicodeLettersAndDigits() {
        assertTrue(StringUtils.isAlphanumericSpace("ẞüñ123"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void isAlphaSpace_ShouldReturnFalse_WhenInputStringIsNullOrEmpty(String input) {
        assertFalse(StringUtils.isAlphaSpace(input));
    }

    @Test
    void isAlphaSpace_ShouldReturnFalse_WhenInputStringContainsDigits() {
        assertFalse(StringUtils.isAlphaSpace("Hell0"));
    }

    @Test
    void isAlphaSpace_ShouldReturnFalse_WhenInputStringContainsSpecialCharacters() {
        assertFalse(StringUtils.isAlphaSpace("hel@lo"));
    }

    @Test
    void isAlphaSpace_ShouldReturnFalse_WhenInputStringContainsUnicodeNonLetters() {
        assertFalse(StringUtils.isAlphaSpace("ẞüñ1"));
    }

    @Test
    void isAlphaSpace_ShouldReturnTrue_WhenInputStringIsSingleSpace() {
        assertTrue(StringUtils.isAlphaSpace(" "));
    }

    @Test
    void isAlphaSpace_ShouldReturnTrue_WhenInputStringContainsOnlyLetters() {
        assertTrue(StringUtils.isAlphaSpace("hello"));
    }

    @Test
    void isAlphaSpace_ShouldReturnTrue_WhenInputStringContainsLettersAndSpaces() {
        assertTrue(StringUtils.isAlphaSpace("Hel lo"));
    }

    @Test
    void isAlphaSpace_ShouldReturnTrue_WhenInputStringIsSingleLetter() {
        assertTrue(StringUtils.isAlphaSpace("a"));
    }

    @Test
    void isAlphaSpace_ShouldReturnTrue_WhenInputStringIsUnicodeLetters() {
        assertTrue(StringUtils.isAlphaSpace("ẞüñ"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void isNumeric_ShouldReturnFalse_WhenInputStringIsNullOrEmpty(String input) {
        assertFalse(StringUtils.isNumeric(input));
    }

    @Test
    void isNumeric_ShouldReturnFalse_WhenInputStringIsBlank() {
        assertFalse(StringUtils.isNumeric(" "));
    }

    @Test
    void isNumeric_ShouldReturnFalse_WhenInputStringContainsLetters() {
        assertFalse(StringUtils.isNumeric("12a34"));
    }

    @Test
    void isNumeric_ShouldReturnFalse_WhenInputStringHasMultipleDecimalPoints() {
        assertFalse(StringUtils.isNumeric("12.34.56"));
    }

    @Test
    void isNumeric_ShouldReturnFalse_WhenInputStringHasOnlyPlusSign() {
        assertFalse(StringUtils.isNumeric("+"));
    }

    @Test
    void isNumeric_ShouldReturnFalse_WhenInputStringHasOnlyMinusSign() {
        assertFalse(StringUtils.isNumeric("-"));
    }

    @Test
    void isNumeric_ShouldReturnFalse_WhenInputStringHasOnlyDecimalPoint() {
        assertFalse(StringUtils.isNumeric("."));
    }

    @Test
    void isNumeric_ShouldReturnFalse_WhenInputStringHasDecimalPointAtEnd() {
        assertFalse(StringUtils.isNumeric("12."));
    }

    @Test
    void isNumeric_ShouldReturnFalse_WhenInputStringContainsWhitespace() {
        assertFalse(StringUtils.isNumeric("12 34"));
    }

    @Test
    void isNumeric_ShouldReturnFalse_WhenInputStringContainsSpecialCharacters() {
        assertFalse(StringUtils.isNumeric("12@34"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"123", "+123", "۱۲۳", "+۱۲۳"})
    void isNumeric_ShouldReturnTrue_WhenInputStringIsPositiveInteger(String input) {
        assertTrue(StringUtils.isNumeric(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"-123", "-۱۲۳"})
    void isNumeric_ShouldReturnTrue_WhenInputStringIsNegativeInteger(String input) {
        assertTrue(StringUtils.isNumeric(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"12.34", "+12.34", "۱۲.۳۴", "+۱۲.۳۴"})
    void isNumeric_ShouldReturnTrue_WhenInputStringIsPositiveDecimal(String input) {
        assertTrue(StringUtils.isNumeric(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"-12.34", "-۱۲.۳۴"})
    void isNumeric_ShouldReturnTrue_WhenInputStringIsNegativeDecimal(String input) {
        assertTrue(StringUtils.isNumeric(input));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void isNumericSpace_ShouldReturnFalse_WhenInputStringIsNullOrEmpty(String input) {
        assertFalse(StringUtils.isNumericSpace(input));
    }

    @Test
    void isNumericSpace_ShouldReturnFalse_WhenInputStringIsBlank() {
        assertFalse(StringUtils.isNumericSpace(" "));
    }

    @Test
    void isNumericSpace_ShouldReturnFalse_WhenInputStringContainsLetters() {
        assertFalse(StringUtils.isNumericSpace("12a34"));
    }

    @Test
    void isNumericSpace_ShouldReturnFalse_WhenInputStringContainsSpecialCharacters() {
        assertFalse(StringUtils.isNumericSpace("12@34"));
    }

    @Test
    void isNumericSpace_ShouldReturnFalse_WhenInputStringContainsUnicodeNonDigits() {
        assertFalse(StringUtils.isNumericSpace("12ẞ34"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"123", "۱۲۳"})
    void isNumericSpace_ShouldReturnTrue_WhenInputStringContainsOnlyDigits(String input) {
        assertTrue(StringUtils.isNumericSpace(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"12 34", "  12 34  ", "۱۲ ۳۴", "  ۱۲ ۳۴  "})
    void isNumericSpace_ShouldReturnTrue_WhenInputStringContainsDigitsAndSpaces(String input) {
        assertTrue(StringUtils.isNumericSpace(input));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void isWhitespace_ShouldReturnFalse_WhenInputStringIsNullOrEmpty(String input) {
        assertFalse(StringUtils.isWhitespace(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Hello", " Hello ", "123", " 123 ", "@", " @ ", "ẞüñ", " ẞüñ "})
    void isWhitespace_ShouldReturnFalse_WhenInputStringContainsCharacter(String input) {
        assertFalse(StringUtils.isWhitespace(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "  ", "\t\n\r", "\u2000\u2003"})
    void isWhitespace_ShouldReturnTrue_WhenInputStringIsSpace(String input) {
        assertTrue(StringUtils.isWhitespace(input));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void left_ShouldReturnOriginalString_WhenInputStringIsNullOrEmpty(String input) {
        assertEquals(input, StringUtils.left(input, 3));
    }

    @Test
    void left_ShouldThrowIllegalArgumentException_WhenLengthIsNegative() {
        assertThrowsExactly(
                IllegalArgumentException.class,
                () -> StringUtils.left("Hello", -1),
                "Length must be positive");
    }

    @Test
    void left_ShouldReturnOriginalString_WhenLengthIsEqualToStringLength() {
        assertEquals("Hello", StringUtils.left("Hello", 5));
    }

    @Test
    void left_ShouldReturnOriginalString_WhenLengthIsGreaterThanStringLength() {
        assertEquals("Hello", StringUtils.left("Hello", 10));
    }

    @Test
    void left_ShouldReturnSubstring_WhenLengthIsLessThanStringLength() {
        assertEquals("Hel", StringUtils.left("Hello", 3));
    }

    @Test
    void left_ShouldReturnEmptyString_WhenLengthIsZero() {
        assertEquals("", StringUtils.left("Hello", 0));
    }

    @Test
    void left_ShouldReturnOriginalString_WhenInputStringIsSingleCharacter() {
        assertEquals("a", StringUtils.left("a", 1));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void length_ShouldReturnZero_WhenInputStringIsNull(String input) {
        assertEquals(0, StringUtils.length(input));
    }

    @Test
    void length_ShouldReturnCorrectLength_WhenInputStringIsNonEmpty() {
        assertEquals(1, StringUtils.length(" "));
        assertEquals(5, StringUtils.length("Hello"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void mid_ShouldReturnOriginalString_WhenInputStringIsNullOrEmpty(String input) {
        assertEquals(input, StringUtils.mid(input, 1, 3));
    }

    @Test
    void mid_ShouldThrowIllegalArgumentException_WhenLengthIsNegative() {
        assertThrowsExactly(
                IllegalArgumentException.class,
                () -> StringUtils.mid("hello", 1, -1),
                "Length must be positive");
    }

    @Test
    void mid_ShouldThrowIllegalArgumentException_WhenPositionIsNegative() {
        assertThrowsExactly(
                IllegalArgumentException.class,
                () -> StringUtils.mid("hello", -1, 3), "Position must be positive");
    }

    @Test
    void mid_ShouldReturnEmptyString_WhenPositionIsBeyondStringLength() {
        assertEquals("", StringUtils.mid("Hello", 6, 3));
    }

    @Test
    void mid_ShouldReturnEmptyString_WhenLengthIsZero() {
        assertEquals("", StringUtils.mid("Hello", 1, 0));
    }

    @Test
    void mid_ShouldReturnSubstring_WhenPositionAndLengthAreValid() {
        assertEquals("ell", StringUtils.mid("Hello", 1, 3));
    }

    @Test
    void mid_ShouldReturnRemainingString_WhenLengthExceedsRemainingString() {
        assertEquals("lo", StringUtils.mid("Hello", 3, 5));
    }

    @Test
    void mid_ShouldReturnSubstring_WhenPositionIsZeroAndLengthIsValid() {
        assertEquals("He", StringUtils.mid("Hello", 0, 2));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void normalizeSpace_ShouldReturnOriginalString_WhenInputStringIsNullOrEmpty(String input) {
        assertEquals(input, StringUtils.normalizeSpace(input));
    }

    @Test
    void normalizeSpace_ShouldReturnEmptyString_WhenInputStringIsBlank() {
        assertEquals("", StringUtils.normalizeSpace(" "));
    }

    @Test
    void normalizeSpace_ShouldReturnEmptyString_WhenInputStringHasOnlyUnicodeWhitespace() {
        assertEquals("", StringUtils.normalizeSpace("\u2000\u2003"));
    }

    @Test
    void normalizeSpace_ShouldReturnOriginalString_WhenInputStringHasNoWhitespace() {
        assertEquals("Hello", StringUtils.normalizeSpace("Hello"));
    }

    @Test
    void normalizeSpace_ShouldNormalizeMultipleSpaces_WhenInputStringHasMultipleSpaces() {
        assertEquals("Hello World", StringUtils.normalizeSpace("  Hello   World  "));
    }

    @Test
    void normalizeSpace_ShouldNormalizeMixedWhitespace_WhenInputStringHasTabsAndNewlines() {
        assertEquals("Hello World", StringUtils.normalizeSpace("Hello\t\nWorld"));
    }

    @Test
    void normalizeSpace_ShouldReplaceNonBreakingSpace_WhenInputStringHasUnicodeNonBreakingSpace() {
        assertEquals("Hello World", StringUtils.normalizeSpace("Hello\u00A0World"));
    }

    @Test
    void normalizeSpace_ShouldReturnSingleWord_WhenInputStringIsSingleWordWithSurroundingSpaces() {
        assertEquals("Hello", StringUtils.normalizeSpace("  Hello  "));
    }

    @Test
    void normalizeSpace_ShouldReturnUnicodeString_WhenInputStringHasUnicodeCharacters() {
        assertEquals("ẞüñ World", StringUtils.normalizeSpace("ẞüñ   World"));
    }

    @Test
    void overlay_ShouldReturnNull_WhenInputStringIsNull() {
        assertNull(StringUtils.overlay(null, "Hello", 1, 2));
    }

    @Test
    void overlay_ShouldThrowIllegalArgumentException_WhenPositionIsNegative() {
        assertThrowsExactly(
                IllegalArgumentException.class,
                () -> StringUtils.overlay("hello", "test", -1, 2),
                "Length must be positive");
    }

    @Test
    void overlay_ShouldThrowIllegalArgumentException_WhenLengthIsNegative() {
        assertThrowsExactly(
                IllegalArgumentException.class,
                () -> StringUtils.overlay("hello", "test", 1, -2),
                "Position must be positive");
    }

    @Test
    void overlay_ShouldReturnOverlay_WhenInputStringIsEmpty() {
        assertEquals("Hello", StringUtils.overlay("", "Hello", 0, 0));
    }

    @Test
    void overlay_ShouldReplaceSegment_WhenPositionAndLengthAreValid() {
        assertEquals("Hetesto", StringUtils.overlay("Hello", "test", 2, 4));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void overlay_ShouldReplaceWithEmpty_WhenOverlayIsNullOrEmpty(String input) {
        assertEquals("Heo", StringUtils.overlay("Hello", input, 2, 4));
    }

    @Test
    void overlay_ShouldAdjustPosition_WhenPositionExceedsStringLength() {
        assertEquals("He World", StringUtils.overlay("Hello", " World", 10, 2));
    }

    @Test
    void overlay_ShouldAdjustLength_WhenLengthExceedsStringLength() {
        assertEquals("He World", StringUtils.overlay("Hello", " World", 2, 10));
    }

    @Test
    void overlay_ShouldSwapPositionAndLength_WhenPositionIsGreaterThanLength() {
        assertEquals("Hetesto", StringUtils.overlay("Hello", "test", 4, 2));
    }

    @Test
    void overlay_ShouldReturnOverlayAtStart_WhenPositionIsZero() {
        assertEquals("World llo", StringUtils.overlay("Hello", "World ", 0, 2));
    }

    @Test
    void overlay_ShouldReturnUnicodeString_WhenInputContainsUnicodeCharacters() {
        assertEquals("ẞütestñ", StringUtils.overlay("ẞüñ", "test", 2, 2));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void replace_ShouldReturnEmptyString_WhenInputStringIsNullOrEmpty(String input) {
        assertEquals(input, StringUtils.replace(input, "old", "new"));
    }

    @Test
    void replace_ShouldReturnBlankString_WhenInputStringIsBlank() {
        assertEquals(" ", StringUtils.replace(" ", "old", "new"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void replace_ShouldReturnOriginalString_WhenOldPatternIsNullOrEmpty(String oldPattern) {
        assertEquals("Hello", StringUtils.replace("Hello", oldPattern, "new"));
    }

    @Test
    void replace_ShouldReturnOriginalString_WhenNewPatternIsNull() {
        assertEquals("Hello", StringUtils.replace("Hello", "old", null));
    }

    @Test
    void replace_ShouldReturnOriginalString_WhenPatternNotFound() {
        assertEquals("Hello", StringUtils.replace("Hello", "World", "new"));
    }

    @Test
    void replace_ShouldReplaceSingleOccurrence_WhenPatternFound() {
        assertEquals("Hello Grok", StringUtils.replace("Hello World", "World", "Grok"));
    }

    @Test
    void replace_ShouldReplaceMultipleOccurrences_WhenPatternFoundMultipleTimes() {
        assertEquals("Hnewlolo", StringUtils.replace("Hellolo", "el", "new"));
    }

    @Test
    void replace_ShouldReplaceWithShorterPattern_WhenNewPatternIsShorter() {
        assertEquals("Hlo", StringUtils.replace("Hello", "el", ""));
    }

    @Test
    void replace_ShouldReplaceWithLongerPattern_WhenNewPatternIsLonger() {
        assertEquals("Hnewnewllo", StringUtils.replace("Hello", "e", "newnew"));
    }

    @Test
    void replace_ShouldReplaceUnicodePattern_WhenPatternIsUnicode() {
        assertEquals("ẞnewñ", StringUtils.replace("ẞüñ", "ü", "new"));
    }

    @Test
    void replace_ShouldReturnEmptyString_WhenReplacingAllWithEmpty() {
        assertEquals("", StringUtils.replace("aaa", "a", ""));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void remove_ShouldReturnOriginalString_WhenInputStringIsNullOrEmpty(String input) {
        assertEquals(input, StringUtils.remove(input, 'a'));
    }

    @Test
    void remove_ShouldReturnOriginalString_WhenCharacterNotFound() {
        assertEquals("Hello", StringUtils.remove("Hello", 'x'));
    }

    @Test
    void remove_ShouldRemoveAllOccurrences_WhenCharacterExists() {
        assertEquals("Heo", StringUtils.remove("Hello", 'l'));
    }

    @Test
    void remove_ShouldRemoveAllOccurrences_WhenCharacterIsWhitespace() {
        assertEquals("Hello", StringUtils.remove("H e l l o", ' '));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void removeStart_ShouldReturnOriginalString_WhenInputStringIsNullOrEmpty(String input) {
        assertEquals(input, StringUtils.removeStart(input, 'a'));
    }

    @Test
    void removeStart_ShouldReturnSubstring_WhenFirstCharacterMatches() {
        assertEquals("ello", StringUtils.removeStart("Hello", 'H'));
    }

    @Test
    void removeStart_ShouldReturnOriginalString_WhenFirstCharacterDoesNotMatch() {
        assertEquals("Hello", StringUtils.removeStart("Hello", 'x'));
    }

    @Test
    void removeStart_ShouldReturnEmptyString_WhenSingleCharacterMatches() {
        assertEquals("", StringUtils.removeStart("a", 'a'));
    }

    @Test
    void removeStart_ShouldReturnSubstring_WhenFirstCharacterIsWhitespaceAndMatches() {
        assertEquals("Hello", StringUtils.removeStart(" Hello", ' '));
    }

    @Test
    void removeStart_ShouldRemoveUnicodeCharacter_WhenFirstCharacterIsUnicodeAndMatches() {
        assertEquals("üñ", StringUtils.removeStart("ẞüñ", 'ẞ'));
    }

    @Test
    void removeStart_ShouldReturnOriginalString_WhenFirstCharacterIsUnicodeAndDoesNotMatch() {
        assertEquals("ẞüñ", StringUtils.removeStart("ẞüñ", 'ü'));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void removeEnd_ShouldReturnOriginalString_WhenInputStringIsNullOrEmpty(String input) {
        assertEquals(input, StringUtils.removeEnd(input, 'a'));
    }

    @Test
    void removeEnd_ShouldReturnSubstring_WhenLastCharacterMatches() {
        assertEquals("Hell", StringUtils.removeEnd("Hello", 'o'));
    }

    @Test
    void removeEnd_ShouldReturnOriginalString_WhenLastCharacterDoesNotMatch() {
        assertEquals("Hello", StringUtils.removeEnd("Hello", 'x'));
    }

    @Test
    void removeEnd_ShouldReturnEmptyString_WhenSingleCharacterMatches() {
        assertEquals("", StringUtils.removeEnd("a", 'a'));
    }

    @Test
    void removeEnd_ShouldReturnSubstring_WhenLastCharacterIsWhitespaceAndMatches() {
        assertEquals("Hello", StringUtils.removeEnd("Hello ", ' '));
    }

    @Test
    void removeEnd_ShouldRemoveUnicodeCharacter_WhenFirstCharacterIsUnicodeAndMatches() {
        assertEquals("ẞü", StringUtils.removeEnd("ẞüñ", 'ñ'));
    }

    @Test
    void removeEnd_ShouldReturnOriginalString_WhenFirstCharacterIsUnicodeAndDoesNotMatch() {
        assertEquals("ẞüñ", StringUtils.removeEnd("ẞüñ", 'ü'));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void reverse_ShouldReturnOriginalString_WhenInputStringIsNullOrEmpty(String input) {
        assertEquals(input, StringUtils.reverse(input));
    }

    @Test
    void reverse_ShouldReturnBlankString_WhenInputStringIsBlank() {
        assertEquals(" ", StringUtils.reverse(" "));
    }

    @Test
    void reverse_ShouldReturnReversedString_WhenInputStringIsNonEmpty() {
        assertEquals("olleh", StringUtils.reverse("hello"));
    }

    @Test
    void reverse_ShouldReturnReversedString_WhenInputStringContainsUnicodeCharacters() {
        assertEquals("ñüẞ", StringUtils.reverse("ẞüñ"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void right_ShouldReturnOriginalString_WhenInputStringIsNullOrEmpty(String input) {
        assertEquals(input, StringUtils.right(input, 3));
    }

    @Test
    void right_ShouldThrowIllegalArgumentException_WhenLengthIsNegative() {
        assertThrowsExactly(IllegalArgumentException.class, () -> StringUtils.right("hello", -1), "Length must be positive");
    }

    @Test
    void right_ShouldReturnOriginalString_WhenLengthIsEqualToStringLength() {
        assertEquals("Hello", StringUtils.right("Hello", 5));
    }

    @Test
    void right_ShouldReturnOriginalString_WhenLengthIsGreaterThanStringLength() {
        assertEquals("Hello", StringUtils.right("Hello", 10));
    }

    @Test
    void right_ShouldReturnSubstring_WhenLengthIsLessThanStringLength() {
        assertEquals("llo", StringUtils.right("Hello", 3));
    }

    @Test
    void right_ShouldReturnOriginalString_WhenInputStringIsSingleCharacter() {
        assertEquals("a", StringUtils.right("a", 1));
    }

    @Test
    void right_ShouldReturnEmptyString_WhenLengthIsZero() {
        assertEquals("", StringUtils.right("Hello", 0));
    }

    @Test
    void right_ShouldReturnUnicodeSubstring_WhenInputStringContainsUnicodeCharacters() {
        assertEquals("üñ", StringUtils.right("ẞüñ", 2));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = " ")
    void split_ShouldReturnEmptyStringArray_WhenInputStringIsNullOrEmpty(String input) {
        // Given
        String separator = ",";
        int max = 0;

        // When
        String[] actual = StringUtils.split(input, separator, max);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void split_ShouldSplitByDefaultSeparator_WhenSeparatorIsNull() {
        // Given
        String input = "a b c";
        String separator = null;
        int max = 0;

        // When
        String[] actual = StringUtils.split(input, separator, max);

        // Then
        then(actual).hasSize(3);
        then(actual).containsExactly("a", "b", "c");
    }

    @Test
    void split_ShouldSplitBySpecifiedSeparator_WhenSeparatorIsProvided() {
        // Given
        String input = "a,b,c";
        String separator = ",";
        int max = 0;

        // When
        String[] actual = StringUtils.split(input, separator, max);

        // Then
        then(actual).hasSize(3);
        then(actual).containsExactly("a", "b", "c");
    }

    @Test
    void split_ShouldLimitOutputArraySize_WhenMaxIsPositive() {
        // Given
        String input = "a,b,c,d";
        String separator = ",";
        int max = 2;

        // When
        String[] actual = StringUtils.split(input, separator, max);

        // Then
        then(actual).hasSize(2);
        then(actual).containsExactly("a", "b,c,d");
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1})
    void split_ShouldReturnFullArray_WhenMaxIsZeroOrNegative(int max) {
        // Given
        String input = "a,b,c";
        String separator = ",";

        // When
        String[] actual = StringUtils.split(input, separator, max);

        // Then
        then(actual).hasSize(3);
        then(actual).containsExactly("a", "b", "c");
    }

    @Test
    void split_ShouldReturnSingleElementArray_WhenInputHasNoSeparator() {
        // Given
        String input = "abc";
        String separator = ",";
        int max = 0;

        // When
        String[] actual = StringUtils.split(input, separator, max);

        // Then
        then(actual).hasSize(1);
        then(actual).containsExactly("abc");
    }

    @Test
    void split_ShouldHandleMultipleSeparators_WhenInputHasConsecutiveSeparators() {
        // Given
        String input = "a,,b,,c";
        String separator = ",";
        int max = 0;

        // When
        String[] actual = StringUtils.split(input, separator, max);

        // Then
        then(actual).hasSize(3);
        then(actual).containsExactly("a", "b", "c");
    }

    @ParameterizedTest
    @NullAndEmptySource
    void startsWithIgnoreCase_ShouldReturnFalse_WhenInputStringIsNullOrEmpty(String input) {
        assertFalse(StringUtils.startsWithIgnoreCase(input, "pre"));
    }

    @Test
    void startsWithIgnoreCase_ShouldReturnFalse_WhenInputStringIsBlank() {
        assertFalse(StringUtils.startsWithIgnoreCase(" ", "pre"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void startsWithIgnoreCase_ShouldReturnFalse_WhenPrefixIsNullOeEmpty(String prefix) {
        assertFalse(StringUtils.startsWithIgnoreCase("Hello", prefix));
    }

    @Test
    void startsWithIgnoreCase_ShouldReturnFalse_WhenPrefixIsBlank() {
        assertFalse(StringUtils.startsWithIgnoreCase("Hello", " "));
    }

    @Test
    void startsWithIgnoreCase_ShouldReturnTrue_WhenPrefixMatchesCaseSensitively() {
        assertTrue(StringUtils.startsWithIgnoreCase("prefix Hello", "prefix"));
    }

    @Test
    void startsWithIgnoreCase_ShouldReturnTrue_WhenPrefixMatchesCaseInsensitively() {
        assertTrue(StringUtils.startsWithIgnoreCase("PREFIX Hello", "prefix"));
    }

    @Test
    void startsWithIgnoreCase_ShouldReturnFalse_WhenPrefixDoesNotMatch() {
        assertFalse(StringUtils.startsWithIgnoreCase("Hello", "prefix"));
    }

    @Test
    void startsWithIgnoreCase_ShouldReturnFalse_WhenStringShorterThanPrefix() {
        assertFalse(StringUtils.startsWithIgnoreCase("pre", "prefix"));
    }

    @Test
    void startsWithIgnoreCase_ShouldReturnTrue_WhenPrefixIsUnicodeAndMatches() {
        assertTrue(StringUtils.startsWithIgnoreCase("ẞüñ Hello", "ẞüñ"));
    }

    @Test
    void startsWithIgnoreCase_ShouldReturnFalse_WhenUnicodePrefixDoesNotMatch() {
        assertFalse(StringUtils.startsWithIgnoreCase("üñ Hello", "ẞüñ"));
    }

    @Test
    void startsWithIgnoreCase_ShouldReturnTrue_WhenSingleCharPrefixMatchesCaseInsensitively() {
        assertTrue(StringUtils.startsWithIgnoreCase("Apple", "a"));
    }

    @Test
    void startsWithIgnoreCase_ShouldReturnTrue_WhenPrefixIsEntireString() {
        assertTrue(StringUtils.startsWithIgnoreCase("prefix", "prefix"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void strip_ShouldReturnOriginalString_WhenInputStringIsNullOrEmpty(String input) {
        assertEquals(input, StringUtils.strip(input));
    }

    @Test
    void strip_ShouldReturnEmptyString_WhenInputStringIsBlank() {
        assertEquals("", StringUtils.strip(" "));
    }

    @Test
    void strip_ShouldRemoveLeadingAndTrailingSpaces_WhenInputStringHasWhitespace() {
        assertEquals("Hello", StringUtils.strip("  Hello  "));
    }

    @Test
    void strip_ShouldReturnOriginalString_WhenInputStringHasNoWhitespace() {
        assertEquals("Hello", StringUtils.strip("Hello"));
    }

    @Test
    void strip_ShouldRemoveMixedWhitespace_WhenInputStringHasTabsAndNewlines() {
        assertEquals("Hello", StringUtils.strip("\t\nHello\r\n"));
    }

    @Test
    void strip_ShouldRemoveUnicodeWhitespace_WhenInputStringHasUnicodeWhitespace() {
        assertEquals("Hello", StringUtils.strip("\u2000Hello\u2003"));
    }

    @Test
    void strip_ShouldRemoveNonBreakingSpace_WhenInputStringHasNonBreakingSpace() {
        assertEquals("Hello", StringUtils.strip("\u00A0Hello\u00A0"));
    }

    @Test
    void strip_ShouldReturnSingleCharacter_WhenInputStringIsSingleCharacterWithWhitespace() {
        assertEquals("a", StringUtils.strip(" a "));
    }

    @Test
    void strip_ShouldReturnUnicodeString_WhenInputStringHasUnicodeCharacters() {
        assertEquals("ẞüñ", StringUtils.strip("  ẞüñ  "));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void stripStart_ShouldReturnOriginalString_WhenInputStringIsNullOrEmpty(String input) {
        assertEquals(input, StringUtils.stripStart(input, "abc"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void stripStart_ShouldRemoveLeadingWhitespace_WhenStripCharsIsNullOrEmpty(String input) {
        assertEquals("Hello", StringUtils.stripStart("  \t\nHello", input));
    }

    @Test
    void stripStart_ShouldRemoveSpecifiedCharacters_WhenStripCharsIsNonEmpty() {
        assertEquals("llo", StringUtils.stripStart("Hello", "He"));
    }

    @Test
    void stripStart_ShouldReturnOriginalString_WhenNoCharactersMatch() {
        assertEquals("Hello", StringUtils.stripStart("Hello", "xyz"));
    }

    @Test
    void stripStart_ShouldReturnEmptyString_WhenAllCharactersMatch() {
        assertEquals("", StringUtils.stripStart("aaa", "a"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void stripStart_ShouldRemoveUnicodeWhitespace_WhenStripCharsIsNullOrEmpty(String input) {
        assertEquals("Hello", StringUtils.stripStart("\u00A0\u2000Hello", input));
    }

    @Test
    void stripStart_ShouldRemoveUnicodeCharacters_WhenStripCharsContainsUnicode() {
        assertEquals("ñ", StringUtils.stripStart("ẞüñ", "ẞü"));
    }

    @Test
    void stripStart_ShouldReturnSingleCharacter_WhenNoCharactersMatch() {
        assertEquals("a", StringUtils.stripStart("a", "b"));
    }

    @Test
    void stripStart_ShouldRemoveLongStripChars_WhenStripCharsIsLong() {
        assertEquals("World", StringUtils.stripStart("Hello World", "Hello "));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void stripEnd_ShouldReturnOriginalString_WhenInputStringIsNullOrEmpty(String input) {
        assertEquals(input, StringUtils.stripEnd(input, "abc"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void stripEnd_ShouldRemoveTrailingWhitespace_WhenStripCharsIsNullOrEmpty(String input) {
        assertEquals("Hello", StringUtils.stripEnd("Hello  \t\n", input));
    }

    @Test
    void stripEnd_ShouldRemoveSpecifiedCharacters_WhenStripCharsIsNonEmpty() {
        assertEquals("He", StringUtils.stripEnd("Hello", "lo"));
    }

    @Test
    void stripEnd_ShouldReturnOriginalString_WhenNoCharactersMatch() {
        assertEquals("Hello", StringUtils.stripEnd("Hello", "xyz"));
    }

    @Test
    void stripEnd_ShouldReturnEmptyString_WhenAllCharactersMatch() {
        assertEquals("", StringUtils.stripEnd("aaa", "a"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void stripEnd_ShouldRemoveUnicodeWhitespace_WhenStripCharsIsNullOrEmpty(String input) {
        assertEquals("Hello", StringUtils.stripEnd("Hello\u00A0\u2000", input));
    }

    @Test
    void stripEnd_ShouldRemoveUnicodeCharacters_WhenStripCharsContainsUnicode() {
        assertEquals("ẞ", StringUtils.stripEnd("ẞüñ", "üñ"));
    }

    @Test
    void stripEnd_ShouldReturnSingleCharacter_WhenNoCharactersMatch() {
        assertEquals("a", StringUtils.stripEnd("a", "b"));
    }

    @Test
    void stripEnd_ShouldRemoveLongStripChars_WhenStripCharsIsLong() {
        assertEquals("Hello", StringUtils.stripEnd("Hello World", " World"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = " ")
    void substringMatch_ShouldReturnFalse_WhenInputStringIsNullOrEmptyOrBlank(String input) {
        assertFalse(StringUtils.substringMatch(input, 0, "hol"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void substringMatch_ShouldReturnFalse_WhenSubstringIsNullOrEmpty(String substring) {
        assertFalse(StringUtils.substringMatch("Hello", 0, substring));
    }

    @Test
    void substringMatch_ShouldThrowIllegalArgumentException_WhenIndexIsNegative() {
        assertThrowsExactly(
                IllegalArgumentException.class,
                () -> StringUtils.substringMatch("Hello", -1, "Hel"),
                "Index must be positive");
    }

    @Test
    void substringMatch_ShouldReturnFalse_WhenSubstringDoesNotMatchAtIndex() {
        assertFalse(StringUtils.substringMatch("Hello", 0, "hol"));
    }

    @Test
    void substringMatch_ShouldReturnFalse_WhenIndexPlusLengthExceedsStringLength() {
        assertFalse(StringUtils.substringMatch("Hello", 3, "llo"));
    }

    @Test
    void substringMatch_ShouldReturnFalse_WhenSubstringIsLongerThanRemainingString() {
        assertFalse(StringUtils.substringMatch("Hello", 4, "Hello"));
    }

    @Test
    void substringMatch_ShouldReturnFalse_WhenCaseMismatch() {
        assertFalse(StringUtils.substringMatch("Hello", 0, "HEL"));
    }

    @Test
    void substringMatch_ShouldReturnTrue_WhenSubstringMatchesAtIndex() {
        assertTrue(StringUtils.substringMatch("Hello", 0, "Hel"));
    }

    @Test
    void substringMatch_ShouldReturnTrue_WhenSubstringMatchesAtEnd() {
        assertTrue(StringUtils.substringMatch("Hello", 2, "llo"));
    }

    @Test
    void substringMatch_ShouldReturnTrue_WhenUnicodeSubstringMatchesAtIndex() {
        assertTrue(StringUtils.substringMatch("ẞüñ", 1, "üñ"));
    }

    @Test
    void substringMatch_ShouldReturnFalse_WhenUnicodeSubstringDoesNotMatchAtIndex() {
        assertFalse(StringUtils.substringMatch("ẞüñ", 0, "üñ"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = " ")
    void substring_ShouldReturnEmptyString_WhenInputStringIsNullOrEmptyOrBlank(String input) {
        // Given
        int start = 0;

        // When
        String actual = StringUtils.substring(input, start);

        // Then
        then(actual).isEmpty();
    }

    @Test
    void substring_ShouldReturnSubstring_WhenStartIndexIsValid() {
        // Given
        String input = "hello";
        int start = 2;

        // When
        String actual = StringUtils.substring(input, start);

        // Then
        then(actual).isEqualTo("llo");
    }

    @Test
    void substring_ShouldReturnSubstringFromAdjustedIndex_WhenStartIndexIsNegative() {
        // Given
        String input = "hello";
        int start = -2;

        // When
        String actual = StringUtils.substring(input, start);

        // Then
        then(actual).isEqualTo("lo");
    }

    @Test
    void substring_ShouldReturnOriginalString_WhenNegativeStartIndexIsBeyondStringLength() {
        // Given
        String input = "hello";
        int start = -10;

        // When
        String actual = StringUtils.substring(input, start);

        // Then
        then(actual).isEqualTo("hello");
    }

    @Test
    void substring_ShouldReturnEmptyString_WhenStartIndexExceedsStringLength() {
        // Given
        String input = "hello";
        int start = 10;

        // When
        String actual = StringUtils.substring(input, start);

        // Then
        then(actual).isEmpty();
    }

    @ParameterizedTest
    @NullAndEmptySource
    void substringAfter_ShouldReturnEmptyString_WhenInputStringIsNullOrEmpty(String input) {
        assertEquals("", StringUtils.substringAfter(input, "a"));
    }

    @Test
    void substringAfter_ShouldReturnEmptyString_WhenInputStringIsBlank() {
        assertEquals("", StringUtils.substringAfter(" ", "a"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void substringAfter_ShouldReturnOriginalString_WhenSeparatorIsNullOrEmpty(String separator) {
        assertEquals("Hello", StringUtils.substringAfter("Hello", separator));
    }

    @Test
    void substringAfter_ShouldReturnEmptyString_WhenSeparatorIsBlankAndNotFound() {
        assertEquals("", StringUtils.substringAfter("Hello", " "));
    }

    @Test
    void substringAfter_ShouldReturnSubstring_WhenSeparatorIsBlankAndFound() {
        assertEquals("", StringUtils.substringAfter("Hello ", " "));
        assertEquals("World", StringUtils.substringAfter("Hello World", " "));
        assertEquals("World", StringUtils.substringAfter("Hello World", " "));
        assertEquals("World ", StringUtils.substringAfter("Hello World ", " "));
    }

    @ParameterizedTest
    @CsvSource(value = {"Hello,World_,", "Hello::World_::"}, delimiter = '_')
    void substringAfter_ShouldReturnSubstring_WhenSeparatorIsFound(String input, String separator) {
        assertEquals("World", StringUtils.substringAfter(input, separator));
    }

    @ParameterizedTest
    @CsvSource(value = {"Hello,h", "Hello World,w"}, delimiter = ',')
    void substringAfter_ShouldReturnEmptyString_WhenSeparatorNotFound(String input, String separator) {
        assertEquals("", StringUtils.substringAfter(input, separator));
    }

    @Test
    void substringAfter_ShouldReturnSubstring_WhenUnicodeSeparatorIsFound() {
        assertEquals("ñ", StringUtils.substringAfter("ẞüñ", "ü"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void substringBefore_ShouldReturnEmptyString_WhenInputStringIsNullOrEmpty(String input) {
        assertEquals("", StringUtils.substringAfter(input, "a"));
    }

    @Test
    void substringBefore_ShouldReturnEmptyString_WhenInputStringIsBlank() {
        assertEquals("", StringUtils.substringBefore(" ", "a"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void substringBefore_ShouldReturnOriginalString_WhenSeparatorIsNullOrEmpty(String separator) {
        assertEquals("Hello", StringUtils.substringBefore("Hello", separator));
    }

    @Test
    void substringBefore_ShouldReturnEmptyString_WhenSeparatorIsBlankAndNotFound() {
        assertEquals("", StringUtils.substringBefore("Hello", " "));
    }

    @Test
    void substringBefore_ShouldReturnSubstring_WhenSeparatorIsBlankAndFound() {
        assertEquals("Hello", StringUtils.substringBefore("Hello World", " "));
        assertEquals("Hello", StringUtils.substringBefore("Hello World ", " "));
    }

    @ParameterizedTest
    @CsvSource(value = {"Hello,World_,", "Hello::World_::"}, delimiter = '_')
    void substringBefore_ShouldReturnSubstring_WhenSeparatorIsFound(String input, String separator) {
        assertEquals("Hello", StringUtils.substringBefore(input, separator));
    }

    @ParameterizedTest
    @CsvSource(value = {"Hello,h", "Hello World,w"}, delimiter = ',')
    void substringBefore_ShouldReturnEmptyString_WhenSeparatorNotFound(String input, String separator) {
        assertEquals("", StringUtils.substringBefore(input, separator));
    }

    @Test
    void substringBefore_ShouldReturnSubstring_WhenUnicodeSeparatorIsFound() {
        assertEquals("ẞ", StringUtils.substringBefore("ẞüñ", "ü"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void substringBetween_ShouldReturnEmptyString_WhenInputStringIsNullOrEmpty(String input) {
        assertEquals("", StringUtils.substringBetween(input, "(", ")"));
    }

    @Test
    void substringBetween_ShouldReturnEmptyString_WhenInputStringIsBlank() {
        assertEquals("", StringUtils.substringBetween(" ", "(", ")"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void substringBetween_ShouldReturnOriginalString_WhenOpenIsNullOrEmpty(String open) {
        assertEquals("Hello", StringUtils.substringBetween("Hello", open, ")"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void substringBetween_ShouldReturnOriginalString_WhenCloseIsNullOrEmpty(String close) {
        assertEquals("Hello", StringUtils.substringBetween("Hello", "(", close));
    }

    @ParameterizedTest
    @CsvSource(value = {"(World),(,)", "<<World>>,<<,>>", "<span>World</span>, <span>, </span>"}, delimiter = ',')
    void substringBetween_ShouldReturnSubstring_WhenOpenAndCloseAreFound(String input, String open, String close) {
        assertEquals("World", StringUtils.substringBetween(input, open, close));
    }

    @ParameterizedTest
    @CsvSource(value = {"World,[,])", "(World,(,)"}, delimiter = ',')
    void substringBetween_ShouldReturnEmptyString_WhenOpenOrCloseNotFound(String input, String open, String close) {
        assertEquals("", StringUtils.substringBetween(input, open, close));
    }

    @Test
    void substringBetween_ShouldReturnSubstring_WhenUnicodeTagsAreFound() {
        assertEquals("üñ", StringUtils.substringBetween("ẞüñẞ", "ẞ", "ẞ"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void substringsBetween_ShouldReturnEmptyList_WhenInputStringIsNullOrEmpty(String input) {
        assertEquals(Collections.emptyList(), StringUtils.substringsBetween(input, "(", ")"));
    }

    @Test
    void substringsBetween_ShouldReturnEmptyList_WhenInputStringIsBlank() {
        assertEquals(Collections.emptyList(), StringUtils.substringsBetween(" ", "(", ")"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void substringsBetween_ShouldReturnEmptyList_WhenOpenIsNullOrEmpty(String open) {
        assertEquals(Collections.emptyList(), StringUtils.substringsBetween("Hello", open, ")"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void substringsBetween_ShouldReturnEmptyList_WhenCloseIsNullOrEmpty(String close) {
        assertEquals(Collections.emptyList(), StringUtils.substringsBetween("Hello", "(", close));
    }

    @ParameterizedTest
    @CsvSource(value = {"(Hello)(World),(,)", "<Hello><World>,<,>"}, delimiter = ',')
    void substringsBetween_ShouldReturnSubstrings_WhenTagsAreFound(String input, String open, String close) {
        assertEquals(Arrays.asList("Hello", "World"), StringUtils.substringsBetween(input, open, close));
    }

    @Test
    void substringsBetween_ShouldReturnEmptyList_WhenTagsNotFound() {
        assertEquals(Collections.emptyList(), StringUtils.substringsBetween("Hello", "[", "]"));
    }

    @Test
    void substringsBetween_ShouldReturnEmptyList_WhenOpenAndCloseAreSame() {
        assertEquals(List.of("Hello]"), StringUtils.substringsBetween("[Hello][World]", "[", "["));
    }

    @Test
    void substringsBetween_ShouldIgnoreEmptySubstrings_WhenTagsAreEmpty() {
        assertEquals(Collections.emptyList(), StringUtils.substringsBetween("()", "(", ")"));
    }

    @Test
    void substringsBetween_ShouldReturnSubstrings_WhenNestedTagsAreValid() {
        assertEquals(List.of("Hello(World)more"), StringUtils.substringsBetween("[Hello(World)more]", "[", "]"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void trimLeadingCharacter_ShouldReturnOriginalString_WhenInputStringIsNullOrEmpty(String input) {
        assertEquals(input, StringUtils.trimLeadingCharacter(input, '-'));
    }

    @Test
    void trimLeadingCharacter_ShouldReturnOriginalString_WhenNoLeadingChar() {
        assertEquals("Hello", StringUtils.trimLeadingCharacter("Hello", '-'));
    }

    @Test
    void trimLeadingCharacter_ShouldRemoveSingleLeadingChar_WhenStringStartsWithChar() {
        assertEquals("Hello", StringUtils.trimLeadingCharacter("-Hello", '-'));
    }

    @Test
    void trimLeadingCharacter_ShouldRemoveMultipleLeadingChars_WhenStringStartsWithMultipleChars() {
        assertEquals("Hello", StringUtils.trimLeadingCharacter("---Hello", '-'));
    }

    @Test
    void trimLeadingCharacter_ShouldReturnEmptyString_WhenAllCharsAreLeading() {
        assertEquals("", StringUtils.trimLeadingCharacter("---", '-'));
    }

    @Test
    void trimLeadingCharacter_ShouldRemoveUnicodeLeadingChar_WhenStringStartsWithUnicodeChar() {
        assertEquals("üñ", StringUtils.trimLeadingCharacter("ẞüñ", 'ẞ'));
    }

    @Test
    void trimLeadingCharacter_ShouldReturnSingleChar_WhenSingleNonLeadingChar() {
        assertEquals("a", StringUtils.trimLeadingCharacter("a", '-'));
    }

    @Test
    void trimLeadingCharacter_ShouldReturnEmptyString_WhenSingleLeadingChar() {
        assertEquals("", StringUtils.trimLeadingCharacter("-", '-'));
    }

    @Test
    void trimLeadingCharacter_ShouldReturnOriginalString_WhenDifferentLeadingChar() {
        assertEquals("#Hello", StringUtils.trimLeadingCharacter("#Hello", '-'));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void trimTrailingCharacter_ShouldReturnOriginalString_WhenInputStringIsNullOrEmpty(String input) {
        assertEquals(input, StringUtils.trimTrailingCharacter(input, '-'));
    }

    @Test
    void trimTrailingCharacter_ShouldReturnOriginalString_WhenNoTrailingChar() {
        assertEquals("Hello", StringUtils.trimTrailingCharacter("Hello", '-'));
    }

    @Test
    void trimTrailingCharacter_ShouldRemoveSingleTrailingChar_WhenStringEndsWithChar() {
        assertEquals("Hello", StringUtils.trimTrailingCharacter("Hello-", '-'));
    }

    @Test
    void trimTrailingCharacter_ShouldRemoveMultipleTrailingChars_WhenStringEndsWithMultipleChars() {
        assertEquals("Hello", StringUtils.trimTrailingCharacter("Hello---", '-'));
    }

    @Test
    void trimTrailingCharacter_ShouldReturnEmptyString_WhenAllCharsAreTrailing() {
        assertEquals("", StringUtils.trimTrailingCharacter("---", '-'));
    }

    @Test
    void trimTrailingCharacter_ShouldRemoveUnicodeTrailingChar_WhenStringEndsWithUnicodeChar() {
        assertEquals("üñ", StringUtils.trimTrailingCharacter("üñẞ", 'ẞ'));
    }

    @Test
    void trimTrailingCharacter_ShouldReturnSingleChar_WhenSingleNonTrailingChar() {
        assertEquals("a", StringUtils.trimTrailingCharacter("a", '-'));
    }

    @Test
    void trimTrailingCharacter_ShouldReturnEmptyString_WhenSingleTrailingChar() {
        assertEquals("", StringUtils.trimTrailingCharacter("-", '-'));
    }

    @Test
    void trimTrailingCharacter_ShouldReturnOriginalString_WhenDifferentTrailingChar() {
        assertEquals("Hello#", StringUtils.trimTrailingCharacter("Hello#", '-'));
    }

    @SuppressWarnings("ConstantValue")
    @Test
    void trimArrayElements_ShouldReturnOriginalStringArray_WhenArrayIsNullOrEmpty() {
        // Given
        String[] input = null;
        String[] input2 = {};

        // When
        String[] actual = StringUtils.trimArrayElements(input);
        String[] actual2 = StringUtils.trimArrayElements(input2);

        // Then
        then(actual).isNull();
        then(actual2).isEmpty();
    }

    @Test
    void trimArrayElements_ShouldTrimElements_WhenArrayHasNonNullElements() {
        // Given
        String[] input = {"  Hello  ", " World "};

        // When
        String[] actual = StringUtils.trimArrayElements(input);

        // Then
        then(actual).containsExactly("Hello", "World");
    }

    @Test
    void trimArrayElements_ShouldReturnNullElementsUnchanged_WhenArrayHasNullElement() {
        // Given
        String[] input = {null, "  Hello  "};

        // When
        String[] actual = StringUtils.trimArrayElements(input);

        // Then
        then(actual).containsExactly(null, "Hello");
    }

    @Test
    void trimArrayElements_ShouldHandleEmptyStrings_WhenArrayHasEmptyOrBlankElements() {
        // Given
        String[] input = {"  ", ""};

        // When
        String[] actual = StringUtils.trimArrayElements(input);

        // Then
        then(actual).containsExactly("", "");
    }

    @Test
    void trimArrayElements_ShouldHandleUnicodeElements_WhenArrayHasUnicodeStrings() {
        // Given
        String[] input = {"  ẞüñ  "};

        // When
        String[] actual = StringUtils.trimArrayElements(input);

        // Then
        then(actual).containsExactly("ẞüñ");
    }

    @SuppressWarnings("ConstantValue")
    @Test
    void toString_ShouldReturnEmptyArray_WhenCollectionIsNullOrEmpty() {
        // Given
        Collection<String> inputNull = null;
        Collection<String> inputEmpty = Collections.emptyList();

        // When
        String[] actualNull = StringUtils.toString(inputNull);
        String[] actualEmpty = StringUtils.toString(inputEmpty);

        // Then
        then(actualNull).isEmpty();
        then(actualEmpty).isEmpty();
    }

    @Test
    void toString_ShouldReturnStringArray_WhenCollectionHasElements() {
        // Given
        Collection<String> input = List.of("Hello", "World");

        // When
        String[] actual = StringUtils.toString(input);

        // Then
        then(actual).containsExactly("Hello", "World");
    }

    @Test
    void toString_ShouldReturnStringArrayWithNull_WhenCollectionHasNullElement() {
        // Given
        Collection<String> input = Arrays.asList("Hello", "World", null);

        // When
        String[] actual = StringUtils.toString(input);

        // Then
        then(actual).containsExactly("Hello", "World", null);
    }

    @Test
    void toString_ShouldReturnStringArrayWithEmptyString_WhenCollectionHasEmptyString() {
        // Given
        Collection<String> input = Arrays.asList("", "World");

        // When
        String[] actual = StringUtils.toString(input);

        // Then
        then(actual).containsExactly("", "World");
    }

    @SuppressWarnings("ConstantValue")
    @Test
    void toString_ShouldReturnEmptyArray_WhenEnumerationIsNullOrEmpty() {
        // Given
        Collection<String> inputNull = null;
        Enumeration<String> inputEmpty = Collections.emptyEnumeration();

        // When
        String[] actualNull = StringUtils.toString(inputNull);
        String[] actualEmpty = StringUtils.toString(inputEmpty);

        // Then
        then(actualNull).isEmpty();
        then(actualEmpty).isEmpty();
    }

    @Test
    void toString_ShouldReturnStringArray_WhenEnumerationHasElements() {
        // Given
        Enumeration<String> input = Collections.enumeration(List.of("Hello", "World"));

        // When
        String[] actual = StringUtils.toString(input);

        // Then
        then(actual).containsExactly("Hello", "World");
    }

    @Test
    void toString_ShouldReturnStringArrayWithNull_WhenEnumerationHasNullElement() {
        // Given
        Enumeration<String> input = Collections.enumeration(Arrays.asList("Hello", "World", null));

        // When
        String[] actual = StringUtils.toString(input);

        // Then
        then(actual).containsExactly("Hello", "World", null);
    }

    @Test
    void toString_ShouldReturnStringArrayWithEmptyString_WhenEnumerationHasEmptyString() {
        // Given
        Enumeration<String> input = Collections.enumeration(List.of("", "World"));

        // When
        String[] actual = StringUtils.toString(input);

        // Then
        then(actual).containsExactly("", "World");
    }

    @ParameterizedTest
    @NullAndEmptySource
    void uncapitalize_ShouldReturnNull_WhenInputStringIsNullOrEmpty(String input) {
        assertEquals(input, StringUtils.uncapitalize(input));
    }

    @Test
    void uncapitalize_ShouldReturnBlankString_WhenInputStringIsBlank() {
        assertEquals(" ", StringUtils.uncapitalize(" "));
    }

    @Test
    void uncapitalize_ShouldReturnLowercaseFirstChar_WhenFirstCharIsUppercase() {
        assertEquals("hello", StringUtils.uncapitalize("Hello"));
    }

    @Test
    void uncapitalize_ShouldReturnSingleLowercaseChar_WhenInputIsSingleUppercaseChar() {
        assertEquals("a", StringUtils.uncapitalize("A"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void unwrap_ShouldReturnOriginalString_WhenInputStringIsNullOrEmpty(String input) {
        assertEquals(input, StringUtils.unwrap(input, '('));
    }

    @Test
    void unwrap_ShouldReturnBlankString_WhenInputStringIsBlank() {
        assertEquals(" ", StringUtils.unwrap(" ", '('));
    }

    @Test
    void unwrap_ShouldUnwrapString_WhenWrappedWithValidToken() {
        assertEquals("Hello", StringUtils.unwrap("#Hello#", '#'));
    }

    @Test
    void unwrap_ShouldReturnString_WhenNotWrappedWithToken() {
        assertEquals("Hello", StringUtils.unwrap("Hello", '('));
    }

    @Test
    void unwrap_ShouldReturnString_WhenOnlyStartsWithToken() {
        assertEquals("(Hello", StringUtils.unwrap("(Hello", '('));
    }

    @Test
    void unwrap_ShouldReturnString_WhenOnlyEndsWithToken() {
        assertEquals("Hello)", StringUtils.unwrap("Hello)", ')'));
    }

    @Test
    void unwrap_ShouldUnwrapUnicode_WhenWrappedWithUnicodeToken() {
        assertEquals("üñ", StringUtils.unwrap("ẞüñẞ", 'ẞ'));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void wrap_ShouldReturnOriginalString_WhenInputStringIsNullOrEmpty(String input) {
        assertEquals(input, StringUtils.wrap(input, "("));
    }

    @Test
    void wrap_ShouldReturnBlankString_WhenInputStringIsBlank() {
        assertEquals(" ", StringUtils.wrap(" ", "("));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void wrap_ShouldReturnOriginalString_WhenWrapWithIsNullOrEmpty(String input) {
        assertEquals("Hello", StringUtils.wrap("Hello", input));
    }

    @Test
    void wrap_ShouldWrapString_WhenWrappedWithIsBlank() {
        assertEquals(" Hello ", StringUtils.wrap("Hello", " "));
    }

    @Test
    void wrap_ShouldWrapString_WhenWrappedWithValidToken() {
        assertEquals("#Hello#", StringUtils.wrap("Hello", "#"));
    }

    @Test
    void wrap_ShouldUnwrapUnicode_WhenWrappedWithUnicodeToken() {
        assertEquals("ẞüñẞ", StringUtils.wrap("üñ", "ẞ"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void wrapIfMissing_ShouldReturnOriginalString_WhenInputStringIsNullOrEmpty(String input) {
        assertEquals(input, StringUtils.wrapIfMissing(input, '('));
    }

    @Test
    void wrapIfMissing_ShouldReturnBlankString_WhenInputStringIsBlank() {
        assertEquals(" ", StringUtils.wrapIfMissing(" ", '('));
    }

    @Test
    void wrapIfMissing_ShouldWrapString_WhenNotWrapped() {
        assertEquals("#world#", StringUtils.wrapIfMissing("world", '#'));
    }

    @Test
    void wrapIfMissing_ShouldReturnOriginalString_WhenAlreadyWrapped() {
        assertEquals("#world#", StringUtils.wrapIfMissing("#world#", '#'));
    }

    @Test
    void wrapIfMissing_ShouldWrapWithUnicodeToken_WhenNotWrapped() {
        assertEquals("ẞüñẞ", StringUtils.wrapIfMissing("üñ", 'ẞ'));
    }
}
