package com.aurxsiu.datahomework.request;

public class RegisterRequest {
    private String Username;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public RegisterRequest(String username, String password) {
        Username = username;
        this.password = password;
    }
}
