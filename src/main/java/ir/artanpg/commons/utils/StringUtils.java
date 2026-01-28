package ir.artanpg.commons.utils;

import ir.artanpg.commons.core.tools.jacoco.Generated;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import static ir.artanpg.commons.utils.ArrayUtils.EMPTY_STRING_ARRAY;

/**
 * Provides utility methods for {@link String} instances.
 *
 * @author Mohammad Yazdian
 */
public abstract class StringUtils {

    /**
     * The empty string {@code ""}.
     */
    public static final String EMPTY = "";

    /**
     * The space string {@code " "}.
     */
    public static final String SPACE = " ";

    /**
     * The comma string {@code ","}.
     */
    public static final String COMMA = ",";

    /**
     * The null string {@code "null"}.
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
     * The Unicode non-breaking space character (U+00A0), which prevents line breaks at its position.
     */
    public static final char NON_BREAKING_SPACE = '\u00A0';

    /**
     * This is a 3-character version of an ellipsis.
     */
    public static final String ELLIPSIS3 = "...";

    /**
     * Represents a failed index search.
     */
    public static final int INDEX_NOT_FOUND = -1;

    /**
     * Private constructor to prevent instantiation of this utility class.
     *
     * @throws UnsupportedOperationException if an attempt is made to instantiate this class
     */
    @Generated
    private StringUtils() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    /**
     * Abbreviates a string to a specified maximum width by appending a default
     * ellipsis marker ({@code ...}).
     *
     * <p>If the string is {@code null}, {@code empty} or {@code blank}, an
     * {@code empty} string is returned.
     *
     * @param string   the string to abbreviate
     * @param maxWidth the maximum length of the resulting string
     * @return the abbreviated string, or the original string if no abbreviation is needed
     * @throws IllegalArgumentException if maxWidth is less than the minimum required width
     * @see #abbreviate(String, String, int)
     */
    public static String abbreviate(String string, int maxWidth) {
        return abbreviate(string, ELLIPSIS3, maxWidth);
    }

    /**
     * Abbreviates a string to a specified maximum width by appending an
     * abbreviation marker.
     *
     * <p>If the input string or abbrev marker is {@code null}, {@code empty}
     * or {@code blank}, an {@code empty} string is returned.
     *
     * <p>If the string length is {@code less than} or {@code equal} to the
     * {@code maximum} width, it is returned unchanged.
     *
     * <p>If the string is {@code longer}, it is truncated and the abbreviation
     * marker is appended to fit within the {@code maximum} width.
     *
     * @param string       the string to abbreviate
     * @param abbrevMarker the marker to append when abbreviating
     * @param maxWidth     the maximum length of the result, including the abbreviation marker
     * @return the abbreviated string, or the original string if no abbreviation is needed
     * @throws IllegalArgumentException if maxWidth is less than the minimum required width
     */
    public static String abbreviate(String string, String abbrevMarker, int maxWidth) {
        if (!hasTextAll(string, abbrevMarker)) return EMPTY;

        int abbrevMarkerLength = abbrevMarker.length();
        int minAbbrevWidth = abbrevMarkerLength + 1;

        if (maxWidth < minAbbrevWidth)
            throw new IllegalArgumentException("Minimum abbreviation width is " + minAbbrevWidth);

        int strLen = string.length();

        return (strLen <= maxWidth) ? string : string.substring(0, maxWidth - abbrevMarkerLength) + abbrevMarker;
    }

    /**
     * Capitalizes the first character of the given string, handling
     * {@code Unicode} characters properly.
     *
     * <p>If the input string is {@code null}, {@code empty} or {@code blank},
     * the input string is returned unchanged.
     *
     * @param string the string to capitalize
     * @return the capitalized string
     * @see #uncapitalize(String)
     */
    public static String capitalize(String string) {
        if (!hasText(string)) return string;

        int firstCodepoint = string.codePointAt(0);
        int newCodePoint = Character.toTitleCase(firstCodepoint);

        if (firstCodepoint == newCodePoint) return string;

        int[] newCodePoints = string.codePoints().toArray();
        newCodePoints[0] = newCodePoint;

        return new String(newCodePoints, 0, newCodePoints.length);
    }

    /**
     * Removes one newline from end of a string if it's there, otherwise leave
     * it alone. A newline is &quot;{@code \n}&quot;, &quot;{@code \r}&quot;,
     * or &quot;{@code \r\n}&quot;.
     *
     * <p>If the input string is {@code null}, {@code empty} or {@code blank},
     * the input string is returned unchanged.
     *
     * @param string the String to chomp a newline from
     * @return string without newline
     */
    public static String chomp(String string) {
        if (!hasLength(string)) return string;

        if (string.length() == 1) {
            char ch = string.charAt(0);
            return (ch == CR || ch == LF) ? EMPTY : string;
        }

        int lastIdx = string.length() - 1;
        char last = string.charAt(lastIdx);
        if (last == LF && string.charAt(lastIdx - 1) == CR) {
            lastIdx--;
        } else if (last != CR) {
            lastIdx++;
        }
        return string.substring(0, lastIdx);
    }

    /**
     * Remove the last character from a string.
     * If the string ends in {@code \r\n}, then remove both of them.
     *
     * <p>If the input string is {@code null}, {@code empty} or {@code blank},
     * an {@code empty} string is returned.
     *
     * @param string the string to chop last character from
     * @return string without last character
     */
    public static String chop(String string) {
        if (!hasText(string) || string.length() < 2) return EMPTY;

        int length = string.length();

        int lastIndex = length - 1;
        String chop = string.substring(0, lastIndex);
        char last = string.charAt(lastIndex);

        return last == LF || last == CR ? chop.substring(0, lastIndex - 1) : chop;
    }

