package ssj.algorithm.collections;

import com.google.common.base.Preconditions;
import ssj.algorithm.Collection;
import ssj.algorithm.Set;
import ssj.algorithm.string.StringBuilder;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by shenshijun on 15/2/5.
 */
public class SkipList<T extends Comparable<? super T>> implements Set<T> {
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
        Preconditions.checkNotNull(ele);
        Node cur_node = values.tail();
        Node new_node = new Node(ele);
        while (cur_node != null) {
            int com_res = cur_node.compareTo(new_node);
            if (com_res == 0) {
                return true;
            } else if (com_res > 0) {
                cur_node = cur_node.getPrev().getDown();
            } else {
                cur_node = cur_node.getNext();
            }
        }
        return false;
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
                pre_node = cur_node;
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
        new_node.setPrev(cur_node);
        new_node.setNext(cur_node.getNext());
        if (cur_node.getNext() != null) {
            cur_node.getNext().setPrev(new_node);
        }
        cur_node.setNext(new_node);
        moveUp(cur_node, new_node);
    }

    private void moveUp(Node pre_node, Node new_node) {
        boolean need_new_level = true;
        Node last_level_node = new_node;
        Node cur_node = pre_node;
        while (rand.nextBoolean() && need_new_level) {
            Node new_up_node = new Node(new_node.getValue());
            new_up_node.setDown(last_level_node);
            last_level_node.setUp(new_up_node);
            while (cur_node != null && cur_node.getUp() == null) {
                cur_node = cur_node.getPrev();
            }

            if (cur_node == null) {
                cur_node = addNewLevel();
                need_new_level = false;
            } else {
                cur_node = cur_node.getUp();
            }

            new_up_node.setPrev(cur_node);
            new_up_node.setNext(cur_node.getNext());
            if (cur_node.getNext() != null) {
                cur_node.getNext().setPrev(new_up_node);
            }
            cur_node.setNext(new_up_node);
            last_level_node = new_up_node;
        }
    }

    private Node addNewLevel() {
        Node head_node = new EmptyNode();
        Node end_node = new EndNode();
        head_node.setNext(end_node);
        if (values.tail() != null) {
            values.tail().setUp(head_node);
        }
        head_node.setDown(values.tail());
        end_node.setPrev(head_node);
        values.appendTail(head_node);
        return head_node;
    }

    @Override
    public void delete(T ele) {
        Preconditions.checkNotNull(ele);
        Node cur_node = values.tail();
        Node new_node = new Node(ele);
        while (cur_node != null) {
            int com_res = cur_node.compareTo(new_node);
            if (com_res == 0) {
                deleteNode(cur_node);
                size--;
                return;
            } else if (com_res > 0) {
                cur_node = cur_node.getPrev().getDown();
            } else {
                cur_node = cur_node.getNext();
            }
        }
    }

    private void deleteNode(Node cur_node) {
        Node delete_node = cur_node;
        while (delete_node != null) {
            delete_node.getNext().setPrev(delete_node.getPrev());
            delete_node.getPrev().setNext(delete_node.getNext());
            delete_node = delete_node.getDown();
        }

        delete_node = cur_node;
        while (delete_node != null) {
            delete_node.getNext().setPrev(delete_node.getPrev());
            delete_node.getPrev().setNext(delete_node.getNext());
            delete_node = delete_node.getUp();
        }
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
        return new SkipListItr(size());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("SkipList=[\n");
        for (Node node : values) {
            while (node != null) {
                sb.append(node);
                sb.append(" , ");
                node = node.getNext();
            }
            sb.append("\n");
        }
        sb.append("\n]");
        return sb.toString();
    }

    private class SkipListItr implements Iterator<T> {

        private int size;
        private Node cur_node;

        SkipListItr(int _size) {
            size = _size;
            cur_node = values.head();
        }

        @Override
        public boolean hasNext() {
            checkCurrencyModify();
            cur_node = cur_node.getNext();
            return !cur_node.isEnd();
        }

        @Override
        public T next() {
            checkCurrencyModify();
            return cur_node.getValue();
        }

        @Override
        public void remove() {
            checkCurrencyModify();
            Node old = cur_node.getPrev();
            SkipList.this.delete(next());
            cur_node = old;
            this.size--;
        }

        private void checkCurrencyModify() {
            if (SkipList.this.size() != this.size) {
                throw new ConcurrentModificationException();
            }
        }
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
        public String toString() {
            final StringBuilder sb = new StringBuilder("Node{");
            sb.append("value=");
            sb.append(String.valueOf(value));
            sb.append("}");
            return sb.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            @SuppressWarnings("unchecked")
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

        @Override
        public String toString() {
            return "EmptyNode";
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

        @Override
        public String toString() {
            return "EndNode";
        }
    }
}
