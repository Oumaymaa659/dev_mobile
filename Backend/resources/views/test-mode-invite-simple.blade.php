<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Test Mode Invité SIMPLIFIÉ - Dar Caftan</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 p-8">
    <div class="max-w-4xl mx-auto">
        <h1 class="text-3xl font-bold mb-6">🛒 Mode Invité Simplifié (Panier Local)</h1>

        <div class="bg-blue-100 border-l-4 border-blue-500 p-4 mb-6">
            <p class="font-bold">Simulation App Mobile</p>
            <p>Le panier est stocké LOCALEMENT, puis envoyé en une seule fois avec vos infos</p>
        </div>

        <!-- Panier Local (affiché) -->
        <div class="bg-white p-6 rounded shadow mb-6">
            <h2 class="text-xl font-bold mb-4">Mon Panier Local</h2>
            <div id="panier-local" class="space-y-2 mb-4">
                <p class="text-gray-500">Panier vide</p>
            </div>
            
            <h3 class="font-bold mt-4 mb-2">Ajouter un caftan :</h3>
            <div class="grid grid-cols-4 gap-2">
                <input type="number" id="caftan_id" value="1" placeholder="ID" class="border rounded px-2 py-1">
                <input type="number" id="quantite" value="1" placeholder="Qté" class="border rounded px-2 py-1">
                <input type="date" id="date_debut" class="border rounded px-2 py-1">
                <input type="date" id="date_fin" class="border rounded px-2 py-1">
            </div>
            <button onclick="ajouterLocal()" class="mt-2 bg-green-600 text-white px-4 py-2 rounded w-full">
                Ajouter au Panier Local
            </button>
        </div>

        <!-- Formulaire Client + Confirmation-->
        <div class="bg-white p-6 rounded shadow mb-6">
            <h2 class="text-xl font-bold mb-4">Confirmer et Finaliser</h2>
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
                <button onclick="confirmerDirect()" class="bg-orange-600 text-white px-4 py-2 rounded w-full">
                    Confirmer ma Commande
                </button>
                <div id="confirm-result" class="mt-4"></div>
            </div>
        </div>

        <!-- Debug -->
        <div class="bg-gray-800 text-white p-4 rounded">
            <strong>Debug :</strong>
            <pre id="debug-info" class="text-sm mt-2 overflow-auto max-h-60">Aucune action</pre>
        </div>
    </div>

    <script>
        let panierLocal = [];
        const API_BASE = 'http://localhost:8000/api';

        // Initialiser dates
        window.addEventListener('DOMContentLoaded', function() {
            const today = new Date();
            const tomorrow = new Date(today);
            tomorrow.setDate(tomorrow.getDate() + 1);
            const dayAfter = new Date(today);
            dayAfter.setDate(dayAfter.getDate() + 3);
            
            document.getElementById('date_debut').value = tomorrow.toISOString().split('T')[0];
            document.getElementById('date_fin').value = dayAfter.toISOString().split('T')[0];
        });

        // Ajouter au panier local
        function ajouterLocal() {
            const item = {
                caftan_id: parseInt(document.getElementById('caftan_id').value),
                quantite: parseInt(document.getElementById('quantite').value),
                date_debut: document.getElementById('date_debut').value,
                date_fin: document.getElementById('date_fin').value
            };

            panierLocal.push(item);
            afficherPanierLocal();
            
            document.getElementById('debug-info').textContent = 
                'Panier local mis à jour:\n' + JSON.stringify(panierLocal, null, 2);
        }

        // Afficher panier local
        function afficherPanierLocal() {
            const container = document.getElementById('panier-local');
            
            if (panierLocal.length === 0) {
                container.innerHTML = '<p class="text-gray-500">Panier vide</p>';
                return;
            }

            let html = '';
            panierLocal.forEach((item, index) => {
                html += `<div class="border-l-4 border-green-500 bg-green-50 p-2">
                    <strong>Caftan #${item.caftan_id}</strong> - Qté: ${item.quantite}<br>
                    <span class="text-sm text-gray-600">${item.date_debut} → ${item.date_fin}</span>
                    <button onclick="supprimerLocal(${index})" class="ml-2 text-red-600 text-sm">Supprimer</button>
                </div>`;
            });
            container.innerHTML = html;
        }

        // Supprimer du panier local
        function supprimerLocal(index) {
            panierLocal.splice(index, 1);
            afficherPanierLocal();
        }

        // Confirmer directement (Mode Invité Simplifié)
        async function confirmerDirect() {
            if (panierLocal.length === 0) {
                alert('Votre panier est vide !');
                return;
            }

            const payload = {
                nom: document.getElementById('client_nom').value,
                email: document.getElementById('client_email').value,
                telephone: document.getElementById('client_telephone').value,
                adresse: document.getElementById('client_adresse').value,
                items: panierLocal
            };

            if (!payload.nom || !payload.email || !payload.telephone) {
                alert('Veuillez remplir tous les champs obligatoires (*)');
                return;
            }

            if (!confirm('Confirmer la commande ?')) return;

            try {
                document.getElementById('debug-info').textContent = 
                    'Envoi confirmation directe...\n' + JSON.stringify(payload, null, 2);

                const response = await fetch(`${API_BASE}/panier/confirmer-direct`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        'Accept': 'application/json'
                    },
                    body: JSON.stringify(payload)
                });

                const data = await response.json();
                
                document.getElementById('debug-info').textContent = 
                    'Réponse:\n' + JSON.stringify(data, null, 2);

                if (data.success) {
                    document.getElementById('confirm-result').innerHTML = 
                        `<div class="bg-green-100 text-green-700 p-4 rounded">
                            <div class="text-xl font-bold mb-2">✅ ${data.message}</div>
                            <div class="space-y-1">
                                <div><strong>Location ID:</strong> ${data.location.id}</div>
                                <div><strong>Statut:</strong> <span class="px-2 py-1 bg-yellow-200 rounded">${data.location.statut}</span></div>
                                <div><strong>Montant Total:</strong> ${data.location.montant_total} DH</div>
                                <div><strong>Caution:</strong> ${data.location.montant_caution} DH</div>
                                <hr class="my-2">
                                <div><strong>Client:</strong> ${data.user.nom}</div>
                                <div><strong>Email:</strong> ${data.user.email}</div>
                                <div><strong>Téléphone:</strong> ${data.user.telephone}</div>
                            </div>
                        </div>`;
                    
                    // Vider le panier local
                    panierLocal = [];
                    afficherPanierLocal();
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
