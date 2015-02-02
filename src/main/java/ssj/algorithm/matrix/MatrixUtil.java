package ssj.algorithm.matrix;

import com.google.common.base.Preconditions;

/**
 * Created by shenshijun on 15/2/2.
 */
public class MatrixUtil {
    /**
     * 原理
     * 1            *      2                   *      1
     * ——————————        * ——————————               * ——————————
     * |        |        * |        |               * |        |
     * |        |        * |        |               * |        |
     * 4 |        | 2 ==》  3 |        | 1  ==》       4 |        | 2
     * |        |        * |        |               * |        |
     * ——————————        * ——————————               * ——————————
     * 3            *      4                   *      3
     *
     * @param matrix
     */
    public static void rightRotate90(int[][] matrix) {
        Preconditions.checkNotNull(matrix);
        Preconditions.checkArgument(matrix.length == matrix[0].length);
        int len = matrix.length;
        for (int i = 0; i < len; i++) {
            for (int j = i; j < len; j++) {
                int tmp = matrix[i][j];
                matrix[i][j] = matrix[len - j - 1][len - i - 1];
                matrix[len - j - 1][len - i - 1] = tmp;
            }
        }

        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                if ((i <= (len) / 2 && j <= i) || (i > len / 2 && i >= (len - j - 1))) {
                    int tmp = matrix[i][j];
                    matrix[i][j] = matrix[i][len - j - 1];
                    matrix[i][len - j - 1] = tmp;
                }
            }
        }
    }
}
