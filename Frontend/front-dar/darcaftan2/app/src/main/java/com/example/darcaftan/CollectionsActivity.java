package com.example.darcaftan;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

public class CollectionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collections);

        // Bouton retour
        ImageButton btnRetour = findViewById(R.id.btn_retour);
        if (btnRetour != null) {
            btnRetour.setOnClickListener(v -> finish());
        }

        // Collection Mariage
        View cardMariage = findViewById(R.id.card_mariage);
        if (cardMariage != null) {
            cardMariage.setOnClickListener(v -> {
                Intent intent = new Intent(CollectionsActivity.this, MariageActivity.class);
                startActivity(intent);
            });
        }

        // Collection Broderie
        View cardBroderie = findViewById(R.id.card_broderie);
        if (cardBroderie != null) {
            cardBroderie.setOnClickListener(v -> {
                Intent intent = new Intent(CollectionsActivity.this, BroderieActivity.class);
                startActivity(intent);
            });
        }

        // Collection Soirée
        View cardSoiree = findViewById(R.id.card_soiree);
        if (cardSoiree != null) {
            cardSoiree.setOnClickListener(v -> {
                Intent intent = new Intent(CollectionsActivity.this, SoireeActivity.class);
                startActivity(intent);
            });
        }

        // Collection Royale
        View cardRoyale = findViewById(R.id.card_royale);
        if (cardRoyale != null) {
            cardRoyale.setOnClickListener(v -> {
                Intent intent = new Intent(CollectionsActivity.this, RoyaleActivity.class);
                startActivity(intent);
            });
        }
    }
}

