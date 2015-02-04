package ssj.algorithm.math;

import com.google.common.base.Preconditions;
import ssj.algorithm.collections.LinkedList;
import ssj.algorithm.string.StringBuilder;

import java.util.Comparator;

/**
 * 使用链表实现BigInt
 * Created by shenshijun on 15/2/3.
 */
public class BigInt implements Comparator<BigInt> {
    private LinkedList<Character> _values;
    private boolean _sign;

    public BigInt(int origin) {
        _values = new LinkedList<>();
        _sign = (origin >= 0);
        addTailBit(Math.abs(origin));
    }

    public BigInt() {
        _values = new LinkedList<>();
        _sign = true;
        addTailBit(0);
    }

    public void setPositive() {
        _sign = true;
    }

    public void setNegative() {
        _sign = false;
    }

    public int bitCount() {
        return _values.size();
    }

    public void setBit(int index, int bit) {
        Preconditions.checkPositionIndex(index, bitCount());
        Preconditions.checkArgument(bit >= 0 && bit < 10);
        _values.set(index, intToChars(bit)[0]);
    }

    private char[] intToChars(int number) {
        String number_string = String.valueOf(number);
        return number_string.toCharArray();
    }


    public void addTailBit(int n) {
        Preconditions.checkArgument(n >= 0);
        for (char c : intToChars(n)) {
            _values.add(c);
        }

    }

    public void addHeadBit(int n) {
        Preconditions.checkArgument(n >= 0);
        for (char c : intToChars(n)) {
            _values.appendHead(c);
        }
    }

    @Override
    public int compare(BigInt o1, BigInt o2) {
//        return o1.sub(o2);
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BigInt bigInt = (BigInt) o;

        if (_sign != bigInt._sign) return false;
        if (!_values.equals(bigInt._values)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = _values.hashCode();
        result = 31 * result + (_sign ? 1 : 0);
        return result;
    }

    public boolean isPositive() {
        return _sign;
    }

    public boolean isNegative() {
        return !isPositive();
    }

    public BigInt add(BigInt other) {
        BigInt result = new BigInt();
        if (other.isNegative() && isPositive()) {
            other.setPositive();
            return sub(other);
        } else if (other.isPositive() && isNegative()) {
//            other.add()
        }
        return null;
    }

    public BigInt sub(BigInt other) {
        return null;
    }

    public BigInt mul(BigInt other) {
        return null;
    }

    public BigInt div(BigInt other) {
        return null;
    }

    private LinkedList<Character> _add(LinkedList<Character> other) {
        return null;
    }

    private LinkedList<Character> _sub(LinkedList<Character> other) {
        return null;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(_values.size());
        sb.add(_sign ? '+' : '-');
        for (Character c : _values) {
            sb.add(c);
        }
        return sb.toString();
    }

}
