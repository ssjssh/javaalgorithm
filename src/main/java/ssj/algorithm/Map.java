package ssj.algorithm;

import com.google.common.base.Preconditions;

import java.util.Iterator;

/**
 * Created by shenshijun on 15/2/3.
 */
public interface Map<K, V> {
    void set(K key, V value);

    V get(K key);

    public default V getDefault(K key, V default_value) {
        Preconditions.checkNotNull(key);
        V value = get(key);
        return (value == null) ? default_value : value;
    }

    V setIfAbsent(K key, V default_value);

    public int size();

    boolean containsKey(K key);

    MapIterator<K, V> iterator();

    Iterator<K> keyIterator();

    Iterator<V> valueIterator();

    public default boolean isEmpty() {
        return size() <= 0;
    }

    V remove(K key);
}


