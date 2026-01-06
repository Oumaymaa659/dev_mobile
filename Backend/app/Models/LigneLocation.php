<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class LigneLocation extends Model
{
    use HasFactory;
    protected $table = "lignes_locations";

    protected $fillable = [
        'location_id',
        'caftan_id',
        'quantite',
        'prix_unitaire',
        'sous_total'
    ];

    public function location()
    {
        return $this->belongsTo(Location::class, 'location_id');
    }

    public function caftan()
    {
        return $this->belongsTo(Caftan::class, 'caftan_id');
    }
}
