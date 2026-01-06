<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class LignePanier extends Model
{
    protected $table = 'lignes_paniers';

    protected $fillable = [
        'panier_id',
        'caftan_id',
        'quantite',
        'prix_unitaire',
        'date_debut',
        'date_fin',
        'sous_total'
    ];

    public function panier()
    {
        return $this->belongsTo(Panier::class, 'panier_id');
    }

    public function caftan()
    {
        return $this->belongsTo(\App\Models\Caftan::class, 'caftan_id');
    }
}
