package com.maxclay.service.impl;

import com.maxclay.model.PlateSearchRequest;
import com.maxclay.model.WantedTransport;
import com.maxclay.service.SearchService;
import com.maxclay.service.WantedTransportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Vlad Glinskiy
 */
@Service
public class SearchServiceImpl implements SearchService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WantedTransportService wantedTransportService;

    @Override
    public List<WantedTransport> search(List<PlateSearchRequest> searchRequest) {
        logger.info("Processing search request: {}", searchRequest);

        List<WantedTransport> result = searchRequest.stream()
                .map(PlateSearchRequest::getNumberPlate)
                .map(number -> number.replaceAll("\\s", ""))
                .map(wantedTransportService::findByNumberPlate)
                .flatMap(List::stream).collect(Collectors.toList());

        logger.info("Search result: {}", result);
        return result;
    }
}
