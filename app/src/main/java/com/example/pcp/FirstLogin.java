package com.example.pcp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.pcp.databinding.FragmentFirstBinding;
import com.example.pcp.dto.User;
import com.example.pcp.helper.FirebaseHelper;
import com.example.pcp.states.UserState;

public class FirstLogin extends Fragment {

    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        redirectIfUserExist();

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    



    public boolean isFirstBoot() {
        final String PREFS_NAME = "MyPrefsFile";

        SharedPreferences settings = getContext().getSharedPreferences(PREFS_NAME, 0);

        if (settings.getBoolean("my_first_time", true)) {
            //the app is being launched for first time
            settings.edit().putBoolean("my_first_time", false).commit();
            return true;
        }
        return false;
    }


    public void redirectIfUserExist() {

        if(!this.isFirstBoot()) {
            Log.d("Comments", "Not first time");
            // first time task
            NavHostFragment.findNavController(FirstLogin.this)
                    .navigate(R.id.action_FirstFragment_to_SecondFragment);
        }
    }




    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // get text from EditText "editTextTextPersonName"
                String name = binding.editTextTextPersonName.getText().toString();

                // get device id
                String deviceId = android.provider.Settings.Secure.getString(getContext().getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

                UserState.set(new User("no name", deviceId));

                if (TextUtils.isEmpty(binding.editTextTextPersonName.getText())) {
                    android.widget.Toast.makeText(getContext(), "Name is required", android.widget.Toast.LENGTH_SHORT).show();
                    binding.editTextTextPersonName.setError("Name is required!");

                } else {
                    FirebaseHelper.createUser(new User(name, deviceId));
                    NavHostFragment.findNavController(FirstLogin.this)
                            .navigate(R.id.action_FirstFragment_to_SecondFragment);

                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}