package it.polito.ai.demo.Entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
public class Disponibilita {
    @EmbeddedId
    private idDisponibilita id;
    @ManyToOne
    @JoinColumn(name = "id_fermataA")
    private Fermata fermataA;
    @ManyToOne
    @JoinColumn(name = "id_fermataR")
    private Fermata fermataR;

}