    /**
     * Tests if string input contains a search string.
     *
     * <p>If the input string is {@code null}, {@code empty} or {@code blank},
     * {@code false} is returned.
     *
     * <p>if the search string is {@code null} or {@code empty}, {@code false}
     * is returned.
     *
     * @param string the string to check
     * @param search the string to find
     * @return {@code true}, if the string contains the search string, {@code false} otherwise
     * @see String#contains(CharSequence)
     */
    public static boolean contains(String string, String search) {
        if (!hasText(string)) return false;
        if (!hasLength(search)) return false;

        return string.contains(search);
    }

    /**
     * Tests if the string contains any character in the given set of
     * characters.
     *
     * <p>If the input string is {@code null}, {@code empty} or {@code blank},
     * {@code false} is returned.
     *
     * <p>if the search string is {@code null} or {@code empty}, {@code false}
     * is returned.
     *
     * @param string the string to check
     * @param search the strings to search for
     * @return {@code true}, if any of the chars are found, {@code false} otherwise
     */
    public static boolean containsAny(String string, String... search) {
        if (!hasText(string)) return false;
        if (!hasLength(search)) return false;

        // Initialize Aho-Corasick and add patterns
        AhoCorasick ahoCorasick = new AhoCorasick();
        for (String str : search) {
            if (hasLength(str)) {
                ahoCorasick.addPattern(str);
            }
        }

        // Build failure links and search
        ahoCorasick.buildFailureLinks();
        return ahoCorasick.search(string);
    }

    /**
     * Tests whether the given string contains any whitespace characters.
     * Whitespace is defined by {@link Character#isWhitespace(char)}.
     *
     * <p>If the input string is {@code null} or {@code empty}, {@code false}
     * is returned.
     *
     * @param string the string to check
     * @return {@code true}, if the string contains at least 1 whitespace character, {@code false} otherwise
     */
    public static boolean containsWhitespace(String string) {
        if (!hasLength(string)) return false;

        int length = string.length();
        for (int i = 0; i < length; i++) {
            if (Character.isWhitespace(string.charAt(i))) return true;
        }

        return false;
    }

    /**
     * Counts how many times the substring appears in the larger string. Note
     * that the code only counts non-overlapping matches.
     *
     * @param string    the string to check
     * @param substring the substring to count
     * @return the number of occurrences
     */
    public static int countMatches(String string, String substring) {
        if (!hasLengthAll(string, substring)) return 0;

        int count = 0;
        int index = 0;

        while ((index = string.indexOf(substring, index)) != INDEX_NOT_FOUND) {
            count++;
            index += substring.length();
        }

        return count;
    }

    /**
     * Converts a collection of string into a single string, with each element
     * separated by a {@code comma}.
     *
     * <p>If the collection is {@code null} or {@code empty}, an {@code empty}
     * string is returned.
     *
     * <p>Each element is converted to its string representation using
     * {@link String#valueOf(Object)} and {@code null} elements are represented
     * as {@code "null"}.
     *
     * @param collection the collection to convert
     * @return a string containing the elements of the collection separated by commas
     * @see #collectionToDelimitedString(Collection, String, String, String)
     */
    public static String collectionToDelimitedString(Collection<String> collection) {
        return collectionToDelimitedString(collection, COMMA, EMPTY, EMPTY);
    }

    /**
     * Converts a collection of strings into a single string, with each element
     * separated by the specified delimiter.
     *
     * <p>If the collection is {@code null} or {@code empty}, an {@code empty}
     * string is returned.
     *
     * <p>Each element is converted to its string representation using
     * {@link String#valueOf(Object)} and {@code null} elements are represented
     * as {@code "null"}.
     *
     * @param collection the collection to convert
     * @param delimiter  the delimiter to separate elements
     * @return a string containing the elements of the collection separated by {@code delimiter}
     * @see #collectionToDelimitedString(Collection, String, String, String)
     */
    public static String collectionToDelimitedString(Collection<String> collection, String delimiter) {
        return collectionToDelimitedString(collection, delimiter, EMPTY, EMPTY);
    }

    /**
     * Converts a collection of objects into a single string, with each element
     * enclosed by the specified {@code prefix} and {@code suffix}, and
     * separated by the specified {@code delimiter}.
     *
     * <p>If the collection is {@code null} or {@code empty}, an {@code empty}
     * string is returned.
     *
     * <p>If the delimiter is {@code null}, an {@code comma} string is used.
     *
     * <p>If the prefix or suffix is {@code null}, an {@code empty} string is used.
     *
     * <p>Each element is converted to its string representation using
     * {@link String#valueOf(Object)} and {@code null} elements are represented
     * as {@code "null"}.
     *
     * @param collection the collection to convert
     * @param delimiter  the delimiter to separate elements
     * @param prefix     the string to prepend to each element
     * @param suffix     the string to append to each element
     * @return a string containing the elements of the collection
     */
    public static String collectionToDelimitedString(Collection<String> collection,
                                                     String delimiter,
                                                     String prefix,
                                                     String suffix) {
        if (!CollectionUtils.hasLength(collection)) return EMPTY;

        if (delimiter == null) delimiter = COMMA;
        if (prefix == null) prefix = EMPTY;
        if (suffix == null) suffix = EMPTY;

        int length =
                collection.size() * (prefix.length() + suffix.length()) + (collection.size() - 1) * delimiter.length();
        for (String element : collection) {
            length += String.valueOf(element).length();
        }

        StringBuilder stringBuilder = new StringBuilder(length);
        Iterator<String> iterator = collection.iterator();
        while (iterator.hasNext()) {
            stringBuilder.append(prefix).append(iterator.next()).append(suffix);
            if (iterator.hasNext()) stringBuilder.append(delimiter);
        }
        return stringBuilder.toString();
    }

