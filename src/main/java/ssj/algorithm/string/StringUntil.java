package ssj.algorithm.string;

import com.google.common.base.Preconditions;

import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shenshijun on 15/2/1.
 */
public class StringUntil {
    /**
     * 比较两个字符串的中的字符是否完全不同
     * 使用数组和BitSet都可以实现，但是BitSet好管理一点。
     * 复杂度是O(s1.length()+s2.length())
     *
     * @param s1
     * @param s2
     * @return
     */
    public static boolean diffChars(String s1, String s2) {
        if (s1 == null && s2 == null) {
            return true;
        }
        Preconditions.checkNotNull(s1);
        Preconditions.checkNotNull(s2);
        BitSet set1 = new BitSet();
        BitSet set2 = new BitSet();
        for (int i = 0; i < s1.length(); i++) {
            set1.set((int) s1.charAt(i));
        }

        for (int i = 0; i < s2.length(); i++) {
            set2.set((int) s2.charAt(i));
        }

        for (int i = 0; i < Math.min(set1.length(), set2.length()); i++) {
            if (set1.get(i) && set2.get(i)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断一个字符串的字符是否各不相同
     *
     * @param s
     * @return
     */
    public static boolean uniqueChar(String s) {
        Preconditions.checkNotNull(s);
        BitSet set = new BitSet(s.length());
        for (int i = 0; i < s.length(); i++) {
            int char_int = (int) s.charAt(i);
            if (set.get(char_int)) {
                return false;
            }
            set.set(char_int);
        }
        return true;
    }

    public static boolean permutation(String s1, String s2) {
        if (s1 == null && s2 == null) {
            return true;
        } else if (s1 == null || s2 == null) {
            return false;
        } else if (s1.length() != s2.length()) {
            return false;
        }
        char[] chars1 = s1.toCharArray();
        Arrays.sort(chars1);

        char[] chars2 = s2.toCharArray();
        Arrays.sort(chars2);
        return Arrays.equals(chars1, chars2);
    }

    public static boolean quickPermutation(String s1, String s2) {
        if (s1 == null && s2 == null) {
            return true;
        } else if (s1 == null || s2 == null) {
            return false;
        } else if (s1.length() != s2.length()) {
            return false;
        }

        Map<Character, Integer> static1 = computeStatistics(s1);
        Map<Character, Integer> static2 = computeStatistics(s2);
        for (Map.Entry one : static1.entrySet()) {
            if (one.getValue().equals(static2.get(one.getKey()))) {
                return false;
            }
            static2.remove(one.getKey());
        }

        if (static1.isEmpty()) {
            return true;
        }
        return false;
    }

    private static Map<Character, Integer> computeStatistics(String s1) {
        Map<Character, Integer> static1 = new HashMap<>();
        for (int i = 0; i < s1.length(); i++) {
            Integer value = static1.get(s1.charAt(i));
            if (value == null) {
                static1.put(s1.charAt(i), 0);
                value = 0;
            }
            value++;
            static1.put(s1.charAt(i), value);
        }
        return static1;
    }

    public static String replaceSpace(String s) {
        StringBuilder sb = new StringBuilder(0);
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ' ') {
                sb.append("%20");
            } else {
                sb.add(s.charAt(i));
            }
        }
        return sb.toString();
    }

    public static String compress(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str.length());
        int count = 0;
        int last_index = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == str.charAt(last_index)) {
                count++;
            } else {
                sb.add(str.charAt(last_index));
                sb.append(count);
                last_index = i;
                count = 1;
            }
        }
        sb.add(str.charAt(last_index));
        sb.append(count);

        String com_str = sb.toString();
        return (com_str.length() < str.length()) ? com_str : str;
    }
}
