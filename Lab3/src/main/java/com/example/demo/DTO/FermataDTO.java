package com.example.demo.DTO;

import com.example.demo.Entity.Fermata;
import com.example.demo.Entity.Linea;
import lombok.*;

@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
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
}