    /**
     * Returns either the passed in String, or if the String is {@code null} or
     * {@code empty}, the value of {@code defaultString}.
     *
     * @param string        the string to check
     * @param defaultString the default string to return
     * @return the passed in string, or the default string
     * @see #hasLength(String)
     */
    public static String defaultIfNotHasLength(String string, String defaultString) {
        return !hasLength(string) ? defaultString : string;
    }

    /**
     * Returns either the passed in String, or if the String is {@code null} or
     * {@code blank}, the value of {@code defaultString}.
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
     * Removes all occurrences of any character contained in the supplied
     * {@code charsToDelete} string from the given input string.
     *
     * <p>Each character in {@code inString} is examined individually and
     * removed if it exists in {@code charsToDelete}. This method operates at
     * the character level and does not perform substring or pattern-based
     * removal.
     *
     * <p>If {@code inString} is {@code null} or empty, or if
     * {@code charsToDelete} is {@code null} or empty, this method returns the
     * original {@code inString} unchanged.
     *
     * <p>For performance reasons, if no characters are removed, the original
     * {@code inString} instance is returned without creating a new
     * {@link String}.
     *
     * @param inString      the input string to process
     * @param charsToDelete a string containing characters to remove from {@code inString}
     * @return a string with all specified characters removed
     */
    public static String deleteAny(String inString, String charsToDelete) {
        if (!hasLength(inString) || !hasLength(charsToDelete)) return inString;

        int lastCharIndex = 0;
        char[] result = new char[inString.length()];

        for (int i = 0; i < inString.length(); i++) {
            char ch = inString.charAt(i);
            if (charsToDelete.indexOf(ch) == -1) result[lastCharIndex++] = ch;
        }

        return (lastCharIndex == inString.length()) ? inString : new String(result, 0, lastCharIndex);
    }

    /**
     * Deletes all whitespaces from a String as defined by
     * {@link Character#isWhitespace(char)}.
     *
     * @param string the String to delete whitespace from
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

        if (count == stringLength) return string;
        return (count == 0) ? EMPTY : new String(chs, 0, count);
    }

    /**
     * Compares two Strings, and returns the portion where they differ.
     *
     * <p>More precisely, return the remainder of the second String, starting
     * from where it's different from the first.
     *
     * @param string1 the first string
     * @param string2 the second string
     * @return the portion of string2 where it differs from string1
     */
    public static String difference(String string1, String string2) {
        if (!hasLength(string1)) return string2;
        if (!hasLength(string2)) return string1;

        int at = indexOfDifference(string1, string2);
        return (at == INDEX_NOT_FOUND) ? EMPTY : string2.substring(at);
    }

    private static int indexOfDifference(String string1, String string2) {
        if (Objects.equals(string1, string2)) return INDEX_NOT_FOUND;
        if (string1 == null || string2 == null) return 0;

        int i;
        for (i = 0; i < string1.length() && i < string2.length(); ++i) {
            if (string1.charAt(i) != string2.charAt(i)) break;
        }

        return (i < string2.length() || i < string1.length()) ? i : INDEX_NOT_FOUND;
    }

    /**
     * Splits the given string into an array of strings based on the specified
     * delimiter.
     *
     * @param string    the input string to split
     * @param delimiter the delimiter string to split on
     * @return an array of strings split from the input string
     * @see #delimitedListToStringArray(String, String, String)
     */
    public static String[] delimitedListToStringArray(String string, String delimiter) {
        return delimitedListToStringArray(string, delimiter, null);
    }

    /**
     * Splits the given string into an array of strings based on the specified
     * delimiter, optionally removing specified characters from each resulting
     * element.
     *
     * <p>The delimiter may consist of multiple characters. If the delimiter is
     * {@code null}, the entire input string is returned as a single-element
     * array. If the delimiter is an empty string, the input string is split
     * into individual characters.
     *
     * <p>If {@code charsToDelete} is provided, any character contained in this
     * string will be removed from each extracted token.
     *
     * <p>If the input {@code string} is {@code null}, this method returns an
     * {@code empty} string array.
     *
     * @param string        the input string to split
     * @param delimiter     the delimiter string to split on
     * @param charsToDelete a string containing characters to remove from each resulting token
     * @return an array of strings extracted from the input string
     */
    public static String[] delimitedListToStringArray(String string, String delimiter, String charsToDelete) {
        if (string == null) return EMPTY_STRING_ARRAY;
        if (delimiter == null) return new String[]{string};

        List<String> result = new ArrayList<>();
        if (delimiter.isEmpty()) {
            for (int i = 0; i < string.length(); i++) {
                result.add(deleteAny(string.substring(i, i + 1), charsToDelete));
            }
        } else {
            int position = 0;
            int delimiterPosition;
            while ((delimiterPosition = string.indexOf(delimiter, position)) != -1) {
                result.add(deleteAny(string.substring(position, delimiterPosition), charsToDelete));
                position = delimiterPosition + delimiter.length();
            }
            if (!string.isEmpty() && position <= string.length()) {
                // Add rest of String, but not in case of empty input.
                result.add(deleteAny(string.substring(position), charsToDelete));
            }
        }

        return (CollectionUtils.hasLength(result)) ? result.toArray(EMPTY_STRING_ARRAY) : EMPTY_STRING_ARRAY;
    }

    /**
     * Test if the given {@code String} ends with the specified suffix,
     * ignoring upper/lower case.
     *
     * <p>A {@code null}, {@code empty} or a {@code blank} input string will
     * return the {@code false}.
     *
     * <p>A {@code null}, {@code empty} or a {@code blank} prefix will return
     * the {@code false}.
     *
     * <p>If nothing is found, the {@code false} is returned.
     *
     * @param string the string to check
     * @param suffix the suffix to look for
     * @return {@code true}, if the string ends with the given suffix, {@code false} otherwise
     * @see String#endsWith(String)
     */
    public static boolean endsWithIgnoreCase(String string, String suffix) {
        return (hasTextAll(string, suffix) && string.length() >= suffix.length() &&
                string.regionMatches(true, string.length() - suffix.length(), suffix, 0, suffix.length()));
    }

