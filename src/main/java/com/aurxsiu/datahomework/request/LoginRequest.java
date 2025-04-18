package com.aurxsiu.datahomework.request;

public class LoginRequest {
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

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public LoginRequest(String Username, String password) {
        this.Username = Username;
        this.password = password;
    }
}
