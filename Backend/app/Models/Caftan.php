<?php

namespace App\Models;


use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\SoftDeletes;

class Caftan extends Model
{
    use HasFactory, SoftDeletes;
    
    protected $table = "caftans";

    protected $fillable = [
        'nom',
        'description',
        'code_interne',
        'prix_journalier',
        'caution',
        'quantite_totale',
        'etat',
        'actif',
        'collection_id',
        'taille',
        'couleur',
    ];

    protected $casts = [
        'prix_journalier' => 'decimal:2',
        'caution' => 'decimal:2',
        'actif' => 'boolean',
    ];

    /**
     * Relation : Un caftan appartient à une collection
     */
    public function collection()
    {
        return $this->belongsTo(Collection::class);
    }

    public function images()
    {
        return $this->hasMany(ImageCaftan::class, 'caftan_id');
    }

    public function lignesLocations()
    {
        return $this->hasMany(LigneLocation::class, 'caftan_id');
    }

    public function disponibilites()
    {
        return $this->hasMany(Disponibilite::class, 'caftan_id');
    }

    /**
     * Scope pour filtrer uniquement les caftans actifs
     */
    public function scopeActif($query)
    {
        return $query->where('actif', true);
    }
}
