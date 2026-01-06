@echo off
echo ================================================
echo SCRIPT DE CREATION DE BASE DE DONNEES
echo ================================================
echo.
echo Ce script va creer la base de donnees: dar_caftan
echo.
pause

mysql -u root -e "DROP DATABASE IF EXISTS dar_caftan;"
mysql -u root -e "CREATE DATABASE dar_caftan CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"

echo.
echo ================================================
echo Base de donnees 'dar_caftan' creee avec succes!
echo ================================================
echo.

echo Mise a jour du fichier .env...
powershell -Command "(Get-Content .env) -replace 'DB_DATABASE=laravel', 'DB_DATABASE=dar_caftan' | Set-Content .env"

echo.
echo Nettoyage du cache Laravel...
php artisan config:clear
php artisan cache:clear

echo.
echo ================================================
echo CONFIGURATION TERMINEE!
echo ================================================
echo.
echo Vous pouvez maintenant executer:
echo   php artisan migrate
echo.
pause
