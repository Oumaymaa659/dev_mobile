<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\SoftDeletes;

class Collection extends Model
{
    use HasFactory, SoftDeletes;
    
    protected $fillable = [
        'nom',
        'description',
        'saison',
        'annee',
        'actif'
    ];

    protected $casts = [
        'actif' => 'boolean',
        'annee' => 'integer',
    ];

    /**
     * Relation : Une collection a plusieurs caftans
     */
    public function caftans()
    {
        return $this->hasMany(Caftan::class);
    }

    /**
     * Scope pour filtrer uniquement les collections actives
     */
    public function scopeActif($query)
    {
        return $query->where('actif', true);
    }
}
