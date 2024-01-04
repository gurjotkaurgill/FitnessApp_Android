package com.example.finalproject_fitnessapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Exercise implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    public int id;
    String name;
    String targetMuscle;
    String bodyPart;
    @ColumnInfo(name="equipment_required")
    String equipment;
    String gifUrl;
    String secondaryMuscles;
    String instructions;

    public String getName() {
        return name;
    }

    public String getTargetMuscle() {
        return targetMuscle;
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

    public void setTargetMuscle(String targetMuscle) {
        this.targetMuscle = targetMuscle;
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

    public Exercise(){}

    protected Exercise(Parcel in) {
        id = in.readInt();
        name = in.readString();
        targetMuscle = in.readString();
        bodyPart = in.readString();
        equipment = in.readString();
        gifUrl = in.readString();
        secondaryMuscles = in.readString();
        instructions = in.readString();
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
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(targetMuscle);
        dest.writeString(bodyPart);
        dest.writeString(equipment);
        dest.writeString(gifUrl);
        dest.writeString(secondaryMuscles);
        dest.writeString(instructions);
    }
}