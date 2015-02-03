package ssj.algorithm;

/**
 * Created by shenshijun on 15/2/3.
 */
public interface Set<T> extends Collection<T> {
    boolean contains(T ele);

    public default Set<T> join(Set<T> other) {
        return null;
    }

    public default Set<T> subtract(Set<T> other) {
        return null;
    }

    public default Set<T> innerJoin(Set<T> other) {
        return null;
    }
}