    /**
     * Returns the first value in the array which is not {@code empty},
     * {@code null} or {@code whitespace} only.
     *
     * <p>If all strings are blank or the array is {@code null} or
     * {@code empty} then {@code null} is returned.
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
     * @param string the string to check
     * @return {@code true}, if the string is not {@code null} and has length, {@code false} otherwise
     * @see #hasText(String)
     */
    public static boolean hasLength(String string) {
        return string != null && !string.isEmpty();
    }

    /**
     * Checks if at least one of the provided strings has a non-zero length.
     *
     * <p>A string is considered to have length if it is not null and not
     * {@code empty} after trimming.
     *
     * @param strings the string to check
     * @return {@code true}, if at least one string has a non-zero length, {@code false} otherwise
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
     * @param strings the array of strings to check
     * @return {@code true}, if all strings have length, {@code false} otherwise
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
     * @param string the string to check
     * @return {@code true}, if the string is not {@code null}, its length is greater than 0, and it does not
     * contain whitespace only, {@code false} otherwise
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
     * @param strings the strings to check
     * @return {@code true} if at least one strings contains non-whitespace text, {@code false} otherwise
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
     * @param strings the array of strings to check
     * @return {@code true} if all strings have text, {@code false} otherwise
     */
    public static boolean hasTextAll(String... strings) {
        if (strings == null || strings.length == 0) return false;

        for (String string : strings) {
            if (!StringUtils.hasText(string)) return false;
        }

        return true;
    }

    /**
     * Finds the index of the first occurrence of any string from the given
     * array within the input string.
     * The search is case-sensitive.
     *
     * <p>This method searches for the earliest occurrence (lowest index) of
     * any string in the {@code search} array within the input {@code string}.
     *
     * <p>If no match is found, or if the input string is {@code empty},
     * {@code null}, or contains only {@code whitespace}, or if the search
     * array is {@code null} or {@code empty}, it returns {@code -1}.
     *
     * @param string the input string to search in
     * @param search the array of strings to search for
     * @return the index of the first occurrence of any string from search in string
     */
    public static int indexOfAny(String string, String[] search) {
        if (!hasText(string) || search == null || search.length == 0) return INDEX_NOT_FOUND;

        int position = Integer.MAX_VALUE;
        int tmp;

        for (String strSearch : search) {
            tmp = string.indexOf(strSearch);
            if (tmp == -1) continue;
            if (tmp < position) position = tmp;
        }

        return (position == Integer.MAX_VALUE) ? INDEX_NOT_FOUND : position;
    }

    /**
     * Finds the last index within a string of the specified substrings.
     *
     * <p>This method searches for the last occurrence of any substring from
     * the provided array within the input string and returns the highest index
     * found.
     *
     * <p>If no substring is found, or if the input string is {@code null},
     * {@code empty}, or the search array is {@code null} or {@code empty},
     * it returns {@code -1}.
     *
     * @param string the string to search in
     * @param search an array of substrings to search for
     * @return the highest index in the string where any substring from the search array is found
     */
    public static int lastIndexOfAny(String string, String[] search) {
        if (!hasText(string) || search == null || search.length == 0) return INDEX_NOT_FOUND;

        int position = -1;
        int tmp;

        for (String strSearch : search) {
            tmp = string.lastIndexOf(strSearch);
            if (tmp > position) position = tmp;
        }

        return position;
    }

    /**
     * Tests if the String contains only lowercase characters.
     *
     * @param string the string to check
     * @return {@code true}, if only contains lowercase characters, {@code false} otherwise
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
     * @param string the string to check
     * @return {@code true}, if only contains uppercase characters, {@code false} otherwise
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
     * @param string the string to check
     * @return {@code true}, if only contains letters, {@code false} otherwise
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
     * @param string the string to check
     * @return {@code true}, if only contains letters or digits, {@code false} otherwise
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
     * @param string the string to check
     * @return {@code true}, if only contains letters, digits or space, {@code false} otherwise
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
     * @param string the string to check
     * @return {@code true}, if only contains letters and space, {@code false} otherwise
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
     * @param string the string to check
     * @return {@code true}, if the string represents a valid numeric value, {@code false} otherwise
     */
    public static boolean isNumeric(String string) {
        if (!hasText(string)) return false;

        int length = string.length();
        boolean hasDecimalPoint = false;

        for (int i = 0; i < length; i++) {
            char ch = string.charAt(i);

            if (shouldSkip(ch, i, length, hasDecimalPoint)) {
                if (ch == '.') hasDecimalPoint = true;
                continue;
            }

            if (!Character.isDigit(ch)) return false;
        }

        return isValidLength(string, length, hasDecimalPoint);
    }

    private static boolean shouldSkip(char ch, int index, int length, boolean hasDecimalPoint) {
        return (index == 0 && (ch == '+' || ch == '-') && length > 1)
                || (ch == '.' && !hasDecimalPoint && index < length - 1);
    }

    private static boolean isValidLength(String string, int length, boolean hasDecimalPoint) {
        if (length == 1) {
            return Character.isDigit(string.charAt(0));
        }
        return length > (hasDecimalPoint ? 2 : 1);
    }


