package com.maxclay.client;

import com.maxclay.model.PlateSearchRequest;
import com.maxclay.model.WantedTransport;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @author Vlad Glinskiy
 */
@FeignClient(name = "number-plate-service")
public interface NumberPlateSearchService {

    @RequestMapping(method = POST, value = "/number-plate-service/", consumes = APPLICATION_JSON_UTF8_VALUE)
    List<WantedTransport> search(List<PlateSearchRequest> searchRequest);

}
