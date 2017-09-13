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
 * Contains methods to load data.
 *
 * Created by chandrashekar.v on 9/13/2017.
 */
@Component
public class DataLoader {

    /**
     * Configurable file location from where the data needs to be read.
     */
    @Value("${file.location}")
    private String fileLocation;

    /**
     * Boolean value specifies whether data load completed.
     */
    private static boolean isDataDumpComplete = false;

    private static final Logger LOG = LoggerFactory.getLogger(DataLoader.class);

    /**
     * Data structure to hold loaded data.
     */
    private Trie trie;

    @PostConstruct
    public void init() {
        if (Objects.isNull(trie)) {
            LOG.info("Data is unavailable. Initiating data fetching from location:" + fileLocation);
            Optional<InputStream> fileData = FileUtils.loadFileFromClasspath(fileLocation);
            List<String> allCities = readDataFromFile(fileData);

            if (!Objects.isNull(allCities)) {
                // Populate data into Trie data structure.
                trie = new Trie(allCities);
                isDataDumpComplete = !Objects.isNull(trie.getRoot());

                LOG.info("Data dump complete.");
            }
        }

    }

    /**
     * Reads given file data and returns required column into list.
     *
     * @param fileData
     * @return
     */
    private List<String> readDataFromFile(Optional<InputStream> fileData) {
        List<String> allCities = null;

        // CSV reading logic
        if (fileData.isPresent()) {
            InputStream citiesDataStream = fileData.get();
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(citiesDataStream))) {
                allCities = bufferedReader.lines().parallel()
                        .skip(1) // Skip first row from CSV
                        .map(line -> Arrays.stream(line.split(","))
                        .skip(7) // consider taluk column as city data.
                        .findFirst())
                        .filter(s -> s.isPresent()).map(s -> s.get()).
                                collect(Collectors.toList());
            } catch (Exception ex) {
                LOG.error("Unable to load data.Parsing failed." + ex.getMessage());
            }
        }
        return allCities;
    }

    /**
     * Retrieves Data.
     *
     * @return
     */
    public Trie loadData() {

        if (Objects.isNull(trie))
            init();

        return trie;
    }

}
