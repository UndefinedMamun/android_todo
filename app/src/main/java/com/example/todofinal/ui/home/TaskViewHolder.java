package com.example.todofinal.ui.home;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;
import com.example.todofinal.R;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

public class TaskViewHolder extends RecyclerView.ViewHolder {
    public View mView;

    public TaskViewHolder(@NonNull View itemView) {
        super(itemView);
        mView = itemView;
    }

    public void setTask(String task){
        TextView taskTextView = mView.findViewById(R.id.taskTv);
        taskTextView.setText(task);
    }

    public void setDesc(String desc){
        TextView descTextView = mView.findViewById(R.id.descriptionTv);
        descTextView.setText(desc);
    }

    public void setDate(String date){
        TextView dateTextView = mView.findViewById(R.id.dateTv);
        dateTextView.setText(date);
    }

    @SuppressLint("ResourceAsColor")
    public  void setStatus(String status) {
        Drawable drawable;
        switch (status){
            case "Done":
                drawable = AppCompatResources.getDrawable(mView.getContext(), R.drawable.background_done);
                mView.setBackground(drawable);
                break;

            case "Canceled":
                drawable = AppCompatResources.getDrawable(mView.getContext(), R.drawable.background_canceled);
                mView.setBackground(drawable);
                break;
        }

    }
}
