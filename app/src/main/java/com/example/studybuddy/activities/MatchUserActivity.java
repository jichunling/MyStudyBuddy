package com.example.studybuddy.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.studybuddy.R;
import com.example.studybuddy.activities.GenAIActivity;
import com.example.studybuddy.activities.LikesActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MatchUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_user);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        bottomNav.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@androidx.annotation.NonNull android.view.MenuItem item) {
                if (item.getItemId() == R.id.nav_matched_buddies) {
                    return true;
                } else if (item.getItemId() == R.id.nav_likes) {
                    openLikesPage();
                    return true;
                } else if (item.getItemId() == R.id.nav_genai) {
                    openGenAIPage();
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    private void openLikesPage() {
        Intent intent = new Intent(MatchUserActivity.this, LikesActivity.class);
        startActivity(intent);
    }

    private void openGenAIPage() {
        Intent intent = new Intent(MatchUserActivity.this, GenAIActivity.class);
        startActivity(intent);
    }
}
