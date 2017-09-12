package com.techexplore.autosuggest.service;

import com.techexplore.autosuggest.domain.AutoSuggestRequest;
import com.techexplore.autosuggest.framework.ApplicationConstants;
import com.techexplore.autosuggest.framework.Response.AutoSuggestResponse;
import com.techexplore.autosuggest.framework.Response.ResponseCode;
import com.techexplore.autosuggest.framework.processor.AbstractAutoSuggestProcessor;
import com.techexplore.autosuggest.framework.searchalgorithm.Trie;
import com.techexplore.autosuggest.framework.searchalgorithm.TrieNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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

    private static boolean isDataDumpComplete = false;

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    ResourceLoader resourceLoader;

    public AutoSuggestResponse invoke(final AutoSuggestRequest request, AutoSuggestResponse response) {

        // load data on first request.
        Optional<TrieNode> data = loadData();
        if (isDataDumpComplete && data.isPresent()) {
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

    private Optional<TrieNode> loadData() {
        Resource resource = resourceLoader.getResource("classpath:data/all_india_PO_list_without_APS_offices_ver2.csv");
        boolean isReadble = resource.isReadable();
        List<String> allCities = null;
        if (isReadble) {
            try {
                String path = resource.getFile().getPath();

                allCities = Files.lines(Paths.get(path)).parallel().skip(1).map(line -> Arrays.stream(line.split(","))
                        .skip(7)
                        .findFirst())
                        .filter(s -> s.isPresent()).map(s -> s.get()).
                                collect(Collectors.toList());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!Objects.isNull(allCities)) {
            Trie trie = new Trie(allCities);
            isDataDumpComplete = !Objects.isNull(trie.getRoot());
            return Optional.of(trie.getRoot());
        }

        return Optional.empty();
    }
}
