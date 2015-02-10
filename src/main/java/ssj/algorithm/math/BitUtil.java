package ssj.algorithm.math;

import com.google.common.base.Preconditions;

/**
 * Created by shenshijun on 15/2/10.
 */
public class BitUtil {
    public static boolean testBit(long value, int index) {
        Preconditions.checkArgument(index >= 0 && index < Integer.SIZE);
        return (value & (1L << index)) != 0;
    }

    public static int firstBitOne(long value) {
        int first_one_index = -1;
        for (int i = 0; i < Integer.SIZE; i++) {
            if (BitUtil.testBit(value, i)) {
                first_one_index = i;
                break;
            }
        }
        return first_one_index;
    }

    public static long moveBit(long value, int from, int to) {
        int clear_mask = ~(1 << from);
        int set_mask = 1 << to;
        return (value & clear_mask) | set_mask;
    }
}
