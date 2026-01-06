@extends('admin.layouts.app')
@section('title', 'Dashboard')

@section('content')
<h1 class="text-3xl font-bold mb-6">📊 Dashboard Admin</h1>

<div class="grid grid-cols-1 md:grid-cols-4 gap-6">
    <div class="bg-white p-6 rounded shadow">
        <div class="text-gray-500">Total commandes</div>
        <div class="text-2xl font-bold">{{ $stats['total_locations'] }}</div>
    </div>

    <div class="bg-white p-6 rounded shadow">
        <div class="text-gray-500">En attente</div>
        <div class="text-2xl font-bold text-orange-600">{{ $stats['en_attente'] }}</div>
    </div>

    <div class="bg-white p-6 rounded shadow">
        <div class="text-gray-500">Confirmées</div>
        <div class="text-2xl font-bold text-green-600">{{ $stats['confirmees'] }}</div>
    </div>

    <div class="bg-white p-6 rounded shadow">
        <div class="text-gray-500">Caftans</div>
        <div class="text-2xl font-bold">{{ $stats['total_caftans'] }}</div>
    </div>
</div>
@endsection
