package it.polito.ai.demo.Entity;


import it.polito.ai.demo.DTO.FermataDTO;
import it.polito.ai.demo.DTO.LineaDTO;
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

    @Builder.Default
    @OneToMany(mappedBy = "linea", cascade = CascadeType.ALL)
    private List<Corsa> corse = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "linea", cascade = CascadeType.ALL)
    private List<Fermata> fermate = new ArrayList<Fermata>();

    @Builder.Default
    @OneToMany(mappedBy = "id.linea", cascade = CascadeType.ALL)
    private List<Disponibilita> disponibilita = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "linea", cascade = CascadeType.ALL)
    private List<Turno> turni = new ArrayList<>();

     public LineaDTO convertToDTO() {

        List<FermataDTO> fermate = new ArrayList<FermataDTO>();
        for (Fermata f : this.fermate)
            fermate.add(f.convertToDTO());

        LineaDTO l = LineaDTO.builder()
                .id(this.id)
                .nome(this.nome)
                .fermate(fermate)
                .build();
        return l;
    }

    /* public NomeLineaDTO convertToNomeLineaDTO() {
        NomeLineaDTO l = NomeLineaDTO.builder()
                .nome(this.nome)
                .build();

        return l;
    }  */
}