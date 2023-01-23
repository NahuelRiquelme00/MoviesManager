package com.example.moviesmanager.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DetalleViewModel extends ViewModel {

    private final MutableLiveData<String> mTitulo;


    public DetalleViewModel() {
        mTitulo = new MutableLiveData<>();
        mTitulo.setValue("This is detalle fragment");
    }

    public LiveData<String> getText() {
        return mTitulo;
    }
}