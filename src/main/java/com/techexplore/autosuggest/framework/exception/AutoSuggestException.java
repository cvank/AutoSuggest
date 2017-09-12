package com.techexplore.autosuggest.framework.exception;

/**
 * Created by chandrashekar.v on 9/12/2017.
 */
public class AutoSuggestException extends Exception {
    public AutoSuggestException() {
        super();
    }

    public AutoSuggestException(String message) {
        super(message);
    }

    public AutoSuggestException(String message, Throwable cause) {
        super(message, cause);
    }

    public AutoSuggestException(Throwable cause) {
        super(cause);
    }

    protected AutoSuggestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
