package com.maxclay.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

/**
 * @author Vlad Glinskiy
 */
@ConfigurationProperties(prefix = "upload.task")
public class TaskFilesUploadProperties {

    private Resource uploadPath;

    public Resource getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = new DefaultResourceLoader().getResource(uploadPath);
    }
}
