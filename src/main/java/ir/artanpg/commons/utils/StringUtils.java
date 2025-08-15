package ir.artanpg.commons.utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * Provides utility methods for {@link String} instances.
 *
 * @author Mohammad Yazdian
 */
public abstract class StringUtils {

    /**
     * The empty String {@code ""}.
     */
    public static final String EMPTY = "";

    /**
     * The space String {@code " "}.
     */
    public static final String SPACE = " ";

    /**
     * The null String {@code "null"}.
     */
    public static final String NULL = "null";

    /**
     * Carriage return character CR ('\r', Unicode 000d).
     */
    public static final char CR = '\r';

    /**
     * Linefeed character LF ({@code '\n'}, Unicode 000a).
     */
    public static final char LF = '\n';

    /**
     * This is a 3 character version of an ellipsis.
     */
    public static final String ELLIPSIS3 = "...";

    /**
     * The maximum size to which the padding constant(s) can expand.
     */
    private static final int PAD_LIMIT = 8192;

    /**
     * Represents a failed index search.
     */
    private static final int INDEX_NOT_FOUND = -1;

    private StringUtils() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    /**
     * Abbreviates a string to a specified maximum width by appending a default
     * ellipsis marker ({@code ...}).
     *
     * <p>Examples:
     * <pre>
     *  StringUtils.abbreviate(null, 4);           = null
     *  StringUtils.abbreviate("", 4);             = ""
     *  StringUtils.abbreviate("Hello World", 4);  = "H..."
     *  StringUtils.abbreviate("Hello World", 8);  = "Hello..."
     *  StringUtils.abbreviate("Hello World", 11); = "Hello World"
     *  StringUtils.abbreviate("Hello World", 12); = "Hello World"
     *  StringUtils.abbreviate("Hello World", 3);  = IllegalArgumentException
     * </pre>
     *
     * @param str      the string to abbreviate, may be null or empty
     * @param maxWidth the maximum length of the resulting string, including the ellipsis marker
     * @return the abbreviated string, or the original string if no abbreviation is needed
     * @throws IllegalArgumentException if maxWidth is less than the minimum required width (length of abbrevMarker + 1)
     * @see #abbreviate(String, String, int)
     */
    public static String abbreviate(String str, int maxWidth) {
        return abbreviate(str, ELLIPSIS3, maxWidth);
    }

    /**
     * Abbreviates a string to a specified maximum width by appending an
     * abbreviation marker.
     *
     * <p>If the string length is less than or equal to the maximum width, it
     * is returned unchanged.
     *
     * <p>If the string is longer, it is truncated and the abbreviation marker
     * is appended to fit within the maximum width.
     *
     * <p>Examples:
     * <pre>
     *  StringUtils.abbreviate(null, "...", 4);           = null
     *  StringUtils.abbreviate("", "...", 4);             = ""
     *  StringUtils.abbreviate("Hello World", "...", 4);  = "H..."
     *  StringUtils.abbreviate("Hello World", "...", 8);  = "Hello..."
     *  StringUtils.abbreviate("Hello World", "...", 11); = "Hello World"
     *  StringUtils.abbreviate("Hello World", "...", 12); = "Hello World"
     *  StringUtils.abbreviate("Hello World", "...", 3);  = IllegalArgumentException
     * </pre>
     *
     * @param string       the string to abbreviate, may be null or empty
     * @param abbrevMarker the marker to append when abbreviating, may be null or empty
     * @param maxWidth     the maximum length of the result, including the abbreviation marker
     * @return the abbreviated string, or the original string if no abbreviation is needed
     * @throws IllegalArgumentException if maxWidth is less than the minimum required width (length of abbrevMarker + 1)
     */
    public static String abbreviate(String string, String abbrevMarker, int maxWidth) {
        if (hasText(string) && EMPTY.equals(abbrevMarker) && maxWidth > 0) return string.substring(0, maxWidth);
        if (!hasLengthAll(string, abbrevMarker)) return string;

        int abbrevMarkerLength = abbrevMarker.length();
        int minAbbrevWidth = abbrevMarkerLength + 1;

        if (maxWidth < minAbbrevWidth)
            throw new IllegalArgumentException("Minimum abbreviation width is " + minAbbrevWidth);

        int strLen = string.length();
        if (strLen <= maxWidth) return string;

        return string.substring(0, maxWidth - abbrevMarkerLength) + abbrevMarker;
    }

    /**
     * Capitalizes the first character of the given string, handling Unicode
     * characters properly.
     *
     * <p>This method converts the first character of the input string to title
     * case using Unicode code points. If the string is already capitalized or
     * empty, it returns the original string unchanged.
     *
     * <p>The method properly handles supplementary Unicode characters that
     * occupy two char values (surrogate pairs).
     *
     * <p>Examples:
     * <pre>
     *  StringUtils.capitalize(null);      = null
     *  StringUtils.capitalize("");        = ""
     *  StringUtils.capitalize("hello");   = "Hello"
     *  StringUtils.capitalize("hEllo");   = "HEllo"
     *  StringUtils.capitalize("'Hello'"); = "'Hello'"
     * </pre>
     *
     * @param str the String to capitalize, may be null
     * @return the capitalized String, {@code null} if null String input
     * @see #uncapitalize(String)
     */
    public static String capitalize(String str) {
        if (!hasText(str)) return str;

        int firstCodepoint = str.codePointAt(0);
        int newCodePoint = Character.toTitleCase(firstCodepoint);

        if (firstCodepoint == newCodePoint) return str;

        int[] newCodePoints = str.codePoints().toArray();
        newCodePoints[0] = newCodePoint; // copy the first code point

        return new String(newCodePoints, 0, newCodePoints.length);
    }

    /**
     * Removes one newline from end of a String if it's there, otherwise leave
     * it alone.
     *
     * <p>A newline is &quot;{@code \n}&quot;, &quot;{@code \r}&quot;, or
     * &quot;{@code \r\n}&quot;.
     *
     * <p>Examples:
     * <pre>
     *  StringUtils.chomp(null);            = null
     *  StringUtils.chomp("");              = ""
     *  StringUtils.chomp("\r");            = ""
     *  StringUtils.chomp("\n");            = ""
     *  StringUtils.chomp("\r\n");          = ""
     *  StringUtils.chomp("Hello\r");       = "Hello"
     *  StringUtils.chomp("Hello\n");       = "Hello"
     *  StringUtils.chomp("Hello\r\n");     = "Hello"
     *  StringUtils.chomp("Hello\r\n\r\n"); = "Hello\r\n"
     * </pre>
     *
     * @param string the String to chomp a newline from
     * @return String without newline, {@code null} if null String input
     */
    public static String chomp(String string) {
        if (!hasLength(string)) return string;

        int lastIdx = string.length();
        if (lastIdx == 1) {
            char ch = string.charAt(0);
            return (ch == CR || ch == LF) ? EMPTY : string;
        }

        char last = string.charAt(lastIdx - 1);
        lastIdx -= last == LF ? (string.charAt(lastIdx - 2) == CR ? 2 : 1) : last == CR ? 1 : 0;

        return lastIdx == string.length() ? string : string.substring(0, lastIdx);
    }

    /**
     * Remove the last character from a String.
     *
     * <p>If the String ends in {@code \r\n}, then remove both of them.
     *
     * <p>Examples:
     * <pre>
     *  StringUtils.chop(null);         = null
     *  StringUtils.chop("");           = ""
     *  StringUtils.chop("a");          = ""
     *  StringUtils.chop("\r");         = ""
     *  StringUtils.chop("\n");         = ""
     *  StringUtils.chop("\r\n");       = ""
     *  StringUtils.chop("Hello");      = "Hell"
     *  StringUtils.chop("Hello\r");    = "Hell"
     *  StringUtils.chop("Hello\n");    = "Hell"
     *  StringUtils.chop("Hello\r\n");  = "Hello"
     *  StringUtils.chop("Hello\nWorld"); = "Hello\nWorl"
     * </pre>
     *
     * @param string the String to chop last character from
     * @return String without last character
     */
    public static String chop(String string) {
        if (string == null) return null;

        int stringLength = string.length();
        if (stringLength < 2) return EMPTY;

        int lastIdx = stringLength - 1;
        String ret = string.substring(0, lastIdx);
        char last = string.charAt(lastIdx);

        return last == LF || last == CR ? ret.substring(0, lastIdx - 1) : ret;
    }

