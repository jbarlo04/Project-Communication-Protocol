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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.project.food_hubs1.chat.ChatListModel;
import com.project.food_hubs1.util.NotificationModel;
import com.project.food_hubs1.util.ProgressDialog;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class CaterViewOrderMenu extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    private Button btnToggleDark;

    Bundle extras;
    String username,userbname,usermobile,oid,custmobile;
    Button btnplaceOrder,btnSendChat;

    DatabaseReference reff,Dreff,delreff1,delreff2,chatListReference;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    Items model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cater_view_order_menu);
        extras = getIntent().getExtras();
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressDialog();
        username = extras.getString("username");
        userbname = extras.getString("userbname");
        usermobile = extras.getString("usermobile");
        oid = extras.getString("oid");
        custmobile = extras.getString("custmobile");
        btnplaceOrder=findViewById(R.id.btnplaceDelivery);
        btnSendChat=findViewById(R.id.btnSendChat);
        recyclerView=findViewById(R.id.recycleViewCartlist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        reff = FirebaseDatabase.getInstance().getReference("CatererOrders").child("receive").child(usermobile).child(oid);
        reff.keepSynced(true);
        btnplaceOrder.setVisibility(View.GONE);
        btnSendChat.setVisibility(View.GONE);

        btnplaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (btnplaceOrder.getText().equals("Accept"))
                {
                    changestatus("Accepted");

                }
                 if (btnplaceOrder.getText().toString().equals("Order Ready"))
                {
                    changestatus("Ready");


                }
                 if (btnplaceOrder.getText().toString().equals("Order Completed"))
                {
                    changestatus("Delivered");
                   placeOrder();


                }

            }
        });
        btnSendChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (btnSendChat.getText().equals("Reject"))
                {
                    changestatus("Rejected");
                }
                else
                {

                    chatListReference = FirebaseDatabase.getInstance().getReference("ChatList");
                    ChatListModel chatListModel = new ChatListModel();
                    chatListModel.setOid(oid);
                    chatListModel.setCmobile(usermobile);
                    chatListModel.setUsermobile(custmobile);
                    chatListModel.setLastmessage("");
                    chatListModel.setDate(String.valueOf(System.currentTimeMillis()));
                    chatListReference.child(oid).setValue(chatListModel, (error, ref) -> {

                        Intent intent = new Intent(CaterViewOrderMenu.this, UserMessagesList.class);

                        intent.putExtra("usermobile",custmobile);
                        intent.putExtra("custmobile",usermobile);
                        intent.putExtra("username",username);
                        intent.putExtra("otype","placed");
                        intent.putExtra("oid",oid);
                        intent.putExtra("from","cater");
                        startActivity(intent);

                    });

                }

            }
        });



        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressDialog.hidedialog();
                btnplaceOrder.setVisibility(View.VISIBLE);
                btnSendChat.setVisibility(View.VISIBLE);
                for (DataSnapshot data :snapshot.getChildren())
                {
                    Log.e("aa",snapshot.getKey());

                    model =  data.getValue(Items.class);

                    if (model.status.equals("new"))
                    {
                        btnplaceOrder.setText("Accept");
                        btnSendChat.setText("Reject");
                    }
                    else if (model.status.equals("Rejected"))
                    {
                        btnSendChat.setText("Chat");
                        btnplaceOrder.setVisibility(View.GONE);

                    }else if (model.status.equals("Accepted"))
                    {
                        btnSendChat.setText("Chat");
                        btnplaceOrder.setText("Order Ready");

                    }
                    else if (model.status.equals("Ready"))
                    {
                        btnSendChat.setText("Chat");
                        btnplaceOrder.setText("Order Completed");
                    }
                    else if (model.status.equals("Delivered")){
                        btnSendChat.setText("Chat");
                        btnplaceOrder.setText("Completed");
                        btnplaceOrder.setVisibility(View.GONE);

                    }

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseRecyclerOptions<Items> options=new FirebaseRecyclerOptions.Builder<Items>()
                .setQuery(reff,Items.class)
                .build();

        FirebaseRecyclerAdapter<Items,ItemCartViewHolder> firebaseRecyclerAdapter=
                new FirebaseRecyclerAdapter<Items, ItemCartViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull @NotNull ItemCartViewHolder holder, int position, @NonNull @NotNull Items model) {
                        holder.setitem(this,model.getName(),model.getPrice(),model.getDesc(),model.getImg(),model.getStatus(),false);
                    }

                    @NotNull
                    @Override
                    public ItemCartViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                        View view= LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.view_cart_list,parent,false);
                        return new ItemCartViewHolder(view);
                    }
                };
        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);

    }

    void changestatus(String status)
    {


        DatabaseReference notificationreference =   FirebaseDatabase.getInstance().getReference("notification").child("order").child(oid);
        NotificationModel notificatio=new NotificationModel();
        notificatio.setCmobile(usermobile);
        notificatio.setMobile(custmobile);
        notificatio.setOid(oid);
        notificatio.setIsread("unread");
        notificatio.setStatus(status);
        notificationreference.setValue(notificatio);

        DatabaseReference placeorderref =   FirebaseDatabase.getInstance().getReference("CustOrders").child("id").child("placed").child(custmobile).child(oid);
        Map<String, Object> map = new HashMap<>();
        map.put("status",status);
        placeorderref.updateChildren(map);

        FirebaseDatabase.getInstance().getReference("CatererOrders").child("id").child(usermobile).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    FirebaseDatabase.getInstance().getReference("CatererOrders").child("id").child(usermobile).child(dataSnapshot.getKey()).child("status").setValue(status);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    Log.e("keys",snapshot1.getKey());
                    Items model =  snapshot1.getValue(Items.class);
                    reff.child(snapshot1.getKey()).child("status").setValue(status);

                    //   ref.child("status").set

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
        delreff2=FirebaseDatabase.getInstance().getReference("CatererOrders").child("id").child(usermobile).child(oid);
        delreff1=FirebaseDatabase.getInstance().getReference("CustOrders").child("id").child("delivered").child(custmobile).child(oid);
        Map<String,Object> map = new HashMap<>();
        map.put("status","Delivered");
        delreff1.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                copyRecord(delreff2,delreff1);
                copyRecord(reff,Dreff);
            }
        });

    }

    private void copyRecord(Query fromPath, final DatabaseReference toPath){
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                toPath.setValue(dataSnapshot.getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isComplete()) {

                            remove();
                            Intent intent12 = new Intent(CaterViewOrderMenu.this , CaterDash.class);

                            intent12.putExtra("usermobile",usermobile);
                            intent12.putExtra("username",username);
                            intent12.putExtra("userbname",userbname);

                            startActivity(intent12);
                            finishAffinity();

                        } else {



                            Toast.makeText(CaterViewOrderMenu.this, "failed", Toast.LENGTH_SHORT).show();

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
        delreff1=FirebaseDatabase.getInstance().getReference("cart").child("placed").child(custmobile).child(oid);
        delreff1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren()){
                    ds.getRef().removeValue();


                    //remove2();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        FirebaseDatabase.getInstance().getReference("CustOrders").child("id").child("placed").child(custmobile).child(oid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren()){
                    ds.getRef().removeValue();


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void remove2(){
        delreff2=FirebaseDatabase.getInstance().getReference("CatererOrders").child("id").child(usermobile).child(oid);
        delreff2.addListenerForSingleValueEvent(new ValueEventListener() {
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
}