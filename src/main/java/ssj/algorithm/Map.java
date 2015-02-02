package ssj.algorithm;

import com.google.common.base.Preconditions;

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

    public int size();

    MapIterator<K, V> iterator();

    public default boolean isEmpty() {
        return size() <= 0;
    }

    V remove();
}