    /**
     * Tests if String contains a search string, handling {@code null}.
     * <p>This method uses {@link String#contains(CharSequence)} if possible.
     *
     * <p>Examples:
     * <pre>
     *  StringUtils.contains(null, "Hello");          = false
     *  StringUtils.contains("", "Hello");            = false
     *  StringUtils.contains("Hello", null);          = false
     *  StringUtils.contains("Hello", "");            = false
     *  StringUtils.contains("Hello World", "Hello"); = true
     *  StringUtils.contains("Hello World", "hello"); = false
     * </pre>
     *
     * @param string       the string to check
     * @param searchString the string to find
     * @return {@code true}, if the String contains the search string, {@code false} if not or {@code null} string input
     */
    public static boolean contains(String string, String searchString) {
        if (!hasText(string)) return false;
        if (!hasText(searchString)) return false;

        return string.contains(searchString);
    }

    /**
     * Tests if the String contains any character in the given set of
     * characters.
     *
     * <p>Examples:
     * <pre>
     *  StringUtils.containsAny(null, "Hello");                   = false
     *  StringUtils.containsAny("", "Hello");                     = false
     *  StringUtils.containsAny("Hello", null);                   = false
     *  StringUtils.containsAny("Hello", "");                     = false
     *  StringUtils.containsAny("Hello World", "hello", "world"); = false
     *  StringUtils.containsAny("Hello World", "Hello", "World"); = true
     * </pre>
     *
     * @param string       the string to check
     * @param searchString the strings to search for
     * @return the {@code true} if any of the chars are found, {@code false} if no match or null input
     */
    public static boolean containsAny(String string, String... searchString) {
        if (!hasText(string)) return false;
        if (!hasText(searchString)) return false;

        // Initialize Aho-Corasick and add patterns
        AhoCorasick ahoCorasick = new AhoCorasick();
        for (String searchStr : searchString) {
            if (hasText(searchStr)) {
                ahoCorasick.addPattern(searchStr);
            }
        }

        // Build failure links and search
        ahoCorasick.buildFailureLinks();
        return ahoCorasick.search(string);
    }

