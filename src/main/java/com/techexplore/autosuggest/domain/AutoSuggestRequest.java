package com.techexplore.autosuggest.domain;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.function.IntPredicate;

/**
 * Created by chandrashekar.v on 9/12/2017.
 */
@Component
@Scope("prototype")
public class AutoSuggestRequest {

    private String start;

    private int atmost;

    private boolean fuzziness = false;

    public static final int DEFAULT_FUZZINESS = 3;

    private int fuzziThreshold = 0;

    private boolean caseSensitive = false;

    public String getStart() {
        if (StringUtils.isNoneBlank(start))
            this.start = start;

        else this.start = StringUtils.EMPTY;

        return start;
    }

    public void setStart(String start) {

        this.start = start;
    }

    public boolean isFuzziness() {
        return fuzziness;
    }

    public void setFuzziness(boolean fuzziness) {
        this.fuzziness = fuzziness;
    }

    public boolean isCaseSensitive() {
        return caseSensitive;
    }

    public void setCaseSensitive(boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }

    public int getAtmost() {
        if (atmost >= 0)
            this.atmost = atmost;
        else this.atmost = 0;

        return this.atmost;
    }

    public void setAtmost(int atmost) {
        this.atmost = atmost;
    }

    public int getFuzziThreshold() {
        if (isFuzziness())
            if (fuzziThreshold <= 0)
                this.fuzziThreshold = DEFAULT_FUZZINESS;
            else
                this.fuzziThreshold = fuzziThreshold;
        else
            this.fuzziThreshold = 0;

        return fuzziThreshold;
    }

    public void setFuzziThreshold(int fuzziThreshold) {
        this.fuzziThreshold = fuzziThreshold;

    }
}
