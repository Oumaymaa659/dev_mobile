package com.example.darcaftan.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Location {
    @SerializedName("id")
    private int id;

    @SerializedName("user_id")
    private int userId;

    @SerializedName("date_debut")
    private String dateDebut;

    @SerializedName("date_fin")
    private String dateFin;

    @SerializedName("prix_total")
    private double prixTotal;

    @SerializedName("statut")
    private String statut;

    @SerializedName("lignes")
    private List<LigneLocation> lignes;

    // Getters
    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public double getPrixTotal() {
        return prixTotal;
    }

    public String getStatut() {
        return statut;
    }

    public List<LigneLocation> getLignes() {
        return lignes;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public void setPrixTotal(double prixTotal) {
        this.prixTotal = prixTotal;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public void setLignes(List<LigneLocation> lignes) {
        this.lignes = lignes;
    }
}
