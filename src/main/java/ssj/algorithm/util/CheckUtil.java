package ssj.algorithm.util;

import java.util.ConcurrentModificationException;

/**
 * Created by shenshijun on 15/2/13.
 */
public class CheckUtil {
    public static void checkCurrencyModify(int expect,int actual) {
        if (expect != actual) {
            throw new ConcurrentModificationException();
        }
    }
}
