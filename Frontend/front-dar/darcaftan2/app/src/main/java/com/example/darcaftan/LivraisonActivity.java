package com.example.darcaftan;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LivraisonActivity extends AppCompatActivity {

    private EditText nom, email, telephone, adresse;
    private Button btnConfirmer, btnAnnuler;
    private final String BASE_URL = "http://10.0.2.2:8000/api/reservation";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_customer_info);

        nom = findViewById(R.id.edit_nom);
        email = findViewById(R.id.edit_email);
        telephone = findViewById(R.id.edit_telephone);
        adresse = findViewById(R.id.edit_adresse);
        btnConfirmer = findViewById(R.id.btn_confirmer_commande);
        btnAnnuler = findViewById(R.id.btn_annuler);

        btnAnnuler.setOnClickListener(v -> finish());

        btnConfirmer.setOnClickListener(v -> {
            String sNom = nom.getText().toString().trim();
            String sEmail = email.getText().toString().trim();
            String sTel = telephone.getText().toString().trim();
            String sAdresse = adresse.getText().toString().trim();

            if(sNom.isEmpty() || sEmail.isEmpty() || sTel.isEmpty() || sAdresse.isEmpty()){
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            } else {
                envoyerReservation(sNom, sEmail, sTel, sAdresse);
            }
        });
    }

    private void envoyerReservation(String nomStr, String emailStr, String telStr, String adresseStr) {
        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject json = new JSONObject();
        try {
            json.put("nom", nomStr);
            json.put("email", emailStr);
            json.put("telephone", telStr);
            json.put("adresse", adresseStr);
        } catch (JSONException e) { e.printStackTrace(); }

        JsonObjectRequest request = new JsonObjectRequest(
            Request.Method.POST,
            BASE_URL,
            json,
            response -> {
                Toast.makeText(LivraisonActivity.this, "Réservation envoyée", Toast.LENGTH_SHORT).show();

                // Vider les champs EditText de la classe
                nom.setText("");
                email.setText("");
                telephone.setText("");
                adresse.setText("");
            },
            error -> Toast.makeText(LivraisonActivity.this, "Erreur: " + error.getMessage(), Toast.LENGTH_SHORT).show()
        );

        queue.add(request);
    }

}
