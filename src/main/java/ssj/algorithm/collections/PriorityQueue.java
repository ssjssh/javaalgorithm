package ssj.algorithm.collections;

import com.google.common.base.Preconditions;
import ssj.algorithm.Collection;
import ssj.algorithm.Queue;

import java.util.Iterator;

/**
 * 使用最大堆实现优先队列
 * Created by shenshijun on 15/2/4.
 */
public class PriorityQueue<T extends Comparable<T>> implements Queue<T> {
    //使用归纳法可以证明_values肯定是满的，也就是说二叉堆是一个完全二叉树
    private Vector<T> _values;

    public PriorityQueue(int capacity) {
        _values = new Vector<>(capacity);
    }

    public static <T extends Comparable<T>> PriorityQueue<T> buildQueue(T... arr) {
        PriorityQueue<T> result = new PriorityQueue<>(arr.length);
        for (T ele : arr) {
            result._values.add(ele);
        }
        for (int i = result._values.size() / 2; i < result._values.size(); i++) {
            result.heapify(i);
        }
        return result;
    }

    @Override
    public T head() {
        if (isEmpty()) {
            return null;
        }
        return _values.get(_values.size() - 1);
    }

    @Override
    public T tail() {
        if (isEmpty()) {
            return null;
        }
        return _values.get(0);
    }


    @Override
    public void appendTail(T ele) {
        Preconditions.checkNotNull(ele);
        _values.add(ele);

        heapify(size() - 1);
    }

    @Override
    public T removeHead() {
        T result = head();
        _values.remove(size() - 1);
        for (int i = size() / 2; i < size(); i++) {
            heapify(i);
        }
        return result;
    }

    @Override
    public void add(T ele) {
        appendTail(ele);
    }

    @Override
    public void delete(T ele) {
        throw new UnsupportedOperationException("delete on PriorityQueue");
    }

    @Override
    public <R> Collection<R> newWithCapacity(int size) {
        return new LinkedList<>();
    }

    @Override
    public int size() {
        return _values.size();
    }

    @Override
    public int capacity() {
        return _values.capacity();
    }

    @Override
    public Iterator<T> iterator() {
        return _values.iterator();
    }

    private void heapify(int index) {
        int cur_index = index;
        while (cur_index > 0) {
            int largest = cur_index;
            if (left(cur_index) >= 0 && _values.get(largest).compareTo(_values.get(left(cur_index))) < 0) {
                largest = left(cur_index);
            }

            if (right(cur_index) >= 0 && _values.get(largest).compareTo(_values.get(right(cur_index))) < 0) {
                largest = right(cur_index);
            }

            if (largest != cur_index) {
                _values.swap(largest, cur_index);
                cur_index = largest;
            } else {
                break;
            }
        }
    }

    private int parent(int index) {
        return size() - (index - 1) / 2 - 1;
    }

    private int left(int index) {
        return 2 * index - size();
    }

    private int right(int index) {
        return left(index) - 1;
    }

    @Override
    public String toString() {
        return _values.toString();
    }

    @Override
    public int hashCode() {
        return _values.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj == this) {
            return true;
        }

        if (obj instanceof PriorityQueue) {
            return _values.equals(((PriorityQueue) obj)._values);
        }
        return false;
    }
}
