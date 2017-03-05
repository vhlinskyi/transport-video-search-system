package com.maxclay.dto;

/**
 * User data transfer object. Used for transferring user-related data, for example in case of user's registration in
 * auth service.
 *
 * @author Vlad Glinskiy
 */
public class UserDto {

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
        sb.append("class UserDto {\n");
        sb.append("  name: ").append(username).append("\n");
        sb.append("  password: ").append(password).append("\n");
        sb.append("}\n");

        return sb.toString();
    }
}
