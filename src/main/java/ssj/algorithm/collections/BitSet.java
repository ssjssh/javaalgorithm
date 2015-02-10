package ssj.algorithm.collections;

import com.google.common.base.Preconditions;

/**
 * Created by shenshijun on 15/2/10.
 */
public class BitSet {
    private Vector<Long> bits;
    private static final long UNSIGNED_MAX_LONG = 0xffffffffffffffffL;

    public BitSet(int size) {
        Preconditions.checkArgument(size > 1);
        int long_count = (size - 1) / Long.SIZE + 1;
        bits = new Vector<>(long_count);
        for (int i = 0; i < long_count; i++) {
            bits.add(0L);
        }
    }

    public boolean get(int index) {
        Preconditions.checkArgument(index >= 0);
        if (index < size()) {
            return (bits.get(vectorPos(index)) & (1L << longPos(index))) != 0;
        } else {
            return false;
        }
    }

    private int longPos(int index) {
        return (Long.SIZE - 1 - index % Long.SIZE);
    }

    private int vectorPos(int index) {
        return index / Long.SIZE;
    }

    public void set(int index) {
        Preconditions.checkArgument(index >= 0);
        int cur_size = size();
        if (index >= cur_size) {
            for (int i = 0; i < (index - cur_size) / Long.SIZE + 1; i++) {
                bits.add(0L);
            }
        }
        long this_long = bits.get(vectorPos(index));
        this_long |= (1L << longPos(index));
        bits.set(vectorPos(index), this_long);
    }

    public void clear(int index) {
        Preconditions.checkPositionIndex(index, size());
        long this_long = bits.get(vectorPos(index));
        this_long &= ~(1L << longPos(index));
        bits.set(vectorPos(index), this_long);
    }

    public void clearRange(int start, int end) {
        Preconditions.checkPositionIndexes(start, end, size());
        int cur_index = start;
        while (cur_index < end) {
            if (end - cur_index < Long.SIZE) {
                clearThisRange(cur_index, end);
                break;
            }
            int next_long_index = (cur_index / Long.SIZE + 1) * Long.SIZE - 1;
            clearThisRange(cur_index, next_long_index);
            cur_index = next_long_index;
        }
    }

    private void clearThisRange(int start, int end) {
        long this_long = bits.get(vectorPos(end));
        this_long &= (UNSIGNED_MAX_LONG << (Long.SIZE - end - start + 1)) >> ((start % Long.SIZE) - 1);
        bits.set(vectorPos(end), this_long);
    }

    private int size() {
        return bits.size() * Long.SIZE;
    }
}
