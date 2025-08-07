package ir.artanpg.commons.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;

import static ir.artanpg.commons.utils.StringUtils.EMPTY;
import static ir.artanpg.commons.utils.StringUtils.SPACE_CHARACTER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the {@link StringUtils} class.
 */
class StringUtilsTests {

    @ParameterizedTest
    @NullAndEmptySource
    void length_ShouldReturnZero_WhenInputIsNullOrEmpty(String input) {
        assertEquals(0, StringUtils.length(input));
    }

    @Test
    void length_ShouldReturnCorrectLength_WhenInputIsSimpleString() {
        assertEquals(5, StringUtils.length("Hello"));
        assertEquals(4, StringUtils.length("سلام"));
        assertEquals(11, StringUtils.length("Hello World"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void hasLength_ShouldReturnFalse_WhenInputIsNullOrEmpty(String input) {
        assertFalse(StringUtils.hasLength(input));
    }

    @Test
    void hasLength_ShouldReturnTrue_WhenInputHasLength() {
        assertTrue(StringUtils.hasLength(SPACE_CHARACTER));
        assertTrue(StringUtils.hasLength("Hello"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void hasText_ShouldReturnFalse_WhenInputIsNullOrEmpty(String input) {
        assertFalse(StringUtils.hasText(input));
    }

    @Test
    void hasText_ShouldReturnTrue_WhenInputHasCharacter() {
        assertTrue(StringUtils.hasText("Hello"));
    }

    @Test
    void hasText_ShouldReturnFalse_WhenInputIsJustSpaceCharacter() {
        assertFalse(StringUtils.hasText(SPACE_CHARACTER));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void containsWhitespace_ShouldReturnFalse_WhenInputIsNullOrEmpty(String input) {
        assertFalse(StringUtils.containsWhitespace(input));
    }

    @Test
    void containsWhitespace_ShouldReturnTrue_WhenInputContainsSpaceCharacter() {
        assertTrue(StringUtils.containsWhitespace(SPACE_CHARACTER));
        assertTrue(StringUtils.containsWhitespace("Hello "));
        assertTrue(StringUtils.containsWhitespace("H ell o"));
    }

    @Test
    void containsWhitespace_ShouldReturnFalse_WhenInputWithoutSpaceCharacter() {
        assertFalse(StringUtils.containsWhitespace("Hello"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void trimAllWhitespace_ShouldReturnEmptyString_WhenInputIsNullOrEmpty(String input) {
        assertEquals(EMPTY, StringUtils.trimAllWhitespace(input));
    }

    @Test
    void trimAllWhitespace_ShouldReturnOriginalStringWithoutSpaceCharacter_WhenInputContainsSpaceCharacter() {
        assertEquals("Hello", StringUtils.trimAllWhitespace("Hello"));
        assertEquals("Hello", StringUtils.trimAllWhitespace("  Hello  "));
        assertEquals("Hello", StringUtils.trimAllWhitespace(" H ell o "));
        assertEquals("Hello", StringUtils.trimAllWhitespace("He\tllo\n"));
    }

    @Test
    void trimAllWhitespace_ShouldReturnEmptyString_WhenInputJustContainsSpaceCharacter() {
        assertEquals(EMPTY, StringUtils.trimAllWhitespace(SPACE_CHARACTER));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void trimLeadingCharacter_ShouldReturnEmptyString_WhenInputIsNullOrEmpty(String input) {
        assertEquals(EMPTY, StringUtils.trimLeadingCharacter(input, '#'));
    }

    @Test
    void trimLeadingCharacter_ShouldReturnOriginalString_WhenNoLeadingCharacterMatch() {
        assertEquals("123", StringUtils.trimLeadingCharacter("123", '0'));
        assertEquals("Hello", StringUtils.trimLeadingCharacter("Hello", '#'));
        assertEquals(" Hello", StringUtils.trimLeadingCharacter(" Hello", '#'));
    }

    @Test
    void trimLeadingCharacter_ShouldTrimSingleLeadingCharacter_WhenOneMatchExists() {
        assertEquals("123", StringUtils.trimLeadingCharacter("0123", '0'));
        assertEquals("Hello", StringUtils.trimLeadingCharacter("#Hello", '#'));
        assertEquals("Hello", StringUtils.trimLeadingCharacter(" Hello", ' '));
    }

    @Test
    void trimLeadingCharacter_ShouldTrimMultipleLeadingCharacters_WhenMultipleMatchesExist() {
        assertEquals("123", StringUtils.trimLeadingCharacter("000123", '0'));
        assertEquals("Hello", StringUtils.trimLeadingCharacter("###Hello", '#'));
        assertEquals("Hello", StringUtils.trimLeadingCharacter("   Hello", ' '));
    }

    @Test
    void trimLeadingCharacter_ShouldReturnEmptyString_WhenAllCharactersMatch() {
        assertEquals(EMPTY, StringUtils.trimLeadingCharacter("000", '0'));
        assertEquals(EMPTY, StringUtils.trimLeadingCharacter("###", '#'));
        assertEquals(EMPTY, StringUtils.trimLeadingCharacter("   ", ' '));
    }

    @Test
    void trimLeadingCharacter_ShouldNotTrimTrailingCharacters_WhenCharactersMatch() {
        assertEquals("12300", StringUtils.trimLeadingCharacter("0012300", '0'));
        assertEquals("Hello##", StringUtils.trimLeadingCharacter("##Hello##", '#'));
        assertEquals("Hello  ", StringUtils.trimLeadingCharacter("  Hello  ", ' '));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void trimTrailingCharacter_ShouldReturnEmptyString_WhenInputIsNullOrEmpty(String input) {
        assertEquals(EMPTY, StringUtils.trimTrailingCharacter(input, '#'));
    }

    @Test
    void trimTrailingCharacter_ShouldReturnOriginalString_WhenNoTrailingCharacterMatch() {
        assertEquals("123", StringUtils.trimTrailingCharacter("123", '0'));
        assertEquals("Hello", StringUtils.trimTrailingCharacter("Hello", '#'));
        assertEquals("Hello ", StringUtils.trimTrailingCharacter("Hello ", '#'));
    }

    @Test
    void trimTrailingCharacter_ShouldTrimSingleTrailingCharacter_WhenOneMatchExists() {
        assertEquals("123", StringUtils.trimTrailingCharacter("1230", '0'));
        assertEquals("Hello", StringUtils.trimTrailingCharacter("Hello#", '#'));
        assertEquals("Hello", StringUtils.trimTrailingCharacter("Hello ", ' '));
    }

    @Test
    void trimTrailingCharacter_ShouldTrimMultipleTrailingCharacters_WhenMultipleMatchesExist() {
        assertEquals("123", StringUtils.trimTrailingCharacter("123000", '0'));
        assertEquals("Hello", StringUtils.trimTrailingCharacter("Hello###", '#'));
        assertEquals("Hello", StringUtils.trimTrailingCharacter("Hello   ", ' '));
    }

    @Test
    void trimTrailingCharacter_ShouldReturnEmptyString_WhenAllCharactersMatch() {
        assertEquals(EMPTY, StringUtils.trimTrailingCharacter("0000", '0'));
        assertEquals(EMPTY, StringUtils.trimTrailingCharacter("####", '#'));
        assertEquals(EMPTY, StringUtils.trimTrailingCharacter("    ", ' '));
    }

    @Test
    void trimTrailingCharacter_ShouldNotTrimLeadingCharacters_WhenCharactersMatch() {
        assertEquals("00123", StringUtils.trimTrailingCharacter("0012300", '0'));
        assertEquals("##Hello", StringUtils.trimTrailingCharacter("##Hello##", '#'));
        assertEquals("  Hello", StringUtils.trimTrailingCharacter("  Hello  ", ' '));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void startsWithIgnoreCase_ShouldReturnFalse_WhenStringIsNullOrEmpty(String str) {
        assertFalse(StringUtils.startsWithIgnoreCase(str, "he"));
    }

    @ParameterizedTest
    @NullSource
    void startsWithIgnoreCase_ShouldReturnFalse_WhenPrefixIsNullOrEmpty(String prefix) {
        assertFalse(StringUtils.startsWithIgnoreCase("Hello", prefix));
    }

    @Test
    void startsWithIgnoreCase_ShouldReturnTrue_WhenStringStartsWithPrefix() {
        assertTrue(StringUtils.startsWithIgnoreCase("Hello World", "Hello"));
        assertTrue(StringUtils.startsWithIgnoreCase("Hello World", "hello"));
        assertTrue(StringUtils.startsWithIgnoreCase("Hello World", "HELLO"));
    }

    @Test
    void startsWithIgnoreCase_ShouldReturnTrue_WhenStringUnicodeStartsWithPrefixUnicode() {
        assertTrue(StringUtils.startsWithIgnoreCase("ŞĞÜöç", "şğü"));
    }

    @Test
    void startsWithIgnoreCase_ShouldReturnFalse_WhenStringDoesNotStartWithPrefix() {
        assertFalse(StringUtils.startsWithIgnoreCase("HelloWorld", "world"));
    }

    @Test
    void startsWithIgnoreCase_ShouldReturnFalse_WhenStringUnicodeDoesNotStartWithPrefixUnicode() {
        assertFalse(StringUtils.startsWithIgnoreCase("şğü", "ŞĞÜöç"));
    }

    @Test
    void startsWithIgnoreCase_ShouldReturnFalse_WhenStringIsShorterThanPrefix() {
        assertFalse(StringUtils.startsWithIgnoreCase("Hi", "Hello"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void endsWithIgnoreCase_ShouldReturnFalse_WhenStringIsNullOrEmpty(String input) {
        assertFalse(StringUtils.endsWithIgnoreCase(input, "lo"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void endsWithIgnoreCase_ShouldReturnFalse_WhenSuffixIsNullOrEmpty(String input) {
        assertFalse(StringUtils.endsWithIgnoreCase("Hello", input));
    }

    @Test
    void endsWithIgnoreCase_ShouldReturnTrue_WhenStringEndsWithSuffixSameCase() {
        assertTrue(StringUtils.endsWithIgnoreCase("Hello World", "World"));
        assertTrue(StringUtils.endsWithIgnoreCase("Hello World", "world"));
        assertTrue(StringUtils.endsWithIgnoreCase("Hello World", "WORLD"));
    }

    @Test
    void endsWithIgnoreCase_ShouldReturnTrue_WhenEndingUnicodeEndsWithSuffixUnicode() {
        assertTrue(StringUtils.endsWithIgnoreCase("MerhabaDünya", "DÜNYA"));
    }

    @Test
    void endsWithIgnoreCase_ShouldReturnFalse_WhenStringIsShorterThanSuffix() {
        assertFalse(StringUtils.endsWithIgnoreCase("Hi", "Hello"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void contains_ShouldReturnFalse_WhenStringIsNullOrEmpty(String input) {
        assertFalse(StringUtils.contains(input, "Hello"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void contains_ShouldReturnFalse_WhenSubstringIsNullOrEmpty(String input) {
        assertFalse(StringUtils.contains("Hello", input));
    }

    @Test
    void contains_ShouldReturnFalse_WhenStringDoesNotContainSubstring() {
        assertFalse(StringUtils.contains("Hello World", "Java"));
    }

    @Test
    void contains_ShouldReturnFalse_WhenSubstringIsLongerThanString() {
        assertFalse(StringUtils.contains("Hello", "Hello World"));
    }


    @Test
    void contains_ShouldReturnTrue_WhenStringContainsSubstring() {
        assertTrue(StringUtils.contains("HelloWorld", "ello"));
    }

    @Test
    void contains_ShouldReturnTrue_WhenSubstringLengthEqualsStringLength() {
        assertTrue(StringUtils.contains("Hello", "Hello"));
    }

    @Test
    void contains_ShouldReturnTrue_ForPartialMatchAtStart() {
        assertTrue(StringUtils.contains("Hello World", "Hello"));
    }

    @Test
    void contains_ShouldReturnTrue_ForPartialMatchAtEnd() {
        assertTrue(StringUtils.contains("Hello World", "World"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void containsAtPosition_ShouldReturnFalse_WhenStringIsNullOrEmpty(String input) {
        assertFalse(StringUtils.containsAtPosition(input, "Hello", 0));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void containsAtPosition_ShouldReturnFalse_WhenSubstringIsNullOrEmpty(String input) {
        assertFalse(StringUtils.containsAtPosition("Hello", input, 0));
    }

    @Test
    void containsAtPosition_ShouldReturnFalse_WhenSubstringDoesNotMatchAtPosition() {
        assertFalse(StringUtils.containsAtPosition("HelloWorld", "World", 6));
    }

    @Test
    void containsAtPosition_ShouldReturnFalse_WhenIndexIsNegative() {
        assertFalse(StringUtils.containsAtPosition("HelloWorld", "He", -1));
    }

    @Test
    void containsAtPosition_ShouldReturnFalse_WhenSubstringExceedsStringLength() {
        assertFalse(StringUtils.containsAtPosition("Hello", "HelloWorld", 0));
    }

    @Test
    void containsAtPosition_ShouldReturnFalse_WhenPositionPlusLengthExceedsStringLength() {
        assertFalse(StringUtils.containsAtPosition("Hello", "ello", 2));
    }

    @Test
    void containsAtPosition_ShouldReturnTrue_ForZeroIndex() {
        assertTrue(StringUtils.containsAtPosition("Hello", "Hello", 0));
    }

    @Test
    void containsAtPosition_ShouldReturnTrue_WhenSubstringExistsAtExactPosition() {
        assertTrue(StringUtils.containsAtPosition("HelloWorld", "World", 5));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void containsAny_ShouldReturnFalse_WhenStringIsNullOrEmpty(String input) {
        assertFalse(StringUtils.containsAny(input, "Hello"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void containsAny_ShouldReturnFalse_WhenSearchStringIsNullOrEmpty(String input) {
        assertFalse(StringUtils.containsAny("Hello", input));
    }

    @Test
    void containsAny_ShouldReturnFalse_WhenStringDoesNotContainSearchString() {
        assertFalse(StringUtils.containsAny("Hello World", "hello", "world"));
    }

    @Test
    void containsAny_ShouldReturnTrue_WhenStringContainsSearchString() {
        assertTrue(StringUtils.containsAny("Hello World", "Hello", "world"));
        assertTrue(StringUtils.containsAny("Hello World", "hello", "World"));
        assertTrue(StringUtils.containsAny("Hello World", "Hello", "world"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void containsWithKMPAlgorithm_ShouldReturnFalse_WhenStringIsNullOrEmpty(String input) {
        assertFalse(StringUtils.containsWithKMPAlgorithm(input, "Hello"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void containsWithKMPAlgorithm_ShouldReturnFalse_WhenSubstringIsNullOrEmpty(String input) {
        assertFalse(StringUtils.containsWithKMPAlgorithm("Hello", input));
    }

    @Test
    void containsWithKMPAlgorithm_ShouldReturnFalse_WhenSubstringDoesNotExist() {
        assertFalse(StringUtils.containsWithKMPAlgorithm("ABCABCDABABCDABCDABDE", "ABCDABE"));
    }

    @Test
    void containsWithKMPAlgorithm_ShouldReturnFalse_WhenSubstringIsLongerThanString() {
        assertFalse(StringUtils.containsWithKMPAlgorithm("Hello", "Hello World"));
    }

    @Test
    void containsWithKMPAlgorithm_ShouldReturnFalse_WhenCaseSensitiveMatching() {
        assertFalse(StringUtils.containsWithKMPAlgorithm("HelloWorld", "world"));
    }

    @Test
    void containsWithKMPAlgorithm_ShouldReturnTrue_WhenHandleRepeatedPatternsCorrectly() {
        assertTrue(StringUtils.containsWithKMPAlgorithm("ABABABABAB", "ABABA"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void countOccurrencesOf_ShouldReturnZero_WhenStringIsNullOrEmpty(String input) {
        assertEquals(0, StringUtils.countOccurrencesOf(input, "hello"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void countOccurrencesOf_ShouldReturnZero_WhenSubstringIsNullOrEmpty(String input) {
        assertEquals(0, StringUtils.countOccurrencesOf("hello", input));
    }

    @Test
    void countOccurrencesOf_ShouldReturnZero_WhenNoOccurrenceFound() {
        assertEquals(0, StringUtils.countOccurrencesOf("hello world", "java"));
    }

    @Test
    void countOccurrencesOf_ShouldReturnZero_WhenSubstringIsLongerThanString() {
        assertEquals(0, StringUtils.countOccurrencesOf("hello", "hello world"));
    }

    @Test
    void countOccurrencesOf_ShouldReturnOne_WhenSingleOccurrenceExists() {
        assertEquals(1, StringUtils.countOccurrencesOf("hello world", "hello"));
        assertEquals(1, StringUtils.countOccurrencesOf("Hello hello HELLO", "hello"));
    }

    @Test
    void countOccurrencesOf_ShouldReturnCorrectCount_WhenMultipleNonOverlappingOccurrencesExist() {
        assertEquals(2, StringUtils.countOccurrencesOf("hello world, hello universe", "hello"));
    }

    @Test
    void countOccurrencesOf_ShouldReturnCorrectCount_WhenMultipleOverlappingOccurrencesExist() {
        assertEquals(2, StringUtils.countOccurrencesOf("abababa", "aba"));
    }

    @Test
    void countOccurrencesOf_ShouldReturnCorrectCount_WhenSubstringEqualsString() {
        assertEquals(1, StringUtils.countOccurrencesOf("hello", "hello"));
    }

    @Test
    void countOccurrencesOf_ShouldReturnCorrectCount_WhenSingleOccurrenceExistsWithUnicodeStringAndUnicodeSubString() {
        assertEquals(1, StringUtils.countOccurrencesOf("سلام دنیا", "سلام"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void replace_ShouldReturnOriginal_WhenOriginalIsNullOrEmpty(String original) {
        assertEquals(original, StringUtils.replace(original, "pattern", "replacement"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void replace_ShouldReturnOriginal_WhenPatternIsNullOrEmpty(String pattern) {
        assertEquals("original", StringUtils.replace("original", pattern, "replacement"));
    }

    @Test
    void replace_ShouldReturnOriginal_WhenReplacementIsNull() {
        assertEquals("original", StringUtils.replace("original", "pattern", null));
    }

    @Test
    void replace_ShouldReplaceSingleOccurrence_WhenPatternExistsInOriginalString() {
        assertEquals("Hello Universe!", StringUtils.replace("Hello World!", "World", "Universe"));
        assertEquals("100 dollars", StringUtils.replace("100$", "$", " dollars"));
        assertEquals("سلام جهان", StringUtils.replace("سلام دنیا", "دنیا", "جهان"));
    }

    @Test
    void replace_ShouldReplaceMultipleOccurrences_WhenPatternExistsInOriginalString() {
        assertEquals("foo-bar-foo-bar", StringUtils.replace("foo.bar.foo.bar", ".", "-"));
    }

    @Test
    void replace_ShouldHandleEmptyReplacement_WhenPatternExistsInOriginalString() {
        assertEquals("HellWrld", StringUtils.replace("HelloWorld", "o", ""));
    }

    @Test
    void replace_ShouldReturnOriginal_WhenPatternNotFound() {
        assertEquals("Java is great", StringUtils.replace("Java is great", "java", "Kotlin"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void replaceRegex_ShouldReturnOriginal_WhenOriginalIsNullOrEmpty(String original) {
        assertEquals(original, StringUtils.replaceRegex(original, "\\d+", "replacement"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void replaceRegex_ShouldReturnOriginal_WhenRegexIsNullOrEmpty(String regex) {
        assertEquals("original", StringUtils.replaceRegex("original", regex, "replacement"));
    }

    @Test
    void replaceRegex_ShouldReturnOriginal_WhenReplacementIsNull() {
        assertEquals("original", StringUtils.replaceRegex("original", "\\d+", null));
    }

    @Test
    void replaceRegex_ShouldReplaceSingleOccurrence_WhenRegexExistsInOriginalString() {
        assertEquals("100 dollars", StringUtils.replaceRegex("100$", "\\$", " dollars"));
    }

    @Test
    void replaceRegex_ShouldReplaceMultipleOccurrences_WhenRegexExistsInOriginalString() {
        assertEquals("Number: XXX, Number: XXX",
                StringUtils.replaceRegex("Number: 123, Number: 456", "\\d+", "XXX"));

        assertEquals("one two three", StringUtils.replaceRegex("one  two   three", "\\s+", " "));
    }

    @Test
    void replaceRegex_ShouldHandleGroupReplacement_WhenRegexExistsInOriginalString() {
        assertEquals("Doe, John", StringUtils.replaceRegex("John Doe", "(\\w+)\\s(\\w+)", "$2, $1"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void normalizeArabic_ShouldReturnNull_WhenStringIsNullOrEmpty(String input) {
        assertNull(StringUtils.normalizeArabic(input));
    }

    @Test
    void normalizeArabic_ShouldReplaceWithEquivalentPersianCharacter_WhenStringContainsSpecialCharacters() {
        assertEquals("کمک", StringUtils.normalizeArabic("کمك"));
        assertEquals("محمدی", StringUtils.normalizeArabic("محمدي"));
        assertEquals("مدرسه", StringUtils.normalizeArabic("مدرسة"));
        assertEquals("خانه", StringUtils.normalizeArabic("خانۀ"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void capitalize_ShouldReturnOriginalString_WhenInputIsNullOrEmpty(String input) {
        assertEquals(input, StringUtils.capitalize(input));
    }

    @Test
    void capitalize_ShouldReturnCapitalizedString_WhenFirstCharacterIsNotCapital() {
        assertEquals("Hello", StringUtils.capitalize("hello"));
    }

    @Test
    void capitalize_ShouldReturnOriginalString_WhenFirstCharacterIsCapital() {
        assertEquals("Hello", StringUtils.capitalize("Hello"));
    }

    @Test
    void capitalize_ShouldReturnOriginalString_WhenFirstCharacterIsNumberOrSpecialChars() {
        assertEquals("1test", StringUtils.capitalize("1test"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void uncapitalize_ShouldReturnOriginalString_WhenInputIsNullOrEmpty(String input) {
        assertEquals(input, StringUtils.uncapitalize(input));
    }

    @Test
    void uncapitalize_ShouldReturnUnCapitalizedString_WhenFirstCharacterIsCapital() {
        assertEquals("hello", StringUtils.uncapitalize("Hello"));
        assertEquals("hELLO", StringUtils.uncapitalize("HELLO"));
    }

    @Test
    void uncapitalize_ShouldReturnOriginalString_WhenFirstCharacterIsNotCapital() {
        assertEquals("hello", StringUtils.uncapitalize("hello"));
    }

    @Test
    void uncapitalize_ShouldReturnOriginalString_WhenFirstCharacterIsNumberOrSpecialChars() {
        assertEquals("1Hello", StringUtils.uncapitalize("1Hello"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void chomp_ShouldReturnOriginalString_WhenInputIsNullOrEmpty(String input) {
        assertEquals(input, StringUtils.chomp(input));
    }

    @Test
    void chomp_ShouldReturnOriginalString_WhenNewLineCharactersAreNotAtTheEndOfTheString() {
        assertEquals("Hello \r World", StringUtils.chomp("Hello \r World"));
        assertEquals("Hello \n World", StringUtils.chomp("Hello \n World"));
        assertEquals("Hello \r\n World", StringUtils.chomp("Hello \r\n World"));
    }

    @Test
    void chomp_ShouldRemovesOneNewLineFromEndOfString_WhenEndOfTheStringHasOneOfTheNewlineCharacters() {
        assertEquals(EMPTY, StringUtils.chomp("\r"));
        assertEquals(EMPTY, StringUtils.chomp("\n"));
        assertEquals(EMPTY, StringUtils.chomp("\r\n"));

        assertEquals("Hello", StringUtils.chomp("Hello\r"));
        assertEquals("Hello", StringUtils.chomp("Hello\n"));
        assertEquals("Hello", StringUtils.chomp("Hello\r\n"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void wrap_ShouldReturnOriginalString_WhenInputStringIsNullOrEmpty(String input) {
        assertEquals(input, StringUtils.wrap(input, "'"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void wrap_ShouldReturnOriginalString_WhenWrapWithStringIsNullOrEmpty(String input) {
        assertEquals("Hello", StringUtils.wrap("Hello", input));
    }

    @Test
    void wrap_ShouldReturnWrappedString_WhenInputAndWrapWithStringIsNotNullOrNotEmpty() {
        assertEquals("'Hello'", StringUtils.wrap("Hello", "'"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void wrapIfMissing_ShouldReturnOriginalString_WhenInputStringIsNullOrEmpty(String input) {
        assertEquals(input, StringUtils.wrapIfMissing(input, "'"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void wrapIfMissing_ShouldReturnOriginalString_WhenWrapWithStringIsNullOrEmpty(String input) {
        assertEquals("Hello", StringUtils.wrapIfMissing("Hello", input));
    }

    @Test
    void wrapIfMissing_ShouldReturnOriginalString_WhenTheStringIsAlreadyWrapped() {
        assertEquals("'Hello'", StringUtils.wrapIfMissing("'Hello'", "'"));
    }

    @Test
    void wrapIfMissing_ShouldReturnWrappedString_WhenInputAndWrapWithStringIsNotNullOrNotEmpty() {
        assertEquals("'Hello'", StringUtils.wrapIfMissing("Hello", "'"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void unwrap_ShouldReturnOriginalString_WhenInputStringIsNullOrEmpty(String input) {
        assertEquals(input, StringUtils.unwrap(input, "'"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void unwrap_ShouldReturnOriginalString_WhenUnwrapWithStringIsNullOrEmpty(String input) {
        assertEquals("Hello", StringUtils.unwrap("Hello", input));
    }

    @Test
    void unwrap_ShouldReturnOriginalString_WhenInputStringLengthIsLessThanUnwrapWithStringLent() {
        assertEquals("Hello", StringUtils.unwrap("Hello", "'Hello'"));
    }

    @Test
    void unwrap_ShouldReturnUnWrappedString_WhenInputAndUnWrapWithStringIsNotNullOrNotEmpty() {
        assertEquals("Hello", StringUtils.unwrap("'Hello'", "'"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void truncate_ShouldReturnOriginalString_WhenInputAndUnWrapWithStringIsNotNullOrNotEmpty(String input) {
        assertEquals(input, StringUtils.truncate(input, 5));
    }

    @Test
    void truncate_ShouldThrowsIllegalArgumentException_WhenMaxLengthIsLessThanZero() {
        assertThrows(IllegalArgumentException.class, () -> StringUtils.truncate("Hello world", -5));
    }

    @Test
    void truncate_ShouldReturnTruncateString_WhenInputStringIsNotNullOrNotEmpty() {
        assertEquals("Hello ...", StringUtils.truncate("Hello world", 5));
        assertEquals("Hello ...", StringUtils.truncate("Hello world", 6));
    }
}
