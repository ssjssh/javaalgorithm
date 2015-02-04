package ssj.algorithm.collections;

import ssj.algorithm.Stack;
import ssj.algorithm.string.StringBuilder;

import java.util.Iterator;


/**
 * Created by shenshijun on 15/2/1.
 */
public class LinkedStack<T> implements Stack<T> {
    private LinkedList<T> _list;

    public LinkedStack() {
        _list = new LinkedList<>();
    }

    public void push(T ele) {
        _list.appendHead(ele);
    }

    public T pop() {
        T head = head();
        _list.remove(0);
        return head;
    }

    public T head() {
        return _list.head();
    }

    public int size() {
        return _list.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Stack{");
        Iterator<T> reverse_iterator = _list.reverse();
        while (reverse_iterator.hasNext()) {
            sb.append(reverse_iterator.next());
            sb.add(',');
        }
        sb.append("}");
        return sb.toString();
    }

}
