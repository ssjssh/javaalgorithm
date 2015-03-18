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
//        int[] data = new int[]{55, 34, 0, 21, 8, 47, 29, 26, 5, 60, 94, 58, 31, 24, 10, 79, 11, 57, 32, 36, 53, 2, 70, 6, 83, 49, 28, 96, 75, 35, 56, 77, 98, 80, 59, 85, 30, 4, 51, 72};
//        int[] data = new int[]{55, 34, 0, 21, 8, 47, 29, 26,5, 60, 94,58};
        int[] data = MathUtil.randUniqueInt(0, 100000, 400);
        System.out.println(Arrays.toString(data));
        for (int i : data) {
            tree.add(i);
        }
        tree.size();
    }

}
