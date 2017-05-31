package com.maxclay.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Vlad Glinskiy
 */
@Document(collection = Task.COLLECTION_NAME)
public class Task implements Serializable {

    public static final String COLLECTION_NAME = "tasks";

    @Id
    private String id;

    private Set<String> images;
    private Set<String> videos;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set<String> getImages() {
        return (images != null) ? images : Collections.emptySet();
    }

    public void setImages(Set<String> images) {
        this.images = images;
    }

    public Set<String> getVideos() {
        return (videos != null) ? videos : Collections.emptySet();
    }

    public void setVideos(Set<String> videos) {
        this.videos = videos;
    }

    public void addImage(String path) {

        if (path == null || path.isEmpty()) {
            throw new IllegalArgumentException("Path can not be empty");
        }

        if (this.images == null) {
            this.images = new HashSet<>();
        }

        this.images.add(path);
    }

    public void addVideo(String path) {

        if (path == null || path.isEmpty()) {
            throw new IllegalArgumentException("Path can not be empty");
        }

        if (this.videos == null) {
            this.videos = new HashSet<>();
        }

        this.videos.add(path);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("images", images)
                .append("videos", videos)
                .toString();
    }
}
