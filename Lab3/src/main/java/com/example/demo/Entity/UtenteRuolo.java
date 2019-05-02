package com.example.demo.Entity;


import lombok.*;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.util.Objects;

@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
@Entity
public class UtenteRuolo {

    @EmbeddedId
    private idRuolo id;

    private String Ruolo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UtenteRuolo that = (UtenteRuolo) o;
        return id.equals(that.id) &&
                Ruolo.equals(that.Ruolo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, Ruolo);
    }
}
