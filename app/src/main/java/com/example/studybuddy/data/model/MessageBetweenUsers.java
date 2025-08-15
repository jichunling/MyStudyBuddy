package com.example.studybuddy.data.model;

import com.google.firebase.Timestamp;


public class MessageBetweenUsers {
    private String senderId;
    private String receiverId;
    private String content;
    private Timestamp timestamp;

    // Firestore requires an empty constructor
    public MessageBetweenUsers() {}

    public MessageBetweenUsers(String senderId, String receiverId, String content, Timestamp timestamp) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.timestamp = timestamp;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
