package ssj.algorithm.collections;

import com.google.common.base.Preconditions;
import ssj.algorithm.Map;
import ssj.algorithm.MapIterator;
import ssj.algorithm.string.StringBuilder;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

/**
 * Created by shenshijun on 15/2/3.
 */
public class TreeMap<K extends Comparable<K>, V> implements Map<K, V> {
    AVLTree<Node> _tree;

    public TreeMap() {
        _tree = new AVLTree<>();
    }

    @Override
    public void set(K key, V value) {
        Preconditions.checkNotNull(key);
        Node new_node;
        if ((new_node = _tree.findOrigin(new Node(key, value))) != null) {
            new_node.setValue(value);
        } else {
            _tree.add(new Node(key, value));
        }
    }

    @Override
    public V get(K key) {
        Preconditions.checkNotNull(key);
        Node new_node = _tree.findOrigin(new Node(key, null));
        return new_node == null ? null : new_node.getValue();
    }

    @Override
    public int size() {
        return _tree.size();
    }

    @Override
    public boolean containsKey(K key) {
        return _tree.contains(new Node(key, null));
    }

    @Override
    public MapIterator<K, V> iterator() {
        return new TreeMapIter(size());
    }

    @Override
    public Iterator<K> keyIterator() {
        return new TreeMapKeyIter(size());
    }

    @Override
    public Iterator<V> valueIterator() {
        return new TreeMapValueIter(size());
    }

    @Override
    public V remove(K key) {
        V result = get(key);
        _tree.delete(new Node(key, null));
        return result;
    }

    private void checkCurrencyModify(int expect_size) {
        if (size() != expect_size) {
            throw new ConcurrentModificationException();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("TreeMap { \n");
        Iterator<Node> iterator = _tree.iterator();
        while (iterator.hasNext()) {
            sb.append(iterator.next());
            sb.append("\n");
        }
        sb.append("}");
        return sb.toString();
    }

    private class TreeMapIter implements MapIterator<K, V> {

        private int iter_size;
        private Iterator<Node> map_iterator;

        public TreeMapIter(int _size) {
            iter_size = _size;
            map_iterator = _tree.iterator();
        }

        @Override
        public boolean hasNext() {
            checkCurrencyModify(iter_size);
            return map_iterator.hasNext();
        }

        @Override
        public Entry<K, V> next() {
            checkCurrencyModify(iter_size);
            return map_iterator.next();
        }

        @Override
        public void set(V value) {
            checkCurrencyModify(iter_size);
            TreeMap.this.set(next().getKey(), value);
        }
    }

    private class TreeMapKeyIter implements Iterator<K> {

        private TreeMapIter iter;

        public TreeMapKeyIter(int _size) {
            iter = new TreeMapIter(_size);
        }

        @Override
        public boolean hasNext() {
            return iter.hasNext();
        }

        @Override
        public K next() {
            return iter.next().getKey();
        }
    }

    private class TreeMapValueIter implements Iterator<V> {
        private TreeMapIter iter;

        public TreeMapValueIter(int _size) {
            iter = new TreeMapIter(_size);
        }

        @Override
        public boolean hasNext() {
            return iter.hasNext();
        }

        @Override
        public V next() {
            return iter.next().getValue();
        }
    }

    private class Node extends MapIterator.Entry<K, V> implements Comparable<Node> {

        public Node(K key, V value) {
            super(key, value);
        }

        public void setValue(V value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Node node = (Node) o;

            if (!getKey().equals(node.getKey())) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return getKey().hashCode();
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Node{");
            sb.append("key=");
            sb.append(getKey());
            sb.append(", value=");
            sb.append(value);
            sb.append('}');
            return sb.toString();
        }

        @Override
        public int compareTo(Node o) {
            return getKey().compareTo(o.getKey());
        }
    }
}
