# Mobile Project - Dar Caftan

Une plateforme complète de location et de vente de caftans de luxe, composée d'une application mobile Android et d'un backend Laravel.

## 📝 Description
Ce projet "Dar Caftan" est une solution moderne pour digitaliser une boutique de caftans. Il offre une expérience utilisateur fluide pour parcourir les collections, vérifier la disponibilité, et passer des commandes ou des réservations.

L'architecture repose sur une API RESTful robuste et une application mobile native performante.

## 🛠 Technologies Utilisées

### Backend (API REST)
- **Framework** : Laravel 10
- **Langage** : PHP 8.1
- **Authentification** : Laravel Sanctum
- **Base de Données** : MySQL
- **Outils** : Composer, Artisan

### Frontend (Application Mobile)
- **OS** : Android (Min SDK 24, Target SDK 36)
- **Langage** : Kotlin / Java
- **Réseau** : Retrofit 2, OkHttp 3, Volley
- **Gestion d'images** : Glide
- **UI** : Material DesignComponents, ConstraintLayout

## ✨ Fonctionnalités Clés
- **Catalogue Numérique** : Exploration des caftans par catégorie (Mariage, Soirée, etc.).
- **Panier & Commandes** : Gestion complète du cycle d'achat/location.
- **Système de Réservation** : Vérification des dates et disponibilités.
- **Compte Client** : Historique et suivi.
- **Administration** : Dashboard backend pour la gestion des produits et des commandes.

## 📚 Documentation & Démo
- **Cahier des Charges** : [Voir le PDF](docs/Cahier_de_charge.pdf)
- **Démonstration Vidéo** : [Voir la Démo](https://github.com/Oumaymaa659/dev_mobile/raw/main/media/demo.mp4)

## 🚀 Installation & Configuration

### Pré-requis
- PHP >= 8.1
- Composer
- Android Studio
- MySQL

### 1. Installation du Backend
```bash
cd Backend
composer install
cp .env.example .env
# Configurer les infos de base de données dans .env
php artisan key:generate
php artisan migrate --seed
php artisan serve
```

### 2. Installation du Mobile
1. Ouvrir le dossier `Frontend/front-dar/darcaftan2` dans **Android Studio**.
2. Laisser gradle synchroniser les dépendances.
3. Configurer l'URL de l'API dans les fichiers de configuration (ex: `RetrofitInstance` ou constantes).
4. Lancer sur un émulateur ou un device physique.
