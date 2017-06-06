package com.maxclay.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Vlad Glinskiy
 */
@Document(collection = Alert.COLLECTION_NAME)
public class Alert implements Serializable {

    public static final String COLLECTION_NAME = "alerts";

    @Id
    private String id;
    private AlertType type;
    private String message;
    private Map<String, Object> refs;
    private Date date;
    private String owner;

    public Alert() {
        this.date = new Date(System.currentTimeMillis());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AlertType getType() {
        return type;
    }

    public void setType(AlertType type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getRefs() {
        return (refs != null) ? refs : Collections.emptyMap();
    }

    public void setRefs(Map<String, Object> refs) {
        this.refs = refs;
    }

    public void addRef(String key, Object ref) {
        if (this.refs == null) {
            this.refs = new HashMap<>();
        }

        this.refs.put(key, ref);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
