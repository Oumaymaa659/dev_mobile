<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Str;
use App\Models\Panier;
use App\Models\LignePanier;
use App\Models\Location;
use App\Models\LigneLocation;
use App\Models\Caftan;
use App\Models\User;

class PanierController extends Controller
{
    // Retourne le panier actif de l'utilisateur (Mode Invité supporté)
    public function show(Request $request)
    {
        // Mode invité : utiliser user_id passé en paramètre si disponible
        $userId = $request->input('user_id') ?? $request->user()?->id;
        
        if (!$userId) {
            // Panier temporaire vide pour invités sans user_id
            return response()->json([
                'id' => null,
                'statut' => 'actif',
                'lignes' => []
            ]);
        }

        $panier = Panier::with('lignes.caftan')
            ->where('user_id', $userId)
            ->where('statut', 'actif')
            ->first();

        if (!$panier) {
            // Créer un panier vide
            $panier = Panier::create([
                'user_id' => $userId,
                'statut' => 'actif'
            ]);
            $panier->load('lignes.caftan');
        }

        return response()->json($panier);
    }

    // Ajouter un caftan au panier (Mode Invité supporté)
    public function ajouter(Request $request)
    {
        $data = $request->validate([
            'caftan_id'  => 'required|exists:caftans,id',
            'quantite'   => 'required|integer|min:1',
            'date_debut' => 'required|date|after_or_equal:today',
            'date_fin'   => 'required|date|after_or_equal:date_debut',
            'user_id'    => 'nullable|exists:users,id' // Pour mode invité
        ]);

        // Mode invité : utiliser user_id passé ou créer un user temporaire
        $userId = $data['user_id'] ?? $request->user()?->id;
        
        if (!$userId) {
            return response()->json([
                'success' => false,
                'message' => 'Veuillez fournir un user_id ou vous authentifier.'
            ], 400);
        }

        $panier = Panier::firstOrCreate([
            'user_id' => $userId,
            'statut' => 'actif'
        ]);

        // Vérif disponibilité
        if (! $this->caftanDisponible($data['caftan_id'], $data['date_debut'], $data['date_fin'], $data['quantite'])) {
            return response()->json([
                'success' => false,
                'message' => "Ce caftan n'est pas disponible pour la période / quantité demandée."
            ], 422);
        }

        $caftan = Caftan::findOrFail($data['caftan_id']);

        // calcul jours inclusifs
        $debut = new \DateTime($data['date_debut']);
        $fin = new \DateTime($data['date_fin']);
        $jours = $debut->diff($fin)->days + 1;

        $prixUnitaire = $caftan->prix_journalier * $jours;
        $sousTotal = $prixUnitaire * $data['quantite'];

        // si caftan déjà présent avec mêmes dates -> augmenter quantité
        $ligne = LignePanier::where('panier_id', $panier->id)
            ->where('caftan_id', $data['caftan_id'])
            ->where('date_debut', $data['date_debut'])
            ->where('date_fin', $data['date_fin'])
            ->first();

        if ($ligne) {
            $ligne->quantite += $data['quantite'];
            $ligne->sous_total = $ligne->quantite * $prixUnitaire;
            $ligne->save();
        } else {
            $ligne = LignePanier::create([
                'panier_id'    => $panier->id,
                'caftan_id'    => $data['caftan_id'],
                'quantite'     => $data['quantite'],
                'prix_unitaire'=> $prixUnitaire,
                'date_debut'   => $data['date_debut'],
                'date_fin'     => $data['date_fin'],
                'sous_total'   => $sousTotal
            ]);
        }

        return response()->json([
            'success' => true,
            'message' => 'Article ajouté au panier',
            'panier'  => $panier->load('lignes.caftan'),
            'user_id' => $userId // Retourner user_id pour stockage côté client
        ], 201);
    }

    // Modifier une ligne (quantite ou dates)
    public function modifierLigne(Request $request, $id)
    {
        $data = $request->validate([
            'quantite'   => 'sometimes|required|integer|min:1',
            'date_debut' => 'sometimes|required|date|after_or_equal:today',
            'date_fin'   => 'sometimes|required|date|after_or_equal:date_debut'
        ]);

        $user = $request->user();
        $ligne = LignePanier::findOrFail($id);
        $panier = $ligne->panier;

        if ($panier->user_id !== $user->id || $panier->statut !== 'actif') {
            return response()->json(['message' => 'Accès refusé.'], 403);
        }

        $quantite = $data['quantite'] ?? $ligne->quantite;
        $dateDebut = $data['date_debut'] ?? $ligne->date_debut;
        $dateFin = $data['date_fin'] ?? $ligne->date_fin;

        // vérif disponibilité
        if (! $this->caftanDisponible($ligne->caftan_id, $dateDebut, $dateFin, $quantite)) {
            return response()->json([
                'success' => false,
                'message' => "Ce caftan n'est pas disponible pour la période / quantité demandée."
            ], 422);
        }

        $caftan = Caftan::findOrFail($ligne->caftan_id);
        $jours = (new \DateTime($dateDebut))->diff(new \DateTime($dateFin))->days + 1;
        $prixUnitaire = $caftan->prix_journalier * $jours;
        $sousTotal = $prixUnitaire * $quantite;

        $ligne->update([
            'quantite' => $quantite,
            'date_debut'=> $dateDebut,
            'date_fin'  => $dateFin,
            'prix_unitaire' => $prixUnitaire,
            'sous_total' => $sousTotal
        ]);

        return response()->json([
            'success' => true,
            'message' => 'Ligne mise à jour',
            'ligne' => $ligne
        ]);
    }

