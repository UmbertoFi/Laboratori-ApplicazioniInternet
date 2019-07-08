package it.polito.ai.demo.DTO;

import it.polito.ai.demo.Entity.Fermata;
import it.polito.ai.demo.Entity.Prenotazione;
import it.polito.ai.demo.Entity.Utente;
import it.polito.ai.demo.Entity.idPrenotazione;
import it.polito.ai.demo.Repository.CorsaRepository;
import it.polito.ai.demo.Repository.FermataRepository;
import it.polito.ai.demo.Repository.PrenotazioneRepository;
import it.polito.ai.demo.Service.FermataService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Date;

@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
public class PrenotatoDTO {
    private int id_bambino;
    private String data;
    private String verso;
    private int id_fermata;

    public Prenotazione convertToEntity(FermataRepository fs, CorsaRepository cr) {

        String[] dataPieces = this.data.split("-");
        LocalDate n_data = LocalDate.of(Integer.parseInt(dataPieces[0]), Integer.parseInt(dataPieces[1]), Integer.parseInt(dataPieces[2]));

        Prenotazione p = Prenotazione.builder()
                .id(idPrenotazione.builder().id_bambino(this.id_bambino)
                                            .data(n_data)
                                            .verso(this.verso)
                                            .build())
                .fermata(fs.findById(this.id_fermata).get())
                .presente(false)
                .corsa(cr.getCorsaByAll(fs.findById(this.id_fermata).get().getLinea().getId(),n_data,verso))
                .build();

        return p;
    }
}
