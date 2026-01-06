package com.example.darcaftan;

import android.os.Bundle;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class ContactActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        // Bouton retour
        ImageButton btnRetour = findViewById(R.id.btn_retour);
        if (btnRetour != null) {
            btnRetour.setOnClickListener(v -> finish()); // Retour à l'activité précédente
        }
    }
}
