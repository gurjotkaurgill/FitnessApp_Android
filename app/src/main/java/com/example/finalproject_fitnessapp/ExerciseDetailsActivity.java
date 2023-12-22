package com.example.finalproject_fitnessapp;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import java.io.IOException;
import java.net.URL;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

public class ExerciseDetailsActivity extends AppCompatActivity {

    String exerciseName;
    TextView targetField, partOfField, equipmentField, secMusclesField, instructionsField;
    ImageView imageView;
    NetworkingManager networkingManager;
    Exercise exercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_details);

        //exerciseName = getIntent().getExtras().getString("exerciseName");
        //exercise = getIntent().getExtras().getParcelable("exercise");
        exercise = ((MyApp)getApplication()).currentExercise;
        exerciseName = exercise.getName();
        exerciseName = exerciseName.substring(0, 1).toUpperCase() + exerciseName.substring(1).toLowerCase();
        this.setTitle(exerciseName);

        initializeViewsAndVariables();
        setValues();

    }

    void initializeViewsAndVariables(){
        //nameField = findViewById(R.id.nameField);
        targetField = findViewById(R.id.targetField);
        partOfField = findViewById(R.id.partOfField);
        equipmentField = findViewById(R.id.equipmentField);
        secMusclesField = findViewById(R.id.secMusclesField);
        instructionsField = findViewById(R.id.instructionsField);
        imageView = findViewById(R.id.demoGIF);
        networkingManager = ((MyApp)getApplication()).networkingManager;
    }

    void setValues(){
//        MyApp.executorService.execute(new Runnable() {
//            @Override
//            public void run() {
//                webView.getSettings().setJavaScriptEnabled(true);
//                webView.setWebViewClient(new WebViewClient());
//                webView.loadUrl(exercise.getGifUrl());
//            }
//        });
        //Glide.with(this).load(exercise.getGifUrl()).into(imageView);
        //nameField.setText(exerciseName);
        getGIF(exercise.getGifUrl());
        targetField.setText(((MyApp)getApplication()).currentMuscleGroup);
        partOfField.setText(exercise.getBodyPart());
        equipmentField.setText(exercise.getEquipment());
        secMusclesField.setText(exercise.getSecondaryMuscles());
//        for(int i=1; i< exercise.getSecondaryMuscles().size();i++){
//            secMusclesField.setText(", " + secMusclesField.getText() + exercise.getSecondaryMuscles().get(i));
//        }
        instructionsField.setText(exercise.getInstructions());
//        for(int i=1; i< exercise.getInstructions().size();i++){
//            instructionsField.setText("\n" + instructionsField.getText() + exercise.getInstructions().get(i));
//        }
    }

    //gets an image from GIF
    public void getGIF(String url) {
        MyApp.executorService.execute(new Runnable() {
            Bitmap demoGIF;
            @Override
            public void run() {
                try {
                    demoGIF = BitmapFactory.decodeStream(new URL(url).openStream());
                    MyApp.mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Glide.with(ExerciseDetailsActivity.this).load(exercise.getGifUrl()).into(imageView);
                        }
                    });
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}