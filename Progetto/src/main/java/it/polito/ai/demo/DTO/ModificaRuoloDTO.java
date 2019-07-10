package it.polito.ai.demo.DTO;


import lombok.*;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class ModificaRuoloDTO {

private String username;
    private String azione;
    private String linea;
}
