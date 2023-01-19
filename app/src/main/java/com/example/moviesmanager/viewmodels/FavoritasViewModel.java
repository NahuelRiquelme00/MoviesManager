package com.example.moviesmanager.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FavoritasViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public FavoritasViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is busqueda fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}