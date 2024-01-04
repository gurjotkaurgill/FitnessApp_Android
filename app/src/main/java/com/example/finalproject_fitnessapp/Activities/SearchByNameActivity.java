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

public class SearchByNameActivity extends AppCompatActivity
        implements NetworkingManager.GotResultFromAPI,
        ExercisesRecyclerAdapter.ExerciseClickListener {

    RecyclerView recyclerView;
    JSONManager jsonManager;
    NetworkingManager networkingManager;
    ArrayList<Exercise> exerciseArrayList;
    ExercisesRecyclerAdapter adapter;
    int checked;
    private SearchView mySearchView;
    private String searchText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_name);
        this.setTitle(getString(R.string.searchByName));

        recyclerView = findViewById(R.id.exerciseByNameList);
        jsonManager = ((MyApp)getApplication()).jsonManager;
        networkingManager = ((MyApp)getApplication()).networkingManager;
        networkingManager.listener = this;
        checked = ((MyApp)getApplication()).optionChecked;

        exerciseArrayList = new ArrayList<>(0);

        if (savedInstanceState != null) {
            exerciseArrayList = savedInstanceState.getParcelableArrayList("list");
            searchText = savedInstanceState.getString("searchText");
        }

        recyclerView = findViewById(R.id.exerciseByNameList);
        adapter = new ExercisesRecyclerAdapter(SearchByNameActivity.this,exerciseArrayList,checked);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(SearchByNameActivity.this));
        adapter.exerciseClickListener = SearchByNameActivity.this;
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
            adapter.exercises = exerciseArrayList;
            adapter.notifyDataSetChanged();
        }
        mySearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.length()>2){
                    networkingManager.searchExerciseByName(newText);
                }
                else{
                    adapter.exercises = new ArrayList<>(0);
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
            Intent homeIntent = new Intent(SearchByNameActivity.this,MainActivity.class);
            startActivity(homeIntent);
            return true;
        }
        else
            return super.onOptionsItemSelected(item);
    }

    @Override
    public void gotListResultFromAPI(String jsonResponse) {
        //got search results from API as JSON, convert to list of Exercise objects
        exerciseArrayList = jsonManager.fromJSONToArrayOfExercises(jsonResponse);
        adapter.exercises = exerciseArrayList;
        adapter.notifyDataSetChanged();
    }

    @Override
    public void exerciseClicked(Exercise exercise) {
        Intent intent3 = new Intent(SearchByNameActivity.this, ExerciseDetailsActivity.class);
        ((MyApp)getApplication()).currentExercise = exercise;
        startActivity(intent3);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("list",exerciseArrayList);
        searchText = mySearchView.getQuery().toString();
        outState.putString("searchText", searchText);
    }
}