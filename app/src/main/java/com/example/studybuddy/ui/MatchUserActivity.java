package com.example.studybuddy.ui;

import static android.content.ClipData.newIntent;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.studybuddy.R;
import com.example.studybuddy.data.database.DatabaseHelper;
import com.example.studybuddy.data.model.User;

import java.util.ArrayList;
import java.util.List;

public class MatchUserActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private ArrayList<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_user);

        Button genaiButton=findViewById(R.id.genai_button);

        genaiButton.setOnClickListener(v->{
            Intent intent=new Intent(MatchUserActivity.this, GenAIActivity.class);
            startActivity(intent);
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("userEmail", null);

        if (userEmail != null) {
            DatabaseHelper databaseHelper = new DatabaseHelper(this);
            List<String> currentUserTopics = getCurrentUserTopics(userEmail, databaseHelper);
            Log.d("MatchUserActivity", "Current user topics: " + currentUserTopics.toString());

            userList = databaseHelper.getUsersWithSameTopics(currentUserTopics, userEmail);
            Log.d("MatchUserActivity", "User list size: " + userList.size());

            userList.removeIf(user -> user.getEmail().equals(userEmail));

            userAdapter = new UserAdapter(userList);
            recyclerView.setAdapter(userAdapter);
        } else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
        }
    }

    private List<String> getCurrentUserTopics(String email, DatabaseHelper databaseHelper) {
        return databaseHelper.getUserTopicsByEmail(email);
    }
}

