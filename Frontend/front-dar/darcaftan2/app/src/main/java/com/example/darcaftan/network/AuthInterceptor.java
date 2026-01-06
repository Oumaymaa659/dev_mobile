package com.example.darcaftan.network;

import android.content.Context;

import com.example.darcaftan.utils.TokenManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    private TokenManager tokenManager;

    public AuthInterceptor(Context context) {
        this.tokenManager = new TokenManager(context);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        // Si on a un token, on l'ajoute à la requête
        String token = tokenManager.getToken();
        if (token != null && !token.isEmpty()) {
            Request newRequest = originalRequest.newBuilder()
                    .header("Authorization", "Bearer " + token)
                    .header("Accept", "application/json")
                    .build();
            return chain.proceed(newRequest);
        }

        // Sinon, on envoie la requête sans modification
        Request newRequest = originalRequest.newBuilder()
                .header("Accept", "application/json")
                .build();
        return chain.proceed(newRequest);
    }
}
