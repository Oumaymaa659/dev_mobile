package com.example.darcaftan;

import android.app.Application;

import com.example.darcaftan.network.ApiClient;

public class DarCaftanApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Initialiser ApiClient avec le contexte de l'application
        ApiClient.init(this);
    }
}
