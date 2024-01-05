package com.example.finalproject_fitnessapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.example.finalproject_fitnessapp.DataManagers.JSONManager;
import com.example.finalproject_fitnessapp.DataManagers.NetworkingManager;
import com.example.finalproject_fitnessapp.Models.Exercise;
import com.example.finalproject_fitnessapp.MyApp;
import com.example.finalproject_fitnessapp.R;
import com.example.finalproject_fitnessapp.RecyclerAdapters.ExercisesRecyclerAdapter;
import java.util.ArrayList;

public class ExercisesForTargetGroupActivity extends AppCompatActivity
        implements NetworkingManager.GotResultFromAPI, ExercisesRecyclerAdapter.ExerciseClickListener
{

    NetworkingManager networkingManager;
    ArrayList<Exercise> exerciseArrayList;
    JSONManager jsonManager;
    String targetGroupName;
    RecyclerView recyclerView;
    ExercisesRecyclerAdapter adapter;
    private SearchView mySearchView;
    private String searchText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises_for_target_group);

        targetGroupName = ((MyApp)getApplication()).currentTargetGroupSelected;

        //change only first character to uppercase
        targetGroupName = targetGroupName.substring(0,1).toUpperCase() + targetGroupName.substring(1).toLowerCase();
        this.setTitle(targetGroupName);

        jsonManager = ((MyApp)getApplication()).jsonManager;
        networkingManager = ((MyApp)getApplication()).networkingManager;

        int checked = ((MyApp) getApplication()).optionChecked;
        exerciseArrayList = new ArrayList<>(0);

        if(savedInstanceState != null) {
            //save a call to API and make the loading of list faster
            exerciseArrayList = savedInstanceState.getParcelableArrayList("list");
            //to save the state of searchView
            searchText = savedInstanceState.getString("searchText");
        }
        else {
            if (checked == 1) {
                networkingManager.getExercisesForMuscleGroup(targetGroupName.toLowerCase());
            }
            if (checked == 2) {
                networkingManager.getExercisesForBodyPart(targetGroupName.toLowerCase());
            }
            if (checked == 3) {
                networkingManager.getExercisesWithEquipment(targetGroupName.toLowerCase());
            }
        }

        networkingManager.listener = this;

        recyclerView = findViewById(R.id.exerciseList);
        adapter = new ExercisesRecyclerAdapter(this,exerciseArrayList,checked);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.exerciseClickListener = this;
    }

    private void filterList(String searchQuery) {
        //search the given text in current list of exercises
        //can be exercise name/ target muscle/ body part or equipment
        ArrayList<Exercise> filteredList = new ArrayList<>(0);
        for(Exercise item : exerciseArrayList) {
            if(item.getName().toLowerCase().contains(searchQuery.toLowerCase()) ||
                    item.getTargetMuscle().toLowerCase().contains(searchQuery.toLowerCase()) ||
                    item.getBodyPart().toLowerCase().contains(searchQuery.toLowerCase()) ||
                    item.getEquipment().toLowerCase().contains(searchQuery.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapter.exercises = filteredList;
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu,menu);
        mySearchView = (SearchView) menu.findItem(R.id.menuSearch).getActionView();
        MenuItem searchMenuItem = menu.findItem(R.id.menuSearch);

        //focus the SearchView
        if (searchText != null && !searchText.isEmpty()) {
            searchMenuItem.expandActionView();
            mySearchView.setQuery(searchText, true);
            mySearchView.clearFocus();
            filterList(searchText);
        }
        mySearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.length()>0){
                    filterList(newText);
                }
                else{
                    adapter.exercises = exerciseArrayList;
                    adapter.notifyDataSetChanged();
                }
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menuHome){
            Intent homeIntent = new Intent(ExercisesForTargetGroupActivity.this,MainActivity.class);
            startActivity(homeIntent);
            return true;
        }
        else
            return super.onOptionsItemSelected(item);
    }

    @Override
    public void gotListResultFromAPI(String jsonResponse) {
        //exercises received from the API as JSON
        //convert them to list of Exercise objects
        exerciseArrayList = jsonManager.fromJSONToArrayOfExercises(jsonResponse);
        adapter.exercises = exerciseArrayList;
        adapter.notifyDataSetChanged();
    }

    @Override
    public void exerciseClicked(Exercise exercise) {
        Intent intent = new Intent(ExercisesForTargetGroupActivity.this, ExerciseDetailsActivity.class);
        ((MyApp)getApplication()).currentExercise = exercise;
        intent.putExtra("goto","ExercisesForTargetGroupActivity");
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("list",exerciseArrayList);
        searchText = mySearchView.getQuery().toString();
        outState.putString("searchText", searchText);
    }

}