    /**
     * Tests if the String contains only Unicode digits and space. A decimal
     * point is not a Unicode digit and returns false.
     *
     * @param string the string to check
     * @return {@code true}, if only contains digits and space, {@code false} otherwise
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
     * @param string the string to check
     * @return {@code true}, if only contains whitespace, {@code false} otherwise
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

        if (position > string.length()) return EMPTY;

        return (string.length() <= position + length) ?
                string.substring(position) : string.substring(position, position + length);
    }

    /**
     * Normalizes whitespace in a string by replacing multiple consecutive
     * whitespace characters (including tabs, newlines, and Unicode whitespace
     * like non-breaking space) with a single space, and trimming leading and
     * trailing whitespace.
     *
     * <p>Non-breaking spaces (U+00A0) are converted to regular spaces
     * (U+0020), and if the string consists only of whitespace, an empty string
     * is returned.
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
            if (Character.isWhitespace(ch) || ch == NON_BREAKING_SPACE) {
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
     * @param string   the input string to process
     * @param overlay  the string to overlay onto the input string
     * @param position the starting index (0-based) where the overlay begins
     * @param length   the number of characters to replace in the input string
     * @return the resulting string with the specified segment replaced by the overlay string, or {@code null}
     * if the input string is {@code null}
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
     * Counts the number of non-overlapping occurrences of a given substring
     * within the specified string.
     *
     * <p>The search is performed from left to right, and each found occurrence
     * advances the search position by the length of the substring, meaning
     * overlapping matches are not counted.
     *
     * <p>If either {@code string} or {@code subString} is {@code null} or
     * {@code empty}, this method returns {@code 0}.
     *
     * @param string    the string to search in
     * @param subString the substring to search for
     * @return the number of non-overlapping occurrences of {@code subString} in {@code string}
     */
    public static int countOccurrencesOf(String string, String subString) {
        if (!hasLength(string) || !hasLength(subString)) return 0;

        int idx;
        int count = 0;
        int position = 0;

        while ((idx = string.indexOf(subString, position)) != -1) {
            ++count;
            position = idx + subString.length();
        }

        return count;
    }

    /**
     * Replaces all occurrences of a specified pattern in the input string with
     * a replacement string. This method is case-sensitive and uses a
     * {@link StringBuilder} for efficient string construction.
     *
     * <p>A {@code null}, {@code empty} or a {@code blank} input string return
     * the original string is returned unchanged.
     *
     * <p>A {@code null} or an {@code empty} old pattern string the original
     * string is returned unchanged.
     *
     * <p>A {@code null} new pattern string the original string is returned
     * unchanged.
     *
     * <p>If the pattern is not found, the original string is returned.
     *
     * @param string     the input string to process
     * @param oldPattern the pattern to search for and replace
     * @param newPattern the replacement string
     * @return the input string with all occurrences of {@code oldPattern} replaced by {@code newPattern},
     * or the original string if invalid inputs or pattern not found
     */
    public static String replace(String string, String oldPattern, String newPattern) {
        if (!hasText(string)) return string;
        if (!hasLength(oldPattern) || newPattern == null) return string;

        int index = string.indexOf(oldPattern);
        if (index == -1) return string;

        int capacity = string.length();
        if (newPattern.length() > oldPattern.length()) capacity += 16;

        StringBuilder stringBuilder = new StringBuilder(capacity);

        int position = 0;  // our position in the old string
        int oldPatternLength = oldPattern.length();
        while (index >= 0) {
            stringBuilder.append(string, position, index);
            stringBuilder.append(newPattern);
            position = index + oldPatternLength;
            index = string.indexOf(oldPattern, position);
        }

        // append any characters to the right of a match
        stringBuilder.append(string, position, string.length());
        return stringBuilder.toString();
    }

    /**
     * Removes all occurrences of the specified character from the input
     * string.
     *
     * <p>If the string is {@code null} or {@code empty}, or if the character
     * to remove is not found, the original string is returned unchanged.
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
     * @param string the string to reverse
     * @return the reversed string
     */
    public static String reverse(String string) {
        return (!hasText(string)) ? string : new StringBuilder(string).reverse().toString();
    }

    /**
     * Returns the rightmost substring of the specified string with the given
     * {@code length}.
     *
     * <p>If the string is {@code null}, {@code empty}, or shorter than or
     * equal to the specified {@code length}, the original string is returned
     * unchanged.
     *
     * <p>If the length is negative, an {@link IllegalArgumentException} is
     * thrown.
     *
     * @param string the input string to process
     * @param length the number of characters to return from the end of the string
     * @return the rightmost substring of the specified length
     * @throws IllegalArgumentException if the length is negative
     */
    public static String right(String string, int length) {
        if (length < 0) throw new IllegalArgumentException("Length must be positive");
        return (!hasLength(string) || string.length() <= length) ? string : string.substring(string.length() - length);
    }

    /**
     * Splits a string into an array of substrings using a single space as the
     * default separator.
     *
     * <p>This method is a convenience wrapper around the
     * {@link #split(String, String, int)} method, using a {@code space} as the
     * separator and no limit on the number of splits.
     *
     * <p>A {@code null}, {@code empty} or a {@code blank} input string will
     * return the {@code empty} array.
     *
     * @param string the input string to split
     * @return an array of strings containing the split substrings
     * @see #split(String, String, int)
     */
    public static String[] split(String string) {
        return split(string, " ", 0);
    }

    /**
     * Splits a string into an array of substrings using the specified
     * separator.
     *
     * <p>This method is a convenience wrapper around the
     * {@link #split(String, String, int)} method, using the provided separator
     * and no limit on the number of splits.
     *
     * <p>A {@code null}, {@code empty} or a {@code blank} input string will
     * return the {@code empty} array.
     *
     * <p>If the separator is {@code null}, default delimiters are used.
     *
     * @param string    the input string to split
     * @param separator the delimiter to split the string
     * @return an array of strings containing the split substrings
     * @see #split(String, String, int)
     */
    public static String[] split(String string, String separator) {
        return split(string, separator, 0);
    }

