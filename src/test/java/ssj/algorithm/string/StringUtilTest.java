package ssj.algorithm.string;

import org.junit.Test;

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

}
