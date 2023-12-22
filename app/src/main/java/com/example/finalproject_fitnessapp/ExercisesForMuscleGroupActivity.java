package com.example.finalproject_fitnessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.SearchView;

import java.util.ArrayList;

public class ExercisesForMuscleGroupActivity extends AppCompatActivity
        implements NetworkingManager.GotResultFromAPI, ExercisesRecyclerAdapter.ExerciseClickListener
{

    NetworkingManager networkingManager;
    ArrayList<Exercise> exerciseArrayList;
    JSONManager jsonManager;
    String muscleGroupName;
    RecyclerView recyclerView;
    ExercisesRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises_for_muscle_group);

        //muscleGroupName= getIntent().getStringExtra("muscleGroup");
        muscleGroupName = ((MyApp)getApplication()).currentMuscleGroup;

        //change only first character to uppercase
        muscleGroupName = muscleGroupName.substring(0,1).toUpperCase() + muscleGroupName.substring(1).toLowerCase();
        this.setTitle(muscleGroupName);

        jsonManager = ((MyApp)getApplication()).jsonManager;
        networkingManager = ((MyApp)getApplication()).networkingManager;
        networkingManager.getExercisesForMuscleGroup(muscleGroupName.toLowerCase());
        networkingManager.listener = this;

        exerciseArrayList = new ArrayList<>(0);
        recyclerView = findViewById(R.id.exerciseList);
        adapter = new ExercisesRecyclerAdapter(this,exerciseArrayList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.exerciseClickListener = this;
    }

    @Override
    public void gotListResultFromAPI(String jsonResponse) {
        exerciseArrayList = jsonManager.fromJSONToArrayOfExercises(jsonResponse);
        adapter.exercises = exerciseArrayList;
        adapter.notifyDataSetChanged();
    }

    @Override
    public void exerciseClicked(Exercise exercise) {
        Intent intent3 = new Intent(ExercisesForMuscleGroupActivity.this, ExerciseDetailsActivity.class);
        //intent3.putExtra("exerciseName",exercise.getName());
        ((MyApp)getApplication()).currentExercise = exercise;
        startActivity(intent3);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        super.onCreateOptionsMenu(menu);
//        getMenuInflater().inflate(R.menu.menu,menu);
//        SearchView menuSearchItem = (SearchView) menu.findItem(R.id.menuSearch).getActionView();
//        menuSearchItem.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                if(newText.length()>2){}
//                else{}
//                return false;
//            }
//        });
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        return super.onOptionsItemSelected(item);
//    }
}