package com.example.finalproject_fitnessapp.DataManagers;

import android.content.Context;
import androidx.room.Room;
import com.example.finalproject_fitnessapp.Models.Exercise;
import com.example.finalproject_fitnessapp.Models.ExerciseDatabase;
import com.example.finalproject_fitnessapp.MyApp;
import java.util.List;

public class DatabaseManager {

    public interface DatabaseListener {
        void gotList(List<Exercise> list);
    }
    public DatabaseListener listener;
    ExerciseDatabase db;
    public void getDatabaseInstance(Context context) {
        if(db == null) {
            db = Room.databaseBuilder(context,
                    ExerciseDatabase.class,
                    "database-exercises")
                    .build();
        }
    }

    public void addExerciseToFavoritesInBGThread(Exercise exercise) {
        MyApp.executorService.execute(() -> db.getDAO().addExercise(exercise));
    }

    public void getAllFavoriteExercisesInBGThread() {
        MyApp.executorService.execute(() -> {
            List<Exercise> list = db.getDAO().getExercises();
            MyApp.mainHandler.post(() -> listener.gotList(list));
        });
    }

    public void searchForExercisesInBGThread(String exerciseName) {
        MyApp.executorService.execute(() -> {
            List<Exercise> list = db.getDAO().searchForExercise(exerciseName);
            MyApp.mainHandler.post(() -> listener.gotList(list));
        });
    }

    public void deleteExerciseInBGThread(Exercise exercise) {
        MyApp.executorService.execute(() -> db.getDAO().removeExercise(exercise));
    }

}
