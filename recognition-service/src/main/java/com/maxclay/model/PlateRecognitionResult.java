package com.maxclay.model;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Vlad Glinskiy
 */
public final class PlateRecognitionResult {

    private final String plateNumber;
    private final float overallConfidence;

    public PlateRecognitionResult(String plateNumber, float overallConfidence) {
        this.plateNumber = plateNumber;
        this.overallConfidence = overallConfidence;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public float getOverallConfidence() {
        return overallConfidence;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("plateNumber", plateNumber)
                .append("overallConfidence", overallConfidence)
                .toString();
    }
}
