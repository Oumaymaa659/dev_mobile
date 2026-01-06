<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use Illuminate\Http\Request;

class CaftanController extends Controller
{
    // GET /api/caftans
    public function index()
    {
        $caftans = Caftan::with('images')
            ->where('actif', true)
            ->get();

        return response()->json($caftans);
    }

    // GET /api/caftans/{id}
    public function show($id)
    {
        $caftan = Caftan::with('images')->findOrFail($id);

        return response()->json($caftan);
    }
    public function disponibilite(Request $request, $id)
    {
        $data = $request->validate([
            'date_debut' => 'required|date',
            'date_fin'   => 'required|date|after_or_equal:date_debut',
            'quantite'   => 'required|integer|min:1'
        ]);

        $caftan = Caftan::findOrFail($id);

        // Quantité déjà réservée sur cette période
        $quantiteReservee = \App\Models\LigneLocation::join('locations', 'lignes_locations.location_id', '=', 'locations.id')
            ->where('lignes_locations.caftan_id', $id)
            ->whereNotIn('locations.statut', ['annulee'])
            ->where(function ($query) use ($data) {
                $query->whereBetween('locations.date_debut', [$data['date_debut'], $data['date_fin']])
                    ->orWhereBetween('locations.date_fin', [$data['date_debut'], $data['date_fin']])
                    ->orWhere(function ($q) use ($data) {
                        $q->where('locations.date_debut', '<=', $data['date_debut'])
                            ->where('locations.date_fin', '>=', $data['date_fin']);
                    });
            })
            ->sum('lignes_locations.quantite');

        $reste = $caftan->quantite_totale - $quantiteReservee;

        return response()->json([
            'caftan'             => $caftan->nom,
            'quantite_demandee'  => $data['quantite'],
            'quantite_disponible'=> max($reste, 0),
            'disponible'         => $reste >= $data['quantite']
        ]);
    }

    private function caftanDisponible($caftanId, $dateDebut, $dateFin, $quantiteDemandee)
    {
        $caftan = \App\Models\Caftan::findOrFail($caftanId);

        $quantiteReservee = \App\Models\LigneLocation::join('locations', 'lignes_locations.location_id', '=', 'locations.id')
            ->where('lignes_locations.caftan_id', $caftanId)
            ->whereNotIn('locations.statut', ['annulee'])
            ->where(function ($query) use ($dateDebut, $dateFin) {
                $query->whereBetween('locations.date_debut', [$dateDebut, $dateFin])
                    ->orWhereBetween('locations.date_fin', [$dateDebut, $dateFin])
                    ->orWhere(function ($q) use ($dateDebut, $dateFin) {
                        $q->where('locations.date_debut', '<=', $dateDebut)
                            ->where('locations.date_fin', '>=', $dateFin);
                    });
            })
            ->sum('lignes_locations.quantite');

        $reste = $caftan->quantite_totale - $quantiteReservee;

        return $reste >= $quantiteDemandee;
    }


}