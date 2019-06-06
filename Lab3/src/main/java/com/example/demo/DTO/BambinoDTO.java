package com.example.demo.DTO;


import com.example.demo.Entity.Bambino;
import com.example.demo.Entity.Utente;
import lombok.*;

@Builder(toBuilder = true)
@Setter
@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor
public class BambinoDTO {
    private int id_bambino;
    private String nome;
    private String cognome;

    public Bambino convertToEntity(Utente u) {
        Bambino b= Bambino.builder()
                .id(this.id_bambino)
                .cognome(cognome)
                .nome(nome)
                .genitore(u)
                .build();

        return b;
    }
}
