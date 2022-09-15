package com.example.pcp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pcp.dto.Group;

import java.util.ArrayList;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.MyViewHolder> {

    private ArrayList<Group> groupsList;


    public recyclerAdapter(ArrayList<Group> groupsList) {
        this.groupsList = groupsList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView groupName;

        public MyViewHolder(final View view) {
            super(view);
            groupName = view.findViewById(R.id.group_text_id);
        }
    }

    @NonNull
    @Override
    public recyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerAdapter.MyViewHolder holder, int position) {
        String name = groupsList.get(position).name;
        holder.groupName.setText(name);
    }

    @Override
    public int getItemCount() {
        return groupsList.size();
    }
}
