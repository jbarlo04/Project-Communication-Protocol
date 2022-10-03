package com.project.food_hubs1;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.project.food_hubs1.chat.ChatListModel;
import com.project.food_hubs1.util.GlobalVariable;

public class ItemOrderViewHolder extends RecyclerView.ViewHolder {
    ImageView imgView;
    TextView tvPname,tvPdesc,items,tvRemove,orderstatus,tvdate,cartermobile;
    public ItemOrderViewHolder(View itemView) {
        super(itemView);
    }
    public void setitem(FirebaseRecyclerAdapter<Orders, ItemOrderViewHolder> activity,Orders model){
        tvPname=itemView.findViewById(R.id.tvcustomername);
        tvPdesc=itemView.findViewById(R.id.tvcustomermobile);
        orderstatus=itemView.findViewById(R.id.tvcarterlistorderstatus);
        tvdate=itemView.findViewById(R.id.tvorderdate);
        items=itemView.findViewById(R.id.tvorderitems);

        orderstatus.setText("Status : "+model.status);
        tvPname.setText("Customer Name :  "+model.customername);
        tvPdesc.setText("Customer No :  "+model.mobile);
        items.setText("Items :  "+model.itemsordered);
        tvdate.setText("Date: "+ GlobalVariable.Companion.date(model.date));

    }
    public void setitems(FirebaseRecyclerAdapter<Orders, ItemOrderViewHolder> activity,Orders model){
        tvPname=itemView.findViewById(R.id.tvuorname);
        tvPdesc=itemView.findViewById(R.id.tvuorprice);
        cartermobile=itemView.findViewById(R.id.tvuordesc);
        orderstatus=itemView.findViewById(R.id.tvuserorderstatus);
        tvdate=itemView.findViewById(R.id.tvdate);

        orderstatus.setText("Status :  "+model.status);
        tvPname.setText("Carter Name :  "+model.bname);
        cartermobile.setText("Mobile Number :  "+model.cmobile);
        tvPdesc.setText("Items :  "+model.itemsordered);
        tvdate.setText("Date: "+ GlobalVariable.Companion.date(model.date));

    }
    public void setChatListItems(FirebaseRecyclerAdapter<ChatListModel, ItemOrderViewHolder> activity, ChatListModel model){
       TextView orderid=itemView.findViewById(R.id.cuOrderId);
       TextView lastmessage=itemView.findViewById(R.id.cuLastMessage);
       TextView date=itemView.findViewById(R.id.cuDate);

        orderid.setText("OrderId:  "+model.getOid());
        lastmessage.setText("Last Message: "+model.getLastmessage());
        date.setText("Date: "+ GlobalVariable.Companion.date(model.getDate()));

    }
}
