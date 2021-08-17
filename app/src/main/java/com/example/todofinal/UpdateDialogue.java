package com.example.todofinal;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateDialogue {
    private String key = "";

    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String onlineUserID;
    private Context rootContext;
    private Model taskModel;

    public UpdateDialogue (Context rootContext, Model taskModel) {
        this.rootContext = rootContext;
        this.taskModel = taskModel;
        this.key = taskModel.getId();

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        onlineUserID = mUser.getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("tasks").child(onlineUserID);
    }

    public void updateTask(){
        AlertDialog.Builder myDialog = new AlertDialog.Builder(rootContext);
        LayoutInflater inflater = LayoutInflater.from(rootContext);
        View view = inflater.inflate(R.layout.update_data, null);
        myDialog.setView(view);

        AlertDialog dialog = myDialog.create();

        //make the corner round
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Spinner spinner = (Spinner) view.findViewById(R.id.status_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(rootContext,
                R.array.task_status, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);


        EditText mTask = view.findViewById(R.id.mEditedTask);
        EditText mDescription = view.findViewById(R.id.mEditedDescription);
        EditText mDate = view.findViewById(R.id.mEditedDate);

        mTask.setText(taskModel.getTask());
        mTask.setSelection(taskModel.getTask().length());

        mDescription.setText(taskModel.getDescription());
        mDescription.setSelection(taskModel.getDescription().length());

        mDate.setText(taskModel.getDate());
        mDate.setSelection(taskModel.getDate().length());

        int spinnerPosition = adapter.getPosition(taskModel.getStatus());
        spinner.setSelection(spinnerPosition);

        Button deleteBtn = view.findViewById(R.id.btnDelete);
        Button updateBtn = view.findViewById(R.id.btnUpdate);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String task = mTask.getText().toString().trim();
                String description = mDescription.getText().toString().trim();
                String date = mDate.getText().toString().trim();
                String status = spinner.getSelectedItem().toString();

//                String date = DateFormat.getDateInstance().format(new Date());

                Model model = new Model(task, description, key, date, status);

                reference.child(key).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(rootContext, "Task has been updated successfully", Toast.LENGTH_SHORT).show();
                        } else{
                            String error = task.getException().toString();
                            Toast.makeText(rootContext, "Update failed" + error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                dialog.dismiss();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.child(key).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(rootContext, "Task has been deleted successfully", Toast.LENGTH_SHORT).show();
                        } else{
                            String error = task.getException().toString();
                            Toast.makeText(rootContext, "Delete failed" + error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
