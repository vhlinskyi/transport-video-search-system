package com.maxclay.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Vlad Glinskiy
 */
public class WantedTransport {

    @JsonProperty("chassis_number")
    private String chassisNumber;

    @JsonProperty("body_number")
    private String bodyNumber;

    @JsonProperty("number_plate")
    private String numberPlate;

    @JsonProperty("model")
    private String model;

    @JsonProperty("color")
    private String color;

    @JsonProperty("department")
    private String department;

    public String getChassisNumber() {
        return chassisNumber;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
    }

    public String getBodyNumber() {
        return bodyNumber;
    }

    public void setBodyNumber(String bodyNumber) {
        this.bodyNumber = bodyNumber;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("chassisNumber", chassisNumber)
                .append("bodyNumber", bodyNumber)
                .append("numberPlate", numberPlate)
                .append("model", model)
                .append("color", color)
                .append("department", department)
                .toString();
    }
}
