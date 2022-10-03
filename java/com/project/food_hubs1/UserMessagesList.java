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
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.project.food_hubs1.chat.ChatMessageModel;
import com.project.food_hubs1.chat.ItemCarterChatViewHolder;
import com.project.food_hubs1.util.NotificationModel;
import com.project.food_hubs1.videocall.VideoCallActivity;
import com.project.food_hubs1.videocall.VideoCallModel;

import java.util.HashMap;
import java.util.Map;

public class UserMessagesList extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    private Button btnToggleDark;

    int count;

    Bundle extras;
    String username,userbname,usermobile,oid,custmobile,otype;
    Button btnplaceOrder;
    ImageView videlCallBtn;
    EditText etText;

    DatabaseReference reff,Dreff,delreff1,delreff2,countref,chatListReference,nameReference;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usermessagescreen);
        extras = getIntent().getExtras();
        MaterialToolbar toolbar = findViewById(R.id.chat_toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        username = extras.getString("username");
        userbname = extras.getString("userbname");
        usermobile = extras.getString("usermobile");
        oid = extras.getString("oid");
        videlCallBtn = toolbar.findViewById(R.id.videoCallimage);

        custmobile = extras.getString("custmobile");
        otype = extras.getString("otype");
        btnplaceOrder=findViewById(R.id.btnSend);
        etText=findViewById(R.id.etText);
        recyclerView=findViewById(R.id.recycleViewChatlist);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        reff = FirebaseDatabase.getInstance().getReference("chatMessages").child(oid);
        chatListReference = FirebaseDatabase.getInstance().getReference("ChatList").child(oid);
        reff.keepSynced(true);

        videlCallBtn.setOnClickListener(v -> {

            DatabaseReference videocall =   FirebaseDatabase.getInstance().getReference("videocall").child(usermobile+custmobile);
            VideoCallModel notificatio=new VideoCallModel();
            notificatio.setCmobile(custmobile);
            notificatio.setMobile(usermobile);
            notificatio.setOid(oid);
            notificatio.setSenderId(extras.getString("from").equals("user")?usermobile:custmobile);

            notificatio.setStatus("calling");
            videocall.setValue(notificatio).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {


                }
            });

        });

        Log.e("usermovilec",usermobile);
        Log.e("custmovil",custmobile);

        FirebaseDatabase.getInstance().getReference("videocall").child(usermobile+custmobile).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                 //   Log.e("data ca",snapshot.getValue().toString());
                if (snapshot.hasChild("status")){

                    VideoCallModel model = snapshot.getValue(VideoCallModel.class);
                    if (model.getStatus().equals("calling"))
                    {
                        Intent intent = new Intent(UserMessagesList.this, VideoCallActivity.class);
                        intent.putExtra("usermobile",usermobile);
                        intent.putExtra("custmobile",custmobile);
                        startActivity(intent);
                    }

                }







            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if (extras.getString("from").equals("user"))
        {
            Log.e("from user",usermobile);
            nameReference =  FirebaseDatabase.getInstance().getReference("Users").child(custmobile);

        }
        else{
            Log.e("from carre",custmobile);

            nameReference =  FirebaseDatabase.getInstance().getReference("Users").child(usermobile);

        }

           nameReference.addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot snapshot) {
                   if (extras.getString("from").equals("user"))
                       toolbar.setTitle(snapshot.child("name").getValue(String.class));
                   else
                       toolbar.setTitle(snapshot.child("name").getValue(String.class));
               }

               @Override
               public void onCancelled(@NonNull DatabaseError error) {

               }
           });
        countref=FirebaseDatabase.getInstance().getReference("chatMessages").child(oid);
        countref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    count=(int)snapshot.getChildrenCount();
                }else{
                    count=0;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        FirebaseRecyclerOptions<ChatMessageModel> options=new FirebaseRecyclerOptions.Builder<ChatMessageModel>()
                .setQuery(reff.orderByChild("usermobile_cmobile").equalTo(usermobile+custmobile),ChatMessageModel.class)
                .build();

        FirebaseRecyclerAdapter<ChatMessageModel,RecyclerView.ViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ChatMessageModel, RecyclerView.ViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull ChatMessageModel model) {
                  if (holder.getItemViewType()==1)
                  {
                      ItemChatViewHolder chatViewHolder = (ItemChatViewHolder)holder;
                      chatViewHolder.setitems(model.getMessage());

                  }
                  else
                  {
                      ItemCarterChatViewHolder chatViewHolder = (ItemCarterChatViewHolder) holder;
                      chatViewHolder.setitems(model.getMessage());
                  }
            }

            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                switch (viewType)
                {
                    case 1: return new ItemChatViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.user_view_chat,parent,false));

                    case 2: return new ItemCarterChatViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.carter_view_chat,parent,false));

                }
                return null;

            }

            @Override
            public int getItemViewType(int position) {

                if (extras.getString("from").equals("user"))
                {
                    if (getItem(position).getSenderId().equals(usermobile))
                        return 1;
                    else
                        return 2;
                }
                else{
                    if (getItem(position).getSenderId().equals(custmobile))
                        return 1;
                    else
                        return 2;

                }


            }
        };

        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
        btnplaceOrder.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference notificationreference =   FirebaseDatabase.getInstance().getReference("notification").child("chat").child(oid);
                NotificationModel notificatio=new NotificationModel();
                notificatio.setCmobile(custmobile);
                notificatio.setMobile(usermobile);
                notificatio.setOid(oid);
                notificatio.setSenderId(extras.getString("from").equals("user")?usermobile:custmobile);

                notificatio.setIsread("unread");
                notificationreference.setValue(notificatio);


                Dreff=FirebaseDatabase.getInstance().getReference("chatMessages").child(oid).push();
                ChatMessageModel chat= new ChatMessageModel();
                chat.setMessage(etText.getText().toString().trim());
                chat.setOid(oid);
                chat.setUsermobile(usermobile);
                chat.setCmobile(custmobile);
                chat.setSenderId(extras.getString("from").equals("user")?usermobile:custmobile);
                chat.setUsermobile_cmobile(usermobile+custmobile);
                chat.setDate(String.valueOf(System.currentTimeMillis()));
                Dreff.setValue(chat).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        etText.setText("");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                linearLayoutManager.smoothScrollToPosition(recyclerView,new RecyclerView.State(),firebaseRecyclerAdapter.getItemCount());

                            }
                        },100);
                    }
                });
                Map<String,Object> map = new HashMap<>();
                map.put("lastmessage",etText.getText().toString());
                map.put("date",String.valueOf(System.currentTimeMillis()));
                chatListReference.updateChildren(map);
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

                            Toast.makeText(UserMessagesList.this, "Updated", Toast.LENGTH_SHORT).show();
                            remove();

                        } else {



                            Toast.makeText(UserMessagesList.this, "failed", Toast.LENGTH_SHORT).show();

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
                    remove2();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    public void remove2(){
        delreff2=FirebaseDatabase.getInstance().getReference("chats").child(oid);
        delreff2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren()){
                    ds.getRef().removeValue();
                    Intent intent12 = new Intent(UserMessagesList.this , UserDash.class);

                    intent12.putExtra("usermobile",usermobile);
                    intent12.putExtra("username",username);
                    intent12.putExtra("userbname",userbname);

                    startActivity(intent12);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }
}