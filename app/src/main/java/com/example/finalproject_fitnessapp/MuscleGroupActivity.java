package com.example.finalproject_fitnessapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import java.util.ArrayList;

public class MuscleGroupActivity extends AppCompatActivity
        implements NetworkingManager.GotResultFromAPI,
        MuscleGroupRecyclerAdapter.MuscleGroupClickListener
{

    NetworkingManager networkingManager;
    JSONManager jsonManager;
    ArrayList<MuscleGroup> muscleGroupArrayList;
    MuscleGroupRecyclerAdapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muscle_group);

        recyclerView = findViewById(R.id.muscleGroupList);

        jsonManager = ((MyApp)getApplication()).jsonManager;
        muscleGroupArrayList = ((MyApp)getApplication()).muscleGroupArrayList;
        networkingManager = ((MyApp)getApplication()).networkingManager;
        networkingManager.listener = this;

        networkingManager.getMuscleGroups();
        adapter = new MuscleGroupRecyclerAdapter(this,muscleGroupArrayList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.muscleGroupClickListener = this;
    }

    @Override
    public void gotListResultFromAPI(String jsonResponse) {
        muscleGroupArrayList = jsonManager.fromJSONToArrayOfMuscleGroups(jsonResponse);
        ((MyApp) getApplication()).muscleGroupArrayList = muscleGroupArrayList;
        adapter.muscleGroupsList = muscleGroupArrayList;
        Log.d("gotMuscleGroupList", muscleGroupArrayList.get(18).getName());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void muscleGroupClicked(MuscleGroup muscleGroup) {
        Intent intent2 = new Intent(MuscleGroupActivity.this,ExercisesForMuscleGroupActivity.class);
        ((MyApp)getApplication()).currentMuscleGroup = muscleGroup.getName();
        //intent2.putExtra("muscleGroup",muscleGroup.getName());
        startActivity(intent2);
    }
}