package com.project.food_hubs1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class RemoveMenuList extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    private Button btnToggleDark;

    Bundle extras;
    String username,ubname,usermobile;

    DatabaseReference reff,Dreff;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_menu_list);
        extras = getIntent().getExtras();
        username=extras.getString("username");
        ubname=extras.getString("ubname");
        usermobile=extras.getString("usermobile");
        MaterialToolbar toolbar = findViewById(R.id.removemenu_toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        recyclerView=findViewById(R.id.recycleViewRem);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        reff = FirebaseDatabase.getInstance().getReference("menus").child(usermobile);
        reff.keepSynced(true);



        FirebaseRecyclerOptions<Items> options=new FirebaseRecyclerOptions.Builder<Items>()
                .setQuery(reff,Items.class)
                .build();

        FirebaseRecyclerAdapter<Items,ItemViewHolder> firebaseRecyclerAdapter=
                new FirebaseRecyclerAdapter<Items, ItemViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull @NotNull ItemViewHolder holder, int position, @NonNull @NotNull Items model) {
                        holder.setitem(this,model.getName(),model.getPrice(),model.getDesc(),model.getImg());

                        holder.tvRemove.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Dreff = FirebaseDatabase.getInstance().getReference("menus").child(usermobile).child(model.getName());
                                Dreff.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot snapshot) {
                                        for(DataSnapshot ds:snapshot.getChildren()){
                                            ds.getRef().removeValue();
                                        }

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError error) {

                                    }
                                });
                            }
                        });
                    }

                    @NotNull
                    @Override
                    public ItemViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                        View view= LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.remove_item,parent,false);
                        return new ItemViewHolder(view);
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
}