package ssj.algorithm.collections;

import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by shenshijun on 15/2/3.
 */
public class LinkedListTest {
    @Test
    public void testAddElement() {
        LinkedList<String> linkedList = new LinkedList<>();
        assertEquals(linkedList.size(), 0);
        linkedList.add("sshs");
        linkedList.append("ssh");
        linkedList.appendHead("head");
        linkedList.set(1, "1");
        assertEquals(linkedList.size(), 3);
        assertEquals(linkedList.get(0), "head");
        assertEquals(linkedList.get(1), "1");
        assertEquals(linkedList.get(2), "ssh");
    }

    @Test
    public void testRemoveElement() {
        LinkedList<String> linkedList = new LinkedList<>();
        assertEquals(linkedList.size(), 0);
        linkedList.add("sshs");
        linkedList.append("ssh");
        linkedList.appendHead("head");
        assertEquals(linkedList.size(), 3);
        linkedList.remove(0);
        linkedList.remove(0);
        assertEquals(linkedList.size(), 1);
        assertEquals(linkedList.head(), "ssh");
    }

    @Test
    public void testIterator() {
        LinkedList<String> linkedList = new LinkedList<>();
        assertEquals(linkedList.size(), 0);
        linkedList.add("sshs");
        linkedList.append("ssh");
        linkedList.appendHead("head");
        Iterator<String> iterator = linkedList.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
            iterator.remove();
        }

    }

    @Test
    public void testGet() {
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 0; i < 200; i++) {
            list.add(i);
        }

        System.out.println(list);
        for (int i = 199; i >= 0; i--) {
            assertTrue(list.getReverse(i).equals(Integer.valueOf(199 - i)));
        }

    }

    @Test
    public void testPartition() {
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 0; i < 200; i++) {
            list.add((int) (Math.random() * 200));
        }

        System.out.println(list);
        System.out.println(list.tail()) ;
        list.partition(list.tail(), (a, b) -> a - b);
        System.out.println(list);
    }

    @Test
    public void testIsPalindromic() {
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 0; i < 200; i++) {
            list.add(i);
        }

        for (int i = 199; i >= 0; i--) {
            list.add(i);
        }
        System.out.println(list);
        assertTrue(list.isPalindromic());
        list.set(199, 0);
        assertFalse(list.isPalindromic());
    }

    @Test
    public void testSort() {
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 0; i < 200; i++) {
            list.add(i);
        }

        for (int i = 199; i >= 0; i--) {
            list.add(i);
        }

        System.out.println(list.sort((a, b) -> a - b));
    }


}
