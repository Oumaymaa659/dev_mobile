<?php

namespace App\Http\Controllers\Api\Admin;

use App\Http\Controllers\Controller;
use App\Models\Caftan;
use Illuminate\Http\Request;

class CaftanAdminController extends Controller
{
    // POST /api/admin/caftans
    public function store(Request $request)
    {
        $data = $request->validate([
            'nom'               => 'required|string|max:255',
            'description'       => 'nullable|string',
            'prix_journalier'   => 'required|numeric|min:0',
            'caution'           => 'required|numeric|min:0',
            'quantite_totale'   => 'required|integer|min:1',
            'etat'              => 'required|in:neuf,bon,use'
        ]);

        $caftan = Caftan::create($data);

        return response()->json([
            'message' => 'Caftan créé avec succès',
            'caftan'  => $caftan
        ], 201);
    }
}
