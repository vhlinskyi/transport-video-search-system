package com.maxclay.service;

import com.maxclay.model.PlateSearchRequest;
import com.maxclay.model.WantedTransport;

import java.util.List;

/**
 * @author Vlad Glinskiy
 */
public interface SearchService {

    List<WantedTransport> search(List<PlateSearchRequest> searchRequest);
}
