package ssj.algorithm.collections;

import com.google.common.base.Preconditions;
import ssj.algorithm.Collection;
import ssj.algorithm.string.StringBuilder;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

/**
 * 遵守C++的命名规则，和ArrayList类似
 * Created by shenshijun on 15/2/1.
 */
public class Vector<T> implements List<T> {
    private Object[] _values;
    private int _cur_pointer;


    public Vector(int capacity) {
        Preconditions.checkArgument(capacity>=0);
        _values = new Object[capacity];
        _cur_pointer = -1;
    }

    @Override
    public T get(int index) {
        Preconditions.checkPositionIndex(index, size());
        return (T) _values[index];
    }

    @Override
    public void set(int index, T ele) {
        Preconditions.checkNotNull(ele);
        Preconditions.checkPositionIndex(index, size());
        _values[index] = ele;
    }

    @Override
    public void remove(int index) {
        for (int i = index; i < size() - 1; i++) {
            _values[i] = _values[i + 1];
        }
        _cur_pointer--;
    }

    @Override
    public void add(T ele) {
        ensureCapacity();
        _values[++_cur_pointer] = ele;
    }

    @Override
    public <R> Collection<R> newWithCapacity(int size) {
        return new Vector<>(size);
    }

    @Override
    public int size() {
        return _cur_pointer + 1;
    }

    @Override
    public int capacity() {
        return _values.length;
    }

    @Override
    public Iterator<T> iterator() {
        return new ListItr(size());
    }

    private void ensureCapacity() {
        if (size() >= capacity()) {
            _values = Arrays.copyOf(_values, extendSize());
        }
    }

    private int extendSize() {
        return (capacity() + 1) * 2;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        this.forEach((ele) -> sb.append(ele + ","));
        sb.append(']');
        return sb.toString();
    }

    private class ListItr implements Iterator<T> {

        int _size;
        int _cur_pointer;

        public ListItr(int size) {
            _size = size;
            _cur_pointer = -1;
        }

        @Override
        public boolean hasNext() {
            this.checkCurrencyModify();
            return ++_cur_pointer < Vector.this.size();
        }

        @Override
        public T next() {
            this.checkCurrencyModify();
            return get(_cur_pointer);
        }

        @Override
        public void remove() {
            this.checkCurrencyModify();
            Vector.this.remove(_cur_pointer--);
            _size--;
        }

        private void checkCurrencyModify() {
            if (_size != Vector.this.size()) {
                throw new ConcurrentModificationException();
            }
        }
    }
}
