package it.polito.ai.lab2.demo.Entity;


import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Data
public class Prenotazione {
    @EmbeddedId
    private idPrenotazione id;// Id multiplo: Persona-Data-Andata/ritorno
    @ManyToOne
    @JoinColumn(name = "id_fermata")
    private Fermata fermata;
}