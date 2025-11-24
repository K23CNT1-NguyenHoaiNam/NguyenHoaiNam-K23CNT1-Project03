package com.devmaster.lesson5.entity;

public class Info {
    private String name;
    private String major;
    private String email;
    private String website;

    public Info(String name, String major, String email, String website) {
        this.name = name;
        this.major = major;
        this.email = email;
        this.website = website;
    }

    // getters
    public String getName() { return name; }
    public String getMajor() { return major; }
    public String getEmail() { return email; }
    public String getWebsite() { return website; }
}
