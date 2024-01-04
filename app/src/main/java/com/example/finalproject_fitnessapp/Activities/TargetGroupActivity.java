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
import com.example.finalproject_fitnessapp.Models.TargetGroup;
import com.example.finalproject_fitnessapp.MyApp;
import com.example.finalproject_fitnessapp.R;
import com.example.finalproject_fitnessapp.RecyclerAdapters.TargetGroupRecyclerAdapter;
import java.util.ArrayList;

public class TargetGroupActivity extends AppCompatActivity
        implements NetworkingManager.GotResultFromAPI,
        TargetGroupRecyclerAdapter.TargetGroupClickListener
{
    NetworkingManager networkingManager;
    JSONManager jsonManager;
    ArrayList<TargetGroup> targetGroupArrayList;
    TargetGroupRecyclerAdapter adapter;
    RecyclerView recyclerView;
    private SearchView mySearchView;
    private String searchText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muscle_group);
        this.setTitle(getString(R.string.bodyParts));

        recyclerView = findViewById(R.id.muscleGroupList);

        jsonManager = ((MyApp)getApplication()).jsonManager;
        targetGroupArrayList = ((MyApp)getApplication()).targetGroupArrayList;
        networkingManager = ((MyApp)getApplication()).networkingManager;
        networkingManager.listener = this;

        if(savedInstanceState != null) {
            //the list was already fetched - save an API call by using it again
            targetGroupArrayList = savedInstanceState.getParcelableArrayList("list");
            searchText = savedInstanceState.getString("searchText");
        }
        else {
            //the activity is created first time
            int checked = ((MyApp) getApplication()).optionChecked;
            if (checked == 1) {
                this.setTitle(getString(R.string.muscleGroups));
                networkingManager.getMuscleGroups();
            }
            if (checked == 2) {
                this.setTitle(getString(R.string.bodyParts));
                networkingManager.getBodyParts();
            }
            if (checked == 3) {
                this.setTitle(getString(R.string.equipments));
                networkingManager.getEquipment();
            }
        }
        adapter = new TargetGroupRecyclerAdapter(this, targetGroupArrayList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.targetGroupClickListener = this;
    }

    @Override
    public void gotListResultFromAPI(String jsonResponse) {
        //got target groups from API as JSON - convert them to TargetGroup objects
        targetGroupArrayList = jsonManager.fromJSONToArrayOfTargetGroups(jsonResponse);
        ((MyApp) getApplication()).targetGroupArrayList = targetGroupArrayList;
        adapter.targetGroupsList = targetGroupArrayList;
        adapter.notifyDataSetChanged();
    }

    @Override
    public void targetGroupClicked(TargetGroup targetGroup) {
        Intent intent2 = new Intent(TargetGroupActivity.this, ExercisesForTargetGroupActivity.class);
        ((MyApp)getApplication()).currentTargetGroupSelected = targetGroup.getName();
        startActivity(intent2);
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
                    adapter.targetGroupsList = targetGroupArrayList;
                    adapter.notifyDataSetChanged();
                }
                return false;
            }
        });
        return true;
    }

    private void filterList(String searchQuery) {
        //search the current list of target groups for the searchQuery specified
        ArrayList<TargetGroup> filteredList = new ArrayList<>(0);
        for(TargetGroup item : targetGroupArrayList) {
            if(item.getName().toLowerCase().contains(searchQuery.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapter.targetGroupsList = filteredList;
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menuHome){
            Intent homeIntent = new Intent(TargetGroupActivity.this,MainActivity.class);
            startActivity(homeIntent);
            return true;
        }
        else
            return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        //this is used to save an API call and make the loading of the list faster
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("list",targetGroupArrayList);
        searchText = mySearchView.getQuery().toString();
        outState.putString("searchText", searchText);
    }

}