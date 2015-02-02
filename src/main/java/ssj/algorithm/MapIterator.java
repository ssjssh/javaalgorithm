package ssj.algorithm;

/**
 * Created by shenshijun on 15/2/3.
 */
public interface MapIterator<K, V> {
    public class Entry<K, V> {
        private final K key;

        public V getValue() {
            return value;
        }

        public K getKey() {
            return key;
        }

        private final V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    boolean hasNext();

    Entry<K, V> next();

    void set(V value);

    void remove();
}
