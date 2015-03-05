package ssj.algorithm.collections;


import org.junit.Test;

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


}
