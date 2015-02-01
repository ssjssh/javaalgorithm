package ssj.algorithm.collections;

import org.junit.Test;
import ssj.algorithm.Collection;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by shenshijun on 15/2/1.
 */
public class VectorTest {
    @Test
    public void testIterator() {
        Vector<Integer> vector = new Vector<>(0);
        for (int i = 0; i < 1000; i++) {
            vector.add(i);
        }
        for (int i = 999; i >= 0; i--) {
            assertEquals(vector.get(i), Integer.valueOf(i));
        }

        Iterator<Integer> iterator = vector.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            assertEquals(iterator.next(), Integer.valueOf(i++));
            iterator.remove();
        }
    }

    @Test
    public void testNewVector() {
        Vector<String> vector = new Vector<>(10);
        System.out.printf(vector.toString());
        assertNotNull(vector.newWithCapacity(10));
    }

    public void testFunc() {
        Vector<String> vector = new Vector<>(10);
        for (int i = 0; i < 20; i++) {
            vector.add(String.valueOf(i));
        }
        for (int i = 100; i < 110; i++) {
            vector.set(i - 100, String.valueOf(i));
        }
        assertEquals(vector.size(), 20);
        Collection<Integer> new_vector = vector.map(Integer::valueOf).filter((i) -> (i % 2 == 0));
        System.out.println(new_vector);
        int vector_sum = new_vector.reduce((i, sum) -> i + sum, 0);
        int vector_product = new_vector.reduce((i, sum) -> i * sum, 1);
        System.out.println(vector_sum);
        System.out.println(vector_product);
    }


}
