package ssj.algorithm.collections;

import ssj.algorithm.Collection;
import ssj.algorithm.Set;

import java.util.Iterator;

/**
 * Created by shenshijun on 15/2/3.
 */
public class HashSet<T> implements Set<T> {
    //这行来自java.util.HashSet
    private static final Object PRESENT = new Object();
    private HashMap<T, Object> _maps;

    public HashSet(int size) {
        HashMap.MapBuilder<T, Object> builder = HashMap.builder();
        builder.setCapacity(size);
        _maps = builder.build();
    }

    public HashSet(){
        HashMap.MapBuilder<T, Object> builder = HashMap.builder();
        _maps = builder.build();
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
        return new HashSet<>(size);
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
