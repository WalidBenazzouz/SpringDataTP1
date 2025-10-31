package ma.emsi.benazzouzwalid.appresttemplate.controller;

import ma.emsi.benazzouzwalid.appresttemplate.service.EtudiantClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/client/rest") // Changé pour éviter les conflits
public class EtudiantClientController {

    @Autowired
    private EtudiantClientService etudiantClientService;

    @GetMapping
    public List<Map<String, Object>> getAllEtudiants() {
        return etudiantClientService.getAllEtudiants();
    }

    @GetMapping("/{p}")
    public Map<String, Object> getEtudiantById(@PathVariable("p") Integer id) {
        return etudiantClientService.getEtudiantById(id);
    }

    @PostMapping
    public void addEtudiant(@RequestBody Map<String, Object> etudiant) {
        etudiantClientService.addEtudiant(etudiant);
    }

    @PutMapping("/{p}")
    public String updateEtudiant(@PathVariable("p") Integer id, @RequestBody Map<String, Object> etudiant) {
        return etudiantClientService.updateEtudiant(id, etudiant);
    }

    @DeleteMapping("/{p}")
    public String deleteEtudiant(@PathVariable("p") Integer id) {
        return etudiantClientService.deleteEtudiant(id);
    }

    @GetMapping("/nam/{n}")
    public List<Map<String, Object>> getEtudiantsParNom(@PathVariable("n") String nom) {
        return etudiantClientService.getEtudiantsByName(nom);
    }

    @GetMapping("/{p}/{s}/{t}") // Ex: /client/rest/0/2/nom,desc
    public List<Map<String, Object>> getEtudiants(@PathVariable("p") int page, @PathVariable("s") int size, @PathVariable("t") String sort){
        return etudiantClientService.getEtudiants(page, size, sort);
    }
}