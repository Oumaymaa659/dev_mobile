@extends('admin.layouts.app')
@section('title', 'Ajouter Caftan')

@section('content')
<h1 class="text-2xl font-bold mb-6">➕ Ajouter un Caftan</h1>

<form method="POST" action="{{ route('admin.caftans.store') }}"
      enctype="multipart/form-data"
      class="bg-white p-6 rounded shadow max-w-xl">
    @csrf

    <div class="mb-4">
        <label class="font-semibold">Nom *</label>
        <input name="nom" class="w-full border rounded px-3 py-2" required>
    </div>

    <div class="mb-4">
        <label class="font-semibold">Prix journalier (DH) *</label>
        <input type="number" step="0.01" name="prix_journalier"
               class="w-full border rounded px-3 py-2" required>
    </div>

    <div class="mb-4">
        <label class="font-semibold">Quantité *</label>
        <input type="number" name="quantite_totale"
               class="w-full border rounded px-3 py-2" required>
    </div>

    <div class="mb-4">
        <label class="font-semibold">État *</label>
        <select name="etat" class="w-full border rounded px-3 py-2">
            <option value="neuf">Neuf</option>
            <option value="bon">Bon</option>
            <option value="use">Usé</option>
        </select>
    </div>

    <div class="mb-4">
        <label class="font-semibold">Collection</label>
        <select name="collection_id" class="w-full border rounded px-3 py-2">
            <option value="">-- Aucune --</option>
            @foreach($collections as $collection)
                <option value="{{ $collection->id }}">{{ $collection->nom }}</option>
            @endforeach
        </select>
    </div>

    <div class="mb-4">
        <label class="font-semibold">Image</label>
        <input type="file" name="image" class="w-full border rounded px-3 py-2">
    </div>

    <button class="bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded">
        Enregistrer
    </button>
</form>
@endsection
