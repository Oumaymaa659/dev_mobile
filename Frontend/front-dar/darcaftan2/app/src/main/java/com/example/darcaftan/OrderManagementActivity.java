package com.example.darcaftan;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.darcaftan.models.LigneLocation;
import com.example.darcaftan.models.Location;
import com.example.darcaftan.network.ApiClient;
import com.example.darcaftan.network.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderManagementActivity extends AppCompatActivity {

    private RecyclerView recyclerOrders;
    private ProgressBar progressBar;
    private TextView textEmpty;
    private ApiService apiService;
    private OrderAdapter adapter;
    private List<Location> orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_management);

        // Initialiser les vues
        recyclerOrders = findViewById(R.id.recycler_orders);
        progressBar = findViewById(R.id.progress_bar);
        textEmpty = findViewById(R.id.text_empty);

        // Initialiser l'API
        apiService = ApiClient.getClient().create(ApiService.class);

        // Configurer le RecyclerView
        orderList = new ArrayList<>();
        adapter = new OrderAdapter(orderList);
        recyclerOrders.setLayoutManager(new LinearLayoutManager(this));
        recyclerOrders.setAdapter(adapter);

        // Charger les commandes
        loadOrders();
    }

    private void loadOrders() {
        progressBar.setVisibility(View.VISIBLE);
        textEmpty.setVisibility(View.GONE);
        recyclerOrders.setVisibility(View.GONE);

        Call<List<Location>> call = apiService.getAdminLocations();
        call.enqueue(new Callback<List<Location>>() {
            @Override
            public void onResponse(Call<List<Location>> call, Response<List<Location>> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {
                    orderList.clear();
                    // Filtrer seulement les commandes en attente
                    for (Location location : response.body()) {
                        if ("en_attente".equals(location.getStatut())) {
                            orderList.add(location);
                        }
                    }
                    adapter.notifyDataSetChanged();

                    if (orderList.isEmpty()) {
                        textEmpty.setVisibility(View.VISIBLE);
                    } else {
                        recyclerOrders.setVisibility(View.VISIBLE);
                    }
                } else {
                    textEmpty.setVisibility(View.VISIBLE);
                    Toast.makeText(OrderManagementActivity.this, "Erreur de chargement", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Location>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                textEmpty.setVisibility(View.VISIBLE);
                Toast.makeText(OrderManagementActivity.this, "Erreur réseau: " + t.getMessage(), Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    private void confirmOrder(int orderId, int position) {
        Call<Location> call = apiService.confirmerLocation(orderId);
        call.enqueue(new Callback<Location>() {
            @Override
            public void onResponse(Call<Location> call, Response<Location> response) {
                if (response.isSuccessful()) {
                    orderList.remove(position);
                    adapter.notifyItemRemoved(position);
                    Toast.makeText(OrderManagementActivity.this, "Commande confirmée avec succès", Toast.LENGTH_SHORT)
                            .show();

                    if (orderList.isEmpty()) {
                        textEmpty.setVisibility(View.VISIBLE);
                        recyclerOrders.setVisibility(View.GONE);
                    }
                } else {
                    Toast.makeText(OrderManagementActivity.this, "Erreur lors de la confirmation", Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onFailure(Call<Location> call, Throwable t) {
                Toast.makeText(OrderManagementActivity.this, "Erreur réseau: " + t.getMessage(), Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    private void refuseOrder(int orderId, int position) {
        Call<Location> call = apiService.refuserLocation(orderId);
        call.enqueue(new Callback<Location>() {
            @Override
            public void onResponse(Call<Location> call, Response<Location> response) {
                if (response.isSuccessful()) {
                    orderList.remove(position);
                    adapter.notifyItemRemoved(position);
                    Toast.makeText(OrderManagementActivity.this, "Commande refusée", Toast.LENGTH_SHORT).show();

                    if (orderList.isEmpty()) {
                        textEmpty.setVisibility(View.VISIBLE);
                        recyclerOrders.setVisibility(View.GONE);
                    }
                } else {
                    Toast.makeText(OrderManagementActivity.this, "Erreur lors du refus", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Location> call, Throwable t) {
                Toast.makeText(OrderManagementActivity.this, "Erreur réseau: " + t.getMessage(), Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    // Adapter pour le RecyclerView
    private class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

        private List<Location> orders;

        public OrderAdapter(List<Location> orders) {
            this.orders = orders;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_order_admin, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Location order = orders.get(position);

            holder.textOrderId.setText("Commande #" + order.getId());
            holder.textOrderStatus.setText(order.getStatut());
            holder.textOrderPrice.setText(String.format("Prix total: %.2f DH", order.getPrixTotal()));
            holder.textDateDebut.setText("Du: " + order.getDateDebut());
            holder.textDateFin.setText("Au: " + order.getDateFin());

            // Client name - on utilise l'ID pour l'instant car on n'a pas le nom dans le
            // modèle
            holder.textClientName.setText("Client ID: " + order.getUserId());

            // Nombre d'articles
            int itemsCount = (order.getLignes() != null) ? order.getLignes().size() : 0;
            holder.textItemsCount.setText("Articles: " + itemsCount);

            // Bouton Confirmer
            holder.btnConfirm.setOnClickListener(v -> {
                new AlertDialog.Builder(OrderManagementActivity.this)
                        .setTitle("Confirmer la commande")
                        .setMessage("Voulez-vous confirmer cette commande ?")
                        .setPositiveButton("Oui", (dialog, which) -> {
                            confirmOrder(order.getId(), position);
                        })
                        .setNegativeButton("Non", null)
                        .show();
            });

            // Bouton Refuser
            holder.btnRefuse.setOnClickListener(v -> {
                new AlertDialog.Builder(OrderManagementActivity.this)
                        .setTitle("Refuser la commande")
                        .setMessage("Voulez-vous refuser cette commande ?")
                        .setPositiveButton("Oui", (dialog, which) -> {
                            refuseOrder(order.getId(), position);
                        })
                        .setNegativeButton("Non", null)
                        .show();
            });
        }

        @Override
        public int getItemCount() {
            return orders.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView textOrderId, textOrderStatus, textClientName, textOrderPrice;
            TextView textDateDebut, textDateFin, textItemsCount;
            Button btnConfirm, btnRefuse;

            ViewHolder(View itemView) {
                super(itemView);
                textOrderId = itemView.findViewById(R.id.text_order_id);
                textOrderStatus = itemView.findViewById(R.id.text_order_status);
                textClientName = itemView.findViewById(R.id.text_client_name);
                textOrderPrice = itemView.findViewById(R.id.text_order_price);
                textDateDebut = itemView.findViewById(R.id.text_date_debut);
                textDateFin = itemView.findViewById(R.id.text_date_fin);
                textItemsCount = itemView.findViewById(R.id.text_items_count);
                btnConfirm = itemView.findViewById(R.id.btn_confirm);
                btnRefuse = itemView.findViewById(R.id.btn_refuse);
            }
        }
    }
}
