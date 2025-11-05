package ir.artanpg.commons.utils;

import ir.artanpg.commons.core.tools.jacoco.Generated;

import java.util.AbstractList;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Provides utility methods for {@link Collection} instances.
 *
 * @author Mohammad Yazdian
 */
public abstract class CollectionUtils {

    /**
     * Private constructor to prevent instantiation of this utility class.
     *
     * @throws UnsupportedOperationException if an attempt is made to instantiate this class
     */
    @Generated
    private CollectionUtils() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    /**
     * Checks if the specified candidate exists in the collection.
     *
     * <p>This method efficiently checks for the presence of a candidate in the
     * collection. If the source collection is already a {@link Set}, it
     * directly uses the set's {@code contains} method. Otherwise, it converts
     * the collection to a {@link HashSet} for efficient O(1) lookup performance.
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
     * <p>This method efficiently determines whether at least one element from
     * the {@code candidates} collection is present in the {@code source}
     * collection. The implementation optimizes for performance by converting
     * the source collection to a {@link HashSet} when it's not already a
     * {@link Set}, allowing for O(1) lookups during the candidate checks.
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
     * <p>This method iterates through the collection and returns {@code true}
     * as soon as it finds an element that matches the predicate condition. If
     * the collection is empty or null, or if the predicate is null, the method
     * returns {@code false}.
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
     * <p>This method iterates through the source collection and applies the
     * provided extractor function to each element. If the extracted value from
     * any element matches the expected value (according to
     * {@link Objects#equals(Object, Object)}), the method returns
     * {@code true}.
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
     * <p>This method performs a null-safe verification that the source map
     * contains the exact key-value pair specified by the map entry. It checks
     * that the key exists in the map and that the value associated with that
     * key matches the entry's value using
     * {@link Objects#equals(Object, Object)} for null-safe comparison.
     *
     * @param <K>       the type of keys in the map
     * @param <V>       the type of values in the map
     * @param source    the map to search through
     * @param candidate the map entry containing the exact key-value pair to search for
     * @return {@code true} if the map contains the exact key-value mapping specified by the entry,
     * {@code false} otherwise
     */
    public static <K, V> boolean contains(Map<K, V> source, Map.Entry<K, V> candidate) {
        if (isEmpty(source) || candidate == null || candidate.getKey() == null) return false;
        return Objects.equals(source.get(candidate.getKey()), candidate.getValue());
    }

