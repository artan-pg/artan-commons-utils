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

import java.util.AbstractList;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * Provides utility methods for {@link Collection} instances.
 *
 * @author Mohammad Yazdian
 * @since 1.0.0
 */
public class CollectionUtils {

    /**
     * Error message used when a predicate is null.
     */
    private static final String PREDICATE_CANNOT_BE_NULL = "The predicate cannot be null";

    /**
     * Error message used when a factory is null.
     */
    private static final String FACTORY_CANNOT_BE_NULL = "The factory cannot be null";

    /**
     * Error message used when a key is null.
     */
    private static final String KEY_CANNOT_BE_NULL = "Key cannot be null";

    /**
     * Error message used when a size is not positive.
     */
    private static final String SIZE_MUST_BE_GREATER_THAN_ZERO = "Size must be greater than 0";

    /**
     * Prefix used in index error messages.
     */
    private static final String INDEX_PREFIX = "Index ";

    /**
     * Private constructor to prevent instantiation of this utility class.
     *
     * @throws UnsupportedOperationException if an attempt is made to instantiate this class
     */
    private CollectionUtils() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    /**
     * Checks if the specified candidate exists in the collection.
     *
     * <p>If the source collection is already a {@link Set}, it directly uses
     * the set's {@code contains} method. Otherwise, it converts the collection
     * to a {@link HashSet} for efficient O(1) lookup performance.
     *
     * @param <T>       the type of elements in the collection
     * @param source    the collection to search through
     * @param candidate the candidate to search for
     * @return {@code true} if the candidate exists in the collection, {@code false} otherwise
     */
    public static <T> boolean contains(Collection<T> source, T candidate) {
        if (isEmpty(source)) return false;

        Set<T> sourceSet = (source instanceof Set) ? (Set<T>) source : new HashSet<>(source);

        return sourceSet.contains(candidate);
    }

    /**
     * Checks if any element from the candidates collection exists in the
     * source collection.
     *
     * <p>If the source is not already a {@link Set}, it is converted to a
     * {@link HashSet} for O(1) lookups during the candidate checks.
     *
     * @param <T>        the type of elements in the collections
     * @param source     the collection to search within
     * @param candidates the collection of elements to search for
     * @return {@code true} if at least one element from candidates exists in source, {@code false} otherwise
     */
    public static <T> boolean contains(Collection<T> source, Collection<T> candidates) {
        if (isEmpty(source) || isEmpty(candidates)) return false;

        Set<T> sourceSet = (source instanceof Set) ? (Set<T>) source : new HashSet<>(source);
        for (T candidate : candidates) {
            if (sourceSet.contains(candidate)) return true;
        }

        return false;
    }

    /**
     * Checks if any element in the collection satisfies the given predicate.
     *
     * <p>Returns {@code true} as soon as a match is found. Returns
     * {@code false} if the collection is null or empty, or the predicate is
     * null.
     *
     * @param <T>       the type of elements in the collection
     * @param source    the collection to search through (can be null or empty)
     * @param predicate the predicate to apply to each element (can be null)
     * @return {@code true} if at least one element satisfies the predicate, {@code false} otherwise
     */
    public static <T> boolean contains(Collection<T> source, Predicate<T> predicate) {
        if (isEmpty(source) || predicate == null) return false;

        for (T element : source) {
            if (predicate.test(element)) return true;
        }

        return false;
    }

    /**
     * Checks if any element in the source collection has a property that
     * matches the expected value.
     *
     * <p>Comparison uses {@link Objects#equals(Object, Object)}.
     *
     * @param <T>           the type of elements in the source collection
     * @param <R>           the type of the extracted property to compare
     * @param source        the collection to search through
     * @param extractor     the function to extract a property from each element
     * @param expectedValue the value to compare against extracted properties
     * @return {@code true} if any element's extracted property matches the expected value, {@code false} otherwise
     */
    public static <T, R> boolean contains(Collection<T> source, Function<T, R> extractor, R expectedValue) {
        if (isEmpty(source) || extractor == null) return false;

        for (T item : source) {
            if (Objects.equals(extractor.apply(item), expectedValue)) return true;
        }

        return false;
    }

    /**
     * Checks if the specified map contains the exact key-value mapping
     * represented by the given entry.
     *
     * <p>Both the key and the value are compared using
     * {@link Objects#equals(Object, Object)}.
     *
     * @param <K>       the type of keys in the map
     * @param <V>       the type of values in the map
     * @param source    the map to search through
     * @param candidate the map entry containing the exact key-value pair to search for
     * @return {@code true} if the map contains the exact mapping, {@code false} otherwise
     */
    public static <K, V> boolean contains(Map<K, V> source, Map.Entry<K, V> candidate) {
        if (isEmpty(source) || candidate == null || candidate.getKey() == null) return false;
        return source.containsKey(candidate.getKey()) &&
                Objects.equals(source.get(candidate.getKey()), candidate.getValue());
    }

