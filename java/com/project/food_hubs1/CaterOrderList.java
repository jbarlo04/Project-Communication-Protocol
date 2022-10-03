package com.project.food_hubs1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.food_hubs1.util.ProgressDialog;

import org.jetbrains.annotations.NotNull;

public class CaterOrderList extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    private Button btnToggleDark;

    Bundle extras;
    String username,ubname,usermobile;

    DatabaseReference reff,Dreff;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cater_order_list);
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressDialog();
        extras = getIntent().getExtras();
        username=extras.getString("username");
        ubname=extras.getString("userbname");
        usermobile=extras.getString("usermobile");

     //   Toast.makeText(this, username+" "+usermobile+" "+ubname, Toast.LENGTH_SHORT).show();
        setUpToolbar();

        navigationView = (NavigationView) findViewById(R.id.navigation_menu_one);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case  R.id.nav_home:

                        Intent intent = new Intent(CaterOrderList.this, CaterDash.class);

                        intent.putExtra("usermobile",usermobile);
                        intent.putExtra("username",username);
                        intent.putExtra("userbname",ubname);
                        startActivity(intent);
                        break;

                    case R.id.nav_drawer1:
                        Intent intent1 = new Intent(CaterOrderList.this , CaterOrderList.class);

                        intent1.putExtra("usermobile",usermobile);
                        intent1.putExtra("username",username);
                        intent1.putExtra("userbname",ubname);

                        startActivity(intent1);
                        break;
                    case R.id.nav_drawer2:
                        Intent intent2 = new Intent(CaterOrderList.this , AddMenuList.class);

                        intent2.putExtra("usermobile",usermobile);
                        intent2.putExtra("username",username);
                        intent2.putExtra("userbname",ubname);

                        startActivity(intent2);
                        break;
                    case R.id.nav_Policy:
                        Intent intent3 = new Intent(CaterOrderList.this , RemoveMenuList.class);

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

        recyclerView=findViewById(R.id.recycleViewOrder);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        reff = FirebaseDatabase.getInstance().getReference("CatererOrders").child("id").child(usermobile);
        reff.keepSynced(true);

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                progressDialog.hidedialog();

                if (dataSnapshot.exists()){
                    Toast.makeText(CaterOrderList.this, "Orders retrieved successfully", Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(CaterOrderList.this, "No orders found", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        FirebaseRecyclerOptions<Orders> options=new FirebaseRecyclerOptions.Builder<Orders>()
                .setQuery(reff,Orders.class)
                .build();

        FirebaseRecyclerAdapter<Orders,ItemOrderViewHolder> firebaseRecyclerAdapter=
                new FirebaseRecyclerAdapter<Orders, ItemOrderViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder( ItemOrderViewHolder holder, int position, @NonNull @NotNull Orders model) {
                        holder.setitem(this,model);
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(CaterOrderList.this, CaterViewOrderMenu.class);

                                intent.putExtra("usermobile",usermobile);
                                intent.putExtra("username",username);
                                intent.putExtra("userbname",ubname);
                                intent.putExtra("oid",model.getOid());
                                intent.putExtra("custmobile",model.getMobile());
                                startActivity(intent);
                            }
                        });

                    }

                    @NotNull
                    @Override
                    public ItemOrderViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                        View view= LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.view_order_list,parent,false);
                        return new ItemOrderViewHolder(view);
                    }
                };
        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public void darkbutton(View view) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

    }

    public void setUpToolbar() {
        drawerLayout = findViewById(R.id.drawerLayout6);
        Toolbar toolbar = findViewById(R.id.orderlisttoolbar);
       // setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        /*actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.black));
        actionBarDrawerToggle.syncState();*/

    }

}