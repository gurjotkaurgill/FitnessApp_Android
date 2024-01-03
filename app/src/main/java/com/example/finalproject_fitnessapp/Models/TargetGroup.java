package com.example.finalproject_fitnessapp.Models;

public class TargetGroup {

    String name;

    public TargetGroup(String jsonString) {
        //create TargetGroup object from the JSON string
        String[] allMuscleGroups = jsonString.split(",");
        this.name = allMuscleGroups[0];
    }

    public String getName() {
        return name;
    }

}