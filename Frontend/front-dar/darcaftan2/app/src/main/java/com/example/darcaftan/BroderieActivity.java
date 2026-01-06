package com.example.darcaftan;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class BroderieActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broderie);

        // Bouton retour
        ImageButton btnRetour = findViewById(R.id.btn_retour);
        if (btnRetour != null) {
            btnRetour.setOnClickListener(v -> finish());
        }

        // Récupération des images
        ImageView img1 = findViewById(R.id.img_brod1);
        ImageView img2 = findViewById(R.id.img_brod2);
        ImageView img3 = findViewById(R.id.img_brod3);
        ImageView img4 = findViewById(R.id.img_brod4);

        // Setup des clics
        setupClick(img1, R.drawable.caftan1, "Caftan Broderie 1", "1200 DH", true, "Collection Broderie");
        setupClick(img2, R.drawable.caftan2, "Caftan Broderie 2", "1300 DH", true, "Collection Broderie");
        setupClick(img3, R.drawable.caftan3, "Caftan Broderie 3", "1500 DH", false, "Collection Broderie");
        setupClick(img4, R.drawable.caftan4, "Caftan Broderie 4", "1400 DH", true, "Collection Broderie");

    }

    private void setupClick(ImageView image, int resId, String titre, String prix, boolean dispo, String collection) {
        if (image != null) {
            image.setOnClickListener(v -> {
                Intent intent = new Intent(BroderieActivity.this, PhotoDetailActivity.class);
                intent.putExtra("imageResId", resId);
                intent.putExtra("titre", titre);
                intent.putExtra("prix", prix);
                intent.putExtra("disponible", dispo);
                intent.putExtra("collection", collection);
                startActivity(intent);
            });
        }
    }
}
