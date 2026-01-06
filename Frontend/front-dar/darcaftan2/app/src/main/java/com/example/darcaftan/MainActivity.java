package com.example.darcaftan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    private static final int VIDEO_RESOURCE_ID = R.raw.video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. TOOLBAR + ICONES
        setupToolbarAndListeners();

        // 2. VIDEO
        setupVideoPlayer();

        // 3. CONTENU (bouton historique)
        setupContentListeners();

        // 4. SECTION SOCIALE
        setupSocialSection();

        // 5. LIEN VERS PAGE CONTACT
        TextView contact = findViewById(R.id.nav_contact);
        if (contact != null) {
            contact.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, ContactActivity.class);
                startActivity(intent);
            });
        }

        // 6. LIEN VERS PAGE COLLECTIONS
        TextView collections = findViewById(R.id.nav_collections);
        if (collections != null) {
            collections.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, CollectionsActivity.class);
                startActivity(intent);
            });
        }

        // 7. LIEN HISTORIQUE
        TextView historique = findViewById(R.id.nav_historique);
        if (historique != null) {
            historique.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, HistoriqueActivity.class);
                startActivity(intent);
            });
        }
    }

    // ======================================================
    // ===============  MÉTHODES PERSONNALISÉES  ===========
    // ======================================================

    private void setupToolbarAndListeners() {
        Toolbar toolbar = findViewById(R.id.my_toolbar);

        if (toolbar != null) {
            setSupportActionBar(toolbar);

            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayShowTitleEnabled(false);
            }
        }

        ImageButton searchIcon = findViewById(R.id.icon_search);
        ImageButton profileIcon = findViewById(R.id.icon_profile);
        ImageButton cartIcon = findViewById(R.id.icon_cart);

        // 🔍 Icône recherche → SearchActivity
        if (searchIcon != null) {
            searchIcon.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            });
        }

        // 👤 Icône profil → LoginActivity
        if (profileIcon != null) {
            profileIcon.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            });
        }

        // 🛒 Icône panier → PanierActivity
        if (cartIcon != null) {
            cartIcon.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, PanierActivity.class);
                startActivity(intent);
            });
        }
    }

    private void setupVideoPlayer() {
        VideoView videoView = findViewById(R.id.video_player);

        if (videoView != null) {
            try {
                Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + VIDEO_RESOURCE_ID);
                videoView.setVideoURI(uri);

                videoView.setOnPreparedListener(mp -> {
                    if (mp != null) mp.setLooping(true);
                });

                videoView.setOnErrorListener((mp, what, extra) -> {
                    Toast.makeText(this, "Erreur lors du chargement de la vidéo", Toast.LENGTH_SHORT).show();
                    return true;
                });

                videoView.start();
            } catch (Exception e) {
                Toast.makeText(this, "Impossible de charger la vidéo", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setupContentListeners() {
        // Bouton historique
        Button discoverButton = findViewById(R.id.btn_discover_history);
        if (discoverButton != null) {
            discoverButton.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, HistoriqueActivity.class);
                startActivity(intent);
            });
        }
    }

    private void setupSocialSection() {
        EditText socialHandle = findViewById(R.id.edit_text_social_handle);

        if (socialHandle != null) {
            socialHandle.setOnClickListener(v ->
                    Toast.makeText(this, "Ouverture du compte social @dar_kaftan...", Toast.LENGTH_SHORT).show()
            );
        }
    }
}
