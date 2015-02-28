package ssj.algorithm.math;

import com.google.common.base.Preconditions;

/**
 * Created by shenshijun on 15/2/10.
 */
public class BitUtil {

    private BitUtil() {
    }

    public static boolean testBit(long value, int index) {
        Preconditions.checkArgument(index >= 0 && index < Long.SIZE);
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

    public static int diffBit(long one, long other) {
        int count = 0;
        for (long c = one ^ other; c != 0; c = c >> 1) {
            count += (c & 1L);
        }
        return count;
    }

    public static long swapBit(long value) {
        return (((value & 0xaaaaaaaaaaaaaaaaL) >> 1) | ((value & 0x5555555555555555L) << 1));
    }
}
