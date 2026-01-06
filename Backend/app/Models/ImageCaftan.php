<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class ImageCaftan extends Model
{
    use HasFactory;
     protected $table = "images_caftans";

    protected $fillable = [
        'caftan_id',
        'chemin_image',
        'principale'
    ];

    public function caftan()
    {
        return $this->belongsTo(Caftan::class, 'caftan_id');
    }
}
