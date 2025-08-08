package ir.artanpg.commons.utils;

import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.regex.Pattern;

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
    private static final String ELLIPSIS3 = "...";

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
        if (hasText(string) && EMPTY.equals(abbrevMarker) && maxWidth > 0) return substring(string, 0, maxWidth);
        if (isAnyEmpty(string, abbrevMarker)) return string;

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
     *  StringUtils.contains("Hello World", "Hello"); = true
     *  StringUtils.contains("Hello World", "hello"); = false
     * </pre>
     *
     * @param string       the string to check
     * @param searchString the string to find
     * @return {@code true}, if the String contains the search string, {@code false} if not or {@code null} string input
     */
    public static boolean contains(String string, String searchString) {
        if (!hasLength(string)) return false;

        return string.contains(searchString);
    }

    /**
     * Tests if the CharSequence contains any character in the given
     * set of characters.
     *
     * <p>A {@code null} CharSequence will return {@code false}.
     * A {@code null} or zero length search array will return {@code false}.</p>
     *
     * <pre>
     * StringUtils.containsAny(null, *)                  = false
     * StringUtils.containsAny("", *)                    = false
     * StringUtils.containsAny(*, null)                  = false
     * StringUtils.containsAny(*, [])                    = false
     * StringUtils.containsAny("zzabyycdxx", ['z', 'a']) = true
     * StringUtils.containsAny("zzabyycdxx", ['b', 'y']) = true
     * StringUtils.containsAny("zzabyycdxx", ['z', 'y']) = true
     * StringUtils.containsAny("aba", ['z'])             = false
     * </pre>
     *
     * @param cs          the CharSequence to check, may be null
     * @param searchChars the chars to search for, may be null
     * @return the {@code true} if any of the chars are found,
     * {@code false} if no match or null input
     */
//    public static boolean containsAny(String cs, char... searchChars) {
//        if (isEmpty(cs) || ArrayUtils.isEmpty(searchChars)) {
//            return false;
//        }
//        int csLength = cs.length();
//        int searchLength = searchChars.length;
//        int csLast = csLength - 1;
//        int searchLast = searchLength - 1;
//        for (int i = 0; i < csLength; i++) {
//            char ch = cs.charAt(i);
//            for (int j = 0; j < searchLength; j++) {
//                if (searchChars[j] == ch) {
//                    if (!Character.isHighSurrogate(ch) || j == searchLast || i < csLast && searchChars[j + 1] == cs.charAt(i + 1)) {
//                        return true;
//                    }
//                }
//            }
//        }
//        return false;
//    }

    /**
     * Tests if the CharSequence contains any character in the given set of characters.
     *
     * <p>
     * A {@code null} CharSequence will return {@code false}. A {@code null} search CharSequence will return
     * {@code false}.
     * </p>
     *
     * <pre>
     * StringUtils.containsAny(null, *)               = false
     * StringUtils.containsAny("", *)                 = false
     * StringUtils.containsAny(*, null)               = false
     * StringUtils.containsAny(*, "")                 = false
     * StringUtils.containsAny("zzabyycdxx", "za")    = true
     * StringUtils.containsAny("zzabyycdxx", "by")    = true
     * StringUtils.containsAny("zzabyycdxx", "zy")    = true
     * StringUtils.containsAny("zzabyycdxx", "\tx")   = true
     * StringUtils.containsAny("zzabyycdxx", "$.#yF") = true
     * StringUtils.containsAny("aba", "z")            = false
     * </pre>
     *
     * @param cs          the CharSequence to check, may be null
     * @param searchChars the chars to search for, may be null
     * @return the {@code true} if any of the chars are found, {@code false} if no match or null input
     */
//    public static boolean containsAny(CharSequence cs, CharSequence searchChars) {
//        if (searchChars == null) {
//            return false;
//        }
//        return containsAny(cs, CharSequenceUtils.toCharArray(searchChars));
//    }

    /**
     * Tests that the CharSequence does not contain certain characters.
     *
     * <p>A {@code null} CharSequence will return {@code true}.
     * A {@code null} invalid character array will return {@code true}.
     * An empty CharSequence (length()=0) always returns true.</p>
     *
     * <pre>
     * StringUtils.containsNone(null, *)       = true
     * StringUtils.containsNone(*, null)       = true
     * StringUtils.containsNone("", *)         = true
     * StringUtils.containsNone("ab", '')      = true
     * StringUtils.containsNone("abab", 'xyz') = true
     * StringUtils.containsNone("ab1", 'xyz')  = true
     * StringUtils.containsNone("abz", 'xyz')  = false
     * </pre>
     *
     * @param cs          the CharSequence to check, may be null
     * @param searchChars an array of invalid chars, may be null
     * @return true if it contains none of the invalid chars, or is null
     */
//    public static boolean containsNone(CharSequence cs, char... searchChars) {
//        if (cs == null || searchChars == null) {
//            return true;
//        }
//        int csLen = cs.length();
//        int csLast = csLen - 1;
//        int searchLen = searchChars.length;
//        int searchLast = searchLen - 1;
//        for (int i = 0; i < csLen; i++) {
//            char ch = cs.charAt(i);
//            for (int j = 0; j < searchLen; j++) {
//                if (searchChars[j] == ch) {
//                    if (!Character.isHighSurrogate(ch) || j == searchLast || i < csLast && searchChars[j + 1] == cs.charAt(i + 1)) {
//                        return false;
//                    }
//                }
//            }
//        }
//        return true;
//    }

    /**
     * Tests that the CharSequence does not contain certain characters.
     *
     * <p>A {@code null} CharSequence will return {@code true}.
     * A {@code null} invalid character array will return {@code true}.
     * An empty String ("") always returns true.</p>
     *
     * <pre>
     * StringUtils.containsNone(null, *)       = true
     * StringUtils.containsNone(*, null)       = true
     * StringUtils.containsNone("", *)         = true
     * StringUtils.containsNone("ab", "")      = true
     * StringUtils.containsNone("abab", "xyz") = true
     * StringUtils.containsNone("ab1", "xyz")  = true
     * StringUtils.containsNone("abz", "xyz")  = false
     * </pre>
     *
     * @param cs           the CharSequence to check, may be null
     * @param invalidChars a String of invalid chars, may be null
     * @return true if it contains none of the invalid chars, or is null
     */
