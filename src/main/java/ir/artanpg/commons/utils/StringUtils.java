package ir.artanpg.commons.utils;

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
    public static final String SPACE_CHARACTER = " ";

    /**
     * The null String {@code "null"}.
     */
    public static final String NULL = "null";

    private StringUtils() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    /**
     * Check that the given {@code String} is neither {@code null} nor of
     * length 0.
     *
     * <p>Example:
     * <pre>
     * 	StringUtils.hasLength(null);    = false
     * 	StringUtils.hasLength("");      = false
     * 	StringUtils.hasLength(" ");     = true
     * 	StringUtils.hasLength("Hello"); = true
     * </pre>
     *
     * @param text the string to check
     * @return {@code true}, if the string is not {@code null} and has length
     * @see #hasText(String)
     */
    public static boolean hasLength(String text) {
        return text != null && !text.isEmpty();
    }

    /**
     * Check whether the given {@code String} contains actual <em>text</em>.
     *
     * <p><strong>Note: </strong>More specifically, this method returns
     * {@code true} if the string is not {@code null}, its length is greater
     * than 0, and it contains at least one non-whitespace character.
     *
     * <p>Example:
     * <pre>
     * 	StringUtils.hasText(null);      = false
     * 	StringUtils.hasText("");        = false
     * 	StringUtils.hasText(" ");       = false
     * 	StringUtils.hasText("Hello");   = true
     * </pre>
     *
     * @param text the string to check
     * @return {@code true}, if the string is not {@code null}, its length is greater than 0, and it does not
     * contain whitespace only
     * @see #hasLength(String)
     * @see String#isBlank()
     */
    public static boolean hasText(String text) {
        return text != null && !text.isBlank();
    }

    /**
     * Check whether the given {@code String} contains any whitespace characters.
     *
     * <p>Example:
     * <pre>
     * 	StringUtils.containsWhitespace(null);      = false
     * 	StringUtils.containsWhitespace("");        = false
     * 	StringUtils.containsWhitespace("Hello");   = false
     * 	StringUtils.containsWhitespace(" ");       = true
     * 	StringUtils.containsWhitespace("Hello ");  = true
     * </pre>
     *
     * @param string the string to check
     * @return {@code true}, if the string is not empty and contains at least 1 whitespace character
     * @see Character#isWhitespace
     */
    public static boolean containsWhitespace(String string) {
        if (!hasLength(string)) {
            return false;
        }
        int stringLength = string.length();
        for (int i = 0; i < stringLength; i++) {
            if (Character.isWhitespace(string.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Trim <em>all</em> whitespace from the given {@code String}: leading,
     * trailing, and in between characters.
     *
     * <p>Example:
     * <pre>
     * 	StringUtils.trimAllWhitespace(null);    = ""
     * 	StringUtils.trimAllWhitespace(" ");     = ""
     * 	StringUtils.trimAllWhitespace(" Hello "); = "Hello"
     * 	StringUtils.trimAllWhitespace(" Hel lo "); = "Hello"
     * </pre>
     *
     * @param string the string to check
     * @return the trimmed string
     * @see Character#isWhitespace
     */
    public static String trimAllWhitespace(String string) {
        if (!hasLength(string)) {
            return EMPTY;
        }

        string = string.trim();
        int stringLength = string.length();
        StringBuilder stringBuilder = new StringBuilder(stringLength);
        for (int i = 0; i < stringLength; i++) {
            char c = string.charAt(i);
            if (!Character.isWhitespace(c)) {
                stringBuilder.append(c);
            }
        }
        return stringBuilder.toString();
    }

    /**
     * Trim all occurrences of the supplied leading character from the given
     * {@code String}.
     *
     * <p>Example:
     * <pre>
     * 	StringUtils.trimLeadingCharacter(null, '#');       = ""
     * 	StringUtils.trimLeadingCharacter("", '#');         = ""
     * 	StringUtils.trimLeadingCharacter("Hello", 'h');    = "Hello"
     * 	StringUtils.trimLeadingCharacter("000123", '0');   = "123"
     * 	StringUtils.trimLeadingCharacter("   Hello", ' '); = "Hello"
     * </pre>
     *
     * @param string           the string to check
     * @param leadingCharacter the leading character to be trimmed
     * @return the trimmed string
     */
    public static String trimLeadingCharacter(String string, char leadingCharacter) {
        if (!hasLength(string)) {
            return EMPTY;
        }

        int beginIndex = 0;
        while (beginIndex < string.length() && leadingCharacter == string.charAt(beginIndex)) {
            beginIndex++;
        }
        return string.substring(beginIndex);
    }

    /**
     * Trim all occurrences of the supplied trailing character from the given
     * {@code String}.
     *
     * <p>Example:
     * <pre>
     * 	StringUtils.trimTrailingCharacter(null, '#');       = ""
     * 	StringUtils.trimTrailingCharacter("", '#');         = ""
     * 	StringUtils.trimTrailingCharacter("Hello", 'h');      = "Hello"
     * 	StringUtils.trimTrailingCharacter("123000", '0');   = "123"
     * 	StringUtils.trimTrailingCharacter("Hello   ", ' '); = "Hello"
     * </pre>
     *
     * @param string            the string to check
     * @param trailingCharacter the trailing character to be trimmed
     * @return the trimmed string
     */
    public static String trimTrailingCharacter(String string, char trailingCharacter) {
        if (!hasLength(string)) {
            return EMPTY;
        }

        int endIndex = string.length() - 1;
        while (endIndex >= 0 && trailingCharacter == string.charAt(endIndex)) {
            endIndex--;
        }
        return string.substring(0, endIndex + 1);
    }

    /**
     * Checks that the given {@code String} starts with the specified prefix,
     * regardless of case sensitivity.
     *
     * <p>Example:
     * <pre>
     * 	StringUtils.startsWithIgnoreCase(null, "he");    = false
     * 	StringUtils.startsWithIgnoreCase("", "he");      = false
     * 	StringUtils.startsWithIgnoreCase("Hello", null); = false
     * 	StringUtils.startsWithIgnoreCase("Hello", "");   = true
     * 	StringUtils.startsWithIgnoreCase("Hello", "he"); = true
     * </pre>
     *
     * @param string the string to check
     * @param prefix the prefix to look for
     * @return {@code true}, if the string starts with the given prefix
     * @see String#startsWith
     */
    public static boolean startsWithIgnoreCase(String string, String prefix) {
        return (string != null && prefix != null && string.length() >= prefix.length() &&
                string.regionMatches(true, 0, prefix, 0, prefix.length()));
    }

    /**
     * Checks that the given {@code String} starts with the specified prefix,
     * regardless of case sensitivity.
     *
     * <p>Example:
     * <pre>
     * 	StringUtils.endsWithIgnoreCase(null, "Hi");   = false
     * 	StringUtils.endsWithIgnoreCase("Hi", null);   = false
     * 	StringUtils.endsWithIgnoreCase("", "Hi");     = false
     * 	StringUtils.endsWithIgnoreCase("Test", "");   = true
     * 	StringUtils.endsWithIgnoreCase("Test", "st"); = true
     * </pre>
     *
     * @param string the string to check
     * @param suffix the suffix to look for
     * @return {@code true}, if the string ends with the given suffix
     * @see String#endsWith
     */
    public static boolean endsWithIgnoreCase(String string, String suffix) {
        return hasText(string) && hasText(suffix) && string.length() >= suffix.length() &&
                string.regionMatches(true, string.length() - suffix.length(), suffix, 0, suffix.length());
    }

    /**
     * Checks the given {@code String} matches the given substring at the given
     * index.
     *
     * <p>Example:
     * <pre>
     * 	StringUtils.substringMatch(null, "Hi", 0);     = false
     * 	StringUtils.substringMatch("Hi", null, 0);     = false
     * 	StringUtils.substringMatch("Hi", "H", -1);     = false
     * 	StringUtils.substringMatch("Hello", "he", 0);  = false
     * 	StringUtils.substringMatch("Hi", "Hello", 0);  = false
     * 	StringUtils.substringMatch("Hello", "He", 0);  = true
     * 	StringUtils.substringMatch("Hello", "llo", 2); = true
     * </pre>
     *
     * @param string    the original string
     * @param substring the substring to match at the given index
     * @param index     the index in the original string to start matching against
     * @return {@code true}, if the given string matches the given substring at the given index
     * @see String#regionMatches
     */
    public static boolean containsAtPosition(String string, String substring, int index) {
        return hasText(string) && hasText(substring) && index >= 0 &&
                (index + substring.length() < string.length()) &&
                string.regionMatches(false, index, substring, 0, substring.length());
    }

    /**
     * Checks the given {@code String} matches the given substring.
     *
     * <p>Example:
     * <pre>
     * 	StringUtils.contains(null, "Hi");     = false
     * 	StringUtils.contains("", "Hi");       = false
     * 	StringUtils.contains(" ", "Hi");      = false
     * 	StringUtils.contains("Hi", null);     = false
     * 	StringUtils.contains("Hi", "");       = false
     * 	StringUtils.contains("Hi", " ");      = false
     * 	StringUtils.contains("Hello", "He");  = true
     * 	StringUtils.contains("Hello", "llo"); = true
     * </pre>
     *
     * @param string    the original string
     * @param substring the substring to match
     * @return {@code true}, if the given string matches the given substring
     */
    public static boolean contains(String string, String substring) {
        return hasText(string) && hasText(substring) && (substring.length() < string.length())
                && string.contains(substring);
    }

    /**
     * It uses the {@code Knuth-Morris-Pratt} algorithm to check whether a given
     * {@code String} matches a given substring.
     *
     * <p>Example:
     * <pre>
     * 	StringUtils.containsWithKMPAlgorithm(null, "pattern");                = false
     * 	StringUtils.containsWithKMPAlgorithm("", "pattern");                  = false
     * 	StringUtils.containsWithKMPAlgorithm("text", null);                   = false
     * 	StringUtils.containsWithKMPAlgorithm("text", "");                     = false
     * 	StringUtils.containsWithKMPAlgorithm("DABACDABABCABAB", "ABABCABAB"); = true
     * </pre>
     *
     * @param string    the original string
     * @param substring the substring to match
     * @return {@code true}, if the given string matches the given substring
     */
    public static boolean containsWithKMPAlgorithm(String string, String substring) {
        int[] lps = computeLPS(substring);
        int indexString = 0; // index for string
        int indexSubstring = 0; // index for substring

        while (indexString < string.length()) {
            if (substring.charAt(indexSubstring) == string.charAt(indexString)) {
                indexString++;
                indexSubstring++;
            }
            if (indexSubstring == substring.length()) {
                return true; // Pattern found
            } else if (indexString < string.length() && substring.charAt(indexSubstring) != string.charAt(indexString)) {
                if (indexSubstring != 0) {
                    indexSubstring = lps[indexSubstring - 1];
                } else {
                    indexString++;
                }
            }
        }
        return false;
    }

    private static int[] computeLPS(String pattern) {
        int[] lps = new int[pattern.length()];
        int length = 0;
        for (int i = 1; i < pattern.length(); ) {
            if (pattern.charAt(i) == pattern.charAt(length)) {
                length++;
                lps[i] = length;
                i++;
            } else {
                if (length != 0) {
                    length = lps[length - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }
        return lps;
    }

    /**
     * Count the occurrences of the substring in {@code String}.
     *
     * <p>Example:
     * <pre>
     * 	StringUtils.countOccurrencesOf(null, "text");   = 0
     * 	StringUtils.countOccurrencesOf("text", null);   = 0
     * 	StringUtils.countOccurrencesOf("aaaa", "aa");   = 2
     * 	StringUtils.countOccurrencesOf("hello", "ll");  = 1
     * 	StringUtils.countOccurrencesOf("ababab", "ab"); = 3
     * </pre>
     *
     * @param string    the string to search in
     * @param substring the substring to count occurrences of
     * @return The number of non-overlapping occurrences of the {@code substring} in the {@code string}
     */
    public static int countOccurrencesOf(String string, String substring) {
        if (!hasLength(string) || !hasLength(substring)) {
            return 0;
        }

        int count = 0;
        int pos = 0;
        int idx;
        while ((idx = string.indexOf(substring, pos)) != -1) {
            ++count;
            pos = idx + substring.length();
        }
        return count;
    }

    /**
     * Replace all occurrences of a substring within a {@code String} with
     * another {@code String}.
     *
     * <p>Example:
     * <pre>
     * 	StringUtils.replace(null, "World", "Java");          = null
     * 	StringUtils.replace("", "World", "Java");            = ""
     * 	StringUtils.replace("Hello World", null, "Java");    = "Hello World"
     * 	StringUtils.replace("Hello World", "", "Java");      = "Hello World"
     * 	StringUtils.replace("Hello World", "World", null);   = "Hello World"
     * 	StringUtils.replace("Hello World", "World", "");     = "Hello"
     * 	StringUtils.replace("Hello World", "World", "Java"); = "Hello Java"
     * </pre>
     *
     * @param original    the original string to process
     * @param pattern     the string to be replaced
     * @param replacement the replacement string
     * @return a new version of the string with the replacements made, or the {@code original} string if
     * there is no match
     * @see String#replace
     */
    public static String replace(String original, String pattern, String replacement) {
        if (!hasLength(original) || !hasLength(pattern) || Objects.isNull(replacement)) {
            return original;
        }
        return original.replace(pattern, replacement);
    }

    /**
     * Replace all occurrences of a substring in a {@code String} with a
     * regular expression.
     *
     * <p>Example:
     * <pre>
     * 	StringUtils.replaceRegex(null, "\\d+", "*");      = null
     * 	StringUtils.replaceRegex("", "\\d+", "*");        = ""
     * 	StringUtils.replaceRegex("123abc", null, "*");    = "123abc"
     * 	StringUtils.replaceRegex("123abc", "", "*");      = "123abc"
     * 	StringUtils.replaceRegex("123abc", "\\d+", null); = "123abc"
     * 	StringUtils.replaceRegex("123abc", "\\d+", "");   = "abc"
     * 	StringUtils.replaceRegex("123abc", "\\d+", "*");  = "*abc"
     * 	StringUtils.replaceRegex("123abc", "abc", "*");   = "123*"
     * </pre>
     *
     * @param original   the original string to process
     * @param regex      the pattern to be replaced
     * @param newPattern the replacement string
     * @return a new version of the string with the replacements made, or the {@code original} string if
     * there is no match
     * @see String#replaceAll
     */
    public static String replaceRegex(String original, String regex, String newPattern) {
        if (!hasLength(original) || !hasLength(regex) || Objects.isNull(newPattern)) {
            return original;
        }
        return original.replaceAll(regex, newPattern);
    }

    /**
     * Normalization of {@code Arabic} to {@code Persian} letters to prevent
     * visual differences in text matching.
     *
     * <p>This method converts certain letters that are similar in
     * {@code Arabic} and {@code Persian} languages but have different Unicode
     *
     * <p>Example:
     * <pre>
     * 	StringUtils.normalizeArabicPersian(null)  = null
     * 	StringUtils.normalizeArabicPersian("")    = ""
     * 	StringUtils.normalizeArabicPersian("کمك")  = "کمک"
     * 	StringUtils.normalizeArabicPersian("محمدي") = "محمدی"
     *
     * @param string string the string to be normalized
     * @return the string that is normalized, or {@code null} if the string is {@code null}
     */
    public static String normalizeArabic(String string) {
        if (!hasLength(string)) {
            return string;
        }
        return string.replace('ي', 'ی').replace('ك', 'ک').replace('ۀ', 'ه').replace('ة', 'ه');
    }

    /**
     * Quote the given {@code String} with single quotes.
     *
     * <p>Example:
     * <pre>
     * 	StringUtils.quote(null);          = null
     * 	StringUtils.quote("");            = null
     * 	StringUtils.quote(" ");           = null
     * 	StringUtils.quote("Hello World"); = "'Hello World'"
     * </pre>
     *
     * @param string the input {@code String}
     * @return the quoted {@code String}, or {@code null} if the input was {@code null}
     * @see #hasText
     */
    public static String quote(String string) {
        return (hasText(string) ? "'" + string + "'" : null);
    }

    /**
     * If the given object is a {@code String}, it converts it to a string
     * using single quotes, otherwise, it keeps the object in its original
     * form.
     *
     * <p>Example:
     * <pre>
     * 	StringUtils.quoteIfString(null);          = null
     * 	StringUtils.quoteIfString("");            = null
     * 	StringUtils.quoteIfString(" ");           = null
     * 	StringUtils.quoteIfString(123);           = 123
     * 	StringUtils.quoteIfString("Hello World"); = "'Hello World'"
     * </pre>
     *
     * @param object the input {@code Object}
     * @return the quoted {@code String}, or the input object as-is if not a {@code String}
     * @see #quote
     */
    public static Object quoteIfString(Object object) {
        return (object instanceof String s ? quote(s) : object);
    }

    /**
     * Abbreviates a String using a given replacement marker. This will turn
     * "Now is the time for all good men" into "...is the time for..." if "..." was defined
     * as the replacement marker.
     *
     * <p>Works like {@code abbreviate(String, String, int)}, but allows you to specify
     * a "left edge" offset.  Note that this left edge is not necessarily going to
     * be the leftmost character in the result, or the first character following the
     * replacement marker, but it will appear somewhere in the result.
     *
     * <p>In no case will it return a String of length greater than {@code maxWidth}.</p>
     *
     * <pre>
     * StringUtils.abbreviate(null, null, *, *)                 = null
     * StringUtils.abbreviate("abcdefghijklmno", null, *, *)    = "abcdefghijklmno"
     * StringUtils.abbreviate("", "...", 0, 4)                  = ""
     * StringUtils.abbreviate("abcdefghijklmno", "---", -1, 10) = "abcdefg---"
     * StringUtils.abbreviate("abcdefghijklmno", ",", 0, 10)    = "abcdefghi,"
     * StringUtils.abbreviate("abcdefghijklmno", ",", 1, 10)    = "abcdefghi,"
     * StringUtils.abbreviate("abcdefghijklmno", ",", 2, 10)    = "abcdefghi,"
     * StringUtils.abbreviate("abcdefghijklmno", "::", 4, 10)   = "::efghij::"
     * StringUtils.abbreviate("abcdefghijklmno", "...", 6, 10)  = "...ghij..."
     * StringUtils.abbreviate("abcdefghijklmno", "*", 9, 10)    = "*ghijklmno"
     * StringUtils.abbreviate("abcdefghijklmno", "'", 10, 10)   = "'ghijklmno"
     * StringUtils.abbreviate("abcdefghijklmno", "!", 12, 10)   = "!ghijklmno"
     * StringUtils.abbreviate("abcdefghij", "abra", 0, 4)       = IllegalArgumentException
     * StringUtils.abbreviate("abcdefghij", "...", 5, 6)        = IllegalArgumentException
     * </pre>
     *
     * @param str  the String to check, may be null
     * @param abbrevMarker  the String used as replacement marker
     * @param offset  left edge of source String
     * @param maxWidth  maximum length of result String, must be at least 4
     * @return abbreviated String, {@code null} if null String input
     * @throws IllegalArgumentException if the width is too small
     * @since 3.6
     */
    public static String abbreviate(final String str, final String abbrevMarker, int offset, final int maxWidth) {
//		if (hasLength(str) && EMPTY.equals(abbrevMarker) && maxWidth > 0) {
//			return substring(str, 0, maxWidth);
//		}
//		if (isAnyEmpty(str, abbrevMarker)) {
//			return str;
//		}
        final int abbrevMarkerLength = abbrevMarker.length();
        final int minAbbrevWidth = abbrevMarkerLength + 1;
        final int minAbbrevWidthOffset = abbrevMarkerLength + abbrevMarkerLength + 1;

        if (maxWidth < minAbbrevWidth) {
            throw new IllegalArgumentException(String.format("Minimum abbreviation width is %d", minAbbrevWidth));
        }
        final int strLen = str.length();
        if (strLen <= maxWidth) {
            return str;
        }
        if (offset > strLen) {
            offset = strLen;
        }
        if (strLen - offset < maxWidth - abbrevMarkerLength) {
            offset = strLen - (maxWidth - abbrevMarkerLength);
        }
        if (offset <= abbrevMarkerLength + 1) {
            return str.substring(0, maxWidth - abbrevMarkerLength) + abbrevMarker;
        }
        if (maxWidth < minAbbrevWidthOffset) {
            throw new IllegalArgumentException(String.format("Minimum abbreviation width with offset is %d", minAbbrevWidthOffset));
        }
        if (offset + maxWidth - abbrevMarkerLength < strLen) {
//			return abbrevMarker + abbreviate(str.substring(offset), abbrevMarker, maxWidth - abbrevMarkerLength);
        }
        return abbrevMarker + str.substring(strLen - (maxWidth - abbrevMarkerLength));
    }
}
