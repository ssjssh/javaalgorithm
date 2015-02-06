package ssj.algorithm.collections;

import org.junit.Test;

/**
 * Created by shenshijun on 15/2/6.
 */
public class TrieTest {

    @Test
    public void testBase() {
        Trie trie = new Trie();
        trie.add("1234567");
        trie.add("12346789101112");
//        trie.add("12346799101112");
//        trie.add("123467109101112");
//        trie.add("12346711101112");
//        trie.add("12346713101112");
//        trie.add("12346715101112");
//        System.out.println(trie.search("1234567"));
    }
}
