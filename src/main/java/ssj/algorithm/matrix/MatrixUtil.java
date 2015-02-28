package ssj.algorithm.matrix;

import com.google.common.base.Preconditions;
import ssj.algorithm.lang.Tuple2;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by shenshijun on 15/2/2.
 */
public class MatrixUtil {
    private MatrixUtil() {

    }

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
    public static <T extends Comparable<? super T>> Tuple2<Integer, Integer> matrixSearch(T[][] matrix, T ele) {
        return binarySearch(matrix, ele);
    }

    static <T extends Comparable<? super T>> Tuple2<Integer, Integer> simpleMatrixSearch(T[][] matrix, T ele) {
        Preconditions.checkNotNull(matrix);
        Preconditions.checkNotNull(ele);
        if (matrix.length == 0) {
            return null;
        }
        int col_count = matrix.length;
        int row_count = matrix[0].length;
        int row = 0;
        int column = col_count - 1;
        while (row < row_count && column >= 0) {
            if (matrix[column][row].equals(ele)) {
                return new Tuple2<>(column, row);
            } else if (matrix[column][row].compareTo(ele) > 0) {
                --column;
            } else {
                ++row;
            }
        }
        return null;
    }

    private static <T extends Comparable<? super T>> Tuple2<Integer, Integer> binaryMatrixSearch(T[][] matrix, Point src, Point dest, T ele) {

        if (dest.compareTo(src) < 0) {
            return null;
        } else if (dest.compareTo(src) == 0) {
            return matrix[dest.getX()][dest.getY()].equals(ele) ? new Tuple2<>(dest.getX(), dest.getY()) : null;
        }
        //首先把矩阵规范化成长宽都是偶数，这样比较好分割
        int col_extra = -1;
        int row_extra = -1;
        int col_count = dest.getX() - src.getX() + 1;
        int row_count = dest.getY() - src.getY() + 1;
        int mid_x = (dest.getX() + src.getX()) / 2;
        int mid_y = (dest.getY() + src.getY()) / 2;
        int end_x = dest.getX();
        int end_y = dest.getY();
        Tuple2<Integer, Integer> result;

        if (col_count % 2 != 0) {
            col_extra = dest.getX();
            end_x--;
        }

        if (row_count % 2 != 0) {
            row_extra = dest.getY();
            end_y--;
        }

        if (matrix[mid_x][mid_y].compareTo(ele) == 0) {
            return new Tuple2<>(mid_x, mid_y);
        } else if (matrix[mid_x][mid_y].compareTo(ele) > 0) {
            result = binaryMatrixSearch(matrix, src, new Point(mid_x, mid_y), ele);
            if (result != null) {
                return result;
            }
        }

        if (matrix[mid_x + 1][mid_y + 1].compareTo(ele) == 0) {
            return new Tuple2<>(mid_x + 1, mid_y + 1);
        } else if (matrix[mid_x + 1][mid_y + 1].compareTo(ele) < 0) {
            result = binaryMatrixSearch(matrix, new Point(mid_x + 1, mid_y + 1), new Point(end_x, end_y), ele);
            if (result != null) {
                return result;
            }
        }

        if (matrix[mid_x][mid_y].compareTo(ele) < 0 && matrix[mid_x + 1][mid_y + 1].compareTo(ele) > 0) {
            result = binaryMatrixSearch(matrix, new Point(src.getX(), mid_y + 1), new Point(mid_x, end_y), ele);
            if (result != null) {
                return result;
            }
            result = binaryMatrixSearch(matrix, new Point(mid_x + 1, src.getY()), new Point(end_x, mid_y), ele);
            if (result != null) {
                return result;
            }
        }

        int extra_y;
        if (col_extra != -1 && (extra_y = Arrays.binarySearch(matrix[col_extra], src.getY(), dest.getY(), ele)) != -1) {
            return new Tuple2<>(col_extra, extra_y);
        }

        int extra_x;
        if (row_extra != -1 && (extra_x = matrixRowBinarySearch(matrix, src.getX(), dest.getX(), row_extra, ele)) != -1) {
            return new Tuple2<>(extra_x, row_extra);
        }
        return null;
    }

    public static <T extends Comparable<? super T>> int matrixRowBinarySearch(T[][] matrix, int col_start, int col_end, int row, T ele) {
        Preconditions.checkNotNull(matrix);
        Preconditions.checkNotNull(ele);
        if (matrix.length == 0) return -1;
        Preconditions.checkPositionIndexes(col_start, col_end, matrix.length);
        Preconditions.checkPositionIndex(row, matrix[0].length);
        int low = col_start;
        int high = col_end;
        while (low <= high) {
            int col_mid = (low + high) / 2;
            int com_res = matrix[col_mid][row].compareTo(ele);
            if (com_res == 0) return col_mid;
            else if (com_res > 0) {
                high = col_mid - 1;
            } else {
                low = col_mid + 1;
            }
        }
        return -1;
    }

    static <T extends Comparable<? super T>> Tuple2<Integer, Integer> binarySearch(T[][] matrix, T ele) {
        Preconditions.checkNotNull(matrix);
        Preconditions.checkNotNull(ele);
        if (matrix.length == 0) {
            return null;
        }
        return binaryMatrixSearch(matrix, new Point(0, 0), new Point(matrix.length - 1, matrix[0].length - 1), ele);
    }


    private static class Point implements Comparable<Point> {
        final int x;
        final int y;

        Point(int _x, int _y) {
            x = _x;
            y = _y;
        }

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

        @Override
        public String toString() {
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }

        @Override
        public int compareTo(Point o) {
            Preconditions.checkNotNull(o);
            if (getX() == o.getX() && getY() == o.getY()) return 0;
            if (getX() > o.getX() && getY() > o.getY()) return 1;
            return -1;
        }
    }
}
