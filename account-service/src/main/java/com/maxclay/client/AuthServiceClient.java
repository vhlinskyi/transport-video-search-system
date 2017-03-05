package com.maxclay.client;

import com.maxclay.dto.UserDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @author Vlad Glinskiy
 */
@FeignClient(name = "auth-service")
public interface AuthServiceClient {

    //TODO exception handling
    @RequestMapping(method = POST, value = "/auth-service/users", consumes = APPLICATION_JSON_UTF8_VALUE)
    void registerUser(UserDto userDto);
}
