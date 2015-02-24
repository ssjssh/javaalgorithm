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

    public static double pow(double base, int exponent) {
        Preconditions.checkArgument(!(doubleEqual(base, 0.0) && exponent < 0));
        double result = powCore(base, Math.abs(exponent));
        if (exponent < 0) {
            return 1.0 / result;
        } else {
            return result;
        }
    }

    private static double powCore(double base, int exponent) {
        Preconditions.checkArgument(exponent >= 0);
        if (exponent == 0) {
            return 1;
        } else if (exponent == 1) {
            return base;
        }
        double result = powCore(base, exponent >> 1);
        result *= result;
        if (exponent % 2 == 1) {
            result *= base;
        }
        return result;
    }

    private static boolean doubleEqual(double one, double two) {
        return Math.abs(one - two) < 0.0000001;
    }
}
