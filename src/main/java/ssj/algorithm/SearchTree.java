package ssj.algorithm;

import com.google.common.base.Preconditions;

import java.util.Iterator;

/**
 * Created by shenshijun on 15/2/5.
 */
public interface SearchTree<T extends Comparable<? super T>> extends Iterable<T> {
    void add(T ele);

    default void addAll(Iterable<? extends T> iter) {
        Preconditions.checkNotNull(iter);
        for (T ele : iter) {
            add(ele);
        }
    }

    default void deleteAll(Iterable<? extends T> iter) {
        Preconditions.checkNotNull(iter);
        for (T ele : iter) {
            delete(ele);
        }
    }

    int size();

    Iterator<T> preIterator();

    Iterator<T> postIterator();

    void delete(T ele);

    T max();

    T min();

    T kthElement(int k);

    boolean contains(T ele);

    public default boolean isEmpty() {
        return size() <= 0;
    }

    public boolean isBalance();
}