    // Supprimer une ligne du panier
    public function supprimerLigne(Request $request, $id)
    {
        $user = $request->user();
        $ligne = LignePanier::findOrFail($id);
        $panier = $ligne->panier;

        if ($panier->user_id !== $user->id || $panier->statut !== 'actif') {
            return response()->json(['message' => 'Accès refusé.'], 403);
        }

        $ligne->delete();

        return response()->json(['success' => true, 'message' => 'Article supprimé du panier']);
    }

    // Vider le panier
    public function vider(Request $request)
    {
        $user = $request->user();
        $panier = Panier::where('user_id', $user->id)->where('statut', 'actif')->first();

        if ($panier) {
            $panier->lignes()->delete();
        }

        return response()->json(['success' => true, 'message' => 'Panier vidé']);
    }

    // Confirmer le panier -> Mode Invité ou Authentifié
    // Accepte les infos client et crée un compte automatiquement si besoin
    public function confirmer(Request $request)
    {
        // Valider les infos client (requises pour mode invité)
        $clientData = $request->validate([
            'nom' => 'required|string|max:255',
            'email' => 'required|email|max:255',
            'telephone' => 'required|string|max:20',
            'adresse' => 'nullable|string|max:500'
        ]);

        DB::beginTransaction();

        try {
            // Chercher ou créer l'utilisateur
            $user = User::where('email', $clientData['email'])->first();
            
            if (!$user) {
                // Créer automatiquement un compte client
                $user = User::create([
                    'name' => $clientData['nom'],
                    'email' => $clientData['email'],
                    'telephone' => $clientData['telephone'],
                    'password' => bcrypt(Str::random(16)), // Mot de passe aléatoire
                    'role' => 'client'
                ]);
            } else {
                // Mettre à jour les infos si l'utilisateur existe
                $user->update([
                    'name' => $clientData['nom'],
                    'telephone' => $clientData['telephone']
                ]);
            }

            // Récupérer le panier (soit du user authentifié, soit créer un nouveau)
            $panier = Panier::with('lignes.caftan')
                ->where('user_id', $user->id)
                ->where('statut', 'actif')
                ->first();

            if (!$panier || $panier->lignes->isEmpty()) {
                DB::rollBack();
                return response()->json(['message' => 'Panier vide.'], 422);
            }

            // Vérifier disponibilité pour chaque ligne
            foreach ($panier->lignes as $ligne) {
                if (!$this->caftanDisponible($ligne->caftan_id, $ligne->date_debut, $ligne->date_fin, $ligne->quantite)) {
                    DB::rollBack();
                    return response()->json([
                        'success' => false,
                        'message' => "Le caftan {$ligne->caftan->nom} n'est plus disponible pour la période/quantité demandée."
                    ], 422);
                }
            }

            // Calculer montants
            $dateDebut = $panier->lignes->min('date_debut');
            $dateFin = $panier->lignes->max('date_fin');
            $montantTotal = $panier->lignes->sum('sous_total');
            $montantCaution = $panier->lignes->sum(function($l){ return $l->caftan->caution * $l->quantite; });

            // Créer la location
            $location = Location::create([
                'user_id' => $user->id,
                'date_debut' => $dateDebut,
                'date_fin' => $dateFin,
                'montant_total' => $montantTotal,
                'montant_caution' => $montantCaution,
                'statut' => 'en_attente'
            ]);

            // Créer lignes_locations
            foreach ($panier->lignes as $ligne) {
                LigneLocation::create([
                    'location_id' => $location->id,
                    'caftan_id' => $ligne->caftan_id,
                    'quantite' => $ligne->quantite,
                    'prix_unitaire' => $ligne->prix_unitaire,
                    'sous_total' => $ligne->sous_total
                ]);
            }

            // Marquer panier comme confirmé
            $panier->statut = 'confirme';
            $panier->save();

            DB::commit();

            return response()->json([
                'success' => true,
                'message' => 'Réservation créée avec succès.',
                'location' => $location->load('lignes.caftan', 'user'),
                'user' => [
                    'id' => $user->id,
                    'nom' => $user->name,
                    'email' => $user->email,
                    'telephone' => $user->telephone
                ]
            ], 201);

        } catch (\Throwable $e) {
            DB::rollBack();
            return response()->json([
                'success' => false,
                'message' => 'Erreur lors de la confirmation.',
                'error' => $e->getMessage()
            ], 500);
        }
    }

