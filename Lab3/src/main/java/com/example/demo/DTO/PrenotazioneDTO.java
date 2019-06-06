package com.example.demo.DTO;

import com.example.demo.Entity.Prenotazione;
import com.example.demo.Entity.idPrenotazione;
import lombok.*;

@Builder(toBuilder = true)
@Setter
@Getter
public class PrenotazioneDTO {

    private int id_bambino;
    private String fermata;
    private String verso;
}
