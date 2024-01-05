package com.example.finalproject_fitnessapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.example.finalproject_fitnessapp.DataManagers.DatabaseManager;
import com.example.finalproject_fitnessapp.Models.Exercise;
import com.example.finalproject_fitnessapp.MyApp;
import com.example.finalproject_fitnessapp.R;
import com.example.finalproject_fitnessapp.RecyclerAdapters.ExercisesRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements DatabaseManager.DatabaseListener,
        ExercisesRecyclerAdapter.ExerciseClickListener{

    FloatingActionButton plusBtn;
    DatabaseManager databaseManager;
    RecyclerView recyclerView;
    ArrayList<Exercise> favExercises;
    ExercisesRecyclerAdapter adapter;
    //Following variables declared here to use in savedInstanceState
    private SearchView mySearchView;
    private String searchText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        favExercises = new ArrayList<>(0);

        plusBtn = findViewById(R.id.addExerciseBtn);
        databaseManager = ((MyApp)getApplication()).databaseManager;
        databaseManager.getDatabaseInstance(this);
        databaseManager.listener = this;

        recyclerView = findViewById(R.id.favList);
        adapter = new ExercisesRecyclerAdapter(this, favExercises,-1);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.exerciseClickListener = this;

        if (savedInstanceState != null) {
            searchText = savedInstanceState.getString("searchText");
        }

        plusBtn.setOnClickListener(v -> {
            Intent intent1 = new Intent(MainActivity.this, SearchByActivity.class);
            startActivity(intent1);
        });

        //on left swipe - delete exercise from database (remove from favorites)
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                databaseManager.deleteExerciseInBGThread(favExercises.get(position));
                favExercises.remove(position);
                adapter.notifyItemRemoved(position);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    //called each time after creating and when the child activities are dismissed
    @Override
    public void onResume() {
        super.onResume();
        if(adapter.exercises.size() == 0)
            databaseManager.getAllFavoriteExercisesInBGThread();
    }

    //to refresh the list with the new changes in database made from exercise details activity
    //(exercise added/ removed)
    @Override
    public void onRestart() {
        super.onRestart();
        //recreate();
        onResume();
    }

    private void searchList(String searchQuery) {
        //search the given text in current list of exercises
        //can be exercise name/ target muscle/ body part or equipment
        ArrayList<Exercise> filteredList = new ArrayList<>(0);
        for(Exercise item : favExercises) {
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

    //to hide home button from the menu
    @Override
    public void invalidateOptionsMenu() {
        super.invalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu,menu);
        menu.getItem(1).setVisible(false);
        this.setTitle(getString(R.string.favExercises));

        mySearchView = (SearchView) menu.findItem(R.id.menuSearch).getActionView();
        MenuItem searchMenuItem = menu.findItem(R.id.menuSearch);

        //focus the SearchView
        if (searchText != null && !searchText.isEmpty()) {
            searchMenuItem.expandActionView();
            mySearchView.setQuery(searchText, true);
            mySearchView.clearFocus();
            searchList(searchText);
        }
        mySearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.length()>0){
                    searchList(newText);
                }
                else{
                    adapter.exercises = favExercises;
                    adapter.notifyDataSetChanged();
                }
                return false;
            }
        });
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void gotList(List<Exercise> list) {
        //got favorite exercises from the database
        favExercises = (ArrayList<Exercise>) list;
        adapter.exercises = favExercises;
        adapter.notifyDataSetChanged();
    }

    @Override
    public void exerciseClicked(Exercise exercise) {
        Intent intent = new Intent(MainActivity.this, ExerciseDetailsActivity.class);
        ((MyApp)getApplication()).currentExercise = exercise;
        intent.putExtra("goto","MainActivity");
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        searchText = mySearchView.getQuery().toString();
        outState.putString("searchText", searchText);
    }
}