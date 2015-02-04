package ssj.algorithm.collections;

/**
 * Created by shenshijun on 15/2/4.
 */
public class ExtensiveStack<T extends Comparable<T>> extends LinkedStack<T> {
    private LinkedStack<T> _min_stack;
    private LinkedStack<T> _max_stack;

    public ExtensiveStack() {
        _min_stack = new LinkedStack<>();
        _max_stack = new LinkedStack<>();
    }

    public T min() {
        return _min_stack.head();
    }

    public T max() {
        return _max_stack.head();
    }

    @Override
    public void push(T ele) {
        if (_min_stack.isEmpty() || ele.compareTo(min()) < 0) {
            _min_stack.push(ele);
        }

        if (_max_stack.isEmpty() || ele.compareTo(max()) > 0) {
            _max_stack.push(ele);
        }
        super.push(ele);
    }

    @Override
    public T pop() {
        if (head().compareTo(max()) == 0) {
            _max_stack.pop();
        }

        if (head().compareTo(min()) == 0) {
            _min_stack.pop();
        }
        return super.pop();
    }
}
