package com.project.food_hubs1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.food_hubs1.chat.ChatUserList;
import com.project.food_hubs1.util.GlobalVariable;
import com.project.food_hubs1.util.NotificationModel;
import com.project.food_hubs1.util.RuntimePermissionHelper;
import com.project.food_hubs1.util.SharedHelper;

import java.util.HashMap;
import java.util.Map;

public class CaterDash extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    private Button btnToggleDark;
    Bundle extras;
    String username,ubname,usermobile;
    ImageView orderbadge,carterchatbadge;
    RuntimePermissionHelper runtimePermissionHelper;
    boolean isorderbadgevisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cater_dash);
        extras = getIntent().getExtras();
        username=extras.getString("username");
        ubname=extras.getString("userbname");
        usermobile=extras.getString("usermobile");
       // setUpToolbar();
        orderbadge = findViewById(R.id.carterorderbadge);
        carterchatbadge = findViewById(R.id.carterchat_badge);
        /*runtimePermissionHelper = new RuntimePermissionHelper(this);
        runtimePermissionHelper.setActivity(this);
        if (!runtimePermissionHelper.isAllPermissionAvailable())
        {
            GlobalVariable.Companion.globalyesno_btndialog(this, "Location permission is required. Press ok to enable permission", new GlobalVariable.OnOkselectforlocation() {
                @Override
                public void yesselect() {
                    runtimePermissionHelper.setActivity(CaterDash.this);
                    runtimePermissionHelper.requestPermissionsIfDenied();

                }

                @Override
                public void noselect() {

                }
            });
        }*/
        DatabaseReference databaseReference =  FirebaseDatabase.getInstance().getReference("notification").child("order");
        databaseReference.orderByChild("cmobile").equalTo(usermobile).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snapshot1:snapshot.getChildren()){
                    FirebaseDatabase.getInstance().getReference("notification").child("order").child(snapshot1.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot2) {

                            Log.e("aa",snapshot2.getValue().toString());
                            NotificationModel model = snapshot2.getValue(NotificationModel.class);
                            if (model.getStatus().equals("new"))
                            {
                                if (!isorderbadgevisible)
                                {
                                    if (model.getIsread().equals("unread"))
                                    {
                                        orderbadge.setVisibility(View.VISIBLE);
                                        isorderbadgevisible = true;
                                    }
                                    else{
                                        orderbadge.setVisibility(View.GONE);

                                    }
                                }

                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference chatnotificationdatabaseReference =  FirebaseDatabase.getInstance().getReference("notification").child("chat");

        chatnotificationdatabaseReference.orderByChild("cmobile").equalTo(usermobile).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                isvisible = false;

                for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                    FirebaseDatabase.getInstance().getReference("notification").child("chat").child(snapshot1.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot2) {

                            NotificationModel model = snapshot2.getValue(NotificationModel.class);
                            showhidebadge(model);




                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        findViewById(R.id.cv_order).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isorderbadgevisible = false;
                orderbadge.setVisibility(View.GONE);

                FirebaseDatabase.getInstance().getReference("notification").child("order").orderByChild("cmobile").equalTo(usermobile).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot snapshot1:snapshot.getChildren()){

                            NotificationModel notificationModel = snapshot1.getValue(NotificationModel.class);
                            DatabaseReference database =  FirebaseDatabase.getInstance().getReference("notification").child("order").child(snapshot1.getKey());
                            Map<String,Object> map = new HashMap<>();
                            map.put("isread","read");
                            if (notificationModel.getStatus().equals("new"))
                                database.updateChildren(map);

                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                Intent intent1 = new Intent(CaterDash.this , CaterOrderList.class);

                intent1.putExtra("usermobile",usermobile);
                intent1.putExtra("username",username);
                intent1.putExtra("userbname",ubname);

                startActivity(intent1);

            }
        });
        findViewById(R.id.cv_menulist).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent2 = new Intent(CaterDash.this , AddMenuList.class);

                intent2.putExtra("usermobile",usermobile);
                intent2.putExtra("username",username);
                intent2.putExtra("userbname",ubname);

                startActivity(intent2);

            }
        });
        findViewById(R.id.cv_carterchatlist).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isvisible = false;

                FirebaseDatabase.getInstance().getReference("notification").child("chat").orderByChild("cmobile").equalTo(usermobile).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot snapshot1:snapshot.getChildren()){

                            NotificationModel notificationModel = snapshot1.getValue(NotificationModel.class);
                            DatabaseReference database =  FirebaseDatabase.getInstance().getReference("notification").child("chat").child(snapshot1.getKey());
                            Map<String,Object> map = new HashMap<>();
                            map.put("isread","read");
                                database.updateChildren(map);

                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                Intent intent2 = new Intent(CaterDash.this , ChatUserList.class);

                intent2.putExtra("usermobile",usermobile);
                intent2.putExtra("from","cmobile");
                intent2.putExtra("username",username);

                startActivity(intent2);

            }
        });
        findViewById(R.id.cv_removemenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(CaterDash.this , RemoveMenuList.class);

                intent3.putExtra("usermobile",usermobile);
                intent3.putExtra("username",username);
                intent3.putExtra("userbname",ubname);
                startActivity(intent3);
            }
        });
        findViewById(R.id.cv_carterlogut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GlobalVariable.Companion.globalyesno_btndialog(CaterDash.this, "Are you sure to logout", new GlobalVariable.OnOkselectforlocation() {
                    @Override
                    public void yesselect() {
                        SharedHelper.getinstance().clearSharedPreferences(CaterDash.this);
                        Intent intent4 = new Intent(getApplicationContext() , MainActivity.class);
                        startActivity(intent4);
                        finish();

                    }

                    @Override
                    public void noselect() {

                    }
                });


            }
        });

        navigationView = (NavigationView) findViewById(R.id.navigation_menu_one);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case  R.id.nav_home:

                        Intent intent = new Intent(CaterDash.this, CaterDash.class);

                        intent.putExtra("usermobile",usermobile);
                        intent.putExtra("username",username);
                        intent.putExtra("userbname",ubname);
                        startActivity(intent);
                        break;

                    case R.id.nav_drawer1:
                        Intent intent1 = new Intent(CaterDash.this , CaterOrderList.class);

                        intent1.putExtra("usermobile",usermobile);
                        intent1.putExtra("username",username);
                        intent1.putExtra("userbname",ubname);

                        startActivity(intent1);
                        break;
                    case R.id.nav_drawer2:
                        Intent intent2 = new Intent(CaterDash.this , AddMenuList.class);

                        intent2.putExtra("usermobile",usermobile);
                        intent2.putExtra("username",username);
                        intent2.putExtra("userbname",ubname);

                        startActivity(intent2);
                        break;
                    case R.id.nav_Policy:
                        Intent intent3 = new Intent(CaterDash.this , RemoveMenuList.class);

                        intent3.putExtra("usermobile",usermobile);
                        intent3.putExtra("username",username);
                        intent3.putExtra("userbname",ubname);
                        startActivity(intent3);
                        break;
                    case R.id.nav_logout:
                        Intent intent4 = new Intent(getApplicationContext() , MainActivity.class);
                        startActivity(intent4);
                        break;


//Paste your privacy policy link

//                    case  R.id.nav_Policy:{
//
//                        Intent browserIntent  = new Intent(Intent.ACTION_VIEW , Uri.parse(""));
//                        startActivity(browserIntent);
//
//                    }
                    //       break;
                    // case  R.id.nav_share:{

                    //    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    //    sharingIntent.setType("text/plain");
                    //    String shareBody =  "http://play.google.com/store/apps/detail?id=" + getPackageName();
                    //   String shareSub = "Try now";
                    //   sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
                    //   sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                    //   startActivity(Intent.createChooser(sharingIntent, "Share using"));

                    // }
                    // break;
                }
                return false;
            }
        });


    }



    public void darkbutton(View view) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==1)
        {
            Toast.makeText(this, "Permissino granted", Toast.LENGTH_SHORT).show();

        }
    }
    boolean isvisible = false;

    private void showhidebadge(NotificationModel model)
    {
        if (!model.getSenderId().equals(usermobile))
        {
            if (!isvisible)
            {
                if (model.getIsread().equals("unread")) {
                    carterchatbadge.setVisibility(View.VISIBLE);
                    isvisible = true;


                } else {
                    carterchatbadge.setVisibility(View.GONE);

                }
            }

        }
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
}