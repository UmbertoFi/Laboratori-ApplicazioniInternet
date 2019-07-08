package it.polito.ai.demo.Entity;

import lombok.*;
import javax.persistence.*;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
public class Bambino {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    private String cognome;
    @ManyToOne
    @JoinColumn(name = "id_genitore")
    private Utente genitore;

    /* public BambinoDTO convertToBambinoDTO() {
        BambinoDTO b = BambinoDTO.builder()
                .nome(this.nome)
                .cognome(this.cognome)
                .id_bambino(this.id)
                .build();
        return b;
    } */


}
