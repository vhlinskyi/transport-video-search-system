package com.maxclay.service;

import com.maxclay.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * Custom user details service which is used for user authentication.
 *
 * @author Vlad Glinskiy
 */
@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        com.maxclay.model.User user;
        try {
            user = userService.get(username);
        } catch (ResourceNotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }

        List<GrantedAuthority> authorities = (List<GrantedAuthority>) getAuthorities(user);
        return createUser(user, authorities);
    }

    @SuppressWarnings("unchecked")
    public UserDetails userToUserDetails(com.maxclay.model.User user) {

        List<GrantedAuthority> authorities = (List<GrantedAuthority>) getAuthorities(user);
        return createUser(user, authorities);
    }

    public void authenticate(com.maxclay.model.User user) {

        if (user != null) {
            UserDetails userDetails = userToUserDetails(user);
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails,
                    userDetails.getPassword(), userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }

    private User createUser(com.maxclay.model.User user, List<GrantedAuthority> authorities) {
        return new User(user.getUsername(), user.getPassword(), authorities);
    }

    private Collection<? extends GrantedAuthority> getAuthorities(com.maxclay.model.User user) {
        // TODO change line below if roles are required
        String[] roles = {"ROLE_USER"};
        return AuthorityUtils.createAuthorityList(roles);
    }
}