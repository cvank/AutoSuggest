package com.techexplore.autosuggest.framework.processor.fuzzy;

import com.techexplore.autosuggest.domain.AutoSuggestRequest;
import com.techexplore.autosuggest.framework.ApplicationConstants;
import com.techexplore.autosuggest.framework.searchalgorithm.TrieNode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Levenshtein Distance algorithm measures the difference between two character sequences.
 * <p>
 * Adopted Levenshtein Distance algorithm to Trie data structure in such a way that distance calculation
 * happens only on newly added character from trie data rather than entire sequence of word every time.
 * <p>
 * Created by chandrashekar.v on 9/14/2017.
 */
@Component(value = ApplicationConstants.LEVENSHTEIN)
public class LevenshteinDistanceStrategy implements FuzzyStrategy {

    private static final Logger LOG = LoggerFactory.getLogger(LevenshteinDistanceStrategy.class);

    private AtomicInteger counter = new AtomicInteger();

    @Override
    public Optional<List<String>> search(AutoSuggestRequest request, TrieNode root) {
        getCounter();
        Optional<List<String>> suggestions = processFuzzySearch(request.getFuzziThreshold(), request.getStart(), root, request.getAtmost());
        setCounter(null);

        return suggestions;
    }

    /**
     * Initiates fuzzy search for the given start word.Ideally it traverese through all nodes available in Trie dats structure and populates relevant words that has fuzzy value less than or euqual to given fuzzy threshold.
     *
     * @param fuzziness
     * @param word
     * @param root
     * @param atMost
     */
    private Optional<List<String>> processFuzzySearch(int fuzziness, String word, final TrieNode root, final int atMost) {
        List<String> suggestions = new ArrayList<>();

        // Prepopulate table with default values for the given search term.
        int[] currentRow = new int[word.length() + 1];
        for (int i = 0; i < currentRow.length; i++) {
            currentRow[i] = i;
        }

        TrieNode currentNode = root;

        // Traverse through all trie nodes.
        for (Map.Entry<Character, TrieNode> entry : currentNode.getChildren().entrySet())
            searchRecursive(entry.getValue(), entry.getKey(), word, currentRow, fuzziness,
                    word.toCharArray(), atMost, suggestions);

        return Optional.ofNullable(suggestions);

    }

    /**
     * recursive method called for each trie node.
     * It implements Levenshtein distance algorithm to retrieve words nearer to the given word.
     *
     * @param node
     * @param key
     * @param word
     * @param previousRow
     * @param fuzziness
     * @param wordChars
     * @param atMost
     * @param suggestions
     */
    private void searchRecursive(TrieNode node, Character key, String word, int[] previousRow, int fuzziness, char[] wordChars,
                                 final int atMost, List<String> suggestions) {
        if (getCounter().get() >= atMost)
            return;

        int columns[] = new int[word.length() + 1];
        int[] currentRow = new int[word.length() + 1];
        currentRow[0] = previousRow[0] + 1;

        int index = 1;
        int insertCost = 0;
        int delCost = 0;
        int replaceCost = 0;
        for (int i = 1; i < columns.length; i++) {
            insertCost = currentRow[i - 1] + 1;
            delCost = previousRow[i] + 1;

            if (wordChars[i - 1] != key.charValue()) {
                replaceCost = previousRow[i - 1] + 1;
            } else {
                replaceCost = previousRow[i - 1];
            }
            currentRow[index++] = Math.min(Math.min(insertCost, delCost), replaceCost);
        }

        if (currentRow[currentRow.length - 1] <= fuzziness && StringUtils.isNoneBlank(node.getWord()) &&
                StringUtils.trimToEmpty(node.getWord()).length() >= word.length()) {
            suggestions.add(node.getWord());
            getCounter().getAndIncrement();
        }

        // Find minimum fuzzy value for the given character. If there is any exists then proceed with it;s child nodes for finding nearer words.
        OptionalInt min = Arrays.stream(currentRow).min();
        if (min.getAsInt() <= fuzziness) {
            {
                for (Map.Entry<Character, TrieNode> entry : node.getChildren().entrySet()) {
                    searchRecursive(entry.getValue(), entry.getKey(), word, currentRow, fuzziness, word.toCharArray(), atMost, suggestions);
                }
            }
        }

    }

    public AtomicInteger getCounter() {
        if (Objects.isNull(counter)) {
            counter = new AtomicInteger(0);
        }
        return counter;
    }

    public void setCounter(AtomicInteger counter) {
        this.counter = counter;
    }
}
