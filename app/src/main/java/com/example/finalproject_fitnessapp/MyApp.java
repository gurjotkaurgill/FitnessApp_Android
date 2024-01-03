package com.example.finalproject_fitnessapp;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import com.example.finalproject_fitnessapp.DataManagers.DatabaseManager;
import com.example.finalproject_fitnessapp.DataManagers.JSONManager;
import com.example.finalproject_fitnessapp.DataManagers.NetworkingManager;
import com.example.finalproject_fitnessapp.Models.Exercise;
import com.example.finalproject_fitnessapp.Models.TargetGroup;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyApp extends Application {

    public NetworkingManager networkingManager = new NetworkingManager();
    public static ExecutorService executorService = Executors.newFixedThreadPool(4);
    public static Handler mainHandler = new Handler(Looper.getMainLooper());
    public JSONManager jsonManager = new JSONManager();
    public DatabaseManager databaseManager = new DatabaseManager();
    public ArrayList<TargetGroup> targetGroupArrayList = new ArrayList<>(0);
    public int optionChecked = 1;
    public String currentTargetGroupSelected;
    public Exercise currentExercise;
}