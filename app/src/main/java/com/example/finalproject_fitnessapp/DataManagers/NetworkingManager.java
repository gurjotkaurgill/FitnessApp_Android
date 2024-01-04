package com.example.finalproject_fitnessapp.DataManagers;

import com.example.finalproject_fitnessapp.MyApp;
import java.io.InputStream;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

public class NetworkingManager {

    public interface GotResultFromAPI {
        void gotListResultFromAPI(String jsonResponse);
    }
    public GotResultFromAPI listener;
    private final String apiKey = "ace899a702msh423906df3dc0feep124266jsna5314aec7bb4";
    //private final String apiKey = "8af8d29439mshc4c16b219413487p1c5590jsn81e500c55059";
    private final int limit = 25;
    public void getMuscleGroups(){
        String urlString = "https://exercisedb.p.rapidapi.com/exercises/targetList";
        connect(urlString);
    }

    public void getBodyParts(){
        String urlString = "https://exercisedb.p.rapidapi.com/exercises/bodyPartList";
        connect(urlString);
    }

    public void getEquipment(){
        String urlString = "https://exercisedb.p.rapidapi.com/exercises/equipmentList";
        connect(urlString);
    }

    public void getExercisesForMuscleGroup(String muscleGroupName){
        String urlString = "https://exercisedb.p.rapidapi.com/exercises/target/" + muscleGroupName + "?limit=" + limit;
        connect(urlString);
    }

    public void getExercisesForBodyPart(String bodyPartName){
        String urlString = "https://exercisedb.p.rapidapi.com/exercises/bodyPart/" + bodyPartName + "?limit=" + limit;
        connect(urlString);
    }

    public void getExercisesWithEquipment(String equipmentName){
        String urlString = "https://exercisedb.p.rapidapi.com/exercises/equipment/" + equipmentName + "?limit" + limit;
        connect(urlString);
    }

    public void searchExerciseByName(String query){
        String urlString = "https://exercisedb.p.rapidapi.com/exercises/name/" + query + "?limit=" + limit;
        connect(urlString);
    }

    private void connect(String urlString){
        MyApp.executorService.execute(() -> {
            HttpsURLConnection httpsURLConnection = null;
            try {
                URL urlObject = new URL(urlString);
                httpsURLConnection = (HttpsURLConnection) urlObject.openConnection();
                httpsURLConnection.setRequestProperty("X-RapidAPI-Key",apiKey);
                httpsURLConnection.setRequestProperty("X-RapidAPI-Host","exercisedb.p.rapidapi.com");
                InputStream inputStream = httpsURLConnection.getInputStream();
                StringBuilder buffer = new StringBuilder();
                int v;
                while((v = inputStream.read()) != -1) {
                    buffer.append((char)v);
                }
                String jsonResponse = buffer.toString();
                MyApp.mainHandler.post(() -> listener.gotListResultFromAPI(jsonResponse));
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                httpsURLConnection.disconnect();
            }
        });
    }

}