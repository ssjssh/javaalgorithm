package ssj.algorithm;

import com.google.common.base.Preconditions;
import ssj.algorithm.collections.HashSet;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;

/**
 * Created by shenshijun on 15/2/1.
 */
public interface List<T> extends Collection<T> {
    public T get(int index);

    public void set(int index, T ele);

    public void remove(int index);

    public default void delete(T ele) {
        Preconditions.checkNotNull(ele);
        Iterator<T> iterator = iterator();
        while (iterator.hasNext()) {
            if (Objects.equals(iterator.next(), ele)) {
                iterator.remove();
            }
        }
    }

    /**
     * 去除列表中重复的元素
     *
     * @return
     */
    public default int removeDuplicate() {
        int result = 0;
        HashSet<T> set = new HashSet<>();
        Iterator<T> iterator = iterator();
        while (iterator.hasNext()) {
            if (set.contains(iterator.next())) {
                iterator.remove();
                result++;
            }
        }
        return result;
    }

    public default T getReverse(int index) {
        int normal_index = size() - index - 1;
        Preconditions.checkPositionIndex(normal_index, size());
        return get(normal_index);
    }

    List<T> partition(T par_ele, Comparator<T> comparator);
}
