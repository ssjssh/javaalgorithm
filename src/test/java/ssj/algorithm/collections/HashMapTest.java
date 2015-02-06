package ssj.algorithm.collections;

import org.junit.Test;
import ssj.algorithm.MapIterator;

import static org.junit.Assert.*;

/**
 * Created by shenshijun on 15/2/6.
 */
public class HashMapTest {
    @Test
    public void testBase() {
        HashMap.MapBuilder<String, Integer> builder = HashMap.builder();
        HashMap<String, Integer> map = builder.build();
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
        HashMap.MapBuilder<String, Integer> builder = HashMap.builder();
        HashMap<String, Integer> map = builder.build();
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

    @Test
    public void testIteratorRemove() {
        int map_size = 10;
        HashMap.MapBuilder<String, Integer> builder = HashMap.builder();
        HashMap<String, Integer> map = builder.build();
        String[] keys = new String[map_size];
        Integer[] values = new Integer[map_size];
        assertTrue(map.isEmpty());
        for (int i = 0; i < map_size; i++) {
            keys[i] = String.valueOf(Math.random() * 10000);
            values[i] = (int) (Math.random() * 10000);
            map.set(keys[i], values[i]);
        }

        MapIterator<String, Integer> iterator = map.iterator();
        while (iterator.hasNext()) {
            MapIterator.Entry<String, Integer> node = iterator.next();
            iterator.remove();
            assertFalse(map.containsKey(node.getKey()));
            for (int i = 0; i < keys.length; i++) {
                if (node.getKey().equals(keys[i])) {
                    keys[i] = null;
                    break;
                }
            }
        }
        assertTrue(map.isEmpty());

        for (String s : keys) {
            assertEquals(null, s);
        }
    }
}
