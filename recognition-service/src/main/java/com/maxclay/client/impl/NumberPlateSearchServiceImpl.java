package com.maxclay.client.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maxclay.client.NumberPlateSearchService;
import com.maxclay.model.PlateSearchRequest;
import com.maxclay.model.WantedTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vlad Glinskiy
 */
@Service
public class NumberPlateSearchServiceImpl implements NumberPlateSearchService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

//    @Autowired
//    private Environment environment;

    @Override
    @SuppressWarnings("unchecked")
    public List<WantedTransport> search(List<PlateSearchRequest> searchRequest) {
//        String numberPlateServiceUrl = environment.getProperty("number-plate-service.url");
        String numberPlateServiceUrl = "http://dummy-url.com";
        logger.info("Sending request '{}' to the number plate service at '{}'", searchRequest, numberPlateServiceUrl);
//        RestTemplate restTemplate = new RestTemplate();
//
//        JsonNode response = restTemplate.postForObject(numberPlateServiceUrl, searchRequest, JsonNode.class);
//        logger.info("Response received from the number plate service: '{}'", response);

        List<WantedTransport> result = new ArrayList<>();
//        ObjectMapper mapper = new ObjectMapper();
//        for (JsonNode transportData : response) {
//            try {
//                result.add(mapper.treeToValue(transportData, WantedTransport.class));
//            } catch (JsonProcessingException e) {
//                e.printStackTrace();
//            }
//        }

        return result;
    }
}