//    public static boolean containsNone(CharSequence cs, String invalidChars) {
//        if (invalidChars == null) {
//            return true;
//        }
//        return containsNone(cs, invalidChars.toCharArray());
//    }

    /**
     * Tests whether the given CharSequence contains any whitespace characters.
     *
     * <p>Whitespace is defined by {@link Character#isWhitespace(char)}.</p>
     *
     * <pre>
     * StringUtils.containsWhitespace(null)       = false
     * StringUtils.containsWhitespace("")         = false
     * StringUtils.containsWhitespace("ab")       = false
     * StringUtils.containsWhitespace(" ab")      = true
     * StringUtils.containsWhitespace("a b")      = true
     * StringUtils.containsWhitespace("ab ")      = true
     * </pre>
     *
     * @param seq the CharSequence to check (may be {@code null})
     * @return {@code true} if the CharSequence is not empty and
     * contains at least 1 (breaking) whitespace character
     */
    // From org.springframework.util.StringUtils, under Apache License 2.0
    public static boolean containsWhitespace(CharSequence seq) {
        if (isEmpty(seq)) {
            return false;
        }
        int strLen = seq.length();
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(seq.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    private static void convertRemainingAccentCharacters(StringBuilder decomposed) {
        for (int i = 0; i < decomposed.length(); i++) {
            char charAt = decomposed.charAt(i);
            switch (charAt) {
                case '\u0141':
                    decomposed.setCharAt(i, 'L');
                    break;
                case '\u0142':
                    decomposed.setCharAt(i, 'l');
                    break;
                // D with stroke
                case '\u0110':
                    // LATIN CAPITAL LETTER D WITH STROKE
                    decomposed.setCharAt(i, 'D');
                    break;
                case '\u0111':
                    // LATIN SMALL LETTER D WITH STROKE
                    decomposed.setCharAt(i, 'd');
                    break;
                // I with bar
                case '\u0197':
                    decomposed.setCharAt(i, 'I');
                    break;
                case '\u0268':
                    decomposed.setCharAt(i, 'i');
                    break;
                case '\u1D7B':
                    decomposed.setCharAt(i, 'I');
                    break;
                case '\u1DA4':
                    decomposed.setCharAt(i, 'i');
                    break;
                case '\u1DA7':
                    decomposed.setCharAt(i, 'I');
                    break;
                // U with bar
                case '\u0244':
                    // LATIN CAPITAL LETTER U BAR
                    decomposed.setCharAt(i, 'U');
                    break;
                case '\u0289':
                    // LATIN SMALL LETTER U BAR
                    decomposed.setCharAt(i, 'u');
                    break;
                case '\u1D7E':
                    // LATIN SMALL CAPITAL LETTER U WITH STROKE
                    decomposed.setCharAt(i, 'U');
                    break;
                case '\u1DB6':
                    // MODIFIER LETTER SMALL U BAR
                    decomposed.setCharAt(i, 'u');
                    break;
                // T with stroke
                case '\u0166':
                    // LATIN CAPITAL LETTER T WITH STROKE
                    decomposed.setCharAt(i, 'T');
                    break;
                case '\u0167':
                    // LATIN SMALL LETTER T WITH STROKE
                    decomposed.setCharAt(i, 't');
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Counts how many times the substring appears in the larger string.
     * Note that the code only counts non-overlapping matches.
     *
     * <p>A {@code null} or empty ("") String input returns {@code 0}.</p>
     *
     * <pre>
     * StringUtils.countMatches(null, *)        = 0
     * StringUtils.countMatches("", *)          = 0
     * StringUtils.countMatches("abba", null)   = 0
     * StringUtils.countMatches("abba", "")     = 0
     * StringUtils.countMatches("abba", "a")    = 2
     * StringUtils.countMatches("abba", "ab")   = 1
     * StringUtils.countMatches("abba", "xxx")  = 0
     * StringUtils.countMatches("ababa", "aba") = 1
     * </pre>
     *
     * @param str the CharSequence to check, may be null
     * @param sub the substring to count, may be null
     * @return the number of occurrences, 0 if either CharSequence is {@code null}
     */
//    public static int countMatches(CharSequence str, CharSequence sub) {
//        if (isEmpty(str) || isEmpty(sub)) {
//            return 0;
//        }
//        int count = 0;
//        int idx = 0;
//        while ((idx = CharSequenceUtils.indexOf(str, sub, idx)) != INDEX_NOT_FOUND) {
//            count++;
//            idx += sub.length();
//        }
//        return count;
//    }

    /**
     * Returns either the passed in CharSequence, or if the CharSequence is {@link #isBlank(CharSequence) blank} (whitespaces, empty ({@code ""}) or
     * {@code null}), the value of {@code defaultStr}.
     *
     * <p>
     * Whitespace is defined by {@link Character#isWhitespace(char)}.
     * </p>
     *
     * <pre>
     * StringUtils.defaultIfBlank(null, "NULL")  = "NULL"
     * StringUtils.defaultIfBlank("", "NULL")    = "NULL"
     * StringUtils.defaultIfBlank(" ", "NULL")   = "NULL"
     * StringUtils.defaultIfBlank("bat", "NULL") = "bat"
     * StringUtils.defaultIfBlank("", null)      = null
     * </pre>
     *
     * @param <T>        the specific kind of CharSequence
     * @param str        the CharSequence to check, may be null
     * @param defaultStr the default CharSequence to return if {@code str} is {@link #isBlank(CharSequence) blank} (whitespaces, empty ({@code""}) or
     *                   {@code null}); may be null
     * @return the passed in CharSequence, or the default
     * @see org.apache.commons.lang3.StringUtils#defaultString(String, String)
     * @see #isBlank(CharSequence)
     */
    public static <T extends CharSequence> T defaultIfBlank(T str, T defaultStr) {
        return isBlank(str) ? defaultStr : str;
    }

    /**
     * Returns either the passed in CharSequence, or if the CharSequence is
     * empty or {@code null}, the value of {@code defaultStr}.
     *
     * <pre>
     * StringUtils.defaultIfEmpty(null, "NULL")  = "NULL"
     * StringUtils.defaultIfEmpty("", "NULL")    = "NULL"
     * StringUtils.defaultIfEmpty(" ", "NULL")   = " "
     * StringUtils.defaultIfEmpty("bat", "NULL") = "bat"
     * StringUtils.defaultIfEmpty("", null)      = null
     * </pre>
     *
     * @param <T>        the specific kind of CharSequence
     * @param str        the CharSequence to check, may be null
     * @param defaultStr the default CharSequence to return
     *                   if the input is empty ("") or {@code null}, may be null
     * @return the passed in CharSequence, or the default
     * @see org.apache.commons.lang3.StringUtils#defaultString(String, String)
     */
    public static <T extends CharSequence> T defaultIfEmpty(T str, T defaultStr) {
        return isEmpty(str) ? defaultStr : str;
    }

    /**
     * Returns either the passed in String,
     * or if the String is {@code null}, an empty String ("").
     *
     * <pre>
     * StringUtils.defaultString(null)  = ""
     * StringUtils.defaultString("")    = ""
     * StringUtils.defaultString("bat") = "bat"
     * </pre>
     *
     * @param str the String to check, may be null
     * @return the passed in String, or the empty String if it
     * was {@code null}
     * @see Objects#toString(Object, String)
     * @see String#valueOf(Object)
     */
    public static String defaultString(String str) {
        return Objects.toString(str, EMPTY);
    }

    /**
     * Deletes all whitespaces from a String as defined by
     * {@link Character#isWhitespace(char)}.
     *
     * <pre>
     * StringUtils.deleteWhitespace(null)         = null
     * StringUtils.deleteWhitespace("")           = ""
     * StringUtils.deleteWhitespace("abc")        = "abc"
     * StringUtils.deleteWhitespace("   ab  c  ") = "abc"
     * </pre>
     *
     * @param str the String to delete whitespace from, may be null
     * @return the String without whitespaces, {@code null} if null String input
     */
    public static String deleteWhitespace(String str) {
        if (isEmpty(str)) {
            return str;
        }
        int sz = str.length();
        char[] chs = new char[sz];
        int count = 0;
        for (int i = 0; i < sz; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                chs[count++] = str.charAt(i);
            }
        }
        if (count == sz) {
            return str;
        }
        if (count == 0) {
            return EMPTY;
        }
        return new String(chs, 0, count);
    }

    /**
     * Compares two Strings, and returns the portion where they differ.
     * More precisely, return the remainder of the second String,
     * starting from where it's different from the first. This means that
     * the difference between "abc" and "ab" is the empty String and not "c".
     *
     * <p>For example,
     * {@code difference("i am a machine", "i am a robot") -> "robot"}.</p>
     *
     * <pre>
     * StringUtils.difference(null, null)       = null
     * StringUtils.difference("", "")           = ""
     * StringUtils.difference("", "abc")        = "abc"
     * StringUtils.difference("abc", "")        = ""
     * StringUtils.difference("abc", "abc")     = ""
     * StringUtils.difference("abc", "ab")      = ""
     * StringUtils.difference("ab", "abxyz")    = "xyz"
     * StringUtils.difference("abcde", "abxyz") = "xyz"
     * StringUtils.difference("abcde", "xyz")   = "xyz"
     * </pre>
     *
     * @param str1 the first String, may be null
     * @param str2 the second String, may be null
     * @return the portion of str2 where it differs from str1; returns the
     * empty String if they are equal
     * @see #indexOfDifference(CharSequence, CharSequence)
     */
    public static String difference(String str1, String str2) {
        if (str1 == null) {
            return str2;
        }
        if (str2 == null) {
            return str1;
        }
        int at = indexOfDifference(str1, str2);
        if (at == INDEX_NOT_FOUND) {
            return EMPTY;
        }
        return str2.substring(at);
    }

    /**
     * Returns the first value in the array which is not empty (""),
     * {@code null} or whitespace only.
     *
     * <p>Whitespace is defined by {@link Character#isWhitespace(char)}.</p>
     *
     * <p>If all values are blank or the array is {@code null}
     * or empty then {@code null} is returned.</p>
     *
     * <pre>
     * StringUtils.firstNonBlank(null, null, null)     = null
     * StringUtils.firstNonBlank(null, "", " ")        = null
     * StringUtils.firstNonBlank("abc")                = "abc"
     * StringUtils.firstNonBlank(null, "xyz")          = "xyz"
     * StringUtils.firstNonBlank(null, "", " ", "xyz") = "xyz"
     * StringUtils.firstNonBlank(null, "xyz", "abc")   = "xyz"
     * StringUtils.firstNonBlank()                     = null
     * </pre>
     *
     * @param <T>    the specific kind of CharSequence
     * @param values the values to test, may be {@code null} or empty
     * @return the first value from {@code values} which is not blank,
     * or {@code null} if there are no non-blank values
     */
    @SafeVarargs
    public static <T extends CharSequence> T firstNonBlank(T... values) {
        if (values != null) {
            for (T val : values) {
                if (isNotBlank(val)) {
                    return val;
                }
            }
        }
        return null;
    }

    /**
     * Returns the first value in the array which is not empty.
     *
     * <p>If all values are empty or the array is {@code null}
     * or empty then {@code null} is returned.</p>
     *
     * <pre>
     * StringUtils.firstNonEmpty(null, null, null)   = null
     * StringUtils.firstNonEmpty(null, null, "")     = null
     * StringUtils.firstNonEmpty(null, "", " ")      = " "
     * StringUtils.firstNonEmpty("abc")              = "abc"
     * StringUtils.firstNonEmpty(null, "xyz")        = "xyz"
     * StringUtils.firstNonEmpty("", "xyz")          = "xyz"
     * StringUtils.firstNonEmpty(null, "xyz", "abc") = "xyz"
     * StringUtils.firstNonEmpty()                   = null
     * </pre>
     *
     * @param <T>    the specific kind of CharSequence
     * @param values the values to test, may be {@code null} or empty
     * @return the first value from {@code values} which is not empty,
     * or {@code null} if there are no non-empty values
     */
//    @SafeVarargs
//    public static <T extends CharSequence> T firstNonEmpty(T... values) {
//        if (values != null) {
//            for (T val : values) {
//                if (hasLength(val)) {
//                    return val;
//                }
//            }
//        }
//        return null;
//    }

    /**
     * Calls {@link String#getBytes(Charset)} in a null-safe manner.
     *
     * @param string  input string
     * @param charset The {@link Charset} to encode the {@link String}. If null, then use the default Charset.
     * @return The empty byte[] if {@code string} is null, the result of {@link String#getBytes(Charset)} otherwise.
     * @see String#getBytes(Charset)
     */
    public static byte[] getBytes(String string, Charset charset) {
        return string == null ? new byte[]{} : string.getBytes(toCharset(charset));
    }

    /**
     * Returns the given {@code charset} or the default Charset if {@code charset} is null.
     *
     * @param charset a Charset or null.
     * @return the given {@code charset} or the default Charset if {@code charset} is null.
     */
    static Charset toCharset(Charset charset) {
        return charset == null ? Charset.defaultCharset() : charset;
    }

    /**
     * Compares all Strings in an array and returns the initial sequence of
     * characters that is common to all of them.
     *
     * <p>For example,
     * {@code getCommonPrefix(new String[] {"i am a machine", "i am a robot"}) -&gt; "i am a "}</p>
     *
     * <pre>
     * StringUtils.getCommonPrefix(null)                             = ""
     * StringUtils.getCommonPrefix(new String[] {})                  = ""
     * StringUtils.getCommonPrefix(new String[] {"abc"})             = "abc"
     * StringUtils.getCommonPrefix(new String[] {null, null})        = ""
     * StringUtils.getCommonPrefix(new String[] {"", ""})            = ""
     * StringUtils.getCommonPrefix(new String[] {"", null})          = ""
     * StringUtils.getCommonPrefix(new String[] {"abc", null, null}) = ""
     * StringUtils.getCommonPrefix(new String[] {null, null, "abc"}) = ""
     * StringUtils.getCommonPrefix(new String[] {"", "abc"})         = ""
     * StringUtils.getCommonPrefix(new String[] {"abc", ""})         = ""
     * StringUtils.getCommonPrefix(new String[] {"abc", "abc"})      = "abc"
     * StringUtils.getCommonPrefix(new String[] {"abc", "a"})        = "a"
     * StringUtils.getCommonPrefix(new String[] {"ab", "abxyz"})     = "ab"
     * StringUtils.getCommonPrefix(new String[] {"abcde", "abxyz"})  = "ab"
     * StringUtils.getCommonPrefix(new String[] {"abcde", "xyz"})    = ""
     * StringUtils.getCommonPrefix(new String[] {"xyz", "abcde"})    = ""
     * StringUtils.getCommonPrefix(new String[] {"i am a machine", "i am a robot"}) = "i am a "
     * </pre>
     *
     * @param strs array of String objects, entries may be null
     * @return the initial sequence of characters that are common to all Strings
     * in the array; empty String if the array is null, the elements are all null
     * or if there is no common prefix.
     */
    public static String getCommonPrefix(String... strs) {
        if (strs == null || strs.length == 0) {
            return EMPTY;
        }
        int smallestIndexOfDiff = indexOfDifference(strs);
        if (smallestIndexOfDiff == INDEX_NOT_FOUND) {
            // all strings were identical
            if (strs[0] == null) {
                return EMPTY;
            }
            return strs[0];
        }
        if (smallestIndexOfDiff == 0) {
            // there were no common initial characters
            return EMPTY;
        }
        // we found a common initial character sequence
        return strs[0].substring(0, smallestIndexOfDiff);
    }

    /**
     * Checks if a String {@code str} contains Unicode digits,
     * if yes then concatenate all the digits in {@code str} and return it as a String.
     *
     * <p>An empty ("") String will be returned if no digits found in {@code str}.</p>
     *
     * <pre>
     * StringUtils.getDigits(null)                 = null
     * StringUtils.getDigits("")                   = ""
     * StringUtils.getDigits("abc")                = ""
     * StringUtils.getDigits("1000$")              = "1000"
     * StringUtils.getDigits("1123~45")            = "112345"
     * StringUtils.getDigits("(541) 754-3010")     = "5417543010"
     * StringUtils.getDigits("\u0967\u0968\u0969") = "\u0967\u0968\u0969"
     * </pre>
     *
     * @param str the String to extract digits from, may be null
     * @return String with only digits,
     * or an empty ("") String if no digits found,
     * or {@code null} String if {@code str} is null
     */
    public static String getDigits(String str) {
        if (isEmpty(str)) {
            return str;
        }
        int sz = str.length();
        StringBuilder strDigits = new StringBuilder(sz);
        for (int i = 0; i < sz; i++) {
            char tempChar = str.charAt(i);
            if (Character.isDigit(tempChar)) {
                strDigits.append(tempChar);
            }
        }
        return strDigits.toString();
    }

    /**
     * Returns either the passed in CharSequence, or if the CharSequence is {@link #isBlank(CharSequence) blank} (whitespaces, empty ({@code ""}) or
     * {@code null}), the value supplied by {@code defaultStrSupplier}.
     *
     * <p>
     * Whitespace is defined by {@link Character#isWhitespace(char)}.
     * </p>
     *
     * <p>
     * Caller responsible for thread-safety and exception handling of default value supplier
     * </p>
     *
     * <pre>
     * {@code
     * StringUtils.getIfBlank(null, () -> "NULL")   = "NULL"
     * StringUtils.getIfBlank("", () -> "NULL")     = "NULL"
     * StringUtils.getIfBlank(" ", () -> "NULL")    = "NULL"
     * StringUtils.getIfBlank("bat", () -> "NULL")  = "bat"
     * StringUtils.getIfBlank("", () -> null)       = null
     * StringUtils.getIfBlank("", null)             = null
     * }</pre>
     *
     * @param <T>             the specific kind of CharSequence
     * @param str             the CharSequence to check, may be null
     * @param defaultSupplier the supplier of default CharSequence to return if the input is {@link #isBlank(CharSequence) blank} (whitespaces, empty
     *                        ({@code ""}) or {@code null}); may be null
     * @return the passed in CharSequence, or the default
     * @see org.apache.commons.lang3.StringUtils#defaultString(String, String)
     * @see #isBlank(CharSequence)
     */
    public static <T extends CharSequence> T getIfBlank(T str, Supplier<T> defaultSupplier) {
        return isBlank(str) ? defaultSupplier.get() : str;
    }

    /**
     * Returns either the passed in CharSequence, or if the CharSequence is
     * empty or {@code null}, the value supplied by {@code defaultStrSupplier}.
     *
     * <p>Caller responsible for thread-safety and exception handling of default value supplier</p>
     *
     * <pre>
     * {@code
     * StringUtils.getIfEmpty(null, () -> "NULL")    = "NULL"
     * StringUtils.getIfEmpty("", () -> "NULL")      = "NULL"
     * StringUtils.getIfEmpty(" ", () -> "NULL")     = " "
     * StringUtils.getIfEmpty("bat", () -> "NULL")   = "bat"
     * StringUtils.getIfEmpty("", () -> null)        = null
     * StringUtils.getIfEmpty("", null)              = null
     * }
     * </pre>
     *
     * @param <T>             the specific kind of CharSequence
     * @param str             the CharSequence to check, may be null
     * @param defaultSupplier the supplier of default CharSequence to return
     *                        if the input is empty ("") or {@code null}, may be null
     * @return the passed in CharSequence, or the default
     * @see org.apache.commons.lang3.StringUtils#defaultString(String, String)
     */
    public static <T extends CharSequence> T getIfEmpty(T str, Supplier<T> defaultSupplier) {
        return isEmpty(str) ? defaultSupplier.get() : str;
    }

    /**
     * Find the first index of any of a set of potential substrings.
     *
     * <p>A {@code null} CharSequence will return {@code -1}.
     * A {@code null} or zero length search array will return {@code -1}.
     * A {@code null} search array entry will be ignored, but a search
     * array containing "" will return {@code 0} if {@code str} is not
     * null. This method uses {@link String#indexOf(String)} if possible.</p>
     *
     * <pre>
     * StringUtils.indexOfAny(null, *)                      = -1
     * StringUtils.indexOfAny(*, null)                      = -1
     * StringUtils.indexOfAny(*, [])                        = -1
     * StringUtils.indexOfAny("zzabyycdxx", ["ab", "cd"])   = 2
     * StringUtils.indexOfAny("zzabyycdxx", ["cd", "ab"])   = 2
     * StringUtils.indexOfAny("zzabyycdxx", ["mn", "op"])   = -1
     * StringUtils.indexOfAny("zzabyycdxx", ["zab", "aby"]) = 1
     * StringUtils.indexOfAny("zzabyycdxx", [""])           = 0
     * StringUtils.indexOfAny("", [""])                     = 0
     * StringUtils.indexOfAny("", ["a"])                    = -1
     * </pre>
     *
     * @param str        the CharSequence to check, may be null
     * @param searchStrs the CharSequences to search for, may be null
     * @return the first index of any of the searchStrs in str, -1 if no match
     */
//    public static int indexOfAny(CharSequence str, CharSequence... searchStrs) {
//        if (str == null || searchStrs == null) {
//            return INDEX_NOT_FOUND;
//        }
//
//        // String's can't have a MAX_VALUEth index.
//        int ret = Integer.MAX_VALUE;
//
//        int tmp;
//        for (CharSequence search : searchStrs) {
//            if (search == null) {
//                continue;
//            }
//            tmp = CharSequenceUtils.indexOf(str, search, 0);
//            if (tmp == INDEX_NOT_FOUND) {
//                continue;
//            }
//
//            if (tmp < ret) {
//                ret = tmp;
//            }
//        }
//
//        return ret == Integer.MAX_VALUE ? INDEX_NOT_FOUND : ret;
//    }

    /**
     * Search a CharSequence to find the first index of any
     * character in the given set of characters.
     *
     * <p>A {@code null} String will return {@code -1}.
     * A {@code null} search string will return {@code -1}.</p>
     *
     * <pre>
     * StringUtils.indexOfAny(null, *)            = -1
     * StringUtils.indexOfAny("", *)              = -1
     * StringUtils.indexOfAny(*, null)            = -1
     * StringUtils.indexOfAny(*, "")              = -1
     * StringUtils.indexOfAny("zzabyycdxx", "za") = 0
     * StringUtils.indexOfAny("zzabyycdxx", "by") = 3
     * StringUtils.indexOfAny("aba", "z")         = -1
     * </pre>
     *
     * @param cs          the CharSequence to check, may be null
     * @param searchChars the chars to search for, may be null
     * @return the index of any of the chars, -1 if no match or null input
     */
//    public static int indexOfAny(CharSequence cs, String searchChars) {
//        if (isEmpty(cs) || isEmpty(searchChars)) {
//            return INDEX_NOT_FOUND;
//        }
//        return indexOfAny(cs, searchChars.toCharArray());
//    }

    /**
     * Searches a CharSequence to find the first index of any
     * character not in the given set of characters, i.e.,
     * find index i of first char in cs such that (cs.codePointAt(i) ∉ { x ∈ codepoints(searchChars) })
     *
     * <p>A {@code null} CharSequence will return {@code -1}.
     * A {@code null} or zero length search array will return {@code -1}.</p>
     *
     * <pre>
     * StringUtils.indexOfAnyBut(null, *)                              = -1
     * StringUtils.indexOfAnyBut("", *)                                = -1
     * StringUtils.indexOfAnyBut(*, null)                              = -1
     * StringUtils.indexOfAnyBut(*, [])                                = -1
     * StringUtils.indexOfAnyBut("zzabyycdxx", new char[] {'z', 'a'} ) = 3
     * StringUtils.indexOfAnyBut("aba", new char[] {'z'} )             = 0
     * StringUtils.indexOfAnyBut("aba", new char[] {'a', 'b'} )        = -1
     *
     * </pre>
     *
     * @param cs          the CharSequence to check, may be null
     * @param searchChars the chars to search for, may be null
     * @return the index of any of the chars, -1 if no match or null input
     */
//    public static int indexOfAnyBut(CharSequence cs, char... searchChars) {
//        if (isEmpty(cs) || ArrayUtils.isEmpty(searchChars)) {
//            return INDEX_NOT_FOUND;
//        }
//        return indexOfAnyBut(cs, CharBuffer.wrap(searchChars));
//    }

    /**
     * Search a CharSequence to find the first index of any
     * character not in the given set of characters, i.e.,
     * find index i of first char in seq such that (seq.codePointAt(i) ∉ { x ∈ codepoints(searchChars) })
     *
     * <p>A {@code null} CharSequence will return {@code -1}.
     * A {@code null} or empty search string will return {@code -1}.</p>
     *
     * <pre>
     * StringUtils.indexOfAnyBut(null, *)            = -1
     * StringUtils.indexOfAnyBut("", *)              = -1
     * StringUtils.indexOfAnyBut(*, null)            = -1
     * StringUtils.indexOfAnyBut(*, "")              = -1
     * StringUtils.indexOfAnyBut("zzabyycdxx", "za") = 3
     * StringUtils.indexOfAnyBut("zzabyycdxx", "")   = -1
     * StringUtils.indexOfAnyBut("aba", "ab")        = -1
     * </pre>
     *
     * @param seq         the CharSequence to check, may be null
     * @param searchChars the chars to search for, may be null
     * @return the index of any of the chars, -1 if no match or null input
     */
//    public static int indexOfAnyBut(CharSequence seq, CharSequence searchChars) {
//        if (isEmpty(seq) || isEmpty(searchChars)) {
//            return INDEX_NOT_FOUND;
//        }
//        Set<Integer> searchSetCodePoints = searchChars.codePoints()
//                .boxed().collect(Collectors.toSet());
//        // advance character index from one interpreted codepoint to the next
//        for (int curSeqCharIdx = 0; curSeqCharIdx < seq.length(); ) {
//            int curSeqCodePoint = Character.codePointAt(seq, curSeqCharIdx);
//            if (!searchSetCodePoints.contains(curSeqCodePoint)) {
//                return curSeqCharIdx;
//            }
//            curSeqCharIdx += Character.charCount(curSeqCodePoint); // skip indices to paired low-surrogates
//        }
//        return INDEX_NOT_FOUND;
//    }

    /**
     * Compares all CharSequences in an array and returns the index at which the
     * CharSequences begin to differ.
     *
     * <p>For example,
     * {@code indexOfDifference(new String[] {"i am a machine", "i am a robot"}) -> 7}</p>
     *
     * <pre>
     * StringUtils.indexOfDifference(null)                             = -1
     * StringUtils.indexOfDifference(new String[] {})                  = -1
     * StringUtils.indexOfDifference(new String[] {"abc"})             = -1
     * StringUtils.indexOfDifference(new String[] {null, null})        = -1
     * StringUtils.indexOfDifference(new String[] {"", ""})            = -1
     * StringUtils.indexOfDifference(new String[] {"", null})          = 0
     * StringUtils.indexOfDifference(new String[] {"abc", null, null}) = 0
     * StringUtils.indexOfDifference(new String[] {null, null, "abc"}) = 0
     * StringUtils.indexOfDifference(new String[] {"", "abc"})         = 0
     * StringUtils.indexOfDifference(new String[] {"abc", ""})         = 0
     * StringUtils.indexOfDifference(new String[] {"abc", "abc"})      = -1
     * StringUtils.indexOfDifference(new String[] {"abc", "a"})        = 1
     * StringUtils.indexOfDifference(new String[] {"ab", "abxyz"})     = 2
     * StringUtils.indexOfDifference(new String[] {"abcde", "abxyz"})  = 2
     * StringUtils.indexOfDifference(new String[] {"abcde", "xyz"})    = 0
     * StringUtils.indexOfDifference(new String[] {"xyz", "abcde"})    = 0
     * StringUtils.indexOfDifference(new String[] {"i am a machine", "i am a robot"}) = 7
     * </pre>
     *
     * @param css array of CharSequences, entries may be null
     * @return the index where the strings begin to differ; -1 if they are all equal
     */
    public static int indexOfDifference(CharSequence... css) {
        if (css == null || css.length <= 1) {
            return INDEX_NOT_FOUND;
        }
        boolean anyStringNull = false;
        boolean allStringsNull = true;
        int arrayLen = css.length;
        int shortestStrLen = Integer.MAX_VALUE;
        int longestStrLen = 0;

        // find the min and max string lengths; this avoids checking to make
        // sure we are not exceeding the length of the string each time through
        // the bottom loop.
        for (CharSequence cs : css) {
            if (cs == null) {
                anyStringNull = true;
                shortestStrLen = 0;
            } else {
                allStringsNull = false;
                shortestStrLen = Math.min(cs.length(), shortestStrLen);
                longestStrLen = Math.max(cs.length(), longestStrLen);
            }
        }

        // handle lists containing all nulls or all empty strings
        if (allStringsNull || longestStrLen == 0 && !anyStringNull) {
            return INDEX_NOT_FOUND;
        }

        // handle lists containing some nulls or some empty strings
        if (shortestStrLen == 0) {
            return 0;
        }

        // find the position with the first difference across all strings
        int firstDiff = -1;
        for (int stringPos = 0; stringPos < shortestStrLen; stringPos++) {
            char comparisonChar = css[0].charAt(stringPos);
            for (int arrayPos = 1; arrayPos < arrayLen; arrayPos++) {
                if (css[arrayPos].charAt(stringPos) != comparisonChar) {
                    firstDiff = stringPos;
                    break;
                }
            }
            if (firstDiff != -1) {
                break;
            }
        }

        if (firstDiff == -1 && shortestStrLen != longestStrLen) {
            // we compared all of the characters up to the length of the
            // shortest string and didn't find a match, but the string lengths
            // vary, so return the length of the shortest string.
            return shortestStrLen;
        }
        return firstDiff;
    }

    /**
     * Compares two CharSequences, and returns the index at which the
     * CharSequences begin to differ.
     *
     * <p>For example,
     * {@code indexOfDifference("i am a machine", "i am a robot") -> 7}</p>
     *
     * <pre>
     * StringUtils.indexOfDifference(null, null)       = -1
     * StringUtils.indexOfDifference("", "")           = -1
     * StringUtils.indexOfDifference("", "abc")        = 0
     * StringUtils.indexOfDifference("abc", "")        = 0
     * StringUtils.indexOfDifference("abc", "abc")     = -1
     * StringUtils.indexOfDifference("ab", "abxyz")    = 2
     * StringUtils.indexOfDifference("abcde", "abxyz") = 2
     * StringUtils.indexOfDifference("abcde", "xyz")   = 0
     * </pre>
     *
     * @param cs1 the first CharSequence, may be null
     * @param cs2 the second CharSequence, may be null
     * @return the index where cs1 and cs2 begin to differ; -1 if they are equal
     * indexOfDifference(CharSequence, CharSequence)
     */
    public static int indexOfDifference(CharSequence cs1, CharSequence cs2) {
        if (cs1 == cs2) {
            return INDEX_NOT_FOUND;
        }
        if (cs1 == null || cs2 == null) {
            return 0;
        }
        int i;
        for (i = 0; i < cs1.length() && i < cs2.length(); ++i) {
            if (cs1.charAt(i) != cs2.charAt(i)) {
                break;
            }
        }
        if (i < cs2.length() || i < cs1.length()) {
            return i;
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * Tests if all of the CharSequences are empty (""), null or whitespace only.
     *
     * <p>Whitespace is defined by {@link Character#isWhitespace(char)}.</p>
     *
     * <pre>
     * StringUtils.isAllBlank(null)             = true
     * StringUtils.isAllBlank(null, "foo")      = false
     * StringUtils.isAllBlank(null, null)       = true
     * StringUtils.isAllBlank("", "bar")        = false
     * StringUtils.isAllBlank("bob", "")        = false
     * StringUtils.isAllBlank("  bob  ", null)  = false
     * StringUtils.isAllBlank(" ", "bar")       = false
     * StringUtils.isAllBlank("foo", "bar")     = false
     * StringUtils.isAllBlank(new String[] {})  = true
     * </pre>
     *
     * @param css the CharSequences to check, may be null or empty
     * @return {@code true} if all of the CharSequences are empty or null or whitespace only
     */
    public static boolean isAllBlank(CharSequence... css) {
        if (css == null || css.length == 0) {
            return true;
        }
        for (CharSequence cs : css) {
            if (isNotBlank(cs)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Tests if all of the CharSequences are empty ("") or null.
     *
     * <pre>
     * StringUtils.isAllEmpty(null)             = true
     * StringUtils.isAllEmpty(null, "")         = true
     * StringUtils.isAllEmpty(new String[] {})  = true
     * StringUtils.isAllEmpty(null, "foo")      = false
     * StringUtils.isAllEmpty("", "bar")        = false
     * StringUtils.isAllEmpty("bob", "")        = false
     * StringUtils.isAllEmpty("  bob  ", null)  = false
     * StringUtils.isAllEmpty(" ", "bar")       = false
     * StringUtils.isAllEmpty("foo", "bar")     = false
     * </pre>
     *
     * @param css the CharSequences to check, may be null or empty
     * @return {@code true} if all of the CharSequences are empty or null
     */
    public static boolean isAllEmpty(String... css) {
        if (css == null || css.length == 0) {
            return true;
        }
        for (String cs : css) {
            if (hasLength(cs)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Tests if the CharSequence contains only lowercase characters.
     *
     * <p>{@code null} will return {@code false}.
     * An empty CharSequence (length()=0) will return {@code false}.</p>
     *
     * <pre>
     * StringUtils.isAllLowerCase(null)   = false
     * StringUtils.isAllLowerCase("")     = false
     * StringUtils.isAllLowerCase("  ")   = false
     * StringUtils.isAllLowerCase("abc")  = true
     * StringUtils.isAllLowerCase("abC")  = false
     * StringUtils.isAllLowerCase("ab c") = false
     * StringUtils.isAllLowerCase("ab1c") = false
     * StringUtils.isAllLowerCase("ab/c") = false
     * </pre>
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if only contains lowercase characters, and is non-null
     */
    public static boolean isAllLowerCase(CharSequence cs) {
        if (isEmpty(cs)) {
            return false;
        }
        int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isLowerCase(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Tests if the CharSequence contains only uppercase characters.
     *
     * <p>{@code null} will return {@code false}.
     * An empty String (length()=0) will return {@code false}.</p>
     *
     * <pre>
     * StringUtils.isAllUpperCase(null)   = false
     * StringUtils.isAllUpperCase("")     = false
     * StringUtils.isAllUpperCase("  ")   = false
     * StringUtils.isAllUpperCase("ABC")  = true
     * StringUtils.isAllUpperCase("aBC")  = false
     * StringUtils.isAllUpperCase("A C")  = false
     * StringUtils.isAllUpperCase("A1C")  = false
     * StringUtils.isAllUpperCase("A/C")  = false
     * </pre>
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if only contains uppercase characters, and is non-null
     */
    public static boolean isAllUpperCase(CharSequence cs) {
        if (isEmpty(cs)) {
            return false;
        }
        int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isUpperCase(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Tests if the CharSequence contains only Unicode letters.
     *
     * <p>{@code null} will return {@code false}.
     * An empty CharSequence (length()=0) will return {@code false}.</p>
     *
     * <pre>
     * StringUtils.isAlpha(null)   = false
     * StringUtils.isAlpha("")     = false
     * StringUtils.isAlpha("  ")   = false
     * StringUtils.isAlpha("abc")  = true
     * StringUtils.isAlpha("ab2c") = false
     * StringUtils.isAlpha("ab-c") = false
     * </pre>
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if only contains letters, and is non-null
     */
    public static boolean isAlpha(CharSequence cs) {
        if (isEmpty(cs)) {
            return false;
        }
        int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isLetter(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Tests if the CharSequence contains only Unicode letters or digits.
     *
     * <p>{@code null} will return {@code false}.
     * An empty CharSequence (length()=0) will return {@code false}.</p>
     *
     * <pre>
     * StringUtils.isAlphanumeric(null)   = false
     * StringUtils.isAlphanumeric("")     = false
     * StringUtils.isAlphanumeric("  ")   = false
     * StringUtils.isAlphanumeric("abc")  = true
     * StringUtils.isAlphanumeric("ab c") = false
     * StringUtils.isAlphanumeric("ab2c") = true
     * StringUtils.isAlphanumeric("ab-c") = false
     * </pre>
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if only contains letters or digits,
     * and is non-null
     */
    public static boolean isAlphanumeric(CharSequence cs) {
        if (isEmpty(cs)) {
            return false;
        }
        int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isLetterOrDigit(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Tests if the CharSequence contains only Unicode letters, digits
     * or space ({@code ' '}).
     *
     * <p>{@code null} will return {@code false}.
     * An empty CharSequence (length()=0) will return {@code true}.</p>
     *
     * <pre>
     * StringUtils.isAlphanumericSpace(null)   = false
     * StringUtils.isAlphanumericSpace("")     = true
     * StringUtils.isAlphanumericSpace("  ")   = true
     * StringUtils.isAlphanumericSpace("abc")  = true
     * StringUtils.isAlphanumericSpace("ab c") = true
     * StringUtils.isAlphanumericSpace("ab2c") = true
     * StringUtils.isAlphanumericSpace("ab-c") = false
     * </pre>
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if only contains letters, digits or space,
     * and is non-null
     */
    public static boolean isAlphanumericSpace(CharSequence cs) {
        if (cs == null) {
            return false;
        }
        int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            char nowChar = cs.charAt(i);
            if (nowChar != ' ' && !Character.isLetterOrDigit(nowChar)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Tests if the CharSequence contains only Unicode letters and
     * space (' ').
     *
     * <p>{@code null} will return {@code false}
     * An empty CharSequence (length()=0) will return {@code true}.</p>
     *
     * <pre>
     * StringUtils.isAlphaSpace(null)   = false
     * StringUtils.isAlphaSpace("")     = true
     * StringUtils.isAlphaSpace("  ")   = true
     * StringUtils.isAlphaSpace("abc")  = true
     * StringUtils.isAlphaSpace("ab c") = true
     * StringUtils.isAlphaSpace("ab2c") = false
     * StringUtils.isAlphaSpace("ab-c") = false
     * </pre>
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if only contains letters and space,
     * and is non-null
     */
    public static boolean isAlphaSpace(CharSequence cs) {
        if (cs == null) {
            return false;
        }
        int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            char nowChar = cs.charAt(i);
            if (nowChar != ' ' && !Character.isLetter(nowChar)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Tests if any of the CharSequences are {@link #isBlank(CharSequence) blank} (whitespaces, empty ({@code ""}) or {@code null}).
     *
     * <p>
     * Whitespace is defined by {@link Character#isWhitespace(char)}.
     * </p>
     *
     * <pre>
     * StringUtils.isAnyBlank((String) null)    = true
     * StringUtils.isAnyBlank((String[]) null)  = false
     * StringUtils.isAnyBlank(null, "foo")      = true
     * StringUtils.isAnyBlank(null, null)       = true
     * StringUtils.isAnyBlank("", "bar")        = true
     * StringUtils.isAnyBlank("bob", "")        = true
     * StringUtils.isAnyBlank("  bob  ", null)  = true
     * StringUtils.isAnyBlank(" ", "bar")       = true
     * StringUtils.isAnyBlank(new String[] {})  = false
     * StringUtils.isAnyBlank(new String[]{""}) = true
     * StringUtils.isAnyBlank("foo", "bar")     = false
     * </pre>
     *
     * @param css the CharSequences to check, may be null or empty
     * @return {@code true} if any of the CharSequences are {@link #isBlank(CharSequence) blank} (whitespaces, empty ({@code ""}) or {@code null})
     * @see #isBlank(CharSequence)
     */
    public static boolean isAnyBlank(CharSequence... css) {
        if (css == null || css.length == 0) {
            return false;
        }
        for (CharSequence cs : css) {
            if (isBlank(cs)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Tests if any of the CharSequences are empty ("") or null.
     *
     * <pre>
     * StringUtils.isAnyEmpty((String) null)    = true
     * StringUtils.isAnyEmpty((String[]) null)  = false
     * StringUtils.isAnyEmpty(null, "foo")      = true
     * StringUtils.isAnyEmpty("", "bar")        = true
     * StringUtils.isAnyEmpty("bob", "")        = true
     * StringUtils.isAnyEmpty("  bob  ", null)  = true
     * StringUtils.isAnyEmpty(" ", "bar")       = false
     * StringUtils.isAnyEmpty("foo", "bar")     = false
     * StringUtils.isAnyEmpty(new String[]{})   = false
     * StringUtils.isAnyEmpty(new String[]{""}) = true
     * </pre>
     *
     * @param css the CharSequences to check, may be null or empty
     * @return {@code true} if any of the CharSequences are empty or null
     */
    public static boolean isAnyEmpty(CharSequence... css) {
        if (css == null || css.length == 0) {
            return false;
        }
        for (CharSequence cs : css) {
            if (isEmpty(cs)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Tests if the CharSequence contains only ASCII printable characters.
     *
     * <p>{@code null} will return {@code false}.
     * An empty CharSequence (length()=0) will return {@code true}.</p>
     *
     * <pre>
     * StringUtils.isAsciiPrintable(null)     = false
     * StringUtils.isAsciiPrintable("")       = true
     * StringUtils.isAsciiPrintable(" ")      = true
     * StringUtils.isAsciiPrintable("Ceki")   = true
     * StringUtils.isAsciiPrintable("ab2c")   = true
     * StringUtils.isAsciiPrintable("!ab-c~") = true
     * StringUtils.isAsciiPrintable("\u0020") = true
     * StringUtils.isAsciiPrintable("\u0021") = true
     * StringUtils.isAsciiPrintable("\u007e") = true
     * StringUtils.isAsciiPrintable("\u007f") = false
     * StringUtils.isAsciiPrintable("Ceki G\u00fclc\u00fc") = false
     * </pre>
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if every character is in the range
     * 32 through 126
     */
    public static boolean isAsciiPrintable(CharSequence cs) {
        if (cs == null) {
            return false;
        }
        int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            char ch = cs.charAt(i);
            if (ch >= 32 && ch < 127) {
                return false;
            }
        }
        return true;
    }

    /**
     * Tests if a CharSequence is empty ({@code "")}, null, or contains only whitespace as defined by {@link Character#isWhitespace(char)}.
     *
     * <pre>
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank("")        = true
     * StringUtils.isBlank(" ")       = true
     * StringUtils.isBlank("bob")     = false
     * StringUtils.isBlank("  bob  ") = false
     * </pre>
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is null, empty or whitespace only
     */
    public static boolean isBlank(CharSequence cs) {
        int strLen = length(cs);
        if (strLen == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Tests if a CharSequence is empty ("") or null.
     *
     * <pre>
     * StringUtils.isEmpty(null)      = true
     * StringUtils.isEmpty("")        = true
     * StringUtils.isEmpty(" ")       = false
     * StringUtils.isEmpty("bob")     = false
     * StringUtils.isEmpty("  bob  ") = false
     * </pre>
     *
     * <p>NOTE: This method changed in Lang version 2.0.
     * It no longer trims the CharSequence.
     * That functionality is available in isBlank().</p>
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is empty or null
     */
    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.isEmpty();
    }


    /**
     * Tests if the CharSequence contains mixed casing of both uppercase and lowercase characters.
     *
     * <p>{@code null} will return {@code false}. An empty CharSequence ({@code length()=0}) will return
     * {@code false}.</p>
     *
     * <pre>
     * StringUtils.isMixedCase(null)    = false
     * StringUtils.isMixedCase("")      = false
     * StringUtils.isMixedCase(" ")     = false
     * StringUtils.isMixedCase("ABC")   = false
     * StringUtils.isMixedCase("abc")   = false
     * StringUtils.isMixedCase("aBc")   = true
     * StringUtils.isMixedCase("A c")   = true
     * StringUtils.isMixedCase("A1c")   = true
     * StringUtils.isMixedCase("a/C")   = true
     * StringUtils.isMixedCase("aC\t")  = true
     * </pre>
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if the CharSequence contains both uppercase and lowercase characters
     */
    public static boolean isMixedCase(CharSequence cs) {
        if (isEmpty(cs) || cs.length() == 1) {
            return false;
        }
        boolean containsUppercase = false;
        boolean containsLowercase = false;
        int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            char nowChar = cs.charAt(i);
            if (Character.isUpperCase(nowChar)) {
                containsUppercase = true;
            } else if (Character.isLowerCase(nowChar)) {
                containsLowercase = true;
            }
            if (containsUppercase && containsLowercase) {
                return true;
            }
        }
        return false;
    }

    /**
     * Tests if none of the CharSequences are empty (""), null or whitespace only.
     *
     * <p>Whitespace is defined by {@link Character#isWhitespace(char)}.</p>
     *
     * <pre>
     * StringUtils.isNoneBlank((String) null)    = false
     * StringUtils.isNoneBlank((String[]) null)  = true
     * StringUtils.isNoneBlank(null, "foo")      = false
     * StringUtils.isNoneBlank(null, null)       = false
     * StringUtils.isNoneBlank("", "bar")        = false
     * StringUtils.isNoneBlank("bob", "")        = false
     * StringUtils.isNoneBlank("  bob  ", null)  = false
     * StringUtils.isNoneBlank(" ", "bar")       = false
     * StringUtils.isNoneBlank(new String[] {})  = true
     * StringUtils.isNoneBlank(new String[]{""}) = false
     * StringUtils.isNoneBlank("foo", "bar")     = true
     * </pre>
     *
     * @param css the CharSequences to check, may be null or empty
     * @return {@code true} if none of the CharSequences are empty or null or whitespace only
     */
    public static boolean isNoneBlank(CharSequence... css) {
        return !isAnyBlank(css);
    }

    /**
     * Tests if none of the CharSequences are empty ("") or null.
     *
     * <pre>
     * StringUtils.isNoneEmpty((String) null)    = false
     * StringUtils.isNoneEmpty((String[]) null)  = true
     * StringUtils.isNoneEmpty(null, "foo")      = false
     * StringUtils.isNoneEmpty("", "bar")        = false
     * StringUtils.isNoneEmpty("bob", "")        = false
     * StringUtils.isNoneEmpty("  bob  ", null)  = false
     * StringUtils.isNoneEmpty(new String[] {})  = true
     * StringUtils.isNoneEmpty(new String[]{""}) = false
     * StringUtils.isNoneEmpty(" ", "bar")       = true
     * StringUtils.isNoneEmpty("foo", "bar")     = true
     * </pre>
     *
     * @param css the CharSequences to check, may be null or empty
     * @return {@code true} if none of the CharSequences are empty or null
     */
    public static boolean isNoneEmpty(CharSequence... css) {
        return !isAnyEmpty(css);
    }

    /**
     * Tests if a CharSequence is not {@link #isBlank(CharSequence) blank} (whitespaces, empty ({@code ""}) or {@code null}).
     *
     * <p>
     * Whitespace is defined by {@link Character#isWhitespace(char)}.
     * </p>
     *
     * <pre>
     * StringUtils.isNotBlank(null)      = false
     * StringUtils.isNotBlank("")        = false
     * StringUtils.isNotBlank(" ")       = false
     * StringUtils.isNotBlank("bob")     = true
     * StringUtils.isNotBlank("  bob  ") = true
     * </pre>
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is not {@link #isBlank(CharSequence) blank} (whitespaces, empty ({@code ""}) or {@code null})
     * @see #isBlank(CharSequence)
     */
    public static boolean isNotBlank(CharSequence cs) {
        return !isBlank(cs);
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
     * Tests if the CharSequence contains only Unicode digits.
     * A decimal point is not a Unicode digit and returns false.
     *
     * <p>{@code null} will return {@code false}.
     * An empty CharSequence (length()=0) will return {@code false}.</p>
     *
     * <p>Note that the method does not allow for a leading sign, either positive or negative.
     * Also, if a String passes the numeric test, it may still generate a NumberFormatException
     * when parsed by Integer.parseInt or Long.parseLong, e.g. if the value is outside the range
     * for int or long respectively.</p>
     *
     * <pre>
     * StringUtils.isNumeric(null)   = false
     * StringUtils.isNumeric("")     = false
     * StringUtils.isNumeric("  ")   = false
     * StringUtils.isNumeric("123")  = true
     * StringUtils.isNumeric("\u0967\u0968\u0969")  = true
     * StringUtils.isNumeric("12 3") = false
     * StringUtils.isNumeric("ab2c") = false
     * StringUtils.isNumeric("12-3") = false
     * StringUtils.isNumeric("12.3") = false
     * StringUtils.isNumeric("-123") = false
     * StringUtils.isNumeric("+123") = false
     * </pre>
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if only contains digits, and is non-null
     */
    public static boolean isNumeric(CharSequence cs) {
        if (isEmpty(cs)) {
            return false;
        }
        int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isDigit(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Tests if the CharSequence contains only Unicode digits or space
     * ({@code ' '}).
     * A decimal point is not a Unicode digit and returns false.
     *
     * <p>{@code null} will return {@code false}.
     * An empty CharSequence (length()=0) will return {@code true}.</p>
     *
     * <pre>
     * StringUtils.isNumericSpace(null)   = false
     * StringUtils.isNumericSpace("")     = true
     * StringUtils.isNumericSpace("  ")   = true
     * StringUtils.isNumericSpace("123")  = true
     * StringUtils.isNumericSpace("12 3") = true
     * StringUtils.isNumericSpace("\u0967\u0968\u0969")   = true
     * StringUtils.isNumericSpace("\u0967\u0968 \u0969")  = true
     * StringUtils.isNumericSpace("ab2c") = false
     * StringUtils.isNumericSpace("12-3") = false
     * StringUtils.isNumericSpace("12.3") = false
     * </pre>
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if only contains digits or space,
     * and is non-null
     */
    public static boolean isNumericSpace(CharSequence cs) {
        if (cs == null) {
            return false;
        }
        int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            char nowChar = cs.charAt(i);
            if (nowChar != ' ' && !Character.isDigit(nowChar)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Tests if the CharSequence contains only whitespace.
     *
     * <p>Whitespace is defined by {@link Character#isWhitespace(char)}.</p>
     *
     * <p>{@code null} will return {@code false}.
     * An empty CharSequence (length()=0) will return {@code true}.</p>
     *
     * <pre>
     * StringUtils.isWhitespace(null)   = false
     * StringUtils.isWhitespace("")     = true
     * StringUtils.isWhitespace("  ")   = true
     * StringUtils.isWhitespace("abc")  = false
     * StringUtils.isWhitespace("ab2c") = false
     * StringUtils.isWhitespace("ab-c") = false
     * </pre>
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if only contains whitespace, and is non-null
     */
    public static boolean isWhitespace(CharSequence cs) {
        if (cs == null) {
            return false;
        }
        int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Joins the elements of the provided array into a single String containing the provided list of elements.
     *
     * <p>
     * No delimiter is added before or after the list. Null objects or empty strings within the array are represented
     * by empty strings.
     * </p>
     *
     * <pre>
     * StringUtils.join(null, *)             = null
     * StringUtils.join([], *)               = ""
     * StringUtils.join([null], *)           = ""
     * StringUtils.join([false, false], ';') = "false;false"
     * </pre>
     *
     * @param array     the array of values to join together, may be null
     * @param delimiter the separator character to use
     * @return the joined String, {@code null} if null array input
     */
    public static String join(boolean[] array, char delimiter) {
        if (array == null) {
            return null;
        }
        return join(array, delimiter, 0, array.length);
    }

    /**
     * Joins the elements of the provided array into a single String containing the provided list of elements.
     *
     * <p>
     * No delimiter is added before or after the list. Null objects or empty strings within the array are represented
     * by empty strings.
     * </p>
     *
     * <pre>
     * StringUtils.join(null, *)                  = null
     * StringUtils.join([], *)                    = ""
     * StringUtils.join([null], *)                = ""
     * StringUtils.join([true, false, true], ';') = "true;false;true"
     * </pre>
     *
     * @param array      the array of values to join together, may be null
     * @param delimiter  the separator character to use
     * @param startIndex the first index to start joining from. It is an error to pass in a start index past the end of the
     *                   array
     * @param endIndex   the index to stop joining from (exclusive). It is an error to pass in an end index past the end of
     *                   the array
     * @return the joined String, {@code null} if null array input
     */
    public static String join(boolean[] array, char delimiter, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        }
        if (endIndex - startIndex <= 0) {
            return EMPTY;
        }
        StringBuilder stringBuilder = new StringBuilder(array.length * 5 + array.length - 1);
        for (int i = startIndex; i < endIndex; i++) {
            stringBuilder
                    .append(array[i])
                    .append(delimiter);
        }
        return stringBuilder.substring(0, stringBuilder.length() - 1);
    }

    /**
     * Joins the elements of the provided array into a single String containing the provided list of elements.
     *
     * <p>
     * No delimiter is added before or after the list. Null objects or empty strings within the array are represented
     * by empty strings.
     * </p>
     *
     * <pre>
     * StringUtils.join(null, *)         = null
     * StringUtils.join([], *)           = ""
     * StringUtils.join([null], *)       = ""
     * StringUtils.join([1, 2, 3], ';')  = "1;2;3"
     * StringUtils.join([1, 2, 3], null) = "123"
     * </pre>
     *
     * @param array     the array of values to join together, may be null
     * @param delimiter the separator character to use
     * @return the joined String, {@code null} if null array input
     */
    public static String join(byte[] array, char delimiter) {
        if (array == null) {
            return null;
        }
        return join(array, delimiter, 0, array.length);
    }

    /**
     * Joins the elements of the provided array into a single String containing the provided list of elements.
     *
     * <p>
     * No delimiter is added before or after the list. Null objects or empty strings within the array are represented
     * by empty strings.
     * </p>
     *
     * <pre>
     * StringUtils.join(null, *)         = null
     * StringUtils.join([], *)           = ""
     * StringUtils.join([null], *)       = ""
     * StringUtils.join([1, 2, 3], ';')  = "1;2;3"
     * StringUtils.join([1, 2, 3], null) = "123"
     * </pre>
     *
     * @param array      the array of values to join together, may be null
     * @param delimiter  the separator character to use
     * @param startIndex the first index to start joining from. It is an error to pass in a start index past the end of the
     *                   array
     * @param endIndex   the index to stop joining from (exclusive). It is an error to pass in an end index past the end of
     *                   the array
     * @return the joined String, {@code null} if null array input
     */
    public static String join(byte[] array, char delimiter, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        }
        if (endIndex - startIndex <= 0) {
            return EMPTY;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = startIndex; i < endIndex; i++) {
            stringBuilder
                    .append(array[i])
                    .append(delimiter);
        }
        return stringBuilder.substring(0, stringBuilder.length() - 1);
    }

    /**
     * Joins the elements of the provided array into a single String containing the provided list of elements.
     *
     * <p>
     * No delimiter is added before or after the list. Null objects or empty strings within the array are represented
     * by empty strings.
     * </p>
     *
     * <pre>
     * StringUtils.join(null, *)         = null
     * StringUtils.join([], *)           = ""
     * StringUtils.join([null], *)       = ""
     * StringUtils.join([1, 2, 3], ';')  = "1;2;3"
     * StringUtils.join([1, 2, 3], null) = "123"
     * </pre>
     *
     * @param array     the array of values to join together, may be null
     * @param delimiter the separator character to use
     * @return the joined String, {@code null} if null array input
     */
    public static String join(char[] array, char delimiter) {
        if (array == null) {
            return null;
        }
        return join(array, delimiter, 0, array.length);
    }

    /**
     * Joins the elements of the provided array into a single String containing the provided list of elements.
     *
     * <p>
     * No delimiter is added before or after the list. Null objects or empty strings within the array are represented
     * by empty strings.
     * </p>
     *
     * <pre>
     * StringUtils.join(null, *)         = null
     * StringUtils.join([], *)           = ""
     * StringUtils.join([null], *)       = ""
     * StringUtils.join([1, 2, 3], ';')  = "1;2;3"
     * StringUtils.join([1, 2, 3], null) = "123"
     * </pre>
     *
     * @param array      the array of values to join together, may be null
     * @param delimiter  the separator character to use
     * @param startIndex the first index to start joining from. It is an error to pass in a start index past the end of the
     *                   array
     * @param endIndex   the index to stop joining from (exclusive). It is an error to pass in an end index past the end of
     *                   the array
     * @return the joined String, {@code null} if null array input
     */
    public static String join(char[] array, char delimiter, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        }
        if (endIndex - startIndex <= 0) {
            return EMPTY;
        }
        StringBuilder stringBuilder = new StringBuilder(array.length * 2 - 1);
        for (int i = startIndex; i < endIndex; i++) {
            stringBuilder
                    .append(array[i])
                    .append(delimiter);
        }
        return stringBuilder.substring(0, stringBuilder.length() - 1);
    }

    /**
     * Joins the elements of the provided array into a single String containing the provided list of elements.
     *
     * <p>
     * No delimiter is added before or after the list. Null objects or empty strings within the array are represented
     * by empty strings.
     * </p>
     *
     * <pre>
     * StringUtils.join(null, *)               = null
     * StringUtils.join([], *)                 = ""
     * StringUtils.join([null], *)             = ""
     * StringUtils.join([1, 2, 3], ';')  = "1;2;3"
     * StringUtils.join([1, 2, 3], null) = "123"
     * </pre>
     *
     * @param array     the array of values to join together, may be null
     * @param delimiter the separator character to use
     * @return the joined String, {@code null} if null array input
     */
    public static String join(double[] array, char delimiter) {
        if (array == null) {
            return null;
        }
        return join(array, delimiter, 0, array.length);
    }

    /**
     * Joins the elements of the provided array into a single String containing the provided list of elements.
     *
     * <p>
     * No delimiter is added before or after the list. Null objects or empty strings within the array are represented
     * by empty strings.
     * </p>
     *
     * <pre>
     * StringUtils.join(null, *)               = null
     * StringUtils.join([], *)                 = ""
     * StringUtils.join([null], *)             = ""
     * StringUtils.join([1, 2, 3], ';')  = "1;2;3"
     * StringUtils.join([1, 2, 3], null) = "123"
     * </pre>
     *
     * @param array      the array of values to join together, may be null
     * @param delimiter  the separator character to use
     * @param startIndex the first index to start joining from. It is an error to pass in a start index past the end of the
     *                   array
     * @param endIndex   the index to stop joining from (exclusive). It is an error to pass in an end index past the end of
     *                   the array
     * @return the joined String, {@code null} if null array input
     */
    public static String join(double[] array, char delimiter, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        }
        if (endIndex - startIndex <= 0) {
            return EMPTY;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = startIndex; i < endIndex; i++) {
            stringBuilder
                    .append(array[i])
                    .append(delimiter);
        }
        return stringBuilder.substring(0, stringBuilder.length() - 1);
    }

    /**
     * Joins the elements of the provided array into a single String containing the provided list of elements.
     *
     * <p>
     * No delimiter is added before or after the list. Null objects or empty strings within the array are represented
     * by empty strings.
     * </p>
     *
     * <pre>
     * StringUtils.join(null, *)               = null
     * StringUtils.join([], *)                 = ""
     * StringUtils.join([null], *)             = ""
     * StringUtils.join([1, 2, 3], ';')  = "1;2;3"
     * StringUtils.join([1, 2, 3], null) = "123"
     * </pre>
     *
     * @param array     the array of values to join together, may be null
     * @param delimiter the separator character to use
     * @return the joined String, {@code null} if null array input
     */
    public static String join(float[] array, char delimiter) {
        if (array == null) {
            return null;
        }
        return join(array, delimiter, 0, array.length);
    }

    /**
     * Joins the elements of the provided array into a single String containing the provided list of elements.
     *
     * <p>
     * No delimiter is added before or after the list. Null objects or empty strings within the array are represented
     * by empty strings.
     * </p>
     *
     * <pre>
     * StringUtils.join(null, *)               = null
     * StringUtils.join([], *)                 = ""
     * StringUtils.join([null], *)             = ""
     * StringUtils.join([1, 2, 3], ';')  = "1;2;3"
     * StringUtils.join([1, 2, 3], null) = "123"
     * </pre>
     *
     * @param array      the array of values to join together, may be null
     * @param delimiter  the separator character to use
     * @param startIndex the first index to start joining from. It is an error to pass in a start index past the end of the
     *                   array
     * @param endIndex   the index to stop joining from (exclusive). It is an error to pass in an end index past the end of
     *                   the array
     * @return the joined String, {@code null} if null array input
     */
    public static String join(float[] array, char delimiter, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        }
        if (endIndex - startIndex <= 0) {
            return EMPTY;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = startIndex; i < endIndex; i++) {
            stringBuilder
                    .append(array[i])
                    .append(delimiter);
        }
        return stringBuilder.substring(0, stringBuilder.length() - 1);
    }

    /**
     * Joins the elements of the provided array into a single String containing the provided list of elements.
     *
     * <p>
     * No delimiter is added before or after the list. Null objects or empty strings within the array are represented
     * by empty strings.
     * </p>
     *
     * <pre>
     * StringUtils.join(null, *)               = null
     * StringUtils.join([], *)                 = ""
     * StringUtils.join([null], *)             = ""
     * StringUtils.join([1, 2, 3], ';')  = "1;2;3"
     * StringUtils.join([1, 2, 3], null) = "123"
     * </pre>
     *
     * @param array     the array of values to join together, may be null
     * @param separator the separator character to use
     * @return the joined String, {@code null} if null array input
     */
    public static String join(int[] array, char separator) {
        if (array == null) {
            return null;
        }
        return join(array, separator, 0, array.length);
    }

    /**
     * Joins the elements of the provided array into a single String containing the provided list of elements.
     *
     * <p>
     * No delimiter is added before or after the list. Null objects or empty strings within the array are represented
     * by empty strings.
     * </p>
     *
     * <pre>
     * StringUtils.join(null, *)               = null
     * StringUtils.join([], *)                 = ""
     * StringUtils.join([null], *)             = ""
     * StringUtils.join([1, 2, 3], ';')  = "1;2;3"
     * StringUtils.join([1, 2, 3], null) = "123"
     * </pre>
     *
     * @param array      the array of values to join together, may be null
     * @param delimiter  the separator character to use
     * @param startIndex the first index to start joining from. It is an error to pass in a start index past the end of the
     *                   array
     * @param endIndex   the index to stop joining from (exclusive). It is an error to pass in an end index past the end of
     *                   the array
     * @return the joined String, {@code null} if null array input
     */
    public static String join(int[] array, char delimiter, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        }
        if (endIndex - startIndex <= 0) {
            return EMPTY;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = startIndex; i < endIndex; i++) {
            stringBuilder
                    .append(array[i])
                    .append(delimiter);
        }
        return stringBuilder.substring(0, stringBuilder.length() - 1);
    }

    /**
     * Joins the elements of the provided {@link Iterable} into
     * a single String containing the provided elements.
     *
     * <p>No delimiter is added before or after the list. Null objects or empty
     * strings within the iteration are represented by empty strings.</p>
     *
     * <p>See the examples here: {@link #join(Object[], char)}.</p>
     *
     * @param iterable  the {@link Iterable} providing the values to join together, may be null
     * @param separator the separator character to use
     * @return the joined String, {@code null} if null iterator input
     */
    public static String join(Iterable<?> iterable, char separator) {
        return iterable != null ? join(iterable.iterator(), separator) : null;
    }

    /**
     * Joins the elements of the provided {@link Iterable} into
     * a single String containing the provided elements.
     *
     * <p>No delimiter is added before or after the list.
     * A {@code null} separator is the same as an empty String ("").</p>
     *
     * <p>See the examples here: {@link #join(Object[], String)}.</p>
     *
     * @param iterable  the {@link Iterable} providing the values to join together, may be null
     * @param separator the separator character to use, null treated as ""
     * @return the joined String, {@code null} if null iterator input
     */
    public static String join(Iterable<?> iterable, String separator) {
        return iterable != null ? join(iterable.iterator(), separator) : null;
    }

    /**
     * Joins the elements of the provided {@link Iterator} into
     * a single String containing the provided elements.
     *
     * <p>No delimiter is added before or after the list. Null objects or empty
     * strings within the iteration are represented by empty strings.</p>
     *
     * <p>See the examples here: {@link #join(Object[], char)}.</p>
     *
     * @param iterator  the {@link Iterator} of values to join together, may be null
     * @param separator the separator character to use
     * @return the joined String, {@code null} if null iterator input
     */
//    public static String join(Iterator<?> iterator, char separator) {
//        // handle null, zero and one elements before building a buffer
//        if (iterator == null) {
//            return null;
//        }
//        if (!iterator.hasNext()) {
//            return EMPTY;
//        }
//        return Streams.of(iterator).collect(LangCollectors.joining(ObjectUtils.toString(String.valueOf(separator)), EMPTY, EMPTY, ObjectUtils::toString));
//    }

    /**
     * Joins the elements of the provided {@link Iterator} into
     * a single String containing the provided elements.
     *
     * <p>No delimiter is added before or after the list.
     * A {@code null} separator is the same as an empty String ("").</p>
     *
     * <p>See the examples here: {@link #join(Object[], String)}.</p>
     *
     * @param iterator  the {@link Iterator} of values to join together, may be null
     * @param separator the separator character to use, null treated as ""
     * @return the joined String, {@code null} if null iterator input
     */
//    public static String join(Iterator<?> iterator, String separator) {
//        // handle null, zero and one elements before building a buffer
//        if (iterator == null) {
//            return null;
//        }
//        if (!iterator.hasNext()) {
//            return EMPTY;
//        }
//        return Streams.of(iterator).collect(LangCollectors.joining(ObjectUtils.toString(separator), EMPTY, EMPTY, ObjectUtils::toString));
//    }

    /**
     * Joins the elements of the provided {@link List} into a single String
     * containing the provided list of elements.
     *
     * <p>No delimiter is added before or after the list.
     * Null objects or empty strings within the array are represented by
     * empty strings.</p>
     *
     * <pre>
     * StringUtils.join(null, *)               = null
     * StringUtils.join([], *)                 = ""
     * StringUtils.join([null], *)             = ""
     * StringUtils.join(["a", "b", "c"], ';')  = "a;b;c"
     * StringUtils.join(["a", "b", "c"], null) = "abc"
     * StringUtils.join([null, "", "a"], ';')  = ";;a"
     * </pre>
     *
     * @param list       the {@link List} of values to join together, may be null
     * @param separator  the separator character to use
     * @param startIndex the first index to start joining from.  It is
     *                   an error to pass in a start index past the end of the list
     * @param endIndex   the index to stop joining from (exclusive). It is
     *                   an error to pass in an end index past the end of the list
     * @return the joined String, {@code null} if null list input
     */
    public static String join(List<?> list, char separator, int startIndex, int endIndex) {
        if (list == null) {
            return null;
        }
        int noOfItems = endIndex - startIndex;
        if (noOfItems <= 0) {
            return EMPTY;
        }
        List<?> subList = list.subList(startIndex, endIndex);
        return join(subList.iterator(), separator);
    }

    /**
     * Joins the elements of the provided {@link List} into a single String
     * containing the provided list of elements.
     *
     * <p>No delimiter is added before or after the list.
     * Null objects or empty strings within the array are represented by
     * empty strings.</p>
     *
     * <pre>
     * StringUtils.join(null, *)               = null
     * StringUtils.join([], *)                 = ""
     * StringUtils.join([null], *)             = ""
     * StringUtils.join(["a", "b", "c"], ';')  = "a;b;c"
     * StringUtils.join(["a", "b", "c"], null) = "abc"
     * StringUtils.join([null, "", "a"], ';')  = ";;a"
     * </pre>
     *
     * @param list       the {@link List} of values to join together, may be null
     * @param separator  the separator character to use
     * @param startIndex the first index to start joining from.  It is
     *                   an error to pass in a start index past the end of the list
     * @param endIndex   the index to stop joining from (exclusive). It is
     *                   an error to pass in an end index past the end of the list
     * @return the joined String, {@code null} if null list input
     */
    public static String join(List<?> list, String separator, int startIndex, int endIndex) {
        if (list == null) {
            return null;
        }
        int noOfItems = endIndex - startIndex;
        if (noOfItems <= 0) {
            return EMPTY;
        }
        List<?> subList = list.subList(startIndex, endIndex);
        return join(subList.iterator(), separator);
    }

    /**
     * Joins the elements of the provided array into a single String containing the provided list of elements.
     *
     * <p>
     * No delimiter is added before or after the list. Null objects or empty strings within the array are represented
     * by empty strings.
     * </p>
     *
     * <pre>
     * StringUtils.join(null, *)               = null
     * StringUtils.join([], *)                 = ""
     * StringUtils.join([null], *)             = ""
     * StringUtils.join([1, 2, 3], ';')  = "1;2;3"
     * StringUtils.join([1, 2, 3], null) = "123"
     * </pre>
     *
     * @param array     the array of values to join together, may be null
     * @param separator the separator character to use
     * @return the joined String, {@code null} if null array input
     */
    public static String join(long[] array, char separator) {
        if (array == null) {
            return null;
        }
        return join(array, separator, 0, array.length);
    }

    /**
     * Joins the elements of the provided array into a single String containing the provided list of elements.
     *
     * <p>
     * No delimiter is added before or after the list. Null objects or empty strings within the array are represented
     * by empty strings.
     * </p>
     *
     * <pre>
     * StringUtils.join(null, *)               = null
     * StringUtils.join([], *)                 = ""
     * StringUtils.join([null], *)             = ""
     * StringUtils.join([1, 2, 3], ';')  = "1;2;3"
     * StringUtils.join([1, 2, 3], null) = "123"
     * </pre>
     *
     * @param array      the array of values to join together, may be null
     * @param delimiter  the separator character to use
     * @param startIndex the first index to start joining from. It is an error to pass in a start index past the end of the
     *                   array
     * @param endIndex   the index to stop joining from (exclusive). It is an error to pass in an end index past the end of
     *                   the array
     * @return the joined String, {@code null} if null array input
     */
    public static String join(long[] array, char delimiter, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        }
        if (endIndex - startIndex <= 0) {
            return EMPTY;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = startIndex; i < endIndex; i++) {
            stringBuilder
                    .append(array[i])
                    .append(delimiter);
        }
        return stringBuilder.substring(0, stringBuilder.length() - 1);
    }

    /**
     * Joins the elements of the provided array into a single String
     * containing the provided list of elements.
     *
     * <p>No delimiter is added before or after the list.
     * Null objects or empty strings within the array are represented by
     * empty strings.</p>
     *
     * <pre>
     * StringUtils.join(null, *)               = null
     * StringUtils.join([], *)                 = ""
     * StringUtils.join([null], *)             = ""
     * StringUtils.join(["a", "b", "c"], ';')  = "a;b;c"
     * StringUtils.join(["a", "b", "c"], null) = "abc"
     * StringUtils.join([null, "", "a"], ';')  = ";;a"
     * </pre>
     *
     * @param array     the array of values to join together, may be null
     * @param delimiter the separator character to use
     * @return the joined String, {@code null} if null array input
     */
    public static String join(Object[] array, char delimiter) {
        if (array == null) {
            return null;
        }
        return join(array, delimiter, 0, array.length);
    }

    /**
     * Joins the elements of the provided array into a single String
     * containing the provided list of elements.
     *
     * <p>No delimiter is added before or after the list.
     * Null objects or empty strings within the array are represented by
     * empty strings.</p>
     *
     * <pre>
     * StringUtils.join(null, *)               = null
     * StringUtils.join([], *)                 = ""
     * StringUtils.join([null], *)             = ""
     * StringUtils.join(["a", "b", "c"], ';')  = "a;b;c"
     * StringUtils.join(["a", "b", "c"], null) = "abc"
     * StringUtils.join([null, "", "a"], ';')  = ";;a"
     * </pre>
     *
     * @param array      the array of values to join together, may be null
     * @param delimiter  the separator character to use
     * @param startIndex the first index to start joining from.  It is
     *                   an error to pass in a start index past the end of the array
     * @param endIndex   the index to stop joining from (exclusive). It is
     *                   an error to pass in an end index past the end of the array
     * @return the joined String, {@code null} if null array input
     */
    public static String join(Object[] array, char delimiter, int startIndex, int endIndex) {
        return join(array, String.valueOf(delimiter), startIndex, endIndex);
    }

    /**
     * Joins the elements of the provided array into a single String
     * containing the provided list of elements.
     *
     * <p>No delimiter is added before or after the list.
     * A {@code null} separator is the same as an empty String ("").
     * Null objects or empty strings within the array are represented by
     * empty strings.</p>
     *
     * <pre>
     * StringUtils.join(null, *)                = null
     * StringUtils.join([], *)                  = ""
     * StringUtils.join([null], *)              = ""
     * StringUtils.join(["a", "b", "c"], "--")  = "a--b--c"
     * StringUtils.join(["a", "b", "c"], null)  = "abc"
     * StringUtils.join(["a", "b", "c"], "")    = "abc"
     * StringUtils.join([null, "", "a"], ',')   = ",,a"
     * </pre>
     *
     * @param array     the array of values to join together, may be null
     * @param delimiter the separator character to use, null treated as ""
     * @return the joined String, {@code null} if null array input
     */
    public static String join(Object[] array, String delimiter) {
        return array != null ? join(array, Objects.toString(delimiter, EMPTY), 0, array.length) : null;
    }

    /**
     * Joins the elements of the provided array into a single String
     * containing the provided list of elements.
     *
     * <p>No delimiter is added before or after the list.
     * A {@code null} separator is the same as an empty String ("").
     * Null objects or empty strings within the array are represented by
     * empty strings.</p>
     *
     * <pre>
     * StringUtils.join(null, *, *, *)                = null
     * StringUtils.join([], *, *, *)                  = ""
     * StringUtils.join([null], *, *, *)              = ""
     * StringUtils.join(["a", "b", "c"], "--", 0, 3)  = "a--b--c"
     * StringUtils.join(["a", "b", "c"], "--", 1, 3)  = "b--c"
     * StringUtils.join(["a", "b", "c"], "--", 2, 3)  = "c"
     * StringUtils.join(["a", "b", "c"], "--", 2, 2)  = ""
     * StringUtils.join(["a", "b", "c"], null, 0, 3)  = "abc"
     * StringUtils.join(["a", "b", "c"], "", 0, 3)    = "abc"
     * StringUtils.join([null, "", "a"], ',', 0, 3)   = ",,a"
     * </pre>
     *
     * @param array      the array of values to join together, may be null
     * @param delimiter  the separator character to use, null treated as ""
     * @param startIndex the first index to start joining from.
     * @param endIndex   the index to stop joining from (exclusive).
     * @return the joined String, {@code null} if null array input; or the empty string
     * if {@code endIndex - startIndex <= 0}. The number of joined entries is given by
     * {@code endIndex - startIndex}
     * @throws ArrayIndexOutOfBoundsException ife<br>
     *                                        {@code startIndex < 0} or <br>
     *                                        {@code startIndex >= array.length()} or <br>
     *                                        {@code endIndex < 0} or <br>
     *                                        {@code endIndex > array.length()}
     */
//    public static String join(Object[] array, String delimiter, int startIndex, int endIndex) {
//        return array != null ? Streams.of(array).skip(startIndex).limit(Math.max(0, endIndex - startIndex))
//                .collect(LangCollectors.joining(delimiter, EMPTY, EMPTY, ObjectUtils::toString)) : null;
//    }

    /**
     * Joins the elements of the provided array into a single String containing the provided list of elements.
     *
     * <p>
     * No delimiter is added before or after the list. Null objects or empty strings within the array are represented
     * by empty strings.
     * </p>
     *
     * <pre>
     * StringUtils.join(null, *)               = null
     * StringUtils.join([], *)                 = ""
     * StringUtils.join([null], *)             = ""
     * StringUtils.join([1, 2, 3], ';')  = "1;2;3"
     * StringUtils.join([1, 2, 3], null) = "123"
     * </pre>
     *
     * @param array     the array of values to join together, may be null
     * @param delimiter the separator character to use
     * @return the joined String, {@code null} if null array input
     */
    public static String join(short[] array, char delimiter) {
        if (array == null) {
            return null;
        }
        return join(array, delimiter, 0, array.length);
    }

    /**
     * Joins the elements of the provided array into a single String containing the provided list of elements.
     *
     * <p>
     * No delimiter is added before or after the list. Null objects or empty strings within the array are represented
     * by empty strings.
     * </p>
     *
     * <pre>
     * StringUtils.join(null, *)               = null
     * StringUtils.join([], *)                 = ""
     * StringUtils.join([null], *)             = ""
     * StringUtils.join([1, 2, 3], ';')  = "1;2;3"
     * StringUtils.join([1, 2, 3], null) = "123"
     * </pre>
     *
     * @param array      the array of values to join together, may be null
     * @param delimiter  the separator character to use
     * @param startIndex the first index to start joining from. It is an error to pass in a start index past the end of the
     *                   array
     * @param endIndex   the index to stop joining from (exclusive). It is an error to pass in an end index past the end of
     *                   the array
     * @return the joined String, {@code null} if null array input
     */
    public static String join(short[] array, char delimiter, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        }
        if (endIndex - startIndex <= 0) {
            return EMPTY;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = startIndex; i < endIndex; i++) {
            stringBuilder
                    .append(array[i])
                    .append(delimiter);
        }
        return stringBuilder.substring(0, stringBuilder.length() - 1);
    }

    /**
     * Joins the elements of the provided array into a single String
     * containing the provided list of elements.
     *
     * <p>No separator is added to the joined String.
     * Null objects or empty strings within the array are represented by
     * empty strings.</p>
     *
     * <pre>
     * StringUtils.join(null)            = null
     * StringUtils.join([])              = ""
     * StringUtils.join([null])          = ""
     * StringUtils.join(["a", "b", "c"]) = "abc"
     * StringUtils.join([null, "", "a"]) = "a"
     * </pre>
     *
     * @param <T>      the specific type of values to join together
     * @param elements the values to join together, may be null
     * @return the joined String, {@code null} if null array input
     */
    @SafeVarargs
    public static <T> String join(T... elements) {
        return join(elements, null);
    }

    /**
     * Joins the elements of the provided varargs into a
     * single String containing the provided elements.
     *
     * <p>No delimiter is added before or after the list.
     * {@code null} elements and separator are treated as empty Strings ("").</p>
     *
     * <pre>
     * StringUtils.joinWith(",", {"a", "b"})        = "a,b"
     * StringUtils.joinWith(",", {"a", "b",""})     = "a,b,"
     * StringUtils.joinWith(",", {"a", null, "b"})  = "a,,b"
     * StringUtils.joinWith(null, {"a", "b"})       = "ab"
     * </pre>
     *
     * @param delimiter the separator character to use, null treated as ""
     * @param array     the varargs providing the values to join together. {@code null} elements are treated as ""
     * @return the joined String.
     * @throws IllegalArgumentException if a null varargs is provided
     */
    public static String joinWith(String delimiter, Object... array) {
        if (array == null) {
            throw new IllegalArgumentException("Object varargs must not be null");
        }
        return join(array, delimiter);
    }

    /**
     * Returns the index within {@code seq} of the last occurrence of
     * the specified character. For values of {@code searchChar} in the
     * range from 0 to 0xFFFF (inclusive), the index (in Unicode code
     * units) returned is the largest value <em>k</em> such that:
     * <blockquote><pre>
     * this.charAt(<em>k</em>) == searchChar
     * </pre></blockquote>
     * is true. For other values of {@code searchChar}, it is the
     * largest value <em>k</em> such that:
     * <blockquote><pre>
     * this.codePointAt(<em>k</em>) == searchChar
     * </pre></blockquote>
     * is true.  In either case, if no such character occurs in this
     * string, then {@code -1} is returned. Furthermore, a {@code null} or empty ("")
     * {@link CharSequence} will return {@code -1}. The
     * {@code seq} {@link CharSequence} object is searched backwards
     * starting at the last character.
     *
     * <pre>
     * StringUtils.lastIndexOf(null, *)         = -1
     * StringUtils.lastIndexOf("", *)           = -1
     * StringUtils.lastIndexOf("aabaabaa", 'a') = 7
     * StringUtils.lastIndexOf("aabaabaa", 'b') = 5
     * </pre>
     *
     * @param seq        the {@link CharSequence} to check, may be null
     * @param searchChar the character to find
     * @return the last index of the search character,
     * -1 if no match or {@code null} string input
     */
//    public static int lastIndexOf(CharSequence seq, int searchChar) {
//        if (isEmpty(seq)) {
//            return INDEX_NOT_FOUND;
//        }
//        return CharSequenceUtils.lastIndexOf(seq, searchChar, seq.length());
//    }

    /**
     * Returns the index within {@code seq} of the last occurrence of
     * the specified character, searching backward starting at the
     * specified index. For values of {@code searchChar} in the range
     * from 0 to 0xFFFF (inclusive), the index returned is the largest
     * value <em>k</em> such that:
     * <blockquote><pre>
     * (this.charAt(<em>k</em>) == searchChar) &amp;&amp; (<em>k</em> &lt;= startPos)
     * </pre></blockquote>
     * is true. For other values of {@code searchChar}, it is the
     * largest value <em>k</em> such that:
     * <blockquote><pre>
     * (this.codePointAt(<em>k</em>) == searchChar) &amp;&amp; (<em>k</em> &lt;= startPos)
     * </pre></blockquote>
     * is true. In either case, if no such character occurs in {@code seq}
     * at or before position {@code startPos}, then
     * {@code -1} is returned. Furthermore, a {@code null} or empty ("")
     * {@link CharSequence} will return {@code -1}. A start position greater
     * than the string length searches the whole string.
     * The search starts at the {@code startPos} and works backwards;
     * matches starting after the start position are ignored.
     *
     * <p>All indices are specified in {@code char} values
     * (Unicode code units).
     *
     * <pre>
     * StringUtils.lastIndexOf(null, *, *)          = -1
     * StringUtils.lastIndexOf("", *,  *)           = -1
     * StringUtils.lastIndexOf("aabaabaa", 'b', 8)  = 5
     * StringUtils.lastIndexOf("aabaabaa", 'b', 4)  = 2
     * StringUtils.lastIndexOf("aabaabaa", 'b', 0)  = -1
     * StringUtils.lastIndexOf("aabaabaa", 'b', 9)  = 5
     * StringUtils.lastIndexOf("aabaabaa", 'b', -1) = -1
     * StringUtils.lastIndexOf("aabaabaa", 'a', 0)  = 0
     * </pre>
     *
     * @param seq        the CharSequence to check, may be null
     * @param searchChar the character to find
     * @param startPos   the start position
     * @return the last index of the search character (always &le; startPos),
     * -1 if no match or {@code null} string input
     */
//    public static int lastIndexOf(CharSequence seq, int searchChar, int startPos) {
//        if (isEmpty(seq)) {
//            return INDEX_NOT_FOUND;
//        }
//        return CharSequenceUtils.lastIndexOf(seq, searchChar, startPos);
//    }

    /**
     * Find the latest index of any substring in a set of potential substrings.
     *
     * <p>A {@code null} CharSequence will return {@code -1}.
     * A {@code null} search array will return {@code -1}.
     * A {@code null} or zero length search array entry will be ignored,
     * but a search array containing "" will return the length of {@code str}
     * if {@code str} is not null. This method uses {@link String#indexOf(String)} if possible</p>
     *
     * <pre>
     * StringUtils.lastIndexOfAny(null, *)                    = -1
     * StringUtils.lastIndexOfAny(*, null)                    = -1
     * StringUtils.lastIndexOfAny(*, [])                      = -1
     * StringUtils.lastIndexOfAny(*, [null])                  = -1
     * StringUtils.lastIndexOfAny("zzabyycdxx", ["ab", "cd"]) = 6
     * StringUtils.lastIndexOfAny("zzabyycdxx", ["cd", "ab"]) = 6
     * StringUtils.lastIndexOfAny("zzabyycdxx", ["mn", "op"]) = -1
     * StringUtils.lastIndexOfAny("zzabyycdxx", ["mn", "op"]) = -1
     * StringUtils.lastIndexOfAny("zzabyycdxx", ["mn", ""])   = 10
     * </pre>
     *
     * @param str        the CharSequence to check, may be null
     * @param searchStrs the CharSequences to search for, may be null
     * @return the last index of any of the CharSequences, -1 if no match
     */
//    public static int lastIndexOfAny(CharSequence str, CharSequence... searchStrs) {
//        if (str == null || searchStrs == null) {
//            return INDEX_NOT_FOUND;
//        }
//        int ret = INDEX_NOT_FOUND;
//        int tmp;
//        for (CharSequence search : searchStrs) {
//            if (search == null) {
//                continue;
//            }
//            tmp = CharSequenceUtils.lastIndexOf(str, search, str.length());
//            if (tmp > ret) {
//                ret = tmp;
//            }
//        }
//        return ret;
//    }

    /**
     * Finds the n-th last index within a String, handling {@code null}.
     * This method uses {@link String#lastIndexOf(String)}.
     *
     * <p>A {@code null} String will return {@code -1}.</p>
     *
     * <pre>
     * StringUtils.lastOrdinalIndexOf(null, *, *)          = -1
     * StringUtils.lastOrdinalIndexOf(*, null, *)          = -1
     * StringUtils.lastOrdinalIndexOf("", "", *)           = 0
     * StringUtils.lastOrdinalIndexOf("aabaabaa", "a", 1)  = 7
     * StringUtils.lastOrdinalIndexOf("aabaabaa", "a", 2)  = 6
     * StringUtils.lastOrdinalIndexOf("aabaabaa", "b", 1)  = 5
     * StringUtils.lastOrdinalIndexOf("aabaabaa", "b", 2)  = 2
     * StringUtils.lastOrdinalIndexOf("aabaabaa", "ab", 1) = 4
     * StringUtils.lastOrdinalIndexOf("aabaabaa", "ab", 2) = 1
     * StringUtils.lastOrdinalIndexOf("aabaabaa", "", 1)   = 8
     * StringUtils.lastOrdinalIndexOf("aabaabaa", "", 2)   = 8
     * </pre>
     *
     * <p>Note that 'tail(CharSequence str, int n)' may be implemented as: </p>
     *
     * <pre>
     *   str.substring(lastOrdinalIndexOf(str, "\n", n) + 1)
     * </pre>
     *
     * @param str       the CharSequence to check, may be null
     * @param searchStr the CharSequence to find, may be null
     * @param ordinal   the n-th last {@code searchStr} to find
     * @return the n-th last index of the search CharSequence,
     * {@code -1} ({@code INDEX_NOT_FOUND}) if no match or {@code null} string input
     */
//    public static int lastOrdinalIndexOf(CharSequence str, CharSequence searchStr, int ordinal) {
//        return ordinalIndexOf(str, searchStr, ordinal, true);
//    }

    /**
     * Gets the leftmost {@code len} characters of a String.
     *
     * <p>If {@code len} characters are not available, or the
     * String is {@code null}, the String will be returned without
     * an exception. An empty String is returned if len is negative.</p>
     *
     * <pre>
     * StringUtils.left(null, *)    = null
     * StringUtils.left(*, -ve)     = ""
     * StringUtils.left("", *)      = ""
     * StringUtils.left("abc", 0)   = ""
     * StringUtils.left("abc", 2)   = "ab"
     * StringUtils.left("abc", 4)   = "abc"
     * </pre>
     *
     * @param str the String to get the leftmost characters from, may be null
     * @param len the length of the required String
     * @return the leftmost characters, {@code null} if null String input
     */
    public static String left(String str, int len) {
        if (str == null) {
            return null;
        }
        if (len < 0) {
            return EMPTY;
        }
        if (str.length() <= len) {
            return str;
        }
        return str.substring(0, len);
    }

    /**
     * Gets a CharSequence length or {@code 0} if the CharSequence is
     * {@code null}.
     *
     * @param cs a CharSequence or {@code null}
     * @return CharSequence length or {@code 0} if the CharSequence is
     * {@code null}.
     */
    public static int length(CharSequence cs) {
        return cs == null ? 0 : cs.length();
    }

    /**
     * Gets {@code len} characters from the middle of a String.
     *
     * <p>If {@code len} characters are not available, the remainder
     * of the String will be returned without an exception. If the
     * String is {@code null}, {@code null} will be returned.
     * An empty String is returned if len is negative or exceeds the
     * length of {@code str}.</p>
     *
     * <pre>
     * StringUtils.mid(null, *, *)    = null
     * StringUtils.mid(*, *, -ve)     = ""
     * StringUtils.mid("", 0, *)      = ""
     * StringUtils.mid("abc", 0, 2)   = "ab"
     * StringUtils.mid("abc", 0, 4)   = "abc"
     * StringUtils.mid("abc", 2, 4)   = "c"
     * StringUtils.mid("abc", 4, 2)   = ""
     * StringUtils.mid("abc", -2, 2)  = "ab"
     * </pre>
     *
     * @param str the String to get the characters from, may be null
     * @param pos the position to start from, negative treated as zero
     * @param len the length of the required String
     * @return the middle characters, {@code null} if null String input
     */
    public static String mid(String str, int pos, int len) {
        if (str == null) {
            return null;
        }
        if (len < 0 || pos > str.length()) {
            return EMPTY;
        }
        if (pos < 0) {
            pos = 0;
        }
        if (str.length() <= pos + len) {
            return str.substring(pos);
        }
        return str.substring(pos, pos + len);
    }

    /**
     * Similar to <a
     * href="https://www.w3.org/TR/xpath/#function-normalize-space">https://www.w3.org/TR/xpath/#function-normalize
     * -space</a>
     *
     * <p>
     * The function returns the argument string with whitespace normalized by using
     * {@code {@link #trim(String)}} to remove leading and trailing whitespace
     * and then replacing sequences of whitespace characters by a single space.
     * </p>
     * In XML Whitespace characters are the same as those allowed by the <a
     * href="https://www.w3.org/TR/REC-xml/#NT-S">S</a> production, which is S ::= (#x20 | #x9 | #xD | #xA)+
     * <p>
     * Java's regexp pattern \s defines whitespace as [ \t\n\x0B\f\r]
     *
     * <p>For reference:</p>
     * <ul>
     * <li>\x0B = vertical tab</li>
     * <li>\f = #xC = form feed</li>
     * <li>#x20 = space</li>
     * <li>#x9 = \t</li>
     * <li>#xA = \n</li>
     * <li>#xD = \r</li>
     * </ul>
     *
     * <p>
     * The difference is that Java's whitespace includes vertical tab and form feed, which this functional will also
     * normalize. Additionally {@code {@link #trim(String)}} removes control characters (char &lt;= 32) from both
     * ends of this String.
     * </p>
     *
     * @param str the source String to normalize whitespaces from, may be null
     * @return the modified string with whitespace normalized, {@code null} if null String input
     * @see Pattern
     * @see #trim(String)
     * @see <a
     * href="https://www.w3.org/TR/xpath/#function-normalize-space">https://www.w3.org/TR/xpath/#function-normalize-space</a>
     */
    public static String normalizeSpace(String str) {
        // LANG-1020: Improved performance significantly by normalizing manually instead of using regex
        // See https://github.com/librucha/commons-lang-normalizespaces-benchmark for performance test
        if (isEmpty(str)) {
            return str;
        }
        int size = str.length();
        char[] newChars = new char[size];
        int count = 0;
        int whitespacesCount = 0;
        boolean startWhitespaces = true;
        for (int i = 0; i < size; i++) {
            char actualChar = str.charAt(i);
            boolean isWhitespace = Character.isWhitespace(actualChar);
            if (isWhitespace) {
                if (whitespacesCount == 0 && !startWhitespaces) {
                    newChars[count++] = SPACE.charAt(0);
                }
                whitespacesCount++;
            } else {
                startWhitespaces = false;
                newChars[count++] = actualChar == 160 ? 32 : actualChar;
                whitespacesCount = 0;
            }
        }
        if (startWhitespaces) {
            return EMPTY;
        }
        return new String(newChars, 0, count - (whitespacesCount > 0 ? 1 : 0)).trim();
    }

    /**
     * Finds the n-th index within a CharSequence, handling {@code null}.
     * This method uses {@link String#indexOf(String)} if possible.
     * <p><strong>Note:</strong> The code starts looking for a match at the start of the target,
     * incrementing the starting index by one after each successful match
     * (unless {@code searchStr} is an empty string in which case the position
     * is never incremented and {@code 0} is returned immediately).
     * This means that matches may overlap.</p>
     * <p>A {@code null} CharSequence will return {@code -1}.</p>
     *
     * <pre>
     * StringUtils.ordinalIndexOf(null, *, *)          = -1
     * StringUtils.ordinalIndexOf(*, null, *)          = -1
     * StringUtils.ordinalIndexOf("", "", *)           = 0
     * StringUtils.ordinalIndexOf("aabaabaa", "a", 1)  = 0
     * StringUtils.ordinalIndexOf("aabaabaa", "a", 2)  = 1
     * StringUtils.ordinalIndexOf("aabaabaa", "b", 1)  = 2
     * StringUtils.ordinalIndexOf("aabaabaa", "b", 2)  = 5
     * StringUtils.ordinalIndexOf("aabaabaa", "ab", 1) = 1
     * StringUtils.ordinalIndexOf("aabaabaa", "ab", 2) = 4
     * StringUtils.ordinalIndexOf("aabaabaa", "", 1)   = 0
     * StringUtils.ordinalIndexOf("aabaabaa", "", 2)   = 0
     * </pre>
     *
     * <p>Matches may overlap:</p>
     * <pre>
     * StringUtils.ordinalIndexOf("ababab", "aba", 1)   = 0
     * StringUtils.ordinalIndexOf("ababab", "aba", 2)   = 2
     * StringUtils.ordinalIndexOf("ababab", "aba", 3)   = -1
     *
     * StringUtils.ordinalIndexOf("abababab", "abab", 1) = 0
     * StringUtils.ordinalIndexOf("abababab", "abab", 2) = 2
     * StringUtils.ordinalIndexOf("abababab", "abab", 3) = 4
     * StringUtils.ordinalIndexOf("abababab", "abab", 4) = -1
     * </pre>
     *
     * <p>Note that 'head(CharSequence str, int n)' may be implemented as: </p>
     *
     * <pre>
     *   str.substring(0, lastOrdinalIndexOf(str, "\n", n))
     * </pre>
     *
     * @param str       the CharSequence to check, may be null
     * @param searchStr the CharSequence to find, may be null
     * @param ordinal   the n-th {@code searchStr} to find
     * @return the n-th index of the search CharSequence,
     * {@code -1} ({@code INDEX_NOT_FOUND}) if no match or {@code null} string input
     */
//    public static int ordinalIndexOf(CharSequence str, CharSequence searchStr, int ordinal) {
//        return ordinalIndexOf(str, searchStr, ordinal, false);
//    }

    /**
     * Finds the n-th index within a String, handling {@code null}.
     * This method uses {@link String#indexOf(String)} if possible.
     * <p>Note that matches may overlap<p>
     *
     * <p>A {@code null} CharSequence will return {@code -1}.</p>
     *
     * @param str       the CharSequence to check, may be null
     * @param searchStr the CharSequence to find, may be null
     * @param ordinal   the n-th {@code searchStr} to find, overlapping matches are allowed.
     * @param lastIndex true if lastOrdinalIndexOf() otherwise false if ordinalIndexOf()
     * @return the n-th index of the search CharSequence,
     * {@code -1} ({@code INDEX_NOT_FOUND}) if no match or {@code null} string input
     */
    // Shared code between ordinalIndexOf(String, String, int) and lastOrdinalIndexOf(String, String, int)
//    private static int ordinalIndexOf(CharSequence str, CharSequence searchStr, int ordinal, boolean lastIndex) {
//        if (str == null || searchStr == null || ordinal <= 0) {
//            return INDEX_NOT_FOUND;
//        }
//        if (searchStr.length() == 0) {
//            return lastIndex ? str.length() : 0;
//        }
//        int found = 0;
//        // set the initial index beyond the end of the string
//        // this is to allow for the initial index decrement/increment
//        int index = lastIndex ? str.length() : INDEX_NOT_FOUND;
//        do {
//            if (lastIndex) {
//                index = CharSequenceUtils.lastIndexOf(str, searchStr, index - 1); // step backwards through string
//            } else {
//                index = CharSequenceUtils.indexOf(str, searchStr, index + 1); // step forwards through string
//            }
//            if (index < 0) {
//                return index;
//            }
//            found++;
//        } while (found < ordinal);
//        return index;
//    }

    /**
     * Overlays part of a String with another String.
     *
     * <p>A {@code null} string input returns {@code null}.
     * A negative index is treated as zero.
     * An index greater than the string length is treated as the string length.
     * The start index is always the smaller of the two indices.</p>
     *
     * <pre>
     * StringUtils.overlay(null, *, *, *)            = null
     * StringUtils.overlay("", "abc", 0, 0)          = "abc"
     * StringUtils.overlay("abcdef", null, 2, 4)     = "abef"
     * StringUtils.overlay("abcdef", "", 2, 4)       = "abef"
     * StringUtils.overlay("abcdef", "", 4, 2)       = "abef"
     * StringUtils.overlay("abcdef", "zzzz", 2, 4)   = "abzzzzef"
     * StringUtils.overlay("abcdef", "zzzz", 4, 2)   = "abzzzzef"
     * StringUtils.overlay("abcdef", "zzzz", -1, 4)  = "zzzzef"
     * StringUtils.overlay("abcdef", "zzzz", 2, 8)   = "abzzzz"
     * StringUtils.overlay("abcdef", "zzzz", -2, -3) = "zzzzabcdef"
     * StringUtils.overlay("abcdef", "zzzz", 8, 10)  = "abcdefzzzz"
     * </pre>
     *
     * @param str     the String to do overlaying in, may be null
     * @param overlay the String to overlay, may be null
     * @param start   the position to start overlaying at
     * @param end     the position to stop overlaying before
     * @return overlayed String, {@code null} if null String input
     */
    public static String overlay(String str, String overlay, int start, int end) {
        if (str == null) {
            return null;
        }
        if (overlay == null) {
            overlay = EMPTY;
        }
        int len = str.length();
        if (start < 0) {
            start = 0;
        }
        if (start > len) {
            start = len;
        }
        if (end < 0) {
            end = 0;
        }
        if (end > len) {
            end = len;
        }
        if (start > end) {
            int temp = start;
            start = end;
            end = temp;
        }
        return str.substring(0, start) +
                overlay +
                str.substring(end);
    }

    /**
     * Removes all occurrences of a character from within the source string.
     *
     * <p>A {@code null} source string will return {@code null}.
     * An empty ("") source string will return the empty string.</p>
     *
     * <pre>
     * StringUtils.remove(null, *)       = null
     * StringUtils.remove("", *)         = ""
     * StringUtils.remove("queued", 'u') = "qeed"
     * StringUtils.remove("queued", 'z') = "queued"
     * </pre>
     *
     * @param str    the source String to search, may be null
     * @param remove the char to search for and remove, may be null
     * @return the substring with the char removed if found,
     * {@code null} if null String input
     */
    public static String remove(String str, char remove) {
        if (isEmpty(str) || str.indexOf(remove) == INDEX_NOT_FOUND) {
            return str;
        }
        char[] chars = str.toCharArray();
        int pos = 0;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] != remove) {
                chars[pos++] = chars[i];
            }
        }
        return new String(chars, 0, pos);
    }

    /**
     * Removes a char only if it is at the beginning of a source string,
     * otherwise returns the source string.
     *
     * <p>A {@code null} source string will return {@code null}.
     * An empty ("") source string will return the empty string.
     * A {@code null} search char will return the source string.</p>
     *
     * <pre>
     * StringUtils.removeStart(null, *)      = null
     * StringUtils.removeStart("", *)        = ""
     * StringUtils.removeStart(*, null)      = *
     * StringUtils.removeStart("/path", '/') = "path"
     * StringUtils.removeStart("path", '/')  = "path"
     * StringUtils.removeStart("path", 0)    = "path"
     * </pre>
     *
     * @param str    the source String to search, may be null.
     * @param remove the char to search for and remove.
     * @return the substring with the char removed if found,
     * {@code null} if null String input.
     */
    public static String removeStart(String str, char remove) {
        if (isEmpty(str)) {
            return str;
        }
        return str.charAt(0) == remove ? str.substring(1) : str;
    }

    /**
     * Replaces all occurrences of a character in a String with another.
     * This is a null-safe version of {@link String#replace(char, char)}.
     *
     * <p>A {@code null} string input returns {@code null}.
     * An empty ("") string input returns an empty string.</p>
     *
     * <pre>
     * StringUtils.replaceChars(null, *, *)        = null
     * StringUtils.replaceChars("", *, *)          = ""
     * StringUtils.replaceChars("abcba", 'b', 'y') = "aycya"
     * StringUtils.replaceChars("abcba", 'z', 'y') = "abcba"
     * </pre>
     *
     * @param str         String to replace characters in, may be null
     * @param searchChar  the character to search for, may be null
     * @param replaceChar the character to replace, may be null
     * @return modified String, {@code null} if null string input
     */
    public static String replaceChars(String str, char searchChar, char replaceChar) {
        if (str == null) {
            return null;
        }
        return str.replace(searchChar, replaceChar);
    }

    /**
     * Replaces multiple characters in a String in one go.
     * This method can also be used to delete characters.
     *
     * <p>For example:<br>
     * {@code replaceChars(&quot;hello&quot;, &quot;ho&quot;, &quot;jy&quot;) = jelly}.</p>
     *
     * <p>A {@code null} string input returns {@code null}.
     * An empty ("") string input returns an empty string.
     * A null or empty set of search characters returns the input string.</p>
     *
     * <p>The length of the search characters should normally equal the length
     * of the replace characters.
     * If the search characters is longer, then the extra search characters
     * are deleted.
     * If the search characters is shorter, then the extra replace characters
     * are ignored.</p>
     *
     * <pre>
     * StringUtils.replaceChars(null, *, *)           = null
     * StringUtils.replaceChars("", *, *)             = ""
     * StringUtils.replaceChars("abc", null, *)       = "abc"
     * StringUtils.replaceChars("abc", "", *)         = "abc"
     * StringUtils.replaceChars("abc", "b", null)     = "ac"
     * StringUtils.replaceChars("abc", "b", "")       = "ac"
     * StringUtils.replaceChars("abcba", "bc", "yz")  = "ayzya"
     * StringUtils.replaceChars("abcba", "bc", "y")   = "ayya"
     * StringUtils.replaceChars("abcba", "bc", "yzx") = "ayzya"
     * </pre>
     *
     * @param str          String to replace characters in, may be null
     * @param searchChars  a set of characters to search for, may be null
     * @param replaceChars a set of characters to replace, may be null
     * @return modified String, {@code null} if null string input
     */
    public static String replaceChars(String str, String searchChars, String replaceChars) {
        if (isEmpty(str) || isEmpty(searchChars)) {
            return str;
        }
        replaceChars = Objects.toString(replaceChars, EMPTY);
        boolean modified = false;
        int replaceCharsLength = replaceChars.length();
        int strLength = str.length();
        StringBuilder buf = new StringBuilder(strLength);
        for (int i = 0; i < strLength; i++) {
            char ch = str.charAt(i);
            int index = searchChars.indexOf(ch);
            if (index >= 0) {
                modified = true;
                if (index < replaceCharsLength) {
                    buf.append(replaceChars.charAt(index));
                }
            } else {
                buf.append(ch);
            }
        }
        if (modified) {
            return buf.toString();
        }
        return str;
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
     * @param str the String to reverse, may be null
     * @return the reversed String, {@code null} if null String input
     */
    public static String reverse(String str) {
        if (str == null) {
            return null;
        }
        return new StringBuilder(str).reverse().toString();
    }

    /**
     * Gets the rightmost {@code len} characters of a String.
     *
     * <p>If {@code len} characters are not available, or the String
     * is {@code null}, the String will be returned without an
     * an exception. An empty String is returned if len is negative.</p>
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
     * Rotate (circular shift) a String of {@code shift} characters.
     * <ul>
     *  <li>If {@code shift > 0}, right circular shift (ex : ABCDEF =&gt; FABCDE)</li>
     *  <li>If {@code shift < 0}, left circular shift (ex : ABCDEF =&gt; BCDEFA)</li>
     * </ul>
     *
     * <pre>
     * StringUtils.rotate(null, *)        = null
     * StringUtils.rotate("", *)          = ""
     * StringUtils.rotate("abcdefg", 0)   = "abcdefg"
     * StringUtils.rotate("abcdefg", 2)   = "fgabcde"
     * StringUtils.rotate("abcdefg", -2)  = "cdefgab"
     * StringUtils.rotate("abcdefg", 7)   = "abcdefg"
     * StringUtils.rotate("abcdefg", -7)  = "abcdefg"
     * StringUtils.rotate("abcdefg", 9)   = "fgabcde"
     * StringUtils.rotate("abcdefg", -9)  = "cdefgab"
     * </pre>
     *
     * @param str   the String to rotate, may be null
     * @param shift number of time to shift (positive : right shift, negative : left shift)
     * @return the rotated String,
     * or the original String if {@code shift == 0},
     * or {@code null} if null String input
     */
    public static String rotate(String str, int shift) {
        if (str == null) {
            return null;
        }

        int strLen = str.length();
        if (shift == 0 || strLen == 0 || shift % strLen == 0) {
            return str;
        }

        StringBuilder builder = new StringBuilder(strLen);
        int offset = -(shift % strLen);
        builder.append(substring(str, offset));
        builder.append(substring(str, 0, offset));
        return builder.toString();
    }

    /**
     * Splits the provided text into an array, using whitespace as the
     * separator.
     * Whitespace is defined by {@link Character#isWhitespace(char)}.
     *
     * <p>The separator is not included in the returned String array.
     * Adjacent separators are treated as one separator.
     * For more control over the split use the StrTokenizer class.</p>
     *
     * <p>A {@code null} input String returns {@code null}.</p>
     *
     * <pre>
     * StringUtils.split(null)       = null
     * StringUtils.split("")         = []
     * StringUtils.split("abc def")  = ["abc", "def"]
     * StringUtils.split("abc  def") = ["abc", "def"]
     * StringUtils.split(" abc ")    = ["abc"]
     * </pre>
     *
     * @param str the String to parse, may be null
     * @return an array of parsed Strings, {@code null} if null String input
     */
    public static String[] split(String str) {
        return split(str, null, -1);
    }

    /**
     * Splits the provided text into an array, separator specified.
     * This is an alternative to using StringTokenizer.
     *
     * <p>The separator is not included in the returned String array.
     * Adjacent separators are treated as one separator.
     * For more control over the split use the StrTokenizer class.</p>
     *
     * <p>A {@code null} input String returns {@code null}.</p>
     *
     * <pre>
     * StringUtils.split(null, *)         = null
     * StringUtils.split("", *)           = []
     * StringUtils.split("a.b.c", '.')    = ["a", "b", "c"]
     * StringUtils.split("a..b.c", '.')   = ["a", "b", "c"]
     * StringUtils.split("a:b:c", '.')    = ["a:b:c"]
     * StringUtils.split("a b c", ' ')    = ["a", "b", "c"]
     * </pre>
     *
     * @param str           the String to parse, may be null
     * @param separatorChar the character used as the delimiter
     * @return an array of parsed Strings, {@code null} if null String input
     */
    public static String[] split(String str, char separatorChar) {
        return splitWorker(str, separatorChar, false);
    }

    /**
     * Splits the provided text into an array, separators specified.
     * This is an alternative to using StringTokenizer.
     *
     * <p>The separator is not included in the returned String array.
     * Adjacent separators are treated as one separator.
     * For more control over the split use the StrTokenizer class.</p>
     *
     * <p>A {@code null} input String returns {@code null}.
     * A {@code null} separatorChars splits on whitespace.</p>
     *
     * <pre>
     * StringUtils.split(null, *)         = null
     * StringUtils.split("", *)           = []
     * StringUtils.split("abc def", null) = ["abc", "def"]
     * StringUtils.split("abc def", " ")  = ["abc", "def"]
     * StringUtils.split("abc  def", " ") = ["abc", "def"]
     * StringUtils.split("ab:cd:ef", ":") = ["ab", "cd", "ef"]
     * </pre>
     *
     * @param str            the String to parse, may be null
     * @param separatorChars the characters used as the delimiters,
     *                       {@code null} splits on whitespace
     * @return an array of parsed Strings, {@code null} if null String input
     */
    public static String[] split(String str, String separatorChars) {
        return splitWorker(str, separatorChars, -1, false);
    }

    /**
     * Splits the provided text into an array with a maximum length,
     * separators specified.
     *
     * <p>The separator is not included in the returned String array.
     * Adjacent separators are treated as one separator.</p>
     *
     * <p>A {@code null} input String returns {@code null}.
     * A {@code null} separatorChars splits on whitespace.</p>
     *
     * <p>If more than {@code max} delimited substrings are found, the last
     * returned string includes all characters after the first {@code max - 1}
     * returned strings (including separator characters).</p>
     *
     * <pre>
     * StringUtils.split(null, *, *)            = null
     * StringUtils.split("", *, *)              = []
     * StringUtils.split("ab cd ef", null, 0)   = ["ab", "cd", "ef"]
     * StringUtils.split("ab   cd ef", null, 0) = ["ab", "cd", "ef"]
     * StringUtils.split("ab:cd:ef", ":", 0)    = ["ab", "cd", "ef"]
     * StringUtils.split("ab:cd:ef", ":", 2)    = ["ab", "cd:ef"]
     * </pre>
     *
     * @param str            the String to parse, may be null
     * @param separatorChars the characters used as the delimiters,
     *                       {@code null} splits on whitespace
     * @param max            the maximum number of elements to include in the
     *                       array. A zero or negative value implies no limit
     * @return an array of parsed Strings, {@code null} if null String input
     */
    public static String[] split(String str, String separatorChars, int max) {
        return splitWorker(str, separatorChars, max, false);
    }

    /**
     * Splits a String by Character type as returned by
     * {@code java.lang.Character.getType(char)}. Groups of contiguous
     * characters of the same type are returned as complete tokens.
     * <pre>
     * StringUtils.splitByCharacterType(null)         = null
     * StringUtils.splitByCharacterType("")           = []
     * StringUtils.splitByCharacterType("ab de fg")   = ["ab", " ", "de", " ", "fg"]
     * StringUtils.splitByCharacterType("ab   de fg") = ["ab", "   ", "de", " ", "fg"]
     * StringUtils.splitByCharacterType("ab:cd:ef")   = ["ab", ":", "cd", ":", "ef"]
     * StringUtils.splitByCharacterType("number5")    = ["number", "5"]
     * StringUtils.splitByCharacterType("fooBar")     = ["foo", "B", "ar"]
     * StringUtils.splitByCharacterType("foo200Bar")  = ["foo", "200", "B", "ar"]
     * StringUtils.splitByCharacterType("ASFRules")   = ["ASFR", "ules"]
     * </pre>
     *
     * @param str the String to split, may be {@code null}
     * @return an array of parsed Strings, {@code null} if null String input
     */
    public static String[] splitByCharacterType(String str) {
        return splitByCharacterType(str, false);
    }

    /**
     * <p>Splits a String by Character type as returned by
     * {@code java.lang.Character.getType(char)}. Groups of contiguous
     * characters of the same type are returned as complete tokens, with the
     * following exception: if {@code camelCase} is {@code true},
     * the character of type {@code Character.UPPERCASE_LETTER}, if any,
     * immediately preceding a token of type {@code Character.LOWERCASE_LETTER}
     * will belong to the following token rather than to the preceding, if any,
     * {@code Character.UPPERCASE_LETTER} token.
     *
     * @param str       the String to split, may be {@code null}
     * @param camelCase whether to use so-called "camel-case" for letter types
     * @return an array of parsed Strings, {@code null} if null String input
     */
    private static String[] splitByCharacterType(String str, boolean camelCase) {
        if (str == null) {
            return null;
        }
        if (str.isEmpty()) {
            return new String[]{};
        }
        char[] c = str.toCharArray();
        List<String> list = new ArrayList<>();
        int tokenStart = 0;
        int currentType = Character.getType(c[tokenStart]);
        for (int pos = tokenStart + 1; pos < c.length; pos++) {
            int type = Character.getType(c[pos]);
            if (type == currentType) {
                continue;
            }
            if (camelCase && type == Character.LOWERCASE_LETTER && currentType == Character.UPPERCASE_LETTER) {
                int newTokenStart = pos - 1;
                if (newTokenStart != tokenStart) {
                    list.add(new String(c, tokenStart, newTokenStart - tokenStart));
                    tokenStart = newTokenStart;
                }
            } else {
                list.add(new String(c, tokenStart, pos - tokenStart));
                tokenStart = pos;
            }
            currentType = type;
        }
        list.add(new String(c, tokenStart, c.length - tokenStart));
        return list.toArray(new String[]{});
    }

    /**
     * <p>Splits a String by Character type as returned by
     * {@code java.lang.Character.getType(char)}. Groups of contiguous
     * characters of the same type are returned as complete tokens, with the
     * following exception: the character of type
     * {@code Character.UPPERCASE_LETTER}, if any, immediately
     * preceding a token of type {@code Character.LOWERCASE_LETTER}
     * will belong to the following token rather than to the preceding, if any,
     * {@code Character.UPPERCASE_LETTER} token.
     * <pre>
     * StringUtils.splitByCharacterTypeCamelCase(null)         = null
     * StringUtils.splitByCharacterTypeCamelCase("")           = []
     * StringUtils.splitByCharacterTypeCamelCase("ab de fg")   = ["ab", " ", "de", " ", "fg"]
     * StringUtils.splitByCharacterTypeCamelCase("ab   de fg") = ["ab", "   ", "de", " ", "fg"]
     * StringUtils.splitByCharacterTypeCamelCase("ab:cd:ef")   = ["ab", ":", "cd", ":", "ef"]
     * StringUtils.splitByCharacterTypeCamelCase("number5")    = ["number", "5"]
     * StringUtils.splitByCharacterTypeCamelCase("fooBar")     = ["foo", "Bar"]
     * StringUtils.splitByCharacterTypeCamelCase("foo200Bar")  = ["foo", "200", "Bar"]
     * StringUtils.splitByCharacterTypeCamelCase("ASFRules")   = ["ASF", "Rules"]
     * </pre>
     *
     * @param str the String to split, may be {@code null}
     * @return an array of parsed Strings, {@code null} if null String input
     */
    public static String[] splitByCharacterTypeCamelCase(String str) {
        return splitByCharacterType(str, true);
    }

    /**
     * <p>Splits the provided text into an array, separator string specified.
     *
     * <p>The separator(s) will not be included in the returned String array.
     * Adjacent separators are treated as one separator.</p>
     *
     * <p>A {@code null} input String returns {@code null}.
     * A {@code null} separator splits on whitespace.</p>
     *
     * <pre>
     * StringUtils.splitByWholeSeparator(null, *)               = null
     * StringUtils.splitByWholeSeparator("", *)                 = []
     * StringUtils.splitByWholeSeparator("ab de fg", null)      = ["ab", "de", "fg"]
     * StringUtils.splitByWholeSeparator("ab   de fg", null)    = ["ab", "de", "fg"]
     * StringUtils.splitByWholeSeparator("ab:cd:ef", ":")       = ["ab", "cd", "ef"]
     * StringUtils.splitByWholeSeparator("ab-!-cd-!-ef", "-!-") = ["ab", "cd", "ef"]
     * </pre>
     *
     * @param str       the String to parse, may be null
     * @param separator String containing the String to be used as a delimiter,
     *                  {@code null} splits on whitespace
     * @return an array of parsed Strings, {@code null} if null String was input
     */
    public static String[] splitByWholeSeparator(String str, String separator) {
        return splitByWholeSeparatorWorker(str, separator, -1, false);
    }

    /**
     * Splits the provided text into an array, separator string specified.
     * Returns a maximum of {@code max} substrings.
     *
     * <p>The separator(s) will not be included in the returned String array.
     * Adjacent separators are treated as one separator.</p>
     *
     * <p>A {@code null} input String returns {@code null}.
     * A {@code null} separator splits on whitespace.</p>
     *
     * <pre>
     * StringUtils.splitByWholeSeparator(null, *, *)               = null
     * StringUtils.splitByWholeSeparator("", *, *)                 = []
     * StringUtils.splitByWholeSeparator("ab de fg", null, 0)      = ["ab", "de", "fg"]
     * StringUtils.splitByWholeSeparator("ab   de fg", null, 0)    = ["ab", "de", "fg"]
     * StringUtils.splitByWholeSeparator("ab:cd:ef", ":", 2)       = ["ab", "cd:ef"]
     * StringUtils.splitByWholeSeparator("ab-!-cd-!-ef", "-!-", 5) = ["ab", "cd", "ef"]
     * StringUtils.splitByWholeSeparator("ab-!-cd-!-ef", "-!-", 2) = ["ab", "cd-!-ef"]
     * </pre>
     *
     * @param str       the String to parse, may be null
     * @param separator String containing the String to be used as a delimiter,
     *                  {@code null} splits on whitespace
     * @param max       the maximum number of elements to include in the returned
     *                  array. A zero or negative value implies no limit.
     * @return an array of parsed Strings, {@code null} if null String was input
     */
    public static String[] splitByWholeSeparator(String str, String separator, int max) {
        return splitByWholeSeparatorWorker(str, separator, max, false);
    }

    /**
     * Splits the provided text into an array, separator string specified.
     *
     * <p>The separator is not included in the returned String array.
     * Adjacent separators are treated as separators for empty tokens.
     * For more control over the split use the StrTokenizer class.</p>
     *
     * <p>A {@code null} input String returns {@code null}.
     * A {@code null} separator splits on whitespace.</p>
     *
     * <pre>
     * StringUtils.splitByWholeSeparatorPreserveAllTokens(null, *)               = null
     * StringUtils.splitByWholeSeparatorPreserveAllTokens("", *)                 = []
     * StringUtils.splitByWholeSeparatorPreserveAllTokens("ab de fg", null)      = ["ab", "de", "fg"]
     * StringUtils.splitByWholeSeparatorPreserveAllTokens("ab   de fg", null)    = ["ab", "", "", "de", "fg"]
     * StringUtils.splitByWholeSeparatorPreserveAllTokens("ab:cd:ef", ":")       = ["ab", "cd", "ef"]
     * StringUtils.splitByWholeSeparatorPreserveAllTokens("ab-!-cd-!-ef", "-!-") = ["ab", "cd", "ef"]
     * </pre>
     *
     * @param str       the String to parse, may be null
     * @param separator String containing the String to be used as a delimiter,
     *                  {@code null} splits on whitespace
     * @return an array of parsed Strings, {@code null} if null String was input
     */
    public static String[] splitByWholeSeparatorPreserveAllTokens(String str, String separator) {
        return splitByWholeSeparatorWorker(str, separator, -1, true);
    }

    /**
     * Splits the provided text into an array, separator string specified.
     * Returns a maximum of {@code max} substrings.
     *
     * <p>The separator is not included in the returned String array.
     * Adjacent separators are treated as separators for empty tokens.
     * For more control over the split use the StrTokenizer class.</p>
     *
     * <p>A {@code null} input String returns {@code null}.
     * A {@code null} separator splits on whitespace.</p>
     *
     * <pre>
     * StringUtils.splitByWholeSeparatorPreserveAllTokens(null, *, *)               = null
     * StringUtils.splitByWholeSeparatorPreserveAllTokens("", *, *)                 = []
     * StringUtils.splitByWholeSeparatorPreserveAllTokens("ab de fg", null, 0)      = ["ab", "de", "fg"]
     * StringUtils.splitByWholeSeparatorPreserveAllTokens("ab   de fg", null, 0)    = ["ab", "", "", "de", "fg"]
     * StringUtils.splitByWholeSeparatorPreserveAllTokens("ab:cd:ef", ":", 2)       = ["ab", "cd:ef"]
     * StringUtils.splitByWholeSeparatorPreserveAllTokens("ab-!-cd-!-ef", "-!-", 5) = ["ab", "cd", "ef"]
     * StringUtils.splitByWholeSeparatorPreserveAllTokens("ab-!-cd-!-ef", "-!-", 2) = ["ab", "cd-!-ef"]
     * </pre>
     *
     * @param str       the String to parse, may be null
     * @param separator String containing the String to be used as a delimiter,
     *                  {@code null} splits on whitespace
     * @param max       the maximum number of elements to include in the returned
     *                  array. A zero or negative value implies no limit.
     * @return an array of parsed Strings, {@code null} if null String was input
     */
    public static String[] splitByWholeSeparatorPreserveAllTokens(String str, String separator, int max) {
        return splitByWholeSeparatorWorker(str, separator, max, true);
    }

    /**
     * Performs the logic for the {@code splitByWholeSeparatorPreserveAllTokens} methods.
     *
     * @param str               the String to parse, may be {@code null}
     * @param separator         String containing the String to be used as a delimiter,
     *                          {@code null} splits on whitespace
     * @param max               the maximum number of elements to include in the returned
     *                          array. A zero or negative value implies no limit.
     * @param preserveAllTokens if {@code true}, adjacent separators are
     *                          treated as empty token separators; if {@code false}, adjacent
     *                          separators are treated as one separator.
     * @return an array of parsed Strings, {@code null} if null String input
     */
    private static String[] splitByWholeSeparatorWorker(
            String str, String separator, int max, boolean preserveAllTokens) {
        if (str == null) {
            return null;
        }

        int len = str.length();

        if (len == 0) {
            return new String[]{};
        }

        if (separator == null || EMPTY.equals(separator)) {
            // Split on whitespace.
            return splitWorker(str, null, max, preserveAllTokens);
        }

        int separatorLength = separator.length();

        ArrayList<String> substrings = new ArrayList<>();
        int numberOfSubstrings = 0;
        int beg = 0;
        int end = 0;
        while (end < len) {
            end = str.indexOf(separator, beg);

            if (end > -1) {
                if (end > beg) {
                    numberOfSubstrings += 1;

                    if (numberOfSubstrings == max) {
                        end = len;
                        substrings.add(str.substring(beg));
                    } else {
                        // The following is OK, because String.substring( beg, end ) excludes
                        // the character at the position 'end'.
                        substrings.add(str.substring(beg, end));

                        // Set the starting point for the next search.
                        // The following is equivalent to beg = end + (separatorLength - 1) + 1,
                        // which is the right calculation:
                        beg = end + separatorLength;
                    }
                } else {
                    // We found a consecutive occurrence of the separator, so skip it.
                    if (preserveAllTokens) {
                        numberOfSubstrings += 1;
                        if (numberOfSubstrings == max) {
                            end = len;
                            substrings.add(str.substring(beg));
                        } else {
                            substrings.add(EMPTY);
                        }
                    }
                    beg = end + separatorLength;
                }
            } else {
                // String.substring( beg ) goes from 'beg' to the end of the String.
                substrings.add(str.substring(beg));
                end = len;
            }
        }

        return substrings.toArray(new String[]{});
    }

    /**
     * Splits the provided text into an array, using whitespace as the
     * separator, preserving all tokens, including empty tokens created by
     * adjacent separators. This is an alternative to using StringTokenizer.
     * Whitespace is defined by {@link Character#isWhitespace(char)}.
     *
     * <p>The separator is not included in the returned String array.
     * Adjacent separators are treated as separators for empty tokens.
     * For more control over the split use the StrTokenizer class.</p>
     *
     * <p>A {@code null} input String returns {@code null}.</p>
     *
     * <pre>
     * StringUtils.splitPreserveAllTokens(null)       = null
     * StringUtils.splitPreserveAllTokens("")         = []
     * StringUtils.splitPreserveAllTokens("abc def")  = ["abc", "def"]
     * StringUtils.splitPreserveAllTokens("abc  def") = ["abc", "", "def"]
     * StringUtils.splitPreserveAllTokens(" abc ")    = ["", "abc", ""]
     * </pre>
     *
     * @param str the String to parse, may be {@code null}
     * @return an array of parsed Strings, {@code null} if null String input
     */
    public static String[] splitPreserveAllTokens(String str) {
        return splitWorker(str, null, -1, true);
    }

    /**
     * Splits the provided text into an array, separator specified,
     * preserving all tokens, including empty tokens created by adjacent
     * separators. This is an alternative to using StringTokenizer.
     *
     * <p>The separator is not included in the returned String array.
     * Adjacent separators are treated as separators for empty tokens.
     * For more control over the split use the StrTokenizer class.</p>
     *
     * <p>A {@code null} input String returns {@code null}.</p>
     *
     * <pre>
     * StringUtils.splitPreserveAllTokens(null, *)         = null
     * StringUtils.splitPreserveAllTokens("", *)           = []
     * StringUtils.splitPreserveAllTokens("a.b.c", '.')    = ["a", "b", "c"]
     * StringUtils.splitPreserveAllTokens("a..b.c", '.')   = ["a", "", "b", "c"]
     * StringUtils.splitPreserveAllTokens("a:b:c", '.')    = ["a:b:c"]
     * StringUtils.splitPreserveAllTokens("a\tb\nc", null) = ["a", "b", "c"]
     * StringUtils.splitPreserveAllTokens("a b c", ' ')    = ["a", "b", "c"]
     * StringUtils.splitPreserveAllTokens("a b c ", ' ')   = ["a", "b", "c", ""]
     * StringUtils.splitPreserveAllTokens("a b c  ", ' ')  = ["a", "b", "c", "", ""]
     * StringUtils.splitPreserveAllTokens(" a b c", ' ')   = ["", "a", "b", "c"]
     * StringUtils.splitPreserveAllTokens("  a b c", ' ')  = ["", "", "a", "b", "c"]
     * StringUtils.splitPreserveAllTokens(" a b c ", ' ')  = ["", "a", "b", "c", ""]
     * </pre>
     *
     * @param str           the String to parse, may be {@code null}
     * @param separatorChar the character used as the delimiter,
     *                      {@code null} splits on whitespace
     * @return an array of parsed Strings, {@code null} if null String input
     */
    public static String[] splitPreserveAllTokens(String str, char separatorChar) {
        return splitWorker(str, separatorChar, true);
    }

    /**
     * Splits the provided text into an array, separators specified,
     * preserving all tokens, including empty tokens created by adjacent
     * separators. This is an alternative to using StringTokenizer.
     *
     * <p>The separator is not included in the returned String array.
     * Adjacent separators are treated as separators for empty tokens.
     * For more control over the split use the StrTokenizer class.</p>
     *
     * <p>A {@code null} input String returns {@code null}.
     * A {@code null} separatorChars splits on whitespace.</p>
     *
     * <pre>
     * StringUtils.splitPreserveAllTokens(null, *)           = null
     * StringUtils.splitPreserveAllTokens("", *)             = []
     * StringUtils.splitPreserveAllTokens("abc def", null)   = ["abc", "def"]
     * StringUtils.splitPreserveAllTokens("abc def", " ")    = ["abc", "def"]
     * StringUtils.splitPreserveAllTokens("abc  def", " ")   = ["abc", "", "def"]
     * StringUtils.splitPreserveAllTokens("ab:cd:ef", ":")   = ["ab", "cd", "ef"]
     * StringUtils.splitPreserveAllTokens("ab:cd:ef:", ":")  = ["ab", "cd", "ef", ""]
     * StringUtils.splitPreserveAllTokens("ab:cd:ef::", ":") = ["ab", "cd", "ef", "", ""]
     * StringUtils.splitPreserveAllTokens("ab::cd:ef", ":")  = ["ab", "", "cd", "ef"]
     * StringUtils.splitPreserveAllTokens(":cd:ef", ":")     = ["", "cd", "ef"]
     * StringUtils.splitPreserveAllTokens("::cd:ef", ":")    = ["", "", "cd", "ef"]
     * StringUtils.splitPreserveAllTokens(":cd:ef:", ":")    = ["", "cd", "ef", ""]
     * </pre>
     *
     * @param str            the String to parse, may be {@code null}
     * @param separatorChars the characters used as the delimiters,
     *                       {@code null} splits on whitespace
     * @return an array of parsed Strings, {@code null} if null String input
     */
    public static String[] splitPreserveAllTokens(String str, String separatorChars) {
        return splitWorker(str, separatorChars, -1, true);
    }

    /**
     * Splits the provided text into an array with a maximum length,
     * separators specified, preserving all tokens, including empty tokens
     * created by adjacent separators.
     *
     * <p>The separator is not included in the returned String array.
     * Adjacent separators are treated as separators for empty tokens.
     * Adjacent separators are treated as one separator.</p>
     *
     * <p>A {@code null} input String returns {@code null}.
     * A {@code null} separatorChars splits on whitespace.</p>
     *
     * <p>If more than {@code max} delimited substrings are found, the last
     * returned string includes all characters after the first {@code max - 1}
     * returned strings (including separator characters).</p>
     *
     * <pre>
     * StringUtils.splitPreserveAllTokens(null, *, *)            = null
     * StringUtils.splitPreserveAllTokens("", *, *)              = []
     * StringUtils.splitPreserveAllTokens("ab de fg", null, 0)   = ["ab", "de", "fg"]
     * StringUtils.splitPreserveAllTokens("ab   de fg", null, 0) = ["ab", "", "", "de", "fg"]
     * StringUtils.splitPreserveAllTokens("ab:cd:ef", ":", 0)    = ["ab", "cd", "ef"]
     * StringUtils.splitPreserveAllTokens("ab:cd:ef", ":", 2)    = ["ab", "cd:ef"]
     * StringUtils.splitPreserveAllTokens("ab   de fg", null, 2) = ["ab", "  de fg"]
     * StringUtils.splitPreserveAllTokens("ab   de fg", null, 3) = ["ab", "", " de fg"]
     * StringUtils.splitPreserveAllTokens("ab   de fg", null, 4) = ["ab", "", "", "de fg"]
     * </pre>
     *
     * @param str            the String to parse, may be {@code null}
     * @param separatorChars the characters used as the delimiters,
     *                       {@code null} splits on whitespace
     * @param max            the maximum number of elements to include in the
     *                       array. A zero or negative value implies no limit
     * @return an array of parsed Strings, {@code null} if null String input
     */
    public static String[] splitPreserveAllTokens(String str, String separatorChars, int max) {
        return splitWorker(str, separatorChars, max, true);
    }

    /**
     * Performs the logic for the {@code split} and
     * {@code splitPreserveAllTokens} methods that do not return a
     * maximum array length.
     *
     * @param str               the String to parse, may be {@code null}
     * @param separatorChar     the separate character
     * @param preserveAllTokens if {@code true}, adjacent separators are
     *                          treated as empty token separators; if {@code false}, adjacent
     *                          separators are treated as one separator.
     * @return an array of parsed Strings, {@code null} if null String input
     */
    private static String[] splitWorker(String str, char separatorChar, boolean preserveAllTokens) {
        // Performance tuned for 2.0 (JDK1.4)
        if (str == null) {
            return null;
        }
        int len = str.length();
        if (len == 0) {
            return new String[]{};
        }
        List<String> list = new ArrayList<>();
        int i = 0;
        int start = 0;
        boolean match = false;
        boolean lastMatch = false;
        while (i < len) {
            if (str.charAt(i) == separatorChar) {
                if (match || preserveAllTokens) {
                    list.add(str.substring(start, i));
                    match = false;
                    lastMatch = true;
                }
                start = ++i;
                continue;
            }
            lastMatch = false;
            match = true;
            i++;
        }
        if (match || preserveAllTokens && lastMatch) {
            list.add(str.substring(start, i));
        }
        return list.toArray(new String[]{});
    }

    /**
     * Performs the logic for the {@code split} and
     * {@code splitPreserveAllTokens} methods that return a maximum array
     * length.
     *
     * @param str               the String to parse, may be {@code null}
     * @param separatorChars    the separate character
     * @param max               the maximum number of elements to include in the
     *                          array. A zero or negative value implies no limit.
     * @param preserveAllTokens if {@code true}, adjacent separators are
     *                          treated as empty token separators; if {@code false}, adjacent
     *                          separators are treated as one separator.
     * @return an array of parsed Strings, {@code null} if null String input
     */
    private static String[] splitWorker(String str, String separatorChars, int max, boolean preserveAllTokens) {
        // Performance tuned for 2.0 (JDK1.4)
        // Direct code is quicker than StringTokenizer.
        // Also, StringTokenizer uses isSpace() not isWhitespace()

        if (str == null) {
            return null;
        }
        int len = str.length();
        if (len == 0) {
            return new String[]{};
        }
        List<String> list = new ArrayList<>();
        int sizePlus1 = 1;
        int i = 0;
        int start = 0;
        boolean match = false;
        boolean lastMatch = false;
        if (separatorChars == null) {
            // Null separator means use whitespace
            while (i < len) {
                if (Character.isWhitespace(str.charAt(i))) {
                    if (match || preserveAllTokens) {
                        lastMatch = true;
                        if (sizePlus1++ == max) {
                            i = len;
                            lastMatch = false;
                        }
                        list.add(str.substring(start, i));
                        match = false;
                    }
                    start = ++i;
                    continue;
                }
                lastMatch = false;
                match = true;
                i++;
            }
        } else if (separatorChars.length() == 1) {
            // Optimize 1 character case
            char sep = separatorChars.charAt(0);
            while (i < len) {
                if (str.charAt(i) == sep) {
                    if (match || preserveAllTokens) {
                        lastMatch = true;
                        if (sizePlus1++ == max) {
                            i = len;
                            lastMatch = false;
                        }
                        list.add(str.substring(start, i));
                        match = false;
                    }
                    start = ++i;
                    continue;
                }
                lastMatch = false;
                match = true;
                i++;
            }
        } else {
            // standard case
            while (i < len) {
                if (separatorChars.indexOf(str.charAt(i)) >= 0) {
                    if (match || preserveAllTokens) {
                        lastMatch = true;
                        if (sizePlus1++ == max) {
                            i = len;
                            lastMatch = false;
                        }
                        list.add(str.substring(start, i));
                        match = false;
                    }
                    start = ++i;
                    continue;
                }
                lastMatch = false;
                match = true;
                i++;
            }
        }
        if (match || preserveAllTokens && lastMatch) {
            list.add(str.substring(start, i));
        }
        return list.toArray(new String[]{});
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
     * Removes diacritics (~= accents) from a string. The case will not be altered.
     * <p>
     * For instance, '&agrave;' will be replaced by 'a'.
     * </p>
     * <p>
     * Decomposes ligatures and digraphs per the KD column in the <a href = "https://www.unicode.org/charts/normalization/">Unicode Normalization Chart.</a>
     * </p>
     * <pre>
     * StringUtils.stripAccents(null)         = null
     * StringUtils.stripAccents("")           = ""
     * StringUtils.stripAccents("control")    = "control"
     * StringUtils.stripAccents("&eacute;clair")     = "eclair"
     * StringUtils.stripAccents("\u1d43\u1d47\u1d9c\u00b9\u00b2\u00b3")     = "abc123"
     * StringUtils.stripAccents("\u00BC \u00BD \u00BE")      = "1⁄4 1⁄2 3⁄4"
     * </pre>
     * <p>
     * See also <a href="http://www.unicode.org/unicode/reports/tr15/tr15-23.html">Unicode Standard Annex #15 Unicode Normalization Forms</a>.
     * </p>
     *
     * @param input String to be stripped
     * @return input text with diacritics removed
     */
    // See also Lucene's ASCIIFoldingFilter (Lucene 2.9) that replaces accented characters by their unaccented equivalent (and uncommitted bug fix: https://issues.apache.org/jira/browse/LUCENE-1343?focusedCommentId=12858907&page=com.atlassian.jira.plugin.system.issuetabpanels%3Acomment-tabpanel#action_12858907).
//    public static String stripAccents(String input) {
//        if (isEmpty(input)) {
//            return input;
//        }
//        StringBuilder decomposed = new StringBuilder(Normalizer.normalize(input, Normalizer.Form.NFKD));
//        convertRemainingAccentCharacters(decomposed);
//        return STRIP_ACCENTS_PATTERN.matcher(decomposed).replaceAll(EMPTY);
//    }

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
        ;
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
     * Strips whitespace from the start and end of a String  returning
     * an empty String if {@code null} input.
     *
     * <p>This is similar to {@link #trimToEmpty(String)} but removes whitespace.
     * Whitespace is defined by {@link Character#isWhitespace(char)}.</p>
     *
     * <pre>
     * StringUtils.stripToEmpty(null)     = ""
     * StringUtils.stripToEmpty("")       = ""
     * StringUtils.stripToEmpty("   ")    = ""
     * StringUtils.stripToEmpty("abc")    = "abc"
     * StringUtils.stripToEmpty("  abc")  = "abc"
     * StringUtils.stripToEmpty("abc  ")  = "abc"
     * StringUtils.stripToEmpty(" abc ")  = "abc"
     * StringUtils.stripToEmpty(" ab c ") = "ab c"
     * </pre>
     *
     * @param str the String to be stripped, may be null
     * @return the trimmed String, or an empty String if {@code null} input
     */
    public static String stripToEmpty(String str) {
        return str == null ? EMPTY : strip(str, null);
    }

    /**
     * Strips whitespace from the start and end of a String  returning
     * {@code null} if the String is empty ("") after the strip.
     *
     * <p>This is similar to {@link #trimToNull(String)} but removes whitespace.
     * Whitespace is defined by {@link Character#isWhitespace(char)}.</p>
     *
     * <pre>
     * StringUtils.stripToNull(null)     = null
     * StringUtils.stripToNull("")       = null
     * StringUtils.stripToNull("   ")    = null
     * StringUtils.stripToNull("abc")    = "abc"
     * StringUtils.stripToNull("  abc")  = "abc"
     * StringUtils.stripToNull("abc  ")  = "abc"
     * StringUtils.stripToNull(" abc ")  = "abc"
     * StringUtils.stripToNull(" ab c ") = "ab c"
     * </pre>
     *
     * @param str the String to be stripped, may be null
     * @return the stripped String,
     * {@code null} if whitespace, empty or null String input
     */
    public static String stripToNull(String str) {
        if (str == null) {
            return null;
        }
        str = strip(str, null);
        return str.isEmpty() ? null : str; // NOSONARLINT str cannot be null here
    }

    /**
     * Gets a substring from the specified String avoiding exceptions.
     *
     * <p>A negative start position can be used to start {@code n}
     * characters from the end of the String.</p>
     *
     * <p>A {@code null} String will return {@code null}.
     * An empty ("") String will return "".</p>
     *
     * <pre>
     * StringUtils.substring(null, *)   = null
     * StringUtils.substring("", *)     = ""
     * StringUtils.substring("abc", 0)  = "abc"
     * StringUtils.substring("abc", 2)  = "c"
     * StringUtils.substring("abc", 4)  = ""
     * StringUtils.substring("abc", -2) = "bc"
     * StringUtils.substring("abc", -4) = "abc"
     * </pre>
     *
     * @param str   the String to get the substring from, may be null
     * @param start the position to start from, negative means
     *              count back from the end of the String by this many characters
     * @return substring from start position, {@code null} if null String input
     */
    public static String substring(String str, int start) {
        if (str == null) {
            return null;
        }

        // handle negatives, which means last n characters
        if (start < 0) {
            start = str.length() + start; // remember start is negative
        }

        if (start < 0) {
            start = 0;
        }
        if (start > str.length()) {
            return EMPTY;
        }

        return str.substring(start);
    }

    /**
     * Gets a substring from the specified String avoiding exceptions.
     *
     * <p>A negative start position can be used to start/end {@code n}
     * characters from the end of the String.</p>
     *
     * <p>The returned substring starts with the character in the {@code start}
     * position and ends before the {@code end} position. All position counting is
     * zero-based -- i.e., to start at the beginning of the string use
     * {@code start = 0}. Negative start and end positions can be used to
     * specify offsets relative to the end of the String.</p>
     *
     * <p>If {@code start} is not strictly to the left of {@code end}, ""
     * is returned.</p>
     *
     * <pre>
     * StringUtils.substring(null, *, *)    = null
     * StringUtils.substring("", * ,  *)    = "";
     * StringUtils.substring("abc", 0, 2)   = "ab"
     * StringUtils.substring("abc", 2, 0)   = ""
     * StringUtils.substring("abc", 2, 4)   = "c"
     * StringUtils.substring("abc", 4, 6)   = ""
     * StringUtils.substring("abc", 2, 2)   = ""
     * StringUtils.substring("abc", -2, -1) = "b"
     * StringUtils.substring("abc", -4, 2)  = "ab"
     * </pre>
     *
     * @param str   the String to get the substring from, may be null
     * @param start the position to start from, negative means
     *              count back from the end of the String by this many characters
     * @param end   the position to end at (exclusive), negative means
     *              count back from the end of the String by this many characters
     * @return substring from start position to end position,
     * {@code null} if null String input
     */
    public static String substring(String str, int start, int end) {
        if (str == null) {
            return null;
        }

        // handle negatives
        if (end < 0) {
            end = str.length() + end; // remember end is negative
        }
        if (start < 0) {
            start = str.length() + start; // remember start is negative
        }

        // check length next
        if (end > str.length()) {
            end = str.length();
        }

        // if start is greater than end, return ""
        if (start > end) {
            return EMPTY;
        }

        if (start < 0) {
            start = 0;
        }
        if (end < 0) {
            end = 0;
        }

        return str.substring(start, end);
    }

    /**
     * Gets the substring after the first occurrence of a separator.
     * The separator is not returned.
     *
     * <p>A {@code null} string input will return {@code null}.
     * An empty ("") string input will return the empty string.
     *
     * <p>If nothing is found, the empty string is returned.</p>
     *
     * <pre>
     * StringUtils.substringAfter(null, *)      = null
     * StringUtils.substringAfter("", *)        = ""
     * StringUtils.substringAfter("abc", 'a')   = "bc"
     * StringUtils.substringAfter("abcba", 'b') = "cba"
     * StringUtils.substringAfter("abc", 'c')   = ""
     * StringUtils.substringAfter("abc", 'd')   = ""
     * StringUtils.substringAfter(" abc", 32)   = "abc"
     * </pre>
     *
     * @param str       the String to get a substring from, may be null
     * @param separator the character (Unicode code point) to search.
     * @return the substring after the first occurrence of the separator,
     * {@code null} if null String input
     */
    public static String substringAfter(String str, int separator) {
        if (isEmpty(str)) {
            return str;
        }
        int pos = str.indexOf(separator);
        if (pos == INDEX_NOT_FOUND) {
            return EMPTY;
        }
        return str.substring(pos + 1);
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
        if (isEmpty(str)) {
            return str;
        }
        if (separator == null) {
            return EMPTY;
        }
        int pos = str.indexOf(separator);
        if (pos == INDEX_NOT_FOUND) {
            return EMPTY;
        }
        return str.substring(pos + separator.length());
    }

    /**
     * Gets the substring after the last occurrence of a separator.
     * The separator is not returned.
     *
     * <p>A {@code null} string input will return {@code null}.
     * An empty ("") string input will return the empty string.
     *
     * <p>If nothing is found, the empty string is returned.</p>
     *
     * <pre>
     * StringUtils.substringAfterLast(null, *)      = null
     * StringUtils.substringAfterLast("", *)        = ""
     * StringUtils.substringAfterLast("abc", 'a')   = "bc"
     * StringUtils.substringAfterLast(" bc", 32)    = "bc"
     * StringUtils.substringAfterLast("abcba", 'b') = "a"
     * StringUtils.substringAfterLast("abc", 'c')   = ""
     * StringUtils.substringAfterLast("a", 'a')     = ""
     * StringUtils.substringAfterLast("a", 'z')     = ""
     * </pre>
     *
     * @param str       the String to get a substring from, may be null
     * @param separator the character (Unicode code point) to search.
     * @return the substring after the last occurrence of the separator,
     * {@code null} if null String input
     */
    public static String substringAfterLast(String str, int separator) {
        if (isEmpty(str)) {
            return str;
        }
        int pos = str.lastIndexOf(separator);
        if (pos == INDEX_NOT_FOUND || pos == str.length() - 1) {
            return EMPTY;
        }
        return str.substring(pos + 1);
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
        if (isEmpty(str)) {
            return str;
        }
        if (isEmpty(separator)) {
            return EMPTY;
        }
        int pos = str.lastIndexOf(separator);
        if (pos == INDEX_NOT_FOUND || pos == str.length() - separator.length()) {
            return EMPTY;
        }
        return str.substring(pos + separator.length());
    }

    /**
     * Gets the substring before the first occurrence of a separator. The separator is not returned.
     *
     * <p>
     * A {@code null} string input will return {@code null}. An empty ("") string input will return the empty string.
     * </p>
     *
     * <p>
     * If nothing is found, the string input is returned.
     * </p>
     *
     * <pre>
     * StringUtils.substringBefore(null, *)      = null
     * StringUtils.substringBefore("", *)        = ""
     * StringUtils.substringBefore("abc", 'a')   = ""
     * StringUtils.substringBefore("abcba", 'b') = "a"
     * StringUtils.substringBefore("abc", 'c')   = "ab"
     * StringUtils.substringBefore("abc", 'd')   = "abc"
     * </pre>
     *
     * @param str       the String to get a substring from, may be null
     * @param separator the character (Unicode code point) to search.
     * @return the substring before the first occurrence of the separator, {@code null} if null String input
     */
    public static String substringBefore(String str, int separator) {
        if (isEmpty(str)) {
            return str;
        }
        int pos = str.indexOf(separator);
        if (pos == INDEX_NOT_FOUND) {
            return str;
        }
        return str.substring(0, pos);
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
        if (isEmpty(str) || separator == null) {
            return str;
        }
        if (separator.isEmpty()) {
            return EMPTY;
        }
        int pos = str.indexOf(separator);
        if (pos == INDEX_NOT_FOUND) {
            return str;
        }
        return str.substring(0, pos);
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
        if (isEmpty(str) || isEmpty(separator)) {
            return str;
        }
        int pos = str.lastIndexOf(separator);
        if (pos == INDEX_NOT_FOUND) {
            return str;
        }
        return str.substring(0, pos);
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
//    public static String substringBetween(String str, String tag) {
//        return substringBetween(str, tag, tag);
//    }

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
     * @return the substring, {@code null} if no match
     */
//    public static String substringBetween(String str, String open, String close) {
//        if (!ObjectUtils.allNotNull(str, open, close)) {
//            return null;
//        }
//        int start = str.indexOf(open);
//        if (start != INDEX_NOT_FOUND) {
//            int end = str.indexOf(close, start + open.length());
//            if (end != INDEX_NOT_FOUND) {
//                return str.substring(start + open.length(), end);
//            }
//        }
//        return null;
//    }

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
        if (str == null || isEmpty(open) || isEmpty(close)) {
            return null;
        }
        int strLen = str.length();
        if (strLen == 0) {
            return new String[]{};
        }
        int closeLen = close.length();
        int openLen = open.length();
        List<String> list = new ArrayList<>();
        int pos = 0;
        while (pos < strLen - closeLen) {
            int start = str.indexOf(open, pos);
            if (start < 0) {
                break;
            }
            start += openLen;
            int end = str.indexOf(close, start);
            if (end < 0) {
                break;
            }
            list.add(str.substring(start, end));
            pos = end + closeLen;
        }
        if (list.isEmpty()) {
            return null;
        }
        return list.toArray(new String[]{});
    }

    /**
     * Swaps the case of a String changing upper and title case to
     * lower case, and lower case to upper case.
     *
     * <ul>
     *  <li>Upper case character converts to Lower case</li>
     *  <li>Title case character converts to Lower case</li>
     *  <li>Lower case character converts to Upper case</li>
     * </ul>
     *
     * <pre>
     * StringUtils.swapCase(null)                 = null
     * StringUtils.swapCase("")                   = ""
     * StringUtils.swapCase("The dog has a BONE") = "tHE DOG HAS A bone"
     * </pre>
     *
     * <p>NOTE: This method changed in Lang version 2.0.
     * It no longer performs a word based algorithm.
     * If you only use ASCII, you will notice no change.
     * That functionality is available in org.apache.commons.lang3.text.WordUtils.</p>
     *
     * @param str the String to swap case, may be null
     * @return the changed String, {@code null} if null String input
     */
    public static String swapCase(String str) {
        if (isEmpty(str)) {
            return str;
        }

        int strLen = str.length();
        int[] newCodePoints = new int[strLen]; // cannot be longer than the char array
        int outOffset = 0;
        for (int i = 0; i < strLen; ) {
            int oldCodepoint = str.codePointAt(i);
            int newCodePoint;
            if (Character.isUpperCase(oldCodepoint) || Character.isTitleCase(oldCodepoint)) {
                newCodePoint = Character.toLowerCase(oldCodepoint);
            } else if (Character.isLowerCase(oldCodepoint)) {
                newCodePoint = Character.toUpperCase(oldCodepoint);
            } else {
                newCodePoint = oldCodepoint;
            }
            newCodePoints[outOffset++] = newCodePoint;
            i += Character.charCount(newCodePoint);
        }
        return new String(newCodePoints, 0, outOffset);
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
     * @param cs the character sequence to convert
     * @return an array of code points
     */
    public static int[] toCodePoints(CharSequence cs) {
        if (cs == null) {
            return null;
        }
        if (cs.length() == 0) {
            return new int[]{};
        }
        return cs.toString().codePoints().toArray();
    }

    /**
     * Converts a {@code byte[]} to a String using the specified character encoding.
     *
     * @param bytes   the byte array to read from
     * @param charset the encoding to use, if null then use the platform default
     * @return a new String
     * @throws NullPointerException if {@code bytes} is null
     */
    public static String toEncodedString(byte[] bytes, Charset charset) {
        return new String(bytes, toCharset(charset));
    }

    /**
     * Converts the given source String as a lower-case using the {@link Locale#ROOT} locale in a null-safe manner.
     *
     * @param source A source String or null.
     * @return the given source String as a lower-case using the {@link Locale#ROOT} locale or null.
     */
    public static String toRootLowerCase(String source) {
        return source == null ? null : source.toLowerCase(Locale.ROOT);
    }

    /**
     * Converts the given source String as an upper-case using the {@link Locale#ROOT} locale in a null-safe manner.
     *
     * @param source A source String or null.
     * @return the given source String as an upper-case using the {@link Locale#ROOT} locale or null.
     */
    public static String toRootUpperCase(String source) {
        return source == null ? null : source.toUpperCase(Locale.ROOT);
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
     * Removes control characters (char &lt;= 32) from both
     * ends of this String returning an empty String ("") if the String
     * is empty ("") after the trim or if it is {@code null}.
     *
     * <p>The String is trimmed using {@link String#trim()}.
     * Trim removes start and end characters &lt;= 32.
     * To strip whitespace use {@link #stripToEmpty(String)}.
     *
     * <pre>
     * StringUtils.trimToEmpty(null)          = ""
     * StringUtils.trimToEmpty("")            = ""
     * StringUtils.trimToEmpty("     ")       = ""
     * StringUtils.trimToEmpty("abc")         = "abc"
     * StringUtils.trimToEmpty("    abc    ") = "abc"
     * </pre>
     *
     * @param str the String to be trimmed, may be null
     * @return the trimmed String, or an empty String if {@code null} input
     */
    public static String trimToEmpty(String str) {
        return str == null ? EMPTY : str.trim();
    }

    /**
     * Removes control characters (char &lt;= 32) from both
     * ends of this String returning {@code null} if the String is
     * empty ("") after the trim or if it is {@code null}.
     *
     * <p>The String is trimmed using {@link String#trim()}.
     * Trim removes start and end characters &lt;= 32.
     * To strip whitespace use {@link #stripToNull(String)}.
     *
     * <pre>
     * StringUtils.trimToNull(null)          = null
     * StringUtils.trimToNull("")            = null
     * StringUtils.trimToNull("     ")       = null
     * StringUtils.trimToNull("abc")         = "abc"
     * StringUtils.trimToNull("    abc    ") = "abc"
     * </pre>
     *
     * @param str the String to be trimmed, may be null
     * @return the trimmed String,
     * {@code null} if only chars &lt;= 32, empty or null String input
     */
    public static String trimToNull(String str) {
        String ts = trim(str);
        return isEmpty(ts) ? null : ts;
    }

    /**
     * Truncates a String. This will turn
     * "Now is the time for all good men" into "Now is the time for".
     *
     * <p>Specifically:</p>
     * <ul>
     *   <li>If {@code str} is less than {@code maxWidth} characters
     *       long, return it.</li>
     *   <li>Else truncate it to {@code substring(str, 0, maxWidth)}.</li>
     *   <li>If {@code maxWidth} is less than {@code 0}, throw an
     *       {@link IllegalArgumentException}.</li>
     *   <li>In no case will it return a String of length greater than
     *       {@code maxWidth}.</li>
     * </ul>
     *
     * <pre>
     * StringUtils.truncate(null, 0)       = null
     * StringUtils.truncate(null, 2)       = null
     * StringUtils.truncate("", 4)         = ""
     * StringUtils.truncate("abcdefg", 4)  = "abcd"
     * StringUtils.truncate("abcdefg", 6)  = "abcdef"
     * StringUtils.truncate("abcdefg", 7)  = "abcdefg"
     * StringUtils.truncate("abcdefg", 8)  = "abcdefg"
     * StringUtils.truncate("abcdefg", -1) = throws an IllegalArgumentException
     * </pre>
     *
     * @param str      the String to truncate, may be null
     * @param maxWidth maximum length of result String, must be positive
     * @return truncated String, {@code null} if null String input
     * @throws IllegalArgumentException If {@code maxWidth} is less than {@code 0}
     */
    public static String truncate(String str, int maxWidth) {
        return truncate(str, 0, maxWidth);
    }

    /**
     * Truncates a String. This will turn
     * "Now is the time for all good men" into "is the time for all".
     *
     * <p>Works like {@code truncate(String, int)}, but allows you to specify
     * a "left edge" offset.
     *
     * <p>Specifically:</p>
     * <ul>
     *   <li>If {@code str} is less than {@code maxWidth} characters
     *       long, return it.</li>
     *   <li>Else truncate it to {@code substring(str, offset, maxWidth)}.</li>
     *   <li>If {@code maxWidth} is less than {@code 0}, throw an
     *       {@link IllegalArgumentException}.</li>
     *   <li>If {@code offset} is less than {@code 0}, throw an
     *       {@link IllegalArgumentException}.</li>
     *   <li>In no case will it return a String of length greater than
     *       {@code maxWidth}.</li>
     * </ul>
     *
     * <pre>
     * StringUtils.truncate(null, 0, 0) = null
     * StringUtils.truncate(null, 2, 4) = null
     * StringUtils.truncate("", 0, 10) = ""
     * StringUtils.truncate("", 2, 10) = ""
     * StringUtils.truncate("abcdefghij", 0, 3) = "abc"
     * StringUtils.truncate("abcdefghij", 5, 6) = "fghij"
     * StringUtils.truncate("raspberry peach", 10, 15) = "peach"
     * StringUtils.truncate("Hello World", 0, 10) = "abcdefghij"
     * StringUtils.truncate("Hello World", -1, 10) = throws an IllegalArgumentException
     * StringUtils.truncate("Hello World", Integer.MIN_VALUE, 10) = throws an IllegalArgumentException
     * StringUtils.truncate("Hello World", Integer.MIN_VALUE, Integer.MAX_VALUE) = throws an IllegalArgumentException
     * StringUtils.truncate("Hello World", 0, Integer.MAX_VALUE) = "Hello World"
     * StringUtils.truncate("Hello World", 1, 10) = "bcdefghijk"
     * StringUtils.truncate("Hello World", 2, 10) = "cdefghijkl"
     * StringUtils.truncate("Hello World", 3, 10) = "defghijklm"
     * StringUtils.truncate("Hello World", 4, 10) = "efghijklmn"
     * StringUtils.truncate("Hello World", 5, 10) = "fghijklmno"
     * StringUtils.truncate("Hello World", 5, 5) = "fghij"
     * StringUtils.truncate("Hello World", 5, 3) = "fgh"
     * StringUtils.truncate("Hello World", 10, 3) = "klm"
     * StringUtils.truncate("Hello World", 10, Integer.MAX_VALUE) = "klmno"
     * StringUtils.truncate("Hello World", 13, 1) = "n"
     * StringUtils.truncate("Hello World", 13, Integer.MAX_VALUE) = "no"
     * StringUtils.truncate("Hello World", 14, 1) = "o"
     * StringUtils.truncate("Hello World", 14, Integer.MAX_VALUE) = "o"
     * StringUtils.truncate("Hello World", 15, 1) = ""
     * StringUtils.truncate("Hello World", 15, Integer.MAX_VALUE) = ""
     * StringUtils.truncate("Hello World", Integer.MAX_VALUE, Integer.MAX_VALUE) = ""
     * StringUtils.truncate("abcdefghij", 3, -1) = throws an IllegalArgumentException
     * StringUtils.truncate("abcdefghij", -2, 4) = throws an IllegalArgumentException
     * </pre>
     *
     * @param str      the String to truncate, may be null
     * @param offset   left edge of source String
     * @param maxWidth maximum length of result String, must be positive
     * @return truncated String, {@code null} if null String input
     * @throws IllegalArgumentException If {@code offset} or {@code maxWidth} is less than {@code 0}
     */
    public static String truncate(String str, int offset, int maxWidth) {
        if (offset < 0) {
            throw new IllegalArgumentException("offset cannot be negative");
        }
        if (maxWidth < 0) {
            throw new IllegalArgumentException("maxWith cannot be negative");
        }
        if (str == null) {
            return null;
        }
        if (offset > str.length()) {
            return EMPTY;
        }
        if (str.length() > maxWidth) {
            int ix = Math.min(offset + maxWidth, str.length());
            return str.substring(offset, ix);
        }
        return str.substring(offset);
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
     * Unwraps a given string from a character.
     *
     * <pre>
     * StringUtils.unwrap(null, null)         = null
     * StringUtils.unwrap(null, '\0')         = null
     * StringUtils.unwrap(null, '1')          = null
     * StringUtils.unwrap("a", 'a')           = "a"
     * StringUtils.unwrap("aa", 'a')           = ""
     * StringUtils.unwrap("\'abc\'", '\'')    = "abc"
     * StringUtils.unwrap("AABabcBAA", 'A')   = "ABabcBA"
     * StringUtils.unwrap("A", '#')           = "A"
     * StringUtils.unwrap("#A", '#')          = "#A"
     * StringUtils.unwrap("A#", '#')          = "A#"
     * </pre>
     *
     * @param str      the String to be unwrapped, can be null
     * @param wrapChar the character used to unwrap
     * @return unwrapped String or the original string
     * if it is not quoted properly with the wrapChar
     */
    public static String unwrap(String str, char wrapChar) {
        if (isEmpty(str) || wrapChar == '\0' || str.length() == 1) {
            return str;
        }

        if (str.charAt(0) == wrapChar && str.charAt(str.length() - 1) == wrapChar) {
            int startIndex = 0;
            int endIndex = str.length() - 1;

            return str.substring(startIndex + 1, endIndex);
        }

        return str;
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
//    public static String unwrap(String str, String wrapToken) {
//        if (isEmpty(str) || isEmpty(wrapToken) || str.length() < 2 * wrapToken.length()) {
//            return str;
//        }
//
//        if (Strings.CS.startsWith(str, wrapToken) && Strings.CS.endsWith(str, wrapToken)) {
//            return str.substring(wrapToken.length(), str.lastIndexOf(wrapToken));
//        }
//
//        return str;
//    }

    /**
     * Returns the string representation of the {@code char} array or null.
     *
     * @param value the character array.
     * @return a String or null
     * @see String#valueOf(char[])
     */
    public static String valueOf(char[] value) {
        return value == null ? null : String.valueOf(value);
    }

    /**
     * Wraps a string with a char.
     *
     * <pre>
     * StringUtils.wrap(null, *)        = null
     * StringUtils.wrap("", *)          = ""
     * StringUtils.wrap("ab", '\0')     = "ab"
     * StringUtils.wrap("ab", 'x')      = "xabx"
     * StringUtils.wrap("ab", '\'')     = "'ab'"
     * StringUtils.wrap("\"ab\"", '\"') = "\"\"ab\"\""
     * </pre>
     *
     * @param str      the string to be wrapped, may be {@code null}
     * @param wrapWith the char that will wrap {@code str}
     * @return the wrapped string, or {@code null} if {@code str == null}
     */
    public static String wrap(String str, char wrapWith) {

        if (isEmpty(str) || wrapWith == '\0') {
            return str;
        }

        return wrapWith + str + wrapWith;
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

        if (isEmpty(str) || isEmpty(wrapWith)) {
            return str;
        }

        return wrapWith.concat(str).concat(wrapWith);
    }

    /**
     * Wraps a string with a char if that char is missing from the start or end of the given string.
     *
     * <p>A new {@link String} will not be created if {@code str} is already wrapped.</p>
     *
     * <pre>
     * StringUtils.wrapIfMissing(null, *)        = null
     * StringUtils.wrapIfMissing("", *)          = ""
     * StringUtils.wrapIfMissing("ab", '\0')     = "ab"
     * StringUtils.wrapIfMissing("ab", 'x')      = "xabx"
     * StringUtils.wrapIfMissing("ab", '\'')     = "'ab'"
     * StringUtils.wrapIfMissing("\"ab\"", '\"') = "\"ab\""
     * StringUtils.wrapIfMissing("/", '/')  = "/"
     * StringUtils.wrapIfMissing("a/b/c", '/')  = "/a/b/c/"
     * StringUtils.wrapIfMissing("/a/b/c", '/')  = "/a/b/c/"
     * StringUtils.wrapIfMissing("a/b/c/", '/')  = "/a/b/c/"
     * </pre>
     *
     * @param str      the string to be wrapped, may be {@code null}
     * @param wrapWith the char that will wrap {@code str}
     * @return the wrapped string, or {@code null} if {@code str == null}
     */
    public static String wrapIfMissing(String str, char wrapWith) {
        if (isEmpty(str) || wrapWith == '\0') {
            return str;
        }
        boolean wrapStart = str.charAt(0) != wrapWith;
        boolean wrapEnd = str.charAt(str.length() - 1) != wrapWith;
        if (!wrapStart && !wrapEnd) {
            return str;
        }

        StringBuilder builder = new StringBuilder(str.length() + 2);
        if (wrapStart) {
            builder.append(wrapWith);
        }
        builder.append(str);
        if (wrapEnd) {
            builder.append(wrapWith);
        }
        return builder.toString();
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
        if (isEmpty(str) || isEmpty(wrapWith)) {
            return str;
        }

        boolean wrapStart = !str.startsWith(wrapWith);
        boolean wrapEnd = !str.endsWith(wrapWith);
        if (!wrapStart && !wrapEnd) {
            return str;
        }

        StringBuilder builder = new StringBuilder(str.length() + wrapWith.length() + wrapWith.length());
        if (wrapStart) {
            builder.append(wrapWith);
        }
        builder.append(str);
        if (wrapEnd) {
            builder.append(wrapWith);
        }
        return builder.toString();
    }
}
