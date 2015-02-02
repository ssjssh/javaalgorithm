package ssj.algorithm;

import com.google.common.base.Preconditions;
import ssj.algorithm.Collection;

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
}
