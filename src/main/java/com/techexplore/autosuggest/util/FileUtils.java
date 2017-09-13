package com.techexplore.autosuggest.util;

import com.techexplore.autosuggest.framework.data.DataLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by chandrashekar.v on 9/13/2017.
 */
public final class FileUtils {

    private static final Logger LOG = LogManager.getLogger(FileUtils.class);

    /**
     * Loads resources from the given class path location.
     *
     * @param location
     * @return
     */
    public static Optional<InputStream> loadFileFromClasspath(final String location) {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        try {
            Resource resource = resourceLoader.getResource("classpath:" + location);
            return Optional.of(resource.getInputStream());
        } catch (IOException ex) {
            LOG.error("Unable to load data from location:" + location + "Error Message: " + ex.getMessage());
        } catch (Exception ex) {
            LOG.error("Unexpected error while loading data from location:" + location + "Error Message: " + ex.getMessage());
        }

        return Optional.empty();

    }

}
