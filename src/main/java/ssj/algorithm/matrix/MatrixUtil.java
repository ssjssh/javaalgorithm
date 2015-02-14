package ssj.algorithm.matrix;

import com.google.common.base.Preconditions;
import ssj.algorithm.lang.Tuple2;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

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

    /**
     * 把矩阵中值为0的位置上的行列都设置为0
     *
     * @param matrix
     */
    public static void clearZero(int[][] matrix) {
        Preconditions.checkNotNull(matrix);
        Set<Point> zero_points = new HashSet<>();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    zero_points.add(new Point(i, j));
                }
            }
        }
        zero_points.stream().forEach((point) -> {
            for (int i = 0; i < matrix.length; i++) {
                matrix[i][point.y] = 0;
            }

            for (int i = 0; i < matrix[0].length; i++) {
                matrix[point.x][i] = 0;
            }
        });
    }

    /**
     * 杨氏矩阵查找，有两种实现算法。
     * 1，使用遍历+排除
     * 2，二维矩阵版二叉查找法
     *
     * @param matrix
     * @param ele
     * @return
     */
    public static <T extends Comparable<T>> Tuple2<Integer, Integer> matrixSearch(T[][] matrix, T ele) {
        return null;
    }

    private static <T extends Comparable<T>> Tuple2<Integer, Integer> simpleMatrixSearch(T[][] matrix, T ele) {
        Preconditions.checkNotNull(matrix);
        Preconditions.checkNotNull(ele);
        if (matrix.length == 0) {
            return null;
        }
        int col_count = matrix.length;
        int row_count = matrix[0].length;
        for (int i = col_count - 1; i >= 0; i--) {
            for (int j = 0; j < row_count; j++) {
                int com_res = ele.compareTo(matrix[i][j]);
                if (com_res == 0) {
                    return new Tuple2<>(i, j);
                } else if (com_res < 0) {
                    break;
                }
            }
        }
        return null;
    }

    private static <T extends Comparator<T>> Tuple2<Integer, Integer> binaryMatrixSearch(T[][] matrix, T ele) {
        return null;
    }


    private static class Point {
        final int x;
        final int y;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Point point = (Point) o;

            if (x != point.x) return false;
            if (y != point.y) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }

        public int getY() {
            return y;

        }

        public int getX() {
            return x;
        }

        Point(int _x, int _y) {
            x = _x;
            y = _y;
        }

        @Override
        public String toString() {
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }
}
