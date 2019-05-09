package com.example.demo.DTO;


import lombok.*;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class ModificaRuoloDTO {

    private String azione;
    private String linea;
}
