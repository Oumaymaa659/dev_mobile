package com.example.darcaftan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.darcaftan.models.Reservation;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "darcaftan.db";
    private static final int DATABASE_VERSION = 2; // Incrémenté pour ajouter la table caftans

    // Table Reservations
    private static final String TABLE_RESERVATIONS = "reservations";
    private static final String COL_ID = "id";
    private static final String COL_ARTICLE_NAME = "article_name";
    private static final String COL_PRICE = "price";
    private static final String COL_STATUS = "status";

    // Table Caftans
    private static final String TABLE_CAFTANS = "caftans";
    private static final String COL_CAFTAN_ID = "id";
    private static final String COL_CAFTAN_NAME = "nom";
    private static final String COL_CAFTAN_PRICE = "prix";
    private static final String COL_CAFTAN_COLLECTION = "collection";
    private static final String COL_CAFTAN_DESCRIPTION = "description";
    private static final String COL_CAFTAN_PHOTO = "photo_path"; // Chemin de la photo

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Créer table reservations
        String CREATE_RESERVATIONS_TABLE = "CREATE TABLE " + TABLE_RESERVATIONS + "("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_ARTICLE_NAME + " TEXT,"
                + COL_PRICE + " REAL,"
                + COL_STATUS + " TEXT"
                + ")";
        db.execSQL(CREATE_RESERVATIONS_TABLE);

        // Créer table caftans
        String CREATE_CAFTANS_TABLE = "CREATE TABLE " + TABLE_CAFTANS + "("
                + COL_CAFTAN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_CAFTAN_NAME + " TEXT NOT NULL,"
                + COL_CAFTAN_PRICE + " REAL NOT NULL,"
                + COL_CAFTAN_COLLECTION + " TEXT NOT NULL,"
                + COL_CAFTAN_DESCRIPTION + " TEXT,"
                + COL_CAFTAN_PHOTO + " TEXT"
                + ")";
        db.execSQL(CREATE_CAFTANS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESERVATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CAFTANS);
        onCreate(db);
    }

    // ========== Méthodes pour Reservations ==========

    public void addReservation(Reservation r) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_ARTICLE_NAME, r.getArticleName());
        values.put(COL_PRICE, r.getprice());
        values.put(COL_STATUS, r.getStatus());
        db.insert(TABLE_RESERVATIONS, null, values);
        db.close();
    }

    public List<Reservation> getConfirmedReservations() {
        List<Reservation> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_RESERVATIONS,
                null,
                COL_STATUS + "=?",
                new String[] { "confirmé" },
                null, null,
                COL_ID + " DESC");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID));
                String articleName = cursor.getString(cursor.getColumnIndexOrThrow(COL_ARTICLE_NAME));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_PRICE));
                String status = cursor.getString(cursor.getColumnIndexOrThrow(COL_STATUS));

                list.add(new Reservation(id, articleName, price, status));

            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return list;
    }

    // ========== Méthodes pour Caftans ==========

    // Modèle simple pour Caftan local
    public static class CaftanLocal {
        private int id;
        private String nom;
        private double prix;
        private String collection;
        private String description;
        private String photoPath;

        public CaftanLocal(int id, String nom, double prix, String collection, String description, String photoPath) {
            this.id = id;
            this.nom = nom;
            this.prix = prix;
            this.collection = collection;
            this.description = description;
            this.photoPath = photoPath;
        }

        // Getters
        public int getId() {
            return id;
        }

        public String getNom() {
            return nom;
        }

        public double getPrix() {
            return prix;
        }

        public String getCollection() {
            return collection;
        }

        public String getDescription() {
            return description;
        }

        public String getPhotoPath() {
            return photoPath;
        }
    }

    // Ajouter un caftan
    public long addCaftan(String nom, double prix, String collection, String description, String photoPath) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_CAFTAN_NAME, nom);
        values.put(COL_CAFTAN_PRICE, prix);
        values.put(COL_CAFTAN_COLLECTION, collection);
        values.put(COL_CAFTAN_DESCRIPTION, description);
        values.put(COL_CAFTAN_PHOTO, photoPath);

        long id = db.insert(TABLE_CAFTANS, null, values);
        db.close();
        return id;
    }

    // Récupérer tous les caftans
    public List<CaftanLocal> getAllCaftans() {
        List<CaftanLocal> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CAFTANS,
                null, null, null, null, null,
                COL_CAFTAN_ID + " DESC");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_CAFTAN_ID));
                String nom = cursor.getString(cursor.getColumnIndexOrThrow(COL_CAFTAN_NAME));
                double prix = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_CAFTAN_PRICE));
                String collection = cursor.getString(cursor.getColumnIndexOrThrow(COL_CAFTAN_COLLECTION));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(COL_CAFTAN_DESCRIPTION));
                String photoPath = cursor.getString(cursor.getColumnIndexOrThrow(COL_CAFTAN_PHOTO));

                list.add(new CaftanLocal(id, nom, prix, collection, description, photoPath));

            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return list;
    }

    // Supprimer un caftan
    public void deleteCaftan(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CAFTANS, COL_CAFTAN_ID + "=?", new String[] { String.valueOf(id) });
        db.close();
    }

    // Obtenir un caftan par ID
    public CaftanLocal getCaftan(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CAFTANS,
                null,
                COL_CAFTAN_ID + "=?",
                new String[] { String.valueOf(id) },
                null, null, null);

        CaftanLocal caftan = null;
        if (cursor != null && cursor.moveToFirst()) {
            String nom = cursor.getString(cursor.getColumnIndexOrThrow(COL_CAFTAN_NAME));
            double prix = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_CAFTAN_PRICE));
            String collection = cursor.getString(cursor.getColumnIndexOrThrow(COL_CAFTAN_COLLECTION));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(COL_CAFTAN_DESCRIPTION));
            String photoPath = cursor.getString(cursor.getColumnIndexOrThrow(COL_CAFTAN_PHOTO));

            caftan = new CaftanLocal(id, nom, prix, collection, description, photoPath);
            cursor.close();
        }
        db.close();
        return caftan;
    }
}
