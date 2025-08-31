package ir.artanpg.commons.utils;

import java.util.ArrayList;
import java.util.Collection;
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
     * Checks whether the specified collection is {@code null} or
     * {@code empty}.
     *
     * <p>Examples:
     * <pre>
     *  CollectionUtils.isEmpty(null);                   = true
     *  CollectionUtils.isEmpty(Collections.emptySet()); = true
     *  CollectionUtils.isEmpty(Set.of("a", "b", "c"));  = false
     * </pre>
     *
     * @param collection the collection to check
     * @return {@code true}, if the collection is {@code null} or {@code empty}, {@code false} otherwise
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * Checks whether the specified map is {@code null} or {@code empty}.
     *
     * <p>Examples:
     * <pre>
     *  CollectionUtils.isEmpty(null);                                = true
     *  CollectionUtils.isEmpty(Collections.emptyMap());              = true
     *  CollectionUtils.isEmpty(Collections.singletonMap("a", "b"));  = false
     * </pre>
     *
     * @param map the map to check
     * @return {@code true}, if the map is {@code null} or {@code empty}, {@code false} otherwise
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    /**
     * Merges two lists into a single list, with elements from the dominant
     * list appearing first, followed by elements from the recessive list that
     * are not already present in the dominant list.
     *
     * <p>If the dominantList and recessiveList is {@code null}, an {@code empty}
     * list is returned.
     *
     * <p>Examples:
     * <pre>
     *  List<String> dominant = Arrays.asList("a", "b", "c");
     *  List<String> recessive = Arrays.asList("b", "d", "e");
     *  List<String> result = CollectionUtils.merge(dominant, recessive); = ["a", "b", "c", "d", "e"]
     * </pre>
     *
     * @param <E>           the type of elements in the lists
     * @param dominantList  the list whose elements take precedence and appear first in the result
     * @param recessiveList the list whose elements are included only if not present in the dominant list
     * @return a new {@link ArrayList} containing all elements from the dominant list followed by unique elements
     * from the recessive list
     */
    public static <E> List<E> merge(List<E> dominantList, List<E> recessiveList) {
        if (isEmpty(dominantList) && isEmpty(recessiveList)) return new ArrayList<>();

        List<E> result = new ArrayList<>();

        if (!isEmpty(dominantList)) result.addAll(dominantList);
        if (!isEmpty(recessiveList)) {
            for (E element : recessiveList) {
                if (!result.contains(element)) {
                    result.add(element);
                }
            }
        }

        return result;
    }

    /**
     * Merges two sets into a single set, including all elements from both sets
     * with duplicates removed.
     *
     * <p>If the dominantSet and recessiveSet is {@code null}, an {@code empty}
     * set is returned.
     *
     * <p><b>Examples:
     * <pre>
     *  Set<String> dominant = new HashSet<>(Arrays.asList("a", "b", "c"));
     *  Set<String> recessive = new HashSet<>(Arrays.asList("b", "d", "e"));
     *  Set<String> result = CollectionUtils.merge(dominant, recessive); = ["a", "b", "c", "d", "e"]
     * </pre>
     *
     * @param <E>          the type of elements in the sets
     * @param dominantSet  the first set whose elements are included in the result
     * @param recessiveSet the second set whose elements are included in the result
     * @return a new {@link HashSet} containing all unique elements from both sets
     */
    public static <E> Set<E> merge(Set<E> dominantSet, Set<E> recessiveSet) {
        if (isEmpty(dominantSet) && isEmpty(recessiveSet)) return new HashSet<>();

        Set<E> result = new HashSet<>();

        if (!isEmpty(dominantSet)) result.addAll(dominantSet);
        if (!isEmpty(recessiveSet)) result.addAll(recessiveSet);

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
     *  Map<String, Integer> dominant = new HashMap<>();
     *  dominant.put("a", 1);
     *  dominant.put("b", 2);
     *  Map<String, Integer> recessive = new HashMap<>();
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
        if (isEmpty(dominantMap) && isEmpty(recessiveMap)) return new HashMap<>();

        Map<K, V> result = new HashMap<>();

        if (!isEmpty(recessiveMap)) result.putAll(recessiveMap);
        if (!isEmpty(dominantMap)) result.putAll(dominantMap);

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
        if (isEmpty(collection)) return new ArrayList<>();
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
        if (isEmpty(collection)) return new CopyOnWriteArrayList<>();
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
        if (isEmpty(collection)) return new LinkedList<>();
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
        if (isEmpty(collection)) return new Vector<>();
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
        if (isEmpty(collection)) return new Stack<>();

        Stack<E> list = new Stack<>();
        list.addAll(collection);

        return list;
    }
}
