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

    public void replace(int start, int end, long value) {
        Preconditions.checkPositionIndexes(start, end, size());
        clearRange(start, end);
        if (vectorPos(start) == vectorPos(end)) {
            long this_long = bits.get(vectorPos(end));
            long this_mask = sliceLongTrue(value, longPos(start), longPos(end));
            bits.set(vectorPos(end), this_long & this_mask);
        } else {
            long front_long = bits.get(vectorPos(start));
            long front_mask = sliceLongTrue(value, 0, Long.SIZE - 1 - longPos(start));
            bits.set(vectorPos(start), front_long & front_mask);
            long back_long = bits.get(vectorPos(end));
            long back_mask = sliceLongTrue(value, longPos(end), longPos(start));
            bits.set(vectorPos(end), back_long & back_mask);
        }
    }

    private long sliceLongFalse(long value, int start, int end) {
        Preconditions.checkPositionIndexes(start, end, Long.SIZE);
        long back_mask = ~((1L << (Long.SIZE - end)) - 1);
        value &= back_mask;
        long front_mask = ~(UNSIGNED_MAX_LONG << (Long.SIZE - start - 1));
        value &= front_mask;
        return value;
    }

    private long sliceLongTrue(long value, int start, int end) {
        Preconditions.checkPositionIndexes(start, end, Long.SIZE);
        long back_mask = (1L << (Long.SIZE - end)) - 1;
        value |= back_mask;
        long front_mask = UNSIGNED_MAX_LONG << (Long.SIZE - start - 1);
        value |= front_mask;
        return value;
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
        while ((end - cur_index) > Long.SIZE) {
            int next_long_index = (cur_index / Long.SIZE + 1) * Long.SIZE - 1;
            clearThisRange(cur_index, next_long_index);
            cur_index = next_long_index;
        }
        clearThisRange(cur_index, end);
    }

    private void clearThisRange(int start, int end) {
        long this_long = bits.get(vectorPos(end));
        this_long &= ~(UNSIGNED_MAX_LONG << (Long.SIZE - (end - start + 1)) >> (start % Long.SIZE));
        bits.set(vectorPos(end), this_long);
    }

    private int size() {
        return bits.size() * Long.SIZE;
    }
}
