package com.example.moviesmanager.network.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class AppExecutors {

    private static AppExecutors instance;

    private final ScheduledExecutorService mNetwork = Executors.newScheduledThreadPool(10);

    public static AppExecutors getInstance(){
        if (instance == null){
            instance = new AppExecutors();
        }
        return instance;
    }

    public ScheduledExecutorService getNetwork(){
        return mNetwork;
    }


}
