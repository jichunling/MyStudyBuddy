package com.example.studybuddy.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.studybuddy.R;
import com.example.studybuddy.data.model.MessageBetweenUsers;
import com.example.studybuddy.data.model.MessageBetweenUsers;

import java.util.List;

public class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageAdapter.MessageViewHolder> {

    private List<MessageBetweenUsers> messages;

    public ChatMessageAdapter(List<MessageBetweenUsers> messages) {
        this.messages = messages;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_message_between_users, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        MessageBetweenUsers message = messages.get(position);
        holder.senderText.setText(message.getSenderId());
        holder.contentText.setText(message.getContent());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView senderText, contentText;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            senderText = itemView.findViewById(R.id.textViewSender);
            contentText = itemView.findViewById(R.id.textViewContent);
        }
    }
}
