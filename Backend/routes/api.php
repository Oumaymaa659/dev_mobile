<?php


use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;
use App\Http\Controllers\Api\PanierController;
use App\Http\Controllers\Api\AuthController;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider and all of them will
| be assigned to the "api" middleware group. Make something great!
|
*/

// Routes publiques (pas d'authentification requise)
Route::post('/login', [AuthController::class, 'login']);

// Routes Panier - MODE INVITÉ (pas d'authentification requise)
Route::get('/panier', [PanierController::class, 'show']);
Route::post('/panier/ajouter', [PanierController::class, 'ajouter']);
Route::put('/panier/ligne/{id}', [PanierController::class, 'modifierLigne']);
Route::delete('/panier/ligne/{id}', [PanierController::class, 'supprimerLigne']);
Route::delete('/panier/vider', [PanierController::class, 'vider']);
Route::post('/panier/confirmer', [PanierController::class, 'confirmer']); // Avec panier existant
Route::post('/panier/confirmer-direct', [PanierController::class, 'confirmerDirect']); // Panier local mobile

// Routes authentifiées (optionnel)
Route::middleware('auth:sanctum')->group(function () {
    Route::get('/user', function (Request $request) {
        return $request->user();
    });
    
    Route::post('/logout', [AuthController::class, 'logout']);
});
