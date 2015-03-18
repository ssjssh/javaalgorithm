package ssj.algorithm.collections;

import org.junit.Test;
import ssj.algorithm.math.MathUtil;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by shenshijun on 15/3/7.
 */
public class BTreeTest {
    @Test
    public void testBasic() {
        BTree<Integer> tree = BTree.degreeOf(2);
        int[] data = MathUtil.randUniqueInt(0, 100000, 400);
        for (int i : data) {
            tree.add(i);
        }
        assertEquals(tree.size(), data.length);
        for (int i : data) {
            assertTrue(tree.contains(i));
        }
    }

}
