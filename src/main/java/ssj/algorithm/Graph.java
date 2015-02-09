package ssj.algorithm;

import com.google.common.base.Function;
import ssj.algorithm.collections.Vector;

import java.util.Iterator;
import java.util.function.Consumer;

/**
 * Created by shenshijun on 15/2/7.
 */
public interface Graph<T> {
    void addEdge(T from, T to);

    Iterator<T> nodes();

    void dfs(Consumer<T> func);

    <R> Vector<R> dfs(Function<T, R> func);

    void bfs(Consumer<T> func);

    <R> Vector<R> bfs(Function<T, R> func);

    Vector<T> next(T ele);

    void topologicalSort(Consumer<T> func);

    <R> Vector<R> topologicalSort(Function<T, R> func);

    int size();

    public default boolean isEmpty() {
        return size() <= 0;
    }

    Vector<T> shortestPath(T from, T to);
}
