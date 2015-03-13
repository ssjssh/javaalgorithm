package ssj.algorithm.collections;

import org.junit.Test;
import ssj.algorithm.math.MathUtil;

import java.util.Arrays;

/**
 * Created by shenshijun on 15/3/7.
 */
public class BTreeTest {
    @Test
    public void testBasic() {
        BTree<Integer> tree = BTree.degreeOf(2);
        int[] data = MathUtil.randUniqueInt(0, 100, 4);
//        int[] data = new int[]{34,9};
        System.out.println();
        for (int i : data) {
            tree.add(i);
        }
        tree.size();
    }

}
