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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.project.food_hubs1.chat.ChatListModel;
import com.project.food_hubs1.util.GlobalVariable;
import com.project.food_hubs1.util.ProgressDialog;

import org.jetbrains.annotations.NotNull;

public class UserViewOrderMenu extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    private Button btnToggleDark;

    Bundle extras;
    String username,userbname,usermobile,oid,custmobile,otype;
    Button btnplaceOrder;

    DatabaseReference reff,Dreff,delreff1,delreff2,chatListReference;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    String bname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_order_menu);
        progressDialog =  new ProgressDialog(this);
        progressDialog.setProgressDialog();
       MaterialToolbar toolbar =  findViewById(R.id.orderviewmenu_toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        extras = getIntent().getExtras();
        username = extras.getString("username");
        userbname = extras.getString("userbname");
        usermobile = extras.getString("usermobile");
        oid = extras.getString("oid");
        custmobile = extras.getString("custmobile");
        otype = extras.getString("otype");
        btnplaceOrder=findViewById(R.id.btnplaceChat);
        recyclerView=findViewById(R.id.recycleViewCartlist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        reff = FirebaseDatabase.getInstance().getReference("cart").child(otype).child(usermobile).child(oid);
        reff.keepSynced(true);

        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressDialog.hidedialog();
                if (snapshot.exists()){

                    GlobalVariable.Companion.showmessage(recyclerView,"Data fetched successfully");

                }
                else
                {
                    GlobalVariable.Companion.showmessage(recyclerView,"No orders found");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        if(otype.equals("placed")){
            btnplaceOrder.setText("CHAT");
        }else{
            btnplaceOrder.setText("Review");
        }

        btnplaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(otype.equals("placed")){

                    chatListReference = FirebaseDatabase.getInstance().getReference("ChatList");
                    ChatListModel chatListModel = new ChatListModel();
                    chatListModel.setOid(oid);
                    chatListModel.setCmobile(custmobile);
                    chatListModel.setUsermobile(usermobile);
                    chatListModel.setLastmessage("");
                    chatListModel.setDate(String.valueOf(System.currentTimeMillis()));
                    chatListReference.child(oid).setValue(chatListModel, (error, ref) -> {

                        Intent intent = new Intent(UserViewOrderMenu.this, UserMessagesList.class);

                        intent.putExtra("usermobile",usermobile);
                        intent.putExtra("custmobile",custmobile);
                        intent.putExtra("username",username);
                        intent.putExtra("otype",otype);
                        intent.putExtra("oid",oid);
                        intent.putExtra("from","user");
                        startActivity(intent);

                    });




                }else{

                    Intent intent = new Intent(UserViewOrderMenu.this, UserRating.class);

                    intent.putExtra("usermobile",usermobile);
                    intent.putExtra("bmobile",custmobile);
                    intent.putExtra("username",username);
                    intent.putExtra("otype",otype);
                    intent.putExtra("oid",oid);
                    startActivity(intent);
                }
            }
        });



        FirebaseRecyclerOptions<Items> options=new FirebaseRecyclerOptions.Builder<Items>()
                .setQuery(reff,Items.class)
                .build();

        FirebaseRecyclerAdapter<Items,ItemCartViewHolder> firebaseRecyclerAdapter=
                new FirebaseRecyclerAdapter<Items, ItemCartViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull @NotNull ItemCartViewHolder holder, int position, @NonNull @NotNull Items model) {
                        holder.setitemuser(this,model.getName(),model.getPrice(),model.getDesc(),model.getImg(),model.getStatus());
                    }

                    @NotNull
                    @Override
                    public ItemCartViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                        View view= LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.view_user_order_menu_list,parent,false);
                        return new ItemCartViewHolder(view);
                    }
                };
        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);

    }

    public void darkbutton(View view) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

    }

    public void setUpToolbar() {
        drawerLayout = findViewById(R.id.drawerLayout2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.black));
        actionBarDrawerToggle.syncState();

    }

    public void placeOrder(){
        Dreff = FirebaseDatabase.getInstance().getReference("cart").child("delivered").child(custmobile).child(oid);
        delreff2=FirebaseDatabase.getInstance().getReference("orders").child("id").child(usermobile).child(oid);
        delreff1=FirebaseDatabase.getInstance().getReference("CustOrders").child("id").child("delivered").child(custmobile).child(oid);
        copyRecord(delreff2,delreff1);
        copyRecord(reff,Dreff);
    }

    private void copyRecord(Query fromPath, final DatabaseReference toPath){
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                toPath.setValue(dataSnapshot.getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isComplete()) {

                            Toast.makeText(UserViewOrderMenu.this, "Updated", Toast.LENGTH_SHORT).show();
                            remove();

                        } else {



                            Toast.makeText(UserViewOrderMenu.this, "failed", Toast.LENGTH_SHORT).show();

                        }

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        fromPath.addListenerForSingleValueEvent(valueEventListener);

    }
    public void remove(){

        delreff1=FirebaseDatabase.getInstance().getReference("CustOrders").child("id").child("placed").child(custmobile).child(oid);
        delreff1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren()){
                    ds.getRef().removeValue();
                    remove2();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    public void remove2(){
        delreff2=FirebaseDatabase.getInstance().getReference("orders").child("id").child(usermobile).child(oid);
        delreff2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren()){
                    ds.getRef().removeValue();
                    remove3();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    public void remove3() {
        delreff2=FirebaseDatabase.getInstance().getReference("orders").child("id").child(usermobile).child(oid);
        delreff2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren()){
                    ds.getRef().removeValue();
                    Intent intent12 = new Intent(UserViewOrderMenu.this , UserDash.class);

                    intent12.putExtra("usermobile",usermobile);
                    intent12.putExtra("username",username);
                    intent12.putExtra("userbname",userbname);

                    startActivity(intent12);
                    remove3();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }
}