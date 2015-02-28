package ssj.algorithm.collections;

import com.google.common.base.Preconditions;
import ssj.algorithm.string.StringBuilder;

import java.util.Iterator;

/**
 * Created by shenshijun on 15/2/5.
 */
public class Trie {
    private static HashMap.MapBuilder<Character, Node> builder = HashMap.builder();
    private Node _head;

    public Trie() {
        _head = new Node(' ');
    }

    public void add(String str) {
        Preconditions.checkNotNull(str);
        if (str.isEmpty()) {
            return;
        }
        int char_index = 0;
        Node cur_node = _head;

        while (charExits(char_index, str, cur_node)) {
            char_index++;
            cur_node = cur_node.getNext(str.charAt(char_index));
        }

        while (char_index < str.length()) {
            cur_node.addNode(str.charAt(char_index));
            cur_node = cur_node.getNext(str.charAt(char_index));
            char_index++;
        }
        cur_node.setEnd(true);
    }

    private boolean charExits(int char_index, String str, Node cur_node) {
        return char_index < str.length() && cur_node.hasNext() && cur_node.contains(str.charAt(char_index));
    }

    public Vector<String> search(String prefix) {
        Preconditions.checkNotNull(prefix);
        Vector<String> result = new Vector<>(0);
        if (prefix.isEmpty()) {
            return result;
        }

        int char_index = 0;
        Node cur_node = _head;

        while (charExits(char_index, prefix, cur_node)) {
            char_index++;
            cur_node = cur_node.getNext(prefix.charAt(char_index));
        }

        if (char_index > prefix.length() - 1) {
            addString(prefix, cur_node, result);
        }
        return result;
    }

    private void addString(String prefix, Node start_node, Vector<String> result) {
        if (start_node.isEnd()) {
            result.add(prefix);
        }

        Iterator<Node> nexts = start_node.getNextNodes();
        while (nexts.hasNext()) {
            addString(prefix, nexts.next(), result);
        }
    }

    private static class Node {
        private char value;
        private boolean is_end;
        private HashMap<Character, Node> nexts;

        public Node(char value) {
            this.value = value;
            nexts = builder.build();
            this.is_end = false;
        }

        public boolean isEnd() {
            return is_end;
        }

        public void setEnd(boolean is_end) {
            this.is_end = is_end;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Node{");
            sb.append("value=");
            sb.append(value);
            sb.append(" , nexts=[");
            Iterator<Node> next_iterator = getNextNodes();
            while (next_iterator.hasNext()) {
                sb.append(next_iterator.hasNext());
            }
            sb.append("]");
            sb.append('}');
            return sb.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            @SuppressWarnings("unchecked")
            Node node = (Node) o;

            return value == node.value;

        }

        @Override
        public int hashCode() {
            return value;
        }

        public char getValue() {
            return value;
        }

        public Node getNext(char c) {
            return nexts.get(c);
        }

        public void removeNext(char c) {
            nexts.remove(c);
        }

        public void addNode(char c) {
            nexts.set(c, new Node(c));
        }

        public boolean contains(char c) {
            return nexts.containsKey(c);
        }

        public Iterator<Node> getNextNodes() {
            return nexts.valueIterator();
        }

        public boolean hasNext() {
            return !nexts.isEmpty();
        }
    }

}
