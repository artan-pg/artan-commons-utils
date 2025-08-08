package ir.artanpg.commons.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

/**
 * Unit tests for the {@link StringUtils} class.
 */
class StringUtilsTests {

    @ParameterizedTest
    @NullAndEmptySource
    void abbreviate_ShouldReturnOriginalString_WhenInputStringIsNullOrEmpty(String input) {
        assertEquals(input, StringUtils.abbreviate(input, 4));
        assertEquals(input, StringUtils.abbreviate(input, "...", 4));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void abbreviate_ShouldReturnOriginalString_WhenAbbrevMarkerIsNullOrEmpty(String abbrevMarker) {
        assertEquals("Hello", StringUtils.abbreviate("Hello", abbrevMarker, 5));
    }

    @Test
    void abbreviate_ShouldReturnOriginalString_WhenStringLengthIsLessThanMaxWidth() {
        assertEquals("Hello", StringUtils.abbreviate("Hello", 10));
        assertEquals("Hello", StringUtils.abbreviate("Hello", "...", 10));
    }

    @Test
    void abbreviate_ShouldReturnOriginalString_WhenStringLengthEqualsMaxWidth() {
        assertEquals("Hello", StringUtils.abbreviate("Hello", 5));
        assertEquals("Hello", StringUtils.abbreviate("Hello", "...", 5));
    }

    @Test
    void abbreviate_ShouldReturnAbbreviatedString_WhenStringLengthExceedsMaxWidth() {
        assertEquals("Hello...", StringUtils.abbreviate("Hello World", 8));
        assertEquals("Hello...", StringUtils.abbreviate("Hello World", "...", 8));
    }

    @Test
    void abbreviate_ShouldReturnSubstring_WhenAbbrevMarkerIsEmptyAndMaxWidthIsPositive() {
        assertEquals("Hello", StringUtils.abbreviate("Hello World", "", 5));
    }

    @Test
    void abbreviate_ShouldThrowIllegalArgumentException_WhenMaxWidthIsLessThanMinAbbrevWidth() {
        assertThrowsExactly(
                IllegalArgumentException.class, () -> StringUtils.abbreviate("Hello", 3),
                "Minimum abbreviation width is 4");

        assertThrowsExactly(
                IllegalArgumentException.class, () -> StringUtils.abbreviate("Hello", "...", 3),
                "Minimum abbreviation width is 4");
    }

    @Test
    void abbreviate_ShouldThrowIllegalArgumentException_WhenMaxWidthIsZero() {
        assertThrowsExactly(
                IllegalArgumentException.class,
                () -> StringUtils.abbreviate("Hello", 0),
                "Minimum abbreviation width is 4");

        assertThrowsExactly(
                IllegalArgumentException.class,
                () -> StringUtils.abbreviate("Hello", "...", 0),
                "Minimum abbreviation width is 4");
    }

    @Test
    void abbreviate_ShouldThrowIllegalArgumentException_WhenMaxWidthIsNegative() {
        assertThrowsExactly(
                IllegalArgumentException.class,
                () -> StringUtils.abbreviate("Hello", -1),
                "Minimum abbreviation width is 4");

        assertThrowsExactly(
                IllegalArgumentException.class,
                () -> StringUtils.abbreviate("Hello", "...", -1),
                "Minimum abbreviation width is 4");
    }

    @ParameterizedTest
    @NullAndEmptySource
    void capitalize_ShouldReturnOriginalString_WhenInputStringIsNullOrEmpty(String input) {
        assertEquals(input, StringUtils.capitalize(input));
    }

    @Test
    void capitalize_ShouldReturnOriginalString_WhenInputStringIsBlank() {
        assertEquals(" ", StringUtils.capitalize(" "));
    }

    @Test
    void capitalize_ShouldCapitalizeFirstCharacter_WhenInputStringStartsWithLowercase() {
        assertEquals("Hello", StringUtils.capitalize("hello"));
    }

    @Test
    void capitalize_ShouldReturnOriginalString_WhenFirstCharacterIsAlreadyCapitalized() {
        assertEquals("Hello", StringUtils.capitalize("Hello"));
    }

    @Test
    void capitalize_ShouldCapitalizeSingleCharacter_WhenInputStringIsSingleLowercaseCharacter() {
        assertEquals("A", StringUtils.capitalize("a"));
    }

    @Test
    void capitalize_ShouldReturnOriginalString_WhenInputStringIsSingleUppercaseCharacter() {
        assertEquals("A", StringUtils.capitalize("A"));
    }

    @Test
    void capitalize_ShouldHandleUnicodeCharacters_WhenInputStringStartsWithUnicodeLowercase() {
        assertEquals("Θeta", StringUtils.capitalize("θeta"));
    }

    @Test
    void capitalize_ShouldReturnOriginalString_WhenInputStringStartsWithNonLetter() {
        assertEquals("123test", StringUtils.capitalize("123test"));
    }

    @Test
    void capitalize_ShouldCapitalizeFirstLetter_WhenInputStringHasMixedCase() {
        assertEquals("HELLO", StringUtils.capitalize("hELLO"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void chomp_ShouldReturnOriginalString_WhenInputStringIsNullOrEmpty(String input) {
        assertEquals(input, StringUtils.chomp(input));
    }

    @Test
    void chomp_ShouldReturnBlankString_WhenInputStringIsBlank() {
        assertEquals(" ", StringUtils.chomp(" "));
    }

    @Test
    void chomp_ShouldRemoveLineFeed_WhenStringEndsWithLineFeed() {
        assertEquals("Hello", StringUtils.chomp("Hello\n"));
    }

    @Test
    void chomp_ShouldRemoveCarriageReturn_WhenStringEndsWithCarriageReturn() {
        assertEquals("Hello", StringUtils.chomp("Hello\r"));
    }

    @Test
    void chomp_ShouldRemoveCarriageReturnLineFeed_WhenStringEndsWithCarriageReturnLineFeed() {
        assertEquals("Hello", StringUtils.chomp("Hello\r\n"));
    }

    @Test
    void chomp_ShouldReturnOriginalString_WhenStringDoesNotEndWithLineFeedOrCarriageReturn() {
        assertEquals("Hello", StringUtils.chomp("Hello"));
    }

    @Test
    void chomp_ShouldReturnOriginalString_WhenStringHasSingleCharacterNotLineFeedOrCarriageReturn() {
        assertEquals("a", StringUtils.chomp("a"));
    }

    @Test
    void chomp_ShouldReturnEmptyString_WhenInputStringIsSingleLineFeed() {
        assertEquals("", StringUtils.chomp("\n"));
    }

    @Test
    void chomp_ShouldReturnEmptyString_WhenInputStringIsSingleCarriageReturn() {
        assertEquals("", StringUtils.chomp("\r"));
    }

    @Test
    void chomp_ShouldRemoveOnlyLastNewline_WhenStringContainsMultipleNewlines() {
        assertEquals("Hello\nWorld", StringUtils.chomp("Hello\nWorld\n"));
    }

    @Test
    void chomp_ShouldRemoveOnlyLastCarriageReturnLineFeed_WhenStringContainsMultipleCarriageReturnLineFeeds() {
        assertEquals("Hello\r\nWorld", StringUtils.chomp("Hello\r\nWorld\r\n"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void chop_ShouldReturnOriginalString_WhenInputStringIsNullOrEmpty(String input) {
        assertEquals(input, StringUtils.chop(input));
    }

    @Test
    void chop_ShouldReturnEmptyString_WhenInputStringIsSingleCharacter() {
        assertEquals("", StringUtils.chop("a"));
    }

    @Test
    void chop_ShouldRemoveLastCharacter_WhenStringDoesNotEndWithLineFeedOrCarriageReturn() {
        assertEquals("Hell", StringUtils.chop("Hello"));
    }

    @Test
    void chop_ShouldRemoveLastTwoCharacters_WhenStringEndsWithLineFeed() {
        assertEquals("Hell", StringUtils.chop("Hello\n"));
    }

    @Test
    void chop_ShouldRemoveLastTwoCharacters_WhenStringEndsWithCarriageReturn() {
        assertEquals("Hell", StringUtils.chop("Hello\r"));
    }

    @Test
    void chop_ShouldRemoveLastTwoCharacters_WhenStringEndsWithCarriageReturnLineFeed() {
        assertEquals("Hello", StringUtils.chop("Hello\r\n"));
    }

    @Test
    void chop_ShouldRemoveOnlyLastCharacter_WhenStringEndsWithNonSpecialCharacter() {
        assertEquals("Hello", StringUtils.chop("Hello!"));
    }

    @Test
    void chop_ShouldRemoveOnlyLastCharacter_WhenStringHasMultipleNonSpecialCharacters() {
        assertEquals("Hello Worl", StringUtils.chop("Hello World"));
    }

    @Test
    void chop_ShouldRemoveLastTwoCharacters_WhenStringHasMultipleLineFeeds() {
        assertEquals("Hello\nWorl", StringUtils.chop("Hello\nWorld\n"));
    }

    @Test
    void chop_ShouldRemoveLastTwoCharacters_WhenStringHasMultipleCarriageReturns() {
        assertEquals("Hello\rWorl", StringUtils.chop("Hello\rWorld\r"));
    }

    @Test
    void contains_ShouldReturnFalse_WhenInputStringIsNull() {
        boolean result = ;
        assertFalse(result);
    }

    @Test
    void contains_ShouldReturnFalse_WhenInputStringIsEmpty() {
        boolean result = StringUtils.contains("", "test");
        assertFalse(result);
    }

    @Test
    void contains_ShouldReturnFalse_WhenInputStringIsBlank() {
        boolean result = StringUtils.contains("   ", "test");
        assertFalse(result);
    }

    @Test
    void contains_ShouldReturnTrue_WhenStringContainsSearchString() {
        boolean result = StringUtils.contains("hello world", "world");
        assertTrue(result);
    }

    @Test
    void contains_ShouldReturnFalse_WhenStringDoesNotContainSearchString() {
        boolean result = StringUtils.contains("hello world", "test");
        assertFalse(result);
    }

    @Test
    void contains_ShouldReturnTrue_WhenSearchStringIsEmpty() {
        boolean result = StringUtils.contains("hello", "");
        assertTrue(result);
    }

    @Test
    void contains_ShouldReturnTrue_WhenSearchStringIsSingleCharacterAndPresent() {
        boolean result = StringUtils.contains("hello", "h");
        assertTrue(result);
    }

    @Test
    void contains_ShouldReturnFalse_WhenSearchStringIsSingleCharacterAndNotPresent() {
        boolean result = StringUtils.contains("hello", "z");
        assertFalse(result);
    }

    @Test
    void contains_ShouldReturnTrue_WhenSearchStringIsCaseSensitiveAndPresent() {
        boolean result = StringUtils.contains("HelloWorld", "Hello");
        assertTrue(result);
    }

    @Test
    void contains_ShouldReturnFalse_WhenSearchStringIsCaseSensitiveAndNotPresent() {
        boolean result = StringUtils.contains("HelloWorld", "hello");
        assertFalse(result);
    }

    @Test
    void contains_ShouldReturnTrue_WhenStringAndSearchStringAreIdentical() {
        boolean result = StringUtils.contains("test", "test");
        assertTrue(result);
    }

    @Test
    void contains_ShouldReturnFalse_WhenSearchStringIsLongerThanInputString() {
        boolean result = StringUtils.contains("hi", "hello");
        assertFalse(result);
    }

    @Test
    void contains_ShouldReturnTrue_WhenStringContainsUnicodeSearchString() {
        boolean result = StringUtils.contains("helloθworld", "θ");
        assertTrue(result);
    }
}
