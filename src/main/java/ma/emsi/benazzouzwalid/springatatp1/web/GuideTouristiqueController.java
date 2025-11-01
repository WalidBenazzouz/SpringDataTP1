package ma.emsi.benazzouzwalid.springatatp1.web;

import ma.emsi.benazzouzwalid.springatatp1.llm.GuideTouristiqueException;
import ma.emsi.benazzouzwalid.springatatp1.llm.GuideTouristiqueService;
import ma.emsi.benazzouzwalid.springatatp1.web.dto.GuideTouristiqueResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/guide")
public class GuideTouristiqueController {

    private final GuideTouristiqueService guideTouristiqueService;

    public GuideTouristiqueController(GuideTouristiqueService guideTouristiqueService) {
        this.guideTouristiqueService = guideTouristiqueService;
    }

    @GetMapping("/lieu/{villeOuPays}")
    public GuideTouristiqueResponse obtenirGuide(
            @PathVariable("villeOuPays") String villeOuPays,
            @RequestParam(name = "nb", defaultValue = "2") int nbEndroits) {

        return guideTouristiqueService.genererInformations(villeOuPays, nbEndroits);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException exception) {
        return ResponseEntity.badRequest().body(Map.of(
                "erreur", exception.getMessage()
        ));
    }

    @ExceptionHandler(GuideTouristiqueException.class)
    public ResponseEntity<Map<String, String>> handleGuideTouristique(GuideTouristiqueException exception) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(Map.of(
                "erreur", exception.getMessage()
        ));
    }
}

