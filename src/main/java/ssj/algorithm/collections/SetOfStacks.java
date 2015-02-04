package ssj.algorithm.collections;

import ssj.algorithm.Stack;

/**
 * 使用一个栈来存储一系列栈，这样可以模拟只有一个栈的情况
 * Created by shenshijun on 15/2/4.
 */
public class SetOfStacks<T> implements Stack<T> {
    private LinkedStack<LinkedStack<T>> _stacks;
    private static final int DEFAULT_STACK_SIZE = 20;
    private int _stack_size;
    private int _each_size;

    public SetOfStacks() {
        this(DEFAULT_STACK_SIZE);
    }

    public SetOfStacks(int size) {
        _each_size = size;
        _stack_size = 0;
        _stacks = new LinkedStack<>();
    }

    @Override
    public void push(T ele) {
        ensureNotFullHead();
        _stacks.head().push(ele);
        _stack_size++;
    }

    @Override
    public T pop() {
        T result = head();
        ensureNotEmptyHead();
        _stacks.head().pop();
        _stack_size--;
        return result;

    }

    @Override
    public int size() {
        return _stack_size;
    }

    @Override
    public T head() {
        return _stacks.head().head();
    }

    private void ensureNotFullHead() {
        if (_stacks.isEmpty() && _stacks.head().size() >= _each_size) {
            _stacks.push(new LinkedStack<>());
        }
    }

    /**
     * 把空的栈的退出，只留下最后一个空的栈
     */
    private void ensureNotEmptyHead() {
        if (_stacks.head() != null && _stacks.head().isEmpty() && _stacks.size() == 1) {
            _stacks.pop();
        }
    }
}
