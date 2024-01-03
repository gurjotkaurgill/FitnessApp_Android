package com.example.finalproject_fitnessapp.RecyclerAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.finalproject_fitnessapp.Models.TargetGroup;
import com.example.finalproject_fitnessapp.R;
import java.util.ArrayList;

public class TargetGroupRecyclerAdapter extends RecyclerView.Adapter<TargetGroupRecyclerAdapter.TargetGroupViewHolder> {

    protected static class TargetGroupViewHolder extends RecyclerView.ViewHolder {
        public TargetGroupViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public interface TargetGroupClickListener {
        void targetGroupClicked(TargetGroup muscleGroup);
    }

    Context context;
    public ArrayList<TargetGroup> targetGroupsList;
    public TargetGroupClickListener targetGroupClickListener;

    public TargetGroupRecyclerAdapter(Context context, ArrayList<TargetGroup> muscleGroupsList) {
        this.context = context;
        this.targetGroupsList = muscleGroupsList;
    }

    @NonNull
    @Override
    public TargetGroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.target_group_row_layout,parent,false);
        return new TargetGroupViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TargetGroupViewHolder holder, int position) {
        TextView rowText = holder.itemView.findViewById(R.id.rowTextView);
        rowText.setText(targetGroupsList.get(position).getName().toUpperCase());
        holder.itemView.setOnClickListener(v -> targetGroupClickListener.targetGroupClicked(targetGroupsList.get(holder.getAdapterPosition())));
    }

    @Override
    public int getItemCount() {
        return targetGroupsList.size();
    }

}