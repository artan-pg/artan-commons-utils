/*
 * Copyright (c) 2026 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ir.artanpg.commons.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;
import java.util.regex.Pattern;

/**
 * Immutable, thread-safe assertion utilities.
 *
 * <p><b>Design contract:</b></p>
 * <ul>
 *   <li>Every method returns the validated value → enables fluent chaining.</li>
 *   <li>On failure, throws {@link PreconditionException} (parameter) or
 *       {@link PreconditionException.State} (state).</li>
 *   <li>Message evaluation is <em>lazy</em> via {@link Supplier} – the string
 *       is only constructed when the assertion actually fails.</li>
 *   <li>No external dependencies – pure JDK 8+.</li>
 * </ul>
 *
 * <h3>Usage</h3>
 * <pre>{@code
 * public Order create(CreateOrderRequest req) {
 *     AssertUtils.notNull(req, "req");
 *     String email = AssertUtils.notBlank(req.email(), "email");
 *     int qty     = AssertUtils.inRange(req.quantity(), 1, 999, "quantity");
 *     // ...
 * }
 * }</pre>
 */
public final class AssertUtils {

    private AssertUtils() {
        throw new UnsupportedOperationException("No instances of AssertUtils");
    }

    // ═══════════════════════════════════════════════════════════════════
    //  BOOLEAN / GENERIC
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Asserts a boolean condition.
     *
     * @param condition   the condition to check
     * @param field       logical field / context name (for error messages)
     * @param messageSupplier lazy message (only evaluated on failure); may be null
     */
    public static void isTrue(boolean condition, String field, Supplier<String> messageSupplier) {
        if (!condition) {
            throw new PreconditionException(field, condition,
                    messageSupplier != null ? messageSupplier.get() : null);
        }
    }

    public static void isFalse(boolean condition, String field, Supplier<String> messageSupplier) {
        isTrue(!condition, field, messageSupplier);
    }

    /**
     * State-level assertion (throws {@link PreconditionException.State}).
     */
    public static void isTrueState(boolean condition, String context, String message) {
        if (!condition) {
            throw new PreconditionException.State(context, message);
        }
    }

    // ═══════════════════════════════════════════════════════════════════
    //  NULL / BLANK / EMPTY
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Asserts value is not null.
     *
     * @return the same non-null value (for chaining)
     */
    public static <T> T notNull(T value, String field) {
        if (value == null) {
            throw new PreconditionException(field, null, null);
        }
        return value;
    }

    public static <T> T notNull(T value, String field, String message) {
        if (value == null) {
            throw new PreconditionException(field, null, message);
        }
        return value;
    }

    /**
     * Asserts a CharSequence is not null AND not blank (after trim).
     */
    public static <T extends CharSequence> T notBlank(T value, String field) {
        if (value == null) {
            throw new PreconditionException(field, null, null);
        }
        if (value.toString().isBlank()) {
            throw new PreconditionException(field, value.toString(),
                    field + " must not be blank");
        }
        return value;
    }

    public static <T extends CharSequence> T notBlank(T value, String field, String message) {
        if (value == null) {
            throw new PreconditionException(field, null, message);
        }
        if (value.toString().isBlank()) {
            throw new PreconditionException(field, value.toString(),
                    message != null ? message : field + " must not be blank");
        }
        return value;
    }

    /**
     * Asserts a Collection is not null and not empty.
     */
    public static <T> Collection<T> notEmpty(Collection<T> collection, String field) {
        if (collection == null) {
            throw new PreconditionException(field, null, null);
        }
        if (collection.isEmpty()) {
            throw new PreconditionException(field, "[]", field + " must not be empty");
        }
        return collection;
    }

    /**
     * Asserts a Map is not null and not empty.
     */
    public static <K, V> Map<K, V> notEmpty(Map<K, V> map, String field) {
        if (map == null) {
            throw new PreconditionException(field, null, null);
        }
        if (map.isEmpty()) {
            throw new PreconditionException(field, "{}", field + " must not be empty");
        }
        return map;
    }

    /**
     * Asserts an array is not null and has at least one element.
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] notEmpty(T[] array, String field) {
        if (array == null) {
            throw new PreconditionException(field, null, null);
        }
        if (array.length == 0) {
            throw new PreconditionException(field, "[]", field + " must not be empty");
        }
        return array;
    }

    // ═══════════════════════════════════════════════════════════════════
    //  NUMERIC RANGE
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Asserts a Number is within [min, max] (inclusive).
     */
    public static <T extends Number & Comparable<T>> T inRange(
            T value, T min, T max, String field) {

        notNull(value, field);
        Objects.requireNonNull(min, "min bound");
        Objects.requireNonNull(max, "max bound");

        if (value.compareTo(min) < 0 || value.compareTo(max) > 0) {
            throw new PreconditionException(field, value,
                    String.format("%s must be in range [%s, %s] (got %s)", field, min, max, value));
        }
        return value;
    }

