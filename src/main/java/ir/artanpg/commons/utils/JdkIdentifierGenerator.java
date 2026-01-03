package ir.artanpg.commons.utils;

import java.util.UUID;

/**
 * An {@link IdentifierGenerator} that calls {@link UUID#randomUUID()}.
 *
 * @author Mohammad Yazdian
 */
public class JdkIdentifierGenerator implements IdentifierGenerator {

    @Override
    public UUID generateIdentifier() {
        return UUID.randomUUID();
    }
}
