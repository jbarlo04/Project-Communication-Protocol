package com.project.food_hubs1;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.squareup.picasso.Picasso;

public class ItemViewHolder extends RecyclerView.ViewHolder {
    ImageView imgView;
    TextView tvPname,tvPdesc,tvPrice,tvRemove;
    public ItemViewHolder(View itemView) {
        super(itemView);
    }
    public void setitems(FirebaseRecyclerAdapter<Items, ItemViewHolder> activity, String key, String name, String price, String desc, String img){
        imgView=itemView.findViewById(R.id.imgView);
        tvPname=itemView.findViewById(R.id.tvcustomername);
        tvPdesc=itemView.findViewById(R.id.tvPdesc);
        tvPrice=itemView.findViewById(R.id.tvPrice);
        tvRemove=itemView.findViewById(R.id.removeTv);

        Picasso.get().load(img).into(imgView);

        tvPname.setText(key);
        tvPdesc.setText(desc);
        tvPrice.setText(price);
    }

    public void setitem(FirebaseRecyclerAdapter<Items, ItemViewHolder> activity,String name, String price, String desc, String img){
        imgView=itemView.findViewById(R.id.imgView);
        tvPname=itemView.findViewById(R.id.tvPname);
        tvPdesc=itemView.findViewById(R.id.tvPdesc);
        tvPrice=itemView.findViewById(R.id.tvPrice);
        tvRemove=itemView.findViewById(R.id.removeTv);

        Picasso.get().load(img).into(imgView);
        tvPname.setText(name);
        tvPdesc.setText(desc);
        tvPrice.setText(price);
    }
}
