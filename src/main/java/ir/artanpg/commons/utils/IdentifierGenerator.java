package ir.artanpg.commons.utils;

import java.util.UUID;

/**
 * Contract for generating universally unique identifiers ({@link UUID UUIDs}).
 *
 * @author Mohammad Yazdian
 */
@FunctionalInterface
public interface IdentifierGenerator {

    /**
     * Generate a new identifier.
     *
     * @return the generated identifier
     */
    UUID generateIdentifier();
}
