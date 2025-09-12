package ir.artanpg.commons.utils;

import lombok.Generated;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

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
     * Check whether the given Iterator contains the given element.
     *
     * <p>If the iterator or the element is {@code null}, a {@code false} is
     * returned.
     *
     * <p>Examples:
     * <pre>
     *  Iterator<String> iterator = List.of("x", "y", "z").iterator();
     *
     *  CollectionUtils.contains(null, null);     = null
     *  CollectionUtils.contains(null, "y");      = null
     *  CollectionUtils.contains(iterator, null); = null
     *  CollectionUtils.contains(iterator, "y");  = "y"
     * </pre>
     *
     * @param iterator the iterator to search for the element
     * @param element  the element to find in the iterator
     * @return {@code true}, if the element it is found in the iterator, {@code false} otherwise
     */
    public static boolean contains(Iterator<?> iterator, Object element) {
        if (iterator != null && element != null) {
            while (iterator.hasNext()) {
                if (Objects.equals(iterator.next(), element)) return true;
            }
        }
        return false;
    }

    /**
     * Checks whether the specified collection is not {@code null} or not
     * {@code empty}.
     *
     * <p>Examples:
     * <pre>
     *  CollectionUtils.hasLength(null);                       = false
     *  CollectionUtils.hasLength(Collections.emptySet());     = false
     *  CollectionUtils.hasLength(Collections.singleton("a")); = true
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
     *  CollectionUtils.hasLength(null);                               = false
     *  CollectionUtils.hasLength(Collections.emptyMap());             = false
     *  CollectionUtils.hasLength(Collections.singletonMap("a", "b")); = true
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
     *
     *  List dominantEmpty = Collections.emptyList();
     *  String[] recessiveEmpty = new String[]{};
     *
     *  CollectionUtils.merge(null, null);                    = []
     *  CollectionUtils.merge(dominantEmpty, recessiveEmpty); = []
     *  CollectionUtils.merge(dominant, null);                = ["a", "b", "c"]
     *  CollectionUtils.merge(dominant, recessiveEmpty);      = ["a", "b", "c"]
     *  CollectionUtils.merge(null, recessive);               = ["b", "d", "e"]
     *  CollectionUtils.merge(dominantEmpty, recessive);      = ["b", "d", "e"]
     *  CollectionUtils.merge(dominant, recessive);           = ["a", "b", "c", "d", "e"]
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
     *
     *  List dominantEmpty = Collections.emptyList();
     *  List recessiveEmpty = Collections.emptyList();
     *
     *  CollectionUtils.merge(null, null);                    = []
     *  CollectionUtils.merge(dominantEmpty, recessiveEmpty); = []
     *  CollectionUtils.merge(dominant, null);                = ["a", "b", "c"]
     *  CollectionUtils.merge(dominant, recessiveEmpty);      = ["a", "b", "c"]
     *  CollectionUtils.merge(null, recessive);               = ["b", "d", "e"]
     *  CollectionUtils.merge(dominantEmpty, recessive);      = ["b", "d", "e"]
     *  CollectionUtils.merge(dominant, recessive);           = ["a", "b", "c", "d", "e"]
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
     *
     *  Set dominantEmpty = Collections.emptySet();
     *  String[] recessiveEmpty = new String[]{};
     *
     *  CollectionUtils.merge(null, null);                    = []
     *  CollectionUtils.merge(dominantEmpty, recessiveEmpty); = []
     *  CollectionUtils.merge(dominant, null);                = ["a", "b", "c"]
     *  CollectionUtils.merge(dominant, recessiveEmpty);      = ["a", "b", "c"]
     *  CollectionUtils.merge(null, recessive);               = ["b", "d", "e"]
     *  CollectionUtils.merge(dominantEmpty, recessive);      = ["b", "d", "e"]
     *  CollectionUtils.merge(dominant, recessive);           = ["a", "b", "c", "d", "e"]
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
     *
     *  Set dominantEmpty = Collections.emptySet();
     *  Set recessiveEmpty = Collections.emptySet();
     *
     *  CollectionUtils.merge(null, null);                    = []
     *  CollectionUtils.merge(dominantEmpty, recessiveEmpty); = []
     *  CollectionUtils.merge(dominant, null);                = ["a", "b", "c"]
     *  CollectionUtils.merge(dominant, recessiveEmpty);      = ["a", "b", "c"]
     *  CollectionUtils.merge(null, recessive);               = ["b", "d", "e"]
     *  CollectionUtils.merge(dominantEmpty, recessive);      = ["b", "d", "e"]
     *  CollectionUtils.merge(dominant, recessive);           = ["a", "b", "c", "d", "e"]
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
     *
     *  Map recessive = new HashMap<>();
     *  recessive.put("b", 3);
     *  recessive.put("c", 4);
     *
     *  Map dominantEmpty = Collections.emptyMap();
     *  Map recessiveEmpty = Collections.emptyMap();
     *
     *  CollectionUtils.merge(null, null);                    = {}
     *  CollectionUtils.merge(dominantEmpty, recessiveEmpty); = {}
     *  CollectionUtils.merge(dominant, null);                = {a=1, b=2}
     *  CollectionUtils.merge(dominant, recessiveEmpty);      = {a=1, b=2}
     *  CollectionUtils.merge(null, recessive);               = {b=3, c=4}
     *  CollectionUtils.merge(dominantEmpty, recessive);      = {b=3, c=4}
     *  CollectionUtils.merge(dominant, recessive);           = {a=1, b=2, c=4}
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
     * Merges a dominant Map with recessive Properties, prioritizing entries
     * from the dominant Map.
     *
     * <p>This method creates a new Map containing all entries from the
     * {@code dominantMap}.
     * It then adds entries from the {@code recessive} Properties, but only for
     * keys that do not already exist in the {@code dominantMap}.
     * The resulting Map retains the {@code dominantMap's} key-value pairs
     * unchanged and supplements them with non-conflicting entries from the
     * recessive Properties.
     *
     * <p>Examples:
     * <pre>
     *  Map dominant = new HashMap<>();
     *  dominant.put("a", "1");
     *  dominant.put("b", "2");
     *
     *  Properties recessive = new Properties();
     *  recessive.setProperty("b", "3");
     *  recessive.setProperty("c", "4");
     *
     *  Map dominantEmpty = Collections.emptyMap();
     *  Properties recessiveEmpty = new Properties();
     *
     *  CollectionUtils.merge(null, null);                    = {}
     *  CollectionUtils.merge(dominantEmpty, recessiveEmpty); = {}
     *  CollectionUtils.merge(dominant, null);                = {a="1", b="2"}
     *  CollectionUtils.merge(dominant, recessiveEmpty);      = {a="1", b="2"}
     *  CollectionUtils.merge(null, recessive);               = {b="3", c="4"}
     *  CollectionUtils.merge(dominantEmpty, recessive);      = {b="3", c="4"}
     *  CollectionUtils.merge(dominant, recessive);           = {a="1", b="2", c="4"}
     * </pre>
     *
     * @param <K>         the type of keys maintained by the dominant Map
     * @param <V>         the type of mapped values
     * @param dominantMap the Map whose entries take precedence
     * @param recessive   the Properties whose entries are added only if the key is not present in dominantMap
     * @return a new {@link HashMap} containing all entries from dominantMap and non-conflicting entries from recessive
     * @throws ClassCastException if the keys or values in recessive cannot be cast to types K or V
     */
    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> merge(Map<K, V> dominantMap, Properties recessive) {
        if (!hasLength(dominantMap) && recessive == null) return new HashMap<>();

        Map<K, V> result = new HashMap<>();

        if (hasLength(dominantMap)) result.putAll(dominantMap);
        if (recessive != null) {
            for (String key : recessive.stringPropertyNames()) {
                K mapKey = (K) key;
                if (!result.containsKey(mapKey)) {
                    V mapValue = (V) recessive.getProperty(key);
                    result.put(mapKey, mapValue);
                }
            }
        }

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
     *  Collection<String> source = List.of("x", "y", "z", "y");
     *  Collection<String> elementsToRemove = List.of("y", "w");
     *
     *  Collection<String> sourceEmpty = Collections.emptyList();
     *  Collection<String> elementsToRemoveEmpty = Collections.emptyList();
     *
     *  CollectionUtils.subtract(null, null);                         = []
     *  CollectionUtils.subtract(sourceEmpty, elementsToRemoveEmpty); = []
     *  CollectionUtils.subtract(null, elementsToRemove);             = []
     *  CollectionUtils.subtract(sourceEmpty, elementsToRemove);      = []
     *  CollectionUtils.subtract(source, null);                       = ["x", "y", "z", "y"]
     *  CollectionUtils.subtract(source, elementsToRemoveEmpty);      = ["x", "y", "z", "y"]
     *  CollectionUtils.subtract(source, elementsToRemove);           = ["x", "z"]
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
     *
     *  Map elementsToRemove = new HashMap<>();
     *  elementsToRemove.put("b", 20);
     *  elementsToRemove.put("d", 40);
     *
     *  Map sourceEmpty = Collections.emptyMap();
     *  Map elementsToRemoveEmpty = Collections.emptyMap();
     *
     *  CollectionUtils.subtract(null, null);                         = {}
     *  CollectionUtils.subtract(sourceEmpty, elementsToRemoveEmpty); = {}
     *  CollectionUtils.subtract(null, elementsToRemove);             = {}
     *  CollectionUtils.subtract(sourceEmpty, elementsToRemove);      = {}
     *  CollectionUtils.subtract(source, null);                       = {a=1, b=2, c=3}
     *  CollectionUtils.subtract(source, elementsToRemoveEmpty);      = {a=1, b=2, c=3}
     *  CollectionUtils.subtract(source, elementsToRemove);           = {a=1, c=3}
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

    /**
     * Converts a given {@code Collection} to an {@code HashSet}.
     *
     * <p>If the collection is {@code null} or {@code empty}, an {@code empty}
     * set is returned.
     *
     * <p>Examples:
     * <pre>
     *  CollectionUtils.toHashSet(null);                    = []
     *  CollectionUtils.toHashSet(Collections.emptyList()); = []
     *  CollectionUtils.toHashSet(List.of("a", "b", "c"));  = ["a", "b", "c"]
     * </pre>
     *
     * @param <E>        the type of elements in the collection
     * @param collection the collection to convert
     * @return a new {@link HashSet} containing all elements from the input collection
     */
    public static <E> HashSet<E> toHashSet(Collection<E> collection) {
        if (!hasLength(collection)) return new HashSet<>();
        return new HashSet<>(collection);
    }

    /**
     * Converts a given {@code Collection} to an {@code LinkedHashSet}.
     *
     * <p>If the collection is {@code null} or {@code empty}, an {@code empty}
     * set is returned.
     *
     * <p>Examples:
     * <pre>
     *  CollectionUtils.toLinkedHashSet(null);                    = []
     *  CollectionUtils.toLinkedHashSet(Collections.emptyList()); = []
     *  CollectionUtils.toLinkedHashSet(List.of("a", "b", "c"));  = ["a", "b", "c"]
     * </pre>
     *
     * @param <E>        the type of elements in the collection
     * @param collection the collection to convert
     * @return a new {@link LinkedHashSet} containing all elements from the input collection
     */
    public static <E> LinkedHashSet<E> toLinkedHashSet(Collection<E> collection) {
        if (!hasLength(collection)) return new LinkedHashSet<>();
        return new LinkedHashSet<>(collection);
    }

    /**
     * Converts a given {@code Collection} to an {@code CopyOnWriteArraySet}.
     *
     * <p>If the collection is {@code null} or {@code empty}, an {@code empty}
     * set is returned.
     *
     * <p>Examples:
     * <pre>
     *  CollectionUtils.toCopyOnWriteArraySet(null);                    = []
     *  CollectionUtils.toCopyOnWriteArraySet(Collections.emptyList()); = []
     *  CollectionUtils.toCopyOnWriteArraySet(List.of("a", "b", "c"));  = ["a", "b", "c"]
     * </pre>
     *
     * @param <E>        the type of elements in the collection
     * @param collection the collection to convert
     * @return a new {@link CopyOnWriteArraySet} containing all elements from the input collection
     */
    public static <E> CopyOnWriteArraySet<E> toCopyOnWriteArraySet(Collection<E> collection) {
        if (!hasLength(collection)) return new CopyOnWriteArraySet<>();
        return new CopyOnWriteArraySet<>(collection);
    }
}
