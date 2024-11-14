package com.example.studybuddy.ui;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studybuddy.R;
import com.example.studybuddy.data.database.DatabaseHelper;
import com.example.studybuddy.utils.ValidationUtils;

public class SignupActivity extends AppCompatActivity {
    private EditText editTextEmail, editTextPassword;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        dbHelper = new DatabaseHelper(this);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        Button buttonSignup = findViewById(R.id.buttonSignup);
        TextView textViewLogin = findViewById(R.id.textViewLogin);


        buttonSignup.setOnClickListener(view -> {
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString();

            if (!ValidationUtils.isValidEmail(email)) {
                Toast.makeText(SignupActivity.this, "Invalid email format", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!ValidationUtils.isValidPassword(password)) {
                Toast.makeText(SignupActivity.this, "Password must be at least 6 characters, include uppercase, lowercase, and a number", Toast.LENGTH_LONG).show();
                return;
            }

            boolean isInserted = dbHelper.insertUser(email, password);
            if (isInserted) {
                Toast.makeText(SignupActivity.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(SignupActivity.this, "Sign Up Failed, email might already be in use", Toast.LENGTH_SHORT).show();
            }
        });

        textViewLogin.setOnClickListener(view -> {
            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });




    }
}
