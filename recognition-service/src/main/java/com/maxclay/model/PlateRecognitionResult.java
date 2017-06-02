package com.maxclay.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Vlad Glinskiy
 */
public final class PlateRecognitionResult {

    @JsonProperty("plate_number")
    private final String plateNumber;

    @JsonProperty("overall_confidence")
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
