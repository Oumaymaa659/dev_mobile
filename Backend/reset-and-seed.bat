@echo off
echo ================================================
echo RESET COMPLET + SEED BASE DE DONNEES
echo ================================================
echo.

echo [1/5] Migration fresh...
php artisan migrate:fresh
if errorlevel 1 (
    echo ERREUR: Migration a echoue!
    pause
    exit /b 1
)

echo.
echo [2/5] Ajout colonnes manquantes...
mysql -u root -P 3307 -e "ALTER TABLE dar_caftan.caftans ADD COLUMN collection_id BIGINT UNSIGNED NULL AFTER actif, ADD COLUMN taille VARCHAR(255) NULL AFTER collection_id, ADD COLUMN couleur VARCHAR(255) NULL AFTER taille, ADD COLUMN deleted_at TIMESTAMP NULL AFTER updated_at, ADD CONSTRAINT fk_caftans_collection FOREIGN KEY (collection_id) REFERENCES collections(id) ON DELETE SET NULL"

echo.
echo [3/5] Seed des donnees de test...
php artisan db:seed --class=TestDataSeeder
if errorlevel 1 (
    echo ERREUR: Seeder a echoue!
    pause
    exit /b 1
)

echo.
echo [4/5] Creation utilisateur admin principal...
php artisan tinker --execute="App\Models\User::create(['name' => 'Admin', 'email' => 'admin@darcaftan.ma', 'password' => bcrypt('admin123'), 'role' => 'admin']);"

echo.
echo [5/5] Verification...
mysql -u root -P 3307 -e "SELECT 'Collections:', COUNT(*) FROM dar_caftan.collections; SELECT 'Caftans:', COUNT(*) FROM dar_caftan.caftans; SELECT 'Users:', COUNT(*) FROM dar_caftan.users; SELECT 'Locations:', COUNT(*) FROM dar_caftan.locations;"

echo.
echo ================================================
echo TERMINE !
echo ================================================
echo.
echo Vous pouvez maintenant tester avec:
echo   php artisan serve
echo.
echo Comptes disponibles:
echo   Admin principal: admin@darcaftan.ma / admin123
echo   Admin test: admin@test.com / password
echo   Client 1: client1@test.com / password
echo   Client 2: client2@test.com / password
echo.
pause
