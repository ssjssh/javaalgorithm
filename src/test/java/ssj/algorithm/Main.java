package ssj.algorithm;

/**
 * Created by shenshijun on 15/2/1.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println(Math.pow(2,3));
    }

    public static <T> T[] test() {
        Object[] arr = new Object[]{"1", "2", "3", "4"};
        System.out.println((T[]) arr);
        return (T[]) arr;
    }
}
