package it.polito.ai.lab2.demo.DTO;

import lombok.*;

import java.time.LocalDate;

@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
/*@Setter(value = AccessLevel.PACKAGE)*/
@Setter
@Getter
public class PrenotazioneDTO {
    private String persona;
    private LocalDate data;
    private String verso;
    private int id_fermata;
}
