package com.maxclay.client;

import com.maxclay.model.PlateSearchRequest;
import com.maxclay.model.WantedTransport;

import java.util.List;

/**
 * @author Vlad Glinskiy
 */
public interface NumberPlateSearchService {

    List<WantedTransport> search(List<PlateSearchRequest> searchRequest);

}
