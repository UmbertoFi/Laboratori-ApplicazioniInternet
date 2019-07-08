package it.polito.ai.demo.DTO;


import it.polito.ai.demo.Entity.Bambino;
import it.polito.ai.demo.Entity.Utente;
import lombok.*;

@Builder(toBuilder = true)
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PersonaDTONew {
    private int id_bambino;
    private String nome;
    private String cognome;
    private boolean selected;
}
