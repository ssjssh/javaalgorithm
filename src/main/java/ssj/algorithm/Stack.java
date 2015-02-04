package ssj.algorithm;

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

}
