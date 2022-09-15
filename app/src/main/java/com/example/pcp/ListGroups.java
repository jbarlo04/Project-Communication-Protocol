package com.example.pcp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pcp.databinding.FragmentSecondBinding;
import com.example.pcp.dto.Group;
import com.example.pcp.dto.User;
import com.example.pcp.dto.UserListCallback;
import com.example.pcp.helper.FirebaseHelper;
import com.example.pcp.states.UserState;

import java.util.ArrayList;

public class ListGroups extends Fragment {

    private FragmentSecondBinding binding;
    private ArrayList<Group> groupsList;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {



        FirebaseHelper.fetchUsers(new UserListCallback() {
            @Override
            public void onCallback(ArrayList<User> users) {
                Log.d("users", "Loaded " + users.size() + " users");
                printUsers(users);

                // set current user
                setUserState(users);
            }
        });

        binding = FragmentSecondBinding.inflate(inflater, container, false);


        groupsList = new ArrayList<>();
        // find view by id "list" of the recycler view
        recyclerView = binding.list;
        // set the adapter


        setGroupInfo();
        setAdapter();

        return binding.getRoot();
    }

    private void setAdapter() {
        recyclerAdapter adapter = new recyclerAdapter(groupsList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setGroupInfo() {
        groupsList.add(new Group("Group 1"));
        groupsList.add(new Group("Group 2"));
        groupsList.add(new Group("Group 3"));
    }

    public void setUserState(ArrayList<User> users) {
        // get device id from UserState
        User currentUser = UserState.get();

        String currentDeviceId = "";

        if (currentUser != null) {
            currentDeviceId = currentUser.deviceId;
        } else {
            // get device id
            String deviceId = android.provider.Settings.Secure.getString(getContext().getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
            currentDeviceId = deviceId;
        }


        // search for user with same device id as current user
        for (User user : users) {

            if (user.deviceId.equals(currentDeviceId)) {
                UserState.set(user);
            }
        }
    }

    public void printUsers(ArrayList<User> users) {
        // print number of users:
        Log.d("UsersSecondFragment", String.valueOf(users.size()));
        for (User user : users) {
            Log.d("Comments", "USER: " + user.name + " - deviceId: " + user.deviceId);
        }
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // When clicking the FAB, redirect to the CreateGroup fragment
                NavHostFragment.findNavController(ListGroups.this)
                        .navigate(R.id.action_global_createGroup);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}