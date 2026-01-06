package com.example.darcaftan;

import android.os.Bundle;
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

public class ValiderLaReservationActivity extends AppCompatActivity {

    private EditText quantite, dateDebut, dateFin;
    private Button btnValider, btnAnnuler;
    private final String BASE_URL = "http://10.0.2.2:8000/api/reservations";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_reservation);

        quantite = findViewById(R.id.edit_quantite);
        dateDebut = findViewById(R.id.edit_date_debut);
        dateFin = findViewById(R.id.edit_date_fin);
        btnValider = findViewById(R.id.btn_confirmer);
        btnAnnuler = findViewById(R.id.btn_annuler);

        btnAnnuler.setOnClickListener(v -> finish());

        btnValider.setOnClickListener(v -> {
            String sQuantite = quantite.getText().toString().trim();
            String sDateDebut = dateDebut.getText().toString().trim();
            String sDateFin = dateFin.getText().toString().trim();

            if (sQuantite.isEmpty() || sDateDebut.isEmpty() || sDateFin.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            } else {
                envoyerReservation(sQuantite, sDateDebut, sDateFin);
            }
        });
    }

    private void envoyerReservation(String quantiteStr, String dateDebutStr, String dateFinStr) {
        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject json = new JSONObject();
        try {
            json.put("quantite", quantiteStr);
            json.put("date_debut", dateDebutStr);
            json.put("date_fin", dateFinStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
            Request.Method.POST,
            BASE_URL,
            json,
            response -> {
                Toast.makeText(ValiderLaReservationActivity.this, "Réservation validée", Toast.LENGTH_SHORT).show();
                // Vider les champs après validation
                quantite.setText("");
                dateDebut.setText("");
                dateFin.setText("");
            },
            error -> Toast.makeText(ValiderLaReservationActivity.this, "Erreur: Vérifiez la connexion", Toast.LENGTH_SHORT).show()
        );

        queue.add(request);
    }
}
