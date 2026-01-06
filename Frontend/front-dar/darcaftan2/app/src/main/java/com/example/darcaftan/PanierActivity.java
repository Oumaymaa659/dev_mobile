package com.example.darcaftan;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.darcaftan.adapters.CartAdapter;
import com.example.darcaftan.models.CartItem;
import com.example.darcaftan.models.Location;
import com.example.darcaftan.network.ApiClient;
import com.example.darcaftan.network.ApiService;
import com.example.darcaftan.utils.CartManager;
import com.example.darcaftan.utils.TempUserManager;
import com.example.darcaftan.utils.TokenManager;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PanierActivity extends AppCompatActivity {

    ListView listPanier;
    Button btnConfirmer;
    TextView textTotal;
    CartAdapter cartAdapter;
    CartManager cartManager;
    TempUserManager tempUserManager;
    TokenManager tokenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panier);

        listPanier = findViewById(R.id.list_panier);
        btnConfirmer = findViewById(R.id.btn_confirmer);
        textTotal = findViewById(R.id.text_total);

        cartManager = CartManager.getInstance();
        tempUserManager = TempUserManager.getInstance(this);
        tokenManager = new TokenManager(this);

        cartAdapter = new CartAdapter(this, cartManager.getItems());
        listPanier.setAdapter(cartAdapter);

        updateTotal();

        btnConfirmer.setOnClickListener(v -> {
            if (cartManager.isEmpty()) {
                Toast.makeText(this, "Panier vide", Toast.LENGTH_SHORT).show();
                return;
            }

            // Afficher le formulaire d'informations client
            showCustomerInfoDialog();
        });
    }

    /**
     * Affiche le dialog pour collecter les informations client
     */
    private void showCustomerInfoDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_customer_info, null);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        // Récupérer les vues du dialog
        EditText editNom = dialogView.findViewById(R.id.edit_nom);
        EditText editEmail = dialogView.findViewById(R.id.edit_email);
        EditText editTelephone = dialogView.findViewById(R.id.edit_telephone);
        EditText editAdresse = dialogView.findViewById(R.id.edit_adresse);
        Button btnAnnuler = dialogView.findViewById(R.id.btn_annuler);
        Button btnConfirmerCommande = dialogView.findViewById(R.id.btn_confirmer_commande);

        // Bouton Annuler
        btnAnnuler.setOnClickListener(v -> dialog.dismiss());

        // Bouton Confirmer la commande
        btnConfirmerCommande.setOnClickListener(v -> {
            String nom = editNom.getText().toString().trim();
            String email = editEmail.getText().toString().trim();
            String telephone = editTelephone.getText().toString().trim();
            String adresse = editAdresse.getText().toString().trim();

            // Valider les données
            if (validateCustomerInfo(nom, email, telephone, adresse)) {
                // Données valides - Confirmer la commande
                confirmerCommande(nom, email, telephone, adresse, dialog);
            }
        });

        dialog.show();
    }

    /**
     * Valide les informations client
     */
    private boolean validateCustomerInfo(String nom, String email, String telephone, String adresse) {
        // Vérifier que tous les champs sont remplis
        if (nom.isEmpty() || email.isEmpty() || telephone.isEmpty() || adresse.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Valider le nom (minimum 3 caractères)
        if (nom.length() < 3) {
            Toast.makeText(this, "Le nom doit contenir au moins 3 caractères", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Valider le format email
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Format d'email invalide", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Valider le format téléphone (numéros marocains : 06XX... ou +212 6XX...)
        Pattern phonePattern = Pattern
                .compile("^(0[5-7][0-9]{8}|\\+212[5-7][0-9]{8}|0[5-7] ?[0-9]{2} ?[0-9]{2} ?[0-9]{2} ?[0-9]{2})$");
        if (!phonePattern.matcher(telephone.replaceAll("\\s+", "")).matches()) {
            Toast.makeText(this, "Format de téléphone invalide (ex: 0612345678)", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Valider l'adresse (minimum 10 caractères)
        if (adresse.length() < 10) {
            Toast.makeText(this, "L'adresse doit contenir au moins 10 caractères", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    /**
     * Confirme la commande en appelant l'API backend
     */
    private void confirmerCommande(String nom, String email, String telephone, String adresse, AlertDialog dialog) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        // Préparer les données à envoyer
        Map<String, Object> customerData = new HashMap<>();

        // Si l'utilisateur n'est pas authentifié, ajouter le temp_user_id
        if (!tokenManager.hasToken()) {
            String tempUserId = tempUserManager.getTempUserId();
            customerData.put("temp_user_id", tempUserId);
        }

        customerData.put("nom", nom);
        customerData.put("email", email);
        customerData.put("telephone", telephone);
        customerData.put("adresse", adresse);

        // Appel API
        Call<Location> call = apiService.confirmerPanier(customerData);
        call.enqueue(new Callback<Location>() {
            @Override
            public void onResponse(Call<Location> call, Response<Location> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Location location = response.body();

                    // Succès - Vider le panier local
                    cartManager.clear();
                    cartAdapter.notifyDataSetChanged();
                    updateTotal();

                    // Supprimer le temp_user_id
                    tempUserManager.clearTempUserId();

                    dialog.dismiss();

                    // Afficher message de succès
                    Toast.makeText(PanierActivity.this,
                            "✓ Commande confirmée avec succès !\nNuméro de réservation : " + location.getId(),
                            Toast.LENGTH_LONG).show();

                    // Optionnel : Rediriger vers l'historique ou l'accueil
                    // Intent intent = new Intent(PanierActivity.this, HistoriqueActivity.class);
                    // startActivity(intent);
                    finish();

                } else {
                    // Gérer les erreurs avec plus de détails
                    String errorMsg = "Erreur lors de la confirmation";

                    try {
                        // Essayer de lire le corps de l'erreur
                        String errorBody = response.errorBody() != null ? response.errorBody().string() : "";
                        android.util.Log.e("PanierActivity", "Code erreur: " + response.code());
                        android.util.Log.e("PanierActivity", "Corps erreur: " + errorBody);

                        if (response.code() == 422) {
                            errorMsg = "Vérifiez les données saisies.\nAssurez-vous que le backend Laravel est démarré.";
                        } else if (response.code() == 404) {
                            errorMsg = "Panier introuvable. Ajoutez d'abord des articles au panier.";
                        } else if (response.code() == 500) {
                            errorMsg = "Erreur serveur. Vérifiez que le backend Laravel fonctionne.";
                        }
                    } catch (Exception e) {
                        android.util.Log.e("PanierActivity", "Erreur lors de la lecture du corps de l'erreur", e);
                    }

                    Toast.makeText(PanierActivity.this, errorMsg, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Location> call, Throwable t) {
                Toast.makeText(PanierActivity.this,
                        "Erreur de connexion : " + t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateTotal() {
        double total = cartManager.getTotal();
        textTotal.setText("Total : " + String.format("%.2f DH", total));
    }

    @Override
    protected void onResume() {
        super.onResume();
        cartAdapter.notifyDataSetChanged();
        updateTotal();
    }
}
