package ma.emsi.benazzouzwalid.springatatp1.llm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ma.emsi.benazzouzwalid.springatatp1.web.dto.GuideTouristiqueResponse;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class GuideTouristiqueService {

    private static final Pattern MARKDOWN_CODE_FENCE = Pattern.compile("```(?:json)?\\s*([\\s\\S]+?)\\s*```", Pattern.CASE_INSENSITIVE);

    private final GuideTouristique guideTouristique;
    private final ObjectMapper objectMapper;

    public GuideTouristiqueService(GuideTouristique guideTouristique, ObjectMapper objectMapper) {
        this.guideTouristique = guideTouristique;
        this.objectMapper = objectMapper;
    }

    public GuideTouristiqueResponse genererInformations(String lieuOuPays, int nbEndroits) {
        if (nbEndroits <= 0) {
            throw new IllegalArgumentException("Le nombre d'endroits a visiter doit etre strictement positif.");
        }

        String rawResponse = guideTouristique.genererGuide(lieuOuPays, nbEndroits);
        if (rawResponse == null || rawResponse.isBlank()) {
            throw new GuideTouristiqueException("La reponse du LLM est vide.");
        }

        String sanitized = sanitize(rawResponse);
        try {
            return objectMapper.readValue(sanitized, GuideTouristiqueResponse.class);
        } catch (JsonProcessingException e) {
            throw new GuideTouristiqueException("Impossible d'interpreter la reponse du LLM en JSON: " + sanitized, e);
        }
    }

    private static String sanitize(String content) {
        String trimmed = content.trim();

        Matcher matcher = MARKDOWN_CODE_FENCE.matcher(trimmed);
        if (matcher.find()) {
            return matcher.group(1).trim();
        }

        return trimmed;
    }
}

