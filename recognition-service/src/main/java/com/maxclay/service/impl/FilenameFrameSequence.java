package com.maxclay.service.impl;

import com.maxclay.service.VideoFrameSequence;
import com.xuggle.xuggler.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * TODO create step in seconds
 * TODO search for optimization
 * TODO REVIEW CODE
 *
 * @author Vlad Glinskiy
 */
public class FilenameFrameSequence implements VideoFrameSequence {

    private static final int MICROSECONDS_IN_SECOND = 1_000_000;
    private final static Logger log = LoggerFactory.getLogger(FilenameFrameSequence.class);

    private IContainer container;
    private IStreamCoder videoCoder;
    private int videoStreamId;

    private int stepInSeconds;
    private boolean hasNext;
    private IPacket currentPacket;
    private IVideoPicture currentPicture;

    public FilenameFrameSequence(String absoluteFilePath, int stepInSeconds) {

        if (absoluteFilePath == null || absoluteFilePath.isEmpty()) {
            throw new IllegalArgumentException("File path can not be empty");
        }

        if (stepInSeconds < 0) {
            throw new IllegalArgumentException("Step must be greater than zero");
        }

        this.stepInSeconds = stepInSeconds;
        container = IContainer.make();
        if (container.open(absoluteFilePath, IContainer.Type.READ, null) < 0) {
            throw new IllegalArgumentException("Could not open file: " + absoluteFilePath);
        }

        videoStreamId = -1;
        for (int i = 0; i < container.getNumStreams(); i++) {
            IStreamCoder coder = container.getStream(i).getStreamCoder();
            if (coder.getCodecType() == ICodec.Type.CODEC_TYPE_VIDEO) {
                videoStreamId = i;
                videoCoder = coder;
                break;
            }
        }

        if (videoStreamId == -1) {
            throw new IllegalArgumentException("Could not find video stream");
        }

        if (videoCoder.open(null, null) < 0) {
            throw new IllegalArgumentException("Could not open video decoder");
        }

        currentPacket = IPacket.make();

        readAndCheckForNextPacket();
    }

    @Override
    public byte[] getNext() {

        if (!this.hasNext) {
            throw new IllegalStateException("No frames left");
        }

        BufferedImage frame = Utils.videoPictureToImage(currentPicture);
        readAndCheckForNextPacket();

        // TODO convert only necessary pictures
        return (frame != null) ? bufferedImageToByteArray(frame) : null;
    }

    private void readAndCheckForNextPacket() {

        this.hasNext = false;
        while (container.readNextPacket(currentPacket) >= 0) {

            if (currentPacket.getStreamIndex() != videoStreamId) {
                continue;
            }

            IVideoPicture decoded = decodePacket(currentPacket);
            if (decoded == null) {
                continue;
            }

            if (currentPicture == null ||
                    decoded.getTimeStamp() - currentPicture.getTimeStamp() > stepInSeconds * MICROSECONDS_IN_SECOND) {

                this.currentPicture = decoded;
                this.hasNext = true;
                break;
            }
        }

        if (!this.hasNext) {
            destroy();
        }
    }

    private IVideoPicture decodePacket(IPacket packet) {

        IVideoPicture picture = IVideoPicture.make(videoCoder.getPixelType(), videoCoder.getWidth(), videoCoder.getHeight());

        int offset = 0;
        while (offset < packet.getSize() && !picture.isComplete()) {

            int bytesDecoded = videoCoder.decodeVideo(picture, packet, offset);
            offset += bytesDecoded;

            if (!picture.isComplete()) {
                continue;
            }

            return picture;
        }//TODO refactor

        return null;
    }

    private byte[] bufferedImageToByteArray(BufferedImage image) {

        byte[] imageData = null;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            ImageIO.write(image, "png", baos);
            baos.flush();
            imageData = baos.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return imageData;
    }


    @Override
    public boolean hasNext() {
        return this.hasNext;
    }

    private void destroy() {

        if (videoCoder != null) {
            videoCoder.close();
        }

        if (container != null) {
            container.close();
        }

        log.info("Resources released");
    }
}
