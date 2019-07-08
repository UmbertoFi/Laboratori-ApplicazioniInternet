package it.polito.ai.demo.Entity;


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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UtenteRuolo that = (UtenteRuolo) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String getUsername(){
        return id.getUtente().getUserName();
    }
    public String getNomeLinea(){
        return id.getNomeLinea();
    }
}
