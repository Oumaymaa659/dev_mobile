@echo off
echo ================================================
echo TEST DIRECT DE L'API PANIER
echo ================================================
echo.

echo [1/2] Creation d'un token pour client1@test.com...
php artisan tinker <<EOF
\$user = App\Models\User::where('email', 'client1@test.com')->first();
\$token = \$user->createToken('test-token')->plainTextToken;
echo "TOKEN: " . \$token . "\n";
EOF

echo.
echo [2/2] Copiez le token ci-dessus et testez avec Postman:
echo.
echo Method: POST
echo URL: http://localhost:8000/api/panier/ajouter
echo Headers:
echo   - Content-Type: application/json
echo   - Accept: application/json
echo   - Authorization: Bearer [VOTRE_TOKEN]
echo.
echo Body (JSON):
echo {
echo   "caftan_id": 1,
echo   "quantite": 1,
echo   "date_debut": "2025-12-20",
echo   "date_fin": "2025-12-22"
echo }
echo.
pause
