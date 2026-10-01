package com.khadija.myfitapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

// Login sida   
public class MainActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonLogin;

    private CheckBox checkBoxRememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        checkBoxRememberMe =findViewById(R.id.checkBoxRememberMe);

        SharedPreferences preferences =
                getSharedPreferences("login", MODE_PRIVATE);

        editTextUsername.setText(
                preferences.getString("username", ""));
        editTextPassword.setText(
                preferences.getString("password", ""));
        checkBoxRememberMe.setChecked(
                preferences.getBoolean("remenberMe", false)
        );


        buttonLogin.setOnClickListener(view -> {

            String username = editTextUsername.getText().toString().trim();
            String password = editTextPassword.getText().toString();

            if (username.equals("admin") && password.equals("1234")) {

                if(checkBoxRememberMe.isChecked()){
                    preferences.edit()
                            .putString("username", username)
                            .putString("password", password)
                            .putBoolean("rememberMe", true)
                            .apply();
                }else {
                    preferences.edit().clear().apply();
                }

                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(
                        MainActivity.this,
                        "Wrong username ore password",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }
}

