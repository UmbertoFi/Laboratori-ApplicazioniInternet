package it.polito.ai.lab2.demo.Entity;


import it.polito.ai.lab2.demo.DTO.PrenotazioneDTO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Entity
@Setter
@Getter
public class Prenotazione {
    @EmbeddedId
    private idPrenotazione id;// Id multiplo: Persona_Data_Verso
    @ManyToOne
    @JoinColumn(name = "id_fermata")
    private Fermata fermata;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Prenotazione that = (Prenotazione) o;
        return Objects.equals(id.toString(), that.id.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fermata);
    }

    public PrenotazioneDTO convertToDTO() {

        PrenotazioneDTO p = PrenotazioneDTO.builder()
                .persona(this.getId().getPersona())
                .data(this.getId().getData())
                .verso(this.getId().getVerso())
                .id_fermata(this.getFermata().getId())
                .build();

        return p;
    }
}