    /**
     * Splits a string into an array of substrings based on a separator, with
     * an optional limit on the number of splits.
     *
     * <p>A {@code null}, {@code empty} or a {@code blank} input string will
     * return the {@code empty} array.
     *
     * <p>If the separator is {@code null}, default delimiters
     * ({@code whitespace}) are used.
     *
     * <p>If {@code max} is positive, the resulting array is limited to
     * {@code max} elements, with the last element containing the remainder of
     * the string.
     *
     * <p>If {@code max} is zero or negative, all tokens are returned.
     *
     * @param string    the input string to split
     * @param separator the delimiter to split the string
     * @param max       the maximum number of elements in the output array
     * @return an array of strings containing the split substrings
     */
    public static String[] split(String string, String separator, int max) {
        if (!hasText(string)) return EMPTY_STRING_ARRAY;

        StringTokenizer stringTokenizer =
                (separator == null) ? new StringTokenizer(string) : new StringTokenizer(string, separator);

        int listSize = stringTokenizer.countTokens();
        if ((max > 0) && (listSize > max)) {
            listSize = max;
        }

        int i = 0;
        int lastTokenBegin;
        int lastTokenEnd = 0;
        String endToken;
        String[] list = new String[listSize];

        while (stringTokenizer.hasMoreTokens()) {
            if ((max > 0) && (i == listSize - 1)) {
                endToken = stringTokenizer.nextToken();
                lastTokenBegin = string.indexOf(endToken, lastTokenEnd);
                list[i] = string.substring(lastTokenBegin);
                break;
            } else {
                list[i] = stringTokenizer.nextToken();
                lastTokenBegin = string.indexOf(list[i], lastTokenEnd);
                lastTokenEnd = lastTokenBegin + list[i].length();
            }
            i++;
        }

        return list;
    }

    /**
     * Test if the given {@code String} starts with the specified prefix,
     * ignoring upper/lower case.
     *
     * <p>A {@code null}, {@code empty} or a {@code blank} input string will
     * return the {@code false}.
     *
     * <p>A {@code null}, {@code empty} or a {@code blank} prefix will return
     * the {@code false}.
     *
     * <p>If nothing is found, the {@code false} is returned.
     *
     * @param string the string to check
     * @param prefix the prefix to look for
     * @return {@code true}, if the string starts with the given prefix
     * @see String#startsWith(String)
     */
    public static boolean startsWithIgnoreCase(String string, String prefix) {
        return (hasTextAll(string, prefix) && string.length() >= prefix.length() &&
                string.regionMatches(true, 0, prefix, 0, prefix.length()));
    }

    /**
     * Strips whitespace from the {@code start} and {@code end} of a String.
     *
     * <p>A {@code null} input String returns {@code null}.
     *
     * @param string the string to remove whitespace from
     * @return the stripped string
     */
    public static String strip(String string) {
        return strip(string, null);
    }

    /**
     * Strips any of a set of characters from the {@code start} and {@code end}
     * of a String.
     * This is similar to {@link String#trim()} but allows the characters to be
     * stripped to be controlled.
     *
     * <p>A {@code null} input String returns {@code null}, or an {@code empty}
     * input String returns the {@code empty} string.
     *
     * <p>If the stripChars String is {@code null} or {@code empty}, whitespace
     * is stripped as defined by {@link Character#isWhitespace(char)}.
     * Alternatively use {@link #strip(String)}.
     *
     * @param string     the string to remove characters from
     * @param stripChars the characters to remove, {@code null} or {@code empty} treated as whitespace
     * @return the stripped string
     */
    public static String strip(String string, String stripChars) {
        string = stripStart(string, stripChars);
        return stripEnd(string, stripChars);
    }

    /**
     * Strips any of a set of characters from the {@code start} of a String.
     *
     * <p>A {@code null} input String returns {@code null}, or an {@code empty}
     * input String returns the {@code empty} string.
     *
     * <p>If the stripChars String is {@code null} or {@code empty}, whitespace
     * is stripped as defined by {@link Character#isWhitespace(char)}.
     *
     * @param string     the string to remove characters from
     * @param stripChars the characters to remove, {@code null} or {@code empty} treated as whitespace
     * @return the stripped String
     */
    public static String stripStart(String string, String stripChars) {
        if (!hasLength(string)) return string;

        int start = 0;
        int length = string.length();
        if (!hasLength(stripChars)) {
            while (start < length &&
                    (Character.isWhitespace(string.charAt(start)) || string.charAt(start) == NON_BREAKING_SPACE)) {
                start++;
            }
        } else {
            Set<Character> charsToStrip = stripChars.chars().mapToObj(ch -> (char) ch).collect(Collectors.toSet());
            while (start < length && charsToStrip.contains(string.charAt(start))) {
                start++;
            }
        }

        return string.substring(start);
    }

    /**
     * Strips any of a set of characters from the {@code end} of a String.
     *
     * <p>A {@code null} input String returns {@code null}, or an {@code empty}
     * input String returns the empty string.
     *
     * <p>If the stripChars String is {@code null} or {@code empty}, whitespace
     * is stripped as defined by {@link Character#isWhitespace(char)}.
     *
     * @param string     the string to remove characters from
     * @param stripChars the characters to remove, {@code null} or {@code empty} treated as whitespace
     * @return the stripped string
     */
    public static String stripEnd(String string, String stripChars) {
        if (!hasLength(string)) return string;

        int length = string.length();
        if (!hasLength(stripChars)) {
            while (length > 0 &&
                    (Character.isWhitespace(string.charAt(length - 1)) || string.charAt(length - 1) == NON_BREAKING_SPACE)) {
                length--;
            }
        } else {
            Set<Character> chars = stripChars.chars().mapToObj(ch -> (char) ch).collect(Collectors.toSet());
            while (length >= chars.size() &&
                    chars.contains(string.charAt(length - 1)) || chars.contains(NON_BREAKING_SPACE)) {
                length--;
            }
        }

        return string.substring(0, length);
    }

