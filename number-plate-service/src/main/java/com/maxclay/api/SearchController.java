package com.maxclay.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.maxclay.exception.ValidationException;
import com.maxclay.model.PlateSearchRequest;
import com.maxclay.model.WantedTransport;
import com.maxclay.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Vlad Glinskiy
 */
@RestController
public class SearchController {

    @Autowired
    @Qualifier("searchRequestValidator")
    private Validator searchRequestValidator;

    @Autowired
    @Qualifier("conversionService")
    private ConversionService conversionService;

    @Autowired
    private SearchService searchService;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> search(@RequestBody JsonNode searchRequestData, BindingResult bindingResult) {

        searchRequestValidator.validate(searchRequestData, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new ValidationException("Search request is invalid.", bindingResult);
        }

        List<PlateSearchRequest> searchRequest = conversionService.convert(searchRequestData, List.class);
        List<WantedTransport> result = searchService.search(searchRequest);
        return ResponseEntity.ok(result);
    }

}
