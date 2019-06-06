package com.example.demo.DTO;

import com.example.demo.Entity.Fermata;
import com.example.demo.Entity.Prenotazione;
import com.example.demo.Entity.Utente;
import com.example.demo.Entity.idPrenotazione;
import com.example.demo.Repository.FermataRepository;
import com.example.demo.Repository.PrenotazioneRepository;
import com.example.demo.Service.FermataService;
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

    public Prenotazione convertToEntity(FermataRepository fs) {

        String[] dataPieces = this.data.split("-");
        LocalDate n_data = LocalDate.of(Integer.parseInt(dataPieces[0]), Integer.parseInt(dataPieces[1]), Integer.parseInt(dataPieces[2]));


        Prenotazione p = Prenotazione.builder()
                .id(idPrenotazione.builder().id_bambino(this.id_bambino)
                                            .data(n_data)
                                            .verso(this.verso)
                                            .build())
                .fermata(fs.findById(this.id_fermata).get())
                .presente(false)
                .build();

        return p;
    }
}
