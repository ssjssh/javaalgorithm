package ssj.algorithm.collections;

import ssj.algorithm.Collection;
import ssj.algorithm.Set;

import java.util.Iterator;

/**
 * Created by shenshijun on 15/2/3.
 */
public class HashSet<T> implements Set<T>{
    @Override
    public boolean contains(T ele) {
        return false;
    }

    @Override
    public void add(T ele) {

    }

    @Override
    public void delete(T ele) {

    }

    @Override
    public <R> Collection<R> newWithCapacity(int size) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public int capacity() {
        return 0;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }
}
