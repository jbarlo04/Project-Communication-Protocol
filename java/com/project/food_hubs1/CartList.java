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
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.project.food_hubs1.util.NotificationModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CartList extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    private Button btnToggleDark;

    Bundle extras;
    String username,city,usermobile,cmobile,bname;
    Button btnplaceOrder;

    DatabaseReference reff,Dreff,caterreff,idref,delreff1;
    RecyclerView recyclerView;
    StringBuffer stringBuffer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);
        stringBuffer = new StringBuffer();
        btnplaceOrder=findViewById(R.id.btnplaceOrder);
        extras = getIntent().getExtras();
        bname = extras.getString("bname");
        username = extras.getString("username");
        city = extras.getString("city");
        usermobile = extras.getString("usermobile");
        cmobile = extras.getString("cmobile");
        recyclerView=findViewById(R.id.recycleViewCartlist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        reff = FirebaseDatabase.getInstance().getReference("cart").child("open").child(usermobile);
        reff.keepSynced(true);

        btnplaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count= (int)(Math.random()*(10000000-2000+1)+2000);
                placeOrder(Integer.toString(count));
            }
        });



        FirebaseRecyclerOptions<Items> options=new FirebaseRecyclerOptions.Builder<Items>()
                .setQuery(reff,Items.class)
                .build();

        FirebaseRecyclerAdapter<Items,ItemCartViewHolder> firebaseRecyclerAdapter=
                new FirebaseRecyclerAdapter<Items, ItemCartViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull @NotNull ItemCartViewHolder holder, int position, @NonNull @NotNull Items model) {
                        holder.setitem(this,model.getName(),model.getPrice(),model.getDesc(),model.getImg(),model.getStatus(),true);
                        stringBuffer.append(model.getName());
                        stringBuffer.append(",");
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

    public void placeOrder(String id){

        Dreff= FirebaseDatabase.getInstance().getReference("cart").child("placed").child(usermobile).child(id);
        caterreff= FirebaseDatabase.getInstance().getReference("CatererOrders").child("receive").child(cmobile).child(id);
        delreff1=FirebaseDatabase.getInstance().getReference("CustOrders").child("id").child("placed").child(usermobile).child(id);
        idref=FirebaseDatabase.getInstance().getReference("CatererOrders").child("id").child(cmobile);
        Orders orders=new Orders();
        orders.setCmobile(cmobile);
        orders.setMobile(usermobile);
        orders.setOid(id);
        orders.setStatus("new");
        orders.setBname(bname);
        orders.setCustomername(username);
        orders.setCmobile(cmobile);
        orders.setDate(String.valueOf(System.currentTimeMillis()));
        orders.setItemsordered(stringBuffer.substring(0,stringBuffer.length()-1));
        idref.child(id).setValue(orders);
        delreff1.setValue(orders);
        copyRecord(reff,caterreff);
        ArrayList<Items> list = new ArrayList<>();
       // caterreff.updateChildren(list);

        copyRecord(reff,Dreff);

        DatabaseReference notificationreference =   FirebaseDatabase.getInstance().getReference("notification").child("order").child(id);
        NotificationModel notificatio=new NotificationModel();
        notificatio.setCmobile(cmobile);
        notificatio.setMobile(usermobile);
        notificatio.setOid(id);
        notificatio.setIsread("unread");
        notificatio.setStatus("new");
        notificationreference.setValue(notificatio);

    }



    private void copyRecord(Query fromPath, final DatabaseReference toPath){
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                toPath.setValue(dataSnapshot.getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isComplete()) {


                            Toast.makeText(CartList.this, "Your order has been placed sucessfully", Toast.LENGTH_SHORT).show();
                            remove();

                        } else {



                            Toast.makeText(CartList.this, "Internal error", Toast.LENGTH_SHORT).show();

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
        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren()){
                    ds.getRef().removeValue();
                    Intent intent10= new Intent(getApplicationContext(),UserDash.class);

                    intent10.putExtra("usermobile",usermobile);
                    intent10.putExtra("username",username);
                    intent10.putExtra("city",city);
                    startActivity(intent10);
                    finishAffinity();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }
}