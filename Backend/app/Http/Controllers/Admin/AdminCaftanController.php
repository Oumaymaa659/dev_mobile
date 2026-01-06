<?php

namespace App\Http\Controllers\Admin;

use App\Http\Controllers\Controller;
use App\Models\Caftan;
use App\Models\Collection;
use App\Models\ImageCaftan;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Storage;

class AdminCaftanController extends Controller
{
    // Liste des caftans
    public function index()
    {
        $caftans = Caftan::with('collection')
            ->latest()
            ->paginate(10);

        return view('admin.caftans.index', compact('caftans'));
    }

    // Formulaire d'ajout
    public function create()
    {
        $collections = Collection::where('actif', true)->get();
        return view('admin.caftans.create', compact('collections'));
    }

    // Enregistrer un caftan
    public function store(Request $request)
    {
        $data = $request->validate([
            'nom'             => 'required|string|max:255',
            'prix_journalier' => 'required|numeric|min:0',
            'quantite_totale' => 'required|integer|min:1',
            'etat'            => 'required|in:neuf,bon,use',
            'collection_id'   => 'nullable|exists:collections,id',
            'image'           => 'nullable|image|max:2048',
            'description'     => 'nullable|string',
            'taille'          => 'nullable|string',
            'couleur'         => 'nullable|string',
        ]);

        // Créer le caftan
        $caftan = Caftan::create($data);

        // Upload image
        if ($request->hasFile('image')) {
            $path = $request->file('image')->store('caftans', 'public');

            ImageCaftan::create([
                'caftan_id'    => $caftan->id,
                'chemin_image' => $path,
                'principale'   => true,
            ]);
        }

        return redirect()
            ->route('admin.caftans.index')
            ->with('success', 'Caftan ajouté avec succès');
    }

    // Supprimer (soft delete)
    public function destroy($id)
    {
        Caftan::findOrFail($id)->delete();

        return back()->with('success', 'Caftan supprimé');
    }
}
