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

public class PreconditionException extends IllegalArgumentException {

    private final String field;
    private final Object offendingValue;

    public PreconditionException(String field, Object offendingValue, String message) {
        super(buildMessage(field, offendingValue, message));
        this.field = field;
        this.offendingValue = offendingValue;
    }

    public String getField() {
        return field;
    }

    public Object getOffendingValue() {
        return offendingValue;
    }

    /**
     * Variant for state-based assertions.
     */
    public static class State extends IllegalStateException {
        private final String context;

        public State(String context, String message) {
            super(message != null ? message : context);
            this.context = context;
        }

        public String getContext() {
            return context;
        }
    }

    private static String buildMessage(String field, Object value, String customMsg) {
        if (customMsg != null && !customMsg.isBlank()) {
            return customMsg;
        }
        return String.format("Precondition failed for [%s] – value: %s", field, value);
    }
}
