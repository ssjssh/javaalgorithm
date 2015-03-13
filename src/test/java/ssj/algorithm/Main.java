package ssj.algorithm;

import ssj.algorithm.collections.AVLTree;

/**
 * Created by shenshijun on 15/2/1.
 */
public class Main {

    public static void main(String[] args) {
        int[] data = new int[]{1, 2, 3, 4, 34, 1, 234};
        AVLTree<Integer> tree = new AVLTree<>();
        for (int i : data) {
            tree.add(i);
        }
        System.out.println(tree);
    }
}
