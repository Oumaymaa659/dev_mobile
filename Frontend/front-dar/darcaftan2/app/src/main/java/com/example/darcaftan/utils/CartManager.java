package com.example.darcaftan.utils;

import com.example.darcaftan.models.CartItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Gestionnaire singleton pour le panier
 * Gère l'ajout, la suppression et la récupération des articles du panier
 */
public class CartManager {
    private static CartManager instance;
    private List<CartItem> cartItems;

    private CartManager() {
        cartItems = new ArrayList<>();
    }

    /**
     * Obtenir l'instance unique du CartManager
     */
    public static synchronized CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    /**
     * Ajouter un article au panier
     */
    public void addItem(CartItem item) {
        if (item != null) {
            cartItems.add(item);
        }
    }

    /**
     * Supprimer un article du panier à une position donnée
     */
    public void removeItem(int position) {
        if (position >= 0 && position < cartItems.size()) {
            cartItems.remove(position);
        }
    }

    /**
     * Supprimer un article spécifique du panier
     */
    public void removeItem(CartItem item) {
        cartItems.remove(item);
    }

    /**
     * Obtenir tous les articles du panier
     */
    public List<CartItem> getItems() {
        return cartItems;
    }

    /**
     * Obtenir le nombre d'articles dans le panier
     */
    public int getItemCount() {
        return cartItems.size();
    }

    /**
     * Vérifier si le panier est vide
     */
    public boolean isEmpty() {
        return cartItems.isEmpty();
    }

    /**
     * Vider le panier
     */
    public void clear() {
        cartItems.clear();
    }

    /**
     * Calculer le total du panier
     */
    public double getTotal() {
        double total = 0.0;
        for (CartItem item : cartItems) {
            total += item.getPrixNumerique();
        }
        return total;
    }

    /**
     * Obtenir le total formaté en DH
     */
    public String getTotalFormatted() {
        return String.format("%.2f DH", getTotal());
    }
}
