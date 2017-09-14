package com.techexplore.autosuggest.framework.processor.fuzzy;

import com.techexplore.autosuggest.domain.AutoSuggestRequest;
import com.techexplore.autosuggest.framework.searchalgorithm.TrieNode;

import java.util.List;
import java.util.Optional;

/**
 * Created by chandrashekar.v on 9/14/2017.
 */
public interface FuzzyStrategy {

    public Optional<List<String>> search(AutoSuggestRequest request, final TrieNode root);
}
