package com.techexplore.autosuggest.framework.Response;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by chandrashekar.v on 9/12/2017.
 */
public class AutoSuggestResponse {

    private String time;

    private List<String> suggestions;

    private ResponseCode responseCode;

    private String responseMessage;


    public AutoSuggestResponse() {
        this.time = String.valueOf(new Date());
    }

    public AutoSuggestResponse(Map<String, Object> errorAttributes) {
        this.time = errorAttributes.getOrDefault("timestamp", String.valueOf(new Date())).toString();
        this.responseMessage = (String) errorAttributes.get("message");
    }

    public AutoSuggestResponse(final String message) {
        this.time = String.valueOf(new Date());
        this.responseMessage = message;
    }

    public List<String> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<String> suggestions) {
        this.suggestions = suggestions;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ResponseCode getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(ResponseCode responseCode) {
        this.responseCode = responseCode;
        this.setResponseMessage(responseCode.message());
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
}
