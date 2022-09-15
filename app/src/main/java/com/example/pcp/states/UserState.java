package com.example.pcp.states;

import com.example.pcp.dto.User;

public class UserState {

    public static User get() {
        return user;
    }

    public static void set(User newUser) {
         user = newUser;
    }

    private static User user;

}
