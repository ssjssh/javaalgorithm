package ssj.algorithm.collections;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by shenshijun on 15/2/6.
 */
public class HashSetTest {
    @Test
    public void testBase() {
        HashSet<String> sets = new HashSet<>(100);
        String[] keys = new String[1000];
        assertTrue(sets.isEmpty());
        for (int i = 0; i < 1000; i++) {
            keys[i] = String.valueOf(Math.random() * 10000);
            sets.add(keys[i]);
        }
        assertEquals(sets.size(), 1000);
        for (int i = 0; i < keys.length; i++) {
            assertTrue(sets.contains(keys[i]));
            sets.delete(keys[i]);
            assertFalse(sets.contains(keys[i]));
        }
        assertTrue(sets.isEmpty());
    }
}
