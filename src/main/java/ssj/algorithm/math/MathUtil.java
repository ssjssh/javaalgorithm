package ssj.algorithm.math;

import com.google.common.base.Preconditions;

import java.math.BigInteger;
import java.util.Random;

/**
 * Created by shenshijun on 14-12-21.
 */
public class MathUtil {
    private static Random randor = new Random();

    public static int gcd(int a, int b) {
        return BigInteger.valueOf(a).gcd(BigInteger.valueOf(b)).intValue();
    }

    public static double max(double... vals) {
        Preconditions.checkNotNull(vals, "vals should not be null");
        if (vals.length == 0) {
            return 0;
        }
        double max_value = vals[0];
        for (double cur : vals) {
            if (max_value < cur) {
                max_value = cur;
            }
        }
        return max_value;
    }

    public static <T extends Comparable> T max(T... vals) {
        Preconditions.checkNotNull(vals, "vals should not be null");
        Preconditions.checkArgument(vals.length > 0, "vals should not be empty");

        T max_value = vals[0];
        for (T cur : vals) {
            if (max_value.compareTo(cur) < 0) {
                max_value = cur;
            }
        }
        return max_value;
    }

    public static int randInt(int start, int end) {
        Preconditions.checkArgument(start <= end);
        if (start == end) {
            return end;
        }
        randor.setSeed(System.currentTimeMillis());
        return randor.nextInt(end - start) + start;
    }
}
