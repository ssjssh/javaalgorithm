package ssj.algorithm.collections;

import ssj.algorithm.SearchTree;

import java.util.Iterator;

/**
 * Created by shenshijun on 15/2/5.
 */
public class BplusTree<T extends Comparable<T>> implements SearchTree<T> {
    //Todo 完成B+树
    @Override
    public void add(T ele) {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Iterator<T> preIterator() {
        return null;
    }

    @Override
    public Iterator<T> postIterator() {
        return null;
    }

    @Override
    public void delete(T ele) {

    }

    @Override
    public T max() {
        return null;
    }

    @Override
    public T min() {
        return null;
    }

    @Override
    public T kthElement(int k) {
        return null;
    }

    @Override
    public boolean contains(T ele) {
        return false;
    }

    @Override
    public boolean isBalance() {
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }
}
