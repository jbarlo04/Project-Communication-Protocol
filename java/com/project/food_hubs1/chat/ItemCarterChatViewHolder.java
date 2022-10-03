package com.project.food_hubs1.chat;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.food_hubs1.R;

import org.jetbrains.annotations.NotNull;


public class ItemCarterChatViewHolder extends RecyclerView.ViewHolder {
    TextView tvChat;
    public ItemCarterChatViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
    }
    public void setitems( String name){
        tvChat=itemView.findViewById(R.id.tvChat);

       tvChat.setText(name);
    }

}
