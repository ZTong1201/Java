import java.util.List;
import java.util.ArrayList;

public class MyTrieSet implements TrieSet61B {

    private static final int R = 128;
    private Node root;

    public MyTrieSet() {
    }

    /** Clears all items out of Trie */
    @Override
    public void clear() {
        root = null;
    }

    /** Returns true if the Trie contains KEY, false otherwise */
    @Override
    public boolean contains(String key) {
        if(key == null) {
            throw new IllegalArgumentException();
        }
        return getKey(root, key, 0) != null;
    }

    private Node getKey(Node node, String key, int d) {
        if(node == null) {
            return null;
        }
        if(d == key.length() && node.isKey) {
            return node;
        }
        char c = key.charAt(d);
        return getKey(node.next[c], key, d + 1);
    }

    private Node get(Node node, String key, int d) {
        if(node == null) {
            return null;
        }
        if(d == key.length()) {
            return node;
        }
        char c = key.charAt(d);
        return get(node.next[c], key, d + 1);
    }

    /** Inserts string KEY into Trie */
    @Override
    public void add(String key) {
        if(key == null || key.length() < 1) {
            return;
        } else {
            root = put(root, key, 0);
        }

    }

    /** Returns a list of all words that start with PREFIX */
    @Override
    public List<String> keysWithPrefix(String prefix) {
        List<String> results = new ArrayList<>();
        Node node = get(root, prefix, 0);
        collect(node, new StringBuilder(prefix), results);
        return results;
    }

    private void collect(Node node, StringBuilder prefix, List<String> res) {
        if(node == null) {
            return;
        }
        if(node.isKey) {
            res.add(prefix.toString());
        }
        for(char c = 0; c < R; c++) {
            if(node.next[c] != null) {
                prefix.append(c);
                collect(node.next[c], prefix, res);
                prefix.deleteCharAt(prefix.length() - 1);
            }
        }
    }

    /** Returns the longest prefix of KEY that exists in the Trie */
    @Override
    public String longestPrefixOf(String key) {
        StringBuilder result = new StringBuilder();
        collect(root, key, result);
        return result.toString();
    }

    private void collect(Node node, String key, StringBuilder res) {
        if(node == null) return;
        for(int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            if(node.next[c] != null) {
                res.append(c);
                node = node.next[c];
            } else return;
        }
    }


    private Node put(Node node, String key, int d) {
        if(node == null) {
            node = new Node();
        }
        if(d == key.length()) {
            node.isKey = true;
            return node;
        }
        char c = key.charAt(d);
        node.next[c] = put(node.next[c], key, d + 1);
        return node;
    }

    private static class Node {
        private boolean isKey;
        private Node[] next;
        public Node() {
            isKey = false;
            next = new Node[R];
        }

        public Node(boolean key) {
            isKey = key;
            next = new Node[R];
        }

        public Node(boolean key, int num) {
            isKey = key;
            next = new Node[num];
        }
    }
}
