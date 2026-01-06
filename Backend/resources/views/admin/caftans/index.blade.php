@extends('admin.layouts.app')
@section('title', 'Caftans')

@section('content')
<h1 class="text-2xl font-bold mb-6">🗑️ Gestion des Caftans</h1>

<table class="w-full bg-white rounded shadow overflow-hidden">
    <thead class="bg-gray-200">
        <tr>
            <th class="p-3 text-left">Nom</th>
            <th class="p-3">Prix</th>
            <th class="p-3">Quantité</th>
            <th class="p-3">Collection</th>
            <th class="p-3">Action</th>
        </tr>
    </thead>
    <tbody>
        @foreach($caftans as $caftan)
            <tr class="border-t">
                <td class="p-3">{{ $caftan->nom }}</td>
                <td class="p-3 text-center">{{ $caftan->prix_journalier }} DH</td>
                <td class="p-3 text-center">{{ $caftan->quantite_totale }}</td>
                <td class="p-3 text-center">{{ optional($caftan->collection)->nom ?? '-' }}</td>
                <td class="p-3 text-center">
                    <form method="POST" action="{{ route('admin.caftans.destroy', $caftan->id) }}">
                        @csrf
                        @method('DELETE')
                        <button class="bg-red-600 hover:bg-red-700 text-white px-3 py-1 rounded"
                                onclick="return confirm('Supprimer ce caftan ?')">
                            Supprimer
                        </button>
                    </form>
                </td>
            </tr>
        @endforeach
    </tbody>
</table>

<div class="mt-4">
    {{ $caftans->links() }}
</div>
@endsection
