package ssj.algorithm;

import com.google.common.base.Preconditions;
import ssj.algorithm.collections.HashSet;

import java.util.Comparator;
import java.util.HashMap;
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
        for (Iterator<T> iterator = iterator(); iterator.hasNext(); ) {
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
        HashSet<T> set = new HashSet<>(100);
        for (Iterator<T> iterator = iterator(); iterator.hasNext(); ) {
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

    List<T> partition(T par_ele, Comparator<? super T> comparator);

    public default Set<T> chainEle() {
        HashSet<T> result = new HashSet<>();
        HashMap<T, Integer> count_eles = new HashMap<>();
        for (T ele : this) {
            if (count_eles.get(ele) == null) {
                count_eles.put(ele, 0);
            }
            count_eles.put(ele, count_eles.get(ele));
        }
        count_eles.entrySet().stream().filter(count -> count.getValue().compareTo(2) >= 0).forEach(count -> {
            result.add(count.getKey());
        });
        return result;
    }

    Iterator<T> reverse();

    public default boolean isPalindromic() {
        Iterator<T> default_iterator = iterator();
        Iterator<T> reversed_iterator = reverse();
        while (default_iterator.hasNext() && reversed_iterator.hasNext()) {
            if (!default_iterator.next().equals(reversed_iterator.next())) {
                return false;
            }
        }
        return true;
    }
}
