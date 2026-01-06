package com.example.darcaftan;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class SoireeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soiree);

        // Bouton retour
        ImageButton btnRetour = findViewById(R.id.btn_retour);
        if (btnRetour != null) {
            btnRetour.setOnClickListener(v -> finish());
        }

        // Images
        ImageView img1 = findViewById(R.id.caftan1);
        ImageView img2 = findViewById(R.id.caftan2);
        ImageView img3 = findViewById(R.id.caftan3);
        ImageView img4 = findViewById(R.id.caftan4);


        // Ajout des clics
        setupClick(img1, R.drawable.caftan1, "Caftan Soirée 1", "1500 DH", true, "Collection Soirée");
        setupClick(img2, R.drawable.caftan2, "Caftan Soirée 2", "1800 DH", true, "Collection Soirée");
        setupClick(img3, R.drawable.caftan3, "Caftan Soirée 3", "2000 DH", false, "Collection Soirée");
        setupClick(img4, R.drawable.caftan4, "Caftan Soirée 4", "1700 DH", true, "Collection Soirée");

    }

    private void setupClick(ImageView image, int resId, String titre, String prix, boolean dispo, String collection) {
        if (image != null) {
            image.setOnClickListener(v -> {
                Intent intent = new Intent(SoireeActivity.this, PhotoDetailActivity.class);
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
