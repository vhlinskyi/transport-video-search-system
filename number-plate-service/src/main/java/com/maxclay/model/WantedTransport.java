package com.maxclay.model;

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
@Document
public class WantedTransport {

    public static final String GENERIC_COLLECTION_NAME = "wanted_transport";

    @Id
    private String id;

    @Field("chassis_number")
    private String chassisNumber;

    @Field("body_number")
    private String bodyNumber;

    @Field("number_plate")
    private String numberPlate;

    @Field("model")
    private String model;

    @Field("color")
    private String color;

    @Field("registered_as_wanted")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date registeredAsWanted;

    @Field("mvs_id")
    private Long mvsId;

    @Field("department")
    private String department;

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
                .toString();
    }
}
