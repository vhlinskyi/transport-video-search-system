package com.maxclay.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

/**
 * @author Vlad Glinskiy
 */
@ConfigurationProperties(prefix = "files.upload")
public class ProfilePicturesUploadProperties {

    private Resource profilePicturesDir;
    private Resource defaultProfilePicture;

    public Resource getDefaultProfilePicture() {
        return defaultProfilePicture;
    }

    public void setDefaultProfilePicture(String defaultProfilePicture) {
        this.defaultProfilePicture = new DefaultResourceLoader().getResource(defaultProfilePicture);
    }

    public Resource getProfilePicturesDir() {
        return profilePicturesDir;
    }

    public void setProfilePicturesDir(String profilePicturesDir) {
        this.profilePicturesDir = new FileSystemResource(profilePicturesDir);
    }
}
