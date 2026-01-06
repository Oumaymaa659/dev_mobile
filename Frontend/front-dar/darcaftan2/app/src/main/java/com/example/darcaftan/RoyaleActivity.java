package com.example.darcaftan;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class RoyaleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_royale);

        // Bouton retour
        ImageButton btnRetour = findViewById(R.id.btn_retour);
        if (btnRetour != null) {
            btnRetour.setOnClickListener(v -> finish());
        }

        // Récupération des images
        ImageView img1 = findViewById(R.id.img_caftan1);
        ImageView img2 = findViewById(R.id.img_caftan2);
        ImageView img3 = findViewById(R.id.img_caftan3);
        ImageView img4 = findViewById(R.id.img_caftan4);

        // Setup des clics pour ouvrir PhotoDetailActivity
        setupClick(img1, R.drawable.caftan1, "Caftan Royale 1", "2500 DH", true, "Collection Royale");
        setupClick(img2, R.drawable.caftan2, "Caftan Royale 2", "2700 DH", true, "Collection Royale");
        setupClick(img3, R.drawable.caftan3, "Caftan Royale 3", "3000 DH", false, "Collection Royale");
        setupClick(img4, R.drawable.caftan4, "Caftan Royale 4", "2800 DH", true, "Collection Royale");
    }

    private void setupClick(ImageView image, int resId, String titre, String prix, boolean dispo, String collection) {
        if (image != null) {
            image.setOnClickListener(v -> {
                Intent intent = new Intent(RoyaleActivity.this, PhotoDetailActivity.class);
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
