package com.example.steppin;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import Data.DatabaseHelper;

public class ActivityFifth extends AppCompatActivity {

    private EditText editTextName, editTextEmail, editTextPassword;
    private DatabaseHelper databaseHelper;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fifth);

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);

        databaseHelper = new DatabaseHelper(this);

        // Загрузка данных пользователя из базы данных
        loadUserData();

        Button buttonSave = findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserData();
            }
        });
    }

    private void loadUserData() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", -1);
        String userEmail = sharedPreferences.getString("userEmail", "");
        String userName = sharedPreferences.getString("userName", "");
        String userPassword = sharedPreferences.getString("userPassword", "");

        editTextName.setText(userName);
        editTextEmail.setText(userEmail);
        editTextPassword.setText(userPassword);
    }

    private void saveUserData() {
        String newName = editTextName.getText().toString().trim();
        String newEmail = editTextEmail.getText().toString().trim();
        String newPassword = editTextPassword.getText().toString().trim();

        if (!newName.isEmpty()) {
            updateUserName(newName);
        }

        if (!newEmail.isEmpty()) {
            updateUserEmail(newEmail);
        }

        if (!newPassword.isEmpty()) {
            updateUserPassword(newPassword);
        }

        Toast.makeText(this, "User data saved successfully", Toast.LENGTH_SHORT).show();
    }

    private void updateUserName(String newName) {
        // Обновляем имя пользователя в базе данных
        databaseHelper.updateUserName(userId, newName);
        // Обновляем имя пользователя в SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userName", newName);
        editor.apply();
    }

    private void updateUserEmail(String newEmail) {
        databaseHelper.updateUserEmail(userId, newEmail);
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userEmail", newEmail);
        editor.apply();
    }

    private void updateUserPassword(String newPassword) {
        databaseHelper.updateUserPassword(userId, newPassword);
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userPassword", newPassword);
        editor.apply();
    }
}