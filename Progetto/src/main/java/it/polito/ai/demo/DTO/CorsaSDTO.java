package it.polito.ai.demo.DTO;

import it.polito.ai.demo.Entity.Corsa;
import it.polito.ai.demo.Entity.idCorsa;
import it.polito.ai.demo.Repository.LineaRepository;
import lombok.*;

import java.time.LocalDate;


@Builder(toBuilder = true)
@Setter
@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor
public class CorsaSDTO {
    private String data;
    private String nome_linea;
    private String verso;


}
