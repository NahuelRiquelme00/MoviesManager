package com.example.moviesmanager.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class YaVistasViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public YaVistasViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is ya vistas fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}