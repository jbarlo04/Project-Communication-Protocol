package com.example.pcp.helper;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.pcp.dto.Group;
import com.example.pcp.dto.GroupListCallback;
import com.example.pcp.dto.User;
import com.example.pcp.dto.UserListCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class FirebaseHelper {

    private static final String server = "https://pcp-project-juris-default-rtdb.europe-west1.firebasedatabase.app";

    private static FirebaseDatabase database = FirebaseDatabase.getInstance(server);


    public static DatabaseReference usersRef = database.getReference("users");
    public static DatabaseReference groupsRef = database.getReference("groups");


  //  private static ArrayList<User> usersList = new ArrayList<>();

    public static final String users = "users";
    public static final String groups = "groups";

    public static void fetchUsers(final UserListCallback myCallback) {
        // Get users from firebase
        usersRef = database.getReference(users);
        ArrayList<User> list = new ArrayList<>();
        // Get users from firebase
        usersRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    list.clear();

                    Log.d("firebase", String.valueOf(task.getResult().getValue()));

                    for (DataSnapshot ds : task.getResult().getChildren()) {
                        User user = ds.getValue(User.class);
                        list.add(user);
                        //Log.d("firebase-helper", "USER: " + user.name + " - deviceId: " + user.deviceId);
                       // printUsers(list);
                    }
                    myCallback.onCallback(list);
                }
            };
        });
    }


    public static void fetchGroups(final GroupListCallback myCallback) {
        // Get users from firebase
        groupsRef = database.getReference(groups);
        ArrayList<Group> list = new ArrayList<>();
        // Get users from firebase
        groupsRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    list.clear();

                    Log.d("firebase", String.valueOf(task.getResult().getValue()));

                    for (DataSnapshot ds : task.getResult().getChildren()) {
                        Group group = ds.getValue(Group.class);
                        list.add(group);
                        //Log.d("firebase-helper", "USER: " + user.name + " - deviceId: " + user.deviceId);
                        // printUsers(list);
                    }
                    myCallback.onCallback(list);
                }
            };
        });
    }

/*    public static ArrayList<User> getUsersList() {
        return usersList;
    }
*/

    public static void printUsers(ArrayList<User> users) {
        // print number of users:
        Log.d("Users", String.valueOf(users.size()));
        for (User user : users) {
            Log.d("PRINTLIST: ", "USER: " + user.name + " - deviceId: " + user.deviceId);
        }
    }

    public static void createUser(User user) {
        usersRef.push().setValue(user);
    }

    public static void createGroup(Group group) {
        groupsRef.push().setValue(group);
    }


}
