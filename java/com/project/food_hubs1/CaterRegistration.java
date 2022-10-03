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

public class CaterRegistration extends AppCompatActivity {
    EditText etName,etBname,etEmail,etMobile,etYop,etPassword,etCity;
    Button btnRegister;

    Users users;

    DatabaseReference reff,catreff;
    Validation validation;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cater_registration);
       progressDialog = new ProgressDialog(this);
        validation = new Validation();
        etName=findViewById(R.id.etName);
        etBname=findViewById(R.id.etBname);
        etEmail=findViewById(R.id.etEmail);
        etMobile=findViewById(R.id.etMobile);
        etYop=findViewById(R.id.etYop);
        etPassword=findViewById(R.id.etPassword);
        etCity=findViewById(R.id.etCity);
        btnRegister=findViewById(R.id.btnRegister);

        users=new Users();


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validation.registercartervalidate(CaterRegistration.this,etName.getText().toString(),etBname.getText().toString(),etEmail.getText().toString(),etMobile.getText().toString(),etYop.getText().toString(),etCity.getText().toString(),etPassword.getText().toString())) {
                    String mobile = etMobile.getText().toString().trim();
                    isUser(mobile);
                }
            }
        });
    }

    private void isUser(String mobile){
        progressDialog.setProgressDialog();
        reff= FirebaseDatabase.getInstance().getReference().child("Users").child(mobile);
        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressDialog.hidedialog();
                if (snapshot.exists()){
                    Toast.makeText(CaterRegistration.this, "Phone number already registered. Please register with different phone number", Toast.LENGTH_SHORT).show();
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
        Toast.makeText(this, "Hi from Enter", Toast.LENGTH_SHORT).show();

        reff= FirebaseDatabase.getInstance().getReference().child("Users");
        catreff= FirebaseDatabase.getInstance().getReference().child("caters");
        String name=etName.getText().toString().trim();
        String bname=etBname.getText().toString().trim();
        String email=etEmail.getText().toString().trim();
        String mobile=etMobile.getText().toString().trim();
        String city=etCity.getText().toString().trim();
        String age=etYop.getText().toString().trim();
        String password=Hiding.hashing(etPassword.getText().toString().trim());


        users.setName(name);
        users.setBname(bname);
        users.setEmail(email);
        users.setMobile(mobile);
        users.setAddress(age);
        users.setPassword(password);
        users.setuType("cater");
        String uprcase = city.substring(0,1).toUpperCase();
        users.setCity(uprcase+city.substring(1).toLowerCase());

        reff.child(mobile).setValue(users);
      //  catreff.child(city).child(mobile).setValue(users);
        Toast.makeText(getApplicationContext(), "Caterer Inserted Successfully", Toast.LENGTH_SHORT).show();
        Intent intent= new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }

}