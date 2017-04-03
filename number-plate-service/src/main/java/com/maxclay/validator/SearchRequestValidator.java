package com.maxclay.validator;


import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author Vlad Glinskiy
 */
@Component("searchRequestValidator")
public class SearchRequestValidator implements Validator {

    private static final String NUMBER_PLATE_REQUIRED_FIELD = "number_plate";

    @Override
    public boolean supports(Class<?> aClass) {
        return JsonNode.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {

        if (target == null) {
            errors.reject("Search request can not be null");
            return;
        }

        if (!(target instanceof JsonNode)) {
            errors.reject("Search request must be instance of JsonNode");
            return;
        }

        JsonNode searchRequest = (JsonNode) target;
        if (!searchRequest.isArray()) {
            errors.reject("Search request must be JSON array");
            return;
        }

        for (JsonNode plateNumber : searchRequest) {

            if (!plateNumber.has(NUMBER_PLATE_REQUIRED_FIELD)) {
                errors.reject(String.format("Each JSON document of search request must contain '%s' field",
                        NUMBER_PLATE_REQUIRED_FIELD));
                return;
            }

            if (!plateNumber.findValue(NUMBER_PLATE_REQUIRED_FIELD).isTextual()) {
                errors.reject(String.format("'%s' field must contain textual value",
                        NUMBER_PLATE_REQUIRED_FIELD));
                return;
            }

        }

    }
}
