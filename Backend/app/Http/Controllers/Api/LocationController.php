<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\Location;
use App\Models\LigneLocation;
use App\Models\Caftan;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;

class LocationController extends Controller
{
    // GET /api/locations (locations de l'utilisateur connecté)
    public function index(Request $request)
    {
        $locations = Location::with('lignes.caftan')
            ->where('user_id', $request->user()->id)
            ->orderBy('created_at', 'desc')
            ->get();

        return response()->json($locations);
    }

    // GET /api/locations/{id}
    public function show($id, Request $request)
    {
        $location = Location::with('lignes.caftan')
            ->where('user_id', $request->user()->id)
            ->findOrFail($id);

        return response()->json($location);
    }

    // POST /api/locations
    // Body JSON attendu :
    // { "caftan_id": 1, "quantite": 1, "date_debut": "2025-12-01", "date_fin": "2025-12-03" }
    public function store(Request $request)
    {
        $data = $request->validate([
            'caftan_id'  => 'required|exists:caftans,id',
            'quantite'   => 'required|integer|min:1',
            'date_debut' => 'required|date|after_or_equal:today',
            'date_fin'   => 'required|date|after_or_equal:date_debut',
        ]);

        $user = $request->user();
        $caftan = Caftan::findOrFail($data['caftan_id']);

        // calcul nombre de jours
        $debut = new \DateTime($data['date_debut']);
        $fin   = new \DateTime($data['date_fin']);
        $interval = $debut->diff($fin)->days + 1; // inclure le dernier jour

        $prixUnitaire = $caftan->prix_journalier * $interval;
        $sousTotal    = $prixUnitaire * $data['quantite'];
        $montantCaution = $caftan->caution * $data['quantite'];

        DB::beginTransaction();

        try {
            $location = Location::create([
                'user_id'         => $user->id,
                'date_debut'      => $data['date_debut'],
                'date_fin'        => $data['date_fin'],
                'montant_total'   => $sousTotal,
                'montant_caution' => $montantCaution,
                'statut'          => 'en_attente',
            ]);

            LigneLocation::create([
                'location_id'  => $location->id,
                'caftan_id'    => $caftan->id,
                'quantite'     => $data['quantite'],
                'prix_unitaire'=> $prixUnitaire,
                'sous_total'   => $sousTotal,
            ]);

            DB::commit();

            return response()->json($location->load('lignes.caftan'), 201);
        } catch (\Throwable $e) {
            DB::rollBack();
            return response()->json([
                'message' => 'Erreur lors de la création de la location',
                'error'   => $e->getMessage(),
            ], 500);
        }
    }
}
 