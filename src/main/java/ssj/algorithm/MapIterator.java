package ssj.algorithm;

/**
 * Created by shenshijun on 15/2/3.
 */
public interface MapIterator<K, V> {
    boolean hasNext();

    Entry<K, V> next();

    void set(V value);

    void remove();

    public static class Entry<K, V> {
        private final K key;
        protected V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public V getValue() {
            return value;
        }

        public K getKey() {
            return key;
        }
    }
}
