package com.example.darcaftan;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.cardview.widget.CardView;

public class DashboardActivity extends AppCompatActivity {

    private CardView cardManageCaftans, cardManageOrders;
    private android.widget.Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Initialiser la base de données avec des données de démo (une seule fois)
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SampleDataHelper.insertSampleData(this, dbHelper);

        // Initialiser les vues
        cardManageCaftans = findViewById(R.id.card_manage_caftans);
        cardManageOrders = findViewById(R.id.card_manage_orders);
        btnLogout = findViewById(R.id.btn_logout);

        // Navigation vers Gestion des Caftans
        cardManageCaftans.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, ManageCaftansActivity.class);
            startActivity(intent);
        });

        // Navigation vers Gestion des Commandes
        cardManageOrders.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, OrderManagementActivity.class);
            startActivity(intent);
        });

        // Déconnexion
        btnLogout.setOnClickListener(v -> logout());
    }

    private void logout() {
        // Supprimer toutes les activités précédentes et retourner au login
        Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
