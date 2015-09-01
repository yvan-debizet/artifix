Logiciel de la table de tir conçu pour et par le Binet Artifix.

Ce logiciel servira lors de la conception et du lancement de chaque feux du Binet Artifix.

Il doit réaliser deux fonctions principaux :
- Aider à la préparation des feux d'artifice.
- Permettre de commander en temps réel le lancement d'un feux d'artifice effectué a l'aide de la table de tir construite en parallèle.

Le projet suivra principalement le paradigme modele-view-listener et aura une architecture à niveaux/couches.
Voici une première ébauche des diverses parties que le projet contiendra :

++couche 0 : librairie découplé du problème
    ++ObservableSynchro
    ++ObserverSyncho

- couche 1 : Modèle et traitement des données 
    - Firework
    - Fire
    - FireType
    - Table
    - TableFireLine
    - Chronometer
    - TriggerFire
    - sendCommand
    - FireworkSaver

- couche 2 : View
    - Frame
    - Holder... (for holding some usefull elements ?)
    - ViewTimeLineInput
    - ViewTimeLineShow
    - ViewShowFirework(a sum list and chronologique list)
    - ViewSetFireworkSetting
    - ViewSetFire
    - ViewShowTable
    - ViewShowMagasin (autorisation brezac ?)
    - ViewConnection (table connect button with some led for show it and a connect button for softly disable or enable fire command)

- couche optionelle : éléments optionnelles du projet
    - ViewShowSimulation (firework and simple fire)
    - ViewSetFirePosition
    - MusicPlayerSynchronised

