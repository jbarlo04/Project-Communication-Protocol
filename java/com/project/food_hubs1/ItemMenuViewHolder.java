package com.project.food_hubs1;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.squareup.picasso.Picasso;

public class ItemMenuViewHolder extends RecyclerView.ViewHolder {

    ImageView mimgView;
    TextView tvMname,tvMdesc,tvMprice,tvMadd;
    public ItemMenuViewHolder(View itemView) {
        super(itemView);
    }
    public void setitem(FirebaseRecyclerAdapter<Items, ItemMenuViewHolder> activity, String name, String price, String desc, String img){
        mimgView=itemView.findViewById(R.id.mimgView);
        tvMname=itemView.findViewById(R.id.tvMname);
        tvMdesc=itemView.findViewById(R.id.tvMdesc);
        tvMprice=itemView.findViewById(R.id.tvMprice);
        tvMadd=itemView.findViewById(R.id.tvMadd);

        Picasso.get().load(img).into(mimgView);
        tvMname.setText(name);
        tvMdesc.setText(desc);
        tvMprice.setText(price);
    }
}
