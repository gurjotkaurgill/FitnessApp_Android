package com.example.finalproject_fitnessapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONManager {

    ArrayList<MuscleGroup> fromJSONToArrayOfMuscleGroups(String json){
        ArrayList<MuscleGroup> muscleGroupsList = new ArrayList<>(0);
        try {
            JSONArray jsonArray = new JSONArray(json);
            for(int i=0;i<jsonArray.length();i++){
                muscleGroupsList.add(new MuscleGroup(jsonArray.get(i).toString()));
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return muscleGroupsList;
    }

    //[] - array, {} - object
    ArrayList<Exercise> fromJSONToArrayOfExercises(String json){
        ArrayList<Exercise> exercisesList = new ArrayList<>(0);
        try {
            JSONArray jsonArray = new JSONArray(json);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Exercise exercise = new Exercise();
                exercise.setBodyPart(jsonObject.getString("bodyPart"));
                exercise.setEquipment(jsonObject.getString("equipment"));
                exercise.setGifUrl(jsonObject.getString("gifUrl"));
                exercise.setName(jsonObject.getString("name"));
                JSONArray secMusclesArray = jsonObject.getJSONArray("secondaryMuscles");
                String secMuscles = "";
                for(int j=0;j<secMusclesArray.length();j++){
                    secMuscles+=secMusclesArray.get(j).toString() + ", ";
                }
                //remove last comma and space
                secMuscles = secMuscles.substring(0, secMuscles.length() - 2);
                exercise.setSecondaryMuscles(secMuscles);
                JSONArray instructionsArray = jsonObject.getJSONArray("instructions");
                String instructions = "";
                for(int j=0;j<instructionsArray.length();j++){
                    instructions+=instructionsArray.get(j).toString() + "\n";
                }
                exercise.setInstructions(instructions);
                exercisesList.add(exercise);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return exercisesList;
    }
}
