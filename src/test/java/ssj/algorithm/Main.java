package ssj.algorithm;

/**
 * Created by shenshijun on 15/2/1.
 */
public class Main {
    public static void main(String[] args) {
        String str1 = new String("840.3491768174221");
        String str2 = new String("840.3491768174221");
        System.out.println(str1.compareTo(str2));
        System.out.println(str1.equals(str2));
    }

    public static <T> T[] test() {
        Object[] arr = new Object[]{"1", "2", "3", "4"};
        System.out.println((T[]) arr);
        return (T[]) arr;
    }
}
