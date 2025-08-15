package com.example.studybuddy.data.database;

import androidx.annotation.Nullable;

import com.example.studybuddy.data.model.MessageBetweenUsers;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class UserMessageRepository {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference messagesRef = db.collection("messages");

    // Send a new message
    public Task<Void> sendMessage(String senderId, String receiverId, String content) {
        Map<String, Object> messageData = new HashMap<>();
        messageData.put("senderId", senderId);
        messageData.put("receiverId", receiverId);
        messageData.put("content", content);
        messageData.put("timestamp", Timestamp.now());
        messageData.put("participants", List.of(senderId, receiverId));

        return messagesRef.add(messageData).continueWith(task -> null); // Return Task<Void>
    }

    // Get all messages between two users (one-time fetch)
    public void getMessagesBetween(String user1, String user2, Consumer<FilteredQuerySnapshot> callback) {
        messagesRef
                .whereArrayContains("participants", user1)
                .orderBy("timestamp", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        // Filter messages where participants contain both users
                        List<QueryDocumentSnapshot> filtered = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : task.getResult()) {
                            List<String> participants = (List<String>) doc.get("participants");
                            if (participants.contains(user1) && participants.contains(user2)) {
                                filtered.add(doc);
                            }
                        }
                        // Wrap filtered docs in FilteredQuerySnapshot and pass to callback
                        callback.accept(new FilteredQuerySnapshot(filtered, null));
                    } else {
                        callback.accept(new FilteredQuerySnapshot(new ArrayList<>(), task.getException()));
                    }
                });
    }


    // Listen to real-time updates for messages between two users
    public void listenToFilteredMessages(String user1, String user2, Consumer<FilteredQuerySnapshot> callback) {
        messagesRef
                .orderBy("timestamp", Query.Direction.ASCENDING)
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        callback.accept(new FilteredQuerySnapshot(new ArrayList<>(), error));
                        return;
                    }

                    List<QueryDocumentSnapshot> filtered = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : value) {
                        String sender = doc.getString("senderId");
                        String receiver = doc.getString("receiverId");
                        if ((sender.equals(user1) && receiver.equals(user2)) ||
                                (sender.equals(user2) && receiver.equals(user1))) {
                            filtered.add(doc);
                        }
                    }

                    callback.accept(new FilteredQuerySnapshot(filtered, null));
                });
    }


    // Convert QuerySnapshot to list of MessageBetweenUsers objects
    public static List<MessageBetweenUsers> toMessageList(QuerySnapshot snapshot) {
        List<MessageBetweenUsers> messageList = new ArrayList<>();
        if (snapshot != null) {
            for (QueryDocumentSnapshot doc : snapshot) {
                MessageBetweenUsers message = doc.toObject(MessageBetweenUsers.class);
                messageList.add(message);
            }
        }
        return messageList;
    }

    // Helper class to simulate filtered QuerySnapshot for getMessagesBetween
    public static class FilteredQuerySnapshot {
        private final List<QueryDocumentSnapshot> filteredDocs;
        private final Exception exception;

        public FilteredQuerySnapshot(List<QueryDocumentSnapshot> filteredDocs, Exception exception) {
            this.filteredDocs = filteredDocs;
            this.exception = exception;
        }


        public List<QueryDocumentSnapshot> getDocuments() {
            return filteredDocs;
        }


        public Exception getException() {
            return exception;
        }
    }
}
