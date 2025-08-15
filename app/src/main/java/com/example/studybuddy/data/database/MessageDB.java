package com.example.studybuddy.data.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.studybuddy.data.model.MessageUserModel;

import java.util.ArrayList;
import java.util.List;

public class MessageDB extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Connections.db";
    public static final String TABLE_NAME = "Messages";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "SENDER_ID";
    public static final String COL_3 = "RECEIVER_ID";
    public static final String COL_4 = "CONTENT";

    public MessageDB(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "SENDER_ID TEXT, RECEIVER_ID TEXT, CONTENT TEXT)");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertMessage(String senderID, String receiverID, String content) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, senderID);
        contentValues.put(COL_3, receiverID);
        contentValues.put(COL_4, content);
        long result = db.insert(TABLE_NAME, null, contentValues);
        db.close();
        return result != -1;
    }
    // Method to get all users
    public List<MessageUserModel> getAllUsers() {
        List<MessageUserModel> userList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("user_messages", null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COL_1));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(COL_2));
                @SuppressLint("Range") String message = cursor.getString(cursor.getColumnIndex(COL_4));
                MessageUserModel user = new MessageUserModel(id, name, message);
                userList.add(user);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return userList;
    }

}

