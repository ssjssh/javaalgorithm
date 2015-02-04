package ssj.algorithm.math;

import com.google.common.base.Preconditions;
import ssj.algorithm.collections.LinkedList;
import ssj.algorithm.string.StringBuilder;

/**
 * 使用链表实现BigInt
 * Created by shenshijun on 15/2/3.
 */
public class BigInt {
    private LinkedList<Character> _values;
    private boolean _sign;

    public BigInt(int origin) {
        _values = new LinkedList<>();
        _sign = (origin >= 0);
        addTailBit(Math.abs(origin));
    }

    public void addTailBit(int n) {
        Preconditions.checkArgument(n >= 0);
        String number_string = String.valueOf(n);
        for (int i = 0; i < number_string.length(); i++) {
            _values.append(number_string.charAt(i));
        }

    }

    public void addHeadBit(int n) {
        Preconditions.checkArgument(n >= 0);
        String number_string = String.valueOf(n);
        for (int i = 0; i < number_string.length(); i++) {
            _values.appendHead(number_string.charAt(i));
        }
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

    public BigInt add(BigInt other) {
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
