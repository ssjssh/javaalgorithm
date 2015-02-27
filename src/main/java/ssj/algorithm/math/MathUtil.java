package ssj.algorithm.math;

import com.google.common.base.Preconditions;
import ssj.algorithm.ArrayUtil;
import ssj.algorithm.lang.Tuple2;

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

    public static <T extends Comparable<T>> T min(T... arr) {
        Preconditions.checkNotNull(arr);
        if (arr.length == 0) {
            return null;
        }

        T min_value = arr[0];
        for (T ele : arr) {
            if (min_value.compareTo(ele) > 0) {
                min_value = ele;
            }
        }
        return min_value;
    }

    /**
     * 最大和连续子数组
     *
     * @param arr
     * @return
     */
    public static Tuple2<Integer, Integer> maxSubArray(double[] arr) {
        Preconditions.checkNotNull(arr);
        double sum = -1, temp_sum = 0;
        int left = 0, right = 0;
        for (int i = 0; i < arr.length; i++) {
            if (temp_sum < 0) {
                temp_sum = arr[i];
                left = i;
            } else {
                temp_sum += arr[i];
            }


            if (temp_sum > sum) {
                right = i;
                sum = temp_sum;
            }
        }

        if (sum < 0) {
            return new Tuple2<>(-1, -1);
        }
        return new Tuple2<>(left, right);
    }

    /**
     * 计算从1到n中，1出现个数的总和。
     *
     * @param n
     * @return
     */
    public static int countNumberOne(int n) {
        Preconditions.checkArgument(n >= 0);
        return countNumberOneCore(String.valueOf(n));
    }

    private static int countNumberOneCore(String number) {
        int first = number.charAt(0) - '0';

        if (number.length() == 1) {
            if (first == 0) {
                return 0;
            } else {
                return 1;
            }
        }

        int firstDigitCount;
        if (first > 0) {
            firstDigitCount = (int) Math.round(Math.pow(10, number.length() - 1));
        } else {
            firstDigitCount = Integer.valueOf(number.substring(1)) + 1;
        }

        int otherDigitCount = first * (number.length() - 1) * (int) Math.round(Math.pow(10, number.length() - 2));

        return firstDigitCount + otherDigitCount + countNumberOneCore(number.substring(1));
    }

    /**
     * 从一系列整数中拼接出最小的数字。
     * 证明：使用反证法。
     * 假设这样得到的序列并不是最小的，也就是说对于序列A1A2...Ax....Ay-1Ay...An来说，如果交换Ax和Ay。
     * 得到的序列A1A2...Ay...Ay-1Ax...An < A1A2...Ax....Ay-1Ay...An。现在分别交换Ax和Ay使得Ax和Ay靠在一起。
     * 不等式左边的交换，由于Ay<Ax+1....Ay-1，所以把Ay往前调的时候，得到A1A2....Ax+1...Ay-1AyAx...An < A1A2...Ay...Ay-1Ax...An
     * 同理：右边也是一样的：A1A2...Ax....Ay-1Ay...An < A1A2...Ax+1....Ay-1AxAy...An。
     * 综合三个等式得到：A1A2....Ax+1...Ay-1AyAx...An < A1A2...Ax+1....Ay-1AxAy...An。也就是AyAx < AxAy，这样
     * 显然和定义的比较规则相反，所以原假设不成立，证明了通过这样的排序规则得到的序列是最小的序列。
     *
     * @param arr
     * @return
     */
    public static Integer[] combineMinNumber(Integer[] arr) {
        Preconditions.checkNotNull(arr);
        ArrayUtil.sort(arr, (one, other) -> {
            Preconditions.checkArgument(one >= 0);
            Preconditions.checkArgument(other >= 0);
            return (one.toString() + other.toString()).compareTo(other.toString() + one.toString());
        });
        return arr;
    }

    /**
     * 取第index个丑数，丑数是指仅可以分解成2，3，5的数。
     *
     * @param index
     * @return
     */
    public static int uglyNumber(int index) {
        Preconditions.checkArgument(index >= 1);
        int[] ugly_numbers = new int[index];
        ugly_numbers[0] = 1;
        int multiply2_index = 0, multiply3_index = 0, multiply5_index = 0;

        int cur_index = 1;
        while (cur_index < index) {
            ugly_numbers[cur_index] = min(ugly_numbers[multiply2_index] * 2, ugly_numbers[multiply3_index] * 3, ugly_numbers[multiply5_index] * 5);

            while (ugly_numbers[multiply2_index] * 2 <= ugly_numbers[cur_index]) {
                multiply2_index++;
            }


            while (ugly_numbers[multiply3_index] * 3 <= ugly_numbers[cur_index]) {
                multiply3_index++;
            }

            while (ugly_numbers[multiply5_index] * 5 <= ugly_numbers[cur_index]) {
                multiply5_index++;
            }

            cur_index++;
        }
        return ugly_numbers[index - 1];
    }

    public static int strToInt(String str) {
        Preconditions.checkNotNull(str);
        Preconditions.checkArgument(str.length() > 0);
        Preconditions.checkArgument(!(str.length() == 1 && (str.charAt(0) == '+' || str.charAt(0) == '-')));
        int result = 0;
        boolean is_negative = false;
        int num_start = 0;
        if (str.charAt(0) == '-') {
            is_negative = true;
            num_start = 1;
        } else if (str.charAt(0) == '+') {
            num_start = 1;
        }

        for (int i = num_start; i < str.length(); i++) {
            int this_digit = str.charAt(i) - '0';
            if (this_digit < 0 || this_digit > 9) {
                throw new ArithmeticException("wrong format");
            }
            result = Math.multiplyExact(result, 10);
            if (is_negative) {
                result = Math.subtractExact(result, this_digit);
            } else {
                result = Math.addExact(result, this_digit);
            }
        }
        return result;
    }

    public static long strToLong(String str) {
        Preconditions.checkNotNull(str);
        Preconditions.checkArgument(str.length() > 0);
        Preconditions.checkArgument(!(str.length() == 1 && (str.charAt(0) == '+' || str.charAt(0) == '-')));
        long result = 0;
        boolean is_negative = false;
        int num_start = 0;
        if (str.charAt(0) == '-') {
            is_negative = true;
            num_start = 1;
        } else if (str.charAt(0) == '+') {
            num_start = 1;
        }

        for (int i = num_start; i < str.length(); i++) {
            long this_digit = str.charAt(i) - '0';
            if (this_digit < 0 || this_digit > 9) {
                throw new ArithmeticException("wrong format");
            }
            result = Math.multiplyExact(result, 10L);
            if (is_negative) {
                result = Math.subtractExact(result, this_digit);
            } else {
                result = Math.addExact(result, this_digit);
            }
        }
        return result;
    }

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
