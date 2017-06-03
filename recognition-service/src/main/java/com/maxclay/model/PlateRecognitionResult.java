package com.maxclay.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.Objects;

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
    public boolean equals(Object obj) {

        if (obj == this) {
            return true;
        }

        if (!(obj instanceof PlateRecognitionResult)) {
            return false;
        }

        PlateRecognitionResult another = (PlateRecognitionResult) obj;
        return (plateNumber == null ? another.plateNumber == null : plateNumber.equals(another.plateNumber));
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(plateNumber);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("plateNumber", plateNumber)
                .append("overallConfidence", overallConfidence)
                .toString();
    }
}
