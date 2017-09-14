package com.techexplore.autosuggest.framework.processor;

import com.techexplore.autosuggest.framework.processor.fuzzy.FuzzyScoreStrategy;
import com.techexplore.autosuggest.framework.processor.fuzzy.JaroWinklerDistanceStrategy;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.similarity.FuzzyScore;
import org.apache.commons.text.similarity.JaroWinklerDistance;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by chandrashekar.v on 9/13/2017.
 */
public final class SearchAlgorithmUtil extends FuzzyScoreStrategy {

    private static final JaroWinklerDistance jaroWinklerDistance = new JaroWinklerDistance();
    private static final FuzzyScore fuzzyScore = new FuzzyScore(Locale.getDefault());

    private static int fuzzyScore(final String term, final String query) {
        return fuzzyScore.fuzzyScore(term, query);
    }

    private static double jaroWinklerDistance(final String term, final String query) {
        return jaroWinklerDistance.apply(term, query);
    }

    /**
     * Applies Jaro Wrinkler algorithm on the given start word and all words available in the data..
     *
     * @param word          - start word
     * @param allQueries    - all available words in the data.
     * @param atMostResults - maximum number of suggestions that need to be returned.
     * @return
     */
    public static Optional<List<String>> applyJaro(String word, List<String> allQueries, int atMostResults) {
        Map<Double, List<String>> wordsByScore = new TreeMap<>((o1, o2) -> o2.compareTo(o1));
        allQueries.stream()
                .filter(StringUtils::isNoneBlank).forEach(query -> {
            double score = jaroWinklerDistance(word, query);
            wordsByScore.computeIfAbsent(score, words -> new ArrayList<>()).add(query);
        });

        return collectAndTrimToAtmostNumber(atMostResults, wordsByScore);
    }

    /**
     * Collects all results based on their ranking and returns the topmost results by considering at most.
     *
     * @param atMostResults
     * @param wordsByScore
     * @return
     */
    private static Optional<List<String>> collectAndTrimToAtmostNumber(int atMostResults, Map<? extends Number, List<String>> wordsByScore) {
        List<String> suggestions = wordsByScore.values().stream().flatMap(List::stream).collect(Collectors.toList());

        if (Objects.isNull(suggestions) || suggestions.size() == 0)
            return Optional.empty();

        int index = atMostResults > suggestions.size() ? suggestions.size() : atMostResults;
        return Optional.of(suggestions.subList(0, index));
    }

    /**
     * Applies Fuzzy score algorithm from apache commons text on the given start word and all words available in the data.
     *
     * @param word          - start word
     * @param allQueries    - all available words in the data.
     * @param atMostResults - maximum number of suggestions that need to be returned.
     * @return
     */
    public static Optional<List<String>> applyFuzzy(String word, List<String> allQueries, int atMostResults) {
        Map<Integer, List<String>> wordsByScore = new TreeMap<>((o1, o2) -> o2.compareTo(o1));
        allQueries.stream()
                .filter(StringUtils::isNoneBlank).forEach(query -> {
            int score = fuzzyScore(word, query);
            wordsByScore.computeIfAbsent(score, words -> new ArrayList<>()).add(query);
        });

        return collectAndTrimToAtmostNumber(atMostResults, wordsByScore);
    }

}
