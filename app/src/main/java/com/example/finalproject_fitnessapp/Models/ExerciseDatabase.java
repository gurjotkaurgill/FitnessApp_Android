package com.example.finalproject_fitnessapp.Models;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.finalproject_fitnessapp.ExerciseDAO;

@Database(entities = {Exercise.class}, version = 1)
public abstract class ExerciseDatabase extends RoomDatabase {

    public abstract ExerciseDAO getDAO();

}
