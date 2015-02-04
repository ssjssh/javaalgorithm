package ssj.algorithm;

import com.google.common.base.Preconditions;
import ssj.algorithm.collections.Vector;

import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by shenshijun on 15/2/1.
 */
public interface Collection<T> extends Iterable<T>, Cloneable {
    public void add(T ele);

    public default <R> Collection<R> map(Function<T, R> func) {
        Preconditions.checkNotNull(func);
        Collection<R> new_collection = newWithCapacity(size());
        for (T ele : this) {
            new_collection.add(func.apply(ele));
        }
        return new_collection;
    }

    public default Collection<T> filter(Predicate<T> is_func) {
        Preconditions.checkNotNull(is_func);
        Collection<T> new_collection = this.newWithZero();
        for (T ele : this) {
            if (is_func.test(ele)) {
                new_collection.add(ele);
            }
        }
        return new_collection;
    }

    public default <R> R reduce(BiFunction<T, R, R> func, R start) {
        Preconditions.checkNotNull(func);
        Preconditions.checkNotNull(start);
        R new_start = start;
        for (T ele : this) {
            new_start = func.apply(ele, new_start);
        }
        return new_start;
    }

    public default boolean isEmpty() {
        return size() <= 0;
    }

    public void delete(T ele);

    /**
     * 这个方法用来实现在接口或者抽象类中创建功能的方式
     * 因为Java中没有办法在接口中创建对象，因此只能使用这种方式来处理
     *
     * @param <R>
     * @return
     */
    public <R> Collection<R> newWithCapacity(int size);

    public default <R> Collection<R> newWithZero() {
        return newWithCapacity(0);
    }

    /**
     * 集合的元素个数
     *
     * @return
     */
    public int size();

    /**
     * 集合的容量，可能里面没有元素
     *
     * @return
     */
    public int capacity();

    public default Vector<T> toVector() {
        Vector<T> result = new Vector<>(size());
        for (T ele : this) {
            result.add(ele);
        }
        return result;
    }

    public default Vector<T> sort(Comparator<T> comparator) {
        return toVector().sortThis(comparator);
    }
}

