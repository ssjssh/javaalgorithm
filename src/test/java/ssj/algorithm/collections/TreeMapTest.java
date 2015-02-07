package ssj.algorithm.collections;

import org.junit.Test;
import ssj.algorithm.MapIterator;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by shenshijun on 15/2/7.
 */
public class TreeMapTest {
    @Test
    public void testBase() {
        int map_size = 10;
        TreeMap<String, Integer> map = new TreeMap<>();
        String[] keys = new String[map_size];
        Integer[] values = new Integer[map_size];
        assertTrue(map.isEmpty());
        for (int i = 0; i < map_size; i++) {
            keys[i] = String.valueOf(Math.random() * 10000);
            values[i] = (int) (Math.random() * 10000);
            map.set(keys[i], values[i]);
        }
        assertEquals(map.size(), map_size);
        for (int i = 0; i < keys.length; i++) {
            assertTrue(map.containsKey(keys[i]));
            assertTrue(map.get(keys[i]).equals(values[i]));
        }
    }

    @Test
    public void testIterator() {
        TreeMap<String, Integer> map = new TreeMap<>();
        String[] keys = new String[1000];
        Integer[] values = new Integer[1000];
        assertTrue(map.isEmpty());
        for (int i = 0; i < 1000; i++) {
            keys[i] = String.valueOf(Math.random() * 10000);
            values[i] = (int) (Math.random() * 10000);
            map.set(keys[i], values[i]);
        }

        MapIterator<String, Integer> iterator = map.iterator();
        while (iterator.hasNext()) {
            MapIterator.Entry<String, Integer> node = iterator.next();
            for (int i = 0; i < keys.length; i++) {
                if (node.getKey().equals(keys[i])) {
                    assertEquals(node.getValue(), values[i]);
                    keys[i] = null;
                    break;
                }
            }
        }

        for (String s : keys) {
            assertEquals(s, null);
        }
    }
}
