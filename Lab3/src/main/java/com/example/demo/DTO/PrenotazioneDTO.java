package com.example.demo.DTO;

import lombok.*;

@Builder(toBuilder = true)
@Setter
@Getter
public class PrenotazioneDTO {

    private String Persona;
    private String fermata;
    private String verso;
}
