package ir.artanpg.commons.utils;

import org.junit.jupiter.api.Test;

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

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenCollection;

/**
 * Unit tests for the {@link CollectionUtils} class.
 *
 * @author Mohammad Yazdian
 */
@SuppressWarnings("ConstantValue")
public class CollectionUtilsTests {

    @Test
    void hasLength_ShouldReturnFalse_WhenInputCollectionIsNull() {
        // Given
        Collection<String> input = null;

        // When
        boolean actual = CollectionUtils.hasLength(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void hasLength_ShouldReturnFalse_WhenInputCollectionIsEmpty() {
        // Given
        Collection<String> input = Collections.emptyList();

        // When
        boolean actual = CollectionUtils.hasLength(input);

        // Then
        then(actual).isFalse();
    }

    @Test
    void hasLength_ShouldReturnTrue_WhenInputCollectionHasElements() {
        // Given
        Collection<String> input = Collections.singletonList("hello");

        // When
        boolean actual = CollectionUtils.hasLength(input);

        // Then
        then(actual).isTrue();
    }

    @Test
    void hasLength_ShouldReturnFalse_WhenInputMapIsNull() {
        // Given
        Map<Object, Object> map = null;

        // When
        boolean actual = CollectionUtils.hasLength(map);

        // Then
        then(actual).isFalse();
    }

    @Test
    void hasLength_ShouldReturnFalse_WhenInputMapIsEmpty() {
        // Given
        Map<Object, Object> map = Collections.emptyMap();

        // When
        boolean actual = CollectionUtils.hasLength(map);

        // Then
        then(actual).isFalse();
    }

    @Test
    void hasLength_ShouldReturnTrue_WhenInputMapHasElements() {
        // Given
        Map<String, String> map = Collections.singletonMap("hello", "world");

        // When
        boolean actual = CollectionUtils.hasLength(map);

        // Then
        then(actual).isTrue();
    }

    @Test
    void merge_ShouldReturnEmptyList_WhenBothInputsAreNull() {
        // Given
        List<String> inputDominant = null;
        List<String> inputRecessive = null;
        String[] inputRecessiveArray = null;

        // When
        List<String> actual = CollectionUtils.merge(inputDominant, inputRecessive);

        // Then
        thenCollection(actual).isInstanceOf(ArrayList.class).isNotNull().isEmpty();

        // When
        actual = CollectionUtils.merge(inputDominant, inputRecessiveArray);

        // Then
        thenCollection(actual).isInstanceOf(ArrayList.class).isNotNull().isEmpty();
    }

    @Test
    void merge_ShouldReturnEmptyList_WhenBothInputsAreEmpty() {
        // Given
        List<String> inputDominant = Collections.emptyList();
        List<String> inputRecessive = Collections.emptyList();
        String[] inputRecessiveArray = new String[]{};

        // When
        List<String> actual = CollectionUtils.merge(inputDominant, inputRecessive);

        // Then
        thenCollection(actual).isInstanceOf(ArrayList.class).isNotNull().isEmpty();

        // When
        actual = CollectionUtils.merge(inputDominant, inputRecessiveArray);

        // Then
        thenCollection(actual).isInstanceOf(ArrayList.class).isNotNull().isEmpty();
    }

    @Test
    void merge_ShouldReturnDominantList_WhenRecessiveListIsNull() {
        // Given
        List<String> inputDominant = List.of("a", "b", "c");
        List<String> inputRecessive = null;
        String[] inputRecessiveArray = null;

        // When
        List<String> actual = CollectionUtils.merge(inputDominant, inputRecessive);

        // Then
        thenCollection(actual)
                .isInstanceOf(ArrayList.class)
                .isNotNull()
                .containsExactly("a", "b", "c");

        // When
        actual = CollectionUtils.merge(inputDominant, inputRecessiveArray);

        // Then
        thenCollection(actual)
                .isInstanceOf(ArrayList.class)
                .isNotNull()
                .containsExactly("a", "b", "c");
    }

    @Test
    void merge_ShouldReturnDominantList_WhenRecessiveListIsEmpty() {
        // Given
        List<String> inputDominant = List.of("a", "b", "c");
        List<String> inputRecessive = Collections.emptyList();
        String[] inputRecessiveArray = new String[]{};

        // When
        List<String> actual = CollectionUtils.merge(inputDominant, inputRecessive);

        // Then
        thenCollection(actual)
                .isInstanceOf(ArrayList.class)
                .isNotNull()
                .containsExactly("a", "b", "c");

        // When
        actual = CollectionUtils.merge(inputDominant, inputRecessiveArray);

        // Then
        thenCollection(actual)
                .isInstanceOf(ArrayList.class)
                .isNotNull()
                .containsExactly("a", "b", "c");
    }

    @Test
    void merge_ShouldReturnRecessiveList_WhenDominantListIsNull() {
        // Given
        List<String> inputDominant = null;
        List<String> inputRecessive = List.of("x", "y", "z");
        String[] inputRecessiveArray = new String[]{"x", "y", "z"};

        // When
        List<String> actual = CollectionUtils.merge(inputDominant, inputRecessive);

        // Then
        thenCollection(actual)
                .isInstanceOf(ArrayList.class)
                .isNotNull()
                .containsExactly("x", "y", "z");

        // When
        actual = CollectionUtils.merge(inputDominant, inputRecessiveArray);

        // Then
        thenCollection(actual)
                .isInstanceOf(ArrayList.class)
                .isNotNull()
                .containsExactly("x", "y", "z");
    }

    @Test
    void merge_ShouldReturnRecessiveList_WhenDominantListIsEmpty() {
        // Given
        List<String> inputDominant = Collections.emptyList();
        List<String> inputRecessive = List.of("x", "y", "z");
        String[] inputRecessiveArray = new String[]{"x", "y", "z"};

        // When
        List<String> actual = CollectionUtils.merge(inputDominant, inputRecessive);

        // Then
        thenCollection(actual)
                .isInstanceOf(ArrayList.class)
                .isNotNull()
                .containsExactly("x", "y", "z");

        // When
        actual = CollectionUtils.merge(inputDominant, inputRecessiveArray);

        // Then
        thenCollection(actual)
                .isInstanceOf(ArrayList.class)
                .isNotNull()
                .containsExactly("x", "y", "z");
    }

    @Test
    void merge_ShouldReturnMergedListWithUniqueElements_WhenBothListsHaveElements() {
        // Given
        List<String> inputDominant = List.of("a", "b", "c");
        List<String> inputRecessive = List.of("b", "d", "e");
        String[] inputRecessiveArray = new String[]{"b", "d", "e"};

        // When
        List<String> actual = CollectionUtils.merge(inputDominant, inputRecessive);

        // Then
        thenCollection(actual)
                .isInstanceOf(ArrayList.class)
                .isNotNull()
                .containsExactly("a", "b", "c", "d", "e");

        // When
        actual = CollectionUtils.merge(inputDominant, inputRecessiveArray);

        // Then
        thenCollection(actual)
                .isInstanceOf(ArrayList.class)
                .isNotNull()
                .containsExactly("a", "b", "c", "d", "e");
    }

    @Test
    void merge_ShouldReturnEmptySet_WhenBothInputsAreNull() {
        // Given
        Set<String> inputDominant = null;
        Set<String> inputRecessive = null;
        String[] inputRecessiveArray = null;

        // When
        Set<String> actual = CollectionUtils.merge(inputDominant, inputRecessive);

        // Then
        thenCollection(actual).isInstanceOf(HashSet.class).isNotNull().isEmpty();

        // When
        actual = CollectionUtils.merge(inputDominant, inputRecessiveArray);

        // Then
        thenCollection(actual).isInstanceOf(HashSet.class).isNotNull().isEmpty();
    }

    @Test
    void merge_ShouldReturnEmptySet_WhenBothInputsAreEmpty() {
        // Given
        Set<String> inputDominant = Collections.emptySet();
        Set<String> inputRecessive = Collections.emptySet();
        String[] inputRecessiveArray = new String[]{};

        // When
        Set<String> actual = CollectionUtils.merge(inputDominant, inputRecessive);

        // Then
        thenCollection(actual).isInstanceOf(HashSet.class).isNotNull().isEmpty();

        // When
        actual = CollectionUtils.merge(inputDominant, inputRecessiveArray);

        // Then
        thenCollection(actual).isInstanceOf(HashSet.class).isNotNull().isEmpty();
    }

    @Test
    void merge_ShouldReturnDominantSet_WhenRecessiveSetIsNull() {
        // Given
        Set<String> inputDominant = Set.of("a", "b", "c");
        Set<String> inputRecessive = null;
        String[] inputRecessiveArray = null;

        // When
        Set<String> actual = CollectionUtils.merge(inputDominant, inputRecessive);

        // Then
        thenCollection(actual)
                .isInstanceOf(HashSet.class)
                .isNotNull()
                .containsExactlyInAnyOrder("a", "b", "c");

        // When
        actual = CollectionUtils.merge(inputDominant, inputRecessiveArray);

        // Then
        thenCollection(actual)
                .isInstanceOf(HashSet.class)
                .isNotNull()
                .containsExactlyInAnyOrder("a", "b", "c");
    }

    @Test
    void merge_ShouldReturnDominantSet_WhenRecessiveSetIsEmpty() {
        // Given
        Set<String> inputDominant = Set.of("a", "b", "c");
        Set<String> inputRecessive = Collections.emptySet();
        String[] inputRecessiveArray = new String[]{};

        // When
        Set<String> actual = CollectionUtils.merge(inputDominant, inputRecessive);

        // Then
        thenCollection(actual)
                .isInstanceOf(HashSet.class)
                .isNotNull()
                .containsExactlyInAnyOrder("a", "b", "c");

        // When
        actual = CollectionUtils.merge(inputDominant, inputRecessiveArray);

        // Then
        thenCollection(actual)
                .isInstanceOf(HashSet.class)
                .isNotNull()
                .containsExactlyInAnyOrder("a", "b", "c");
    }

    @Test
    void merge_ShouldReturnRecessiveSet_WhenDominantSetIsNull() {
        // Given
        Set<String> inputDominant = null;
        Set<String> inputRecessive = Set.of("x", "y", "z");
        String[] inputRecessiveArray = new String[]{"x", "y", "z"};

        // When
        Set<String> actual = CollectionUtils.merge(inputDominant, inputRecessive);

        // Then
        thenCollection(actual)
                .isInstanceOf(HashSet.class)
                .isNotNull()
                .containsExactlyInAnyOrder("x", "y", "z");

        // When
        actual = CollectionUtils.merge(inputDominant, inputRecessiveArray);

        // Then
        thenCollection(actual)
                .isInstanceOf(HashSet.class)
                .isNotNull()
                .containsExactlyInAnyOrder("x", "y", "z");
    }

    @Test
    void merge_ShouldReturnRecessiveSet_WhenDominantSetIsEmpty() {
        // Given
        Set<String> inputDominant = Collections.emptySet();
        Set<String> inputRecessive = Set.of("x", "y", "z");
        String[] inputRecessiveArray = new String[]{"x", "y", "z"};

        // When
        Set<String> actual = CollectionUtils.merge(inputDominant, inputRecessive);

        // Then
        thenCollection(actual)
                .isInstanceOf(HashSet.class)
                .isNotNull()
                .containsExactlyInAnyOrder("x", "y", "z");

        // When
        actual = CollectionUtils.merge(inputDominant, inputRecessiveArray);

        // Then
        thenCollection(actual)
                .isInstanceOf(HashSet.class)
                .isNotNull()
                .containsExactlyInAnyOrder("x", "y", "z");
    }

    @Test
    void merge_ShouldReturnMergedSetWithUniqueElements_WhenBothSetsHaveElements() {
        // Given
        Set<String> inputDominant = new HashSet<>(Set.of("a", "b", "c"));
        Set<String> inputRecessive = new HashSet<>(Set.of("b", "d", "e"));
        String[] inputRecessiveArray = new String[]{"b", "d", "e"};

        // When
        Set<String> actual = CollectionUtils.merge(inputDominant, inputRecessive);

        // Then
        thenCollection(actual)
                .isInstanceOf(HashSet.class)
                .isNotNull()
                .containsExactlyInAnyOrder("a", "b", "c", "d", "e");

        // When
        actual = CollectionUtils.merge(inputDominant, inputRecessiveArray);

        // Then
        thenCollection(actual)
                .isInstanceOf(HashSet.class)
                .isNotNull()
                .containsExactlyInAnyOrder("a", "b", "c", "d", "e");
    }

    @Test
    void merge_ShouldReturnEmptyMap_WhenBothInputsAreNull() {
        // Given
        Map<String, Integer> inputDominant = null;
        Map<String, Integer> inputRecessive = null;

        // When
        Map<String, Integer> actual = CollectionUtils.merge(inputDominant, inputRecessive);

        // Then
        then(actual).isInstanceOf(HashMap.class).isNotNull().isEmpty();
    }

    @Test
    void merge_ShouldReturnEmptyMap_WhenBothInputsAreEmpty() {
        // Given
        Map<String, Integer> inputDominant = Collections.emptyMap();
        Map<String, Integer> inputRecessive = Collections.emptyMap();

        // When
        Map<String, Integer> actual = CollectionUtils.merge(inputDominant, inputRecessive);

        // Then
        then(actual).isInstanceOf(HashMap.class).isNotNull().isEmpty();
    }

    @Test
    void merge_ShouldReturnDominantMap_WhenRecessiveMapIsNull() {
        // Given
        Map<String, Integer> inputDominant = new HashMap<>();
        inputDominant.put("a", 1);
        inputDominant.put("b", 2);
        Map<String, Integer> inputRecessive = null;

        // When
        Map<String, Integer> actual = CollectionUtils.merge(inputDominant, inputRecessive);

        // Then
        then(actual)
                .isInstanceOf(HashMap.class)
                .isNotEmpty()
                .containsExactlyInAnyOrderEntriesOf(inputDominant);
    }

    @Test
    void merge_ShouldReturnDominantMap_WhenRecessiveMapIsEmpty() {
        // Given
        Map<String, Integer> inputDominant = new HashMap<>();
        inputDominant.put("a", 1);
        inputDominant.put("b", 2);
        Map<String, Integer> inputRecessive = Collections.emptyMap();

        // When
        Map<String, Integer> actual = CollectionUtils.merge(inputDominant, inputRecessive);

        // Then
        then(actual)
                .isInstanceOf(HashMap.class)
                .isNotEmpty()
                .containsExactlyInAnyOrderEntriesOf(inputDominant);
    }

    @Test
    void merge_ShouldReturnRecessiveMap_WhenDominantMapIsNull() {
        // Given
        Map<String, Integer> inputDominant = null;
        Map<String, Integer> inputRecessive = new HashMap<>();
        inputRecessive.put("x", 10);
        inputRecessive.put("y", 20);

        // When
        Map<String, Integer> actual = CollectionUtils.merge(inputDominant, inputRecessive);

        // Then
        then(actual)
                .isInstanceOf(HashMap.class)
                .isNotEmpty()
                .containsExactlyInAnyOrderEntriesOf(inputRecessive);
    }

    @Test
    void merge_ShouldReturnRecessiveMap_WhenDominantMapIsEmpty() {
        // Given
        Map<String, Integer> inputDominant = Collections.emptyMap();
        Map<String, Integer> inputRecessive = new HashMap<>();
        inputRecessive.put("x", 10);
        inputRecessive.put("y", 20);

        // When
        Map<String, Integer> actual = CollectionUtils.merge(inputDominant, inputRecessive);

        // Then
        then(actual)
                .isInstanceOf(HashMap.class)
                .isNotEmpty()
                .containsExactlyInAnyOrderEntriesOf(inputRecessive);
    }

    @Test
    void merge_ShouldReturnMergedMapWithDominantPrecedence_WhenBothMapsHaveElements() {
        // Given
        Map<String, Integer> inputDominant = new HashMap<>();
        inputDominant.put("a", 1);
        inputDominant.put("b", 2);

        Map<String, Integer> inputRecessive = new HashMap<>();
        inputRecessive.put("b", 3);
        inputRecessive.put("c", 4);

        // When
        Map<String, Integer> actual = CollectionUtils.merge(inputDominant, inputRecessive);

        // Then
        then(actual)
                .isInstanceOf(HashMap.class)
                .isNotEmpty()
                .hasSize(3)
                .containsEntry("a", 1)
                .containsEntry("b", 2)
                .containsEntry("c", 4);
    }

    @Test
    void subtract_ShouldReturnEmptyCollection_WhenSourceIsNull() {
        // Given
        Collection<String> inputSource = null;
        Collection<String> inputElementsToRemove = List.of("x", "y");

        // When
        Collection<String> actual = CollectionUtils.subtract(inputSource, inputElementsToRemove);

        // Then
        thenCollection(actual)
                .isInstanceOf(ArrayList.class)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void subtract_ShouldReturnEmptyCollection_WhenSourceIsEmpty() {
        // Given
        Collection<String> inputSource = Collections.emptyList();
        Collection<String> inputElementsToRemove = List.of("x", "y");

        // When
        Collection<String> actual = CollectionUtils.subtract(inputSource, inputElementsToRemove);

        // Then
        thenCollection(actual)
                .isInstanceOf(ArrayList.class)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void subtract_ShouldReturnFirstCollection_WhenElementsToRemoveIsNull() {
        // Given
        Collection<String> inputSource = List.of("a", "b", "c");
        Collection<String> inputElementsToRemove = null;

        // When
        Collection<String> actual = CollectionUtils.subtract(inputSource, inputElementsToRemove);

        // Then
        thenCollection(actual)
                .isInstanceOf(ArrayList.class)
                .isNotNull()
                .hasSize(3)
                .containsExactly("a", "b", "c");
    }

    @Test
    void subtract_ShouldReturnFirstCollection_WhenElementsToRemoveIsEmpty() {
        // Given
        Collection<String> inputSource = List.of("a", "b", "c");
        Collection<String> inputElementsToRemove = Collections.emptyList();

        // When
        Collection<String> actual = CollectionUtils.subtract(inputSource, inputElementsToRemove);

        // Then
        thenCollection(actual)
                .isInstanceOf(ArrayList.class)
                .isNotNull()
                .hasSize(3)
                .containsExactly("a", "b", "c");
    }

    @Test
    void subtract_ShouldReturnCollectionWithRemainingElements_WhenBothCollectionsHaveElements() {
        // Given
        Collection<String> inputSource = List.of("a", "b", "c", "b");
        Collection<String> inputElementsToRemove = List.of("b", "d");

        // When
        Collection<String> actual = CollectionUtils.subtract(inputSource, inputElementsToRemove);

        // Then
        thenCollection(actual)
                .isInstanceOf(ArrayList.class)
                .isNotNull()
                .hasSize(2)
                .containsExactly("a", "c");
    }

    @Test
    void subtract_ShouldReturnEmptyCollection_WhenAllElementsAreInElementsToRemoveCollection() {
        // Given
        Collection<String> inputSource = List.of("a", "b");
        Collection<String> inputElementsToRemove = List.of("a", "b", "c");

        // When
        Collection<String> actual = CollectionUtils.subtract(inputSource, inputElementsToRemove);

        // Then
        thenCollection(actual)
                .isInstanceOf(ArrayList.class)
                .isEmpty();
    }

    @Test
    void subtract_ShouldReturnEmptyMap_WhenSourceIsNull() {
        // Given
        Map<String, Integer> inputSource = null;
        Map<String, Integer> inputElementsToRemove = Collections.singletonMap("x", 10);

        // When
        Map<String, Integer> actual = CollectionUtils.subtract(inputSource, inputElementsToRemove);

        // Then
        then(actual)
                .isInstanceOf(HashMap.class)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void subtract_ShouldReturnEmptyMap_WhenSourceIsEmpty() {
        // Given
        Map<String, Integer> inputSource = Collections.emptyMap();
        Map<String, Integer> inputElementsToRemove = Collections.singletonMap("x", 10);

        // When
        Map<String, Integer> actual = CollectionUtils.subtract(inputSource, inputElementsToRemove);

        // Then
        then(actual)
                .isInstanceOf(HashMap.class)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void subtract_ShouldReturnSourceMap_WhenElementsToRemoveIsNull() {
        // Given
        Map<String, Integer> inputSource = Collections.singletonMap("x", 10);
        Map<String, Integer> inputElementsToRemove = null;

        // When
        Map<String, Integer> actual = CollectionUtils.subtract(inputSource, inputElementsToRemove);

        // Then
        then(actual)
                .isInstanceOf(HashMap.class)
                .isNotNull()
                .containsExactlyInAnyOrderEntriesOf(inputSource);
    }

    @Test
    void subtract_ShouldReturnSourceMap_WhenElementsToRemoveIsEmpty() {
        // Given
        Map<String, Integer> inputSource = Collections.singletonMap("x", 10);
        Map<String, Integer> inputElementsToRemove = Collections.emptyMap();

        // When
        Map<String, Integer> actual = CollectionUtils.subtract(inputSource, inputElementsToRemove);

        // Then
        then(actual)
                .isInstanceOf(HashMap.class)
                .isNotNull()
                .containsExactlyInAnyOrderEntriesOf(inputSource);
    }

    @Test
    void subtract_ShouldReturnMapWithRemainingEntries_WhenBothMapsHaveElements() {
        // Given
        Map<String, Integer> inputSource = new HashMap<>();
        inputSource.put("a", 1);
        inputSource.put("b", 2);
        inputSource.put("c", 3);

        Map<String, Integer> inputElementsToRemove = new HashMap<>();
        inputElementsToRemove.put("b", 20);
        inputElementsToRemove.put("d", 40);

        // When
        Map<String, Integer> actual = CollectionUtils.subtract(inputSource, inputElementsToRemove);

        // Then
        then(actual)
                .isInstanceOf(HashMap.class)
                .isNotNull()
                .hasSize(2)
                .containsEntry("a", 1)
                .containsEntry("c", 3);
    }

    @Test
    void subtract_ShouldReturnEmptyMap_WhenAllKeysAreInElementsToRemove() {
        // Given
        Map<String, Integer> inputSource = new HashMap<>();
        inputSource.put("a", 1);
        inputSource.put("b", 2);

        Map<String, Integer> inputElementsToRemove = new HashMap<>();
        inputElementsToRemove.put("a", 10);
        inputElementsToRemove.put("b", 20);
        inputElementsToRemove.put("c", 30);

        // When
        Map<String, Integer> actual = CollectionUtils.subtract(inputSource, inputElementsToRemove);

        // Then
        then(actual)
                .isInstanceOf(HashMap.class)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void toArrayList_ShouldReturnEmptyArrayList_WhenInputIsNull() {
        // When
        ArrayList<Object> actual = CollectionUtils.toArrayList(null);

        // Then
        thenCollection(actual).isNotNull().isEmpty();
    }

    @Test
    void toArrayList_ShouldReturnEmptyArrayList_WhenInputIsEmpty() {
        // Given
        Set<Object> input = Collections.emptySet();

        // When
        ArrayList<Object> actual = CollectionUtils.toArrayList(input);

        // Then
        thenCollection(actual).isNotNull().isEmpty();
    }

    @Test
    void toArrayList_ShouldReturnArrayListWithElements_WhenInputHasElements() {
        // Given
        Set<String> input = Set.of("a", "b", "c");

        // When
        ArrayList<String> actual = CollectionUtils.toArrayList(input);

        // Then
        thenCollection(actual)
                .isNotNull()
                .isInstanceOf(ArrayList.class)
                .containsExactlyInAnyOrder("a", "b", "c");
    }

    @Test
    void toCopyOnWriteArrayList_ShouldReturnEmptyCopyOnWriteArrayList_WhenInputIsNull() {
        // When
        CopyOnWriteArrayList<Object> actual = CollectionUtils.toCopyOnWriteArrayList(null);

        // Then
        thenCollection(actual).isNotNull().isEmpty();
    }

    @Test
    void toCopyOnWriteArrayList_ShouldReturnEmptyCopyOnWriteArrayList_WhenInputIsEmpty() {
        // Given
        Set<Object> input = Collections.emptySet();

        // When
        CopyOnWriteArrayList<Object> actual = CollectionUtils.toCopyOnWriteArrayList(input);

        // Then
        thenCollection(actual).isNotNull().isEmpty();
    }

    @Test
    void toCopyOnWriteArrayList_ShouldReturnCopyOnWriteArrayListWithElements_WhenInputHasElements() {
        // Given
        Set<String> input = Set.of("a", "b", "c");

        // When
        CopyOnWriteArrayList<String> actual = CollectionUtils.toCopyOnWriteArrayList(input);

        // Then
        thenCollection(actual)
                .isNotNull()
                .isInstanceOf(CopyOnWriteArrayList.class)
                .containsExactlyInAnyOrder("a", "b", "c");
    }

    @Test
    void toLinkedList_ShouldReturnEmptyLinkedList_WhenInputIsNull() {
        // When
        LinkedList<String> actual = CollectionUtils.toLinkedList(null);

        // Then
        thenCollection(actual).isNotNull().isEmpty();
    }

    @Test
    void toLinkedList_ShouldReturnEmptyLinkedList_WhenInputIsEmpty() {
        // Given
        Set<Object> input = Collections.emptySet();

        // When
        LinkedList<Object> actual = CollectionUtils.toLinkedList(input);

        // Then
        thenCollection(actual).isNotNull().isEmpty();
    }

    @Test
    void toLinkedList_ShouldReturnLinkedListWithElements_WhenInputHasElements() {
        // Given
        Set<String> input = Set.of("a", "b", "c");

        // When
        LinkedList<String> actual = CollectionUtils.toLinkedList(input);

        // Then
        thenCollection(actual)
                .isNotNull()
                .isInstanceOf(LinkedList.class)
                .containsExactlyInAnyOrder("a", "b", "c");
    }

    @Test
    void toVector_ShouldReturnEmptyVector_WhenInputIsNull() {
        // When
        Vector<Double> actual = CollectionUtils.toVector(null);

        // Then
        thenCollection(actual).isNotNull().isEmpty();
    }

    @Test
    void toVector_ShouldReturnEmptyVector_WhenInputIsEmpty() {
        // Given
        Set<Object> input = Collections.emptySet();

        // When
        Vector<Object> actual = CollectionUtils.toVector(input);

        // Then
        thenCollection(actual).isNotNull().isEmpty();
    }

    @Test
    void toVector_ShouldReturnVectorWithElements_WhenInputHasElements() {
        // Given
        Set<String> input = Set.of("a", "b", "c");

        // When
        Vector<String> actual = CollectionUtils.toVector(input);

        // Then
        thenCollection(actual)
                .isNotNull()
                .isInstanceOf(Vector.class)
                .containsExactlyInAnyOrder("a", "b", "c");
    }

    @Test
    void toStack_ShouldReturnEmptyStack_WhenInputIsNull() {
        // When
        Stack<Object> actual = CollectionUtils.toStack(null);

        // Then
        thenCollection(actual).isNotNull().isEmpty();
    }

    @Test
    void toStack_ShouldReturnEmptyStack_WhenInputIsEmpty() {
        // Given
        Set<Object> input = Collections.emptySet();

        // When
        Stack<Object> actual = CollectionUtils.toStack(input);

        // Then
        thenCollection(actual).isNotNull().isEmpty();
    }

    @Test
    void toStack_ShouldReturnStackWithElements_WhenInputHasElements() {
        // Given
        Set<String> input = Set.of("a", "b", "c");

        // When
        Stack<String> actual = CollectionUtils.toStack(input);

        // Then
        thenCollection(actual)
                .isNotNull()
                .isInstanceOf(Stack.class)
                .containsExactlyInAnyOrder("a", "b", "c");
    }
}
