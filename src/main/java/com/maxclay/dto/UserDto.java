package com.maxclay.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * User data transfer object. Used for transferring user-related data, for example in case of user registration, when
 * additional password field is required.
 *
 * @author Vlad Glinskiy
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    private String id;
    private String name;
    private String email;
    private String password;
    private String matchingPassword;

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

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("class UserDto {\n");
        sb.append("  id: ").append(id).append("\n");
        sb.append("  name: ").append(name).append("\n");
        sb.append("  email: ").append(email).append("\n");
        sb.append("  password: ").append(password).append("\n");
        sb.append("  matchingPassword: ").append(matchingPassword).append("\n");
        sb.append("}\n");

        return sb.toString();
    }
}
