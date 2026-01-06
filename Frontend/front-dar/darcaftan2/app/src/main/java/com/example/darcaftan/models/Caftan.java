package com.example.darcaftan.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Caftan {
    @SerializedName("id")
    private int id;

    @SerializedName("nom")
    private String nom;

    @SerializedName("description")
    private String description;

    @SerializedName("prix")
    private double prix;

    @SerializedName("taille")
    private String taille;

    @SerializedName("couleur")
    private String couleur;

    @SerializedName("categorie")
    private String categorie;

    @SerializedName("quantite_totale")
    private int quantiteTotale;

    @SerializedName("actif")
    private boolean actif;

    @SerializedName("images")
    private List<ImageCaftan> images;

    // Getters
    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getDescription() {
        return description;
    }

    public double getPrix() {
        return prix;
    }

    public String getTaille() {
        return taille;
    }

    public String getCouleur() {
        return couleur;
    }

    public String getCategorie() {
        return categorie;
    }

    public int getQuantiteTotale() {
        return quantiteTotale;
    }

    public boolean isActif() {
        return actif;
    }

    public List<ImageCaftan> getImages() {
        return images;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public void setTaille(String taille) {
        this.taille = taille;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public void setQuantiteTotale(int quantiteTotale) {
        this.quantiteTotale = quantiteTotale;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public void setImages(List<ImageCaftan> images) {
        this.images = images;
    }
}
