package com.maxclay.service;

/**
 * @author Vlad Glinskiy
 */
public interface VideoService {

    long getDurationInSeconds(String absoluteVideoPath);

    VideoFrameSequence getSequence(String absoluteVideoPath);

    VideoFrameSequence getSequence(String absoluteVideoPath, int frameStepInSecond);

}
