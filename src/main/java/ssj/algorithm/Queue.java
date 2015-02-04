package ssj.algorithm;

/**
 * Created by shenshijun on 15/2/2.
 */
public interface Queue<T> {
    T head();

    T tail();

    void append(T ele);

    void appendHead(T ele);

    T removeHead();

    T removeTail();

}
