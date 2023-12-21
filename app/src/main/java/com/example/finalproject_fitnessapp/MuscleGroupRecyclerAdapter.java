package com.example.finalproject_fitnessapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MuscleGroupRecyclerAdapter extends RecyclerView.Adapter<MuscleGroupRecyclerAdapter.MuscleGroupViewHolder> {

    protected class MuscleGroupViewHolder extends RecyclerView.ViewHolder {
        public MuscleGroupViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    interface MuscleGroupClickListener {
        void muscleGroupClicked(MuscleGroup muscleGroup);
    }

    Context context;
    ArrayList<MuscleGroup> muscleGroupsList;
    MuscleGroupClickListener muscleGroupClickListener;

    public MuscleGroupRecyclerAdapter(Context context, ArrayList<MuscleGroup> muscleGroupsList) {
        this.context = context;
        this.muscleGroupsList = muscleGroupsList;
    }

    @NonNull
    @Override
    public MuscleGroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.row_layout,parent,false);
        return new MuscleGroupViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MuscleGroupViewHolder holder, int position) {
        TextView rowText = holder.itemView.findViewById(R.id.rowTextView);
        rowText.setText(muscleGroupsList.get(position).getName().toUpperCase());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                muscleGroupClickListener.muscleGroupClicked(muscleGroupsList.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return muscleGroupsList.size();
    }

}