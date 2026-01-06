package com.example.darcaftan.models;

import com.google.gson.annotations.SerializedName;

public class LignePanier {
    @SerializedName("id")
    private int id;

    @SerializedName("panier_id")
    private int panierId;

    @SerializedName("caftan_id")
    private int caftanId;

    @SerializedName("quantite")
    private int quantite;

    @SerializedName("date_debut")
    private String dateDebut;

    @SerializedName("date_fin")
    private String dateFin;

    @SerializedName("prix_unitaire")
    private double prixUnitaire;

    @SerializedName("caftan")
    private Caftan caftan;

    // Getters
    public int getId() {
        return id;
    }

    public int getPanierId() {
        return panierId;
    }

    public int getCaftanId() {
        return caftanId;
    }

    public int getQuantite() {
        return quantite;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public String getDateFin() {
        return dateFin;
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

    public void setPanierId(int panierId) {
        this.panierId = panierId;
    }

    public void setCaftanId(int caftanId) {
        this.caftanId = caftanId;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public void setPrixUnitaire(double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public void setCaftan(Caftan caftan) {
        this.caftan = caftan;
    }
}
