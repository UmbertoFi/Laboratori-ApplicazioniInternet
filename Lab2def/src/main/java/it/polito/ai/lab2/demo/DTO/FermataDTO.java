package it.polito.ai.lab2.demo.DTO;

import it.polito.ai.lab2.demo.Entity.Fermata;
import it.polito.ai.lab2.demo.Entity.Linea;
import lombok.*;

@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PACKAGE)
@Getter
public class FermataDTO {
    private int id;
    private String nome;
    private int n_seq;
    private String ora_andata;
    private String ora_ritorno;

    public Fermata convertToEntity(Linea l) {

        Fermata f = Fermata.builder()
                .id(this.id)
                .nome(this.nome)
                .n_seq(this.n_seq)
                .linea(l)
                .ora_andata(this.ora_andata)
                .ora_ritorno(this.ora_ritorno)
                .build();

        return f;
    }
    /* private List<Prenotazione> prenotazioni = new ArrayList<Prenotazione>(); */
}