    /**
     * Checks if the specified substring matches the characters in the input
     * string starting at the given index.
     *
     * @param string    the input string to check
     * @param index     the starting index in {@code string}
     * @param substring the substring to match
     * @return {@code true}, if substring matches string starting at index
     * @throws IllegalArgumentException if {@code index} is negative
     */
    public static boolean substringMatch(String string, int index, String substring) {
        if (index <= INDEX_NOT_FOUND) throw new IllegalArgumentException("Index must be positive");
        if (!hasText(string) || !hasLength(substring) || (index + substring.length() > string.length())) return false;

        for (int i = 0; i < substring.length(); i++) {
            if (string.charAt(index + i) != substring.charAt(i)) return false;
        }

        return true;
    }

    /**
     * Extracts a substring from the input string starting at the specified
     * index.
     *
     * <p>If the input string is {@code null}, {@code empty} or {@code blank},
     * an {@code empty} string is returned.
     *
     * <p>Negative start indices are treated as relative to the end of the
     * string.
     * If the adjusted start index is still negative, it is set to {@code 0}.
     *
     * <p>If the start index exceeds the string's length, an {@code empty}
     * string is returned.
     *
     * @param string the input string to extract the substring from
     * @param start  the starting index
     * @return the substring from the start index to the end of the string
     */
    public static String substring(String string, int start) {
        if (!hasText(string)) return EMPTY;

        if (start < 0) start = string.length() + start;

        if (start < 0) start = 0;
        if (start > string.length()) return EMPTY;

        return string.substring(start);
    }

    /**
     * Gets the substring after the first occurrence of a separator.
     * The separator is not returned.
     *
     * <p>A {@code null}, {@code empty} or a {@code blank} input string will
     * return the {@code empty} string.
     *
     * <p>A {@code null} or an {@code empty} separator will return the
     * {@code Original} string.
     *
     * <p>If nothing is found, the {@code empty} string is returned.
     *
     * @param string    the string to get a substring from
     * @param separator the string to search for
     * @return the substring after the first occurrence of the separator
     */
    public static String substringAfter(String string, String separator) {
        if (!hasText(string)) return EMPTY;
        if (!hasLength(separator)) return string;

        int position = string.indexOf(separator);
        return (position == INDEX_NOT_FOUND) ? EMPTY : string.substring(position + separator.length());
    }

    /**
     * Gets the substring before the first occurrence of a separator.
     * The separator is not returned.
     *
     * <p>A {@code null}, {@code empty} or a {@code blank} input string will
     * return the {@code empty} string.
     *
     * <p>A {@code null} or an {@code empty} separator will return the
     * {@code Original} string.
     *
     * <p>If nothing is found, the {@code empty} string is returned.
     *
     * @param string    the string to get a substring from
     * @param separator the string to search for
     * @return the substring before the first occurrence of the separator
     */
    public static String substringBefore(String string, String separator) {
        if (!hasText(string)) return EMPTY;
        if (!hasLength(separator)) return string;

        int pos = string.indexOf(separator);
        return (pos == INDEX_NOT_FOUND) ? EMPTY : string.substring(0, pos);
    }

    /**
     * Gets the String that is nested in between two strings.
     * Only the first match is returned.
     *
     * <p>A {@code null}, {@code empty} or a {@code blank} input string will
     * return the {@code empty} string.
     *
     * <p>A {@code null} or an {@code empty} open/close will return the
     * {@code Original} string.
     *
     * <p>If nothing is found, the {@code empty} string is returned.
     *
     * @param string the string containing the substring
     * @param open   the string before the substring
     * @param close  the string after the substring
     * @return the substring
     */
    public static String substringBetween(String string, String open, String close) {
        if (!hasText(string)) return EMPTY;
        if (!hasLengthAll(open, close)) return string;

        int startIndex = string.indexOf(open);
        if (startIndex != INDEX_NOT_FOUND) {
            int endIndex = string.indexOf(close, startIndex + open.length());
            if (endIndex != INDEX_NOT_FOUND) return string.substring(startIndex + open.length(), endIndex);
        }

        return EMPTY;
    }

    /**
     * Extracts all substrings from the input string that are enclosed between
     * the specified {@code open} and {@code close} tags. The substrings are
     * returned as an array.
     *
     * <p>A {@code null}, {@code empty} or a {@code blank} input string will
     * return the {@code empty} array.
     *
     * <p>A {@code null} or an {@code empty} open/close will return the
     * {@code empty} array.
     *
     * <p>If nothing is found, the {@code empty} array is returned.
     *
     * @param string the input string to process
     * @param open   the opening tag
     * @param close  the closing tag
     * @return an array of non-empty substrings between matching {@code open} and {@code close} tags, or an
     * {@code empty} array if no valid substrings are found or inputs are invalid
     */
    public static String[] substringsBetween(String string, String open, String close) {
        if (!hasText(string) || !hasLengthAll(open, close)) return EMPTY_STRING_ARRAY;

        List<String> results = new ArrayList<>();

        int position = 0;
        while (true) {
            int start = string.indexOf(open, position);
            int end = string.indexOf(close, start + open.length());

            if (start < 0 || end < 0) break;

            results.add(string.substring(start + open.length(), end));
            position = end + close.length();
        }

        return (results.isEmpty()) ? EMPTY_STRING_ARRAY : results.toArray(EMPTY_STRING_ARRAY);
    }

