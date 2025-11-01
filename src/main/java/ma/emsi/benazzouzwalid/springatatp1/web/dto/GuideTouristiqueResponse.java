package ma.emsi.benazzouzwalid.springatatp1.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record GuideTouristiqueResponse(
        @JsonProperty("ville_ou_pays") String ville_ou_pays,
        @JsonProperty("endroits_a_visiter") List<String> endroits_a_visiter,
        @JsonProperty("prix_moyen_repas") String prix_moyen_repas
) {
}

