package ir.artanpg.commons.utils;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
    void constructor_ShouldThrowUnsupportedOperationException_WhenAttemptingInstantiation() throws NoSuchMethodException {
        // Given
        Constructor<CollectionUtils> input = CollectionUtils.class.getDeclaredConstructor();
        input.setAccessible(true); // Make private constructor accessible

        // When & Then
        assertThatThrownBy(input::newInstance)
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessage("This class cannot be instantiated");
    }

    @SuppressWarnings("DataFlowIssue")
    @Test
    void hasLength_ShouldReturnFalse_WhenInputIsNull() {
        // Given
        Collection<?> inputCollection = null;
        Map<?, ?> inputMap = null;

        // When
        boolean actual = CollectionUtils.hasLength(inputCollection);

        // Then
        then(actual).isFalse();

        // When
        actual = CollectionUtils.hasLength(inputMap);

        // Then
        then(actual).isFalse();
    }

    @Test
    void hasLength_ShouldReturnFalse_WhenInputIsEmpty() {
        // Given
        Collection<?> inputCollection = Collections.emptyList();
        Map<?, ?> inputMap = Collections.emptyMap();

        // When
        boolean actual = CollectionUtils.hasLength(inputCollection);

        // Then
        then(actual).isFalse();

        // When
        actual = CollectionUtils.hasLength(inputMap);

        // Then
        then(actual).isFalse();
    }

    @Test
    void hasLength_ShouldReturnTrue_WhenInputHasElements() {
        // Given
        Collection<?> inputCollection = Collections.singletonList("hello");
        Map<?, ?> inputMap = Collections.singletonMap("hello", "world");

        // When
        boolean actual = CollectionUtils.hasLength(inputCollection);

        // Then
        then(actual).isTrue();

        // When
        actual = CollectionUtils.hasLength(inputMap);

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
        thenCollection(actual)
                .isNotNull()
                .isInstanceOf(ArrayList.class)
                .isEmpty();

        // When
        actual = CollectionUtils.merge(inputDominant, inputRecessiveArray);

        // Then
        thenCollection(actual)
                .isNotNull()
                .isInstanceOf(ArrayList.class)
                .isEmpty();
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
        thenCollection(actual)
                .isNotNull()
                .isInstanceOf(ArrayList.class)
                .isEmpty();

        // When
        actual = CollectionUtils.merge(inputDominant, inputRecessiveArray);

        // Then
        thenCollection(actual)
                .isNotNull()
                .isInstanceOf(ArrayList.class)
                .isEmpty();
    }

    @Test
    void merge_ShouldReturnDominantList_WhenRecessiveIsNull() {
        // Given
        List<String> inputDominant = List.of("a", "b", "c");
        List<String> inputRecessive = null;
        String[] inputRecessiveArray = null;

        // When
        List<String> actual = CollectionUtils.merge(inputDominant, inputRecessive);

        // Then
        thenCollection(actual)
                .isNotNull()
                .isInstanceOf(ArrayList.class)
                .containsExactly("a", "b", "c");

        // When
        actual = CollectionUtils.merge(inputDominant, inputRecessiveArray);

        // Then
        thenCollection(actual)
                .isNotNull()
                .isInstanceOf(ArrayList.class)
                .containsExactly("a", "b", "c");
    }

    @Test
    void merge_ShouldReturnDominantList_WhenRecessiveIsEmpty() {
        // Given
        List<String> inputDominant = List.of("a", "b", "c");
        List<String> inputRecessive = Collections.emptyList();
        String[] inputRecessiveArray = new String[]{};

        // When
        List<String> actual = CollectionUtils.merge(inputDominant, inputRecessive);

        // Then
        thenCollection(actual)
                .isNotNull()
                .isInstanceOf(ArrayList.class)
                .containsExactly("a", "b", "c");

        // When
        actual = CollectionUtils.merge(inputDominant, inputRecessiveArray);

        // Then
        thenCollection(actual)
                .isNotNull()
                .isInstanceOf(ArrayList.class)
                .containsExactly("a", "b", "c");
    }

    @Test
    void merge_ShouldReturnRecessiveList_WhenDominantIsNull() {
        // Given
        List<String> inputDominant = null;
        List<String> inputRecessive = List.of("x", "y", "z");
        String[] inputRecessiveArray = new String[]{"x", "y", "z"};

        // When
        List<String> actual = CollectionUtils.merge(inputDominant, inputRecessive);

        // Then
        thenCollection(actual)
                .isNotNull()
                .isInstanceOf(ArrayList.class)
                .containsExactly("x", "y", "z");

        // When
        actual = CollectionUtils.merge(inputDominant, inputRecessiveArray);

        // Then
        thenCollection(actual)
                .isNotNull()
                .isInstanceOf(ArrayList.class)
                .containsExactly("x", "y", "z");
    }

    @Test
    void merge_ShouldReturnRecessiveList_WhenDominantIsEmpty() {
        // Given
        List<String> inputDominant = Collections.emptyList();
        List<String> inputRecessive = List.of("x", "y", "z");
        String[] inputRecessiveArray = new String[]{"x", "y", "z"};

        // When
        List<String> actual = CollectionUtils.merge(inputDominant, inputRecessive);

        // Then
        thenCollection(actual)
                .isNotNull()
                .isInstanceOf(ArrayList.class)
                .containsExactly("x", "y", "z");

        // When
        actual = CollectionUtils.merge(inputDominant, inputRecessiveArray);

        // Then
        thenCollection(actual)
                .isNotNull()
                .isInstanceOf(ArrayList.class)
                .containsExactly("x", "y", "z");
    }

    @Test
    void merge_ShouldReturnMergedListWithUniqueElements_WhenBothInputsHaveElements() {
        // Given
        List<String> inputDominant = List.of("a", "b", "c");
        List<String> inputRecessive = List.of("b", "d", "e");
        String[] inputRecessiveArray = new String[]{"b", "d", "e"};

        // When
        List<String> actual = CollectionUtils.merge(inputDominant, inputRecessive);

        // Then
        thenCollection(actual)
                .isNotNull()
                .isInstanceOf(ArrayList.class)
                .containsExactly("a", "b", "c", "d", "e");

        // When
        actual = CollectionUtils.merge(inputDominant, inputRecessiveArray);

        // Then
        thenCollection(actual)
                .isNotNull()
                .isInstanceOf(ArrayList.class)
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
        thenCollection(actual)
                .isNotNull()
                .isInstanceOf(HashSet.class)
                .isEmpty();

        // When
        actual = CollectionUtils.merge(inputDominant, inputRecessiveArray);

        // Then
        thenCollection(actual)
                .isNotNull()
                .isInstanceOf(HashSet.class)
                .isEmpty();
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
        thenCollection(actual)
                .isNotNull()
                .isInstanceOf(HashSet.class)
                .isEmpty();

        // When
        actual = CollectionUtils.merge(inputDominant, inputRecessiveArray);

        // Then
        thenCollection(actual)
                .isNotNull()
                .isInstanceOf(HashSet.class)
                .isEmpty();
    }

    @Test
    void merge_ShouldReturnDominantSet_WhenRecessiveIsNull() {
        // Given
        Set<String> inputDominant = Set.of("a", "b", "c");
        Set<String> inputRecessive = null;
        String[] inputRecessiveArray = null;

        // When
        Set<String> actual = CollectionUtils.merge(inputDominant, inputRecessive);

        // Then
        thenCollection(actual)
                .isNotNull()
                .isInstanceOf(HashSet.class)
                .containsExactlyInAnyOrder("a", "b", "c");

        // When
        actual = CollectionUtils.merge(inputDominant, inputRecessiveArray);

        // Then
        thenCollection(actual)
                .isNotNull()
                .isInstanceOf(HashSet.class)
                .containsExactlyInAnyOrder("a", "b", "c");
    }

    @Test
    void merge_ShouldReturnDominantSet_WhenRecessiveIsEmpty() {
        // Given
        Set<String> inputDominant = Set.of("a", "b", "c");
        Set<String> inputRecessive = Collections.emptySet();
        String[] inputRecessiveArray = new String[]{};

        // When
        Set<String> actual = CollectionUtils.merge(inputDominant, inputRecessive);

        // Then
        thenCollection(actual)
                .isNotNull()
                .isInstanceOf(HashSet.class)
                .containsExactlyInAnyOrder("a", "b", "c");

        // When
        actual = CollectionUtils.merge(inputDominant, inputRecessiveArray);

        // Then
        thenCollection(actual)
                .isNotNull()
                .isInstanceOf(HashSet.class)
                .containsExactlyInAnyOrder("a", "b", "c");
    }

    @Test
    void merge_ShouldReturnRecessiveSet_WhenDominantIsNull() {
        // Given
        Set<String> inputDominant = null;
        Set<String> inputRecessive = Set.of("x", "y", "z");
        String[] inputRecessiveArray = new String[]{"x", "y", "z"};

        // When
        Set<String> actual = CollectionUtils.merge(inputDominant, inputRecessive);

        // Then
        thenCollection(actual)
                .isNotNull()
                .isInstanceOf(HashSet.class)
                .containsExactlyInAnyOrder("x", "y", "z");

        // When
        actual = CollectionUtils.merge(inputDominant, inputRecessiveArray);

        // Then
        thenCollection(actual)
                .isNotNull()
                .isInstanceOf(HashSet.class)
                .containsExactlyInAnyOrder("x", "y", "z");
    }

    @Test
    void merge_ShouldReturnRecessiveSet_WhenDominantIsEmpty() {
        // Given
        Set<String> inputDominant = Collections.emptySet();
        Set<String> inputRecessive = Set.of("x", "y", "z");
        String[] inputRecessiveArray = new String[]{"x", "y", "z"};

        // When
        Set<String> actual = CollectionUtils.merge(inputDominant, inputRecessive);

        // Then
        thenCollection(actual)
                .isNotNull()
                .isInstanceOf(HashSet.class)
                .containsExactlyInAnyOrder("x", "y", "z");

        // When
        actual = CollectionUtils.merge(inputDominant, inputRecessiveArray);

        // Then
        thenCollection(actual)
                .isNotNull()
                .isInstanceOf(HashSet.class)
                .containsExactlyInAnyOrder("x", "y", "z");
    }

    @Test
    void merge_ShouldReturnMergedSetWithUniqueElements_WhenBothInputsHaveElements() {
        // Given
        Set<String> inputDominant = new HashSet<>(Set.of("a", "b", "c"));
        Set<String> inputRecessive = new HashSet<>(Set.of("b", "d", "e"));
        String[] inputRecessiveArray = new String[]{"b", "d", "e"};

        // When
        Set<String> actual = CollectionUtils.merge(inputDominant, inputRecessive);

        // Then
        thenCollection(actual)
                .isNotNull()
                .isInstanceOf(HashSet.class)
                .containsExactlyInAnyOrder("a", "b", "c", "d", "e");

        // When
        actual = CollectionUtils.merge(inputDominant, inputRecessiveArray);

        // Then
        thenCollection(actual)
                .isNotNull()
                .isInstanceOf(HashSet.class)
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
        then(actual)
                .isNotNull()
                .isInstanceOf(HashMap.class)
                .isEmpty();
    }

    @Test
    void merge_ShouldReturnEmptyMap_WhenBothInputsAreEmpty() {
        // Given
        Map<String, Integer> inputDominant = Collections.emptyMap();
        Map<String, Integer> inputRecessive = Collections.emptyMap();

        // When
        Map<String, Integer> actual = CollectionUtils.merge(inputDominant, inputRecessive);

        // Then
        then(actual)
                .isNotNull()
                .isInstanceOf(HashMap.class)
                .isEmpty();
    }

    @Test
    void merge_ShouldReturnDominantMap_WhenRecessiveIsNull() {
        // Given
        Map<String, Integer> inputDominant = new HashMap<>();
        inputDominant.put("a", 1);
        inputDominant.put("b", 2);
        Map<String, Integer> inputRecessive = null;

        // When
        Map<String, Integer> actual = CollectionUtils.merge(inputDominant, inputRecessive);

        // Then
        then(actual)
                .isNotEmpty()
                .isInstanceOf(HashMap.class)
                .containsEntry("a", 1)
                .containsEntry("b", 2);
    }

    @Test
    void merge_ShouldReturnDominantMap_WhenRecessiveIsEmpty() {
        // Given
        Map<String, Integer> inputDominant = new HashMap<>();
        inputDominant.put("a", 1);
        inputDominant.put("b", 2);
        Map<String, Integer> inputRecessive = Collections.emptyMap();

        // When
        Map<String, Integer> actual = CollectionUtils.merge(inputDominant, inputRecessive);

        // Then
        then(actual)
                .isNotNull()
                .isInstanceOf(HashMap.class)
                .containsEntry("a", 1)
                .containsEntry("b", 2);
    }

    @Test
    void merge_ShouldReturnRecessiveMap_WhenDominantIsNull() {
        // Given
        Map<String, Integer> inputDominant = null;
        Map<String, Integer> inputRecessive = new HashMap<>();
        inputRecessive.put("x", 10);
        inputRecessive.put("y", 20);

        // When
        Map<String, Integer> actual = CollectionUtils.merge(inputDominant, inputRecessive);

        // Then
        then(actual)
                .isNotNull()
                .isInstanceOf(HashMap.class)
                .hasSize(2)
                .containsEntry("x", 10)
                .containsEntry("y", 20);
    }

    @Test
    void merge_ShouldReturnRecessiveMap_WhenDominantIsEmpty() {
        // Given
        Map<String, Integer> inputDominant = Collections.emptyMap();
        Map<String, Integer> inputRecessive = new HashMap<>();
        inputRecessive.put("x", 10);
        inputRecessive.put("y", 20);

        // When
        Map<String, Integer> actual = CollectionUtils.merge(inputDominant, inputRecessive);

        // Then
        then(actual)
                .isNotNull()
                .isInstanceOf(HashMap.class)
                .hasSize(2)
                .containsEntry("x", 10)
                .containsEntry("y", 20);
    }

    @Test
    void merge_ShouldReturnMergedMapWithDominantPrecedence_WhenBothInputsHaveElements() {
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
                .isNotNull()
                .isInstanceOf(HashMap.class)
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
                .isNotNull()
                .isInstanceOf(ArrayList.class)
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
                .isNotNull()
                .isInstanceOf(ArrayList.class)
                .isEmpty();
    }

    @Test
    void subtract_ShouldReturnSourceCollection_WhenElementsToRemoveIsNull() {
        // Given
        Collection<String> inputSource = List.of("a", "b", "c");
        Collection<String> inputElementsToRemove = null;

        // When
        Collection<String> actual = CollectionUtils.subtract(inputSource, inputElementsToRemove);

        // Then
        thenCollection(actual)
                .isNotNull()
                .isInstanceOf(ArrayList.class)
                .hasSize(3)
                .containsExactly("a", "b", "c");
    }

    @Test
    void subtract_ShouldReturnSourceCollection_WhenElementsToRemoveIsEmpty() {
        // Given
        Collection<String> inputSource = List.of("a", "b", "c");
        Collection<String> inputElementsToRemove = Collections.emptyList();

        // When
        Collection<String> actual = CollectionUtils.subtract(inputSource, inputElementsToRemove);

        // Then
        thenCollection(actual)
                .isNotNull()
                .isInstanceOf(ArrayList.class)
                .hasSize(3)
                .containsExactly("a", "b", "c");
    }

    @Test
    void subtract_ShouldReturnEmptyCollection_WhenBothInputsAreNull() {
        // Given
        Collection<String> inputSource = null;
        Collection<String> inputElementsToRemove = null;

        // When
        Collection<String> actual = CollectionUtils.subtract(inputSource, inputElementsToRemove);

        // Then
        thenCollection(actual)
                .isNotNull()
                .isInstanceOf(ArrayList.class)
                .isEmpty();
    }

    @Test
    void subtract_ShouldReturnEmptyCollection_WhenBothInputsAreEmpty() {
        // Given
        Collection<String> inputSource = Collections.emptyList();
        Collection<String> inputElementsToRemove = Collections.emptyList();

        // When
        Collection<String> actual = CollectionUtils.subtract(inputSource, inputElementsToRemove);

        // Then
        thenCollection(actual)
                .isNotNull()
                .isInstanceOf(ArrayList.class)
                .isEmpty();
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
                .isNotNull()
                .isInstanceOf(ArrayList.class)
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
                .isNotNull()
                .isInstanceOf(HashMap.class)
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
                .isNotNull()
                .isInstanceOf(HashMap.class)
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
                .isNotNull()
                .isInstanceOf(HashMap.class)
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
                .isNotNull()
                .isInstanceOf(HashMap.class)
                .containsExactlyInAnyOrderEntriesOf(inputSource);
    }

    @Test
    void subtract_ShouldReturnEmptyMap_WhenBothInputsAreNull() {
        // Given
        Map<String, Integer> inputSource = null;
        Map<String, Integer> inputElementsToRemove = null;

        // When
        Map<String, Integer> actual = CollectionUtils.subtract(inputSource, inputElementsToRemove);

        // Then
        then(actual)
                .isNotNull()
                .isInstanceOf(HashMap.class)
                .isEmpty();
    }

    @Test
    void subtract_ShouldReturnEmptyMap_WhenBothInputsAreEmpty() {
        // Given
        Map<String, Integer> inputSource = Collections.emptyMap();
        Map<String, Integer> inputElementsToRemove = Collections.emptyMap();

        // When
        Map<String, Integer> actual = CollectionUtils.subtract(inputSource, inputElementsToRemove);

        // Then
        then(actual)
                .isNotNull()
                .isInstanceOf(HashMap.class)
                .isEmpty();
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
                .isNotNull()
                .isInstanceOf(HashMap.class)
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
                .isNotNull()
                .isInstanceOf(HashMap.class)
                .isEmpty();
    }

    @Test
    void toArrayList_ShouldReturnEmptyArrayList_WhenInputIsNull() {
        // Given
        Set<Object> input = null;

        // When
        ArrayList<Object> actual = CollectionUtils.toArrayList(input);

        // Then
        thenCollection(actual)
                .isNotNull()
                .isInstanceOf(ArrayList.class)
                .isEmpty();
    }

    @Test
    void toArrayList_ShouldReturnEmptyArrayList_WhenInputIsEmpty() {
        // Given
        Set<Object> input = Collections.emptySet();

        // When
        ArrayList<Object> actual = CollectionUtils.toArrayList(input);

        // Then
        thenCollection(actual)
                .isNotNull()
                .isInstanceOf(ArrayList.class)
                .isEmpty();
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
        // Given
        Set<Object> input = null;

        // When
        CopyOnWriteArrayList<Object> actual = CollectionUtils.toCopyOnWriteArrayList(input);

        // Then
        thenCollection(actual)
                .isNotNull()
                .isInstanceOf(CopyOnWriteArrayList.class)
                .isEmpty();
    }

    @Test
    void toCopyOnWriteArrayList_ShouldReturnEmptyCopyOnWriteArrayList_WhenInputIsEmpty() {
        // Given
        Set<Object> input = Collections.emptySet();

        // When
        CopyOnWriteArrayList<Object> actual = CollectionUtils.toCopyOnWriteArrayList(input);

        // Then
        thenCollection(actual)
                .isNotNull()
                .isInstanceOf(CopyOnWriteArrayList.class)
                .isEmpty();
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
        // Given
        Set<Object> input = null;

        // When
        LinkedList<Object> actual = CollectionUtils.toLinkedList(input);

        // Then
        thenCollection(actual)
                .isNotNull()
                .isInstanceOf(LinkedList.class)
                .isEmpty();
    }

    @Test
    void toLinkedList_ShouldReturnEmptyLinkedList_WhenInputIsEmpty() {
        // Given
        Set<Object> input = Collections.emptySet();

        // When
        LinkedList<Object> actual = CollectionUtils.toLinkedList(input);

        // Then
        thenCollection(actual)
                .isNotNull()
                .isInstanceOf(LinkedList.class)
                .isEmpty();
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
        // Given
        Set<Object> input = null;

        // When
        Vector<Object> actual = CollectionUtils.toVector(input);

        // Then
        thenCollection(actual)
                .isNotNull()
                .isInstanceOf(Vector.class)
                .isEmpty();
    }

    @Test
    void toVector_ShouldReturnEmptyVector_WhenInputIsEmpty() {
        // Given
        Set<Object> input = Collections.emptySet();

        // When
        Vector<Object> actual = CollectionUtils.toVector(input);

        // Then
        thenCollection(actual)
                .isNotNull()
                .isInstanceOf(Vector.class)
                .isEmpty();
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
        // Given
        Set<Object> input = null;

        // When
        Stack<Object> actual = CollectionUtils.toStack(input);

        // Then
        thenCollection(actual)
                .isNotNull()
                .isInstanceOf(Stack.class)
                .isEmpty();
    }

    @Test
    void toStack_ShouldReturnEmptyStack_WhenInputIsEmpty() {
        // Given
        Set<Object> input = Collections.emptySet();

        // When
        Stack<Object> actual = CollectionUtils.toStack(input);

        // Then
        thenCollection(actual)
                .isNotNull()
                .isInstanceOf(Stack.class)
                .isEmpty();
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

    @Test
    void toHashSet_ShouldReturnEmptyHashSet_WhenInputIsNull() {
        // Given
        List<Object> input = null;

        // When
        HashSet<Object> actual = CollectionUtils.toHashSet(input);

        // Then
        thenCollection(actual)
                .isNotNull()
                .isInstanceOf(HashSet.class)
                .isEmpty();
    }

    @Test
    void toHashSet_ShouldReturnEmptyHashSet_WhenInputIsEmpty() {
        // Given
        List<Object> input = Collections.emptyList();

        // When
        HashSet<Object> actual = CollectionUtils.toHashSet(input);

        // Then
        thenCollection(actual)
                .isNotNull()
                .isInstanceOf(HashSet.class)
                .isEmpty();
    }

    @Test
    void toHashSet_ShouldReturnHashSetWithElements_WhenInputHasElements() {
        // Given
        List<String> input = List.of("a", "b", "c");

        // When
        HashSet<String> actual = CollectionUtils.toHashSet(input);

        // Then
        thenCollection(actual)
                .isNotNull()
                .isInstanceOf(HashSet.class)
                .containsExactlyInAnyOrder("a", "b", "c");
    }

    @Test
    void toLinkedHashSet_ShouldReturnEmptyLinkedHashSet_WhenInputIsNull() {
        // Given
        List<Object> input = null;

        // When
        LinkedHashSet<Object> actual = CollectionUtils.toLinkedHashSet(input);

        // Then
        thenCollection(actual)
                .isNotNull()
                .isInstanceOf(LinkedHashSet.class)
                .isEmpty();
    }

    @Test
    void toLinkedHashSet_ShouldReturnEmptyLinkedHashSet_WhenInputIsEmpty() {
        // Given
        List<Object> input = Collections.emptyList();

        // When
        LinkedHashSet<Object> actual = CollectionUtils.toLinkedHashSet(input);

        // Then
        thenCollection(actual)
                .isNotNull()
                .isInstanceOf(LinkedHashSet.class)
                .isEmpty();
    }

    @Test
    void toLinkedHashSet_ShouldReturnLinkedHashSetWithElements_WhenInputHasElements() {
        // Given
        List<String> input = List.of("a", "b", "c");

        // When
        LinkedHashSet<String> actual = CollectionUtils.toLinkedHashSet(input);

        // Then
        thenCollection(actual)
                .isNotNull()
                .isInstanceOf(LinkedHashSet.class)
                .containsExactlyInAnyOrder("a", "b", "c");
    }

    @Test
    void toCopyOnWriteArraySet_ShouldReturnEmptyCopyOnWriteArraySet_WhenInputIsNull() {
        // Given
        List<Object> input = null;

        // When
        CopyOnWriteArraySet<Object> actual = CollectionUtils.toCopyOnWriteArraySet(input);

        // Then
        thenCollection(actual)
                .isNotNull()
                .isInstanceOf(CopyOnWriteArraySet.class)
                .isEmpty();
    }

    @Test
    void toCopyOnWriteArraySet_ShouldReturnEmptyCopyOnWriteArraySet_WhenInputIsEmpty() {
        // Given
        List<Object> input = Collections.emptyList();

        // When
        CopyOnWriteArraySet<Object> actual = CollectionUtils.toCopyOnWriteArraySet(input);

        // Then
        thenCollection(actual)
                .isNotNull()
                .isInstanceOf(CopyOnWriteArraySet.class)
                .isEmpty();
    }

    @Test
    void toCopyOnWriteArraySet_ShouldReturnCopyOnWriteArraySetWithElements_WhenInputHasElements() {
        // Given
        List<String> input = List.of("a", "b", "c");

        // When
        CopyOnWriteArraySet<String> actual = CollectionUtils.toCopyOnWriteArraySet(input);

        // Then
        thenCollection(actual)
                .isNotNull()
                .isInstanceOf(CopyOnWriteArraySet.class)
                .containsExactlyInAnyOrder("a", "b", "c");
    }
}
