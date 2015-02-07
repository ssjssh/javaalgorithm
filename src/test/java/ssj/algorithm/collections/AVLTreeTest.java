package ssj.algorithm.collections;

import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * Created by shenshijun on 15/2/7.
 */
public class AVLTreeTest {

    @Test
    public void testBase() {
        AVLTree<Integer> tree = new AVLTree<>();
        for (int i = 0; i < 10001; i++) {
            tree.add(i);
        }
        assertEquals(tree.size(), 10001);
        assertTrue(tree.max().equals(10000));
        assertTrue(tree.min().equals(0));
        assertTrue(tree.contains(10000));
        assertFalse(tree.contains(1000000));
    }

    @Test
    public void testDelete() {
        AVLTree<Integer> tree = new AVLTree<>();
        for (int i = 0; i < 10001; i++) {
            tree.add(i);
        }
        assertEquals(tree.size(), 10001);
        assertTrue(tree.max().equals(10000));
        assertTrue(tree.min().equals(0));
        assertTrue(tree.contains(10000));
        assertFalse(tree.contains(1000000));
        for (int i = 0; i < 10001; i++) {
            tree.delete(i);
            assertFalse(tree.contains(i));
        }
        System.out.println(tree);
        assertTrue(tree.isEmpty());
    }

    @Test
    public void testSuccessor() {
        AVLTree<Integer> tree = new AVLTree<>();
        for (int i = 0; i < 10001; i++) {
            tree.add(i);
        }
        assertEquals(tree.size(), 10001);
        assertTrue(tree.max().equals(10000));
        assertTrue(tree.min().equals(0));
        assertTrue(tree.contains(10000));
        assertFalse(tree.contains(1000000));
        assertTrue(tree.successor(1000).equals(1001));
        assertTrue(tree.successor(10000) == null);
        assertTrue(tree.predecessor(1000).equals(999));
        assertTrue(tree.predecessor(0) == null);
    }

    @Test
    public void testIterator() {
        AVLTree<Integer> tree = new AVLTree<>();
        Integer[] values = new Integer[10001];
        for (int i = 0; i < 10001; i++) {
            tree.add(i);
            values[i] = i;
        }
        assertEquals(tree.size(), 10001);
        assertTrue(tree.max().equals(10000));
        assertTrue(tree.min().equals(0));
        assertTrue(tree.contains(10000));
        assertFalse(tree.contains(1000000));

        Iterator<Integer> pre_iterator = tree.preIterator();
        while (pre_iterator.hasNext()) {
            values[pre_iterator.next()] = null;
        }
        for (int i = 0; i < 10001; i++) {
            assertTrue(values[i] == null);
            values[i] = i;
        }

        Iterator<Integer> mid_iterator = tree.iterator();
        for (int i = 0; i < tree.size() && mid_iterator.hasNext(); i++) {
            assertEquals(values[i], mid_iterator.next());
        }

        Iterator<Integer> post_iterator = tree.postIterator();
        while (post_iterator.hasNext()) {
            values[post_iterator.next()] = null;
        }
        for (int i = 0; i < 10001; i++) {
            assertTrue(values[i] == null);
            values[i] = i;
        }
    }
}
