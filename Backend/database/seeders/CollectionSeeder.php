<?php

namespace Database\Seeders;

use Illuminate\Database\Seeder;
use App\Models\Collection;

class CollectionSeeder extends Seeder
{
    /**
     * Seed les 4 collections du frontend Dar Caftan
     */
    public function run(): void
    {
        $collections = [
            [
                'nom' => 'Collection Mariage',
                'description' => 'Caftans élégants pour mariages et cérémonies',
                'saison' => 'Toutes saisons',
                'annee' => 2024,
                'actif' => true
            ],
            [
                'nom' => 'Collection Broderie',
                'description' => 'Caftans avec broderies traditionnelles marocaines',
                'saison' => 'Toutes saisons',
                'annee' => 2024,
                'actif' => true
            ],
            [
                'nom' => 'Collection Soirée',
                'description' => 'Caftans luxueux pour soirées et événements',
                'saison' => 'Toutes saisons',
                'annee' => 2024,
                'actif' => true
            ],
            [
                'nom' => 'Collection Royale',
                'description' => 'Caftans royaux haut de gamme',
                'saison' => 'Toutes saisons',
                'annee' => 2024,
                'actif' => true
            ]
        ];

        foreach ($collections as $collection) {
            Collection::create($collection);
        }
    }
}
