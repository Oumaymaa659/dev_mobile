package com.example.darcaftan;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.ImageButton;
import java.util.ArrayList;
import java.util.HashMap;

public class SearchActivity extends AppCompatActivity {

    SearchView searchView;
    ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList<String> dataList;
    HashMap<String, Class<?>> collectionMap; // Pour relier nom → activité
    ImageButton btnRetour; // 🔙 Bouton retour

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // 🔍 Recherche et liste
        searchView = findViewById(R.id.searchView);
        listView = findViewById(R.id.listView);

        // 🔙 Initialisation du bouton retour
        btnRetour = findViewById(R.id.btn_retour);
        btnRetour.setOnClickListener(v -> finish());

        // 1️⃣ Liste des collections
        dataList = new ArrayList<>();
        dataList.add("Collection Mariage");
        dataList.add("Collection Broderie");
        dataList.add("Collection Soirée");
        dataList.add("Collection Royale");

        // 2️⃣ Map pour relier le nom de la collection à son activité
        collectionMap = new HashMap<>();
        collectionMap.put("Collection Mariage", MariageActivity.class);
        collectionMap.put("Collection Broderie", BroderieActivity.class);
        collectionMap.put("Collection Soirée", SoireeActivity.class);
        collectionMap.put("Collection Royale", RoyaleActivity.class);

        // 3️⃣ Adapter pour la ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);

        // 4️⃣ Filtrage en temps réel
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        // 5️⃣ Action lors du clic sur un item
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String selected = adapter.getItem(position);
            if (selected != null && collectionMap.containsKey(selected)) {
                Intent intent = new Intent(SearchActivity.this, collectionMap.get(selected));
                startActivity(intent);
            }
        });
    }
}
