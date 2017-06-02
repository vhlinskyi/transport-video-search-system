package com.maxclay.service.impl;

import com.maxclay.service.VideoFrameSequence;
import com.maxclay.service.VideoService;
import com.xuggle.xuggler.IContainer;
import org.springframework.stereotype.Service;

/**
 * TODO create pool? search for optimization
 *
 * @author Vlad Glinskiy
 */
@Service
public class VideoServiceImpl implements VideoService {

    public static final int DEFAULT_STEP_IN_SECONDS = 3;

    private static final int MICROSECONDS_IN_SECOND = 1_000_000;

    @Override
    public long getDurationInSeconds(String absoluteVideoPath) {

        if (absoluteVideoPath == null || absoluteVideoPath.isEmpty()) {
            throw new IllegalArgumentException("Path to the video file can not be empty");
        }

        IContainer container = IContainer.make();
        container.open(absoluteVideoPath, IContainer.Type.READ, null);
        long duration = container.getDuration();
        container.close();

        return duration / MICROSECONDS_IN_SECOND;
    }

    @Override
    public VideoFrameSequence getSequence(String absoluteVideoPath, int frameStepInSecond) {

        if (absoluteVideoPath == null || absoluteVideoPath.isEmpty()) {
            throw new IllegalArgumentException("Path to the video file can not be empty");
        }

        if (frameStepInSecond < 0) {
            throw new IllegalArgumentException("Frame step must be greater than zero");
        }

        return new FilenameFrameSequence(absoluteVideoPath, frameStepInSecond);
    }

    @Override
    public VideoFrameSequence getSequence(String absoluteVideoPath) {
        return getSequence(absoluteVideoPath, DEFAULT_STEP_IN_SECONDS);
    }
}
