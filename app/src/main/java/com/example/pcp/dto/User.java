package com.example.pcp.dto;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {

    public String name;
    public String deviceId;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String name, String deviceId) {
        this.name = name;
        this.deviceId = deviceId;
    }

}
