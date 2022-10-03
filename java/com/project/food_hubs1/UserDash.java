package com.project.food_hubs1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.food_hubs1.chat.ChatUserList;
import com.project.food_hubs1.util.GlobalVariable;
import com.project.food_hubs1.util.NotificationModel;
import com.project.food_hubs1.util.SharedHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class UserDash extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    private Button btnToggleDark;
    boolean isorderbadgevisible = false;
    boolean isdeliveredbadgevisible = false;

    Bundle extras;
    String username,city,usermobile;
    CardView carterlist,orderlist,deliverlist,logout,chatlist;
    ImageView placeorderbadge,deliverorderbadge,chatbadge;
    ArrayList<NotificationModel> chatnotificationmodel= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dash);
        chatlist = findViewById(R.id.cv_userchatlist);
        carterlist = findViewById(R.id.cv_carterlist);
        orderlist = findViewById(R.id.cv_orderlist);
        deliverlist = findViewById(R.id.cv_deliverlist);
        placeorderbadge = findViewById(R.id.placeorderbadge);
        chatbadge = findViewById(R.id.userchat_badge);
        deliverorderbadge = findViewById(R.id.deliverorderbadge);
        logout = findViewById(R.id.cv_logut);
        extras = getIntent().getExtras();
        username=extras.getString("username");
        city=extras.getString("city");
        usermobile=extras.getString("usermobile");


        //  setUpToolbar();

      DatabaseReference databaseReference =  FirebaseDatabase.getInstance().getReference("notification").child("order");
      DatabaseReference chatnotificationdatabaseReference =  FirebaseDatabase.getInstance().getReference("notification").child("chat");
      databaseReference.orderByChild("mobile").equalTo(usermobile).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snapshot1:snapshot.getChildren()){
                    FirebaseDatabase.getInstance().getReference("notification").child("order").child(snapshot1.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot2) {

                            NotificationModel model = snapshot2.getValue(NotificationModel.class);
                            if (model.getStatus().equals("Delivered"))
                            {
                                if (!isdeliveredbadgevisible)
                                {
                                    if (model.getIsread().equals("unread"))
                                    {
                                        deliverorderbadge.setVisibility(View.VISIBLE);
                                        isdeliveredbadgevisible = true;
                                    }
                                    else{
                                        deliverorderbadge.setVisibility(View.GONE);

                                    }
                                }

                            }
                            else{
                                deliverorderbadge.setVisibility(View.GONE);

                            }
                            if (model.getStatus().equals("new"))
                            {
                                placeorderbadge.setVisibility(View.GONE);

                            }
                            else
                            {
                                if (!model.getStatus().equals("Delivered"))
                                {
                                    if (!isorderbadgevisible)
                                    {
                                        if (model.getIsread().equals("unread"))
                                        {
                                            placeorderbadge.setVisibility(View.VISIBLE);
                                            isorderbadgevisible = true;
                                        }
                                        else{
                                            placeorderbadge.setVisibility(View.GONE);

                                        }
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
      chatnotificationdatabaseReference.orderByChild("mobile").equalTo(usermobile).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                isvisible = false;

                for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                    FirebaseDatabase.getInstance().getReference("notification").child("chat").child(snapshot1.getKey()).addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot2) {

                            NotificationModel model = snapshot2.getValue(NotificationModel.class);

                            showhidechatbadge(model);



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



        chatlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isvisible = false;

                FirebaseDatabase.getInstance().getReference("notification").child("chat").orderByChild("mobile").equalTo(usermobile).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot snapshot1:snapshot.getChildren()){

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


                Intent intent6 = new Intent(getApplicationContext(), ChatUserList.class);

                intent6.putExtra("usermobile",usermobile);
                intent6.putExtra("username",username);
                intent6.putExtra("from","usermobile");
                intent6.putExtra("city",city);
                startActivity(intent6);

            }
        });
        carterlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent6 = new Intent(getApplicationContext(), CaterList.class);

                intent6.putExtra("usermobile",usermobile);
                intent6.putExtra("username",username);
                intent6.putExtra("city",city);
                startActivity(intent6);

            }
        });
        orderlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isorderbadgevisible = false;
                isdeliveredbadgevisible = false;

                FirebaseDatabase.getInstance().getReference("notification").child("order").orderByChild("mobile").equalTo(usermobile).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot snapshot1:snapshot.getChildren()){

                            NotificationModel notificationModel = snapshot1.getValue(NotificationModel.class);
                           DatabaseReference database =  FirebaseDatabase.getInstance().getReference("notification").child("order").child(snapshot1.getKey());
                           Map<String,Object> map = new HashMap<>();
                           map.put("isread","read");
                           if (!notificationModel.getStatus().equals("new"))
                           database.updateChildren(map);

                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


                Intent intent1 = new Intent(getApplicationContext() , UserOrderList.class);

                intent1.putExtra("usermobile",usermobile);
                intent1.putExtra("username",username);
                intent1.putExtra("city",city);
                intent1.putExtra("otype","placed");


                startActivity(intent1);

            }
        });
        deliverlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent15 = new Intent(getApplicationContext() , UserOrderList.class);

                intent15.putExtra("usermobile",usermobile);
                intent15.putExtra("username",username);
                intent15.putExtra("city",city);
                intent15.putExtra("otype","delivered");
                startActivity(intent15);

            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   GlobalVariable.Companion.globalyesno_btndialog(UserDash.this, "Are you sure to logout", new GlobalVariable.OnOkselectforlocation() {
                       @Override
                       public void yesselect() {
                           SharedHelper.getinstance().clearSharedPreferences(UserDash.this);
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

        navigationView = (NavigationView) findViewById(R.id.navigation_menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case  R.id.Userhome:

                        Intent intent = new Intent(getApplicationContext(), UserDash.class);

                        intent.putExtra("usermobile",usermobile);
                        intent.putExtra("username",username);
                        intent.putExtra("city",city);
                        startActivity(intent);
                        break;
                    case  R.id.cater_list:

                        Intent intent6 = new Intent(getApplicationContext(), CaterList.class);

                        intent6.putExtra("usermobile",usermobile);
                        intent6.putExtra("username",username);
                        intent6.putExtra("city",city);
                        startActivity(intent6);
                        break;
                    case R.id.orderList:
                        Intent intent1 = new Intent(getApplicationContext() , UserOrderList.class);

                        intent1.putExtra("usermobile",usermobile);
                        intent1.putExtra("username",username);
                        intent1.putExtra("city",city);
                        intent1.putExtra("otype","placed");


                        startActivity(intent1);
                        break;
                    case R.id.delivered_orderList:
                        Intent intent15 = new Intent(getApplicationContext() , UserOrderList.class);

                        intent15.putExtra("usermobile",usermobile);
                        intent15.putExtra("username",username);
                        intent15.putExtra("city",city);
                        intent15.putExtra("otype","delivered");


                        startActivity(intent15);
                        break;

                    case R.id.logout:
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

    boolean isvisible = false;

    private void showhidechatbadge(NotificationModel model){



            if (!model.getSenderId().equals(usermobile))
            {
                if (!isvisible) {
                    if (model.getIsread().equals("unread")) {
                        chatbadge.setVisibility(View.VISIBLE);
                        isvisible= true;



                    } else {
                        chatbadge.setVisibility(View.GONE);

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