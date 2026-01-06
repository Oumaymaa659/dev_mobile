package com.example.darcaftan;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DURATION = 5000; // 5 secondes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Récupération des vues
        View logo = findViewById(R.id.splashLogo);
        View title = findViewById(R.id.splashTitle);
        View line = findViewById(R.id.splashLine);
        View subtitle = findViewById(R.id.splashSubtitle);
        View tagline = findViewById(R.id.splashTagline);

        // Animation fade-in avec délais progressifs
        fadeIn(logo, 500, 0);
        fadeIn(title, 500, 700);
        fadeIn(line, 500, 1200);
        fadeIn(subtitle, 500, 1700);
        fadeIn(tagline, 500, 2200);

        // Passage à MainActivity après SPLASH_DURATION
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }, SPLASH_DURATION);
    }

    private void fadeIn(View view, int duration, int delay) {
        if (view != null) {
            view.setVisibility(View.VISIBLE); // Rendre la vue visible avant l'animation
            Animation fade = new AlphaAnimation(0f, 1f);
            fade.setDuration(duration);
            fade.setStartOffset(delay);
            fade.setFillAfter(true);
            view.startAnimation(fade);
        }
    }
}
