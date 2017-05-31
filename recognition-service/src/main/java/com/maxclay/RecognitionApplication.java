package com.maxclay;

import com.maxclay.config.TaskFilesUploadProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
//@EnableDiscoveryClient
//@EnableConfigurationProperties
@EnableConfigurationProperties({TaskFilesUploadProperties.class})
public class RecognitionApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecognitionApplication.class, args);
    }

}
