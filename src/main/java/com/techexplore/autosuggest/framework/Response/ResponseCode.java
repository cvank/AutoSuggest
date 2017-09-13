package com.techexplore.autosuggest.framework.Response;

/**
 * Response codes for Error and success cases.
 *
 * Created by chandrashekar.v on 9/12/2017.
 */
public interface ResponseCode {
    public String message();

    public enum Errors implements ResponseCode {
        AU_E_400("INVALID_REQUEST"),
        AU_E_401("DATA UNAVAILABLE"),
        AU_E_402("UNEXPECTED_ERROR");
        private String description;

        Errors(String description) {
            this.description = description;
        }

        @Override
        public String message() {
            return this.description;
        }
    }

    public enum Success implements ResponseCode {
        AU_S_200("REQUEST PROCESSED SUCCESSFULLY");
        private String description;

        Success(String description) {
            this.description = description;
        }

        @Override
        public String message() {
            return this.description;
        }
    }
}
