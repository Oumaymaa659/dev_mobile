# Instructions pour ajouter des vidéos dans l'application

## Emplacement des vidéos

Placez vos vidéos dans le dossier : `app/src/main/res/raw/`

## Noms des fichiers vidéo

Pour utiliser différentes vidéos dans l'interface historique, nommez vos fichiers :

1. **video1.mp4** - Vidéo pour la section "Atelier et savoir-faire" (première vidéo)
2. **video2.mp4** - Vidéo pour la section "Créations et collections" (deuxième vidéo)
3. **video3.mp4** - Vidéo pour la section "Artisanat et techniques" (troisième vidéo)
4. **video4.mp4** - Vidéo pour la section "Collection finale" (quatrième vidéo)

## Format des vidéos

- **Format recommandé** : MP4
- **Codec** : H.264 (compatible Android)
- **Résolution** : 720p ou 1080p
- **Durée** : Courtes vidéos (30 secondes à 2 minutes recommandées)

## Sources de vidéos

Vous pouvez télécharger des vidéos de :
- **Pinterest** : Recherchez "caftan marocain", "moroccan caftan", "haute couture marocaine"
- **YouTube** : Utilisez un convertisseur YouTube vers MP4
- **Sites de mode marocaine** : Sites spécialisés en caftans et haute couture marocaine

## Comment ajouter les vidéos

1. Téléchargez ou créez vos vidéos
2. Renommez-les : `video1.mp4`, `video2.mp4`, `video3.mp4`, `video4.mp4`
3. Copiez-les dans le dossier `app/src/main/res/raw/`
4. Recompilez l'application

## Fallback automatique

Si une vidéo spécifique n'est pas trouvée, l'application utilisera automatiquement `video.mp4` comme vidéo de remplacement.

## Exemple de structure

```
app/src/main/res/raw/
├── video.mp4          (vidéo principale - utilisée comme fallback)
├── video1.mp4         (vidéo 1 - Atelier)
├── video2.mp4         (vidéo 2 - Créations)
├── video3.mp4         (vidéo 3 - Artisanat)
└── video4.mp4         (vidéo 4 - Collection)
```

## Note importante

- Les vidéos doivent être en minuscules (video1.mp4, pas Video1.mp4)
- Les vidéos sont lues en boucle automatiquement
- Assurez-vous que les vidéos ne sont pas trop lourdes (max 10-20 MB chacune)

