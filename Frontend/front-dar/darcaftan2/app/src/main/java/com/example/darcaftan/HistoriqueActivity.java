package com.example.darcaftan;

import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;

public class HistoriqueActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique);

        // Bouton retour
        ImageButton btnRetour = findViewById(R.id.btn_retour);
        if (btnRetour != null) {
            btnRetour.setOnClickListener(v -> finish());
        }

        // Configuration des vidéos
        setupVideos();
    }

    private void setupVideos() {
        // Vidéo 1 - Utilise video1.mp4
        VideoView video1 = findViewById(R.id.video_historique_1);
        if (video1 != null) {
            setupVideoPlayer(video1, getVideoResourceId("video1"));
        }

        // Vidéo 2 - Utilise video2.mp4
        VideoView video2 = findViewById(R.id.video_historique_2);
        if (video2 != null) {
            setupVideoPlayer(video2, getVideoResourceId("video2"));
        }
    }

    // Méthode pour obtenir l'ID de la ressource vidéo avec fallback
    private int getVideoResourceId(String videoName) {
        try {
            int id = getResources().getIdentifier(videoName, "raw", getPackageName());
            if (id != 0) {
                return id;
            }
        } catch (Exception e) {
            // Ignorer
        }
        // Fallback sur video.mp4 si la vidéo spécifique n'existe pas
        return R.raw.video;
    }

    private void setupVideoPlayer(VideoView videoView, int videoResourceId) {
        try {
            Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + videoResourceId);
            videoView.setVideoURI(uri);
            videoView.setOnPreparedListener(mp -> {
                if (mp != null) {
                    mp.setLooping(true);
                    // Désactiver le son (volume à 0)
                    mp.setVolume(0f, 0f);
                }
            });
            videoView.setOnErrorListener((mp, what, extra) -> {
                // Erreur silencieuse - la vidéo utilisera le fallback
                return true;
            });
            videoView.start();
        } catch (Exception e) {
            // Erreur silencieuse
        }
    }
}

