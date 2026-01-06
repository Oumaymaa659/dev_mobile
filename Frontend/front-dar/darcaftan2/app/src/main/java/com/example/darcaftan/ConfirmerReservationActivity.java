package com.example.darcaftan;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public class ConfirmerReservationActivity extends AppCompatActivity {

    private Button btnConfirmer;
    private final String BASE_URL = "http://10.0.2.2:8000/api/confirmer/";
    private int reservationId; // ID de la réservation à confirmer

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_customer_info);

        // Récupérer l'ID de la réservation depuis l'activité précédente
        reservationId = getIntent().getIntExtra("reservationId", -1);
        if (reservationId == -1) {
            Toast.makeText(this, "ID de réservation invalide", Toast.LENGTH_SHORT).show();
            finish(); // fermer l'activité si ID invalide
            return;
        }

        // Lier le bouton XML
        btnConfirmer = findViewById(R.id.btn_confirmer_commande); // Assure-toi que c'est le bon ID dans ton XML

        // Définir l'action au clic
        btnConfirmer.setOnClickListener(v -> confirmerReservation(reservationId));
    }

    /**
     * Méthode pour confirmer la réservation via API Laravel
     *
     * @param id ID de la réservation
     */
    private void confirmerReservation(int id){
        // Créer la file d'attente Volley
        RequestQueue queue = Volley.newRequestQueue(this);

        // Construire l'URL complète
        String url = BASE_URL + id + "/confirmer";

        // Créer la requête PUT
        JsonObjectRequest request = new JsonObjectRequest(
            Request.Method.PUT,
            url,
            null, // pas de corps JSON nécessaire
            response -> {
                // Succès : afficher un message
                Toast.makeText(this, "Réservation confirmée avec succès", Toast.LENGTH_SHORT).show();
                // Optionnel : fermer l'activité après confirmation
                finish();
            },
            error -> {
                // Afficher l'erreur détaillée
                String message = (error.getMessage() != null) ? error.getMessage() : "Erreur inconnue";
                Toast.makeText(this, "Erreur lors de la confirmation : " + message, Toast.LENGTH_LONG).show();
            }
        );

        // Ajouter la requête à la file d'attente
        queue.add(request);
    }
}
