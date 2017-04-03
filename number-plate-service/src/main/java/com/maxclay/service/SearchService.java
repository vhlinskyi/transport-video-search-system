package com.maxclay.service;

import com.maxclay.model.PlateSearchRequest;
import com.maxclay.model.PlateSearchResult;

import java.util.List;

/**
 * @author Vlad Glinskiy
 */
public interface SearchService {

    List<PlateSearchResult> search(List<PlateSearchRequest> searchRequest);
}
