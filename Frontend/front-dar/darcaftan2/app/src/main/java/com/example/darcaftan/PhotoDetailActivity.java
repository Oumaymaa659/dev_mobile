package com.example.darcaftan;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.darcaftan.models.CartItem;
import com.example.darcaftan.network.ApiClient;
import com.example.darcaftan.network.ApiService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotoDetailActivity extends AppCompatActivity {

    private int imageResId;
    private String titre;
    private String prix;
    private boolean disponible;
    private String collection;
    private int caftanId = 0; // ID du caftan depuis le backend (0 par défaut si non fourni)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);

        // Récupérer les données passées depuis l'activité précédente
        Intent intent = getIntent();
        imageResId = intent.getIntExtra("imageResId", R.drawable.caftan1);
        titre = intent.getStringExtra("titre");
        prix = intent.getStringExtra("prix");
        disponible = intent.getBooleanExtra("disponible", true);
        collection = intent.getStringExtra("collection");
        caftanId = intent.getIntExtra("caftanId", 0); // Récupérer l'ID si fourni

        // Initialiser les vues
        ImageView imageView = findViewById(R.id.image_detail);
        TextView titreView = findViewById(R.id.titre_detail);
        TextView prixView = findViewById(R.id.prix_detail);
        TextView disponibiliteView = findViewById(R.id.disponibilite_detail);
        Button btnReservation = findViewById(R.id.btn_reservation);
        ImageButton btnRetour = findViewById(R.id.btn_retour);

        // Afficher les données
        if (imageView != null) {
            imageView.setImageResource(imageResId);
        }

        if (titreView != null && titre != null) {
            titreView.setText(titre);
        }

        if (prixView != null && prix != null) {
            prixView.setText(prix);
        }

        if (disponibiliteView != null) {
            if (disponible) {
                disponibiliteView.setText("Disponible");
                disponibiliteView.setTextColor(getResources().getColor(R.color.primary_gold));
            } else {
                disponibiliteView.setText("Non disponible");
                disponibiliteView.setTextColor(getResources().getColor(R.color.text_gray));
            }
        }

        // Bouton retour
        if (btnRetour != null) {
            btnRetour.setOnClickListener(v -> finish());
        }

        // Bouton réservation - Afficher le dialog
        if (btnReservation != null) {
            btnReservation.setOnClickListener(v -> showReservationDialog());

            // Désactiver le bouton si non disponible
            if (!disponible) {
                btnReservation.setEnabled(false);
                btnReservation.setAlpha(0.5f);
            }
        }
    }

    /**
     * Affiche le dialog de réservation avec formulaire
     */
    private void showReservationDialog() {
        // Créer le dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_reservation, null);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        // Récupérer les vues du dialog
        TextView txtCaftanNom = dialogView.findViewById(R.id.txt_caftan_nom);
        EditText editQuantite = dialogView.findViewById(R.id.edit_quantite);
        EditText editDateDebut = dialogView.findViewById(R.id.edit_date_debut);
        EditText editDateFin = dialogView.findViewById(R.id.edit_date_fin);
        Button btnAnnuler = dialogView.findViewById(R.id.btn_annuler);
        Button btnConfirmer = dialogView.findViewById(R.id.btn_confirmer);

        // Afficher le nom du caftan
        if (txtCaftanNom != null && titre != null) {
            txtCaftanNom.setText(titre);
        }

        // Calendrier pour gérer les dates
        final Calendar calendar = Calendar.getInstance();
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        final SimpleDateFormat displayFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        // Variables pour stocker les dates sélectionnées
        final String[] dateDebut = { "" };
        final String[] dateFin = { "" };

        // DatePicker pour date de début
        editDateDebut.setOnClickListener(v -> {
            Calendar minDate = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    PhotoDetailActivity.this,
                    (view, year, month, dayOfMonth) -> {
                        calendar.set(year, month, dayOfMonth);
                        dateDebut[0] = dateFormatter.format(calendar.getTime());
                        editDateDebut.setText(displayFormatter.format(calendar.getTime()));
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));
            // Ne pas permettre de sélectionner une date passée
            datePickerDialog.getDatePicker().setMinDate(minDate.getTimeInMillis());
            datePickerDialog.show();
        });

        // DatePicker pour date de fin
        editDateFin.setOnClickListener(v -> {
            Calendar minDate = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    PhotoDetailActivity.this,
                    (view, year, month, dayOfMonth) -> {
                        calendar.set(year, month, dayOfMonth);
                        dateFin[0] = dateFormatter.format(calendar.getTime());
                        editDateFin.setText(displayFormatter.format(calendar.getTime()));
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));
            // Ne pas permettre de sélectionner une date passée
            datePickerDialog.getDatePicker().setMinDate(minDate.getTimeInMillis());
            datePickerDialog.show();
        });

        // Bouton Annuler
        btnAnnuler.setOnClickListener(v -> dialog.dismiss());

        // Bouton Confirmer
        btnConfirmer.setOnClickListener(v -> {
            // Valider les données
            String quantiteStr = editQuantite.getText().toString().trim();

            if (quantiteStr.isEmpty()) {
                Toast.makeText(this, "Veuillez entrer une quantité", Toast.LENGTH_SHORT).show();
                return;
            }

            int quantite;
            try {
                quantite = Integer.parseInt(quantiteStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Quantité invalide", Toast.LENGTH_SHORT).show();
                return;
            }

            if (quantite <= 0) {
                Toast.makeText(this, "La quantité doit être supérieure à 0", Toast.LENGTH_SHORT).show();
                return;
            }

            if (dateDebut[0].isEmpty()) {
                Toast.makeText(this, "Veuillez sélectionner une date de début", Toast.LENGTH_SHORT).show();
                return;
            }

            if (dateFin[0].isEmpty()) {
                Toast.makeText(this, "Veuillez sélectionner une date de fin", Toast.LENGTH_SHORT).show();
                return;
            }

            // Vérifier que la date de fin est après la date de début
            try {
                Calendar calDebut = Calendar.getInstance();
                Calendar calFin = Calendar.getInstance();
                calDebut.setTime(dateFormatter.parse(dateDebut[0]));
                calFin.setTime(dateFormatter.parse(dateFin[0]));

                if (calFin.before(calDebut) || calFin.equals(calDebut)) {
                    Toast.makeText(this, "La date de fin doit être après la date de début", Toast.LENGTH_SHORT).show();
                    return;
                }
            } catch (Exception e) {
                Toast.makeText(this, "Erreur dans les dates", Toast.LENGTH_SHORT).show();
                return;
            }

            // Données valides - Ajouter au panier
            ajouterAuPanier(quantite, dateDebut[0], dateFin[0], dialog);
        });

        dialog.show();
    }

    /**
     * Ajoute le caftan au panier via l'API
     */
    private void ajouterAuPanier(int quantite, String dateDebut, String dateFin, AlertDialog dialog) {
        // Si pas d'ID caftan (données statiques), ajouter directement au CartManager
        // local
        if (caftanId == 0) {
            ajouterAuPanierLocal(quantite, dateDebut, dateFin);
            dialog.dismiss();
            return;
        }

        // Sinon, appeler l'API
        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        Map<String, Object> requestData = new HashMap<>();
        requestData.put("caftan_id", caftanId);
        requestData.put("quantite", quantite);
        requestData.put("date_debut", dateDebut);
        requestData.put("date_fin", dateFin);

        Call<Map<String, Object>> call = apiService.ajouterAuPanier(requestData);
        call.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                if (response.isSuccessful()) {
                    // Ajouter aussi au panier local pour affichage
                    ajouterAuPanierLocal(quantite, dateDebut, dateFin);

                    Toast.makeText(PhotoDetailActivity.this, "✓ Ajouté au panier avec succès !", Toast.LENGTH_SHORT)
                            .show();
                    dialog.dismiss();
                } else {
                    // Gérer les erreurs
                    String errorMsg = "Erreur lors de l'ajout au panier";
                    if (response.code() == 422) {
                        errorMsg = "Caftan non disponible pour ces dates ou quantité insuffisante";
                    }
                    Toast.makeText(PhotoDetailActivity.this, errorMsg, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                Toast.makeText(PhotoDetailActivity.this, "Erreur de connexion : " + t.getMessage(), Toast.LENGTH_LONG)
                        .show();
            }
        });
    }

    /**
     * Ajoute le caftan au panier local (CartManager)
     */
    private void ajouterAuPanierLocal(int quantite, String dateDebut, String dateFin) {
        CartItem cartItem = new CartItem(
                imageResId,
                titre != null ? titre : "Caftan",
                prix != null ? prix : "0 DH",
                collection != null ? collection : "Collection",
                "M", // Taille par défaut
                "", // Couleur
                disponible,
                quantite,
                dateDebut,
                dateFin);

        com.example.darcaftan.utils.CartManager.getInstance().addItem(cartItem);
    }
}
