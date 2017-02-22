package com.maxclay.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @author Vlad Glinskiy
 */
@Document(collection = User.COLLECTION_NAME)
public class User implements Serializable {

    public static final String COLLECTION_NAME = "users";

    @Id
    private String id;
    private String name;
    private String email;
    private String password;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("class User {\n");
        sb.append("  id: ").append(id).append("\n");
        sb.append("  name: ").append(name).append("\n");
        sb.append("  email: ").append(email).append("\n");
        sb.append("  password: ").append(password).append("\n");
        sb.append("}\n");

        return sb.toString();
    }
}
