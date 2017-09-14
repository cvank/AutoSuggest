package com.techexplore.autosuggest.framework.processor.fuzzy;

import com.techexplore.autosuggest.domain.AutoSuggestRequest;
import com.techexplore.autosuggest.framework.searchalgorithm.TrieNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Context class that holds the strategy instance and will redirect control to respective stragy implementation at runtime.
 * <p>
 * <p>
 * Created by chandrashekar.v on 9/14/2017.
 */
public class FuzzyContext {

    /**
     * Algorithm strategy to be set at run time.
     */
    private FuzzyStrategy strategy;

    public FuzzyContext(FuzzyStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Calls auto suggest against the strategy at run time.
     *
     * @param request
     * @param root
     * @return
     */
    public List<String> autoSuggest(AutoSuggestRequest request, final TrieNode root) {
        Optional<List<String>> suggestions = strategy.search(request, root);
        return suggestions.orElse(new ArrayList<>());
    }

}
