package ssj.algorithm;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

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


}
