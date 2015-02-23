package ssj.algorithm.collections;

import org.junit.Test;
import ssj.algorithm.ArrayUtil;

import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * Created by shenshijun on 15/2/7.
 */
public class AVLTreeTest {

    private String[] sortStringArray(int size) {
        String[] results = new String[size];
        for (int i = 0; i < size; i++) {
            results[i] = String.valueOf(Math.random());
        }
        ArrayUtil.sort(results);
        return results;
    }

    @Test
    public void testBase() {
        int tree_size = 10000;
//        AVLTree<String> tree = new AVLTree<>();
//        String[] values = new String[tree_size];
//        for (int i = 0; i < tree_size; i++) {
//            values[i] = String.valueOf((int) (Math.random() * tree_size));
//            tree.add(values[i]);
//        }
//        ArrayUtil.sort(values);
        String[] values = sortStringArray(tree_size);
        AVLTree<String> tree = new AVLTree<>(values);
        assertEquals(tree.size(), tree_size);
        assertTrue(tree.max().equals(values[tree_size - 1]));
        assertTrue(tree.min().equals(values[0]));
        for (int i = 0; i < tree_size; i++) {
            assertTrue(tree.contains(values[i]));
        }
        Iterator<String> mid_iterator = tree.iterator();
        for (int i = 0; i < tree_size && mid_iterator.hasNext(); i++) {
            assertEquals(values[i], mid_iterator.next());
        }
    }

    @Test
    public void testDelete() {
        int tree_size = 10000;
//        AVLTree<String> tree = new AVLTree<>();
//        String[] values = new String[tree_size];
//        for (int i = 0; i < tree_size; i++) {
//            values[i] = String.valueOf((int) (Math.random() * tree_size));
//            tree.add(values[i]);
//        }
//        ArrayUtil.sort(values);
        String[] values = sortStringArray(tree_size);
        AVLTree<String> tree = new AVLTree<>(values);
        assertEquals(tree.size(), tree_size);
        assertTrue(tree.max().equals(values[tree_size - 1]));
        assertTrue(tree.min().equals(values[0]));
        Iterator<String> mid_iterator = tree.iterator();
        for (int i = 0; i < tree_size && mid_iterator.hasNext(); i++) {
            assertEquals(values[i], mid_iterator.next());
        }
        for (int i = 0; i < tree_size; i++) {
            assertTrue(tree.contains(values[i]));
            tree.delete(values[i]);
            assertFalse(tree.contains(values[i]));
        }

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

    @Test
    public void testIsBalance() {
        int tree_size = 10000;
        AVLTree<String> tree = new AVLTree<>();
        String[] values = new String[tree_size];
        for (int i = 0; i < tree_size; i++) {
            values[i] = String.valueOf((int) (Math.random() * tree_size));
            tree.add(values[i]);
        }
        ArrayUtil.sort(values);
        assertEquals(tree.size(), tree_size);
        assertTrue(tree.max().equals(values[tree_size - 1]));
        assertTrue(tree.min().equals(values[0]));
        assertTrue(tree.isBalance());
    }


    @Test
    public void testPostIterator() {
        int[] tree_data = new int[]{10, 4, 6, 14, 4, 8, 9, 12, 16, 11};
        AVLTree<Integer> tree = new AVLTree<>();
        for (int i : tree_data) {
            tree.add(i);
        }
        Iterator<Integer> iterator = tree.postIterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }
}
