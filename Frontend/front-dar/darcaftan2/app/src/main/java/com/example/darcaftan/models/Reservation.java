package com.example.darcaftan.models;

public class Reservation {
    private int id;
    private String articleName;
    private double price;   // Ajouter le prix
    private String status;

    public Reservation(int id, String articleName, double price, String status) {
        this.id = id;
        this.articleName = articleName;
        this.price = price;
        this.status = status;
    }
    // Getter
    public double getprice() { return price; }
    public int getId() { return id; }
    public String getArticleName() { return articleName; }

    public String getStatus() { return status; }
}
