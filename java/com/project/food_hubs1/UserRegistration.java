package com.project.food_hubs1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.food_hubs1.util.ProgressDialog;
import com.project.food_hubs1.util.Validation;

public class UserRegistration extends AppCompatActivity {

    EditText etName,etEmail,etMobile,etYop,etPassword;
    Button btnRegister;
    //Spinner spinner;

    Users users;

    DatabaseReference reff;
    ProgressDialog progressDialog;
    Validation validation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
        progressDialog = new ProgressDialog(this);
        validation = new Validation();
        //addSpinner();

        etName=findViewById(R.id.etName);
        etEmail=findViewById(R.id.etEmail);
        etMobile=findViewById(R.id.etMobile);
        etPassword=findViewById(R.id.etPassword);
        btnRegister=findViewById(R.id.btnRegister);

        users=new Users();


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile=etMobile.getText().toString().trim();

                if (validation.registervalidate(UserRegistration.this,etName.getText().toString(),etEmail.getText().toString(),etMobile.getText().toString(),etPassword.getText().toString()))
                {
                    isUser(mobile);

                }
            }
        });
    }

    //public void addSpinner(){
        //spinner=findViewById(R.id.spinAge);
      //  final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
      //          this,android.R.layout.simple_spinner_dropdown_item,
      //          getResources().getStringArray(R.array.Age)); //Your resource name
      //  spinner.setAdapter(arrayAdapter);
   // }


    private void isUser(String mobile){
        progressDialog.setProgressDialog();
        reff= FirebaseDatabase.getInstance().getReference().child("Users").child(mobile);
        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressDialog.hidedialog();
                if (snapshot.exists()){
                    Toast.makeText(UserRegistration.this, "Phone number already registered. Please register with different phone number", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getApplicationContext(),CaterRegistration.class);
                }else{
                    enter();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void enter(){

        reff= FirebaseDatabase.getInstance().getReference().child("Users");
       // spinner=findViewById(R.id.spinAge);
        String name=etName.getText().toString().trim();
        String email=etEmail.getText().toString().trim();
        String mobile=etMobile.getText().toString().trim();
       // String age=spinner.getSelectedItem().toString();
        String password=Hiding.hashing(etPassword.getText().toString().trim());


        users.setName(name);
        users.setEmail(email);
        users.setMobile(mobile);
        //users.setYop(age);
        users.setPassword(password);
        users.setuType("user");

        reff.child(mobile).setValue(users);
        Toast.makeText(getApplicationContext(), "User Inserted Successfully", Toast.LENGTH_SHORT).show();
        Intent intent= new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }

}
