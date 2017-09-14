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
 * Jaro Winkler Distance algorithm is a similarity algorithm that indicates percenatge of matched characters between two given character sequences.
 * <p>
 * <p>
 * <p>Ref:  http://en.wikipedia.org/wiki/Jaro%E2%80%93Winkler_distance.</p>
 * Created by chandrashekar.v on 9/14/2017.
 */
@Component(value = ApplicationConstants.JARO)
public class JaroWinklerDistanceStrategy implements FuzzyStrategy {

    private static final Logger LOG = LoggerFactory.getLogger(JaroWinklerDistanceStrategy.class);

    @Override
    public Optional<List<String>> search(AutoSuggestRequest request, TrieNode root) {

        List<String> words = TrieDataInteractor.fetchWords(request.getStart(), root);
        LOG.info("Total words rebuilt:" + words.size());
        return SearchAlgorithmUtil.applyJaro(request.getStart(), words, request.getAtmost());

    }
}
