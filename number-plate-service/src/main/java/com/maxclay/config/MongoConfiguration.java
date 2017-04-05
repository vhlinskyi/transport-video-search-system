package com.maxclay.config;

import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.net.UnknownHostException;

/**
 * @author Vlad Glinskiy
 */
@Configuration
public class MongoConfiguration {

    @Autowired
    private Environment environment;

    // TODO username, password

    @Bean
    public MongoTemplate mongoTemplate() throws UnknownHostException {
        return new MongoTemplate(mongoClient(), environment.getProperty("mongodb.database"));
    }

    @Bean
    public MongoClient mongoClient() throws UnknownHostException {
        return new MongoClient(environment.getProperty("mongodb.host"),
                Integer.parseInt(environment.getProperty("mongodb.port")));
    }

}