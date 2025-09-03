package ir.artanpg.commons.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

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
    private CollectionUtils() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    /**
     * Checks whether the specified collection is not {@code null} or not
     * {@code empty}.
     *
     * <p>Examples:
     * <pre>
     *  CollectionUtils.isNotEmpty(null);                       = false
     *  CollectionUtils.isNotEmpty(Collections.emptySet());     = false
     *  CollectionUtils.isNotEmpty(Collections.singleton("a")); = true
     * </pre>
     *
     * @param collection the collection to check
     * @return {@code true}, if the collection is not {@code null} or not {@code empty}, {@code false} otherwise
     */
    public static boolean hasLength(Collection<?> collection) {
        return collection != null && !collection.isEmpty();
    }

    /**
     * Checks whether the specified map is not {@code null} or not
     * {@code empty}.
     *
     * <p>Examples:
     * <pre>
     *  CollectionUtils.isNotEmpty(null);                               = false
     *  CollectionUtils.isNotEmpty(Collections.emptyMap());             = false
     *  CollectionUtils.isNotEmpty(Collections.singletonMap("a", "b")); = true
     * </pre>
     *
     * @param map the map to check
     * @return {@code true}, if the map is not {@code null} or not {@code empty}, {@code false} otherwise
     */
    public static boolean hasLength(Map<?, ?> map) {
        return map != null && !map.isEmpty();
    }

    /**
     * Merges a list and a variable number of elements into a single list, with
     * elements from the dominant list appearing first, followed by unique
     * elements from the recessive elements that are not already present in the
     * dominant list.
     *
     * <p>If the {@code dominantList} and {@code recessive} is {@code null} or
     * {@code empty}, an {@code empty} list is returned.
     *
     * <p>Examples:
     * <pre>
     *  List dominant = List.of("a", "b", "c");
     *  String[] recessive = {"b", "d", "e"};
     *  CollectionUtils.merge(dominant, recessive); = ["a", "b", "c", "d", "e"]
     * </pre>
     *
     * @param <E>          the type of elements in the list and varargs
     * @param dominantList the list whose elements take precedence and appear first in the result
     * @param recessive    the variable number of elements to be merged
     * @return a new {@link ArrayList} containing all elements from the dominant list followed by unique elements
     * from the recessive elements
     */
    @SafeVarargs
    public static <E> List<E> merge(List<E> dominantList, E... recessive) {
        if (!hasLength(dominantList) && !ArrayUtils.hasLength(recessive)) return new ArrayList<>();

        List<E> result = new ArrayList<>();

        if (hasLength(dominantList)) result.addAll(dominantList);
        if (ArrayUtils.hasLength(recessive)) {
            for (E element : recessive) {
                if (!result.contains(element)) {
                    result.add(element);
                }
            }
        }

        return result;
    }

    /**
     * Merges two lists into a single list, with elements from the dominant
     * list appearing first, followed by elements from the recessive list that
     * are not already present in the dominant list.
     *
     * <p>If the {@code dominantList} and {@code recessiveList} is {@code null}
     * or {@code empty}, an {@code empty} list is returned.
     *
     * <p>Examples:
     * <pre>
     *  List dominant = List.of("a", "b", "c");
     *  List recessive = List.of("b", "d", "e");
     *  CollectionUtils.merge(dominant, recessive); = ["a", "b", "c", "d", "e"]
     * </pre>
     *
     * @param <E>           the type of elements in the lists
     * @param dominantList  the list whose elements take precedence and appear first in the result
     * @param recessiveList the list whose elements are included only if not present in the dominant list
     * @return a new {@link ArrayList} containing all elements from the dominant list followed by unique elements
     * from the recessive list
     */
    public static <E> List<E> merge(List<E> dominantList, List<E> recessiveList) {
        if (!hasLength(dominantList) && !hasLength(recessiveList)) return new ArrayList<>();

        List<E> result = new ArrayList<>();

        if (hasLength(dominantList)) result.addAll(dominantList);
        if (hasLength(recessiveList)) {
            for (E element : recessiveList) {
                if (!result.contains(element)) {
                    result.add(element);
                }
            }
        }

        return result;
    }

    /**
     * Merges a set and a variable number of elements into a single set,
     * including all unique elements from both inputs.
     *
     * <p>If the {@code dominantSet} and {@code recessive} is {@code null} or
     * {@code empty}, an {@code empty} set is returned.
     *
     * <p>Examples:
     * <pre>
     *  Set dominant = Set.of("a", "b", "c");
     *  String[] recessive = {"b", "d", "e"};
     *  CollectionUtils.merge(dominant, recessive); = ["a", "b", "c", "d", "e"]
     * </pre>
     *
     * @param <E>         the type of elements in the set and varargs
     * @param dominantSet the set whose elements are included in the result
     * @param recessive   the variable number of elements to be merged into the result
     * @return a new {@link HashSet} containing all unique elements from both sets
     */
    @SafeVarargs
    public static <E> Set<E> merge(Set<E> dominantSet, E... recessive) {
        if (!hasLength(dominantSet) && !ArrayUtils.hasLength(recessive)) return new HashSet<>();

        Set<E> result = new HashSet<>();

        if (hasLength(dominantSet)) result.addAll(dominantSet);
        if (ArrayUtils.hasLength(recessive)) Collections.addAll(result, recessive);

        return result;
    }

    /**
     * Merges two sets into a single set, including all elements from both sets
     * with duplicates removed.
     *
     * <p>If the {@code dominantSet} and {@code recessiveSet} is {@code null}
     * or {@code empty}, an {@code empty} set is returned.
     *
     * <p>Examples:
     * <pre>
     *  Set dominant = Set.of("a", "b", "c");
     *  Set recessive = Set.of("b", "d", "e");
     *  CollectionUtils.merge(dominant, recessive); = ["a", "b", "c", "d", "e"]
     * </pre>
     *
     * @param <E>          the type of elements in the sets
     * @param dominantSet  the first set whose elements are included in the result
     * @param recessiveSet the second set whose elements are included in the result
     * @return a new {@link HashSet} containing all unique elements from both sets
     */
    public static <E> Set<E> merge(Set<E> dominantSet, Set<E> recessiveSet) {
        if (!hasLength(dominantSet) && !hasLength(recessiveSet)) return new HashSet<>();

        Set<E> result = new HashSet<>();

        if (hasLength(dominantSet)) result.addAll(dominantSet);
        if (hasLength(recessiveSet)) result.addAll(recessiveSet);

        return result;
    }

    /**
     * Merges two maps into a single map, with entries from the dominant map
     * taking precedence over the recessive map.
     *
     * <p>If the dominantMap and recessiveMap is {@code null}, an {@code empty}
     * map is returned.
     *
     * <p>Examples:
     * <pre>
     *  Map dominant = new HashMap<>();
     *  dominant.put("a", 1);
     *  dominant.put("b", 2);
     *  Map recessive = new HashMap<>();
     *  recessive.put("b", 3);
     *  recessive.put("c", 4);
     *  CollectionUtils.merge(dominant, recessive); = {a=1, b=2, c=4}
     * </pre>
     *
     * @param <K>          the type of keys in the maps
     * @param <V>          the type of values in the maps
     * @param dominantMap  the map whose entries take precedence in case of key conflicts
     * @param recessiveMap the map whose entries are included only if the key is not present in the dominant map
     * @return a new {@link HashMap} containing all entries from both maps
     */
    public static <K, V> Map<K, V> merge(Map<K, V> dominantMap, Map<K, V> recessiveMap) {
        if (!hasLength(dominantMap) && !hasLength(recessiveMap)) return new HashMap<>();

        Map<K, V> result = new HashMap<>();

        if (hasLength(recessiveMap)) result.putAll(recessiveMap);
        if (hasLength(dominantMap)) result.putAll(dominantMap);

        return result;
    }

    /**
     * Returns a new collection containing all elements from the {@code source}
     * collection that are not present in the {@code elementsToRemove}
     * collection.
     *
     * <p>If the {@code source} collection is {@code null} or {@code empty}, an
     * {@code empty} collection is returned.
     *
     * <p>Examples:
     * <pre>
     *  Collection<String> source = Arrays.asList("x", "y", "z", "y");
     *  Collection<String> elementsToRemove = Arrays.asList("y", "w");
     *  CollectionUtils.subtract(source, elementsToRemove); = ["x", "z"]
     * </pre>
     *
     * @param <T>              the type of elements in the collections
     * @param source           the collection from which elements are taken
     * @param elementsToRemove the collection whose elements are to be subtracted from the source
     * @return a new {@link ArrayList} containing elements from the {@code source} that are not in {@code elementsToRemove}
     * @see Collection#removeAll(Collection)
     */
    public static <T> Collection<T> subtract(Collection<T> source, Collection<T> elementsToRemove) {
        if (!hasLength(source)) return new ArrayList<>();

        List<T> subtract = new ArrayList<>(source);

        if (hasLength(elementsToRemove)) subtract.removeAll(elementsToRemove);

        return subtract;
    }

    /**
     * Returns a new map containing all entries from the {@code source} map
     * that are not present in the {@code elementsToRemove} map.
     *
     * <p>If the {@code source} map is {@code null} or {@code empty}, an
     * {@code empty} map is returned.
     *
     * <p>Examples:
     * <pre>
     *  Map source = new HashMap<>();
     *  source.put("a", 1);
     *  source.put("b", 2);
     *  source.put("c", 3);
     *  Map elementsToRemove = new HashMap<>();
     *  elementsToRemove.put("b", 20);
     *  elementsToRemove.put("d", 40);
     *  CollectionUtils.subtract(source, elementsToRemove); = {a=1, c=3}
     * </pre>
     *
     * @param <K>              the type of keys in the maps
     * @param <V>              the type of values in the maps
     * @param source           the map from which entries are taken
     * @param elementsToRemove the map whose keys are to be subtracted from the source
     * @return a new {@link HashMap} containing entries from the {@code source} that are not in {@code elementsToRemove}
     * @see Map#remove(Object)
     */
    public static <K, V> Map<K, V> subtract(Map<K, V> source, Map<K, V> elementsToRemove) {
        if (!hasLength(source)) return new HashMap<>();

        Map<K, V> result = new HashMap<>(source);

        if (hasLength(elementsToRemove)) {
            for (K key : elementsToRemove.keySet()) {
                result.remove(key);
            }
        }

        return result;
    }

    /**
     * Converts a given {@code Collection} to an {@code ArrayList}.
     *
     * <p>If the collection is {@code null} or {@code empty}, an {@code empty}
     * list is returned.
     *
     * <p>Examples:
     * <pre>
     *  CollectionUtils.toArrayList(null);                   = []
     *  CollectionUtils.toArrayList(Collections.emptySet()); = []
     *  CollectionUtils.toArrayList(Set.of("a", "b", "c"));  = ["a", "b", "c"]
     * </pre>
     *
     * @param <E>        the type of elements in the collection
     * @param collection the collection to convert
     * @return a new {@link ArrayList} containing all elements from the input collection
     */
    public static <E> ArrayList<E> toArrayList(Collection<E> collection) {
        if (!hasLength(collection)) return new ArrayList<>();
        return new ArrayList<>(collection);
    }

    /**
     * Converts a given {@code Collection} to an {@code CopyOnWriteArrayList}.
     *
     * <p>If the collection is {@code null} or {@code empty}, an {@code empty}
     * list is returned.
     *
     * <p>Examples:
     * <pre>
     *  CollectionUtils.toCopyOnWriteArrayList(null);                   = []
     *  CollectionUtils.toCopyOnWriteArrayList(Collections.emptySet()); = []
     *  CollectionUtils.toCopyOnWriteArrayList(Set.of("a", "b", "c"));  = ["a", "b", "c"]
     * </pre>
     *
     * @param <E>        the type of elements in the collection
     * @param collection the collection to convert
     * @return a new {@link CopyOnWriteArrayList} containing all elements from the input collection
     */
    public static <E> CopyOnWriteArrayList<E> toCopyOnWriteArrayList(Collection<E> collection) {
        if (!hasLength(collection)) return new CopyOnWriteArrayList<>();
        return new CopyOnWriteArrayList<>(collection);
    }

    /**
     * Converts a given {@code Collection} to an {@code LinkedList}.
     *
     * <p>If the collection is {@code null} or {@code empty}, an {@code empty}
     * list is returned.
     *
     * <p>Examples:
     * <pre>
     *  CollectionUtils.toLinkedList(null);                   = []
     *  CollectionUtils.toLinkedList(Collections.emptySet()); = []
     *  CollectionUtils.toLinkedList(Set.of("a", "b", "c"));  = ["a", "b", "c"]
     * </pre>
     *
     * @param <E>        the type of elements in the collection
     * @param collection the collection to convert
     * @return a new {@link LinkedList} containing all elements from the input collection
     */
    public static <E> LinkedList<E> toLinkedList(Collection<E> collection) {
        if (!hasLength(collection)) return new LinkedList<>();
        return new LinkedList<>(collection);
    }

    /**
     * Converts a given {@code Collection} to an {@code Vector}.
     *
     * <p>If the collection is {@code null} or {@code empty}, an {@code empty}
     * list is returned.
     *
     * <p>Examples:
     * <pre>
     *  CollectionUtils.toVector(null);                   = []
     *  CollectionUtils.toVector(Collections.emptySet()); = []
     *  CollectionUtils.toVector(Set.of("a", "b", "c"));  = ["a", "b", "c"]
     * </pre>
     *
     * @param <E>        the type of elements in the collection
     * @param collection the collection to convert
     * @return a new {@link Vector} containing all elements from the input collection
     */
    public static <E> Vector<E> toVector(Collection<E> collection) {
        if (!hasLength(collection)) return new Vector<>();
        return new Vector<>(collection);
    }

    /**
     * Converts a given {@code Collection} to an {@code Stack}.
     *
     * <p>If the collection is {@code null} or {@code empty}, an {@code empty}
     * list is returned.
     *
     * <p>Examples:
     * <pre>
     *  CollectionUtils.toStack(null);                   = []
     *  CollectionUtils.toStack(Collections.emptySet()); = []
     *  CollectionUtils.toStack(Set.of("a", "b", "c"));  = ["a", "b", "c"]
     * </pre>
     *
     * @param <E>        the type of elements in the collection
     * @param collection the collection to convert
     * @return a new {@link Stack} containing all elements from the input collection
     */
    public static <E> Stack<E> toStack(Collection<E> collection) {
        if (!hasLength(collection)) return new Stack<>();

        Stack<E> list = new Stack<>();
        list.addAll(collection);

        return list;
    }
}
