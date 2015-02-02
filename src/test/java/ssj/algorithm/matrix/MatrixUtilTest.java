package ssj.algorithm.matrix;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

/**
 * Created by shenshijun on 15/2/2.
 */
public class MatrixUtilTest {
    @Test
    public void testRightRotate90() {
        int[][] matrixes = new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}};
        MatrixUtil.rightRotate90(matrixes);
        for (int[] line : matrixes) {
            System.out.println(Arrays.toString(line));
        }
    }

    @Test
    public void testClearZero() {
        int[][] nozero_matrixes = new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}};
        int[][] nozero_matrixes1 = new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}};
        int[][] zero_matrixes = new int[][]{{1, 2, 3, 4}, {5, 0, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}};
        int[][] zero_matrixes1 = new int[][]{{1, 0, 3, 4}, {0, 0, 0, 0}, {9, 0, 11, 12}, {13, 0, 15, 16}};
        MatrixUtil.clearZero(nozero_matrixes);
        assertTrue(Arrays.deepEquals(nozero_matrixes, nozero_matrixes1));
        MatrixUtil.clearZero(zero_matrixes);
        assertTrue(Arrays.deepEquals(zero_matrixes, zero_matrixes1));
    }


}
