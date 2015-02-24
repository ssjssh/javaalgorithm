package ssj.algorithm;

import org.junit.Test;

import java.util.Arrays;

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
        Integer[] datas = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,-1, -2, -3, -4, -5, -6, -7, -8, -9, -10, -11};
        System.out.println(Arrays.toString(ArrayUtil.group(datas, (ele) -> ele % 4)));
    }


}
