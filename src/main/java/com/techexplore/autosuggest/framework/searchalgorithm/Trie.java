package com.techexplore.autosuggest.framework.searchalgorithm;

import java.util.Arrays;
import java.util.List;

/**
 * Holds parent node of the data structure. parent node in turn mapped to its corresponding child nodes.
 * Created by chandrashekar.v on 9/12/2017.
 */
public class Trie {

    /**
     * Root node.
     */
    private TrieNode root;

    public Trie() {
    }

    public Trie(String[] words) {
        root = new TrieNode();
        Arrays.stream(words).parallel().forEach(word -> root.insert(word));
    }


    /**
     * Load nodes from the given list of strings.
     *
     * @param words
     */
    public Trie(List<String> words) {
        root = new TrieNode();
        words.parallelStream().forEach(word -> root.insert(word));
    }

    public TrieNode getRoot() {
        return root;
    }

}
