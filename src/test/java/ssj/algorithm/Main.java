package ssj.algorithm;

import com.google.common.base.Preconditions;

/**
 * Created by shenshijun on 15/2/1.
 */
public class Main {
    private static final long UNSIGNED_MAX_LONG = 0xffffffffffffffffL;

    public static void main(String[] args) {
        System.out.println(Long.toHexString(slice(UNSIGNED_MAX_LONG, 0, 61)));
    }

    private static long slice(long value, int start, int end) {
        Preconditions.checkPositionIndexes(start, end, Long.SIZE);
        long back_mask = ~((1L << (Long.SIZE - end)) - 1);
        value &= back_mask;
        long front_mask = ~(UNSIGNED_MAX_LONG << (Long.SIZE - start - 1));
        value &= front_mask;
        return value;
    }


}
