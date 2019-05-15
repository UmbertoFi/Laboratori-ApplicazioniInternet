package com.example.demo.Entity;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
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
    private String username;

    private String NomeLinea;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        idRuolo idRuolo = (idRuolo) o;
        return username.equals(idRuolo.username) &&
                NomeLinea.equals(idRuolo.NomeLinea);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, NomeLinea);
    }
}
