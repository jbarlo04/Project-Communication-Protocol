package com.project.food_hubs1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.food_hubs1.util.GlobalVariable;
import com.project.food_hubs1.util.ProgressDialog;
import com.project.food_hubs1.util.SharedHelper;
import com.project.food_hubs1.util.Validation;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Button tvReglink;
    EditText etUsername,etPassword;
    Button btnLogin;
    String city;
    FusedLocationProviderClient fusedLocationProviderClient;
    DatabaseReference reff;
    Validation validation;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog = new ProgressDialog(this);

        validation = new Validation();


        tvReglink=findViewById(R.id.tvReglink);
        etUsername=findViewById(R.id.etUsername);
        etPassword=findViewById(R.id.etPassword);
        btnLogin=findViewById(R.id.btnLogin);


        tvReglink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(),Register_chk.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile=etUsername.getText().toString().trim();
                if (validation.loginvalidate(MainActivity.this,mobile,etPassword.getText().toString()))
                {
                    hideKeyboardFrom(MainActivity.this,getCurrentFocus());

                    String password=Hiding.hashing(etPassword.getText().toString().trim());
                    isUser(mobile,password);
                }

            }
        });
    }



    private void isUser(String mobile, String password){
        progressDialog.setProgressDialog();
        reff= FirebaseDatabase.getInstance().getReference().child("Users").child(mobile);
        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){String upassword=snapshot.child("password").getValue(String.class);
                     progressDialog.hidedialog();
                    if(upassword.equals(password)){
                        Toast.makeText(MainActivity.this, "Login successfully", Toast.LENGTH_SHORT).show();
                        String utype=snapshot.child("uType").getValue(String.class);
                        String uname=snapshot.child("name").getValue(String.class);
                        String ubname=snapshot.child("bname").getValue(String.class);

                         // store variables
                        SharedHelper.getinstance().putKey(MainActivity.this, GlobalVariable.variables.UserType,utype);
                        SharedHelper.getinstance().putKey(MainActivity.this, GlobalVariable.variables.mobile,mobile);
                        SharedHelper.getinstance().putKey(MainActivity.this, GlobalVariable.variables.uname,uname);
                        SharedHelper.getinstance().putKey(MainActivity.this, GlobalVariable.variables.bname,ubname);

                        chkLogin(mobile,utype,uname,ubname);
                    }else{
                        Toast.makeText(MainActivity.this, "Please check your Credentials", Toast.LENGTH_SHORT).show();

                    }
                }else{
                    progressDialog.hidedialog();

                    Toast.makeText(MainActivity.this, "User Not Exists", Toast.LENGTH_SHORT).show();
                    Intent intent55=new Intent(getApplicationContext(),Register_chk.class);
                    startActivity(intent55);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "User Not Exists or Please check your Credentials", Toast.LENGTH_SHORT).show();
                Intent intent55=new Intent(getApplicationContext(),Register_chk.class);
                startActivity(intent55);
            }
        });
    }

    private void chkLogin(String mobile,String uType, String uname, String ubname){

        if(uType.equals("user")){
            Intent intent=new Intent(getApplicationContext(), UserDash.class);
            intent.putExtra("usermobile",mobile);
            intent.putExtra("username",uname);
            intent.putExtra("city",SharedHelper.getinstance().getKey(this,"city"));
            startActivity(intent);
            finish();

       // getLocation(intent);
        }else{
            Intent intent=new Intent(getApplicationContext(), CaterDash.class);
            intent.putExtra("usermobile",mobile);
            intent.putExtra("username",uname);
            intent.putExtra("userbname",ubname);
            startActivity(intent);
            finish();
        }
    }

    public void getLocation(Intent intent)
    {
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);
        if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

            get_Location(intent);
        }else{
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
        }
    }

    @SuppressLint("MissingPermission")
    public void get_Location(Intent intent) {
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Location> task) {
                Location location=task.getResult();

                if(location!=null){
                    try {
                        Geocoder geocoder=new Geocoder(MainActivity.this, Locale.getDefault());
                        List<Address> addressList=geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);

                        city=addressList.get(0).getLocality();
                        intent.putExtra("city",city);
                        startActivity(intent);
                        finish();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public static void hideKeyboardFrom(Activity context, View view) {

        if (view!=null)
        {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }
}