package com.maxclay.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.sql.Timestamp;
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

    private String date;

    @JsonProperty("approximate_size")
    private Long approximateSize;

    @Transient
    private long processed;

    private Set<PlateRecognitionResult> recognized;

    @JsonIgnore
    private Set<String> images;

    @JsonIgnore
    private Set<String> videos;

    private boolean done;
    private boolean processing;


    public Task() {
        date = new Timestamp(System.currentTimeMillis()).toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getProcessed() {
        return processed;
    }

    public void setProcessed(long processed) {
        this.processed = processed;
    }

    public void incrementProcessed() {
        this.processed++;
    }

    public Long getApproximateSize() {
        return approximateSize;
    }

    public void setApproximateSize(Long approximateSize) {
        this.approximateSize = approximateSize;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean isProcessing() {
        return processing;
    }

    public void setProcessing(boolean processing) {
        this.processing = processing;
    }

    public Set<PlateRecognitionResult> getRecognized() {
        return (recognized != null) ? recognized : Collections.emptySet();
    }

    public void setRecognized(Set<PlateRecognitionResult> recognized) {
        this.recognized = recognized;
    }

    public void addRecognized(PlateRecognitionResult recognitionResult) {

        if (recognized == null) {
            recognized = new HashSet<>();
        }

        recognized.add(recognitionResult);
    }

    public Set<String> getImages() {
        return (images != null) ? images : Collections.emptySet();
    }

    @JsonProperty("images_number")
    public int getImagesNumber() {
        return getImages().size();
    }

    public void setImages(Set<String> images) {
        this.images = images;
    }

    public Set<String> getVideos() {
        return (videos != null) ? videos : Collections.emptySet();
    }

    @JsonProperty("videos_number")
    public int getVideosNumber() {
        return getVideos().size();
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
                .append("date", date)
                .append("approximateSize", approximateSize)
                .append("processed", processed)
                .append("recognized", recognized)
                .append("images", images)
                .append("videos", videos)
                .append("done", done)
                .append("processing", processing)
                .toString();
    }
}
