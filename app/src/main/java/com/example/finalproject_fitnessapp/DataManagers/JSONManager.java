package com.example.finalproject_fitnessapp.DataManagers;

import com.example.finalproject_fitnessapp.Models.Exercise;
import com.example.finalproject_fitnessapp.Models.TargetGroup;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class JSONManager {

    public ArrayList<TargetGroup> fromJSONToArrayOfTargetGroups(String json){
        ArrayList<TargetGroup> targetGroupsList = new ArrayList<>(0);
        /*
        This JSON consists of an array of strings (names), e.g.:
        ["abductors","abs","adductors","biceps","calves","cardiovascular system","forearms", ...]
         */
        try {
            JSONArray jsonArray = new JSONArray(json);
            for(int i=0;i<jsonArray.length();i++){
                targetGroupsList.add(new TargetGroup(jsonArray.get(i).toString()));
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return targetGroupsList;
    }

    //[] - array, {} - object
    public ArrayList<Exercise> fromJSONToArrayOfExercises(String json){
        ArrayList<Exercise> exercisesList = new ArrayList<>(0);
        /*
        This JSON is a mix of arrays and objects
        [
           {
              "bodyPart":"neck",
              "equipment":"body weight",
              "gifUrl":"https://v2.exercisedb.io/image/Yrg9bpM0un87c6",
              "id":"1403",
              "name":"neck side stretch",
              "target":"levator scapulae",
              "secondaryMuscles":[
                 "trapezius",
                 "sternocleidomastoid"
              ],
              "instructions":[
                 "Stand or sit up straight with your shoulders relaxed.",
                 "Tilt your head to one side, bringing your ear towards your shoulder.",
                 "Hold the stretch for 15-30 seconds.",
                 "Repeat on the other side.",
                 "Perform 2-4 sets on each side."
              ]
           },
           ...
        ]
         */
        try {
            JSONArray jsonArray = new JSONArray(json);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Exercise exercise = new Exercise();
                exercise.setBodyPart(jsonObject.getString("bodyPart"));
                exercise.setEquipment(jsonObject.getString("equipment"));
                exercise.setGifUrl(jsonObject.getString("gifUrl"));
                exercise.setName(jsonObject.getString("name"));
                exercise.setTargetMuscle(jsonObject.getString("target"));
                JSONArray secMusclesArray = jsonObject.getJSONArray("secondaryMuscles");
                StringBuilder secMuscles = new StringBuilder();
                for(int j=0;j<secMusclesArray.length();j++){
                    secMuscles.append(secMusclesArray.get(j).toString()).append(", ");
                }
                //remove last comma and space
                secMuscles = new StringBuilder(secMuscles.substring(0, secMuscles.length() - 2));
                exercise.setSecondaryMuscles(secMuscles.toString());
                JSONArray instructionsArray = jsonObject.getJSONArray("instructions");
                StringBuilder instructions = new StringBuilder();
                for(int j=0;j<instructionsArray.length();j++){
                    instructions.append("• ").append(instructionsArray.get(j).toString()).append("\n\n");
                }
                exercise.setInstructions(instructions.toString());
                exercisesList.add(exercise);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return exercisesList;
    }
}
