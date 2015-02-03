package ssj.algorithm.collections;

import com.google.common.base.Preconditions;
import ssj.algorithm.Collection;
import ssj.algorithm.List;
import ssj.algorithm.Queue;
import ssj.algorithm.string.StringBuilder;

import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

/**
 * 双向链表实现
 * Created by shenshijun on 15/2/1.
 */
public class LinkedList<T> implements List<T>, Queue<T> {

    private Node empty_node;
    private Node _head;
    private int _size;

    public LinkedList() {
        empty_node = emptyNode();
        _head = empty_node;
        _size = 0;
    }

    @Override
    public T head() {
        return _head.getNext().getValue();
    }

    @Override
    public T tail() {
        return _head.getPrev().getValue();
    }

    @Override
    public void append(T ele) {
        add(ele);
    }

    @Override
    public void appendHead(T ele) {
        Node head_node = new Node(ele, _head, _head.getNext());
        _head.getNext().setPrev(head_node);
        _head.setNext(head_node);
        _size++;
    }

    @Override
    public T get(int index) {
        Preconditions.checkPositionIndex(index, size());
        int cur_index = 0;
        for (T node : this) {
            if (cur_index == index) {
                return node;
            }
            cur_index++;
        }
        return null;
    }

    @Override
    public void set(int index, T ele) {
        Preconditions.checkPositionIndex(index, size());
        Node cur_node = _head.getNext();
        int cur_pos = 0;
        while (cur_node != empty_node && cur_pos < index) {
            cur_node = cur_node.getNext();
            cur_pos++;
        }
        cur_node.setValue(ele);
    }

    @Override
    public void remove(int index) {
        Preconditions.checkPositionIndex(index, size());
        int cur_index = 0;
        Iterator<T> iterator = iterator();
        while (iterator.hasNext()) {
            if (cur_index == index) {
                iterator.remove();
                break;
            }
            cur_index++;
        }
    }

    @Override
    public List<T> partition(T par_ele, Comparator<T> comparator) {
        Preconditions.checkNotNull(par_ele);
        Preconditions.checkNotNull(comparator);
        Node less_node = _head.getNext();
        Node equal_node = less_node;
        Node cur_node = less_node;
        while (cur_node != empty_node) {
            int c = comparator.compare(par_ele, cur_node.getValue());
            if (c > 0) {
                swapValue(equal_node, cur_node);
                swapValue(less_node, equal_node);
                equal_node = equal_node.getNext();
                less_node = less_node.getNext();
            } else if (c == 0) {
                swapValue(equal_node, cur_node);
                equal_node = equal_node.getNext();
            }
            cur_node = cur_node.getNext();
        }
        return this;
    }

    private void swapValue(Node first, Node second) {
        Preconditions.checkNotNull(first);
        Preconditions.checkNotNull(second);
        T tmp = first.getValue();
        first.setValue(second.getValue());
        second.setValue(tmp);
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LinkList{");
        for (T ele : this) {
            sb.append(ele);
            sb.append(',');
        }
        sb.append("}");
        return sb.toString();
    }

    @Override
    public void add(T ele) {
        Node tail = _head.getPrev();
        Node new_node = new Node(ele, tail, _head);
        tail.setNext(new_node);
        _head.setPrev(new_node);
        _size++;
    }

    @Override
    public <R> Collection<R> newWithCapacity(int size) {
        return new LinkedList<>();
    }

    @Override
    public int size() {
        return _size;
    }

    @Override
    public int capacity() {
        return _size;
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedItr(_head, size());
    }

    private class LinkedItr implements Iterator<T> {

        private int size;
        private Node cur_node;

        public LinkedItr(Node head, int _size) {
            size = _size;
            cur_node = head;
        }

        @Override
        public boolean hasNext() {
            checkCurrencyModify();
            cur_node = cur_node.getNext();
            return cur_node != empty_node;
        }

        @Override
        public T next() {
            checkCurrencyModify();
            return cur_node.getValue();
        }

        @Override
        public void remove() {
            checkCurrencyModify();
            delete(cur_node);
            size--;
            LinkedList.this._size--;

        }

        private void delete(Node ele) {
            Node pre_node = ele.getPrev();
            Node next_node = ele.getNext();
            pre_node.setNext(next_node);
            next_node.setPrev(pre_node);
            cur_node = pre_node;
        }

        private void checkCurrencyModify() {
            if (LinkedList.this.size() != size) {
                System.out.println(_size);
                System.out.println(size);
                throw new ConcurrentModificationException();
            }
        }
    }

    private Node emptyNode() {
        Node empty = new Node();
        empty.setValue(null);
        empty.setNext(empty);
        empty.setPrev(empty);
        return empty;
    }

    private class Node {
        private T value;
        private Node prev;
        private Node next;

        public Node() {
        }

        public Node(T value, Node prev, Node next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public Node getPrev() {
            return prev;
        }

        public void setPrev(Node prev) {
            this.prev = prev;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Node node = (Node) o;

            if (next != null ? !next.equals(node.next) : node.next != null) return false;
            if (prev != null ? !prev.equals(node.prev) : node.prev != null) return false;
            if (value != null ? !value.equals(node.value) : node.value != null) return false;

            return true;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Node{");
            sb.append("value=");
            sb.append(value);
            sb.append(", prev=");
            sb.append(prev);
            sb.append(", next=");
            sb.append(next);
            sb.append('}');
            return sb.toString();
        }

        @Override
        public int hashCode() {
            int result = value != null ? value.hashCode() : 0;
            result = 31 * result + (prev != null ? prev.hashCode() : 0);
            result = 31 * result + (next != null ? next.hashCode() : 0);
            return result;
        }
    }
}
