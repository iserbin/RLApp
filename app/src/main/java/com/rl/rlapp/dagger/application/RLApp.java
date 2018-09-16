package com.rl.rlapp.dagger.application;

import android.app.Application;

public class RLApp extends Application {

    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .build();
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }
}
