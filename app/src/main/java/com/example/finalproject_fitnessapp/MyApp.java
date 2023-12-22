package com.example.finalproject_fitnessapp;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyApp extends Application {

    NetworkingManager networkingManager = new NetworkingManager();
    static ExecutorService executorService = Executors.newFixedThreadPool(4);
    static Handler mainHandler = new Handler(Looper.getMainLooper());
    JSONManager jsonManager = new JSONManager();
    ArrayList<MuscleGroup> muscleGroupArrayList = new ArrayList<>(0);
    String currentMuscleGroup;
    Exercise currentExercise;
}