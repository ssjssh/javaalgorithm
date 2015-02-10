package ssj.algorithm.collections;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import ssj.algorithm.Collection;
import ssj.algorithm.Set;

import java.util.Iterator;
import java.util.Random;

/**
 * Created by shenshijun on 15/2/5.
 */
public class SkipList<T extends Comparable<T>> implements Set<T> {
    private LinkedList<Node> values;
    private int size;
    private Random rand;

    public SkipList() {
        values = new LinkedList<>();
        addNewLevel();
        rand = new Random();
    }

    @Override
    public boolean contains(T ele) {
        return false;
    }

    private <R> R search(T ele, Function<T, R> succ_func, Function<T, R> fail_func) {
        Preconditions.checkNotNull(ele);
        Node cur_node = values.tail();
        while (cur_node != null) {
            int com_res = cur_node.compareTo(new Node(ele));
            if (com_res == 0) {
                return null;
            } else if (com_res > 0) {
                cur_node = cur_node.getPrev().getDown();
            } else {
                cur_node = cur_node.getNext();
            }
        }
        return null;
    }

    @Override
    public void add(T ele) {
        Preconditions.checkNotNull(ele);
        Node cur_node = values.tail();
        Node new_node = new Node(ele);
        Node pre_node = cur_node;
        while (cur_node != null) {
            int com_res = cur_node.compareTo(new_node);
            //如果找到，则立即下降
            if (com_res == 0) {
                pre_node = cur_node;
                break;
            } else if (com_res > 0) {
                pre_node = cur_node.getPrev();
                cur_node = pre_node.getDown();
            } else {
                cur_node = cur_node.getNext();
            }
        }
        insertNode(pre_node, new_node);
        size++;
    }

    private void insertNode(Node pre_node, Node new_node) {
        Node cur_node = pre_node;
        while (cur_node.getDown() != null) {
            cur_node = cur_node.getDown();
        }
        moveUp(cur_node, new_node);
    }

    private void moveUp(Node pre_node, Node new_node) {
        int cur_level = values.size();
        Node cur_node = pre_node;
        Node new_up_node = new_node;
        while (rand.nextBoolean()) {
            if (cur_node == null) {
                if (values.size() <= cur_level + 1) {
                    cur_node = addNewLevel();
                } else {
                    break;
                }
            }
            new_up_node.setPrev(cur_node);
            new_up_node.setNext(cur_node.getNext());
            if (new_up_node.getDown() != null) {
                new_up_node.getDown().setUp(new_up_node);
            }
            if (cur_node.getNext() != null) {
                cur_node.getNext().setPrev(new_up_node);
            }
            cur_node.setDown(new_up_node);
            cur_node = cur_node.getUp();
            Node tmp = new Node(new_up_node.getValue());
            tmp.setDown(new_up_node);
            new_up_node = tmp;
        }
    }

    private Node addNewLevel() {
        Node head_node = new EmptyNode();
        Node end_node = new EndNode();
        head_node.setNext(end_node);
        if (values.tail() != null) {
            values.tail().setUp(head_node);
        }
        end_node.setPrev(head_node);
        values.appendHead(head_node);
        return head_node;
    }

    @Override
    public void delete(T ele) {

    }

    @Override
    public <R> Collection<R> newWithCapacity(int size) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int capacity() {
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    private class Node implements Comparable<Node> {
        private T value;
        private Node up;
        private Node down;
        private Node prev;
        private Node next;


        public Node(Node down, Node next, Node prev, Node up, T value) {

            this.down = down;
            this.next = next;
            this.prev = prev;
            this.up = up;
            this.value = value;
        }

        public Node(T value) {

            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Node node = (Node) o;

            if (!value.equals(node.value)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return value.hashCode();
        }

        public Node getDown() {

            return down;
        }

        public void setDown(Node down) {
            this.down = down;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public Node getPrev() {
            return prev;
        }

        public void setPrev(Node prev) {
            this.prev = prev;
        }

        public Node getUp() {
            return up;
        }

        public void setUp(Node up) {
            this.up = up;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public boolean isEmpty() {
            return false;
        }

        public boolean isEnd() {
            return false;
        }

        @Override
        public int compareTo(Node o) {
            if (this.isEmpty() || o.isEnd()) return -1;
            else if (o.isEmpty() || this.isEnd()) return 1;
            return getValue().compareTo(o.getValue());
        }
    }

    private class EmptyNode extends Node {

        public EmptyNode(Node down, Node next, Node prev, Node up) {
            super(down, next, prev, up, null);
        }

        public EmptyNode() {
            super(null);
        }

        @Override
        public boolean isEmpty() {
            return true;
        }
    }

    private class EndNode extends Node {

        public EndNode(Node down, Node next, Node prev, Node up) {
            super(down, next, prev, up, null);
        }

        public EndNode() {
            super(null);
        }

        @Override
        public boolean isEnd() {
            return true;
        }
    }
}
