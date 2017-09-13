package com.techexplore.autosuggest.domain;

import org.apache.commons.lang3.BooleanUtils;

import java.util.Objects;

/**
 * Request pojo that will be used by spring to bind request params.
 * <p>
 * Created by chandrashekar.v on 9/12/2017.
 */
public class AutoSuggestRequest {

    private String start;

    private int atmost;

    private boolean fuzziness = false;

    private int fuzziThreshold;

    private boolean caseSensitive = false;

    public AutoSuggestRequest(RequestBuilder requestBuilder) {
        this.start = requestBuilder.start;
        this.atmost = requestBuilder.atmost;
        this.fuzziThreshold = requestBuilder.fuzziThreshold;
        this.fuzziness = requestBuilder.fuzziness;
        this.caseSensitive = requestBuilder.caseSensitive;
    }

    public String getStart() {
        return start;
    }


    public boolean isFuzziness() {
        return fuzziness;
    }


    public boolean isCaseSensitive() {
        return caseSensitive;
    }


    public int getAtmost() {
        return this.atmost;
    }


    public int getFuzziThreshold() {
        return fuzziThreshold;
    }


    public static class RequestBuilder {

        private String start;

        private int atmost;

        private boolean fuzziness = false;

        public int defaultFuzziThreshold;

        private int fuzziThreshold;

        private boolean caseSensitive = false;

        private int defaultAtmost;

        public RequestBuilder(String start, int defaultFuzziThreshold, int defaultAtmost) {
            this.start = start;
            this.defaultFuzziThreshold = defaultFuzziThreshold;
            this.defaultAtmost = defaultAtmost;
        }

        public RequestBuilder setStart(String start) {
            this.start = start;
            return this;
        }

        public RequestBuilder atmost(Integer atMost) {
            if (!Objects.isNull(atMost) && atMost >= 0)
                this.atmost = atMost;
            else
                atMost = defaultAtmost;

            return this;
        }

        public RequestBuilder fuzziness(Boolean fuzziness) {
            this.fuzziness = BooleanUtils.toBooleanDefaultIfNull(fuzziness, false);
            return this;
        }

        public RequestBuilder fuzziThreshold(Integer fuzziThreshold) {
            if (!Objects.isNull(fuzziThreshold) && fuzziThreshold >= 0)
                this.fuzziThreshold = fuzziThreshold;
            else
                this.fuzziThreshold = defaultFuzziThreshold;

            return this;
        }

        public RequestBuilder caseSensitive(Boolean caseSensitive) {
            this.caseSensitive = BooleanUtils.toBooleanDefaultIfNull(caseSensitive, false);
            return this;
        }

        public AutoSuggestRequest build() {
            return new AutoSuggestRequest(this);
        }
    }
}
