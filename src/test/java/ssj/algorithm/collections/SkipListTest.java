package ssj.algorithm.collections;

import org.junit.Test;
import ssj.algorithm.ArrayUtil;

import java.util.Iterator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by shenshijun on 15/2/12.
 */
public class SkipListTest {
    @Test
    public void testAdd() {
        SkipList<String> skipList = new SkipList<>();
        String[] values = new String[1000];
        for (int i = 0; i < 1000; i++) {
            values[i] = String.valueOf(Math.random() * 1000);
            skipList.add(values[i]);
        }
        assertTrue(skipList.size() == 1000);
        for (int i = 0; i < 1000; i++) {
            assertTrue(skipList.contains(values[i]));
            skipList.delete(values[i]);
            assertFalse(skipList.contains(values[i]));
        }
        assertTrue(skipList.isEmpty());
        System.out.println(skipList);
    }

    @Test
    public void testIterator() {
        SkipList<String> skipList = new SkipList<>();
        String[] values = new String[1000];
        for (int i = 0; i < 1000; i++) {
            values[i] = String.valueOf(Math.random() * 1000);
            skipList.add(values[i]);
        }
        ArrayUtil.sort(values);
        assertTrue(skipList.size() == 1000);
        Iterator<String> iterator = skipList.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            assertTrue(values[i].equals(iterator.next()));
            iterator.remove();
            assertFalse(skipList.contains(values[i]));
            i++;
        }
        assertTrue(i==1000);
        assertTrue(skipList.isEmpty());
    }

}
