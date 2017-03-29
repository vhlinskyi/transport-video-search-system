package com.maxclay.service;

import com.maxclay.model.RecognitionResult;

/**
 * @author Vlad Glinskiy
 */
public interface RecognitionService {

    RecognitionResult recognize(byte[] imageData);
}
