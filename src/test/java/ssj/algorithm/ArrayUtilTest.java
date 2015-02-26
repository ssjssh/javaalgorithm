package ssj.algorithm;

import org.junit.Test;
import ssj.algorithm.lang.Tuple2;
import ssj.algorithm.string.StringBuilder;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by shenshijun on 15/2/13.
 */
public class ArrayUtilTest {
    @Test
    public void testMergeSort() {
        int arr_size = 2000;
        String[] values1 = new String[arr_size];
        String[] values2 = new String[arr_size];
        for (int i = 0; i < arr_size; i++) {
            values1[i] = String.valueOf(Math.random());
            values2[i] = values1[i];
        }

        ArrayUtil.sort(values1);
        ArrayUtil.mergeSort(values2, String::compareTo);
        for (int i = 0; i < arr_size; i++) {
            assertEquals(values1[i], values2[i]);
        }
    }

    @Test
    public void testCountSort() {
        int arr_size = 10;
        Integer[] values1 = new Integer[arr_size];
        int[] values2 = new int[arr_size];
        for (int i = 0; i < arr_size; i++) {
            values2[i] = (int) (arr_size * Math.random());
            values1[i] = values2[i];
        }
        ArrayUtil.sort(values1);
        ArrayUtil.countSort(values2, 0, arr_size);
        for (int i = 0; i < arr_size; i++) {
            assertTrue(values1[i].equals(values2[i]));
        }
    }

    @Test
    public void testGroup() {
        Integer[] datas = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, -1, -2, -3, -4, -5, -6, -7, -8, -9, -10, -11};
        System.out.println(Arrays.toString(ArrayUtil.group(datas, (ele) -> ele % 4)));
    }

    @Test
    public void testPermutation() {
        Character[] str = new Character[]{'a', 'b', 'c'};
        ArrayUtil.permutation(str, (arr) -> System.out.println(Arrays.toString(arr)));
    }

    @Test
    public void testCombination() {
        Character[] str = new Character[]{'a', 'b', 'c', 'd'};
        ArrayUtil.combination(str, (arr, end) -> {
            ssj.algorithm.string.StringBuilder sb = new StringBuilder("[");
            if (end != -1) {
                for (int j = 0; j <= end; j++) {
                    sb.append(arr[j]);
                }
            }

            sb.add(']');
            System.out.println(sb);
        });
    }

    @Test
    public void testMoreThanHalfEle() {
        Integer[] data = new Integer[]{1, 2, 3, 2, 2, 2, 5, 4, 2};
        assertEquals(Integer.valueOf(2), ArrayUtil.moreThanHalfEle(data));
    }

    @Test
    public void testMaxSubArray() {
        assertEquals(ArrayUtil.maxSubArray(new double[0]), new Tuple2<>(-1, -1));
        assertEquals(ArrayUtil.maxSubArray(new double[]{-1, -2, -3}), new Tuple2<>(-1, -1));
        assertEquals(ArrayUtil.maxSubArray(new double[]{1, -2, 3, 10, -4, 7, 2, -5}), new Tuple2<>(2, 6));
    }

    @Test
    public void testCountNumberOne() {
        assertEquals(ArrayUtil.countNumberOne(20), 12);
    }

    @Test
    public void testCombineMinNumber() {
        assertArrayEquals(new Integer[]{321, 32, 3}, ArrayUtil.combineMinNumber(new Integer[]{3, 32, 321}));
    }


}
