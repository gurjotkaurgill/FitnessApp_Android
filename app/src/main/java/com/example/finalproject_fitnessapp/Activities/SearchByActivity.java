package com.example.finalproject_fitnessapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.example.finalproject_fitnessapp.MyApp;
import com.example.finalproject_fitnessapp.R;

public class SearchByActivity extends AppCompatActivity {
    RadioGroup radioGroup;
    Button exploreButton;
    RadioButton muscleGroupRB, bodyPartRB, equipmentRB, nameRB;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by);

        radioGroup = findViewById(R.id.radioGroup);
        exploreButton = findViewById(R.id.exploreBtn);
        muscleGroupRB = findViewById(R.id.targetMuscleRB);
        bodyPartRB = findViewById(R.id.targetBPRB);
        equipmentRB = findViewById(R.id.equipmentRB);
        nameRB = findViewById(R.id.exerciseNameRB);

        //preserve the state of which button was chosen first
        if(((MyApp)getApplication()).optionChecked == 1){
            muscleGroupRB.setChecked(true);
        }
        else if(((MyApp)getApplication()).optionChecked == 2){
            bodyPartRB.setChecked(true);
        }
        else if(((MyApp)getApplication()).optionChecked == 3){
            equipmentRB.setChecked(true);
        }
        else {
            nameRB.setChecked(true);
        }

        exploreButton.setOnClickListener(v -> {
            if(muscleGroupRB.isChecked()){
                ((MyApp)getApplication()).optionChecked = 1;
                Intent intent = new Intent(SearchByActivity.this, TargetGroupActivity.class);
                startActivity(intent);
            }
            else if(bodyPartRB.isChecked()) {
                ((MyApp)getApplication()).optionChecked = 2;
                Intent intent = new Intent(SearchByActivity.this, TargetGroupActivity.class);
                startActivity(intent);
            }
            else if(equipmentRB.isChecked()) {
                ((MyApp)getApplication()).optionChecked = 3;
                Intent intent = new Intent(SearchByActivity.this, TargetGroupActivity.class);
                startActivity(intent);
            }
            else {
                ((MyApp)getApplication()).optionChecked = 4;
                Intent intent = new Intent(SearchByActivity.this,SearchByNameActivity.class);
                startActivity(intent);
            }
        });
    }

    //hide search button from menu
    @Override
    public void invalidateOptionsMenu() {
        super.invalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        menu.getItem(0).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menuHome){
            finish();
            return true;
        }
        else
            return super.onOptionsItemSelected(item);
    }

}