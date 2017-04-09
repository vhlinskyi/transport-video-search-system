package com.maxclay.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Vlad Glinskiy
 */
public class PlateSearchRequest {

    @JsonProperty("number_plate")
    private String numberPlate;

    public PlateSearchRequest(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("numberPlate", numberPlate)
                .toString();
    }
}