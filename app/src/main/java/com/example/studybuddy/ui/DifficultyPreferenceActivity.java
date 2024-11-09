// DifficultyPreferenceActivity.java
package com.example.studybuddy.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studybuddy.R;
import com.example.studybuddy.data.database.DatabaseHelper;

public class DifficultyPreferenceActivity extends AppCompatActivity {

    private String email;
    private RadioGroup radioGroupDifficulty;
    private Button btnSaveDifficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty_preference);

        email = getIntent().getStringExtra("USER_EMAIL");

        radioGroupDifficulty = findViewById(R.id.radioGroupDifficulty);
        btnSaveDifficulty = findViewById(R.id.btnSaveDifficulty);

        btnSaveDifficulty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroupDifficulty.getCheckedRadioButtonId();
                RadioButton selectedRadioButton = findViewById(selectedId);

                if (selectedRadioButton != null) {
                    String difficultyLevel = selectedRadioButton.getText().toString();

                    DatabaseHelper databaseHelper = new DatabaseHelper(DifficultyPreferenceActivity.this);
                    boolean isUpdated = databaseHelper.updateUserStudyDifficultyLevel(email, difficultyLevel);

                    if (isUpdated) {
                        Toast.makeText(DifficultyPreferenceActivity.this, "Difficulty level saved!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(DifficultyPreferenceActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(DifficultyPreferenceActivity.this, "Failed to save difficulty level.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DifficultyPreferenceActivity.this, "Please select a difficulty level.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