    /**
     * Checks if any of the key-value pairs from the candidates collection
     * exists in the source map.
     *
     * <p>This method verifies whether at least one entry from the
     * {@code candidates} collection (containing {@link Map.Entry} objects) is
     * present in the {@code source} map. For an entry to be considered as
     * "contained", both the key must exist in the source map and the
     * corresponding value must match (according to
     * {@link Objects#equals(Object, Object)}).
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
            // Check if key exists (sourceValue != null OR key exists with null value)
            if ((source.get(candidate.getKey()) != null || source.containsKey(candidate.getKey()))
                    && Objects.equals(source.get(candidate.getKey()), candidate.getValue())) return true;
        }

        return false;
    }

    /**
     * Checks if any key-value pair in the map satisfies the given predicate.
     *
     * <p>This method provides maximum flexibility by allowing custom
     * predicates to test map entries. It iterates through the map's entry set
     * and returns {@code true} as soon as it finds an entry that matches the
     * predicate condition.
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
     * Returns a List containing elements from the source collection that are
     * not present in the candidates' collection.
     *
     * <p>This is a convenience method that performs a set difference operation
     * and returns the result as an {@link ArrayList}. It returns all elements
     * from the {@code source} collection that are not found in the
     * {@code candidates} collection.
     *
     * <p><b>Performance Note:</b> This method internally optimizes by
     * converting the candidates collection to a {@link HashSet} if it's not
     * already a {@link Set}, providing O(1) lookups during the difference
     * operation.
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
     * Returns a new collection containing all elements from the source
     * collection that are not present in the candidates' collection.
     * The type of the returned collection is determined by the provided
     * collection factory.
     *
     * <p>This method efficiently computes the set difference between two
     * collections. The operation is equivalent to: {@code source ∖ candidates}
     * (elements in source but not in candidates).
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
        return difference(source, candidates, item -> true, factory);
    }

    /**
     * Returns a collection containing elements from the source collection that
     * are not present in the candidates collection and satisfy the given
     * predicate.
     *
     * <p>This method performs a filtered set difference operation. It returns
     * all elements from the {@code source} collection that:
     * <ul>
     *   <li>Are not present in the {@code candidates} collection</li>
     *   <li> Satisfy the given {@code predicate} condition</li>
     * </ul>
     * The type of the returned collection is determined by the provided
     * factory.
     *
     * <p><b>Performance Note:</b> For optimal performance with large candidate
     * collections, consider using a {@link Set} implementation. This method
     * automatically optimizes by using the candidates collection directly if
     * it's already a Set, otherwise converts it to a HashSet for O(1) lookups.
     *
     * @param <T>        the type of elements in the collections
     * @param <C>        the type of collection to return
     * @param source     the source collection to filter elements from
     * @param candidates the collection of elements to exclude
     * @param predicate  the condition that elements must satisfy
     * @param factory    the supplier for creating the result collection
     * @return a new collection containing elements from source that satisfy the predicate and are not in candidates
     * @throws IllegalArgumentException if predicate or factory is {@code null}
     */
    public static <T, C extends Collection<T>> C difference(Collection<T> source,
                                                            Collection<T> candidates,
                                                            Predicate<T> predicate,
                                                            Supplier<C> factory) {
        if (predicate == null) throw new IllegalArgumentException("The predicate cannot be null");
        if (factory == null) throw new IllegalArgumentException("The factory cannot be null");

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
     * Returns a new map containing all entries from the {@code source} map
     * whose keys are not present in the {@code candidates} map. The type of
     * the returned map is determined by the provided map factory.
     *
     * <p>This method computes the key-based difference between two maps. An
     * entry from the source map is included in the result if and only if its
     * key is not present in the candidates map. The values associated with
     * keys are not compared - only key existence is considered.
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
        if (factory == null) throw new IllegalArgumentException("The factory cannot be null");

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

        return (sourceSet.contains(candidate)) ? Optional.of(candidate) : Optional.empty();
    }

    /**
     * Finds the first element from the candidates collection that exists in
     * the source collection.
     *
     * <p>This method searches through the candidates collection in its
     * iteration order and returns the first candidate that is found in the
     * source collection. The search stops immediately when the first match is
     * found.
     *
     * @param <T>        the type of elements in the collections
     * @param source     the collection to search in
     * @param candidates the collection of elements to search for
     * @return the first candidate from the candidates collection that exists in source,
     * {@link Optional#empty()} otherwise
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
     * <p>This method iterates through the source collection in its natural
     * iteration order and returns the first element for which the specified
     * predicate returns {@code true}. The search stops immediately when a
     * matching element is found.
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
     * <p>This method iterates through the collection and extracts a property
     * from each element using the provided function. It returns the first
     * element whose extracted property value matches the target value
     * according to {@link Objects#equals}.
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
     * <p>This method iterates through the map's entry set and returns the
     * first entry for which the specified predicate returns {@code true}. The
     * search follows the iteration order of the map's entry set and stops at
     * the first match.
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
            if (predicate.test(entry))
                return Optional.of(new AbstractMap.SimpleImmutableEntry<>(entry.getKey(), entry.getValue()));
        }

        return Optional.empty();
    }

    /**
     * Finds the first map entry whose key satisfies the given predicate.
     *
     * <p>This method searches through the map's entry set and returns the
     * first entry where the key matches the predicate condition. The search is
     * performed in the iteration order of the map's entry set, which may vary
     * depending on the map implementation.
     *
     * @param <K>       the type of keys in the map
     * @param <V>       the type of values in the map
     * @param source    the map to search through
     * @param predicate the predicate to test keys against
     * @return an {@link Optional} containing the first entry with a matching key, {@link Optional#empty()} otherwise
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
     * <p>This method searches through the map's entry set and returns the
     * first entry where the value matches the predicate condition. The search
     * is performed in the iteration order of the map's entry set. This method
     * is particularly useful for searching based on value conditions when keys
     * are not relevant.
     *
     * @param <K>       the type of keys in the map
     * @param <V>       the type of values in the map
     * @param source    the map to search through
     * @param predicate the predicate to test values against
     * @return an {@link Optional} containing the first entry with a matching value, {@link Optional#empty()} otherwise
     */
    public static <K, V> Optional<Map.Entry<K, V>> findByValue(Map<K, V> source, Predicate<V> predicate) {
        if (isEmpty(source) || predicate == null) return Optional.empty();

        for (Map.Entry<K, V> entry : source.entrySet()) {
            if (predicate.test(entry.getValue())) return Optional.of(entry);
        }

        return Optional.empty();
    }

