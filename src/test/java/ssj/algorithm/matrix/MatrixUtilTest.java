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

    @Test
    public void testSimpleMatrixSearch() {
        Integer[][] matrix = new Integer[][]{{15, 20, 35, 40}, {20, 35, 55, 80}, {70, 80, 95, 100}, {85, 95, 105, 120}};
        System.out.println(MatrixUtil.simpleMatrixSearch(matrix, 85));
        System.out.println(MatrixUtil.simpleMatrixSearch(matrix, 120));
        System.out.println(MatrixUtil.simpleMatrixSearch(matrix, 0));
    }

    @Test
    public void testBinaryMatrixSearch() {
        Integer[][] matrix = new Integer[][]{{15, 20, 35, 40}, {20, 35, 55, 80}, {70, 80, 95, 100}, {85, 95, 105, 120}};
        System.out.println(MatrixUtil.binarySearch(matrix, 85));
        System.out.println(MatrixUtil.binarySearch(matrix, 120));
        System.out.println(MatrixUtil.binarySearch(matrix, 0));
    }


}
