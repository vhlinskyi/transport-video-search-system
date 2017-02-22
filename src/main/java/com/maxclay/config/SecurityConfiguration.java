package com.maxclay.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Vlad Glinskiy
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    //TODO create own filter to send appropriate JSON responses instead of redirecting
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/sign-up").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/").permitAll()
                .loginProcessingUrl("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .and()
                .csrf().disable();//TODO enable it to prevent xss?
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
