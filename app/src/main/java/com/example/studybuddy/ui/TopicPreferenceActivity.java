package com.example.studybuddy.ui;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studybuddy.R;

public class TopicPreferenceActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private TextView textProgress;
    private Button buttonNext;
    private RadioGroup radioGroupTopics;
    private int currentQuestion = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_preference);

        progressBar = findViewById(R.id.progressBar);
        textProgress = findViewById(R.id.textProgress);
        buttonNext = findViewById(R.id.buttonNext);
        radioGroupTopics = findViewById(R.id.radioGroupTopics);

        buttonNext.setOnClickListener(v -> {
            int selectedId = radioGroupTopics.getCheckedRadioButtonId();
            if (selectedId != -1) {
                RadioButton selectedRadioButton = findViewById(selectedId);
                String selectedTopic = selectedRadioButton.getText().toString();

                Toast.makeText(TopicPreferenceActivity.this, "Selected Topic: " + selectedTopic, Toast.LENGTH_SHORT).show();

                currentQuestion++;
                if (currentQuestion <= 4) {
                    ObjectAnimator progressAnimator = ObjectAnimator.ofInt(progressBar, "progress", currentQuestion - 1, currentQuestion);
                    progressAnimator.setDuration(500);  // Animation duration in milliseconds
                    progressAnimator.start();

                    textProgress.setText(currentQuestion + "/4");
                } else {
                    Toast.makeText(TopicPreferenceActivity.this, "All questions complete!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
