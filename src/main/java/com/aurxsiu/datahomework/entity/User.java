package com.aurxsiu.datahomework.entity;

import java.util.HashMap;
import java.util.Objects;

public class User {
    private String username;
    private String password;
    private HashMap<String,Experience> experienceLog;
    private int id;

    public User(String username, String password, HashMap<String, Experience> experienceLog, int id) {
        this.username = username;
        this.password = password;
        this.experienceLog = experienceLog;
        this.id = id;
    }
    public User(){}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public HashMap<String, Experience> getExperienceLog() {
        return experienceLog;
    }

    public void setExperienceLog(HashMap<String, Experience> experienceLog) {
        this.experienceLog = experienceLog;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
