package ssj.algorithm;

import com.google.common.base.Preconditions;
import ssj.algorithm.collections.BitSet;

import java.util.Comparator;

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

    public static <T extends Comparable<T>> void sort(T[] arr) {
        sort(Comparable::compareTo, arr, 0, arr.length - 1);
    }

    public static <T> void sort(T[] arr, Comparator<T> comparator) {
        sort(comparator, arr, 0, arr.length - 1);
    }

    private static <T> void sort(Comparator<T> comparator, T[] arr, int start, int end) {
        Preconditions.checkNotNull(arr, "arr should not be null");
        Preconditions.checkNotNull(comparator, "comparator should not be null");
        int length = end - start + 1;
        if (length < 2) {
            return;
        }
        int par_index = partition(comparator, arr, start, end);
        sort(comparator, arr, start, par_index - 1);
        sort(comparator, arr, par_index + 1, end);
    }


    private static <T extends Comparable<T>> int partition(T[] arr, int start, int end) {
        return partition(Comparable::compareTo, arr, start, end);
    }

    private static <T> int partition(Comparator<T> comparator, T[] arr, int start, int end) {
        int par_index = start;
        for (int i = start + 1; i <= end; i++) {
            if (comparator.compare(arr[i], arr[start]) <= 0) {
                par_index++;
                ArrayUtil.swap(arr, par_index, i);
            }
        }
        ArrayUtil.swap(arr, start, par_index);
        return par_index;
    }

    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        //TODO 完成归并排序
    }

    public static <T> void countSort(T[] arr, Comparator<T> comparator) {
        //TODO 完成计数排序
    }

    public static <T> T[] leftRotate(T[] raw_array, int start, int end, int rot_index) {
        Preconditions.checkNotNull(raw_array);
        Preconditions.checkPositionIndexes(start, end, raw_array.length);
        Preconditions.checkPositionIndex(rot_index, raw_array.length);
        ArrayUtil.reverse(raw_array, start, rot_index);
        ArrayUtil.reverse(raw_array, rot_index + 1, end);
        ArrayUtil.reverse(raw_array, start, end);
        return null;
    }

    public static <T extends Comparable<T>> T[] smallestK(T[] arr) {
        //TODO 最小的k个数
        // https://github.com/julycoding/The-Art-Of-Programming-By-July/blob/master/ebook/zh/02.01.md
        return null;
    }

    /**
     * * 使用BitSet实现的位图排序，其实就是桶排序的一个变种
     * 这个算法只能用于Int排序，因为要排序的元素会作为BitSet中使用的索引
     * <p>
     * 1，必须是取值范围比较小，不然需要大量的空间。
     * 2，没有重复，因为BitSet只有True和False
     * 原地排序，因此改变了原来数组中的内容
     *
     * @param un_sort_list
     * @return
     */
    public static int[] bitMapSort(int[] un_sort_list) {
        if (null == un_sort_list) {
            throw new NullPointerException("un_sort_list");
        }
        BitSet bit_set = new BitSet(un_sort_list.length / 8);
        for (int x : un_sort_list) {
            if (x < 0) {
                throw new IllegalArgumentException("list need to be sorted should not have negative number");
            }
            if (bit_set.get(x)) {
                throw new IllegalArgumentException("should have not repeat in array");
            }
            bit_set.set(x);
        }

        int sorted_index = 0;
        for (int i = 0; i < bit_set.length(); i++) {
            if (bit_set.get(i)) {
                un_sort_list[sorted_index++] = i;
            }
        }
        return un_sort_list;
    }

}



