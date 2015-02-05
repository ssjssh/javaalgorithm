package ssj.algorithm.collections;

import ssj.algorithm.Collection;
import ssj.algorithm.Queue;

import java.util.Iterator;

/**
 * Created by shenshijun on 15/2/5.
 */
public class StackQueue<T> implements Queue<T> {
    private LinkedStack<T> _old_stack;
    private LinkedStack<T> _new_stack;

    public StackQueue() {
        _old_stack = new LinkedStack<>();
        _new_stack = new LinkedStack<>();
    }

    @Override
    public T head() {
        moveNew();
        return _old_stack.head();
    }

    @Override
    public T tail() {
        moveOld();
        return _new_stack.head();
    }

    @Override
    public void appendTail(T ele) {
        _new_stack.push(ele);
    }

    @Override
    public T removeHead() {
        moveNew();
        return _old_stack.pop();
    }

    private void moveNew() {
        if (_old_stack.isEmpty()) {
            while (!_new_stack.isEmpty()) {
                _old_stack.push(_new_stack.pop());
            }
        }
    }

    private void moveOld() {
        if (_new_stack.isEmpty()) {
            while (!_old_stack.isEmpty()) {
                _new_stack.push(_old_stack.pop());
            }
        }
    }

    @Override
    public void add(T ele) {
        appendTail(ele);
    }

    @Override
    public void delete(T ele) {
        throw new UnsupportedOperationException("delete on queue");
    }

    @Override
    public <R> Collection<R> newWithCapacity(int size) {
        return new StackQueue<>();
    }

    @Override
    public int size() {
        return _old_stack.size() + _new_stack.size();
    }

    @Override
    public int capacity() {
        return size();
    }

    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException("iterator on queue");
    }
}
