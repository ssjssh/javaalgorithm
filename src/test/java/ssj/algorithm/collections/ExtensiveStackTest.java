package ssj.algorithm.collections;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by shenshijun on 15/2/4.
 */
public class ExtensiveStackTest {
    @Test
    public void testStack() {
        ExtensiveStack<Integer> int_stack = new ExtensiveStack<>();
        assertTrue(int_stack.isEmpty());
        for (int i = 0; i < 100; i++) {
            int_stack.push(i);
        }
        assertEquals(int_stack.size(), 100);
        assertTrue(int_stack.head().equals(99));
        for (int i = 99; i >= 0; i--) {
            assertTrue(int_stack.pop().equals(i));
        }
        assertTrue(int_stack.isEmpty());
    }

    @Test
    public void testStackMinMax() {
        ExtensiveStack<Integer> int_stack = new ExtensiveStack<>();
        assertTrue(int_stack.isEmpty());
        for (int i = 0; i < 100; i++) {
            int random_int = (int) (Math.random() * 100);
            int_stack.push(random_int);
        }
        System.out.println(int_stack);
        for (int i = 99; i >= 0; i--) {
            System.out.println(int_stack.max());
            System.out.println(int_stack.min());
            int_stack.pop();
        }
        assertTrue(int_stack.isEmpty());
    }
}
