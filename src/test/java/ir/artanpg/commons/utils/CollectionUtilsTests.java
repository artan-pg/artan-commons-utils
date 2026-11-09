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

import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

/**
 * Unit tests for {@link CollectionUtils}.
 *
 * @author Mohammad Yazdian
 */
@DisplayName("CollectionUtils unit tests")
class CollectionUtilsTests {

    @Nested
    @DisplayName("constructor")
    class ConstructorTests {

        @Test
        @DisplayName("Should throw UnsupportedOperationException when instantiated")
        @SneakyThrows
        void constructor_ShouldThrowUnsupportedOperationException_WhenInstantiated() {
            // given
            Constructor<CollectionUtils> constructor = CollectionUtils.class.getDeclaredConstructor();
            constructor.setAccessible(true);

            // when & then
            thenThrownBy(constructor::newInstance)
                    .as("Utility class must not be instantiable")
                    .isInstanceOf(InvocationTargetException.class)
                    .hasCauseInstanceOf(UnsupportedOperationException.class)
                    .cause()
                    .hasMessage("This class cannot be instantiated");
        }
    }

    @Nested
    @DisplayName("contains(Collection, T)")
    class ContainsCollectionCandidate {

        @Test
        @DisplayName("Should return true when candidate exists")
        void contains_ShouldReturnTrue_WhenCandidateExists() {
            // given
            Collection<String> source = List.of("a", "b", "c");
            String candidate = "b";

            // when
            boolean result = CollectionUtils.contains(source, candidate);

            // then
            then(result).as("Candidate present in source should be found").isTrue();
        }

        @Test
        @DisplayName("Should return false when candidate is missing")
        void contains_ShouldReturnFalse_WhenCandidateIsMissing() {
            // given
            Collection<String> source = List.of("a", "b");
            String candidate = "z";

            // when
            boolean result = CollectionUtils.contains(source, candidate);

            // then
            then(result).as("Missing candidate should not be found").isFalse();
        }

        @Test
        @DisplayName("Should return false when source is null")
        void contains_ShouldReturnFalse_WhenSourceIsNull() {
            // given
            Collection<String> source = null;

            // when
            boolean result = CollectionUtils.contains(source, "a");

            // then
            then(result).as("Null source should yield false").isFalse();
        }

        @Test
        @DisplayName("Should return false when source is empty")
        void contains_ShouldReturnFalse_WhenSourceIsEmpty() {
            // given
            Collection<String> source = List.of();

            // when
            boolean result = CollectionUtils.contains(source, "a");

            // then
            then(result).as("Empty source should yield false").isFalse();
        }

        @Test
        @DisplayName("Should work when source is already a Set")
        void contains_ShouldReturnTrue_WhenSourceIsSet() {
            // given
            Collection<String> source = Set.of("a", "b");
            String candidate = "a";

            // when
            boolean result = CollectionUtils.contains(source, candidate);

            // then
            then(result).as("Set source should support contains").isTrue();
        }
    }

    @Nested
    @DisplayName("contains(Collection, Collection)")
    class ContainsCollectionCandidates {

        @Test
        @DisplayName("Should return true when any candidate exists")
        void contains_ShouldReturnTrue_WhenAnyCandidateExists() {
            // given
            Collection<String> source = List.of("a", "b", "c");
            Collection<String> candidates = List.of("x", "b");

            // when
            boolean result = CollectionUtils.contains(source, candidates);

            // then
            then(result).as("At least one overlapping element should yield true").isTrue();
        }

        @Test
        @DisplayName("Should return false when no candidate exists")
        void contains_ShouldReturnFalse_WhenNoCandidateExists() {
            // given
            Collection<String> source = List.of("a", "b");
            Collection<String> candidates = List.of("x", "y");

            // when
            boolean result = CollectionUtils.contains(source, candidates);

            // then
            then(result).as("No overlap should yield false").isFalse();
        }

        @Test
        @DisplayName("Should return false when source is null")
        void contains_ShouldReturnFalse_WhenSourceIsNull() {
            // given
            Collection<String> source = null;
            Collection<String> candidates = List.of("a");

            // when
            boolean result = CollectionUtils.contains(source, candidates);

            // then
            then(result).as("Null source should yield false").isFalse();
        }

        @Test
        @DisplayName("Should return false when candidates is null")
        void contains_ShouldReturnFalse_WhenCandidatesIsNull() {
            // given
            Collection<String> source = List.of("a");
            Collection<String> candidates = null;

            // when
            boolean result = CollectionUtils.contains(source, candidates);

            // then
            then(result).as("Null candidates should yield false").isFalse();
        }

        @Test
        @DisplayName("Should return false when source is empty")
        void contains_ShouldReturnFalse_WhenSourceIsEmpty() {
            // given
            Collection<String> source = List.of();
            Collection<String> candidates = List.of("a");

            // when
            boolean result = CollectionUtils.contains(source, candidates);

            // then
            then(result).as("Empty source should yield false").isFalse();
        }

        @Test
        @DisplayName("Should return false when candidates is empty")
        void contains_ShouldReturnFalse_WhenCandidatesIsEmpty() {
            // given
            Collection<String> source = List.of("a");
            Collection<String> candidates = List.of();

            // when
            boolean result = CollectionUtils.contains(source, candidates);

            // then
            then(result).as("Empty candidates should yield false").isFalse();
        }

        @Test
        @DisplayName("Should return true when source is already a Set")
        void contains_ShouldReturnTrue_WhenSourceIsSet() {
            // given
            Collection<String> source = Set.of("a", "b", "c");
            Collection<String> candidates = List.of("x", "b");

            // when
            boolean result = CollectionUtils.contains(source, candidates);

            // then
            then(result).as("Set source should support contains with candidates").isTrue();
        }
    }

    @Nested
    @DisplayName("contains(Collection, Predicate)")
    class ContainsCollectionPredicate {

        @Test
        @DisplayName("Should return true when predicate matches")
        void contains_ShouldReturnTrue_WhenPredicateMatches() {
            // given
            Collection<Integer> source = List.of(1, 2, 3);
            Predicate<Integer> predicate = n -> n > 2;

            // when
            boolean result = CollectionUtils.contains(source, predicate);

            // then
            then(result).as("Matching predicate should yield true").isTrue();
        }

        @Test
        @DisplayName("Should return false when predicate does not match")
        void contains_ShouldReturnFalse_WhenPredicateDoesNotMatch() {
            // given
            Collection<Integer> source = List.of(1, 2);
            Predicate<Integer> predicate = n -> n > 5;

            // when
            boolean result = CollectionUtils.contains(source, predicate);

            // then
            then(result).as("Non-matching predicate should yield false").isFalse();
        }

        @Test
        @DisplayName("Should return false when source is null")
        void contains_ShouldReturnFalse_WhenSourceIsNull() {
            // given
            Collection<Integer> source = null;
            Predicate<Integer> predicate = _ -> true;

            // when
            boolean result = CollectionUtils.contains(source, predicate);

            // then
            then(result).as("Null source should yield false").isFalse();
        }

        @Test
        @DisplayName("Should return false when predicate is null")
        void contains_ShouldReturnFalse_WhenPredicateIsNull() {
            // given
            Collection<Integer> source = List.of(1);
            Predicate<Integer> predicate = null;

            // when
            boolean result = CollectionUtils.contains(source, predicate);

            // then
            then(result).as("Null predicate should yield false").isFalse();
        }

        @Test
        @DisplayName("Should return false when source is empty")
        void contains_ShouldReturnFalse_WhenSourceIsEmpty() {
            // given
            Collection<Integer> source = List.of();
            Predicate<Integer> predicate = _ -> true;

            // when
            boolean result = CollectionUtils.contains(source, predicate);

            // then
            then(result).as("Empty source should yield false").isFalse();
        }
    }

    @Nested
    @DisplayName("contains(Collection, Function, R)")
    class ContainsCollectionExtractor {

        @Test
        @DisplayName("Should return true when extracted value matches")
        void contains_ShouldReturnTrue_WhenExtractedValueMatches() {
            // given
            Collection<String> source = List.of("aa", "bbb", "c");
            Function<String, Integer> extractor = String::length;
            Integer expected = 3;

            // when
            boolean result = CollectionUtils.contains(source, extractor, expected);

            // then
            then(result).as("Matching extracted value should yield true").isTrue();
        }

        @Test
        @DisplayName("Should return false when no extracted value matches")
        void contains_ShouldReturnFalse_WhenNoExtractedValueMatches() {
            // given
            Collection<String> source = List.of("a", "bb");
            Function<String, Integer> extractor = String::length;
            Integer expected = 5;

            // when
            boolean result = CollectionUtils.contains(source, extractor, expected);

            // then
            then(result).as("No matching extracted value should yield false").isFalse();
        }

        @Test
        @DisplayName("Should return false when source is null")
        void contains_ShouldReturnFalse_WhenSourceIsNull() {
            // given
            Collection<String> source = null;

            // when
            boolean result = CollectionUtils.contains(source, String::length, 1);

            // then
            then(result).as("Null source should yield false").isFalse();
        }

        @Test
        @DisplayName("Should return false when extractor is null")
        void contains_ShouldReturnFalse_WhenExtractorIsNull() {
            // given
            Collection<String> source = List.of("a");
            Function<String, Integer> extractor = null;

            // when
            boolean result = CollectionUtils.contains(source, extractor, 1);

            // then
            then(result).as("Null extractor should yield false").isFalse();
        }

        @Test
        @DisplayName("Should return false when source is empty")
        void contains_ShouldReturnFalse_WhenSourceIsEmpty() {
            // given
            Collection<String> source = List.of();
            Function<String, Integer> extractor = String::length;

            // when
            boolean result = CollectionUtils.contains(source, extractor, 1);

            // then
            then(result).as("Empty source should yield false").isFalse();
        }
    }

    @Nested
    @DisplayName("contains(Map, Map.Entry)")
    class ContainsMapEntry {

        @Test
        @DisplayName("Should return true when exact entry exists")
        void contains_ShouldReturnTrue_WhenExactEntryExists() {
            // given
            Map<String, Integer> source = Map.of("a", 1, "b", 2);
            Map.Entry<String, Integer> candidate = Map.entry("a", 1);

            // when
            boolean result = CollectionUtils.contains(source, candidate);

            // then
            then(result).as("Exact key-value pair should be found").isTrue();
        }

        @Test
        @DisplayName("Should return false when value differs")
        void contains_ShouldReturnFalse_WhenValueDiffers() {
            // given
            Map<String, Integer> source = Map.of("a", 1);
            Map.Entry<String, Integer> candidate = Map.entry("a", 99);

            // when
            boolean result = CollectionUtils.contains(source, candidate);

            // then
            then(result).as("Different value should not match").isFalse();
        }

        @Test
        @DisplayName("Should return false when source is null")
        void contains_ShouldReturnFalse_WhenSourceIsNull() {
            // given
            Map<String, Integer> source = null;

            // when
            boolean result = CollectionUtils.contains(source, Map.entry("a", 1));

            // then
            then(result).as("Null source should yield false").isFalse();
        }

        @Test
        @DisplayName("Should return false when candidate is null")
        void contains_ShouldReturnFalse_WhenCandidateIsNull() {
            // given
            Map<String, Integer> source = Map.of("a", 1);
            Map.Entry<String, Integer> candidate = null;

            // when
            boolean result = CollectionUtils.contains(source, candidate);

            // then
            then(result).as("Null candidate should yield false").isFalse();
        }

        @Test
        @DisplayName("Should return false when candidate key is null")
        void contains_ShouldReturnFalse_WhenCandidateKeyIsNull() {
            // given
            Map<String, Integer> source = new HashMap<>();
            source.put(null, 1);
            Map.Entry<String, Integer> candidate = new HashMap.SimpleEntry<>(null, 1);

            // when
            boolean result = CollectionUtils.contains(source, candidate);

            // then
            then(result).as("Null key in candidate should yield false").isFalse();
        }


        @Test
        @DisplayName("Should return false when key is not present in source")
        void contains_ShouldReturnFalse_WhenKeyIsNotPresent() {
            // given
            Map<String, Integer> source = Map.of("a", 1);
            Map.Entry<String, Integer> candidate = Map.entry("z", 1);

            // when
            boolean result = CollectionUtils.contains(source, candidate);

            // then
            then(result).as("Missing key should not match").isFalse();
        }

        @Test
        @DisplayName("Should return false when source is empty")
        void contains_ShouldReturnFalse_WhenSourceIsEmpty() {
            // given
            Map<String, Integer> source = Map.of();
            Map.Entry<String, Integer> candidate = Map.entry("a", 1);

            // when
            boolean result = CollectionUtils.contains(source, candidate);

            // then
            then(result).as("Empty source should yield false").isFalse();
        }
    }

    @Nested
    @DisplayName("contains(Map, Collection of entries)")
    class ContainsMapCandidates {

        @Test
        @DisplayName("Should return true when any candidate entry matches")
        void contains_ShouldReturnTrue_WhenAnyCandidateMatches() {
            // given
            Map<String, Integer> source = Map.of("a", 1, "b", 2);
            Collection<Map.Entry<String, Integer>> candidates = List.of(Map.entry("x", 9), Map.entry("b", 2));

            // when
            boolean result = CollectionUtils.contains(source, candidates);

            // then
            then(result).as("Matching candidate entry should yield true").isTrue();
        }

        @Test
        @DisplayName("Should return false when no candidate matches")
        void contains_ShouldReturnFalse_WhenNoCandidateMatches() {
            // given
            Map<String, Integer> source = Map.of("a", 1);
            Collection<Map.Entry<String, Integer>> candidates = List.of(Map.entry("a", 99), Map.entry("x", 1));

            // when
            boolean result = CollectionUtils.contains(source, candidates);

            // then
            then(result).as("No matching entry should yield false").isFalse();
        }

        @Test
        @DisplayName("Should return false when source is null")
        void contains_ShouldReturnFalse_WhenSourceIsNull() {
            // given
            Map<String, Integer> source = null;

            // when
            boolean result = CollectionUtils.contains(source, List.of(Map.entry("a", 1)));

            // then
            then(result).as("Null source should yield false").isFalse();
        }

        @Test
        @DisplayName("Should return false when candidates is null")
        void contains_ShouldReturnFalse_WhenCandidatesIsNull() {
            // given
            Map<String, Integer> source = Map.of("a", 1);
            Collection<Map.Entry<String, Integer>> candidates = null;

            // when
            boolean result = CollectionUtils.contains(source, candidates);

            // then
            then(result).as("Null candidates should yield false").isFalse();
        }

        @Test
        @DisplayName("Should return false when source is empty")
        void contains_ShouldReturnFalse_WhenSourceIsEmpty() {
            // given
            Map<String, Integer> source = Map.of();
            Collection<Map.Entry<String, Integer>> candidates = List.of(Map.entry("a", 1));

            // when
            boolean result = CollectionUtils.contains(source, candidates);

            // then
            then(result).as("Empty source should yield false").isFalse();
        }

        @Test
        @DisplayName("Should return false when candidates is empty")
        void contains_ShouldReturnFalse_WhenCandidatesIsEmpty() {
            // given
            Map<String, Integer> source = Map.of("a", 1);
            Collection<Map.Entry<String, Integer>> candidates = List.of();

            // when
            boolean result = CollectionUtils.contains(source, candidates);

            // then
            then(result).as("Empty candidates should yield false").isFalse();
        }

        @Test
        @DisplayName("Should handle null value in map correctly")
        void contains_ShouldReturnTrue_WhenMapHasNullValueAndEntryMatches() {
            // given
            Map<String, Integer> source = new HashMap<>();
            source.put("a", null);
            Collection<Map.Entry<String, Integer>> candidates = List.of(new HashMap.SimpleEntry<>("a", null));

            // when
            boolean result = CollectionUtils.contains(source, candidates);

            // then
            then(result).as("Null value entry should match when present").isTrue();
        }

        @Test
        @DisplayName("Should return false when candidate has null key")
        void contains_ShouldReturnFalse_WhenCandidateHasNullKey() {
            // given
            Map<String, Integer> source = Map.of("a", 1);
            Collection<Map.Entry<String, Integer>> candidates = List.of(new HashMap.SimpleEntry<>(null, 1));

            // when
            boolean result = CollectionUtils.contains(source, candidates);

            // then
            then(result).as("Null key candidate should be skipped").isFalse();
        }

    }

    @Nested
    @DisplayName("contains(Map, Predicate)")
    class ContainsMapPredicate {

        @Test
        @DisplayName("Should return true when predicate matches an entry")
        void contains_ShouldReturnTrue_WhenPredicateMatches() {
            // given
            Map<String, Integer> source = Map.of("a", 1, "b", 2);
            Predicate<Map.Entry<String, Integer>> predicate = e -> e.getValue() > 1;

            // when
            boolean result = CollectionUtils.contains(source, predicate);

            // then
            then(result).as("Matching entry predicate should yield true").isTrue();
        }

        @Test
        @DisplayName("Should return false when predicate does not match")
        void contains_ShouldReturnFalse_WhenPredicateDoesNotMatch() {
            // given
            Map<String, Integer> source = Map.of("a", 1);
            Predicate<Map.Entry<String, Integer>> predicate = e -> e.getValue() > 10;

            // when
            boolean result = CollectionUtils.contains(source, predicate);

            // then
            then(result).as("Non-matching predicate should yield false").isFalse();
        }

        @Test
        @DisplayName("Should return false when source is null")
        void contains_ShouldReturnFalse_WhenSourceIsNull() {
            // given
            Map<String, Integer> source = null;
            Predicate<Map.Entry<String, Integer>> predicate = _ -> true;

            // when
            boolean result = CollectionUtils.contains(source, predicate);

            // then
            then(result).as("Null source should yield false").isFalse();
        }

        @Test
        @DisplayName("Should return false when predicate is null")
        void contains_ShouldReturnFalse_WhenPredicateIsNull() {
            // given
            Map<String, Integer> source = Map.of("a", 1);
            Predicate<Map.Entry<String, Integer>> predicate = null;

            // when
            boolean result = CollectionUtils.contains(source, predicate);

            // then
            then(result).as("Null predicate should yield false").isFalse();
        }

        @Test
        @DisplayName("Should return false when source is empty")
        void contains_ShouldReturnFalse_WhenSourceIsEmpty() {
            // given
            Map<String, Integer> source = Map.of();
            Predicate<Map.Entry<String, Integer>> predicate = _ -> true;

            // when
            boolean result = CollectionUtils.contains(source, predicate);

            // then
            then(result).as("Empty source should yield false").isFalse();
        }
    }

    @Nested
    @DisplayName("difference(Collection, Collection)")
    class DifferenceCollectionDefault {

        @Test
        @DisplayName("Should return elements present only in source")
        void difference_ShouldReturnElementsOnlyInSource_WhenBothProvided() {
            // given
            Collection<String> source = List.of("a", "b", "c");
            Collection<String> candidates = List.of("b", "d");

            // when
            List<String> result = CollectionUtils.difference(source, candidates);

            // then
            then(result)
                    .as("Difference should contain only source-exclusive elements")
                    .containsExactlyInAnyOrder("a", "c");
        }

        @Test
        @DisplayName("Should return empty list when source is null")
        void difference_ShouldReturnEmptyList_WhenSourceIsNull() {
            // given
            Collection<String> source = null;

            // when
            List<String> result = CollectionUtils.difference(source, List.of("a"));

            // then
            then(result).as("Null source should yield empty list").isEmpty();
        }

        @Test
        @DisplayName("Should return all source elements when candidates is empty")
        void difference_ShouldReturnAllSource_WhenCandidatesIsEmpty() {
            // given
            Collection<String> source = List.of("a", "b");
            Collection<String> candidates = List.of();

            // when
            List<String> result = CollectionUtils.difference(source, candidates);

            // then
            then(result)
                    .as("Empty candidates should keep all source elements")
                    .containsExactly("a", "b");
        }
    }

    @Nested
    @DisplayName("difference(Collection, Collection, Supplier)")
    class DifferenceCollectionFactory {

        @Test
        @DisplayName("Should use provided factory for result")
        void difference_ShouldUseFactory_WhenValid() {
            // given
            Collection<String> source = List.of("a", "b", "c");
            Collection<String> candidates = List.of("b");
            Supplier<LinkedList<String>> factory = LinkedList::new;

            // when
            LinkedList<String> result = CollectionUtils.difference(source, candidates, factory);

            // then
            then(result).as("Result should be instance of factory type").isInstanceOf(LinkedList.class);
            then(result).as("Difference should contain exclusive elements").containsExactlyInAnyOrder("a", "c");
        }

        @SuppressWarnings("DataFlowIssue")
        @Test
        @DisplayName("Should throw IllegalArgumentException when factory is null")
        void difference_ShouldThrowIllegalArgumentException_WhenFactoryIsNull() {
            // given
            Collection<String> source = List.of("a");
            Collection<String> candidates = List.of("b");
            Supplier<List<String>> factory = null;

            // when & then
            thenThrownBy(() -> CollectionUtils.difference(source, candidates, factory))
                    .as("Null factory must be rejected")
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The factory cannot be null");
        }
    }

    @Nested
    @DisplayName("difference(Collection, Collection, Predicate, Supplier)")
    class DifferenceCollectionPredicateFactory {

        @Test
        @DisplayName("Should filter by predicate and exclude candidates")
        void difference_ShouldFilterAndExclude_WhenPredicateAndCandidatesProvided() {
            // given
            Collection<Integer> source = List.of(1, 2, 3, 4);
            Collection<Integer> candidates = List.of(2, 5);
            Predicate<Integer> predicate = n -> n > 1;
            Supplier<ArrayList<Integer>> factory = ArrayList::new;

            // when
            ArrayList<Integer> result = CollectionUtils.difference(source, candidates, predicate, factory);

            // then
            then(result).as("Only elements > 1 and not in candidates should remain").containsExactly(3, 4);
        }

        @SuppressWarnings("DataFlowIssue")
        @Test
        @DisplayName("Should throw IllegalArgumentException when predicate is null")
        void difference_ShouldThrowIllegalArgumentException_WhenPredicateIsNull() {
            // given
            Collection<String> source = List.of("a");
            Collection<String> candidates = List.of("b");
            Predicate<String> predicate = null;
            Supplier<List<String>> factory = ArrayList::new;

            // when & then
            thenThrownBy(() -> CollectionUtils.difference(source, candidates, predicate, factory))
                    .as("Null predicate must be rejected")
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The predicate cannot be null");
        }

        @SuppressWarnings("DataFlowIssue")
        @Test
        @DisplayName("Should throw IllegalArgumentException when factory is null")
        void difference_ShouldThrowIllegalArgumentException_WhenFactoryIsNull() {
            // given
            Collection<String> source = List.of("a");
            Collection<String> candidates = List.of("b");
            Predicate<String> predicate = _ -> true;
            Supplier<List<String>> factory = null;

            // when & then
            thenThrownBy(() -> CollectionUtils.difference(source, candidates, predicate, factory))
                    .as("Null factory must be rejected")
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The factory cannot be null");
        }

        @Test
        @DisplayName("Should return empty when source is empty")
        void difference_ShouldReturnEmpty_WhenSourceIsEmpty() {
            // given
            Collection<String> source = List.of();
            Collection<String> candidates = List.of("a");
            Predicate<String> predicate = _ -> true;
            Supplier<List<String>> factory = ArrayList::new;

            // when
            List<String> result = CollectionUtils.difference(source, candidates, predicate, factory);

            // then
            then(result).as("Empty source should yield empty result").isEmpty();
        }

        @Test
        @DisplayName("Should return all source elements when candidates is empty")
        void difference_ShouldReturnAllSource_WhenCandidatesIsEmpty() {
            // given
            Collection<Integer> source = List.of(1, 2, 3);
            Collection<Integer> candidates = List.of();
            Predicate<Integer> predicate = n -> n > 1;
            Supplier<List<Integer>> factory = ArrayList::new;

            // when
            List<Integer> result = CollectionUtils.difference(source, candidates, predicate, factory);

            // then
            then(result).as("Empty candidates should keep all source elements").containsExactly(1, 2, 3);
        }

        @Test
        @DisplayName("Should optimize when candidates is already a Set")
        void difference_ShouldWork_WhenCandidatesIsSet() {
            // given
            Collection<String> source = List.of("a", "b", "c");
            Collection<String> candidates = Set.of("b");
            Predicate<String> predicate = _ -> true;
            Supplier<List<String>> factory = ArrayList::new;

            // when
            List<String> result = CollectionUtils.difference(source, candidates, predicate, factory);

            // then
            then(result).as("Set candidates should be handled correctly").containsExactlyInAnyOrder("a", "c");
        }
    }

    @Nested
    @DisplayName("difference(Map, Map, Supplier)")
    class DifferenceMapFactory {

        @Test
        @DisplayName("Should return entries whose keys are absent in candidates")
        void difference_ShouldReturnKeyDifference_WhenValid() {
            // given
            Map<String, Integer> source = Map.of("a", 1, "b", 2, "c", 3);
            Map<String, Integer> candidates = Map.of("b", 99);
            Supplier<HashMap<String, Integer>> factory = HashMap::new;

            // when
            HashMap<String, Integer> result = CollectionUtils.difference(source, candidates, factory);

            // then
            then(result).as("Result should exclude keys present in candidates").containsOnlyKeys("a", "c");
            then(result).as("Values should come from source").containsEntry("a", 1).containsEntry("c", 3);
        }

        @SuppressWarnings("DataFlowIssue")
        @Test
        @DisplayName("Should throw IllegalArgumentException when factory is null")
        void difference_ShouldThrowIllegalArgumentException_WhenFactoryIsNull() {
            // given
            Map<String, Integer> source = Map.of("a", 1);
            Map<String, Integer> candidates = Map.of("b", 2);
            Supplier<HashMap<String, Integer>> factory = null;

            // when & then
            thenThrownBy(() -> CollectionUtils.difference(source, candidates, factory))
                    .as("Null factory must be rejected")
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The factory cannot be null");
        }

        @Test
        @DisplayName("Should return empty when source is empty")
        void difference_ShouldReturnEmpty_WhenSourceIsEmpty() {
            // given
            Map<String, Integer> source = Map.of();
            Map<String, Integer> candidates = Map.of("a", 1);
            Supplier<HashMap<String, Integer>> factory = HashMap::new;

            // when
            HashMap<String, Integer> result = CollectionUtils.difference(source, candidates, factory);

            // then
            then(result).as("Empty source should yield empty result").isEmpty();
        }

        @Test
        @DisplayName("Should return all source when candidates is empty")
        void difference_ShouldReturnAllSource_WhenCandidatesIsEmpty() {
            // given
            Map<String, Integer> source = Map.of("a", 1, "b", 2);
            Map<String, Integer> candidates = Map.of();
            Supplier<HashMap<String, Integer>> factory = HashMap::new;

            // when
            HashMap<String, Integer> result = CollectionUtils.difference(source, candidates, factory);

            // then
            then(result)
                    .as("Empty candidates should keep all source entries")
                    .containsExactlyInAnyOrderEntriesOf(source);
        }
    }

    @Nested
    @DisplayName("find(Collection, T)")
    class FindCollectionCandidate {

        @Test
        @DisplayName("Should return Optional of candidate when present")
        void find_ShouldReturnOptionalOfCandidate_WhenPresent() {
            // given
            Collection<String> source = List.of("a", "b");
            String candidate = "b";

            // when
            Optional<String> result = CollectionUtils.find(source, candidate);

            // then
            then(result).as("Present candidate should be returned").contains("b");
        }

