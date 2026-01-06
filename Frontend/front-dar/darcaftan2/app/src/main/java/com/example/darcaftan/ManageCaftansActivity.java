package com.example.darcaftan;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ManageCaftansActivity extends AppCompatActivity {

    private RecyclerView recyclerCaftans;
    private ProgressBar progressBar;
    private TextView textEmpty;
    private Button btnAddCaftan;
    private DatabaseHelper dbHelper;
    private CaftanAdapter adapter;
    private List<DatabaseHelper.CaftanLocal> caftanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_caftans);

        // Initialiser les vues
        recyclerCaftans = findViewById(R.id.recycler_caftans);
        progressBar = findViewById(R.id.progress_bar);
        textEmpty = findViewById(R.id.text_empty);
        btnAddCaftan = findViewById(R.id.btn_add_caftan);

        // Initialiser la base de données
        dbHelper = new DatabaseHelper(this);

        // Configurer le RecyclerView
        caftanList = new ArrayList<>();
        adapter = new CaftanAdapter(caftanList);
        recyclerCaftans.setLayoutManager(new LinearLayoutManager(this));
        recyclerCaftans.setAdapter(adapter);

        // Bouton Ajouter
        btnAddCaftan.setOnClickListener(v -> {
            Intent intent = new Intent(ManageCaftansActivity.this, AddCaftanActivity.class);
            startActivity(intent);
        });

        // Charger les caftans
        loadCaftans();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Recharger les caftans quand on revient à cette activité
        loadCaftans();
    }

    private void loadCaftans() {
        progressBar.setVisibility(View.VISIBLE);
        textEmpty.setVisibility(View.GONE);
        recyclerCaftans.setVisibility(View.GONE);

        // Charger depuis la base de données locale
        caftanList.clear();
        caftanList.addAll(dbHelper.getAllCaftans());

        progressBar.setVisibility(View.GONE);

        if (caftanList.isEmpty()) {
            textEmpty.setVisibility(View.VISIBLE);
        } else {
            recyclerCaftans.setVisibility(View.VISIBLE);
            adapter.notifyDataSetChanged();
        }
    }

    private void deleteCaftan(int caftanId, int position) {
        // Supprimer de la base de données
        dbHelper.deleteCaftan(caftanId);

        // Retirer de la liste et mettre à jour l'affichage
        caftanList.remove(position);
        adapter.notifyItemRemoved(position);

        Toast.makeText(this, "Caftan supprimé avec succès", Toast.LENGTH_SHORT).show();

        if (caftanList.isEmpty()) {
            textEmpty.setVisibility(View.VISIBLE);
            recyclerCaftans.setVisibility(View.GONE);
        }
    }

    // Adapter pour le RecyclerView
    private class CaftanAdapter extends RecyclerView.Adapter<CaftanAdapter.ViewHolder> {

        private List<DatabaseHelper.CaftanLocal> caftans;

        public CaftanAdapter(List<DatabaseHelper.CaftanLocal> caftans) {
            this.caftans = caftans;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_caftan_admin, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            DatabaseHelper.CaftanLocal caftan = caftans.get(position);
            holder.textName.setText(caftan.getNom());
            holder.textPrice.setText(String.format("Prix: %.2f DH", caftan.getPrix()));
            holder.textCollection.setText("Collection: " + caftan.getCollection());

            // Charger la photo si disponible
            if (caftan.getPhotoPath() != null && !caftan.getPhotoPath().isEmpty()) {
                File imageFile = new File(caftan.getPhotoPath());
                if (imageFile.exists()) {
                    Glide.with(ManageCaftansActivity.this)
                            .load(imageFile)
                            .centerCrop()
                            .into(holder.imagePhoto);
                    holder.imagePhoto.setVisibility(View.VISIBLE);
                } else {
                    holder.imagePhoto.setVisibility(View.GONE);
                }
            } else {
                holder.imagePhoto.setVisibility(View.GONE);
            }

            holder.btnDelete.setOnClickListener(v -> {
                // Dialog de confirmation
                new AlertDialog.Builder(ManageCaftansActivity.this)
                        .setTitle("Confirmer la suppression")
                        .setMessage("Voulez-vous vraiment supprimer ce caftan ?")
                        .setPositiveButton("Oui", (dialog, which) -> {
                            deleteCaftan(caftan.getId(), position);
                        })
                        .setNegativeButton("Non", null)
                        .show();
            });
        }

        @Override
        public int getItemCount() {
            return caftans.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView imagePhoto;
            TextView textName, textPrice, textCollection;
            Button btnDelete;

            ViewHolder(View itemView) {
                super(itemView);
                imagePhoto = itemView.findViewById(R.id.image_caftan_photo);
                textName = itemView.findViewById(R.id.text_caftan_name);
                textPrice = itemView.findViewById(R.id.text_caftan_price);
                textCollection = itemView.findViewById(R.id.text_caftan_collection);
                btnDelete = itemView.findViewById(R.id.btn_delete);
            }
        }
    }
}
