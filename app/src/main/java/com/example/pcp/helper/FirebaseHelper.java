package com.example.pcp.helper;

import com.example.pcp.dto.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseHelper {

    private static final String server = "https://pcp-project-juris-default-rtdb.europe-west1.firebasedatabase.app";

    private static FirebaseDatabase database = FirebaseDatabase.getInstance(server);


    public static DatabaseReference usersRef = database.getReference("users");
    public static DatabaseReference groups = database.getReference("groups");


    public static final String users = "users";

    public static User[] getUsers() {
        // Get users from firebase
        usersRef = database.getReference(users);

        User[] users = new User[2];
        users[0] = new User("Juris", "12345");
        users[1] = new User("Juris", "12345");
        return users;
    }

    public static void createUser(User user) {
        usersRef.push().setValue(user);
    }


}
