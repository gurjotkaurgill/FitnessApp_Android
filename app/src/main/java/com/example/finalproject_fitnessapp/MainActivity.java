package com.example.finalproject_fitnessapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton plusBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        plusBtn = findViewById(R.id.addExerciseBtn);

        plusBtn.setOnClickListener(v -> {
            Intent intent1 = new Intent(MainActivity.this,MuscleGroupActivity.class);
            startActivity(intent1);
        });
    }
}