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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;

/**
 * Implements the Aho-Corasick algorithm for efficient multi-pattern string
 * matching.
 *
 * <p>This class builds a Trie from patterns and uses failure links to search
 * for any pattern in a given text efficiently.
 *
 * @author Mohammad Yazdian
 */
class AhoCorasick {

    /**
     * The root node of the Trie.
     */
    private final TrieNode root;

    /**
     * Creates a new Aho-Corasick automaton with an empty Trie.
     */
    public AhoCorasick() {
        root = new TrieNode();
    }

    /**
     * Adds a pattern to the Trie using Unicode code points.
     *
     * @param pattern the pattern to add to the Trie
     * @throws IllegalArgumentException if the pattern is {@code null}
     */
    public void addPattern(String pattern) {
        TrieNode current = root;
        int i = 0;
        while (i < pattern.length()) {
            int codePoint = Character.codePointAt(pattern, i);
            current = current.getChildren().computeIfAbsent(codePoint, k -> new TrieNode());
            i += Character.charCount(codePoint);
        }
        current.setEndOfWord(true);
    }

    /**
     * Builds failure links for all nodes in the Trie using BFS.
     *
     * <p>Failure links enable efficient pattern matching by pointing to the
     * longest proper suffix of the current prefix that is also a prefix in the
     * Trie.
     */
    public void buildFailureLinks() {
        Queue<TrieNode> queue = new LinkedList<>();
        root.setFailureLink(root);

        for (TrieNode child : root.getChildren().values()) {
            child.setFailureLink(root);
            queue.add(child);
        }

        while (!queue.isEmpty()) {
            TrieNode current = queue.poll();
            for (Map.Entry<Integer, TrieNode> entry : current.getChildren().entrySet()) {
                int codePoint = entry.getKey();
                TrieNode child = entry.getValue();
                queue.add(child);

                TrieNode failure = current.getFailureLink();
                while (failure != root && !failure.getChildren().containsKey(codePoint)) {
                    failure = failure.getFailureLink();
                }
                TrieNode next = failure.getChildren().getOrDefault(codePoint, root);
                child.setFailureLink(next);

                // Propagate end-of-word (output link optimization)
                if (next.isEndOfWord()) child.setEndOfWord(true);
            }
        }
    }

    /**
     * Searches for any of the added patterns in the given text using Unicode
     * code points.
     *
     * @param text the text to search in
     * @return true if any pattern is found in the text, false otherwise
     * @throws IllegalArgumentException if the text is {@code null}
     */
    public boolean search(String text) {
        TrieNode current = root;
        int i = 0;
        while (i < text.length()) {
            int codePoint = Character.codePointAt(text, i);
            int charCount = Character.charCount(codePoint);

            while (current != root && !current.getChildren().containsKey(codePoint)) {
                current = current.getFailureLink();
            }
            current = current.getChildren().getOrDefault(codePoint, root);

            if (current.isEndOfWord()) return true;
            i += charCount;
        }
        return false;
    }

    /**
     * A node in the Trie structure used for the Aho-Corasick algorithm.
     */
    static class TrieNode {

        /**
         * Flag indicating if this node marks the end of a pattern.
         */
        private boolean endOfWord;

        /**
         * Failure link to another TrieNode for Aho-Corasick pattern matching.
         */
        private TrieNode failureLink;

        /**
         * Map of child nodes, keyed by Unicode code point.
         */
        private final Map<Integer, TrieNode> children;

        /**
         * Creates an empty Trie node with no children.
         */
        public TrieNode() {
            this.endOfWord = false;
            this.failureLink = null;
            this.children = new HashMap<>();
        }

        /**
         * Checks if this node marks the end of a pattern.
         *
         * @return true if this node marks the end of a pattern, false otherwise
         */
        public boolean isEndOfWord() {
            return endOfWord;
        }

        /**
         * Sets the flag indicating whether this node marks the end of a
         * pattern.
         *
         * @param endOfWord true to mark this node as the end of a pattern, false otherwise
         */
        public void setEndOfWord(boolean endOfWord) {
            this.endOfWord = endOfWord;
        }

        /**
         * Gets the failure link of this node.
         *
         * @return the TrieNode representing the failure link
         */
        public TrieNode getFailureLink() {
            return failureLink;
        }

        /**
         * Sets the failure link of this node.
         *
         * @param failureLink the TrieNode to set as the failure link
         */
        public void setFailureLink(TrieNode failureLink) {
            this.failureLink = failureLink;
        }

        /**
         * Gets the map of child nodes.
         *
         * @return the map of child nodes, keyed by code point
         */
        public Map<Integer, TrieNode> getChildren() {
            return children;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof TrieNode trieNode)) return false;
            return endOfWord == trieNode.endOfWord &&
                    Objects.equals(failureLink, trieNode.failureLink) &&
                    Objects.equals(children, trieNode.children);
        }

        @Override
        public int hashCode() {
            return Objects.hash(endOfWord, failureLink, children);
        }
    }
}
