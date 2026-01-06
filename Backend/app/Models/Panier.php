<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Panier extends Model
{
    protected $table = 'paniers';

    protected $fillable = ['user_id', 'statut'];

    public function user()
    {
        return $this->belongsTo(\App\Models\User::class, 'user_id');
    }

    public function lignes()
    {
        return $this->hasMany(\App\Models\LignePanier::class, 'panier_id');
    }
}
