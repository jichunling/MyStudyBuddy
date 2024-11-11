package com.example.studybuddy.ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.studybuddy.R;
import com.example.studybuddy.data.database.DatabaseHelper;
import com.example.studybuddy.data.model.User;

import java.util.ArrayList;

public class MatchUserActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private ArrayList<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_user);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        userList = databaseHelper.getAllUsers();

        userAdapter = new UserAdapter(userList);
        recyclerView.setAdapter(userAdapter);
    }
}
