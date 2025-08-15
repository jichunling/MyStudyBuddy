package com.example.studybuddy.activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.studybuddy.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView
                = findViewById(R.id.bottom_navigation);

        bottomNavigationView
                .setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.nav_matched_buddies);

    }
    MatchUserFragment matchUserFragment = new MatchUserFragment();
    LikeFragment likeFragment = new LikeFragment();
    GenAiFragment genAiFragment = new GenAiFragment();
    AccountFragment accountFragment = new AccountFragment();
    ChatFragment chatFragment = new ChatFragment();


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        Fragment fragment = null;

        if (id == R.id.nav_matched_buddies) {
            fragment = matchUserFragment;
        } else if (id == R.id.nav_likes) {
            fragment = likeFragment;
        } else if (id == R.id.nav_genai) {
            fragment = genAiFragment;
        } else if (id == R.id.nav_account) {
            fragment = accountFragment;
        } else if (id == R.id.nav_chat) {
            fragment = chatFragment;
        }

        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
    }
    return false;
    }
}


