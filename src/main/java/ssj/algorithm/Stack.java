package ssj.algorithm;

import ssj.algorithm.collections.LinkedStack;

import java.util.Comparator;

/**
 * Created by shenshijun on 15/2/4.
 */
public interface Stack<T> {
    void push(T ele);

    T pop();

    int size();

    public default boolean isEmpty() {
        return size() <= 0;
    }

    T head();

    public default Stack<T> sortStack(Comparator<T> comparator) {
        LinkedStack<T> result = new LinkedStack<>();
        while (!isEmpty()) {
            T cur_ele = pop();
            while (!result.isEmpty() && comparator.compare(result.head(), cur_ele) > 0) {
                push(result.pop());
            }
            result.push(cur_ele);
        }
        return result;
    }
}
