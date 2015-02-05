package ssj.algorithm.collections;

import ssj.algorithm.string.StringBuilder;

import java.util.Iterator;

/**
 * Created by shenshijun on 15/2/5.
 */
public class Trie {
    private static class Node {
        private char value;
        private LinkedList<Node> nexts;

        public Node(char value) {
            this.value = value;
            nexts = new LinkedList<>();
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Node{");
            sb.append("value=");
            sb.append(value);
            sb.append('}');
            return sb.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Node node = (Node) o;

            if (value != node.value) return false;
            if (nexts != null ? !nexts.equals(node.nexts) : node.nexts != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = (int) value;
            result = 31 * result + (nexts != null ? nexts.hashCode() : 0);
            return result;
        }

        public char getValue() {

            return value;
        }

        public Node getNext(char c) {
            for (Node node : nexts) {
                if (node.getValue() == c) {
                    return node;
                }
            }
            return null;
        }

        public void removeNext(char c) {
            Iterator<Node> iterator = nexts.iterator();
            while (iterator.hasNext()) {
                if (iterator.next().getValue() == c) {
                    iterator.remove();
                }
            }
        }

        public void addNode(char c) {
            if (getNext(c) == null) {
                this.nexts.add(new Node(c));
            }
        }
    }
}
