package com.project.food_hubs1;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;


public class ItemChatViewHolder extends RecyclerView.ViewHolder {
    TextView tvChat;
    public ItemChatViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
    }
    public void setitems( String name){
        tvChat=itemView.findViewById(R.id.tvChat);

       tvChat.setText(name);
    }

}
