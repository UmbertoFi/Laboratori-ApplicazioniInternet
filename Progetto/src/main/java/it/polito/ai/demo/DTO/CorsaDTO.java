package it.polito.ai.demo.DTO;

import it.polito.ai.demo.Entity.Corsa;
import it.polito.ai.demo.Entity.idCorsa;
import it.polito.ai.demo.Repository.LineaRepository;
import it.polito.ai.demo.Service.CorsaService;
import lombok.*;

import java.time.LocalDate;


@Builder(toBuilder = true)
@Setter
@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor
public class CorsaDTO {
    private String data;
    private int id_linea;
    private String verso;

    public Corsa convertToEntity(LineaRepository lr) {

        String[] dataPieces = this.data.split("-");
        LocalDate n_data = LocalDate.of(Integer.parseInt(dataPieces[0]), Integer.parseInt(dataPieces[1]), Integer.parseInt(dataPieces[2]));

        Corsa c=Corsa.builder()
                .data(n_data)
                .linea(lr.findById(id_linea).get())
                .verso(verso)
                .build();
        return c;
    }
}
