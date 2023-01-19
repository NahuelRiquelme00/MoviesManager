package com.example.moviesmanager.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RecomendacionesViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public RecomendacionesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is recomendaciones fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}