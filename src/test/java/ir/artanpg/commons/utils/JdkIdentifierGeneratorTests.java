package ir.artanpg.commons.utils;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.assertj.core.api.BDDAssertions.then;

/**
 * Unit tests for the {@link JdkIdentifierGenerator} class.
 *
 * @author Mohammad Yazdian
 */
@DisplayName("JdkIdentifierGenerator")
class JdkIdentifierGeneratorTests {

    private final IdentifierGenerator generator = new JdkIdentifierGenerator();

    @Test
    void generateIdentifier_ShouldReturnNonNullUUID_WhenCalled() {
        // When
        UUID actual = generator.generateIdentifier();

        // Then
        then(actual).isNotNull();
    }

    @Test
    void generateIdentifier_ShouldReturnValidUUIDInstance_WhenCalled() {
        // When
        UUID actual = generator.generateIdentifier();

        // Then
        then(actual).isInstanceOf(UUID.class);
    }

    @Test
    void generateIdentifier_ShouldReturnDifferentUUIDs_WhenCalledMultipleTimes() {
        // Given
        int numberOfCalls = 100;

        // When
        List<UUID> uuids = IntStream.range(0, numberOfCalls)
                .mapToObj(i -> generator.generateIdentifier())
                .toList();

        // Then
        then(uuids)
                .hasSize(numberOfCalls)
                .doesNotHaveDuplicates();
    }

    @Test
    void generateIdentifier_ShouldReturnVersion4UUID_WhenCalled() {
        // When
        UUID actual = generator.generateIdentifier();

        // Then
        then(actual.version()).isEqualTo(4);
    }

    @Test
    void generateIdentifier_ShouldReturnVariant2UUID_WhenCalled() {
        // When
        UUID actual = generator.generateIdentifier();

        // Then
        then(actual.variant()).isEqualTo(2);
    }

    @Test
    void generateIdentifier_ShouldReturnUUIDWithValidStringRepresentation_WhenCalled() {
        // When
        UUID actual = generator.generateIdentifier();

        // Then
        String uuidString = actual.toString();

        then(uuidString)
                .isNotEmpty()
                .hasSize(36)
                .matches("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}");
    }

    @Test
    void generateIdentifier_ShouldBeThreadSafe_WhenCalledConcurrently() throws InterruptedException {
        // Given
        int threadCount = 50;
        int callsPerThread = 20;
        var results = java.util.Collections.synchronizedSet(new java.util.HashSet<UUID>());

        // When
        List<Thread> threads = IntStream.range(0, threadCount)
                .mapToObj(i -> new Thread(() -> {
                    for (int j = 0; j < callsPerThread; j++) {
                        UUID uuid = generator.generateIdentifier();
                        results.add(uuid);
                    }
                }))
                .toList();

        threads.forEach(Thread::start);
        for (Thread thread : threads) {
            thread.join();
        }

        // Then
        then(results)
                .hasSize(threadCount * callsPerThread)
                .allMatch(uuid -> uuid.version() == 4);
    }
}
