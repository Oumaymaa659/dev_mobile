<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Connexion - Dar Caftan</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 min-h-screen flex items-center justify-center">
    <div class="bg-white p-8 rounded shadow-md w-full max-w-md">
        <h1 class="text-2xl font-bold mb-6 text-center">👗 Dar Caftan</h1>
        <h2 class="text-xl mb-4">Connexion Admin</h2>

        @if($errors->any())
            <div class="bg-red-100 text-red-700 p-3 rounded mb-4">
                {{ $errors->first() }}
            </div>
        @endif

        <form method="POST" action="{{ route('login') }}">
            @csrf

            <div class="mb-4">
                <label class="block font-semibold mb-2">Email</label>
                <input type="email" name="email" value="{{ old('email') }}"
                       class="w-full border rounded px-3 py-2" required autofocus>
            </div>

            <div class="mb-4">
                <label class="block font-semibold mb-2">Mot de passe</label>
                <input type="password" name="password"
                       class="w-full border rounded px-3 py-2" required>
            </div>

            <button class="w-full bg-blue-600 hover:bg-blue-700 text-white py-2 rounded">
                Se connecter
            </button>
        </form>
    </div>
</body>
</html>
