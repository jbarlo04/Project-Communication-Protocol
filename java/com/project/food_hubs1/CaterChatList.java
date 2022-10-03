package com.project.food_hubs1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import android.view.View;
import android.widget.Button;

import com.google.android.material.navigation.NavigationView;
public class CaterChatList extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    private Button btnToggleDark;

    Bundle extras;
    String username,ubname,usermobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cater_chat_list);
        extras = getIntent().getExtras();
        username=extras.getString("username");
        ubname=extras.getString("ubname");
        usermobile=extras.getString("usermobile");
        setUpToolbar();

        navigationView = (NavigationView) findViewById(R.id.navigation_menu_one);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case  R.id.nav_home:

                        Intent intent = new Intent(CaterChatList.this, CaterDash.class);

                        intent.putExtra("usermobile",usermobile);
                        intent.putExtra("username",username);
                        intent.putExtra("userbname",ubname);
                        startActivity(intent);
                        break;

                    case R.id.nav_drawer1:
                        Intent intent1 = new Intent(CaterChatList.this , CaterOrderList.class);

                        intent1.putExtra("usermobile",usermobile);
                        intent1.putExtra("username",username);
                        intent1.putExtra("userbname",ubname);

                        startActivity(intent1);
                        break;
                    case R.id.nav_drawer2:
                        Intent intent2 = new Intent(CaterChatList.this , AddMenuList.class);

                        intent2.putExtra("usermobile",usermobile);
                        intent2.putExtra("username",username);
                        intent2.putExtra("userbname",ubname);

                        startActivity(intent2);
                        break;
                    case R.id.nav_Policy:
                        Intent intent3 = new Intent(CaterChatList.this , RemoveMenuList.class);

                        intent3.putExtra("usermobile",usermobile);
                        intent3.putExtra("username",username);
                        intent3.putExtra("userbname",ubname);
                        startActivity(intent3);
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