    /**
     * Tests whether the given String contains any whitespace characters.
     *
     * <p>Whitespace is defined by {@link Character#isWhitespace(char)}.
     *
     * <p>Examples:
     * <pre>
     *  StringUtils.containsWhitespace(null);          = false
     *  StringUtils.containsWhitespace("");            = false
     *  StringUtils.containsWhitespace("Hello");       = false
     *  StringUtils.containsWhitespace(" Hello");      = true
     *  StringUtils.containsWhitespace("Hello ");      = true
     *  StringUtils.containsWhitespace("Hello World"); = true
     * </pre>
     *
     * @param string the string to check
     * @return {@code true}, if the string is not empty and contains at least 1 (breaking) whitespace character
     */
    public static boolean containsWhitespace(String string) {
        if (!hasLength(string)) return false;

        int strLen = string.length();
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(string.charAt(i))) return true;
        }

        return false;
    }

    /**
     * Counts how many times the substring appears in the larger string.
     * Note that the code only counts non-overlapping matches.
     *
     * <p>Examples:
     * <pre>
     *  StringUtils.countMatches(null, "*");        = 0
     *  StringUtils.countMatches("", "*");          = 0
     *  StringUtils.countMatches("Hello", null);    = 0
     *  StringUtils.countMatches("Hello", "");      = 0
     *  StringUtils.countMatches("Hello", "l");     = 2
     *  StringUtils.countMatches("Hello", "ll");    = 1
     *  StringUtils.countMatches("Hello", "Hello"); = 1
     *  StringUtils.countMatches("Hello", "hello"); = 0
     * </pre>
     *
     * @param string    the string to check
     * @param substring the substring to count
     * @return the number of occurrences
     */
    public static int countMatches(String string, String substring) {
        if (!hasLength(string) || !hasLength(substring)) return 0;

        int count = 0;
        int idx = 0;

        while ((idx = string.indexOf(substring, idx)) != INDEX_NOT_FOUND) {
            count++;
            idx += substring.length();
        }

        return count;
    }

    /**
     * Returns either the passed in String, or if the String is
     * {@code null} or {@code empty}, the value of {@code defaultString}.
     *
     * <p>Examples:
     * <pre>
     *  StringUtils.defaultIfNotHasLength(null, "NULL");    = "NULL"
     *  StringUtils.defaultIfNotHasLength("", "NULL");      = "NULL"
     *  StringUtils.defaultIfNotHasLength(" ", "NULL");     = " "
     *  StringUtils.defaultIfNotHasLength("Hello", "NULL"); = "Hello"
     * </pre>
     *
     * @param string        the string to check
     * @param defaultString the default string to return
     * @return the passed in String, or the default string
     * @see #hasLength(String)
     */
    public static String defaultIfNotHasLength(String string, String defaultString) {
        return !hasLength(string) ? defaultString : string;
    }

    /**
     * Returns either the passed in String, or if the String is
     * {@code null} or {@code blank}, the value of {@code defaultString}.
     *
     * <p>Examples:
     * <pre>
     *  StringUtils.defaultIfNotHasText(null, "NULL");    = "NULL"
     *  StringUtils.defaultIfNotHasText("", "NULL");      = "NULL"
     *  StringUtils.defaultIfNotHasText(" ", "NULL");     = "NULL"
     *  StringUtils.defaultIfNotHasText("Hello", "NULL"); = "Hello"
     * </pre>
     *
     * @param string        the string to check
     * @param defaultString the default string to return
     * @return the passed in String, or the default string
     * @see #hasText(String)
     */
    public static String defaultIfNotHasText(String string, String defaultString) {
        return !hasText(string) ? defaultString : string;
    }

    /**
     * Deletes all whitespaces from a String as defined by
     * {@link Character#isWhitespace(char)}.
     *
     * <p>Examples:
     * <pre>
     * StringUtils.deleteWhitespace(null);            = null
     * StringUtils.deleteWhitespace("");              = ""
     * StringUtils.deleteWhitespace(" ");             = ""
     * StringUtils.deleteWhitespace("Hello");         = "Hello"
     * StringUtils.deleteWhitespace(" Hello World "); = "HelloWorld"
     * </pre>
     *
     * @param string the String to delete whitespace from, may be null
     * @return the String without whitespaces, {@code null} if null String input
     */
    public static String deleteWhitespace(String string) {
        if (!hasLength(string)) return string;

        int stringLength = string.length();
        char[] chs = new char[stringLength];
        int count = 0;

        for (int i = 0; i < stringLength; i++) {
            if (!Character.isWhitespace(string.charAt(i))) chs[count++] = string.charAt(i);
        }

        return (count == stringLength) ? string : (count == 0) ? EMPTY : new String(chs, 0, count);
    }

    /**
     * Compares two Strings, and returns the portion where they differ.
     *
     * <p>More precisely, return the remainder of the second String, starting
     * from where it's different from the first.
     *
     * <p>Examples:
     * <pre>
     *  StringUtils.difference(null, null);             = null
     *  StringUtils.difference("", "");                 = ""
     *  StringUtils.difference(null, "Hello");          = "Hello"
     *  StringUtils.difference("", "Hello");            = "Hello"
     *  StringUtils.difference("Hello", null);          = "Hello"
     *  StringUtils.difference("Hello", "");            = "Hello"
     *  StringUtils.difference("Hello", "Hello");       = ""
     *  StringUtils.difference("Hello World", "Hello"); = ""
     *  StringUtils.difference("Hello", "Hello World"); = " World"
     * </pre>
     *
     * @param string1 the first string
     * @param string2 the second string
     * @return the portion of string2 where it differs from string1; returns the empty String if they are equal
     */
    public static String difference(String string1, String string2) {
        if (!hasLength(string1)) return string2;
        if (!hasLength(string2)) return string1;

        int at = indexOfDifference(string1, string2);
        return (at == INDEX_NOT_FOUND) ? EMPTY : string2.substring(at);
    }

    static int indexOfDifference(String string1, String string2) {
        if (Objects.equals(string1, string2)) return INDEX_NOT_FOUND;
        if (string1 == null || string2 == null) return 0;

        int i;
        for (i = 0; i < string1.length() && i < string2.length(); ++i) {
            if (string1.charAt(i) != string2.charAt(i)) break;
        }

        return (i < string2.length() || i < string1.length()) ? i : INDEX_NOT_FOUND;
    }

    /**
     * Returns the first value in the array which is not {@code empty},
     * {@code null} or {@code whitespace} only.
     *
     * <p>If all strings are blank or the array is {@code null} or
     * {@code empty} then {@code null} is returned.
     *
     * <p>Examples:
     * <pre>
     *  StringUtils.firstHasText();                       = null
     *  StringUtils.firstHasText(null, null, null);       = null
     *  StringUtils.firstHasText(null, "", " ");          = null
     *  StringUtils.firstHasText(null, "Hello");          = "Hello"
     *  StringUtils.firstHasText(null, "", " ", "Hello"); = "Hello"
     * </pre>
     *
     * @param strings the strings to test
     * @return the first value from strings which is not blank
     */
    public static String firstHasText(String... strings) {
        if (strings != null) {
            for (String string : strings) {
                if (hasText(string)) return string;
            }
        }
        return null;
    }

    /**
     * Extracts and returns all digit characters from the input string.
     *
     * <p>If the input string is null, empty, or contains no digits, an empty
     * string will be returned.
     *
     * <p>Examples:
     * <pre>
     *  StringUtils.getDigits(null);       = ""
     *  StringUtils.getDigits("");         = ""
     *  StringUtils.getDigits(" ");        = ""
     *  StringUtils.getDigits("123");      = "123"
     *  StringUtils.getDigits("Hello");    = ""
     *  StringUtils.getDigits("Hello123"); = "123"
     * </pre>
     *
     * @param string the input string from which to extract digits
     * @return a string containing only the digit characters from the input string
     * @see #hasText(String)
     */
    public static String getDigits(String string) {
        if (!hasText(string)) return EMPTY;

        char tempChar;
        int length = string.length();
        StringBuilder builder = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            tempChar = string.charAt(i);
            if (Character.isDigit(tempChar)) builder.append(tempChar);
        }

        return builder.toString();
    }

    /**
     * Check that the given {@code String} is neither {@code null} nor of
     * length 0.
     *
     * <p>Examples:
     * <pre>
     * 	StringUtils.hasLength(null);    = false
     * 	StringUtils.hasLength("");      = false
     * 	StringUtils.hasLength(" ");     = true
     * 	StringUtils.hasLength("Hello"); = true
     * </pre>
     *
     * @param string the string to check
     * @return {@code true}, if the string is not {@code null} and has length
     * @see #hasText(String)
     */
    public static boolean hasLength(String string) {
        return string != null && !string.isEmpty();
    }

    /**
     * Checks if at least one of the provided strings has a non-zero length.
     *
     * <p>A string is considered to have length if it is not null and not empty
     * after trimming.
     *
     * <p>Examples:
     * <pre>
     * 	StringUtils.hasLength(null);        = false
     * 	StringUtils.hasLength("", "");      = false
     * 	StringUtils.hasLength(" ", "");     = true
     * 	StringUtils.hasLength("Hello", ""); = true
     * </pre>
     *
     * @param strings the string to check
     * @return {@code true}, if at least one string has a non-zero length
     * @see #hasText(String)
     */
    public static boolean hasLength(String... strings) {
        if (strings == null) return false;

        for (String string : strings) {
            if (StringUtils.hasLength(string)) return true;
        }

        return false;
    }

    /**
     * Checks if all provided strings have length, meaning they are neither
     * {@code null} nor {@code empty}.
     *
     * <p>Examples:
     * <pre>
     * 	StringUtils.hasLengthAll(null);         = false
     * 	StringUtils.hasLengthAll("", "");       = false
     * 	StringUtils.hasLengthAll(" ", "");      = false
     * 	StringUtils.hasLengthAll("Hello", "");  = false
     * 	StringUtils.hasLengthAll("Hello", " "); = true
     * </pre>
     *
     * @param strings the array of strings to check
     * @return {@code true}, if all strings have length
     */
    public static boolean hasLengthAll(String... strings) {
        if (strings == null || strings.length == 0) return false;

        for (String string : strings) {
            if (!StringUtils.hasLength(string)) return false;
        }

        return true;
    }

    /**
     * Check whether the given String contains actual <em>text</em>.
     *
     * <p><strong>Note: </strong>More specifically, this method returns
     * {@code true} if the string is not {@code null}, its length is greater
     * than 0, and it contains at least one non-whitespace character.
     *
     * <p>Examples:
     * <pre>
     * 	StringUtils.hasText(null);    = false
     * 	StringUtils.hasText("");      = false
     * 	StringUtils.hasText(" ");     = false
     * 	StringUtils.hasText("Hello"); = true
     * </pre>
     *
     * @param string the string to check
     * @return {@code true}, if the string is not {@code null}, its length is greater than 0, and it does not
     * contain whitespace only
     * @see String#isBlank()
     */
    public static boolean hasText(String string) {
        return string != null && !string.isBlank();
    }

    /**
     * Checks if at least one of the provided strings contains visible,
     * non-whitespace text.
     *
     * <p>A strings are considered to have text if it is not {@code null},
     * not {@code empty}, and contains at least one non-whitespace character.
     *
     * <p>Examples:
     * <pre>
     * 	StringUtils.hasText(null);        = false
     * 	StringUtils.hasText("", "");      = false
     * 	StringUtils.hasText(" ", "");     = false
     * 	StringUtils.hasText("Hello", ""); = true
     * </pre>
     *
     * @param strings the strings to check
     * @return {@code true} if at least one strings contains non-whitespace text
     */
    public static boolean hasText(String... strings) {
        if (strings == null) return false;

        for (String string : strings) {
            if (StringUtils.hasText(string)) return true;
        }

        return false;
    }

    /**
     * Checks if all provided strings have text, meaning they are neither
     * {@code null}, {@code empty}, nor consist only of whitespace.
     *
     * <p>Examples:
     * <pre>
     * 	StringUtils.hasTextAll(null);              = false
     * 	StringUtils.hasTextAll("", "");            = false
     * 	StringUtils.hasTextAll(" ", "");           = false
     * 	StringUtils.hasTextAll("Hello", "");       = false
     * 	StringUtils.hasTextAll("Hello", " ");      = false
     * 	StringUtils.hasTextAll("Hello", " World"); = true
     * </pre>
     *
     * @param strings the array of strings to check
     * @return {@code true} if all strings have text
     */
    public static boolean hasTextAll(String... strings) {
        if (strings == null || strings.length == 0) return false;

        for (String string : strings) {
            if (!StringUtils.hasText(string)) return false;
        }

        return true;
    }

    /**
     * Tests if the String contains only lowercase characters.
     *
     * <p>Examples:
     * <pre>
     *  StringUtils.isAllLowerCase(null);      = false
     *  StringUtils.isAllLowerCase("");        = false
     *  StringUtils.isAllLowerCase(" ");       = false
     *  StringUtils.isAllLowerCase("Hello");   = false
     *  StringUtils.isAllLowerCase("Hello ");  = false
     *  StringUtils.isAllLowerCase("Hello 1"); = false
     *  StringUtils.isAllLowerCase("Hello,");  = false
     *  StringUtils.isAllLowerCase("hello");   = true
     * </pre>
     *
     * @param string the string to check
     * @return {@code true}, if only contains lowercase characters
     */
    public static boolean isAllLowerCase(String string) {
        if (!hasText(string)) return false;

        int length = string.length();
        for (int i = 0; i < length; i++) {
            if (!Character.isLowerCase(string.charAt(i))) return false;
        }

        return true;
    }

    /**
     * Tests if the String contains only uppercase characters.
     *
     * <p>Examples:
     * <pre>
     *  StringUtils.isAllUpperCase(null);      = false
     *  StringUtils.isAllUpperCase("");        = false
     *  StringUtils.isAllUpperCase(" ");       = false
     *  StringUtils.isAllUpperCase("Hello");   = false
     *  StringUtils.isAllUpperCase("Hello ");  = false
     *  StringUtils.isAllUpperCase("Hello 1"); = false
     *  StringUtils.isAllUpperCase("Hello,");  = false
     *  StringUtils.isAllUpperCase("HELLO");   = true
     * </pre>
     *
     * @param string the string to check
     * @return {@code true}, if only contains uppercase characters
     */
    public static boolean isAllUpperCase(String string) {
        if (!hasText(string)) return false;

        int length = string.length();
        for (int i = 0; i < length; i++) {
            if (!Character.isUpperCase(string.charAt(i))) return false;
        }

        return true;
    }

    /**
     * Tests if the String contains only Unicode letters.
     *
     * <p>Examples:
     * <pre>
     *  StringUtils.isAlpha(null);     = false
     *  StringUtils.isAlpha("");       = false
     *  StringUtils.isAlpha(" ");      = false
     *  StringUtils.isAlpha("Hello1"); = false
     *  StringUtils.isAlpha("Hello,"); = false
     *  StringUtils.isAlpha("Hello");  = true
     * </pre>
     *
     * @param string the string to check
     * @return {@code true}, if only contains letters
     */
    public static boolean isAlpha(String string) {
        if (!hasText(string)) return false;

        int length = string.length();
        for (int i = 0; i < length; i++) {
            if (!Character.isLetter(string.charAt(i))) return false;
        }

        return true;
    }

    /**
     * Tests if the String contains only Unicode letters or digits.
     *
     * <p>Examples:
     * <pre>
     *  StringUtils.isAlphanumeric(null);     = false
     *  StringUtils.isAlphanumeric("");       = false
     *  StringUtils.isAlphanumeric(" ");      = false
     *  StringUtils.isAlphanumeric("Hello "); = false
     *  StringUtils.isAlphanumeric("Hello,"); = false
     *  StringUtils.isAlphanumeric("Hello");  = true
     *  StringUtils.isAlphanumeric("Hello1"); = true
     * </pre>
     *
     * @param string the string to check
     * @return {@code true}, if only contains letters or digits
     */
    public static boolean isAlphanumeric(String string) {
        if (!hasText(string)) return false;

        int length = string.length();

        for (int i = 0; i < length; i++) {
            if (!Character.isLetterOrDigit(string.charAt(i))) return false;
        }

        return true;
    }

    /**
     * Tests if the String contains only Unicode letters, digits or space.
     *
     * <p>Examples:
     * <pre>
     *  StringUtils.isAlphanumericSpace(null);      = false
     *  StringUtils.isAlphanumericSpace("");        = false
     *  StringUtils.isAlphanumericSpace("Hello,");  = false
     *  StringUtils.isAlphanumericSpace(" ");       = true
     *  StringUtils.isAlphanumericSpace("Hello");   = true
     *  StringUtils.isAlphanumericSpace("Hello ");  = true
     *  StringUtils.isAlphanumericSpace("Hello 1"); = true
     * </pre>
     *
     * @param string the string to check
     * @return {@code true}, if only contains letters, digits or space
     */
    public static boolean isAlphanumericSpace(String string) {
        if (!hasLength(string)) return false;

        int length = string.length();
        char nowChar;
        for (int i = 0; i < length; i++) {
            nowChar = string.charAt(i);
            if (nowChar != ' ' && !Character.isLetterOrDigit(nowChar)) return false;
        }

        return true;
    }

    /**
     * Tests if the String contains only Unicode letters and space.
     *
     * <p>Examples:
     * <pre>
     *  StringUtils.isAlphaSpace(null);     = false
     *  StringUtils.isAlphaSpace("");       = false
     *  StringUtils.isAlphaSpace("Hello1"); = false
     *  StringUtils.isAlphaSpace("Hello,"); = false
     *  StringUtils.isAlphaSpace(" ");      = true
     *  StringUtils.isAlphaSpace("Hello");  = true
     *  StringUtils.isAlphaSpace("Hello "); = true
     * </pre>
     *
     * @param string the string to check
     * @return {@code true}, if only contains letters and space
     */
    public static boolean isAlphaSpace(String string) {
        if (!hasLength(string)) return false;

        int length = string.length();
        char nowChar;
        for (int i = 0; i < length; i++) {
            nowChar = string.charAt(i);
            if (nowChar != ' ' && !Character.isLetter(nowChar)) return false;
        }

        return true;
    }

    /**
     * Tests if the given string represents a valid numeric value, including
     * positive numbers, negative numbers, and decimal numbers.
     *
     * <p>Examples:
     * <pre>
     *  StringUtils.isNumeric(null);   = false
     *  StringUtils.isNumeric("");     = false
     *  StringUtils.isNumeric(" ");    = false
     *  StringUtils.isNumeric("12 3"); = false
     *  StringUtils.isNumeric("12-3"); = false
     *  StringUtils.isNumeric("123");  = true
     *  StringUtils.isNumeric("12.3"); = true
     *  StringUtils.isNumeric("-123"); = true
     *  StringUtils.isNumeric("+123"); = true
     * </pre>
     *
     * @param string the string to check
     * @return {@code true}, if the string represents a valid numeric value
     */
    public static boolean isNumeric(String string) {
        if (!hasText(string)) return false;

        int length = string.length();
        boolean hasDecimalPoint = false;

        char ch;
        for (int i = 0; i < length; i++) {
            ch = string.charAt(i);
            if (i == 0 && (ch == '+' || ch == '-') && length > 1) continue;
            if (ch == '.' && !hasDecimalPoint && i < length - 1) {
                hasDecimalPoint = true;
                continue;
            }
            if (!Character.isDigit(ch)) return false;
        }

        return length > (hasDecimalPoint ? 2 : 1) || (length == 1 && Character.isDigit(string.charAt(0)));
    }

    /**
     * Tests if the String contains only Unicode digits and space.
     * A decimal point is not a Unicode digit and returns false.
     *
     * <p>Examples:
     * <pre>
     *  StringUtils.isNumericSpace(null);   = false
     *  StringUtils.isNumericSpace("");     = false
     *  StringUtils.isNumericSpace("  ");   = false
     *  StringUtils.isNumericSpace("12-3"); = false
     *  StringUtils.isNumericSpace("12.3"); = false
     *  StringUtils.isNumericSpace("123");  = true
     *  StringUtils.isNumericSpace("12 3"); = true
     * </pre>
     *
     * @param string the string to check
     * @return {@code true}, if only contains digits and space
     */
    public static boolean isNumericSpace(String string) {
        if (!hasText(string)) return false;

        int length = string.length();
        char nowChar;
        for (int i = 0; i < length; i++) {
            nowChar = string.charAt(i);
            if (nowChar != ' ' && !Character.isDigit(nowChar)) return false;
        }

        return true;
    }

    /**
     * Tests if the String contains only whitespace.
     *
     * <p>Examples:
     * <pre>
     *  StringUtils.isWhitespace(null);    = false
     *  StringUtils.isWhitespace("");      = false
     *  StringUtils.isWhitespace("Hello"); = false
     *  StringUtils.isWhitespace(" ");     = true
     * </pre>
     *
     * @param string the string to check
     * @return {@code true}, if only contains whitespace
     */
    public static boolean isWhitespace(String string) {
        if (!hasLength(string)) return false;

        int length = string.length();
        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(string.charAt(i))) return false;
        }

        return true;
    }

    /**
     * Returns the leftmost substring of the specified string with the given
     * length.
     *
     * <p>If the string is null, empty, or shorter than or equal to the
     * specified length, the original string is returned unchanged.
     *
     * <p>Examples:
     * <pre>
     *  StringUtils.left(null, 0);     = null
     *  StringUtils.left("", 0);       = ""
     *  StringUtils.left("Hello", 0);  = ""
     *  StringUtils.left("Hello", 2);  = "He"
     *  StringUtils.left("Hello", 4);  = "Hell"
     *  StringUtils.left("Hello", 5);  = "Hello"
     *  StringUtils.left("Hello", 6);  = "Hello"
     *  StringUtils.left("Hello", -1); = IllegalArgumentException
     * </pre>
     *
     * @param string the string to process
     * @param length the number of characters to return from the start of the string
     * @return the leftmost substring of the specified length
     * @throws IllegalArgumentException if the length is negative
     */
    public static String left(String string, int length) {
        if (length < 0) throw new IllegalArgumentException("Length must be positive");
        return (!hasLength(string) || string.length() <= length) ? string : string.substring(0, length);
    }

    /**
     * Gets a String length or {@code 0} if the String is {@code null}.
     *
     * <p>Examples:
     * <pre>
     *  StringUtils.length(null);    = 0
     *  StringUtils.length("");      = 0
     *  StringUtils.length(" ");     = 1
     *  StringUtils.length("Hello"); = 5
     * </pre>
     *
     * @param string the string to process
     * @return string length or {@code 0} if the string is {@code null}
     */
    public static int length(String string) {
        return string == null ? 0 : string.length();
    }

    /**
     * Extracts a substring from the specified string starting at the given
     * position with the specified length.
     *
     * <p>If the string is null or empty, returns original String.
     *
     * <p>If the position is beyond the string's length, an empty string is
     * returned.
     *
     * <p>If the length or position is negative, an
     * {@link IllegalArgumentException} is thrown.
     *
     * <p>If the requested substring extends beyond the string's length, the
     * substring from the position to the end of the string is returned.
     *
     * <p>Examples:
     * <pre>
     *  StringUtils.mid(null, 0, 0);     = null
     *  StringUtils.mid("", 0, 0);       = ""
     *  StringUtils.mid("Hello", 0, 2);  = "He"
     *  StringUtils.mid("Hello", 0, 4);  = "Hell"
     *  StringUtils.mid("Hello", 2, 4);  = "llo"
     *  StringUtils.mid("Hello", 4, 2);  = "o"
     *  StringUtils.mid("Hello", 6, 0);  = ""
     *  StringUtils.mid("Hello", -1, 0); = IllegalArgumentException
     *  StringUtils.mid("Hello", 0, -1); = IllegalArgumentException
     * </pre>
     *
     * @param string   the input string to process
     * @param position the starting index (0-based) from which to extract the substring
     * @param length   the number of characters to extract
     * @return the extracted substring, or {@code null} if the input string is {@code null} or {@code empty},
     * or an {@code empty} string if the position is beyond the string's length
     * @throws IllegalArgumentException if length or position is negative
     */
    public static String mid(String string, int position, int length) {
        if (!hasLength(string)) return string;

        if (length < 0) throw new IllegalArgumentException("Length must be positive");
        if (position < 0) throw new IllegalArgumentException("Position must be positive");

        return (position > string.length()) ? EMPTY :
                (string.length() <= position + length) ?
                        string.substring(position) : string.substring(position, position + length);
    }

    /**
     * Normalizes whitespace in a string by replacing multiple consecutive
     * whitespace characters (including tabs, newlines, and Unicode whitespace
     * like non-breaking space) with a single space, and trimming leading and
     * trailing whitespace.
     *
     * <p>Non-breaking spaces (U+00A0) are converted to regular spaces (U+0020)
     * and if the string consists only of whitespace, an empty string is
     * returned.
     *
     * <p>Examples:
     * <pre>
     *  StringUtils.normalizeSpace(null);            = null
     *  StringUtils.normalizeSpace("");              = ""
     *  StringUtils.normalizeSpace("Hello World");   = "Hello World"
     *  StringUtils.normalizeSpace(" Hello World "); = "Hello World"
     *  StringUtils.normalizeSpace("Hello  World");  = "Hello World"
     * </pre>
     *
     * @param string the string to normalize
     * @return the normalized string with single spaces between words and no leading/trailing whitespace
     */
    public static String normalizeSpace(String string) {
        if (!hasLength(string)) return string;

        StringBuilder builder = new StringBuilder();
        boolean lastWasWhitespace = true;

        char ch;
        for (int i = 0; i < string.length(); i++) {
            ch = string.charAt(i);
            if (Character.isWhitespace(ch) || ch == '\u00A0') {
                if (!lastWasWhitespace) {
                    builder.append(SPACE);
                    lastWasWhitespace = true;
                }
            } else {
                builder.append(ch);
                lastWasWhitespace = false;
            }
        }

        return builder.toString().trim();
    }

    /**
     * Overlays a specified string onto another string, replacing a segment of
     * the original string starting at the given {@code position} with the
     * specified {@code length}.
     *
     * <p>If the input string is {@code null}, returns {@code null}.
     *
     * <p>If the overlay string is {@code null}, it is treated as an
     * {@code empty} string.
     *
     * <p>If the {@code position} or {@code length} exceeds the string's length
     * they are adjusted to the string's length.
     *
     * <p>If the {@code position} is greater than the {#code length}, they are
     * swapped to ensure valid substring boundaries.
     *
     * <p>Examples:
     * <pre>
     * StringUtils.overlay(null, "", 0, 0);          = null
     * StringUtils.overlay("", "Hello", 0, 0);       = "Hello"
     * StringUtils.overlay("Hello", null, 2, 4);     = "Heo"
     * StringUtils.overlay("Hello", "", 2, 4);       = "Heo"
     * StringUtils.overlay("Hello", "", 4, 2);       = "Heo"
     * StringUtils.overlay("Hello", " ", 2, 4);      = "He o"
     * StringUtils.overlay("Hello", "Hello", -2, 3); = IllegalArgumentException
     * StringUtils.overlay("Hello", "Hello", 2, -3); = IllegalArgumentException
     * </pre>
     *
     * @param string   the input string to process; may be null
     * @param overlay  the string to overlay onto the input string; may be null (treated as empty string)
     * @param position the starting index (0-based) where the overlay begins; must be non-negative
     * @param length   the number of characters to replace in the input string; must be non-negative
     * @return the resulting string with the specified segment replaced by the overlay string,
     * or {@code null} if the input string is {@code null}
     * @throws IllegalArgumentException if position or length is negative
     */
    public static String overlay(String string, String overlay, int position, int length) {
        if (string == null) return null;

        if (length < 0) throw new IllegalArgumentException("Length must be positive");
        if (position < 0) throw new IllegalArgumentException("Position must be positive");

        if (overlay == null) overlay = EMPTY;

        int len = string.length();

        if (position > len) position = len;
        if (length > len) length = len;

        if (position > length) {
            int temp = position;
            position = length;
            length = temp;
        }

        return string.substring(0, position) + overlay + string.substring(length);
    }

    /**
     * Removes all occurrences of the specified character from the input
     * string.
     *
     * <p>If the string is {@code null} or {@code empty}, or if the character
     * to remove is not found, the original string is returned unchanged.
     *
     * <p>Examples:
     * <pre>
     *  StringUtils.remove(null, ' ');    = null
     *  StringUtils.remove("", ' ');      = ""
     *  StringUtils.remove("Hello", 'l'); = "Heo"
     *  StringUtils.remove("Hello", 'z'); = "Hello"
     * </pre>
     *
     * @param string the source String to search
     * @param remove the char to search for and remove
     * @return the resulting string with all occurrences of the specified character removed
     */
    public static String remove(String string, char remove) {
        if (!hasLength(string)) return string;

        StringBuilder builder = new StringBuilder(string.length());
        for (char ch : string.toCharArray()) {
            if (ch != remove) builder.append(ch);
        }

        return builder.toString();
    }

    /**
     * Removes a char only if it is at the beginning of a source string,
     * otherwise returns the original string.
     *
     * <pre>
     *  StringUtils.removeStart(null, '/');    = null
     *  StringUtils.removeStart("", '/');      = ""
     *  StringUtils.removeStart(" ", '/');     = " "
     *  StringUtils.removeStart("/path", '/'); = "path"
     * </pre>
     *
     * @param string the source String to search
     * @param remove the char to search for and remove
     * @return the substring with the char removed if found
     */
    public static String removeStart(String string, char remove) {
        if (!hasLength(string)) return string;
        return string.charAt(0) == remove ? string.substring(1) : string;
    }

    /**
     * Removes a char only if it is at the end of a source string,
     * otherwise returns the original string.
     *
     * <p>Examples:
     * <pre>
     *  StringUtils.removeEnd(null, '/');    = null
     *  StringUtils.removeEnd("", '/');      = ""
     *  StringUtils.removeEnd(" ", '/');     = " "
     *  StringUtils.removeEnd("path/", '/'); = "path"
     * </pre>
     *
     * @param string the source String to search
     * @param remove the char to search for and remove
     * @return the substring with the char removed if found
     */
    public static String removeEnd(String string, char remove) {
        if (!hasText(string)) return string;
        int lastIndex = string.length() - 1;
        return string.charAt(lastIndex) == remove ? string.substring(0, lastIndex) : string;
    }

    /**
     * Reverses a String as per {@link StringBuilder#reverse()}.
     *
     * <p>A {@code null} String returns {@code null}.</p>
     *
     * <pre>
     * StringUtils.reverse(null)  = null
     * StringUtils.reverse("")    = ""
     * StringUtils.reverse("bat") = "tab"
     * </pre>
     *
     * @param string the String to reverse, may be null
     * @return the reversed String, {@code null} if null String input
     */
    public static String reverse(String string) {
        return (!hasText(string)) ? string : new StringBuilder(string).reverse().toString();
    }

    /**
     * Gets the rightmost {@code len} characters of a String.
     *
     * <p>If {@code len} characters are not available, or the String
     * is {@code null}, the String will be returned without an
     * exception. An empty String is returned if len is negative.</p>
     *
     * <pre>
     * StringUtils.right(null, *)    = null
     * StringUtils.right(*, -ve)     = ""
     * StringUtils.right("", *)      = ""
     * StringUtils.right("abc", 0)   = ""
     * StringUtils.right("abc", 2)   = "bc"
     * StringUtils.right("abc", 4)   = "abc"
     * </pre>
     *
     * @param str the String to get the rightmost characters from, may be null
     * @param len the length of the required String
     * @return the rightmost characters, {@code null} if null String input
     */
    public static String right(String str, int len) {
        if (str == null) {
            return null;
        }
        if (len < 0) {
            return EMPTY;
        }
        if (str.length() <= len) {
            return str;
        }
        return str.substring(str.length() - len);
    }

    /**
     * Strips whitespace from the start and end of a String.
     *
     * <p>This is similar to {@link #trim(String)} but removes whitespace.
     * Whitespace is defined by {@link Character#isWhitespace(char)}.</p>
     *
     * <p>A {@code null} input String returns {@code null}.</p>
     *
     * <pre>
     * StringUtils.strip(null)     = null
     * StringUtils.strip("")       = ""
     * StringUtils.strip("   ")    = ""
     * StringUtils.strip("abc")    = "abc"
     * StringUtils.strip("  abc")  = "abc"
     * StringUtils.strip("abc  ")  = "abc"
     * StringUtils.strip(" abc ")  = "abc"
     * StringUtils.strip(" ab c ") = "ab c"
     * </pre>
     *
     * @param str the String to remove whitespace from, may be null
     * @return the stripped String, {@code null} if null String input
     */
    public static String strip(String str) {
        return strip(str, null);
    }

    /**
     * Strips any of a set of characters from the start and end of a String.
     * This is similar to {@link String#trim()} but allows the characters
     * to be stripped to be controlled.
     *
     * <p>A {@code null} input String returns {@code null}.
     * An empty string ("") input returns the empty string.</p>
     *
     * <p>If the stripChars String is {@code null}, whitespace is
     * stripped as defined by {@link Character#isWhitespace(char)}.
     * Alternatively use {@link #strip(String)}.</p>
     *
     * <pre>
     * StringUtils.strip(null, *)          = null
     * StringUtils.strip("", *)            = ""
     * StringUtils.strip("abc", null)      = "abc"
     * StringUtils.strip("  abc", null)    = "abc"
     * StringUtils.strip("abc  ", null)    = "abc"
     * StringUtils.strip(" abc ", null)    = "abc"
     * StringUtils.strip("  abcyx", "xyz") = "  abc"
     * </pre>
     *
     * @param str        the String to remove characters from, may be null
     * @param stripChars the characters to remove, null treated as whitespace
     * @return the stripped String, {@code null} if null String input
     */
    public static String strip(String str, String stripChars) {
        str = stripStart(str, stripChars);
        return stripEnd(str, stripChars);
    }

    /**
     * Strips whitespace from the start and end of every String in an array.
     * Whitespace is defined by {@link Character#isWhitespace(char)}.
     *
     * <p>A new array is returned each time, except for length zero.
     * A {@code null} array will return {@code null}.
     * An empty array will return itself.
     * A {@code null} array entry will be ignored.</p>
     *
     * <pre>
     * StringUtils.stripAll(null)             = null
     * StringUtils.stripAll([])               = []
     * StringUtils.stripAll(["abc", "  abc"]) = ["abc", "abc"]
     * StringUtils.stripAll(["abc  ", null])  = ["abc", null]
     * </pre>
     *
     * @param strs the array to remove whitespace from, may be null
     * @return the stripped Strings, {@code null} if null array input
     */
    public static String[] stripAll(String... strs) {
        return stripAll(strs, null);
    }

    /**
     * Strips any of a set of characters from the start and end of every
     * String in an array.
     * <p>Whitespace is defined by {@link Character#isWhitespace(char)}.</p>
     *
     * <p>A new array is returned each time, except for length zero.
     * A {@code null} array will return {@code null}.
     * An empty array will return itself.
     * A {@code null} array entry will be ignored.
     * A {@code null} stripChars will strip whitespace as defined by
     * {@link Character#isWhitespace(char)}.</p>
     *
     * <pre>
     * StringUtils.stripAll(null, *)                = null
     * StringUtils.stripAll([], *)                  = []
     * StringUtils.stripAll(["abc", "  abc"], null) = ["abc", "abc"]
     * StringUtils.stripAll(["abc  ", null], null)  = ["abc", null]
     * StringUtils.stripAll(["abc  ", null], "yz")  = ["abc  ", null]
     * StringUtils.stripAll(["yabcz", null], "yz")  = ["abc", null]
     * </pre>
     *
     * @param strs       the array to remove characters from, may be null
     * @param stripChars the characters to remove, null treated as whitespace
     * @return the stripped Strings, {@code null} if null array input
     */
    public static String[] stripAll(String[] strs, String stripChars) {
        int strsLen = strs != null ? Array.getLength(strs) : 0;
        if (strsLen == 0) {
            return strs;
        }
        String[] newArr = new String[strsLen];
        Arrays.setAll(newArr, i -> strip(strs[i], stripChars));
        return newArr;
    }

    /**
     * Strips any of a set of characters from the end of a String.
     *
     * <p>A {@code null} input String returns {@code null}.
     * An empty string ("") input returns the empty string.</p>
     *
     * <p>If the stripChars String is {@code null}, whitespace is
     * stripped as defined by {@link Character#isWhitespace(char)}.</p>
     *
     * <pre>
     * StringUtils.stripEnd(null, *)          = null
     * StringUtils.stripEnd("", *)            = ""
     * StringUtils.stripEnd("abc", "")        = "abc"
     * StringUtils.stripEnd("abc", null)      = "abc"
     * StringUtils.stripEnd("  abc", null)    = "  abc"
     * StringUtils.stripEnd("abc  ", null)    = "abc"
     * StringUtils.stripEnd(" abc ", null)    = " abc"
     * StringUtils.stripEnd("  abcyx", "xyz") = "  abc"
     * StringUtils.stripEnd("120.00", ".0")   = "12"
     * </pre>
     *
     * @param str        the String to remove characters from, may be null
     * @param stripChars the set of characters to remove, null treated as whitespace
     * @return the stripped String, {@code null} if null String input
     */
    public static String stripEnd(String str, String stripChars) {
        int end = length(str);
        if (end == 0) {
            return str;
        }

        if (stripChars == null) {
            while (end != 0 && Character.isWhitespace(str.charAt(end - 1))) {
                end--;
            }
        } else if (stripChars.isEmpty()) {
            return str;
        } else {
            while (end != 0 && stripChars.indexOf(str.charAt(end - 1)) != INDEX_NOT_FOUND) {
                end--;
            }
        }
        return str.substring(0, end);
    }

    /**
     * Strips any of a set of characters from the start of a String.
     *
     * <p>A {@code null} input String returns {@code null}.
     * An empty string ("") input returns the empty string.</p>
     *
     * <p>If the stripChars String is {@code null}, whitespace is
     * stripped as defined by {@link Character#isWhitespace(char)}.</p>
     *
     * <pre>
     * StringUtils.stripStart(null, *)          = null
     * StringUtils.stripStart("", *)            = ""
     * StringUtils.stripStart("abc", "")        = "abc"
     * StringUtils.stripStart("abc", null)      = "abc"
     * StringUtils.stripStart("  abc", null)    = "abc"
     * StringUtils.stripStart("abc  ", null)    = "abc  "
     * StringUtils.stripStart(" abc ", null)    = "abc "
     * StringUtils.stripStart("yxabc  ", "xyz") = "abc  "
     * </pre>
     *
     * @param str        the String to remove characters from, may be null
     * @param stripChars the characters to remove, null treated as whitespace
     * @return the stripped String, {@code null} if null String input
     */
    public static String stripStart(String str, String stripChars) {
        int strLen = length(str);
        if (strLen == 0) {
            return str;
        }
        int start = 0;
        if (stripChars == null) {
            while (start != strLen && Character.isWhitespace(str.charAt(start))) {
                start++;
            }
        } else if (stripChars.isEmpty()) {
            return str;
        } else {
            while (start != strLen && stripChars.indexOf(str.charAt(start)) != INDEX_NOT_FOUND) {
                start++;
            }
        }
        return str.substring(start);
    }

    /**
     * Gets the substring after the first occurrence of a separator.
     * The separator is not returned.
     *
     * <p>A {@code null} string input will return {@code null}.
     * An empty ("") string input will return the empty string.
     * A {@code null} separator will return the empty string if the
     * input string is not {@code null}.</p>
     *
     * <p>If nothing is found, the empty string is returned.</p>
     *
     * <pre>
     * StringUtils.substringAfter(null, *)      = null
     * StringUtils.substringAfter("", *)        = ""
     * StringUtils.substringAfter(*, null)      = ""
     * StringUtils.substringAfter("abc", "a")   = "bc"
     * StringUtils.substringAfter("abcba", "b") = "cba"
     * StringUtils.substringAfter("abc", "c")   = ""
     * StringUtils.substringAfter("abc", "d")   = ""
     * StringUtils.substringAfter("abc", "")    = "abc"
     * </pre>
     *
     * @param str       the String to get a substring from, may be null
     * @param separator the String to search for, may be null
     * @return the substring after the first occurrence of the separator,
     * {@code null} if null String input
     */
    public static String substringAfter(String str, String separator) {
        if (!hasText(str) || !hasText(separator)) return str;

        int pos = str.indexOf(separator);
        return (pos == INDEX_NOT_FOUND) ? EMPTY : str.substring(pos + separator.length());
    }

    /**
     * Gets the substring after the last occurrence of a separator.
     * The separator is not returned.
     *
     * <p>A {@code null} string input will return {@code null}.
     * An empty ("") string input will return the empty string.
     * An empty or {@code null} separator will return the empty string if
     * the input string is not {@code null}.</p>
     *
     * <p>If nothing is found, the empty string is returned.</p>
     *
     * <pre>
     * StringUtils.substringAfterLast(null, *)      = null
     * StringUtils.substringAfterLast("", *)        = ""
     * StringUtils.substringAfterLast(*, "")        = ""
     * StringUtils.substringAfterLast(*, null)      = ""
     * StringUtils.substringAfterLast("abc", "a")   = "bc"
     * StringUtils.substringAfterLast("abcba", "b") = "a"
     * StringUtils.substringAfterLast("abc", "c")   = ""
     * StringUtils.substringAfterLast("a", "a")     = ""
     * StringUtils.substringAfterLast("a", "z")     = ""
     * </pre>
     *
     * @param str       the String to get a substring from, may be null
     * @param separator the String to search for, may be null
     * @return the substring after the last occurrence of the separator,
     * {@code null} if null String input
     */
    public static String substringAfterLast(String str, String separator) {
        if (!hasText(str) || !hasText(separator)) return str;

        int pos = str.lastIndexOf(separator);
        return (pos == INDEX_NOT_FOUND || pos == str.length() - separator.length()) ?
                EMPTY :
                str.substring(pos + separator.length());
    }

    /**
     * Gets the substring before the first occurrence of a separator.
     * The separator is not returned.
     *
     * <p>A {@code null} string input will return {@code null}.
     * An empty ("") string input will return the empty string.
     * A {@code null} separator will return the input string.</p>
     *
     * <p>If nothing is found, the string input is returned.</p>
     *
     * <pre>
     * StringUtils.substringBefore(null, *)      = null
     * StringUtils.substringBefore("", *)        = ""
     * StringUtils.substringBefore("abc", "a")   = ""
     * StringUtils.substringBefore("abcba", "b") = "a"
     * StringUtils.substringBefore("abc", "c")   = "ab"
     * StringUtils.substringBefore("abc", "d")   = "abc"
     * StringUtils.substringBefore("abc", "")    = ""
     * StringUtils.substringBefore("abc", null)  = "abc"
     * </pre>
     *
     * @param str       the String to get a substring from, may be null
     * @param separator the String to search for, may be null
     * @return the substring before the first occurrence of the separator,
     * {@code null} if null String input
     */
    public static String substringBefore(String str, String separator) {
        if (!hasText(str) || !hasText(separator)) return str;

        int pos = str.indexOf(separator);
        return (pos == INDEX_NOT_FOUND) ? str : str.substring(0, pos);
    }

    /**
     * Gets the substring before the last occurrence of a separator.
     * The separator is not returned.
     *
     * <p>A {@code null} string input will return {@code null}.
     * An empty ("") string input will return the empty string.
     * An empty or {@code null} separator will return the input string.</p>
     *
     * <p>If nothing is found, the string input is returned.</p>
     *
     * <pre>
     * StringUtils.substringBeforeLast(null, *)      = null
     * StringUtils.substringBeforeLast("", *)        = ""
     * StringUtils.substringBeforeLast("abcba", "b") = "abc"
     * StringUtils.substringBeforeLast("abc", "c")   = "ab"
     * StringUtils.substringBeforeLast("a", "a")     = ""
     * StringUtils.substringBeforeLast("a", "z")     = "a"
     * StringUtils.substringBeforeLast("a", null)    = "a"
     * StringUtils.substringBeforeLast("a", "")      = "a"
     * </pre>
     *
     * @param str       the String to get a substring from, may be null
     * @param separator the String to search for, may be null
     * @return the substring before the last occurrence of the separator,
     * {@code null} if null String input
     */
    public static String substringBeforeLast(String str, String separator) {
        if (!hasText(str) || !hasText(separator)) return str;

        int pos = str.lastIndexOf(separator);
        return (pos == INDEX_NOT_FOUND) ? str : str.substring(0, pos);
    }

    /**
     * Gets the String that is nested in between two instances of the
     * same String.
     *
     * <p>A {@code null} input String returns {@code null}.
     * A {@code null} tag returns {@code null}.</p>
     *
     * <pre>
     * StringUtils.substringBetween(null, *)            = null
     * StringUtils.substringBetween("", "")             = ""
     * StringUtils.substringBetween("", "tag")          = null
     * StringUtils.substringBetween("tagabctag", null)  = null
     * StringUtils.substringBetween("tagabctag", "")    = ""
     * StringUtils.substringBetween("tagabctag", "tag") = "abc"
     * </pre>
     *
     * @param str the String containing the substring, may be null
     * @param tag the String before and after the substring, may be null
     * @return the substring, {@code null} if no match
     */
    public static String substringBetween(String str, String tag) {
        return substringBetween(str, tag, tag);
    }

    /**
     * Gets the String that is nested in between two Strings.
     * Only the first match is returned.
     *
     * <p>A {@code null} input String returns {@code null}.
     * A {@code null} open/close returns {@code null} (no match).
     * An empty ("") open and close returns an empty string.</p>
     *
     * <pre>
     * StringUtils.substringBetween("wx[b]yz", "[", "]") = "b"
     * StringUtils.substringBetween(null, *, *)          = null
     * StringUtils.substringBetween(*, null, *)          = null
     * StringUtils.substringBetween(*, *, null)          = null
     * StringUtils.substringBetween("", "", "")          = ""
     * StringUtils.substringBetween("", "", "]")         = null
     * StringUtils.substringBetween("", "[", "]")        = null
     * StringUtils.substringBetween("yabcz", "", "")     = ""
     * StringUtils.substringBetween("yabcz", "y", "z")   = "abc"
     * StringUtils.substringBetween("yabczyabcz", "y", "z")   = "abc"
     * </pre>
     *
     * @param str   the String containing the substring, may be null
     * @param open  the String before the substring, may be null
     * @param close the String after the substring, may be null
     * @return the substring, {@link #EMPTY} if no match
     */
    public static String substringBetween(String str, String open, String close) {
        if (!hasTextAll(str, open, close)) return EMPTY;
        int start = str.indexOf(open);

        if (start != INDEX_NOT_FOUND) {
            int end = str.indexOf(close, start + open.length());
            if (end != INDEX_NOT_FOUND) return str.substring(start + open.length(), end);
        }

        return EMPTY;
    }

    /**
     * Searches a String for substrings delimited by a start and end tag,
     * returning all matching substrings in an array.
     *
     * <p>A {@code null} input String returns {@code null}.
     * A {@code null} open/close returns {@code null} (no match).
     * An empty ("") open/close returns {@code null} (no match).</p>
     *
     * <pre>
     * StringUtils.substringsBetween("[a][b][c]", "[", "]") = ["a","b","c"]
     * StringUtils.substringsBetween(null, *, *)            = null
     * StringUtils.substringsBetween(*, null, *)            = null
     * StringUtils.substringsBetween(*, *, null)            = null
     * StringUtils.substringsBetween("", "[", "]")          = []
     * </pre>
     *
     * @param str   the String containing the substrings, null returns null, empty returns empty
     * @param open  the String identifying the start of the substring, empty returns null
     * @param close the String identifying the end of the substring, empty returns null
     * @return a String Array of substrings, or {@code null} if no match
     */
    public static String[] substringsBetween(String str, String open, String close) {
        if (!hasTextAll(str, open, close)) return new String[]{};

        int strLength = str.length();
        int closeLength = close.length();
        int openLength = open.length();

        List<String> list = new ArrayList<>();
        int pos = 0;
        while (pos < strLength - closeLength) {
            int start = str.indexOf(open, pos);

            if (start < 0) break;

            start += openLength;
            int end = str.indexOf(close, start);

            if (end < 0) break;

            list.add(str.substring(start, end));
            pos = end + closeLength;
        }

        return (list.isEmpty()) ? new String[]{} : list.toArray(new String[]{});
    }

    /**
     * Converts a {@link CharSequence} into an array of code points.
     *
     * <p>Valid pairs of surrogate code units will be converted into a single supplementary
     * code point. Isolated surrogate code units (i.e. a high surrogate not followed by a low surrogate or
     * a low surrogate not preceded by a high surrogate) will be returned as-is.</p>
     *
     * <pre>
     * StringUtils.toCodePoints(null)   =  null
     * StringUtils.toCodePoints("")     =  []  // empty array
     * </pre>
     *
     * @param string the character sequence to convert
     * @return an array of code points
     */
    public static int[] toCodePoints(String string) {
        return (!hasText(string)) ? new int[]{} : string.codePoints().toArray();
    }

    /**
     * Converts the given source String as a lower-case using the {@link Locale#ROOT} locale in a null-safe manner.
     *
     * @param source A source String or null.
     * @return the given source String as a lower-case using the {@link Locale#ROOT} locale or null.
     */
    public static String toRootLowerCase(String source) {
        return (!hasText(source)) ? source : source.toLowerCase(Locale.ROOT);
    }

    /**
     * Converts the given source String as an upper-case using the {@link Locale#ROOT} locale in a null-safe manner.
     *
     * @param source A source String or null.
     * @return the given source String as an upper-case using the {@link Locale#ROOT} locale or null.
     */
    public static String toRootUpperCase(String source) {
        return (!hasText(source)) ? source : source.toUpperCase(Locale.ROOT);
    }

    /**
     * Removes control characters (char &lt;= 32) from both
     * ends of this String, handling {@code null} by returning
     * {@code null}.
     *
     * <p>The String is trimmed using {@link String#trim()}.
     * Trim removes start and end characters &lt;= 32.
     * To strip whitespace use {@link #strip(String)}.</p>
     *
     * <p>To trim your choice of characters, use the
     * {@link #strip(String, String)} methods.</p>
     *
     * <pre>
     * StringUtils.trim(null)          = null
     * StringUtils.trim("")            = ""
     * StringUtils.trim("     ")       = ""
     * StringUtils.trim("abc")         = "abc"
     * StringUtils.trim("    abc    ") = "abc"
     * </pre>
     *
     * @param str the String to be trimmed, may be null
     * @return the trimmed string, {@code null} if null String input
     */
    public static String trim(String str) {
        return str == null ? null : str.trim();
    }

    /**
     * Uncapitalizes a String, changing the first character to lower case as
     * per {@link Character#toLowerCase(int)}. No other characters are changed.
     *
     * <pre>
     * StringUtils.uncapitalize(null)  = null
     * StringUtils.uncapitalize("")    = ""
     * StringUtils.uncapitalize("cat") = "cat"
     * StringUtils.uncapitalize("Cat") = "cat"
     * StringUtils.uncapitalize("CAT") = "cAT"
     * </pre>
     *
     * @param str the String to uncapitalize, may be null
     * @return the uncapitalized String, {@code null} if null String input
     * @see #capitalize(String)
     */
    public static String uncapitalize(String str) {
        int strLen = length(str);
        if (strLen == 0) {
            return str;
        }
        int firstCodePoint = str.codePointAt(0);
        int newCodePoint = Character.toLowerCase(firstCodePoint);
        if (firstCodePoint == newCodePoint) {
            // already uncapitalized
            return str;
        }
        int[] newCodePoints = str.codePoints().toArray();
        newCodePoints[0] = newCodePoint; // copy the first code point
        return new String(newCodePoints, 0, newCodePoints.length);
    }

    /**
     * Unwraps a given string from another string.
     *
     * <pre>
     * StringUtils.unwrap(null, null)         = null
     * StringUtils.unwrap(null, "")           = null
     * StringUtils.unwrap(null, "1")          = null
     * StringUtils.unwrap("a", "a")           = "a"
     * StringUtils.unwrap("aa", "a")          = ""
     * StringUtils.unwrap("\'abc\'", "\'")    = "abc"
     * StringUtils.unwrap("\"abc\"", "\"")    = "abc"
     * StringUtils.unwrap("AABabcBAA", "AA")  = "BabcB"
     * StringUtils.unwrap("A", "#")           = "A"
     * StringUtils.unwrap("#A", "#")          = "#A"
     * StringUtils.unwrap("A#", "#")          = "A#"
     * </pre>
     *
     * @param str       the String to be unwrapped, can be null
     * @param wrapToken the String used to unwrap
     * @return unwrapped String or the original string
     * if it is not quoted properly with the wrapToken
     */
    public static String unwrap(String str, String wrapToken) {
        return str;
    }

    /**
     * Wraps a String with another String.
     *
     * <p>
     * A {@code null} input String returns {@code null}.
     * </p>
     *
     * <pre>
     * StringUtils.wrap(null, *)         = null
     * StringUtils.wrap("", *)           = ""
     * StringUtils.wrap("ab", null)      = "ab"
     * StringUtils.wrap("ab", "x")       = "xabx"
     * StringUtils.wrap("ab", "\"")      = "\"ab\""
     * StringUtils.wrap("\"ab\"", "\"")  = "\"\"ab\"\""
     * StringUtils.wrap("ab", "'")       = "'ab'"
     * StringUtils.wrap("'abcd'", "'")   = "''abcd''"
     * StringUtils.wrap("\"abcd\"", "'") = "'\"abcd\"'"
     * StringUtils.wrap("'abcd'", "\"")  = "\"'abcd'\""
     * </pre>
     *
     * @param str      the String to be wrapper, may be null
     * @param wrapWith the String that will wrap str
     * @return wrapped String, {@code null} if null String input
     */
    public static String wrap(String str, String wrapWith) {
        return (!hasTextAll(str, wrapWith)) ? str : wrapWith.concat(str).concat(wrapWith);
    }

    /**
     * Wraps a string with a string if that string is missing from the start or end of the given string.
     *
     * <p>A new {@link String} will not be created if {@code str} is already wrapped.</p>
     *
     * <pre>
     * StringUtils.wrapIfMissing(null, *)         = null
     * StringUtils.wrapIfMissing("", *)           = ""
     * StringUtils.wrapIfMissing("ab", null)      = "ab"
     * StringUtils.wrapIfMissing("ab", "x")       = "xabx"
     * StringUtils.wrapIfMissing("ab", "\"")      = "\"ab\""
     * StringUtils.wrapIfMissing("\"ab\"", "\"")  = "\"ab\""
     * StringUtils.wrapIfMissing("ab", "'")       = "'ab'"
     * StringUtils.wrapIfMissing("'abcd'", "'")   = "'abcd'"
     * StringUtils.wrapIfMissing("\"abcd\"", "'") = "'\"abcd\"'"
     * StringUtils.wrapIfMissing("'abcd'", "\"")  = "\"'abcd'\""
     * StringUtils.wrapIfMissing("/", "/")  = "/"
     * StringUtils.wrapIfMissing("a/b/c", "/")  = "/a/b/c/"
     * StringUtils.wrapIfMissing("/a/b/c", "/")  = "/a/b/c/"
     * StringUtils.wrapIfMissing("a/b/c/", "/")  = "/a/b/c/"
     * </pre>
     *
     * @param str      the string to be wrapped, may be {@code null}
     * @param wrapWith the string that will wrap {@code str}
     * @return the wrapped string, or {@code null} if {@code str == null}
     */
    public static String wrapIfMissing(String str, String wrapWith) {
        if ((!hasTextAll(str, wrapWith))) return str;

        boolean wrapStart = !str.startsWith(wrapWith);
        boolean wrapEnd = !str.endsWith(wrapWith);

        if (!wrapStart && !wrapEnd) return str;

        StringBuilder builder = new StringBuilder(str.length() + wrapWith.length() + wrapWith.length());

        if (wrapStart) builder.append(wrapWith);

        builder.append(str);

        if (wrapEnd) builder.append(wrapWith);

        return builder.toString();
    }
}
