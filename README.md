# DatacenterTCPServeur  

Application minimaliste de gestion de datacenter (salles, admins, serveurs) via le protocole TCP et les sockets en java.  
Projet mis en œuvre dans le cadre du module de `systèmes répartis et middleware`.  
Ce repo contient le serveur de l'application.  

## Architecture du projet  

Cette partie (serveur) de l'application s'articule autour de trois (3) couches essentiellement matérialisées respectivement par les packages :  

- DAO  

Représentant la couche d'accès aux données et contenant les interfaces respectives des objets métiers.  

- Domaine 

Contient les objets métiers.  

- Métier  

Contient la logique métier. Ici, il n'y a qu'une seule classe, en l'occurence `Serveur` chargée de répondre aux requêtes du ou des client(s).  

- Service  

Représente la couche service ; Elle contient q'une seule classe dénomée `DatabaseConnexionManager` permettant la connexion à n'importe quelle base de donnée et retourne un objet représentant la connexion à votre BD.  

## Notes  

Vous trouverez également un repertoire `lib`. Il contient un `driver` pour `MySQL` comme j'utilise ce `SGBD`.  
