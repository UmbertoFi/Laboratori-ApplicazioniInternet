package com.example.demo.Entity;

import com.example.demo.DTO.*;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
public class Fermata {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    private int n_seq;
    @ManyToOne
    @JoinColumn(name = "id_linea")
    private Linea linea;
    private String ora_andata;
    private String ora_ritorno;
    @Builder.Default
    @OneToMany(mappedBy = "fermata", cascade = CascadeType.ALL)
    private List<Prenotazione> prenotazioni = new ArrayList<Prenotazione>();

    public FermataDTO convertToDTO() {
        FermataDTO f = FermataDTO.builder()
                .id(this.id)
                .nome(this.nome)
                .n_seq(this.n_seq)
                .ora_andata(this.ora_andata)
                .ora_ritorno(this.ora_ritorno)
                .build();

        return f;
    }

    public DettagliLineaDTO convertToDettagliLineaDTO(String verso) {
        if (verso.compareTo("andata") == 0) {
            DettagliLineaDTO dl = DettagliLineaDTO.builder()
                    .id(this.id)
                    .nome(this.nome)
                    .ora(this.ora_andata)
                    .build();
            return dl;
        } else {
            DettagliLineaDTO dl = DettagliLineaDTO.builder()
                    .id(this.id)
                    .nome(this.nome)
                    .ora(this.ora_ritorno)
                    .build();
            return dl;
        }
    }

    public DettagliLineaPersoneDTO convertToDettagliLineaPersoneDTO(List<String> strings) {
        DettagliLineaPersoneDTO dtl = DettagliLineaPersoneDTO.builder()
                .id(this.id)
                .nome(this.nome)
                .persone(strings)
                .build();
        return dtl;

    }

    public DettagliLineaPersoneDTONew convertToDettagliLineaPersoneDTONew(List<PersonaDTONew> persone, int verso) {
        String ora;
        if(verso==0)
            ora=ora_andata;
        else
            ora=ora_ritorno;
        DettagliLineaPersoneDTONew dtl = DettagliLineaPersoneDTONew.builder()
                .id_fermata(this.id)
                .nome(this.nome)
                .ora(ora)
                .persone(persone)
                .build();
        return dtl;

    }


}
