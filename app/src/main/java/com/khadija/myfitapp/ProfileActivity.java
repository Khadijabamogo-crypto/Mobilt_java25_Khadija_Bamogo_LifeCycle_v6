package com.khadija.myfitapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private EditText editTextName;

    private EditText editTextAge;
    private EditText editTextHeight;
    private EditText editTextWeight;

    private Button buttonCalculateBMI;
    private Button buttonSaveProfile;
    private TextView textViewBMI;

    private Button buttonFitness;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        editTextName = findViewById(R.id.editTextName);
        editTextAge = findViewById(R.id.editTextAge);
        editTextHeight = findViewById(R.id.editTextHeight);
        editTextWeight = findViewById(R.id.editTextWeight);

        buttonCalculateBMI = findViewById(R.id.buttonCalculateBMI);
        buttonSaveProfile = findViewById(R.id.buttonSaveProfile);

        buttonFitness = findViewById(R.id.buttonFitness);

        buttonFitness.setOnClickListener(view ->{

            Intent intent = new Intent(ProfileActivity.this, FitnessActivity.class);
            startActivity(intent);
        });

        textViewBMI = findViewById(R.id.textViewBMI);

        buttonCalculateBMI.setOnClickListener(view ->{
            String heightText = editTextHeight.getText().toString();
            String weightText = editTextWeight.getText().toString();

            if(!heightText.isEmpty()&& !weightText.isEmpty()){

                double height = Double.parseDouble(heightText) /100;
                double weight = Double.parseDouble(weightText);

                double bmi = weight / (height * height);

                textViewBMI.setText("BMI: "+ String.format("%.1f", bmi));
            }
        });

        buttonSaveProfile.setOnClickListener(view ->{
            SharedPreferences preferences= getSharedPreferences("profile", MODE_PRIVATE);

            SharedPreferences.Editor editor = preferences.edit();

            editor.putString("name", editTextName.getText().toString());
            editor.putString("age",editTextAge.getText().toString());
            editor.putString("height", editTextHeight.getText().toString());
            editor.putString("weight", editTextWeight.getText().toString());

            editor.apply();

            Toast.makeText(
                    ProfileActivity.this, "Profile saved!",Toast.LENGTH_SHORT
            ).show();

        });

        textViewBMI = findViewById(R.id.textViewBMI);
        SharedPreferences preferences = getSharedPreferences("profile", MODE_PRIVATE);

        editTextName.setText(preferences.getString("name", ""));
        editTextAge.setText(preferences.getString("age", ""));
        editTextHeight.setText(preferences.getString("height", ""));
        editTextWeight.setText(preferences.getString("weight",""));


        Button buttonBack = findViewById(R.id.buttonBack);

        buttonBack.setOnClickListener(view -> {
            finish();
        });

    }
}
