package ssj.algorithm.string;

import com.google.common.base.Preconditions;
import ssj.algorithm.Collection;
import ssj.algorithm.math.MathUtil;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * Created by shenshijun on 15/2/1.
 */
public class StringBuilder implements Collection<Character>, CharSequence, Comparable<StringBuilder> {
    private Character[] _interval_string;
    private int _cur_point;

    public StringBuilder(int capacity) {
        Preconditions.checkArgument(capacity >= 0, "capacity can not be less than 0");
        _cur_point = -1;
        _interval_string = new Character[capacity];
    }

    public StringBuilder(String s) {
        this(s.length());
        append(s);
    }

    @Override
    public void add(Character ele) {
        Preconditions.checkNotNull(ele);
        //必须加一，以防止容量是0
        ensureCapacity((capacity() + 1) * 2);
        _interval_string[++_cur_point] = ele;
    }

    public void append(String ele) {
        Preconditions.checkNotNull(ele);
        for (int i = 0; i < ele.length(); i++) {
            add(ele.charAt(i));
        }
    }

    public Character get(int i) {
        return _interval_string[i];
    }

    public void append(Object ele) {
        append(String.valueOf(ele));
    }

    public void remove(int index) {
        Preconditions.checkPositionIndex(index, size());
        System.arraycopy(_interval_string, index + 1, _interval_string, index, size() - 1 - index);
        _cur_point--;
    }

    @Override
    public void delete(Character ele) {
        Preconditions.checkNotNull(ele);
        int i = 0;
        for (; i < size(); i++) {
            if (ele.equals(_interval_string[i])) {
                remove(i);
                break;
            }
        }
    }

    @Override
    public Collection<Character> filter(Predicate<? super Character> is_func) {
        StringBuilder new_builder = new StringBuilder(size());
        for (Character ele : this) {
            if (is_func.test(ele)) {
                new_builder.add(ele);
            }
        }
        return new_builder;
    }

    @Override
    public <R> Collection<R> newWithCapacity(int size) {
        throw new UnsupportedOperationException("string builder");
    }

    @Override
    public int size() {
        return _cur_point + 1;
    }

    @Override
    public int capacity() {
        return _interval_string.length;
    }

    @Override
    public Iterator<Character> iterator() {
        return new CharIterator(size());
    }

    private void ensureCapacity(int new_size) {
        if (_cur_point >= capacity() - 1 && new_size > capacity()) {
            _interval_string = Arrays.copyOf(_interval_string, new_size);
        }
    }

    @Override
    public int length() {
        return size();
    }

    @Override
    public char charAt(int index) {
        return _interval_string[index];
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        StringBuilder new_builder = new StringBuilder(0);
        for (Character c : Arrays.copyOfRange(_interval_string, start, end)) {
            new_builder.append(c);
        }
        return new_builder;
    }

    @Override
    public String toString() {
        return String.join("", this);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof StringBuilder) {
            StringBuilder other = (StringBuilder) obj;
            return Objects.equals(_interval_string, other._interval_string);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(_interval_string);
    }

    @Override
    public int compareTo(StringBuilder o) {
        //TODO 实现字符串比较
        return 0;
    }

    public int intValue() {
        return MathUtil.strToInt(toString());
    }

    public long longValue() {
        return MathUtil.strToLong(toString());
    }

    public double doubleValue() {
        //TODO 完成String to double
        return -1;
    }

    private class CharIterator implements Iterator<Character> {
        private int _cur;
        private int _size;

        public CharIterator(int size) {
            _size = size;
            _cur = -1;
        }

        @Override
        public boolean hasNext() {
            checkCurrencyModify();
            return ++_cur < _size;
        }

        @Override
        public Character next() {
            checkCurrencyModify();
            return StringBuilder.this.get(_cur);
        }

        @Override
        public void remove() {
            checkCurrencyModify();
            StringBuilder.this.remove(_cur--);
            _size--;
        }

        private void checkCurrencyModify() {
            if (_size != StringBuilder.this.size()) {
                throw new ConcurrentModificationException();
            }
        }
    }
}
