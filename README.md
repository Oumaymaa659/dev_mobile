# Mobile Project - Dar Caftan

Ce projet est une application mobile complète comprenant un backend Laravel et une application mobile Android native (Kotlin).

## Description
Ce projet vise à gérer la location et la vente de caftans (Dar Caftan). Il permet aux utilisateurs de parcourir les collections, de passer des commandes et de gérer leurs réservations.

L'architecture est divisée en deux parties :
- **Backend** : API RESTful développée avec Laravel.
- **Frontend** : Application mobile Android développée avec Kotlin / Java.

## Fonctionnalités Principales
- Catalogue de caftans
- Gestion du panier et des commandes
- Authentification utilisateur
- Dashboard administrateur (Backend)

## Documentation
[Consulter le Cahier de Charge (PDF)](docs/Cahier_de_charge.pdf)

## Démo

[Voir/Télécharger la vidéo de démonstration (Raw)](https://github.com/Oumaymaa659/dev_mobile/raw/main/media/demo.mp4)

## Installation
### Backend
1. Naviguer dans le dossier `Backend`.
2. Installer les dépendances : `composer install`
3. Configurer le fichier `.env`.
4. Lancer le serveur : `php artisan serve`

### Mobile
1. Ouvrir le dossier `Frontend/front-dar/darcaftan2` dans Android Studio.
2. Synchroniser le projet avec Gradle.
3. Lancer l'application sur un émulateur ou un appareil physique.