    public static <T extends Number & Comparable<T>> T inRange(
            T value, T min, T max, String field, String message) {

        notNull(value, field);
        if (value.compareTo(min) < 0 || value.compareTo(max) > 0) {
            throw new PreconditionException(field, value,
                    message != null ? message
                            : String.format("%s must be in range [%s, %s] (got %s)", field, min, max, value));
        }
        return value;
    }

    public static <T extends Number> T positive(T value, String field) {
        notNull(value, field);
        if (value.doubleValue() <= 0) {
            throw new PreconditionException(field, value, field + " must be > 0");
        }
        return value;
    }

    public static <T extends Number> T nonNegative(T value, String field) {
        notNull(value, field);
        if (value.doubleValue() < 0) {
            throw new PreconditionException(field, value, field + " must be >= 0");
        }
        return value;
    }

    public static <T extends Number> T negative(T value, String field) {
        notNull(value, field);
        if (value.doubleValue() >= 0) {
            throw new PreconditionException(field, value, field + " must be < 0");
        }
        return value;
    }

    /**
     * Asserts a BigDecimal is strictly positive.
     */
    public static BigDecimal positive(BigDecimal value, String field) {
        notNull(value, field);
        if (value.signum() <= 0) {
            throw new PreconditionException(field, value, field + " must be > 0");
        }
        return value;
    }

    /**
     * Asserts a BigDecimal is zero.
     */
    public static BigDecimal isZero(BigDecimal value, String field) {
        notNull(value, field);
        if (value.signum() != 0) {
            throw new PreconditionException(field, value, field + " must be 0");
        }
        return value;
    }

    // ═══════════════════════════════════════════════════════════════════
    //  COMPARABLE (Dates, Strings, custom)
    // ═══════════════════════════════════════════════════════════════════

    public static <T extends Comparable<T>> T isAfter(T value, T reference, String field) {
        notNull(value, field);
        Objects.requireNonNull(reference, "reference");
        if (value.compareTo(reference) <= 0) {
            throw new PreconditionException(field, value,
                    String.format("%s must be after %s (got %s)", field, reference, value));
        }
        return value;
    }

    public static <T extends Comparable<T>> T isBefore(T value, T reference, String field) {
        notNull(value, field);
        Objects.requireNonNull(reference, "reference");
        if (value.compareTo(reference) >= 0) {
            throw new PreconditionException(field, value,
                    String.format("%s must be before %s (got %s)", field, reference, value));
        }
        return value;
    }

    // ═══════════════════════════════════════════════════════════════════
    //  COLLECTION / ARRAY SIZE
    // ═══════════════════════════════════════════════════════════════════

    public static <T> Collection<T> hasSize(Collection<T> collection, int expectedSize, String field) {
        notEmpty(collection, field);
        if (collection.size() != expectedSize) {
            throw new PreconditionException(field, collection.size(),
                    String.format("%s size must be %d (got %d)", field, expectedSize, collection.size()));
        }
        return collection;
    }

    public static <T> Collection<T> hasMinSize(Collection<T> collection, int min, String field) {
        notEmpty(collection, field);
        if (collection.size() < min) {
            throw new PreconditionException(field, collection.size(),
                    String.format("%s size must be >= %d (got %d)", field, min, collection.size()));
        }
        return collection;
    }

    public static <T> Collection<T> hasMaxSize(Collection<T> collection, int max, String field) {
        notEmpty(collection, field);
        if (collection.size() > max) {
            throw new PreconditionException(field, collection.size(),
                    String.format("%s size must be <= %d (got %d)", field, max, collection.size()));
        }
        return collection;
    }

    public static <T> Collection<T> hasSizeBetween(
            Collection<T> collection, int min, int max, String field) {
        notEmpty(collection, field);
        int size = collection.size();
        if (size < min || size > max) {
            throw new PreconditionException(field, size,
                    String.format("%s size must be in [%d, %d] (got %d)", field, min, max, size));
        }
        return collection;
    }

    // ═══════════════════════════════════════════════════════════════════
    //  ENUM / SET MEMBERSHIP
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Asserts value is in the allowed set.
     */
    public static <T> T isIn(Collection<T> allowed, T value, String field) {
        Objects.requireNonNull(allowed, "allowed set");
        if (value == null || !allowed.contains(value)) {
            throw new PreconditionException(field, value,
                    String.format("%s must be one of %s (got %s)", field, allowed, value));
        }
        return value;
    }

