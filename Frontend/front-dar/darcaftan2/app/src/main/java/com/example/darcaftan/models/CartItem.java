package com.example.darcaftan.models;

/**
 * Modèle représentant un article dans le panier
 */
public class CartItem {
    private int imageResId;
    private String titre;
    private String prix;
    private String collection;
    private String taille;
    private String couleur;
    private boolean disponible;
    private int quantite;
    private String dateDebut;
    private String dateFin;

    public CartItem(int imageResId, String titre, String prix, String collection, String taille, boolean disponible) {
        this.imageResId = imageResId;
        this.titre = titre;
        this.prix = prix;
        this.collection = collection;
        this.taille = taille;
        this.disponible = disponible;
        this.couleur = "";
        this.quantite = 1;
        this.dateDebut = "";
        this.dateFin = "";
    }

    public CartItem(int imageResId, String titre, String prix, String collection, String taille, String couleur,
            boolean disponible) {
        this.imageResId = imageResId;
        this.titre = titre;
        this.prix = prix;
        this.collection = collection;
        this.taille = taille;
        this.couleur = couleur;
        this.disponible = disponible;
        this.quantite = 1;
        this.dateDebut = "";
        this.dateFin = "";
    }

    public CartItem(int imageResId, String titre, String prix, String collection, String taille, String couleur,
            boolean disponible, int quantite, String dateDebut, String dateFin) {
        this.imageResId = imageResId;
        this.titre = titre;
        this.prix = prix;
        this.collection = collection;
        this.taille = taille;
        this.couleur = couleur;
        this.disponible = disponible;
        this.quantite = quantite;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    // Getters
    public int getImageResId() {
        return imageResId;
    }

    public String getTitre() {
        return titre;
    }

    public String getPrix() {
        return prix;
    }

    public String getCollection() {
        return collection;
    }

    public String getTaille() {
        return taille;
    }

    public String getCouleur() {
        return couleur;
    }

    public boolean isDisponible() {
        return disponible;
    }

    // Setters
    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public void setTaille(String taille) {
        this.taille = taille;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    /**
     * Extrait le prix numérique de la chaîne de prix
     * Par exemple: "1500 DH" -> 1500.0
     */
    public double getPrixNumerique() {
        try {
            // Extraire les chiffres de la chaîne de prix
            String prixStr = prix.replaceAll("[^0-9.]", "");
            return Double.parseDouble(prixStr);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    @Override
    public String toString() {
        return titre + " - " + prix + " (Taille: " + taille + ")";
    }
}
