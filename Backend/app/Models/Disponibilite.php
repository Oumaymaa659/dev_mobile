<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Disponibilite extends Model
{
    use HasFactory;
        protected $table = "disponibilites";

    protected $fillable = [
        'caftan_id',
        'date_debut',
        'date_fin',
        'bloque',
        'raison'
    ];

    public function caftan()
    {
        return $this->belongsTo(Caftan::class, 'caftan_id');
    }
}
