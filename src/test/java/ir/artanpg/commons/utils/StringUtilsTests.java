package ir.artanpg.commons.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;

import static ir.artanpg.commons.utils.StringUtils.EMPTY;
import static ir.artanpg.commons.utils.StringUtils.SPACE_CHARACTER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the {@link StringUtils} class.
 */
class StringUtilsTests {

    @ParameterizedTest
    @NullAndEmptySource
    void hasLength_ShouldReturnFalse_WhenInputIsNullOrEmpty(String input) {
        assertFalse(StringUtils.hasLength(input));
    }

    @Test
    void hasLength_ShouldReturnTrue_WhenInputHasLength() {
        assertTrue(StringUtils.hasLength(SPACE_CHARACTER));
        assertTrue(StringUtils.hasLength("hello"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void hasText_ShouldReturnFalse_WhenInputIsNullOrEmpty(String input) {
        assertFalse(StringUtils.hasText(input));
    }

    @Test
    void hasText_ShouldReturnTrue_WhenInputHasCharacter() {
        assertTrue(StringUtils.hasText("hello"));
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
        assertTrue(StringUtils.containsWhitespace("hello "));
        assertTrue(StringUtils.containsWhitespace("h ell o"));
    }

    @Test
    void containsWhitespace_ShouldReturnFalse_WhenInputWithoutSpaceCharacter() {
        assertFalse(StringUtils.containsWhitespace("hello"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void trimAllWhitespace_ShouldReturnEmptyString_WhenInputIsNullOrEmpty(String input) {
        assertEquals(EMPTY, StringUtils.trimAllWhitespace(input));
    }

    @Test
    void trimAllWhitespace_ShouldReturnOriginalStringWithoutSpaceCharacter_WhenInputContainsSpaceCharacter() {
        assertEquals("hello", StringUtils.trimAllWhitespace("hello"));
        assertEquals("hello", StringUtils.trimAllWhitespace("  hello  "));
        assertEquals("hello", StringUtils.trimAllWhitespace(" h ell o "));
        assertEquals("hello", StringUtils.trimAllWhitespace("he\tllo\n"));
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
        assertEquals("hello", StringUtils.trimLeadingCharacter("hello", '#'));
        assertEquals(" hello", StringUtils.trimLeadingCharacter(" hello", '#'));
    }

    @Test
    void trimLeadingCharacter_ShouldTrimSingleLeadingCharacter_WhenOneMatchExists() {
        assertEquals("123", StringUtils.trimLeadingCharacter("0123", '0'));
        assertEquals("hello", StringUtils.trimLeadingCharacter("#hello", '#'));
        assertEquals("hello", StringUtils.trimLeadingCharacter(" hello", ' '));
    }

    @Test
    void trimLeadingCharacter_ShouldTrimMultipleLeadingCharacters_WhenMultipleMatchesExist() {
        assertEquals("123", StringUtils.trimLeadingCharacter("000123", '0'));
        assertEquals("hello", StringUtils.trimLeadingCharacter("###hello", '#'));
        assertEquals("hello", StringUtils.trimLeadingCharacter("   hello", ' '));
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
        assertEquals("hello##", StringUtils.trimLeadingCharacter("##hello##", '#'));
        assertEquals("hello  ", StringUtils.trimLeadingCharacter("  hello  ", ' '));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void trimTrailingCharacter_ShouldReturnEmptyString_WhenInputIsNullOrEmpty(String input) {
        assertEquals(EMPTY, StringUtils.trimTrailingCharacter(input, '#'));
    }

    @Test
    void trimTrailingCharacter_ShouldReturnOriginalString_WhenNoTrailingCharacterMatch() {
        assertEquals("123", StringUtils.trimTrailingCharacter("123", '0'));
        assertEquals("hello", StringUtils.trimTrailingCharacter("hello", '#'));
        assertEquals("hello ", StringUtils.trimTrailingCharacter("hello ", '#'));
    }

    @Test
    void trimTrailingCharacter_ShouldTrimSingleTrailingCharacter_WhenOneMatchExists() {
        assertEquals("123", StringUtils.trimTrailingCharacter("1230", '0'));
        assertEquals("hello", StringUtils.trimTrailingCharacter("hello#", '#'));
        assertEquals("hello", StringUtils.trimTrailingCharacter("hello ", ' '));
    }

    @Test
    void trimTrailingCharacter_ShouldTrimMultipleTrailingCharacters_WhenMultipleMatchesExist() {
        assertEquals("123", StringUtils.trimTrailingCharacter("123000", '0'));
        assertEquals("hello", StringUtils.trimTrailingCharacter("hello###", '#'));
        assertEquals("hello", StringUtils.trimTrailingCharacter("hello   ", ' '));
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
        assertEquals("##hello", StringUtils.trimTrailingCharacter("##hello##", '#'));
        assertEquals("  hello", StringUtils.trimTrailingCharacter("  hello  ", ' '));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void startsWithIgnoreCase_ShouldReturnFalse_WhenStringIsNullOrEmpty(String str) {
        assertFalse(StringUtils.startsWithIgnoreCase(str, "he"));
    }

    @ParameterizedTest
    @NullSource
    void startsWithIgnoreCase_ShouldReturnFalse_WhenPrefixIsNull(String prefix) {
        assertFalse(StringUtils.startsWithIgnoreCase("Hello", prefix));
    }

    @ParameterizedTest
    @EmptySource
    void startsWithIgnoreCase_ShouldReturnTrue_WhenPrefixIsEmpty(String prefix) {
        assertTrue(StringUtils.startsWithIgnoreCase("Hello", prefix));
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
        assertFalse(StringUtils.startsWithIgnoreCase("hi", "hello"));
    }
}
