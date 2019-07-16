package it.polito.ai.demo.Entity;

import lombok.*;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
public class Turno {
    @EmbeddedId
    private idTurno id;
    @ManyToOne
    @JoinColumn(name = "id_linea")
    private Linea linea;
    private Boolean consolidato;


}