package com.maxclay.model;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Vlad Glinskiy
 */
public class RecognitionResult {

    private List<PlateRecognitionResult> plateResults = new ArrayList<>();

    public List<PlateRecognitionResult> getPlateResults() {
        return Collections.unmodifiableList(plateResults);
    }

    public void addPlateResult(PlateRecognitionResult plateResult) {
        plateResults.add(plateResult);
    }

    public boolean isEmpty() {
        return plateResults.isEmpty();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("plateResults", plateResults)
                .toString();
    }
}
