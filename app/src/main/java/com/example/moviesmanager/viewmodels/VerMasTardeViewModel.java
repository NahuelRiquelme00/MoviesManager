package com.example.moviesmanager.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class VerMasTardeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public VerMasTardeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is ver mas tarde fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}