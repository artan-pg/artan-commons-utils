package ir.artanpg.commons.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
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

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static java.util.Collections.emptySet;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenException;

/**
 * Unit tests for the {@link CollectionUtils} class.
 *
 * @author Mohammad Yazdian
 */
@DisplayName("CollectionUtils")
@SuppressWarnings({"ConstantValue", "DataFlowIssue"})
class CollectionUtilsTests {

    @Nested
    @DisplayName("contains(Collection<T>, T)")
    class ContainsCollectionWithItem {

        @Test
        void contains_ShouldReturnFalse_WhenCollectionIsNull() {
            // Given
            Collection<String> input = null;

            // When
            boolean actual = CollectionUtils.contains(input, "a");

            // Then
            then(actual).isFalse();
        }

        @Test
        void contains_ShouldReturnFalse_WhenCollectionIsEmpty() {
            // Given
            Collection<String> input = emptyList();

            // When
            boolean actual = CollectionUtils.contains(input, "a");

            // Then
            then(actual).isFalse();
        }

        @Test
        void contains_ShouldReturnTrue_WhenCandidateIsNull() {
            // Given
            Collection<String> input = Arrays.asList("a", null, "c");

            // When
            boolean actual = CollectionUtils.contains(input, (String) null);

            // Then
            then(actual).isTrue();
        }

        @Test
        void contains_ShouldReturnTrue_WhenCollectionContainsElement() {
            // Given
            Collection<String> input = Arrays.asList("a", "b", "c");

            // When
            boolean actual = CollectionUtils.contains(input, "b");

            // Then
            then(actual).isTrue();
        }

        @Test
        void contains_ShouldReturnFalse_WhenCollectionDoesNotContainElement() {
            // Given
            Collection<String> input = Arrays.asList("a", "b", "c");

            // When
            boolean actual = CollectionUtils.contains(input, "x");

            // Then
            then(actual).isFalse();
        }

        @Test
        void contains_ShouldWorkProperly_WhenCollectionIsSet() {
            // Given
            Set<String> input = new HashSet<>(Arrays.asList("x", "y", "z"));

            // When
            boolean actualContains = CollectionUtils.contains(input, "y");
            boolean actualNotContains = CollectionUtils.contains(input, "a");

            // Then
            then(actualContains).isTrue();
            then(actualNotContains).isFalse();
        }
    }

    @Nested
    @DisplayName("contains(Collection<T>, Collection<T>)")
    class ContainsCollectionWithCollection {

        @Test
        void contains_ShouldReturnFalse_WhenSourceIsNull() {
            // Given
            Collection<String> source = null;
            Collection<String> candidates = Arrays.asList("a", "b");

            // When
            boolean actual = CollectionUtils.contains(source, candidates);

            // Then
            then(actual).isFalse();
        }

        @Test
        void contains_ShouldReturnFalse_WhenSourceIsEmpty() {
            // Given
            Collection<String> source = emptyList();
            Collection<String> candidates = Arrays.asList("a", "b");

            // When
            boolean actual = CollectionUtils.contains(source, candidates);

            // Then
            then(actual).isFalse();
        }

        @Test
        void contains_ShouldReturnFalse_WhenCandidatesIsNull() {
            // Given
            Collection<String> source = Arrays.asList("a", "b");
            Collection<String> candidates = null;

            // When
            boolean actual = CollectionUtils.contains(source, candidates);

            // Then
            then(actual).isFalse();
        }

        @Test
        void contains_ShouldReturnFalse_WhenCandidatesIsEmpty() {
            // Given
            Collection<String> source = Arrays.asList("a", "b");
            Collection<String> candidates = emptyList();

            // When
            boolean actual = CollectionUtils.contains(source, candidates);

            // Then
            then(actual).isFalse();
        }

        @Test
        void contains_ShouldReturnFalse_WhenSourceIsSetAndNoCandidateFound() {
            // Given
            Set<String> source = new HashSet<>(Arrays.asList("x", "y", "z"));
            Collection<String> candidates = Arrays.asList("a", "b");

            // When
            boolean actual = CollectionUtils.contains(source, candidates);

            // Then
            then(actual).isFalse();
        }

        @Test
        void contains_ShouldReturnTrue_WhenSourceIsSetAndContainsCandidate() {
            // Given
            Set<String> source = new HashSet<>(Arrays.asList("x", "y", "z"));
            Collection<String> candidates = Arrays.asList("a", "y");

            // When
            boolean actual = CollectionUtils.contains(source, candidates);

            // Then
            then(actual).isTrue();
        }

        @Test
        void contains_ShouldReturnTrue_WhenSourceIsListAndContainsCandidate() {
            // Given
            Collection<String> source = Arrays.asList("a", "b", "c");
            Collection<String> candidates = Arrays.asList("x", "b");

            // When
            boolean actual = CollectionUtils.contains(source, candidates);

            // Then
            then(actual).isTrue();
        }

        @Test
        void contains_ShouldReturnFalse_WhenSourceIsListAndNoCandidateFound() {
            // Given
            Collection<String> source = Arrays.asList("a", "b", "c");
            Collection<String> candidates = Arrays.asList("x", "y");

            // When
            boolean actual = CollectionUtils.contains(source, candidates);

            // Then
            then(actual).isFalse();
        }

        @Test
        void contains_ShouldReturnTrue_WhenCandidateIsNullAndSourceContainsNull() {
            // Given
            Collection<String> source = Arrays.asList("a", null, "b");
            Collection<String> candidates = Arrays.asList(null, "x");

            // When
            boolean actual = CollectionUtils.contains(source, candidates);

            // Then
            then(actual).isTrue();
        }

        @Test
        void contains_ShouldReturnFalse_WhenCandidateIsNullButSourceHasNoNull() {
            // Given
            Collection<String> source = Arrays.asList("a", "b", "c");
            Collection<String> candidates = Arrays.asList(null, "x");

            // When
            boolean actual = CollectionUtils.contains(source, candidates);

            // Then
            then(actual).isFalse();
        }
    }

    @Nested
    @DisplayName("contains(Collection<T>, Predicate<T>)")
    class ContainsCollectionWithPredicate {

        @Test
        void contains_ShouldReturnFalse_WhenSourceIsNull() {
            // Given
            Collection<String> source = null;
            Predicate<String> predicate = s -> s.startsWith("a");

            // When
            boolean actual = CollectionUtils.contains(source, predicate);

            // Then
            then(actual).isFalse();
        }

        @Test
        void contains_ShouldReturnFalse_WhenSourceIsEmpty() {
            // Given
            Collection<String> source = emptyList();
            Predicate<String> predicate = s -> s.startsWith("a");

            // When
            boolean actual = CollectionUtils.contains(source, predicate);

            // Then
            then(actual).isFalse();
        }

        @Test
        void contains_ShouldReturnFalse_WhenPredicateIsNull() {
            // Given
            Collection<String> source = Arrays.asList("a", "b", "c");
            Predicate<String> predicate = null;

            // When
            boolean actual = CollectionUtils.contains(source, predicate);

            // Then
            then(actual).isFalse();
        }

        @Test
        void contains_ShouldReturnTrue_WhenPredicateMatchesElement() {
            // Given
            Collection<String> source = Arrays.asList("a", "b", "c");
            Predicate<String> predicate = "b"::equals;

            // When
            boolean actual = CollectionUtils.contains(source, predicate);

            // Then
            then(actual).isTrue();
        }

        @Test
        void contains_ShouldReturnFalse_WhenPredicateMatchesNoElement() {
            // Given
            Collection<String> source = Arrays.asList("a", "b", "c");
            Predicate<String> predicate = s -> s.equals("x");

            // When
            boolean actual = CollectionUtils.contains(source, predicate);

            // Then
            then(actual).isFalse();
        }

        @Test
        void contains_ShouldHandleNullElementsSafely() {
            // Given
            Collection<String> source = Arrays.asList("a", null, "c");
            Predicate<String> predicate = Objects::isNull;

            // When
            boolean actual = CollectionUtils.contains(source, predicate);

            // Then
            then(actual).isTrue();
        }
    }

    @Nested
    @DisplayName("contains(Collection<T>, Function<T,R>, R)")
    class ContainsCollectionWithFunction {

        @Test
        void contains_ShouldReturnFalse_WhenSourceIsNull() {
            // Given
            Collection<String> source = null;
            Function<String, Integer> extractor = String::length;

            // When
            boolean actual = CollectionUtils.contains(source, extractor, 3);

            // Then
            then(actual).isFalse();
        }

        @Test
        void contains_ShouldReturnFalse_WhenSourceIsEmpty() {
            // Given
            Collection<String> source = emptyList();
            Function<String, Integer> extractor = String::length;

            // When
            boolean actual = CollectionUtils.contains(source, extractor, 3);

            // Then
            then(actual).isFalse();
        }

        @Test
        void contains_ShouldReturnFalse_WhenExtractorIsNull() {
            // Given
            Collection<String> source = Arrays.asList("a", "bb", "ccc");
            Function<String, Integer> extractor = null;

            // When
            boolean actual = CollectionUtils.contains(source, extractor, 2);

            // Then
            then(actual).isFalse();
        }

        @Test
        void contains_ShouldReturnTrue_WhenExtractorValueEqualsExpectedValue() {
            // Given
            Collection<String> source = Arrays.asList("a", "bb", "ccc");
            Function<String, Integer> extractor = String::length;

            // When
            boolean actual = CollectionUtils.contains(source, extractor, 2);

            // Then
            then(actual).isTrue();
        }

        @Test
        void contains_ShouldReturnFalse_WhenExtractorValueNeverEqualsExpectedValue() {
            // Given
            Collection<String> source = Arrays.asList("a", "bb", "ccc");
            Function<String, Integer> extractor = String::length;

            // When
            boolean actual = CollectionUtils.contains(source, extractor, 5);

            // Then
            then(actual).isFalse();
        }

        @Test
        void contains_ShouldReturnTrue_WhenExtractorReturnsNullAndExpectedIsNull() {
            // Given
            Collection<String> source = Arrays.asList("x", null, "y");
            Function<String, String> extractor = s -> s; // خودش را برمی‌گرداند

            // When
            boolean actual = CollectionUtils.contains(source, extractor, null);

            // Then
            then(actual).isTrue();
        }

        @Test
        void contains_ShouldReturnFalse_WhenExpectedIsNullButExtractorNeverReturnsNull() {
            // Given
            Collection<String> source = Arrays.asList("a", "b");
            Function<String, String> extractor = String::toUpperCase;

            // When
            boolean actual = CollectionUtils.contains(source, extractor, null);

            // Then
            then(actual).isFalse();
        }
    }

    @Nested
    @DisplayName("contains(Map<K,V>, Map.Entry<K,V>)")
    class ContainsMapWithEntry {

        @Test
        void contains_ShouldReturnFalse_WhenSourceIsNull() {
            // Given
            Map<String, String> source = null;
            Map.Entry<String, String> candidate = Map.entry("a", "1");

            // When
            boolean actual = CollectionUtils.contains(source, candidate);

            // Then
            then(actual).isFalse();
        }

        @Test
        void contains_ShouldReturnFalse_WhenSourceIsEmpty() {
            // Given
            Map<String, String> source = emptyMap();
            Map.Entry<String, String> candidate = Map.entry("a", "1");

            // When
            boolean actual = CollectionUtils.contains(source, candidate);

            // Then
            then(actual).isFalse();
        }

        @Test
        void contains_ShouldReturnFalse_WhenCandidateIsNull() {
            // Given
            Map<String, String> source = Map.of("a", "1", "b", "2");
            Map.Entry<String, String> candidate = null;

            // When
            boolean actual = CollectionUtils.contains(source, candidate);

            // Then
            then(actual).isFalse();
        }

        @Test
        void contains_ShouldReturnFalse_WhenCandidateKeyIsNull() {
            // Given
            Map<String, String> source = Map.of("a", "1");
            Map.Entry<String, String> candidate = new AbstractMap.SimpleEntry<>(null, "x");

            // When
            boolean actual = CollectionUtils.contains(source, candidate);

            // Then
            then(actual).isFalse();
        }

        @Test
        void contains_ShouldReturnTrue_WhenMapContainsEntryWithSameKeyAndValue() {
            // Given
            Map<String, String> source = Map.of("a", "1", "b", "2");
            Map.Entry<String, String> candidate = Map.entry("b", "2");

            // When
            boolean actual = CollectionUtils.contains(source, candidate);

            // Then
            then(actual).isTrue();
        }

        @Test
        void contains_ShouldReturnFalse_WhenMapContainsKeyButDifferentValue() {
            // Given
            Map<String, String> source = Map.of("a", "1", "b", "2");
            Map.Entry<String, String> candidate = Map.entry("b", "99");

            // When
            boolean actual = CollectionUtils.contains(source, candidate);

            // Then
            then(actual).isFalse();
        }

        @Test
        void contains_ShouldReturnFalse_WhenMapDoesNotContainKey() {
            // Given
            Map<String, String> source = Map.of("a", "1", "b", "2");
            Map.Entry<String, String> candidate = Map.entry("x", "1");

            // When
            boolean actual = CollectionUtils.contains(source, candidate);

            // Then
            then(actual).isFalse();
        }
    }

