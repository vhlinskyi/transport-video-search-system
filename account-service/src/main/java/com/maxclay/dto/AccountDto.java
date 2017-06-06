package com.maxclay.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Account data transfer object. Used for transferring user-related data, for example in case of registration user's
 * account, when additional password field is required.
 *
 * @author Vlad Glinskiy
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountDto {

    private String id;
    private String name;
    private String email;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("skype_name")
    private String skypeName;

    private String phone;
    private String quote;
    private String picture;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSkypeName() {
        return skypeName;
    }

    public void setSkypeName(String skypeName) {
        this.skypeName = skypeName;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
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
        sb.append("class AccountDto {\n");
        sb.append("  id: ").append(id).append("\n");
        sb.append("  name: ").append(name).append("\n");
        sb.append("  email: ").append(email).append("\n");
        sb.append("  firstName: ").append(firstName).append("\n");
        sb.append("  lastName: ").append(lastName).append("\n");
        sb.append("  skypeName: ").append(skypeName).append("\n");
        sb.append("  phone: ").append(phone).append("\n");
        sb.append("  quote: ").append(quote).append("\n");
        sb.append("  picture: ").append(picture).append("\n");
        sb.append("  password: ").append(password).append("\n");
        sb.append("  matchingPassword: ").append(matchingPassword).append("\n");
        sb.append("}\n");

        return sb.toString();
    }
}
