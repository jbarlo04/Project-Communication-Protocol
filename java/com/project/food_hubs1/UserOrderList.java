package com.project.food_hubs1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.food_hubs1.util.GlobalVariable;
import com.project.food_hubs1.util.ProgressDialog;

import org.jetbrains.annotations.NotNull;

public class UserOrderList extends AppCompatActivity {

    DrawerLayout drawerLayouts;
    ActionBarDrawerToggle actionBarDrawerToggles;
    NavigationView navigationViews;
    private Button btnToggleDark;

    Bundle extras;
    String username, city, usermobile,otype;

    DatabaseReference reff,Dreff;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_order_list);
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressDialog();
        extras = getIntent().getExtras();
       MaterialToolbar toolbar =  findViewById(R.id.userorder_toolbar);
       toolbar.setNavigationOnClickListener(v -> onBackPressed());
        username = extras.getString("username");
        city = extras.getString("city");
        usermobile = extras.getString("usermobile");
        otype = extras.getString("otype");
        recyclerView=findViewById(R.id.recycleViewOrder_User);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        reff = FirebaseDatabase.getInstance().getReference("CustOrders").child("id").child(otype).child(usermobile);
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

        FirebaseRecyclerOptions<Orders> options=new FirebaseRecyclerOptions.Builder<Orders>()
                .setQuery(reff,Orders.class)
                .build();

        FirebaseRecyclerAdapter<Orders,ItemOrderViewHolder> firebaseRecyclerAdapter=
                new FirebaseRecyclerAdapter<Orders, ItemOrderViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder( ItemOrderViewHolder holder, int position, @NonNull @NotNull Orders model) {
                        holder.setitems(this,model);
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(UserOrderList.this, UserViewOrderMenu.class);

                                intent.putExtra("usermobile",usermobile);
                                intent.putExtra("username",username);
                                intent.putExtra("otype",otype);
                                intent.putExtra("oid",model.getOid());
                                intent.putExtra("custmobile",model.getCmobile());
                                startActivity(intent);
                            }
                        });

                    }

                    @NotNull
                    @Override
                    public ItemOrderViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                        View view= LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.view_order_list_user,parent,false);
                        return new ItemOrderViewHolder(view);
                    }
                };
        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
}