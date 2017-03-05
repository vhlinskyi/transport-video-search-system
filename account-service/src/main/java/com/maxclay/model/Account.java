package com.maxclay.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * Representation of user's account.
 *
 * @author Vlad Glinskiy
 */
@Document(collection = Account.COLLECTION_NAME)
public class Account implements Serializable {

    public static final String COLLECTION_NAME = "accounts";

    @Id
    private String id;
    private String name;
    private String email;

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

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("class Account {\n");
        sb.append("  id: ").append(id).append("\n");
        sb.append("  name: ").append(name).append("\n");
        sb.append("  email: ").append(email).append("\n");
        sb.append("}\n");

        return sb.toString();
    }
}
