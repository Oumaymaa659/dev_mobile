package com.example.darcaftan;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class MariageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mariage);

        // Bouton retour
        ImageButton btnRetour = findViewById(R.id.btn_retour);
        if (btnRetour != null) {
            btnRetour.setOnClickListener(v -> finish());
        }

        // Images
        ImageView img1 = findViewById(R.id.img_caftan1);
        ImageView img2 = findViewById(R.id.img_caftan2);
        ImageView img3 = findViewById(R.id.img_caftan3);
        ImageView img4 = findViewById(R.id.img_caftan4);
        ImageView img5 = findViewById(R.id.img_caftan5);

        // Setup clics
        setupClick(img1, R.drawable.caftan1, "Caftan Mariage 1", "2500 DH", true, "Collection Mariage");
        setupClick(img2, R.drawable.caftan2, "Caftan Mariage 2", "2700 DH", true, "Collection Mariage");
        setupClick(img3, R.drawable.caftan3, "Caftan Mariage 3", "3000 DH", false, "Collection Mariage");
        setupClick(img4, R.drawable.caftan4, "Caftan Mariage 4", "2800 DH", true, "Collection Mariage");
        setupClick(img5, R.drawable.caftan5, "Caftan Mariage 5", "3200 DH", true, "Collection Mariage");
    }

    private void setupClick(ImageView image, int resId, String titre, String prix, boolean dispo, String collection) {
        if (image != null) {
            image.setOnClickListener(v -> {
                Intent intent = new Intent(MariageActivity.this, PhotoDetailActivity.class);
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
