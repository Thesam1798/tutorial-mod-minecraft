# Tutorial Mod Minecraft

Ce project est un mod Minecraft suivant un tutoriel pour apprendre à créer des mods Minecraft avec Fabric.

## Fonctionnalités
- Vérification de mise à jour du mod depuis GitHub (github api release)

## Prérequis
- Java 21
- Minecraft Fabric
- Compte GitHub (pour les GitHub Actions et secrets)

## Configuration des secrets
Ce projet utilise GitHub Actions qui nécessitent la configuration des secrets suivants :

- `SNYK_TOKEN` : Un token [snyk](https://snyk.io/) pour la vérification des dépendances et des vulnérabilités.
- `SNYK_ORG` : Organisation id de [snyk](https://snyk.io/).
- `PERSONAL_GITHUB_TOKEN` : Un token GitHub utilisé pour les opérations nécessitant une authentification, telles que le push sur le répository, la création d'issues et de Pull Requests (PR).
- `GPG_SIGNING_KEY` : La clé de signature GPG utilisée pour signer les commits et les tags. Assure l'intégrité et la vérification de l'auteur des commits.

Pour configurer ces secrets, allez dans les paramètres de votre répository GitHub, dans la section "Secrets", et ajoutez ces deux secrets avec leurs valeurs respectives.