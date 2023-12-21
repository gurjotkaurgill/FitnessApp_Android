package com.example.finalproject_fitnessapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExercisesRecyclerAdapter extends RecyclerView.Adapter<ExercisesRecyclerAdapter.ExerciseViewHolder> {

    protected class ExerciseViewHolder extends RecyclerView.ViewHolder{

        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    interface ExerciseClickListener {
        void exerciseClicked(Exercise exercise);
    }

    Context context;
    ArrayList<Exercise> exercises;
    ExerciseClickListener exerciseClickListener;

    public ExercisesRecyclerAdapter(Context context, ArrayList<Exercise> exercises) {
        this.context = context;
        this.exercises = exercises;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.exercise_row_layout,parent,false);
        return new ExercisesRecyclerAdapter.ExerciseViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        TextView upperRowText = holder.itemView.findViewById(R.id.upperRowTextView);
        TextView lowerRowText = holder.itemView.findViewById(R.id.lowerRowTextView);
        upperRowText.setText(exercises.get(position).getName().toUpperCase());
        String equipment = exercises.get(position).getEquipment();
        //change first character to uppercase
        equipment = equipment.substring(0, 1).toUpperCase() + equipment.substring(1).toLowerCase();
        lowerRowText.setText(equipment);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exerciseClickListener.exerciseClicked(exercises.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }
}