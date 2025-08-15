package com.example.studybuddy.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.studybuddy.R;
import com.example.studybuddy.adapter.ChatMessageAdapter;
import com.example.studybuddy.data.model.MessageBetweenUsers;
import com.example.studybuddy.data.model.MessageBetweenUsers;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import android.widget.Button;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import com.google.firebase.Timestamp;

public class ChatFragment extends Fragment {

    private RecyclerView recyclerView;
    private TextInputEditText editTextMessage;
    private Button buttonSend;
    private ChatMessageAdapter messageAdapter;
    private List<MessageBetweenUsers> messageList = new ArrayList<>();
    private FirebaseFirestore db;
    private ListenerRegistration messageListener;

    // Replace these with actual user IDs
    private String currentUserId = "user1";
    private String chatPartnerId = "user2";

    @SuppressLint("WrongViewCast")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewMessages);
        editTextMessage = view.findViewById(R.id.editTextMessage);
        buttonSend = view.findViewById(R.id.buttonSend);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        messageAdapter = new ChatMessageAdapter(messageList);
        recyclerView.setAdapter(messageAdapter);

        db = FirebaseFirestore.getInstance();

        loadMessages();

        buttonSend.setOnClickListener(v -> {
            String content = editTextMessage.getText().toString().trim();
            if (!TextUtils.isEmpty(content)) {
                sendMessage(content);
                editTextMessage.setText("");
            }
        });

        return view;
    }

    private void loadMessages() {
        messageListener = db.collection("messages")
                .whereIn("senderId", java.util.Arrays.asList(currentUserId, chatPartnerId))
                .whereIn("receiverId", java.util.Arrays.asList(currentUserId, chatPartnerId))
                .orderBy("timestamp", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) return;
                        messageList.clear();
                        for (DocumentSnapshot doc : snapshots) {
                            MessageBetweenUsers message = doc.toObject(MessageBetweenUsers.class);
                            messageList.add(message);
                        }
                        messageAdapter.notifyDataSetChanged();
                        recyclerView.scrollToPosition(messageList.size() - 1);
                    }
                });
    }


// ...

    private void sendMessage(String content) {
        MessageBetweenUsers message = new MessageBetweenUsers(
                currentUserId,
                chatPartnerId,
                content,
                Timestamp.now() // <-- use Firebase Timestamp
        );
        db.collection("messages").add(message);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (messageListener != null) messageListener.remove();
    }
}
