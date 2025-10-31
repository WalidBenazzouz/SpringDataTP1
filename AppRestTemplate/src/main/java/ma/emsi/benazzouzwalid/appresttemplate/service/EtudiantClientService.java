package ma.emsi.benazzouzwalid.appresttemplate.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Map;

@Service
public class EtudiantClientService {

    // L'URL de base de votre API "Serveur"
    private final String BASE_URL = "http://localhost:8080/etudiants";

    // URL pour la recherche par nom
    private static final String BASE_URL2 = "http://localhost:8080/etudiants/search/findEtudiantByNom?n={nom}";

    // RestTemplate est l'outil qui effectue les appels HTTP
    private final RestTemplate restTemplate = new RestTemplate();


    /**
     * Récupère tous les étudiants.
     * Note: La réponse de Spring Data REST est imbriquée.
     */
    public List<Map<String, Object>> getAllEtudiants() {
        // 1. Fait un appel GET à BASE_URL [cite: 70]
        Map response = restTemplate.getForObject(BASE_URL, Map.class);

        // 2. Extrait la liste "etudiants" de la clé "_embedded" [cite: 71, 72]
        Map<String, Object> embedded = (Map<String, Object>) response.get("_embedded");
        return (List<Map<String, Object>>) embedded.get("etudiants");
    }

    /**
     * Récupère un étudiant par son ID.
     */
    public Map<String, Object> getEtudiantById(Integer id) {
        String url = BASE_URL + "/" + id;
        return restTemplate.getForObject(url, Map.class);
    }

    /**
     * Ajoute un nouvel étudiant.
     */
    public void addEtudiant(Map<String, Object> etudiant) {
        restTemplate.postForObject(BASE_URL, etudiant, Map.class);
    }

    /**
     * Met à jour un étudiant existant.
     */
    public String updateEtudiant(Integer id, Map<String, Object> etudiant) {
        String url = BASE_URL + "/" + id;
        restTemplate.put(url, etudiant);
        return "Etudiant " + id + " updated";
    }

    /**
     * Supprime un étudiant.
     */
    public String deleteEtudiant(Integer id) {
        String url = BASE_URL + "/" + id;
        restTemplate.delete(url);
        return "Etudiant " + id + " deleted";
    }

    /**
     * Recherche des étudiants par nom.
     */
    public List<Map<String, Object>> getEtudiantsByName(String nom) {
        // Passe le paramètre 'nom' dans l'URL
        Map response = restTemplate.getForObject(BASE_URL2, Map.class, nom);
        Map<String, Object> embedded = (Map<String, Object>) response.get("_embedded");
        return (List<Map<String, Object>>) embedded.get("etudiants");
    }

    /**
     * Récupère les étudiants avec pagination et tri.
     */
    public List<Map<String, Object>> getEtudiants(int page, int size, String sort) {
        String url = BASE_URL + "?page={page}&size={size}&sort={sort}";
        Map response = restTemplate.getForObject(url, Map.class, page, size, sort);

        Map<String, Object> embedded = (Map<String, Object>) response.get("_embedded");
        return (List<Map<String, Object>>) embedded.get("etudiants");
    }
}