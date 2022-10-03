package com.project.food_hubs1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class ViewMenu extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    private Button btnToggleDark;

    Bundle extras;
    String username,city,usermobile,cmobile,bname;

    DatabaseReference reff,Dreff;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_menu);
        extras = getIntent().getExtras();
         username = extras.getString("username");
         bname = extras.getString("bname");
        city = extras.getString("city");
        usermobile = extras.getString("usermobile");
        cmobile = extras.getString("bmobile");
        recyclerView=findViewById(R.id.recycleViewMlist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        reff = FirebaseDatabase.getInstance().getReference("menus").child(cmobile);
        reff.keepSynced(true);



        FirebaseRecyclerOptions<Items> options=new FirebaseRecyclerOptions.Builder<Items>()
                .setQuery(reff,Items.class)
                .build();

        FirebaseRecyclerAdapter<Items,ItemMenuViewHolder> firebaseRecyclerAdapter=
                new FirebaseRecyclerAdapter<Items, ItemMenuViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull @NotNull ItemMenuViewHolder holder, int position, @NonNull @NotNull Items model) {
                        holder.setitem(this,model.getName(),model.getPrice(),model.getDesc(),model.getImg());
                        holder.tvMadd.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Dreff= FirebaseDatabase.getInstance().getReference("cart").child("open").child(usermobile);

                                Items items=new Items();
                                items.setMobile(usermobile);
                                items.setCmobile(cmobile);
                                items.setBname(bname);
                                items.setName(model.getName());
                                items.setImg(model.getImg());
                                items.setDesc(model.getDesc());
                                items.setPrice(model.getPrice());
                                items.setBname(bname);
                                items.setStatus("new");

                                Dreff.child(model.getName()).setValue(items);
                                Toast.makeText(getApplicationContext(), "Items Inserted to Cart Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent10= new Intent(getApplicationContext(),CartList.class);

                                intent10.putExtra("usermobile",usermobile);
                                intent10.putExtra("bname",bname);
                                intent10.putExtra("username",username);
                                intent10.putExtra("cmobile",cmobile);
                                intent10.putExtra("city",city);
                                startActivity(intent10);
                            }
                        });
                    }

                    @NotNull
                    @Override
                    public ItemMenuViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                        View view= LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.view_menu_list,parent,false);
                        return new ItemMenuViewHolder(view);
                    }
                };
        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);

    }

    public void darkbutton(View view) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

    }

    public void setUpToolbar() {
        drawerLayout = findViewById(R.id.drawerLayout1);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.black));
        actionBarDrawerToggle.syncState();

    }
}