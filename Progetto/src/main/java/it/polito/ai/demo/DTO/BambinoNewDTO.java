package it.polito.ai.demo.DTO;


import it.polito.ai.demo.Entity.Bambino;
import it.polito.ai.demo.Entity.Utente;
import lombok.*;

@Builder(toBuilder = true)
@Setter
@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor
public class BambinoNewDTO {
    private String nome;
    private String cognome;

    public Bambino convertToEntity(Utente u) {
        Bambino b= Bambino.builder()
                .cognome(cognome)
                .nome(nome)
                .genitore(u)
                .build();

        return b;
    }
}