<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Test API Panier - Dar Caftan</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 p-8">
    <div class="max-w-4xl mx-auto">
        <h1 class="text-3xl font-bold mb-6">🧪 Test API Panier</h1>

        <!-- Login Client -->
        <div class="bg-white p-6 rounded shadow mb-6">
            <h2 class="text-xl font-bold mb-4">1. Connexion Client</h2>
            <div class="space-y-3">
                <div>
                    <label class="font-semibold">Email</label>
                    <input type="email" id="email" value="client1@test.com" 
                           class="w-full border rounded px-3 py-2">
                </div>
                <div>
                    <label class="font-semibold">Mot de passe</label>
                    <input type="password" id="password" value="password" 
                           class="w-full border rounded px-3 py-2">
                </div>
                <button onclick="login()" class="bg-blue-600 text-white px-4 py-2 rounded">
                    Se connecter
                </button>
                <div id="login-result" class="mt-2"></div>
            </div>
        </div>

        <!-- Ajouter au Panier -->
        <div class="bg-white p-6 rounded shadow mb-6">
            <h2 class="text-xl font-bold mb-4">2. Ajouter un Caftan au Panier</h2>
            <div class="space-y-3">
                <div>
                    <label class="font-semibold">Caftan ID</label>
                    <input type="number" id="caftan_id" value="1" 
                           class="w-full border rounded px-3 py-2">
                </div>
                <div class="grid grid-cols-2 gap-3">
                    <div>
                        <label class="font-semibold">Quantité</label>
                        <input type="number" id="quantite" value="1" 
                               class="w-full border rounded px-3 py-2">
                    </div>
                    <div>
                        <label class="font-semibold">Date début</label>
                        <input type="date" id="date_debut" 
                               class="w-full border rounded px-3 py-2">
                    </div>
                </div>
                <div>
                    <label class="font-semibold">Date fin</label>
                    <input type="date" id="date_fin" 
                           class="w-full border rounded px-3 py-2">
                </div>
                <button onclick="ajouterAuPanier()" class="bg-green-600 text-white px-4 py-2 rounded">
                    Ajouter au Panier
                </button>
                <div id="panier-result" class="mt-2"></div>
            </div>
        </div>

        <!-- Voir le Panier -->
        <div class="bg-white p-6 rounded shadow mb-6">
            <h2 class="text-xl font-bold mb-4">3. Voir le Panier</h2>
            <button onclick="voirPanier()" class="bg-purple-600 text-white px-4 py-2 rounded">
                Afficher le Panier
            </button>
            <div id="panier-display" class="mt-4"></div>
        </div>

        <!-- Confirmer le Panier -->
        <div class="bg-white p-6 rounded shadow mb-6">
            <h2 class="text-xl font-bold mb-4">4. Confirmer la Commande</h2>
            <button onclick="confirmerPanier()" class="bg-orange-600 text-white px-4 py-2 rounded">
                Confirmer le Panier
            </button>
            <div id="confirm-result" class="mt-4"></div>
        </div>

        <!-- Token -->
        <div class="bg-gray-800 text-white p-4 rounded">
            <strong>Token API :</strong>
            <code id="token-display" class="text-sm break-all">Connectez-vous d'abord</code>
        </div>
    </div>

    <script>
        let apiToken = null;

        // URLs API
        const API_BASE = 'http://localhost:8000/api';

        // Connexion
        async function login() {
            const email = document.getElementById('email').value;
            const password = document.getElementById('password').value;

            try {
                const response = await fetch(`${API_BASE}/login`, {
                    method: 'POST',
                    headers: {'Content-Type': 'application/json'},
                    body: JSON.stringify({ email, password })
                });

                const data = await response.json();
                
                if (data.token) {
                    apiToken = data.token;
                    document.getElementById('token-display').textContent = apiToken;
                    document.getElementById('login-result').innerHTML = 
                        '<div class="bg-green-100 text-green-700 p-2 rounded">✅ Connexion réussie!</div>';
                } else {
                    document.getElementById('login-result').innerHTML = 
                        '<div class="bg-red-100 text-red-700 p-2 rounded">❌ ' + (data.message || 'Erreur') + '</div>';
                }
            } catch (error) {
                document.getElementById('login-result').innerHTML = 
                    '<div class="bg-red-100 text-red-700 p-2 rounded">❌ ' + error.message + '</div>';
            }
        }

        // Ajouter au panier
        async function ajouterAuPanier() {
            if (!apiToken) {
                alert('Connectez-vous d\'abord!');
                return;
            }

            const payload = {
                caftan_id: parseInt(document.getElementById('caftan_id').value),
                quantite: parseInt(document.getElementById('quantite').value),
                date_debut: document.getElementById('date_debut').value,
                date_fin: document.getElementById('date_fin').value
            };

            try {
                const response = await fetch(`${API_BASE}/panier/ajouter`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        'Accept': 'application/json',
                        'Authorization': `Bearer ${apiToken}`
                    },
                    body: JSON.stringify(payload)
                });

                const data = await response.json();
                
                if (data.success) {
                    document.getElementById('panier-result').innerHTML = 
                        '<div class="bg-green-100 text-green-700 p-2 rounded">✅ ' + data.message + '</div>';
                } else {
                    document.getElementById('panier-result').innerHTML = 
                        '<div class="bg-red-100 text-red-700 p-2 rounded">❌ ' + (data.message || 'Erreur') + '</div>';
                }
            } catch (error) {
                document.getElementById('panier-result').innerHTML = 
                    '<div class="bg-red-100 text-red-700 p-2 rounded">❌ ' + error.message + '</div>';
            }
        }

        // Voir le panier
        async function voirPanier() {
            if (!apiToken) {
                alert('Connectez-vous d\'abord!');
                return;
            }

            try {
                const response = await fetch(`${API_BASE}/panier`, {
                    headers: {
                        'Authorization': `Bearer ${apiToken}`
                    }
                });

                const data = await response.json();
                
                let html = '<div class="border rounded p-3">';
                html += '<h3 class="font-bold">Articles dans le panier :</h3>';
                
                if (data.lignes && data.lignes.length > 0) {
                    data.lignes.forEach(ligne => {
                        html += `<div class="border-t mt-2 pt-2">
                            <div>Caftan: ${ligne.caftan.nom}</div>
                            <div>Quantité: ${ligne.quantite}</div>
                            <div>Prix: ${ligne.sous_total} DH</div>
                            <div>Dates: ${ligne.date_debut} → ${ligne.date_fin}</div>
                        </div>`;
                    });
                } else {
                    html += '<p class="mt-2">Panier vide</p>';
                }
                
                html += '</div>';
                document.getElementById('panier-display').innerHTML = html;
            } catch (error) {
                document.getElementById('panier-display').innerHTML = 
                    '<div class="bg-red-100 text-red-700 p-2 rounded">❌ ' + error.message + '</div>';
            }
        }

        // Confirmer le panier
        async function confirmerPanier() {
            if (!apiToken) {
                alert('Connectez-vous d\'abord!');
                return;
            }

            if (!confirm('Confirmer la commande?')) return;

            try {
                const response = await fetch(`${API_BASE}/panier/confirmer`, {
                    method: 'POST',
                    headers: {
                        'Authorization': `Bearer ${apiToken}`
                    }
                });

                const data = await response.json();
                
                if (data.success) {
                    document.getElementById('confirm-result').innerHTML = 
                        `<div class="bg-green-100 text-green-700 p-3 rounded">
                            ✅ ${data.message}<br>
                            <strong>Location ID:</strong> ${data.location.id}<br>
                            <strong>Statut:</strong> ${data.location.statut}<br>
                            <strong>Montant:</strong> ${data.location.montant_total} DH
                        </div>`;
                } else {
                    document.getElementById('confirm-result').innerHTML = 
                        '<div class="bg-red-100 text-red-700 p-2 rounded">❌ ' + (data.message || 'Erreur') + '</div>';
                }
            } catch (error) {
                document.getElementById('confirm-result').innerHTML = 
                    '<div class="bg-red-100 text-red-700 p-2 rounded">❌ ' + error.message + '</div>';
            }
        }

        // Initialiser les dates par défaut
        document.addEventListener('DOMContentLoaded', function() {
            const today = new Date();
            const tomorrow = new Date(today);
            tomorrow.setDate(tomorrow.getDate() + 1);
            const dayAfter = new Date(today);
            dayAfter.setDate(dayAfter.getDate() + 3);
            
            document.getElementById('date_debut').value = tomorrow.toISOString().split('T')[0];
            document.getElementById('date_fin').value = dayAfter.toISOString().split('T')[0];
        });
    </script>
</body>
</html>
