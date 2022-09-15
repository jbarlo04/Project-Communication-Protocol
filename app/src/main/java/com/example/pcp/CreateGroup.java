package com.example.pcp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pcp.dto.Group;
import com.example.pcp.dto.User;
import com.example.pcp.helper.FirebaseHelper;
import com.example.pcp.states.UserState;
import com.google.android.material.snackbar.Snackbar;


public class CreateGroup extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_group, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        // When clicking button "Create" with id "button_first" get text from input field "@+id/textview_first"
        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String groupName = ((android.widget.EditText) view.getRootView().findViewById(R.id.editTextTextPersonName)).getText().toString();

                Log.d("Groups:", groupName);
                Group group = new Group(groupName);

                FirebaseHelper.createGroup(group);

                NavHostFragment.findNavController(CreateGroup.this)
                        .navigate(R.id.action_createGroup_to_SecondFragment);

                // display snack bar
                Snackbar.make(view, "Group created", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}