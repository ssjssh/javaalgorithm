package ssj.algorithm.collections;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by shenshijun on 15/2/5.
 */
public class StackQueueTest {
    @Test
    public void testQueue() {
        StackQueue<Integer> int_stack = new StackQueue<>();
        assertTrue(int_stack.isEmpty());
        for (int i = 0; i < 100; i++) {
            int_stack.appendTail(i);
        }
        assertEquals(int_stack.size(), 100);
        assertTrue(int_stack.head().equals(0));
        assertTrue(int_stack.tail().equals(99));
        for (int i = 0; i < 80; i++) {
            assertTrue(int_stack.removeHead().equals(i));
        }
        assertFalse(int_stack.isEmpty());
        for (int i = 100; i < 200; i++) {
            int_stack.appendTail(i);
        }
        for (int i = 80; i <200; i++) {
            assertTrue(int_stack.removeHead().equals(i));
        }
        assertTrue(int_stack.isEmpty());

    }
}
