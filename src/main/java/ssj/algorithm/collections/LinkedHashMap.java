package ssj.algorithm.collections;

import ssj.algorithm.MapIterator;
import ssj.algorithm.util.CheckUtil;

/**
 * Created by shenshijun on 15/2/5.
 */
public class LinkedHashMap<K, V> extends HashMap<K, V> {

    private LinkedNode<K, V> head;
    private boolean enable_flag;

    private LinkedHashMap(MapBuilder<K, V> builder) {
        super(builder);
        head = new LinkedNode<>(null, null, null);
        head.setNextNode(head);
        head.setPreNode(head);
        enable_flag = true;
    }

    @Override
    public MapIterator<K, V> iterator() {
        return new LinkedHashMapIter(size());
    }

    @Override
    protected Node<K, V> newNode(K key, V value) {
        return new LinkedNode<>(key, value, null);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Node<K, V>[] newNodeArray(int size) {
        return (LinkedNode<K, V>[]) (new LinkedNode[size]);
    }

    public static <K, V> LinkedHashMapBuilder<K, V> builder() {
        return new LinkedHashMapBuilder<>();
    }

    @Override
    protected void afterDeleteNode(Node<K, V> deleted_node) {
        if (enable_flag) {
            LinkedNode<K, V> linkedNode = convertDown(deleted_node);
            linkedNode.getNextNode().setPreNode(linkedNode.getPreNode());
            linkedNode.getPreNode().setNextNode(linkedNode.getNextNode());
        }
    }

    @Override
    protected void afterInsertNewNode(Node<K, V> new_node) {
        if (enable_flag) {
            LinkedNode<K, V> linkedNode = convertDown(new_node);
            linkedNode.setPreNode(head);
            linkedNode.setNextNode(head.getNextNode());
            head.getNextNode().setPreNode(linkedNode);
            head.setNextNode(linkedNode);
        }

    }

    @Override
    protected void clearOldArray() {
        enable_flag = false;
    }

    @Override
    protected void resetOldArray() {
        enable_flag = true;
    }

    @Override
    protected void afterReplaceNode(Node<K, V> node) {
        if (enable_flag){
            afterDeleteNode(node);
            afterInsertNewNode(node);
        }
    }

    private class LinkedHashMapIter implements MapIterator<K, V> {

        private int iter_size;
        private LinkedNode<K, V> cur_node;

        private LinkedHashMapIter(int _size) {
            iter_size = _size;
            cur_node = head;
        }

        @Override
        public boolean hasNext() {
            CheckUtil.checkCurrencyModify(iter_size, LinkedHashMap.this.size());
            cur_node = cur_node.getPreNode();
            return cur_node != head;
        }

        @Override
        public Entry<K, V> next() {
            CheckUtil.checkCurrencyModify(iter_size, LinkedHashMap.this.size());
            return cur_node;
        }

        @Override
        public void set(V value) {
            CheckUtil.checkCurrencyModify(iter_size, LinkedHashMap.this.size());
            LinkedHashMap.this.set(cur_node.getKey(), value);
        }

        @Override
        public void remove() {
            LinkedNode<K, V> old = cur_node.getPreNode();
            LinkedHashMap.this.remove(cur_node.getKey());
            cur_node = old;
            iter_size--;
        }
    }

    private LinkedNode<K, V> convertDown(Node<K, V> node) {
        if (node instanceof LinkedNode) {
            return (LinkedNode<K, V>) node;
        }
        throw new ClassCastException("Node should be LinkedNode");
    }

    public static class LinkedHashMapBuilder<K, V> extends HashMap.MapBuilder<K, V> {

        @Override
        public LinkedHashMap<K, V> build() {
            return new LinkedHashMap<>(this);
        }
    }

    private static class LinkedNode<K, V> extends Node<K, V> {

        private LinkedNode<K, V> pre_node;
        private LinkedNode<K, V> next_node;

        public LinkedNode<K, V> getNextNode() {
            return next_node;
        }

        public void setNextNode(LinkedNode<K, V> next_node) {
            this.next_node = next_node;
        }

        public LinkedNode<K, V> getPreNode() {
            return pre_node;
        }

        public void setPreNode(LinkedNode<K, V> pre_node) {
            this.pre_node = pre_node;
        }

        public LinkedNode(K key, V value, Node<K, V> next) {
            super(key, value, next);
        }
    }
}
