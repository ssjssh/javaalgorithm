package ssj.algorithm.collections;

import org.junit.Test;
import ssj.algorithm.ArrayUtil;
import ssj.algorithm.Stack;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by shenshijun on 15/2/3.
 */
public class LinkedStackTest {
    @Test
    public void testStack() {
        LinkedStack<Integer> int_stack = new LinkedStack<>();
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
    public void testStacks() {
        LinkedStack<Integer> int_stack = new LinkedStack<>();
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
    public void testStackSort() {
        LinkedStack<Integer> string_stack = new LinkedStack<>();
        Integer[] ints = new Integer[200];
        for (int i = 0; i < 200; i++) {
            ints[i] = (int) (Math.random() * 1000);
            string_stack.push(ints[i]);
        }
        Stack<Integer> sorted_stack = string_stack.sortStack((a, b) -> a - b);
        ArrayUtil.sort(ints);
        for (int i = 199; i >= 0; i--) {
            assertEquals(ints[i], sorted_stack.pop());
        }
        assertTrue(sorted_stack.isEmpty());
    }

}
