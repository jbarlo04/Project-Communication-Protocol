package com.example.pcp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pcp.dto.Group;
import com.example.pcp.dto.User;
import com.example.pcp.helper.FirebaseHelper;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link chat#newInstance} factory method to
 * create an instance of this fragment.
 */
public class chat extends Fragment {

    Group currentGroup = new Group();
    ArrayList<User> users = new ArrayList<>();
    private String groupId;


    public chat() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment chat.
     */
    // TODO: Rename and change types and number of parameters
    public static chat newInstance(String param1, String param2) {
        chat fragment = new chat();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    // creates a unique code that allows users to join the group
    public String createUniqueCodeGroup() {
        return currentGroup.id;
    }


    public void invitePeople(User user) {
        FirebaseHelper.addUserToGroup(user.deviceId, this.groupId);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_chat, container, false);
    }


    // onViewCreated
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                invitePeople(new User());
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }
}