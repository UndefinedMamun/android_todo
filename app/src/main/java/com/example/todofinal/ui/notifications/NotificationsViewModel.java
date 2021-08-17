package com.example.todofinal.ui.notifications;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class NotificationsViewModel extends ViewModel {
    private FirebaseAuth mAuth;

    private MutableLiveData<String> mText;

    public NotificationsViewModel() {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        mText = new MutableLiveData<>();
        mText.setValue(user.getEmail());
    }

    public LiveData<String> getText() {
        return mText;
    }
}