package com.techexplore.autosuggest.framework.searchalgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by chandrashekar.v on 9/14/2017.
 */
public class TrieDataInteractor {

    /**
     * Initiate fetching data from already built Trie Data structure.
     *
     * @param word
     * @param root
     * @return
     */
    public static List<String> fetchWords(String word, final TrieNode root) {
        List<String> allWords = new ArrayList<>();
        fetchWordsRecursively(allWords, word, root);
        return allWords;
    }

    /**
     * Reads and stores completed words from Trie data to List.
     *
     * @param allWords
     * @param word
     * @param root
     */
    private static void fetchWordsRecursively(List<String> allWords, String word, final TrieNode root) {

        if (root.isTerminated()) {
            allWords.add(root.getWord());
        }

        if (root.getChildren().size() == 0)
            return;

        for (Map.Entry<Character, TrieNode> entry : root.getChildren().entrySet()) {
            fetchWordsRecursively(allWords, word, entry.getValue());
        }


    }
}
