package com.techexplore.autosuggest.service;

import com.techexplore.autosuggest.domain.AutoSuggestRequest;
import com.techexplore.autosuggest.framework.ApplicationConstants;
import com.techexplore.autosuggest.framework.Response.AutoSuggestResponse;
import com.techexplore.autosuggest.framework.Response.ResponseCode;
import com.techexplore.autosuggest.framework.data.DataLoader;
import com.techexplore.autosuggest.framework.processor.AbstractAutoSuggestProcessor;
import com.techexplore.autosuggest.framework.searchalgorithm.Trie;
import com.techexplore.autosuggest.framework.searchalgorithm.TrieNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

/**
 * Created by chandrashekar.v on 9/12/2017.
 */
@Service
public class AutoSuggestInvoker {
    @Autowired
    @Qualifier(value = ApplicationConstants.FUZZY)
    private AbstractAutoSuggestProcessor fuzzyProcessor;

    @Autowired
    @Qualifier(value = ApplicationConstants.DEFAULT)
    private AbstractAutoSuggestProcessor defaultProcessor;

    @Autowired
    DataLoader dataLoader;

    public AutoSuggestResponse invoke(final AutoSuggestRequest request, AutoSuggestResponse response) {

        // load data on first request.
        Optional<TrieNode> data = loadData();
        if (!Objects.isNull(data) && data.isPresent()) {
            AbstractAutoSuggestProcessor processor = null;
            if (request.isFuzziness())
                processor = fuzzyProcessor;
            else
                processor = defaultProcessor;

            return processor.process(request, response, data.get());
        }
        response.setResponseCode(ResponseCode.Errors.AU_E_401);
        return response;
    }

    /**
     * Loads trie data.
     *
     * @return
     */
    private Optional<TrieNode> loadData() {
        Trie trie = dataLoader.loadData();
        if (Objects.isNull(trie))
            return Optional.empty();

        return Optional.ofNullable(trie.getRoot());

    }
}
