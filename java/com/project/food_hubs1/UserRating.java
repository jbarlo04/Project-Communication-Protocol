package com.project.food_hubs1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.google.android.gms.location.FusedLocationProviderClient;

public class UserRating extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    private Button btnToggleDark;

    Bundle extras;
    String username,oid,usermobile;
    Button btnReview;
    EditText etReviewText;
    RatingBar ratingBar;
    float myRating;
    String message;
    DatabaseReference reff;
    String city,bmobile;

    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_rating);
        MaterialToolbar toolbar = findViewById(R.id.review_toolbar);
        toolbar.setNavigationOnClickListener(v->onBackPressed());
        extras = getIntent().getExtras();
        username=extras.getString("username");
        usermobile=extras.getString("usermobile");
        bmobile=extras.getString("bmobile");
        oid=extras.getString("oid");
        ratingBar=findViewById(R.id.ratingBar);
        etReviewText=findViewById(R.id.etReviewText);
        btnReview=findViewById(R.id.btnReview);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean fromUser) {
                int rating=(int) v;
                message=null;

                myRating=ratingBar.getRating();

                switch (rating){
                    case 1:
                        message="Sorry to Hear That !";
                        break;
                    case 2:
                        message="Please give us your Suggetions !";
                        break;
                    case 3:
                        message="We will stabilize Quickly !";
                        break;
                    case 4:
                        message="Thanks for the Valuable Review !";
                        break;
                    case 5:
                        message="Thanks and Expecting again !";
                        break;
                }
            }
        });

        btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


          DatabaseReference userdb=    FirebaseDatabase.getInstance().getReference("Users").child(bmobile).child("Ratings").child(oid);
                HashMap<String, Object> map = new HashMap();
                map.put("rating",ratingBar.getRating());
                map.put("message",etReviewText.getText().toString());
          userdb.updateChildren(map);


                /*Rating rating=new Rating();
                reff= FirebaseDatabase.getInstance().getReference("ratings").child(oid);
                rating.setMessage(etReviewText.getText().toString().trim());
                rating.setOid(oid);
                rating.setUser(usermobile);
                rating.setRating(Float.toString(myRating));

                reff.setValue(rating);*/
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                onBackPressed();
            }
        });
    }
    public void getLocation(Intent intent)
    {
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);
        if(ActivityCompat.checkSelfPermission(UserRating.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

            get_Location(intent);
        }else{
            ActivityCompat.requestPermissions(UserRating.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
        }
    }

    public void get_Location(Intent intent) {
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Location> task) {
                Location location=task.getResult();
                if(location!=null){
                    try {
                        Geocoder geocoder=new Geocoder(UserRating.this, Locale.getDefault());
                        List<Address> addressList=geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);

                        city=addressList.get(0).getLocality();
                        intent.putExtra("city",city);
                        startActivity(intent);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}