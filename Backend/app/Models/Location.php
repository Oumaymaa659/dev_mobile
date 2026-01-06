<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Location extends Model
{
    use HasFactory;
    protected $table = "locations";

    protected $fillable = [
        'user_id',
        'date_debut',
        'date_fin',
        'montant_total',
        'montant_caution',
        'statut'
    ];


    protected $casts = [
        'montant_total' => 'decimal:2',
        'montant_caution' => 'decimal:2',
        'date_debut' => 'date',
        'date_fin' => 'date',
    ];

    /**
     * Relation : Une location appartient à un utilisateur
     */
    public function user()
    {
        return $this->belongsTo(User::class, 'user_id');
    }

    public function lignes()
    {
        return $this->hasMany(LigneLocation::class, 'location_id');
    }
}
