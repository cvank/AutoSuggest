package com.techexplore.autosuggest.framework.searchalgorithm;

import java.util.*;

/**
 * Created by chandrashekar.v on 9/12/2017.
 */
public class Trie {

    private TrieNode root;

    public Trie() {
    }

    public Trie(String[] words) {
        root = new TrieNode();
        Arrays.stream(words)./*parallel().*/forEach(word -> root.insert(word));
    }


    public Trie(List<String> words) {
        root = new TrieNode();
        words./*parallelStream().*/forEach(word -> root.insert(word));
    }

    public TrieNode getRoot() {
        return root;
    }

}
