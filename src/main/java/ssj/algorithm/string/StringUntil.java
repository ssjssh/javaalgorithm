package ssj.algorithm.string;

import com.google.common.base.Preconditions;

import java.util.BitSet;

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
}
