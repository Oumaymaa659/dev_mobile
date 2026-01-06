package com.example.darcaftan;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class AddCaftanActivity extends AppCompatActivity {

    private EditText editCaftanName, editCaftanPrice, editCaftanDescription;
    private Spinner spinnerCollection;
    private ImageView imagePreview;
    private Button btnSelectPhoto, btnSave, btnCancel;
    private ProgressBar progressBar;
    private DatabaseHelper dbHelper;

    private Uri selectedImageUri;
    private String savedImagePath;

    // Launcher pour sélectionner une image
    private ActivityResultLauncher<String> imagePickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_caftan);

        // Initialiser les vues
        editCaftanName = findViewById(R.id.edit_caftan_name);
        editCaftanPrice = findViewById(R.id.edit_caftan_price);
        editCaftanDescription = findViewById(R.id.edit_caftan_description);
        spinnerCollection = findViewById(R.id.spinner_collection);
        imagePreview = findViewById(R.id.image_preview);
        btnSelectPhoto = findViewById(R.id.btn_select_photo);
        btnSave = findViewById(R.id.btn_save);
        btnCancel = findViewById(R.id.btn_cancel);
        progressBar = findViewById(R.id.progress_bar);

        // Initialiser la base de données
        dbHelper = new DatabaseHelper(this);

        // Configurer le Spinner des collections
        setupCollectionSpinner();

        // Configurer le launcher pour sélectionner une image
        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        selectedImageUri = uri;
                        imagePreview.setImageURI(uri);
                        imagePreview.setPadding(0, 0, 0, 0);
                    }
                });

        // Bouton Sélectionner Photo
        btnSelectPhoto.setOnClickListener(v -> {
            imagePickerLauncher.launch("image/*");
        });

        // Bouton Annuler
        btnCancel.setOnClickListener(v -> finish());

        // Bouton Enregistrer
        btnSave.setOnClickListener(v -> saveCaftan());
    }

    private void setupCollectionSpinner() {
        String[] collections = { "Mariage", "Soirée", "Broderie" };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                collections);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCollection.setAdapter(adapter);
    }

    private void saveCaftan() {
        // Récupérer les valeurs
        String name = editCaftanName.getText().toString().trim();
        String priceStr = editCaftanPrice.getText().toString().trim();
        String description = editCaftanDescription.getText().toString().trim();
        String collection = spinnerCollection.getSelectedItem().toString();

        // Validation
        if (name.isEmpty()) {
            editCaftanName.setError("Le nom est requis");
            editCaftanName.requestFocus();
            return;
        }

        if (priceStr.isEmpty()) {
            editCaftanPrice.setError("Le prix est requis");
            editCaftanPrice.requestFocus();
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceStr);
            if (price <= 0) {
                editCaftanPrice.setError("Le prix doit être positif");
                editCaftanPrice.requestFocus();
                return;
            }
        } catch (NumberFormatException e) {
            editCaftanPrice.setError("Prix invalide");
            editCaftanPrice.requestFocus();
            return;
        }

        // Afficher le ProgressBar
        progressBar.setVisibility(View.VISIBLE);
        btnSave.setEnabled(false);

        // Sauvegarder l'image si elle a été sélectionnée
        savedImagePath = null;
        if (selectedImageUri != null) {
            savedImagePath = saveImageToInternalStorage(selectedImageUri);
        }

        // Ajouter le caftan à la base de données
        long id = dbHelper.addCaftan(name, price, collection, description, savedImagePath);

        progressBar.setVisibility(View.GONE);
        btnSave.setEnabled(true);

        if (id != -1) {
            Toast.makeText(this, "Caftan ajouté avec succès!", Toast.LENGTH_SHORT).show();
            finish(); // Retour à l'activité précédente
        } else {
            Toast.makeText(this, "Erreur lors de l'ajout du caftan", Toast.LENGTH_SHORT).show();
        }
    }

    // Sauvegarder l'image dans le stockage interne de l'application
    private String saveImageToInternalStorage(Uri imageUri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(imageUri);
            File directory = new File(getFilesDir(), "caftan_images");
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String fileName = "caftan_" + System.currentTimeMillis() + ".jpg";
            File file = new File(directory, fileName);

            OutputStream outputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            outputStream.flush();
            outputStream.close();
            inputStream.close();

            return file.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Erreur lors de la sauvegarde de l'image", Toast.LENGTH_SHORT).show();
            return null;
        }
    }
}
