package com.example.finalproject_fitnessapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class TargetGroup implements Parcelable {

    //this class implements Parcelable to allow it to be used in a list in savedInstanceState
    /*
        Target group can be:
        • a muscle group (like biceps, triceps, calves)
        • a body part (like arms, legs)
        • an equipment (band, cable, dumbbell)
     */
    String name;

    public TargetGroup(String jsonString) {
        //create TargetGroup object from the JSON string
        String[] allMuscleGroups = jsonString.split(",");
        this.name = allMuscleGroups[0];
    }

    public String getName() {
        return name;
    }

    protected TargetGroup(Parcel in) {
        name = in.readString();
    }

    public static final Creator<TargetGroup> CREATOR = new Creator<TargetGroup>() {
        @Override
        public TargetGroup createFromParcel(Parcel in) {
            return new TargetGroup(in);
        }

        @Override
        public TargetGroup[] newArray(int size) {
            return new TargetGroup[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
    }
}