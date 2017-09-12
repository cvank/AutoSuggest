package com.techexplore.autosuggest.framework.Response;

import java.util.Date;
import java.util.List;

/**
 * Created by chandrashekar.v on 9/12/2017.
 */
public class AutoSuggestResponse {

    private Date time;

    private List<String> suggestions;

    private ResponseCode responseCode;

    private String responseMessage;

    public List<String> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<String> suggestions) {
        this.suggestions = suggestions;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
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
