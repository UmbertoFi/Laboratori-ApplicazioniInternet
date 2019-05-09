package com.example.demo.DTO;

import com.example.demo.Entity.Fermata;
import com.example.demo.Entity.Linea;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
public class LineaDTO {
    private int id;
    private String nome;
    private String amministratore;
    @Builder.Default
    private List<FermataDTO> fermate = new ArrayList<FermataDTO>();

    public Linea convertToEntity() {

        Linea l = Linea.builder()
                .id(this.id)
                .nome(this.nome)
                .amministratore(this.amministratore)
                .build();

        List<Fermata> fermate = new ArrayList<Fermata>();
        for (FermataDTO f : this.fermate)
            fermate.add(f.convertToEntity(l));

        l.setFermate(fermate);

        return l;

    }
}
