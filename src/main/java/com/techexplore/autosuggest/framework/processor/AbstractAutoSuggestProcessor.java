package com.techexplore.autosuggest.framework.processor;

import com.techexplore.autosuggest.domain.AutoSuggestRequest;
import com.techexplore.autosuggest.framework.ApplicationConstants;
import com.techexplore.autosuggest.framework.Response.AutoSuggestResponse;
import com.techexplore.autosuggest.framework.Response.ResponseCode;
import com.techexplore.autosuggest.framework.searchalgorithm.TrieNode;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by chandrashekar.v on 9/12/2017.
 */
public abstract class AbstractAutoSuggestProcessor {

    public abstract List<String> search(final AutoSuggestRequest request, final TrieNode trieNode);

    private void validate(final AutoSuggestRequest request, AutoSuggestResponse response) {
        if (StringUtils.isBlank(request.getStart()) || request.getAtmost() <= 0) {
            response.setResponseCode(ResponseCode.Errors.AU_E_400);
        }
    }

    public AutoSuggestResponse process(final AutoSuggestRequest request, AutoSuggestResponse response, final TrieNode node) {
        validate(request, response);
        Optional<ResponseCode.Errors> error = Arrays.stream(ResponseCode.Errors.values()).parallel().filter(s -> s.equals(response.getResponseCode())).findAny();

        if (!error.isPresent()) {

            // Validation is success. Proceed with auto suggestion processing.
            List<String> suggestions = search(request, node);
            postProcess(suggestions, response);
        }
        return response;
    }

    private void postProcess(List<String> suggestions, AutoSuggestResponse response) {
        response.setSuggestions(suggestions);
        response.setResponseCode(ResponseCode.Success.AU_S_200);
        if (Objects.isNull(response) || Objects.isNull(response.getSuggestions())) {
            response.setResponseMessage(ApplicationConstants.NO_RESULTS);
        }
    }
}
