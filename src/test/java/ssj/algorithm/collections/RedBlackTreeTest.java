package ssj.algorithm.collections;


import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by shenshijun on 15/3/4.
 */
public class RedBlackTreeTest {
    @Test
    public void testBasic() {
        int[] tree_data = new int[]{10, 4, 6, 14, 4, 8, 9, 12, 16, 11};
        RedBlackTree<Integer> tree = RedBlackTree.newInstance();
        for (int i : tree_data) {
            tree.add(i);
        }

        assertTrue(tree.successor(10).equals(11));
        assertTrue(tree.successor(9).equals(10));
        assertTrue(tree.successor(16) == null);
        assertTrue(tree.predecessor(10).equals(9));
        assertTrue(tree.predecessor(11).equals(10));
        assertTrue(tree.predecessor(4).equals(4));
    }

    @Test
    public void testDelete() {
        int[] tree_data = new int[]{10, 4, 6, 14, 4, 8, 9, 12, 16, 11};
        RedBlackTree<Integer> tree = RedBlackTree.newInstance();
        for (int i : tree_data) {
            tree.add(i);
        }

        tree.delete(10);
        tree.size();
    }

    @Test
    public void testPostIterator() {
        int[] tree_data = new int[]{10, 4, 6, 14, 4, 8, 9, 12, 16, 11};
        RedBlackTree<Integer> tree = RedBlackTree.newInstance();
        for (int i : tree_data) {
            tree.add(i);
        }
        Iterator<Integer> iterator = tree.postIterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    @Test
    public void testIterator() {
        RedBlackTree<Integer> tree = RedBlackTree.newInstance();
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
