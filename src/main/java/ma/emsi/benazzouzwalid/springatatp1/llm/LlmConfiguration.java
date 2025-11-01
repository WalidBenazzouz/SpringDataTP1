package ma.emsi.benazzouzwalid.springatatp1.llm;

import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Configuration
public class LlmConfiguration {

    @Bean
    public GuideTouristique guideTouristique(
            @Value("${gemini.api-key:}") String configuredApiKey,
            @Value("${gemini.model:gemini-1.5-flash}") String modelName) {

        String apiKey = StringUtils.hasText(configuredApiKey) ? configuredApiKey : System.getenv("GEMINI_API_KEY");

        if (StringUtils.hasText(apiKey)) {
            GoogleAiGeminiChatModel model = GoogleAiGeminiChatModel.builder()
                    .apiKey(apiKey)
                    .modelName(modelName)
                    .temperature(0.4)
                    .maxOutputTokens(1024)
                    .build();

            return AiServices.builder(GuideTouristique.class)
                    .chatLanguageModel(model)
                    .build();
        }

        return (lieuOuPays, nbEndroits) -> fallbackResponse(lieuOuPays, nbEndroits);
    }

    private static String fallbackResponse(String lieuOuPays, int nbEndroits) {
        String lieu = StringUtils.hasText(lieuOuPays) ? lieuOuPays : "Inconnu";
        int nombre = nbEndroits > 0 ? nbEndroits : 2;

        String endroits = IntStream.range(0, nombre)
                .mapToObj(index -> MessageFormat.format("\"Information indisponible (cle API Gemini manquante) #{0}\"", index + 1))
                .collect(Collectors.joining(", "));

        return "{" +
                "\n  \"ville_ou_pays\": \"" + lieu + "\"," +
                "\n  \"endroits_a_visiter\": [" + endroits + "]," +
                "\n  \"prix_moyen_repas\": \"Non disponible\"\n}";
    }
}

