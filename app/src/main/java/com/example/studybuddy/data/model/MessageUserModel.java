package com.example.studybuddy.data.model;

public class MessageUserModel {
    private int id;
    private String name;
    private String message;

    public MessageUserModel(int id, String name, String message) {
        this.id = id;
        this.name = name;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }
}
