package ssj.algorithm;

/**
 * Created by shenshijun on 15/2/2.
 */
public interface Queue<T> extends Collection<T>{
    T head();

    T tail();

    void appendTail(T ele);

    T removeHead();
}
