<?php

namespace App\Http\Controllers\Admin;

use App\Http\Controllers\Controller;
use App\Models\Location;
use Illuminate\Http\Request;

class AdminLocationController extends Controller
{
    // Liste des réservations
    public function index(Request $request)
    {
        $query = Location::with('user', 'lignes.caftan')
            ->orderByDesc('created_at');

        if ($request->filled('statut')) {
            $query->where('statut', $request->statut);
        }

        $locations = $query->paginate(15);

        return view('admin.locations.index', compact('locations'));
    }

    // Confirmer
    public function confirmer($id)
    {
        $location = Location::findOrFail($id);

        if ($location->statut !== 'en_attente') {
            return back()->with('error', 'Action non autorisée');
        }

        $location->update(['statut' => 'confirmee']);

        return back()->with('success', 'Réservation confirmée');
    }

    // Refuser
    public function refuser($id)
    {
        $location = Location::findOrFail($id);

        if ($location->statut !== 'en_attente') {
            return back()->with('error', 'Action non autorisée');
        }

        $location->update(['statut' => 'annulee']);

        return back()->with('success', 'Réservation refusée');
    }
}
