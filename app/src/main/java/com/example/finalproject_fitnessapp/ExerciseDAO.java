package com.example.finalproject_fitnessapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.finalproject_fitnessapp.Models.Exercise;
import java.util.List;

@Dao
public interface ExerciseDAO {

    @Insert
    void addExercise(Exercise exercise);

    @Query("select * from Exercise")
    List<Exercise> getExercises();

    @Query("select * from Exercise where name LIKE :word")
    List<Exercise> searchForExercise(String word);

    @Delete
    void removeExercise(Exercise exercise);
}
