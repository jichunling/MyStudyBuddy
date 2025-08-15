package com.example.studybuddy.adapter;

import android.app.Activity;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studybuddy.data.model.MessageUserModel;


import java.util.List;

public class MessageUserAdaptor extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Activity context;
    List<MessageUserModel> list;
    public MessageUserAdaptor(Activity context, List<MessageUserModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}