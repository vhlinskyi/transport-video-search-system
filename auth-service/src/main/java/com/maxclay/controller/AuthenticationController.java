package com.maxclay.controller;

import com.maxclay.dto.UserDto;
import com.maxclay.model.User;
import com.maxclay.service.CustomUserDetailsService;
import com.maxclay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * Spring's {@link org.springframework.web.bind.annotation.RestController}, which handles users' registration requests.
 *
 * @author Vlad Glinskiy
 */
@RestController
public class AuthenticationController {

    private final ConversionService conversionService;
    private final UserService userService;
    private final CustomUserDetailsService customUserDetailsService;

    @Autowired
    public AuthenticationController(@Qualifier("conversionService") ConversionService conversionService,
                                    UserService userService,
                                    CustomUserDetailsService customUserDetailsService) {

        this.conversionService = conversionService;
        this.userService = userService;
        this.customUserDetailsService = customUserDetailsService;
    }

    @RequestMapping(value = "/sign-up", method = RequestMethod.POST)
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto, UriComponentsBuilder ucb) {

        User user = userService.register(userDto);
        customUserDetailsService.authenticate(user);

        URI locationUri = ucb.path("/users/").path(user.getId()).build().toUri();
        return ResponseEntity.created(locationUri)
                .body(conversionService.convert(user, UserDto.class));
    }

}
