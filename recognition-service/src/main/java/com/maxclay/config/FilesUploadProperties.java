package com.maxclay.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

/**
 * @author Vlad Glinskiy
 */
@ConfigurationProperties(prefix = "files.upload")
public class FilesUploadProperties {

    private Resource tasksFilesPath;

    private Resource suspiciousPicturesPath;

    public Resource getTasksFilesPath() {
        return tasksFilesPath;
    }

    public void setTasksFilesPath(String tasksFilesPath) {
        this.tasksFilesPath = new DefaultResourceLoader().getResource(tasksFilesPath);
    }

    public Resource getSuspiciousPicturesPath() {
        return suspiciousPicturesPath;
    }

    public void setSuspiciousPicturesPath(String suspiciousPicturesPath) {
        this.suspiciousPicturesPath = new DefaultResourceLoader().getResource(suspiciousPicturesPath);
    }
}
