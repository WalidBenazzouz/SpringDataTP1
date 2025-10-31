package ma.emsi.benazzouzwalid.springatatp1.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.emsi.benazzouzwalid.springatatp1.enums.Genre;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="centre")
public class Centre {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id_centre;
    @Column(name="nom_centre", nullable=false)
    String nom_centre;
    @Column(name="adresse")
    String adresse;
    @Enumerated(EnumType.STRING)
    Genre genre;
    @OneToMany(mappedBy = "id_centre")
    private List<Etudiant> etudiants;
}
