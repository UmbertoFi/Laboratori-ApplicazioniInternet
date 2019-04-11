package it.polito.ai.lab2.demo.Entity;

import it.polito.ai.lab2.demo.DTO.FermataDTO;
import it.polito.ai.lab2.demo.DTO.LineaDTO;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PACKAGE)
@Getter
public class Linea {
    @Id
    private int id;
    private String nome;
    private String amministratore;
    @OneToMany(mappedBy = "linea", cascade = CascadeType.ALL)
    private List<Fermata> fermate = new ArrayList<Fermata>();


    /* public LineaDTO convertToDTO() {
        LineaDTO l = LineaDTO.builder()
                .id(this.id)
                .nome(this.nome)
                .amministratore(this.amministratore)
                .build();

        List<FermataDTO> fermate = new ArrayList<FermataDTO>();
        for(Fermata f : this.fermate)
            fermate.add(f.convertToDTO(l));

        l.toBuilder().fermate(fermate).build();

        return l;
    } */
}