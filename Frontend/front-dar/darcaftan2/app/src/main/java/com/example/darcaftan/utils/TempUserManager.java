package com.example.darcaftan.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.UUID;

/**
 * Gestionnaire singleton pour les identifiants temporaires des utilisateurs
 * invités
 * Génère et stocke un UUID unique pour identifier le panier temporaire
 */
public class TempUserManager {
    private static TempUserManager instance;
    private static final String PREF_NAME = "DarCaftanPrefs";
    private static final String KEY_TEMP_USER_ID = "temp_user_id";
    private SharedPreferences sharedPreferences;

    private TempUserManager(Context context) {
        sharedPreferences = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    /**
     * Obtenir l'instance unique du TempUserManager
     */
    public static synchronized TempUserManager getInstance(Context context) {
        if (instance == null) {
            instance = new TempUserManager(context);
        }
        return instance;
    }

    /**
     * Obtenir ou générer le temp_user_id
     * Si aucun UUID n'existe, en génère un nouveau et le sauvegarde
     */
    public String getTempUserId() {
        String tempUserId = sharedPreferences.getString(KEY_TEMP_USER_ID, null);

        if (tempUserId == null || tempUserId.isEmpty()) {
            // Générer un nouveau UUID
            tempUserId = UUID.randomUUID().toString();
            // Sauvegarder dans SharedPreferences
            sharedPreferences.edit().putString(KEY_TEMP_USER_ID, tempUserId).apply();
        }

        return tempUserId;
    }

    /**
     * Vérifier si un temp_user_id existe
     */
    public boolean hasTempUserId() {
        String tempUserId = sharedPreferences.getString(KEY_TEMP_USER_ID, null);
        return tempUserId != null && !tempUserId.isEmpty();
    }

    /**
     * Supprimer le temp_user_id (après confirmation de commande réussie)
     */
    public void clearTempUserId() {
        sharedPreferences.edit().remove(KEY_TEMP_USER_ID).apply();
    }

    /**
     * Définir manuellement un temp_user_id (pour tests ou restauration)
     */
    public void setTempUserId(String tempUserId) {
        sharedPreferences.edit().putString(KEY_TEMP_USER_ID, tempUserId).apply();
    }
}
