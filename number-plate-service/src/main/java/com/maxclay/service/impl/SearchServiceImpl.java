package com.maxclay.service.impl;

import com.maxclay.model.PlateSearchRequest;
import com.maxclay.model.PlateSearchResult;
import com.maxclay.service.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author Vlad Glinskiy
 */
@Service
public class SearchServiceImpl implements SearchService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public List<PlateSearchResult> search(List<PlateSearchRequest> searchRequest) {
        logger.info("Processing search request: {}", searchRequest);
        return Collections.emptyList();
    }
}
