package ssj.algorithm;

import com.google.common.base.Preconditions;

/**
 * Created by shenshijun on 14-12-20.
 */
public class ArrayUtil {
    public static <T> void swap(T[] array, int left, int right) {
        Preconditions.checkNotNull(array);
        Preconditions.checkPositionIndexes(left, right, array.length);

        T temp = array[left];
        array[left] = array[right];
        array[right] = temp;
    }

    public static void swap(int[] array, int left, int right) {
        Preconditions.checkNotNull(array);
        Preconditions.checkPositionIndexes(left, right, array.length);

        int temp = array[left];
        array[left] = array[right];
        array[right] = temp;
    }

    public static void swap(double[] array, int left, int right) {
        Preconditions.checkNotNull(array);
        Preconditions.checkPositionIndexes(left, right, array.length);

        double temp = array[left];
        array[left] = array[right];
        array[right] = temp;
    }

    public static void swap(long[] array, int left, int right) {
        Preconditions.checkNotNull(array);
        Preconditions.checkPositionIndexes(left, right, array.length);

        long temp = array[left];
        array[left] = array[right];
        array[right] = temp;
    }


    public static void swap(short[] array, int left, int right) {
        Preconditions.checkNotNull(array);
        Preconditions.checkPositionIndexes(left, right, array.length);

        short temp = array[left];
        array[left] = array[right];
        array[right] = temp;
    }

    public static void swap(char[] array, int left, int right) {
        Preconditions.checkNotNull(array);
        Preconditions.checkPositionIndexes(left, right, array.length);

        char temp = array[left];
        array[left] = array[right];
        array[right] = temp;
    }

    public static <T> void swapRange(T[] source, int start, int end, int len) {
        Preconditions.checkNotNull(source);
        Preconditions.checkPositionIndexes(start, end, source.length);

        for (int i = 0; i < len; i++) {
            swap(source, start + i, end + i);
        }
    }

    public static <T> void reverse(T[] source, int start, int end) {
        Preconditions.checkNotNull(source, "source");
        Preconditions.checkPositionIndexes(start, end, source.length);
        for (int i = start; i < (end - start + 1) / 2 + start; i++) {
            swap(source, i, end + start - i);
        }
    }

}



