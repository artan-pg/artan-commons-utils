package ir.artanpg.commons.utils;

import java.util.Arrays;
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

    public AhoCorasick() {
        root = new TrieNode();
    }

    /**
     * Adds a pattern to the compressed Patricia Trie using Unicode code
     * points.
     *
     * <p>Compresses single-child paths by merging them into a single edge
     * label.
     *
     * @param pattern the pattern to add to the Trie
     * @throws IllegalArgumentException if the pattern contains invalid Unicode characters
     */
    public void addPattern(String pattern) {
        if (!validateUnicodeString(pattern))
            throw new IllegalArgumentException("Pattern contains invalid Unicode characters: " + pattern);

        int[] codePoints = pattern.codePoints().toArray();
        if (codePoints.length == 0) return;

        TrieNode current = root;
        int i = 0;

        while (i < codePoints.length) {
            int currentCodePoint = codePoints[i];
            TrieNode nextNode = current.getChildren().get(currentCodePoint);
            if (nextNode == null) {
                // Add new node with remaining code points
                int[] remaining = Arrays.copyOfRange(codePoints, i, codePoints.length);
                current.getChildren().put(currentCodePoint, new TrieNode(remaining));
                current.getChildren().get(currentCodePoint).setEndOfWord(true);
                return;
            }

            int[] edgeLabel = nextNode.getEdgeLabel();
            int matchedLength = matchEdge(codePoints, i, edgeLabel);
            if (matchedLength == edgeLabel.length) {
                // Full match, move to next node
                current = nextNode;
                i += matchedLength;
            } else {
                // Partial match, split the node
                TrieNode newNode = new TrieNode(Arrays.copyOfRange(edgeLabel, matchedLength, edgeLabel.length));
                newNode.setChildren(nextNode.getChildren());
                newNode.setEndOfWord(nextNode.isEndOfWord());
                newNode.setFailureLink(nextNode.getFailureLink());

                nextNode.setEdgeLabel(Arrays.copyOfRange(edgeLabel, 0, matchedLength));
                nextNode.setChildren(new HashMap<>());
                nextNode.getChildren().put(edgeLabel[matchedLength], newNode);
                nextNode.setEndOfWord(false);

                current = nextNode;
                i += matchedLength;
            }
        }
        current.setEndOfWord(true);
    }

    /**
     * Matches as many code points as possible between the pattern and edge
     * label.
     *
     * @param codePoints the pattern code points
     * @param start      the starting index in the pattern
     * @param edgeLabel  the edge label to match against
     * @return the number of matched code points
     */
    private int matchEdge(int[] codePoints, int start, int[] edgeLabel) {
        int matched = 0;
        while (matched < edgeLabel.length && start + matched < codePoints.length &&
                codePoints[start + matched] == edgeLabel[matched]) {
            matched++;
        }
        return matched;
    }

    /**
     * Builds failure links for all nodes in the Patricia Trie using BFS.
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
                child.setFailureLink(failure.getChildren().getOrDefault(codePoint, root));
                if (child.getFailureLink().isEndOfWord()) {
                    child.setEndOfWord(true);
                }
            }
        }
    }

    /**
     * Searches for any of the added patterns in the given text using Unicode
     * code points in a compressed Patricia Trie.
     *
     * @param text the text to search in
     * @return true if any pattern is found in the text, false otherwise
     * @throws IllegalArgumentException if the text contains invalid Unicode characters
     */
    public boolean search(String text) {
        if (!validateUnicodeString(text))
            throw new IllegalArgumentException("Text contains invalid Unicode characters");

        TrieNode current = root;
        for (int i = 0; i < text.length(); ) {
            int codePoint = Character.codePointAt(text, i);
            if (current.getChildren().containsKey(codePoint)) {
                TrieNode next = current.getChildren().get(codePoint);
                int[] edgeLabel = next.getEdgeLabel();
                boolean matched = true;
                int j = i;
                for (int k = 0; k < edgeLabel.length && j < text.length(); k++) {
                    int textCodePoint = Character.codePointAt(text, j);
                    if (edgeLabel[k] != textCodePoint) {
                        matched = false;
                        break;
                    }
                    j += Character.charCount(textCodePoint);
                }
                if (matched && j <= text.length()) {
                    if (next.isEndOfWord()) return true;
                    current = next;
                    i = j;
                    continue;
                }
            }
            while (current != root && !current.getChildren().containsKey(codePoint)) {
                current = current.getFailureLink();
            }
            current = current.getChildren().getOrDefault(codePoint, root);
            if (current.isEndOfWord()) return true;
            i += Character.charCount(codePoint);
        }
        return false;
    }

    /**
     * Validates that the given CharSequence contains valid Unicode characters.
     *
     * @param string the CharSequence to validate
     * @return {@code true}, if valid
     */
    private boolean validateUnicodeString(String string) {
        if (string == null) return false;
        try {
            for (int i = 0; i < string.length(); ) {
                int codePoint = Character.codePointAt(string, i);
                if (!Character.isValidCodePoint(codePoint)) {
                    return false;
                }
                i += Character.charCount(codePoint);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * A node in the Trie structure used for the Aho-Corasick algorithm.
     *
     * <p>Each node represents a character in the pattern and contains links to
     * child nodes, a failure link for pattern matching, and a flag indicating
     * the end of a pattern.
     */
    static class TrieNode {

        /**
         * Flag indicating if this node marks the end of a pattern.
         */
        private boolean endOfWord;

        /**
         * Sequence of code points represented by this node's edge.
         */
        private int[] edgeLabel;

        /**
         * Failure link to another TrieNode for Aho-Corasick pattern matching.
         */
        private TrieNode failureLink;

        /**
         * Map of child nodes, keyed by the first Unicode code point of the edge.
         */
        private Map<Integer, TrieNode> children;

        public TrieNode() {
            this.endOfWord = false;
            this.edgeLabel = new int[0];
            this.failureLink = null;
            this.children = new HashMap<>();
        }

        public TrieNode(int[] edgeLabel) {
            this.children = new HashMap<>();
            this.failureLink = null;
            this.endOfWord = false;
            this.edgeLabel = edgeLabel != null ? edgeLabel : new int[0];
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
         * Gets the edge label of this node.
         *
         * @return the sequence of code points represented by this node
         */
        public int[] getEdgeLabel() {
            return edgeLabel;
        }

        /**
         * Sets the edge label of this node.
         *
         * @param edgeLabel the sequence of code points to set
         */
        public void setEdgeLabel(int[] edgeLabel) {
            this.edgeLabel = edgeLabel != null ? edgeLabel : new int[0];
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
         * @return the map of child nodes, keyed by character
         */
        public Map<Integer, TrieNode> getChildren() {
            return children;
        }

        /**
         * Sets the map of child nodes.
         *
         * @param children the map of child nodes to set
         */
        public void setChildren(Map<Integer, TrieNode> children) {
            this.children = children;
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
