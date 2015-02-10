package ssj.algorithm.collections;

import com.google.common.base.Preconditions;
import ssj.algorithm.Map;
import ssj.algorithm.MapIterator;
import ssj.algorithm.string.StringBuilder;

import java.math.BigInteger;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

import static java.lang.Math.abs;
import static java.lang.Math.pow;

/**
 * Created by shenshijun on 15/2/3.
 */
public class HashMap<K, V> implements Map<K, V> {
    // 来自算法导论乘法哈希函数的值,暂时仅支持2**32个元素
    private static final BigInteger HASH_CONST = BigInteger.valueOf(2654435769L);
    private static final int DEFAULT_SIZE_POWER = 3;
    private static final int DEFAULT_SIZE = 2 << DEFAULT_SIZE_POWER; //aka16
    private static final double DEFAULT_LOAD_FACTOR = 0.75;//默认装载因子0.75

    private Node<K, V>[] _value;
    private double _load_factor;
    private int _size;
    private int _power;

    private HashMap(MapBuilder<K, V> builder) {
        _load_factor = builder.getLoadFactor();
        _value = (Node<K, V>[]) (new Node[builder.getCapacity()]);
        _power = builder._power;
        _size = 0;
    }

    public static <K, V> MapBuilder<K, V> builder() {
        return new MapBuilder<>();
    }

    @Override
    public void set(K key, V value) {
        Preconditions.checkNotNull(key);
        ensureCapacity();
        int index = eleHashCode(key.hashCode());
        assert (index >= 0 && index <= _value.length);
        Node<K, V> old_node = _value[index];
        if (old_node == null) {
            _value[index] = new Node<>(key, value, null);
        } else {
            Node<K, V> pre_node = old_node;
            while (old_node != null) {
                if (old_node.getKey().equals(key)) {
                    old_node.setValue(value);
                    return;
                }
                pre_node = old_node;
                old_node = old_node.getNext();
            }
            pre_node.setNext(new Node<>(key, value, null));
        }
        _size++;
    }

    @Override
    public V get(K key) {
        Preconditions.checkNotNull(key);
        int index = eleHashCode(key.hashCode());
        Node<K, V> old_node = _value[index];
        while (old_node != null) {
            if (old_node.getKey().equals(key)) {
                return old_node.getValue();
            }
            old_node = old_node.getNext();
        }
        return null;
    }

    @Override
    public V setIfAbsent(K key, V default_value) {
        Preconditions.checkNotNull(key);
        V value = get(key);
        if (value == null) {
            set(key, default_value);
            value = default_value;
        }
        return value;
    }

    @Override
    public int size() {
        return _size;
    }

    @Override
    public boolean containsKey(K key) {
        Preconditions.checkNotNull(key);
        int index = eleHashCode(key.hashCode());
        Node<K, V> old_node = _value[index];
        while (old_node != null) {
            if (old_node.getKey().equals(key)) {
                return true;
            }
            old_node = old_node.getNext();
        }
        return false;
    }

    @Override
    public MapIterator<K, V> iterator() {
        return new HashMapItr(size());
    }

    @Override
    public Iterator<K> keyIterator() {
        return new HashMapKeyItr();
    }

    @Override
    public Iterator<V> valueIterator() {
        return new HashMapValueItr();
    }

