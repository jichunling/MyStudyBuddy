package com.example.studybuddy.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.studybuddy.R;
import com.example.studybuddy.data.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.Objects;

public class EditMyTopicPreferences extends AppCompatActivity {

    private CheckBox checkComputerScience, checkBiology, checkChemistry, checkMathematics,
            checkEngineering, checkPhysics, checkEnglish, checkFrench, checkHistory, checkPhilosophy;
    private DatabaseHelper databaseHelper;
    private ImageView backButton;
    Button saveTopics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_my_topic_preferences);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        checkComputerScience = findViewById(R.id.editCheckComputerScience);
        checkBiology = findViewById(R.id.editCheckBiology);
        checkChemistry = findViewById(R.id.editCheckChemistry);
        checkMathematics = findViewById(R.id.editCheckMathematics);
        checkEngineering = findViewById(R.id.editCheckEngineering);
        checkPhysics = findViewById(R.id.editCheckPhysics);
        checkEnglish = findViewById(R.id.editCheckEnglish);
        checkFrench = findViewById(R.id.editCheckFrench);
        checkHistory = findViewById(R.id.editCheckHistory);
        checkPhilosophy = findViewById(R.id.editCheckPhilosophy);

        saveTopics = findViewById(R.id.saveButton);
        backButton = findViewById(R.id.backButton);


        //Creating an array of all checkboxes
        CheckBox[] checkBoxes = {checkComputerScience, checkBiology, checkChemistry, checkMathematics, checkEngineering, checkPhysics, checkEnglish, checkFrench, checkHistory, checkPhilosophy};
        databaseHelper = new DatabaseHelper(this);

        Intent intent = getIntent();
        String userEmail = intent.getStringExtra("userEmail");

        //Getting the current topics of the logged in user
        //Querying DB directly is better than intent.getStringExtra() because:
        //You want to guarantee the edit screen always shows the latest saved data.
        //It keeps the edit screen decoupled — it doesn’t depend on who opened it or what they passed in.
        //It avoids bugs if you forget to pass the topics in some places.
        String currentUserTopics = databaseHelper.getUserTopicString(userEmail);

        //If the person had already checked the boxes, updating it!
        for(CheckBox checkBox : checkBoxes){
            checkBox.setChecked(currentUserTopics.contains(checkBox.getText().toString()));
        }

        //collects the current selections that the user made
        saveTopics.setOnClickListener( v -> {
            StringBuilder selectedTopics = new StringBuilder();

            if (checkComputerScience.isChecked()) selectedTopics.append("Computer Science, ");
            if (checkBiology.isChecked()) selectedTopics.append("Biology, ");
            if (checkChemistry.isChecked()) selectedTopics.append("Chemistry, ");
            if (checkMathematics.isChecked()) selectedTopics.append("Mathematics, ");
            if (checkEngineering.isChecked()) selectedTopics.append("Engineering, ");
            if (checkPhysics.isChecked()) selectedTopics.append("Physics, ");
            if (checkEnglish.isChecked()) selectedTopics.append("English, ");
            if (checkFrench.isChecked()) selectedTopics.append("French, ");
            if (checkHistory.isChecked()) selectedTopics.append("History, ");
            if (checkPhilosophy.isChecked()) selectedTopics.append("Philosophy, ");

            if (selectedTopics.length() > 0) {
                selectedTopics.setLength(selectedTopics.length() - 2);
            }

            if (selectedTopics.length() > 0) {
                Toast.makeText(this, "Topics Updated Successfully!", Toast.LENGTH_SHORT).show();
                saveTopicToDatabase(selectedTopics.toString());
                Intent startMain = new Intent(this, MainActivity.class);
                startActivity(startMain);
                finish();

            } else {
                Toast.makeText(this, "No topics selected.", Toast.LENGTH_SHORT).show();
            }
        });


        backButton.setOnClickListener(v -> {
            finish();
        });

    }

//   This method (which isn’t shown in your snippet, but should exist in your code) is where you actually save the new selections into the database.
//So the “new” selections are not saved when the user taps checkboxes;
//They’re saved only when the user taps the save button.
    private void saveTopicToDatabase(String topics) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("userEmail", null);

        if (userEmail != null) {
            boolean isSaved = databaseHelper.updateUserTopic(userEmail, topics);
            if (isSaved) {
               // Toast.makeText(this, "Topics saved!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to save topics.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "User not logged in.", Toast.LENGTH_SHORT).show();
        }
    }
}