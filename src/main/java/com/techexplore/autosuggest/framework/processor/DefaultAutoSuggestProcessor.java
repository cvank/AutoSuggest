package com.techexplore.autosuggest.framework.processor;

import com.techexplore.autosuggest.domain.AutoSuggestRequest;
import com.techexplore.autosuggest.framework.ApplicationConstants;
import com.techexplore.autosuggest.framework.searchalgorithm.TrieNode;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by chandrashekar.v on 9/12/2017.
 */
@Service(value = ApplicationConstants.DEFAULT)
public class DefaultAutoSuggestProcessor extends AbstractAutoSuggestProcessor {

    private AtomicInteger counter = new AtomicInteger();

    public AtomicInteger getCounter() {
        if (Objects.isNull(counter)) {
            counter = new AtomicInteger(0);
        }
        return counter;
    }

    public void setCounter(AtomicInteger counter) {
        this.counter = counter;
    }

    @Override
    public List<String> search(AutoSuggestRequest request, final TrieNode node) {
        getCounter();
        List<String> suggestions = new ArrayList<>();
        fetch(request.getStart(), request.isCaseSensitive(), node, request.getAtmost(), suggestions);
        setCounter(null);
        return suggestions;
    }

    public void fetch(String prefix, final boolean caseSensitive, TrieNode root, final int atMost, List<String> suggestions) {
        prefix = prefix.toLowerCase();
        TrieNode lastNode = root;

        StringBuilder prefixActual = new StringBuilder();
        for (int i = 0; i < prefix.length(); i++) {
            lastNode = lastNode.getChildNode(prefix.charAt(i));
            if (Objects.isNull(lastNode)) {
                return;
            }
            prefixActual.append(lastNode.getDisplayCharacter());
        }
        if (caseSensitive)
            caseSensitiveSuggestions(prefixActual.toString(), lastNode, atMost, suggestions);
        else
            caseInsensitiveSuggestions(lastNode, atMost, suggestions);
    }

    private void caseSensitiveSuggestions(final String prefix, TrieNode parent, final int atMost, List<String> suggestions) {
        if (getCounter().intValue() >= atMost)
            return;

        if (parent.isTerminated()) {
            suggestions.add(prefix);
            getCounter().getAndIncrement();
        }

        if (parent.getChildren().size() == 0)
            return;

        for (Map.Entry<Character, TrieNode> entry : parent.getChildren().entrySet()) {
            caseSensitiveSuggestions(prefix + entry.getValue().getDisplayCharacter(), entry.getValue(), atMost, suggestions);

        }
    }

    private void caseInsensitiveSuggestions(TrieNode parent, final int atMost, List<String> suggestions) {

        if (getCounter().intValue() >= atMost)
            return;

        if (parent.isTerminated()) {
            suggestions.add(parent.getWord());
            getCounter().getAndIncrement();
        }

        if (parent.getChildren().size() == 0)
            return;

        for (Map.Entry<Character, TrieNode> entry : parent.getChildren().entrySet()) {
            caseInsensitiveSuggestions(entry.getValue(), atMost, suggestions);

        }
    }

}
