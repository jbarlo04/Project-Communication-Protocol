package com.project.food_hubs1.chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.food_hubs1.ItemOrderViewHolder;
import com.project.food_hubs1.R;
import com.project.food_hubs1.UserMessagesList;
import com.project.food_hubs1.util.ProgressDialog;

import org.jetbrains.annotations.NotNull;

public class ChatUserList extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    private Button btnToggleDark;

    Bundle extras;
    String username,ubname,usermobile;

    DatabaseReference reff,databaseReference;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userchatlist);
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressDialog();
        extras = getIntent().getExtras();
        username=extras.getString("username");
        ubname=extras.getString("userbname");
        usermobile=extras.getString("usermobile");

     //   Toast.makeText(this, username+" "+usermobile+" "+ubname, Toast.LENGTH_SHORT).show();


        recyclerView=findViewById(R.id.recycleViewChatList);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(linearLayoutManager);
        reff = FirebaseDatabase.getInstance().getReference("ChatList");
        FirebaseDatabase.getInstance().getReference("ChatList").orderByChild(extras.getString("from")).equalTo(usermobile).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressDialog.hidedialog();
                if (snapshot.exists())
                {
                    Toast.makeText(ChatUserList.this, "chat list retrieved successfully", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(ChatUserList.this, "no chats found", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        reff.keepSynced(true);
        setUpToolbar();


        FirebaseRecyclerOptions<ChatListModel> options=new FirebaseRecyclerOptions.Builder<ChatListModel>()
                .setQuery(reff.orderByChild(extras.getString("from")).equalTo(usermobile),ChatListModel.class)
                .build();

        FirebaseRecyclerAdapter<ChatListModel, ItemOrderViewHolder> firebaseRecyclerAdapter=
                new FirebaseRecyclerAdapter<ChatListModel, ItemOrderViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder( ItemOrderViewHolder holder, int position, @NonNull @NotNull ChatListModel model) {
                        holder.setChatListItems(this,model);
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(ChatUserList.this, UserMessagesList.class);

                                intent.putExtra("from",extras.getString("from").equals("usermobile")?"user":"carter");
                                intent.putExtra("usermobile",model.usermobile);
                                intent.putExtra("custmobile",model.cmobile);
                                intent.putExtra("username",username);
                                intent.putExtra("userbname",ubname);
                                intent.putExtra("oid",model.getOid());
                                startActivity(intent);
                            }
                        });

                    }

                    @NotNull
                    @Override
                    public ItemOrderViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                        View view= LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.view_userchatlist,parent,false);
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
        Toolbar toolbar = findViewById(R.id.chatlist_toolbar);

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