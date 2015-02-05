package ssj.algorithm.collections;

import org.junit.Test;
import ssj.algorithm.ArrayUtil;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by shenshijun on 15/2/5.
 */
public class PriorityQueueTest {
    @Test
    public void testStackSort() {
        int size = 1300;
        PriorityQueue<Integer> int_queue = new PriorityQueue<>(200);
        Integer[] ints = new Integer[size];
        for (int i = 0; i < size; i++) {
            ints[i] = (int) (Math.random() * 1000);
            int_queue.appendTail(ints[i]);
        }
        System.out.println(int_queue);
        ArrayUtil.sort(ints);
        System.out.println(Arrays.toString(ints));
        for (int i = size - 1; i >= 0; i--) {
            assertEquals(ints[i], int_queue.removeHead());
        }
        assertTrue(int_queue.isEmpty());
    }
}
