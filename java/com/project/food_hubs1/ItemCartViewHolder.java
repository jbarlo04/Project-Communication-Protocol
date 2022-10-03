package com.project.food_hubs1;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.squareup.picasso.Picasso;

public class ItemCartViewHolder extends RecyclerView.ViewHolder {

    ImageView mimgView;
    TextView tvMname,tvMdesc,tvMprice,tvorderStatus;
    public ItemCartViewHolder(View itemView) {
        super(itemView);
    }
    public void setitem(FirebaseRecyclerAdapter<Items, ItemCartViewHolder> activity, String name, String price, String desc, String img,String status,boolean fromcart){
        mimgView=itemView.findViewById(R.id.ctimgView);
        tvMname=itemView.findViewById(R.id.tvctname);
        tvMdesc=itemView.findViewById(R.id.tvctdesc);
        tvMprice=itemView.findViewById(R.id.tvctprice);
        tvorderStatus=itemView.findViewById(R.id.tvcartvieworderstatus);

        if (fromcart)
        {
            tvorderStatus.setVisibility(View.GONE);
        }
        else
        {
            tvorderStatus.setVisibility(View.VISIBLE);
        }

        Picasso.get().load(img).into(mimgView);
        tvMname.setText(name);
        tvMdesc.setText(desc);
        tvMprice.setText(price);
        tvMprice.setText(price);

        tvorderStatus.setText("Status : "+status);
    }

    public void setitemuser(FirebaseRecyclerAdapter<Items, ItemCartViewHolder> activity, String name, String price, String desc, String img,String status){
        mimgView=itemView.findViewById(R.id.usimgView);
        tvMname=itemView.findViewById(R.id.tvusname);
        tvMdesc=itemView.findViewById(R.id.tvusdesc);
        tvMprice=itemView.findViewById(R.id.tvusprice);
       TextView tvorderStatus=itemView.findViewById(R.id.tvusrmenuorderstatus);

        Picasso.get().load(img).into(mimgView);
        tvMname.setText(name);
        tvMdesc.setText(desc);
        tvMprice.setText(price);
        tvorderStatus.setText("Status : "+status);

    }
}
