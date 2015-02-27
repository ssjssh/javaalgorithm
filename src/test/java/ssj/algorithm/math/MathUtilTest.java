package ssj.algorithm.math;

import org.junit.Test;
import ssj.algorithm.lang.Tuple2;

import static org.junit.Assert.*;

/**
 * Created by shenshijun on 15/2/26.
 */
public class MathUtilTest {
    @Test
    public void testMaxSubArray() {
        assertEquals(MathUtil.maxSubArray(new double[0]), new Tuple2<>(-1, -1));
        assertEquals(MathUtil.maxSubArray(new double[]{-1, -2, -3}), new Tuple2<>(-1, -1));
        assertEquals(MathUtil.maxSubArray(new double[]{1, -2, 3, 10, -4, 7, 2, -5}), new Tuple2<>(2, 6));
        assertEquals((int) MathUtil.maxSubList(new double[0]), 0);
        assertEquals((int) MathUtil.maxSubList(new double[]{-1, -2, -3}), 0);
        assertEquals((int) MathUtil.maxSubList(new double[]{1, -2, 3, 10, -4, 7, 2, -5}), 18);
    }

    @Test
    public void testCountNumberOne() {
        assertEquals(MathUtil.countNumberOne(20), 12);
    }

    @Test
    public void testCombineMinNumber() {
        assertArrayEquals(new Integer[]{321, 32, 3}, MathUtil.combineMinNumber(new Integer[]{3, 32, 321}));
    }

    @Test
    public void testStrToInt() {
        assertEquals(10000, MathUtil.strToInt("10000"));
        assertEquals(-10000, MathUtil.strToInt("-10000"));
        assertEquals(0, MathUtil.strToInt("-0"));
        assertEquals(0, MathUtil.strToInt("+0"));
        assertEquals(Integer.MAX_VALUE, MathUtil.strToInt(String.valueOf(Integer.MAX_VALUE)));
        assertEquals(Integer.MIN_VALUE, MathUtil.strToInt(String.valueOf(Integer.MIN_VALUE)));
        try {
            MathUtil.strToInt("+");
            fail("never go here");
        } catch (Exception e) {
        }

        try {
            MathUtil.strToInt("-");
            fail("never go here");
        } catch (Exception e) {

        }

        try {
            MathUtil.strToInt("");
            fail("never go here");
        } catch (Exception e) {
        }

        try {
            MathUtil.strToInt("-10292a292983");
            fail("never go here");
        } catch (Exception e) {

        }

        try {
            MathUtil.strToInt(String.valueOf(Integer.MAX_VALUE + 10L));
            fail("never go here");
        } catch (Exception e) {

        }

        try {
            MathUtil.strToInt(String.valueOf(Integer.MIN_VALUE - 10L));
            fail("never go here");
        } catch (Exception e) {

        }
    }
}
