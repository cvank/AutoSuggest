package com.techexplore.autosuggest.framework.data;

import com.techexplore.autosuggest.framework.searchalgorithm.Trie;
import com.techexplore.autosuggest.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * Created by chandrashekar.v on 9/13/2017.
 */
@Component
public class DataLoader {

    @Value("${file.location}")
    private String fileLocation;

    private static boolean isDataDumpComplete = false;

    private static final Logger LOG = LoggerFactory.getLogger(DataLoader.class);

    private Trie trie;

    @PostConstruct
    public void init() {
        if (Objects.isNull(trie)) {
            LOG.info("Data is unavailable. Initiating data fetching from location:" + fileLocation);
            Optional<InputStream> fileData = FileUtils.loadFileFromClasspath(fileLocation);
            List<String> allCities = null;

            if (fileData.isPresent()) {
                InputStream citiesDataStream = fileData.get(); // <-- this is the difference
                try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(citiesDataStream))) {
                    allCities = bufferedReader.lines().parallel().skip(1).map(line -> Arrays.stream(line.split(","))
                            .skip(7)
                            .findFirst())
                            .filter(s -> s.isPresent()).map(s -> s.get()).
                                    collect(Collectors.toList());
                } catch (Exception ex) {
                    LOG.error("Unable to load data.Parsing failed." + ex.getMessage());
                }
            }

            if (!Objects.isNull(allCities)) {
                trie = new Trie(allCities);
                isDataDumpComplete = !Objects.isNull(trie.getRoot());

                LOG.info("Data dump complete.");
            }
        }

    }

    /**
     * Retrievs Data.
     *
     * @return
     */
    public Trie loadData() {

        if (Objects.isNull(trie))
            init();

        return trie;
    }

}
