package com.example.darcaftan;

import android.content.Context;
import android.util.Log;

public class SampleDataHelper {

    private static final String TAG = "SampleDataHelper";

    // Méthode pour ajouter des données de démonstration
    public static void insertSampleData(Context context, DatabaseHelper dbHelper) {
        // Vérifier si des caftans existent déjà
        if (!dbHelper.getAllCaftans().isEmpty()) {
            Log.d(TAG, "La base de données contient déjà des caftans");
            return;
        }

        Log.d(TAG, "Insertion des données de démonstration...");

        // Ajouter les caftans de démonstration (sans photos pour l'instant)
        dbHelper.addCaftan(
                "Caftan Royal Blanc",
                2500.00,
                "Mariage",
                "Caftan élégant en blanc et or avec broderies traditionnelles, perles et paillettes. Parfait pour les mariages.",
                null);

        dbHelper.addCaftan(
                "Caftan Soirée Bleu Velours",
                1800.00,
                "Soirée",
                "Magnifique caftan de soirée en velours bleu avec broderies argentées. Design sophistiqué et élégant.",
                null);

        dbHelper.addCaftan(
                "Caftan Broderie Rouge",
                1500.00,
                "Broderie",
                "Caftan traditionnel brodé à la main avec des motifs marocains en fil d'or. Artisanat d'exception.",
                null);

        dbHelper.addCaftan(
                "Caftan Mariée Crème",
                3200.00,
                "Mariage",
                "Caftan de mariée luxueux couleur crème avec perles, cristaux et détails en dentelle. Design royal et élégant.",
                null);

        dbHelper.addCaftan(
                "Caftan Soirée Vert Émeraude",
                2100.00,
                "Soirée",
                "Caftan moderne en satin vert émeraude avec broderies géométriques dorées. Parfait pour les soirées chic.",
                null);

        dbHelper.addCaftan(
                "Caftan Traditionnel Bordeaux",
                1650.00,
                "Broderie",
                "Caftan bordeaux avec broderies florales complexes. Style traditionnel marocain authentique.",
                null);

        dbHelper.addCaftan(
                "Caftan Royal Or",
                2800.00,
                "Mariage",
                "Caftan doré avec ornements précieux, idéal pour grandes occasions. Finition luxueuse.",
                null);

        Log.d(TAG, "Données de démonstration insérées avec succès!");
    }
}
