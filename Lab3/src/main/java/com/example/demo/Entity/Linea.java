package com.example.demo.Entity;

import com.example.demo.DTO.FermataDTO;
import com.example.demo.DTO.LineaDTO;
import com.example.demo.DTO.NomeLineaDTO;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity

@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
public class Linea {

    @Id
    private int id;
    private String nome;
    private String amministratore;
    @Builder.Default
    @OneToMany(mappedBy = "linea", cascade = CascadeType.ALL)
    private List<Fermata> fermate = new ArrayList<Fermata>();

    public LineaDTO convertToDTO() {

        List<FermataDTO> fermate = new ArrayList<FermataDTO>();
        for (Fermata f : this.fermate)
            fermate.add(f.convertToDTO());

        LineaDTO l = LineaDTO.builder()
                .id(this.id)
                .nome(this.nome)
                .amministratore(this.amministratore)
                .fermate(fermate)
                .build();
        return l;
    }

    public NomeLineaDTO convertToNomeLineaDTO() {
        NomeLineaDTO l = NomeLineaDTO.builder()
                .nome(this.nome)
                .build();

        return l;
    }
}