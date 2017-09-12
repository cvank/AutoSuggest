package com.techexplore.autosuggest.validate;

import com.techexplore.autosuggest.domain.AutoSuggestRequest;
import com.techexplore.autosuggest.framework.Response.AutoSuggestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * Created by chandrashekar.v on 9/12/2017.
 */
@Component
public class RequestValidator {

    @Autowired
    private AutoSuggestRequest request;

    AutoSuggestResponse response;

    public RequestValidator(AutoSuggestRequest request) {
        this.request = request;
    }

    public RequestValidator() {
    }

    public AutoSuggestRequest getRequest() {
        return request;
    }

    public void setRequest(AutoSuggestRequest request) {
        this.request = request;
    }

    public AutoSuggestResponse validate() {
        Field[] fields = this.getRequest().getClass().getFields();
        return response;
    }
}
