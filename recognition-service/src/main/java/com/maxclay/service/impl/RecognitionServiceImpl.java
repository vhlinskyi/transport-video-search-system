package com.maxclay.service.impl;

import com.maxclay.model.PlateRecognitionResult;
import com.maxclay.model.RecognitionResult;
import com.maxclay.service.RecognitionService;
import com.openalpr.jni.Alpr;
import com.openalpr.jni.AlprPlate;
import com.openalpr.jni.AlprPlateResult;
import com.openalpr.jni.AlprResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Vlad Glinskiy
 */
@Component
public class RecognitionServiceImpl implements RecognitionService {

    private static final int NUMBER_OF_RECOGNITION_RESULTS = 1;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final Alpr alpr;

    @Autowired
    public RecognitionServiceImpl(@Value("${alpr.country}") String country,
                                  @Value("${alpr.config}") String configFile,
                                  @Value("${alpr.runtime}") String runtimeData) {

        this.alpr = new Alpr(country, configFile, runtimeData);
        this.alpr.setTopN(NUMBER_OF_RECOGNITION_RESULTS);
    }

    @Override
    public RecognitionResult recognize(byte[] imageData) {

        if (imageData == null || imageData.length == 0) {
            throw new IllegalArgumentException("Image data can not be empty");
        }

        RecognitionResult recognitionResult = new RecognitionResult();
        AlprResults results = alpr.recognize(imageData);
        for (AlprPlateResult result : results.getPlates()) {
            for (AlprPlate plate : result.getTopNPlates()) {
                recognitionResult.addPlateResult(new PlateRecognitionResult(plate.getCharacters(), plate.getOverallConfidence()));
            }
        }

        logger.info("Recognized: {}", recognitionResult);
        return recognitionResult;
    }
}
