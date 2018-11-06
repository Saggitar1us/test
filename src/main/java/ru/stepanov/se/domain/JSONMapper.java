package ru.stepanov.se.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Aleksei Stepanov
 */

public class JSONMapper {

    public static String getToJSON(final PlaceHolder placeHolder) throws JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(placeHolder);
        return json;
    }

}