    /**
     * Removes all leading occurrences of a specified character from the
     * beginning of a string.
     *
     * <p>This method scans the input string from the start and removes all
     * consecutive occurrences of the specified character until a different
     * character is found or the end of the string is reached.
     *
     * <p>If the input string is {@code null} or {@code empty}, it is returned
     * unchanged.
     *
     * @param string  the string to trim leading characters from
     * @param leading the character to remove from the start of the string
     * @return the string with all leading occurrences of the specified character removed
     */
    public static String trimLeadingCharacter(String string, char leading) {
        if (!hasLength(string)) return string;

        int beginIdx = 0;
        while (beginIdx < string.length() && leading == string.charAt(beginIdx)) {
            beginIdx++;
        }
        return string.substring(beginIdx);
    }

    /**
     * Removes all leading occurrences of a specified character from the end of
     * a string.
     *
     * <p>This method scans the input string from the end and removes all
     * consecutive occurrences of the specified character until a different
     * character is found or the beginning of the string is reached.
     *
     * <p>If the input string is {@code null} or {@code empty}, it is returned
     * unchanged.
     *
     * @param string   the string to trim leading characters from
     * @param trailing the character to remove from the end of the string
     * @return the string with all leading occurrences of the specified character removed
     */
    public static String trimTrailingCharacter(String string, char trailing) {
        if (!hasLength(string)) return string;

        int endIdx = string.length() - 1;
        while (endIdx >= 0 && trailing == string.charAt(endIdx)) {
            endIdx--;
        }
        return string.substring(0, endIdx + 1);
    }

    /**
     * Trims whitespace from each non-null element in the input string strings.
     *
     * <p>If the input strings is {@code null} or {@code empty}, it is returned
     * unchanged.
     *
     * <p>Each non-null element is processed using {@link String#trim()}, which
     * removes {@code leading} and {@code trailing} whitespace.
     *
     * <p>Null elements remain {@code null} in the output strings.
     * The returned strings is a new strings with the same length as the input
     * strings.
     *
     * @param strings the input strings to process
     * @return a new string strings with the same length as the input, where each non-null element has
     * been trimmed of leading and trailing whitespace
     */
    public static String[] trimArrayElements(String[] strings) {
        if (strings == null || strings.length == 0) return strings;

        String[] result = new String[strings.length];
        for (int i = 0; i < strings.length; i++) {
            result[i] = (strings[i] != null ? strings[i].trim() : null);
        }
        return result;
    }

    /**
     * Converts a {@link Collection} of strings to a string array.
     * This method is useful for converting collections to arrays in a safe
     * manner without null pointer exceptions.
     *
     * <p>If the collection is {@code null} or {@code empty}, an {@code empty}
     * array is returned.
     *
     * @param collection the collection of strings to convert
     * @return a string array containing the elements of the collection
     */
    public static String[] toString(Collection<String> collection) {
        return (!CollectionUtils.hasLength(collection)) ? EMPTY_STRING_ARRAY : collection.toArray(EMPTY_STRING_ARRAY);
    }

    /**
     * Converts an {@link Enumeration} of strings to a string array.
     * This method is useful for converting enumerations to arrays in a safe
     * manner.
     *
     * <p>If the enumeration is {@code null}, an {@code empty} array is
     * returned.
     *
     * @param enumeration the enumeration of strings to convert
     * @return a string array containing the elements of the enumeration
     */
    public static String[] toString(Enumeration<String> enumeration) {
        return (enumeration == null || !enumeration.hasMoreElements()) ?
                EMPTY_STRING_ARRAY : toString(Collections.list(enumeration));
    }

    /**
     * Uncapitalizes a String, changing the first character to lower case as
     * per {@link Character#toLowerCase(int)}. No other characters are changed.
     *
     * @param string the String to uncapitalize
     * @return the uncapitalized string
     * @see #capitalize(String)
     */
    public static String uncapitalize(String string) {
        if (!hasText(string)) return string;

        int firstCodePoint = string.codePointAt(0);
        int newCodePoint = Character.toLowerCase(firstCodePoint);

        if (firstCodePoint == newCodePoint) return string;

        int[] newCodePoints = string.codePoints().toArray();
        newCodePoints[0] = newCodePoint;

        return new String(newCodePoints, 0, newCodePoints.length);
    }

    /**
     * Unwraps a given string from another string.
     *
     * @param string    the string to be unwrapped
     * @param wrapToken the string used to unwrap
     * @return the unwrapped string with {@code wrapToken} removed from both start and end
     */
    public static String unwrap(String string, char wrapToken) {
        if (!hasText(string)) return string;

        int startIndex = 0;
        int endIndex = string.length() - 1;

        return (string.charAt(0) == wrapToken && string.charAt(string.length() - 1) == wrapToken) ?
                string.substring(startIndex + 1, endIndex) : string;
    }

    /**
     * Wraps a String with another String.
     *
     * @param string   the string to be wrapper
     * @param wrapWith the string that will wrap string
     * @return the wrapped string with {@code wrapWith} added from both start and end
     */
    public static String wrap(String string, String wrapWith) {
        return (!hasText(string) || !hasLength(wrapWith)) ? string : wrapWith.concat(string).concat(wrapWith);
    }

    /**
     * Wraps a string with a string if that string is missing from the
     * {@code start} or {@code end} of the given string.
     *
     * <p>A new {@link String} will not be created if {@code string} is already
     * wrapped.
     *
     * @param string   the string to be wrapped
     * @param wrapWith the string that will wrap string
     * @return the wrapped string with {@code wrapWith}
     */
    public static String wrapIfMissing(String string, char wrapWith) {
        if ((!hasText(string))) return string;

        boolean wrapStart = string.charAt(0) != wrapWith;
        boolean wrapEnd = string.charAt(string.length() - 1) != wrapWith;

        if (!wrapStart && !wrapEnd) return string;

        StringBuilder builder = new StringBuilder(string.length() + (wrapStart ? 1 : 0) + (wrapEnd ? 1 : 0));

        if (wrapStart) builder.append(wrapWith);
        builder.append(string);
        if (wrapEnd) builder.append(wrapWith);

        return builder.toString();
    }
}