    public static <T> T isIn(Collection<T> allowed, T value, String field, String message) {
        Objects.requireNonNull(allowed, "allowed set");
        if (value == null || !allowed.contains(value)) {
            throw new PreconditionException(field, value,
                    message != null ? message
                            : String.format("%s must be one of %s (got %s)", field, allowed, value));
        }
        return value;
    }

    /**
     * Convenience for Enum types.
     */
    public static <E extends Enum<E>> E enumValue(E value, String field) {
        if (value == null) {
            throw new PreconditionException(field, null, field + " must not be null");
        }
        return value;
    }

    // ═══════════════════════════════════════════════════════════════════
    //  TYPE / INSTANCE CHECKS
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Asserts object is an instance of the given type.
     *
     * @return the value cast to T (safe)
     */
    @SuppressWarnings("unchecked")
    public static <T> T isInstanceOf(Class<T> type, Object obj, String field) {
        Objects.requireNonNull(type, "type");
        if (obj == null) {
            throw new PreconditionException(field, null,
                    String.format("%s must be an instance of %s", field, type.getSimpleName()));
        }
        if (!type.isInstance(obj)) {
            throw new PreconditionException(field, obj.getClass().getName(),
                    String.format("%s must be an instance of %s (got %s)",
                            field, type.getSimpleName(), obj.getClass().getSimpleName()));
        }
        return (T) obj;
    }

    /**
     * Asserts type is assignable to the given target type.
     */
    public static Class<?> isAssignableTo(Class<?> source, Class<?> target, String field) {
        Objects.requireNonNull(source, "source class");
        Objects.requireNonNull(target, "target class");
        if (!target.isAssignableFrom(source)) {
            throw new PreconditionException(field, source.getName(),
                    String.format("%s must be assignable to %s", field, target.getName()));
        }
        return source;
    }

    // ═══════════════════════════════════════════════════════════════════
    //  STRING FORMAT (REGEX)
    // ═══════════════════════════════════════════════════════════════════

    public static String matches(String value, Pattern pattern, String field) {
        notBlank(value, field);
        if (pattern == null || !pattern.matcher(value).matches()) {
            throw new PreconditionException(field, value,
                    String.format("%s does not match pattern %s", field,
                            pattern != null ? pattern.pattern() : "null"));
        }
        return value;
    }

    public static String matches(String value, Pattern pattern, String field, String message) {
        notBlank(value, field);
        if (pattern == null || !pattern.matcher(value).matches()) {
            throw new PreconditionException(field, value,
                    message != null ? message
                            : String.format("%s does not match required format", field));
        }
        return value;
    }

    // ═══════════════════════════════════════════════════════════════════
    //  LENGTH
    // ═══════════════════════════════════════════════════════════════════

    public static <T extends CharSequence> T hasLength(
            T value, int min, int max, String field) {
        notBlank(value, field);
        int len = value.length();
        if (len < min || len > max) {
            throw new PreconditionException(field, value.toString(),
                    String.format("%s length must be in [%d, %d] (got %d)", field, min, max, len));
        }
        return value;
    }

    // ═══════════════════════════════════════════════════════════════════
    //  BATCH / COMPOSITE
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Runs multiple assertions and collects ALL failures before throwing.
     * Useful for request-level validation where you want the full error list.
     *
     * @param checks each Runnable that throws PreconditionException on failure
     * @return all collected error messages (empty if all passed)
     */
    @SafeVarargs
    public static <T> List<String> collectAll(Runnable... checks) {
        Objects.requireNonNull(checks, "checks");
        List<String> errors = new ArrayList<>();

        for (Runnable check : checks) {
            try {
                check.run();
            } catch (PreconditionException e) {
                errors.add(e.getMessage());
            }
        }

        if (!errors.isEmpty()) {
            throw new PreconditionException("<batch>", null,
                    String.join(" | ", errors));
        }
        return Collections.emptyList();
    }

    /**
     * Validates multiple objects against their preconditions,
     * collecting all errors into a single exception.
     */
    public static void allOf(Runnable... checks) {
        collectAll(checks);
    }

    // ═══════════════════════════════════════════════════════════════════
    //  MATH UTILITIES (assert + return)
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Asserts a long fits in int range and returns it as int.
     */
    public static int toInt(long value, String field) {
        if (value < Integer.MIN_VALUE || value > Integer.MAX_VALUE) {
            throw new PreconditionException(field, value,
                    String.format("%s exceeds int range (got %d)", field, value));
        }
        return (int) value;
    }

    /**
     * Asserts a BigInteger fits in long range.
     */
    public static long toLong(BigInteger value, String field) {
        notNull(value, field);
        try {
            return value.longValueExact();
        } catch (ArithmeticException e) {
            throw new PreconditionException(field, value,
                    field + " exceeds long range");
        }
    }
}
