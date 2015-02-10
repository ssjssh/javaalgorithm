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
        //TODO 素数检测
        return false;
    }

    public long[] cloestSum(long[] arr, long value) {
        //todo 寻找和为定值的两个数
        return null;
    }

    public static double maxSubList(double[] arr) {
        Preconditions.checkNotNull(arr, "arr should not be null");
        double max_value = 0;
        double this_max_value = 0;
        for (double cur : arr) {
            this_max_value += cur;
            if (this_max_value < 0) {
                this_max_value = 0;
            } else if (max_value < this_max_value) {
                max_value = this_max_value;
            }
        }
        return max_value;
    }
}
