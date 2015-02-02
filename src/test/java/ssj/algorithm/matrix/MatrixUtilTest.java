package ssj.algorithm.matrix;

import org.junit.Test;

import java.util.Arrays;

/**
 * Created by shenshijun on 15/2/2.
 */
public class MatrixUtilTest {
    @Test
    public void testRightRotate90() {
        int[][] matrixs = new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}};
        MatrixUtil.rightRotate90(matrixs);
        for (int[] line : matrixs) {
            System.out.println(Arrays.toString(line));
        }
    }

}
