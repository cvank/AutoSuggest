package com.techexplore.autosuggest.framework.processor;

import com.techexplore.autosuggest.domain.AutoSuggestRequest;
import com.techexplore.autosuggest.framework.ApplicationConstants;
import com.techexplore.autosuggest.framework.processor.fuzzy.AlgorithmIdentifier;
import com.techexplore.autosuggest.framework.processor.fuzzy.FuzzyContext;
import com.techexplore.autosuggest.framework.processor.fuzzy.FuzzyStrategy;
import com.techexplore.autosuggest.framework.searchalgorithm.TrieNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Processor that populates suggestions by identifying the algorithm and triggering search.
 * Algorithm details and search algorithm implementation completely hidden from the caller.
 * <p>
 * Created by chandrashekar.v on 9/12/2017.
 */
@Service(value = ApplicationConstants.FUZZY)
public class FuzzyAutoSuggestProcessor extends AbstractAutoSuggestProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(FuzzyAutoSuggestProcessor.class);

    @Autowired
    AlgorithmIdentifier algorithmIdentifier;

    /**
     * Initiates fuzzy search by
     * 1. identifying the algorithm
     * 2. setting the strategy to conetxt
     * 3. trigger auto suggest.
     *
     * @param request
     * @param root
     * @return
     */
    @Override
    public List<String> search(AutoSuggestRequest request, final TrieNode root) {
        // Identify strategy instance
        FuzzyStrategy strategy = algorithmIdentifier.identify(request.getSearchAlgorithm());

        // Set strategy to context
        FuzzyContext fuzzyContext = new FuzzyContext(strategy);

        // Trigger and return auto suggest results on the current context.
        return fuzzyContext.autoSuggest(request, root);

    }


}
