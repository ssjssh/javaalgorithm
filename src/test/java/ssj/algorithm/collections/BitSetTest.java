package ssj.algorithm.collections;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by shenshijun on 15/2/10.
 */
public class BitSetTest {
    @Test
    public void testBase() {
        BitSet bs = new BitSet(60);
        Vector<Integer> vector = new Vector<>(1000);
        for (int i = 0; i < 1000; i++) {
            vector.add((int) (Math.random() * 10000));
            bs.set(vector.get(i));
        }

        for (int i = 0; i < 1000; i++) {
            assertTrue(bs.get(vector.get(i)));
        }
    }

    @Test
    public void testClear() {
        BitSet bs = new BitSet(60);
        HashSet<Integer> set = new HashSet<>();
        for (int i = 0; i < 1000; i++) {
            int rand = (int) (Math.random() * 10000);
            set.add(rand);
            bs.set(rand);
        }

        for (int i : set) {
            assertTrue(set.contains(i));
        }

        for (int i : set) {
            if (!bs.get(i)){
                System.out.println(i);
            }
            assertTrue(bs.get(i));
            bs.clear(i);
            assertFalse(bs.get(i));
        }
    }


}
