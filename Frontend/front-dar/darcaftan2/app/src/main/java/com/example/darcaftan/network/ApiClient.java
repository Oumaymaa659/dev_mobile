package com.example.darcaftan.network;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    // Pour l'émulateur Android, utilisez 10.0.2.2 au lieu de 127.0.0.1
    // Pour un appareil physique, utilisez l'IP locale de votre PC (ex: 192.168.1.X)
    private static final String BASE_URL = "http://10.0.2.2:8000/api/";
    private static Retrofit retrofit = null;
    private static Context appContext = null;

    public static void init(Context context) {
        appContext = context.getApplicationContext();
    }

    public static Retrofit getClient() {
        if (retrofit == null) {
            // Intercepteur de logs pour le débogage
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            // Configuration OkHttp avec intercepteurs
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(loggingInterceptor);

            // Ajouter l'intercepteur d'authentification si le contexte est disponible
            if (appContext != null) {
                httpClient.addInterceptor(new AuthInterceptor(appContext));
            }

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    // Méthode pour changer l'URL de base (utile pour tester avec un appareil
    // physique)
    public static void setBaseUrl(String baseUrl) {
        retrofit = null;
        // Recréer avec la nouvelle URL sera fait au prochain appel de getClient()
    }

    public static String getBaseUrl() {
        return BASE_URL;
    }

    public static Retrofit getRetrofit() {
        return retrofit;
    }

    public static void setRetrofit(Retrofit retrofit) {
        ApiClient.retrofit = retrofit;
    }

    public static Context getAppContext() {
        return appContext;
    }

    public static void setAppContext(Context appContext) {
        ApiClient.appContext = appContext;
    }
}
