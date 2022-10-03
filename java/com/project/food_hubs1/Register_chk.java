package com.project.food_hubs1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Register_chk extends AppCompatActivity {
    Button btnUsereg,btncatereg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_chk);
        btnUsereg=findViewById(R.id.btnUsereg);
        btncatereg=findViewById(R.id.btncatereg);


        btnUsereg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),UserRegistration.class);
                startActivity(intent);
            }
        });

        btncatereg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),CaterRegistration.class);
                startActivity(intent);
            }
        });
    }
}