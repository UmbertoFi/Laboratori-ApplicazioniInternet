package it.polito.ai.demo.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder(toBuilder = true)
@Setter
@Getter
public class PrenotazioneDTONew {

    private int id_bambino;
    private int id_fermata;
    private String verso;
}
