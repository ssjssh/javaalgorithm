package ssj.algorithm.string;

import com.google.common.base.Preconditions;
import ssj.algorithm.Set;
import ssj.algorithm.collections.HashSet;

import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shenshijun on 15/2/1.
 */
public class StringUntil {
    private StringUntil() {
    }

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

    /**
     * 判断是不是变位词。
     *
     * @param s1
     * @param s2
     * @return
     */
    public static boolean isPermutation(String s1, String s2) {
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

    /**
     * kmp算法实现，返回第一个匹配发生的位置
     *
     * @param str
     * @param pattern
     * @return
     */
    public static int search(String str, String pattern) {
        Preconditions.checkNotNull(str);
        Preconditions.checkNotNull(pattern);
        Preconditions.checkArgument(!pattern.isEmpty());

        int[] next_arr = buildNext(pattern);

        for (int cur_index = 0, pattern_index = 0; cur_index < str.length(); cur_index++) {
            while (pattern_index > 0 && str.charAt(cur_index) != pattern.charAt(pattern_index)) {
                pattern_index = next_arr[pattern_index - 1];
            }

            if (pattern.charAt(pattern_index) == str.charAt(cur_index)) {
                pattern_index++;
            }

            if (pattern_index == pattern.length()) {
                return cur_index - pattern_index + 1;
            }
        }

        return -1;
    }

    /**
     * 创建数组对应移动表
     * A  B  C  D  A  B  D
     * 0  0  0  0  1  2  0
     * 使用这种表示格式近似表示移动位置，但是在不匹配发生的时候都是用前面元素的位置作为移动
     * 比如如果最后一个D发送不匹配时，使用B的移动位置也就是2移动
     *
     * @param pattern
     * @return
     */
    private static int[] buildNext(String pattern) {
        int[] result = new int[pattern.length()];
        for (int cur_index = 2, last_match = 0; cur_index < pattern.length(); cur_index++) {
            while (last_match > 0 && pattern.charAt(cur_index) != pattern.charAt(last_match)) {
                last_match = result[last_match];
            }

            if (pattern.charAt(cur_index) == pattern.charAt(last_match)) {
                last_match++;
            }

            result[cur_index] = last_match;
        }
        return result;
    }

    /**
     * 要处理变位词需要对输入的单词进行两次排序
     * 1, 按照字母表顺序对每一个串中的字母进行排序
     * 2，使用一个HashMap收集单词
     * 假设字符串平均长度m，字符串个数n。
     * 第一步复杂度是O(nmlgm)，第二步复杂度是O(n)
     * 所以算法总体的复杂度就是O(nmlgm)
     *
     * @param words
     * @return
     */
    public static Map<String, Set<Integer>> findAnagram(String[] words) {
        Preconditions.checkNotNull(words, "words");
        String[] new_array = Arrays.copyOf(words, words.length);
        for (int i = 0; i < new_array.length; i++) {
            char[] char_array = new_array[i].toCharArray();
            Arrays.sort(char_array);
            new_array[i] = String.valueOf(char_array);
        }
        System.out.println(Arrays.toString(new_array));
        Map<String, Set<Integer>> result_map = new HashMap<>();
        for (int i = 0; i < new_array.length; i++) {
            result_map.putIfAbsent(new_array[i], new HashSet<>());
            result_map.get(new_array[i]).add(i);
        }
        return result_map;
    }
}
