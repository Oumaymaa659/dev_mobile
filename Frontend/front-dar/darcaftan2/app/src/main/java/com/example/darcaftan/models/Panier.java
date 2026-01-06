package com.example.darcaftan.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Panier {
    @SerializedName("id")
    private int id;

    @SerializedName("user_id")
    private int userId;

    @SerializedName("lignes")
    private List<LignePanier> lignes;

    @SerializedName("total")
    private double total;

    // Getters
    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public List<LignePanier> getLignes() {
        return lignes;
    }

    public double getTotal() {
        return total;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setLignes(List<LignePanier> lignes) {
        this.lignes = lignes;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