    /**
     * Finds all elements from the candidate array that exist in the source
     * collection.
     *
     * <p>This method checks each candidate in the provided varargs array against the source
     * collection and collects all candidate that are found in the source. The result list
     * maintains the order of candidate as they appear in the input array.
     *
     * @param <T>       the type of elements in the collections
     * @param source    the source collection to search in
     * @param candidate the elements to search for in the source
     * @return a list containing all candidate found in the source
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
     * <p>This method checks each candidate in the provided collection against
     * the source collection and collects all candidates that are found in the
     * source. The result list maintains the order of candidates as they appear
     * in the input collection.
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
     * <p>This method filters the source collection by applying the specified
     * predicate to each element and collects all elements for which the
     * predicate returns {@code true}. The result preserves the iteration order
     * of the original collection.
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
            if (predicate.test(element)) {
                result.add(element);
            }
        }

        return Collections.unmodifiableList(result);
    }

    /**
     * Finds all elements in the collection that have the specified property
     * value.
     *
     * <p>This method iterates through the collection and extracts a property
     * from each element using the provided function. Elements whose extracted
     * property matches the target value (according to {@link Objects#equals})
     * are included in the result list.
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
     * as a list.
     *
     * <p>This method searches through the entire map's entry set and collects
     * all entries that match the predicate condition into a list. Unlike
     * methods that return only the first match, this method returns all
     * matching entries.
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
        for (T element : source) {
            if (max == null || comparator.compare(element, max) > 0) max = element;
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
        for (T element : source) {
            if (min == null || comparator.compare(element, min) < 0) min = element;
        }

        return Optional.ofNullable(min);
    }

    /**
     * Groups elements of a collection by a key derived from each element using
     * the specified function.
     *
     * <p>This method categorizes elements into groups where all elements in
     * the same group share the same key value as computed by the provided
     * function. The result is a map where keys are the computed values and
     * values are lists of elements that produced that key.
     *
     * @param <T>      the type of elements in the source collection
     * @param <K>      the type of keys used for grouping
     * @param source   the collection to group
     * @param function the function that extracts the grouping key from each element
     * @return an immutable map where keys are the computed grouping keys and values are lists
     * of elements that share the same key
     */
    public static <K, T> Map<K, List<T>> groupBy(Collection<T> source, Function<T, K> function) {
        if (isEmpty(source) || function == null) return Map.of();

        Map<K, List<T>> result = new HashMap<>();
        for (T item : source) {
            result.computeIfAbsent(function.apply(item), k -> new ArrayList<>()).add(item);
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
     * Checks whether the specified {@link Map} is {@code null} or
     * {@code empty}.
     *
     * @param map the map to check
     * @return {@code true}, if the map is {@code null} or {@code empty}, {@code false} otherwise
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    /**
     * Returns a new collection containing the intersection of two collections,
     * considering the cardinality of elements. The result includes elements
     * common to both collections, with each element appearing the minimum
     * number of times it occurs in either collection.
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
     * Returns a new map containing the intersection of two maps, based on
     * their keys.
     *
     * @param <K>        the type of keys in the maps
     * @param <V>        the type of values in the maps
     * @param source     the first map, whose key-value pairs are considered for intersection
     * @param candidates the second map, whose keys are used to determine the intersection
     * @return a new {@link HashMap} containing key-value pairs with keys present in both
     * {@code source} and {@code candidates}
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
     * <p>If the {@code source} and {@code candidates} is {@code null} or
     * {@code empty}, an {@code empty} list is returned.
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
     * <p>If the {@code source} and {@code candidates} is {@code null} or
     * {@code empty}, an {@code empty} list is returned.
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
     * Returns a new collection containing all elements from the {@code source}
     * collection that are not present in the {@code elementsToRemove}
     * collection.
     *
     * @param <T>              the type of elements in the collection
     * @param source           the collection from which elements are taken
     * @param elementsToRemove the collection whose elements are to be subtracted from the source
     * @return a new {@link ArrayList} containing elements from the {@code source} that are not in {@code elementsToRemove}
     * @see Collection#removeAll(Collection)
     */
    public static <T> Collection<T> subtract(Collection<T> source, Collection<T> elementsToRemove) {
        if (isEmpty(source)) return List.of();

        List<T> subtract = new ArrayList<>(source);

        if (!isEmpty(elementsToRemove)) subtract.removeAll(elementsToRemove);

        return subtract;
    }

    /**
     * Returns a new map containing all entries from the {@code source} map
     * that are not present in the {@code elementsToRemove} map.
     *
     * <p>If the {@code source} map is {@code null} or {@code empty}, an
     * {@code empty} map is returned.
     *
     * @param <K>              the type of keys in the maps
     * @param <V>              the type of values in the maps
     * @param source           the map from which entries are taken
     * @param elementsToRemove the map whose keys are to be subtracted from the source
     * @return a new {@link HashMap} containing entries from the {@code source} that are not in {@code elementsToRemove}
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
     * <p>If the source list is empty, an empty list is returned. If the size
     * of the source list is not evenly divisible by the partition size, the
     * last partition will contain the remaining elements and will be smaller
     * than the specified size.
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
        if (size <= 0) throw new IllegalArgumentException("Size must be greater than 0");

        return Collections.unmodifiableList(new Partition<>(source, size));
    }

    /**
     * Transforms a collection of elements of type T to a list of elements of
     * type R by applying the specified function to each element in the input
     * collection.
     *
     * <p>This method provides a functional programming approach to transform
     * collections and is similar to the map operation in functional
     * programming languages.
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
     * <p>If the collection is {@code null} or {@code empty}, an {@code empty}
     * list is returned.
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
     * Converts a given {@code Collection} to an {@code CopyOnWriteArrayList}.
     *
     * <p>If the collection is {@code null} or {@code empty}, an {@code empty}
     * list is returned.
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
     * Converts a given {@code Collection} to an {@code LinkedList}.
     *
     * <p>If the collection is {@code null} or {@code empty}, an {@code empty}
     * list is returned.
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
     * Converts a given {@code Collection} to an {@code Vector}.
     *
     * <p>If the collection is {@code null} or {@code empty}, an {@code empty}
     * list is returned.
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
     * Converts a given {@code Collection} to an {@code HashSet}.
     *
     * <p>If the collection is {@code null} or {@code empty}, an {@code empty}
     * set is returned.
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
     * Converts a given {@code Collection} to an {@code LinkedHashSet}.
     *
     * <p>If the collection is {@code null} or {@code empty}, an {@code empty}
     * set is returned.
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
     * Converts a given {@code Collection} to an {@code CopyOnWriteArraySet}.
     *
     * <p>If the collection is {@code null} or {@code empty}, an {@code empty}
     * set is returned.
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
     * single {@link List}, with elements from the dominant collection
     * appearing first, followed by elements from the recessive varargs that
     * are not already present in the dominant collection.
     *
     * <p>If the {@code dominant} and {@code recessive} is {@code null} or
     * {@code empty}, an {@code empty} list is returned.
     *
     * @param <T>       the type of elements in the collection and varargs
     * @param dominant  the collection whose elements take precedence and appear first in the result
     * @param recessive the variable number of elements to be merged
     * @return a new {@link ArrayList} containing all elements from the dominant collection followed by
     * {@code unique} elements from the recessive elements
     */
    @SafeVarargs
    public static <T> List<T> union(Collection<T> dominant, T... recessive) {
        if (isEmpty(dominant) && !ArrayUtils.hasLength(recessive)) return List.of();

        List<T> result = new ArrayList<>();

        if (!isEmpty(dominant)) result.addAll(dominant);
        if (ArrayUtils.hasLength(recessive)) {
            for (T element : recessive) {
                if (!result.contains(element)) {
                    result.add(element);
                }
            }
        }

        return Collections.unmodifiableList(result);
    }

    /**
     * Merges two {@link Collection}s into a single {@link List}, with elements
     * from the dominant collection appearing first, followed by elements from
     * the recessive collection that are not already present in the dominant
     * collection.
     *
     * <p>If the {@code dominant} and {@code recessive} is {@code null}
     * or {@code empty}, an {@code empty} list is returned.
     *
     * @param <T>       the type of elements in the collections
     * @param dominant  the collection whose elements take precedence and appear first in the result
     * @param recessive the collection whose elements are included only if not present in the dominant
     * @return a new {@link ArrayList} containing all elements from the dominant collection followed by
     * {@code unique} elements from the recessive collection
     */
    public static <T> List<T> union(Collection<T> dominant, Collection<T> recessive) {
        if (isEmpty(dominant) && isEmpty(recessive)) return List.of();

        List<T> result = new ArrayList<>();

        if (!isEmpty(dominant)) result.addAll(dominant);
        if (!isEmpty(recessive)) {
            for (T element : recessive) {
                if (!result.contains(element)) {
                    result.add(element);
                }
            }
        }

        return Collections.unmodifiableList(result);
    }

    /**
     * Merges two {@link Map}s into a single {@link Map}, with entries from the
     * dominant map taking precedence over the recessive map.
     *
     * <p>If the dominant and recessive is {@code null}, an {@code empty}
     * map is returned.
     *
     * @param <K>       the type of keys in the maps
     * @param <V>       the type of values in the maps
     * @param dominant  the map whose entries take precedence in case of key conflicts
     * @param recessive the map whose entries are included only if the key is not present in the dominant map
     * @return a new {@link HashMap} containing all entries from both maps
     * @throws IllegalArgumentException If the keys of dominant or recessive maps are null
     */
    public static <K, V> Map<K, V> union(Map<K, V> dominant, Map<K, V> recessive) {
        if (isEmpty(dominant) && isEmpty(recessive)) return new HashMap<>();

        Map<K, V> result = new HashMap<>();

        if (!isEmpty(recessive)) {
            for (Map.Entry<K, V> entry : recessive.entrySet()) {
                if (entry.getKey() == null) throw new IllegalArgumentException("Key cannot be null");
                result.put(entry.getKey(), entry.getValue());
            }
        }

        if (!isEmpty(dominant)) {
            for (Map.Entry<K, V> entry : dominant.entrySet()) {
                if (entry.getKey() == null) throw new IllegalArgumentException("Key cannot be null");
                result.put(entry.getKey(), entry.getValue());
            }
        }

        return Collections.unmodifiableMap(result);
    }

    /**
     * A fixed-size partition view of a list that divides the original list
     * into sublists of specified size. This class provides a read-only view of
     * partitioned segments of the original list without copying elements.
     *
     * <p>Each partition represents a contiguous segment of the original list.
     * The last partition may be smaller than the specified partition size if
     * the original list size is not evenly divisible by the partition size.
     *
     * <p>This class is immutable and thread-safe if the underlying list is
     * thread-safe and not modified after partition creation.
     *
     * @param <T> the type of elements in the list
     */
    private static final class Partition<T> extends AbstractList<List<T>> {
        private final List<T> list;
        private final int size;

        private Partition(List<T> list, int size) {
            this.list = list;
            this.size = size;
        }

        @Override
        public List<T> get(int index) {
            int listSize = size();
            if (index < 0) throw new IndexOutOfBoundsException("Index " + index + " must not be negative");
            if (index >= listSize)
                throw new IndexOutOfBoundsException("Index " + index + " must be less than size " + listSize);

            int start = index * size;
            int end = Math.min(start + size, list.size());
            return list.subList(start, end);
        }

        @Override
        public boolean isEmpty() {
            return list.isEmpty();
        }

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