    @Override
    public V remove(K key) {
        Preconditions.checkNotNull(key);
        int index = eleHashCode(key.hashCode());
        Node<K, V> old_node = _value[index];
        if (old_node != null) {
            if (old_node.getKey().equals(key)) {
                _value[index] = old_node.getNext();
                _size--;
                return old_node.getValue();
            } else {
                Node<K, V> last_node = old_node;
                while (old_node != null) {
                    if (old_node.getKey().equals(key)) {
                        last_node.setNext(old_node.getNext());
                        _size--;
                        return old_node.getValue();
                    }
                    last_node = old_node;
                    old_node = old_node.getNext();
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("HashMap{");
        sb.append("values=[");
        for (Node<K, V> node : _value) {
            if (node != null) {
                sb.append(node.toString());
                sb.append(" , ");
            }
        }
        sb.append("]}");
        return sb.toString();
    }

    private static int log2(int n) {
        return (int) (Math.log(n) / Math.log(2));
    }

    private int eleHashCode(int hash_code) {
        BigInteger hashCode = BigInteger.valueOf(hash_code);
        return abs((hashCode.multiply(HASH_CONST)).intValue()) >> (32 - _power);
    }

    private void ensureCapacity() {
        if ((size() / _value.length) > _load_factor) {
            Node<K, V>[] old_value = _value;
            _power++;
            _value = (Node<K, V>[]) new Node[(int) Math.pow(2, _power)];
            _size = 0;//必须初始化为0，不然会出现重复计算的情况
            for (Node<K, V> node : old_value) {
                while (node != null) {
                    set(node.getKey(), node.getValue());
                    node = node.getNext();
                }
            }
        }
    }

    private static class Node<K, V> extends MapIterator.Entry<K, V> {

        private Node<K, V> next;

        public Node(K key, V value, Node<K, V> next) {
            super(key, value);
            this.next = next;

        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("Node={");
            sb.append("key=");
            sb.append(getKey());
            sb.append(" , value=");
            sb.append(getValue());
            if (next != null) {
                sb.append(" , next=");
                sb.append(next);
            }
            sb.append("}");
            return sb.toString();
        }

        public Node<K, V> getNext() {
            return next;
        }

        public void setValue(V value) {
            this.value = value;
        }

        public void setNext(Node<K, V> next) {
            this.next = next;
        }

    }

    public static class MapBuilder<K, V> {
        private double _load_factor;
        private int _capacity;
        private int _power;

        public MapBuilder() {
            _load_factor = DEFAULT_LOAD_FACTOR;
            _capacity = DEFAULT_SIZE;
            _power = DEFAULT_SIZE_POWER;
        }

        public int getCapacity() {
            return _capacity;
        }

        public MapBuilder setCapacity(int _capacity) {
            Preconditions.checkArgument(_capacity >= DEFAULT_SIZE);
            this._capacity = _capacity;
            return this;
        }

        public double getLoadFactor() {
            return _load_factor;
        }

        public MapBuilder setLoadFactor(double _load_factor) {
            this._load_factor = _load_factor;
            return this;
        }

        public HashMap<K, V> build() {
            _power = log2(_capacity);
            _capacity = (int) pow(2, _power);
            return new HashMap<>(this);
        }
    }

    private class HashMapItr implements MapIterator<K, V> {
        private int _iter_size;
        private int _itered_size;
        private Node<K, V> _cur_node;
        private int _cur_index;

        public HashMapItr(int size) {
            _iter_size = size;
            _itered_size = 1;
            _cur_index = 0;
        }

        @Override
        public boolean hasNext() {
            checkCurrencyModify();
            if (_itered_size <= _iter_size) {
                if (_cur_node != null && _cur_node.getNext() != null) {
                    _cur_node = _cur_node.getNext();
                } else {
                    if (_cur_node != null) {
                        _cur_index++;
                    }
                    while (_cur_index < _value.length && _value[_cur_index] == null) {
                        _cur_index++;
                    }
                    _cur_node = _value[_cur_index];
                }
                _itered_size++;
                return true;
            }
            return false;
        }

        @Override
        public Entry<K, V> next() {
            checkCurrencyModify();
            return _cur_node;
        }

        @Override
        public void set(V value) {
            checkCurrencyModify();
            if (!HashMap.this.containsKey(_cur_node.getKey())) {
                _iter_size++;
            }
            HashMap.this.set(_cur_node.getKey(), value);
        }

        @Override
        public void remove() {
            checkCurrencyModify();
            HashMap.this.remove(_cur_node.getKey());
            _iter_size--;
            _itered_size--;
            _cur_node = null;
        }

        private void checkCurrencyModify() {
            if (HashMap.this.size() != _iter_size) {
                throw new ConcurrentModificationException();
            }
        }
    }

    private class HashMapKeyItr implements Iterator<K> {

        private MapIterator<K, V> _map_iterator;

        public HashMapKeyItr() {
            _map_iterator = HashMap.this.iterator();
        }

        @Override
        public boolean hasNext() {
            return _map_iterator.hasNext();
        }

        @Override
        public K next() {
            return _map_iterator.next().getKey();
        }

        @Override
        public void remove() {
            _map_iterator.remove();
        }
    }

    private class HashMapValueItr implements Iterator<V> {

        private MapIterator<K, V> _map_iterator;

        public HashMapValueItr() {
            _map_iterator = HashMap.this.iterator();
        }

        @Override
        public boolean hasNext() {
            return _map_iterator.hasNext();
        }

        @Override
        public V next() {
            return _map_iterator.next().getValue();
        }

        @Override
        public void remove() {
            _map_iterator.remove();
        }
    }

}
