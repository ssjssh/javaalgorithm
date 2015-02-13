package ssj.algorithm.collections;

import org.junit.Test;
import ssj.algorithm.MapIterator;

import static org.junit.Assert.*;

/**
 * Created by shenshijun on 15/2/13.
 */
public class LinkedHashMapTest {
    @Test
    public void testBase() {
        LinkedHashMap.LinkedHashMapBuilder<String, Integer> builder = LinkedHashMap.builder();
        LinkedHashMap<String, Integer> map = builder.build();
        String[] keys = new String[1000];
        Integer[] values = new Integer[1000];
        assertTrue(map.isEmpty());
        for (int i = 0; i < 1000; i++) {
            keys[i] = String.valueOf(Math.random() * 10000);
            values[i] = (int) (Math.random() * 10000);
            map.set(keys[i], values[i]);
        }
        assertEquals(map.size(), 1000);
        for (int i = 0; i < keys.length; i++) {
            assertTrue(map.containsKey(keys[i]));
            assertTrue(map.get(keys[i]).equals(values[i]));
            assertTrue(map.remove(keys[i]).equals(values[i]));
            assertFalse(map.containsKey(keys[i]));
        }
        assertTrue(map.isEmpty());
    }

    @Test
    public void testIterator() {
        int map_size = 1000;
        LinkedHashMap.LinkedHashMapBuilder<String, Integer> builder = LinkedHashMap.builder();
        LinkedHashMap<String, Integer> map = builder.build();
        String[] keys = new String[map_size];
        Integer[] values = new Integer[map_size];
        assertTrue(map.isEmpty());
        for (int i = 0; i < map_size; i++) {
            keys[i] = String.valueOf(i);
            values[i] = (i);
            map.set(keys[i], values[i]);
        }

        assertEquals(map.size(), map_size);

        MapIterator<String, Integer> iterator = map.iterator();
        for (int i = 0; i < map_size && iterator.hasNext(); i++) {
            MapIterator.Entry<String, Integer> node = iterator.next();
            assertEquals(keys[i], node.getKey());
            assertEquals(values[i], node.getValue());
            keys[i] = null;
            values[i] = null;
        }

        for (int i = 0; i < map_size; i++) {
            assertTrue(keys[i] == null);
            assertTrue(values[i] == null);
        }
    }
}
