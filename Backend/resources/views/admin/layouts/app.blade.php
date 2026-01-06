<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>@yield('title', 'Admin') - Dar Caftan</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 min-h-screen flex">

<!-- Sidebar -->
<aside class="w-64 bg-gray-900 text-white flex flex-col">
    <div class="p-6 text-2xl font-bold border-b border-gray-700">
        👗 Dar Caftan
    </div>

    <nav class="flex-1 p-4 space-y-2">
        <a href="{{ route('admin.dashboard') }}" class="block px-4 py-2 rounded hover:bg-gray-700">
            📊 Dashboard
        </a>
        <a href="{{ route('admin.caftans.create') }}" class="block px-4 py-2 rounded hover:bg-gray-700">
            ➕ Ajouter Caftan
        </a>
        <a href="{{ route('admin.caftans.index') }}" class="block px-4 py-2 rounded hover:bg-gray-700">
            🗑️ Gérer Caftans
        </a>
        <a href="{{ route('admin.locations.index') }}" class="block px-4 py-2 rounded hover:bg-gray-700">
            📦 Réservations
        </a>
    </nav>

    <form method="POST" action="{{ route('logout') }}" class="p-4 border-t border-gray-700">
        @csrf
        <button class="w-full bg-red-600 hover:bg-red-700 py-2 rounded">
            🚪 Déconnexion
        </button>
    </form>
</aside>

<!-- Content -->
<main class="flex-1 p-8">
    @if(session('success'))
        <div class="bg-green-100 text-green-700 p-3 rounded mb-4">
            {{ session('success') }}
        </div>
    @endif

    @if(session('error'))
        <div class="bg-red-100 text-red-700 p-3 rounded mb-4">
            {{ session('error') }}
        </div>
    @endif

    @yield('content')
</main>

</body>
</html>
