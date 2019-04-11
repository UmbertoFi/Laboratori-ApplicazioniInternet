package it.polito.ai.lab2.demo.Entity;

import it.polito.ai.lab2.demo.DTO.FermataDTO;
import it.polito.ai.lab2.demo.DTO.LineaDTO;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PACKAGE)
@Getter
public class Fermata {
    @Id
    private int id;
    private String nome;
    private int n_seq;
    @ManyToOne
    @JoinColumn(name = "id_linea")
    private Linea linea;
    private String ora_andata;
    private String ora_ritorno;

    /* public FermataDTO convertToDTO() {
        FermataDTO f = FermataDTO.builder()
                .id(this.id)
                .nome(this.nome)
                .n_seq(this.n_seq)
                .ora_andata(this.ora_andata)
                .ora_ritorno(this.ora_ritorno)
                .build();

        return f;
    }
    /* @OneToMany(mappedBy = "fermata", cascade = CascadeType.ALL)
    private List<Prenotazione> prenotazioni = new ArrayList<Prenotazione>(); */


}
