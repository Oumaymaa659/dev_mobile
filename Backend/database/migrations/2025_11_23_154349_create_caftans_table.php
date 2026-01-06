<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    /**
     * Run the migrations.
     */
    public function up(): void
    {
        Schema::create('caftans', function (Blueprint $table) {
            $table->id();
            $table->string('nom');
            $table->text('description')->nullable();
            $table->string('code_interne')->nullable();
            $table->decimal('prix_journalier', 10, 2);
            $table->decimal('caution', 10, 2)->default(0);
            $table->integer('quantite_totale')->default(1);
            $table->enum('etat', ['neuf', 'bon', 'use'])->default('bon');
            $table->boolean('actif')->default(true);
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('caftans');
    }
};
