package it.polito.ai.demo.Entity;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
public class idRuolo implements Serializable {

    @Email
    @ManyToOne
    @JoinColumn(name = "username")
    private Utente utente;
    private String NomeLinea;
    private String ruolo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        idRuolo idRuolo = (idRuolo) o;
        return utente.equals(idRuolo.utente) &&
                NomeLinea.equals(idRuolo.NomeLinea);
    }

    @Override
    public int hashCode() {
        return Objects.hash(utente, NomeLinea);
    }
}
