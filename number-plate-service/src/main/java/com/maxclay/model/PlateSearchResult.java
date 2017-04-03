package com.maxclay.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Vlad Glinskiy
 */
public final class PlateSearchResult {

    @JsonProperty("number_plate")
    private String numberPlate;

    @JsonProperty("is_found")
    private boolean found;

    public String getNumberPlate() {
        return numberPlate;
    }

    public PlateSearchResult(String plateNumber, boolean found) {
        this.numberPlate = plateNumber;
        this.found = found;
    }

    public boolean isFound() {
        return found;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("numberPlate", numberPlate)
                .append("found", found)
                .toString();
    }
}
