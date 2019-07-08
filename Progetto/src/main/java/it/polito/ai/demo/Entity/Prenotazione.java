package it.polito.ai.demo.Entity;


import lombok.*;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
public class Prenotazione {
    @EmbeddedId
    private idPrenotazione id;
    @ManyToOne
    @JoinColumn(name = "id_fermata")
    private Fermata fermata;
    private boolean presente;
    @ManyToOne
    @JoinColumn(name = "id_corsa")
    private Corsa corsa;

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

    /* public PrenotatoDTO convertToDTO() {

        PrenotatoDTO p = PrenotatoDTO.builder()
                .id_bambino(this.getId().getId_bambino())
                .data(this.getId().getLocalData())
                .verso(this.getId().getVerso())
                .id_fermata(this.getFermata().getId())
                .build();

        return p;
    } */
}