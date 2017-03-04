package com.maxclay.config;

import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Configuration, which allows to adjust connection to the MongoDB server and creates
 * {@link org.springframework.data.mongodb.core.MongoTemplate} bean.
 *
 * @author Vlad Glinskiy
 */
@Configuration
public class MongoConfiguration {

    @Value("${mongo.host}")
    private String host;

    @Value("${mongo.port}")
    private Integer port;

    @Value("${mongo.database}")
    private String databaseName;

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), databaseName);
    }

    @Bean
    public MongoClient mongoClient() {
        return new MongoClient(host, port);
    }

}
