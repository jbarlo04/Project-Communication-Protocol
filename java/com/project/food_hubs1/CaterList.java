package com.project.food_hubs1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.food_hubs1.util.ProgressDialog;
import com.project.food_hubs1.util.RuntimePermissionHelper;
import com.project.food_hubs1.util.SharedHelper;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class CaterList extends AppCompatActivity {

    FusedLocationProviderClient fusedLocationProviderClient;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    private Button btnToggleDark;
    EditText etLoc_search;
    Button btnLoc_search;
    RuntimePermissionHelper runtimePermissionHelper;

    Bundle extras;
    String username, cityy,usermobile;

    DatabaseReference reff,Dreff;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cater_list);
        progressDialog = new ProgressDialog(this);
        etLoc_search=findViewById(R.id.etLoc_search);
        btnLoc_search=findViewById(R.id.btnLoc_search);
        extras = getIntent().getExtras();
        username = extras.getString("username");
        cityy = extras.getString("city");
        usermobile = extras.getString("usermobile");
        runtimePermissionHelper = new RuntimePermissionHelper(this);
        runtimePermissionHelper.setActivity(this);

        showDialog(this, new MyClick() {
            @Override
            public void OnClick() {

                etLoc_search.requestFocus();

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(etLoc_search, InputMethodManager.SHOW_IMPLICIT);;
            }
        });


        etLoc_search.setText(cityy);
        recyclerView=findViewById(R.id.recycleViewClist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));




        btnLoc_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cityy =etLoc_search.getText().toString().trim();

                if (getCurrentFocus()!=null)
                hideKeyboardFrom(CaterList.this,getCurrentFocus());
                 setdata();
                /*Intent intent6 = new Intent(getApplicationContext(), CaterList.class);

                intent6.putExtra("usermobile",usermobile);
                intent6.putExtra("username",username);
                intent6.putExtra("city", cityy);
                startActivity(intent6);
                finish();*/
            }
        });


    }

    void setdata()
    {
        ;
        reff = FirebaseDatabase.getInstance().getReference("Users");
        reff.keepSynced(true);
        FirebaseRecyclerOptions<DataSnapshot> options=new FirebaseRecyclerOptions.Builder<DataSnapshot>()
                .setQuery(reff.orderByChild("city").equalTo(cityy.substring(0,1).toUpperCase()+cityy.substring(1).toLowerCase()), new SnapshotParser<DataSnapshot>() {
                    @NonNull
                    @Override
                    public DataSnapshot parseSnapshot(@NonNull DataSnapshot snapshot) {
                        return snapshot;
                    }
                })
                .build();

        reff.orderByChild("city").equalTo(cityy).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressDialog.hidedialog();
                if (snapshot.exists())
                {
                    Toast.makeText(CaterList.this, "Carters found successfully", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        progressDialog.setProgressDialog();




        FirebaseRecyclerAdapter<DataSnapshot,UsersViewHolder> firebaseRecyclerAdapter=
                new FirebaseRecyclerAdapter<DataSnapshot, UsersViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull @NotNull UsersViewHolder holder, int position, @NonNull @NotNull DataSnapshot data) {

                        Users model = data.getValue(Users.class);



                            holder.setitem(this,model.getName(),model.getBname(),model.getEmail(),model.getMobile(),model.getAddress(),model.getPassword(),model.getuType(),model.getCity(),data);

                            holder.btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Uri gmmIntentUri = Uri.parse("google.navigation:q="+model.getAddress());
                                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                    mapIntent.setPackage("com.google.android.apps.maps");
                                    startActivity(mapIntent);
                                }
                            });
                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Log.e("cl","ccc");

                                    Intent intent = new Intent(getApplicationContext(), ViewMenu.class);

                                    intent.putExtra("usermobile",usermobile);
                                    intent.putExtra("username",username);
                                    intent.putExtra("city", cityy);
                                    intent.putExtra("bmobile",model.getMobile());
                                    intent.putExtra("bname",model.getBname());
                                    startActivity(intent);
                                }
                            });


                    }

                    @NotNull
                    @Override
                    public UsersViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                        View view= LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.view_cater,parent,false);

                        return new UsersViewHolder(view);
                    }
                };

        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);

    }

    public void darkbutton(View view) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

    }

    public void setUpToolbar() {
        drawerLayout = findViewById(R.id.drawerLayout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.black));
        actionBarDrawerToggle.syncState();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==1)
        {
            getLocation();
            Toast.makeText(this, "Permissino granted", Toast.LENGTH_SHORT).show();

        }
    }


    void getLocation() {
        Snackbar.make(getWindow().getDecorView().getRootView(), "Fetching location please wait...", Snackbar.LENGTH_LONG).show();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        get_Location();

    }



    @SuppressLint("MissingPermission")
    void get_Location() {
        Log.e("location",""+"ff");

        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {

                Location location = task.getResult();
                if (location==null) {
                    Snackbar.make(getWindow().getDecorView().getRootView(), "Not able to get your current location. Please enter manually", Snackbar.LENGTH_LONG).show();
                }
                else{
                    Geocoder geocoder = new Geocoder(CaterList.this, Locale.getDefault());
                    try {
                       List<Address> addressList =  geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                         String city = addressList.get(0).getLocality();
                         SharedHelper.getinstance().putKey(CaterList.this,"city",city);
                        etLoc_search.setText(city);
                        cityy = city;
                        setdata();


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void showDialog(Activity activity,MyClick myClick){
        final Dialog dialog = new Dialog(activity,R.style.WideDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        dialog.setContentView(R.layout.enablelocationdialog);

        TextView enablelocation = (TextView) dialog.findViewById(R.id.enablelocation);
        TextView manually = (TextView) dialog.findViewById(R.id.tventerlocmanually);

        enablelocation.setOnClickListener(view-> {

                    if (!runtimePermissionHelper.isAllPermissionAvailable()) {
                        runtimePermissionHelper.setActivity(CaterList.this);
                        runtimePermissionHelper.requestPermissionsIfDenied();
                    }else{getLocation();}


        dialog.dismiss();
        }
        );
        manually.setOnClickListener(view->{
            dialog.dismiss();
           myClick.OnClick();

        });

        dialog.show();

    }

    interface MyClick{
        void OnClick();
    }

}