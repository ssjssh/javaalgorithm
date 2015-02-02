package ssj.algorithm.collections;

/**
 * Created by shenshijun on 15/2/1.
 */
public class Stack<T> {
    private LinkedList<T> _list;

    public Stack() {
        _list = new LinkedList<>();
    }

    public void push(T ele) {
        _list.appendHead(ele);
    }

    public T pop() {
        T head = head();
        _list.remove(0);
        return head;
    }

    public T head() {
        return _list.head();
    }

    public int size() {
        return _list.size();
    }

    public boolean isEmpty() {
        return size() <= 0;
    }
}
