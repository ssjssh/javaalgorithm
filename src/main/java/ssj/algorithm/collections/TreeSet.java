package ssj.algorithm.collections;

import ssj.algorithm.Collection;
import ssj.algorithm.Set;

import java.util.Iterator;

/**
 * Created by shenshijun on 15/2/3.
 */
public class TreeSet<T extends Comparable<T>> implements Set<T> {

    //这行来自java.util.HashSet
    private static final Object PRESENT = new Object();
    private TreeMap<T, Object> _maps;

    public TreeSet() {
        _maps = new TreeMap<>();
    }

    @Override
    public boolean contains(T ele) {
        return _maps.containsKey(ele);
    }

    @Override
    public void add(T ele) {
        _maps.set(ele, PRESENT);
    }

    @Override
    public void delete(T ele) {
        _maps.remove(ele);
    }

    @Override
    public <R> Collection<R> newWithCapacity(int size) {
        return null;
    }

    @Override
    public int size() {
        return _maps.size();
    }

    @Override
    public int capacity() {
        return _maps.size();
    }

    @Override
    public Iterator<T> iterator() {
        return _maps.keyIterator();
    }
}
