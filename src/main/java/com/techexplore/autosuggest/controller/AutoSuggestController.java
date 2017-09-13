package com.techexplore.autosuggest.controller;

import com.techexplore.autosuggest.domain.AutoSuggestRequest;
import com.techexplore.autosuggest.framework.ApplicationConstants;
import com.techexplore.autosuggest.framework.Response.AutoSuggestResponse;
import com.techexplore.autosuggest.framework.exception.AutoSuggestException;
import com.techexplore.autosuggest.service.AutoSuggestInvoker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * AutoSuggestController : Accepts search request and responds with suggestions.
 * <p>
 * Created by chandrashekar.v on 9/12/2017.
 */
@RestController
public class AutoSuggestController {
    private static final Logger LOG = LoggerFactory.getLogger(AutoSuggestController.class);

    @Value("${default.fuzzi.threshold}")
    public Integer defaultFuziThreshold;

    @Value("${default.atmost}")
    private Integer defaultAtmost;

    @Autowired
    AutoSuggestInvoker invoker;

    /**
     * Returns response object with all the necessary details.
     *
     * @param start
     * @param atMost
     * @param fuzziness
     * @return
     */
    @RequestMapping(value = "/suggest_cities1", method = RequestMethod.GET, produces = {"application/json"})
    public @ResponseBody
    AutoSuggestResponse suggest(@RequestParam("start") String start,
                                @RequestParam(value = "atmost", required = false) Integer atMost,
                                @RequestParam(value = "fuzzy", required = false) Boolean fuzziness,
                                @RequestParam(value = "threshold", required = false) Integer fuzziThreshold,
                                @RequestParam(value = "casesensitive", required = false) Boolean caseSensitive) throws AutoSuggestException {

        try {
            AutoSuggestResponse response = triggerSearch(start, atMost, fuzziness, fuzziThreshold, caseSensitive);
            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new AutoSuggestException(ex.getMessage());
        }

    }

    /**
     * responds with Plain text response with suggested cities list.
     *
     * @param start
     * @param atMost
     * @param fuzziness
     * @param fuzziThreshold
     * @param caseSensitive
     * @return
     * @throws AutoSuggestException
     */
    @RequestMapping(value = "/suggest_cities", method = RequestMethod.GET, produces = {"text/plain"})
    public @ResponseBody
    String suggest2(@RequestParam("start") String start,
                    @RequestParam(value = "atmost", required = false) Integer atMost,
                    @RequestParam(value = "fuzzy", required = false) Boolean fuzziness,
                    @RequestParam(value = "threshold", required = false) Integer fuzziThreshold,
                    @RequestParam(value = "case", required = false) Boolean caseSensitive)
            throws AutoSuggestException {
        LOG.info("Initiating auto suggestion feature. prefix:" + start);
        try {
            AutoSuggestResponse response = triggerSearch(start, atMost, fuzziness, fuzziThreshold, caseSensitive);
            if (Objects.isNull(response) || Objects.isNull(response.getSuggestions())) {
                return ApplicationConstants.NO_RESULTS;
            }
            return response.getSuggestions().parallelStream().reduce((s, s2) -> s + "\n" + s2).orElseGet(() -> ApplicationConstants.NO_RESULTS);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new AutoSuggestException(ex.getMessage());
        }
    }

    /**
     * utility method that invoke search for the given prefix.
     *
     * @param start
     * @param atMost
     * @param fuzziness
     * @param fuzziThreshold
     * @param caseSensitive
     * @return
     */
    private AutoSuggestResponse triggerSearch(String start,
                                              Integer atMost,
                                              Boolean fuzziness,
                                              Integer fuzziThreshold,
                                              Boolean caseSensitive) {

        AutoSuggestRequest request = new AutoSuggestRequest
                .RequestBuilder(start, defaultFuziThreshold, defaultAtmost)
                .atmost(atMost).fuzziness(fuzziness)
                .fuzziThreshold(fuzziThreshold)
                .caseSensitive(caseSensitive)
                .build();

        AutoSuggestResponse response = new AutoSuggestResponse();
        invoker.invoke(request, response);
        return response;
    }
}