    @Nested
    @DisplayName("contains(Map<K,V>, Collection<Map.Entry<K,V>>)")
    class ContainsMapWithCollectionEntry {

        @Test
        void contains_ShouldReturnFalse_WhenSourceIsNull() {
            // Given
            Map<String, String> source = null;
            Collection<Map.Entry<String, String>> candidates = List.of(Map.entry("a", "1"));

            // When
            boolean actual = CollectionUtils.contains(source, candidates);

            // Then
            then(actual).isFalse();
        }

        @Test
        void contains_ShouldReturnFalse_WhenSourceIsEmpty() {
            // Given
            Map<String, String> source = emptyMap();
            Collection<Map.Entry<String, String>> candidates = List.of(Map.entry("a", "1"));

            // When
            boolean actual = CollectionUtils.contains(source, candidates);

            // Then
            then(actual).isFalse();
        }

        @Test
        void contains_ShouldReturnFalse_WhenCandidatesIsNull() {
            // Given
            Map<String, String> source = Map.of("a", "1");
            Collection<Map.Entry<String, String>> candidates = null;

            // When
            boolean actual = CollectionUtils.contains(source, candidates);

            // Then
            then(actual).isFalse();
        }

        @Test
        void contains_ShouldReturnFalse_WhenCandidatesIsEmpty() {
            // Given
            Map<String, String> source = Map.of("a", "1");
            Collection<Map.Entry<String, String>> candidates = emptyList();

            // When
            boolean actual = CollectionUtils.contains(source, candidates);

            // Then
            then(actual).isFalse();
        }

        @Test
        void contains_ShouldReturnTrue_WhenMapContainsMatchingEntry() {
            // Given
            Map<String, String> source = Map.of("a", "1", "b", "2");
            Collection<Map.Entry<String, String>> candidates = List.of(
                    Map.entry("x", "9"),
                    Map.entry("b", "2")
            );

            // When
            boolean actual = CollectionUtils.contains(source, candidates);

            // Then
            then(actual).isTrue();
        }

        @Test
        void contains_ShouldReturnFalse_WhenMapContainsKeyButDifferentValue() {
            // Given
            Map<String, String> source = Map.of("a", "1", "b", "2");
            Collection<Map.Entry<String, String>> candidates = List.of(
                    Map.entry("b", "99")
            );

            // When
            boolean actual = CollectionUtils.contains(source, candidates);

            // Then
            then(actual).isFalse();
        }

        @Test
        void contains_ShouldReturnFalse_WhenMapDoesNotContainKey() {
            // Given
            Map<String, String> source = Map.of("a", "1");
            Collection<Map.Entry<String, String>> candidates = List.of(Map.entry("x", "1"));

            // When
            boolean actual = CollectionUtils.contains(source, candidates);

            // Then
            then(actual).isFalse();
        }

        @Test
        void contains_ShouldReturnFalse_WhenMapValueIsNullButCandidateValueIsNot() {
            // Given
            Map<String, String> source = new HashMap<>();
            source.put("k1", null);
            Collection<Map.Entry<String, String>> candidates = List.of(Map.entry("k1", "x"));

            // When
            boolean actual = CollectionUtils.contains(source, candidates);

            // Then
            then(actual).isFalse();
        }
    }

    @Nested
    @DisplayName("contains(Map<K,V>, Predicate<Map.Entry<K,V>>)")
    class ContainsMapWithPredicate {

        @Test
        void contains_ShouldReturnFalse_WhenSourceIsNull() {
            // Given
            Map<String, Integer> source = null;
            Predicate<Map.Entry<String, Integer>> predicate = e -> e.getValue() > 0;

            // When
            boolean actual = CollectionUtils.contains(source, predicate);

            // Then
            then(actual).isFalse();
        }

        @Test
        void contains_ShouldReturnFalse_WhenSourceIsEmpty() {
            // Given
            Map<String, Integer> source = emptyMap();
            Predicate<Map.Entry<String, Integer>> predicate = e -> e.getValue() > 0;

            // When
            boolean actual = CollectionUtils.contains(source, predicate);

            // Then
            then(actual).isFalse();
        }

        @Test
        void contains_ShouldReturnFalse_WhenPredicateIsNull() {
            // Given
            Map<String, Integer> source = Map.of("a", 1);
            Predicate<Map.Entry<String, Integer>> predicate = null;

            // When
            boolean actual = CollectionUtils.contains(source, predicate);

            // Then
            then(actual).isFalse();
        }

        @Test
        void contains_ShouldReturnTrue_WhenAnyEntryMatchesPredicate() {
            // Given
            Map<String, Integer> source = Map.of("a", 1, "b", 2, "c", 3);
            Predicate<Map.Entry<String, Integer>> predicate = e -> e.getValue() == 2;

            // When
            boolean actual = CollectionUtils.contains(source, predicate);

            // Then
            then(actual).isTrue();
        }

        @Test
        void contains_ShouldReturnFalse_WhenNoEntryMatchesPredicate() {
            // Given
            Map<String, Integer> source = Map.of("a", 1, "b", 2, "c", 3);
            Predicate<Map.Entry<String, Integer>> predicate = e -> e.getValue() == 99;

            // When
            boolean actual = CollectionUtils.contains(source, predicate);

            // Then
            then(actual).isFalse();
        }
    }

    @Nested
    @DisplayName("difference(Collection<T>, Collection<T>)")
    class DifferenceCollection {

