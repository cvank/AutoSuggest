package com.techexplore.autosuggest.framework.processor.fuzzy;

import com.techexplore.autosuggest.domain.AutoSuggestRequest;
import com.techexplore.autosuggest.framework.ApplicationConstants;
import com.techexplore.autosuggest.framework.processor.SearchAlgorithmUtil;
import com.techexplore.autosuggest.framework.searchalgorithm.TrieDataInteractor;
import com.techexplore.autosuggest.framework.searchalgorithm.TrieNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Fuzzy score strategy utilizes Apache commons text matching algorithm.
 * <p>
 * Created by chandrashekar.v on 9/14/2017.
 */
@Component(value = ApplicationConstants.FUZZY_SCORE)
public class FuzzyScoreStrategy implements FuzzyStrategy {


    private static final Logger LOG = LoggerFactory.getLogger(FuzzyScoreStrategy.class);

    @Override
    public Optional<List<String>> search(AutoSuggestRequest request, TrieNode root) {
        LOG.info("Fuzzy score strategy search initiated.");
        List<String> words = TrieDataInteractor.fetchWords(request.getStart(), root);
        LOG.info("Total words rebuilt:" + words.size());
        return SearchAlgorithmUtil.applyFuzzy(request.getStart(), words, request.getAtmost());

    }
}
