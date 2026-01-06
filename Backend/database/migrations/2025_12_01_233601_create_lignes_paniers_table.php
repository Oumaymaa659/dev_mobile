<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration {
    public function up(): void
    {
        Schema::create('lignes_paniers', function (Blueprint $table) {
            $table->id();
            $table->foreignId('panier_id')->constrained('paniers')->onDelete('cascade');
            $table->foreignId('caftan_id')->constrained('caftans')->onDelete('cascade');
            $table->integer('quantite')->default(1);
            $table->decimal('prix_unitaire', 10, 2); // snapshot prix_journalier * nombre de jours (si on stocke dates)
            $table->date('date_debut')->nullable(); // si tu veux stocker la période par ligne
            $table->date('date_fin')->nullable();
            $table->decimal('sous_total', 10, 2)->default(0);
            $table->timestamps();
        });
    }

    public function down(): void
    {
        Schema::dropIfExists('lignes_paniers');
    }
};
