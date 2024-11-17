package com.example.studybuddy.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.studybuddy.R;
import com.example.studybuddy.activities.GenAIActivity;
import com.example.studybuddy.activities.LikesActivity;
import com.example.studybuddy.activities.ProfileActivity; // Assuming you have a ProfileActivity
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MatchUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_user);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        // Set a listener to handle item selections
        bottomNav.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@androidx.annotation.NonNull android.view.MenuItem item) {
                if (item.getItemId() == R.id.nav_matched_buddies) {
                    // Navigate to Matched Buddies page (do nothing, already in this page)
                    return true;
                } else if (item.getItemId() == R.id.nav_likes) {
                    // Navigate to Likes page
                    openLikesPage();
                    return true;
                } else if (item.getItemId() == R.id.nav_genai) {
                    // Navigate to GenAI page
                    openGenAIPage();
                    return true;
                } else if (item.getItemId() == R.id.nav_profile) {
                    // Navigate to Profile page
                    openProfilePage();
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

    private void openProfilePage() {
        Intent intent = new Intent(MatchUserActivity.this, ProfileActivity.class);
        startActivity(intent);
    }
}
