package com.example.finalproject_fitnessapp.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.finalproject_fitnessapp.DataManagers.DatabaseManager;
import com.example.finalproject_fitnessapp.Models.Exercise;
import com.example.finalproject_fitnessapp.MyApp;
import com.example.finalproject_fitnessapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;
import java.util.Objects;

public class ExerciseDetailsActivity extends AppCompatActivity
        implements DatabaseManager.DatabaseListener {

    String exerciseName;
    TextView targetField, partOfField, equipmentField, secMusclesField, instructionsField;
    ImageView imageView;
    Exercise exercise;
    FloatingActionButton favButton;
    DatabaseManager databaseManager;
    boolean isFavorite;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_details);

        exercise = ((MyApp)getApplication()).currentExercise;
        exerciseName = exercise.getName();
        //change first character to uppercase
        exerciseName = exerciseName.substring(0, 1).toUpperCase() + exerciseName.substring(1).toLowerCase();
        this.setTitle(exerciseName);

        databaseManager = ((MyApp)getApplication()).databaseManager;
        databaseManager.listener = ExerciseDetailsActivity.this;
        databaseManager.getDatabaseInstance(ExerciseDetailsActivity.this);
        /*search if the exercise is already a favorite
          if yes - then the button shows a filled heart,
          if no - then the button shows the heart outline
        */
        databaseManager.searchForExercisesInBGThread(exerciseName.toLowerCase());

        initializeViewsAndVariables();
        setValues();
        favButton.setOnClickListener(v -> {
            if(isFavorite){
                databaseManager.deleteExerciseInBGThread(exercise);
                favButton.setImageResource(R.drawable.fav_no);
                isFavorite = false;
            }
            else{
                databaseManager.addExerciseToFavoritesInBGThread(exercise);
                favButton.setImageResource(R.drawable.fav_yes);
                isFavorite = true;
            }
        });
    }

    //this activity has multiple parents: MainActivity, ExercisesForTargetGroupActivity
    //and SearchByNameActivity
    //so instead of adding meta data in android manifest for this activity,
    //it is better to dismiss it
    @Nullable
    @Override
    public Intent getSupportParentActivityIntent() {
        return getParentActivityIntentCustom();
    }

    @Nullable
    @Override
    public Intent getParentActivityIntent() {
        return getParentActivityIntentCustom();
    }

    private Intent getParentActivityIntentCustom() {
        Intent intent;
        String goToIntent = Objects.requireNonNull(getIntent().getExtras()).getString("goto");

        //determine which activity is the parent
        if(Objects.requireNonNull(goToIntent).equals("searchByNameActivity")) {
            intent = new Intent(this, SearchByNameActivity.class);
        }
        else if(goToIntent.equals("ExercisesForTargetGroupActivity")) {
            intent = new Intent(this, ExercisesForTargetGroupActivity.class);
        }
        else {
            intent = new Intent(this, MainActivity.class);
        }
        //set flag to reuse the previous activity instead of creating a new activity instance
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return intent;
    }

    private void initializeViewsAndVariables(){
        targetField = findViewById(R.id.targetField);
        partOfField = findViewById(R.id.partOfField);
        equipmentField = findViewById(R.id.equipmentField);
        secMusclesField = findViewById(R.id.secMusclesField);
        instructionsField = findViewById(R.id.instructionsField);
        imageView = findViewById(R.id.demoGIF);
        favButton = findViewById(R.id.floatingActionButton);
    }

    private void setValues(){
        //load function works asynchronously
        Glide.with(ExerciseDetailsActivity.this)
                .load(exercise.getGifUrl())
                .placeholder(R.drawable.loading)
                .error(R.drawable.error_pic)
                .into(imageView);
        targetField.setText(exercise.getTargetMuscle());
        partOfField.setText(exercise.getBodyPart());
        equipmentField.setText(exercise.getEquipment());
        secMusclesField.setText(exercise.getSecondaryMuscles());
        instructionsField.setText(exercise.getInstructions());
    }

    //to hide search menu item
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
            Intent homeIntent = new Intent(ExerciseDetailsActivity.this,MainActivity.class);
            startActivity(homeIntent);
            return true;
        }
        else
            return super.onOptionsItemSelected(item);
    }

    @Override
    public void gotList(List<Exercise> list) {
        if (list.size() == 0) {
            //exercise is not a favorite
            isFavorite = false;
            favButton.setImageResource(R.drawable.fav_no);
        }
        else {
            //exercise is a favorite
            isFavorite = true;
            favButton.setImageResource(R.drawable.fav_yes);
        }
    }

}