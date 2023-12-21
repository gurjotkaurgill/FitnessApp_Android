package com.example.finalproject_fitnessapp;

public class MuscleGroup {

    String name;

    public MuscleGroup(String jsonString) {
        String[] allMuscleGroups = jsonString.split(",");
        this.name = allMuscleGroups[0];
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}