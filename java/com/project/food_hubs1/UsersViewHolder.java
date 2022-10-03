package com.project.food_hubs1;

import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class UsersViewHolder extends RecyclerView.ViewHolder {
    TextView tvCname,tvCloc,tvcPhone;
    Button btn;
    RatingBar ratingBar;
    float total = 0;
    float tt = 0;
    public UsersViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
    }
    public void setitem(FirebaseRecyclerAdapter<DataSnapshot, UsersViewHolder> activity, String name, String bname, String email, String mobile, String yop, String password, String uType, String city, DataSnapshot rating){
        tvCname=itemView.findViewById(R.id.tvCname);
        tvCloc=itemView.findViewById(R.id.tvCloc);
        tvcPhone=itemView.findViewById(R.id.tvcPhone);
        btn=itemView.findViewById(R.id.btnView_dir);
        ratingBar = itemView.findViewById(R.id.cartertating);
            tvCname.setText(bname);
            tvCloc.setText(city);
            tvcPhone.setText(mobile);


        FirebaseDatabase.getInstance().getReference("Users").child(mobile).child("Ratings").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                total =0;
                tt = 0;
                for (DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    Ratings ratings = dataSnapshot.getValue(Ratings.class);
                    total  += ratings.rating;
                    tt +=1;

                }
                float average = total/tt;
                ratingBar.setRating(average);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }
}
