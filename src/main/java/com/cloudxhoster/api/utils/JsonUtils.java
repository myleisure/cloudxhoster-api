package com.cloudxhoster.api.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class JsonUtils {

    private static final Logger LOG = LoggerFactory.getLogger(JsonUtils.class);

    public static boolean isValidJSON(final String json) {
        try {
            final JsonParser parser = new ObjectMapper().getFactory().createParser(json);
            while (parser.nextToken() != null) {
            }
            return true;
        } catch (IOException e) {
            LOG.error("Invalid json : {}", json);
            return false;
        }
    }
}
