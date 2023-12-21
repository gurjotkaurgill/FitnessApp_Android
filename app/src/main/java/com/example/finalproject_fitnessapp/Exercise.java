package com.example.finalproject_fitnessapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Exercise implements Parcelable {
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

    protected Exercise(Parcel in) {
        name = in.readString();
        bodyPart = in.readString();
        equipment = in.readString();
        gifUrl = in.readString();
        secondaryMuscles = in.readString();
        instructions = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(bodyPart);
        dest.writeString(equipment);
        dest.writeString(gifUrl);
        dest.writeString(secondaryMuscles);
        dest.writeString(instructions);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Exercise> CREATOR = new Creator<Exercise>() {
        @Override
        public Exercise createFromParcel(Parcel in) {
            return new Exercise(in);
        }

        @Override
        public Exercise[] newArray(int size) {
            return new Exercise[size];
        }
    };

}