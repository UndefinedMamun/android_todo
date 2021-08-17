package com.example.todofinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {
    private EditText RegEmail, RegPwd;
    private Button RegBtn;
    private FirebaseAuth mAuth;

    private ProgressDialog loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();
        loader = new ProgressDialog(this);

        RegEmail = findViewById(R.id.RegistrationEmail);
        RegPwd = findViewById(R.id.RegistrationPassword);
        RegBtn = findViewById(R.id.RegistrationButton);
    }



    public void goLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void doRegistration(View view) {
        String email = RegEmail.getText().toString().trim();
        String password = RegPwd.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            RegEmail.setError("A valid email is required");
            return;
        }
        if (TextUtils.isEmpty(password)){
            RegPwd.setError("A valid password is required");
            return;
        } else{
            loader.setMessage("Registration in progress");
            loader.setCanceledOnTouchOutside(false);
            loader.show();
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){
                        Intent intent = new Intent(RegistrationActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else{
                        String error = task.getException().toString();
                        Toast.makeText(RegistrationActivity.this, "Registration failed" + error, Toast.LENGTH_SHORT).show();
                    }
                    loader.dismiss();
                }
            });
        }

    }
}