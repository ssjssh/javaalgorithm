package ssj.algorithm.math;

import com.google.common.base.Preconditions;

/**
 * Created by shenshijun on 15/2/10.
 */
public class NumberUtil {
    public int closestBig(int origion) {
        Preconditions.checkArgument(origion > 0);
        int first_one_index = BitUtil.firstBitOne(origion);

        if (first_one_index > 0) {
            for (int i = first_one_index; i >= 0; i--) {
                if (!BitUtil.testBit(origion, i)) {
                    return (int) BitUtil.moveBit(origion, first_one_index, i);
                }
            }
        }
        return -1;
    }

    public int closestLittle(int origion) {
        Preconditions.checkArgument(origion > 0);
        int first_one_index = BitUtil.firstBitOne(origion);

        if (first_one_index > 0) {
            for (int i = first_one_index; i < Integer.SIZE - 1; i++) {
                if (!BitUtil.testBit(origion, i)) {
                    return (int) BitUtil.moveBit(origion, first_one_index, i);
                }
            }
        }
        return -1;
    }

    public boolean isPrime(long value) {
        return false;
    }
}