        @Test
        @DisplayName("Should return empty when candidate is missing")
        void find_ShouldReturnEmpty_WhenCandidateIsMissing() {
            // given
            Collection<String> source = List.of("a");
            String candidate = "z";

            // when
            Optional<String> result = CollectionUtils.find(source, candidate);

            // then
            then(result).as("Missing candidate should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty when source is null")
        void find_ShouldReturnEmpty_WhenSourceIsNull() {
            // given
            Collection<String> source = null;

            // when
            Optional<String> result = CollectionUtils.find(source, "a");

            // then
            then(result).as("Null source should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty when candidate is null")
        void find_ShouldReturnEmpty_WhenCandidateIsNull() {
            // given
            Collection<String> source = List.of("a");
            String candidate = null;

            // when
            Optional<String> result = CollectionUtils.find(source, candidate);

            // then
            then(result).as("Null candidate should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should work when source is a Set")
        void find_ShouldReturnOptional_WhenSourceIsSet() {
            // given
            Collection<String> source = Set.of("a", "b");
            String candidate = "a";

            // when
            Optional<String> result = CollectionUtils.find(source, candidate);

            // then
            then(result).as("Set source should support find").contains("a");
        }
    }

    @Nested
    @DisplayName("find(Collection, Collection)")
    class FindCollectionCandidates {

        @Test
        @DisplayName("Should return first matching candidate")
        void find_ShouldReturnFirstMatchingCandidate_WhenPresent() {
            // given
            Collection<String> source = List.of("a", "b", "c");
            Collection<String> candidates = List.of("x", "b", "c");

            // when
            Optional<String> result = CollectionUtils.find(source, candidates);

            // then
            then(result).as("First matching candidate in iteration order should be returned").contains("b");
        }

        @Test
        @DisplayName("Should return empty when no candidate matches")
        void find_ShouldReturnEmpty_WhenNoCandidateMatches() {
            // given
            Collection<String> source = List.of("a");
            Collection<String> candidates = List.of("x", "y");

            // when
            Optional<String> result = CollectionUtils.find(source, candidates);

            // then
            then(result).as("No match should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty when source is null")
        void find_ShouldReturnEmpty_WhenSourceIsNull() {
            // given
            Collection<String> source = null;
            Collection<String> candidates = List.of("a");

            // when
            Optional<String> result = CollectionUtils.find(source, candidates);

            // then
            then(result).as("Null source should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty when candidates is null")
        void find_ShouldReturnEmpty_WhenCandidatesIsNull() {
            // given
            Collection<String> source = List.of("a");
            Collection<String> candidates = null;

            // when
            Optional<String> result = CollectionUtils.find(source, candidates);

            // then
            then(result).as("Null candidates should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty when source is empty")
        void find_ShouldReturnEmpty_WhenSourceIsEmpty() {
            // given
            Collection<String> source = List.of();
            Collection<String> candidates = List.of("a");

            // when
            Optional<String> result = CollectionUtils.find(source, candidates);

            // then
            then(result).as("Empty source should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return first match when source is already a Set")
        void find_ShouldReturnFirstMatch_WhenSourceIsSet() {
            // given
            Collection<String> source = Set.of("a", "b", "c");
            Collection<String> candidates = List.of("x", "b");

            // when
            Optional<String> result = CollectionUtils.find(source, candidates);

            // then
            then(result).as("Set source should support find with candidates").contains("b");
        }
    }

    @Nested
    @DisplayName("find(Collection, Predicate)")
    class FindCollectionPredicate {

        @Test
        @DisplayName("Should return first element matching predicate")
        void find_ShouldReturnFirstMatch_WhenPredicateMatches() {
            // given
            Collection<Integer> source = List.of(1, 2, 3);
            Predicate<Integer> predicate = n -> n > 1;

            // when
            Optional<Integer> result = CollectionUtils.find(source, predicate);

            // then
            then(result).as("First matching element should be returned").contains(2);
        }

        @Test
        @DisplayName("Should return empty when predicate does not match")
        void find_ShouldReturnEmpty_WhenPredicateDoesNotMatch() {
            // given
            Collection<Integer> source = List.of(1, 2);
            Predicate<Integer> predicate = n -> n > 10;

            // when
            Optional<Integer> result = CollectionUtils.find(source, predicate);

            // then
            then(result).as("No match should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty when source is null")
        void find_ShouldReturnEmpty_WhenSourceIsNull() {
            // given
            Collection<Integer> source = null;
            Predicate<Integer> predicate = _ -> true;

            // when
            Optional<Integer> result = CollectionUtils.find(source, predicate);

            // then
            then(result).as("Null source should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty when predicate is null")
        void find_ShouldReturnEmpty_WhenPredicateIsNull() {
            // given
            Collection<Integer> source = List.of(1);
            Predicate<Integer> predicate = null;

            // when
            Optional<Integer> result = CollectionUtils.find(source, predicate);

            // then
            then(result).as("Null predicate should yield empty").isEmpty();
        }
    }

    @Nested
    @DisplayName("find(Collection, Function, V)")
    class FindCollectionProperty {

        @Test
        @DisplayName("Should return first element with matching property")
        void find_ShouldReturnFirstMatch_WhenPropertyMatches() {
            // given
            Collection<String> source = List.of("a", "bb", "ccc");
            Function<String, Integer> property = String::length;
            Integer value = 2;

            // when
            Optional<String> result = CollectionUtils.find(source, property, value);

            // then
            then(result).as("First element with matching property should be returned").contains("bb");
        }

        @Test
        @DisplayName("Should return empty when no property matches")
        void find_ShouldReturnEmpty_WhenNoPropertyMatches() {
            // given
            Collection<String> source = List.of("a", "bb");
            Function<String, Integer> property = String::length;
            Integer value = 5;

            // when
            Optional<String> result = CollectionUtils.find(source, property, value);

            // then
            then(result).as("No match should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty when source is null")
        void find_ShouldReturnEmpty_WhenSourceIsNull() {
            // given
            Collection<String> source = null;

            // when
            Optional<String> result = CollectionUtils.find(source, String::length, 1);

            // then
            then(result).as("Null source should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty when property is null")
        void find_ShouldReturnEmpty_WhenPropertyIsNull() {
            // given
            Collection<String> source = List.of("a");
            Function<String, Integer> property = null;

            // when
            Optional<String> result = CollectionUtils.find(source, property, 1);

            // then
            then(result).as("Null property should yield empty").isEmpty();
        }
    }

    @Nested
    @DisplayName("find(Map, Predicate)")
    class FindMapPredicate {

        @Test
        @DisplayName("Should return first matching entry as immutable entry")
        void find_ShouldReturnFirstMatchingEntry_WhenPredicateMatches() {
            // given
            Map<String, Integer> source = new LinkedHashMap<>();
            source.put("a", 1);
            source.put("b", 2);
            Predicate<Map.Entry<String, Integer>> predicate = e -> e.getValue() > 1;

            // when
            Optional<Map.Entry<String, Integer>> result = CollectionUtils.find(source, predicate);

            // then
            then(result).as("Matching entry should be present").isPresent();
            then(result.get().getKey()).as("Key should match").isEqualTo("b");
            then(result.get().getValue()).as("Value should match").isEqualTo(2);
        }

        @Test
        @DisplayName("Should return empty when predicate does not match")
        void find_ShouldReturnEmpty_WhenPredicateDoesNotMatch() {
            // given
            Map<String, Integer> source = Map.of("a", 1);
            Predicate<Map.Entry<String, Integer>> predicate = e -> e.getValue() > 10;

            // when
            Optional<Map.Entry<String, Integer>> result = CollectionUtils.find(source, predicate);

            // then
            then(result).as("No match should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty when source is null")
        void find_ShouldReturnEmpty_WhenSourceIsNull() {
            // given
            Map<String, Integer> source = null;
            Predicate<Map.Entry<String, Integer>> predicate = _ -> true;

            // when
            Optional<Map.Entry<String, Integer>> result = CollectionUtils.find(source, predicate);

            // then
            then(result).as("Null source should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty when predicate is null")
        void find_ShouldReturnEmpty_WhenPredicateIsNull() {
            // given
            Map<String, Integer> source = Map.of("a", 1);
            Predicate<Map.Entry<String, Integer>> predicate = null;

            // when
            Optional<Map.Entry<String, Integer>> result = CollectionUtils.find(source, predicate);

            // then
            then(result).as("Null predicate should yield empty").isEmpty();
        }
    }

    @Nested
    @DisplayName("findByKey")
    class FindByKey {

        @Test
        @DisplayName("Should return first entry whose key matches predicate")
        void findByKey_ShouldReturnMatchingEntry_WhenKeyMatches() {
            // given
            Map<String, Integer> source = new LinkedHashMap<>();
            source.put("aa", 1);
            source.put("b", 2);
            Predicate<String> predicate = k -> k.length() == 1;

            // when
            Optional<Map.Entry<String, Integer>> result = CollectionUtils.findByKey(source, predicate);

            // then
            then(result).as("Matching key entry should be present").isPresent();
            then(result.get().getKey()).as("Key should be the matching one").isEqualTo("b");
        }

        @Test
        @DisplayName("Should return empty when no key matches")
        void findByKey_ShouldReturnEmpty_WhenNoKeyMatches() {
            // given
            Map<String, Integer> source = Map.of("aa", 1);
            Predicate<String> predicate = k -> k.length() == 1;

            // when
            Optional<Map.Entry<String, Integer>> result = CollectionUtils.findByKey(source, predicate);

            // then
            then(result).as("No match should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty when source is null")
        void findByKey_ShouldReturnEmpty_WhenSourceIsNull() {
            // given
            Map<String, Integer> source = null;
            Predicate<String> predicate = _ -> true;

            // when
            Optional<Map.Entry<String, Integer>> result = CollectionUtils.findByKey(source, predicate);

            // then
            then(result).as("Null source should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty when predicate is null")
        void findByKey_ShouldReturnEmpty_WhenPredicateIsNull() {
            // given
            Map<String, Integer> source = Map.of("a", 1);
            Predicate<String> predicate = null;

            // when
            Optional<Map.Entry<String, Integer>> result = CollectionUtils.findByKey(source, predicate);

            // then
            then(result).as("Null predicate should yield empty").isEmpty();
        }
    }

    @Nested
    @DisplayName("findByValue")
    class FindByValue {

        @Test
        @DisplayName("Should return first entry whose value matches predicate")
        void findByValue_ShouldReturnMatchingEntry_WhenValueMatches() {
            // given
            Map<String, Integer> source = new LinkedHashMap<>();
            source.put("a", 1);
            source.put("b", 2);
            Predicate<Integer> predicate = v -> v > 1;

            // when
            Optional<Map.Entry<String, Integer>> result = CollectionUtils.findByValue(source, predicate);

            // then
            then(result).as("Matching value entry should be present").isPresent();
            then(result.get().getKey()).as("Key should correspond to matching value").isEqualTo("b");
        }

        @Test
        @DisplayName("Should return empty when no value matches")
        void findByValue_ShouldReturnEmpty_WhenNoValueMatches() {
            // given
            Map<String, Integer> source = Map.of("a", 1);
            Predicate<Integer> predicate = v -> v > 10;

            // when
            Optional<Map.Entry<String, Integer>> result = CollectionUtils.findByValue(source, predicate);

            // then
            then(result).as("No match should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty when source is null")
        void findByValue_ShouldReturnEmpty_WhenSourceIsNull() {
            // given
            Map<String, Integer> source = null;
            Predicate<Integer> predicate = _ -> true;

            // when
            Optional<Map.Entry<String, Integer>> result = CollectionUtils.findByValue(source, predicate);

            // then
            then(result).as("Null source should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty when predicate is null")
        void findByValue_ShouldReturnEmpty_WhenPredicateIsNull() {
            // given
            Map<String, Integer> source = Map.of("a", 1);
            Predicate<Integer> predicate = null;

            // when
            Optional<Map.Entry<String, Integer>> result = CollectionUtils.findByValue(source, predicate);

            // then
            then(result).as("Null predicate should yield empty").isEmpty();
        }
    }

    @Nested
    @DisplayName("findAll(Collection, T)")
    class FindAllCollectionCandidate {

        @Test
        @DisplayName("Should return all occurrences of candidate")
        void findAll_ShouldReturnAllOccurrences_WhenCandidatePresent() {
            // given
            Collection<String> source = List.of("a", "b", "a", "c");
            String candidate = "a";

            // when
            List<String> result = CollectionUtils.findAll(source, candidate);

            // then
            then(result).as("All occurrences of candidate should be returned").containsExactly("a", "a");
        }

        @Test
        @DisplayName("Should return empty list when candidate is missing")
        void findAll_ShouldReturnEmpty_WhenCandidateIsMissing() {
            // given
            Collection<String> source = List.of("a", "b");
            String candidate = "z";

            // when
            List<String> result = CollectionUtils.findAll(source, candidate);

            // then
            then(result).as("Missing candidate should yield empty list").isEmpty();
        }

        @Test
        @DisplayName("Should return empty list when source is null")
        void findAll_ShouldReturnEmpty_WhenSourceIsNull() {
            // given
            Collection<String> source = null;

            // when
            List<String> result = CollectionUtils.findAll(source, "a");

            // then
            then(result).as("Null source should yield empty list").isEmpty();
        }

        @Test
        @DisplayName("Should return empty list when source is empty")
        void findAll_ShouldReturnEmpty_WhenSourceIsEmpty() {
            // given
            Collection<String> source = List.of();

            // when
            List<String> result = CollectionUtils.findAll(source, "a");

            // then
            then(result).as("Empty source should yield empty list").isEmpty();
        }
    }

    @Nested
    @DisplayName("findAll(Collection, Collection)")
    class FindAllCollectionCandidates {

        @Test
        @DisplayName("Should return all candidates present in source")
        void findAll_ShouldReturnMatchingCandidates_WhenPresent() {
            // given
            Collection<String> source = List.of("a", "b", "c");
            Collection<String> candidates = List.of("b", "x", "a");

            // when
            List<String> result = CollectionUtils.findAll(source, candidates);

            // then
            then(result)
                    .as("Candidates found in source should be returned in candidate order")
                    .containsExactly("b", "a");
        }

        @Test
        @DisplayName("Should return empty list when no candidates match")
        void findAll_ShouldReturnEmpty_WhenNoCandidatesMatch() {
            // given
            Collection<String> source = List.of("a");
            Collection<String> candidates = List.of("x", "y");

            // when
            List<String> result = CollectionUtils.findAll(source, candidates);

            // then
            then(result).as("No match should yield empty list").isEmpty();
        }

        @Test
        @DisplayName("Should return empty list when source is null")
        void findAll_ShouldReturnEmpty_WhenSourceIsNull() {
            // given
            Collection<String> source = null;
            Collection<String> candidates = List.of("a");

            // when
            List<String> result = CollectionUtils.findAll(source, candidates);

            // then
            then(result).as("Null source should yield empty list").isEmpty();
        }

        @Test
        @DisplayName("Should return empty list when candidates is null")
        void findAll_ShouldReturnEmpty_WhenCandidatesIsNull() {
            // given
            Collection<String> source = List.of("a");
            Collection<String> candidates = null;

            // when
            List<String> result = CollectionUtils.findAll(source, candidates);

            // then
            then(result).as("Null candidates should yield empty list").isEmpty();
        }
    }

    @Nested
    @DisplayName("findAll(Collection, Predicate)")
    class FindAllCollectionPredicate {

        @Test
        @DisplayName("Should return all elements matching predicate")
        void findAll_ShouldReturnAllMatches_WhenPredicateMatches() {
            // given
            Collection<Integer> source = List.of(1, 2, 3, 4);
            Predicate<Integer> predicate = n -> n % 2 == 0;

            // when
            List<Integer> result = CollectionUtils.findAll(source, predicate);

            // then
            then(result).as("All even numbers should be returned").containsExactly(2, 4);
        }

        @Test
        @DisplayName("Should return empty list when predicate matches nothing")
        void findAll_ShouldReturnEmpty_WhenPredicateMatchesNothing() {
            // given
            Collection<Integer> source = List.of(1, 3);
            Predicate<Integer> predicate = n -> n % 2 == 0;

            // when
            List<Integer> result = CollectionUtils.findAll(source, predicate);

            // then
            then(result).as("No match should yield empty list").isEmpty();
        }

        @Test
        @DisplayName("Should return empty list when source is null")
        void findAll_ShouldReturnEmpty_WhenSourceIsNull() {
            // given
            Collection<Integer> source = null;
            Predicate<Integer> predicate = _ -> true;

            // when
            List<Integer> result = CollectionUtils.findAll(source, predicate);

            // then
            then(result).as("Null source should yield empty list").isEmpty();
        }

        @Test
        @DisplayName("Should return empty list when predicate is null")
        void findAll_ShouldReturnEmpty_WhenPredicateIsNull() {
            // given
            Collection<Integer> source = List.of(1);
            Predicate<Integer> predicate = null;

            // when
            List<Integer> result = CollectionUtils.findAll(source, predicate);

            // then
            then(result).as("Null predicate should yield empty list").isEmpty();
        }
    }

    @Nested
    @DisplayName("findAll(Collection, Function, V)")
    class FindAllCollectionProperty {

        @Test
        @DisplayName("Should return all elements with matching property")
        void findAll_ShouldReturnAllMatches_WhenPropertyMatches() {
            // given
            Collection<String> source = List.of("a", "bb", "c", "dd");
            Function<String, Integer> property = String::length;
            Integer value = 2;

            // when
            List<String> result = CollectionUtils.findAll(source, property, value);

            // then
            then(result).as("All elements with length 2 should be returned").containsExactly("bb", "dd");
        }

        @Test
        @DisplayName("Should return empty list when no property matches")
        void findAll_ShouldReturnEmpty_WhenNoPropertyMatches() {
            // given
            Collection<String> source = List.of("a", "bb");
            Function<String, Integer> property = String::length;
            Integer value = 5;

            // when
            List<String> result = CollectionUtils.findAll(source, property, value);

            // then
            then(result).as("No match should yield empty list").isEmpty();
        }

        @Test
        @DisplayName("Should return empty list when collection is null")
        void findAll_ShouldReturnEmpty_WhenCollectionIsNull() {
            // given
            Collection<String> collection = null;

            // when
            List<String> result = CollectionUtils.findAll(collection, String::length, 1);

            // then
            then(result).as("Null collection should yield empty list").isEmpty();
        }

        @Test
        @DisplayName("Should return empty list when property is null")
        void findAll_ShouldReturnEmpty_WhenPropertyIsNull() {
            // given
            Collection<String> source = List.of("a");
            Function<String, Integer> property = null;

            // when
            List<String> result = CollectionUtils.findAll(source, property, 1);

            // then
            then(result).as("Null property should yield empty list").isEmpty();
        }
    }

    @Nested
    @DisplayName("findAll(Map, Predicate)")
    class FindAllMapPredicate {

        @Test
        @DisplayName("Should return all matching entries as immutable entries")
        void findAll_ShouldReturnAllMatchingEntries_WhenPredicateMatches() {
            // given
            Map<String, Integer> source = new LinkedHashMap<>();
            source.put("a", 1);
            source.put("b", 2);
            source.put("c", 3);
            Predicate<Map.Entry<String, Integer>> predicate = e -> e.getValue() > 1;

            // when
            List<Map.Entry<String, Integer>> result = CollectionUtils.findAll(source, predicate);

            // then
            then(result).as("Two matching entries should be returned").hasSize(2);
            then(result.get(0).getKey()).as("First key").isEqualTo("b");
            then(result.get(1).getKey()).as("Second key").isEqualTo("c");
        }

        @Test
        @DisplayName("Should return empty list when predicate matches nothing")
        void findAll_ShouldReturnEmpty_WhenPredicateMatchesNothing() {
            // given
            Map<String, Integer> source = Map.of("a", 1);
            Predicate<Map.Entry<String, Integer>> predicate = e -> e.getValue() > 10;

            // when
            List<Map.Entry<String, Integer>> result = CollectionUtils.findAll(source, predicate);

            // then
            then(result).as("No match should yield empty list").isEmpty();
        }

        @Test
        @DisplayName("Should return empty list when source is null")
        void findAll_ShouldReturnEmpty_WhenSourceIsNull() {
            // given
            Map<String, Integer> source = null;
            Predicate<Map.Entry<String, Integer>> predicate = _ -> true;

            // when
            List<Map.Entry<String, Integer>> result = CollectionUtils.findAll(source, predicate);

            // then
            then(result).as("Null source should yield empty list").isEmpty();
        }

        @Test
        @DisplayName("Should return empty list when predicate is null")
        void findAll_ShouldReturnEmpty_WhenPredicateIsNull() {
            // given
            Map<String, Integer> source = Map.of("a", 1);
            Predicate<Map.Entry<String, Integer>> predicate = null;

            // when
            List<Map.Entry<String, Integer>> result = CollectionUtils.findAll(source, predicate);

            // then
            then(result).as("Null predicate should yield empty list").isEmpty();
        }
    }

    @Nested
    @DisplayName("findMax")
    class FindMax {

        @Test
        @DisplayName("Should return maximum element according to comparator")
        void findMax_ShouldReturnMax_WhenValid() {
            // given
            Collection<Integer> source = List.of(3, 1, 4, 1, 5);
            Comparator<Integer> comparator = Integer::compareTo;

            // when
            Optional<Integer> result = CollectionUtils.findMax(source, comparator);

            // then
            then(result).as("Maximum element should be returned").contains(5);
        }

        @Test
        @DisplayName("Should return empty when source is null")
        void findMax_ShouldReturnEmpty_WhenSourceIsNull() {
            // given
            Collection<Integer> source = null;

            // when
            Optional<Integer> result = CollectionUtils.findMax(source, Integer::compareTo);

            // then
            then(result).as("Null source should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty when comparator is null")
        void findMax_ShouldReturnEmpty_WhenComparatorIsNull() {
            // given
            Collection<Integer> source = List.of(1, 2);

            // when
            Optional<Integer> result = CollectionUtils.findMax(source, null);

            // then
            then(result).as("Null comparator should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty when source is empty")
        void findMax_ShouldReturnEmpty_WhenSourceIsEmpty() {
            // given
            Collection<Integer> source = List.of();
            Comparator<Integer> comparator = Integer::compareTo;

            // when
            Optional<Integer> result = CollectionUtils.findMax(source, comparator);

            // then
            then(result).as("Empty source should yield empty").isEmpty();
        }
    }

    @Nested
    @DisplayName("findMin")
    class FindMin {

        @Test
        @DisplayName("Should return minimum element according to comparator")
        void findMin_ShouldReturnMin_WhenValid() {
            // given
            Collection<Integer> source = List.of(3, 1, 4, 1, 5);
            Comparator<Integer> comparator = Integer::compareTo;

            // when
            Optional<Integer> result = CollectionUtils.findMin(source, comparator);

            // then
            then(result).as("Minimum element should be returned").contains(1);
        }

        @Test
        @DisplayName("Should return empty when source is null")
        void findMin_ShouldReturnEmpty_WhenSourceIsNull() {
            // given
            Collection<Integer> source = null;

            // when
            Optional<Integer> result = CollectionUtils.findMin(source, Integer::compareTo);

            // then
            then(result).as("Null source should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty when comparator is null")
        void findMin_ShouldReturnEmpty_WhenComparatorIsNull() {
            // given
            Collection<Integer> source = List.of(1, 2);

            // when
            Optional<Integer> result = CollectionUtils.findMin(source, null);

            // then
            then(result).as("Null comparator should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty when source is empty")
        void findMin_ShouldReturnEmpty_WhenSourceIsEmpty() {
            // given
            Collection<Integer> source = List.of();
            Comparator<Integer> comparator = Integer::compareTo;

            // when
            Optional<Integer> result = CollectionUtils.findMin(source, comparator);

            // then
            then(result).as("Empty source should yield empty").isEmpty();
        }
    }

    @Nested
    @DisplayName("groupBy")
    class GroupBy {

        @Test
        @DisplayName("Should group elements by extracted key")
        void groupBy_ShouldGroupByKey_WhenValid() {
            // given
            Collection<String> source = List.of("a", "bb", "c", "dd", "eee");
            Function<String, Integer> function = String::length;

            // when
            Map<Integer, List<String>> result = CollectionUtils.groupBy(source, function);

            // then
            then(result).as("Keys should be the lengths").containsOnlyKeys(1, 2, 3);
            then(result.get(1)).as("Length 1 group").containsExactly("a", "c");
            then(result.get(2)).as("Length 2 group").containsExactly("bb", "dd");
            then(result.get(3)).as("Length 3 group").containsExactly("eee");
        }

        @Test
        @DisplayName("Should return empty map when source is null")
        void groupBy_ShouldReturnEmpty_WhenSourceIsNull() {
            // given
            Collection<String> source = null;

            // when
            Map<Integer, List<String>> result = CollectionUtils.groupBy(source, String::length);

            // then
            then(result).as("Null source should yield empty map").isEmpty();
        }

        @Test
        @DisplayName("Should return empty map when function is null")
        void groupBy_ShouldReturnEmpty_WhenFunctionIsNull() {
            // given
            Collection<String> source = List.of("a");

            // when
            Map<Integer, List<String>> result = CollectionUtils.groupBy(source, null);

            // then
            then(result).as("Null function should yield empty map").isEmpty();
        }

        @Test
        @DisplayName("Should return empty map when source is empty")
        void groupBy_ShouldReturnEmpty_WhenSourceIsEmpty() {
            // given
            Collection<String> source = List.of();
            Function<String, Integer> function = String::length;

            // when
            Map<Integer, List<String>> result = CollectionUtils.groupBy(source, function);

            // then
            then(result).as("Empty source should yield empty map").isEmpty();
        }
    }

    @Nested
    @DisplayName("isEmpty(Collection)")
    class IsEmptyCollection {

        @Test
        @DisplayName("Should return true when collection is null")
        void isEmpty_ShouldReturnTrue_WhenCollectionIsNull() {
            // given
            Collection<?> collection = null;

            // when
            boolean result = CollectionUtils.isEmpty(collection);

            // then
            then(result).as("Null collection should be empty").isTrue();
        }

        @Test
        @DisplayName("Should return true when collection is empty")
        void isEmpty_ShouldReturnTrue_WhenCollectionIsEmpty() {
            // given
            Collection<?> collection = List.of();

            // when
            boolean result = CollectionUtils.isEmpty(collection);

            // then
            then(result).as("Empty collection should be empty").isTrue();
        }

        @Test
        @DisplayName("Should return false when collection has elements")
        void isEmpty_ShouldReturnFalse_WhenCollectionHasElements() {
            // given
            Collection<?> collection = List.of("a");

            // when
            boolean result = CollectionUtils.isEmpty(collection);

            // then
            then(result).as("Non-empty collection should not be empty").isFalse();
        }
    }

    @Nested
    @DisplayName("isEmpty(Map)")
    class IsEmptyMap {

        @Test
        @DisplayName("Should return true when map is null")
        void isEmpty_ShouldReturnTrue_WhenMapIsNull() {
            // given
            Map<?, ?> map = null;

            // when
            boolean result = CollectionUtils.isEmpty(map);

            // then
            then(result).as("Null map should be empty").isTrue();
        }

        @Test
        @DisplayName("Should return true when map is empty")
        void isEmpty_ShouldReturnTrue_WhenMapIsEmpty() {
            // given
            Map<?, ?> map = Map.of();

            // when
            boolean result = CollectionUtils.isEmpty(map);

            // then
            then(result).as("Empty map should be empty").isTrue();
        }

        @Test
        @DisplayName("Should return false when map has entries")
        void isEmpty_ShouldReturnFalse_WhenMapHasEntries() {
            // given
            Map<?, ?> map = Map.of("a", 1);

            // when
            boolean result = CollectionUtils.isEmpty(map);

            // then
            then(result).as("Non-empty map should not be empty").isFalse();
        }
    }

    @Nested
    @DisplayName("intersection(Collection, Collection)")
    class IntersectionCollection {

        @Test
        @DisplayName("Should return common elements respecting cardinality")
        void intersection_ShouldReturnCommonElements_WhenValid() {
            // given
            Collection<String> source = List.of("a", "a", "b", "c");
            Collection<String> candidates = List.of("a", "b", "b", "d");

            // when
            Collection<String> result = CollectionUtils.intersection(source, candidates);

            // then
            then(result).as("Intersection should respect min cardinality").containsExactlyInAnyOrder("a", "b");
        }

        @Test
        @DisplayName("Should return empty when source is null")
        void intersection_ShouldReturnEmpty_WhenSourceIsNull() {
            // given
            Collection<String> source = null;

            // when
            Collection<String> result = CollectionUtils.intersection(source, List.of("a"));

            // then
            then(result).as("Null source should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty when candidates is null")
        void intersection_ShouldReturnEmpty_WhenCandidatesIsNull() {
            // given
            Collection<String> source = List.of("a");

            // when
            Collection<String> result = CollectionUtils.intersection(source, null);

            // then
            then(result).as("Null candidates should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty when source is empty")
        void intersection_ShouldReturnEmpty_WhenSourceIsEmpty() {
            // given
            Collection<String> source = List.of();
            Collection<String> candidates = List.of("a");

            // when
            Collection<String> result = CollectionUtils.intersection(source, candidates);

            // then
            then(result).as("Empty source should yield empty").isEmpty();
        }
    }

    @Nested
    @DisplayName("intersection(Map, Map)")
    class IntersectionMap {

        @Test
        @DisplayName("Should return entries from candidates whose keys exist in source")
        void intersection_ShouldReturnCommonKeyEntries_WhenValid() {
            // given
            Map<String, Integer> source = Map.of("a", 1, "b", 2);
            Map<String, Integer> candidates = Map.of("b", 20, "c", 30);

            // when
            Map<String, Integer> result = CollectionUtils.intersection(source, candidates);

            // then
            then(result).as("Only common keys from candidates should be present").containsOnlyKeys("b");
            then(result.get("b")).as("Value should come from candidates").isEqualTo(20);
        }

        @Test
        @DisplayName("Should return empty when source is null")
        void intersection_ShouldReturnEmpty_WhenSourceIsNull() {
            // given
            Map<String, Integer> source = null;

            // when
            Map<String, Integer> result = CollectionUtils.intersection(source, Map.of("a", 1));

            // then
            then(result).as("Null source should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty when candidates is null")
        void intersection_ShouldReturnEmpty_WhenCandidatesIsNull() {
            // given
            Map<String, Integer> source = Map.of("a", 1);

            // when
            Map<String, Integer> result = CollectionUtils.intersection(source, null);

            // then
            then(result).as("Null candidates should yield empty").isEmpty();
        }
    }

    @Nested
    @DisplayName("merge(Collection, T...)")
    class MergeCollectionVarargs {

        @Test
        @DisplayName("Should merge source and varargs elements")
        void merge_ShouldMergeElements_WhenBothPresent() {
            // given
            Collection<String> source = List.of("a", "b");
            String[] candidates = {"c", "d"};

            // when
            List<String> result = CollectionUtils.merge(source, candidates);

            // then
            then(result).as("Merged list should contain all elements").containsExactly("a", "b", "c", "d");
        }

        @Test
        @DisplayName("Should return candidates when source is empty")
        void merge_ShouldReturnCandidates_WhenSourceIsEmpty() {
            // given
            Collection<String> source = List.of();
            String[] candidates = {"a", "b"};

            // when
            List<String> result = CollectionUtils.merge(source, candidates);

            // then
            then(result).as("Empty source should yield candidates").containsExactly("a", "b");
        }

        @Test
        @DisplayName("Should return source when candidates is empty")
        void merge_ShouldReturnSource_WhenCandidatesIsEmpty() {
            // given
            Collection<String> source = List.of("a", "b");
            String[] candidates = {};

            // when
            List<String> result = CollectionUtils.merge(source, candidates);

            // then
            then(result).as("Empty candidates should yield source").containsExactly("a", "b");
        }

        @Test
        @DisplayName("Should return empty when both are empty")
        void merge_ShouldReturnEmpty_WhenBothEmpty() {
            // given
            Collection<String> source = List.of();
            String[] candidates = {};

            // when
            List<String> result = CollectionUtils.merge(source, candidates);

            // then
            then(result).as("Both empty should yield empty list").isEmpty();
        }

        @Test
        @DisplayName("Should return empty when source is null and candidates empty")
        void merge_ShouldReturnEmpty_WhenSourceNullAndCandidatesEmpty() {
            // given
            Collection<String> source = null;

            // when
            List<String> result = CollectionUtils.merge(source);

            // then
            then(result).as("Null source with no candidates should yield empty").isEmpty();
        }
    }

    @Nested
    @DisplayName("merge(Collection, Collection)")
    class MergeCollectionCollection {

        @Test
        @DisplayName("Should merge two collections")
        void merge_ShouldMergeCollections_WhenBothPresent() {
            // given
            Collection<String> source = List.of("a", "b");
            Collection<String> candidates = List.of("c", "d");

            // when
            List<String> result = CollectionUtils.merge(source, candidates);

            // then
            then(result).as("Merged list should contain all elements").containsExactly("a", "b", "c", "d");
        }

        @Test
        @DisplayName("Should return candidates when source is empty")
        void merge_ShouldReturnCandidates_WhenSourceIsEmpty() {
            // given
            Collection<String> source = List.of();
            Collection<String> candidates = List.of("a", "b");

            // when
            List<String> result = CollectionUtils.merge(source, candidates);

            // then
            then(result).as("Empty source should yield candidates").containsExactly("a", "b");
        }

        @Test
        @DisplayName("Should return source when candidates is empty")
        void merge_ShouldReturnSource_WhenCandidatesIsEmpty() {
            // given
            Collection<String> source = List.of("a", "b");
            Collection<String> candidates = List.of();

            // when
            List<String> result = CollectionUtils.merge(source, candidates);

            // then
            then(result).as("Empty candidates should yield source").containsExactly("a", "b");
        }

        @Test
        @DisplayName("Should return empty when both are empty")
        void merge_ShouldReturnEmpty_WhenBothEmpty() {
            // given
            Collection<String> source = List.of();
            Collection<String> candidates = List.of();

            // when
            List<String> result = CollectionUtils.merge(source, candidates);

            // then
            then(result).as("Both empty should yield empty list").isEmpty();
        }

        @Test
        @DisplayName("Should return empty when both are null")
        void merge_ShouldReturnEmpty_WhenBothNull() {
            // given
            Collection<String> source = null;
            Collection<String> candidates = null;

            // when
            List<String> result = CollectionUtils.merge(source, candidates);

            // then
            then(result).as("Both null should yield empty list").isEmpty();
        }
    }

    @Nested
    @DisplayName("subtract(Collection, Collection)")
    class SubtractCollection {

        @Test
        @DisplayName("Should remove elements present in elementsToRemove")
        void subtract_ShouldRemoveMatchingElements_WhenValid() {
            // given
            Collection<String> source = List.of("a", "b", "c", "b");
            Collection<String> elementsToRemove = List.of("b", "d");

            // when
            Collection<String> result = CollectionUtils.subtract(source, elementsToRemove);

            // then
            then(result).as("Matching elements should be removed").containsExactly("a", "c");
        }

        @Test
        @DisplayName("Should return empty when source is null")
        void subtract_ShouldReturnEmpty_WhenSourceIsNull() {
            // given
            Collection<String> source = null;

            // when
            Collection<String> result = CollectionUtils.subtract(source, List.of("a"));

            // then
            then(result).as("Null source should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return all source when elementsToRemove is empty")
        void subtract_ShouldReturnSource_WhenElementsToRemoveIsEmpty() {
            // given
            Collection<String> source = List.of("a", "b");
            Collection<String> elementsToRemove = List.of();

            // when
            Collection<String> result = CollectionUtils.subtract(source, elementsToRemove);

            // then
            then(result).as("Empty remove list should keep all source").containsExactly("a", "b");
        }

        @Test
        @DisplayName("Should return empty when source is empty")
        void subtract_ShouldReturnEmpty_WhenSourceIsEmpty() {
            // given
            Collection<String> source = List.of();
            Collection<String> elementsToRemove = List.of("a");

            // when
            Collection<String> result = CollectionUtils.subtract(source, elementsToRemove);

            // then
            then(result).as("Empty source should yield empty").isEmpty();
        }
    }

    @Nested
    @DisplayName("subtract(Map, Map)")
    class SubtractMap {

        @Test
        @DisplayName("Should remove entries whose keys are in elementsToRemove")
        void subtract_ShouldRemoveMatchingKeys_WhenValid() {
            // given
            Map<String, Integer> source = Map.of("a", 1, "b", 2, "c", 3);
            Map<String, Integer> elementsToRemove = Map.of("b", 99);

            // when
            Map<String, Integer> result = CollectionUtils.subtract(source, elementsToRemove);

            // then
            then(result).as("Matching keys should be removed").containsOnlyKeys("a", "c");
        }

        @Test
        @DisplayName("Should return empty map when source is null")
        void subtract_ShouldReturnEmpty_WhenSourceIsNull() {
            // given
            Map<String, Integer> source = null;

            // when
            Map<String, Integer> result = CollectionUtils.subtract(source, Map.of("a", 1));

            // then
            then(result).as("Null source should yield empty map").isEmpty();
        }

        @Test
        @DisplayName("Should return all source when elementsToRemove is empty")
        void subtract_ShouldReturnSource_WhenElementsToRemoveIsEmpty() {
            // given
            Map<String, Integer> source = Map.of("a", 1, "b", 2);
            Map<String, Integer> elementsToRemove = Map.of();

            // when
            Map<String, Integer> result = CollectionUtils.subtract(source, elementsToRemove);

            // then
            then(result).as("Empty remove map should keep all source").containsExactlyInAnyOrderEntriesOf(source);
        }

        @Test
        @DisplayName("Should return empty when source is empty")
        void subtract_ShouldReturnEmpty_WhenSourceIsEmpty() {
            // given
            Map<String, Integer> source = Map.of();
            Map<String, Integer> elementsToRemove = Map.of("a", 1);

            // when
            Map<String, Integer> result = CollectionUtils.subtract(source, elementsToRemove);

            // then
            then(result).as("Empty source should yield empty").isEmpty();
        }
    }

    @Nested
    @DisplayName("partition")
    class Partition {

        @Test
        @DisplayName("Should partition list into sublists of given size")
        void partition_ShouldCreateSublists_WhenValid() {
            // given
            List<Integer> source = List.of(1, 2, 3, 4, 5);
            int size = 2;

            // when
            List<List<Integer>> result = CollectionUtils.partition(source, size);

            // then
            then(result).as("Should create three partitions").hasSize(3);
            then(result.get(0)).as("First partition").containsExactly(1, 2);
            then(result.get(1)).as("Second partition").containsExactly(3, 4);
            then(result.get(2)).as("Last partition may be smaller").containsExactly(5);
        }

        @Test
        @DisplayName("Should return empty list when source is null")
        void partition_ShouldReturnEmpty_WhenSourceIsNull() {
            // given

            // when
            List<List<Integer>> result = CollectionUtils.partition(null, 2);

            // then
            then(result).as("Null source should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty list when source is empty")
        void partition_ShouldReturnEmpty_WhenSourceIsEmpty() {
            // given
            List<Integer> source = List.of();

            // when
            List<List<Integer>> result = CollectionUtils.partition(source, 2);

            // then
            then(result).as("Empty source should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when size is zero")
        void partition_ShouldThrowIllegalArgumentException_WhenSizeIsZero() {
            // given
            List<Integer> source = List.of(1, 2);

            // when & then
            thenThrownBy(() -> CollectionUtils.partition(source, 0))
                    .as("Zero size must be rejected")
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Size must be greater than 0");
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when size is negative")
        void partition_ShouldThrowIllegalArgumentException_WhenSizeIsNegative() {
            // given
            List<Integer> source = List.of(1, 2);

            // when & then
            thenThrownBy(() -> CollectionUtils.partition(source, -1))
                    .as("Negative size must be rejected")
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Size must be greater than 0");
        }

        @SuppressWarnings({"DataFlowIssue", "ResultOfMethodCallIgnored"})
        @Test
        @DisplayName("Should throw IndexOutOfBoundsException when index is negative")
        void partition_ShouldThrowIndexOutOfBoundsException_WhenIndexIsNegative() {
            // given
            List<Integer> source = List.of(1, 2, 3);
            List<List<Integer>> partitions = CollectionUtils.partition(source, 2);

            // when & then
            thenThrownBy(() -> partitions.get(-1))
                    .as("Negative index must be rejected")
                    .isInstanceOf(IndexOutOfBoundsException.class)
                    .hasMessage("Index -1 must not be negative");
        }

        @SuppressWarnings("ResultOfMethodCallIgnored")
        @Test
        @DisplayName("Should throw IndexOutOfBoundsException when index exceeds size")
        void partition_ShouldThrowIndexOutOfBoundsException_WhenIndexExceedsSize() {
            // given
            List<Integer> source = List.of(1, 2, 3);
            List<List<Integer>> partitions = CollectionUtils.partition(source, 2);

            // when & then
            thenThrownBy(() -> partitions.get(2))
                    .as("Index equal to size must be rejected")
                    .isInstanceOf(IndexOutOfBoundsException.class)
                    .hasMessage("Index 2 must be less than size 2");
        }

        @Test
        @DisplayName("Should report isEmpty correctly")
        void partition_ShouldReportIsEmpty_WhenUnderlyingListIsEmpty() {
            // given
            List<Integer> source = List.of();
            List<List<Integer>> partitions = CollectionUtils.partition(source, 2);

            // when
            boolean empty = partitions.isEmpty();

            // then
            then(empty).as("Partition of empty list should be empty").isTrue();
        }

        @Test
        @DisplayName("Should compute equals and hashCode correctly on Partition")
        void partition_ShouldSupportEqualsAndHashCode_WhenCompared() {
            // given
            List<Integer> source = List.of(1, 2, 3, 4);
            Object partition1 = newPartition(source, 2);
            Object partition2 = newPartition(source, 2);
            Object partitionDifferentSize = newPartition(source, 3);
            Object partitionDifferentList = newPartition(List.of(1, 2, 3, 4, 5), 2);

            // when & then
            then(partition1).as("Same Partition instances should be equal").isEqualTo(partition2);
            then(partition1.hashCode())
                    .as("Equal Partition instances should have same hashCode")
                    .isEqualTo(partition2.hashCode());
            then(partition1).as("Different size Partition should not be equal").isNotEqualTo(partitionDifferentSize);
            then(partition1).as("Different list Partition should not be equal").isNotEqualTo(partitionDifferentList);
            then(partition1).as("Partition should not equal null").isNotEqualTo(null);
            then(partition1).as("Partition should not equal unrelated type").isNotEqualTo("string");
            then(partition1).as("Partition should not equal different class List").isNotEqualTo(List.of());
        }
    }

    @Nested
    @DisplayName("transform")
    class Transform {

        @Test
        @DisplayName("Should transform each element using the function")
        void transform_ShouldApplyFunction_WhenValid() {
            // given
            Collection<String> source = List.of("a", "bb", "ccc");
            Function<String, Integer> transformer = String::length;

            // when
            List<Integer> result = CollectionUtils.transform(source, transformer);

            // then
            then(result).as("Transformed lengths should be returned").containsExactly(1, 2, 3);
        }

        @Test
        @DisplayName("Should return empty list when source is null")
        void transform_ShouldReturnEmpty_WhenSourceIsNull() {
            // given
            Collection<String> source = null;

            // when
            List<Integer> result = CollectionUtils.transform(source, String::length);

            // then
            then(result).as("Null source should yield empty list").isEmpty();
        }

        @Test
        @DisplayName("Should return empty list when transformer is null")
        void transform_ShouldReturnEmpty_WhenTransformerIsNull() {
            // given
            Collection<String> source = List.of("a");

            // when
            List<Integer> result = CollectionUtils.transform(source, null);

            // then
            then(result).as("Null transformer should yield empty list").isEmpty();
        }

        @Test
        @DisplayName("Should return empty list when source is empty")
        void transform_ShouldReturnEmpty_WhenSourceIsEmpty() {
            // given
            Collection<String> source = List.of();
            Function<String, Integer> transformer = String::length;

            // when
            List<Integer> result = CollectionUtils.transform(source, transformer);

            // then
            then(result).as("Empty source should yield empty list").isEmpty();
        }
    }

    @Nested
    @DisplayName("toArrayList")
    class ToArrayList {

        @Test
        @DisplayName("Should convert collection to ArrayList")
        void toArrayList_ShouldConvert_WhenValid() {
            // given
            Collection<String> collection = Set.of("a", "b");

            // when
            List<String> result = CollectionUtils.toArrayList(collection);

            // then
            then(result).as("Result should be ArrayList").isInstanceOf(ArrayList.class);
            then(result).as("Result should contain all elements").containsExactlyInAnyOrder("a", "b");
        }

        @Test
        @DisplayName("Should return empty ArrayList when collection is null")
        void toArrayList_ShouldReturnEmpty_WhenCollectionIsNull() {
            // given

            // when
            List<String> result = CollectionUtils.toArrayList(null);

            // then
            then(result).as("Null collection should yield empty ArrayList").isInstanceOf(ArrayList.class).isEmpty();
        }

        @Test
        @DisplayName("Should return empty ArrayList when collection is empty")
        void toArrayList_ShouldReturnEmpty_WhenCollectionIsEmpty() {
            // given
            Collection<String> collection = List.of();

            // when
            List<String> result = CollectionUtils.toArrayList(collection);

            // then
            then(result).as("Empty collection should yield empty ArrayList").isInstanceOf(ArrayList.class).isEmpty();
        }
    }

    @Nested
    @DisplayName("toCopyOnWriteArrayList")
    class ToCopyOnWriteArrayList {

        @Test
        @DisplayName("Should convert collection to CopyOnWriteArrayList")
        void toCopyOnWriteArrayList_ShouldConvert_WhenValid() {
            // given
            Collection<String> collection = List.of("a", "b");

            // when
            List<String> result = CollectionUtils.toCopyOnWriteArrayList(collection);

            // then
            then(result).as("Result should be CopyOnWriteArrayList").isInstanceOf(CopyOnWriteArrayList.class);
            then(result).as("Result should contain all elements").containsExactly("a", "b");
        }

        @Test
        @DisplayName("Should return empty CopyOnWriteArrayList when collection is null")
        void toCopyOnWriteArrayList_ShouldReturnEmpty_WhenCollectionIsNull() {
            // given

            // when
            List<String> result = CollectionUtils.toCopyOnWriteArrayList(null);

            // then
            then(result)
                    .as("Null collection should yield empty CopyOnWriteArrayList")
                    .isInstanceOf(CopyOnWriteArrayList.class)
                    .isEmpty();
        }
    }

    @Nested
    @DisplayName("toLinkedList")
    class ToLinkedList {

        @Test
        @DisplayName("Should convert collection to LinkedList")
        void toLinkedList_ShouldConvert_WhenValid() {
            // given
            Collection<String> collection = List.of("a", "b");

            // when
            List<String> result = CollectionUtils.toLinkedList(collection);

            // then
            then(result).as("Result should be LinkedList").isInstanceOf(LinkedList.class);
            then(result).as("Result should contain all elements").containsExactly("a", "b");
        }

        @Test
        @DisplayName("Should return empty LinkedList when collection is null")
        void toLinkedList_ShouldReturnEmpty_WhenCollectionIsNull() {
            // given

            // when
            List<String> result = CollectionUtils.toLinkedList(null);

            // then
            then(result).as("Null collection should yield empty LinkedList").isInstanceOf(LinkedList.class).isEmpty();
        }
    }

    @Nested
    @DisplayName("toVector")
    class ToVector {

        @Test
        @DisplayName("Should convert collection to Vector")
        void toVector_ShouldConvert_WhenValid() {
            // given
            Collection<String> collection = List.of("a", "b");

            // when
            List<String> result = CollectionUtils.toVector(collection);

            // then
            then(result).as("Result should be Vector").isInstanceOf(Vector.class);
            then(result).as("Result should contain all elements").containsExactly("a", "b");
        }

        @Test
        @DisplayName("Should return empty Vector when collection is null")
        void toVector_ShouldReturnEmpty_WhenCollectionIsNull() {
            // given

            // when
            List<String> result = CollectionUtils.toVector(null);

            // then
            then(result).as("Null collection should yield empty Vector").isInstanceOf(Vector.class).isEmpty();
        }
    }

    @Nested
    @DisplayName("toHashSet")
    class ToHashSet {

        @Test
        @DisplayName("Should convert collection to HashSet")
        void toHashSet_ShouldConvert_WhenValid() {
            // given
            Collection<String> collection = List.of("a", "b", "a");

            // when
            Set<String> result = CollectionUtils.toHashSet(collection);

            // then
            then(result).as("Result should be HashSet").isInstanceOf(HashSet.class);
            then(result).as("Result should contain unique elements").containsExactlyInAnyOrder("a", "b");
        }

        @Test
        @DisplayName("Should return empty HashSet when collection is null")
        void toHashSet_ShouldReturnEmpty_WhenCollectionIsNull() {
            // given

            // when
            Set<String> result = CollectionUtils.toHashSet(null);

            // then
            then(result).as("Null collection should yield empty HashSet").isInstanceOf(HashSet.class).isEmpty();
        }
    }

    @Nested
    @DisplayName("toLinkedHashSet")
    class ToLinkedHashSet {

        @Test
        @DisplayName("Should convert collection to LinkedHashSet")
        void toLinkedHashSet_ShouldConvert_WhenValid() {
            // given
            Collection<String> collection = List.of("a", "b", "a");

            // when
            Set<String> result = CollectionUtils.toLinkedHashSet(collection);

            // then
            then(result).as("Result should be LinkedHashSet").isInstanceOf(LinkedHashSet.class);
            then(result).as("Result should preserve order and uniqueness").containsExactly("a", "b");
        }

        @Test
        @DisplayName("Should return empty LinkedHashSet when collection is null")
        void toLinkedHashSet_ShouldReturnEmpty_WhenCollectionIsNull() {
            // given

            // when
            Set<String> result = CollectionUtils.toLinkedHashSet(null);

            // then
            then(result)
                    .as("Null collection should yield empty LinkedHashSet")
                    .isInstanceOf(LinkedHashSet.class)
                    .isEmpty();
        }
    }

    @Nested
    @DisplayName("toCopyOnWriteArraySet")
    class ToCopyOnWriteArraySet {

        @Test
        @DisplayName("Should convert collection to CopyOnWriteArraySet")
        void toCopyOnWriteArraySet_ShouldConvert_WhenValid() {
            // given
            Collection<String> collection = List.of("a", "b");

            // when
            Set<String> result = CollectionUtils.toCopyOnWriteArraySet(collection);

            // then
            then(result).as("Result should be CopyOnWriteArraySet").isInstanceOf(CopyOnWriteArraySet.class);
            then(result).as("Result should contain all elements").containsExactlyInAnyOrder("a", "b");
        }

        @Test
        @DisplayName("Should return empty CopyOnWriteArraySet when collection is null")
        void toCopyOnWriteArraySet_ShouldReturnEmpty_WhenCollectionIsNull() {
            // given

            // when
            Set<String> result = CollectionUtils.toCopyOnWriteArraySet(null);

            // then
            then(result)
                    .as("Null collection should yield empty CopyOnWriteArraySet")
                    .isInstanceOf(CopyOnWriteArraySet.class)
                    .isEmpty();
        }
    }

    @Nested
    @DisplayName("union(Collection, T...)")
    class UnionCollectionVarargs {

        @Test
        @DisplayName("Should return dominant elements followed by unique recessive")
        void union_ShouldPreferDominant_WhenBothPresent() {
            // given
            Collection<String> dominant = List.of("a", "b");
            String[] recessive = {"b", "c"};

            // when
            List<String> result = CollectionUtils.union(dominant, recessive);

            // then
            then(result).as("Dominant first, then unique recessive").containsExactly("a", "b", "c");
        }

        @Test
        @DisplayName("Should return recessive when dominant is empty")
        void union_ShouldReturnRecessive_WhenDominantIsEmpty() {
            // given
            Collection<String> dominant = List.of();
            String[] recessive = {"a", "b"};

            // when
            List<String> result = CollectionUtils.union(dominant, recessive);

            // then
            then(result).as("Empty dominant should yield recessive").containsExactly("a", "b");
        }

        @Test
        @DisplayName("Should return dominant when recessive is empty")
        void union_ShouldReturnDominant_WhenRecessiveIsEmpty() {
            // given
            Collection<String> dominant = List.of("a", "b");
            String[] recessive = {};

            // when
            List<String> result = CollectionUtils.union(dominant, recessive);

            // then
            then(result).as("Empty recessive should yield dominant").containsExactly("a", "b");
        }

        @Test
        @DisplayName("Should return empty when both empty")
        void union_ShouldReturnEmpty_WhenBothEmpty() {
            // given
            Collection<String> dominant = List.of();
            String[] recessive = {};

            // when
            List<String> result = CollectionUtils.union(dominant, recessive);

            // then
            then(result).as("Both empty should yield empty").isEmpty();
        }
    }

    @Nested
    @DisplayName("union(Collection, Collection)")
    class UnionCollectionCollection {

        @Test
        @DisplayName("Should return dominant elements followed by unique recessive")
        void union_ShouldPreferDominant_WhenBothPresent() {
            // given
            Collection<String> dominant = List.of("a", "b");
            Collection<String> recessive = List.of("b", "c");

            // when
            List<String> result = CollectionUtils.union(dominant, recessive);

            // then
            then(result).as("Dominant first, then unique recessive").containsExactly("a", "b", "c");
        }

        @Test
        @DisplayName("Should return recessive when dominant is empty")
        void union_ShouldReturnRecessive_WhenDominantIsEmpty() {
            // given
            Collection<String> dominant = List.of();
            Collection<String> recessive = List.of("a", "b");

            // when
            List<String> result = CollectionUtils.union(dominant, recessive);

            // then
            then(result).as("Empty dominant should yield recessive").containsExactly("a", "b");
        }

        @Test
        @DisplayName("Should return dominant when recessive is empty")
        void union_ShouldReturnDominant_WhenRecessiveIsEmpty() {
            // given
            Collection<String> dominant = List.of("a", "b");
            Collection<String> recessive = List.of();

            // when
            List<String> result = CollectionUtils.union(dominant, recessive);

            // then
            then(result).as("Empty recessive should yield dominant").containsExactly("a", "b");
        }

        @Test
        @DisplayName("Should return empty when both empty")
        void union_ShouldReturnEmpty_WhenBothEmpty() {
            // given
            Collection<String> dominant = List.of();
            Collection<String> recessive = List.of();

            // when
            List<String> result = CollectionUtils.union(dominant, recessive);

            // then
            then(result).as("Both empty should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty when both null")
        void union_ShouldReturnEmpty_WhenBothNull() {
            // given
            Collection<String> dominant = null;
            Collection<String> recessive = null;

            // when
            List<String> result = CollectionUtils.union(dominant, recessive);

            // then
            then(result).as("Both null should yield empty").isEmpty();
        }
    }

    @Nested
    @DisplayName("union(Map, Map)")
    class UnionMap {

        @Test
        @DisplayName("Should prefer dominant entries on key conflict")
        void union_ShouldPreferDominant_WhenKeyConflict() {
            // given
            Map<String, Integer> dominant = Map.of("a", 1, "b", 2);
            Map<String, Integer> recessive = Map.of("b", 20, "c", 30);

            // when
            Map<String, Integer> result = CollectionUtils.union(dominant, recessive);

            // then
            then(result)
                    .as("Dominant values should win on conflict")
                    .containsEntry("a", 1)
                    .containsEntry("b", 2)
                    .containsEntry("c", 30);
        }

        @Test
        @DisplayName("Should return empty map when both empty")
        void union_ShouldReturnEmpty_WhenBothEmpty() {
            // given
            Map<String, Integer> dominant = Map.of();
            Map<String, Integer> recessive = Map.of();

            // when
            Map<String, Integer> result = CollectionUtils.union(dominant, recessive);

            // then
            then(result).as("Both empty should yield empty map").isEmpty();
        }

        @Test
        @DisplayName("Should return recessive when dominant is empty")
        void union_ShouldReturnRecessive_WhenDominantIsEmpty() {
            // given
            Map<String, Integer> dominant = Map.of();
            Map<String, Integer> recessive = Map.of("a", 1);

            // when
            Map<String, Integer> result = CollectionUtils.union(dominant, recessive);

            // then
            then(result).as("Empty dominant should yield recessive").containsEntry("a", 1);
        }

        @Test
        @DisplayName("Should return dominant when recessive is empty")
        void union_ShouldReturnDominant_WhenRecessiveIsEmpty() {
            // given
            Map<String, Integer> dominant = Map.of("a", 1);
            Map<String, Integer> recessive = Map.of();

            // when
            Map<String, Integer> result = CollectionUtils.union(dominant, recessive);

            // then
            then(result).as("Empty recessive should yield dominant").containsEntry("a", 1);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when recessive has null key")
        void union_ShouldThrowIllegalArgumentException_WhenRecessiveHasNullKey() {
            // given
            Map<String, Integer> dominant = Map.of("a", 1);
            Map<String, Integer> recessive = new HashMap<>();
            recessive.put(null, 2);

            // when & then
            thenThrownBy(() -> CollectionUtils.union(dominant, recessive))
                    .as("Null key in recessive must be rejected")
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Key cannot be null");
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when dominant has null key")
        void union_ShouldThrowIllegalArgumentException_WhenDominantHasNullKey() {
            // given
            Map<String, Integer> dominant = new HashMap<>();
            dominant.put(null, 1);
            Map<String, Integer> recessive = Map.of("a", 2);

            // when & then
            thenThrownBy(() -> CollectionUtils.union(dominant, recessive))
                    .as("Null key in dominant must be rejected")
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Key cannot be null");
        }
    }


    @Nested
    @DisplayName("isNotEmpty(Collection)")
    class IsNotEmptyCollection {

        @Test
        @DisplayName("Should return true when collection has elements")
        void isNotEmpty_ShouldReturnTrue_WhenHasElements() {
            // given
            Collection<String> collection = List.of("a");

            // when
            boolean result = CollectionUtils.isNotEmpty(collection);

            // then
            then(result).as("Has elements").isTrue();
        }

        @Test
        @DisplayName("Should return false when collection is null")
        void isNotEmpty_ShouldReturnFalse_WhenNull() {
            // given

            // when
            boolean result = CollectionUtils.isNotEmpty((Collection<?>) null);

            // then
            then(result).as("Null is empty").isFalse();
        }
    }

    @Nested
    @DisplayName("isNotEmpty(Map)")
    class IsNotEmptyMap {

        @Test
        @DisplayName("Should return true when map has elements")
        void isNotEmpty_ShouldReturnTrue_WhenHasElements() {
            // given
            Map<String, Integer> map = Map.of("a", 1);

            // when
            boolean result = CollectionUtils.isNotEmpty(map);

            // then
            then(result).as("Has elements").isTrue();
        }

        @Test
        @DisplayName("Should return false when map is empty")
        void isNotEmpty_ShouldReturnFalse_WhenEmpty() {
            // given
            Map<String, Integer> map = Map.of();

            // when
            boolean result = CollectionUtils.isNotEmpty(map);

            // then
            then(result).as("Empty is not not-empty").isFalse();
        }
    }

    @Nested
    @DisplayName("size(Collection)")
    class SizeCollection {

        @Test
        @DisplayName("Should return size of collection")
        void size_ShouldReturnSize_WhenValid() {
            // given
            Collection<String> collection = List.of("a", "b");

            // when
            int result = CollectionUtils.size(collection);

            // then
            then(result).as("Size should be 2").isEqualTo(2);
        }

        @Test
        @DisplayName("Should return zero when null")
        void size_ShouldReturnZero_WhenNull() {
            // given

            // when
            int result = CollectionUtils.size((Collection<?>) null);

            // then
            then(result).as("Null size is 0").isEqualTo(0);
        }
    }

    @Nested
    @DisplayName("size(Map)")
    class SizeMap {

        @Test
        @DisplayName("Should return zero when map is null")
        void size_ShouldReturnZero_WhenMapIsNull() {
            // given

            // when
            int result = CollectionUtils.size((Map<?, ?>) null);

            // then
            then(result).as("Null map size should be 0").isEqualTo(0);
        }

        @Test
        @DisplayName("Should return zero when map is empty")
        void size_ShouldReturnZero_WhenMapIsEmpty() {
            // given
            Map<String, Integer> map = Map.of();

            // when
            int result = CollectionUtils.size(map);

            // then
            then(result).as("Empty map size should be 0").isEqualTo(0);
        }

        @Test
        @DisplayName("Should return size of map")
        void size_ShouldReturnSize_WhenValid() {
            // given
            Map<String, Integer> map = Map.of("a", 1);

            // when
            int result = CollectionUtils.size(map);

            // then
            then(result).as("Size should be 1").isEqualTo(1);
        }

        @Test
        @DisplayName("Should return size of multi-entry map")
        void size_ShouldReturnSize_WhenMultipleEntries() {
            // given
            Map<String, Integer> map = Map.of("a", 1, "b", 2, "c", 3);

            // when
            int result = CollectionUtils.size(map);

            // then
            then(result).as("Size should be 3").isEqualTo(3);
        }
    }

    @Nested
    @DisplayName("emptyIfNull(Set)")
    class EmptyIfNullSet {

        @Test
        @DisplayName("Should return empty set when null")
        void emptyIfNull_ShouldReturnEmpty_WhenNull() {
            // given

            // when
            Set<String> result = CollectionUtils.emptyIfNull((Set<String>) null);

            // then
            then(result).as("Null set should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return same when not null")
        void emptyIfNull_ShouldReturnSame_WhenNotNull() {
            // given
            Set<String> source = Set.of("a");

            // when
            Set<String> result = CollectionUtils.emptyIfNull(source);

            // then
            then(result).as("Same instance or equal").isEqualTo(source);
        }
    }

    @Nested
    @DisplayName("emptyIfNull(Map)")
    class EmptyIfNullMap {

        @Test
        @DisplayName("Should return empty map when null")
        void emptyIfNull_ShouldReturnEmpty_WhenNull() {
            // given

            // when
            Map<String, Integer> result = CollectionUtils.emptyIfNull((Map<String, Integer>) null);

            // then
            then(result).as("Null map should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return immutable empty map when null")
        void emptyIfNull_ShouldReturnImmutableEmpty_WhenNull() {
            // given

            // when
            Map<String, Integer> result = CollectionUtils.emptyIfNull((Map<String, Integer>) null);

            // then
            then(result).as("Null should yield empty map").isEmpty();
            thenThrownBy(() -> result.put("a", 1))
                    .as("Empty result should be immutable")
                    .isInstanceOf(UnsupportedOperationException.class);
        }

        @Test
        @DisplayName("Should return same reference when map is non-empty")
        void emptyIfNull_ShouldReturnSameReference_WhenNonEmpty() {
            // given
            Map<String, Integer> source = new HashMap<>(Map.of("a", 1));

            // when
            Map<String, Integer> result = CollectionUtils.emptyIfNull(source);

            // then
            then(result).as("Should return same reference").isSameAs(source);
            then(result).as("Should preserve entries").containsEntry("a", 1);
        }

        @Test
        @DisplayName("Should return same empty map reference when map is empty but not null")
        void emptyIfNull_ShouldReturnSameReference_WhenEmptyNonNull() {
            // given
            Map<String, Integer> source = new HashMap<>();

            // when
            Map<String, Integer> result = CollectionUtils.emptyIfNull(source);

            // then
            then(result).as("Empty non-null map should be returned as-is").isSameAs(source);
            then(result).as("Result should be empty").isEmpty();
        }

        @Test
        @DisplayName("Should preserve mutability of non-null map")
        void emptyIfNull_ShouldPreserveMutability_WhenNonNull() {
            // given
            Map<String, Integer> source = new HashMap<>(Map.of("a", 1));

            // when
            Map<String, Integer> result = CollectionUtils.emptyIfNull(source);
            result.put("b", 2);

            // then
            then(source).as("Mutation through result should affect source").containsEntry("b", 2);
        }

        @Test
        @DisplayName("Should return empty non-null map when null input")
        void emptyIfNull_ShouldNeverReturnNull_WhenNullInput() {
            // given

            // when
            Map<String, Integer> result = CollectionUtils.emptyIfNull((Map<String, Integer>) null);

            // then
            then(result).as("Result should never be null").isNotNull();
            then(result).as("Result should be empty").isEmpty();
        }
    }

    @Nested
    @DisplayName("countMatches")
    class CountMatches {

        @Test
        @DisplayName("Should count elements matching predicate")
        void countMatches_ShouldCountMatching_WhenPredicateValid() {
            // given
            Collection<Integer> source = List.of(1, 2, 3, 4);
            Predicate<Integer> predicate = n -> n > 2;

            // when
            int result = CollectionUtils.countMatches(source, predicate);

            // then
            then(result).as("Count of >2").isEqualTo(2);
        }

        @Test
        @DisplayName("Should return zero when source is null")
        void countMatches_ShouldReturnZero_WhenSourceIsNull() {
            // given

            // when
            int result = CollectionUtils.countMatches(null, _ -> true);

            // then
            then(result).as("Null source should yield 0").isEqualTo(0);
        }

        @Test
        @DisplayName("Should return zero when source is empty")
        void countMatches_ShouldReturnZero_WhenSourceIsEmpty() {
            // given
            Collection<Integer> source = List.of();

            // when
            int result = CollectionUtils.countMatches(source, _ -> true);

            // then
            then(result).as("Empty source should yield 0").isEqualTo(0);
        }

        @Test
        @DisplayName("Should return zero when predicate is null")
        void countMatches_ShouldReturnZero_WhenPredicateIsNull() {
            // given
            Collection<Integer> source = List.of(1, 2, 3);

            // when
            int result = CollectionUtils.countMatches(source, null);

            // then
            then(result).as("Null predicate should yield 0").isEqualTo(0);
        }

        @Test
        @DisplayName("Should return zero when source and predicate are null")
        void countMatches_ShouldReturnZero_WhenSourceAndPredicateNull() {
            // given

            // when
            int result = CollectionUtils.countMatches(null, null);

            // then
            then(result).as("Both null should yield 0").isEqualTo(0);
        }

        @Test
        @DisplayName("Should return zero when predicate matches nothing")
        void countMatches_ShouldReturnZero_WhenNoMatches() {
            // given
            Collection<Integer> source = List.of(1, 3, 5);

            // when
            int result = CollectionUtils.countMatches(source, n -> n % 2 == 0);

            // then
            then(result).as("No matches should yield 0").isEqualTo(0);
        }

        @Test
        @DisplayName("Should return size when predicate matches all")
        void countMatches_ShouldReturnSize_WhenAllMatch() {
            // given
            Collection<Integer> source = List.of(1, 2, 3, 4);

            // when
            int result = CollectionUtils.countMatches(source, _ -> true);

            // then
            then(result).as("All matching should yield size").isEqualTo(4);
        }

        @Test
        @DisplayName("Should count duplicates that match")
        void countMatches_ShouldCountDuplicates_WhenPresent() {
            // given
            Collection<String> source = List.of("a", "a", "b", "a", "c");

            // when
            int result = CollectionUtils.countMatches(source, "a"::equals);

            // then
            then(result).as("All 'a' occurrences should be counted").isEqualTo(3);
        }

        @Test
        @DisplayName("Should handle null elements in source")
        void countMatches_ShouldHandleNullElements_WhenPresent() {
            // given
            Collection<String> source = new ArrayList<>();
            source.add("a");
            source.add(null);
            source.add(null);
            source.add("b");

            // when
            int result = CollectionUtils.countMatches(source, Objects::isNull);

            // then
            then(result).as("Null elements should be counted").isEqualTo(2);
        }

        @Test
        @DisplayName("Should invoke predicate exactly once per element")
        void countMatches_ShouldInvokePredicateOncePerElement_WhenValid() {
            // given
            Collection<Integer> source = List.of(1, 2, 3, 4, 5);
            int[] invocations = {0};
            Predicate<Integer> predicate = n -> {
                invocations[0]++;
                return n % 2 == 0;
            };

            // when
            int result = CollectionUtils.countMatches(source, predicate);

            // then
            then(result).as("Two even numbers").isEqualTo(2);
            then(invocations[0]).as("Predicate invoked exactly once per element").isEqualTo(5);
        }

        @Test
        @DisplayName("Should not modify the source collection")
        void countMatches_ShouldNotModifySource_WhenValid() {
            // given
            List<Integer> source = new ArrayList<>(List.of(1, 2, 3));

            // when
            CollectionUtils.countMatches(source, n -> n > 1);

            // then
            then(source).as("Source should remain unchanged").containsExactly(1, 2, 3);
        }
    }

    @Nested
    @DisplayName("filter")
    class Filter {

        @Test
        @DisplayName("Should return elements matching predicate")
        void filter_ShouldKeepMatching_WhenPredicateValid() {
            // given
            Collection<Integer> source = List.of(1, 2, 3, 4);
            Predicate<Integer> predicate = n -> n % 2 == 0;

            // when
            List<Integer> result = CollectionUtils.filter(source, predicate);

            // then
            then(result).as("Even numbers").containsExactly(2, 4);
        }

        @Test
        @DisplayName("Should return empty when source is null")
        void filter_ShouldReturnEmpty_WhenSourceIsNull() {
            // given

            // when
            List<Integer> result = CollectionUtils.filter(null, _ -> true);

            // then
            then(result).as("Null source should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty when source is empty")
        void filter_ShouldReturnEmpty_WhenSourceIsEmpty() {
            // given
            Collection<Integer> source = List.of();

            // when
            List<Integer> result = CollectionUtils.filter(source, _ -> true);

            // then
            then(result).as("Empty source should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty when predicate is null")
        void filter_ShouldReturnEmpty_WhenPredicateIsNull() {
            // given
            Collection<Integer> source = List.of(1, 2, 3);

            // when
            List<Integer> result = CollectionUtils.filter(source, null);

            // then
            then(result).as("Null predicate should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty when source is null and predicate is null")
        void filter_ShouldReturnEmpty_WhenSourceAndPredicateNull() {
            // given

            // when
            List<Integer> result = CollectionUtils.filter(null, null);

            // then
            then(result).as("Both null should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty list when predicate matches nothing")
        void filter_ShouldReturnEmpty_WhenPredicateMatchesNothing() {
            // given
            Collection<Integer> source = List.of(1, 3, 5);

            // when
            List<Integer> result = CollectionUtils.filter(source, n -> n % 2 == 0);

            // then
            then(result).as("No matching elements should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return all elements when predicate matches all")
        void filter_ShouldReturnAll_WhenPredicateMatchesAll() {
            // given
            Collection<Integer> source = List.of(1, 2, 3);

            // when
            List<Integer> result = CollectionUtils.filter(source, _ -> true);

            // then
            then(result).as("All matching should yield all elements").containsExactly(1, 2, 3);
        }

        @Test
        @DisplayName("Should preserve source iteration order")
        void filter_ShouldPreserveOrder_WhenValid() {
            // given
            Collection<Integer> source = List.of(5, 1, 4, 2, 3);

            // when
            List<Integer> result = CollectionUtils.filter(source, n -> n % 2 == 1);

            // then
            then(result).as("Order of source should be preserved").containsExactly(5, 1, 3);
        }

        @Test
        @DisplayName("Should handle null elements in source")
        void filter_ShouldHandleNullElements_WhenPresent() {
            // given
            Collection<String> source = new ArrayList<>();
            source.add("a");
            source.add(null);
            source.add("b");

            // when
            List<String> result = CollectionUtils.filter(source, Objects::nonNull);

            // then
            then(result).as("Null elements filtered out").containsExactly("a", "b");
        }

        @Test
        @DisplayName("Should return modifiable list when source and predicate valid")
        void filter_ShouldReturnModifiableList_WhenValid() {
            // given
            Collection<Integer> source = List.of(1, 2, 3);

            // when
            List<Integer> result = CollectionUtils.filter(source, n -> n > 1);

            // then
            result.add(99);
            then(result).as("Result should be modifiable in valid path").containsExactly(2, 3, 99);
        }

        @Test
        @DisplayName("Should return immutable empty list when source is null")
        void filter_ShouldReturnImmutableEmpty_WhenSourceIsNull() {
            // given

            // when
            List<Integer> result = CollectionUtils.filter(null, _ -> true);

            // then
            thenThrownBy(() -> result.add(99))
                    .as("Empty result from null source should be immutable")
                    .isInstanceOf(UnsupportedOperationException.class);
        }

        @Test
        @DisplayName("Should not modify the source collection")
        void filter_ShouldNotModifySource_WhenValid() {
            // given
            List<Integer> source = new ArrayList<>(List.of(1, 2, 3));

            // when
            CollectionUtils.filter(source, n -> n == 2);

            // then
            then(source).as("Source should remain unchanged").containsExactly(1, 2, 3);
        }
    }

    @Nested
    @DisplayName("reject")
    class Reject {

        @Test
        @DisplayName("Should reject elements matching predicate")
        void reject_ShouldRemoveMatching_WhenPredicateValid() {
            // given
            Collection<Integer> source = List.of(1, 2, 3, 4);
            Predicate<Integer> predicate = n -> n % 2 == 0;

            // when
            List<Integer> result = CollectionUtils.reject(source, predicate);

            // then
            then(result).as("Odds remaining").containsExactly(1, 3);
        }

        @Test
        @DisplayName("Should return empty when source is null")
        void reject_ShouldReturnEmpty_WhenSourceIsNull() {
            // given

            // when
            List<Integer> result = CollectionUtils.reject(null, _ -> true);

            // then
            then(result).as("Null source should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty when source is empty")
        void reject_ShouldReturnEmpty_WhenSourceIsEmpty() {
            // given
            Collection<Integer> source = List.of();

            // when
            List<Integer> result = CollectionUtils.reject(source, _ -> true);

            // then
            then(result).as("Empty source should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty copy when predicate is null")
        void reject_ShouldReturnCopyOfSource_WhenPredicateIsNull() {
            // given
            Collection<Integer> source = List.of(1, 2, 3);

            // when
            List<Integer> result = CollectionUtils.reject(source, null);

            // then
            then(result).as("Null predicate should return copy of source").containsExactly(1, 2, 3);
        }

        @Test
        @DisplayName("Should return empty copy when source empty and predicate null")
        void reject_ShouldReturnEmpty_WhenSourceEmptyAndPredicateNull() {
            // given
            Collection<Integer> source = List.of();

            // when
            List<Integer> result = CollectionUtils.reject(source, null);

            // then
            then(result).as("Empty source with null predicate should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should reject all elements when predicate matches all")
        void reject_ShouldReturnEmpty_WhenPredicateMatchesAll() {
            // given
            Collection<Integer> source = List.of(1, 2, 3);

            // when
            List<Integer> result = CollectionUtils.reject(source, _ -> true);

            // then
            then(result).as("All rejected should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return all elements when predicate matches none")
        void reject_ShouldReturnAll_WhenPredicateMatchesNone() {
            // given
            Collection<Integer> source = List.of(1, 2, 3);

            // when
            List<Integer> result = CollectionUtils.reject(source, _ -> false);

            // then
            then(result).as("No rejection should yield all elements").containsExactly(1, 2, 3);
        }

        @Test
        @DisplayName("Should return modifiable list when predicate provided")
        void reject_ShouldReturnModifiableList_WhenPredicateProvided() {
            // given
            Collection<Integer> source = List.of(1, 2, 3);

            // when
            List<Integer> result = CollectionUtils.reject(source, n -> n == 2);

            // then
            result.add(99);
            then(result).as("Result should be modifiable").containsExactly(1, 3, 99);
        }

        @Test
        @DisplayName("Should return modifiable list when predicate is null")
        void reject_ShouldReturnModifiableList_WhenPredicateIsNull() {
            // given
            Collection<Integer> source = List.of(1, 2);

            // when
            List<Integer> result = CollectionUtils.reject(source, null);

            // then
            result.add(3);
            then(result).as("Result should be modifiable").containsExactly(1, 2, 3);
        }

        @Test
        @DisplayName("Should not modify the source collection")
        void reject_ShouldNotModifySource_WhenValid() {
            // given
            List<Integer> source = new ArrayList<>(List.of(1, 2, 3));

            // when
            CollectionUtils.reject(source, n -> n == 2);

            // then
            then(source).as("Source should remain unchanged").containsExactly(1, 2, 3);
        }
    }

    @Nested
    @DisplayName("isEqualCollection")
    class IsEqualCollection {

        @Test
        @DisplayName("Should return true when collections have same elements with same cardinality")
        void isEqualCollection_ShouldReturnTrue_WhenSameElements() {
            // given
            Collection<String> a = List.of("a", "b", "a");
            Collection<String> b = List.of("b", "a", "a");

            // when
            boolean result = CollectionUtils.isEqualCollection(a, b);

            // then
            then(result).as("Equal collections should match").isTrue();
        }

        @Test
        @DisplayName("Should return false when different elements")
        void isEqualCollection_ShouldReturnFalse_WhenDifferent() {
            // given
            Collection<String> a = List.of("a");
            Collection<String> b = List.of("b");

            // when
            boolean result = CollectionUtils.isEqualCollection(a, b);

            // then
            then(result).as("Different should not match").isFalse();
        }

        @Test
        @DisplayName("Should return true when both null")
        void isEqualCollection_ShouldReturnTrue_WhenBothNull() {
            // given

            // when
            boolean result = CollectionUtils.isEqualCollection(null, null);

            // then
            then(result).as("Both null should be equal").isTrue();
        }

        @Test
        @DisplayName("Should return true when both are same reference")
        void isEqualCollection_ShouldReturnTrue_WhenSameReference() {
            // given
            Collection<String> a = new ArrayList<>(List.of("a", "b"));

            // when
            boolean result = CollectionUtils.isEqualCollection(a, a);

            // then
            then(result).as("Same reference should be equal").isTrue();
        }

        @Test
        @DisplayName("Should return true when both are empty")
        void isEqualCollection_ShouldReturnTrue_WhenBothEmpty() {
            // given
            Collection<String> a = List.of();
            Collection<String> b = List.of();

            // when
            boolean result = CollectionUtils.isEqualCollection(a, b);

            // then
            then(result).as("Both empty should be equal").isTrue();
        }

        @Test
        @DisplayName("Should return false when one is null and other has elements")
        void isEqualCollection_ShouldReturnFalse_WhenOneNullOtherNonEmpty() {
            // given
            Collection<String> a = null;
            Collection<String> b = List.of("a");

            // when
            boolean result = CollectionUtils.isEqualCollection(a, b);

            // then
            then(result).as("Null vs non-empty should be unequal").isFalse();
        }

        @Test
        @DisplayName("Should return false when one is empty and other is not")
        void isEqualCollection_ShouldReturnFalse_WhenOneEmpty() {
            // given
            Collection<String> a = List.of();
            Collection<String> b = List.of("a");

            // when
            boolean result = CollectionUtils.isEqualCollection(a, b);

            // then
            then(result).as("Empty vs non-empty should be unequal").isFalse();
        }

        @Test
        @DisplayName("Should return false when sizes differ")
        void isEqualCollection_ShouldReturnFalse_WhenSizesDiffer() {
            // given
            Collection<String> a = List.of("a", "b");
            Collection<String> b = List.of("a");

            // when
            boolean result = CollectionUtils.isEqualCollection(a, b);

            // then
            then(result).as("Different sizes should be unequal").isFalse();
        }

        @Test
        @DisplayName("Should return false when cardinalities differ despite same elements")
        void isEqualCollection_ShouldReturnFalse_WhenCardinalityDiffers() {
            // given
            Collection<String> a = List.of("a", "a", "b");
            Collection<String> b = List.of("a", "b", "b");

            // when
            boolean result = CollectionUtils.isEqualCollection(a, b);

            // then
            then(result).as("Different cardinality should be unequal").isFalse();
        }

        @Test
        @DisplayName("Should return false when same size but different elements")
        void isEqualCollection_ShouldReturnFalse_WhenDifferentElementsSameSize() {
            // given
            Collection<String> a = List.of("a", "b");
            Collection<String> b = List.of("a", "c");

            // when
            boolean result = CollectionUtils.isEqualCollection(a, b);

            // then
            then(result).as("Different elements should be unequal").isFalse();
        }

        @Test
        @DisplayName("Should compare null elements correctly")
        void isEqualCollection_ShouldHandleNulls_WhenPresentInBoth() {
            // given
            Collection<String> a = new ArrayList<>();
            a.add(null);
            a.add("a");
            Collection<String> b = new ArrayList<>();
            b.add("a");
            b.add(null);

            // when
            boolean result = CollectionUtils.isEqualCollection(a, b);

            // then
            then(result).as("Nulls in both should be equal").isTrue();
        }

        @Test
        @DisplayName("Should detect null count mismatch")
        void isEqualCollection_ShouldReturnFalse_WhenNullCountDiffers() {
            // given
            Collection<String> a = new ArrayList<>();
            a.add(null);
            a.add(null);
            Collection<String> b = new ArrayList<>();
            b.add(null);
            b.add("a");

            // when
            boolean result = CollectionUtils.isEqualCollection(a, b);

            // then
            then(result).as("Different null cardinality should be unequal").isFalse();
        }

        @Test
        @DisplayName("Should use equals for comparison, not identity")
        void isEqualCollection_ShouldUseEquals_WhenComparingStrings() {
            // given
            Collection<String> a = List.of("abc");
            Collection<String> b = List.of("abc");

            // when
            boolean result = CollectionUtils.isEqualCollection(a, b);

            // then
            then(result).as("Equal strings by value should match").isTrue();
        }

        @Test
        @DisplayName("Should work when one is a Set and other is a List")
        void isEqualCollection_ShouldWork_WhenDifferentCollectionTypes() {
            // given
            Collection<String> a = new HashSet<>(Set.of("a", "b"));
            Collection<String> b = List.of("b", "a");

            // when
            boolean result = CollectionUtils.isEqualCollection(a, b);

            // then
            then(result).as("Set vs List with same elements should be equal").isTrue();
        }

        @Test
        @DisplayName("Should return false when first is empty and second is non-empty")
        void isEqualCollection_ShouldReturnFalse_WhenFirstEmptySecondNonEmpty() {
            // given
            Collection<String> a = List.of();
            Collection<String> b = List.of("a", "b");

            // when
            boolean result = CollectionUtils.isEqualCollection(a, b);

            // then
            then(result)
                    .as("Empty first vs non-empty second should be unequal (short-circuit on isEmpty(a))")
                    .isFalse();
        }

        @Test
        @DisplayName("Should return false when first is non-empty and second is empty")
        void isEqualCollection_ShouldReturnFalse_WhenFirstNonEmptySecondEmpty() {
            // given
            Collection<String> a = List.of("a", "b");
            Collection<String> b = List.of();

            // when
            boolean result = CollectionUtils.isEqualCollection(a, b);

            // then
            then(result)
                    .as("Non-empty first vs empty second should be unequal (isEmpty(b) branch)")
                    .isFalse();
        }

        @Test
        @DisplayName("Should return false when first is non-empty and second is null")
        void isEqualCollection_ShouldReturnFalse_WhenFirstNonEmptySecondNull() {
            // given
            Collection<String> a = List.of("a");
            Collection<String> b = null;

            // when
            boolean result = CollectionUtils.isEqualCollection(a, b);

            // then
            then(result)
                    .as("Non-empty first vs null second should be unequal (isEmpty(b) branch)")
                    .isFalse();
        }

        @Test
        @DisplayName("Should return false when sizes differ even if all elements overlap")
        void isEqualCollection_ShouldReturnFalse_WhenSizeDiffersButElementsOverlap() {
            // given
            Collection<String> a = List.of("a", "b", "c");
            Collection<String> b = List.of("a", "b");

            // when
            boolean result = CollectionUtils.isEqualCollection(a, b);

            // then
            then(result)
                    .as("Different sizes should short-circuit to false (size branch)")
                    .isFalse();
        }

        @Test
        @DisplayName("Should return true when first is null and second is empty")
        void isEqualCollection_ShouldReturnTrue_WhenFirstNullSecondEmpty() {
            // given
            Collection<String> a = null;
            Collection<String> b = List.of();

            // when
            boolean result = CollectionUtils.isEqualCollection(a, b);

            // then
            then(result)
                    .as("Null first and empty second are both empty → true")
                    .isTrue();
        }

        @Test
        @DisplayName("Should return true when first is empty and second is null")
        void isEqualCollection_ShouldReturnTrue_WhenFirstEmptySecondNull() {
            // given
            Collection<String> a = List.of();
            Collection<String> b = null;

            // when
            boolean result = CollectionUtils.isEqualCollection(a, b);

            // then
            then(result)
                    .as("Empty first and null second are both empty → true")
                    .isTrue();
        }

        @Test
        @DisplayName("Should return false when first is null and second is non-empty")
        void isEqualCollection_ShouldReturnFalse_WhenFirstNullSecondNonEmpty() {
            // given
            Collection<String> a = null;
            Collection<String> b = List.of("a", "b");

            // when
            boolean result = CollectionUtils.isEqualCollection(a, b);

            // then
            then(result)
                    .as("Null first vs non-empty second → false (condition short-circuits on isEmpty(b))")
                    .isFalse();
        }
    }

    @Nested
    @DisplayName("getFirst")
    class GetFirst {

        @Test
        @DisplayName("Should return first element")
        void getFirst_ShouldReturnFirst_WhenListHasElements() {
            // given
            List<String> list = List.of("a", "b");

            // when
            Optional<String> result = CollectionUtils.getFirst(list);

            // then
            then(result).as("First element").contains("a");
        }

        @Test
        @DisplayName("Should return empty when list is empty")
        void getFirst_ShouldReturnEmpty_WhenListIsEmpty() {
            // given
            List<String> list = List.of();

            // when
            Optional<String> result = CollectionUtils.getFirst(list);

            // then
            then(result).as("Empty list should yield empty").isEmpty();
        }
    }

    @Nested
    @DisplayName("getLast")
    class GetLast {

        @Test
        @DisplayName("Should return last element")
        void getLast_ShouldReturnLast_WhenListHasElements() {
            // given
            List<String> list = List.of("a", "b");

            // when
            Optional<String> result = CollectionUtils.getLast(list);

            // then
            then(result).as("Last element").contains("b");
        }

        @Test
        @DisplayName("Should return empty when list is null")
        void getLast_ShouldReturnEmpty_WhenListIsNull() {
            // given

            // when
            Optional<String> result = CollectionUtils.getLast(null);

            // then
            then(result).as("Null list should yield empty").isEmpty();
        }
    }

    @Nested
    @DisplayName("get")
    class Get {

        @Test
        @DisplayName("Should return element at index")
        void get_ShouldReturnElement_WhenValidIndex() {
            // given
            List<String> list = List.of("a", "b", "c");

            // when
            Optional<String> result = CollectionUtils.get(list, 1);

            // then
            then(result).as("Element at 1").contains("b");
        }

        @Test
        @DisplayName("Should return empty when index invalid")
        void get_ShouldReturnEmpty_WhenIndexInvalid() {
            // given
            List<String> list = List.of("a");

            // when
            Optional<String> result = CollectionUtils.get(list, 5);

            // then
            then(result).as("Invalid index should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty when list is null")
        void get_ShouldReturnEmpty_WhenListIsNull() {
            // given

            // when
            Optional<String> result = CollectionUtils.get(null, 0);

            // then
            then(result).as("Null list should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty when list is empty")
        void get_ShouldReturnEmpty_WhenListIsEmpty() {
            // given
            List<String> list = List.of();

            // when
            Optional<String> result = CollectionUtils.get(list, 0);

            // then
            then(result).as("Empty list should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty when index is negative")
        void get_ShouldReturnEmpty_WhenIndexIsNegative() {
            // given
            List<String> list = List.of("a", "b");

            // when
            Optional<String> result = CollectionUtils.get(list, -1);

            // then
            then(result).as("Negative index should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty when index equals size")
        void get_ShouldReturnEmpty_WhenIndexEqualsSize() {
            // given
            List<String> list = List.of("a", "b");

            // when
            Optional<String> result = CollectionUtils.get(list, 2);

            // then
            then(result).as("Index equal to size should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return first element when index is zero")
        void get_ShouldReturnFirstElement_WhenIndexZero() {
            // given
            List<String> list = List.of("a", "b", "c");

            // when
            Optional<String> result = CollectionUtils.get(list, 0);

            // then
            then(result).as("First element should be returned").contains("a");
        }

        @Test
        @DisplayName("Should return last element when index is size minus one")
        void get_ShouldReturnLastElement_WhenIndexSizeMinusOne() {
            // given
            List<String> list = List.of("a", "b", "c");

            // when
            Optional<String> result = CollectionUtils.get(list, 2);

            // then
            then(result).as("Last element should be returned").contains("c");
        }

        @Test
        @DisplayName("Should return empty when element at index is null")
        void get_ShouldReturnEmpty_WhenElementIsNull() {
            // given
            List<String> list = new ArrayList<>();
            list.add("a");
            list.add(null);

            // when
            Optional<String> result = CollectionUtils.get(list, 1);

            // then
            then(result).as("Null element should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should work with LinkedList input")
        void get_ShouldWork_WhenListIsLinkedList() {
            // given
            List<String> list = new LinkedList<>(List.of("a", "b", "c"));

            // when
            Optional<String> result = CollectionUtils.get(list, 1);

            // then
            then(result).as("LinkedList should be supported").contains("b");
        }
    }

    @Nested
    @DisplayName("toMap")
    class ToMap {

        @Test
        @DisplayName("Should convert collection to map using key and value functions")
        void toMap_ShouldCreateMap_WhenFunctionsProvided() {
            // given
            Collection<String> source = List.of("a", "bb");
            Function<String, String> keyFn = Function.identity();
            Function<String, Integer> valueFn = String::length;

            // when
            Map<String, Integer> result = CollectionUtils.toMap(source, keyFn, valueFn);

            // then
            then(result).as("Map from collection").containsEntry("a", 1).containsEntry("bb", 2);
        }

        @Test
        @DisplayName("Should return empty map when source is null")
        void toMap_ShouldReturnEmpty_WhenSourceIsNull() {
            // given

            // when
            Map<String, Integer> result = CollectionUtils.toMap(null, Function.identity(), String::length);

            // then
            then(result).as("Null source should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty when source is empty")
        void toMap_ShouldReturnEmpty_WhenSourceIsEmpty() {
            // given
            Collection<String> source = List.of();

            // when
            Map<String, Integer> result = CollectionUtils.toMap(source, Function.identity(), String::length);

            // then
            then(result).as("Empty source should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty when keyMapper is null")
        void toMap_ShouldReturnEmpty_WhenKeyMapperIsNull() {
            // given
            Collection<String> source = List.of("a");

            // when
            Map<String, Integer> result = CollectionUtils.toMap(source, null, String::length);

            // then
            then(result).as("Null keyMapper should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty when valueMapper is null")
        void toMap_ShouldReturnEmpty_WhenValueMapperIsNull() {
            // given
            Collection<String> source = List.of("a");

            // when
            Map<String, Integer> result = CollectionUtils.toMap(source, Function.identity(), null);

            // then
            then(result).as("Null valueMapper should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should skip entries whose mapped key is null")
        void toMap_ShouldSkipNullKeys_WhenKeyMapperReturnsNull() {
            // given
            Collection<String> source = List.of("a", "bb", "c");
            Function<String, String> keyFn = s -> s.length() == 1 ? null : s;
            Function<String, Integer> valueFn = String::length;

            // when
            Map<String, Integer> result = CollectionUtils.toMap(source, keyFn, valueFn);

            // then
            then(result).as("Null key entries should be skipped").containsOnlyKeys("bb");
            then(result).as("Null key should not exist").doesNotContainKey(null);
        }

        @Test
        @DisplayName("Should keep last value when duplicate keys produced")
        void toMap_ShouldKeepLastValue_WhenDuplicateKeys() {
            // given
            Collection<String> source = List.of("a", "bb", "cc");
            Function<String, Integer> keyFn = String::length;
            Function<String, String> valueFn = Function.identity();

            // when
            Map<Integer, String> result = CollectionUtils.toMap(source, keyFn, valueFn);

            // then
            then(result).as("Duplicate length keys should keep last").hasSize(2);
            then(result).as("Key 2 should be 'cc' (last wins)").containsEntry(2, "cc");
            then(result).as("Key 1 should be 'a'").containsEntry(1, "a");
        }

        @Test
        @DisplayName("Should allow null values from valueMapper")
        void toMap_ShouldAllowNullValues_WhenValueMapperReturnsNull() {
            // given
            Collection<String> source = List.of("a", "bb");
            Function<String, String> keyFn = Function.identity();
            Function<String, Integer> valueFn = s -> s.length() > 1 ? null : s.length();

            // when
            Map<String, Integer> result = CollectionUtils.toMap(source, keyFn, valueFn);

            // then
            then(result).as("Null value should be kept").containsKey("bb");
            then(result.get("bb")).as("Value should be null").isNull();
            then(result).as("Normal entry present").containsEntry("a", 1);
        }

        @Test
        @DisplayName("Should return modifiable map")
        void toMap_ShouldReturnModifiableMap_WhenValid() {
            // given
            Collection<String> source = List.of("a");

            // when
            Map<String, Integer> result = CollectionUtils.toMap(source, Function.identity(), String::length);

            // then
            result.put("b", 2);
            then(result).as("Result should be modifiable").containsEntry("b", 2);
        }

        @Test
        @DisplayName("Should produce map of expected size when all keys unique")
        void toMap_ShouldProduceExpectedSize_WhenKeysUnique() {
            // given
            Collection<String> source = List.of("a", "bb", "ccc");
            Function<String, String> keyFn = Function.identity();
            Function<String, Integer> valueFn = String::length;

            // when
            Map<String, Integer> result = CollectionUtils.toMap(source, keyFn, valueFn);

            // then
            then(result).as("All unique keys should map").hasSize(3);
            then(result).containsEntry("a", 1).containsEntry("bb", 2).containsEntry("ccc", 3);
        }
    }

    @Nested
    @DisplayName("invertMap")
    class InvertMap {

        @Test
        @DisplayName("Should return empty when map is empty")
        void invertMap_ShouldReturnEmpty_WhenMapIsEmpty() {
            // given
            Map<String, Integer> source = Map.of();

            // when
            Map<Integer, String> result = CollectionUtils.invertMap(source);

            // then
            then(result).as("Empty map should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should invert single-entry map")
        void invertMap_ShouldInvert_WhenSingleEntry() {
            // given
            Map<String, Integer> source = Map.of("a", 1);

            // when
            Map<Integer, String> result = CollectionUtils.invertMap(source);

            // then
            then(result).as("Single entry inverted").containsExactly(Map.entry(1, "a"));
        }

        @Test
        @DisplayName("Should keep only last key for duplicate values")
        void invertMap_ShouldKeepLastKey_WhenDuplicateValues() {
            // given
            Map<String, Integer> source = new LinkedHashMap<>();
            source.put("a", 1);
            source.put("b", 1);
            source.put("c", 2);

            // when
            Map<Integer, String> result = CollectionUtils.invertMap(source);

            // then
            then(result).as("Duplicate value should keep one entry").hasSize(2);
            then(result).as("Value 1 should be mapped to last key 'b'").containsEntry(1, "b");
            then(result).as("Value 2 should be mapped to 'c'").containsEntry(2, "c");
        }

        @Test
        @DisplayName("Should skip entries with null values")
        void invertMap_ShouldSkipNullValues_WhenPresent() {
            // given
            Map<String, Integer> source = new HashMap<>();
            source.put("a", 1);
            source.put("b", null);

            // when
            Map<Integer, String> result = CollectionUtils.invertMap(source);

            // then
            then(result).as("Null values should be skipped").containsOnlyKeys(1);
            then(result).as("Only non-null value mapped").containsEntry(1, "a");
        }

        @Test
        @DisplayName("Should return empty when all values are null")
        void invertMap_ShouldReturnEmpty_WhenAllValuesNull() {
            // given
            Map<String, Integer> source = new HashMap<>();
            source.put("a", null);
            source.put("b", null);

            // when
            Map<Integer, String> result = CollectionUtils.invertMap(source);

            // then
            then(result).as("All null values should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should invert map with null key")
        void invertMap_ShouldHandleNullKey_WhenPresent() {
            // given
            Map<String, Integer> source = new HashMap<>();
            source.put(null, 1);
            source.put("b", 2);

            // when
            Map<Integer, String> result = CollectionUtils.invertMap(source);

            // then
            then(result).as("Null key should become a value").containsEntry(1, null);
            then(result).as("Non-null entry should be present").containsEntry(2, "b");
        }

        @Test
        @DisplayName("Should use equals for value comparison, not identity")
        void invertMap_ShouldUseEqualsForValues_WhenCustomObjects() {
            // given
            Map<String, Point> source = new LinkedHashMap<>();
            source.put("first", new Point(1, 2));
            source.put("second", new Point(1, 2)); // equal value

            // when
            Map<Point, String> result = CollectionUtils.invertMap(source);

            // then
            then(result).as("Equal values should be deduplicated").hasSize(1);
            then(result).as("Last key should win").containsEntry(new Point(1, 2), "second");
        }

        @Test
        @DisplayName("Should preserve all entries when values unique")
        void invertMap_ShouldPreserveAllEntries_WhenValuesUnique() {
            // given
            Map<String, Integer> source = new LinkedHashMap<>();
            source.put("a", 1);
            source.put("b", 2);
            source.put("c", 3);

            // when
            Map<Integer, String> result = CollectionUtils.invertMap(source);

            // then
            then(result).as("All entries should be inverted").hasSize(3);
            then(result).containsEntry(1, "a").containsEntry(2, "b").containsEntry(3, "c");
        }
    }

    @Nested
    @DisplayName("isDisjoint")
    class IsDisjoint {

        @Test
        @DisplayName("Should return true when first collection is null")
        void isDisjoint_ShouldReturnTrue_WhenFirstIsNull() {
            // given

            // when
            boolean result = CollectionUtils.isDisjoint(null, List.of("a"));

            // then
            then(result).as("Null first should be disjoint").isTrue();
        }

        @Test
        @DisplayName("Should return true when second collection is null")
        void isDisjoint_ShouldReturnTrue_WhenSecondIsNull() {
            // given

            // when
            boolean result = CollectionUtils.isDisjoint(List.of("a"), null);

            // then
            then(result).as("Null second should be disjoint").isTrue();
        }

        @Test
        @DisplayName("Should return true when first collection is empty")
        void isDisjoint_ShouldReturnTrue_WhenFirstIsEmpty() {
            // given
            Collection<String> a = List.of();
            Collection<String> b = List.of("a", "b");

            // when
            boolean result = CollectionUtils.isDisjoint(a, b);

            // then
            then(result).as("Empty first should be disjoint").isTrue();
        }

        @Test
        @DisplayName("Should return true when second collection is empty")
        void isDisjoint_ShouldReturnTrue_WhenSecondIsEmpty() {
            // given
            Collection<String> a = List.of("a", "b");
            Collection<String> b = List.of();

            // when
            boolean result = CollectionUtils.isDisjoint(a, b);

            // then
            then(result).as("Empty second should be disjoint").isTrue();
        }

        @Test
        @DisplayName("Should return false when first is larger and has common element")
        void isDisjoint_ShouldReturnFalse_WhenFirstIsLargerAndCommonElement() {
            // given
            Collection<String> a = List.of("a", "b", "c", "d");
            Collection<String> b = List.of("c");

            // when
            boolean result = CollectionUtils.isDisjoint(a, b);

            // then
            then(result).as("Common element should yield false even when first is larger").isFalse();
        }

        @Test
        @DisplayName("Should return true when first is larger and no common elements")
        void isDisjoint_ShouldReturnTrue_WhenFirstIsLargerAndNoCommonElement() {
            // given
            Collection<String> a = List.of("a", "b", "c", "d");
            Collection<String> b = List.of("x", "y");

            // when
            boolean result = CollectionUtils.isDisjoint(a, b);

            // then
            then(result).as("No common elements should yield true").isTrue();
        }

        @Test
        @DisplayName("Should handle set as larger collection efficiently")
        void isDisjoint_ShouldWork_WhenLargerIsSet() {
            // given
            Collection<String> a = Set.of("a", "b", "c");
            Collection<String> b = List.of("c", "d");

            // when
            boolean result = CollectionUtils.isDisjoint(a, b);

            // then
            then(result).as("Set larger with common element should yield false").isFalse();
        }

        @Test
        @DisplayName("Should handle set as smaller collection")
        void isDisjoint_ShouldWork_WhenSmallerIsSet() {
            // given
            Collection<String> a = Set.of("a");
            Collection<String> b = List.of("a", "b", "c", "d");

            // when
            boolean result = CollectionUtils.isDisjoint(a, b);

            // then
            then(result).as("Set smaller with common element should yield false").isFalse();
        }

        @Test
        @DisplayName("Should handle both as sets")
        void isDisjoint_ShouldWork_WhenBothAreSets() {
            // given
            Collection<String> a = Set.of("a", "b");
            Collection<String> b = Set.of("b", "c");

            // when
            boolean result = CollectionUtils.isDisjoint(a, b);

            // then
            then(result).as("Intersecting sets should yield false").isFalse();
        }

        @Test
        @DisplayName("Should correctly compare null elements")
        void isDisjoint_ShouldHandleNullElements_WhenPresentInBoth() {
            // given
            Collection<String> a = new ArrayList<>();
            a.add(null);
            a.add("a");
            Collection<String> b = new ArrayList<>();
            b.add(null);
            b.add("x");

            // when
            boolean result = CollectionUtils.isDisjoint(a, b);

            // then
            then(result).as("Null element common to both should yield false").isFalse();
        }

        @Test
        @DisplayName("Should return true when null only exists in one collection")
        void isDisjoint_ShouldReturnTrue_WhenNullOnlyInOne() {
            // given
            Collection<String> a = new ArrayList<>();
            a.add(null);
            Collection<String> b = List.of("x", "y");

            // when
            boolean result = CollectionUtils.isDisjoint(a, b);

            // then
            then(result).as("Null not shared should yield true").isTrue();
        }

        @Test
        @DisplayName("Should use equals for comparison, not identity")
        void isDisjoint_ShouldUseEquals_WhenComparingStrings() {
            // given
            Collection<String> a = List.of("abc");
            Collection<String> b = List.of("abc");

            // when
            boolean result = CollectionUtils.isDisjoint(a, b);

            // then
            then(result).as("Equal strings by value should be considered common").isFalse();
        }
    }

    @Nested
    @DisplayName("removeNulls")
    class RemoveNulls {

        @Test
        @DisplayName("Should remove null elements")
        void removeNulls_ShouldRemoveNulls_WhenPresent() {
            // given
            Collection<String> source = new ArrayList<>();
            source.add("a");
            source.add(null);
            source.add("b");
            source.add(null);

            // when
            Collection<String> result = CollectionUtils.removeNulls(source);

            // then
            then(result).as("Nulls removed").containsExactly("a", "b");
        }

        @Test
        @DisplayName("Should return empty when source is null")
        void removeNulls_ShouldReturnEmpty_WhenSourceIsNull() {
            // given

            // when
            Collection<String> result = CollectionUtils.removeNulls(null);

            // then
            then(result).as("Null source should yield empty").isEmpty();
        }
    }

    @Nested
    @DisplayName("retainAll")
    class RetainAll {

        @Test
        @DisplayName("Should return empty when source is null")
        void retainAll_ShouldReturnEmpty_WhenSourceIsNull() {
            // given

            // when
            List<String> result = CollectionUtils.retainAll(null, List.of("a"));

            // then
            then(result).as("Null source should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty when candidates is null")
        void retainAll_ShouldReturnEmpty_WhenCandidatesIsNull() {
            // given
            Collection<String> source = List.of("a");

            // when
            List<String> result = CollectionUtils.retainAll(source, null);

            // then
            then(result).as("Null candidates should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty when source is empty")
        void retainAll_ShouldReturnEmpty_WhenSourceIsEmpty() {
            // given
            Collection<String> source = List.of();

            // when
            List<String> result = CollectionUtils.retainAll(source, List.of("a"));

            // then
            then(result).as("Empty source should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty when candidates is empty")
        void retainAll_ShouldReturnEmpty_WhenCandidatesIsEmpty() {
            // given
            Collection<String> source = List.of("a");

            // when
            List<String> result = CollectionUtils.retainAll(source, List.of());

            // then
            then(result).as("Empty candidates should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should preserve cardinality and order of duplicates in source")
        void retainAll_ShouldPreserveCardinality_WhenSourceHasDuplicates() {
            // given
            Collection<String> source = List.of("a", "b", "a", "c", "a");
            Collection<String> candidates = List.of("a", "c");

            // when
            List<String> result = CollectionUtils.retainAll(source, candidates);

            // then
            then(result).as("All occurrences in source that are in candidates should remain in order")
                    .containsExactly("a", "a", "c", "a");
        }

        @Test
        @DisplayName("Should handle candidates as Set efficiently")
        void retainAll_ShouldWork_WhenCandidatesIsSet() {
            // given
            Collection<String> source = List.of("a", "b", "c");
            Collection<String> candidates = Set.of("a", "c");

            // when
            List<String> result = CollectionUtils.retainAll(source, candidates);

            // then
            then(result).as("Set candidates should be handled").containsExactly("a", "c");
        }

        @Test
        @DisplayName("Should return all source elements when all present in candidates")
        void retainAll_ShouldReturnAllSource_WhenAllPresentInCandidates() {
            // given
            Collection<String> source = List.of("a", "b", "c");
            Collection<String> candidates = List.of("a", "b", "c", "d");

            // when
            List<String> result = CollectionUtils.retainAll(source, candidates);

            // then
            then(result).as("All source elements should remain").containsExactly("a", "b", "c");
        }

        @Test
        @DisplayName("Should preserve null elements in source when candidates contain null")
        void retainAll_ShouldPreserveNulls_WhenCandidatesContainNull() {
            // given
            Collection<String> source = new ArrayList<>();
            source.add(null);
            source.add("a");
            source.add(null);
            Collection<String> candidates = new ArrayList<>();
            candidates.add(null);
            candidates.add("a");

            // when
            List<String> result = CollectionUtils.retainAll(source, candidates);

            // then
            then(result).as("Null elements in source should remain")
                    .containsExactly(null, "a", null);
        }

        @Test
        @DisplayName("Should remove nulls from source when candidates do not contain null")
        void retainAll_ShouldExcludeNulls_WhenCandidatesDoNotContainNull() {
            // given
            Collection<String> source = new ArrayList<>();
            source.add(null);
            source.add("a");
            Collection<String> candidates = List.of("a");

            // when
            List<String> result = CollectionUtils.retainAll(source, candidates);

            // then
            then(result).as("Nulls should be excluded").containsExactly("a");
        }

        @Test
        @DisplayName("Should return modifiable list")
        void retainAll_ShouldReturnModifiableList_WhenValid() {
            // given
            Collection<String> source = List.of("a", "b");
            Collection<String> candidates = List.of("a");

            // when
            List<String> result = CollectionUtils.retainAll(source, candidates);

            // then
            result.add("z");
            then(result).as("Result should be modifiable").containsExactly("a", "z");
        }

        @Test
        @DisplayName("Should not modify the source collection")
        void retainAll_ShouldNotModifySource_WhenValid() {
            // given
            List<String> source = new ArrayList<>(List.of("a", "b", "c"));
            Collection<String> candidates = List.of("a", "c");

            // when
            CollectionUtils.retainAll(source, candidates);

            // then
            then(source).as("Source should remain unchanged").containsExactly("a", "b", "c");
        }
    }

    @Nested
    @DisplayName("cardinality")
    class Cardinality {

        @Test
        @DisplayName("Should count occurrences of a non-null object")
        void cardinality_ShouldCountOccurrences_WhenObjectPresent() {
            // given
            Collection<String> collection = List.of("a", "b", "a", "c", "a");

            // when
            int result = CollectionUtils.cardinality("a", collection);

            // then
            then(result).as("Frequency of a should be 3").isEqualTo(3);
        }

        @Test
        @DisplayName("Should return zero when object is missing")
        void cardinality_ShouldReturnZero_WhenObjectMissing() {
            // given
            Collection<String> collection = List.of("a", "b");

            // when
            int result = CollectionUtils.cardinality("z", collection);

            // then
            then(result).as("Missing object frequency should be 0").isEqualTo(0);
        }

        @Test
        @DisplayName("Should return zero when collection is null")
        void cardinality_ShouldReturnZero_WhenCollectionIsNull() {
            // given

            // when
            int result = CollectionUtils.cardinality("a", null);

            // then
            then(result).as("Null collection should yield 0").isEqualTo(0);
        }

        @Test
        @DisplayName("Should return zero when collection is empty")
        void cardinality_ShouldReturnZero_WhenCollectionIsEmpty() {
            // given
            Collection<String> collection = List.of();

            // when
            int result = CollectionUtils.cardinality("a", collection);

            // then
            then(result).as("Empty collection should yield 0").isEqualTo(0);
        }

        @Test
        @DisplayName("Should count null occurrences when object is null")
        void cardinality_ShouldCountNulls_WhenObjectIsNull() {
            // given
            Collection<String> collection = new ArrayList<>();
            collection.add(null);
            collection.add("a");
            collection.add(null);
            collection.add(null);

            // when
            int result = CollectionUtils.cardinality(null, collection);

            // then
            then(result).as("Null occurrences should be 3").isEqualTo(3);
        }

        @Test
        @DisplayName("Should return zero when object is null and collection has no nulls")
        void cardinality_ShouldReturnZero_WhenObjectNullAndNoNulls() {
            // given
            Collection<String> collection = List.of("a", "b");

            // when
            int result = CollectionUtils.cardinality(null, collection);

            // then
            then(result).as("No nulls should yield 0").isEqualTo(0);
        }

        @Test
        @DisplayName("Should use equals for comparison, not identity")
        void cardinality_ShouldUseEquals_WhenCustomEquals() {
            // given
            Collection<Point> collection = List.of(
                    new Point(1, 2),
                    new Point(3, 4),
                    new Point(1, 2));

            // when
            int result = CollectionUtils.cardinality(new Point(1, 2), collection);

            // then
            then(result).as("Two equal points should be counted").isEqualTo(2);
        }

        @Test
        @DisplayName("Should work correctly with Set input")
        void cardinality_ShouldReturnAtMostOne_WhenCollectionIsSet() {
            // given
            Collection<String> collection = Set.of("a", "b");

            // when
            int result = CollectionUtils.cardinality("a", collection);

            // then
            then(result).as("Set can contain at most one occurrence").isEqualTo(1);
        }

        @Test
        @DisplayName("Should count strings by value equality")
        void cardinality_ShouldCountStringsByValue_WhenDifferentInstances() {
            // given
            String a1 = "x";
            String a2 = "x";
            Collection<String> collection = List.of(a1, a2, "y");

            // when
            int result = CollectionUtils.cardinality("x", collection);

            // then
            then(result).as("Both 'x' instances should be counted").isEqualTo(2);
        }
    }

    @Nested
    @DisplayName("collate(Collection, Collection)")
    class Collate {

        @Test
        @DisplayName("Should collate two collections using natural order")
        void collate_ShouldMergeSorted_WhenNaturalOrder() {
            // given
            Collection<Integer> source = List.of(1, 3);
            Collection<Integer> candidates = List.of(2, 4);

            // when
            List<Integer> result = CollectionUtils.collate(source, candidates);

            // then
            then(result).as("Collated should be sorted").containsExactly(1, 2, 3, 4);
        }
    }

    @Nested
    @DisplayName("collate(Collection, Collection, Comparator)")
    class CollateWithComparator {

        @Test
        @DisplayName("Should return copy of b when a is empty")
        void collate_ShouldReturnCopyOfB_WhenAIsEmpty() {
            // given
            Collection<Integer> a = List.of();
            Collection<Integer> b = List.of(3, 1, 2);
            Comparator<Integer> comparator = Integer::compareTo;

            // when
            List<Integer> result = CollectionUtils.collate(a, b, comparator);

            // then
            then(result).as("Empty a should return b contents in original order")
                    .containsExactly(3, 1, 2);
            result.add(99);
            then(result).as("Result should be modifiable").contains(99);
        }

        @Test
        @DisplayName("Should return copy of a when b is empty")
        void collate_ShouldReturnCopyOfA_WhenBIsEmpty() {
            // given
            Collection<Integer> a = List.of(2, 1, 3);
            Collection<Integer> b = List.of();
            Comparator<Integer> comparator = Integer::compareTo;

            // when
            List<Integer> result = CollectionUtils.collate(a, b, comparator);

            // then
            then(result).as("Empty b should return a contents in original order")
                    .containsExactly(2, 1, 3);
            result.add(99);
            then(result).as("Result should be modifiable").contains(99);
        }

        @Test
        @DisplayName("Should fall back to natural order when comparator is null")
        void collate_ShouldUseNaturalOrder_WhenComparatorIsNull() {
            // given
            Collection<Integer> a = List.of(1, 3, 5);
            Collection<Integer> b = List.of(2, 4, 6);

            // when
            List<Integer> result = CollectionUtils.collate(a, b, null);

            // then
            then(result).as("Natural order merge expected").containsExactly(1, 2, 3, 4, 5, 6);
        }

        @Test
        @DisplayName("Should collate with descending comparator")
        void collate_ShouldCollateDescending_WhenComparatorReversed() {
            // given
            Collection<Integer> a = List.of(5, 3, 1);
            Collection<Integer> b = List.of(6, 4, 2);
            Comparator<Integer> comparator = Comparator.reverseOrder();

            // when
            List<Integer> result = CollectionUtils.collate(a, b, comparator);

            // then
            then(result).as("Descending merge expected").containsExactly(6, 5, 4, 3, 2, 1);
        }

        @Test
        @DisplayName("Should prefer items from a when elements are equal")
        void collate_ShouldPreferA_WhenElementsEqual() {
            // given
            Collection<Integer> a = List.of(2);
            Collection<Integer> b = List.of(2);
            Comparator<Integer> comparator = Integer::compareTo;

            // when
            List<Integer> result = CollectionUtils.collate(a, b, comparator);

            // then
            then(result).as("Both equal elements should appear").containsExactly(2, 2);
        }

        @Test
        @DisplayName("Should preserve stability for equal elements within each input")
        void collate_ShouldPreserveStability_WhenDuplicatesPresent() {
            // given
            Collection<Integer> a = List.of(1, 3);
            Collection<Integer> b = List.of(1, 2);
            Comparator<Integer> comparator = Integer::compareTo;

            // when
            List<Integer> result = CollectionUtils.collate(a, b, comparator);

            // then
            then(result).as("Merged sorted result").containsExactly(1, 1, 2, 3);
            then(result.get(0)).as("First 1 should come from a").isEqualTo(1);
            then(result.get(1)).as("Second 1 should come from b").isEqualTo(1);
        }

        @Test
        @DisplayName("Should handle collections of unequal sizes")
        void collate_ShouldHandleUnequalSizes_WhenValid() {
            // given
            Collection<Integer> a = List.of(1);
            Collection<Integer> b = List.of(2, 3, 4, 5);
            Comparator<Integer> comparator = Integer::compareTo;

            // when
            List<Integer> result = CollectionUtils.collate(a, b, comparator);

            // then
            then(result).as("All elements should be merged in order")
                    .containsExactly(1, 2, 3, 4, 5);
        }

        @Test
        @DisplayName("Should place all a-elements before all b-elements when a smaller")
        void collate_ShouldPlaceAllAFirst_WhenAllASmaller() {
            // given
            Collection<Integer> a = List.of(1, 2);
            Collection<Integer> b = List.of(10, 20, 30);
            Comparator<Integer> comparator = Integer::compareTo;

            // when
            List<Integer> result = CollectionUtils.collate(a, b, comparator);

            // then
            then(result).as("All a elements first, then b").containsExactly(1, 2, 10, 20, 30);
        }

        @Test
        @DisplayName("Should place all b-elements before all a-elements when b smaller")
        void collate_ShouldPlaceAllBFirst_WhenAllBSmaller() {
            // given
            Collection<Integer> a = List.of(10, 20);
            Collection<Integer> b = List.of(1, 2, 3);
            Comparator<Integer> comparator = Integer::compareTo;

            // when
            List<Integer> result = CollectionUtils.collate(a, b, comparator);

            // then
            then(result).as("All b elements first, then a").containsExactly(1, 2, 3, 10, 20);
        }

        @Test
        @DisplayName("Should collate custom-typed elements with custom comparator")
        void collate_ShouldHandleCustomComparator_WhenCustomType() {
            // given
            Collection<Person> a = List.of(new Person("A", 20), new Person("C", 40));
            Collection<Person> b = List.of(new Person("B", 30), new Person("D", 50));
            Comparator<Person> comparator = Comparator.comparingInt(Person::age);

            // when
            List<Person> result = CollectionUtils.collate(a, b, comparator);

            // then
            then(result).as("Merged by age")
                    .extracting(Person::name)
                    .containsExactly("A", "B", "C", "D");
        }

        @Test
        @DisplayName("Should return empty when both are null")
        void collate_ShouldReturnEmpty_WhenBothNull() {
            // given
            Collection<Integer> a = null;
            Collection<Integer> b = null;
            Comparator<Integer> comparator = Integer::compareTo;

            // when
            List<Integer> result = CollectionUtils.collate(a, b, comparator);

            // then
            then(result).as("Both null → List.of()").isEmpty();
        }

        @Test
        @DisplayName("Should return empty when first is null and second is empty")
        void collate_ShouldReturnEmpty_WhenFirstNullSecondEmpty() {
            // given
            Collection<Integer> a = null;
            Collection<Integer> b = List.of();
            Comparator<Integer> comparator = Integer::compareTo;

            // when
            List<Integer> result = CollectionUtils.collate(a, b, comparator);

            // then
            then(result).as("Null first + empty second → both empty → List.of()").isEmpty();
        }

        @Test
        @DisplayName("Should return empty when first is empty and second is null")
        void collate_ShouldReturnEmpty_WhenFirstEmptySecondNull() {
            // given
            Collection<Integer> a = List.of();
            Collection<Integer> b = null;
            Comparator<Integer> comparator = Integer::compareTo;

            // when
            List<Integer> result = CollectionUtils.collate(a, b, comparator);

            // then
            then(result).as("Empty first + null second → both empty → List.of()").isEmpty();
        }

        @Test
        @DisplayName("Should return empty when both are empty")
        void collate_ShouldReturnEmpty_WhenBothEmpty() {
            // given
            Collection<Integer> a = List.of();
            Collection<Integer> b = List.of();
            Comparator<Integer> comparator = Integer::compareTo;

            // when
            List<Integer> result = CollectionUtils.collate(a, b, comparator);

            // then
            then(result).as("Both empty → List.of()").isEmpty();
        }

        @Test
        @DisplayName("Should return copy of second when first is null")
        void collate_ShouldReturnCopyOfB_WhenFirstNull() {
            // given
            Collection<Integer> a = null;
            Collection<Integer> b = List.of(3, 1, 2);
            Comparator<Integer> comparator = Integer::compareTo;

            // when
            List<Integer> result = CollectionUtils.collate(a, b, comparator);

            // then
            then(result).as("Null first → copy of b (isEmpty(a) branch)").containsExactly(3, 1, 2);
        }

        @Test
        @DisplayName("Should return copy of first when second is null")
        void collate_ShouldReturnCopyOfA_WhenSecondNull() {
            // given
            Collection<Integer> a = List.of(1, 2, 3);
            Collection<Integer> b = null;
            Comparator<Integer> comparator = Integer::compareTo;

            // when
            List<Integer> result = CollectionUtils.collate(a, b, comparator);

            // then
            then(result).as("Null second → copy of a (isEmpty(b) branch)").containsExactly(1, 2, 3);
        }
    }

    @Nested
    @DisplayName("zip")
    class Zip {

        @Test
        @DisplayName("Should return empty when first collection is null")
        void zip_ShouldReturnEmpty_WhenFirstIsNull() {
            // given

            // when
            List<Map.Entry<String, Integer>> result = CollectionUtils.zip(null, List.of(1, 2));

            // then
            then(result).as("Null first should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty when first collection is empty")
        void zip_ShouldReturnEmpty_WhenFirstIsEmpty() {
            // given
            Collection<String> keys = List.of();
            Collection<Integer> values = List.of(1, 2);

            // when
            List<Map.Entry<String, Integer>> result = CollectionUtils.zip(keys, values);

            // then
            then(result).as("Empty first should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty when second collection is null")
        void zip_ShouldReturnEmpty_WhenSecondIsNull() {
            // given

            // when
            List<Map.Entry<String, Integer>> result = CollectionUtils.zip(List.of("a"), null);

            // then
            then(result).as("Null second should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should zip to shorter length when first is longer")
        void zip_ShouldUseShorterLength_WhenFirstIsLonger() {
            // given
            Collection<String> keys = List.of("a", "b", "c");
            Collection<Integer> values = List.of(1, 2);

            // when
            List<Map.Entry<String, Integer>> result = CollectionUtils.zip(keys, values);

            // then
            then(result).as("Shorter length should be used").hasSize(2);
            then(result.get(0).getKey()).isEqualTo("a");
            then(result.get(0).getValue()).isEqualTo(1);
            then(result.get(1).getKey()).isEqualTo("b");
            then(result.get(1).getValue()).isEqualTo(2);
        }

        @Test
        @DisplayName("Should zip to shorter length when second is longer")
        void zip_ShouldUseShorterLength_WhenSecondIsLonger() {
            // given
            Collection<String> keys = List.of("a");
            Collection<Integer> values = List.of(1, 2, 3);

            // when
            List<Map.Entry<String, Integer>> result = CollectionUtils.zip(keys, values);

            // then
            then(result).as("Shorter length should be used").hasSize(1);
            then(result.getFirst().getKey()).isEqualTo("a");
            then(result.getFirst().getValue()).isEqualTo(1);
        }

        @Test
        @DisplayName("Should zip single-element collections")
        void zip_ShouldHandleSingleElements_WhenBothSingle() {
            // given
            Collection<String> keys = List.of("a");
            Collection<Integer> values = List.of(1);

            // when
            List<Map.Entry<String, Integer>> result = CollectionUtils.zip(keys, values);

            // then
            then(result).as("Single pair expected").hasSize(1);
            then(result.getFirst().getKey()).isEqualTo("a");
            then(result.getFirst().getValue()).isEqualTo(1);
        }

        @Test
        @DisplayName("Should preserve null elements in entries")
        void zip_ShouldPreserveNullElements_WhenPresent() {
            // given
            Collection<String> keys = new ArrayList<>();
            keys.add("a");
            keys.add(null);
            Collection<Integer> values = new ArrayList<>();
            values.add(null);
            values.add(2);

            // when
            List<Map.Entry<String, Integer>> result = CollectionUtils.zip(keys, values);

            // then
            then(result).as("Two pairs expected").hasSize(2);
            then(result.get(0).getKey()).isEqualTo("a");
            then(result.get(0).getValue()).as("Null value should be preserved").isNull();
            then(result.get(1).getKey()).as("Null key should be preserved").isNull();
            then(result.get(1).getValue()).isEqualTo(2);
        }

        @Test
        @DisplayName("Should return immutable entries")
        void zip_ShouldReturnImmutableEntries_WhenValid() {
            // given
            Collection<String> keys = List.of("a");
            Collection<Integer> values = List.of(1);

            // when
            List<Map.Entry<String, Integer>> result = CollectionUtils.zip(keys, values);

            // then
            thenThrownBy(() -> result.getFirst().setValue(99))
                    .as("Entries should be immutable")
                    .isInstanceOf(UnsupportedOperationException.class);
        }
    }

    @Nested
    @DisplayName("flatten")
    class Flatten {

        @Test
        @DisplayName("Should return empty when nested collection is empty")
        void flatten_ShouldReturnEmpty_WhenNestedCollectionIsEmpty() {
            // given
            Collection<Collection<String>> source = List.of();

            // when
            List<String> result = CollectionUtils.flatten(source);

            // then
            then(result).as("Empty outer collection should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should skip null and empty inner collections")
        void flatten_ShouldSkipNullAndEmptyInner_WhenPresent() {
            // given
            Collection<Collection<String>> source = new ArrayList<>();
            source.add(List.of("a"));
            source.add(List.of());
            source.add(null);
            source.add(List.of("b"));

            // when
            List<String> result = CollectionUtils.flatten(source);

            // then
            then(result).as("Only non-empty inner elements").containsExactly("a", "b");
        }

        @Test
        @DisplayName("Should return empty when all inner collections are empty or null")
        void flatten_ShouldReturnEmpty_WhenAllInnerEmptyOrNull() {
            // given
            Collection<Collection<String>> source = new ArrayList<>();
            source.add(List.of());
            source.add(null);
            source.add(List.of());

            // when
            List<String> result = CollectionUtils.flatten(source);

            // then
            then(result).as("All inner empty/null should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should preserve order of inner collections and elements")
        void flatten_ShouldPreserveOrder_WhenMultipleInner() {
            // given
            Collection<Collection<String>> source = List.of(
                    List.of("a", "b"),
                    List.of("c"),
                    List.of("d", "e"));

            // when
            List<String> result = CollectionUtils.flatten(source);

            // then
            then(result).as("Order should be preserved").containsExactly("a", "b", "c", "d", "e");
        }

        @Test
        @DisplayName("Should handle single inner collection")
        void flatten_ShouldHandleSingleInner_WhenOneElement() {
            // given
            Collection<Collection<String>> source = List.of(List.of("a", "b", "c"));

            // when
            List<String> result = CollectionUtils.flatten(source);

            // then
            then(result).as("Single inner should be flattened").containsExactly("a", "b", "c");
        }

        @Test
        @DisplayName("Should preserve null elements inside inner collections")
        void flatten_ShouldPreserveNullElements_WhenInnerHasNulls() {
            // given
            Collection<Collection<String>> source = new ArrayList<>();
            List<String> inner = new ArrayList<>();
            inner.add("a");
            inner.add(null);
            inner.add("b");
            source.add(inner);

            // when
            List<String> result = CollectionUtils.flatten(source);

            // then
            then(result).as("Null elements inside inner should be preserved").containsExactly("a", null, "b");
        }

        @Test
        @DisplayName("Should return new modifiable list independent of input")
        void flatten_ShouldReturnNewModifiableList_WhenValid() {
            // given
            Collection<Collection<String>> source = List.of(List.of("a"));

            // when
            List<String> result = CollectionUtils.flatten(source);

            // then
            result.add("b");
            then(result).as("Result should be modifiable").containsExactly("a", "b");
            then(source).as("Source should be unaffected").containsExactly(List.of("a"));
        }
    }

    @Nested
    @DisplayName("toMultiMap")
    class ToMultiMap {

        @Test
        @DisplayName("Should group elements by key function")
        void toMultiMap_ShouldGroup_WhenFunctionProvided() {
            // given
            Collection<String> source = List.of("a", "bb", "c", "dd");
            Function<String, Integer> keyFn = String::length;

            // when
            Map<Integer, List<String>> result = CollectionUtils.toMultiMap(source, keyFn);

            // then
            then(result).as("Grouped by length").containsKey(1).containsKey(2);
            then(result.get(1)).as("Length 1").containsExactlyInAnyOrder("a", "c");
        }
    }

    @Nested
    @DisplayName("addAll")
    class AddAll {

        @Test
        @DisplayName("Should return false when source is null")
        void addAll_ShouldReturnFalse_WhenSourceIsNull() {
            // given
            Collection<String> candidates = List.of("a");

            // when
            boolean result = CollectionUtils.addAll(null, candidates);

            // then
            then(result).as("Null source should yield false").isFalse();
        }

        @Test
        @DisplayName("Should return false when candidates is null")
        void addAll_ShouldReturnFalse_WhenCandidatesIsNull() {
            // given
            Collection<String> target = new ArrayList<>();

            // when
            boolean result = CollectionUtils.addAll(target, null);

            // then
            then(result).as("Null candidates should yield false").isFalse();
            then(target).as("Target should remain empty").isEmpty();
        }

        @Test
        @DisplayName("Should return false when candidates is empty")
        void addAll_ShouldReturnFalse_WhenCandidatesIsEmpty() {
            // given
            Collection<String> target = new ArrayList<>(List.of("a"));
            Collection<String> candidates = List.of();

            // when
            boolean result = CollectionUtils.addAll(target, candidates);

            // then
            then(result).as("Empty candidates should yield false").isFalse();
            then(target).as("Target should remain unchanged").containsExactly("a");
        }

        @Test
        @DisplayName("Should return false when all candidates already present in target set")
        void addAll_ShouldReturnFalse_WhenAllAlreadyPresentInSet() {
            // given
            Collection<String> target = new HashSet<>(Set.of("a", "b"));
            Collection<String> candidates = List.of("a", "b");

            // when
            boolean result = CollectionUtils.addAll(target, candidates);

            // then
            then(result).as("No new elements should yield false").isFalse();
            then(target).as("Target should remain unchanged").containsExactlyInAnyOrder("a", "b");
        }

        @Test
        @DisplayName("Should return true when at least one new element added to set")
        void addAll_ShouldReturnTrue_WhenAtLeastOneNewElementInSet() {
            // given
            Collection<String> target = new HashSet<>(Set.of("a"));
            Collection<String> candidates = List.of("a", "b");

            // when
            boolean result = CollectionUtils.addAll(target, candidates);

            // then
            then(result).as("New element should yield true").isTrue();
            then(target).as("New element should be added").containsExactlyInAnyOrder("a", "b");
        }

        @Test
        @DisplayName("Should add all elements to empty target")
        void addAll_ShouldAddAll_WhenTargetEmpty() {
            // given
            Collection<String> target = new ArrayList<>();
            Collection<String> candidates = List.of("a", "b", "c");

            // when
            boolean result = CollectionUtils.addAll(target, candidates);

            // then
            then(result).as("Additions should yield true").isTrue();
            then(target).as("All elements should be added").containsExactly("a", "b", "c");
        }

        @Test
        @DisplayName("Should add null elements from candidates to list target")
        void addAll_ShouldIncludeNullElements_WhenListTarget() {
            // given
            Collection<String> target = new ArrayList<>();
            Collection<String> candidates = new ArrayList<>();
            candidates.add("a");
            candidates.add(null);
            candidates.add("b");

            // when
            boolean result = CollectionUtils.addAll(target, candidates);

            // then
            then(result).as("Additions including null should yield true").isTrue();
            then(target).as("Null elements should also be added").containsExactly("a", null, "b");
        }

        @Test
        @DisplayName("Should throw UnsupportedOperationException when source is unmodifiable")
        void addAll_ShouldThrow_WhenSourceUnmodifiable() {
            // given
            Collection<String> target = List.of("a");
            Collection<String> candidates = List.of("b");

            // when & then
            thenThrownBy(() -> CollectionUtils.addAll(target, candidates))
                    .as("Unmodifiable target should throw")
                    .isInstanceOf(UnsupportedOperationException.class);
        }
    }

    @Nested
    @DisplayName("addAllIgnoreNull")
    class AddAllIgnoreNull {

        @Test
        @DisplayName("Should return false when source is null")
        void addAllIgnoreNull_ShouldReturnFalse_WhenSourceIsNull() {
            // given
            Collection<String> candidates = new ArrayList<>(List.of("a"));

            // when
            boolean result = CollectionUtils.addAllIgnoreNull(null, candidates);

            // then
            then(result).as("Null source should yield false").isFalse();
        }

        @Test
        @DisplayName("Should return false when candidates is empty")
        void addAllIgnoreNull_ShouldReturnFalse_WhenCandidatesIsEmpty() {
            // given
            Collection<String> target = new ArrayList<>(List.of("a"));
            Collection<String> candidates = List.of();

            // when
            boolean result = CollectionUtils.addAllIgnoreNull(target, candidates);

            // then
            then(result).as("Empty candidates should yield false").isFalse();
            then(target).as("Target should be unchanged").containsExactly("a");
        }

        @Test
        @DisplayName("Should return false when candidates contains only nulls")
        void addAllIgnoreNull_ShouldReturnFalse_WhenCandidatesOnlyNulls() {
            // given
            Collection<String> target = new ArrayList<>();
            Collection<String> candidates = new ArrayList<>();
            candidates.add(null);
            candidates.add(null);

            // when
            boolean result = CollectionUtils.addAllIgnoreNull(target, candidates);

            // then
            then(result).as("Only nulls should not change target").isFalse();
            then(target).as("Target should remain empty").isEmpty();
        }

        @Test
        @DisplayName("Should return false when all candidates already present in target set")
        void addAllIgnoreNull_ShouldReturnFalse_WhenAllAlreadyPresent() {
            // given
            Collection<String> target = new HashSet<>(Set.of("a", "b"));
            Collection<String> candidates = List.of("a", "b");

            // when
            boolean result = CollectionUtils.addAllIgnoreNull(target, candidates);

            // then
            then(result).as("No new elements should yield false").isFalse();
            then(target).as("Target should remain unchanged").containsExactlyInAnyOrder("a", "b");
        }

        @Test
        @DisplayName("Should return true when at least one non-null new element added")
        void addAllIgnoreNull_ShouldReturnTrue_WhenAtLeastOneNewElement() {
            // given
            Collection<String> target = new ArrayList<>();
            Collection<String> candidates = new ArrayList<>();
            candidates.add(null);
            candidates.add("a");
            candidates.add(null);

            // when
            boolean result = CollectionUtils.addAllIgnoreNull(target, candidates);

            // then
            then(result).as("At least one addition should yield true").isTrue();
            then(target).as("Only non-null should be added").containsExactly("a");
        }

        @Test
        @DisplayName("Should add all non-null elements to empty target")
        void addAllIgnoreNull_ShouldAddAllNonNull_WhenTargetEmpty() {
            // given
            Collection<String> target = new ArrayList<>();
            Collection<String> candidates = new ArrayList<>();
            candidates.add("a");
            candidates.add(null);
            candidates.add("b");
            candidates.add("c");

            // when
            boolean result = CollectionUtils.addAllIgnoreNull(target, candidates);

            // then
            then(result).as("New elements should yield true").isTrue();
            then(target).as("All non-null added in order").containsExactly("a", "b", "c");
        }

        @Test
        @DisplayName("Should handle duplicates correctly in list target")
        void addAllIgnoreNull_ShouldHandleDuplicates_WhenListTarget() {
            // given
            Collection<String> target = new ArrayList<>();
            Collection<String> candidates = List.of("a", "a", "b");

            // when
            boolean result = CollectionUtils.addAllIgnoreNull(target, candidates);

            // then
            then(result).as("Additions should yield true").isTrue();
            then(target).as("Duplicates should be added since target is a list").containsExactly("a", "a", "b");
        }

        @Test
        @DisplayName("Should not add anything when source is unmodifiable and candidates empty")
        void addAllIgnoreNull_ShouldReturnFalse_WhenUnmodifiableAndCandidatesEmpty() {
            // given
            Collection<String> target = List.of("a");
            Collection<String> candidates = List.of();

            // when
            boolean result = CollectionUtils.addAllIgnoreNull(target, candidates);

            // then
            then(result).as("Empty candidates should short-circuit before add").isFalse();
        }

        @Test
        @DisplayName("Should throw UnsupportedOperationException when source unmodifiable and new non-null present")
        void addAllIgnoreNull_ShouldThrow_WhenSourceUnmodifiableAndNewElement() {
            // given
            Collection<String> target = List.of("a");
            Collection<String> candidates = List.of("b");

            // when & then
            thenThrownBy(() -> CollectionUtils.addAllIgnoreNull(target, candidates))
                    .as("Unmodifiable target with new element should throw")
                    .isInstanceOf(UnsupportedOperationException.class);
        }
    }

    @Nested
    @DisplayName("unmodifiableCollection")
    class UnmodifiableCollection {

        @Test
        @DisplayName("Should return empty immutable collection when null")
        void unmodifiableCollection_ShouldReturnEmpty_WhenNull() {
            // given

            // when
            Collection<String> result = CollectionUtils.unmodifiableCollection(null);

            // then
            then(result).as("Null input should yield empty collection").isEmpty();
            thenThrownBy(() -> result.add("a"))
                    .as("Result should be unmodifiable")
                    .isInstanceOf(UnsupportedOperationException.class);
        }

        @Test
        @DisplayName("Should return empty unmodifiable view when collection is empty")
        void unmodifiableCollection_ShouldReturnEmptyView_WhenEmpty() {
            // given
            Collection<String> source = new ArrayList<>();

            // when
            Collection<String> result = CollectionUtils.unmodifiableCollection(source);

            // then
            then(result).as("Empty source should yield empty view").isEmpty();
            thenThrownBy(() -> result.add("a"))
                    .as("Empty view should be unmodifiable")
                    .isInstanceOf(UnsupportedOperationException.class);
        }

        @Test
        @DisplayName("Should throw UnsupportedOperationException on remove")
        void unmodifiableCollection_ShouldThrowOnRemove_WhenValid() {
            // given
            Collection<String> source = new ArrayList<>(List.of("a"));
            Collection<String> result = CollectionUtils.unmodifiableCollection(source);

            // when & then
            thenThrownBy(() -> result.remove("a"))
                    .as("Remove should be rejected")
                    .isInstanceOf(UnsupportedOperationException.class);
        }

        @Test
        @DisplayName("Should throw UnsupportedOperationException on clear")
        void unmodifiableCollection_ShouldThrowOnClear_WhenValid() {
            // given
            Collection<String> source = new ArrayList<>(List.of("a"));
            Collection<String> result = CollectionUtils.unmodifiableCollection(source);

            // when & then
            thenThrownBy(result::clear)
                    .as("Clear should be rejected")
                    .isInstanceOf(UnsupportedOperationException.class);
        }

        @Test
        @DisplayName("Should throw UnsupportedOperationException on addAll")
        void unmodifiableCollection_ShouldThrowOnAddAll_WhenValid() {
            // given
            Collection<String> source = new ArrayList<>(List.of("a"));
            Collection<String> result = CollectionUtils.unmodifiableCollection(source);

            // when & then
            thenThrownBy(() -> result.addAll(List.of("b", "c")))
                    .as("AddAll should be rejected")
                    .isInstanceOf(UnsupportedOperationException.class);
        }

        @Test
        @DisplayName("Should reflect changes made to the backing collection")
        void unmodifiableCollection_ShouldReflectBackingChanges_WhenModified() {
            // given
            Collection<String> source = new ArrayList<>(List.of("a"));
            Collection<String> result = CollectionUtils.unmodifiableCollection(source);

            // when
            source.add("b");

            // then
            then(result).as("View should reflect changes in backing collection").containsExactly("a", "b");
        }
    }

    @Nested
    @DisplayName("unmodifiableList")
    class UnmodifiableList {

        @Test
        @DisplayName("Should return empty immutable list when null")
        void unmodifiableList_ShouldReturnEmpty_WhenNull() {
            // given

            // when
            List<String> result = CollectionUtils.unmodifiableList(null);

            // then
            then(result).as("Null input should yield empty list").isEmpty();
            thenThrownBy(() -> result.add("a"))
                    .as("Result should be unmodifiable")
                    .isInstanceOf(UnsupportedOperationException.class);
        }

        @Test
        @DisplayName("Should return empty unmodifiable view when list is empty")
        void unmodifiableList_ShouldReturnEmptyView_WhenEmpty() {
            // given
            List<String> source = new ArrayList<>();

            // when
            List<String> result = CollectionUtils.unmodifiableList(source);

            // then
            then(result).as("Empty source should yield empty view").isEmpty();
            thenThrownBy(() -> result.add("a"))
                    .as("Empty view should be unmodifiable")
                    .isInstanceOf(UnsupportedOperationException.class);
        }

        @Test
        @DisplayName("Should throw UnsupportedOperationException on add")
        void unmodifiableList_ShouldThrowOnAdd_WhenValid() {
            // given
            List<String> source = new ArrayList<>(List.of("a"));
            List<String> result = CollectionUtils.unmodifiableList(source);

            // when & then
            thenThrownBy(() -> result.add("b"))
                    .as("Add should be rejected")
                    .isInstanceOf(UnsupportedOperationException.class);
        }

        @Test
        @DisplayName("Should throw UnsupportedOperationException on remove")
        void unmodifiableList_ShouldThrowOnRemove_WhenValid() {
            // given
            List<String> source = new ArrayList<>(List.of("a", "b"));
            List<String> result = CollectionUtils.unmodifiableList(source);

            // when & then
            thenThrownBy(result::removeFirst)
                    .as("Remove by index should be rejected")
                    .isInstanceOf(UnsupportedOperationException.class);
        }

        @Test
        @DisplayName("Should throw UnsupportedOperationException on set")
        void unmodifiableList_ShouldThrowOnSet_WhenValid() {
            // given
            List<String> source = new ArrayList<>(List.of("a"));
            List<String> result = CollectionUtils.unmodifiableList(source);

            // when & then
            thenThrownBy(() -> result.set(0, "b"))
                    .as("Set should be rejected")
                    .isInstanceOf(UnsupportedOperationException.class);
        }

        @Test
        @DisplayName("Should throw UnsupportedOperationException on clear")
        void unmodifiableList_ShouldThrowOnClear_WhenValid() {
            // given
            List<String> source = new ArrayList<>(List.of("a"));
            List<String> result = CollectionUtils.unmodifiableList(source);

            // when & then
            thenThrownBy(result::clear)
                    .as("Clear should be rejected")
                    .isInstanceOf(UnsupportedOperationException.class);
        }

        @Test
        @DisplayName("Should support index access")
        void unmodifiableList_ShouldSupportIndexAccess_WhenValid() {
            // given
            List<String> source = new ArrayList<>(List.of("a", "b", "c"));
            List<String> result = CollectionUtils.unmodifiableList(source);

            // when
            String element = result.get(1);

            // then
            then(element).as("Index access should work on unmodifiable view").isEqualTo("b");
        }

        @Test
        @DisplayName("Should reflect changes made to the backing list")
        void unmodifiableList_ShouldReflectBackingChanges_WhenModified() {
            // given
            List<String> source = new ArrayList<>(List.of("a"));
            List<String> result = CollectionUtils.unmodifiableList(source);

            // when
            source.add("b");

            // then
            then(result).as("View should reflect changes in backing list").containsExactly("a", "b");
        }
    }

    @Nested
    @DisplayName("unmodifiableSet")
    class UnmodifiableSet {

        @Test
        @DisplayName("Should return empty immutable set when null")
        void unmodifiableSet_ShouldReturnEmpty_WhenNull() {
            // given

            // when
            Set<String> result = CollectionUtils.unmodifiableSet(null);

            // then
            then(result).as("Null input should yield empty set").isEmpty();
            thenThrownBy(() -> result.add("a"))
                    .as("Result should be unmodifiable")
                    .isInstanceOf(UnsupportedOperationException.class);
        }

        @Test
        @DisplayName("Should return empty unmodifiable view when set is empty")
        void unmodifiableSet_ShouldReturnEmptyView_WhenEmpty() {
            // given
            Set<String> source = new HashSet<>();

            // when
            Set<String> result = CollectionUtils.unmodifiableSet(source);

            // then
            then(result).as("Empty source should yield empty view").isEmpty();
            thenThrownBy(() -> result.add("a"))
                    .as("Empty view should be unmodifiable")
                    .isInstanceOf(UnsupportedOperationException.class);
        }

        @Test
        @DisplayName("Should throw UnsupportedOperationException on add")
        void unmodifiableSet_ShouldThrowOnAdd_WhenValid() {
            // given
            Set<String> source = new HashSet<>(Set.of("a"));
            Set<String> result = CollectionUtils.unmodifiableSet(source);

            // when & then
            thenThrownBy(() -> result.add("b"))
                    .as("Add should be rejected")
                    .isInstanceOf(UnsupportedOperationException.class);
        }

        @Test
        @DisplayName("Should throw UnsupportedOperationException on remove")
        void unmodifiableSet_ShouldThrowOnRemove_WhenValid() {
            // given
            Set<String> source = new HashSet<>(Set.of("a"));
            Set<String> result = CollectionUtils.unmodifiableSet(source);

            // when & then
            thenThrownBy(() -> result.remove("a"))
                    .as("Remove should be rejected")
                    .isInstanceOf(UnsupportedOperationException.class);
        }

        @Test
        @DisplayName("Should throw UnsupportedOperationException on clear")
        void unmodifiableSet_ShouldThrowOnClear_WhenValid() {
            // given
            Set<String> source = new HashSet<>(Set.of("a"));
            Set<String> result = CollectionUtils.unmodifiableSet(source);

            // when & then
            thenThrownBy(result::clear)
                    .as("Clear should be rejected")
                    .isInstanceOf(UnsupportedOperationException.class);
        }

        @Test
        @DisplayName("Should throw UnsupportedOperationException on addAll")
        void unmodifiableSet_ShouldThrowOnAddAll_WhenValid() {
            // given
            Set<String> source = new HashSet<>(Set.of("a"));
            Set<String> result = CollectionUtils.unmodifiableSet(source);

            // when & then
            thenThrownBy(() -> result.addAll(Set.of("b", "c")))
                    .as("AddAll should be rejected")
                    .isInstanceOf(UnsupportedOperationException.class);
        }

        @Test
        @DisplayName("Should support contains on unmodifiable view")
        void unmodifiableSet_ShouldSupportContains_WhenValid() {
            // given
            Set<String> source = new HashSet<>(Set.of("a", "b"));
            Set<String> result = CollectionUtils.unmodifiableSet(source);

            // when & then
            then(result.contains("a")).as("Contains should work").isTrue();
            then(result.contains("z")).as("Missing element should be false").isFalse();
        }

        @Test
        @DisplayName("Should reflect changes made to the backing set")
        void unmodifiableSet_ShouldReflectBackingChanges_WhenModified() {
            // given
            Set<String> source = new HashSet<>(Set.of("a"));
            Set<String> result = CollectionUtils.unmodifiableSet(source);

            // when
            source.add("b");

            // then
            then(result).as("View should reflect changes in backing set").containsExactlyInAnyOrder("a", "b");
        }
    }

    @Nested
    @DisplayName("unmodifiableMap")
    class UnmodifiableMap {

        @Test
        @DisplayName("Should return empty immutable map when null")
        void unmodifiableMap_ShouldReturnEmpty_WhenNull() {
            // given

            // when
            Map<String, Integer> result = CollectionUtils.unmodifiableMap(null);

            // then
            then(result).as("Null input should yield empty map").isEmpty();
            thenThrownBy(() -> result.put("a", 1))
                    .as("Result should be unmodifiable")
                    .isInstanceOf(UnsupportedOperationException.class);
        }

        @Test
        @DisplayName("Should return empty unmodifiable view when map is empty")
        void unmodifiableMap_ShouldReturnEmptyView_WhenEmpty() {
            // given
            Map<String, Integer> source = new HashMap<>();

            // when
            Map<String, Integer> result = CollectionUtils.unmodifiableMap(source);

            // then
            then(result).as("Empty source should yield empty view").isEmpty();
            thenThrownBy(() -> result.put("a", 1))
                    .as("Empty view should be unmodifiable")
                    .isInstanceOf(UnsupportedOperationException.class);
        }

        @Test
        @DisplayName("Should throw UnsupportedOperationException on put")
        void unmodifiableMap_ShouldThrowOnPut_WhenValid() {
            // given
            Map<String, Integer> source = new HashMap<>(Map.of("a", 1));
            Map<String, Integer> result = CollectionUtils.unmodifiableMap(source);

            // when & then
            thenThrownBy(() -> result.put("b", 2))
                    .as("Put should be rejected")
                    .isInstanceOf(UnsupportedOperationException.class);
        }

        @Test
        @DisplayName("Should throw UnsupportedOperationException on remove")
        void unmodifiableMap_ShouldThrowOnRemove_WhenValid() {
            // given
            Map<String, Integer> source = new HashMap<>(Map.of("a", 1));
            Map<String, Integer> result = CollectionUtils.unmodifiableMap(source);

            // when & then
            thenThrownBy(() -> result.remove("a"))
                    .as("Remove should be rejected")
                    .isInstanceOf(UnsupportedOperationException.class);
        }

        @Test
        @DisplayName("Should throw UnsupportedOperationException on clear")
        void unmodifiableMap_ShouldThrowOnClear_WhenValid() {
            // given
            Map<String, Integer> source = new HashMap<>(Map.of("a", 1));
            Map<String, Integer> result = CollectionUtils.unmodifiableMap(source);

            // when & then
            thenThrownBy(result::clear)
                    .as("Clear should be rejected")
                    .isInstanceOf(UnsupportedOperationException.class);
        }

        @Test
        @DisplayName("Should throw UnsupportedOperationException on putAll")
        void unmodifiableMap_ShouldThrowOnPutAll_WhenValid() {
            // given
            Map<String, Integer> source = new HashMap<>(Map.of("a", 1));
            Map<String, Integer> result = CollectionUtils.unmodifiableMap(source);

            // when & then
            thenThrownBy(() -> result.putAll(Map.of("b", 2, "c", 3)))
                    .as("PutAll should be rejected")
                    .isInstanceOf(UnsupportedOperationException.class);
        }

        @Test
        @DisplayName("Should support get and containsKey on unmodifiable view")
        void unmodifiableMap_ShouldSupportReadOperations_WhenValid() {
            // given
            Map<String, Integer> source = new HashMap<>(Map.of("a", 1));
            Map<String, Integer> result = CollectionUtils.unmodifiableMap(source);

            // when & then
            then(result.get("a")).as("Get should work").isEqualTo(1);
            then(result.containsKey("a")).as("ContainsKey should work").isTrue();
            then(result.containsKey("z")).as("Missing key should be false").isFalse();
        }

        @Test
        @DisplayName("Should reflect changes made to the backing map")
        void unmodifiableMap_ShouldReflectBackingChanges_WhenModified() {
            // given
            Map<String, Integer> source = new HashMap<>(Map.of("a", 1));
            Map<String, Integer> result = CollectionUtils.unmodifiableMap(source);

            // when
            source.put("b", 2);

            // then
            then(result).as("View should reflect changes in backing map").containsEntry("b", 2);
        }
    }

    @Nested
    @DisplayName("shuffle")
    class Shuffle {

        @Test
        @DisplayName("Should return empty when collection is null")
        void shuffle_ShouldReturnEmpty_WhenCollectionIsNull() {
            // given

            // when
            List<Integer> result = CollectionUtils.shuffle(null);

            // then
            then(result).as("Null collection should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty when collection is empty")
        void shuffle_ShouldReturnEmpty_WhenCollectionIsEmpty() {
            // given
            Collection<Integer> source = List.of();

            // when
            List<Integer> result = CollectionUtils.shuffle(source);

            // then
            then(result).as("Empty collection should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should not modify the original collection")
        void shuffle_ShouldNotModifyOriginalCollection_WhenValid() {
            // given
            List<Integer> source = new ArrayList<>(List.of(1, 2, 3, 4, 5));

            // when
            CollectionUtils.shuffle(source);

            // then
            then(source).as("Original collection must remain unchanged").containsExactly(1, 2, 3, 4, 5);
        }

        @Test
        @DisplayName("Should preserve all elements and cardinality after shuffle")
        void shuffle_ShouldPreserveAllElements_WhenValid() {
            // given
            Collection<Integer> source = List.of(1, 1, 2, 3, 3, 3);

            // when
            List<Integer> result = CollectionUtils.shuffle(source);

            // then
            then(result).as("Size must be preserved").hasSize(6);
            then(result).as("All elements including duplicates must be present")
                    .containsExactlyInAnyOrder(1, 1, 2, 3, 3, 3);
        }

        @Test
        @DisplayName("Should return same single element when collection has one element")
        void shuffle_ShouldReturnSameSingleElement_WhenOneElement() {
            // given
            Collection<Integer> source = List.of(99);

            // when
            List<Integer> result = CollectionUtils.shuffle(source);

            // then
            then(result).as("Single element should remain").containsExactly(99);
        }

        @Test
        @DisplayName("Should return modifiable list independent of source")
        void shuffle_ShouldReturnModifiableList_WhenValid() {
            // given
            Collection<Integer> source = List.of(1, 2, 3);

            // when
            List<Integer> result = CollectionUtils.shuffle(source);

            // then
            result.add(99);
            then(result).as("Result should be modifiable and independent").contains(99);
            then(source).as("Source should be unaffected").containsExactly(1, 2, 3);
        }

        @Test
        @DisplayName("Should shuffle set input correctly")
        void shuffle_ShouldHandleSetInput_WhenValid() {
            // given
            Collection<Integer> source = new LinkedHashSet<>(List.of(1, 2, 3, 4));

            // when
            List<Integer> result = CollectionUtils.shuffle(source);

            // then
            then(result).as("All elements from set should be present")
                    .containsExactlyInAnyOrder(1, 2, 3, 4);
        }

        @Test
        @DisplayName("Should return a new list instance, not the same reference")
        void shuffle_ShouldReturnNewListInstance_WhenValid() {
            // given
            List<Integer> source = new ArrayList<>(List.of(1, 2, 3));

            // when
            List<Integer> result = CollectionUtils.shuffle(source);

            // then
            then(result).as("Should be a different instance").isNotSameAs(source);
        }

        @Test
        @DisplayName("Should return empty when collection is null with random")
        void shuffle_ShouldReturnEmpty_WhenCollectionIsNullWithRandom() {
            // given

            // when
            List<Integer> result = CollectionUtils.shuffle(null, new Random(1));

            // then
            then(result).as("Null collection should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty when collection is empty with random")
        void shuffle_ShouldReturnEmpty_WhenCollectionIsEmptyWithRandom() {
            // given
            Collection<Integer> source = List.of();
            Random random = new Random(1);

            // when
            List<Integer> result = CollectionUtils.shuffle(source, random);

            // then
            then(result).as("Empty collection should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should produce reproducible order for same seed")
        void shuffle_ShouldBeReproducible_WhenSameSeedProvided() {
            // given
            Collection<Integer> source = List.of(1, 2, 3, 4, 5);

            // when
            List<Integer> result1 = CollectionUtils.shuffle(source, new Random(42));
            List<Integer> result2 = CollectionUtils.shuffle(source, new Random(42));

            // then
            then(result1).as("Same seed should produce same order").containsExactlyElementsOf(result2);
        }

        @Test
        @DisplayName("Should throw NullPointerException when random is null")
        void shuffle_ShouldThrowNullPointerException_WhenRandomIsNull() {
            // given
            Collection<Integer> source = List.of(1, 2, 3);

            // when & then
            thenThrownBy(() -> CollectionUtils.shuffle(source, null))
                    .as("Null random should cause NPE from Collections.shuffle")
                    .isInstanceOf(NullPointerException.class);
        }
    }

    @Nested
    @DisplayName("permutations")
    class Permutations {

        @Test
        @DisplayName("Should return empty when list is null")
        void permutations_ShouldReturnEmpty_WhenListIsNull() {
            // given

            // when
            List<List<String>> result = CollectionUtils.permutations(null);

            // then
            then(result).as("Null list should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty when list is empty")
        void permutations_ShouldReturnEmpty_WhenListIsEmpty() {
            // given
            List<String> source = List.of();

            // when
            List<List<String>> result = CollectionUtils.permutations(source);

            // then
            then(result).as("Empty list should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return single permutation when list has one element")
        void permutations_ShouldReturnSinglePermutation_WhenOneElement() {
            // given
            List<String> source = List.of("a");

            // when
            List<List<String>> result = CollectionUtils.permutations(source);

            // then
            then(result).as("1! = 1 permutation").hasSize(1);
            then(result.getFirst()).as("Permutation should be the element itself").containsExactly("a");
        }

        @Test
        @DisplayName("Should return both permutations when list has two elements")
        void permutations_ShouldReturnBothOrderings_WhenTwoElements() {
            // given
            List<String> source = List.of("a", "b");

            // when
            List<List<String>> result = CollectionUtils.permutations(source);

            // then
            then(result).as("2! = 2 permutations").hasSize(2);
            then(result).as("Both orderings expected")
                    .containsExactlyInAnyOrder(List.of("a", "b"), List.of("b", "a"));
        }

        @Test
        @DisplayName("Should return all six permutations when list has three elements")
        void permutations_ShouldReturnAllSix_WhenThreeElements() {
            // given
            List<String> source = List.of("a", "b", "c");

            // when
            List<List<String>> result = CollectionUtils.permutations(source);

            // then
            then(result).as("3! = 6 permutations").hasSize(6);
            then(result).as("All expected permutations")
                    .containsExactlyInAnyOrder(
                            List.of("a", "b", "c"), List.of("a", "c", "b"),
                            List.of("b", "a", "c"), List.of("b", "c", "a"),
                            List.of("c", "a", "b"), List.of("c", "b", "a"));
        }

        @Test
        @DisplayName("Should not modify the original list")
        void permutations_ShouldNotModifyOriginalList_WhenValid() {
            // given
            List<String> source = new ArrayList<>(List.of("a", "b", "c"));

            // when
            CollectionUtils.permutations(source);

            // then
            then(source).as("Original list must remain unchanged").containsExactly("a", "b", "c");
        }

        @Test
        @DisplayName("Should generate 24 permutations for four elements")
        void permutations_ShouldReturn24_WhenFourElements() {
            // given
            List<Integer> source = List.of(1, 2, 3, 4);

            // when
            List<List<Integer>> result = CollectionUtils.permutations(source);

            // then
            then(result).as("4! = 24 permutations").hasSize(24);
            then(result).as("All permutations have the same elements in some order")
                    .allSatisfy(p -> then(p).containsExactlyInAnyOrder(1, 2, 3, 4));
        }

        @Test
        @DisplayName("Should handle duplicate elements producing permutations")
        void permutations_ShouldProducePermutations_WhenDuplicatesPresent() {
            // given
            List<String> source = List.of("a", "a");

            // when
            List<List<String>> result = CollectionUtils.permutations(source);

            // then
            then(result).as("2! = 2 permutations even with duplicates (permutes indices)")
                    .hasSize(2);
            then(result).as("Both permutations are [a, a]")
                    .allSatisfy(p -> then(p).containsExactly("a", "a"));
        }
    }

    @Nested
    @DisplayName("combinations")
    class Combinations {

        @Test
        @DisplayName("Should return empty when list is null")
        void combinations_ShouldReturnEmpty_WhenListIsNull() {
            // given

            // when
            List<List<String>> result = CollectionUtils.combinations(null, 2);

            // then
            then(result).as("Null list should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty when list is empty")
        void combinations_ShouldReturnEmpty_WhenListIsEmpty() {
            // given
            List<String> source = List.of();

            // when
            List<List<String>> result = CollectionUtils.combinations(source, 1);

            // then
            then(result).as("Empty list should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty when k is negative")
        void combinations_ShouldReturnEmpty_WhenKIsNegative() {
            // given
            List<String> source = List.of("a", "b", "c");

            // when
            List<List<String>> result = CollectionUtils.combinations(source, -1);

            // then
            then(result).as("Negative k should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty when k is greater than list size")
        void combinations_ShouldReturnEmpty_WhenKGreaterThanSize() {
            // given
            List<String> source = List.of("a", "b");

            // when
            List<List<String>> result = CollectionUtils.combinations(source, 3);

            // then
            then(result).as("k > size should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return single combination when k equals list size")
        void combinations_ShouldReturnSingleFullCombination_WhenKEqualsSize() {
            // given
            List<String> source = List.of("a", "b", "c");

            // when
            List<List<String>> result = CollectionUtils.combinations(source, 3);

            // then
            then(result).as("One full-size combination expected").hasSize(1);
            then(result.getFirst()).as("Combination should be the whole list").containsExactly("a", "b", "c");
        }

        @Test
        @DisplayName("Should return all single-element combinations when k is one")
        void combinations_ShouldReturnAllSingletons_WhenKIsOne() {
            // given
            List<String> source = List.of("a", "b", "c");

            // when
            List<List<String>> result = CollectionUtils.combinations(source, 1);

            // then
            then(result)
                    .as("C(3,1) = 3 combinations")
                    .hasSize(3);
            then(result)
                    .as("Each element as a singleton combination")
                    .containsExactlyInAnyOrder(List.of("a"), List.of("b"), List.of("c"));
        }

        @Test
        @DisplayName("Should return all expected combinations in order when k is two")
        void combinations_ShouldReturnAllExpectedCombinations_WhenKIsTwo() {
            // given
            List<String> source = List.of("a", "b", "c");

            // when
            List<List<String>> result = CollectionUtils.combinations(source, 2);

            // then
            then(result)
                    .as("C(3,2) = 3 combinations")
                    .hasSize(3);
            then(result)
                    .as("All expected pairs")
                    .containsExactlyInAnyOrder(List.of("a", "b"), List.of("a", "c"), List.of("b", "c"));
        }

        @Test
        @DisplayName("Should compute C(4,2) = 6 combinations")
        void combinations_ShouldReturnSixCombinations_WhenFourChooseTwo() {
            // given
            List<Integer> source = List.of(1, 2, 3, 4);

            // when
            List<List<Integer>> result = CollectionUtils.combinations(source, 2);

            // then
            then(result)
                    .as("C(4,2) = 6 combinations")
                    .hasSize(6);
            then(result)
                    .as("All expected pairs")
                    .containsExactlyInAnyOrder(
                            List.of(1, 2), List.of(1, 3), List.of(1, 4),
                            List.of(2, 3), List.of(2, 4), List.of(3, 4));
        }

        @Test
        @DisplayName("Should preserve source order inside combinations")
        void combinations_ShouldPreserveOrderInsideCombinations_WhenValid() {
            // given
            List<String> source = List.of("a", "b", "c", "d");

            // when
            List<List<String>> result = CollectionUtils.combinations(source, 2);

            // then
            then(result).as("Each combination should keep source order")
                    .allSatisfy(combination -> then(combination).isSortedAccordingTo(Comparator.naturalOrder()));
        }
    }

    @Nested
    @DisplayName("firstEntry")
    class FirstEntry {

        @Test
        @DisplayName("Should return first entry when map has elements")
        void firstEntry_ShouldReturnFirstEntry_WhenMapHasElements() {
            // given
            SortedMap<String, Integer> map = new TreeMap<>();
            map.put("a", 1);
            map.put("b", 2);

            // when
            Optional<Map.Entry<String, Integer>> result = CollectionUtils.firstEntry(map);

            // then
            then(result).as("First entry should be present").isPresent();
            then(result.get().getKey()).as("First key should be a").isEqualTo("a");
        }

        @Test
        @DisplayName("Should return empty when map is null")
        void firstEntry_ShouldReturnEmpty_WhenMapIsNull() {
            // given

            // when
            Optional<Map.Entry<String, Integer>> result = CollectionUtils.firstEntry(null);

            // then
            then(result).as("Null map should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty when map is empty")
        void firstEntry_ShouldReturnEmpty_WhenMapIsEmpty() {
            // given
            SortedMap<String, Integer> map = new TreeMap<>();

            // when
            Optional<Map.Entry<String, Integer>> result = CollectionUtils.firstEntry(map);

            // then
            then(result).as("Empty map should yield empty").isEmpty();
        }
    }

    @Nested
    @DisplayName("lastEntry")
    class LastEntry {

        @Test
        @DisplayName("Should return last entry when map has elements")
        void lastEntry_ShouldReturnLastEntry_WhenMapHasElements() {
            // given
            SortedMap<String, Integer> map = new TreeMap<>();
            map.put("a", 1);
            map.put("b", 2);

            // when
            Optional<Map.Entry<String, Integer>> result = CollectionUtils.lastEntry(map);

            // then
            then(result).as("Last entry should be present").isPresent();
            then(result.get().getKey()).as("Last key should be b").isEqualTo("b");
        }

        @Test
        @DisplayName("Should return empty when map is null")
        void lastEntry_ShouldReturnEmpty_WhenMapIsNull() {
            // given

            // when
            Optional<Map.Entry<String, Integer>> result = CollectionUtils.lastEntry(null);

            // then
            then(result).as("Null map should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty when map is empty")
        void lastEntry_ShouldReturnEmpty_WhenMapIsEmpty() {
            // given
            SortedMap<String, Integer> map = new TreeMap<>();

            // when
            Optional<Map.Entry<String, Integer>> result = CollectionUtils.lastEntry(map);

            // then
            then(result).as("Empty map should yield empty").isEmpty();
        }
    }

    @Nested
    @DisplayName("subMap")
    class SubMap {

        @Test
        @DisplayName("Should return submap when valid range")
        void subMap_ShouldReturnSubMap_WhenValidRange() {
            // given
            SortedMap<String, Integer> map = new TreeMap<>();
            map.put("a", 1);
            map.put("b", 2);
            map.put("c", 3);

            // when
            SortedMap<String, Integer> result = CollectionUtils.subMap(map, "a", "c");

            // then
            then(result).as("Submap should contain expected keys").containsOnlyKeys("a", "b");
        }

        @Test
        @DisplayName("Should return empty when map is null")
        void subMap_ShouldReturnEmpty_WhenMapIsNull() {
            // given

            // when
            SortedMap<String, Integer> result = CollectionUtils.subMap(null, "a", "b");

            // then
            then(result).as("Null map should yield empty").isEmpty();
        }
    }

    @Nested
    @DisplayName("headMap")
    class HeadMap {

        @Test
        @DisplayName("Should return head map when valid")
        void headMap_ShouldReturnHeadMap_WhenValid() {
            // given
            SortedMap<String, Integer> map = new TreeMap<>();
            map.put("a", 1);
            map.put("b", 2);
            map.put("c", 3);

            // when
            SortedMap<String, Integer> result = CollectionUtils.headMap(map, "c");

            // then
            then(result).as("Head map should contain keys less than c").containsOnlyKeys("a", "b");
        }

        @Test
        @DisplayName("Should return empty when map is null")
        void headMap_ShouldReturnEmpty_WhenMapIsNull() {
            // given

            // when
            SortedMap<String, Integer> result = CollectionUtils.headMap(null, "a");

            // then
            then(result).as("Null map should yield empty").isEmpty();
        }
    }

    @Nested
    @DisplayName("tailMap")
    class TailMap {

        @Test
        @DisplayName("Should return tail map when valid")
        void tailMap_ShouldReturnTailMap_WhenValid() {
            // given
            SortedMap<String, Integer> map = new TreeMap<>();
            map.put("a", 1);
            map.put("b", 2);
            map.put("c", 3);

            // when
            SortedMap<String, Integer> result = CollectionUtils.tailMap(map, "b");

            // then
            then(result).as("Tail map should contain keys from b").containsOnlyKeys("b", "c");
        }

        @Test
        @DisplayName("Should return empty when map is null")
        void tailMap_ShouldReturnEmpty_WhenMapIsNull() {
            // given

            // when
            SortedMap<String, Integer> result = CollectionUtils.tailMap(null, "a");

            // then
            then(result).as("Null map should yield empty").isEmpty();
        }
    }

    @Nested
    @DisplayName("rotate")
    class Rotate {

        @Test
        @DisplayName("Should rotate list to the right")
        void rotate_ShouldRotateRight_WhenPositiveDistance() {
            // given
            List<Integer> list = List.of(1, 2, 3, 4);

            // when
            List<Integer> result = CollectionUtils.rotate(list, 1);

            // then
            then(result).as("Rotated right by 1").containsExactly(4, 1, 2, 3);
        }

        @Test
        @DisplayName("Should rotate list to the left")
        void rotate_ShouldRotateLeft_WhenNegativeDistance() {
            // given
            List<Integer> list = List.of(1, 2, 3, 4);

            // when
            List<Integer> result = CollectionUtils.rotate(list, -1);

            // then
            then(result).as("Rotated left by 1").containsExactly(2, 3, 4, 1);
        }

        @Test
        @DisplayName("Should return empty when list is null")
        void rotate_ShouldReturnEmpty_WhenListIsNull() {
            // given

            // when
            List<Integer> result = CollectionUtils.rotate(null, 1);

            // then
            then(result).as("Null list should yield empty").isEmpty();
        }
    }

    @Nested
    @DisplayName("binarySearch")
    class BinarySearch {

        @Test
        @DisplayName("Should return index when key found")
        void binarySearch_ShouldReturnIndex_WhenKeyFound() {
            // given
            List<Integer> list = List.of(1, 3, 5, 7);

            // when
            int result = CollectionUtils.binarySearch(list, 5);

            // then
            then(result).as("Index of 5 should be 2").isEqualTo(2);
        }

        @Test
        @DisplayName("Should return negative when key not found")
        void binarySearch_ShouldReturnNegative_WhenKeyNotFound() {
            // given
            List<Integer> list = List.of(1, 3, 5);

            // when
            int result = CollectionUtils.binarySearch(list, 4);

            // then
            then(result).as("Not found should return negative").isNegative();
        }

        @Test
        @DisplayName("Should return -1 when list is null")
        void binarySearch_ShouldReturnMinusOne_WhenListIsNull() {
            // given

            // when
            int result = CollectionUtils.binarySearch(null, 1);

            // then
            then(result).as("Null list should return -1").isEqualTo(-1);
        }
    }

    @Nested
    @DisplayName("binarySearch with comparator")
    class BinarySearchWithComparator {

        @Test
        @DisplayName("Should return index when key found with comparator")
        void binarySearch_ShouldReturnIndex_WhenKeyFoundWithComparator() {
            // given
            List<String> list = List.of("a", "c", "e");
            Comparator<String> comparator = String::compareTo;

            // when
            int result = CollectionUtils.binarySearch(list, "c", comparator);

            // then
            then(result).as("Index of c should be 1").isEqualTo(1);
        }

        @Test
        @DisplayName("Should return -1 when list is null")
        void binarySearch_ShouldReturnMinusOne_WhenListIsNull() {
            // given
            Comparator<String> comparator = String::compareTo;

            // when
            int result = CollectionUtils.binarySearch(null, "a", comparator);

            // then
            then(result).as("Null list should return -1").isEqualTo(-1);
        }
    }

    @Nested
    @DisplayName("ceilingEntry")
    class CeilingEntry {

        @Test
        @DisplayName("Should return ceiling entry when exists")
        void ceilingEntry_ShouldReturnEntry_WhenExists() {
            // given
            NavigableMap<Integer, String> map = new TreeMap<>();
            map.put(1, "a");
            map.put(3, "c");

            // when
            Optional<Map.Entry<Integer, String>> result = CollectionUtils.ceilingEntry(map, 2);

            // then
            then(result).as("Ceiling of 2 should be 3").isPresent();
            then(result.get().getKey()).as("Key should be 3").isEqualTo(3);
        }

        @Test
        @DisplayName("Should return empty when map is null")
        void ceilingEntry_ShouldReturnEmpty_WhenMapIsNull() {
            // given

            // when
            Optional<Map.Entry<Integer, String>> result = CollectionUtils.ceilingEntry(null, 1);

            // then
            then(result).as("Null map should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty when key is null")
        void ceilingEntry_ShouldReturnEmpty_WhenKeyIsNull() {
            // given
            NavigableMap<Integer, String> map = new TreeMap<>();
            map.put(1, "a");

            // when
            Optional<Map.Entry<Integer, String>> result = CollectionUtils.ceilingEntry(map, null);

            // then
            then(result).as("Null key should yield empty").isEmpty();
        }
    }

    @Nested
    @DisplayName("floorEntry")
    class FloorEntry {

        @Test
        @DisplayName("Should return empty when map is empty")
        void floorEntry_ShouldReturnEmpty_WhenMapIsEmpty() {
            // given
            NavigableMap<Integer, String> map = new TreeMap<>();

            // when
            Optional<Map.Entry<Integer, String>> result = CollectionUtils.floorEntry(map, 2);

            // then
            then(result).as("Empty map should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty when key is null")
        void floorEntry_ShouldReturnEmpty_WhenKeyIsNull() {
            // given
            NavigableMap<Integer, String> map = new TreeMap<>();
            map.put(1, "a");

            // when
            Optional<Map.Entry<Integer, String>> result = CollectionUtils.floorEntry(map, null);

            // then
            then(result).as("Null key should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return exact entry when key exists in map")
        void floorEntry_ShouldReturnExactEntry_WhenKeyExists() {
            // given
            NavigableMap<Integer, String> map = new TreeMap<>();
            map.put(1, "a");
            map.put(3, "c");

            // when
            Optional<Map.Entry<Integer, String>> result = CollectionUtils.floorEntry(map, 3);

            // then
            then(result).as("Exact key should be returned").isPresent();
            then(result.get().getKey()).as("Key should be 3").isEqualTo(3);
            then(result.get().getValue()).as("Value should be c").isEqualTo("c");
        }

        @Test
        @DisplayName("Should return empty when no floor entry exists")
        void floorEntry_ShouldReturnEmpty_WhenNoFloorExists() {
            // given
            NavigableMap<Integer, String> map = new TreeMap<>();
            map.put(3, "c");
            map.put(5, "e");

            // when
            Optional<Map.Entry<Integer, String>> result = CollectionUtils.floorEntry(map, 1);

            // then
            then(result).as("No floor entry should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return correct floor when key is between entries")
        void floorEntry_ShouldReturnClosestLowerEntry_WhenKeyBetweenEntries() {
            // given
            NavigableMap<Integer, String> map = new TreeMap<>();
            map.put(1, "a");
            map.put(5, "e");
            map.put(9, "i");

            // when
            Optional<Map.Entry<Integer, String>> result = CollectionUtils.floorEntry(map, 7);

            // then
            then(result).as("Floor of 7 should be 5").isPresent();
            then(result.get().getKey()).as("Key should be 5").isEqualTo(5);
            then(result.get().getValue()).as("Value should be e").isEqualTo("e");
        }

        @Test
        @DisplayName("Should return floor entry with null value correctly")
        void floorEntry_ShouldReturnEntry_WhenFloorValueIsNull() {
            // given
            NavigableMap<Integer, String> map = new TreeMap<>();
            map.put(1, null);
            map.put(3, "c");

            // when
            Optional<Map.Entry<Integer, String>> result = CollectionUtils.floorEntry(map, 2);

            // then
            then(result).as("Entry with null value should still be present").isPresent();
            then(result.get().getKey()).as("Key should be 1").isEqualTo(1);
            then(result.get().getValue()).as("Value should be null").isNull();
        }
    }

    @Nested
    @DisplayName("higherEntry")
    class HigherEntry {

        @Test
        @DisplayName("Should return empty when map is empty")
        void higherEntry_ShouldReturnEmpty_WhenMapIsEmpty() {
            // given
            NavigableMap<Integer, String> map = new TreeMap<>();

            // when
            Optional<Map.Entry<Integer, String>> result = CollectionUtils.higherEntry(map, 1);

            // then
            then(result).as("Empty map should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty when key is null")
        void higherEntry_ShouldReturnEmpty_WhenKeyIsNull() {
            // given
            NavigableMap<Integer, String> map = new TreeMap<>();
            map.put(1, "a");

            // when
            Optional<Map.Entry<Integer, String>> result = CollectionUtils.higherEntry(map, null);

            // then
            then(result).as("Null key should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return next entry, not exact, when key exists in map")
        void higherEntry_ShouldReturnNextEntry_WhenKeyExists() {
            // given
            NavigableMap<Integer, String> map = new TreeMap<>();
            map.put(1, "a");
            map.put(3, "c");
            map.put(5, "e");

            // when
            Optional<Map.Entry<Integer, String>> result = CollectionUtils.higherEntry(map, 3);

            // then
            then(result).as("Higher of 3 should be 5, not 3 itself").isPresent();
            then(result.get().getKey()).as("Key should be 5").isEqualTo(5);
            then(result.get().getValue()).as("Value should be e").isEqualTo("e");
        }

        @Test
        @DisplayName("Should return empty when no higher entry exists")
        void higherEntry_ShouldReturnEmpty_WhenNoHigherExists() {
            // given
            NavigableMap<Integer, String> map = new TreeMap<>();
            map.put(1, "a");
            map.put(3, "c");

            // when
            Optional<Map.Entry<Integer, String>> result = CollectionUtils.higherEntry(map, 3);

            // then
            then(result).as("No higher entry should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return correct higher when key is between entries")
        void higherEntry_ShouldReturnClosestHigherEntry_WhenKeyBetweenEntries() {
            // given
            NavigableMap<Integer, String> map = new TreeMap<>();
            map.put(1, "a");
            map.put(5, "e");
            map.put(9, "i");

            // when
            Optional<Map.Entry<Integer, String>> result = CollectionUtils.higherEntry(map, 3);

            // then
            then(result).as("Higher of 3 should be 5").isPresent();
            then(result.get().getKey()).as("Key should be 5").isEqualTo(5);
            then(result.get().getValue()).as("Value should be e").isEqualTo("e");
        }

        @Test
        @DisplayName("Should return first entry when key is smaller than all entries")
        void higherEntry_ShouldReturnFirstEntry_WhenKeySmallerThanAll() {
            // given
            NavigableMap<Integer, String> map = new TreeMap<>();
            map.put(3, "c");
            map.put(5, "e");

            // when
            Optional<Map.Entry<Integer, String>> result = CollectionUtils.higherEntry(map, 1);

            // then
            then(result).as("Higher of 1 should be 3").isPresent();
            then(result.get().getKey()).as("Key should be 3").isEqualTo(3);
        }

        @Test
        @DisplayName("Should return higher entry with null value correctly")
        void higherEntry_ShouldReturnEntry_WhenHigherValueIsNull() {
            // given
            NavigableMap<Integer, String> map = new TreeMap<>();
            map.put(1, "a");
            map.put(3, null);

            // when
            Optional<Map.Entry<Integer, String>> result = CollectionUtils.higherEntry(map, 1);

            // then
            then(result).as("Entry with null value should still be present").isPresent();
            then(result.get().getKey()).as("Key should be 3").isEqualTo(3);
            then(result.get().getValue()).as("Value should be null").isNull();
        }
    }

    @Nested
    @DisplayName("lowerEntry")
    class LowerEntry {

        @Test
        @DisplayName("Should return empty when map is empty")
        void lowerEntry_ShouldReturnEmpty_WhenMapIsEmpty() {
            // given
            NavigableMap<Integer, String> map = new TreeMap<>();

            // when
            Optional<Map.Entry<Integer, String>> result = CollectionUtils.lowerEntry(map, 2);

            // then
            then(result).as("Empty map should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty when key is null")
        void lowerEntry_ShouldReturnEmpty_WhenKeyIsNull() {
            // given
            NavigableMap<Integer, String> map = new TreeMap<>();
            map.put(1, "a");

            // when
            Optional<Map.Entry<Integer, String>> result = CollectionUtils.lowerEntry(map, null);

            // then
            then(result).as("Null key should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return previous entry, not exact, when key exists in map")
        void lowerEntry_ShouldReturnPreviousEntry_WhenKeyExists() {
            // given
            NavigableMap<Integer, String> map = new TreeMap<>();
            map.put(1, "a");
            map.put(3, "c");
            map.put(5, "e");

            // when
            Optional<Map.Entry<Integer, String>> result = CollectionUtils.lowerEntry(map, 3);

            // then
            then(result).as("Lower of 3 should be 1, not 3 itself").isPresent();
            then(result.get().getKey()).as("Key should be 1").isEqualTo(1);
            then(result.get().getValue()).as("Value should be a").isEqualTo("a");
        }

        @Test
        @DisplayName("Should return empty when no lower entry exists")
        void lowerEntry_ShouldReturnEmpty_WhenNoLowerExists() {
            // given
            NavigableMap<Integer, String> map = new TreeMap<>();
            map.put(1, "a");
            map.put(3, "c");

            // when
            Optional<Map.Entry<Integer, String>> result = CollectionUtils.lowerEntry(map, 1);

            // then
            then(result).as("No lower entry should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return correct lower when key is between entries")
        void lowerEntry_ShouldReturnClosestLowerEntry_WhenKeyBetweenEntries() {
            // given
            NavigableMap<Integer, String> map = new TreeMap<>();
            map.put(1, "a");
            map.put(5, "e");
            map.put(9, "i");

            // when
            Optional<Map.Entry<Integer, String>> result = CollectionUtils.lowerEntry(map, 7);

            // then
            then(result).as("Lower of 7 should be 5").isPresent();
            then(result.get().getKey()).as("Key should be 5").isEqualTo(5);
            then(result.get().getValue()).as("Value should be e").isEqualTo("e");
        }

        @Test
        @DisplayName("Should return last entry when key is greater than all entries")
        void lowerEntry_ShouldReturnLastEntry_WhenKeyGreaterThanAll() {
            // given
            NavigableMap<Integer, String> map = new TreeMap<>();
            map.put(1, "a");
            map.put(3, "c");

            // when
            Optional<Map.Entry<Integer, String>> result = CollectionUtils.lowerEntry(map, 10);

            // then
            then(result).as("Lower of 10 should be 3").isPresent();
            then(result.get().getKey()).as("Key should be 3").isEqualTo(3);
        }

        @Test
        @DisplayName("Should return lower entry with null value correctly")
        void lowerEntry_ShouldReturnEntry_WhenLowerValueIsNull() {
            // given
            NavigableMap<Integer, String> map = new TreeMap<>();
            map.put(1, null);
            map.put(3, "c");

            // when
            Optional<Map.Entry<Integer, String>> result = CollectionUtils.lowerEntry(map, 3);

            // then
            then(result).as("Entry with null value should still be present").isPresent();
            then(result.get().getKey()).as("Key should be 1").isEqualTo(1);
            then(result.get().getValue()).as("Value should be null").isNull();
        }
    }

    @Nested
    @DisplayName("descendingKeySet")
    class DescendingKeySet {

        @Test
        @DisplayName("Should return descending key set")
        void descendingKeySet_ShouldReturnDescending_WhenValid() {
            // given
            NavigableMap<Integer, String> map = new TreeMap<>();
            map.put(1, "a");
            map.put(2, "b");

            // when
            var result = CollectionUtils.descendingKeySet(map);

            // then
            then(result).as("Descending keys").containsExactly(2, 1);
        }

        @Test
        @DisplayName("Should return empty when map is null")
        void descendingKeySet_ShouldReturnEmpty_WhenMapIsNull() {
            // given

            // when
            var result = CollectionUtils.descendingKeySet(null);

            // then
            then(result).as("Null map should yield empty").isEmpty();
        }
    }

    @Nested
    @DisplayName("descendingMap")
    class DescendingMap {

        @Test
        @DisplayName("Should return descending map")
        void descendingMap_ShouldReturnDescending_WhenValid() {
            // given
            NavigableMap<Integer, String> map = new TreeMap<>();
            map.put(1, "a");
            map.put(2, "b");

            // when
            NavigableMap<Integer, String> result = CollectionUtils.descendingMap(map);

            // then
            then(result.keySet()).as("Descending keys").containsExactly(2, 1);
        }

        @Test
        @DisplayName("Should return empty when map is null")
        void descendingMap_ShouldReturnEmpty_WhenMapIsNull() {
            // given

            // when
            NavigableMap<Integer, String> result = CollectionUtils.descendingMap(null);

            // then
            then(result).as("Null map should yield empty").isEmpty();
        }
    }

    @Nested
    @DisplayName("frequency")
    class Frequency {

        @Test
        @DisplayName("Should return count of element")
        void frequency_ShouldReturnCount_WhenElementPresent() {
            // given
            Collection<String> collection = List.of("a", "b", "a", "c");

            // when
            int result = CollectionUtils.frequency(collection, "a");

            // then
            then(result).as("Frequency of a should be 2").isEqualTo(2);
        }

        @Test
        @DisplayName("Should return zero when element missing")
        void frequency_ShouldReturnZero_WhenElementMissing() {
            // given
            Collection<String> collection = List.of("a", "b");

            // when
            int result = CollectionUtils.frequency(collection, "z");

            // then
            then(result).as("Missing element frequency should be 0").isEqualTo(0);
        }
    }

    @Nested
    @DisplayName("indexOf")
    class IndexOf {

        @Test
        @DisplayName("Should return index when element found")
        void indexOf_ShouldReturnIndex_WhenElementFound() {
            // given
            List<String> list = List.of("a", "b", "c");

            // when
            int result = CollectionUtils.indexOf(list, "b");

            // then
            then(result).as("Index of b should be 1").isEqualTo(1);
        }

        @Test
        @DisplayName("Should return -1 when list is null")
        void indexOf_ShouldReturnMinusOne_WhenListIsNull() {
            // given

            // when
            int result = CollectionUtils.indexOf(null, "a");

            // then
            then(result).as("Null list should return -1").isEqualTo(-1);
        }
    }

    @Nested
    @DisplayName("lastIndexOf")
    class LastIndexOf {

        @Test
        @DisplayName("Should return last index when element found")
        void lastIndexOf_ShouldReturnLastIndex_WhenElementFound() {
            // given
            List<String> list = List.of("a", "b", "a");

            // when
            int result = CollectionUtils.lastIndexOf(list, "a");

            // then
            then(result).as("Last index of a should be 2").isEqualTo(2);
        }

        @Test
        @DisplayName("Should return -1 when list is null")
        void lastIndexOf_ShouldReturnMinusOne_WhenListIsNull() {
            // given

            // when
            int result = CollectionUtils.lastIndexOf(null, "a");

            // then
            then(result).as("Null list should return -1").isEqualTo(-1);
        }
    }

    @Nested
    @DisplayName("poll")
    class Poll {

        @Test
        @DisplayName("Should return and remove head")
        void poll_ShouldReturnHead_WhenQueueHasElements() {
            // given
            Queue<String> queue = new LinkedList<>();
            queue.offer("a");
            queue.offer("b");

            // when
            Optional<String> result = CollectionUtils.poll(queue);

            // then
            then(result).as("Polled element").contains("a");
            then(queue).as("Queue should have remaining").containsExactly("b");
        }

        @Test
        @DisplayName("Should return empty when queue is null")
        void poll_ShouldReturnEmpty_WhenQueueIsNull() {
            // given

            // when
            Optional<String> result = CollectionUtils.poll(null);

            // then
            then(result).as("Null queue should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty when queue is empty")
        void poll_ShouldReturnEmpty_WhenQueueIsEmpty() {
            // given
            Queue<String> queue = new LinkedList<>();

            // when
            Optional<String> result = CollectionUtils.poll(queue);

            // then
            then(result).as("Empty queue should yield empty").isEmpty();
        }
    }

    @Nested
    @DisplayName("peek")
    class Peek {

        @Test
        @DisplayName("Should return head without removing")
        void peek_ShouldReturnHead_WhenQueueHasElements() {
            // given
            Queue<String> queue = new LinkedList<>();
            queue.offer("a");
            queue.offer("b");

            // when
            Optional<String> result = CollectionUtils.peek(queue);

            // then
            then(result).as("Peeked element").contains("a");
            then(queue).as("Queue should remain unchanged").containsExactly("a", "b");
        }

        @Test
        @DisplayName("Should return empty when queue is null")
        void peek_ShouldReturnEmpty_WhenQueueIsNull() {
            // given

            // when
            Optional<String> result = CollectionUtils.peek(null);

            // then
            then(result).as("Null queue should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty when queue is empty")
        void peek_ShouldReturnEmpty_WhenQueueIsEmpty() {
            // given
            Queue<String> queue = new LinkedList<>();

            // when
            Optional<String> result = CollectionUtils.peek(queue);

            // then
            then(result).as("Empty queue should yield empty").isEmpty();
        }
    }

    @Nested
    @DisplayName("offer")
    class Offer {

        @Test
        @DisplayName("Should add element when valid")
        void offer_ShouldAddElement_WhenValid() {
            // given
            Queue<String> queue = new LinkedList<>();

            // when
            boolean result = CollectionUtils.offer(queue, "a");

            // then
            then(result).as("Offer should succeed").isTrue();
            then(queue).as("Element should be added").containsExactly("a");
        }

        @Test
        @DisplayName("Should return false when queue is null")
        void offer_ShouldReturnFalse_WhenQueueIsNull() {
            // given

            // when
            boolean result = CollectionUtils.offer(null, "a");

            // then
            then(result).as("Null queue should return false").isFalse();
        }

        @Test
        @DisplayName("Should return false when element is null")
        void offer_ShouldReturnFalse_WhenElementIsNull() {
            // given
            Queue<String> queue = new LinkedList<>();

            // when
            boolean result = CollectionUtils.offer(queue, null);

            // then
            then(result).as("Null element should return false").isFalse();
        }
    }

    @Nested
    @DisplayName("pollFirst")
    class PollFirst {

        @Test
        @DisplayName("Should return and remove first")
        void pollFirst_ShouldReturnFirst_WhenDequeHasElements() {
            // given
            Deque<String> deque = new ArrayDeque<>();
            deque.add("a");
            deque.add("b");

            // when
            Optional<String> result = CollectionUtils.pollFirst(deque);

            // then
            then(result).as("Polled first").contains("a");
            then(deque).as("Remaining").containsExactly("b");
        }

        @Test
        @DisplayName("Should return empty when deque is null")
        void pollFirst_ShouldReturnEmpty_WhenDequeIsNull() {
            // given

            // when
            Optional<String> result = CollectionUtils.pollFirst(null);

            // then
            then(result).as("Null deque should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty when deque is empty")
        void pollFirst_ShouldReturnEmpty_WhenDequeIsEmpty() {
            // given
            Deque<String> deque = new ArrayDeque<>();

            // when
            Optional<String> result = CollectionUtils.pollFirst(deque);

            // then
            then(result).as("Empty deque should yield empty").isEmpty();
        }
    }

    @Nested
    @DisplayName("pollLast")
    class PollLast {

        @Test
        @DisplayName("Should return and remove last")
        void pollLast_ShouldReturnLast_WhenDequeHasElements() {
            // given
            Deque<String> deque = new ArrayDeque<>();
            deque.add("a");
            deque.add("b");

            // when
            Optional<String> result = CollectionUtils.pollLast(deque);

            // then
            then(result).as("Polled last").contains("b");
            then(deque).as("Remaining").containsExactly("a");
        }

        @Test
        @DisplayName("Should return empty when deque is null")
        void pollLast_ShouldReturnEmpty_WhenDequeIsNull() {
            // given

            // when
            Optional<String> result = CollectionUtils.pollLast(null);

            // then
            then(result).as("Null deque should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty when deque is empty")
        void pollLast_ShouldReturnEmpty_WhenDequeIsEmpty() {
            // given
            Deque<String> deque = new ArrayDeque<>();

            // when
            Optional<String> result = CollectionUtils.pollLast(deque);

            // then
            then(result).as("Empty deque should yield empty").isEmpty();
        }
    }

    @Nested
    @DisplayName("peekFirst")
    class PeekFirst {

        @Test
        @DisplayName("Should return first without removing")
        void peekFirst_ShouldReturnFirst_WhenDequeHasElements() {
            // given
            Deque<String> deque = new ArrayDeque<>();
            deque.add("a");
            deque.add("b");

            // when
            Optional<String> result = CollectionUtils.peekFirst(deque);

            // then
            then(result).as("Peeked first").contains("a");
            then(deque).as("Unchanged").containsExactly("a", "b");
        }

        @Test
        @DisplayName("Should return empty when deque is null")
        void peekFirst_ShouldReturnEmpty_WhenDequeIsNull() {
            // given

            // when
            Optional<String> result = CollectionUtils.peekFirst(null);

            // then
            then(result).as("Null deque should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty when deque is empty")
        void peekFirst_ShouldReturnEmpty_WhenDequeIsEmpty() {
            // given
            Deque<String> deque = new ArrayDeque<>();

            // when
            Optional<String> result = CollectionUtils.peekFirst(deque);

            // then
            then(result).as("Empty deque should yield empty").isEmpty();
        }
    }

    @Nested
    @DisplayName("peekLast")
    class PeekLast {

        @Test
        @DisplayName("Should return last without removing")
        void peekLast_ShouldReturnLast_WhenDequeHasElements() {
            // given
            Deque<String> deque = new ArrayDeque<>();
            deque.add("a");
            deque.add("b");

            // when
            Optional<String> result = CollectionUtils.peekLast(deque);

            // then
            then(result).as("Peeked last").contains("b");
            then(deque).as("Unchanged").containsExactly("a", "b");
        }

        @Test
        @DisplayName("Should return empty when deque is null")
        void peekLast_ShouldReturnEmpty_WhenDequeIsNull() {
            // given

            // when
            Optional<String> result = CollectionUtils.peekLast(null);

            // then
            then(result).as("Null deque should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty when deque is empty")
        void peekLast_ShouldReturnEmpty_WhenDequeIsEmpty() {
            // given
            Deque<String> deque = new ArrayDeque<>();

            // when
            Optional<String> result = CollectionUtils.peekLast(deque);

            // then
            then(result).as("Empty deque should yield empty").isEmpty();
        }
    }

    @Nested
    @DisplayName("addFirst")
    class AddFirst {

        @Test
        @DisplayName("Should add element to front")
        void addFirst_ShouldAddToFront_WhenValid() {
            // given
            Deque<String> deque = new ArrayDeque<>();
            deque.add("b");

            // when
            boolean result = CollectionUtils.addFirst(deque, "a");

            // then
            then(result).as("Add should succeed").isTrue();
            then(deque).as("Front should be a").containsExactly("a", "b");
        }

        @Test
        @DisplayName("Should return false when deque is null")
        void addFirst_ShouldReturnFalse_WhenDequeIsNull() {
            // given

            // when
            boolean result = CollectionUtils.addFirst(null, "a");

            // then
            then(result).as("Null deque should return false").isFalse();
        }

        @Test
        @DisplayName("Should return false when element is null")
        void addFirst_ShouldReturnFalse_WhenElementIsNull() {
            // given
            Deque<String> deque = new ArrayDeque<>();

            // when
            boolean result = CollectionUtils.addFirst(deque, null);

            // then
            then(result).as("Null element should return false").isFalse();
        }
    }

    @Nested
    @DisplayName("addLast")
    class AddLast {

        @Test
        @DisplayName("Should add element to end")
        void addLast_ShouldAddToEnd_WhenValid() {
            // given
            Deque<String> deque = new ArrayDeque<>();
            deque.add("a");

            // when
            boolean result = CollectionUtils.addLast(deque, "b");

            // then
            then(result).as("Add should succeed").isTrue();
            then(deque).as("End should be b").containsExactly("a", "b");
        }

        @Test
        @DisplayName("Should return false when deque is null")
        void addLast_ShouldReturnFalse_WhenDequeIsNull() {
            // given

            // when
            boolean result = CollectionUtils.addLast(null, "a");

            // then
            then(result).as("Null deque should return false").isFalse();
        }

        @Test
        @DisplayName("Should return false when element is null")
        void addLast_ShouldReturnFalse_WhenElementIsNull() {
            // given
            Deque<String> deque = new ArrayDeque<>();

            // when
            boolean result = CollectionUtils.addLast(deque, null);

            // then
            then(result).as("Null element should return false").isFalse();
        }
    }

    @Nested
    @DisplayName("removeIf")
    class RemoveIf {

        @Test
        @DisplayName("Should remove elements matching predicate")
        void removeIf_ShouldRemoveMatching_WhenPredicateMatches() {
            // given
            Collection<Integer> source = List.of(1, 2, 3, 4);
            Predicate<Integer> predicate = n -> n % 2 == 0;

            // when
            List<Integer> result = CollectionUtils.removeIf(source, predicate);

            // then
            then(result).as("Odd numbers should remain").containsExactly(1, 3);
        }

        @Test
        @DisplayName("Should return empty list when source is null")
        void removeIf_ShouldReturnEmpty_WhenSourceIsNull() {
            // given

            // when
            List<Integer> result = CollectionUtils.removeIf(null, _ -> true);

            // then
            then(result).as("Null source should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty list when source is empty")
        void removeIf_ShouldReturnEmpty_WhenSourceIsEmpty() {
            // given
            Collection<Integer> source = List.of();
            Predicate<Integer> predicate = _ -> true;

            // when
            List<Integer> result = CollectionUtils.removeIf(source, predicate);

            // then
            then(result).as("Empty source should yield empty list").isEmpty();
        }

        @Test
        @DisplayName("Should return copy of source when predicate is null")
        void removeIf_ShouldReturnCopyOfSource_WhenPredicateIsNull() {
            // given
            Collection<Integer> source = List.of(1, 2, 3);

            // when
            List<Integer> result = CollectionUtils.removeIf(source, null);

            // then
            then(result).as("Null predicate should return copy of source").containsExactly(1, 2, 3);
        }
    }

    @Nested
    @DisplayName("replaceAll")
    class ReplaceAll {

        @Test
        @DisplayName("Should replace elements using operator")
        void replaceAll_ShouldReplaceElements_WhenOperatorValid() {
            // given
            List<Integer> source = List.of(1, 2, 3);
            UnaryOperator<Integer> operator = n -> n * 2;

            // when
            List<Integer> result = CollectionUtils.replaceAll(source, operator);

            // then
            then(result).as("Elements doubled").containsExactly(2, 4, 6);
        }

        @Test
        @DisplayName("Should return empty when source is null")
        void replaceAll_ShouldReturnEmpty_WhenSourceIsNull() {
            // given

            // when
            List<Integer> result = CollectionUtils.replaceAll(null, n -> n);

            // then
            then(result).as("Null source should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty list when source is empty")
        void replaceAll_ShouldReturnEmpty_WhenSourceIsEmpty() {
            // given
            List<Integer> source = List.of();
            UnaryOperator<Integer> operator = n -> n * 2;

            // when
            List<Integer> result = CollectionUtils.replaceAll(source, operator);

            // then
            then(result).as("Empty source should yield empty list").isEmpty();
        }

        @Test
        @DisplayName("Should return copy of source when operator is null")
        void replaceAll_ShouldReturnCopyOfSource_WhenOperatorIsNull() {
            // given
            List<Integer> source = List.of(1, 2, 3);

            // when
            List<Integer> result = CollectionUtils.replaceAll(source, null);

            // then
            then(result).as("Null operator should return copy of source").containsExactly(1, 2, 3);
        }
    }

    @Nested
    @DisplayName("filterIterator")
    class FilterIterator {

        @Test
        @DisplayName("Should iterate only matching elements")
        void filterIterator_ShouldYieldMatching_WhenPredicateValid() {
            // given
            Collection<Integer> source = List.of(1, 2, 3, 4);
            Predicate<Integer> predicate = n -> n % 2 == 0;

            // when
            Iterator<Integer> iterator = CollectionUtils.filterIterator(source, predicate);

            // then
            then(iterator.hasNext()).as("Should have next").isTrue();
            then(iterator.next()).as("First even").isEqualTo(2);
            then(iterator.next()).as("Second even").isEqualTo(4);
            then(iterator.hasNext()).as("No more").isFalse();
        }

        @Test
        @DisplayName("Should throw NoSuchElementException when no more elements")
        void filterIterator_ShouldThrowNoSuchElementException_WhenNoMore() {
            // given
            Collection<Integer> source = List.of(1);
            Iterator<Integer> iterator = CollectionUtils.filterIterator(source, n -> n > 10);

            // when & then
            thenThrownBy(iterator::next)
                    .as("No matching should throw")
                    .isInstanceOf(java.util.NoSuchElementException.class);
        }

        @Test
        @DisplayName("Should return empty iterator when source is null")
        void filterIterator_ShouldReturnEmpty_WhenSourceIsNull() {
            // given

            // when
            Iterator<Integer> iterator = CollectionUtils.filterIterator(null, _ -> true);

            // then
            then(iterator.hasNext()).as("Empty iterator").isFalse();
        }

        @Test
        @DisplayName("Should return empty iterator when source is empty")
        void filterIterator_ShouldReturnEmpty_WhenSourceIsEmpty() {
            // given
            Collection<Integer> source = List.of();
            Predicate<Integer> predicate = _ -> true;

            // when
            Iterator<Integer> iterator = CollectionUtils.filterIterator(source, predicate);

            // then
            then(iterator.hasNext()).as("Empty source should yield empty iterator").isFalse();
        }

        @Test
        @DisplayName("Should return empty iterator when predicate is null")
        void filterIterator_ShouldReturnEmpty_WhenPredicateIsNull() {
            // given
            Collection<Integer> source = List.of(1, 2, 3);

            // when
            Iterator<Integer> iterator = CollectionUtils.filterIterator(source, null);

            // then
            then(iterator.hasNext()).as("Null predicate should yield empty iterator").isFalse();
        }
    }

    @Nested
    @DisplayName("listIterator")
    class ListIteratorMethod {

        @Test
        @DisplayName("Should return list iterator when valid")
        void listIterator_ShouldReturnIterator_WhenValid() {
            // given
            List<String> list = List.of("a", "b");

            // when
            ListIterator<String> iterator = CollectionUtils.listIterator(list);

            // then
            then(iterator.hasNext()).as("Should have elements").isTrue();
        }

        @Test
        @DisplayName("Should return empty iterator when list is null")
        void listIterator_ShouldReturnEmpty_WhenListIsNull() {
            // given

            // when
            ListIterator<String> iterator = CollectionUtils.listIterator(null, 0);

            // then
            then(iterator.hasNext()).as("Null list should yield empty iterator").isFalse();
        }

        @Test
        @DisplayName("Should return empty iterator when list is null")
        void listIterator_ShouldReturnEmptyIterator_WhenListIsNull() {
            // given

            // when
            ListIterator<String> iterator = CollectionUtils.listIterator(null);

            // then
            then(iterator.hasNext()).as("Empty iterator has no next").isFalse();
            then(iterator.hasPrevious()).as("Empty iterator has no previous").isFalse();
            then(iterator.nextIndex()).as("nextIndex should be 0").isEqualTo(0);
            then(iterator.previousIndex()).as("previousIndex should be -1").isEqualTo(-1);
        }

        @Test
        @DisplayName("Should throw NoSuchElementException on next when list is null")
        void listIterator_ShouldThrowOnNext_WhenListIsNull() {
            // given
            ListIterator<String> iterator = CollectionUtils.listIterator(null);

            // when & then
            thenThrownBy(iterator::next)
                    .as("Empty iterator next should throw")
                    .isInstanceOf(NoSuchElementException.class);
        }

        @Test
        @DisplayName("Should throw NoSuchElementException on previous when list is null")
        void listIterator_ShouldThrowOnPrevious_WhenListIsNull() {
            // given
            ListIterator<String> iterator = CollectionUtils.listIterator(null);

            // when & then
            thenThrownBy(iterator::previous)
                    .as("Empty iterator previous should throw")
                    .isInstanceOf(NoSuchElementException.class);
        }

        @Test
        @DisplayName("Should throw UnsupportedOperationException on add when list is null")
        void listIterator_ShouldThrowOnAdd_WhenListIsNull() {
            // given
            ListIterator<String> iterator = CollectionUtils.listIterator(null);

            // when & then
            thenThrownBy(() -> iterator.add("a"))
                    .as("Empty iterator add should throw")
                    .isInstanceOf(UnsupportedOperationException.class);
        }

        @Test
        @DisplayName("Should throw IllegalStateException on remove when list is null")
        void listIterator_ShouldThrowOnRemove_WhenListIsNull() {
            // given
            ListIterator<String> iterator = CollectionUtils.listIterator(null);

            // when & then
            thenThrownBy(iterator::remove)
                    .as("Empty iterator remove should throw IllegalStateException")
                    .isInstanceOf(IllegalStateException.class);
        }

        @Test
        @DisplayName("Should throw IllegalStateException on set when list is null")
        void listIterator_ShouldThrowOnSet_WhenListIsNull() {
            // given
            ListIterator<String> iterator = CollectionUtils.listIterator(null);

            // when & then
            thenThrownBy(() -> iterator.set("a"))
                    .as("Empty iterator set should throw IllegalStateException")
                    .isInstanceOf(IllegalStateException.class);
        }

        @Test
        @DisplayName("Should return empty iterator when list is empty")
        void listIterator_ShouldReturnEmptyIterator_WhenListIsEmpty() {
            // given
            List<String> list = List.of();

            // when
            ListIterator<String> iterator = CollectionUtils.listIterator(list);

            // then
            then(iterator.hasNext()).as("Empty list has no next").isFalse();
            then(iterator.hasPrevious()).as("Empty list has no previous").isFalse();
        }

        @Test
        @DisplayName("Should iterate all elements in forward order")
        void listIterator_ShouldIterateForward_WhenListNonEmpty() {
            // given
            List<String> list = List.of("a", "b", "c");

            // when
            ListIterator<String> iterator = CollectionUtils.listIterator(list);

            // then
            then(iterator.next()).isEqualTo("a");
            then(iterator.next()).isEqualTo("b");
            then(iterator.next()).isEqualTo("c");
            then(iterator.hasNext()).isFalse();
        }

        @Test
        @DisplayName("Should support bidirectional navigation")
        void listIterator_ShouldSupportBidirectionalNavigation_WhenValid() {
            // given
            List<String> list = List.of("a", "b", "c");

            // when
            ListIterator<String> iterator = CollectionUtils.listIterator(list);
            iterator.next();
            iterator.next();

            // then
            then(iterator.previous()).as("Should go back to b").isEqualTo("b");
            then(iterator.previous()).as("Should go back to a").isEqualTo("a");
            then(iterator.hasPrevious()).as("No previous from start").isFalse();
        }

        @Test
        @DisplayName("Should allow mutation via iterator on mutable list")
        void listIterator_ShouldAllowMutation_WhenListMutable() {
            // given
            List<String> list = new ArrayList<>(List.of("a", "b"));

            // when
            ListIterator<String> iterator = CollectionUtils.listIterator(list);
            iterator.next();
            iterator.set("A");
            iterator.add("x");

            // then
            then(list).as("List should reflect iterator mutations").containsExactly("A", "x", "b");
        }

        @Test
        @DisplayName("Should return iterator positioned at start")
        void listIterator_ShouldBePositionedAtStart_WhenListNonEmpty() {
            // given
            List<String> list = List.of("a", "b");

            // when
            ListIterator<String> iterator = CollectionUtils.listIterator(list);

            // then
            then(iterator.nextIndex()).as("Initial nextIndex should be 0").isEqualTo(0);
            then(iterator.previousIndex()).as("Initial previousIndex should be -1").isEqualTo(-1);
        }

        @Test
        @DisplayName("Should reflect same underlying list, not a copy")
        void listIterator_ShouldOperateOnSameList_WhenValid() {
            // given
            List<String> list = new ArrayList<>(List.of("a", "b"));
            ListIterator<String> iterator = CollectionUtils.listIterator(list);

            // when
            iterator.next();
            iterator.set("A");

            // then
            then(list.getFirst()).as("set should modify the original list").isEqualTo("A");
        }
    }

    @Nested
    @DisplayName("listIterator with index")
    class ListIteratorWithIndex {

        @Test
        @DisplayName("Should return iterator starting at index")
        void listIterator_ShouldStartAtIndex_WhenValid() {
            // given
            List<String> list = List.of("a", "b", "c");

            // when
            ListIterator<String> iterator = CollectionUtils.listIterator(list, 1);

            // then
            then(iterator.next()).as("Should start at b").isEqualTo("b");
        }

        @Test
        @DisplayName("Should return empty when index invalid")
        void listIterator_ShouldReturnEmpty_WhenIndexInvalid() {
            // given
            List<String> list = List.of("a");

            // when
            ListIterator<String> iterator = CollectionUtils.listIterator(list, 5);

            // then
            then(iterator.hasNext()).as("Empty for invalid index").isFalse();
        }

        @Test
        @DisplayName("Should return empty iterator when list is null")
        void listIterator_ShouldReturnEmpty_WhenListIsNull() {
            // given

            // when
            ListIterator<String> iterator = CollectionUtils.listIterator(null, 0);

            // then
            then(iterator.hasNext()).as("Null list should yield empty iterator").isFalse();
        }

        @Test
        @DisplayName("Should return empty iterator when index is negative")
        void listIterator_ShouldReturnEmpty_WhenIndexIsNegative() {
            // given
            List<String> list = List.of("a", "b");

            // when
            ListIterator<String> iterator = CollectionUtils.listIterator(list, -1);

            // then
            then(iterator.hasNext()).as("Negative index should yield empty iterator").isFalse();
        }

        @Test
        @DisplayName("Should return iterator at end when index equals size")
        void listIterator_ShouldReturnIteratorAtEnd_WhenIndexEqualsSize() {
            // given
            List<String> list = List.of("a", "b");

            // when
            ListIterator<String> iterator = CollectionUtils.listIterator(list, list.size());

            // then
            then(iterator.hasNext()).as("Index equal to size should point to end").isFalse();
            then(iterator.hasPrevious()).as("Should have previous at end").isTrue();
        }
    }

    @Nested
    @DisplayName("toList")
    class ToList {

        @Test
        @DisplayName("Should collect remaining elements")
        void toList_ShouldCollectElements_WhenIteratorValid() {
            // given
            List<String> source = List.of("a", "b", "c");
            Iterator<String> iterator = source.iterator();
            iterator.next();

            // when
            List<String> result = CollectionUtils.toList(iterator);

            // then
            then(result).as("Remaining elements").containsExactly("b", "c");
        }

        @Test
        @DisplayName("Should return empty when iterator is null")
        void toList_ShouldReturnEmpty_WhenIteratorIsNull() {
            // given

            // when
            List<String> result = CollectionUtils.toList(null);

            // then
            then(result).as("Null iterator should yield empty").isEmpty();
        }
    }

    @Nested
    @DisplayName("advance")
    class Advance {

        @Test
        @DisplayName("Should return element after advancing")
        void advance_ShouldReturnElement_WhenStepsValid() {
            // given
            List<String> source = List.of("a", "b", "c");
            Iterator<String> iterator = source.iterator();

            // when
            Optional<String> result = CollectionUtils.advance(iterator, 2);

            // then
            then(result).as("Element after 2 steps").contains("b");
        }

        @Test
        @DisplayName("Should return empty when steps is zero")
        void advance_ShouldReturnEmpty_WhenStepsIsZero() {
            // given
            List<String> source = List.of("a");
            Iterator<String> iterator = source.iterator();

            // when
            Optional<String> result = CollectionUtils.advance(iterator, 0);

            // then
            then(result).as("Zero steps should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return empty when iterator is null")
        void advance_ShouldReturnEmpty_WhenIteratorIsNull() {
            // given

            // when
            Optional<String> result = CollectionUtils.advance(null, 1);

            // then
            then(result).as("Null iterator should yield empty").isEmpty();
        }

        @Test
        @DisplayName("Should return last available element when steps exceed remaining")
        void advance_ShouldReturnLastElement_WhenStepsExceedRemaining() {
            // given
            List<String> source = List.of("a", "b");
            Iterator<String> iterator = source.iterator();

            // when
            Optional<String> result = CollectionUtils.advance(iterator, 5);

            // then
            then(result).as("Should return last element when steps exceed size").contains("b");
        }

        @SuppressWarnings("RedundantOperationOnEmptyContainer")
        @Test
        @DisplayName("Should return empty when iterator is already exhausted")
        void advance_ShouldReturnEmpty_WhenIteratorIsExhausted() {
            // given
            List<String> source = List.of();
            Iterator<String> iterator = source.iterator();

            // when
            Optional<String> result = CollectionUtils.advance(iterator, 1);

            // then
            then(result).as("Exhausted iterator should yield empty").isEmpty();
        }
    }

    // -------------------------------------------------------------------------
    // Helpers and test doubles
    // -------------------------------------------------------------------------

    @SneakyThrows
    private static Object newPartition(List<?> list, int size) {
        Class<?> partitionClass = Class.forName("ir.artanpg.commons.utils.CollectionUtils$Partition");
        Constructor<?> constructor = partitionClass.getDeclaredConstructor(List.class, int.class);
        constructor.setAccessible(true);
        return constructor.newInstance(list, size);
    }

    record Point(int x, int y) {
    }

    record Person(String name, int age) {
    }
}
