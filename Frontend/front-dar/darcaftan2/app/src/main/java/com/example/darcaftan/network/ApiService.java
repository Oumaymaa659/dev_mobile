package com.example.darcaftan.network;

import com.example.darcaftan.models.Caftan;
import com.example.darcaftan.models.User;
import com.example.darcaftan.models.LoginResponse;
import com.example.darcaftan.models.RegisterResponse;
import com.example.darcaftan.models.Location;
import com.example.darcaftan.models.Panier;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
        // ========== Authentification ==========
        @POST("auth/register")
        Call<RegisterResponse> registerUser(@Body User user);

        @POST("auth/login")
        Call<LoginResponse> loginUser(@Body User user);

        @POST("auth/logout")
        Call<Map<String, String>> logoutUser();

        // ========== Caftans ==========
        @GET("caftans")
        Call<List<Caftan>> getCaftans();

        @GET("caftans")
        Call<List<Caftan>> searchCaftans(@Query("search") String search);

        @GET("caftans/{id}")
        Call<Caftan> getCaftan(@Path("id") int id);

        @GET("caftans/{id}/disponibilite")
        Call<Map<String, Object>> checkDisponibilite(
                        @Path("id") int id,
                        @Query("date_debut") String dateDebut,
                        @Query("date_fin") String dateFin,
                        @Query("quantite") int quantite);

        // ========== Locations ==========
        @GET("locations")
        Call<List<Location>> getLocations();

        @POST("locations")
        Call<Location> createLocation(@Body Map<String, Object> locationData);

        @GET("locations/{id}")
        Call<Location> getLocation(@Path("id") int id);

        // ========== Panier ==========
        @GET("panier")
        Call<Panier> getPanier();

        @POST("panier/ajouter")
        Call<Map<String, Object>> ajouterAuPanier(@Body Map<String, Object> item);

        @PUT("panier/lignes/{id}")
        Call<Map<String, Object>> modifierLignePanier(
                        @Path("id") int id,
                        @Body Map<String, Object> data);

        @DELETE("panier/lignes/{id}")
        Call<Map<String, String>> supprimerLignePanier(@Path("id") int id);

        @POST("panier/vider")
        Call<Map<String, String>> viderPanier();

        @POST("panier/confirmer")
        Call<Location> confirmerPanier(@Body Map<String, Object> customerData);

        // ========== Admin - Caftans ==========
        @POST("admin/caftans")
        Call<Caftan> createCaftan(@Body Map<String, Object> caftanData);

        @DELETE("admin/caftans/{id}")
        Call<Map<String, String>> deleteCaftan(@Path("id") int id);

        @GET("admin/caftans")
        Call<List<Caftan>> getAdminCaftans();

        // ========== Admin - Locations ==========
        @GET("admin/locations")
        Call<List<Location>> getAdminLocations();

        @PUT("admin/locations/{id}/confirmer")
        Call<Location> confirmerLocation(@Path("id") int id);

        @PUT("admin/locations/{id}/refuser")
        Call<Location> refuserLocation(@Path("id") int id);
}
