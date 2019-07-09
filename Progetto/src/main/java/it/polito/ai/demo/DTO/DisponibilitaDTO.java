package it.polito.ai.demo.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder(toBuilder = true)
@Setter
@Getter
public class DisponibilitaDTO {

    private String linea;
    private String data;
    private String verso;
    /*private int id_fermataA;
    private int id_fermataR;*/
}