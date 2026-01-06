package com.example.darcaftan.models;

import com.google.gson.annotations.SerializedName;

public class LigneLocation {
    @SerializedName("id")
    private int id;

    @SerializedName("location_id")
    private int locationId;

    @SerializedName("caftan_id")
    private int caftanId;

    @SerializedName("quantite")
    private int quantite;

    @SerializedName("prix_unitaire")
    private double prixUnitaire;

    @SerializedName("caftan")
    private Caftan caftan;

    // Getters
    public int getId() {
        return id;
    }

    public int getLocationId() {
        return locationId;
    }

    public int getCaftanId() {
        return caftanId;
    }

    public int getQuantite() {
        return quantite;
    }

    public double getPrixUnitaire() {
        return prixUnitaire;
    }

    public Caftan getCaftan() {
        return caftan;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public void setCaftanId(int caftanId) {
        this.caftanId = caftanId;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public void setPrixUnitaire(double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public void setCaftan(Caftan caftan) {
        this.caftan = caftan;
    }
}
