package ir.artanpg.commons.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        assertEquals("Hello", StringUtils.capitalize("Hello"));
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

    @ParameterizedTest
    @NullAndEmptySource
    void contains_ShouldReturnFalse_WhenInputStringIsNullOrEmpty(String input) {
        assertFalse(StringUtils.contains(input, "Hello"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void contains_ShouldReturnFalse_WhenSearchStringIsNullOrEmpty(String search) {
        assertFalse(StringUtils.contains("Hello World", search));
    }

    @Test
    void contains_ShouldReturnFalse_WhenInputStringIsBlank() {
        assertFalse(StringUtils.contains(" ", "World"));
    }

    @Test
    void contains_ShouldReturnFalse_WhenSearchStringIsBlank() {
        assertFalse(StringUtils.contains("Hello", " "));
    }

    @Test
    void contains_ShouldReturnTrue_WhenStringContainsSearchString() {
        assertTrue(StringUtils.contains("Hello World", "World"));
    }

    @Test
    void contains_ShouldReturnFalse_WhenStringDoesNotContainSearchString() {
        assertFalse(StringUtils.contains("Hello World", "world"));
    }

    @Test
    void contains_ShouldReturnTrue_WhenSearchStringIsSingleCharacterAndPresent() {
        assertTrue(StringUtils.contains("Hello", "H"));
    }

    @Test
    void contains_ShouldReturnFalse_WhenSearchStringIsSingleCharacterAndNotPresent() {
        assertFalse(StringUtils.contains("Hello", "z"));
    }

    @Test
    void contains_ShouldReturnTrue_WhenStringAndSearchStringAreIdentical() {
        assertTrue(StringUtils.contains("Hello", "Hello"));
    }

    @Test
    void contains_ShouldReturnFalse_WhenSearchStringIsLongerThanInputString() {
        assertFalse(StringUtils.contains("hi", "Hello"));
    }

    @Test
    void contains_ShouldReturnTrue_WhenStringContainsUnicodeSearchString() {
        assertTrue(StringUtils.contains("HelloθWorld", "θ"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void containsAny_ShouldReturnFalse_WhenInputStringIsNullOrEmpty(String input) {
        assertFalse(StringUtils.containsAny(input, "Hello", "World"));
    }

    @Test
    void containsAny_ShouldReturnFalse_WhenInputStringIsBlank() {
        assertFalse(StringUtils.containsAny(" ", "Hello", "World"));
    }

    @Test
    void containsAny_ShouldReturnFalse_WhenSearchStringArrayIsNull() {
        assertFalse(StringUtils.containsAny("Hello", (String[]) null));
    }

    @Test
    void containsAny_ShouldReturnFalse_WhenSearchStringArrayIsEmpty() {
        assertFalse(StringUtils.containsAny("Hello"));
    }

    @Test
    void containsAny_ShouldReturnFalse_WhenSearchStringArrayIsBlank() {
        assertFalse(StringUtils.containsAny("Hello World", " "));
    }

    @Test
    void containsAny_ShouldReturnTrue_WhenStringContainsAnyCharFromSearchStringArray() {
        assertTrue(StringUtils.containsAny("Hello World", "", "Hello", null));
    }

    @Test
    void containsAny_ShouldReturnFalse_WhenStringDoesNotContainAnyCharFromSearchStrings() {
        assertFalse(StringUtils.containsAny("Hello World", "", "hello", null));
    }

    @Test
    void containsAny_ShouldReturnTrue_WhenStringContainsUnicodeCharFromSearchString() {
        assertTrue(StringUtils.containsAny("HelloθWorld", "θ", "Hello"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void containsWhitespace_ShouldReturnFalse_WhenInputStringIsNull(String input) {
        assertFalse(StringUtils.containsWhitespace(input));
    }

    @Test
    void containsWhitespace_ShouldReturnTrue_WhenInputStringIsBlank() {
        assertTrue(StringUtils.containsWhitespace(" "));
    }

    @Test
    void containsWhitespace_ShouldReturnFalse_WhenStringHasNoWhitespace() {
        assertFalse(StringUtils.containsWhitespace("HelloWorld"));
    }

    @Test
    void containsWhitespace_ShouldReturnTrue_WhenStringContainsSpace() {
        assertTrue(StringUtils.containsWhitespace("Hello World"));
    }

    @Test
    void containsWhitespace_ShouldReturnTrue_WhenStringContainsTab() {
        assertTrue(StringUtils.containsWhitespace("Hello\tWorld"));
    }

    @Test
    void containsWhitespace_ShouldReturnTrue_WhenStringContainsNewline() {
        assertTrue(StringUtils.containsWhitespace("Hello\nWorld"));
    }

    @Test
    void containsWhitespace_ShouldReturnTrue_WhenStringContainsUnicodeWhitespace() {
        assertTrue(StringUtils.containsWhitespace("Hello\u2002World"));
    }

    @Test
    void containsWhitespace_ShouldReturnTrue_WhenStringContainsMultipleWhitespaces() {
        assertTrue(StringUtils.containsWhitespace("Hello \t\n World"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void countMatches_ShouldReturnZero_WhenInputStringIsNullOrEmpty(String input) {
        assertEquals(0, StringUtils.countMatches(input, "Hello"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void countMatches_ShouldReturnZero_WhenSubStringIsNullOrEmpty(String input) {
        assertEquals(0, StringUtils.countMatches("Hello", input));
    }

    @Test
    void countMatches_ShouldReturnZero_WhenInputStringIsBlankAndSubStringNotBlank() {
        assertEquals(0, StringUtils.countMatches(" ", "Hello"));
    }

    @Test
    void countMatches_ShouldReturnZero_WhenSubstringIsBlankAndStringNotBlank() {
        assertEquals(0, StringUtils.countMatches("Hello", " "));
    }

    @Test
    void countMatches_ShouldReturnCount_WhenSubstringAppearsOnce() {
        assertEquals(1, StringUtils.countMatches("Hello", "He"));
        assertEquals(1, StringUtils.countMatches("banana", "ana"));
    }

    @Test
    void countMatches_ShouldReturnZero_WhenSubstringDoesNotAppear() {
        assertEquals(0, StringUtils.countMatches("Hello", "he"));
    }

    @Test
    void countMatches_ShouldReturnCount_WhenSubstringIsSingleChar() {
        assertEquals(2, StringUtils.countMatches("Hello", "l"));
    }

    @Test
    void countMatches_ShouldReturnCount_WhenStringAndSubstringAreIdentical() {
        assertEquals(1, StringUtils.countMatches("Hello", "Hello"));
    }

    @Test
    void countMatches_ShouldReturnCount_WhenSubstringIsUnicodeAndAppears() {
        assertEquals(2, StringUtils.countMatches("θetaθworld", "θ"));
    }

    @Test
    void countMatches_ShouldReturnZero_WhenSubstringIsLongerThanString() {
        assertEquals(0, StringUtils.countMatches("Hi", "Hello"));
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
}