    /**
     * Checks if any of the key-value pairs from the candidates collection
     * exists in the source map.
     *
     * <p>An entry is considered contained when the key exists in the source
     * and the corresponding value matches using
     * {@link Objects#equals(Object, Object)}.
     *
     * @param <K>        the type of keys maintained by the map
     * @param <V>        the type of mapped values
     * @param source     the map to search within
     * @param candidates the collection of map entries to search for
     * @return {@code true} if at least one candidate entry exists in the source map, {@code false} otherwise
     */
    public static <K, V> boolean contains(Map<K, V> source, Collection<Map.Entry<K, V>> candidates) {
        if (isEmpty(source) || isEmpty(candidates)) return false;

        for (Map.Entry<K, V> candidate : candidates) {
            K key = candidate.getKey();
            if (key != null && source.containsKey(key) && Objects.equals(source.get(key), candidate.getValue())) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if any key-value pair in the map satisfies the given predicate.
     *
     * <p>Iterates through the map's entry set and returns {@code true} as soon
     * as a match is found.
     *
     * @param <K>       the type of keys in the map
     * @param <V>       the type of values in the map
     * @param source    the map to search through
     * @param predicate the predicate to apply to each map entry
     * @return {@code true} if at least one entry satisfies the predicate, {@code false} otherwise
     */
    public static <K, V> boolean contains(Map<K, V> source, Predicate<Map.Entry<K, V>> predicate) {
        if (isEmpty(source) || predicate == null) return false;

        for (Map.Entry<K, V> entry : source.entrySet()) {
            if (predicate.test(entry)) return true;
        }

        return false;
    }

    /**
     * Creates a List containing elements from the source collection that are
     * not present in the candidates' collection.
     *
     * <p>The result is returned as an {@link ArrayList}. The candidates are
     * converted to a {@link HashSet} when not already a {@link Set}.
     *
     * @param <T>        the type of elements in the collections
     * @param source     the source collection to get elements from
     * @param candidates the collection of elements to exclude
     * @return a new {@link ArrayList} containing elements from source that are not in candidates
     * @see #difference(Collection, Collection, Supplier)
     */
    public static <T> List<T> difference(Collection<T> source, Collection<T> candidates) {
        return difference(source, candidates, ArrayList::new);
    }

    /**
     * Creates a new collection containing all elements from the source
     * collection that are not present in the candidates' collection.
     *
     * <p>The type of the returned collection is determined by the provided
     * factory.
     *
     * @param <T>        the type of elements in the collections
     * @param <C>        the type of the returned collection
     * @param source     the source collection from which elements are taken
     * @param candidates the collection containing elements to exclude from the result
     * @param factory    a supplier that creates the result collection instance
     * @return a new collection of type {@code C} containing elements from source that are not in candidates
     * @throws IllegalArgumentException if factory is {@code null}
     * @see #difference(Collection, Collection, Predicate, Supplier)
     */
    public static <T, C extends Collection<T>> C difference(Collection<T> source,
                                                            Collection<T> candidates,
                                                            Supplier<C> factory) {
        return difference(source, candidates, _ -> true, factory);
    }

    /**
     * Creates a collection containing elements from the source collection that
     * are not present in the candidates collection and satisfy the given
     * predicate.
     *
     * <p>Returns all elements from the {@code source} collection that are not
     * present in the {@code candidates} collection and satisfy the given
     * {@code predicate}. The type of the returned collection is determined by
     * the provided factory.
     *
     * @param <T>        the type of elements in the collections
     * @param <C>        the type of collection to return
     * @param source     the source collection to filter elements from
     * @param candidates the collection of elements to exclude
     * @param predicate  the condition that elements must satisfy
     * @param factory    the supplier for creating the result collection
     * @return a new collection containing elements from source that satisfy the predicate and
     *         are not in candidates
     * @throws IllegalArgumentException if predicate or factory is {@code null}
     */
    public static <T, C extends Collection<T>> C difference(Collection<T> source,
                                                            Collection<T> candidates,
                                                            Predicate<T> predicate,
                                                            Supplier<C> factory) {
        if (predicate == null) throw new IllegalArgumentException(PREDICATE_CANNOT_BE_NULL);
        if (factory == null) throw new IllegalArgumentException(FACTORY_CANNOT_BE_NULL);

        C result = factory.get();

        if (isEmpty(source)) return result;
        if (isEmpty(candidates)) {
            result.addAll(source);
            return result;
        }

        Set<T> candidateSet = (candidates instanceof Set) ? (Set<T>) candidates : new HashSet<>(candidates);
        for (T item : source) {
            if (predicate.test(item) && !candidateSet.contains(item)) result.add(item);
        }

        return result;
    }

    /**
     * Creates a new map containing all entries from the {@code source} map
     * whose keys are not present in the {@code candidates} map.
     *
     * <p>Only key existence is considered; values are not compared. The type
     * of the returned map is determined by the provided factory.
     *
     * @param <K>        the type of keys maintained by the maps
     * @param <V>        the type of mapped values
     * @param <C>        the type of the returned map
     * @param source     the source map from which entries are taken
     * @param candidates the map containing keys to exclude from the result
     * @param factory    a supplier that creates the result map instance
     * @return a new map of type {@code C} containing entries from source whose keys are not in candidates
     * @throws IllegalArgumentException if factory is {@code null}
     */
    public static <K, V, C extends Map<K, V>> C difference(Map<K, V> source,
                                                           Map<K, V> candidates,
                                                           Supplier<C> factory) {
        if (factory == null) throw new IllegalArgumentException(FACTORY_CANNOT_BE_NULL);

        C result = factory.get();

        if (isEmpty(source)) return result;
        if (isEmpty(candidates)) {
            result.putAll(source);
            return result;
        }

        for (Map.Entry<K, V> element : source.entrySet()) {
            if (!candidates.containsKey(element.getKey())) result.put(element.getKey(), element.getValue());
        }

        return result;
    }

    /**
     * Finds and returns the specified {@code candidate} element if it exists
     * in the given {@code source} collection.
     *
     * @param <T>       the type of elements in the collection
     * @param source    the collection in which to search for the candidate element
     * @param candidate the element to find in the collection
     * @return the first candidate found in the source, {@link Optional#empty()} otherwise
     */
    public static <T> Optional<T> find(Collection<T> source, T candidate) {
        if (isEmpty(source) || candidate == null) return Optional.empty();

        Set<T> sourceSet = (source instanceof Set) ? (Set<T>) source : new HashSet<>(source);

        return sourceSet.contains(candidate) ? Optional.of(candidate) : Optional.empty();
    }

    /**
     * Finds the first element from the candidates collection that exists in
     * the source collection.
     *
     * <p>Candidates are examined in iteration order and the search stops at
     * the first match.
     *
     * @param <T>        the type of elements in the collections
     * @param source     the collection to search in
     * @param candidates the collection of elements to search for
     * @return the first candidate from the candidates collection that exists in source,
     *         {@link Optional#empty()} otherwise
     */
    public static <T> Optional<T> find(Collection<T> source, Collection<T> candidates) {
        if (isEmpty(source) || isEmpty(candidates)) return Optional.empty();

        Set<T> sourceSet = (source instanceof Set) ? (Set<T>) source : new HashSet<>(source);
        for (T candidate : candidates) {
            if (sourceSet.contains(candidate)) return Optional.ofNullable(candidate);
        }

        return Optional.empty();
    }

    /**
     * Finds the first element in the collection that matches the given
     * predicate.
     *
     * <p>Iterates through the source collection in its natural order and
     * returns the first matching element.
     *
     * @param <T>       the type of elements in the collection
     * @param source    the collection to search
     * @param predicate the condition to test each element against
     * @return the first element that matches the predicate, {@link Optional#empty()} otherwise
     */
    public static <T> Optional<T> find(Collection<T> source, Predicate<T> predicate) {
        if (isEmpty(source) || predicate == null) return Optional.empty();

        for (T element : source) {
            if (predicate.test(element)) return Optional.ofNullable(element);
        }

        return Optional.empty();
    }

    /**
     * Finds the first element in the collection that has the specified
     * property value.
     *
     * <p>Comparison uses {@link Objects#equals}.
     *
     * @param <T>      the type of elements in the collection
     * @param <V>      the type of the property value to match
     * @param source   the collection to search through
     * @param property property the function to extract the property value from each element
     * @param value    the value to match against extracted properties
     * @return the first element whose property matches the target value, {@link Optional#empty()} otherwise
     */
    public static <T, V> Optional<T> find(Collection<T> source, Function<T, V> property, V value) {
        if (isEmpty(source) || property == null) return Optional.empty();

        for (T element : source) {
            if (Objects.equals(property.apply(element), value)) return Optional.ofNullable(element);
        }

        return Optional.empty();
    }

    /**
     * Finds the first entry in the map that matches the given predicate.
     *
     * <p>Iterates over the map's entry set in its natural iteration order and
     * stops at the first match.
     *
     * @param <K>       the type of keys maintained by the map
     * @param <V>       the type of mapped values
     * @param source    the map to search through
     * @param predicate the condition to test each map entry against
     * @return the first map entry that matches the predicate, {@link Optional#empty()} otherwise
     */
    public static <K, V> Optional<Map.Entry<K, V>> find(Map<K, V> source, Predicate<Map.Entry<K, V>> predicate) {
        if (isEmpty(source) || predicate == null) return Optional.empty();

        for (Map.Entry<K, V> entry : source.entrySet()) {
            if (predicate.test(entry)) {
                return Optional.of(new AbstractMap.SimpleImmutableEntry<>(entry.getKey(), entry.getValue()));
            }
        }

        return Optional.empty();
    }

    /**
     * Finds the first map entry whose key satisfies the given predicate.
     *
     * @param <K>       the type of keys in the map
     * @param <V>       the type of values in the map
     * @param source    the map to search through
     * @param predicate the predicate to test keys against
     * @return an {@link Optional} containing the first entry with a matching key,
     *         {@link Optional#empty()} otherwise
     */
    public static <K, V> Optional<Map.Entry<K, V>> findByKey(Map<K, V> source, Predicate<K> predicate) {
        if (isEmpty(source) || predicate == null) return Optional.empty();

        for (Map.Entry<K, V> entry : source.entrySet()) {
            if (predicate.test(entry.getKey())) return Optional.of(entry);
        }

        return Optional.empty();
    }

    /**
     * Finds the first map entry whose value satisfies the given predicate.
     *
     * @param <K>       the type of keys in the map
     * @param <V>       the type of values in the map
     * @param source    the map to search through
     * @param predicate the predicate to test values against
     * @return an {@link Optional} containing the first entry with a matching value,
     *         {@link Optional#empty()} otherwise
     */
    public static <K, V> Optional<Map.Entry<K, V>> findByValue(Map<K, V> source, Predicate<V> predicate) {
        if (isEmpty(source) || predicate == null) return Optional.empty();

        for (Map.Entry<K, V> entry : source.entrySet()) {
            if (predicate.test(entry.getValue())) return Optional.of(entry);
        }

        return Optional.empty();
    }

    /**
     * Finds all elements from the source collection that equal the given
     * candidate.
     *
     * <p>Equality is determined by {@link Objects#equals}. The result is
     * returned as an unmodifiable list.
     *
     * @param <T>       the type of elements in the collections
     * @param source    the source collection to search in
     * @param candidate the element to search for in the source
     * @return a list containing all matching elements
     * @see #findAll(Collection, Collection)
     */
    public static <T> List<T> findAll(Collection<T> source, T candidate) {
        if (isEmpty(source)) return List.of();

        List<T> result = new ArrayList<>();
        for (T element : source) {
            if (Objects.equals(element, candidate)) result.add(candidate);
        }

        return Collections.unmodifiableList(result);
    }

    /**
     * Finds all elements from the candidates collection that exist in the
     * source collection.
     *
     * <p>Iterates the candidates in their original order and returns those
     * found in the source as an unmodifiable list.
     *
     * @param <T>        the type of elements in the collections
     * @param source     the source collection to search in
     * @param candidates the elements to search for in the source
     * @return a list containing all candidates found in the source
     * @see #findAll(Collection, Object)
     */
    public static <T> List<T> findAll(Collection<T> source, Collection<T> candidates) {
        if (isEmpty(source) || isEmpty(candidates)) return List.of();

        List<T> result = new ArrayList<>();
        for (T candidate : candidates) {
            if (source.contains(candidate)) result.add(candidate);
        }

        return Collections.unmodifiableList(result);
    }

    /**
     * Finds all elements in the collection that match the given predicate.
     *
     * <p>The result preserves the iteration order of the original collection
     * and is returned as an unmodifiable list.
     *
     * @param <T>       the type of elements in the collection
     * @param source    the collection to filter
     * @param predicate the condition to test each element against
     * @return a new list containing all elements that match the predicate
     */
    public static <T> List<T> findAll(Collection<T> source, Predicate<T> predicate) {
        if (isEmpty(source) || predicate == null) return List.of();

        List<T> result = new ArrayList<>();
        for (T element : source) {
            if (predicate.test(element)) result.add(element);
        }

        return Collections.unmodifiableList(result);
    }

    /**
     * Finds all elements in the collection that have the specified property
     * value.
     *
     * <p>Comparison uses {@link Objects#equals}.
     *
     * @param <T>        the type of elements in the collection
     * @param <V>        the type of the property value to match
     * @param collection the collection to search through
     * @param property   the function to extract the property value from each element
     * @param value      the value to match against extracted properties
     * @return a new list containing all elements whose property matches the target value
     */
    public static <T, V> List<T> findAll(Collection<T> collection, Function<T, V> property, V value) {
        if (isEmpty(collection) || property == null) return List.of();

        List<T> result = new ArrayList<>();
        for (T element : collection) {
            V propertyValue = property.apply(element);
            if (Objects.equals(propertyValue, value)) result.add(element);
        }

        return Collections.unmodifiableList(result);
    }

    /**
     * Finds all map entries that satisfy the given predicate and returns them
     * as an unmodifiable list.
     *
     * @param <K>       the type of keys in the map
     * @param <V>       the type of values in the map
     * @param source    the map to search through
     * @param predicate the predicate to test each map entry against
     * @return a {@link List} of immutable entries that satisfy the predicate
     * @see #findByKey(Map, Predicate)
     * @see #findByValue(Map, Predicate)
     */
    public static <K, V> List<Map.Entry<K, V>> findAll(Map<K, V> source, Predicate<Map.Entry<K, V>> predicate) {
        if (isEmpty(source) || predicate == null) return List.of();

        List<Map.Entry<K, V>> result = new ArrayList<>();
        for (Map.Entry<K, V> entry : source.entrySet()) {
            if (predicate.test(entry)) result.add(new AbstractMap.SimpleImmutableEntry<>(entry));
        }

        return Collections.unmodifiableList(result);
    }

    /**
     * Finds the element with {@code maximum} value based on the
     * {@code comparator}.
     *
     * @param <T>        the type of elements
     * @param source     the collection to search
     * @param comparator the comparator to determine order
     * @return the maximum element, {@link Optional#empty()} source is {@code null} or {@code empty} otherwise
     */
    public static <T> Optional<T> findMax(Collection<T> source, Comparator<T> comparator) {
        if (isEmpty(source) || comparator == null) return Optional.empty();

        T max = null;
        boolean first = true;
        for (T element : source) {
            if (first || comparator.compare(element, max) > 0) {
                max = element;
                first = false;
            }
        }

        return Optional.ofNullable(max);
    }

    /**
     * Finds the element with {@code minimum} value based on the
     * {@code comparator}.
     *
     * @param <T>        the type of elements
     * @param source     the collection to search
     * @param comparator the comparator to determine order
     * @return the minimum element, {@link Optional#empty()} source is {@code null} or {@code empty} otherwise
     */
    public static <T> Optional<T> findMin(Collection<T> source, Comparator<T> comparator) {
        if (isEmpty(source) || comparator == null) return Optional.empty();

        T min = null;
        boolean first = true;
        for (T element : source) {
            if (first || comparator.compare(element, min) < 0) {
                min = element;
                first = false;
            }
        }

        return Optional.ofNullable(min);
    }

    /**
     * Groups elements of a collection by a key derived from each element using
     * the specified function.
     *
     * <p>The result maps each computed key to the list of elements that
     * produced that key.
     *
     * @param <T>      the type of elements in the source collection
     * @param <K>      the type of keys used for grouping
     * @param source   the collection to group
     * @param function the function that extracts the grouping key from each element
     * @return an immutable map where keys are the computed grouping keys and values are
     *         lists of elements that share the same key
     */
    public static <K, T> Map<K, List<T>> groupBy(Collection<T> source, Function<T, K> function) {
        if (isEmpty(source) || function == null) return Map.of();

        Map<K, List<T>> result = new HashMap<>();
        for (T item : source) {
            result.computeIfAbsent(function.apply(item), _ -> new ArrayList<>()).add(item);
        }

        return result;
    }

    /**
     * Checks whether the specified {@link Collection} is {@code null} or
     * {@code empty}.
     *
     * @param collection the collection to check
     * @return {@code true}, if the collection is {@code null} or {@code empty}, {@code false} otherwise
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * Checks whether the specified {@link Map} is {@code null} or {@code
     * empty}.
     *
     * @param map the map to check
     * @return {@code true}, if the map is {@code null} or {@code empty}, {@code false} otherwise
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    /**
     * Creates a new collection containing the intersection of two collections,
     * considering the cardinality of elements.
     *
     * <p>Each element appears the minimum number of times it occurs in either
     * collection.
     *
     * @param <T>        the type of elements in the collections
     * @param source     the first collection, may contain duplicates
     * @param candidates the second collection, may contain duplicates
     * @return a new {@link ArrayList} containing the common elements
     */
    public static <T> Collection<T> intersection(Collection<T> source, Collection<T> candidates) {
        if (isEmpty(source) || isEmpty(candidates)) return List.of();

        Map<T, Integer> countA = new HashMap<>();
        Map<T, Integer> countB = new HashMap<>();

        for (T item : source) {
            countA.put(item, countA.getOrDefault(item, 0) + 1);
        }

        for (T item : candidates) {
            countB.put(item, countB.getOrDefault(item, 0) + 1);
        }

        List<T> result = new ArrayList<>();
        for (Map.Entry<T, Integer> entry : countA.entrySet()) {
            if (countB.containsKey(entry.getKey())) {
                int minCount = Math.min(entry.getValue(), countB.get(entry.getKey()));
                for (int i = 0; i < minCount; i++) {
                    result.add(entry.getKey());
                }
            }
        }

        return List.copyOf(result);
    }

    /**
     * Creates a new map containing the intersection of two maps, based on
     * their keys.
     *
     * @param <K>        the type of keys in the maps
     * @param <V>        the type of values in the maps
     * @param source     the first map, whose key-value pairs are considered for intersection
     * @param candidates the second map, whose keys are used to determine the intersection
     * @return a new {@link HashMap} containing key-value pairs with keys
     *         present in both {@code source} and {@code candidates}
     */
    public static <K, V> Map<K, V> intersection(Map<K, V> source, Map<K, V> candidates) {
        if (isEmpty(source) || isEmpty(candidates)) return Map.of();

        Map<K, V> result = new HashMap<>();
        for (Map.Entry<K, V> entry : candidates.entrySet()) {
            if (source.containsKey(entry.getKey())) result.put(entry.getKey(), entry.getValue());
        }

        return Map.copyOf(result);
    }

    /**
     * Merges a given {@link Collection} of elements with additional candidate
     * elements (varargs) into a single {@link List}.
     *
     * <p>If both inputs are null or empty, an empty list is returned.
     *
     * @param <T>        the type of elements in the collection and varargs
     * @param source     the initial collection
     * @param candidates additional elements to append
     * @return a new {@link List} containing merged elements
     */
    @SafeVarargs
    public static <T> List<T> merge(Collection<T> source, T... candidates) {
        if (isEmpty(source) && !ArrayUtils.hasLength(candidates)) return List.of();
        if (isEmpty(source)) return List.of(candidates);
        if (!ArrayUtils.hasLength(candidates)) return List.copyOf(source);

        List<T> result = new ArrayList<>(source);
        Collections.addAll(result, candidates);

        return Collections.unmodifiableList(result);
    }

    /**
     * Merges two {@link Collection} instances into a single {@link List}.
     *
     * <p>If both inputs are null or empty, an empty list is returned.
     *
     * @param <T>        the type of elements in the collection
     * @param source     the primary collection
     * @param candidates the secondary collection to append
     * @return a new {@link List} containing merged elements from both collections
     */
    public static <T> List<T> merge(Collection<T> source, Collection<T> candidates) {
        if (isEmpty(source) && isEmpty(candidates)) return List.of();
        if (isEmpty(source)) return new ArrayList<>(candidates);
        if (isEmpty(candidates)) return new ArrayList<>(source);

        List<T> result = new ArrayList<>(source);
        result.addAll(candidates);

        return Collections.unmodifiableList(result);
    }

    /**
     * Creates a new collection containing all elements from the {@code source}
     * collection that are not present in the {@code elementsToRemove}
     * collection.
     *
     * @param <T>              the type of elements in the collection
     * @param source           the collection from which elements are taken
     * @param elementsToRemove the collection whose elements are to be subtracted from the source
     * @return a new {@link ArrayList} containing elements from the {@code source} that
     *         are not in {@code elementsToRemove}
     * @see Collection#removeAll(Collection)
     */
    public static <T> Collection<T> subtract(Collection<T> source, Collection<T> elementsToRemove) {
        if (isEmpty(source)) return List.of();

        List<T> subtract = new ArrayList<>(source);
        if (!isEmpty(elementsToRemove)) subtract.removeAll(elementsToRemove);

        return subtract;
    }

    /**
     * Creates a new map containing all entries from the {@code source} map
     * that are not present in the {@code elementsToRemove} map.
     *
     * <p>If the {@code source} map is null or empty, an empty map is
     * returned.
     *
     * @param <K>              the type of keys in the maps
     * @param <V>              the type of values in the maps
     * @param source           the map from which entries are taken
     * @param elementsToRemove the map whose keys are to be subtracted from the source
     * @return a new {@link HashMap} containing entries from the {@code source}
     *         that are not in {@code elementsToRemove}
     * @see Map#remove(Object)
     */
    public static <K, V> Map<K, V> subtract(Map<K, V> source, Map<K, V> elementsToRemove) {
        if (isEmpty(source)) return new HashMap<>();

        Map<K, V> result = new HashMap<>(source);
        if (!isEmpty(elementsToRemove)) {
            for (K key : elementsToRemove.keySet()) {
                result.remove(key);
            }
        }

        return Map.copyOf(result);
    }

    /**
     * Partitions the specified list into sublists of the specified size.
     *
     * <p>If the source list is empty, an empty list is returned. If the list
     * size is not evenly divisible by the partition size, the last partition
     * contains the remaining elements.
     *
     * @param <T>    the type of elements in the list
     * @param source the list to partition
     * @param size   the desired size of each partition
     * @return a list of partitions where each partition is a list view of the original list
     * @throws IllegalArgumentException if {@code size} is less than or equal to 0
     * @see List#subList(int, int)
     */
    public static <T> List<List<T>> partition(List<T> source, int size) {
        if (isEmpty(source)) return List.of();
        if (size <= 0) throw new IllegalArgumentException(SIZE_MUST_BE_GREATER_THAN_ZERO);

        return Collections.unmodifiableList(new Partition<>(source, size));
    }

    /**
     * Transforms a collection of elements of type T to a list of elements of
     * type R by applying the specified function to each element in the input
     * collection.
     *
     * <p>This is similar to the map operation in functional programming
     * languages.
     *
     * @param <T>         the type of elements in the input collection
     * @param <R>         the type of elements in the resulting list
     * @param source      the collection to transform
     * @param transformer the function to apply to each element
     * @return a new list containing the transformed elements in the same order as the input collection
     */
    public static <T, R> List<R> transform(Collection<T> source, Function<T, R> transformer) {
        if (isEmpty(source) || transformer == null) return List.of();

        List<R> result = new ArrayList<>();
        for (T item : source) {
            result.add(transformer.apply(item));
        }

        return result;
    }

    /**
     * Converts a given {@code Collection} to an {@code ArrayList}.
     *
     * <p>If the collection is null or empty, an empty list is returned.
     *
     * @param <E>        the type of elements in the collection
     * @param collection the collection to convert
     * @return a new {@link ArrayList} containing all elements from the input collection
     */
    public static <E> List<E> toArrayList(Collection<E> collection) {
        if (isEmpty(collection)) return new ArrayList<>();
        return new ArrayList<>(collection);
    }

    /**
     * Converts a given {@code Collection} to a {@code CopyOnWriteArrayList}.
     *
     * <p>If the collection is null or empty, an empty list is returned.
     *
     * @param <E>        the type of elements in the collection
     * @param collection the collection to convert
     * @return a new {@link CopyOnWriteArrayList} containing all elements from the input collection
     */
    public static <E> List<E> toCopyOnWriteArrayList(Collection<E> collection) {
        if (isEmpty(collection)) return new CopyOnWriteArrayList<>();
        return new CopyOnWriteArrayList<>(collection);
    }

    /**
     * Converts a given {@code Collection} to a {@code LinkedList}.
     *
     * <p>If the collection is null or empty, an empty list is returned.
     *
     * @param <T>        the type of elements in the collection
     * @param collection the collection to convert
     * @return a new {@link LinkedList} containing all elements from the input collection
     */
    public static <T> List<T> toLinkedList(Collection<T> collection) {
        if (isEmpty(collection)) return new LinkedList<>();
        return new LinkedList<>(collection);
    }

    /**
     * Converts a given {@code Collection} to a {@code Vector}.
     *
     * <p>If the collection is null or empty, an empty list is returned.
     *
     * @param <T>        the type of elements in the collection
     * @param collection the collection to convert
     * @return a new {@link Vector} containing all elements from the input collection
     */
    public static <T> List<T> toVector(Collection<T> collection) {
        if (isEmpty(collection)) return new Vector<>();
        return new Vector<>(collection);
    }

    /**
     * Converts a given {@code Collection} to a {@code HashSet}.
     *
     * <p>If the collection is null or empty, an empty set is returned.
     *
     * @param <T>        the type of elements in the collection
     * @param collection the collection to convert
     * @return a new {@link HashSet} containing all elements from the input collection
     */
    public static <T> Set<T> toHashSet(Collection<T> collection) {
        if (isEmpty(collection)) return new HashSet<>();
        return new HashSet<>(collection);
    }

    /**
     * Converts a given {@code Collection} to a {@code LinkedHashSet}.
     *
     * <p>If the collection is null or empty, an empty set is returned.
     *
     * @param <E>        the type of elements in the collection
     * @param collection the collection to convert
     * @return a new {@link LinkedHashSet} containing all elements from the input collection
     */
    public static <E> Set<E> toLinkedHashSet(Collection<E> collection) {
        if (isEmpty(collection)) return new LinkedHashSet<>();
        return new LinkedHashSet<>(collection);
    }

    /**
     * Converts a given {@code Collection} to a {@code CopyOnWriteArraySet}.
     *
     * <p>If the collection is null or empty, an empty set is returned.
     *
     * @param <T>        the type of elements in the collection
     * @param collection the collection to convert
     * @return a new {@link CopyOnWriteArraySet} containing all elements from the input collection
     */
    public static <T> Set<T> toCopyOnWriteArraySet(Collection<T> collection) {
        if (isEmpty(collection)) return new CopyOnWriteArraySet<>();
        return new CopyOnWriteArraySet<>(collection);
    }

    /**
     * Merges a {@link Collection} and a variable number of elements into a
     * single {@link List}.
     *
     * <p>Elements from the dominant collection appear first, followed by
     * recessive elements not already present. If both inputs are null or
     * empty, an empty list is returned.
     *
     * @param <T>       the type of elements in the collection and varargs
     * @param dominant  the collection whose elements take precedence and appear first in the result
     * @param recessive the variable number of elements to be merged
     * @return a new {@link ArrayList} containing all elements from the dominant collection
     *         followed by {@code unique} elements from the recessive elements
     */
    @SafeVarargs
    public static <T> List<T> union(Collection<T> dominant, T... recessive) {
        if (isEmpty(dominant) && !ArrayUtils.hasLength(recessive)) return List.of();

        List<T> result = new ArrayList<>();

        if (!isEmpty(dominant)) result.addAll(dominant);
        if (ArrayUtils.hasLength(recessive)) {
            for (T element : recessive) {
                if (!result.contains(element)) result.add(element);
            }
        }

        return Collections.unmodifiableList(result);
    }

    /**
     * Merges two {@link Collection}s into a single {@link List}.
     *
     * <p>Elements from the dominant collection appear first, followed by
     * recessive elements not already present. If both inputs are null or
     * empty, an empty list is returned.
     *
     * @param <T>       the type of elements in the collections
     * @param dominant  the collection whose elements take precedence and appear first in the result
     * @param recessive the collection whose elements are included only if not present in the dominant
     * @return a new {@link ArrayList} containing all elements from the dominant collection
     *         followed by {@code unique} elements from the recessive collection
     */
    public static <T> List<T> union(Collection<T> dominant, Collection<T> recessive) {
        if (isEmpty(dominant) && isEmpty(recessive)) return List.of();

        List<T> result = new ArrayList<>();

        if (!isEmpty(dominant)) result.addAll(dominant);
        if (!isEmpty(recessive)) {
            for (T element : recessive) {
                if (!result.contains(element)) result.add(element);
            }
        }

        return Collections.unmodifiableList(result);
    }

    /**
     * Merges two {@link Map}s into a single {@link Map}.
     *
     * <p>Entries from the dominant map take precedence over the recessive map.
     * If both maps are null, an empty map is returned.
     *
     * @param <K>       the type of keys in the maps
     * @param <V>       the type of values in the maps
     * @param dominant  the map whose entries take precedence in case of key conflicts
     * @param recessive the map whose entries are included only if the key is not present in the dominant map
     * @return a new {@link HashMap} containing all entries from both maps
     * @throws IllegalArgumentException If the keys of dominant or recessive  maps are null
     */
    public static <K, V> Map<K, V> union(Map<K, V> dominant, Map<K, V> recessive) {
        if (isEmpty(dominant) && isEmpty(recessive)) return new HashMap<>();

        Map<K, V> result = new HashMap<>();

        if (!isEmpty(recessive)) {
            for (Map.Entry<K, V> entry : recessive.entrySet()) {
                if (entry.getKey() == null) throw new IllegalArgumentException(KEY_CANNOT_BE_NULL);
                result.put(entry.getKey(), entry.getValue());
            }
        }

        if (!isEmpty(dominant)) {
            for (Map.Entry<K, V> entry : dominant.entrySet()) {
                if (entry.getKey() == null) throw new IllegalArgumentException(KEY_CANNOT_BE_NULL);
                result.put(entry.getKey(), entry.getValue());
            }
        }

        return Collections.unmodifiableMap(result);
    }

    /**
     * Checks whether the specified collection is not null and not empty.
     *
     * @param collection the collection to check
     * @return {@code true} if the collection is not null and not empty, {@code false} otherwise
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    /**
     * Checks whether the specified map is not null and not empty.
     *
     * @param map the map to check
     * @return {@code true} if the map is not null and not empty, {@code false} otherwise
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    /**
     * Gets the size of the collection, or {@code 0} if the collection is
     * {@code null}.
     *
     * @param collection the collection
     * @return the size, or {@code 0} if {@code null}
     */
    public static int size(Collection<?> collection) {
        return collection == null ? 0 : collection.size();
    }

    /**
     * Gets the size of the map, or {@code 0} if the map is {@code null}.
     *
     * @param map the map
     * @return the size, or {@code 0} if {@code null}
     */
    public static int size(Map<?, ?> map) {
        return map == null ? 0 : map.size();
    }

    /**
     * Gets the collection if it is not null, otherwise an empty list.
     *
     * @param <T>        the type of elements
     * @param collection the collection
     * @return the original collection or an empty list
     */
    public static <T> Collection<T> emptyIfNull(Collection<T> collection) {
        return collection == null ? List.of() : collection;
    }

    /**
     * Gets the list if it is not null, otherwise an empty list.
     *
     * @param <T>  the type of elements
     * @param list the list
     * @return the original list or an empty list
     */
    public static <T> List<T> emptyIfNull(List<T> list) {
        return list == null ? List.of() : list;
    }

    /**
     * Gets the set if it is not null, otherwise an empty set.
     *
     * @param <T> the type of elements
     * @param set the set
     * @return the original set or an empty set
     */
    public static <T> Set<T> emptyIfNull(Set<T> set) {
        return set == null ? Set.of() : set;
    }

    /**
     * Gets the map if it is not null, otherwise an empty map.
     *
     * @param <K> the key type
     * @param <V> the value type
     * @param map the map
     * @return the original map or an empty map
     */
    public static <K, V> Map<K, V> emptyIfNull(Map<K, V> map) {
        return map == null ? Map.of() : map;
    }

    /**
     * Counts the number of elements in the collection that match the given
     * predicate.
     *
     * @param <T>       the type of elements
     * @param source    the collection
     * @param predicate the predicate
     * @return the number of matching elements
     */
    public static <T> int countMatches(Collection<T> source, Predicate<T> predicate) {
        if (isEmpty(source) || predicate == null) return 0;
        int count = 0;
        for (T element : source) {
            if (predicate.test(element)) count++;
        }
        return count;
    }

    /**
     * Filters the collection and returns a new list containing only elements
     * that match the predicate.
     *
     * @param <T>       the type of elements
     * @param source    the collection
     * @param predicate the predicate
     * @return a new list of matching elements
     */
    public static <T> List<T> filter(Collection<T> source, Predicate<T> predicate) {
        if (isEmpty(source) || predicate == null) return List.of();
        List<T> result = new ArrayList<>();
        for (T element : source) {
            if (predicate.test(element)) result.add(element);
        }
        return result;
    }

    /**
     * Returns a new list containing elements that do not match the predicate.
     *
     * @param <T>       the type of elements
     * @param source    the collection
     * @param predicate the predicate
     * @return a new list of non-matching elements
     */
    public static <T> List<T> reject(Collection<T> source, Predicate<T> predicate) {
        if (isEmpty(source)) return new ArrayList<>();
        if (predicate == null) return new ArrayList<>(source);
        return filter(source, predicate.negate());
    }

    /**
     * Checks if the two collections contain the same elements with the same
     * cardinality, regardless of order.
     *
     * @param <T> the type of elements
     * @param a   the first collection
     * @param b   the second collection
     * @return {@code true} if the collections are equal in content, {@code false} otherwise
     */
    public static <T> boolean isEqualCollection(Collection<T> a, Collection<T> b) {
        if (a == b) return true;
        if (isEmpty(a) && isEmpty(b)) return true;
        if (isEmpty(a) || isEmpty(b) || a.size() != b.size()) return false;

        Map<T, Integer> countA = new HashMap<>();
        for (T item : a) {
            countA.put(item, countA.getOrDefault(item, 0) + 1);
        }
        for (T item : b) {
            Integer count = countA.get(item);
            if (count == null || count == 0) return false;
            countA.put(item, count - 1);
        }
        return true;
    }

    /**
     * Gets the first element of the list, or empty Optional if the list is
     * null or empty.
     *
     * @param <T>  the type of elements
     * @param list the list
     * @return the first element or empty
     */
    public static <T> Optional<T> getFirst(List<T> list) {
        if (isEmpty(list)) return Optional.empty();
        return Optional.ofNullable(list.getFirst());
    }

    /**
     * Gets the last element of the list, or empty Optional if the list is
     * null or empty.
     *
     * @param <T>  the type of elements
     * @param list the list
     * @return the last element or empty
     */
    public static <T> Optional<T> getLast(List<T> list) {
        if (isEmpty(list)) return Optional.empty();
        return Optional.ofNullable(list.getLast());
    }

    /**
     * Safely gets the element at the specified index, returning empty Optional
     * if out of bounds or list is null.
     *
     * @param <T>   the type of elements
     * @param list  the list
     * @param index the index
     * @return the element or empty
     */
    public static <T> Optional<T> get(List<T> list, int index) {
        if (isEmpty(list) || index < 0 || index >= list.size()) return Optional.empty();
        return Optional.ofNullable(list.get(index));
    }

    /**
     * Creates a new map by applying the key and value functions to each
     * element of the collection.
     *
     * @param <T>         the type of source elements
     * @param <K>         the key type
     * @param <V>         the value type
     * @param source      the source collection
     * @param keyMapper   function to extract key
     * @param valueMapper function to extract value
     * @return a new map
     */
    public static <T, K, V> Map<K, V> toMap(Collection<T> source,
                                            Function<T, K> keyMapper,
                                            Function<T, V> valueMapper) {
        if (isEmpty(source) || keyMapper == null || valueMapper == null) return Map.of();
        Map<K, V> result = new HashMap<>();
        for (T item : source) {
            K key = keyMapper.apply(item);
            if (key != null) result.put(key, valueMapper.apply(item));
        }
        return result;
    }

    /**
     * Inverts a map (keys become values and values become keys).
     *
     * <p>If there are duplicate values, later ones overwrite earlier ones.
     *
     * @param <K> the original key type
     * @param <V> the original value type
     * @param map the map to invert
     * @return a new inverted map
     */
    public static <K, V> Map<V, K> invertMap(Map<K, V> map) {
        if (isEmpty(map)) return Map.of();
        Map<V, K> result = new HashMap<>();
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (entry.getValue() != null) result.put(entry.getValue(), entry.getKey());
        }
        return result;
    }

    /**
     * Checks if the two collections have no elements in common.
     *
     * @param <T> the type of elements
     * @param a   the first collection
     * @param b   the second collection
     * @return {@code true} if the collections are disjoint, {@code false} otherwise
     */
    public static <T> boolean isDisjoint(Collection<T> a, Collection<T> b) {
        if (isEmpty(a) || isEmpty(b)) return true;
        Collection<T> smaller = a.size() <= b.size() ? a : b;
        Collection<T> larger = a.size() > b.size() ? a : b;
        Set<T> set = larger instanceof Set ? (Set<T>) larger : new HashSet<>(larger);
        for (T item : smaller) {
            if (set.contains(item)) return false;
        }
        return true;
    }

    /**
     * Removes all null elements from the collection and returns a new list.
     *
     * @param <T>        the type of elements
     * @param collection the collection
     * @return a new list without nulls
     */
    public static <T> List<T> removeNulls(Collection<T> collection) {
        if (isEmpty(collection)) return List.of();
        List<T> result = new ArrayList<>();
        for (T item : collection) {
            if (item != null) result.add(item);
        }
        return result;
    }

    /**
     * Returns a new list containing only the elements from the source
     * collection that are also present in the candidates' collection.
     *
     * <p>This is a null-safe version of retainAll.
     *
     * @param <T>        the type of elements
     * @param source     the source collection
     * @param candidates the collection of elements to retain
     * @return a new list with retained elements
     */
    public static <T> List<T> retainAll(Collection<T> source, Collection<T> candidates) {
        if (isEmpty(source)) return List.of();
        if (isEmpty(candidates)) return List.of();

        Set<T> candidateSet = (candidates instanceof Set) ? (Set<T>) candidates : new HashSet<>(candidates);
        List<T> result = new ArrayList<>();
        for (T item : source) {
            if (candidateSet.contains(item)) result.add(item);
        }
        return result;
    }

    /**
     * Counts the number of occurrences of the given object in the collection
     * (cardinality).
     *
     * @param <T>        the type of elements
     * @param object     the object to count
     * @param collection the collection
     * @return the number of occurrences, or 0 if collection is null/empty
     */
    public static <T> int cardinality(T object, Collection<T> collection) {
        if (isEmpty(collection)) return 0;
        int count = 0;
        for (T item : collection) {
            if (Objects.equals(object, item)) count++;
        }
        return count;
    }

    /**
     * Merges two sorted collections into a single sorted list (collate).
     *
     * <p>Both input collections are assumed to be sorted according to the
     * natural order.
     *
     * @param <T> the type of elements (must be Comparable)
     * @param a   the first sorted collection
     * @param b   the second sorted collection
     * @return a new sorted list containing all elements from both collections
     */
    public static <T extends Comparable<? super T>> List<T> collate(Collection<T> a, Collection<T> b) {
        return collate(a, b, Comparator.naturalOrder());
    }

    /**
     * Merges two sorted collections into a single sorted list using the
     * provided comparator.
     *
     * @param <T>        the type of elements
     * @param a          the first sorted collection
     * @param b          the second sorted collection
     * @param comparator the comparator
     * @return a new sorted list
     */
    public static <T> List<T> collate(Collection<T> a, Collection<T> b, Comparator<T> comparator) {
        if (isEmpty(a) && isEmpty(b)) return List.of();
        if (isEmpty(a)) return new ArrayList<>(b);
        if (isEmpty(b)) return new ArrayList<>(a);

        Comparator<T> effectiveComparator = comparator;
        if (effectiveComparator == null) {
            // If comparator is null, assume natural ordering (T must be Comparable)
            @SuppressWarnings("unchecked")
            Comparator<T> natural = (Comparator<T>) Comparator.naturalOrder();
            effectiveComparator = natural;
        }

        List<T> result = new ArrayList<>(a.size() + b.size());
        Iterator<T> itA = a.iterator();
        Iterator<T> itB = b.iterator();
        T itemA = itA.next();
        T itemB = itB.next();

        while (itemA != null || itemB != null) {
            if (itemA == null) {
                result.add(itemB);
                itemB = itB.hasNext() ? itB.next() : null;
            } else if (itemB == null) {
                result.add(itemA);
                itemA = itA.hasNext() ? itA.next() : null;
            } else if (effectiveComparator.compare(itemA, itemB) <= 0) {
                result.add(itemA);
                itemA = itA.hasNext() ? itA.next() : null;
            } else {
                result.add(itemB);
                itemB = itB.hasNext() ? itB.next() : null;
            }
        }
        return result;
    }

    /**
     * Zips two collections into a list of pairs (Map.Entry).
     *
     * <p>The resulting list has the length of the shorter collection.
     *
     * @param <A> the type of first collection elements
     * @param <B> the type of second collection elements
     * @param a   the first collection
     * @param b   the second collection
     * @return a list of entries (pairs)
     */
    public static <A, B> List<Map.Entry<A, B>> zip(Collection<A> a, Collection<B> b) {
        if (isEmpty(a) || isEmpty(b)) return List.of();
        List<Map.Entry<A, B>> result = new ArrayList<>(Math.min(a.size(), b.size()));
        var itA = a.iterator();
        var itB = b.iterator();
        while (itA.hasNext() && itB.hasNext()) {
            result.add(new AbstractMap.SimpleImmutableEntry<>(itA.next(), itB.next()));
        }
        return result;
    }

    /**
     * Flattens a collection of collections into a single list.
     *
     * @param <T>              the type of elements
     * @param nestedCollection the collection of collections
     * @return a flat list of all elements
     */
    public static <T> List<T> flatten(Collection<? extends Collection<T>> nestedCollection) {
        if (isEmpty(nestedCollection)) return List.of();
        List<T> result = new ArrayList<>();
        for (Collection<T> inner : nestedCollection) {
            if (!isEmpty(inner)) result.addAll(inner);
        }
        return result;
    }

    /**
     * Groups elements into a multimap (Map of Lists) by the given key
     * function.
     *
     * <p>This is an alias for {@link #groupBy(Collection, Function)}.
     *
     * @param <T>    the type of elements
     * @param <K>    the key type
     * @param source the source collection
     * @param keyFn  the key extractor
     * @return a multimap {@code Map<K, List<T>>}
     */
    public static <T, K> Map<K, List<T>> toMultiMap(Collection<T> source, Function<T, K> keyFn) {
        return groupBy(source, keyFn);
    }

    /**
     * Adds all elements from the candidates to the source collection
     * (null-safe).
     *
     * @param <T>        the type of elements
     * @param source     the source collection (must be modifiable)
     * @param candidates the elements to add
     * @return {@code true} if the collection changed, {@code false} otherwise
     */
    public static <T> boolean addAll(Collection<T> source, Collection<T> candidates) {
        if (source == null || isEmpty(candidates)) return false;
        return source.addAll(candidates);
    }

    /**
     * Adds all non-null elements from the candidates to the source collection.
     *
     * @param <T>        the type of elements
     * @param source     the source collection
     * @param candidates the elements to add
     * @return {@code true} if the collection changed, {@code false} otherwise
     */
    public static <T> boolean addAllIgnoreNull(Collection<T> source, Collection<T> candidates) {
        if (source == null || isEmpty(candidates)) return false;
        boolean changed = false;
        for (T item : candidates) {
            if (item != null && source.add(item)) changed = true;
        }
        return changed;
    }

    /**
     * Returns an unmodifiable view of the collection, or an empty list if
     * null.
     *
     * @param <T>        the type of elements
     * @param collection the collection
     * @return an unmodifiable collection
     */
    public static <T> Collection<T> unmodifiableCollection(Collection<T> collection) {
        return collection == null ? List.of() : Collections.unmodifiableCollection(collection);
    }

    /**
     * Returns an unmodifiable view of the list, or an empty list if null.
     *
     * @param <T>  the type of elements
     * @param list the list
     * @return an unmodifiable list
     */
    public static <T> List<T> unmodifiableList(List<T> list) {
        return list == null ? List.of() : Collections.unmodifiableList(list);
    }

    /**
     * Returns an unmodifiable view of the set, or an empty set if null.
     *
     * @param <T> the type of elements
     * @param set the set
     * @return an unmodifiable set
     */
    public static <T> Set<T> unmodifiableSet(Set<T> set) {
        return set == null ? Set.of() : Collections.unmodifiableSet(set);
    }

    /**
     * Returns an unmodifiable view of the map, or an empty map if null.
     *
     * @param <K> the key type
     * @param <V> the value type
     * @param map the map
     * @return an unmodifiable map
     */
    public static <K, V> Map<K, V> unmodifiableMap(Map<K, V> map) {
        return map == null ? Map.of() : Collections.unmodifiableMap(map);
    }

    /**
     * Returns a new shuffled list containing the elements of the given
     * collection.
     *
     * <p>Uses the default random source.
     *
     * @param <T>        the type of elements
     * @param collection the collection to shuffle
     * @return a new shuffled list
     */
    public static <T> List<T> shuffle(Collection<T> collection) {
        if (isEmpty(collection)) return List.of();
        List<T> result = new ArrayList<>(collection);
        Collections.shuffle(result);
        return result;
    }

    /**
     * Returns a new shuffled list containing the elements of the given
     * collection using the provided random source.
     *
     * @param <T>        the type of elements
     * @param collection the collection to shuffle
     * @param random     the random source
     * @return a new shuffled list
     */
    public static <T> List<T> shuffle(Collection<T> collection, Random random) {
        if (isEmpty(collection)) return List.of();
        List<T> result = new ArrayList<>(collection);
        Collections.shuffle(result, random);
        return result;
    }

    /**
     * Generates all permutations of the given list.
     *
     * <p>The number of permutations is n!, so use with caution for large
     * lists.
     *
     * @param <T>  the type of elements
     * @param list the list to permute
     * @return a list of all permutations (each permutation is a new list)
     */
    public static <T> List<List<T>> permutations(List<T> list) {
        if (isEmpty(list)) return List.of();
        List<List<T>> result = new ArrayList<>();
        permute(new ArrayList<>(list), 0, result);
        return result;
    }

    /**
     * Recursively generates permutations by swapping elements in place.
     *
     * @param <T>    the type of elements
     * @param arr    the list being permuted
     * @param start  the current starting index
     * @param result the list collecting all permutations
     */
    private static <T> void permute(List<T> arr, int start, List<List<T>> result) {
        if (start == arr.size()) {
            result.add(new ArrayList<>(arr));
            return;
        }
        for (int i = start; i < arr.size(); i++) {
            Collections.swap(arr, start, i);
            permute(arr, start + 1, result);
            Collections.swap(arr, start, i); // backtrack
        }
    }

    /**
     * Generates all combinations of the given size from the list.
     *
     * <p>The number of combinations is C(n, k), so use with caution for large
     * values.
     *
     * @param <T>  the type of elements
     * @param list the source list
     * @param k    the combination size
     * @return a list of all combinations of size k
     */
    public static <T> List<List<T>> combinations(List<T> list, int k) {
        if (isEmpty(list) || k <= 0 || k > list.size()) return List.of();
        List<List<T>> result = new ArrayList<>();
        combine(list, k, 0, new ArrayList<>(), result);
        return result;
    }

    /**
     * Recursively builds combinations of the given size.
     *
     * @param <T>     the type of elements
     * @param list    the source list
     * @param k       the combination size
     * @param start   the current starting index
     * @param current the current partial combination
     * @param result  the list collecting all combinations
     */
    private static <T> void combine(List<T> list, int k, int start, List<T> current, List<List<T>> result) {
        if (current.size() == k) {
            result.add(new ArrayList<>(current));
            return;
        }
        for (int i = start; i < list.size(); i++) {
            current.add(list.get(i));
            combine(list, k, i + 1, current, result);
            current.removeLast();
        }
    }

    /**
     * Gets the first entry of a SortedMap, or empty Optional if the map is
     * null or empty.
     *
     * @param <K> the key type
     * @param <V> the value type
     * @param map the sorted map
     * @return the first entry or empty
     */
    public static <K, V> Optional<Map.Entry<K, V>> firstEntry(SortedMap<K, V> map) {
        if (isEmpty(map)) return Optional.empty();
        return Optional.ofNullable(map.firstEntry());
    }

    /**
     * Gets the last entry of a SortedMap, or empty Optional if the map is
     * null or empty.
     *
     * @param <K> the key type
     * @param <V> the value type
     * @param map the sorted map
     * @return the last entry or empty
     */
    public static <K, V> Optional<Map.Entry<K, V>> lastEntry(SortedMap<K, V> map) {
        if (isEmpty(map)) return Optional.empty();
        return Optional.ofNullable(map.lastEntry());
    }

    /**
     * Returns a submap view of the SortedMap from fromKey (inclusive) to toKey
     * (exclusive).
     *
     * <p>Returns an empty map if the source is null.
     *
     * @param <K>     the key type
     * @param <V>     the value type
     * @param map     the sorted map
     * @param fromKey the low endpoint (inclusive)
     * @param toKey   the high endpoint (exclusive)
     * @return the submap or empty map
     */
    public static <K, V> SortedMap<K, V> subMap(SortedMap<K, V> map, K fromKey, K toKey) {
        if (isEmpty(map)) return Collections.emptySortedMap();
        return map.subMap(fromKey, toKey);
    }

    /**
     * Returns a head map view of the SortedMap (keys less than toKey).
     *
     * @param <K>   the key type
     * @param <V>   the value type
     * @param map   the sorted map
     * @param toKey the high endpoint (exclusive)
     * @return the head map or empty map
     */
    public static <K, V> SortedMap<K, V> headMap(SortedMap<K, V> map, K toKey) {
        if (isEmpty(map)) return Collections.emptySortedMap();
        return map.headMap(toKey);
    }

    /**
     * Returns a tail map view of the SortedMap (keys greater than or equal to
     * fromKey).
     *
     * @param <K>     the key type
     * @param <V>     the value type
     * @param map     the sorted map
     * @param fromKey the low endpoint (inclusive)
     * @return the tail map or empty map
     */
    public static <K, V> SortedMap<K, V> tailMap(SortedMap<K, V> map, K fromKey) {
        if (isEmpty(map)) return Collections.emptySortedMap();
        return map.tailMap(fromKey);
    }

    /**
     * Rotates the elements in the list by the specified distance.
     *
     * <p>Positive distance rotates to the right, negative to the left. Returns
     * a new list; the original is not modified.
     *
     * @param <T>      the type of elements
     * @param list     the list to rotate
     * @param distance the distance to rotate
     * @return a new rotated list
     */
    public static <T> List<T> rotate(List<T> list, int distance) {
        if (isEmpty(list)) return List.of();
        List<T> result = new ArrayList<>(list);
        Collections.rotate(result, distance);
        return result;
    }

    /**
     * Performs a binary search on a sorted list for the specified key.
     *
     * <p>The list must be sorted according to the natural ordering. Returns
     * the index of the key, or a negative value if not found.
     *
     * @param <T>  the type of elements (must be Comparable)
     * @param list the sorted list
     * @param key  the key to search for
     * @return the index of the key, or (-(insertion point) - 1) if not found
     */
    public static <T extends Comparable<? super T>> int binarySearch(List<T> list, T key) {
        if (isEmpty(list)) return -1;
        return Collections.binarySearch(list, key);
    }

    /**
     * Performs a binary search on a sorted list using the provided comparator.
     *
     * @param <T>        the type of elements
     * @param list       the sorted list
     * @param key        the key to search for
     * @param comparator the comparator
     * @return the index of the key, or (-(insertion point) - 1) if not found
     */
    public static <T> int binarySearch(List<T> list, T key, Comparator<T> comparator) {
        if (isEmpty(list)) return -1;
        return Collections.binarySearch(list, key, comparator);
    }

    /**
     * Gets the ceiling entry (least key greater than or equal to the given
     * key) from a NavigableMap.
     *
     * <p>Returns empty Optional if none exists or the map is null.
     *
     * @param <K> the key type
     * @param <V> the value type
     * @param map the navigable map
     * @param key the key
     * @return the ceiling entry or empty
     */
    public static <K, V> Optional<Map.Entry<K, V>> ceilingEntry(NavigableMap<K, V> map, K key) {
        if (isEmpty(map) || key == null) return Optional.empty();
        return Optional.ofNullable(map.ceilingEntry(key));
    }

    /**
     * Gets the floor entry (greatest key less than or equal to the given
     * key) from a NavigableMap.
     *
     * <p>Returns empty Optional if none exists or the map is null.
     *
     * @param <K> the key type
     * @param <V> the value type
     * @param map the navigable map
     * @param key the key
     * @return the floor entry or empty
     */
    public static <K, V> Optional<Map.Entry<K, V>> floorEntry(NavigableMap<K, V> map, K key) {
        if (isEmpty(map) || key == null) return Optional.empty();
        return Optional.ofNullable(map.floorEntry(key));
    }

    /**
     * Gets the higher entry (least key strictly greater than the given key)
     * from a NavigableMap, or empty Optional if none exists or map is null.
     *
     * @param <K> the key type
     * @param <V> the value type
     * @param map the navigable map
     * @param key the key
     * @return the higher entry or empty
     */
    public static <K, V> Optional<Map.Entry<K, V>> higherEntry(NavigableMap<K, V> map, K key) {
        if (isEmpty(map) || key == null) return Optional.empty();
        return Optional.ofNullable(map.higherEntry(key));
    }

    /**
     * Gets the lower entry (greatest key strictly less than the given key)
     * from a NavigableMap, or empty Optional if none exists or map is null.
     *
     * @param <K> the key type
     * @param <V> the value type
     * @param map the navigable map
     * @param key the key
     * @return the lower entry or empty
     */
    public static <K, V> Optional<Map.Entry<K, V>> lowerEntry(NavigableMap<K, V> map, K key) {
        if (isEmpty(map) || key == null) return Optional.empty();
        return Optional.ofNullable(map.lowerEntry(key));
    }

    /**
     * Returns a descending key set view of the NavigableMap.
     *
     * <p>Returns an empty set if the map is null.
     *
     * @param <K> the key type
     * @param <V> the value type
     * @param map the navigable map
     * @return the descending key set
     */
    public static <K, V> NavigableSet<K> descendingKeySet(NavigableMap<K, V> map) {
        if (isEmpty(map)) return Collections.emptyNavigableSet();
        return map.descendingKeySet();
    }

    /**
     * Returns a descending map view of the NavigableMap.
     *
     * <p>Returns an empty map if the source is null.
     *
     * @param <K> the key type
     * @param <V> the value type
     * @param map the navigable map
     * @return the descending map
     */
    public static <K, V> NavigableMap<K, V> descendingMap(NavigableMap<K, V> map) {
        if (isEmpty(map)) return Collections.emptyNavigableMap();
        return map.descendingMap();
    }

    /**
     * Counts the number of times the specified object occurs in the
     * collection.
     *
     * <p>This is an alias for {@link #cardinality(Object, Collection)}.
     *
     * @param <T>        the type of elements
     * @param object     the object to count
     * @param collection the collection
     * @return the frequency of the object
     */
    public static <T> int frequency(Collection<T> collection, T object) {
        return cardinality(object, collection);
    }

    /**
     * Finds the index of the first occurrence of the specified element in
     * the list, or -1 if the list is null, empty, or the element is not found.
     *
     * @param <T>     the type of elements
     * @param list    the list
     * @param element the element to search for
     * @return the index of the first occurrence, or -1
     */
    public static <T> int indexOf(List<T> list, T element) {
        if (isEmpty(list)) return -1;
        return list.indexOf(element);
    }

    /**
     * Finds the index of the last occurrence of the specified element in the
     * list, or -1 if the list is null, empty, or the element is not found.
     *
     * @param <T>     the type of elements
     * @param list    the list
     * @param element the element to search for
     * @return the index of the last occurrence, or -1
     */
    public static <T> int lastIndexOf(List<T> list, T element) {
        if (isEmpty(list)) return -1;
        return list.lastIndexOf(element);
    }

    /**
     * Safely polls (retrieves and removes) the head of the queue, or returns
     * empty Optional if the queue is null or empty.
     *
     * @param <T>   the type of elements
     * @param queue the queue
     * @return the head element or empty
     */
    public static <T> Optional<T> poll(Queue<T> queue) {
        if (queue == null || queue.isEmpty()) return Optional.empty();
        return Optional.ofNullable(queue.poll());
    }

    /**
     * Safely peeks (retrieves but does not remove) the head of the queue, or
     * returns empty Optional if the queue is null or empty.
     *
     * @param <T>   the type of elements
     * @param queue the queue
     * @return the head element or empty
     */
    public static <T> Optional<T> peek(Queue<T> queue) {
        if (queue == null || queue.isEmpty()) return Optional.empty();
        return Optional.ofNullable(queue.peek());
    }

    /**
     * Safely offers an element to the queue (null-safe).
     *
     * @param <T>     the type of elements
     * @param queue   the queue
     * @param element the element to add
     * @return {@code true} if the element was added, {@code false} otherwise
     */
    public static <T> boolean offer(Queue<T> queue, T element) {
        if (queue == null || element == null) return false;
        return queue.offer(element);
    }

    /**
     * Safely polls the first element of a Deque, or returns empty Optional if
     * null or empty.
     *
     * @param <T>   the type of elements
     * @param deque the deque
     * @return the first element or empty
     */
    public static <T> Optional<T> pollFirst(Deque<T> deque) {
        if (deque == null || deque.isEmpty()) return Optional.empty();
        return Optional.ofNullable(deque.pollFirst());
    }

    /**
     * Safely polls the last element of a Deque, or returns empty Optional if
     * null or empty.
     *
     * @param <T>   the type of elements
     * @param deque the deque
     * @return the last element or empty
     */
    public static <T> Optional<T> pollLast(Deque<T> deque) {
        if (deque == null || deque.isEmpty()) return Optional.empty();
        return Optional.ofNullable(deque.pollLast());
    }

    /**
     * Safely peeks the first element of a Deque, or returns empty Optional if
     * null or empty.
     *
     * @param <T>   the type of elements
     * @param deque the deque
     * @return the first element or empty
     */
    public static <T> Optional<T> peekFirst(Deque<T> deque) {
        if (deque == null || deque.isEmpty()) return Optional.empty();
        return Optional.ofNullable(deque.peekFirst());
    }

    /**
     * Safely peeks the last element of a Deque, or returns empty Optional if
     * null or empty.
     *
     * @param <T>   the type of elements
     * @param deque the deque
     * @return the last element or empty
     */
    public static <T> Optional<T> peekLast(Deque<T> deque) {
        if (deque == null || deque.isEmpty()) return Optional.empty();
        return Optional.ofNullable(deque.peekLast());
    }

    /**
     * Adds an element to the front of the Deque (null-safe).
     *
     * @param <T>     the type of elements
     * @param deque   the deque
     * @param element the element to add
     * @return {@code true} if the element was added, {@code false} otherwise
     */
    public static <T> boolean addFirst(Deque<T> deque, T element) {
        if (deque == null || element == null) return false;
        deque.addFirst(element);
        return true;
    }

    /**
     * Adds an element to the end of the Deque (null-safe).
     *
     * @param <T>     the type of elements
     * @param deque   the deque
     * @param element the element to add
     * @return {@code true} if the element was added, {@code false} otherwise
     */
    public static <T> boolean addLast(Deque<T> deque, T element) {
        if (deque == null || element == null) return false;
        deque.addLast(element);
        return true;
    }

    /**
     * Removes all elements from the collection that match the given predicate
     * (null-safe).
     *
     * <p>Returns a new list with the remaining elements; the original
     * collection is not modified.
     *
     * @param <T>       the type of elements
     * @param source    the source collection
     * @param predicate the predicate to test elements for removal
     * @return a new list without the matching elements
     */
    public static <T> List<T> removeIf(Collection<T> source, Predicate<T> predicate) {
        if (isEmpty(source) || predicate == null) return new ArrayList<>(emptyIfNull(source));
        List<T> result = new ArrayList<>();
        for (T element : source) {
            if (!predicate.test(element)) result.add(element);
        }
        return result;
    }

    /**
     * Replaces each element of the list with the result of applying the given
     * operator.
     *
     * <p>Returns a new list; the original is not modified.
     *
     * @param <T>      the type of elements
     * @param source   the source list
     * @param operator the operator to apply to each element
     * @return a new list with replaced elements
     */
    public static <T> List<T> replaceAll(List<T> source, UnaryOperator<T> operator) {
        if (isEmpty(source) || operator == null) return new ArrayList<>(emptyIfNull(source));
        List<T> result = new ArrayList<>(source.size());
        for (T element : source) {
            result.add(operator.apply(element));
        }
        return result;
    }

    /**
     * Creates an iterator that skips elements matching the given predicate.
     *
     * @param <T>       the type of elements
     * @param source    the source collection
     * @param predicate the predicate to skip elements
     * @return a filtered iterator
     */
    public static <T> Iterator<T> filterIterator(Collection<T> source, Predicate<T> predicate) {
        if (isEmpty(source) || predicate == null) return Collections.emptyIterator();
        return new Iterator<>() {

            /**
             * The underlying iterator.
             */
            private final Iterator<T> iterator = source.iterator();

            /**
             * The next matching element.
             */
            private T next;

            /**
             * Whether another matching element is available.
             */
            private boolean hasNext;

            {
                advance();
            }

            /**
             * Advances to the next matching element.
             */
            private void advance() {
                hasNext = false;
                while (iterator.hasNext()) {
                    T candidate = iterator.next();
                    if (predicate.test(candidate)) {
                        next = candidate;
                        hasNext = true;
                        break;
                    }
                }
            }

            /**
             * Returns whether another matching element exists.
             *
             * @return {@code true} if another element is available, {@code false} otherwise
             */
            @Override
            public boolean hasNext() {
                return hasNext;
            }

            /**
             * Gets the next matching element.
             *
             * @return the next matching element
             * @throws NoSuchElementException if no more elements exist
             */
            @Override
            public T next() {
                if (!hasNext) throw new NoSuchElementException();
                T result = next;
                advance();
                return result;
            }
        };
    }

    /**
     * Returns a ListIterator starting at the beginning of the list, or empty
     * iterator if list is null.
     *
     * @param <T>  the type of elements
     * @param list the list
     * @return a ListIterator
     */
    public static <T> ListIterator<T> listIterator(List<T> list) {
        if (list == null) return Collections.emptyListIterator();
        return list.listIterator();
    }

    /**
     * Returns a ListIterator starting at the specified index, or empty
     * iterator if list is null or index is invalid.
     *
     * @param <T>   the type of elements
     * @param list  the list
     * @param index the starting index
     * @return a ListIterator
     */
    public static <T> ListIterator<T> listIterator(List<T> list, int index) {
        if (list == null || index < 0 || index > list.size()) return Collections.emptyListIterator();
        return list.listIterator(index);
    }

    /**
     * Collects all remaining elements from an Iterator into a new List.
     *
     * @param <T>      the type of elements
     * @param iterator the iterator
     * @return a list of remaining elements
     */
    public static <T> List<T> toList(Iterator<T> iterator) {
        if (iterator == null) return List.of();
        List<T> result = new ArrayList<>();
        iterator.forEachRemaining(result::add);
        return result;
    }

    /**
     * Advances the iterator by the specified number of steps and returns the
     * last advanced element, or empty Optional if the iterator is exhausted.
     *
     * @param <T>      the type of elements
     * @param iterator the iterator
     * @param steps    the number of steps to advance
     * @return the element after advancing, or empty
     */
    public static <T> Optional<T> advance(Iterator<T> iterator, int steps) {
        if (iterator == null || steps <= 0) return Optional.empty();
        T current = null;
        for (int i = 0; i < steps && iterator.hasNext(); i++) {
            current = iterator.next();
        }
        return Optional.ofNullable(current);
    }

    /**
     * A fixed-size partition view of a list.
     *
     * <p>Provides a read-only view of partitioned segments of the original
     * list without copying elements. Each partition represents a contiguous
     * segment. The last partition may be smaller if the list size is not
     * evenly divisible by the partition size.
     *
     * @param <T> the type of elements in the list
     */
    private static final class Partition<T> extends AbstractList<List<T>> {

        /**
         * The backing list.
         */
        private final List<T> list;

        /**
         * The partition size.
         */
        private final int size;

        /**
         * Creates a partition view over the given list.
         *
         * @param list the backing list
         * @param size the partition size
         */
        private Partition(List<T> list, int size) {
            this.list = list;
            this.size = size;
        }

        /**
         * Gets the partition at the given index.
         *
         * @param index the partition index
         * @return the sublist for the partition
         * @throws IndexOutOfBoundsException if index is out of range
         */
        @Override
        public List<T> get(int index) {
            int listSize = size();
            if (index < 0) throw new IndexOutOfBoundsException(INDEX_PREFIX + index + " must not be negative");
            if (index >= listSize) {
                throw new IndexOutOfBoundsException(INDEX_PREFIX + index + " must be less than size " + listSize);
            }

            int start = index * size;
            int end = Math.min(start + size, list.size());
            return list.subList(start, end);
        }

        /**
         * Returns whether the backing list is empty.
         *
         * @return {@code true} if empty, {@code false} otherwise
         */
        @Override
        public boolean isEmpty() {
            return list.isEmpty();
        }

        /**
         * Gets the number of partitions.
         *
         * @return the partition count
         */
        @Override
        public int size() {
            return (int) Math.ceil((double) list.size() / (double) size);
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;

            Partition<?> partition = (Partition<?>) o;

            return size == partition.size && Objects.equals(list, partition.list);
        }

        @Override
        public int hashCode() {
            return Objects.hash(super.hashCode(), list, size);
        }
    }
}
