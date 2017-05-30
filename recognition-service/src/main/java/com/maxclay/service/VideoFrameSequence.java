package com.maxclay.service;

/**
 * @author Vlad Glinskiy
 */
public interface VideoFrameSequence {

    byte[] getNext();

    boolean hasNext();

}
