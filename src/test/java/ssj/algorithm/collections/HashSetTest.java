package ssj.algorithm.collections;

import org.junit.Test;

import static org.junit.Assert.*;

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
            keys[i] = String.valueOf(i);
            sets.add(keys[i]);
        }
        assertEquals(sets.size(), 1000);
        for (String s : sets) {
            assertTrue(sets.contains(s));
        }
        for (int i = 0; i < keys.length; i++) {
            assertTrue(sets.contains(keys[i]));
            sets.delete(keys[i]);
            assertFalse(sets.contains(keys[i]));
        }
        assertTrue(sets.isEmpty());
    }

    @Test
    public void testDupl() {
        HashSet<Integer> sets = new HashSet<>(100);
        assertTrue(sets.isEmpty());
        for (int i = 0; i < 1000; i++) {
            sets.add(i);
            sets.add(i);
            sets.add(i);
            sets.add(i);
            sets.add(i);
        }
        assertEquals(sets.size(), 1000);
        for (int s : sets) {
            assertTrue(sets.contains(s));
        }
        for (int i = 0; i < 1000; i++) {
            assertTrue(sets.contains(i));
            sets.delete(i);
            assertFalse(sets.contains(i));
        }
        assertTrue(sets.isEmpty());
    }
}
