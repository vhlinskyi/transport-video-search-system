package com.maxclay.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.maxclay.utils.DateUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author Vlad Glinskiy
 */
@Document(collection = WantedTransport.COLLECTION_NAME)
public class WantedTransport {

    public static final String COLLECTION_NAME = "wanted_transport";

    @JsonIgnore
    @Id
    private String id;

    @JsonProperty("chassis_number")
    @Field("chassis_number")
    private String chassisNumber;

    @JsonProperty("body_number")
    @Field("body_number")
    private String bodyNumber;

    // TODO use indexes
    @JsonProperty("number_plate")
    @Field("number_plate")
    private String numberPlate;

    @JsonProperty("model")
    @Field("model")
    private String model;

    @JsonProperty("color")
    @Field("color")
    private String color;

    @JsonIgnore
    @Field("registered_as_wanted")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date registeredAsWanted;

    @JsonIgnore
    @Field("mvs_id")
    private Long mvsId;

    @Field("department")
    private String department;

    @JsonIgnore
    @Field("data_set_version_id")
    private String dataSetVersionId;

    @JsonIgnore
    @Field("removed_at_version_id")
    private String removedAtVersionId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public Date getRegisteredAsWanted() {
        return registeredAsWanted;
    }

    public void setRegisteredAsWanted(Date registeredAsWanted) {
        this.registeredAsWanted = registeredAsWanted;
    }

    public Long getMvsId() {
        return mvsId;
    }

    public void setMvsId(Long mvsId) {
        this.mvsId = mvsId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDataSetVersionId() {
        return dataSetVersionId;
    }

    public void setDataSetVersionId(String dataSetVersionId) {
        this.dataSetVersionId = dataSetVersionId;
    }

    public String getRemovedAtVersionId() {
        return removedAtVersionId;
    }

    public void setRemovedAtVersionId(String removedAtVersionId) {
        this.removedAtVersionId = removedAtVersionId;
    }

    @JsonIgnore
    public boolean isFound() {
        return removedAtVersionId != null;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("chassisNumber", chassisNumber)
                .append("bodyNumber", bodyNumber)
                .append("numberPlate", numberPlate)
                .append("model", model)
                .append("color", color)
                .append("registeredAsWanted", DateUtils.formatToDay(registeredAsWanted))
                .append("mvsId", mvsId)
                .append("department", department)
                .append("dataSetVersionId", dataSetVersionId)
                .append("removedAtVersionId", removedAtVersionId)
                .toString();
    }
}
