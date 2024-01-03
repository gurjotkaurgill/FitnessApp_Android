package com.example.finalproject_fitnessapp.RecyclerAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.finalproject_fitnessapp.Models.Exercise;
import com.example.finalproject_fitnessapp.R;
import java.util.ArrayList;

public class ExercisesRecyclerAdapter extends RecyclerView.Adapter<ExercisesRecyclerAdapter.ExerciseViewHolder> {

    protected static class ExerciseViewHolder extends RecyclerView.ViewHolder{

        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public interface ExerciseClickListener {
        void exerciseClicked(Exercise exercise);
    }

    Context context;
    public ArrayList<Exercise> exercises;
    public ExerciseClickListener exerciseClickListener;
    private final int checked;

    public ExercisesRecyclerAdapter(Context context, ArrayList<Exercise> exercises, int checked) {
        this.context = context;
        this.exercises = exercises;
        this.checked = checked;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.exercise_row_layout,parent,false);
        return new ExerciseViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        TextView upperRowText = holder.itemView.findViewById(R.id.upperRowTextView);
        TextView lowerRowText = holder.itemView.findViewById(R.id.lowerRowTextView);
        upperRowText.setText(exercises.get(position).getName().toUpperCase());

        String equipment = exercises.get(position).getEquipment();
        //change first character to uppercase
        equipment = equipment.substring(0, 1).toUpperCase() + equipment.substring(1).toLowerCase();

        String bodyPart = exercises.get(position).getBodyPart();
        bodyPart = bodyPart.substring(0, 1).toUpperCase() + bodyPart.substring(1).toLowerCase();

        String muscleGroup = exercises.get(position).getTargetMuscle();
        muscleGroup = muscleGroup.substring(0, 1).toUpperCase() + muscleGroup.substring(1).toLowerCase();

        //whatever the chosen target group (out of target muscle, body part and equipment),
        //the other two are also displayed as secondary information
        if(checked == 1) {
            lowerRowText.setText(context.getString(R.string.lowerRowText,bodyPart,equipment));
        }
        else if(checked == 2) {
            lowerRowText.setText(context.getString(R.string.lowerRowText,muscleGroup,equipment));
        }
        else if(checked == 3) {
            lowerRowText.setText(context.getString(R.string.lowerRowText,muscleGroup,bodyPart));
        }
        else {
            lowerRowText.setText(context.getString(R.string.secInfo,muscleGroup,bodyPart,equipment));
        }

        holder.itemView.setOnClickListener(v -> exerciseClickListener.exerciseClicked(exercises.get(holder.getAdapterPosition())));
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }
}