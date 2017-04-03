package com.maxclay.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.maxclay.model.PlateSearchRequest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Converts instance of {@link JsonNode} class which contains search request data into {@link List} of
 * {@link PlateSearchRequest}. Note that specified object must be validated before conversation, otherwise
 * {@link IllegalArgumentException} will be thrown.
 *
 * @author Vlad Glinskiy
 */
@Component("jsonNodeToPlateSearchRequest")
public class JsonNodeToPlateSearchRequest implements Converter<JsonNode, List<PlateSearchRequest>> {

    @Override
    public List<PlateSearchRequest> convert(JsonNode requestData) {

        if (requestData == null) {
            throw new IllegalArgumentException("Request data can not be null");
        }

        if (!requestData.isArray()) {
            throw new IllegalArgumentException("Request data must be an array");
        }

        List<PlateSearchRequest> searchRequestList = new ArrayList<>();
        for (JsonNode plateData : requestData) {
            if (plateData.has("number_plate")) {
                searchRequestList.add(new PlateSearchRequest(plateData.get("number_plate").asText()));
            } else {
                throw new IllegalArgumentException("Request data is invalid");
            }
        }

        return searchRequestList;
    }
}
