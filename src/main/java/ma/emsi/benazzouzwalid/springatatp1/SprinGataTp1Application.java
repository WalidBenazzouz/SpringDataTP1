package ma.emsi.benazzouzwalid.springatatp1;

import ma.emsi.benazzouzwalid.springatatp1.enums.Genre;
import ma.emsi.benazzouzwalid.springatatp1.model.Etudiant;
import ma.emsi.benazzouzwalid.springatatp1.repositories.EtudiantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SprinGataTp1Application implements CommandLineRunner {
    @Autowired
    EtudiantRepository etudiantRepository;
    @Override
    public void run(String... args) throws Exception {
        Etudiant etudiant1 = Etudiant.builder()
                .nom("Benazzouz")
                .prenom("Walid")
                .genre(Genre.Homme)
                .build();
        etudiantRepository.save(etudiant1);
        Etudiant etudiant2 = Etudiant.builder()
                .nom("Msik")
                .prenom("Amine")
                .genre(Genre.Homme)
                .build();
        etudiantRepository.save(etudiant2);
        Etudiant etudiant3 = Etudiant.builder()
                .nom("Iness")
                .prenom("Bounouifa")
                .genre(Genre.Femme)
                .build();
        etudiantRepository.save(etudiant3);
    }

    public static void main(String[] args) {
        SpringApplication.run(SprinGataTp1Application.class, args);
    }

}
