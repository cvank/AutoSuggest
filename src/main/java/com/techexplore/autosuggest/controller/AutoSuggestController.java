package com.techexplore.autosuggest.controller;

import com.techexplore.autosuggest.domain.AutoSuggestRequest;
import com.techexplore.autosuggest.framework.Response.AutoSuggestResponse;
import com.techexplore.autosuggest.service.AutoSuggestInvoker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by chandrashekar.v on 9/12/2017.
 */
@RestController
public class AutoSuggestController {

    @Autowired
    AutoSuggestInvoker invoker;

    /**
     * Handles post request with request body.
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/suggest_cities1", method = RequestMethod.POST)
    public @ResponseBody
    AutoSuggestResponse suggest1(@RequestBody AutoSuggestRequest request) {
        AutoSuggestResponse response = new AutoSuggestResponse();
        return invoker.invoke(request, response);

    }

    /**
     * Returns response object with all the necessary details.
     *
     * @param start
     * @param atmost
     * @param fuzziness
     * @return
     */
    @RequestMapping(value = "/suggest_cities", method = RequestMethod.GET, produces = {"application/json"})
    public @ResponseBody
    AutoSuggestResponse suggest(@RequestParam("start") String start, @RequestParam("atmost") int atmost, @RequestParam("fuzzy") boolean fuzziness) {
        AutoSuggestRequest request = new AutoSuggestRequest();
        request.setAtmost(atmost);
        request.setFuzziness(fuzziness);
        request.setStart(start);
        AutoSuggestResponse response = new AutoSuggestResponse();
        return invoker.invoke(request, response);

    }

    /**
     * Returns just plain cities names.
     *
     * @param start
     * @param atmost
     * @param fuzziness
     * @return
     */
    @RequestMapping(value = "/suggest_cities2", method = RequestMethod.GET, produces = {"text/plain"})
    public @ResponseBody
    String suggest2(@RequestParam("start") String start, @RequestParam("atmost") int atmost, @RequestParam("fuzzy") boolean fuzziness) {
        AutoSuggestRequest request = new AutoSuggestRequest();
        request.setAtmost(atmost);
        request.setFuzziness(fuzziness);
        request.setStart(start);
        AutoSuggestResponse response = new AutoSuggestResponse();
        invoker.invoke(request, response);
        return response.getSuggestions().parallelStream().reduce((s, s2) -> s + "\n" + s2).orElseGet(() -> "No results");
    }
}
