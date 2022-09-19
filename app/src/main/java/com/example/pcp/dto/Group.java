package com.example.pcp.dto;

import com.example.pcp.states.UserState;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

@IgnoreExtraProperties
public class Group {

    public String id;
    public String name;
    public String adminId;
    public ArrayList<String> membersIds;

    public Group() {}

    public Group(String name) {
        this.name = name;
        this.adminId = UserState.get().deviceId;
        this.membersIds = new ArrayList<>();
    }

    public Group(String name, String adminId, ArrayList<String> membersIds) {
        this.name = name;
        this.adminId = adminId;
        this.membersIds = membersIds;
    }
}