    // Réutilise la même logique que précédemment
    private function caftanDisponible($caftanId, $dateDebut, $dateFin, $quantiteDemandee)
    {
        $caftan = Caftan::findOrFail($caftanId);

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

    // Mode Invité SIMPLIFIÉ - Confirmer directement avec panier local
    public function confirmerDirect(Request $request)
    {
        $data = $request->validate([
            'nom' => 'required|string|max:255',
            'email' => 'required|email|max:255',
            'telephone' => 'required|string|max:20',
            'adresse' => 'nullable|string|max:500',
            'items' => 'required|array|min:1',
            'items.*.caftan_id' => 'required|exists:caftans,id',
            'items.*.quantite' => 'required|integer|min:1',
            'items.*.date_debut' => 'required|date',
            'items.*.date_fin' => 'required|date|after_or_equal:items.*.date_debut'
        ]);

        DB::beginTransaction();

        try {
            // 1. Chercher ou créer l'utilisateur
            $user = User::where('email', $data['email'])->first();
            
            if (!$user) {
                $user = User::create([
                    'name' => $data['nom'],
                    'email' => $data['email'],
                    'telephone' => $data['telephone'],
                    'password' => bcrypt(Str::random(16)),
                    'role' => 'client'
                ]);
            } else {
                $user->update([
                    'name' => $data['nom'],
                    'telephone' => $data['telephone']
                ]);
            }

            // 2. Vérifier disponibilité et calculer montants
            $montantTotal = 0;
            $montantCaution = 0;
            $dateDebut = null;
            $dateFin = null;

            foreach ($data['items'] as $item) {
                if (!$this->caftanDisponible($item['caftan_id'], $item['date_debut'], $item['date_fin'], $item['quantite'])) {
                    DB::rollBack();
                    $caftan = Caftan::find($item['caftan_id']);
                    return response()->json([
                        'success' => false,
                        'message' => "Le caftan {$caftan->nom} n'est pas disponible."
                    ], 422);
                }

                $caftan = Caftan::findOrFail($item['caftan_id']);
                $jours = (new \DateTime($item['date_debut']))->diff(new \DateTime($item['date_fin']))->days + 1;
                $prixUnitaire = $caftan->prix_journalier * $jours;
                $sousTotal = $prixUnitaire * $item['quantite'];

                $montantTotal += $sousTotal;
                $montantCaution += $caftan->caution * $item['quantite'];

                if (!$dateDebut || $item['date_debut'] < $dateDebut) {
                    $dateDebut = $item['date_debut'];
                }
                if (!$dateFin || $item['date_fin'] > $dateFin) {
                    $dateFin = $item['date_fin'];
                }
            }

            // 3. Créer la location
            $location = Location::create([
                'user_id' => $user->id,
                'date_debut' => $dateDebut,
                'date_fin' => $dateFin,
                'montant_total' => $montantTotal,
                'montant_caution' => $montantCaution,
                'statut' => 'en_attente'
            ]);

            // 4. Créer les lignes de location
            foreach ($data['items'] as $item) {
                $caftan = Caftan::findOrFail($item['caftan_id']);
                $jours = (new \DateTime($item['date_debut']))->diff(new \DateTime($item['date_fin']))->days + 1;
                $prixUnitaire = $caftan->prix_journalier * $jours;
                $sousTotal = $prixUnitaire * $item['quantite'];

                LigneLocation::create([
                    'location_id' => $location->id,
                    'caftan_id' => $item['caftan_id'],
                    'quantite' => $item['quantite'],
                    'prix_unitaire' => $prixUnitaire,
                    'sous_total' => $sousTotal
                ]);
            }

            DB::commit();

            return response()->json([
                'success' => true,
                'message' => 'Réservation créée avec succès.',
                'location' => $location->load('lignes.caftan', 'user'),
                'user' => [
                    'id' => $user->id,
                    'nom' => $user->name,
                    'email' => $user->email,
                    'telephone' => $user->telephone
                ]
            ], 201);

        } catch (\Throwable $e) {
            DB::rollBack();
            return response()->json([
                'success' => false,
                'message' => 'Erreur lors de la confirmation.',
                'error' => $e->getMessage()
            ], 500);
        }
    }
}
