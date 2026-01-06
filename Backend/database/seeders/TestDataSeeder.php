<?php

namespace Database\Seeders;

use Illuminate\Database\Seeder;
use App\Models\User;
use App\Models\Collection;
use App\Models\Caftan;
use App\Models\Location;
use App\Models\LigneLocation;

class TestDataSeeder extends Seeder
{
    public function run(): void
    {
        // Créer des utilisateurs
        $admin = User::create([
            'name' => 'Admin Test',
            'email' => 'admin@test.com',
            'password' => bcrypt('password'),
            'role' => 'admin',
            'telephone' => '0612345678'
        ]);

        $client1 = User::create([
            'name' => 'Client 1',
            'email' => 'client1@test.com',
            'password' => bcrypt('password'),
            'role' => 'client',
            'telephone' => '0623456789'
        ]);

        $client2 = User::create([
            'name' => 'Client 2',
            'email' => 'client2@test.com',
            'password' => bcrypt('password'),
            'role' => 'client',
            'telephone' => '0634567890'
        ]);

        // Créer les 4 collections du frontend
        $collectionMariage = Collection::create([
            'nom' => 'Collection Mariage',
            'description' => 'Caftans élégants pour mariages et cérémonies',
            'saison' => 'Toutes saisons',
            'annee' => 2024,
            'actif' => true
        ]);

        $collectionBroderie = Collection::create([
            'nom' => 'Collection Broderie',
            'description' => 'Caftans avec broderies traditionnelles marocaines',
            'saison' => 'Toutes saisons',
            'annee' => 2024,
            'actif' => true
        ]);

        $collectionSoiree = Collection::create([
            'nom' => 'Collection Soirée',
            'description' => 'Caftans luxueux pour soirées et événements',
            'saison' => 'Toutes saisons',
            'annee' => 2024,
            'actif' => true
        ]);

        $collectionRoyale = Collection::create([
            'nom' => 'Collection Royale',
            'description' => 'Caftans royaux haut de gamme',
            'saison' => 'Toutes saisons',
            'annee' => 2024,
            'actif' => true
        ]);

        // Créer des caftans avec les collections correspondantes
        $caftan1 = Caftan::create([
            'nom' => 'Caftan Royal Bleu',
            'description' => 'Magnifique caftan bleu royal avec broderies dorées',
            'code_interne' => 'CAF-001',
            'prix_journalier' => 500.00,
            'caution' => 1000.00,
            'quantite_totale' => 3,
            'etat' => 'neuf',
            'actif' => true,
            'collection_id' => $collectionMariage->id,
            'taille' => 'M',
            'couleur' => 'Bleu Royal'
        ]);

        $caftan2 = Caftan::create([
            'nom' => 'Caftan Doré Luxe',
            'description' => 'Caftan doré avec perles fines et broderies',
            'code_interne' => 'CAF-002',
            'prix_journalier' => 750.00,
            'caution' => 1500.00,
            'quantite_totale' => 2,
            'etat' => 'neuf',
            'actif' => true,
            'collection_id' => $collectionBroderie->id,
            'taille' => 'L',
            'couleur' => 'Doré'
        ]);

        $caftan3 = Caftan::create([
            'nom' => 'Caftan Rouge Passion',
            'description' => 'Caftan rouge élégant pour occasions spéciales',
            'code_interne' => 'CAF-003',
            'prix_journalier' => 600.00,
            'caution' => 1200.00,
            'quantite_totale' => 4,
            'etat' => 'bon',
            'actif' => true,
            'collection_id' => $collectionSoiree->id,
            'taille' => 'S',
            'couleur' => 'Rouge'
        ]);

        $caftan4 = Caftan::create([
            'nom' => 'Caftan Vert Émeraude',
            'description' => 'Caftan vert royal avec motifs traditionnels',
            'code_interne' => 'CAF-004',
            'prix_journalier' => 800.00,
            'caution' => 1600.00,
            'quantite_totale' => 2,
            'etat' => 'neuf',
            'actif' => true,
            'collection_id' => $collectionRoyale->id,
            'taille' => 'M',
            'couleur' => 'Vert Émeraude'
        ]);

        $caftan5 = Caftan::create([
            'nom' => 'Caftan Blanc Mariage',
            'description' => 'Caftan blanc pur pour mariées',
            'code_interne' => 'CAF-005',
            'prix_journalier' => 900.00,
            'caution' => 1800.00,
            'quantite_totale' => 1,
            'etat' => 'neuf',
            'actif' => true,
            'collection_id' => $collectionMariage->id,
            'taille' => 'L',
            'couleur' => 'Blanc'
        ]);

        // Créer des réservations/locations
        $location1 = Location::create([
            'user_id' => $client1->id,
            'date_debut' => now()->addDays(5),
            'date_fin' => now()->addDays(7),
            'montant_total' => 1000.00,
            'montant_caution' => 2000.00,
            'statut' => 'en_attente'
        ]);

        LigneLocation::create([
            'location_id' => $location1->id,
            'caftan_id' => $caftan1->id,
            'quantite' => 1,
            'prix_unitaire' => 500.00,
            'sous_total' => 500.00
        ]);

        $location2 = Location::create([
            'user_id' => $client2->id,
            'date_debut' => now()->addDays(10),
            'date_fin' => now()->addDays(12),
            'montant_total' => 1500.00,
            'montant_caution' => 3000.00,
            'statut' => 'en_attente'
        ]);

        LigneLocation::create([
            'location_id' => $location2->id,
            'caftan_id' => $caftan2->id,
            'quantite' => 1,
            'prix_unitaire' => 750.00,
            'sous_total' => 750.00
        ]);

        $location3 = Location::create([
            'user_id' => $client1->id,
            'date_debut' => now()->subDays(5),
            'date_fin' => now()->subDays(3),
            'montant_total' => 1200.00,
            'montant_caution' => 2400.00,
            'statut' => 'confirmee'
        ]);

        LigneLocation::create([
            'location_id' => $location3->id,
            'caftan_id' => $caftan3->id,
            'quantite' => 1,
            'prix_unitaire' => 600.00,
            'sous_total' => 600.00
        ]);

        $location4 = Location::create([
            'user_id' => $client2->id,
            'date_debut' => now()->addDays(15),
            'date_fin' => now()->addDays(17),
            'montant_total' => 900.00,
            'montant_caution' => 1800.00,
            'statut' => 'annulee'
        ]);

        LigneLocation::create([
            'location_id' => $location4->id,
            'caftan_id' => $caftan4->id,
            'quantite' => 1,
            'prix_unitaire' => 800.00,
            'sous_total' => 800.00
        ]);
    }
}
