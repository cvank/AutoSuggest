package com.techexplore.autosuggest.framework.processor.fuzzy;

import com.techexplore.autosuggest.framework.ApplicationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import static com.techexplore.autosuggest.framework.ApplicationConstants.*;

/**
 * Created by chandrashekar.v on 9/14/2017.
 */
@Component
public class AlgorithmIdentifier {

    @Autowired
    @Qualifier(value = FUZZY_SCORE)
    FuzzyStrategy fuzzyScoreStrategy;

    @Autowired
    @Qualifier(value = JARO)
    FuzzyStrategy jaroWrinklerStrategy;

    @Autowired
    @Qualifier(value = LEVENSHTEIN)
    FuzzyStrategy levenshteinStrategy;

    /**
     * Identifies and returns fuzzy search algorithm based on given string literal.
     *
     * @param algorithm
     * @return
     */
    public FuzzyStrategy identify(final String algorithm) {
        switch (algorithm) {
            case JARO:
                return jaroWrinklerStrategy;
            case LEVENSHTEIN:
                return levenshteinStrategy;
            case FUZZY_SCORE:
                return fuzzyScoreStrategy;
            default:
                return jaroWrinklerStrategy;

        }
    }
}
