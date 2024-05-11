package com.example.steppin;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import Data.DatabaseHelper;
import Model.User;
public class ThirdActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                DatabaseHelper databaseHelper = new DatabaseHelper(ThirdActivity.this);
                User currentUser = databaseHelper.getUser(email, password);

                if (currentUser != null) {
                    SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("userId", currentUser.getId());
                    editor.putString("userEmail", currentUser.getEmail());
                    editor.putString("userName", currentUser.getName());
                    editor.putString("userPassword", currentUser.getPassword());
                    editor.apply();

                    if (email.equals("admin@mail.ru")) {
                        redirectAdminActivity();
                        Toast.makeText(ThirdActivity.this, "Вход администратора выполнен успешно!", Toast.LENGTH_SHORT).show();
                    } else {
                        redirectToProductActivity();
                        Toast.makeText(ThirdActivity.this, "Вход выполнен успешно!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ThirdActivity.this, "Неверный email или пароль!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void redirectAdminActivity() {
        Intent intent = new Intent(ThirdActivity.this, AdminActivity.class);
        startActivity(intent);
    }

    private void redirectToProductActivity() {
        Intent intent = new Intent(ThirdActivity.this, FourActivity.class);
        startActivity(intent);
        finish();
    }
}