        @Test
        void difference_ShouldReturnEmptyList_WhenSourceIsNull() {
            // Given
            Collection<String> source = null;
            Collection<String> candidates = List.of("a");

            // When
            List<String> actual = CollectionUtils.difference(source, candidates);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void difference_ShouldReturnAllSource_WhenCandidatesIsNull() {
            // Given
            Collection<String> source = List.of("a", "b");
            Collection<String> candidates = null;

            // When
            List<String> actual = CollectionUtils.difference(source, candidates);

            // Then
            then(actual).containsExactlyInAnyOrder("a", "b");
        }

        @Test
        void difference_ShouldReturnAllSource_WhenCandidatesIsEmpty() {
            // Given
            Collection<String> source = List.of("x", "y");
            Collection<String> candidates = emptyList();

            // When
            List<String> actual = CollectionUtils.difference(source, candidates);

            // Then
            then(actual).containsExactlyInAnyOrder("x", "y");
        }

        @Test
        void difference_ShouldReturnEmptyList_WhenSourceIsEmpty() {
            // Given
            Collection<String> source = emptyList();
            Collection<String> candidates = List.of("a");

            // When
            List<String> actual = CollectionUtils.difference(source, candidates);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void difference_ShouldReturnItemsNotInCandidates() {
            // Given
            Collection<String> source = List.of("a", "b", "c");
            Collection<String> candidates = List.of("a", "b");

            // When
            List<String> actual = CollectionUtils.difference(source, candidates);

            // Then
            then(actual).containsExactly("c");
        }

        @Test
        void difference_ShouldReturnAllItems_WhenNoOverlapBetweenSourceAndCandidates() {
            // Given
            Collection<String> source = List.of("a", "b");
            Collection<String> candidates = List.of("x", "y");

            // When
            List<String> actual = CollectionUtils.difference(source, candidates);

            // Then
            then(actual).containsExactlyInAnyOrder("a", "b");
        }
    }

    @Nested
    @DisplayName("difference(Collection<T>, Collection<T>, Supplier<C>)")
    class DifferenceCollectionWithCollection {

        @Test
        void difference_ShouldThrowException_WhenFactoryIsNull() {
            // Given
            Collection<String> source = List.of("a", "b");
            Collection<String> candidates = List.of("a");
            Supplier<List<String>> factory = null;

            // When & Then
            thenException()
                    .isThrownBy(() -> CollectionUtils.difference(source, candidates, factory))
                    .isInstanceOf(IllegalArgumentException.class)
                    .withMessage("The factory cannot be null");
        }

        @Test
        void difference_ShouldReturnEmptyCollection_WhenSourceIsNull() {
            // Given
            Collection<String> source = null;
            Collection<String> candidates = List.of("a");
            Supplier<List<String>> factory = ArrayList::new;

            // When
            Collection<String> actual = CollectionUtils.difference(source, candidates, factory);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void difference_ShouldReturnAllSource_WhenCandidatesIsNull() {
            // Given
            Collection<String> source = List.of("a", "b");
            Collection<String> candidates = null;
            Supplier<List<String>> factory = ArrayList::new;

            // When
            Collection<String> actual = CollectionUtils.difference(source, candidates, factory);

            // Then
            then(actual).containsExactlyInAnyOrder("a", "b");
        }

        @Test
        void difference_ShouldReturnAllSource_WhenCandidatesIsEmpty() {
            // Given
            Collection<String> source = List.of("x", "y");
            Collection<String> candidates = emptyList();
            Supplier<List<String>> factory = ArrayList::new;

            // When
            Collection<String> actual = CollectionUtils.difference(source, candidates, factory);

            // Then
            then(actual).containsExactlyInAnyOrder("x", "y");
        }

        @Test
        void difference_ShouldReturnEmpty_WhenSourceIsEmpty() {
            // Given
            Collection<String> source = emptyList();
            Collection<String> candidates = List.of("a");
            Supplier<List<String>> factory = ArrayList::new;

            // When
            Collection<String> actual = CollectionUtils.difference(source, candidates, factory);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void difference_ShouldReturnItemsNotInCandidates_WhenAllPredicateTrue() {
            // Given
            Collection<String> source = List.of("a", "b", "c");
            Collection<String> candidates = List.of("a", "b");
            Supplier<List<String>> factory = ArrayList::new;

            // When
            Collection<String> actual = CollectionUtils.difference(source, candidates, factory);

            // Then
            then(actual).containsExactly("c");
        }

        @Test
        void difference_ShouldReturnAllItems_WhenNoOverlapBetweenSourceAndCandidates() {
            // Given
            Collection<String> source = List.of("a", "b");
            Collection<String> candidates = List.of("x", "y");
            Supplier<List<String>> factory = ArrayList::new;

            // When
            Collection<String> actual = CollectionUtils.difference(source, candidates, factory);

            // Then
            then(actual).containsExactlyInAnyOrder("a", "b");
        }
    }

    @Nested
    @DisplayName("difference(Collection<T>, Collection<T>, Predicate<T>, Supplier<C>)")
    class DifferenceCollectionWithPredicate {

        @Test
        void difference_ShouldThrowException_WhenPredicateIsNull() {
            // Given
            Collection<String> source = List.of("a", "b");
            Collection<String> candidates = List.of("a");
            Predicate<String> predicate = null;
            Supplier<List<String>> factory = ArrayList::new;

            // When & Then
            thenException()
                    .isThrownBy(() -> CollectionUtils.difference(source, candidates, predicate, factory))
                    .isInstanceOf(IllegalArgumentException.class)
                    .withMessage("The predicate cannot be null");
        }

        @Test
        void difference_ShouldThrowException_WhenFactoryIsNull() {
            // Given
            Collection<String> source = List.of("a", "b");
            Collection<String> candidates = List.of("a");
            Predicate<String> predicate = s -> true;
            Supplier<List<String>> factory = null;

            // When & Then
            thenException()
                    .isThrownBy(() -> CollectionUtils.difference(source, candidates, predicate, factory))
                    .isInstanceOf(IllegalArgumentException.class)
                    .withMessage("The factory cannot be null");
        }

        @Test
        void difference_ShouldReturnEmptyCollection_WhenSourceIsNull() {
            // Given
            Collection<String> source = null;
            Collection<String> candidates = List.of("a");
            Predicate<String> predicate = s -> true;
            Supplier<List<String>> factory = ArrayList::new;

            // When
            Collection<String> actual = CollectionUtils.difference(source, candidates, predicate, factory);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void difference_ShouldReturnAllSource_WhenCandidatesIsEmpty() {
            // Given
            Collection<String> source = List.of("a", "b");
            Collection<String> candidates = emptyList();
            Predicate<String> predicate = s -> true;
            Supplier<List<String>> factory = ArrayList::new;

            // When
            Collection<String> actual = CollectionUtils.difference(source, candidates, predicate, factory);

            // Then
            then(actual).containsExactlyInAnyOrder("a", "b");
        }

        @Test
        void difference_ShouldHandleCandidatesAsSetInstance() {
            // Given
            Collection<String> source = List.of("a", "b", "c");
            Collection<String> candidates = Set.of("a");
            Predicate<String> predicate = s -> s.compareTo("a") > 0; // "b", "c"
            Supplier<List<String>> factory = ArrayList::new;

            // When
            Collection<String> actual = CollectionUtils.difference(source, candidates, predicate, factory);

            // Then
            then(actual).containsExactlyInAnyOrder("b", "c");
        }

        @Test
        void difference_ShouldHandleCandidatesAsNonSetCollection() {
            // Given
            Collection<String> source = List.of("a", "b", "c");
            Collection<String> candidates = List.of("a", "b");
            Predicate<String> predicate = s -> s.startsWith("c");
            Supplier<List<String>> factory = ArrayList::new;

            // When
            Collection<String> actual = CollectionUtils.difference(source, candidates, predicate, factory);

            // Then
            then(actual).containsExactly("c");
        }

        @Test
        void difference_ShouldAddItem_WhenPredicateIsTrueAndNotInCandidates() {
            // Given
            Collection<String> source = List.of("x", "y");
            Collection<String> candidates = List.of("y");
            Predicate<String> predicate = s -> s.equals("x");
            Supplier<List<String>> factory = ArrayList::new;

            // When
            Collection<String> actual = CollectionUtils.difference(source, candidates, predicate, factory);

            // Then
            then(actual).containsExactly("x");
        }

        @Test
        void difference_ShouldNotAddItem_WhenPredicateIsFalse() {
            // Given
            Collection<String> source = List.of("x");
            Collection<String> candidates = List.of("z");
            Predicate<String> predicate = s -> false;
            Supplier<List<String>> factory = ArrayList::new;

            // When
            Collection<String> actual = CollectionUtils.difference(source, candidates, predicate, factory);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void difference_ShouldNotAddItem_WhenPredicateIsTrueButItemIsInCandidates() {
            // Given
            Collection<String> source = List.of("x");
            Collection<String> candidates = List.of("x");
            Predicate<String> predicate = s -> true;
            Supplier<List<String>> factory = ArrayList::new;

            // When
            Collection<String> actual = CollectionUtils.difference(source, candidates, predicate, factory);

            // Then
            then(actual).isEmpty();
        }
    }

    @Nested
    @DisplayName("difference(Map<K,V>, Map<K,V>, Supplier<C>)")
    class DifferenceMap {

        @Test
        void difference_ShouldThrowException_WhenFactoryIsNull() {
            // Given
            Map<String, String> source = Map.of("a", "1");
            Map<String, String> candidates = Map.of("b", "2");
            Supplier<Map<String, String>> factory = null;

            thenException()
                    .isThrownBy(() -> CollectionUtils.difference(source, candidates, factory))
                    .isInstanceOf(IllegalArgumentException.class)
                    .withMessage("The factory cannot be null");
        }

        @Test
        void difference_ShouldReturnEmptyMap_WhenSourceIsNull() {
            // Given
            Map<String, String> source = null;
            Map<String, String> candidates = Map.of("a", "1");
            Supplier<Map<String, String>> factory = HashMap::new;

            // When
            Map<String, String> actual = CollectionUtils.difference(source, candidates, factory);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void difference_ShouldReturnAllSource_WhenCandidatesIsNull() {
            // Given
            Map<String, String> source = Map.of("a", "1", "b", "2");
            Map<String, String> candidates = null;
            Supplier<Map<String, String>> factory = HashMap::new;

            // When
            Map<String, String> actual = CollectionUtils.difference(source, candidates, factory);

            // Then
            then(actual).containsExactlyInAnyOrderEntriesOf(source);
        }

        @Test
        void difference_ShouldReturnEmptyMap_WhenSourceIsEmpty() {
            // Given
            Map<String, String> source = emptyMap();
            Map<String, String> candidates = Map.of("a", "1");
            Supplier<Map<String, String>> factory = HashMap::new;

            // When
            Map<String, String> actual = CollectionUtils.difference(source, candidates, factory);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void difference_ShouldReturnAllSource_WhenCandidatesIsEmpty() {
            // Given
            Map<String, String> source = Map.of("a", "1", "b", "2");
            Map<String, String> candidates = emptyMap();
            Supplier<Map<String, String>> factory = HashMap::new;

            // When
            Map<String, String> actual = CollectionUtils.difference(source, candidates, factory);

            // Then
            then(actual).containsExactlyInAnyOrderEntriesOf(source);
        }

        @Test
        void difference_ShouldReturnOnlyItemsNotInCandidates() {
            // Given
            Map<String, String> source = Map.of("a", "1", "b", "2", "c", "3");
            Map<String, String> candidates = Map.of("a", "1", "b", "99");
            Supplier<Map<String, String>> factory = HashMap::new;

            // When
            Map<String, String> actual = CollectionUtils.difference(source, candidates, factory);

            // Then
            then(actual).containsExactlyInAnyOrderEntriesOf(Map.of("c", "3"));
        }

        @Test
        void difference_ShouldReturnAllItems_WhenNoOverlapBetweenSourceAndCandidates() {
            // Given
            Map<String, String> source = Map.of("a", "1", "b", "2");
            Map<String, String> candidates = Map.of("x", "9");
            Supplier<Map<String, String>> factory = HashMap::new;

            // When
            Map<String, String> actual = CollectionUtils.difference(source, candidates, factory);

            // Then
            then(actual).containsExactlyInAnyOrderEntriesOf(source);
        }
    }

    @Nested
    @DisplayName("find(Collection<T>, T)")
    class FindCollectionWithElement {

        @Test
        void find_ShouldReturnEmpty_WhenSourceIsNull() {
            // Given
            Collection<String> source = null;

            // When
            Optional<String> actual = CollectionUtils.find(source, "a");

            // Then
            then(actual).isEmpty();
        }

        @Test
        void find_ShouldReturnEmpty_WhenSourceIsEmpty() {
            // Given
            Collection<String> source = emptyList();

            // When
            Optional<String> actual = CollectionUtils.find(source, "a");

            // Then
            then(actual).isEmpty();
        }

        @Test
        void find_ShouldReturnEmpty_WhenCandidateIsNull() {
            // Given
            Collection<String> source = List.of("a", "b");

            // When
            Optional<String> actual = CollectionUtils.find(source, (String) null);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void find_ShouldReturnCandidate_WhenSourceContainsCandidateList() {
            // Given
            Collection<String> source = List.of("a", "b", "c");

            // When
            Optional<String> actual = CollectionUtils.find(source, "b");

            // Then
            then(actual).contains("b");
        }

        @Test
        void find_ShouldReturnEmpty_WhenSourceDoesNotContainCandidateList() {
            // Given
            Collection<String> source = List.of("a", "b", "c");

            // When
            Optional<String> actual = CollectionUtils.find(source, "x");

            // Then
            then(actual).isEmpty();
        }

        @Test
        void find_ShouldReturnCandidate_WhenSourceContainsCandidateSet() {
            // Given
            Collection<String> source = new HashSet<>(List.of("a", "b", "c"));

            // When
            Optional<String> actual = CollectionUtils.find(source, "a");

            // Then
            then(actual).contains("a");
        }

        @Test
        void find_ShouldReturnEmpty_WhenSourceDoesNotContainCandidateSet() {
            // Given
            Collection<String> source = new HashSet<>(List.of("a", "b", "c"));

            // When
            Optional<String> actual = CollectionUtils.find(source, "z");

            // Then
            then(actual).isEmpty();
        }
    }

    @Nested
    @DisplayName("find(Collection<T>, Collection<T>)")
    class FindCollectionWithCollection {

        @Test
        void find_ShouldReturnEmpty_WhenSourceIsNull() {
            // Given
            Collection<String> source = null;
            Collection<String> candidates = List.of("a");

            // When
            Optional<String> actual = CollectionUtils.find(source, candidates);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void find_ShouldReturnEmpty_WhenSourceIsEmpty() {
            // Given
            Collection<String> source = emptyList();
            Collection<String> candidates = List.of("a");

            // When
            Optional<String> actual = CollectionUtils.find(source, candidates);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void find_ShouldReturnEmpty_WhenCandidatesIsNull() {
            // Given
            Collection<String> source = List.of("a", "b");
            Collection<String> candidates = null;

            // When
            Optional<String> actual = CollectionUtils.find(source, candidates);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void find_ShouldReturnEmpty_WhenCandidatesIsEmpty() {
            // Given
            Collection<String> source = List.of("a", "b");
            Collection<String> candidates = emptyList();

            // When
            Optional<String> actual = CollectionUtils.find(source, candidates);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void find_ShouldReturnFirstCandidate_WhenSourceContainsCandidateList() {
            // Given
            Collection<String> source = List.of("x", "y", "z");
            Collection<String> candidates = List.of("y", "z");

            // When
            Optional<String> actual = CollectionUtils.find(source, candidates);

            // Then
            then(actual).contains("y");
        }

        @Test
        void find_ShouldReturnEmpty_WhenNoCandidateInSourceList() {
            // Given
            Collection<String> source = List.of("a", "b");
            Collection<String> candidates = List.of("x", "y");

            // When
            Optional<String> actual = CollectionUtils.find(source, candidates);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void find_ShouldReturnFirstCandidate_WhenSourceContainsCandidateSet() {
            // Given
            Collection<String> source = new HashSet<>(List.of("a", "b", "c"));
            Collection<String> candidates = List.of("b", "c");

            // When
            Optional<String> actual = CollectionUtils.find(source, candidates);

            // Then
            then(actual).contains("b");
        }

        @Test
        void find_ShouldReturnEmpty_WhenNoCandidateInSourceSet() {
            // Given
            Collection<String> source = new HashSet<>(List.of("a", "b"));
            Collection<String> candidates = List.of("x", "y");

            // When
            Optional<String> actual = CollectionUtils.find(source, candidates);

            // Then
            then(actual).isEmpty();
        }
    }

    @Nested
    @DisplayName("find(Collection<T>, Predicate<T>)")
    class FindCollectionWithPredicate {

        @Test
        void find_ShouldReturnEmpty_WhenSourceIsNull() {
            // Given
            Collection<String> source = null;
            Predicate<String> predicate = s -> s.equals("a");

            // When
            Optional<String> actual = CollectionUtils.find(source, predicate);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void find_ShouldReturnEmpty_WhenSourceIsEmpty() {
            // Given
            Collection<String> source = emptyList();
            Predicate<String> predicate = s -> s.equals("a");

            // When
            Optional<String> actual = CollectionUtils.find(source, predicate);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void find_ShouldReturnEmpty_WhenPredicateIsNull() {
            // Given
            Collection<String> source = List.of("a", "b");
            Predicate<String> predicate = null;

            // When
            Optional<String> actual = CollectionUtils.find(source, predicate);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void find_ShouldReturnFirstElement_WhenPredicateMatches() {
            // Given
            Collection<String> source = List.of("x", "y", "z");
            Predicate<String> predicate = s -> s.startsWith("y");

            // When
            Optional<String> actual = CollectionUtils.find(source, predicate);

            // Then
            then(actual).contains("y");
        }

        @Test
        void find_ShouldReturnEmpty_WhenNoElementMatchesPredicate() {
            // Given
            Collection<String> source = List.of("a", "b", "c");
            Predicate<String> predicate = s -> s.startsWith("x");

            // When
            Optional<String> actual = CollectionUtils.find(source, predicate);

            // Then
            then(actual).isEmpty();
        }
    }

    @Nested
    @DisplayName("find(Collection<T>, Function<T,V>, V)")
    class FindCollectionWithProperty {

        @Test
        void find_ShouldReturnEmpty_WhenSourceIsNull() {
            // Given
            Collection<String> source = null;
            Function<String, String> property = s -> s;

            // When
            Optional<String> actual = CollectionUtils.find(source, property, "a");

            // Then
            then(actual).isEmpty();
        }

        @Test
        void find_ShouldReturnEmpty_WhenSourceIsEmpty() {
            // Given
            Collection<String> source = emptyList();
            Function<String, String> property = s -> s;

            // When
            Optional<String> actual = CollectionUtils.find(source, property, "a");

            // Then
            then(actual).isEmpty();
        }

        @Test
        void find_ShouldReturnEmpty_WhenPropertyIsNull() {
            // Given
            Collection<String> source = List.of("a", "b");
            Function<String, String> property = null;

            // When
            Optional<String> actual = CollectionUtils.find(source, property, "a");

            // Then
            then(actual).isEmpty();
        }

        @Test
        void find_ShouldReturnFirstElement_WhenPropertyEqualsValue() {
            // Given
            Collection<String> source = List.of("x", "y", "z");
            Function<String, String> property = s -> s;
            String value = "y";

            // When
            Optional<String> actual = CollectionUtils.find(source, property, value);

            // Then
            then(actual).isPresent().contains("y");
        }

        @Test
        void find_ShouldReturnEmpty_WhenNoElementMatchesValue() {
            // Given
            Collection<String> source = List.of("a", "b", "c");
            Function<String, String> property = s -> s;
            String value = "x";

            // When
            Optional<String> actual = CollectionUtils.find(source, property, value);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void find_ShouldReturnElement_WhenValueIsNullAndPropertyIsNull() {
            // Given
            Collection<String> source = Arrays.asList("a", null, "c");
            Function<String, String> property = s -> s;

            // When
            Optional<String> actual = CollectionUtils.find(source, property, null);

            // Then
            then(actual).isNotPresent();
        }
    }

    @Nested
    @DisplayName("find(Map<K,V>, Predicate<Map.Entry<K,V>>)")
    class FindMapWithPredicate {

        @Test
        void find_ShouldReturnEmpty_WhenSourceIsNull() {
            // Given
            Map<String, String> source = null;
            Predicate<Map.Entry<String, String>> predicate = e -> true;

            // When
            Optional<Map.Entry<String, String>> actual = CollectionUtils.find(source, predicate);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void find_ShouldReturnEmpty_WhenSourceIsEmpty() {
            // Given
            Map<String, String> source = emptyMap();
            Predicate<Map.Entry<String, String>> predicate = e -> true;

            // When
            Optional<Map.Entry<String, String>> actual = CollectionUtils.find(source, predicate);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void find_ShouldReturnEmpty_WhenPredicateIsNull() {
            // Given
            Map<String, String> source = Map.of("a", "1");
            Predicate<Map.Entry<String, String>> predicate = null;

            // When
            Optional<Map.Entry<String, String>> actual = CollectionUtils.find(source, predicate);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void find_ShouldReturnFirstEntry_WhenPredicateMatches() {
            // Given
            Map<String, String> source = Map.of("a", "1", "b", "2");
            Predicate<Map.Entry<String, String>> predicate = e -> "2".equals(e.getValue());

            // When
            Optional<Map.Entry<String, String>> actual = CollectionUtils.find(source, predicate);

            // Then
            then(actual).isPresent();
            then(actual.get().getKey()).isEqualTo("b");
            then(actual.get().getValue()).isEqualTo("2");
        }

        @Test
        void find_ShouldReturnEmpty_WhenNoEntryMatchesPredicate() {
            // Given
            Map<String, String> source = Map.of("a", "1", "b", "2");
            Predicate<Map.Entry<String, String>> predicate = e -> "x".equals(e.getValue());

            // When
            Optional<Map.Entry<String, String>> actual = CollectionUtils.find(source, predicate);

            // Then
            then(actual).isEmpty();
        }
    }

    @Nested
    @DisplayName("findByKey(Map<K,V>, Predicate<K>)")
    class FindByKey {

        @Test
        void findByKey_ShouldReturnEmpty_WhenSourceIsNull() {
            // Given
            Map<String, String> source = null;
            Predicate<String> predicate = k -> true;

            // When
            Optional<Map.Entry<String, String>> actual = CollectionUtils.findByKey(source, predicate);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void findByKey_ShouldReturnEmpty_WhenSourceIsEmpty() {
            // Given
            Map<String, String> source = emptyMap();
            Predicate<String> predicate = k -> true;

            // When
            Optional<Map.Entry<String, String>> actual = CollectionUtils.findByKey(source, predicate);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void findByKey_ShouldReturnEmpty_WhenPredicateIsNull() {
            // Given
            Map<String, String> source = Map.of("a", "1");
            Predicate<String> predicate = null;

            // When
            Optional<Map.Entry<String, String>> actual = CollectionUtils.findByKey(source, predicate);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void findByKey_ShouldReturnFirstEntry_WhenPredicateMatches() {
            // Given
            Map<String, String> source = Map.of("a", "1", "b", "2");
            Predicate<String> predicate = "b"::equals;

            // When
            Optional<Map.Entry<String, String>> actual = CollectionUtils.findByKey(source, predicate);

            // Then
            then(actual).isPresent();
            then(actual.get().getKey()).isEqualTo("b");
            then(actual.get().getValue()).isEqualTo("2");
        }

        @Test
        void findByKey_ShouldReturnEmpty_WhenNoKeyMatchesPredicate() {
            // Given
            Map<String, String> source = Map.of("a", "1", "b", "2");
            Predicate<String> predicate = "x"::equals;

            // When
            Optional<Map.Entry<String, String>> actual = CollectionUtils.findByKey(source, predicate);

            // Then
            then(actual).isEmpty();
        }
    }

    @Nested
    @DisplayName("findByValue(Map<K,V>, Predicate<V>)")
    class FindByValue {

        @Test
        void findByValue_ShouldReturnEmpty_WhenSourceIsNull() {
            // Given
            Map<String, String> source = null;
            Predicate<String> predicate = v -> true;

            // When
            Optional<Map.Entry<String, String>> actual = CollectionUtils.findByValue(source, predicate);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void findByValue_ShouldReturnEmpty_WhenSourceIsEmpty() {
            // Given
            Map<String, String> source = emptyMap();
            Predicate<String> predicate = v -> true;

            // When
            Optional<Map.Entry<String, String>> actual = CollectionUtils.findByValue(source, predicate);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void findByValue_ShouldReturnEmpty_WhenPredicateIsNull() {
            // Given
            Map<String, String> source = Map.of("a", "1");
            Predicate<String> predicate = null;

            // When
            Optional<Map.Entry<String, String>> actual = CollectionUtils.findByValue(source, predicate);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void findByValue_ShouldReturnFirstEntry_WhenPredicateMatches() {
            // Given
            Map<String, String> source = Map.of("a", "1", "b", "2");
            Predicate<String> predicate = "2"::equals;

            // When
            Optional<Map.Entry<String, String>> actual = CollectionUtils.findByValue(source, predicate);

            // Then
            then(actual).isPresent();
            then(actual.get().getKey()).isEqualTo("b");
            then(actual.get().getValue()).isEqualTo("2");
        }

        @Test
        void findByValue_ShouldReturnEmpty_WhenNoValueMatchesPredicate() {
            // Given
            Map<String, String> source = Map.of("a", "1", "b", "2");
            Predicate<String> predicate = "9"::equals;

            // When
            Optional<Map.Entry<String, String>> actual = CollectionUtils.findByValue(source, predicate);

            // Then
            then(actual).isEmpty();
        }
    }

    @Nested
    @DisplayName("findAll(Collection<T>, Predicate<T>)")
    class FindAllCollectionWithPredicate {

        @Test
        void findAll_ShouldReturnEmptyList_WhenSourceIsNull() {
            // Given
            Collection<String> source = null;
            Predicate<String> predicate = s -> true;

            // When
            List<String> actual = CollectionUtils.findAll(source, predicate);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void findAll_ShouldReturnEmptyList_WhenSourceIsEmpty() {
            // Given
            Collection<String> source = emptyList();
            Predicate<String> predicate = s -> true;

            // When
            List<String> actual = CollectionUtils.findAll(source, predicate);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void findAll_ShouldReturnEmptyList_WhenPredicateIsNull() {
            // Given
            Collection<String> source = List.of("a", "b");
            Predicate<String> predicate = null;

            // When
            List<String> actual = CollectionUtils.findAll(source, predicate);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void findAll_ShouldReturnAllMatchingElements_WhenSomeElementsMatch() {
            // Given
            Collection<String> source = List.of("a", "b", "c", "b");
            Predicate<String> predicate = "b"::equals;

            // When
            List<String> actual = CollectionUtils.findAll(source, predicate);

            // Then
            then(actual).containsExactly("b", "b");
            then(actual).isInstanceOf(Collections.unmodifiableList(actual).getClass());
        }

        @Test
        void findAll_ShouldReturnEmptyList_WhenNoElementsMatch() {
            // Given
            Collection<String> source = List.of("a", "b", "c");
            Predicate<String> predicate = "x"::equals;

            // When
            List<String> actual = CollectionUtils.findAll(source, predicate);

            // Then
            then(actual).isEmpty();
        }
    }

    @Nested
    @DisplayName("findAll(Collection<T>, T)")
    class FindAllCollectionWithElement {

        @Test
        void findAll_ShouldReturnEmptyList_WhenSourceIsNull() {
            // Given
            Collection<String> source = null;
            String candidate = "a";

            // When
            List<String> actual = CollectionUtils.findAll(source, candidate);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void findAll_ShouldReturnEmptyList_WhenSourceIsEmpty() {
            // Given
            Collection<String> source = emptyList();
            String candidate = "a";

            // When
            List<String> actual = CollectionUtils.findAll(source, candidate);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void findAll_ShouldReturnAllMatchingElements() {
            // Given
            Collection<String> source = List.of("a", "b", "a", "c");
            String candidate = "a";

            // When
            List<String> actual = CollectionUtils.findAll(source, candidate);

            // Then
            then(actual).containsExactly("a", "a");
            then(actual).isInstanceOf(Collections.unmodifiableList(actual).getClass());
        }

        @Test
        void findAll_ShouldReturnEmptyList_WhenNoElementsMatch() {
            // Given
            Collection<String> source = List.of("a", "b", "c");
            String candidate = "x";

            // When
            List<String> actual = CollectionUtils.findAll(source, candidate);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void findAll_ShouldReturnAllMatchingElements_WhenCandidateIsNull() {
            // Given
            Collection<String> source = Arrays.asList("a", null, "b", null);
            String candidate = null;

            // When
            List<String> actual = CollectionUtils.findAll(source, candidate);

            // Then
            then(actual).containsExactly(null, null);
        }
    }

    @Nested
    @DisplayName("findAll(Collection<T>, Collection<T>)")
    class FindAllCollectionWithCollection {

        @Test
        void findAll_ShouldReturnEmptyList_WhenSourceIsNull() {
            // Given
            Collection<String> source = null;
            Collection<String> candidates = List.of("a", "b");

            // When
            List<String> actual = CollectionUtils.findAll(source, candidates);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void findAll_ShouldReturnEmptyList_WhenSourceIsEmpty() {
            // Given
            Collection<String> source = emptyList();
            Collection<String> candidates = List.of("a", "b");

            // When
            List<String> actual = CollectionUtils.findAll(source, candidates);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void findAll_ShouldReturnEmptyList_WhenCandidatesIsNull() {
            // Given
            Collection<String> source = List.of("a", "b");
            Collection<String> candidates = null;

            // When
            List<String> actual = CollectionUtils.findAll(source, candidates);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void findAll_ShouldReturnEmptyList_WhenCandidatesIsEmpty() {
            // Given
            Collection<String> source = List.of("a", "b");
            Collection<String> candidates = emptyList();

            // When
            List<String> actual = CollectionUtils.findAll(source, candidates);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void findAll_ShouldReturnAllMatchingElements() {
            // Given
            Collection<String> source = List.of("a", "b", "c");
            Collection<String> candidates = List.of("b", "c", "x");

            // When
            List<String> actual = CollectionUtils.findAll(source, candidates);

            // Then
            then(actual).containsExactly("b", "c");
            then(actual).isInstanceOf(Collections.unmodifiableList(actual).getClass());
        }

        @Test
        void findAll_ShouldReturnEmptyList_WhenNoCandidatesMatch() {
            // Given
            Collection<String> source = List.of("a", "b", "c");
            Collection<String> candidates = List.of("x", "y");

            // When
            List<String> actual = CollectionUtils.findAll(source, candidates);

            // Then
            then(actual).isEmpty();
        }
    }

    @Nested
    @DisplayName("findAll(Collection<T>, Function<T,V>, V)")
    class FindAllCollectionWithProperty {

        @Test
        void findAll_ShouldReturnEmptyList_WhenCollectionIsNull() {
            // Given
            Collection<String> collection = null;
            Function<String, String> property = s -> s;

            // When
            List<String> actual = CollectionUtils.findAll(collection, property, "a");

            // Then
            then(actual).isEmpty();
        }

        @Test
        void findAll_ShouldReturnEmptyList_WhenCollectionIsEmpty() {
            // Given
            Collection<String> collection = emptyList();
            Function<String, String> property = s -> s;

            // When
            List<String> actual = CollectionUtils.findAll(collection, property, "a");

            // Then
            then(actual).isEmpty();
        }

        @Test
        void findAll_ShouldReturnEmptyList_WhenPropertyIsNull() {
            // Given
            Collection<String> collection = List.of("a", "b");
            Function<String, String> property = null;

            // When
            List<String> actual = CollectionUtils.findAll(collection, property, "a");

            // Then
            then(actual).isEmpty();
        }

        @Test
        void findAll_ShouldReturnAllMatchingElements_WhenSomeMatch() {
            // Given
            Collection<String> collection = List.of("a", "b", "c", "b");
            Function<String, String> property = s -> s;

            // When
            List<String> actual = CollectionUtils.findAll(collection, property, "b");

            // Then
            then(actual).containsExactly("b", "b");
            then(actual).isInstanceOf(Collections.unmodifiableList(actual).getClass());
        }

        @Test
        void findAll_ShouldReturnEmptyList_WhenNoElementsMatch() {
            // Given
            Collection<String> collection = List.of("a", "b", "c");
            Function<String, String> property = s -> s;

            // When
            List<String> actual = CollectionUtils.findAll(collection, property, "x");

            // Then
            then(actual).isEmpty();
        }

        @Test
        void findAll_ShouldReturnElements_WhenValueIsNull() {
            // Given
            Collection<String> collection = Arrays.asList("a", null, "b");
            Function<String, String> property = s -> s;

            // When
            List<String> actual = CollectionUtils.findAll(collection, property, null);

            // Then
            then(actual).containsExactly((String) null);
        }
    }

    @Nested
    @DisplayName("findAll(Map<K,V>, Predicate<Map.Entry<K,V>>)")
    class FindAllMapWithPredicate {

        @Test
        void findAll_ShouldReturnEmptyList_WhenSourceIsNull() {
            // Given
            Map<String, String> source = null;
            Predicate<Map.Entry<String, String>> predicate = e -> true;

            // When
            List<Map.Entry<String, String>> actual = CollectionUtils.findAll(source, predicate);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void findAll_ShouldReturnEmptyList_WhenSourceIsEmpty() {
            // Given
            Map<String, String> source = emptyMap();
            Predicate<Map.Entry<String, String>> predicate = e -> true;

            // When
            List<Map.Entry<String, String>> actual = CollectionUtils.findAll(source, predicate);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void findAll_ShouldReturnEmptyList_WhenPredicateIsNull() {
            // Given
            Map<String, String> source = Map.of("a", "1");
            Predicate<Map.Entry<String, String>> predicate = null;

            // When
            List<Map.Entry<String, String>> actual = CollectionUtils.findAll(source, predicate);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void findAll_ShouldReturnAllMatchingEntries_WhenSomeMatch() {
            // Given
            Map<String, String> source = Map.of("a", "1", "b", "2", "c", "2");
            Predicate<Map.Entry<String, String>> predicate = e -> "2".equals(e.getValue());

            // When
            List<Map.Entry<String, String>> actual = CollectionUtils.findAll(source, predicate);

            // Then
            then(actual)
                    .hasSize(2)
                    .isInstanceOf(Collections.unmodifiableList(actual).getClass())
                    .containsExactlyInAnyOrder(
                            new AbstractMap.SimpleImmutableEntry<>("b", "2"),
                            new AbstractMap.SimpleImmutableEntry<>("c", "2")
                    );
        }

        @Test
        void findAll_ShouldReturnEmptyList_WhenNoEntryMatches() {
            // Given
            Map<String, String> source = Map.of("a", "1", "b", "2");
            Predicate<Map.Entry<String, String>> predicate = e -> "x".equals(e.getValue());

            // When
            List<Map.Entry<String, String>> actual = CollectionUtils.findAll(source, predicate);

            // Then
            then(actual).isEmpty();
        }
    }

    @Nested
    @DisplayName("findMax(Collection<T>, Comparator<T>)")
    class FindMaxCollection {

        @Test
        void findMax_ShouldReturnEmpty_WhenSourceIsNull() {
            // Given
            Collection<Integer> source = null;
            Comparator<Integer> comparator = Integer::compareTo;

            // When
            Optional<Integer> actual = CollectionUtils.findMax(source, comparator);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void findMax_ShouldReturnEmpty_WhenSourceIsEmpty() {
            // Given
            Collection<Integer> source = emptyList();
            Comparator<Integer> comparator = Integer::compareTo;

            // When
            Optional<Integer> actual = CollectionUtils.findMax(source, comparator);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void findMax_ShouldReturnEmpty_WhenComparatorIsNull() {
            // Given
            Collection<Integer> source = List.of(1, 2, 3);
            Comparator<Integer> comparator = null;

            // When
            Optional<Integer> actual = CollectionUtils.findMax(source, comparator);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void findMax_ShouldReturnMaxElement_WhenElementsExist() {
            // Given
            Collection<Integer> source = List.of(3, 1, 4, 2);
            Comparator<Integer> comparator = Integer::compareTo;

            // When
            Optional<Integer> actual = CollectionUtils.findMax(source, comparator);

            // Then
            then(actual).isPresent();
            then(actual.get()).isEqualTo(4);
        }

        @Test
        void findMax_ShouldReturnMaxElement_WhenAllElementsEqual() {
            // Given
            Collection<Integer> source = List.of(2, 2, 2);
            Comparator<Integer> comparator = Integer::compareTo;

            // When
            Optional<Integer> actual = CollectionUtils.findMax(source, comparator);

            // Then
            then(actual)
                    .isPresent()
                    .get()
                    .isEqualTo(2);
        }
    }

    @Nested
    @DisplayName("findMin(Collection<T>, Comparator<T>)")
    class FindMinCollection {

        @Test
        void findMin_ShouldReturnEmpty_WhenSourceIsNull() {
            // Given
            Collection<Integer> source = null;
            Comparator<Integer> comparator = Integer::compareTo;

            // When
            Optional<Integer> actual = CollectionUtils.findMin(source, comparator);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void findMin_ShouldReturnEmpty_WhenSourceIsEmpty() {
            // Given
            Collection<Integer> source = emptyList();
            Comparator<Integer> comparator = Integer::compareTo;

            // When
            Optional<Integer> actual = CollectionUtils.findMin(source, comparator);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void findMin_ShouldReturnEmpty_WhenComparatorIsNull() {
            // Given
            Collection<Integer> source = List.of(1, 2, 3);
            Comparator<Integer> comparator = null;

            // When
            Optional<Integer> actual = CollectionUtils.findMin(source, comparator);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void findMin_ShouldReturnMinElement_WhenElementsExist() {
            // Given
            Collection<Integer> source = List.of(3, 1, 4, 2);
            Comparator<Integer> comparator = Integer::compareTo;

            // When
            Optional<Integer> actual = CollectionUtils.findMin(source, comparator);

            // Then
            then(actual)
                    .isPresent()
                    .get()
                    .isEqualTo(1);
        }

        @Test
        void findMin_ShouldReturnMinElement_WhenAllElementsEqual() {
            // Given
            Collection<Integer> source = List.of(2, 2, 2);
            Comparator<Integer> comparator = Integer::compareTo;

            // When
            Optional<Integer> actual = CollectionUtils.findMin(source, comparator);

            // Then
            then(actual)
                    .isPresent()
                    .get()
                    .isEqualTo(2);
        }
    }

    @Nested
    @DisplayName("groupBy(Collection<T>, Function<T, K>)")
    class GroupByCollection {

        @Test
        void groupBy_ShouldReturnEmptyMap_WhenSourceIsNull() {
            // Given
            Collection<String> source = null;
            Function<String, Integer> classifier = String::length;

            // When
            Map<Integer, List<String>> result = CollectionUtils.groupBy(source, classifier);

            // Then
            then(result).isEmpty();
        }

        @Test
        void groupBy_ShouldReturnEmptyMap_WhenSourceIsEmpty() {
            // Given
            Collection<String> source = List.of();
            Function<String, Integer> classifier = String::length;

            // When
            Map<Integer, List<String>> result = CollectionUtils.groupBy(source, classifier);

            // Then
            then(result).isEmpty();
        }

        @Test
        void groupBy_ShouldReturnEmptyMap_WhenFunctionIsNull() {
            // Given
            Collection<String> source = List.of("A", "BB");

            // When
            Map<Object, List<String>> result = CollectionUtils.groupBy(source, null);

            // Then
            then(result).isEmpty();
        }

        @Test
        void groupBy_ShouldGroupElementsByKey_WhenValidInputsProvided() {
            // Given
            Collection<String> source = List.of("A", "BB", "C", "DDD");
            Function<String, Integer> classifier = String::length;

            // When
            Map<Integer, List<String>> result = CollectionUtils.groupBy(source, classifier);

            // Then
            then(result).hasSize(3);
            then(result.get(1)).containsExactly("A", "C");
            then(result.get(2)).containsExactly("BB");
            then(result.get(3)).containsExactly("DDD");
        }

        @Test
        void groupBy_ShouldHandleNullKeys_WhenFunctionReturnsNull() {
            // Given
            Collection<String> source = Arrays.asList("A", null, "B");
            Function<String, String> classifier = s -> s == null ? null : s.toLowerCase();

            // When
            Map<String, List<String>> result = CollectionUtils.groupBy(source, classifier);

            // Then
            then(result).containsKey(null);
            then(result.get(null)).containsExactly((String) null);
            then(result.get("a")).containsExactly("A");
            then(result.get("b")).containsExactly("B");
        }

        @Test
        void groupBy_ShouldGroupMultipleItemsUnderSameKey() {
            // Given
            Collection<String> source = List.of("apple", "ant", "banana", "boat");
            Function<String, Character> classifier = s -> s.charAt(0);

            // When
            Map<Character, List<String>> result = CollectionUtils.groupBy(source, classifier);

            // Then
            then(result.get('a')).containsExactly("apple", "ant");
            then(result.get('b')).containsExactly("banana", "boat");
        }
    }

    @Nested
    @DisplayName("isEmpty(Collection)")
    class IsEmptyCollection {

        @Test
        void isEmpty_ShouldReturnTrue_WhenCollectionIsNull() {
            // Given
            Collection<String> input = null;

            // When
            boolean actual = CollectionUtils.isEmpty(input);

            // Then
            then(actual).isTrue();
        }

        @Test
        void isEmpty_ShouldReturnTrue_WhenCollectionIsEmpty() {
            // Given
            Collection<String> input = emptyList();

            // When
            boolean actual = CollectionUtils.isEmpty(input);

            // Then
            then(actual).isTrue();
        }

        @Test
        void isEmpty_ShouldReturnFalse_WhenCollectionIsNotNullAndNotEmpty() {
            // Given
            Collection<String> input = singletonList("a");

            // When
            boolean actual = CollectionUtils.isEmpty(input);

            // Then
            then(actual).isFalse();
        }
    }

    @Nested
    @DisplayName("isEmpty(Map)")
    class IsEmptyMap {

        @Test
        void isEmpty_ShouldReturnTrue_WhenMapIsNull() {
            // Given
            Map<String, Integer> input = null;

            // When
            boolean actual = CollectionUtils.isEmpty(input);

            // Then
            then(actual).isTrue();
        }

        @Test
        void isEmpty_ShouldReturnTrue_WhenMapIsEmpty() {
            // Given
            Map<String, Integer> input = emptyMap();

            // When
            boolean actual = CollectionUtils.isEmpty(input);

            // Then
            then(actual).isTrue();
        }

        @Test
        void isEmpty_ShouldReturnFalse_WhenMapIsNotNullAndNotEmpty() {
            // Given
            Map<String, Integer> input = Map.of("a", 1);

            // When
            boolean actual = CollectionUtils.isEmpty(input);

            // Then
            then(actual).isFalse();
        }
    }

    @Nested
    @DisplayName("intersection(Collection, Collection)")
    class IntersectionCollection {

        @Test
        void intersection_ShouldReturnEmpty_WhenSourceIsNull() {
            // Given
            Collection<String> inputSource = null;
            Collection<String> inputCandidates = singletonList("a");

            // When
            Collection<String> actual = CollectionUtils.intersection(inputSource, inputCandidates);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void intersection_ShouldReturnEmpty_WhenCandidatesIsNull() {
            // Given
            Collection<String> inputSource = singletonList("a");
            Collection<String> inputCandidates = null;

            // When
            Collection<String> actual = CollectionUtils.intersection(inputSource, inputCandidates);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void intersection_ShouldReturnCommonElementsWithMinCardinality_WhenDuplicatesExist() {
            // Given
            Collection<String> inputSource = Arrays.asList("a", "b", "b", "c");
            Collection<String> inputCandidates = Arrays.asList("b", "b", "d", "c", "c");

            // When
            Collection<String> actual = CollectionUtils.intersection(inputSource, inputCandidates);

            // Then
            then(actual).containsExactlyInAnyOrder("b", "b", "c");
        }

        @Test
        void intersection_ShouldReturnEmpty_WhenNoCommonElements() {
            // Given
            Collection<String> inputSource = Arrays.asList("a", "b");
            Collection<String> inputCandidates = Arrays.asList("c", "d");

            // When
            Collection<String> actual = CollectionUtils.intersection(inputSource, inputCandidates);

            // Then
            then(actual).isEmpty();
        }
    }

    @Nested
    @DisplayName("intersection(Map, Map)")
    class IntersectionMap {

        @Test
        void intersection_ShouldReturnEmpty_WhenSourceIsNull() {
            // Given
            Map<String, Integer> inputSource = null;
            Map<String, Integer> inputCandidates = Map.of("a", 1);

            // When
            Map<String, Integer> actual = CollectionUtils.intersection(inputSource, inputCandidates);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void intersection_ShouldReturnEmpty_WhenCandidatesIsNull() {
            // Given
            Map<String, Integer> inputSource = Map.of("a", 1);
            Map<String, Integer> inputCandidates = null;

            // When
            Map<String, Integer> actual = CollectionUtils.intersection(inputSource, inputCandidates);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void intersection_ShouldReturnEntriesWithCommonKeys_WhenValuesDiffer() {
            // Given
            Map<String, Integer> inputSource = Map.of("a", 1, "b", 2, "c", 3);
            Map<String, Integer> inputCandidates = Map.of("b", 20, "c", 30, "d", 4);

            // When
            Map<String, Integer> actual = CollectionUtils.intersection(inputSource, inputCandidates);

            // Then
            then(actual).containsOnly(
                    Map.entry("b", 20),
                    Map.entry("c", 30)
            );
        }

        @Test
        void intersection_ShouldReturnEmpty_WhenNoCommonKeys() {
            // Given
            Map<String, Integer> inputSource = Map.of("a", 1);
            Map<String, Integer> inputCandidates = Map.of("b", 2);

            // When
            Map<String, Integer> actual = CollectionUtils.intersection(inputSource, inputCandidates);

            // Then
            then(actual).isEmpty();
        }
    }

    @Nested
    @DisplayName("merge(Collection, T...)")
    class MergeCollectionWithVarargs {

        @Test
        void merge_ShouldReturnCandidates_WhenSourceIsNull() {
            // Given
            Collection<String> inputSource = null;
            String[] inputCandidates = {"a", "b"};

            // When
            List<String> actual = CollectionUtils.merge(inputSource, inputCandidates);

            // Then
            then(actual).containsExactly("a", "b");
        }

        @Test
        void merge_ShouldReturnSource_WhenCandidatesIsEmpty() {
            // Given
            Collection<String> inputSource = Arrays.asList("a", "b");
            String[] inputCandidates = {};

            // When
            List<String> actual = CollectionUtils.merge(inputSource, inputCandidates);

            // Then
            then(actual).containsExactly("a", "b");
        }

        @Test
        void merge_ShouldCombineCollectionAndVarargs_WhenBothNonEmpty() {
            // Given
            Collection<String> inputSource = Arrays.asList("a", "b");
            String[] inputCandidates = {"c", "d"};

            // When
            List<String> actual = CollectionUtils.merge(inputSource, inputCandidates);

            // Then
            then(actual).containsExactly("a", "b", "c", "d");
        }

        @Test
        void merge_ShouldReturnEmpty_WhenBothNullOrEmpty() {
            // Given
            Collection<String> inputSource = null;
            String[] inputCandidates = {};

            // When
            List<String> actual = CollectionUtils.merge(inputSource, inputCandidates);

            // Then
            then(actual).isEmpty();
        }
    }

    @Nested
    @DisplayName("merge(Collection, Collection)")
    class MergeTwoCollections {

        @Test
        void merge_ShouldReturnCandidates_WhenSourceIsNull() {
            // Given
            Collection<String> inputSource = null;
            Collection<String> inputCandidates = Arrays.asList("a", "b");

            // When
            List<String> actual = CollectionUtils.merge(inputSource, inputCandidates);

            // Then
            then(actual).containsExactly("a", "b");
        }

        @Test
        void merge_ShouldReturnEmpty_WhenBothNullOrEmpty() {
            // Given
            Collection<String> inputSource = emptyList();
            Collection<String> inputCandidates = null;

            // When
            List<String> actual = CollectionUtils.merge(inputSource, inputCandidates);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void merge_ShouldCombineBothCollections_WhenBothNonEmpty() {
            // Given
            Collection<String> inputSource = Arrays.asList("a", "b");
            Collection<String> inputCandidates = Arrays.asList("c", "d");

            // When
            List<String> actual = CollectionUtils.merge(inputSource, inputCandidates);

            // Then
            then(actual).containsExactly("a", "b", "c", "d");
        }

        @Test
        void merge_ShouldReturnEmptyList_WhenBothNull() {
            // Given
            Collection<String> inputSource = null;
            Collection<String> inputCandidates = null;

            // When
            List<String> actual = CollectionUtils.merge(inputSource, inputCandidates);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void merge_ShouldReturnEmptyList_WhenSourceNull_And_CandidatesEmpty() {
            // Given
            Collection<String> inputSource = null;
            Collection<String> inputCandidates = emptyList();

            // When
            List<String> actual = CollectionUtils.merge(inputSource, inputCandidates);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void merge_ShouldReturnEmptyList_WhenBothEmpty() {
            // Given
            Collection<String> inputSource = emptyList();
            Collection<String> inputCandidates = emptySet();

            // When
            List<String> actual = CollectionUtils.merge(inputSource, inputCandidates);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void merge_ShouldReturnCandidates_WhenSourceNull_And_CandidatesHasElements() {
            // Given
            Collection<String> inputSource = null;
            Collection<String> inputCandidates = Arrays.asList("x", "y");

            // When
            List<String> actual = CollectionUtils.merge(inputSource, inputCandidates);

            // Then
            then(actual).containsExactly("x", "y");
        }

        @Test
        void merge_ShouldReturnCandidates_WhenSourceEmpty_And_CandidatesHasElements() {
            // Given
            Collection<String> inputSource = emptyList();
            Collection<String> inputCandidates = Arrays.asList("a", "b");

            // When
            List<String> actual = CollectionUtils.merge(inputSource, inputCandidates);

            // Then
            then(actual).containsExactly("a", "b");
        }

        @Test
        void merge_ShouldReturnSource_WhenCandidatesNull_And_SourceHasElements() {
            // Given
            Collection<String> inputSource = Arrays.asList("a", "b");
            Collection<String> inputCandidates = null;

            // When
            List<String> actual = CollectionUtils.merge(inputSource, inputCandidates);

            // Then
            then(actual).containsExactly("a", "b");
        }

        @Test
        void merge_ShouldReturnSource_WhenCandidatesEmpty_And_SourceHasElements() {
            // Given
            Collection<String> inputSource = Arrays.asList("a", "b");
            Collection<String> inputCandidates = emptySet();

            // When
            List<String> actual = CollectionUtils.merge(inputSource, inputCandidates);

            // Then
            then(actual).containsExactly("a", "b");
        }

        @Test
        void merge_ShouldPreserveOrder_WhenSourceIsLinkedList() {
            // Given
            Collection<String> inputSource = new LinkedList<>(Arrays.asList("x", "y"));
            Collection<String> inputCandidates = List.of("z");

            // When
            List<String> actual = CollectionUtils.merge(inputSource, inputCandidates);

            // Then
            then(actual).containsExactly("x", "y", "z");
        }

        @Test
        void merge_ShouldHandleDuplicates_WhenBothHaveSameElement() {
            // Given
            Collection<String> inputSource = Arrays.asList("a", "b", "a");
            Collection<String> inputCandidates = Arrays.asList("b", "c");

            // When
            List<String> actual = CollectionUtils.merge(inputSource, inputCandidates);

            // Then
            then(actual).containsExactly("a", "b", "a", "b", "c");
        }

        @Test
        void merge_ShouldHandleNullElements_WhenSourceContainsNull() {
            // Given
            Collection<String> inputSource = Arrays.asList("a", null, "b");
            Collection<String> inputCandidates = Arrays.asList("c", null);

            // When
            List<String> actual = CollectionUtils.merge(inputSource, inputCandidates);

            // Then
            then(actual).containsExactly("a", null, "b", "c", null);
        }

        @Test
        void merge_ShouldNotThrowNPE_WhenSourceNull_ButBranchHandled() {
            // Given
            Collection<String> inputSource = null;
            Collection<String> inputCandidates = List.of("safe");

            // When
            List<String> actual = CollectionUtils.merge(inputSource, inputCandidates);

            // Then
            then(actual).containsExactly("safe");
        }

        @Test
        void merge_ShouldCreateNewListInstance_WhenBothNonEmpty() {
            // Given
            Collection<String> inputSource = new ArrayList<>(List.of("a"));
            Collection<String> inputCandidates = List.of("b");

            // When
            List<String> actual = CollectionUtils.merge(inputSource, inputCandidates);

            // Then
            then(actual)
                    .isNotSameAs(inputSource)
                    .containsExactly("a", "b");
        }
    }

    @Nested
    @DisplayName("subtract(Collection, Collection)")
    class SubtractCollection {

        @Test
        void subtract_ShouldReturnSource_WhenToRemoveIsNull() {
            // Given
            Collection<String> inputSource = Arrays.asList("a", "b");
            Collection<String> inputToRemove = null;

            // When
            Collection<String> actual = CollectionUtils.subtract(inputSource, inputToRemove);

            // Then
            then(actual).containsExactly("a", "b");
        }

        @Test
        void subtract_ShouldReturnEmpty_WhenSourceIsNull() {
            // Given
            Collection<String> inputSource = null;
            Collection<String> inputToRemove = singletonList("a");

            // When
            Collection<String> actual = CollectionUtils.subtract(inputSource, inputToRemove);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void subtract_ShouldRemoveAllElementsInToRemove_WhenPresent() {
            // Given
            Collection<String> inputSource = new ArrayList<>(Arrays.asList("a", "b", "c", "b"));
            Collection<String> inputToRemove = Arrays.asList("b", "d");

            // When
            Collection<String> actual = CollectionUtils.subtract(inputSource, inputToRemove);

            // Then
            then(actual).containsExactly("a", "c");
        }
    }

    @Nested
    @DisplayName("subtract(Map, Map)")
    class SubtractMap {

        @Test
        void subtract_ShouldReturnEmpty_WhenSourceIsNull() {
            // Given
            Map<String, Integer> inputSource = null;
            Map<String, Integer> inputToRemove = Map.of("a", 1);

            // When
            Map<String, Integer> actual = CollectionUtils.subtract(inputSource, inputToRemove);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void subtract_ShouldReturnSource_WhenToRemoveIsNull() {
            // Given
            Map<String, Integer> inputSource = Map.of("a", 1);
            Map<String, Integer> inputToRemove = null;

            // When
            Map<String, Integer> actual = CollectionUtils.subtract(inputSource, inputToRemove);

            // Then
            then(actual).containsExactly(Map.entry("a", 1));
        }

        @Test
        void subtract_ShouldRemoveKeysInToRemove_WhenPresent() {
            // Given
            Map<String, Integer> inputSource = new HashMap<>(Map.of("a", 1, "b", 2, "c", 3));
            Map<String, Integer> inputToRemove = Map.of("b", 20, "d", 4);

            // When
            Map<String, Integer> actual = CollectionUtils.subtract(inputSource, inputToRemove);

            // Then
            then(actual).containsOnly(
                    Map.entry("a", 1),
                    Map.entry("c", 3)
            );
        }
    }

    @Nested
    @DisplayName("partition(List<T>, int)")
    class Partition {

        @Test
        void partition_shouldReturnEmptyList_WhenSourceIsNull() {
            // Given
            List<String> source = null;
            int size = 2;

            // When
            List<List<String>> actual = CollectionUtils.partition(source, size);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void partition_shouldReturnEmptyList_WhenSourceIsEmpty() {
            // Given
            List<String> source = emptyList();
            int size = 3;

            // When
            List<List<String>> actual = CollectionUtils.partition(source, size);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void partition_shouldThrowException_WhenSizeIsZeroOrNegative() {
            // Given
            List<String> source = List.of("a", "b");

            // When / Then
            thenException()
                    .isThrownBy(() -> CollectionUtils.partition(source, 0))
                    .isInstanceOf(IllegalArgumentException.class)
                    .withMessage("Size must be greater than 0");

            thenException()
                    .isThrownBy(() -> CollectionUtils.partition(source, -1))
                    .isInstanceOf(IllegalArgumentException.class)
                    .withMessage("Size must be greater than 0");
        }

        @Test
        void partition_shouldPartitionList_WhenSizeIsValid() {
            // Given
            List<String> source = List.of("a", "b", "c", "d", "e");
            int size = 2;

            // When
            List<List<String>> actual = CollectionUtils.partition(source, size);

            // Then
            then(actual)
                    .isInstanceOf(Collections.unmodifiableList(actual).getClass())
                    .hasSize(3)
                    .containsExactly(
                            List.of("a", "b"),
                            List.of("c", "d"),
                            List.of("e")
                    );
        }

        @Test
        void partition_shouldReturnSinglePartition_WhenSizeGreaterThanListSize() {
            // Given
            List<String> source = List.of("a", "b", "c");
            int size = 10;

            // When
            List<List<String>> actual = CollectionUtils.partition(source, size);

            // Then
            then(actual)
                    .isInstanceOf(Collections.unmodifiableList(actual).getClass())
                    .hasSize(1)
                    .containsExactly(List.of("a", "b", "c"));
        }
    }

    @Nested
    @DisplayName("transform(Collection<T>, Function<T, R>)")
    class Transform {

        @Test
        void transform_ShouldReturnEmptyList_WhenSourceIsNull() {
            // Given
            Collection<String> source = null;
            Function<String, Integer> transformer = String::length;

            // When
            List<Integer> result = CollectionUtils.transform(source, transformer);

            // Then
            then(result).isEmpty();
        }

        @Test
        void transform_ShouldReturnEmptyList_WhenSourceIsEmpty() {
            // Given
            Collection<String> source = List.of();
            Function<String, Integer> transformer = String::length;

            // When
            List<Integer> result = CollectionUtils.transform(source, transformer);

            // Then
            then(result).isEmpty();
        }

        @Test
        void transform_ShouldReturnEmptyList_WhenTransformerIsNull() {
            // Given
            Collection<String> source = List.of("A", "BB");

            // When
            List<Integer> result = CollectionUtils.transform(source, null);

            // Then
            then(result).isEmpty();
        }

        @Test
        void transform_ShouldReturnTransformedList_WhenValidInputsProvided() {
            // Given
            Collection<String> source = List.of("A", "BB", "CCC");
            Function<String, Integer> transformer = String::length;

            // When
            List<Integer> result = CollectionUtils.transform(source, transformer);

            // Then
            then(result).containsExactly(1, 2, 3);
        }

        @Test
        void transform_ShouldAllowNullElementsInSource() {
            // Given
            Collection<String> source = Arrays.asList("A", null, "B");
            Function<String, Integer> transformer = s -> s == null ? -1 : s.length();

            // When
            List<Integer> result = CollectionUtils.transform(source, transformer);

            // Then
            then(result).containsExactly(1, -1, 1);
        }
    }

    @Nested
    @DisplayName("toArrayList")
    class ToArrayList {

        @Test
        void toArrayList_ShouldReturnEmptyArrayList_WhenInputNull() {
            // Given
            Collection<String> input = null;

            // When
            List<String> actual = CollectionUtils.toArrayList(input);

            // Then
            then(actual)
                    .isInstanceOf(ArrayList.class)
                    .isEmpty();
        }

        @Test
        void toArrayList_ShouldReturnArrayListWithSameElements_WhenInputNotEmpty() {
            // Given
            Collection<String> input = Arrays.asList("a", "b");

            // When
            List<String> actual = CollectionUtils.toArrayList(input);

            // Then
            then(actual)
                    .isInstanceOf(ArrayList.class)
                    .containsExactly("a", "b");
        }
    }

    @Nested
    @DisplayName("toCopyOnWriteArrayList")
    class ToCopyOnWriteArrayList {

        @Test
        void toCopyOnWriteArrayList_ShouldReturnEmpty_WhenInputNull() {
            // Given
            Collection<String> input = null;

            // When
            List<String> actual = CollectionUtils.toCopyOnWriteArrayList(input);

            // Then
            then(actual)
                    .isInstanceOf(CopyOnWriteArrayList.class)
                    .isEmpty();
        }

        @Test
        void toCopyOnWriteArrayList_ShouldReturnCopyOnWriteArrayList_WhenInputNotEmpty() {
            // Given
            Collection<String> input = Arrays.asList("a", "b");

            // When
            List<String> actual = CollectionUtils.toCopyOnWriteArrayList(input);

            // Then
            then(actual)
                    .isInstanceOf(CopyOnWriteArrayList.class)
                    .containsExactly("a", "b");
        }
    }

    @Nested
    @DisplayName("Conversion Methods")
    class ConversionMethods {

        @Test
        void toLinkedList_ShouldReturnLinkedList_WhenInputNotEmpty() {
            // Given
            Collection<String> input = Arrays.asList("a", "b");

            // When
            List<String> actual = CollectionUtils.toLinkedList(input);

            // Then
            then(actual)
                    .isInstanceOf(LinkedList.class)
                    .containsExactly("a", "b");
        }

        @Test
        void toLinkedList_ShouldReturnEmptyLinkedList_WhenCollectionIsNull() {
            // Given
            Collection<String> input = null;

            // When
            List<String> actual = CollectionUtils.toLinkedList(input);

            // Then
            then(actual)
                    .isInstanceOf(LinkedList.class)
                    .isEmpty();
        }

        @Test
        void toLinkedList_ShouldReturnEmptyLinkedList_WhenCollectionIsEmpty() {
            // Given
            Collection<String> input = emptyList();

            // When
            List<String> actual = CollectionUtils.toLinkedList(input);

            // Then
            then(actual)
                    .isInstanceOf(LinkedList.class)
                    .isEmpty();
        }

        @Test
        void toLinkedList_ShouldReturnNewLinkedListWithElements_WhenCollectionHasElements() {
            // Given
            Collection<String> input = Arrays.asList("a", "b", "c");

            // When
            List<String> actual = CollectionUtils.toLinkedList(input);

            // Then
            then(actual)
                    .isInstanceOf(LinkedList.class)
                    .containsExactly("a", "b", "c")
                    .isNotSameAs(input);
        }

        @Test
        void toLinkedList_ShouldPreserveOrder_WhenInputIsArrayList() {
            // Given
            Collection<String> input = new ArrayList<>(Arrays.asList("x", "y"));

            // When
            List<String> actual = CollectionUtils.toLinkedList(input);

            // Then
            then(actual)
                    .isInstanceOf(LinkedList.class)
                    .containsExactly("x", "y");
        }

        @Test
        void toLinkedList_ShouldHandleNullElements_WhenCollectionContainsNull() {
            // Given
            Collection<String> input = Arrays.asList("a", null, "b");

            // When
            List<String> actual = CollectionUtils.toLinkedList(input);

            // Then
            then(actual)
                    .containsExactly("a", null, "b");
        }

        @Test
        void toVector_ShouldReturnVector_WhenInputNotEmpty() {
            // Given
            Collection<String> input = Arrays.asList("a", "b");

            // When
            List<String> actual = CollectionUtils.toVector(input);

            // Then
            then(actual)
                    .isInstanceOf(Vector.class)
                    .containsExactly("a", "b");
        }

        @Test
        void toVector_ShouldReturnEmptyVector_WhenCollectionIsNull() {
            // Given
            Collection<String> input = null;

            // When
            List<String> actual = CollectionUtils.toVector(input);

            // Then
            then(actual)
                    .isInstanceOf(Vector.class)
                    .isEmpty();
        }

        @Test
        void toVector_ShouldReturnEmptyVector_WhenCollectionIsEmpty() {
            // Given
            Collection<String> input = emptySet();

            // When
            List<String> actual = CollectionUtils.toVector(input);

            // Then
            then(actual)
                    .isInstanceOf(Vector.class)
                    .isEmpty();
        }

        @Test
        void toVector_ShouldReturnNewVectorWithElements_WhenCollectionHasElements() {
            // Given
            Collection<String> input = Arrays.asList("p", "q");

            // When
            List<String> actual = CollectionUtils.toVector(input);

            // Then
            then(actual)
                    .isInstanceOf(Vector.class)
                    .containsExactly("p", "q")
                    .isNotSameAs(input);
        }

        @Test
        void toVector_ShouldHandleDuplicates_WhenCollectionHasDuplicates() {
            // Given
            Collection<String> input = Arrays.asList("a", "a", "b");

            // When
            List<String> actual = CollectionUtils.toVector(input);

            // Then
            then(actual)
                    .containsExactly("a", "a", "b");
        }

        @Test
        void toHashSet_ShouldReturnHashSet_WhenInputNotEmpty() {
            // Given
            Collection<String> input = Arrays.asList("a", "b", "a");

            // When
            Set<String> actual = CollectionUtils.toHashSet(input);

            // Then
            then(actual)
                    .isInstanceOf(HashSet.class)
                    .containsExactlyInAnyOrder("a", "b");
        }

        @Test
        void toHashSet_ShouldReturnEmptyHashSet_WhenCollectionIsNull() {
            // Given
            Collection<String> input = null;

            // When
            Set<String> actual = CollectionUtils.toHashSet(input);

            // Then
            then(actual)
                    .isInstanceOf(HashSet.class)
                    .isEmpty();
        }

        @Test
        void toHashSet_ShouldReturnEmptyHashSet_WhenCollectionIsEmpty() {
            // Given
            Collection<String> input = emptyList();

            // When
            Set<String> actual = CollectionUtils.toHashSet(input);

            // Then
            then(actual)
                    .isInstanceOf(HashSet.class)
                    .isEmpty();
        }

        @Test
        void toHashSet_ShouldReturnHashSetWithUniqueElements_WhenCollectionHasDuplicates() {
            // Given
            Collection<String> input = Arrays.asList("a", "b", "a", "c");

            // When
            Set<String> actual = CollectionUtils.toHashSet(input);

            // Then
            then(actual)
                    .isInstanceOf(HashSet.class)
                    .containsExactlyInAnyOrder("a", "b", "c")
                    .hasSize(3);
        }

        @Test
        void toHashSet_ShouldPreserveNull_WhenCollectionContainsNull() {
            // Given
            Collection<String> input = Arrays.asList("a", null, "b", null);

            // When
            Set<String> actual = CollectionUtils.toHashSet(input);

            // Then
            then(actual)
                    .containsExactlyInAnyOrder("a", "b", null)
                    .contains((String) null);
        }

        @Test
        void toHashSet_ShouldNotPreserveOrder_WhenInputIsOrdered() {
            // Given
            Collection<String> input = Arrays.asList("x", "y", "z");

            // When
            Set<String> actual = CollectionUtils.toHashSet(input);

            // Then
            then(actual)
                    .containsExactlyInAnyOrder("x", "y", "z")
                    .isNotInstanceOf(LinkedHashSet.class);
        }

        @Test
        void toLinkedHashSet_ShouldPreserveOrder_WhenInputNotEmpty() {
            // Given
            Collection<String> input = Arrays.asList("a", "b", "a");

            // When
            Set<String> actual = CollectionUtils.toLinkedHashSet(input);

            // Then
            then(actual)
                    .isInstanceOf(LinkedHashSet.class)
                    .containsExactly("a", "b");
        }

        @Test
        void toLinkedHashSet_ShouldReturnEmptyLinkedHashSet_WhenCollectionIsNull() {
            // Given
            Collection<String> input = null;

            // When
            Set<String> actual = CollectionUtils.toLinkedHashSet(input);

            // Then
            then(actual)
                    .isInstanceOf(LinkedHashSet.class)
                    .isEmpty();
        }

        @Test
        void toLinkedHashSet_ShouldReturnEmptyLinkedHashSet_WhenCollectionIsEmpty() {
            // Given
            Collection<String> input = emptyList();

            // When
            Set<String> actual = CollectionUtils.toLinkedHashSet(input);

            // Then
            then(actual)
                    .isInstanceOf(LinkedHashSet.class)
                    .isEmpty();
        }

        @Test
        void toLinkedHashSet_ShouldPreserveInsertionOrder_WhenCollectionHasElements() {
            // Given
            Collection<String> input = Arrays.asList("first", "second", "third");

            // When
            Set<String> actual = CollectionUtils.toLinkedHashSet(input);

            // Then
            then(actual)
                    .isInstanceOf(LinkedHashSet.class)
                    .containsExactly("first", "second", "third");
        }

        @Test
        void toLinkedHashSet_ShouldRemoveDuplicatesButPreserveFirstOccurrenceOrder() {
            // Given
            Collection<String> input = Arrays.asList("a", "b", "a", "c", "b");

            // When
            Set<String> actual = CollectionUtils.toLinkedHashSet(input);

            // Then
            then(actual)
                    .containsExactly("a", "b", "c");
        }

        @Test
        void toLinkedHashSet_ShouldPreserveNullAsFirstOccurrence() {
            // Given
            Collection<String> input = Arrays.asList(null, "a", null, "b");

            // When
            Set<String> actual = CollectionUtils.toLinkedHashSet(input);

            // Then
            then(actual)
                    .containsExactly(null, "a", "b");
        }

        @Test
        void toCopyOnWriteArraySet_ShouldReturnCopyOnWriteArraySet_WhenInputNotEmpty() {
            // Given
            Collection<String> input = Arrays.asList("a", "b");

            // When
            Set<String> actual = CollectionUtils.toCopyOnWriteArraySet(input);

            // Then
            then(actual)
                    .isInstanceOf(CopyOnWriteArraySet.class)
                    .containsExactlyInAnyOrder("a", "b");
        }

        @Test
        void toCopyOnWriteArraySet_ShouldReturnEmptySet_WhenCollectionIsNull() {
            // Given
            Collection<String> input = null;

            // When
            Set<String> actual = CollectionUtils.toCopyOnWriteArraySet(input);

            // Then
            then(actual)
                    .isInstanceOf(CopyOnWriteArraySet.class)
                    .isEmpty();
        }

        @Test
        void toCopyOnWriteArraySet_ShouldReturnEmptySet_WhenCollectionIsEmpty() {
            // Given
            Collection<String> input = emptySet();

            // When
            Set<String> actual = CollectionUtils.toCopyOnWriteArraySet(input);

            // Then
            then(actual)
                    .isInstanceOf(CopyOnWriteArraySet.class)
                    .isEmpty();
        }

        @Test
        void toCopyOnWriteArraySet_ShouldReturnSetWithUniqueElements_WhenCollectionHasDuplicates() {
            // Given
            Collection<String> input = Arrays.asList("x", "y", "x", "z");

            // When
            Set<String> actual = CollectionUtils.toCopyOnWriteArraySet(input);

            // Then
            then(actual)
                    .isInstanceOf(CopyOnWriteArraySet.class)
                    .containsExactlyInAnyOrder("x", "y", "z");
        }

        @Test
        void toCopyOnWriteArraySet_ShouldPreserveInsertionOrder_WhenNoDuplicates() {
            // Given
            Collection<String> input = Arrays.asList("p", "q", "r");

            // When
            Set<String> actual = CollectionUtils.toCopyOnWriteArraySet(input);

            // Then
            then(actual)
                    .containsExactly("p", "q", "r"); // حفظ ترتیب
        }

        @Test
        void toCopyOnWriteArraySet_ShouldIncludeNull_WhenCollectionContainsNull() {
            // Given
            Collection<String> input = Arrays.asList("a", null, "b");

            // When
            Set<String> actual = CollectionUtils.toCopyOnWriteArraySet(input);

            // Then
            then(actual)
                    .containsExactlyInAnyOrder("a", "b", null)
                    .contains((String) null);
        }

        @Test
        void toCopyOnWriteArraySet_ShouldBeThreadSafeInstance() {
            // Given
            Collection<String> input = Arrays.asList("thread", "safe");

            // When
            Set<String> actual = CollectionUtils.toCopyOnWriteArraySet(input);

            // Then
            then(actual)
                    .isInstanceOf(CopyOnWriteArraySet.class)
                    .isNotInstanceOf(HashSet.class);
        }
    }

    @Nested
    @DisplayName("union(Collection, T...)")
    class UnionCollectionWithVarargs {

        @Test
        void union_ShouldReturnEmpty_WhenDominantNullAndRecessiveNull() {
            // Given
            Collection<String> inputDominant = null;
            String[] inputRecessive = null;

            // When
            List<String> actual = CollectionUtils.union(inputDominant, inputRecessive);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void union_ShouldReturnEmpty_WhenDominantNullAndRecessiveEmptyArray() {
            // Given
            Collection<String> inputDominant = null;
            String[] inputRecessive = new String[0];

            // When
            List<String> actual = CollectionUtils.union(inputDominant, inputRecessive);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void union_ShouldReturnEmpty_WhenDominantEmptyAndRecessiveNull() {
            // Given
            Collection<String> inputDominant = emptyList();
            String[] inputRecessive = null;

            // When
            List<String> actual = CollectionUtils.union(inputDominant, inputRecessive);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void union_ShouldReturnEmpty_WhenDominantEmptyAndRecessiveEmptyArray() {
            // Given
            Collection<String> inputDominant = emptyList();
            String[] inputRecessive = {};

            // When
            List<String> actual = CollectionUtils.union(inputDominant, inputRecessive);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void union_ShouldReturnEmpty_WhenBothNullOrEmpty() {
            // Given
            Collection<String> inputDominant = null;
            String[] inputRecessive = {};

            // When
            List<String> actual = CollectionUtils.union(inputDominant, inputRecessive);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void union_ShouldReturnDominantOnly_WhenRecessiveNullAndDominantHasElements() {
            // Given
            Collection<String> inputDominant = Arrays.asList("a", "b");
            String[] inputRecessive = null;

            // When
            List<String> actual = CollectionUtils.union(inputDominant, inputRecessive);

            // Then
            then(actual).containsExactly("a", "b");
        }

        @Test
        void union_ShouldReturnDominantOnly_WhenRecessiveEmptyArrayAndDominantHasElements() {
            // Given
            Collection<String> inputDominant = Arrays.asList("a", "b");
            String[] inputRecessive = new String[0];

            // When
            List<String> actual = CollectionUtils.union(inputDominant, inputRecessive);

            // Then
            then(actual).containsExactly("a", "b");
        }

        @Test
        void union_ShouldReturnRecessiveOnly_WhenDominantNullAndRecessiveHasElements() {
            // Given
            Collection<String> inputDominant = null;
            String[] inputRecessive = {"x", "y"};

            // When
            List<String> actual = CollectionUtils.union(inputDominant, inputRecessive);

            // Then
            then(actual).containsExactly("x", "y");
        }

        @Test
        void union_ShouldPreserveOrderAndAvoidDuplicates_WhenDominantAndRecessiveHaveOverlap() {
            // Given
            Collection<String> inputDominant = new CopyOnWriteArrayList<>(Arrays.asList("a", "b"));
            String[] inputRecessive = {"b", "c", "a"};

            // When
            List<String> actual = CollectionUtils.union(inputDominant, inputRecessive);

            // Then
            then(actual).containsExactly("a", "b", "c");
        }

        @Test
        void union_ShouldHandleNullElementsInRecessive_WhenDominantDoesNotContainThem() {
            // Given
            Collection<String> inputDominant = List.of("a");
            String[] inputRecessive = {"b", null, "c"};

            // When
            List<String> actual = CollectionUtils.union(inputDominant, inputRecessive);

            // Then
            then(actual).containsExactly("a", "b", null, "c");
        }

        @Test
        void union_ShouldNotAddNullFromRecessive_WhenDominantAlreadyContainsNull() {
            // Given
            Collection<String> inputDominant = Arrays.asList("a", null);
            String[] inputRecessive = {null, "b"};

            // When
            List<String> actual = CollectionUtils.union(inputDominant, inputRecessive);

            // Then
            then(actual).containsExactly("a", null, "b");
        }

        @Test
        void union_ShouldIncludeDominantThenUniqueRecessive_WhenBothNonEmpty() {
            // Given
            Collection<String> inputDominant = Arrays.asList("a", "b");
            String[] inputRecessive = {"b", "c"};

            // When
            List<String> actual = CollectionUtils.union(inputDominant, inputRecessive);

            // Then
            then(actual).containsExactly("a", "b", "c");
        }
    }

    @Nested
    @DisplayName("union(Collection, Collection)")
    class UnionTwoCollections {

        @Test
        void union_ShouldReturnEmpty_WhenBothNull() {
            // Given
            Collection<String> inputDominant = null;
            Collection<String> inputRecessive = null;

            // When
            List<String> actual = CollectionUtils.union(inputDominant, inputRecessive);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void union_ShouldReturnEmpty_WhenDominantEmptyAndRecessiveNull() {
            // Given
            Collection<String> inputDominant = emptyList();
            Collection<String> inputRecessive = null;

            // When
            List<String> actual = CollectionUtils.union(inputDominant, inputRecessive);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void union_ShouldReturnEmpty_WhenDominantNullAndRecessiveEmpty() {
            // Given
            Collection<String> inputDominant = null;
            Collection<String> inputRecessive = emptyList();

            // When
            List<String> actual = CollectionUtils.union(inputDominant, inputRecessive);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void union_ShouldReturnRecessiveOnly_WhenDominantNullAndRecessiveHasElements() {
            // Given
            Collection<String> inputDominant = null;
            Collection<String> inputRecessive = Arrays.asList("x", "y");

            // When
            List<String> actual = CollectionUtils.union(inputDominant, inputRecessive);

            // Then
            then(actual).containsExactly("x", "y");
        }

        @Test
        void union_ShouldReturnDominantOnly_WhenRecessiveNullAndDominantHasElements() {
            // Given
            Collection<String> inputDominant = Arrays.asList("a", "b");
            Collection<String> inputRecessive = null;

            // When
            List<String> actual = CollectionUtils.union(inputDominant, inputRecessive);

            // Then
            then(actual).containsExactly("a", "b");
        }

        @Test
        void union_ShouldReturnDominantOnly_WhenRecessiveEmptyAndDominantHasElements() {
            // Given
            Collection<String> inputDominant = Arrays.asList("a", "b");
            Collection<String> inputRecessive = emptySet();

            // When
            List<String> actual = CollectionUtils.union(inputDominant, inputRecessive);

            // Then
            then(actual).containsExactly("a", "b");
        }

        @Test
        void union_ShouldPreserveDominantOrderAndAvoidDuplicates_WhenRecessiveHasOverlap() {
            // Given
            Collection<String> inputDominant = new LinkedList<>(Arrays.asList("a", "b"));
            Collection<String> inputRecessive = Arrays.asList("b", "c", "a");

            // When
            List<String> actual = CollectionUtils.union(inputDominant, inputRecessive);

            // Then
            then(actual).containsExactly("a", "b", "c");
        }

        @Test
        void union_ShouldIncludeNullFromRecessive_WhenNotInDominant() {
            // Given
            Collection<String> inputDominant = List.of("a");
            Collection<String> inputRecessive = Arrays.asList(null, "b");

            // When
            List<String> actual = CollectionUtils.union(inputDominant, inputRecessive);

            // Then
            then(actual).containsExactly("a", null, "b");
        }

        @Test
        void union_ShouldNotDuplicateNull_WhenAlreadyInDominant() {
            // Given
            Collection<String> inputDominant = Arrays.asList(null, "a");
            Collection<String> inputRecessive = Arrays.asList("a", null);

            // When
            List<String> actual = CollectionUtils.union(inputDominant, inputRecessive);

            // Then
            then(actual).containsExactly(null, "a");
        }

        @Test
        void union_ShouldIncludeDominantThenUniqueRecessive_WhenBothNonEmpty() {
            // Given
            Collection<String> inputDominant = Arrays.asList("a", "b");
            Collection<String> inputRecessive = Arrays.asList("b", "c");

            // When
            List<String> actual = CollectionUtils.union(inputDominant, inputRecessive);

            // Then
            then(actual).containsExactly("a", "b", "c");
        }
    }

    @Nested
    @DisplayName("union(Map, Map)")
    class UnionTwoMaps {

        @Test
        void union_ShouldReturnEmpty_WhenBothNull() {
            // Given
            Map<String, Integer> inputDominant = null;
            Map<String, Integer> inputRecessive = null;

            // When
            Map<String, Integer> actual = CollectionUtils.union(inputDominant, inputRecessive);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void union_ShouldReturnEmpty_WhenDominantEmptyAndRecessiveNull() {
            // Given
            Map<String, Integer> inputDominant = emptyMap();
            Map<String, Integer> inputRecessive = null;

            // When
            Map<String, Integer> actual = CollectionUtils.union(inputDominant, inputRecessive);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void union_ShouldReturnEmpty_WhenDominantNullAndRecessiveEmpty() {
            // Given
            Map<String, Integer> inputDominant = null;
            Map<String, Integer> inputRecessive = emptyMap();

            // When
            Map<String, Integer> actual = CollectionUtils.union(inputDominant, inputRecessive);

            // Then
            then(actual).isEmpty();
        }

        @Test
        void union_ShouldReturnRecessiveOnly_WhenDominantNullAndRecessiveHasEntries() {
            // Given
            Map<String, Integer> inputDominant = null;
            Map<String, Integer> inputRecessive = Map.of("x", 1, "y", 2);

            // When
            Map<String, Integer> actual = CollectionUtils.union(inputDominant, inputRecessive);

            // Then
            then(actual).containsOnly(
                    Map.entry("x", 1),
                    Map.entry("y", 2)
            );
        }

        @Test
        void union_ShouldReturnDominantOnly_WhenRecessiveNullAndDominantHasEntries() {
            // Given
            Map<String, Integer> inputDominant = Map.of("a", 1);
            Map<String, Integer> inputRecessive = null;

            // When
            Map<String, Integer> actual = CollectionUtils.union(inputDominant, inputRecessive);

            // Then
            then(actual).containsExactly(Map.entry("a", 1));
        }

        @Test
        void union_ShouldReturnDominantOnly_WhenRecessiveEmptyAndDominantHasEntries() {
            // Given
            Map<String, Integer> inputDominant = Map.of("a", 1);
            Map<String, Integer> inputRecessive = emptyMap();

            // When
            Map<String, Integer> actual = CollectionUtils.union(inputDominant, inputRecessive);

            // Then
            then(actual).containsExactly(Map.entry("a", 1));
        }

        @Test
        void union_ShouldPreferDominantValue_WhenSameKeyExists() {
            // Given
            Map<String, Integer> inputDominant = Map.of("key", 100);
            Map<String, Integer> inputRecessive = Map.of("key", 1, "extra", 2);

            // When
            Map<String, Integer> actual = CollectionUtils.union(inputDominant, inputRecessive);

            // Then
            then(actual).containsOnly(
                    Map.entry("extra", 2),
                    Map.entry("key", 100)
            );
        }

        @Test
        void union_ShouldPreferDominantValues_WhenKeyConflict() {
            // Given
            Map<String, Integer> inputDominant = Map.of("a", 1, "b", 20);
            Map<String, Integer> inputRecessive = Map.of("b", 2, "c", 3);

            // When
            Map<String, Integer> actual = CollectionUtils.union(inputDominant, inputRecessive);

            // Then
            then(actual).containsOnly(
                    Map.entry("a", 1),
                    Map.entry("b", 20),
                    Map.entry("c", 3)
            );
        }

        @Test
        void union_ShouldThrowIllegalArgumentException_WhenDominantHasNullKey() {
            // Given
            Map<String, Integer> inputDominant = new HashMap<>();
            inputDominant.put(null, 1);
            inputDominant.put("a", null);

            Map<String, Integer> inputRecessive = Map.of("b", 2);

            // When && Then
            thenException()
                    .isThrownBy(() -> CollectionUtils.union(inputDominant, inputRecessive))
                    .isInstanceOf(IllegalArgumentException.class)
                    .withMessage("Key cannot be null");
        }

        @Test
        void union_ShouldThrowIllegalArgumentException_WhenRecessiveHasNullKey() {
            // Given
            Map<String, Integer> inputDominant = new HashMap<>();
            inputDominant.put("a", 1);
            inputDominant.put("b", null);

            Map<String, Integer> inputRecessive = new HashMap<>();
            inputRecessive.put(null, 2);

            // When && Then
            thenException()
                    .isThrownBy(() -> CollectionUtils.union(inputDominant, inputRecessive))
                    .isInstanceOf(IllegalArgumentException.class)
                    .withMessage("Key cannot be null");
        }
    }
}
