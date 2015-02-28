package ssj.algorithm.string;

import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * Created by shenshijun on 15/2/1.
 */
public class StringBuilderTest {

    @Test
    public void testAdd() {
        StringBuilder s = new StringBuilder(10);
        assertEquals(s.size(), 0);
        assertEquals(s.capacity(), 10);
        assertTrue(s.isEmpty());
        s.append("ssj");
        assertEquals(s.size(), 3);
        assertFalse(s.isEmpty());
        for (int i = 0; i < 100; i++) {
            s.append("ssj");
        }
        assertEquals(s.size(), 303);
        System.out.println(s);
    }

    @Test
    public void testDelete() {
        StringBuilder sb = new StringBuilder(0);
        for (int i = 0; i < 10; i++) {
            sb.append(i);
        }
        assertEquals(sb.size(), 10);
        sb.remove(9);
        sb.remove(1);
        assertEquals(sb.size(), 8);
        assertEquals(sb.toString(), "02345678");
        sb.delete('8');
        assertEquals(sb.size(), 7);
        assertEquals(sb.toString(), "0234567");
        sb.delete('9');
        assertEquals(sb.size(), 7);
        assertEquals(sb.toString(), "0234567");
    }

    @Test
    public void testIterate() {
        StringBuilder sb = new StringBuilder("0123456789");
        assertEquals(sb.get(1), Character.valueOf('1'));
        Iterator<Character> iterator = sb.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            Character character = iterator.next();
            assertTrue(Integer.parseInt(character.toString()) == i);
            iterator.remove();
            i++;
        }
    }
}
