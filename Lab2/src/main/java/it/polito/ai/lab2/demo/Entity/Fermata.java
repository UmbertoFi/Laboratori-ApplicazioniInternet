package it.polito.ai.lab2.demo.Entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PACKAGE)
@Getter
public class Fermata {
    @Id
    private int id;
    private String nome;
    private int n_seq;
    @ManyToOne
    @JoinColumn(name = "id_linea")
    private Linea linea;
    private String ora_andata;
    private String ora_ritorno;
    @OneToMany(mappedBy = "fermata", cascade = CascadeType.ALL)
    private Set<Prenotazione> prenotazioni = new HashSet<Prenotazione>();


}
