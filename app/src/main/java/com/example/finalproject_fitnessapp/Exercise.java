package com.example.finalproject_fitnessapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Exercise {
    String name;
    String bodyPart;
    String equipment;
    String gifUrl;
    String secondaryMuscles;
    String instructions;

    public Exercise(){}

    public String getName() {
        return name;
    }

    public String getBodyPart() {
        return bodyPart;
    }

    public String getEquipment() {
        return equipment;
    }

    public String getGifUrl() {
        return gifUrl;
    }

    public String getSecondaryMuscles() {
        return secondaryMuscles;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBodyPart(String bodyPart) {
        this.bodyPart = bodyPart;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public void setGifUrl(String gifUrl) {
        this.gifUrl = gifUrl;
    }

    public void setSecondaryMuscles(String secondaryMuscles) {
        this.secondaryMuscles = secondaryMuscles;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

}