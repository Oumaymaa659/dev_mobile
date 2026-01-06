<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Test Mode Invité - Dar Caftan</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 p-8">
    <div class="max-w-4xl mx-auto">
        <h1 class="text-3xl font-bold mb-6">🧪 Test Mode Invité (Sans Connexion)</h1>

        <!-- Info -->
        <div class="bg-blue-100 border-l-4 border-blue-500 p-4 mb-6">
            <p class="font-bold">Mode Invité Activé</p>
            <p>Vous pouvez ajouter au panier et confirmer SANS vous connecter !</p>
            <p class="mt-2">User ID Temporaire : <code id="temp-user-id" class="bg-white px-2 py-1 rounded"></code></p>
        </div>

        <!-- 1. Ajouter au Panier -->
        <div class="bg-white p-6 rounded shadow mb-6">
            <h2 class="text-xl font-bold mb-4">1. Ajouter un Caftan au Panier</h2>
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
                <button onclick="ajouterAuPanier()" class="bg-green-600 text-white px-4 py-2 rounded w-full">
                    Ajouter au Panier (Mode Invité)
                </button>
                <div id="panier-result" class="mt-2"></div>
            </div>
        </div>

        <!-- 2. Voir le Panier -->
        <div class="bg-white p-6 rounded shadow mb-6">
            <h2 class="text-xl font-bold mb-4">2. Mon Panier</h2>
            <button onclick="voirPanier()" class="bg-purple-600 text-white px-4 py-2 rounded mb-4">
                Afficher le Panier
            </button>
            <div id="panier-display" class="mt-4"></div>
        </div>

        <!-- 3. Formulaire Client + Confirmation -->
        <div class="bg-white p-6 rounded shadow mb-6">
            <h2 class="text-xl font-bold mb-4">3. Vos Informations et Confirmation</h2>
            <div class="space-y-3">
                <div>
                    <label class="font-semibold">Nom complet *</label>
                    <input type="text" id="client_nom" placeholder="Ex: Oumaima Benali"
                           class="w-full border rounded px-3 py-2">
                </div>
                <div>
                    <label class="font-semibold">Email *</label>
                    <input type="email" id="client_email" placeholder="Ex: oumaima@gmail.com"
                           class="w-full border rounded px-3 py-2">
                </div>
                <div>
                    <label class="font-semibold">Téléphone *</label>
                    <input type="tel" id="client_telephone" placeholder="Ex: 0612345678"
                           class="w-full border rounded px-3 py-2">
                </div>
                <div>
                    <label class="font-semibold">Adresse (optionnel)</label>
                    <input type="text" id="client_adresse" placeholder="Ex: 123 Rue..."
                           class="w-full border rounded px-3 py-2">
                </div>
                <button onclick="confirmerCommande()" class="bg-orange-600 text-white px-4 py-2 rounded w-full">
                    Confirmer ma Commande
                </button>
                <div id="confirm-result" class="mt-4"></div>
            </div>
        </div>

        <!-- Debug Info -->
        <div class="bg-gray-800 text-white p-4 rounded">
            <strong>Debug Info :</strong>
            <pre id="debug-info" class="text-sm mt-2 overflow-auto">Aucune action encore</pre>
        </div>
    </div>

    <script>
        let tempUserId = null;
        const API_BASE = 'http://localhost:8000/api';

        // Générer un ID utilisateur temporaire au chargement
        window.addEventListener('DOMContentLoaded', function() {
            // Vérifier si on a déjà un user_id temporaire
            tempUserId = localStorage.getItem('temp_user_id');
            
            if (!tempUserId) {
                // Utiliser un user existant pour le test (client 1 = ID 2)
                tempUserId = 2; // Client 1 de notre test data
                localStorage.setItem('temp_user_id', tempUserId);
            }
            
            document.getElementById('temp-user-id').textContent = tempUserId;

            // Initialiser les dates
            const today = new Date();
            const tomorrow = new Date(today);
            tomorrow.setDate(tomorrow.getDate() + 1);
            const dayAfter = new Date(today);
            dayAfter.setDate(dayAfter.getDate() + 3);
            
            document.getElementById('date_debut').value = tomorrow.toISOString().split('T')[0];
            document.getElementById('date_fin').value = dayAfter.toISOString().split('T')[0];

            // Charger le panier au démarrage
            voirPanier();
        });

        // Ajouter au panier (Mode Invité)
        async function ajouterAuPanier() {
            const payload = {
                caftan_id: parseInt(document.getElementById('caftan_id').value),
                quantite: parseInt(document.getElementById('quantite').value),
                date_debut: document.getElementById('date_debut').value,
                date_fin: document.getElementById('date_fin').value,
                user_id: parseInt(tempUserId) // Mode invité avec user_id temp
            };

            try {
                document.getElementById('debug-info').textContent = 
                    'Envoi requête...\n' + JSON.stringify(payload, null, 2);

                const response = await fetch(`${API_BASE}/panier/ajouter`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        'Accept': 'application/json'
                    },
                    body: JSON.stringify(payload)
                });

                const data = await response.json();
                
                document.getElementById('debug-info').textContent = 
                    'Réponse reçue:\n' + JSON.stringify(data, null, 2);

                if (data.success) {
                    document.getElementById('panier-result').innerHTML = 
                        '<div class="bg-green-100 text-green-700 p-2 rounded">✅ ' + data.message + '</div>';
                    // Rafraîchir l'affichage du panier
                    voirPanier();
                } else {
                    document.getElementById('panier-result').innerHTML = 
                        '<div class="bg-red-100 text-red-700 p-2 rounded">❌ ' + (data.message || 'Erreur') + '</div>';
                }
            } catch (error) {
                document.getElementById('panier-result').innerHTML = 
                    '<div class="bg-red-100 text-red-700 p-2 rounded">❌ ' + error.message + '</div>';
                document.getElementById('debug-info').textContent = 'Erreur: ' + error.message;
            }
        }

        // Voir le panier
        async function voirPanier() {
            try {
                const response = await fetch(`${API_BASE}/panier?user_id=${tempUserId}`, {
                    headers: {
                        'Accept': 'application/json'
                    }
                });

                const data = await response.json();
                
                let html = '<div class="border rounded p-3">';
                html += '<h3 class="font-bold text-lg mb-2">Articles dans le panier :</h3>';
                
                if (data.lignes && data.lignes.length > 0) {
                    let total = 0;
                    data.lignes.forEach(ligne => {
                        html += `<div class="border-t mt-2 pt-2">
                            <div class="font-semibold">${ligne.caftan.nom}</div>
                            <div class="text-sm text-gray-600">Quantité: ${ligne.quantite}</div>
                            <div class="text-sm text-gray-600">Prix: ${ligne.sous_total} DH</div>
                            <div class="text-sm text-gray-600">Dates: ${ligne.date_debut} → ${ligne.date_fin}</div>
                        </div>`;
                        total += parseFloat(ligne.sous_total);
                    });
                    html += `<div class="mt-3 pt-3 border-t font-bold text-xl">Total : ${total} DH</div>`;
                } else {
                    html += '<p class="mt-2 text-gray-500">Panier vide</p>';
                }
                
                html += '</div>';
                document.getElementById('panier-display').innerHTML = html;
            } catch (error) {
                document.getElementById('panier-display').innerHTML = 
                    '<div class="bg-red-100 text-red-700 p-2 rounded">❌ ' + error.message + '</div>';
            }
        }

        // Confirmer la commande (Mode Invité)
        async function confirmerCommande() {
            const clientData = {
                nom: document.getElementById('client_nom').value,
                email: document.getElementById('client_email').value,
                telephone: document.getElementById('client_telephone').value,
                adresse: document.getElementById('client_adresse').value
            };

            // Validation
            if (!clientData.nom || !clientData.email || !clientData.telephone) {
                alert('Veuillez remplir tous les champs obligatoires (*)');
                return;
            }

            if (!confirm('Confirmer la commande avec ces informations ?')) return;

            try {
                document.getElementById('debug-info').textContent = 
                    'Envoi confirmation...\n' + JSON.stringify(clientData, null, 2);

                const response = await fetch(`${API_BASE}/panier/confirmer`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        'Accept': 'application/json'
                    },
                    body: JSON.stringify(clientData)
                });

                const data = await response.json();
                
                document.getElementById('debug-info').textContent = 
                    'Réponse confirmation:\n' + JSON.stringify(data, null, 2);

                if (data.success) {
                    document.getElementById('confirm-result').innerHTML = 
                        `<div class="bg-green-100 text-green-700 p-4 rounded">
                            <div class="text-xl font-bold mb-2">✅ ${data.message}</div>
                            <div class="space-y-1">
                                <div><strong>Location ID:</strong> ${data.location.id}</div>
                                <div><strong>Statut:</strong> <span class="px-2 py-1 bg-yellow-200 rounded">${data.location.statut}</span></div>
                                <div><strong>Montant Total:</strong> ${data.location.montant_total} DH</div>
                                <div><strong>Montant Caution:</strong> ${data.location.montant_caution} DH</div>
                                <hr class="my-2">
                                <div><strong>Client:</strong> ${data.user.nom}</div>
                                <div><strong>Email:</strong> ${data.user.email}</div>
                                <div><strong>Téléphone:</strong> ${data.user.telephone}</div>
                            </div>
                        </div>`;
                    
                    // Vider le panier affiché
                    document.getElementById('panier-display').innerHTML = 
                        '<div class="bg-gray-100 p-4 rounded">Panier confirmé et vidé ✅</div>';
                } else {
                    document.getElementById('confirm-result').innerHTML = 
                        '<div class="bg-red-100 text-red-700 p-2 rounded">❌ ' + (data.message || 'Erreur') + '</div>';
                }
            } catch (error) {
                document.getElementById('confirm-result').innerHTML = 
                    '<div class="bg-red-100 text-red-700 p-2 rounded">❌ ' + error.message + '</div>';
                document.getElementById('debug-info').textContent = 'Erreur: ' + error.message;
            }
        }
    </script>
</body>
</html>
