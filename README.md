# SprinGataTP1 & Clients HTTP

Ce depot illustre un ecosysteme Spring Boot centre sur la gestion des etudiants. Il comprend :

- `SprinGataTP1` : API REST exposee via Spring Data REST avec persistence H2 et representation HAL.
- `AppRestTemplate` : client HTTP base sur `RestTemplate` qui repropose des routes simplifiees.
- `AppWebClient` : squelette d un client reactif WebClient (a completer).
- `AppFeignClient` : squelette d un client OpenFeign (a completer).

L objectif est de comparer plusieurs approches clientes pour consommer la meme API serveur.

## Architecture du depot

- `src/main/java/.../springatatp1` : entites `Etudiant` et `Centre`, enumeration `Genre`, repository REST `EtudiantRepository` et initialisation de donnees via `CommandLineRunner`.
- `AppRestTemplate/` : application Spring Boot independante exposant des routes `/client/rest/**` qui deleguent aux methodes du service `EtudiantClientService`.
- `AppWebClient/` et `AppFeignClient/` : projets Maven preconfigures prets a recevoir une implementation WebClient ou Feign.

## Prerequis

- JDK 21 recommande pour `SprinGataTP1` (declare dans le `pom.xml`).
- JDK 17 pour les sous-projets clients (configuration Maven actuelle).
- Maven Wrapper deja fourni (`mvnw`). Aucun Maven global n est necessaire.
- Port 8080 libre pour l API principale et ports secondaires pour les clients (par defaut Spring Boot selectionne 8080 ; ajustez si besoin via `server.port`).

## Demarrage rapide

1. **Lancer l API principale**
   ```bash
   cd /chemin/vers/le/projet   # dossier contenant ce README et le pom.xml racine
   ./mvnw spring-boot:run
   ```
   Au demarrage, trois etudiants sont inseres automatiquement.

2. **Tester l API REST**
   - `GET http://localhost:8080/students` - liste complete (HAL `_embedded`).
   - `GET http://localhost:8080/students/{id}` - detail d un etudiant.
   - `POST http://localhost:8080/students` - creation (payload JSON conforme a l entite `Etudiant`).
   - `PUT` / `PATCH` / `DELETE` - operations standard sur les ressources HAL.
   - `GET http://localhost:8080/students/search/findEtudiantByNom?nom=Walid` - recherche par nom.

   > **Astuce HAL** : la reponse contient des liens `_links` faciles a explorer avec un client HAL (Postman, Insomnia, extensions VS Code, etc.).

3. **Demarrer le client RestTemplate (optionnel)**
   ```bash
   cd AppRestTemplate
   ./mvnw spring-boot:run
   ```
   Ce service ouvre des endpoints `GET` / `POST` / `PUT` / `DELETE` sous `http://localhost:8081/client/rest` (definissez `server.port=8081` dans `application.properties` pour eviter les conflits). Les URLs cibles sont configurees dans `EtudiantClientService` via `BASE_URL` et `BASE_URL2` ; adaptez-les a l URL effective de l API (`students`).

4. **Completer WebClient ou Feign**
   - Implantez `AppWebClient/src/main/java/.../service/EtudiantWebClient` pour fournir des methodes non bloquantes.
   - Annotez `AppFeignClient/.../client/EtudiantClient` avec `@FeignClient` et exposez les operations necessaires.

## Jeu de donnees initial

`SprinGataTp1Application` implemente `CommandLineRunner` pour inserer trois etudiants (Walid, Amine, Iness) des le lancement. Les identifiants sont generes en base H2 (`GenerationType.AUTO`).

## Base de donnees

- Base en memoire H2 (URL JDBC implicite : `jdbc:h2:mem:testdb`).
- Pour activer la console web, ajoutez `spring.h2.console.enabled=true` et `spring.h2.console.path=/h2-console` dans `src/main/resources/application.properties`.
- La relation `Centre` <-> `Etudiant` est deja modelisee (cle etrangere `id_centre`).

## Tests

Executer les tests unitaires (actuellement un smoke test `contextLoads`) :

```bash
./mvnw test
```

## Pistes d amelioration

- Finaliser les implementations WebClient et Feign et ajouter des exemples d appels.
- Centraliser la configuration (ports, URLs cibles) via des profils Spring ou Spring Cloud Config.
- Ajouter des DTO et de la validation (`jakarta.validation`) cote API principale.
- Enrichir la suite de tests (MockMvc, tests d integration REST, tests des clients).

## Licence

Projet academique sans licence explicite. Ajoutez un fichier `LICENSE` si necessaire.
