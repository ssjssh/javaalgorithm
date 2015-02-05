package ssj.algorithm.collections;

import com.google.common.base.Preconditions;
import ssj.algorithm.string.StringBuilder;

/**
 * 可以表示任意多的栈，可以任意切换使用哪个栈
 * Created by shenshijun on 15/2/5.
 */
public class MultiStack<T> {
    private Vector<Node<T>> _stacks;
    private int _cur_stack;
    private Vector<Node<T>> _stack_data;
    private Vector<Integer> _stack_sizes;


    public MultiStack(int stack_count) {
        Preconditions.checkArgument(stack_count > 0);
        _stacks = new Vector<>(stack_count);
        _cur_stack = 0;
        _stack_data = new Vector<>(0);
        _stack_sizes = new Vector<>(stack_count);
        for (int i = 0; i < stack_count; i++) {
            _stack_sizes.add(0);
            _stacks.add(null);
        }
    }


    public void push(T ele) {
        Node<T> node = new Node<>(_stacks.get(_cur_stack), ele);
        _stack_data.add(node);
        _stacks.set(_cur_stack, node);
        _stack_sizes.set(_cur_stack, _stack_sizes.get(_cur_stack) + 1);
    }

    public T pop() {
        if (isEmpty()) {
            return null;
        }
        Node<T> cur_node = _stacks.get(_cur_stack);
        Node<T> next_node = cur_node.getLast();
        _stack_data.delete(cur_node);
        _stack_sizes.set(_cur_stack, _stack_sizes.get(_cur_stack));
        _stacks.set(_cur_stack, next_node);
        return cur_node.getValue();

    }

    public int size() {
        return _stack_sizes.get(_cur_stack);
    }

    public boolean isEmpty() {
        return size() <= 0;
    }

    public T head() {
        return _stacks.get(_cur_stack).getValue();
    }

    public void addStack() {
        _stack_sizes.add(0);
        _stacks.add(null);
    }

    public void use(int index) {
        Preconditions.checkPositionIndex(index, _stack_sizes.size());
        _cur_stack = index;
    }

    public int curStack() {
        return _cur_stack;
    }

    private static class Node<T> {
        private T value;
        private Node<T> last;

        public Node(Node<T> last, T value) {
            this.last = last;
            this.value = value;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Node{");
            sb.append("value=");
            sb.append(value);
            sb.append('}');
            return sb.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Node node = (Node) o;

            if (last != null ? !last.equals(node.last) : node.last != null) return false;
            if (value != null ? !value.equals(node.value) : node.value != null) return false;

            return true;

        }

        @Override
        public int hashCode() {
            int result = value != null ? value.hashCode() : 0;
            result = 31 * result + (last != null ? last.hashCode() : 0);
            return result;
        }

        public T getValue() {

            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public Node<T> getLast() {
            return last;
        }

        public void setLast(Node<T> last) {
            this.last = last;
        }
    }
}
