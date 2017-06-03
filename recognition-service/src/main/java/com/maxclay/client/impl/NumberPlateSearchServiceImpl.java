package com.maxclay.client.impl;

import com.maxclay.client.NumberPlateSearchService;
import com.maxclay.model.PlateSearchRequest;
import com.maxclay.model.WantedTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * TODO change if use with Number Plate Service
 *
 * @author Vlad Glinskiy
 */
@Service
public class NumberPlateSearchServiceImpl implements NumberPlateSearchService {

    public static final List<String> TEST_NUMBER_PLATES = Arrays.asList("AE1558AX", "AAO520KP");

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * TODO SHOULD BE CHANGED. USED JUST FOR TESTING
     *
     * @param searchRequest
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<WantedTransport> search(List<PlateSearchRequest> searchRequest) {

        PlateSearchRequest singleSearchRequest = searchRequest.get(0);
        // added just for testing
        boolean isFound = TEST_NUMBER_PLATES.contains(singleSearchRequest.getNumberPlate());

        if (!isFound) {
            return Collections.emptyList();
        }

        WantedTransport dummy = new WantedTransport();
        dummy.setNumberPlate(singleSearchRequest.getNumberPlate());
        dummy.setBodyNumber("dummy");
        dummy.setChassisNumber("dummy");
        dummy.setColor("dummy");
        dummy.setModel("dummy");
        dummy.setDepartment("dummy");
        logger.info("FOUND SUSPICIOUS TRANSPORT: {}", dummy);

        return Arrays.asList(dummy);
    }
}
