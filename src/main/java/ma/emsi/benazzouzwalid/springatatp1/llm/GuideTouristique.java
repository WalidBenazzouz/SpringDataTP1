package ma.emsi.benazzouzwalid.springatatp1.llm;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

/**
 * LangChain4j service interface that instructs Gemini to generate tourist information.
 */
public interface GuideTouristique {

    @SystemMessage("""
            Tu es un guide touristique francophone.
            Reponds uniquement en JSON strictement valide avec le format exact suivant :
            {
              "ville_ou_pays": "nom de la ville ou du pays",
              "endroits_a_visiter": ["endroit 1", "endroit 2"],
              "prix_moyen_repas": "<prix> <devise du pays>"
            }
            N'utilise pas Markdown, pas de texte additionnel.
            Mentionne des lieux emblematique et un prix moyen fidele a la devise locale.
            """)
    @UserMessage("""
            Fournis les informations touristiques pour {lieu}.
            Donne exactement {nbEndroits} endroits a visiter et le prix moyen d'un repas pour une personne.
            """)
    String genererGuide(@V("lieu") String lieuOuPays, @V("nbEndroits") int nbEndroits);
}

