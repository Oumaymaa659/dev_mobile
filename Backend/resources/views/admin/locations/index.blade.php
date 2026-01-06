@extends('admin.layouts.app')
@section('title', 'Réservations')

@section('content')
<h1 class="text-2xl font-bold mb-6">📦 Réservations Clients</h1>

<table class="w-full bg-white rounded shadow overflow-hidden">
    <thead class="bg-gray-200">
        <tr>
            <th class="p-3">Client</th>
            <th class="p-3">Période</th>
            <th class="p-3">Montant</th>
            <th class="p-3">Statut</th>
            <th class="p-3">Action</th>
        </tr>
    </thead>
    <tbody>
        @foreach($locations as $location)
            <tr class="border-t">
                <td class="p-3">{{ $location->user->name }}</td>
                <td class="p-3 text-center">
                    {{ $location->date_debut }} → {{ $location->date_fin }}
                </td>
                <td class="p-3 text-center">{{ $location->montant_total }} DH</td>
                <td class="p-3 text-center">{{ $location->statut }}</td>
                <td class="p-3 text-center space-x-2">
                    @if($location->statut === 'en_attente')
                        <form method="POST" action="{{ route('admin.locations.confirmer', $location->id) }}" class="inline">
                            @csrf
                            <button class="bg-green-600 text-white px-2 py-1 rounded">
                                Confirmer
                            </button>
                        </form>

                        <form method="POST" action="{{ route('admin.locations.refuser', $location->id) }}" class="inline">
                            @csrf
                            <button class="bg-red-600 text-white px-2 py-1 rounded">
                                Refuser
                            </button>
                        </form>
                    @else
                        —
                    @endif
                </td>
            </tr>
        @endforeach
    </tbody>
</table>

<div class="mt-4">
    {{ $locations->links() }}
</div>
@endsection
