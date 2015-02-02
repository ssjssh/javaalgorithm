package ssj.algorithm.string;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by shenshijun on 15/2/2.
 */
public class StringUtilTest {
    @Test
    public void testUniqueChar() {
        assertTrue(StringUntil.uniqueChar("我是中国人"));
        assertFalse(StringUntil.uniqueChar("I am a chinese"));
    }

    @Test
    public void testDiff() {
        assertTrue(StringUntil.diffChars("我是中国人", "I am an chinese"));
        assertFalse(StringUntil.diffChars("我是中国人", "I am an chinese,中国人"));
    }

    @Test
    public void testPermutation() {
        assertTrue(StringUntil.permutation("I am a chinese", "eseinch a ma I"));
        assertFalse(StringUntil.permutation("I am a chinese", "esainch a ma I"));
    }

    @Test
    public void testQuickPermutation() {
        assertTrue(StringUntil.permutation("I am a chinese", "eseinch a ma I"));
        assertFalse(StringUntil.permutation("I am a chinese", "esainch a ma I"));
    }

    @Test
    public void testReplaceSpace() {
        assertEquals(StringUntil.replaceSpace("Mr John Smith"),"Mr%20John%20Smith");
    }

}
